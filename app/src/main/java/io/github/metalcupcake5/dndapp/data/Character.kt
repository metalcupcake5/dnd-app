package io.github.metalcupcake5.dndapp.data

data class Character(
    val name: String,
    val race: String,
    val className: String,
    val background: String,
    val alignment: String,
    val xp: Int,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int,
    val hp: Int,
    val initiativeBonus: Int,
    val armorClass: Int,
    val speed: Int
)
