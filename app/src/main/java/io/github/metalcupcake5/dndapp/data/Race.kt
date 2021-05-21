package io.github.metalcupcake5.dndapp.data

data class Race(
    val ability_bonuses: List<AbilityBonuses>,
    val index: String,
    val name: String,
    val size: String,
    val speed: Int
)