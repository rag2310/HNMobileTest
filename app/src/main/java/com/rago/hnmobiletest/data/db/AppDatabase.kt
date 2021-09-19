package com.rago.hnmobiletest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rago.hnmobiletest.data.db.dao.ExcludeArticleDao
import com.rago.hnmobiletest.data.model.ExcludeArticle


//Configuracion de las entidades y DAO para la base de datos
@Database(version = 1, entities = [ExcludeArticle::class])
abstract class AppDatabase : RoomDatabase() {
    abstract fun excludeArticleDao(): ExcludeArticleDao
}