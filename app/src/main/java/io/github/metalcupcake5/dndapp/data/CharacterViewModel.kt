package io.github.metalcupcake5.dndapp.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class CharacterViewModel(private val repository: CharacterRepository) : ViewModel() {
    // Using LiveData and caching what allCharacters returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allCharacters: LiveData<List<Character>> = repository.allCharacters.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(character: Character) = viewModelScope.launch {
        repository.insert(character)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun getCharacter(id: Int) = viewModelScope.launch {
        repository.getCharacter(id)
    }

    fun deleteCharacter(id: Int) = viewModelScope.launch {
        repository.deleteCharacter(id)
    }

    fun createCharacter(name: String, className: String, race: String, stats: HashMap<String, Int>) = viewModelScope.launch {
        val character = Character(name = name,className = className,race = race)
        repository.insert(character)
    }
}
class CharacterViewModelFactory(private val repository: CharacterRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CharacterViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CharacterViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
