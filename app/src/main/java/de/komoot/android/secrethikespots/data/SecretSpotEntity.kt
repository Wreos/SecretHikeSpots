package de.komoot.android.secrethikespots.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SecretSpot")
data class SecretSpotEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
    val image: String
)