package com.example.notaaplicacion.Database

import androidx.lifecycle.LiveData
import com.example.notaaplicacion.Model.Tareas

class TareasRepositorio(private val tareaDao: TareaDao) {
    val allTareas : LiveData<List<Tareas>> = tareaDao.getAllTareas()

    suspend fun insert(tarea: Tareas){
        tareaDao.insert(tarea)
    }

    suspend fun delete(tarea: Tareas){
        tareaDao.delete(tarea)
    }

    suspend fun update(tarea: Tareas){
        tareaDao.update(tarea.id, tarea.nombre, tarea.descripcion, tarea.fecha, tarea.recordatorio)
    }


}