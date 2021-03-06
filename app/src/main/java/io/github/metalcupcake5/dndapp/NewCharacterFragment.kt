package io.github.metalcupcake5.dndapp

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.metalcupcake5.dndapp.data.*

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
        val strSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityStr)
        val dexSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityDex)
        val conSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityCon)
        val intSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityInt)
        val wisSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityWis)
        val chaSpinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_abilityCha)


        // class json
        val classInputStream = resources.openRawResource(R.raw.classes)
        val classInputString = classInputStream.bufferedReader().use {
            it.readText()
        }
        // race json
        /*val raceInputStream = resources.openRawResource(R.raw.races)
        val raceInputString = raceInputStream.bufferedReader().use {
            it.readText()
        }*/

        // race data json
        val raceDataInputStream = resources.openRawResource(R.raw.race_data)
        val raceDataInputString = raceDataInputStream.bufferedReader().use {
            it.readText()
        }

        // parsing the string into our custom objects using Gson
        val gson = Gson()
        // use the parsing between collection, list, or array section of:
        // https://medium.com/@hissain.khan/parsing-with-google-gson-library-in-android-kotlin-7920e26f5520
        val jsonDataType = object : TypeToken<List<String>>() {}.type
        val classes = gson.fromJson<List<String>>(classInputString, jsonDataType)
        //val races = gson.fromJson<List<String>>(raceInputString, jsonDataType)
        val races: ArrayList<String> = ArrayList()
        val raceJsonDataType = object : TypeToken<List<Race>>() {}.type
        val raceData = gson.fromJson<List<Race>>(raceDataInputString, raceJsonDataType)
        for(race in raceData){
            races.add(race.name)
        }

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
                val characterRaceObject = raceData[races.indexOf(raceSpinner.selectedItem.toString())]

                // Ability Scores
                val str = strSpinner.selectedItem.toString().toInt()
                val dex = dexSpinner.selectedItem.toString().toInt()
                val con = conSpinner.selectedItem.toString().toInt()
                val int = intSpinner.selectedItem.toString().toInt()
                val wis = wisSpinner.selectedItem.toString().toInt()
                val cha = chaSpinner.selectedItem.toString().toInt()

                val abilityScores = mutableMapOf("str" to str, "dex" to dex, "con" to con, "int" to int, "wis" to wis, "cha" to cha)
                characterRaceObject.ability_bonuses.forEach { bonus ->
                    val currentValue = abilityScores[bonus.index]
                    if (currentValue != null) {
                        abilityScores[bonus.index] = currentValue + bonus.bonus
                    }
                }
                val character = Character(
                    name = characterName,
                    className = characterClass,
                    race = characterRace,
                    strength = abilityScores["str"],
                    dexterity = abilityScores["dex"],
                    constitution = abilityScores["con"],
                    intelligence = abilityScores["int"],
                    wisdom = abilityScores["wis"],
                    charisma = abilityScores["cha"],
                    speed = characterRaceObject.speed
                )
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