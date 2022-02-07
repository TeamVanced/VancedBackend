package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Latest(
    @SerialName("version_name")
    val versionName: String,

    @SerialName("version_code")
    val versionCode: Long,

    @SerialName("changelog")
    val changelog: String,
)
