package io.github.metalcupcake5.dndapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.metalcupcake5.dndapp.data.CharacterApplication
import io.github.metalcupcake5.dndapp.data.CharacterViewModel
import io.github.metalcupcake5.dndapp.data.CharacterViewModelFactory
import kotlinx.android.synthetic.main.fragment_character_list.*
import kotlinx.android.synthetic.main.fragment_character_list.view.*

class CharacterListFragment : Fragment() {

    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory((requireActivity().application as CharacterApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_character_list, container, false)

        val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_characterListFragment_characters)
        val adapter = CharacterListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())

        characterViewModel.allCharacters.observe(requireActivity(), { characters ->
            characters?.let { adapter.submitList(it) }
        })


        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return rootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.new_character_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        view?.findNavController()?.navigate(R.id.action_characterListFragment_to_newCharacterFragment)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            CharacterListFragment()
    }
}