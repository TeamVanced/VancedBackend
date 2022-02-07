package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApkLink(
    @SerialName("url")
    val url: String,

    @SerialName("file_name")
    val fileName: String,

    @SerialName("hash")
    val hashSum: String? = null,
)
