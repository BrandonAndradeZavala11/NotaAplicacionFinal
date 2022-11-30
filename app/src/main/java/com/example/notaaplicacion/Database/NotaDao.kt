package com.example.notaaplicacion.Database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notaaplicacion.Model.Nota

@Dao
interface NotaDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    suspend fun insert(nota : Nota)

    @Delete
    suspend fun delete(nota: Nota)

    @Query("SELECT * FROM notas order by id ASC")
    fun getAllNotas() : LiveData<List<Nota>>

    @Query("UPDATE notas set nombre = :nombre, descripcion = :descripcion, tipo = :tipo WHERE id = :id")
    suspend fun update(id : Int?, nombre : String?, descripcion : String?, tipo : String?)
}