package com.example.wineapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.wineapp.databinding.ItemWineBinding

// se ingrese el tipo de dato Wine
// Luego se utiliza la class Recycler view
// dentro de los parentesis se ubica los parametros para diferenciar de un objeto a otro
// para diferencia de un objeto a otro le pasamos el winediff el reciclerview
class WineListAdapter : ListAdapter<Wine, RecyclerView.ViewHolder>(WineDiff()) {

    private lateinit var context: Context
    private lateinit var listener: OnclickListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        //Inflar la vista
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_wine, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val wine = getItem(position)
        (holder as ViewHolder).run {

            setListener(wine)
            with(bindig) {
                tvWinery.text = wine.winery
                tvWine.text = wine.wine
                rating.rating = wine.rating.average.toFloat()
                tvLocation.text = wine.location

                Glide.with(context)
                    .load(wine.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .centerCrop()
                    .into(imgWine)
            }
        }
    }

    fun setOnclickListener(listener: OnclickListener) {

        this.listener = listener

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bindig = ItemWineBinding.bind(view)

        fun setListener(wine: Wine){
            bindig.root.setOnLongClickListener {
                listener.onLongClickListener(wine)
                true
            }
        }
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