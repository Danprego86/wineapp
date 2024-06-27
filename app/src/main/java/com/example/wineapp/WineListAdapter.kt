package com.example.wineapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.wineapp.databinding.ItemWineBinding

// se ingrese el tipo de dato Wine
// Luego se utiliza la class Recycler view
// dentro de los parentesis se ubica los parametros para diferenciar de un objeto a otro
// para diferencia de un objeto a otro le pasamos el winediff el reciclerview
class WineListAdapter : ListAdapter<Wine, RecyclerView.ViewHolder>(WineDiff()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Inflar la vista
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_wine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val wine = getItem(position)
        (holder as ViewHolder).run {

            with(bindig){
                tvWine.text = wine.wine
                tvWinery.text= wine.winery
                tvLocation.text= wine.location
                rating.rating= wine.rating.average.toFloat()
            }
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bindig = ItemWineBinding.bind(view)
    }

    private class WineDiff : DiffUtil.ItemCallback<Wine>() {
        override fun areItemsTheSame(oldItem: Wine, newItem: Wine): Boolean {
            //Retorne el ultimo dato con el id y lo compare con el nuevo con un true
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Wine, newItem: Wine): Boolean {
            //Retorne la validacion de todo el objeto
            return oldItem == newItem
        }
    }
}