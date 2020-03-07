package com.github.jakubiec.treasure_hunt

import com.github.jakubiec.treasure_hunt.adapters.ConsoleAdapter
import com.github.jakubiec.treasure_hunt.adapters.InMemoryTreasureStorage
import com.github.jakubiec.treasure_hunt.application.object_oriented.Explorer
import kotlinx.coroutines.*

fun main() = runBlocking {
    launchConsoleAdapter()
}


private fun CoroutineScope.launchConsoleAdapter() {
    val explorer = Explorer(InMemoryTreasureStorage)
    val consoleAdapter = ConsoleAdapter(explorer)

    launch(Dispatchers.IO) {
        while (isActive) {
            consoleAdapter.exploreInput()
        }
    }
}

