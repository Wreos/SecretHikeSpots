package de.komoot.android.secrethikespots.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SecretSpotDao {
    @Query("SELECT * FROM SecretSpot ORDER BY id DESC")
    fun getAll(): Flow<List<SecretSpotEntity>>

    @Insert
    fun insert(secretSpot: SecretSpotEntity)

    @Query("UPDATE SecretSpot SET name = :name WHERE id = :id")
    fun update(id: Long, name: String)

    @Query("DELETE FROM SecretSpot WHERE id = :id")
    fun delete(id: Long)
}