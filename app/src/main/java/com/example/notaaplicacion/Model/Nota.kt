package com.example.notaaplicacion.Model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notas")
data class Nota(
    @PrimaryKey(autoGenerate = true) val id : Int?,
    @ColumnInfo(name = "nombre") val nombre : String?,
    @ColumnInfo(name = "descripcion") val descripcion : String?,
    @ColumnInfo(name = "fecha") val fecha : String?,
    @ColumnInfo(name = "tipo") val tipo : String?,
    @ColumnInfo(name = "uriF") val uriF: String?,
    @ColumnInfo(name = "uriV") val uriV: String?,
    @ColumnInfo(name = "uriA") val uriA: String
) : Serializable
