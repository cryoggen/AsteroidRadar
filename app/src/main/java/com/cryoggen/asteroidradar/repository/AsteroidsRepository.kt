package com.cryoggen.asteroidradar.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.cryoggen.asteroidradar.Constants
import com.cryoggen.asteroidradar.database.AsteroidsDatabase
import com.cryoggen.asteroidradar.database.asDomainModel
import com.cryoggen.asteroidradar.domain.asDatabaseModel
import com.cryoggen.asteroidradar.network.Network
import com.cryoggen.asteroidradar.network.getNextSevenDaysFormattedDates
import com.cryoggen.asteroidradar.network.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

enum class AsteroidApiFilter { SHOW_WEEK, SHOW_TODAY, SHOW_SAVED }

@Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AsteroidsRepository(private val database: AsteroidsDatabase) {

    var filterAsteroids = MutableLiveData<AsteroidApiFilter>(AsteroidApiFilter.SHOW_WEEK)

    /*Monitors the asteroid filter, makes a corresponding request to the database when the filter is changed*/
    val asteroids = Transformations.switchMap(filterAsteroids) { filter ->
        when (filter) {
            AsteroidApiFilter.SHOW_SAVED -> {
                Transformations.map(database.asteroidDao.getAsteroids()) { it.asDomainModel() }
            }
            AsteroidApiFilter.SHOW_TODAY -> {
                Transformations.map(
                    database.asteroidDao.getTodayAsteroids(
                        getNextSevenDaysFormattedDates()[0]
                    )
                )
                {
                    it.asDomainModel()
                }
            }
            AsteroidApiFilter.SHOW_WEEK -> {
                Transformations.map(
                    database.asteroidDao.getWeeklyAsteroids(
                        getNextSevenDaysFormattedDates()[0],
                        getNextSevenDaysFormattedDates()[getNextSevenDaysFormattedDates().size - 1]
                    )
                )
                {
                    it.asDomainModel()
                }
            }
        }
    }

    /*Update the asteroids stored in the offline cache*/
    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            try {
                val asteroidsList = Network.nasaApi!!.getAsteroids(Constants.API_KEY).body()
                val i = parseAsteroidsJsonResult(JSONObject(asteroidsList)).asDatabaseModel()
                database.asteroidDao.insertAll(*i)
            } catch (e: Exception) {

            }
        }
    }

}



