package com.example.notaaplicacion.Adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notaaplicacion.Model.Nota
import com.example.notaaplicacion.R
import kotlin.random.Random

class NotasAdapter(private val context : Context, val listener: NotasclickListener) :
    RecyclerView.Adapter<NotasAdapter.NotaViewHolder>() {

    private val NotasList = ArrayList<Nota>()
    private val fullList = ArrayList<Nota>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotaViewHolder {
        return NotaViewHolder(
            LayoutInflater.from(context).inflate(R.layout.listanotas, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NotaViewHolder, position: Int) {
        val notactual = NotasList[position]
        holder.nombreT.text = notactual.nombre
        holder.nombreT.isSelected = true

        holder.descripcionT.text = notactual.descripcion
        holder.tipoT.text = notactual.tipo
        holder.fechaT.text = notactual.fecha
        holder.fechaT.isSelected = true


        holder.notas_layoutT.setOnClickListener {
            listener.onItemclic(NotasList[holder.adapterPosition])
        }

        holder.notas_layoutT.setOnLongClickListener {
            listener.onItemClicked(NotasList[holder.adapterPosition],holder.notas_layoutT)
            true
        }

    }

    override fun getItemCount(): Int {
        return NotasList.size
    }

    fun updateList(newList : List<Nota>){

        fullList.clear()
        fullList.addAll(newList)

        NotasList.clear()
        NotasList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filtrarLista(search : String){
        NotasList.clear()
        for(item in fullList){
            if(item.nombre?.lowercase()?.contains(search.lowercase()) == true ||
                item.descripcion?.lowercase()?.contains(search.lowercase()) == true ||
                item.tipo?.lowercase()?.contains(search.lowercase()) == true){

                NotasList.add(item)

            }
        }

        notifyDataSetChanged()

    }


    inner class NotaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val notas_layoutT = itemView.findViewById<CardView>(R.id.card_layout)
        val nombreT = itemView.findViewById<TextView>(R.id.txtnombre)
        val descripcionT = itemView.findViewById<TextView>(R.id.txtdescripcion)
        val fechaT = itemView.findViewById<TextView>(R.id.txtFecha)
        val tipoT = itemView.findViewById<TextView>(R.id.txtTipo)

    }

    interface NotasclickListener{
        fun onItemclic(nota:Nota)
        fun onItemClicked(nota:Nota,cardView: CardView)
    }

}