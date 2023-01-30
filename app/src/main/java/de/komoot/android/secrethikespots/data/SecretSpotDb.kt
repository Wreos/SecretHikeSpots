package de.komoot.android.secrethikespots.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SecretSpotEntity::class], version = 1)
abstract class SecretSpotDb: RoomDatabase() {
    abstract fun secretSpotDao(): SecretSpotDao

    companion object {
        @Volatile
        private var INSTANCE: SecretSpotDb? = null

        fun getDatabase(context: Context): SecretSpotDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, SecretSpotDb::class.java, "secretSpotDatabase").build().also {
                    INSTANCE = it
                }
            }
        }
    }
}
