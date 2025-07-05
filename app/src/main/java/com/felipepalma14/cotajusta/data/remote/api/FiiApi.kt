package com.felipepalma14.cotajusta.data.remote.api

import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import retrofit2.http.GET

interface FiiApi {
    @GET("fiis")
    suspend fun getFiis(): FiiResponse
}
