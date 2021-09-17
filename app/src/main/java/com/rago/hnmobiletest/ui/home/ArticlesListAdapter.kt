package com.rago.hnmobiletest.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.fragment.app.findFragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rago.hnmobiletest.databinding.RowArticlesBinding

class ArticlesListAdapter :
    ListAdapter<String, ArticlesListAdapter.ArticlesViewHolder>(
        ArticlesListComparator()
    ) {

    class ArticlesViewHolder(private val binding: RowArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: String) {
            binding.tvStoryTitle.text = data
            binding.cvArticle.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToArticlesDetailsFragment(data)
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

    class ArticlesListComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem  //ID

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesViewHolder =
        ArticlesViewHolder.from(parent)

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}
