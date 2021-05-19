package io.github.metalcupcake5.dndapp

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.metalcupcake5.dndapp.data.Character
import io.github.metalcupcake5.dndapp.data.CharacterApplication
import io.github.metalcupcake5.dndapp.data.CharacterViewModel
import io.github.metalcupcake5.dndapp.data.CharacterViewModelFactory

class NewCharacterFragment : Fragment() {

    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory((requireActivity().application as CharacterApplication).repository)
    }

    private lateinit var editCharacterView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_new_character, container, false)

        editCharacterView = rootView.findViewById(R.id.textView_newCharacter_name)
        val classSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_characterClass)
        val raceSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_characterRace)

        // class json
        val classInputStream = resources.openRawResource(R.raw.classes)
        val classInputString = classInputStream.bufferedReader().use {
            it.readText()
        }
        // race json
        val raceInputStream = resources.openRawResource(R.raw.races)
        val raceInputString = raceInputStream.bufferedReader().use {
            it.readText()
        }

        // parsing the string into our custom objects using Gson
        val gson = Gson()
        // use the parsing between collection, list, or array section of:
        // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
        val jsonDataType = object : TypeToken<List<String>>() {}.type
        val classes = gson.fromJson<List<String>>(classInputString, jsonDataType)
        val races = gson.fromJson<List<String>>(raceInputString, jsonDataType)

        ArrayAdapter(
            requireActivity(),
            R.layout.support_simple_spinner_dropdown_item,
            classes
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            classSpinner.adapter = adapter
        }

        ArrayAdapter(
            requireActivity(),
            R.layout.support_simple_spinner_dropdown_item,
            races
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            raceSpinner.adapter = adapter
        }

        val button = rootView.findViewById<Button>(R.id.button_newCharacter_save)
        button.setOnClickListener {
            if (TextUtils.isEmpty(editCharacterView.text)) {
                Toast.makeText(requireActivity(), "No name provided!", Toast.LENGTH_SHORT).show()
            } else {
                val characterName = editCharacterView.text.toString()
                val characterClass = classSpinner.selectedItem.toString()
                val characterRace = raceSpinner.selectedItem.toString()
                val character = Character(name = characterName,className = characterClass,race = characterRace)
                val race = rootView.findViewById<EditText>(R.id.textView_newCharacter_race).text.toString()

                characterViewModel.insert(character)
                view?.findNavController()?.navigate(R.id.action_newCharacterFragment_to_characterListFragment)
            }
        }
        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewCharacterFragment()
    }
}