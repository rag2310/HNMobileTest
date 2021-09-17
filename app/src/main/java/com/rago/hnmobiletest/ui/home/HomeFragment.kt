package com.rago.hnmobiletest.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rago.hnmobiletest.R
import com.rago.hnmobiletest.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

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

        homeViewModel.listArticles.observe(viewLifecycleOwner, {
            articlesListAdapter.submitList(it)
        })
    }

    /*val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {


        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            println("onSwiped: placesList ${homeViewModel.listArticles.value!!.size}")
            println("position ${viewHolder.adapterPosition}")
        }

    }*/
}