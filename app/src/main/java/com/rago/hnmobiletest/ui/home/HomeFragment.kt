package com.rago.hnmobiletest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rago.hnmobiletest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val homeViewModel: HomeViewModels by viewModels()
    private lateinit var articlesListAdapter: ArticlesListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        articlesListAdapter = ArticlesListAdapter()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articlesRV = binding.rvArticles
        articlesRV.adapter = articlesListAdapter
        articlesRV.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )


        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                homeViewModel.delete(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)

        itemTouchHelper.attachToRecyclerView(articlesRV)

        binding.swipeRefreshArticles.setOnRefreshListener {
            homeViewModel.refresh()
            binding.swipeRefreshArticles.isRefreshing = false
        }

        homeViewModel.listHit.observe(viewLifecycleOwner, {
            articlesListAdapter.submitList(it)
        })

        homeViewModel.loading.observe(viewLifecycleOwner, {
            if (!it) {
                binding.cpiLoadingArticles.visibility = View.GONE
                binding.swipeRefreshArticles.visibility = View.VISIBLE
            } else {
                binding.cpiLoadingArticles.visibility = View.VISIBLE
                binding.swipeRefreshArticles.visibility = View.GONE
            }
        })
    }
}