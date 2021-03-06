package base.utils

import okhttp3.Headers

object GithubApiRequester {

    private lateinit var githubAccessToken: String

    fun init(githubAccessToken: String) {
        this.githubAccessToken = githubAccessToken
    }

    suspend inline fun <reified T> get(url: String): T {
        return ApiRequester.request("GET", url, createHeaders())
    }

    fun createHeaders(): Headers {
        return Headers.of(
            mapOf(
                "Accept" to "application/vnd.github.v3.raw",
                "Authorization" to "token $githubAccessToken"
            )
        )
    }
}