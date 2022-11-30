package com.example.notaaplicacion

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notaaplicacion.Adapter.NotasAdapter
import com.example.notaaplicacion.Database.NotaDatabase
import com.example.notaaplicacion.Model.Nota
import com.example.notaaplicacion.Model.NotaViewModel
import com.example.notaaplicacion.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotasAdapter.NotasclickListener, PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NotaDatabase
    lateinit var viewModel: NotaViewModel
    lateinit var adapter: NotasAdapter
    lateinit var selectedNota : Nota

    private val updateNota = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

        if(result.resultCode == Activity.RESULT_OK){
            val nota = result.data?.getSerializableExtra("nota") as? Nota
            if(nota != null){
                viewModel.updateNota(nota)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotaViewModel::class.java)

        viewModel.allnotas.observe(this) { list ->
            list?.let {
                adapter.updateList(list)
            }
        }

        database = NotaDatabase.getDatabase(this)

    }

    private fun initUI() {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = NotasAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == Activity.RESULT_OK){

                val nota = result.data?.getSerializableExtra("nota") as? Nota
                if(nota != null){
                    viewModel.insertNota(nota)
                }
            }

        }

        binding.fbadd.setOnClickListener {

            val intent = Intent(this, agregarNota::class.java)
            getContent.launch(intent)

        }

        binding.buscador.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText != null){
                    adapter.filtrarLista(newText)
                }

                return true
            }

        })

    }

    override fun onItemclic(nota: Nota) {
        val intent = Intent(this@MainActivity, agregarNota::class.java)
        intent.putExtra("Nota_Actual", nota)
        updateNota.launch(intent)
    }

    override fun onItemClicked(nota: Nota, cardView: CardView) {
        selectedNota = nota
        popupDisplay(cardView)
    }

    private fun popupDisplay(cardView: CardView) {
        val popup = PopupMenu(this, cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.principal)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.borrarNota){
            viewModel.deleteNota(selectedNota)
            return true
        }
        return false
    }
}