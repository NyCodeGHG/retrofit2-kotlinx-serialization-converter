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

import kotlinx.serialization.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.lang.reflect.Type

internal sealed class Serializer {
    abstract fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T
    abstract fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody

    protected abstract val format: SerialFormat

    fun serializer(type: Type): KSerializer<Any> = format.serializersModule.serializer(type)

    class FromString(override val format: StringFormat) : Serializer() {
        override fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
            val string = body.string()
            return format.decodeFromString(loader, string)
        }

        override fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody {
            val string = format.encodeToString(saver, value)
            return RequestBody.create(contentType, string)
        }
    }

    class FromBytes(override val format: BinaryFormat) : Serializer() {
        override fun <T> fromResponseBody(loader: DeserializationStrategy<T>, body: ResponseBody): T {
            val bytes = body.bytes()
            return format.decodeFromByteArray(loader, bytes)
        }

        override fun <T> toRequestBody(contentType: MediaType, saver: SerializationStrategy<T>, value: T): RequestBody {
            val bytes = format.encodeToByteArray(saver, value)
            return RequestBody.create(contentType, bytes)
        }
    }
}
