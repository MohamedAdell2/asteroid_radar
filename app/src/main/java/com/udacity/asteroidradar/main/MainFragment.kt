package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        val app = requireNotNull(this.activity)
        ViewModelProvider(this, MainViewModel.Factory(app.application))
            .get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val adapter = AsteroidAdapter(AsteroidOnClickListener{
            this.findNavController().navigate(MainFragmentDirections
                .actionShowDetail(it))
        } )
        binding.viewModel = viewModel
        binding.asteroidRecycler.adapter=adapter
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.filterResult(when(item.itemId){
            R.id.show_today_menu ->DataBaseFilter.Today
            else ->DataBaseFilter.ALL
        })
        return true
    }
}
