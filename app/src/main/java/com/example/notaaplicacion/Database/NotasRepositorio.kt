package com.example.notaaplicacion.Database

import androidx.lifecycle.LiveData
import com.example.notaaplicacion.Model.Nota

class NotasRepositorio(private val notaDao: NotaDao){

    val allNotas : LiveData<List<Nota>> = notaDao.getAllNotas()

    suspend fun insert(nota: Nota){
        notaDao.insert(nota)
    }

    suspend fun delete(nota: Nota){
        notaDao.delete(nota)
    }

    suspend fun update(nota: Nota){
        notaDao.update(nota.id, nota.nombre, nota.descripcion, nota.tipo, nota.uriF, nota.uriV, nota.uriA)
    }

}