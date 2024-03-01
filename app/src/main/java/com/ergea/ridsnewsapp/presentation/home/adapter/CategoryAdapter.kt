package com.ergea.ridsnewsapp.presentation.home.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergea.ridsnewsapp.databinding.ItemCategoryBinding


class CategoryAdapter(private val itemClick: (String) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    private var items: MutableList<String> = mutableListOf()

    fun setItems(items: List<String>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        val itemClick: (String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: String) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvCategory.text = item
                }
            }

        }
    }

}