package com.github.jakubiec.treasure_hunt.domain.functional

import com.github.jakubiec.treasure_hunt.domain.ValidInputTreasureMap
import io.kotlintest.shouldBe
import io.kotlintest.specs.FeatureSpec

class ExplorationSpec : FeatureSpec() {

    init {

        feature("Treasure map exploration") {

            scenario("should find treasure") {
                val treasureMap = ValidInputTreasureMap(
                    arrayOf(
                        intArrayOf(55, 14, 25, 52, 21),
                        intArrayOf(44, 31, 11, 53, 43),
                        intArrayOf(24, 13, 45, 12, 34),
                        intArrayOf(42, 22, 43, 32, 41),
                        intArrayOf(51, 23, 33, 54, 15)
                    )
                )

                val startingPoint = StartingPoint(ValidCoordinates(1, 1))

                val explorationResult = explore(treasureMap, startingPoint)

                explorationResult shouldBe TreasureFound(
                    setOf(
                        VisitedCell(ValidCoordinates(1, 1), CellValue.of(55)),
                        VisitedCell(ValidCoordinates(5, 5), CellValue.of(15)),
                        VisitedCell(ValidCoordinates(1, 5), CellValue.of(21)),
                        VisitedCell(ValidCoordinates(2, 1), CellValue.of(44)),
                        VisitedCell(ValidCoordinates(4, 4), CellValue.of(32)),
                        VisitedCell(ValidCoordinates(3, 2), CellValue.of(13)),
                        VisitedCell(ValidCoordinates(1, 3), CellValue.of(25)),
                        VisitedCell(ValidCoordinates(2, 5), CellValue.of(43)),
                        VisitedCell(ValidCoordinates(4, 3), CellValue.of(43))
                    )
                )
            }

            scenario("should not find treasure") {
                val treasureMap = ValidInputTreasureMap(
                    arrayOf(
                        intArrayOf(22, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11),
                        intArrayOf(11, 11, 11, 11, 11)
                    )
                )

                val startingPoint = StartingPoint(ValidCoordinates(1, 1))

                val explorationResult = explore(treasureMap, startingPoint)

                explorationResult shouldBe TreasureNotFound
            }

        }
    }
}