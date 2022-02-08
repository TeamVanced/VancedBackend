package plugins

import data.Latest
import getLatestMicrogRelease
import getLatestManagerRelease
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import util.API_BASE
import kotlin.experimental.ExperimentalTypeInference

fun Application.configureRouting() {
    routing {
        apiV2()
        get("/") {
            call.respondText("Hello World!")
        }
    }
}

private fun Route.apiV2() {
        route("/v2") {
        route("/download") {
            route("/vanced") {
                get("/youtube") {
                    val variant = call.request.queryParameters["variant"] ?: return@get
                    val version = call.request.queryParameters["version"] ?: return@get

                    val arch = call.request.queryParameters["arch"] ?: return@get
                    val theme = call.request.queryParameters["theme"] ?: return@get
                    val languages = call.request.queryParameters["languages"]?.split(",") ?: return@get

                    val baseUrl = "$API_BASE/main/v$version/$variant"

                    call.respondApks {
                        add("$baseUrl/Arch/split_config.$arch.bsdiff")
                        add("$baseUrl/Theme/$theme.bsdiff")
                        languages.forEach { languageCode ->
                            add("$baseUrl/Language/split_config.$languageCode.bsdiff")
                        }
                    }
                }
                get("/youtube-music") {
                    val variant = call.request.queryParameters["variant"] ?: return@get
                    val version = call.request.queryParameters["version"] ?: return@get

                    call.respondApks {
                        add("https://vancedapp.com/api/v1/music/v$version/$variant.apk")
                    }
                }
                get("/microg") {
                    call.respondApks {
                        add("https://github.com/YTVanced/VancedMicroG/releases/latest/download/microg.apk")
                    }
                }
            }
        }
        route("/latest") {
            route("/vanced") {
                get("youtube") {
                    //call.respondLatest() from db
                }
                get("youtube-music") {

                }
                get("/microg") {
                    val microgRelease = getLatestMicrogRelease()
                    val versionName = microgRelease.tagName.split("-")[0].substringAfter("v")
                    val versionCode = microgRelease.tagName.split("-")[1].toLong()
                    val changelog = microgRelease.changelog
                    call.respondLatest(versionName, versionCode, changelog)
                }
                get("/manager") {
                    val managerRelease = getLatestManagerRelease()
                    val versionName = managerRelease.tagName.split("-")[0].substringAfter("v")
                    val versionCode = managerRelease.tagName.split("-")[1].toLong()
                    val changelog = managerRelease.changelog
                    call.respondLatest(versionName, versionCode, changelog)
                }
            }
        }
        get("/") {
            call.respondText("Latest data goes here")
        }
    }
}

private suspend fun ApplicationCall.respondLatest(
    versionName: String,
    versionCode: Long,
    changelog: String
) = respond(HttpStatusCode.OK, Latest(versionName, versionCode, changelog))

@OptIn(ExperimentalTypeInference::class)
private suspend inline fun ApplicationCall.respondApks(
    @BuilderInference builder: MutableList<String>.() -> Unit
) = respond(HttpStatusCode.OK, buildList(builder))