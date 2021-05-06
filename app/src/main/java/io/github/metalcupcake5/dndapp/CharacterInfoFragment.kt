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
    private lateinit var character: Character

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
        Log.d("characterInfoFragment: ", characterId.toString())
        characterViewModel.getCharacter(characterId).invokeOnCompletion { data ->

        }
        /*val character = characterViewModel.allCharacters.observe(requireActivity(), { characters ->
            character = characters[0]
            Log.d("characterInfoFragment: ", character.toString())
        })*/


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_info, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CharacterInfoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int) =
            CharacterInfoFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, param1)
                }
            }
    }
}