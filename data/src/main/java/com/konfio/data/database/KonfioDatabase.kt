package com.konfio.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.konfio.data.database.models.DogEntity

@Database(entities = [DogEntity::class], version = 1)
abstract class KonfioDatabase : RoomDatabase() {

    abstract fun getDao(): DogsDao

    companion object {
        const val DATABASE_NAME = "konfio_database"
    }
}

//app/data/domain/core(ui- view - compose - componentes base /reutilizables de ui / constantes)
//buscar la doc de google
//