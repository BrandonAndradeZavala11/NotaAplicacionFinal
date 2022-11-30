package com.example.notaaplicacion.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notaaplicacion.Database.TareaDatabase
import com.example.notaaplicacion.Database.TareasRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {
    private val repositorio : TareasRepositorio

    val alltareas : LiveData<List<Tareas>>

    init {
        val dao = TareaDatabase.getDatabase(application).getTareaDao()
        repositorio = TareasRepositorio(dao)
        alltareas = repositorio.allTareas
    }

    fun deleteTarea(tarea : Tareas) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.delete(tarea)
    }

    fun insertTarea(tarea : Tareas) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.insert(tarea)
    }

    fun updateTarea(tarea : Tareas) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.update(tarea)
    }


}