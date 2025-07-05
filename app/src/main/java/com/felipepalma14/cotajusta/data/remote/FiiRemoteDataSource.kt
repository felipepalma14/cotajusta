package com.felipepalma14.cotajusta.data.remote

import com.felipepalma14.cotajusta.data.remote.api.FiiApi
import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class FiiRemoteDataSource @Inject constructor(
    private val api: FiiApi
){
    fun fetchFii(): Flow<Result<FiiResponse>> = flow {
        try {
            val response = api.getFiis()
            emit(Result.success(response))
        } catch (e: Exception) {
            if (e is IOException || e is RuntimeException) throw e
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)
}