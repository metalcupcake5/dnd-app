package io.github.metalcupcake5.dndapp.data

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class CharacterRepository(private val characterDao: CharacterDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allCharacters: Flow<List<Character>> = characterDao.getAllCharacters()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(character: Character) {
        characterDao.insert(character)
    }

    suspend fun deleteAll(){
        characterDao.deleteAll()
    }

    fun getCharacter(id: Int) {
        characterDao.getCharacter(id)
    }
}
