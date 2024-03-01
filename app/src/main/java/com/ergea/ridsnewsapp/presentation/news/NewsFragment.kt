package com.ergea.ridsnewsapp.presentation.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.ridsnewsapp.common.proceedWhen
import com.ergea.ridsnewsapp.common.show
import com.ergea.ridsnewsapp.common.showSnackBar
import com.ergea.ridsnewsapp.databinding.FragmentNewsBinding
import com.ergea.ridsnewsapp.presentation.detail.DetailActivity
import com.ergea.ridsnewsapp.presentation.news.adapter.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {

    private val viewModel: NewsViewModel by activityViewModels()
    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val navArgs: NewsFragmentArgs by navArgs()
    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter { data ->
            DetailActivity.startActivity(requireContext(), data)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvNews.text = "${navArgs.sourceName} Article"
        fetchData()
        setRecyclerViewNews()
    }

    private fun fetchData() = with(viewModel) {
        getNews(navArgs.sourceId)
    }

    private fun setRecyclerViewNews() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@NewsFragment.newsAdapter
        }
        setObserveDataNews()
    }

    private fun setObserveDataNews() = binding.run {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.news.collect {
                    it.proceedWhen(
                        doOnSuccess = { result ->
                            pbLoading.show(false)
                            rvNews.show(true)
                            result.payload?.let { data ->
                                newsAdapter.setItems(data)
                            }
                        },
                        doOnLoading = {
                            pbLoading.show(true)
                            rvNews.show(false)
                        },
                        doOnError = { err ->
                            rvNews.show(false)
                            pbLoading.show(false)
                            binding.root.showSnackBar(err.exception?.message.orEmpty() ?: "Check ur connection please..")
                        },
                        doOnEmpty = {
                            pbLoading.show(false)
                            rvNews.show(false)
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