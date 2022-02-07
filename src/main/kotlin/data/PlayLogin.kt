package data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayLogin(
    @SerialName("email") val email: String,
    @SerialName("auth") val token: String,
)
