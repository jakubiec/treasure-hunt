package com.github.jakubiec.treasure_hunt.application.object_oriented

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.object_oriented.FoundTreasure
import com.github.jakubiec.treasure_hunt.domain.object_oriented.NotFoundTreasure
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.specs.StringSpec
import io.mockk.coVerify
import io.mockk.mockk

class ExplorerSpec : StringSpec() {

    init {

        "should store and find treasure" {
            val validInput = arrayOf(
                intArrayOf(55, 14, 25, 52, 21),
                intArrayOf(44, 31, 11, 53, 43),
                intArrayOf(24, 13, 45, 12, 34),
                intArrayOf(42, 22, 43, 32, 41),
                intArrayOf(51, 23, 33, 54, 15)
            )

            val treasureStorage = mockk<TreasureStorage>(relaxed = true)
            val explorer = Explorer(treasureStorage)

            val treasure = explorer.explore(validInput)

            treasure::class shouldBeSameInstanceAs FoundTreasure::class

            coVerify(exactly = 1) { treasureStorage.store(any()) }
        }

        "should not store and return not found treasure" {
            val invalidInput = emptyArray<IntArray>()

            val treasureStorage = mockk<TreasureStorage>(relaxed = true)
            val explorer = Explorer(treasureStorage)

            val treasure = explorer.explore(invalidInput)

            treasure::class shouldBeSameInstanceAs NotFoundTreasure::class

            coVerify(exactly = 0) { treasureStorage.store(any()) }
        }

    }

}