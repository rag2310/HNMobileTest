package com.rago.hnmobiletest.data.repository

import androidx.annotation.WorkerThread
import com.rago.hnmobiletest.data.db.dao.ExcludeArticleDao
import com.rago.hnmobiletest.data.model.ExcludeArticle
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// Repositorio con todos funciones a realizar en base de datos usando el DAO definido
class ExcludeArticleRepository @Inject constructor(private val excludeArticleDao: ExcludeArticleDao) {

    val allExcludeArticle: Flow<List<ExcludeArticle>> = excludeArticleDao.getAll()

    @WorkerThread
    suspend fun insert(excludeArticle: ExcludeArticle) {
        excludeArticleDao.insertAll(excludeArticle)
    }
}