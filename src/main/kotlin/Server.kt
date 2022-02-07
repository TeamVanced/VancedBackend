import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import plugins.configureRouting
import util.ensureLinksNotDown
import util.ensureLinksUpToDate
import util.jsonConfig

suspend fun main() {
    coroutineScope {
        launch(Dispatchers.IO) {
            ensureLinksUpToDate()
        }
        launch {
            ensureLinksNotDown()
        }
    }
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        install(ContentNegotiation) {
            json(jsonConfig)
        }
    }.start(wait = true)
}