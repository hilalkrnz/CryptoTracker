package com.hilalkara.cryptotracker.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("x-cg-demo-api-key", "\tCG-uS6b739vYtrEAqip8BSs4V9B")
            .build()

        return chain.proceed(request)
    }
}