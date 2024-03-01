package com.ergea.ridsnewsapp.presentation.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.ridsnewsapp.R
import com.ergea.ridsnewsapp.common.proceedWhen
import com.ergea.ridsnewsapp.common.show
import com.ergea.ridsnewsapp.common.showSnackBar
import com.ergea.ridsnewsapp.databinding.FragmentNewsBinding
import com.ergea.ridsnewsapp.databinding.FragmentSearchBinding
import com.ergea.ridsnewsapp.presentation.news.NewsFragmentArgs
import com.ergea.ridsnewsapp.presentation.news.NewsViewModel
import com.ergea.ridsnewsapp.presentation.news.adapter.NewsAdapter
import com.ergea.ridsnewsapp.presentation.source.SourceFragmentDirections
import com.ergea.ridsnewsapp.presentation.source.adapter.SourceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by activityViewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter { data ->
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToNewsFragment(
                data.source?.id.orEmpty(),
                data.source?.name.orEmpty()
            ))
        }
    }
    private val sourceAdapter: SourceAdapter by lazy {
        SourceAdapter { data ->
            findNavController().navigate(
                SearchFragmentDirections.actionSearchFragmentToSourceFragment(
                    data.category.orEmpty()
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        setRecyclerViewNews()
        setRecyclerViewSource()
        binding.icBack.setOnClickListener {
            it.findNavController().popBackStack()
        }
    }

    private fun fetchData() = binding.run {
        etSearch.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val searchQuery = etSearch.text.toString().trim()
                viewModel.searchSourceAndNews(searchQuery)
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    private fun setRecyclerViewNews() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.newsAdapter
        }
        setObserveDataNews()
    }

    private fun setObserveDataNews() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.news.collect {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            pbLoading2.show(false)
                            rvNews.show(true)
                            tvEmptyNews.show(false)
                            result.payload?.let { data ->
                                newsAdapter.setItems(data)
                            }
                        },
                        doOnLoading = {
                            pbLoading2.show(true)
                            rvNews.show(false)
                            tvEmptyNews.show(false)
                        },
                        doOnError = { err ->
                            rvNews.show(false)
                            pbLoading2.show(false)
                            tvEmptyNews.show(true)
                            tvEmptyNews.text =
                                err.exception?.message.orEmpty() ?: "Check ur connection please.."
                            binding.root.showSnackBar(
                                err.exception?.message.orEmpty() ?: "Check ur connection please.."
                            )
                        },
                        doOnEmpty = {
                            pbLoading2.show(false)
                            rvNews.show(false)
                            tvEmptyNews.show(true)
                        }
                    )
                }
            }
        }
    }

    private fun setRecyclerViewSource() {
        binding.rvSource.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = this@SearchFragment.sourceAdapter
        }
        setObserveDataSource()
    }

    private fun setObserveDataSource() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.source.collect {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            pbLoading.show(false)
                            rvSource.show(true)
                            result.payload?.let { data ->
                                sourceAdapter.setItems(data)
                            }
                            tvEmptySource.show(false)
                        },
                        doOnLoading = {
                            pbLoading.show(true)
                            rvSource.show(false)
                            tvEmptySource.show(false)
                        },
                        doOnError = { err ->
                            rvSource.show(false)
                            pbLoading.show(false)
                            tvEmptySource.show(true)
                            tvEmptySource.text =
                                err.exception?.message.orEmpty() ?: "Check ur connection please.."
                            binding.root.showSnackBar(
                                err.exception?.message.orEmpty() ?: "Check ur connection please.."
                            )
                        },
                        doOnEmpty = {
                            pbLoading.show(false)
                            rvSource.show(false)
                            tvEmptySource.show(true)
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