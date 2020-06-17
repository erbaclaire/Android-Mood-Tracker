package com.chelseatroy.canary.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MockyAPIImplementation {
    private val service: LoginService

    companion object {
        const val BASE_URL = "http://www.mocky.io/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(LoginService::class.java)
    }

    suspend fun authenticate(): CanarySession  {
        return service.authenticate()
    }
}