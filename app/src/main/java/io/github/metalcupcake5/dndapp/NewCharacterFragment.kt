package io.github.metalcupcake5.dndapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
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
        val spinner: Spinner = rootView.findViewById(R.id.spinner_newCharacter_characterClass)

        ArrayAdapter.createFromResource(
            requireActivity(),
            R.array.characterClasses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val button = rootView.findViewById<Button>(R.id.button_newCharacter_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCharacterView.text)) {
                Toast.makeText(requireActivity(), "No name provided!", Toast.LENGTH_SHORT).show()
            } else {
                val characterName = editCharacterView.text.toString()
                val characterClass = spinner.selectedItem.toString()
                replyIntent.putExtra(NewCharacterActivity.EXTRA_NAME, characterName)
                replyIntent.putExtra(NewCharacterActivity.EXTRA_CLASS, characterClass)
                val character = Character(name = characterName,className = characterClass)
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