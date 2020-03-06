package com.github.jakubiec.treasure_hunt.domain.object_oriented

sealed class Treasure
data class FoundTreasure(val visitedCells: Set<Cell>): Treasure()
object NotFoundTreasure: Treasure()