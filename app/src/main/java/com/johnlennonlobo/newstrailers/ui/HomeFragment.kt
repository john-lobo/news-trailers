package com.johnlennonlobo.newstrailers.ui

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.johnlennonlobo.newstrailers.MainActivity
import com.johnlennonlobo.newstrailers.R
import com.johnlennonlobo.newstrailers.databinding.HomeFragmentBinding
import com.johnlennonlobo.newstrailers.ui.adapter.ListOfMoviesAdapter
import com.johnlennonlobo.newstrailers.viewmodel.HomeViewModel
import javax.inject.Inject

class HomeFragment : Fragment() {


    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory
    private val viewModel by viewModels<HomeViewModel>{viewModelFactory}


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity() as MainActivity).mainComponent.inject(this)
    }

    private lateinit var binding: HomeFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.home_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        onAttachObservers()

        return binding.root
    }

    private fun onAttachObservers() {
        listsOfMoviesObservers()
    }

    private fun listsOfMoviesObservers() {
        viewModel.listOfMovies?.observe(viewLifecycleOwner){ listMovies ->
            listMovies?.let {
                binding.rvListOfMovies.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                binding.rvListOfMovies.adapter = ListOfMoviesAdapter(requireContext(),listMovies)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}