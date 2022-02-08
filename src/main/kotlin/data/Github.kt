package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubRelease(
    @SerialName("tag_name")
    val tagName: String,

    @SerialName("assets")
    val assets: List<GithubAsset>,

    @SerialName("body")
    val changelog: String,
)

@Serializable
data class GithubAsset(
    @SerialName("browser_download_url")
    val browserDownloadUrl: String,
)
