package com.github.jakubiec.treasure_hunt.domain.object_oriented

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import com.github.jakubiec.treasure_hunt.validInputTreasureMap
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec

class TreasureMapSpec : FeatureSpec() {

    init {

        feature("Treasure map exploration") {

            scenario("should find treasure") {
                val treasure = TreasureMap(validInputTreasureMap).explore()

                treasure shouldBe FoundTreasure(
                    setOf(
                        Cell(1, 1, 55),
                        Cell(5, 5, 15),
                        Cell(1, 5, 21),
                        Cell(2, 1, 44),
                        Cell(4, 4, 32),
                        Cell(3, 2, 13),
                        Cell(1, 3, 25),
                        Cell(2, 5, 43),
                        Cell(4, 3, 43)
                    )
                )
            }

            scenario("should not find treasure") {
                val input = ValidInputTreasureMap(
                    arrayOf(
                        intArrayOf(22, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11)
                    )
                )

                val treasure = TreasureMap(input).explore()

                treasure shouldBe NotFoundTreasure
            }
        }
    }
}