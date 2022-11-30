package com.example.notaaplicacion.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notaaplicacion.Model.Tareas

@Dao
interface TareaDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insert(tarea : Tareas)

    @Delete
    suspend fun delete(tarea : Tareas)

    @Query("SELECT * FROM tareas order by id ASC")
    fun getAllTareas() : LiveData<List<Tareas>>

    @Query("UPDATE tareas set nombre = :nombre, descripcion = :descripcion, fecha = :fecha, recordatorio = :recordatorio WHERE id = :id")
    suspend fun update(id : Int?, nombre : String?, descripcion : String?, fecha : String?, recordatorio : String?)
}