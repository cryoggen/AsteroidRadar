package com.cryoggen.asteroidradar.picture

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cryoggen.asteroidradar.databinding.FragmentPictureOfDayBinding

@Suppress("DEPRECATION")
class PictureOfDayFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        /*Sets the horizontal orientation for displaying the image of the day on the full screen*/
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        /*Hides the Action Bar and the information panel*/
        (activity as AppCompatActivity).supportActionBar!!.hide()
        (activity as AppCompatActivity).window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

        val binding = FragmentPictureOfDayBinding.inflate(inflater)

        binding.lifecycleOwner = this

        /*Gets a link to the image*/
        val imageUrl = PictureOfDayFragmentArgs.fromBundle(requireArguments()).imageUrl

        /*Passes the link to the layout*/
        binding.imgUrl = imageUrl

        return binding.root
    }


}