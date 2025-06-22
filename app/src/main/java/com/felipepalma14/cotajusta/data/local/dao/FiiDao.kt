package com.felipepalma14.cotajusta.data.local.dao

import androidx.room.*
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FiiDao {
    @Query("SELECT * FROM fiis")
    fun getAllFiis(): Flow<List<FiiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fiis: List<FiiEntity>)

    @Query("UPDATE fiis SET isFavorite = :isFavorite WHERE code = :code")
    suspend fun setFavorite(code: String, isFavorite: Boolean)

    @Query("SELECT * FROM fiis WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<FiiEntity>>

    @Query("SELECT * FROM fiis WHERE code = :code LIMIT 1")
    suspend fun getFiiByCode(code: String): FiiEntity?
}
