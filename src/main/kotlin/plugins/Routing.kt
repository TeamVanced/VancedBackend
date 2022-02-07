package plugins

import data.Latest
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
            route("/stock") {
                get("/youtube") {
//                    val versionCode = call.request.queryParameters["versionCode"] ?: return@get
//                    val arch = call.request.queryParameters["arch"] ?: return@get
//                    val languages = call.request.queryParameters["langs"]?.split(",") ?: return@get

                }
                get("/youtube-music") {
                    call.respondText("Test")
                }
            }
            route("/vanced") {
                get("/youtube") {
                    val variant = call.request.queryParameters["variant"] ?: return@get
                    val version = call.request.queryParameters["version"] ?: return@get

                    val arch = call.request.queryParameters["arch"] ?: return@get
                    val theme = call.request.queryParameters["theme"] ?: return@get
                    val languages = call.request.queryParameters["languages"]?.split(",") ?: return@get

                    val baseUrl = "$API_BASE/main/v$version/$variant"

                    call.respondApks {
                        add("$baseUrl/Arch/split_config.$arch.apk")
                        add("$baseUrl/Theme/$theme.apk")
                        languages.forEach { languageCode ->
                            add("$baseUrl/Language/split_config.$languageCode.apk")
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

                }
                get("youtube-music") {

                }
                get("/microg") {

                }
                get("/manager") {

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