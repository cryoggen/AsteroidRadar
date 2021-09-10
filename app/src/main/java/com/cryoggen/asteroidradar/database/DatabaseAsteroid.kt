package com.cryoggen.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cryoggen.asteroidradar.domain.Asteroid


@Entity
data class DatabaseAsteroid(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = formatDateDomainModel(it.closeApproachDate),
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}

/*changing the order of writing the date*/
fun formatDateDomainModel(date: String): String {
    val stringArray = date.split("-")
    var result = ""
    for (i in stringArray.size - 1 downTo 0) {
        result += stringArray[i]
        if (i != 0) {
            result += "-"
        }
    }
    return result
}
