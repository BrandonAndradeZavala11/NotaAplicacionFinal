package com.example.notaaplicacion.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notaaplicacion.Model.Nota
import com.example.notaaplicacion.Utilities.DATABASE_NAME

@Database(entities = arrayOf(Nota::class), version = 1, exportSchema = false)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun getNotaDao() : NotaDao

    companion object{
        @Volatile
        private var INSTANCE : NotaDatabase? = null

        fun getDatabase(context : Context) : NotaDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}