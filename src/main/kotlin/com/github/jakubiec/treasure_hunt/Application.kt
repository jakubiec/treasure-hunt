package com.github.jakubiec.treasure_hunt

import com.github.jakubiec.treasure_hunt.adapters.ConsoleAdapter
import com.github.jakubiec.treasure_hunt.adapters.RestAdapter
import com.github.jakubiec.treasure_hunt.adapters.TreasureStorageFactory
import com.github.jakubiec.treasure_hunt.application.object_oriented.Explorer
import io.micronaut.runtime.Micronaut
import kotlinx.coroutines.*

fun main() = runBlocking {
    launchConsoleAdapter()
    launchRestAdapter()
}

private fun CoroutineScope.launchConsoleAdapter() {
    val explorer = Explorer(TreasureStorageFactory().treasureStorage())
    val consoleAdapter = ConsoleAdapter(explorer)

    launch(Dispatchers.IO) {
        while (isActive) {
            consoleAdapter.exploreInput()
        }
    }
}

private fun CoroutineScope.launchRestAdapter() {
    launch {
        Micronaut.run(RestAdapter::class.java)
    }
}

