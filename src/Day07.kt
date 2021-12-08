import kotlin.math.abs
import kotlin.math.min

/**
 * AoC 2021, Day 07: The Treachery of Whales
 * https://adventofcode.com/2021/day/7
 *
 * Align the crabs and escape the giant whale
 */
fun main() {
    fun distancesFrom(source: Int, nodes: Map<Int, Int>) =
        nodes.keys.filter { it != source }
            .map { abs(it - source) * nodes[it]!! }
            .sum()

    fun part1(input: List<Int>): Int {
        val nodes = input.groupingBy { it }.eachCount()
        var shortest = Int.MAX_VALUE
        for (source in nodes.keys) {
            shortest = min(distancesFrom(source, nodes), shortest)
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
