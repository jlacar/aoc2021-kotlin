import kotlin.math.abs

/**
 * AoC 2021, Day 07: The Treachery of Whales
 * https://adventofcode.com/2021/day/7
 *
 * Align the crabs and escape the giant whale
 *
 * See also: https://coderanch.com/t/747834/AoC-Day-Solutions-Spoilers
 */
fun main() {
    fun distancesFrom(source: Int, nodes: Map<Int, Int>) =
        nodes.keys.filter { it != source }
            .sumOf { abs(it - source) * nodes[it]!! }

    fun gaussSum(n: Int) = n * (n + 1) / 2

    fun gaussDistancesFrom(source: Int, nodes: Map<Int, Int>) =
        nodes.keys.filter { it != source }
            .sumOf { gaussSum(abs(it - source)) * nodes[it]!! }

    fun part1(input: List<Int>): Int {
        val nodes = input.groupingBy { it }.eachCount()
        return (nodes.minOf { it.key } .. nodes.maxOf { it.key })
            .minOf { source -> distancesFrom(source, nodes) }
    }

    fun part2(input: List<Int>): Int {
        val nodes = input.groupingBy { it }.eachCount()
        return (nodes.minOf { it.key } .. nodes.maxOf { it.key })
            .minOf { source -> gaussDistancesFrom(source, nodes) }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = toIntList(readInput("Day07").first())
    println(part1(input).also { check(it == 336701) }) // verified solution
    println(part2(input).also { check(it == 95167302) }) // verified solution
}
