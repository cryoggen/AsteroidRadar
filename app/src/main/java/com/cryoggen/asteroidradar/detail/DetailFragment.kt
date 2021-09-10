package com.cryoggen.asteroidradar.detail


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.cryoggen.asteroidradar.R
import com.cryoggen.asteroidradar.databinding.FragmentDetailBinding

/*The fragment displays detailed information about the asteroid*/

class DetailFragment : Fragment() {

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDetailBinding.inflate(inflater)

        /*Set the lifecycleOwner so DataBinding can observe LiveData*/
        binding.lifecycleOwner = this

        /*Retrieves the Asteroid object for detailed information display*/
        val asteroid = DetailFragmentArgs.fromBundle(arguments!!).selectedAsteroid

        binding.asteroid = asteroid

        /*Processing a button click with a question mark*/
        binding.helpButton.setOnClickListener {
            displayAstronomicalUnitExplanationDialog()
        }

        return binding.root
    }

    /*Processing a button click with a question mark*/
    @SuppressLint("UseRequireInsteadOfGet")
    private fun displayAstronomicalUnitExplanationDialog() {
        val builder = AlertDialog.Builder(activity!!)
            .setMessage(getString(R.string.astronomica_unit_explanation))
            .setPositiveButton(android.R.string.ok, null)
        builder.create().show()
    }
}
