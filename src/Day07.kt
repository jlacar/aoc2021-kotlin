import kotlin.math.abs

/**
 * AoC 2021, Day 07: The Treachery of Whales
 * https://adventofcode.com/2021/day/7
 *
 * Align the crabs and escape the giant whale
 */
fun main() {
    /**
     * Dijkstra's shortest path from a single source node
     */
    fun part1(input: List<Int>): Int {
        val frequencies = input.groupingBy { it }.eachCount()
        var shortest = Int.MAX_VALUE
        for (source in frequencies.keys) {
            shortest = frequencies.keys.filter { it != source }
                .map { abs(it - source) * frequencies[it]!! }
                .sum().let { if (it < shortest) it else shortest }
        }
        return shortest
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14")
    check(part1(testInput) == 37)

    val input = toIntList(readInput("Day07").first())
    println(part1(input).also { check(it == 336701) }) // solved
//    println(part2(input))
}
