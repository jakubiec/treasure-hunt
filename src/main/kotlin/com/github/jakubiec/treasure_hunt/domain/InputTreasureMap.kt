package com.github.jakubiec.treasure_hunt.domain

sealed class InputTreasureMap {

    companion object {
        private const val requiredSize = 5
        private val allowedValueRange = (11..55)

        fun of(table: Array<IntArray>): InputTreasureMap =
            table
                .takeIf(::hasRequiredSize)
                ?.takeIf(::rowsHasRequiredSizeAndValues)
                ?.let { ValidInputTreasureMap(table) }
                ?: InvalidInputTreasureMap(table)

        private fun hasRequiredSize(input: Array<IntArray>) = input.size == requiredSize

        private fun rowsHasRequiredSizeAndValues(input: Array<IntArray>) = input.all(::rowHasRequiredSizeAndValues)

        private fun rowHasRequiredSizeAndValues(row: IntArray) = hasRequiredSize(row) && hasAllowedValue(row)

        private fun hasRequiredSize(input: IntArray) = input.size == requiredSize

        private fun hasAllowedValue(row: IntArray) = row.all { value -> value in allowedValueRange }
    }
}

data class ValidInputTreasureMap internal constructor(val table: Array<IntArray>) : InputTreasureMap()
data class InvalidInputTreasureMap internal constructor(val input: Array<IntArray>) : InputTreasureMap()
