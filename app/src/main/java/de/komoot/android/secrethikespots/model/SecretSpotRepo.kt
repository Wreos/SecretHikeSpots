package de.komoot.android.secrethikespots.model

import android.content.Context
import de.komoot.android.secrethikespots.data.SecretSpotDb
import de.komoot.android.secrethikespots.data.SecretSpotEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.random.Random
import kotlin.random.nextLong


class SecretSpotRepo(context: Context) {
    private val db by lazy { SecretSpotDb.getDatabase(context.applicationContext) }

    fun getAll() : Flow<List<SecretSpot>> {
        return db.secretSpotDao().getAll().distinctUntilChanged().map { list ->
            list.map { it.toModel() }
        }
    }

    suspend fun add(new: NewSpot) {
        withContext(Dispatchers.IO) {
            delay(Random.nextLong(50L..5000L))
            db.secretSpotDao().insert(
                SecretSpotEntity(
                    Date().time,
                    new.name,
                    new.location.latitude,
                    new.location.longitude,
                    new.image
                )
            )
        }
    }

    suspend fun update(update: UpdateSpot) {
        withContext(Dispatchers.IO) {
            delay(Random.nextLong(50L..1000L))
            db.secretSpotDao().update(
                update.spot.id,
                update.name
            )
        }
    }

    suspend fun delete(id: Long) {
        withContext(Dispatchers.IO) {
            delay(Random.nextLong(50L..3000L))
            db.secretSpotDao().delete(id)
        }
    }
}
