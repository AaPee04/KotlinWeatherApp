package com.example.kotlinweatherapp.data.local.dao

import androidx.room.*
import com.example.kotlinweatherapp.data.local.entity.SearchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    @Query("SELECT * FROM search_history ORDER BY searchedAt DESC")
    fun getSearchHistory(): Flow<List<SearchHistoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(history: SearchHistoryEntity)

    @Query("DELETE FROM search_history")
    suspend fun clearHistory()
}