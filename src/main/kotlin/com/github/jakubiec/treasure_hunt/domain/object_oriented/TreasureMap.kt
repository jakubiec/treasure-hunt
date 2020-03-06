package com.github.jakubiec.treasure_hunt.domain.object_oriented

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap

class TreasureMap(input: ValidInputTreasureMap) {
    private val startingRow = 1
    private val startingColumn = 1
    private val table = input.table

    fun explore(): Set<Cell> =
        exploreTreasureMap()
            .takeIf { cells -> cells.any { cell -> cell.isTreasure() } }
            ?: emptySet()

    private fun exploreTreasureMap(): LinkedHashSet<Cell> {
        var cell = startingCell()
        val visitedCells = LinkedHashSet<Cell>()

        while (cell.isNotTreasure() && visitedCells.contains(cell).not()) {
            visitedCells.add(cell)
            cell = nextCellOf(cell)
        }

        visitedCells.add(cell)
        return visitedCells
    }

    private fun startingCell() = Cell(startingRow, startingColumn, table[startingRow - 1][startingColumn - 1])

    private fun nextCellOf(cell: Cell) = cell.run {
        Cell(nextCellRow, nextCellColumn, table[nextCellRow - 1][nextCellColumn - 1])
    }

    data class Cell(val row: Int, val column: Int, val value: Int) {

        internal val nextCellRow = value / 10
        internal val nextCellColumn = value % 10

        fun isNotTreasure(): Boolean = isTreasure().not()

        fun isTreasure() = row * 10 + column == value
    }

}
