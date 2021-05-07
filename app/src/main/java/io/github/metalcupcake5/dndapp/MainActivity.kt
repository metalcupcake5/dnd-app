package io.github.metalcupcake5.dndapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.github.metalcupcake5.dndapp.data.Character
import io.github.metalcupcake5.dndapp.data.CharacterApplication
import io.github.metalcupcake5.dndapp.data.CharacterViewModel
import io.github.metalcupcake5.dndapp.data.CharacterViewModelFactory

class MainActivity : AppCompatActivity() {

    /*private val newCharacterActivityRequestCode = 1
    private val characterViewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory((application as CharacterApplication).repository)
    }*/

    private lateinit var navController : NavController
    private lateinit var navHostFragment : NavHostFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController


        NavigationUI.setupActionBarWithNavController(this,navController)

        /*val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val adapter = CharacterListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        characterViewModel.allCharacters.observe(this, { characters ->
            characters?.let { adapter.submitList(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewCharacterActivity::class.java)
            startActivityForResult(intent, newCharacterActivityRequestCode)
        }*/
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newCharacterActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getStringExtra(NewCharacterActivity.EXTRA_NAME)?.let {
                val character = Character(name = it,className = NewCharacterActivity.EXTRA_CLASS)
                characterViewModel.insert(character)

            }
        } else {
            Toast.makeText(
                applicationContext,
                R.string.empty_not_saved,
                Toast.LENGTH_LONG).show()
        }
    }*/


}