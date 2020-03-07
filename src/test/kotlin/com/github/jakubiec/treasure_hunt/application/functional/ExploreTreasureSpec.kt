package com.github.jakubiec.treasure_hunt.application.functional

import com.github.jakubiec.treasure_hunt.application.TreasureStorage
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureFound
import com.github.jakubiec.treasure_hunt.domain.functional.TreasureNotFound
import com.github.jakubiec.treasure_hunt.validInputTreasureMap
import io.kotlintest.matchers.types.shouldBeSameInstanceAs
import io.kotlintest.specs.StringSpec
import io.mockk.coEvery
import io.mockk.mockk

class ExploreTreasureSpec : StringSpec() {

    init {

        "should load treasure and explore" {
            val treasureStorage = mockk<TreasureStorage>()
            coEvery { treasureStorage.get() } answers { validInputTreasureMap }

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
            val treasureStorage = mockk<TreasureStorage>()
            coEvery { treasureStorage.get() } answers { validInputTreasureMap }

            val treasure = exploreTreasure(treasureStorage)(3)

            treasure::class shouldBeSameInstanceAs TreasureNotFound::class
        }
    }
}