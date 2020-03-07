package com.github.jakubiec.treasure_hunt.domain.functional

internal data class TreasureMap(val cells: Set<Cell>, val visitedCells: Set<VisitedCell> = emptySet()) {

    private val cellLookup = cells.map { it.coordinates to it }.toMap()

    fun cellAt(coordinates: ValidCoordinates): Cell = cellLookup.getValue(coordinates)

    fun visit(coordinates: ValidCoordinates): TreasureMap = visitCell(cellAt(coordinates))

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


    private fun replaceCell(coordinates: ValidCoordinates, cell: Cell) =
        (cellLookup + (coordinates to cell)).values.toSet()

}

sealed class Cell {
    abstract val value: CellValue
    abstract val coordinates: ValidCoordinates

    companion object {

        fun of(coordinates: ValidCoordinates, cellValue: CellValue): Cell =
            if (cellValue.toCoordinates() == coordinates) {
                TreasureCell(coordinates, cellValue)
            } else {
                NotVisitedCell(coordinates, cellValue)
            }
    }
}

internal data class NotVisitedCell(override val coordinates: ValidCoordinates, override val value: CellValue) : Cell() {
    fun visit(): VisitedCell = VisitedCell(coordinates, value)
}

data class VisitedCell(override val coordinates: ValidCoordinates, override val value: CellValue) : Cell()
internal data class TreasureCell(override val coordinates: ValidCoordinates, override val value: CellValue) : Cell()

data class CellValue private constructor(val value: Int) {

    companion object {
        private val allowedRange = (11..55)

        fun of(value: Int): CellValue {
            require(value in allowedRange)
            return CellValue(value)
        }
    }

    fun toCoordinates(): ValidCoordinates = ValidCoordinates(tens(), units())

    private fun units() = value % 10

    private fun tens() = value / 10
}

sealed class Coordinates {
    abstract val row: Int
    abstract val column: Int

    companion object {
        private val allowedRange = (1..5)

        fun of(row: Int, column: Int): Coordinates {
            return if (row in allowedRange && column in allowedRange) {
                ValidCoordinates(row, column)
            } else {
                InvalidCoordinates(row, column)
            }
        }
    }
}

data class ValidCoordinates internal constructor(
    override val row: Int,
    override val column: Int
) : Coordinates()

data class InvalidCoordinates internal constructor(
    override val row: Int,
    override val column: Int
) : Coordinates()
