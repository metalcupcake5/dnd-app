package io.github.metalcupcake5.dndapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import io.github.metalcupcake5.dndapp.data.Character
import io.github.metalcupcake5.dndapp.data.CharacterApplication
import io.github.metalcupcake5.dndapp.data.CharacterViewModel
import io.github.metalcupcake5.dndapp.data.CharacterViewModelFactory
import kotlinx.android.synthetic.main.fragment_character_info.*

/**
 * A simple [Fragment] subclass.
 * Use the [CharacterInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CharacterInfoFragment : Fragment() {
    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory((requireActivity().application as CharacterApplication).repository)
    }
    private val ARG_ID = "id"
    private var characterId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            characterId = it.getInt(ARG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var characterList : List<Character>
        characterViewModel.allCharacters.observe(requireActivity(), { characters ->
            characterList = characters.filter{ it.id == characterId }
            if(characterList.isNotEmpty()){
                val character = characterList[0]
                Log.d("characterInfoFragment", character.toString())
                textView_characterInfoFragment_name.text = character.name
                textView_characterInfoFragment_class.text = "Class: ${character.className}"
            }
        })



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_info, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            CharacterInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, param1)
                }
            }
    }
}