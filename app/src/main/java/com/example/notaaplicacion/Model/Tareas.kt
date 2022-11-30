package com.example.notaaplicacion.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "tareas")
data class Tareas(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "nombre") val nombre : String?,
    @ColumnInfo(name = "descripcion") val descripcion : String?,
    @ColumnInfo(name = "fecha") val fecha : String?,
    @ColumnInfo(name = "recordatorio") val recordatorio : String?
) : Serializable
