package base.utils

import okhttp3.Headers
import okhttp3.Response

object JiraAuthenticationCookieGetter {

    data class JiraAuthData(
        val jiraDomain: String,
        val loginPostBody: String
    )

    private lateinit var jiraAuthData: JiraAuthData

    fun init(jiraAuthData: JiraAuthData) {
        this.jiraAuthData = jiraAuthData
    }

    suspend fun get(): String {
        return getCookie(getLoginResponseHeaders())
    }

    private suspend fun getLoginResponseHeaders(): Headers {
        return ApiRequester.request<Response>("POST", getLoginUrl(), jsonBody = jiraAuthData.loginPostBody)
            .let {
                it.close()
                it.headers()
            }
    }

    private fun getLoginUrl(): String {
        return "${jiraAuthData.jiraDomain}/rest/auth/1/session"
    }

    private fun getCookie(headers: Headers): String {
        return headers.toMultimap().entries
            .first { it.key.toLowerCase() == "set-cookie" }.value
            .joinToString("; ")
    }
}