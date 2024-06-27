package com.example.wineapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wineapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: WineListAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: WineService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdpater()
        setupRecyclerview()
        setupRetrofit()
        swipSetuprefresh()
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

    private fun swipSetuprefresh() {
        binding.srWines.setOnRefreshListener {
            adapter.submitList(listOf())
            getWines()
        }
    }

    private fun setupRetrofit() {

        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(WineService::class.java)
    }


    private fun getWines() {// implementar corrutinas
        lifecycleScope.launch(Dispatchers.IO) {

            try {// val wines = getLocalWines()
                val serverOk = Random.nextBoolean()
                val wines = if (serverOk) service.getRedWines() else listOf()

                //Se cambia la ubicacion de muestra ya que el main si esta optimizado en cambios al usuario
                withContext(Dispatchers.Main) {
                    if (wines.isNullOrEmpty()) {
                        adapter.submitList(wines)
                    } else {
                        Snackbar.make(binding.root, "=(", Snackbar.LENGTH_LONG).show()
                    }
                }
            } finally {
                binding.srWines.isRefreshing = false
            }
        }
    }

//    private fun getLocalWines() =
//        listOf(
//            Wine(
//                "Cursos android",
//                "Daniel",
//                Rating("4.8", "236 ratings"),
//                "Mexico",
//                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
//                1
//            ),
//
//            Wine(
//                "Cursos android",
//                "Daniel",
//                Rating("4.8", "236 ratings"),
//                "Mexico",
//                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
//                2
//            ),
//
//            Wine(
//                "Cursos android",
//                "Daniel",
//                Rating("4.8", "236 ratings"),
//                "Mexico",
//                "https://lavaquita.co/cdn/shop/products/supermercados_la_vaquita_supervaquita_vino_viejo_vinedo_750ml_tinto_bebidas_alcoholicas_1024x1024.jpg?v=1621442593",
//                3
//            )
//        )

    // funcion para cada vez que se reanuda la app o se pausa muestra de nuevo las imagenes
    override fun onResume() {
        super.onResume()
        getWines()
    }
}