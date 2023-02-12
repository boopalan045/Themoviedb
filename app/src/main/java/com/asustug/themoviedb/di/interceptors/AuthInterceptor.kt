package com.asustug.themoviedb.di.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
        request.addHeader("Content-Type", "application/json;charset=utf-8")
        val response = chain.proceed(request.build())
        return response
    }
}