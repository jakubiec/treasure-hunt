package com.github.jakubiec.treasure_hunt.domain.functional

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap

typealias Explore = (ValidInputTreasureMap, StartingPoint) -> ExplorationResult

private typealias ToTreasureMap = (ValidInputTreasureMap) -> TreasureMap
private typealias ExploreWorkflow = (ToTreasureMap) -> Explore

sealed class ExplorationResult
object TreasureNotFound : ExplorationResult()
data class TreasureFound(val visitedCells: Set<VisitedCell>) : ExplorationResult()

inline class StartingPoint(val cellCoordinates: Coordinates)

val explore: Explore = { inputTreasureMap, startingPoint ->
    exploreWorkflow(toTreasureMap)(inputTreasureMap, startingPoint)
}

private val exploreWorkflow: ExploreWorkflow = { convert ->
    { treasureMap, startingPoint ->
        visit(convert(treasureMap), startingPoint.cellCoordinates)
    }
}

private val toTreasureMap: ToTreasureMap = { inputTreasureMap ->
    inputTreasureMap
        .table
        .mapIndexed { rowIndex, row ->
            row.mapIndexed { columnIndex, value ->
                Cell.of(Coordinates.of(rowIndex+1, columnIndex+1), CellValue.of(value))
            }
        }
        .flatten()
        .toSet()
        .let { cells -> TreasureMap(cells) }
}

private tailrec fun visit(map: TreasureMap, nextCellCoordinates: Coordinates): ExplorationResult =
    when (val cell = map.cellAt(nextCellCoordinates)) {
        is VisitedCell -> TreasureNotFound
        is TreasureCell -> TreasureFound(map.visitedCells + VisitedCell(cell.coordinates, cell.value))
        is NotVisitedCell -> visit(map.visit(nextCellCoordinates), cell.value.toCoordinates())
    }
