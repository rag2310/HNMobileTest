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

        //Inicializamos el recyclerView con su adaptador
        val articlesRV = binding.rvArticles
        articlesRV.adapter = articlesListAdapter
        articlesRV.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )


        //Callback para el manejo del swipe delete
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                homeViewModel.delete(viewHolder.adapterPosition)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)

        itemTouchHelper.attachToRecyclerView(articlesRV)

        //si se refresca la lista SwipeRefreshLayout se llama a la funcion refresh()
        binding.swipeRefreshArticles.setOnRefreshListener {
            homeViewModel.refresh()
            binding.swipeRefreshArticles.isRefreshing = false
        }

        //Observador de la lista de articulos para ser actualizada en el adaptador
        //del recyclerView
        homeViewModel.listHit.observe(viewLifecycleOwner, {
            articlesListAdapter.submitList(it)
        })

        //observador para mostrar el circular progressa indefinido.
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