package util

import com.aurora.gplayapi.data.models.AuthData
import com.aurora.gplayapi.data.models.File
import com.aurora.gplayapi.helpers.AuthHelper
import com.aurora.gplayapi.helpers.PurchaseHelper
import data.PlayLogin

fun getPlayAuthData(auth: PlayLogin, deviceName: String): AuthData {
    return AuthHelper.buildInsecure(
        email = auth.email,
        authToken = auth.token,
        deviceName = deviceName
    )
}

fun getAppApks(
    authData: AuthData,
    packageName: String,
    versionCode: Int,
): List<File> {
    return PurchaseHelper(authData)
        .purchase(packageName, versionCode, 1)
}
