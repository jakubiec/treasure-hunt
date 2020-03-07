package com.github.jakubiec.treasure_hunt.domain

import com.github.jakubiec.treasure_hunt.validInput
import io.kotlintest.data.forall
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import io.kotlintest.tables.row

class InputTreasureMapSpec : StringSpec() {

    init {

        "should create valid input treasure map" {
            val inputTreasureMap = InputTreasureMap.of(validInput)

            inputTreasureMap shouldBe ValidInputTreasureMap(validInput)
        }

        forall(
            row(
                arrayOf(
                    intArrayOf(55, 14, 25, 52, 21),
                    intArrayOf(44, 31, 11, 53, 43),
                    intArrayOf(51, 23, 33, 54, 15)
                ),
                "wrong row size"
            ),
            row(
                arrayOf(
                    intArrayOf(55, 14, 25, 52),
                    intArrayOf(44, 31, 11, 53, 43),
                    intArrayOf(24, 13, 45, 12, 34),
                    intArrayOf(42, 22, 43, 32, 41),
                    intArrayOf(51, 23, 33, 54, 15)
                ),
                "wrong column size"
            ),
            row(
                arrayOf(
                    intArrayOf(1, 14, 25, 52, 21),
                    intArrayOf(44, 31, 11, 53, 43),
                    intArrayOf(24, 13, 45, 12, 34),
                    intArrayOf(42, 22, 43, 32, 41),
                    intArrayOf(51, 23, 33, 54, 15)
                ),
                "wrong cell value"
            )
        ) { input, case ->
            "should create invalid treasure map for $case" {
                InputTreasureMap.of(input) shouldBe InvalidInputTreasureMap(input)
            }
        }
    }

}