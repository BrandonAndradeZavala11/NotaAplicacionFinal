package com.example.notaaplicacion

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.navigation.fragment.findNavController

class FragmentTareas : Fragment(R.layout.fragment_tareas) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val botonatras = requireView().findViewById<ImageView>(R.id.imgatras)
        val botonhecho = requireView().findViewById<ImageView>(R.id.imghecho)
        val botonfoto = requireView().findViewById<Button>(R.id.button2)
        val botonvideo = requireView().findViewById<Button>(R.id.btnvideo)
        val botonaudio = requireView().findViewById<Button>(R.id.btnAudio)

        botonatras.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentTareas_to_mainActivity)
        }
    }
}