package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.application.object_oriented.Explorer
import com.github.jakubiec.treasure_hunt.domain.object_oriented.FoundTreasure
import com.github.jakubiec.treasure_hunt.domain.object_oriented.NotFoundTreasure


class ConsoleAdapter(private val explorer: Explorer) {

    private val size = 5

    suspend fun exploreInput() {
        runCatching { explore() }
            .onFailure { error -> System.err.println("Sth very bad happened: ${error.localizedMessage}") }
    }

    private suspend fun explore() {
        val table = readIntArray2d()
        when (val result = explorer.explore(table)) {
            is NotFoundTreasure -> printNoTreasure()
            is FoundTreasure -> printPathToTreasure(result)
        }
    }

    private fun printNoTreasure() {
        print("NO TREASURE")
    }

    private fun printPathToTreasure(treasure: FoundTreasure) {
        treasure
            .visitedCells
            .forEach { cell -> println("${cell.row} ${cell.column}") }
    }

    private fun line() = readLine()!!.trimStart()
    private fun stringLine() = line().split(' ')
    private fun readIntArray() = stringLine().run { IntArray(size) { get(it).toInt() } }
    private fun readIntArray2d() = Array(size) { readIntArray().also { require(it.size == size) } }
}