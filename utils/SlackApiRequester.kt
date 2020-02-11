package base.utils

import okhttp3.Headers

object SlackApiRequester {

    private lateinit var botAccessToken: String

    fun init(botAccessToken: String) {
        this.botAccessToken = botAccessToken
    }

    suspend inline fun <reified T> get(url: String, accessToken: String? = null): T {
        return ApiRequester.request("GET", url, createHeaders(accessToken))
    }

    fun createHeaders(accessToken: String? = null): Headers {
        return Headers.of(mapOf("Authorization" to "Bearer ${accessToken ?: botAccessToken}"))
    }

    suspend inline fun <reified T> post(url: String, jsonBody: String): T {
        return ApiRequester.request("POST", url, createHeaders(), jsonBody)
    }
}