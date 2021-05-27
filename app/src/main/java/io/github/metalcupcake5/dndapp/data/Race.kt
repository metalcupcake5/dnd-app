package io.github.metalcupcake5.dndapp.data

data class Race(
    val index: String,
    val name: String,
    val speed: Int,
    val ability_bonuses: List<AbilityBonuses>,
    val size: String,
    val ability_bonus_options: AbilityBonusOptions? = null

)