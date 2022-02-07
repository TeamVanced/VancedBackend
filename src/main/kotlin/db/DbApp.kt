package db

enum class DbApp(val key: String, val packageName: String) {
    YOUTUBE("stock_youtube", "com.google.android.youtube"),
    YOUTUBE_MUSIC("stock_youtube_music", "com.google.android.apps.youtube.music");
}