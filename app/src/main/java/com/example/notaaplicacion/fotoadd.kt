package com.example.notaaplicacion

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class fotoadd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fotoadd)
        val botoni = findViewById<Button>(R.id.btnAgregar)
        val boton = findViewById<ImageView>(R.id.imgatras)


        boton.setOnClickListener {
            val iniciar = Intent(this, agregarNota::class.java)
            startActivity(iniciar)
        }

        botoni.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageview = findViewById<ImageView>(R.id.imgFoto)
            imageview.setImageBitmap(imageBitmap)
        }
    }
}