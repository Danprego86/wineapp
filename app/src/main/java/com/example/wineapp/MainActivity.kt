package com.example.wineapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wineapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WineListAdapter
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdpater()
        setupRecyclerview()
    }

    private fun setupAdpater() {
        adapter = WineListAdapter()
    }

    //configurando el grill de publicacion en tiempo real//
    private fun setupRecyclerview() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

            adapter = this@MainActivity.adapter
        }
    }

    private fun getWines() {
        val wines = getLocalWines()
        adapter.submitList(wines)
    }

    private fun getLocalWines() =
        listOf(
            Wine(
                "Cursos android",
                "Daniel",
                Rating("4.8", "236 ratings"),
                "Mexico",
                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
                1
            ),

            Wine(
                "Cursos android",
                "Daniel",
                Rating("4.8", "236 ratings"),
                "Mexico",
                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
                2
            ),

            Wine(
                "Cursos android",
                "Daniel",
                Rating("4.8", "236 ratings"),
                "Mexico",
                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
                3
            )
        )
// funcion para cada vez que se reanuda la app o se pausa muestra de nuevo las imagenes
    override fun onResume() {
        super.onResume()
        getWines()
    }
}