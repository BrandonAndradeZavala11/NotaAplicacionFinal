package com.example.notaaplicacion

import android.app.*
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.notaaplicacion.Model.Nota
import com.example.notaaplicacion.databinding.ActivityAgregarNotaBinding
import com.example.notaaplicacion.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import java.util.logging.SimpleFormatter

class agregarNota : AppCompatActivity() {

    lateinit var recorder: MediaRecorder
    lateinit var player: MediaPlayer
    lateinit var archivo: File
    private lateinit var binding: ActivityAgregarNotaBinding
    private lateinit var nota : Nota
    private lateinit var oldNota: Nota
    var updateNot = false
    lateinit var mr : MediaRecorder
    val chanelID = "chat"
    val chanelName = "chat"


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarNotaBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var path : String = Environment.getExternalStorageDirectory().toString()+"/miadudio.3gp"
        mr = MediaRecorder()
        binding.btnAudioparar.isEnabled = false

        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE),111)

        binding.btnAudioparar.isEnabled = true

        try {
            oldNota = intent.getSerializableExtra("Nota_Actual") as Nota
            binding.Nombre.setText(oldNota.nombre)
            binding.descripcionota.setText(oldNota.descripcion)
            binding.tiponota.setText(oldNota.tipo)
            updateNot = true
        }catch(e: Exception){
            e.printStackTrace()
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val importancia = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(chanelID,chanelName,importancia)
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }

        binding.imghecho.setOnClickListener {
            val nombre = binding.Nombre.text.toString()
            val descripcion = binding.descripcionota.text.toString()
            val tipo = binding.tiponota.text.toString()

            if(nombre.isEmpty() || descripcion.isEmpty() || tipo.isEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:mm a")

                if(updateNot){
                    nota = Nota(
                        oldNota.id,nombre,descripcion,formatter.format(Date()),tipo
                    )

                }else {
                    nota = Nota(
                        null,nombre,descripcion,formatter.format(Date()),tipo
                    )
                }
                val intent = Intent()
                intent.putExtra("nota",nota)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@agregarNota, "Rellena los datos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val notificacion = NotificationCompat.Builder(this,chanelID).also { noti->
                noti.setContentTitle(binding.Nombre.text.toString())
                noti.setContentText(binding.descripcionota.text.toString())
                noti.setSmallIcon(R.drawable.imgnoti)
            }.build()
            val notificacionManager = NotificationManagerCompat.from(applicationContext)
            notificacionManager.notify(1,notificacion)

        }


        binding.imgatras.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }

        binding.btnAgregar.setOnClickListener {
            startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
        }

        binding.btnAudios.setOnClickListener {
            mr.setAudioSource(MediaRecorder.AudioSource.MIC)
            mr.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mr.setOutputFile(path)
            mr.prepare()
            mr.start()

        }

        binding.btnAudioparar.setOnClickListener {
            mr.stop()
        }

        binding.btnAudioescuchar.setOnClickListener {
            var mp = MediaPlayer()
            mp.setDataSource(path)
            mp.prepare()
            mp.start()

        }

    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            binding.btnAudioparar.isEnabled = true
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
        if(result.resultCode == Activity.RESULT_OK){
            val intent = result.data
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageview = findViewById<ImageView>(R.id.imageView)
            imageview.setImageBitmap(imageBitmap)
        }
    }

    private fun saveToGallery(){
        val content = createContent()
        val uri = save(content)
        Toast.makeText(this, getString(R.string.imagen_saved), Toast.LENGTH_LONG).show()
    }


    private fun save(content: ContentValues): Uri {
        var outputStream : OutputStream?
        var uri:Uri?
        application.contentResolver.also { resolver ->
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
            outputStream = resolver.openOutputStream(uri!!)
        }
        outputStream.use { output ->
            val imageBitmap = intent?.extras?.get("data") as Bitmap
            val imageview = findViewById<ImageView>(R.id.imageView)
            imageview.setImageBitmap(imageBitmap)
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100,output)
        }
        return uri!!
    }

    private fun createContent(): ContentValues {
        val fileName = binding.Nombre.text.toString()
        val fileType = "image.jpg"
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, fileType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.IS_PENDING,1)
        }

    }


}