package com.cryoggen.asteroidradar.adapters

import android.icu.number.NumberFormatter.with
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cryoggen.asteroidradar.R
import com.cryoggen.asteroidradar.domain.Asteroid
import com.cryoggen.asteroidradar.main.AsteroidAdapter
import com.squareup.picasso.Picasso

/*Binding adapter used for getting the list of asteroids by the adapter*/
@BindingAdapter("listData")
fun bindRecyclerView(
    recyclerView: RecyclerView,
    data: List<Asteroid>?
) {
    val adapter = recyclerView.adapter as AsteroidAdapter
    adapter.submitList(data)
}

/*Binding adapter retrieves an image of the NASA day using the Picasso library*/
@BindingAdapter("imageUrl")
fun setImageUrl(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get()
            .load(imgUri)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.saturn)
            .into(imgView)
    }
}

/*Binding adapter selects the icon status to display*/
@BindingAdapter("statusIcon")
fun bindAsteroidStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.ic_baseline_thumb_down_alt_24)
    } else {
        imageView.setImageResource(R.drawable.ic_baseline_thumb_up_alt_24)
    }
}

/*Binding adapter selects the image according to the status of the asteroid*/
@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    if (isHazardous) {
        imageView.setImageResource(R.drawable.asteroid_hazardous)
        imageView.contentDescription =
            imageView.context.getString(R.string.potentially_hazardous_asteroid_image)
    } else {
        imageView.setImageResource(R.drawable.asteroid_safe)
        imageView.contentDescription =
            imageView.context.getString(R.string.not_hazardous_asteroid_image)
    }
}

/*The methods below are used for formatting text*/
@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}
