/**
 * Giant Squid Bingo
 *
 * 5x5 grids, only complete rows and columns win, no diagonals.
 */
fun main() {
    /**
     * Final score is sum of unmarked numbers * winning number called
     */
    fun part1(input: List<String>): Int {
        println(input.first())
        println(input.slice(2..input.lastIndex).chunked(6))
        return 0
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

class Board {
    val numbers: Set<Array<Int>> = mutableSetOf()
}