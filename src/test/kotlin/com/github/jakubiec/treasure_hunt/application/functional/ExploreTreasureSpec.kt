package com.github.jakubiec.treasure_hunt.application.functional

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureFound
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureNotFound
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class ExploreTreasureSpec : StringSpec() {

    init {

        "should load treasure and explore" {
            val inputMap = ValidInputTreasureMap(
                arrayOf(
                    intArrayOf(55, 14, 25, 52, 21),
                    intArrayOf(44, 31, 11, 53, 43),
                    intArrayOf(24, 13, 45, 12, 34),
                    intArrayOf(42, 22, 43, 32, 41),
                    intArrayOf(51, 23, 33, 54, 15)
                )
            )
            val treasureStorage = mockk<TreasureStorage>()
            coEvery { treasureStorage.get() } answers { inputMap }

            val exploration = exploreTreasure(treasureStorage)(11)

            exploration::class shouldBeSameInstanceAs TreasureFound::class
        }

        "should not find treasure when input map not store" {
            val treasureStorage = mockk<TreasureStorage>()
            coEvery { treasureStorage.get() } answers { null }

            val treasure = exploreTreasure(treasureStorage)(11)

            treasure::class shouldBeSameInstanceAs TreasureNotFound::class
        }

        "should not find treasure for invalid coordinates" {
            val inputMap = ValidInputTreasureMap(
                arrayOf(
                    intArrayOf(55, 14, 25, 52, 21),
                    intArrayOf(44, 31, 11, 53, 43),
                    intArrayOf(24, 13, 45, 12, 34),
                    intArrayOf(42, 22, 43, 32, 41),
                    intArrayOf(51, 23, 33, 54, 15)
                )
            )
            val treasureStorage = mockk<TreasureStorage>()
            coEvery { treasureStorage.get() } answers { inputMap }

            val treasure = exploreTreasure(treasureStorage)(3)

            treasure::class shouldBeSameInstanceAs TreasureNotFound::class
        }
    }
}