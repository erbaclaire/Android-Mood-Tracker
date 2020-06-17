package com.chelseatroy.canary.api

import retrofit2.http.GET

interface LoginService {
    @GET("/v2/5ed1d35132000070005ca001")
    suspend fun authenticate(): CanarySession
}