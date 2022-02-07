package db

import com.aurora.gplayapi.data.models.File
import io.lettuce.core.ExperimentalLettuceCoroutinesApi
import io.lettuce.core.RedisClient
import io.lettuce.core.api.coroutines
import kotlinx.coroutines.flow.collect
import util.supportedArchitectures

@OptIn(ExperimentalLettuceCoroutinesApi::class)
object Redis {
    private val redis = RedisClient.create(System.getenv("REDIS_URL"))
    private val redisConnection = redis.connect().coroutines()

    object MapKeyProvider {
        fun stockYoutubeBase(versionCode: Int) = "${versionCode}_base"
        fun stockYoutubeDpi(versionCode: Int) = "${versionCode}_split_dpi"
        fun stockYoutubeArch(versionCode: Int, arch: String) = "${versionCode}_split_arch_$arch"
        fun stockYoutubeLanguage(versionCode: Int, language: String) = "${versionCode}_split_lang_$language"

        fun stockYoutubeMusicBase(versionCode: Int, arch: String) = "${versionCode}_base_$arch"
    }

    suspend fun setStockYoutubeUrls(
        versionCode: Int,
        apks: List<File>
    ) {
        redisConnection.hmset(
            key = DbApp.YOUTUBE.key,
            map = buildMap {
                for (apk in apks) {
                    // Base APK
                    if (apk.name.contains(DbApp.YOUTUBE.packageName)) {
                        put(MapKeyProvider.stockYoutubeBase(versionCode), apk.url)
                        continue
                    }

                    // DPI
                    if (apk.name.contains("dpi")) {
                        put(MapKeyProvider.stockYoutubeDpi(versionCode), apk.url)
                        continue
                    }

                    // If the apk is of architecture type, and we support that architecture,
                    // arch variable will be the name of the architecture, else null.
                    val arch = supportedArchitectures.find { apk.name.contains(it) }
                    if (arch != null) {
                        put(MapKeyProvider.stockYoutubeArch(versionCode, arch), apk.url)
                        continue
                    }

                    // Finally, the language split
                    val languageCode = apk.name.substringAfter(".").substringBefore(".")
                    put(MapKeyProvider.stockYoutubeLanguage(versionCode, languageCode), apk.url)
                }
            }
        )
    }

    suspend fun getStockYoutubeUrls(
        versionCode: Int,
        arch: String,
        languages: List<String>
    ) = buildList {
        val languageKeys = languages.map { language ->
            MapKeyProvider.stockYoutubeLanguage(versionCode, language)
        }.toTypedArray()
        redisConnection.hmget(
            DbApp.YOUTUBE.key,
            MapKeyProvider.stockYoutubeBase(versionCode),
            MapKeyProvider.stockYoutubeDpi(versionCode),
            MapKeyProvider.stockYoutubeArch(versionCode, arch),
            *languageKeys
        ).collect {
            //Map URL to file name based on the key
            val fileName = when (it.key) {
                MapKeyProvider.stockYoutubeBase(versionCode) -> "base.apk"
                MapKeyProvider.stockYoutubeDpi(versionCode) -> "split_config.xxxhdpi.apk"
                MapKeyProvider.stockYoutubeArch(versionCode, arch) -> "split_config.$arch.apk"
                else -> {
                    val languageCode = it.key.substringAfter("_")
                    "split_config.$languageCode.apk"
                }
            }
            add(Apk(fileName, url = it.value))
        }
    }

    suspend fun getAllStockYoutubeUrls(
        versionCode: Int
    ): List<String> {
        val urls = mutableListOf<String>()
        redisConnection.hgetall(DbApp.YOUTUBE.key).collect {
            urls.add(it.value)
        }
        return urls
    }

    suspend fun getStockYoutubeMusicUrls(
        versionCode: Int,
        arch: String
    ) = buildList {
        redisConnection.hmget(
            DbApp.YOUTUBE_MUSIC.key,
            MapKeyProvider.stockYoutubeMusicBase(versionCode, arch)
        ).collect {
            add(Apk("base_$arch.apk", it.value))
        }
    }

    suspend fun setStockYoutubeMusicUrls(
        versionCode: Int,
        apks: List<File>
    ) {
        redisConnection.hmset(
            key = DbApp.YOUTUBE_MUSIC.key,
            map = buildMap {
                for (apk in apks) {
                    val arch = supportedArchitectures.find { apk.name.contains(it) }
                    if (arch != null) {
                        put(MapKeyProvider.stockYoutubeMusicBase(versionCode, arch), apk.url)
                        continue
                    }
                }
            }
        )
    }
}