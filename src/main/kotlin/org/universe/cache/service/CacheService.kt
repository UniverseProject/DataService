package org.universe.cache.service

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.builtins.serializer
import org.universe.cache.CacheClient

abstract class CacheService(val client: CacheClient, val prefixKey: String) {

    /**
     * Create the key from a [String] value to identify data in cache.
     * @param value Value using to create key.
     * @return [ByteArray] corresponding to the key using the [prefixKey] and [value].
     */
    protected fun getKey(value: String): ByteArray = encodeToByteArray(String.serializer(), "$prefixKey$value")

    /**
     * Transform an instance to a [ByteArray] by encoding data using [binaryFormat][CacheClient.binaryFormat].
     * @param value Value that will be serialized.
     * @return Result of the serialization of [value].
     */
    protected fun <T> encodeToByteArray(serializer: SerializationStrategy<T>, value: T): ByteArray =
        client.binaryFormat.encodeToByteArray(serializer, value)

    /***
     * Transform a [ByteArray] to a value by decoding data using [binaryFormat][CacheClient.binaryFormat].
     * @param valueSerial Serialization of the value.
     * @return The value from the [valueSerial] decoded.
     */
    protected fun <T> decodeFromByteArray(deserializer: DeserializationStrategy<T>, valueSerial: ByteArray): T =
        client.binaryFormat.decodeFromByteArray(deserializer, valueSerial)
}