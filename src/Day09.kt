import java.lang.Integer.max
import java.lang.Integer.min

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    /* Scaffolding */

    fun result(input: List<String>) = input.map { it.toList().map { it.digitToInt() } }

    /* Tests */

    val testInput = readInput("Day09_test")

    fun lowestNeighbor(map: List<List<Int>>, row: Int, col: Int): List<Int> {
        val maxRow = map.size
        val maxCol = map.first().size
        var min = map[row][col]
        var neighbors = buildList() {
            if (row-1 >= 0) add(map[row-1][col])
            if (row+1 < maxRow) add(map[row+1][col])
            if (col-1 >= 0) add(map[row][col-1])
            if (col+1 < maxCol) add(map[row][col+1])
        }
        return listOf(0) // min(map[row][col], neighbors.minOf {it})
    }

    val map = result(testInput).also(::println)
    val heights = Pair(map.size, map.first().size)
    val lowPoints = mutableListOf<Int>()

    part1(testInput).also { println("Part 1 (test): $it")}
        .also(assertAnswerIs(15))

//    part2(testInput).also { println("Part 2 (test): $it")}
//        .also(assertAnswerIs(?))

    val input = readInput("Day09")

    /* Go for gold! */

    part1(input).also { println("Part 1 (final): $it")}
        // .also(assertAnswerIs(?))
        // .also(earnedGoldStar)

    part2(input).also { println("Part 2 (final): $it")}
        // .also(assertAnswerIs(?))
        // .also(earnedGoldStar)
}
