package com.rago.hnmobiletest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rago.hnmobiletest.data.model.Hit
import com.rago.hnmobiletest.databinding.RowArticlesBinding
import com.rago.hnmobiletest.utils.getTimeArticles

class ArticlesListAdapter :
    ListAdapter<Hit, ArticlesListAdapter.ArticlesViewHolder>(
        ArticlesListComparator()
    ) {

    class ArticlesViewHolder(private val binding: RowArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Hit) {
            binding.hit = data

            data.createdAt?.let {
                binding.timeHit = getTimeArticles(it)
            }
            binding.cvArticle.setOnClickListener {
                val action =
                    HomeFragmentDirections.actionHomeFragmentToArticlesDetailsFragment(
                        data.story_url ?: "https://github.com/rag2310/HNMobileTest/tree/dev_rgu"
                    )
                binding.root.findNavController().navigate(action)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticlesViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowArticlesBinding.inflate(layoutInflater, parent, false)
                return ArticlesViewHolder(binding)
            }
        }
    }

    class ArticlesListComparator : DiffUtil.ItemCallback<Hit>() {
        override fun areItemsTheSame(oldItem: Hit, newItem: Hit): Boolean =
            oldItem.storyId == newItem.storyId  //ID

        override fun areContentsTheSame(oldItem: Hit, newItem: Hit): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder =
        ArticlesViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}
