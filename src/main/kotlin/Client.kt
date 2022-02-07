import data.GithubRelease
import data.PlayLogin
import db.DbApp
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import util.*

val httpClient = HttpClient(CIO) {
    install(JsonFeature) {
        serializer = KotlinxSerializer(jsonConfig)
    }
}

suspend fun generatePlayLogin(): PlayLogin =
    httpClient.get("http://goolag.store:1337/api/auth")

suspend fun getLatestManagerRelease() =
    httpClient.get<GithubRelease>("$API_GITHUB_BASE/repos/YTVanced/VancedManager/releases/latest")

suspend fun getLatestMicrogRelease() =
    httpClient.get<GithubRelease>("$API_GITHUB_BASE/repos/YTVanced/VancedMicroG/releases/latest")

suspend fun generateAppLinks() {
    val playLogin = generatePlayLogin()
    deviceProperties.forEach { property ->
        val playAuthData = getPlayAuthData(playLogin, property)
        val stockYtApks = getAppApks(playAuthData, DbApp.YOUTUBE.packageName, 0)
        val stockYtMusicApks = getAppApks(playAuthData, DbApp.YOUTUBE_MUSIC.packageName, 0)
    }
}