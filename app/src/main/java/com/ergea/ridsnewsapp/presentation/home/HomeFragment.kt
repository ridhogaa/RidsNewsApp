package com.ergea.ridsnewsapp.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.ergea.ridsnewsapp.common.proceedWhen
import com.ergea.ridsnewsapp.databinding.FragmentHomeBinding
import com.ergea.ridsnewsapp.presentation.home.adapter.CategoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter { data ->

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        setRecyclerViewCategory()
    }

    private fun fetchData() = with(viewModel) {
        getCategories()
    }

    private fun setRecyclerViewCategory() {
        binding.rvCategory.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = this@HomeFragment.categoryAdapter
        }
        setObserveDataCategory()
    }

    private fun setObserveDataCategory() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.category.collect {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            result.payload?.let { payload ->
                                categoryAdapter.setItems(payload)
                            }
                        }
                    )
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}