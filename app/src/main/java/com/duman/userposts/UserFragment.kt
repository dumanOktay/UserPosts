package com.duman.userposts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.duman.userposts.data.model.ApiResult
import com.duman.userposts.databinding.FragmentUserBinding
import com.duman.userposts.ui.UserPostViewModel
import com.duman.userposts.ui.adapters.UserAdapter
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val viewModel: UserPostViewModel by activityViewModels()
    private var userAdapter = UserAdapter { userDataUiModel ->
        viewModel.setCurrentUserDataModel(userDataUiModel)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvUserList.apply {
            adapter = userAdapter
        }
        viewModel.resultFlow.onEach { result ->
            when (result) {
                ApiResult.Fail -> {
                    Snackbar.make(binding.rvUserList, "Network error", Snackbar.LENGTH_SHORT).show()
                }
                ApiResult.Loading -> {
                    binding.progressView.isVisible = true
                    binding.rvUserList.isVisible = false
                }
                is ApiResult.Success -> {
                    userAdapter.submitList(result.data)
                    binding.progressView.isVisible = false
                    binding.rvUserList.isVisible = true
                }
                null -> {}
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
