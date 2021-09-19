package com.rago.hnmobiletest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rago.hnmobiletest.data.model.ExcludeArticle
import kotlinx.coroutines.flow.Flow


// DAO para las funciones de consulta y insercion de datos a la base
@Dao
interface ExcludeArticleDao {

    @Query("SELECT * FROM EXCLUDE_ARTICLE")
    fun getAll(): Flow<List<ExcludeArticle>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg excludeArticle: ExcludeArticle)
}