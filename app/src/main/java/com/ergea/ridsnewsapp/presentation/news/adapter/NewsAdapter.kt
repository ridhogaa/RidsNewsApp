package com.ergea.ridsnewsapp.presentation.news.adapter;

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ergea.ridsnewsapp.common.dateFormat
import com.ergea.ridsnewsapp.common.dateToTimeFormat
import com.ergea.ridsnewsapp.common.loadImage
import com.ergea.ridsnewsapp.common.show
import com.ergea.ridsnewsapp.databinding.ItemNewsBinding
import com.ergea.ridsnewsapp.model.News


class NewsAdapter(private val itemClick: (News) -> Unit) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    private var items: MutableList<News> = mutableListOf()

    fun setItems(items: List<News>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size


    class NewsViewHolder(private val binding: ItemNewsBinding, val itemClick: (News) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: News) {
            with(item) {
                itemView.setOnClickListener { itemClick(this) }
                binding.run {
                    title.text = item.title
                    desc.text = item.description
                    source.text = item.source?.name.orEmpty()
                    if (dateFormat(item.publishedAt.orEmpty())?.isEmpty() == true){
                        time.show(false)
                        layoutDate.show(false)
                    } else {
                        time.text = " \u2022 " + dateToTimeFormat(item.publishedAt.orEmpty())
                        publishedAt.text = dateFormat(item.publishedAt.orEmpty())
                    }
                    author.text = item.author
                    img.loadImage(itemView.context, item.urlToImage, prograssLoadPhoto)
                }
            }

        }
    }

}