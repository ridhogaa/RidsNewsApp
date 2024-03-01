package com.ergea.ridsnewsapp.presentation.source

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergea.ridsnewsapp.common.proceedWhen
import com.ergea.ridsnewsapp.common.show
import com.ergea.ridsnewsapp.common.showSnackBar
import com.ergea.ridsnewsapp.databinding.FragmentSourceBinding
import com.ergea.ridsnewsapp.presentation.home.HomeFragmentDirections
import com.ergea.ridsnewsapp.presentation.source.adapter.SourceAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourceFragment : Fragment() {

    private val viewModel: SourceViewModel by activityViewModels()
    private var _binding: FragmentSourceBinding? = null
    private val binding get() = _binding!!
    private val navArgs: SourceFragmentArgs by navArgs()
    private val sourceAdapter: SourceAdapter by lazy {
        SourceAdapter { data ->
            findNavController().navigate(
                SourceFragmentDirections.actionSourceFragmentToNewsFragment(
                    data.id.orEmpty(),
                    data.name.orEmpty()
                )
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSourceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchData()
        setRecyclerView()
    }

    private fun fetchData() = with(viewModel) {
        getSource(navArgs.category)
    }

    private fun setRecyclerView() {
        binding.rvSource.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SourceFragment.sourceAdapter
        }
        setObserveData()
    }

    private fun setObserveData() = binding.run {
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
                        },
                        doOnLoading = {
                            pbLoading.show(true)
                            rvSource.show(false)
                        },
                        doOnError = { err ->
                            rvSource.show(false)
                            pbLoading.show(false)
                            binding.root.showSnackBar(err.exception?.message.orEmpty() ?: "Check ur connection please..")
                        },
                        doOnEmpty = {
                            pbLoading.show(false)
                            rvSource.show(false)
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