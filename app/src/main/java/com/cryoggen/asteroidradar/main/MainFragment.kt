package com.cryoggen.asteroidradar.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cryoggen.asteroidradar.R
import com.cryoggen.asteroidradar.databinding.FragmentMainBinding
import com.cryoggen.asteroidradar.repository.AsteroidApiFilter

/*the fragment displays a list of asteroids*/
class MainFragment : Fragment() {

    private val viewModel: AsteroidsViewModel by lazy {
        val activity = requireNotNull(this.activity)
        ViewModelProvider(this, AsteroidsViewModel.Factory(activity.application)).get(
            AsteroidsViewModel::class.java
        )
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        val binding: FragmentMainBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_main,
            container,
            false
        )

        /*creates an adapter for RecyclerView*/
        binding.asteroidRecycler.adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
        })

        /*When you click on the image, we launch a fragment with the image in full screen*/
        binding.activityMainImageOfTheDay.setOnClickListener {
            this.findNavController()
                .navigate(MainFragmentDirections.actionMainFragmentToPictureOfDayFragment(viewModel.getUrlPictureOfDay()))
        }

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /*Menu for filtering the list of asteroids*/
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        viewModel.updateFilter(
            when (item.itemId) {
                R.id.show_saved_asteroids -> AsteroidApiFilter.SHOW_SAVED
                R.id.show_today_asteroids -> AsteroidApiFilter.SHOW_TODAY
                R.id.show_week_asteroids -> AsteroidApiFilter.SHOW_WEEK
                else -> AsteroidApiFilter.SHOW_SAVED
            }
        )
        return true
    }


}


