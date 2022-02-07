package util

import data.Version
import db.Redis
import generateAppLinks
import httpClient
import io.ktor.client.request.*
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
tailrec suspend fun ensureLinksUpToDate() {
    generateAppLinks()
    delay(23.hours)
    ensureLinksUpToDate()
}

@OptIn(ExperimentalTime::class)
tailrec suspend fun ensureLinksNotDown() {
    delay(5.minutes)
    Redis.getAllStockYoutubeUrls(0).forEach {
        httpClient.head(it) {

        }
    }
    generateAppLinks()
    ensureLinksNotDown()
}

@OptIn(ExperimentalTime::class)
tailrec suspend fun scanVancedYoutubeVersions() {
    val versions = buildList {
        File("./vanced").listFiles()?.forEach {
            val versionCode = File(it.absolutePath + "/VERSIONCODE").readText().toInt()
            add(
                Version(
                    versionCode = versionCode,
                    versionName = it.name.removePrefix("v"),
                )
            )
        }
    }
    delay(5.minutes)
    scanVancedYoutubeVersions()
}

@OptIn(ExperimentalTime::class)
tailrec suspend fun scanVancedMusicVersions() {
    val versions = buildList {
        File("./music").listFiles()?.forEach {
            val versionCode = File(it.absolutePath + "/VERSIONCODE").readText().toInt()
            add(
                Version(
                    versionCode = versionCode,
                    versionName = it.name.removePrefix("v"),
                )
            )
        }
    }
    delay(5.minutes)
    scanVancedMusicVersions()
}

@OptIn(ExperimentalTime::class)
tailrec suspend fun ensureLatestDataUpToDate() {
    delay(5.minutes)
    ensureLatestDataUpToDate()
}


