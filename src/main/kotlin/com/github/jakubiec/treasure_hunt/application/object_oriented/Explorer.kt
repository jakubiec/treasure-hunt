package com.github.jakubiec.treasure_hunt.application.object_oriented

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.InputTreasureMap
import com.github.jakubiec.treasure_hunt.domain.InvalidInputTreasureMap
import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import com.github.jakubiec.treasure_hunt.domain.object_oriented.NotFoundTreasure
import com.github.jakubiec.treasure_hunt.domain.object_oriented.Treasure
import com.github.jakubiec.treasure_hunt.domain.object_oriented.TreasureMap

class Explorer(private val treasureStorage: TreasureStorage) {

    suspend fun explore(input: Array<IntArray>): Treasure =
        when (val map = InputTreasureMap.of(input)) {
            is InvalidInputTreasureMap -> NotFoundTreasure
            is ValidInputTreasureMap -> storeAndExplore(map)
        }

    private suspend fun storeAndExplore(map: ValidInputTreasureMap): Treasure {
        treasureStorage.store(map)
        return TreasureMap(map).explore()
    }

}