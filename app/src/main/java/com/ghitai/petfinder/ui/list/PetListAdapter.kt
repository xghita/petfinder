package com.ghitai.petfinder.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ghitai.petfinder.data.pet.Pet
import com.ghitai.petfinder.databinding.ItemPetDataBinding
import com.ghitai.petfinder.core.AppGlide

class PetAdapter(
    private val onPetSelected: (id: Int, name: String, cardView: CardView) -> Unit
) : RecyclerView.Adapter<PetAdapter.ItemVH>() {

    private val items = mutableListOf<Pet.ListItem>()

    init {
        setHasStableIds(true)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemId(position: Int): Long = items[position].hashCode().toLong()

    fun updateList(items: List<Pet.ListItem>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val inflater = LayoutInflater.from(parent.context)
        return PetVH(ItemPetDataBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.bind(items[position])
    }

    inner class PetVH(private val binding: ItemPetDataBinding) : ItemVH(binding.root) {

        init {
            binding.apply {
                cardView.setOnClickListener {
                    val item = items[absoluteAdapterPosition]
                    onPetSelected(item.id, item.name, cardView)
                }
            }
        }

        override fun bind(item: Pet.ListItem) {
            binding.apply {
                data = item
                AppGlide.load(item.photoUrl, avatarIv)
            }
        }
    }

    open inner class ItemVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        open fun bind(item: Pet.ListItem) {}
    }
}