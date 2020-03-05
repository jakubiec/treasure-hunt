package com.github.jakubiec.treasure_hunt.domain.functional

internal data class TreasureMap(val cells: Set<Cell>, val visitedCells: Set<VisitedCell> = emptySet()) {

    private val cellLookup = cells.map { it.coordinates to it }.toMap()

    fun cellAt(coordinates: Coordinates): Cell = cellLookup.getValue(coordinates)

    fun visit(coordinates: Coordinates): TreasureMap = visitCell(cellAt(coordinates))

    private fun visitCell(cell: Cell) =
        when (cell) {
            is NotVisitedCell -> visitCell(cell)
            else -> copy()
        }

    private fun visitCell(cell: NotVisitedCell) =
        cell
            .visit()
            .let { visitedCell ->
                copy(
                    cells = replaceCell(cell.coordinates, visitedCell),
                    visitedCells = visitedCells + visitedCell
                )
            }


    private fun replaceCell(coordinates: Coordinates, cell: Cell) = (cellLookup + (coordinates to cell)).values.toSet()

}

sealed class Cell {
    abstract val value: CellValue
    abstract val coordinates: Coordinates

    companion object {

        fun of(coordinates: Coordinates, cellValue: CellValue): Cell =
            if (cellValue.toCoordinates() == coordinates) {
                TreasureCell(coordinates, cellValue)
            } else {
                NotVisitedCell(coordinates, cellValue)
            }
    }
}

internal data class NotVisitedCell(override val coordinates: Coordinates, override val value: CellValue) : Cell() {
    fun visit(): VisitedCell = VisitedCell(coordinates, value)
}

data class VisitedCell(override val coordinates: Coordinates, override val value: CellValue) : Cell()
internal data class TreasureCell(override val coordinates: Coordinates, override val value: CellValue) : Cell()

data class CellValue private constructor(val value: Int) {

    companion object {
        private val allowedRange = (11..55)

        fun of(value: Int): CellValue {
            require(value in allowedRange)
            return CellValue(value)
        }
    }

    fun toCoordinates(): Coordinates = Coordinates.of(tens(), units())

    private fun units() = value % 10

    private fun tens() = value / 10
}

data class Coordinates private constructor(val row: Int, val column: Int) {

    companion object {
        private val allowedRange = (1..5)

        fun of(row: Int, column: Int): Coordinates {
            require(row in allowedRange)
            require(column in allowedRange)
            return Coordinates(row, column)
        }
    }
}


