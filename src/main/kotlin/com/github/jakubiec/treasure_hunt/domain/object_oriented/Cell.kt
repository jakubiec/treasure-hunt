package com.github.jakubiec.treasure_hunt.domain.object_oriented

data class Cell(val row: Int, val column: Int, val value: Int) {

    internal val nextCellRow = value / 10
    internal val nextCellColumn = value % 10

    fun isNotTreasure(): Boolean = isTreasure().not()

    fun isTreasure() = row * 10 + column == value
}