package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import io.micronaut.context.annotation.Factory
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Singleton

private object InMemoryTreasureStorage : TreasureStorage {

    private val mutex = Mutex()
    private var storedTreasureMap: ValidInputTreasureMap? = null

    override suspend fun store(treasureMap: ValidInputTreasureMap) {
        mutex.withLock {
            storedTreasureMap = treasureMap
        }
    }

    override suspend fun get(): ValidInputTreasureMap? = mutex.withLock {
        storedTreasureMap
    }
}

@Factory
internal class TreasureStorageFactory {

    @Singleton
    fun treasureStorage(): TreasureStorage = InMemoryTreasureStorage
}