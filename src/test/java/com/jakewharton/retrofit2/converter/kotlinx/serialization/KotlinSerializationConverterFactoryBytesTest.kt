package com.jakewharton.retrofit2.converter.kotlinx.serialization

import kotlinx.serialization.Serializable
import kotlinx.serialization.protobuf.ProtoBuf
import kotlinx.serialization.protobuf.ProtoNumber
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.junit5.internal.MockWebServerExtension
import okhttp3.MediaType.Companion.toMediaType
import okio.Buffer
import okio.ByteString
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val bobBytes = ByteString.of(0x0a, 0x03, 'B'.code.toByte(), 'o'.code.toByte(), 'b'.code.toByte())

//@ExtendWith(MockWebServerExtension::class)
class KotlinSerializationConverterFactoryBytesTest(val server: MockWebServer) {

  private lateinit var service: Service

  interface Service {
    @GET("/")
    fun deserialize(): Call<User>

    @POST("/")
    fun serialize(@Body user: User): Call<Void?>
  }

  @Serializable
  data class User(@ProtoNumber(1) val name: String)

  @BeforeEach
  fun setUp() {
    val contentType = "application/x-protobuf".toMediaType()
    val retrofit = Retrofit.Builder()
      .baseUrl(server.url("/"))
      .addConverterFactory(ProtoBuf.asConverterFactory(contentType))
      .build()
    service = retrofit.create(Service::class.java)
  }

  @Test
  fun deserialize() {
    server.enqueue(MockResponse().setBody(Buffer().write(bobBytes)))
    val user = service.deserialize().execute().body()!!
    assertEquals(User("Bob"), user)
  }

  @Test
  fun serialize() {
    server.enqueue(MockResponse())
    service.serialize(User("Bob")).execute()
    val request = server.takeRequest()
    assertEquals(bobBytes, request.body.readByteString())
    assertEquals("application/x-protobuf", request.headers["Content-Type"])
  }
}
