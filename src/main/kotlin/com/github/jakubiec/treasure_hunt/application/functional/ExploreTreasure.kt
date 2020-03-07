package com.github.jakubiec.treasure_hunt.application.functional

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.functional.*


typealias ExploreTreasure = suspend (Int) -> ExplorationResult
typealias ExploreTreasureWorkflow = (TreasureStorage) -> ExploreTreasure

val exploreTreasure: ExploreTreasureWorkflow = { treasureStorage ->
    { coordinatesInput ->
        when (val coordinates = Coordinates.of(coordinatesInput)) {
            is ValidCoordinates -> loadMapAndExplore(treasureStorage, coordinates)
            is InvalidCoordinates -> TreasureNotFound
        }
    }
}

private suspend fun loadMapAndExplore(
    treasureStorage: TreasureStorage,
    coordinates: ValidCoordinates
) = treasureStorage.get()
    ?.let { map -> explore(map, StartingPoint(coordinates)) }
    ?: TreasureNotFound
