package com.cryoggen.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cryoggen.asteroidradar.databinding.AsteroidItemBinding
import com.cryoggen.asteroidradar.domain.Asteroid
import kotlinx.android.synthetic.main.fragment_main.view.*


/*This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
data, including computing diffs between lists.*/
class AsteroidAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Asteroid,
            AsteroidAdapter.AsteroidViewHolder>(DiffCallback) {


    class AsteroidViewHolder(var binding: AsteroidItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid

            /*it forces the data binding to execute immediately,
            which allows the RecyclerView to make the correct view size measurements*/
            binding.executePendingBindings()
        }
    }

    /*Allows the RecyclerView to determine which items have changed when the [List] of [Asteroids]
    has been updated.*/
    companion object DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AsteroidViewHolder {
        return AsteroidViewHolder(
            AsteroidItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.binding.asteroidCallback = onClickListener
        holder.bind(asteroid)
    }

    /*Custom listener that handles clicks on [RecyclerView] items.  Passes the [Asteroid]*/
    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}

