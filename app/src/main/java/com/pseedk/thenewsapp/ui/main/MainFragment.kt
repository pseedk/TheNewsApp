package com.pseedk.thenewsapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.pseedk.thenewsapp.R
import com.pseedk.thenewsapp.databinding.FragmentMainBinding
import com.pseedk.thenewsapp.ui.adapters.NewsAdapter
import com.pseedk.thenewsapp.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<MainViewModel>()
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        newsAdapter.setOnItemClickListener {
            val bundle = bundleOf("article" to it)
            view.findNavController().navigate(
                R.id.action_mainFragment_to_detailsFragment,
                bundle
            )
        }

        viewModel.newsLiveData.observe(viewLifecycleOwner) { response ->
            when(response) {
                is Resource.Success -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        newsAdapter.differ.submitList(it.articles)
                    }
                }
                is Resource.Error -> {
                    binding.pagProgressBar.visibility = View.INVISIBLE
                    response.data?.let {
                        Log.e("checkData", " MainFragment: error: ${it}")
                    }
                }
                is Resource.Loading -> {
                    binding.pagProgressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initAdapter() {
        newsAdapter = NewsAdapter()
        binding.newsAdapter.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}