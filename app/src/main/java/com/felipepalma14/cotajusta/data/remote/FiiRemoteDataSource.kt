package com.felipepalma14.cotajusta.data.remote

import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import com.felipepalma14.cotajusta.data.remote.api.FiiApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class FiiRemoteDataSource @Inject constructor(
    private val api: FiiApi
){
    fun fetchFii(): Flow<Result<FiiResponse>> = flow {
        try {
            val response = api.getFiis()
            emit(Result.success(response.single()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}