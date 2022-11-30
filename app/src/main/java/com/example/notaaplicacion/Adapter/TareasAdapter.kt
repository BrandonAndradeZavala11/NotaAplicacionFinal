package com.example.notaaplicacion.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.notaaplicacion.Model.TareaViewModel
import com.example.notaaplicacion.Model.Tareas
import com.example.notaaplicacion.R

class TareasAdapter(private val context: Context, val listener: TareasclickListener) :
    RecyclerView.Adapter<TareasAdapter.TareaViewHolder>() {

    private val TareasList = ArrayList<Tareas>()
    private val fullList = ArrayList<Tareas>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        return TareaViewHolder(
            LayoutInflater.from(context).inflate(R.layout.listanotas, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tareaactual = TareasList[position]
        holder.nombreT.text = tareaactual.nombre
        holder.nombreT.isSelected = true
        holder.descripcionT.text = tareaactual.descripcion
        holder.fechaT.text = tareaactual.fecha
        holder.fechaT.isSelected = true



        holder.tareas_layoutT.setOnClickListener {
            listener.onItemclic(TareasList[holder.adapterPosition])
        }

        holder.tareas_layoutT.setOnLongClickListener {
            listener.onItemClicked(TareasList[holder.adapterPosition],holder.tareas_layoutT)
            true
        }

    }

    override fun getItemCount(): Int {
        return TareasList.size
    }

    fun updateList(newList : List<Tareas>){

        fullList.clear()
        fullList.addAll(newList)

        TareasList.clear()
        TareasList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filtrarLista(search : String){
        TareasList.clear()
        for(item in fullList){
            if(item.nombre?.lowercase()?.contains(search.lowercase()) == true ||
                item.descripcion?.lowercase()?.contains(search.lowercase()) == true){

                TareasList.add(item)

            }
        }

        notifyDataSetChanged()

    }


    inner class TareaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val tareas_layoutT = itemView.findViewById<CardView>(R.id.card_layout)
        val nombreT = itemView.findViewById<TextView>(R.id.txtnombre)
        val descripcionT = itemView.findViewById<TextView>(R.id.txtdescripcion)
        val fechaT = itemView.findViewById<TextView>(R.id.txtFecha)
    }

    interface TareasclickListener{
        fun onItemclic(tarea: Tareas)
        fun onItemClicked(tarea: Tareas, cardView: CardView)
    }

}