package com.duman.userposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.duman.userposts.databinding.FragmentPostBinding
import com.duman.userposts.ui.UserPostViewModel
import com.duman.userposts.ui.adapters.PostAdapter

class PostFragment : Fragment() {

    private var _binding: FragmentPostBinding? = null
    private val viewModel: UserPostViewModel by activityViewModels()
    private val postAdapter = PostAdapter()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvCommentList.apply {
            adapter = postAdapter
        }

        viewModel.getCurrentUserDataModel()?.let { uiModel ->
            postAdapter.submitList(uiModel.postList)
            Glide.with(binding.userImage).load(uiModel.imageUrl).into(binding.userImage)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        viewModel.clearUserDataModel()
    }
}
