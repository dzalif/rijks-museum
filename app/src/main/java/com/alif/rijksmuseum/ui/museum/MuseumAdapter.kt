package com.alif.rijksmuseum.ui.museum

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alif.rijksmuseum.databinding.ItemMuseumBinding
import com.alif.rijksmuseum.model.data.ArtObject

class MuseumAdapter (private val listener: OnMuseumPressedListener) : ListAdapter<ArtObject, MuseumAdapter.ViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemMuseumBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position), listener, holder.adapterPosition)

    class ViewHolder(private val binding: ItemMuseumBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ArtObject, listener: OnMuseumPressedListener, position: Int) {
            binding.data = model
            binding.root.setOnClickListener {
                listener.onMuseumPressed(model, position)
            }
            binding.executePendingBindings()
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<ArtObject>(){
            override fun areItemsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: ArtObject, newItem: ArtObject): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnMuseumPressedListener {
        fun onMuseumPressed(art: ArtObject, position: Int)
    }

}