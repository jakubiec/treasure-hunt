package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

object InMemoryTreasureStorage : TreasureStorage {

    private val mutex = Mutex()
    private lateinit var storedTreasureMap: ValidInputTreasureMap

    override suspend fun store(treasureMap: ValidInputTreasureMap) {
        mutex.withLock {
            storedTreasureMap = treasureMap
        }
    }

    override suspend fun get(): ValidInputTreasureMap = mutex.withLock {
        storedTreasureMap
    }
}