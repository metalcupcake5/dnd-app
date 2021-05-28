package io.github.metalcupcake5.dndapp

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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
                Log.d("characterinfofragment ", "right before name yea ok")
                Log.d("characterinfofragment: ", character.name)
                textView_characterInfoFragment_name.text = character.name
                textView_characterInfoFragment_description.text = "${character.race} ${character.className}"
                textView_characterInfo_level.text = "${character.xp} xp"
                textView_characterInfo_speed.text = "Speed: ${character.speed}"
                textView_characterInfo_strValue.text = character.strength.toString()
                textView_characterInfo_dexValue.text = character.dexterity.toString()
                textView_characterInfo_conValue.text = character.constitution.toString()
                textView_characterInfo_intValue.text = character.intelligence.toString()
                textView_characterInfo_wisValue.text = character.wisdom.toString()
                textView_characterInfo_chaValue.text = character.charisma.toString()
            }
            characterViewModel.allCharacters.removeObservers(requireActivity())
        })

        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_character_info, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.character_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.toString() == "Delete Character") {
            characterViewModel.deleteCharacter(characterId)
            Toast.makeText(requireActivity().application as CharacterApplication, "Character deleted", Toast.LENGTH_SHORT).show()
            view?.findNavController()?.navigate(R.id.action_characterInfoFragment_to_characterListFragment)
        }
        return super.onOptionsItemSelected(item)
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