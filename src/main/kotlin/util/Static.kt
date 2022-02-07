package util

import kotlinx.serialization.json.Json

val jsonConfig = Json {
    isLenient = true
    prettyPrint = true
}

val deviceProperties = arrayOf(
    "x86.properties",
    "armeabi-v7a.properties",
    "arm64_v8a.properties",
)

val supportedArchitectures = arrayOf(
    "x86",
    "armeabi-v7a",
    "arm64_v8a"
)