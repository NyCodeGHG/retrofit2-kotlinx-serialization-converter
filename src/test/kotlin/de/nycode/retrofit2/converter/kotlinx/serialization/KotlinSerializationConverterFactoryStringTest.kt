/*
 *
 *     Copyright 2018-2021 Jake Wharton, NyCode
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package de.nycode.retrofit2.converter.kotlinx.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.junit5.internal.MockWebServerExtension
import okhttp3.MediaType.Companion.toMediaType
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

//@ExtendWith(MockWebServerExtension::class)
class KotlinSerializationConverterFactoryStringTest(val server: MockWebServer) {

  private lateinit var service: Service

  interface Service {
    @GET("/")
    fun deserialize(): Call<User>

    @POST("/")
    fun serialize(@Body user: User): Call<Void?>
  }

  @Serializable
  data class User(val name: String)

  @BeforeEach
  fun setUp() {
    val contentType = "application/json; charset=utf-8".toMediaType()
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addConverterFactory(Json.asConverterFactory(contentType))
      .build()
    service = retrofit.create(Service::class.java)
  }

  @Test
  fun deserialize() {
    server.enqueue(MockResponse().setBody("""{"name":"Bob"}"""))
    val user = service.deserialize().execute().body()!!
    assertEquals(User("Bob"), user)
  }

  @Test
  fun serialize() {
    server.enqueue(MockResponse())
    service.serialize(User("Bob")).execute()
    val request = server.takeRequest()
    assertEquals("""{"name":"Bob"}""", request.body.readUtf8())
    assertEquals("application/json; charset=utf-8", request.headers["Content-Type"])
  }
}
