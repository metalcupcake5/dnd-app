package io.github.metalcupcake5.dndapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class NewCharacterActivity : AppCompatActivity() {
    private lateinit var editCharacterView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
        editCharacterView = findViewById(R.id.textView_newCharacter_name)
        val spinner: Spinner = findViewById(R.id.spinner_newCharacter_characterClass)

        ArrayAdapter.createFromResource(
            this,
            R.array.characterClasses,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val button = findViewById<Button>(R.id.button_newCharacter_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCharacterView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val characterName = editCharacterView.text.toString()
                val characterClass = spinner.selectedItem.toString()
                replyIntent.putExtra(EXTRA_NAME, characterName)
                replyIntent.putExtra(EXTRA_CLASS, characterClass)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_NAME = "name"
        const val EXTRA_CLASS = "class"
    }

}