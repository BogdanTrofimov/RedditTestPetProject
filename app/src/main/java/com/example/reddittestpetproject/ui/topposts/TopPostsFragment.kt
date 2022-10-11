package com.example.reddittestpetproject.ui.topposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reddittestpetproject.R
import com.example.reddittestpetproject.databinding.FragmentToppostsBinding
import com.example.reddittestpetproject.databinding.ViewItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopPostsFragment : Fragment() {
    private lateinit var binding: FragmentToppostsBinding
    private var redditAdapter: RedditAdapter? = null
    private val topPostsViewModel by viewModels<TopPostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToppostsBinding.inflate(inflater)
        binding.viewModel = topPostsViewModel
        redditAdapter = RedditAdapter(OnClickListener {
            topPostsViewModel.displayDetail(it)
        })
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = redditAdapter
        }
        topPostsViewModel.navigateToSelected.observe(viewLifecycleOwner) {
            if (null != it) {
                val itemBinding = ViewItemBinding.inflate(inflater)
                val extras = FragmentNavigatorExtras(
                    itemBinding.image as View to getString(R.string.transition_name)
                )
                this.findNavController().navigate(
                    TopPostsFragmentDirections.actionNavGalleryToDetailFragment(it.postData.thumbnail),
                    extras
                )
                topPostsViewModel.displayDetailComplete()
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topPostsViewModel.childrenList.observe(viewLifecycleOwner) { childrenList ->
            childrenList.apply {
                redditAdapter?.childrenList = childrenList
            }
        }
    }
}