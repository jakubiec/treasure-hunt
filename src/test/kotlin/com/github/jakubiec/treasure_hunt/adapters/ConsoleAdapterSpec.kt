package com.github.jakubiec.treasure_hunt.adapters

import com.github.jakubiec.treasure_hunt.application.object_oriented.Explorer
import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@ExperimentalStdlibApi
class ConsoleAdapterSpec : StringSpec() {

    init {

        "should print path to treasure from standard input" {
            val input = """
                55 14 25 52 21
                44 31 11 53 43
                24 13 45 12 34
                42 22 43 32 41
                51 23 33 54 15
            """.trimIndent()

            System.setIn((ByteArrayInputStream(input.encodeToByteArray())))

            val out = ByteArrayOutputStream()
            System.setOut(PrintStream(out))

            consoleAdapter.exploreInput()

            val expectedOutput = """
               1 1
               5 5
               1 5
               2 1
               4 4
               3 2
               1 3
               2 5
               4 3
               
               """.trimIndent()

            out.toString() shouldBe expectedOutput
        }

        "should print no treasure from standard input" {
            val input = """
                22 11 11 11 11
                11 11 11 11 11
                11 11 11 11 11
                11 11 11 11 11
                11 11 11 11 11
            """.trimIndent()

            System.setIn((ByteArrayInputStream(input.encodeToByteArray())))

            val out = ByteArrayOutputStream()
            System.setOut(PrintStream(out))

            consoleAdapter.exploreInput()

            out.toString() shouldBe "NO TREASURE"
        }
    }

    private val explorer = Explorer(TreasureStorageFactory().treasureStorage())
    private val consoleAdapter = ConsoleAdapter(explorer)
}
