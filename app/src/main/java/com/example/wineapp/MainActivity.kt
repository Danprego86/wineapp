package com.example.wineapp

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wineapp.Constants.BASE_URL
import com.example.wineapp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

class MainActivity : AppCompatActivity(), OnclickListener {

    private lateinit var adapter: WineListAdapter
    private lateinit var binding: ActivityMainBinding
    private lateinit var service: WineService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupRecyclerview()
        setupRetrofit()
        swipeSetupRefresh()
    }

    private fun setupAdapter() {
        adapter = WineListAdapter()
        adapter.setOnclickListener(this)
    }

    //configurando el grill de publicacion en tiempo real//
    private fun setupRecyclerview() {
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)

            adapter = this@MainActivity.adapter
        }
    }

    private fun swipeSetupRefresh() {
        binding.srWines.setOnRefreshListener {
            adapter.submitList(listOf())
            getWines()
        }
    }

    private fun setupRetrofit() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        service = retrofit.create(WineService::class.java)
    }


    private fun getWines() {// implementar corrutinas
        lifecycleScope.launch(Dispatchers.IO) {

            try {
                // val wines = getLocalWines()
                //val serverOk = Random.nextBoolean()
                val wines =  service.getRedWines()//if (serverOk) else listOf()

                //Se cambia la ubicacion de muestra ya que el main si esta optimizado en cambios al usuario
                withContext(Dispatchers.Main) {
//                    if (wines.isEmpty()) {
////                        showRecyclerView(true)
////                        showDataView(false)
                        adapter.submitList(wines)
//                    } else {
////                        showRecyclerView(false)
////                        showDataView(true)
//                    }
                }
            } catch (e: Exception) {
                Log.d("TAG-1", e.toString())
            showMessage(R.string.common_request_fail)
            } finally {
                showProgress(false)
            }
        }
    }
    private fun showMessage(msgRes: Int){
        Snackbar.make(binding.root, msgRes, Snackbar.LENGTH_LONG).show()
    }
    private fun showRecyclerView(isVisible: Boolean) {
        binding.recyclerView.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
    private fun showDataView(isVisible: Boolean) {
        binding.tvNoData.visibility = if (isVisible) View.VISIBLE else View.GONE

    }
    private fun showProgress(isVisible: Boolean) {
        binding.srWines.isRefreshing = isVisible
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
        showProgress(true)
        getWines()
    }

    //llamadas a bases de datos o dialogos
    override fun onLongClickListener(wine: Wine) {
        val options = resources.getStringArray(R.array.array_dialog_add_options)

        MaterialAlertDialogBuilder(this)
            .setTitle(R.string.dialog_add_fav_title)
            .setItems(options){_,index ->

                when(index){
                    0->{
                        addToFavorite(wine)
                    }
                }
            }
            .show()
    }

    private fun addToFavorite(wine: Wine) {
        lifecycleScope.launch(Dispatchers.IO) {

            val result = WineApplication.database.wineDao().addWine(wine)

            if (result != -1L){
                showMessage(R.string.room_save_success)
            }else{
                showMessage(R.string.room_save_fail)
            }
        }
    }
}