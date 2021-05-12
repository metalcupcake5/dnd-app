package io.github.metalcupcake5.dndapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM character_table")
    fun getAllCharacters(): Flow<List<Character>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(character: Character)

    @Query("DELETE FROM character_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM character_table WHERE id LIKE :id")
    fun getCharacter(id: Int): Flow<List<Character>>

    @Query("DELETE FROM character_table WHERE id LIKE :id")
    suspend fun deleteCharacter(id: Int)
}
