package io.github.metalcupcake5.dndapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character_table")
data class Character(
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val name: String,
        val race: String = "",
        val className: String = "",
        val background: String = "",
        val alignment: String = "",
        val xp: Int = 0,
        val strength: Int = 0,
        val dexterity: Int = 0,
        val constitution: Int = 0,
        val intelligence: Int = 0,
        val wisdom: Int = 0,
        val charisma: Int = 0,
        val hp: Int = 0,
        val initiativeBonus: Int = 0,
        val armorClass: Int = 0,
        val speed: Int = 0
)
