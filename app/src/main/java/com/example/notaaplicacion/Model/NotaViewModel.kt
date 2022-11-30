package com.example.notaaplicacion.Model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notaaplicacion.Database.NotaDatabase
import com.example.notaaplicacion.Database.NotasRepositorio
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotaViewModel(application: Application) : AndroidViewModel(application) {
    private val repositorio : NotasRepositorio

    val allnotas : LiveData<List<Nota>>

    init {
        val dao = NotaDatabase.getDatabase(application).getNotaDao()
        repositorio = NotasRepositorio(dao)
        allnotas = repositorio.allNotas
    }

    fun deleteNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.delete(nota)
    }

    fun insertNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.insert(nota)
    }

    fun updateNota(nota: Nota) = viewModelScope.launch(Dispatchers.IO) {
        repositorio.update(nota)
    }


}