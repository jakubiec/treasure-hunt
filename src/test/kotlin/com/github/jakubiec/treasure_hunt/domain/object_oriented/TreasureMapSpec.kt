package com.github.jakubiec.treasure_hunt.domain.object_oriented

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import com.github.jakubiec.treasure_hunt.domain.object_oriented.TreasureMap.Cell
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.matchers.collections.shouldContainExactly
import io.kotlintest.specs.FeatureSpec

class TreasureMapSpec : FeatureSpec() {

    init {

        feature("Treasure map exploration") {

            scenario("should find treasure") {
                val input = ValidInputTreasureMap(
                    arrayOf(
                        intArrayOf(55, 14, 25, 52, 21),
                        intArrayOf(44, 31, 11, 53, 43),
                        intArrayOf(24, 13, 45, 12, 34),
                        intArrayOf(42, 22, 43, 32, 41),
                        intArrayOf(51, 23, 33, 54, 15)
                    )
                )

                val cells = TreasureMap(input).explore()

                cells shouldContainExactly setOf(
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

                val cells = TreasureMap(input).explore()

                cells.shouldBeEmpty()
            }
        }
    }
}