package com.ergea.ridsnewsapp.presentation.source.adapter;

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergea.ridsnewsapp.databinding.ItemSourceBinding
import com.ergea.ridsnewsapp.model.Source


class SourceAdapter(private val itemClick: (Source) -> Unit) :
    RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {


    private var items: MutableList<Source> = mutableListOf()

    fun setItems(items: List<Source>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SourceViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class SourceViewHolder(
        private val binding: ItemSourceBinding,
        val itemClick: (Source) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Source) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    tvTitle.text = name
                    tvDesc.text = description
                    tvCategory.text = category
                }
            }

        }
    }

}