package com.cryoggen.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.cryoggen.asteroidradar.Constants
import com.cryoggen.asteroidradar.database.getDatabase
import com.cryoggen.asteroidradar.domain.Asteroid
import com.cryoggen.asteroidradar.network.Network
import com.cryoggen.asteroidradar.repository.AsteroidApiFilter
import com.cryoggen.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch


class AsteroidsViewModel(application: Application) : AndroidViewModel(application) {

    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    /*Observe the receipt of a link to the image of the NASA day*/
    private var _pictureOfDayUrl = MutableLiveData<String>()
    val pictureOfDayUrl: LiveData<String>
        get() = _pictureOfDayUrl

    /*Observe the receipt of the caption to the image of the NASA day*/
    private var _pictureOfDayTitle = MutableLiveData<String>()
    val pictureOfDayTitle: LiveData<String>
        get() = _pictureOfDayTitle

    /*Requests a link to the image of the day from the server and updates the database*/
    init {
        getPictureOfDay()
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }

    }

    /*Observes the list of asteroids obtained from the database*/
    var asteroids: LiveData<List<Asteroid>> = asteroidsRepository.asteroids

    /*Requests a link to the NASA image of the day from the server*/
    private fun getPictureOfDay() {
        viewModelScope.launch {
            try {
                val pictureOfDay = Network.nasaApi!!.getPictureOfDay(Constants.API_KEY)
                if (pictureOfDay.mediaType == "image") {
                    _pictureOfDayTitle.value = pictureOfDay.title
                    _pictureOfDayUrl.value = pictureOfDay.url
                } else {
                    _pictureOfDayUrl.value = ""
                    _pictureOfDayTitle.value = ""
                }
            } catch (e: Exception) {
                _pictureOfDayUrl.value = ""
                _pictureOfDayTitle.value = ""
            }
        }

    }

    /*Returns a link to the image of the day to use later in another fragment*/
    fun getUrlPictureOfDay(): String? {
        return _pictureOfDayUrl.value
    }

    /*Сhanges the value of the filter for displaying the list of asteroids*/
    fun updateFilter(filter: AsteroidApiFilter) {
        asteroidsRepository.filterAsteroids.value = filter
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AsteroidsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AsteroidsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}

