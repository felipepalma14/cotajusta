package com.felipepalma14.cotajusta.data.remote.api

import com.felipepalma14.cotajusta.data.model.FiiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

interface FiiApi {
    @GET("fii")
    fun getFiis(): Flow<FiiResponse>
}
