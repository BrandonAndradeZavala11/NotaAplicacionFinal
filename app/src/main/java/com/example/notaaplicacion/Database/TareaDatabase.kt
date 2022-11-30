package com.example.notaaplicacion.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notaaplicacion.Model.Tareas
import com.example.notaaplicacion.Utilities.DATABASE_NAME

@Database(entities = arrayOf(Tareas::class), version = 1, exportSchema = false)
abstract class TareaDatabase : RoomDatabase() {
    abstract fun getTareaDao() : TareaDao

    companion object{
        @Volatile
        private var INSTANCE : TareaDatabase? = null

        fun getDatabase(context : Context) : TareaDatabase{

            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TareaDatabase::class.java,
                    DATABASE_NAME
                ).build()

                INSTANCE = instance

                instance
            }
        }
    }
}