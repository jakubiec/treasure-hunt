package com.github.jakubiec.treasure_hunt.application

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap

interface TreasureStorage {

    suspend fun store(treasureMap: ValidInputTreasureMap)

    suspend fun get(): ValidInputTreasureMap?
}