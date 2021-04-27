package io.github.metalcupcake5.dndapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText

class NewCharacterActivity : AppCompatActivity() {
    private lateinit var editCharacterView: EditText

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)
        editCharacterView = findViewById(R.id.edit_character)

        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editCharacterView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val character = editCharacterView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, character)
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.example.android.wordlistsql.REPLY"
    }

}