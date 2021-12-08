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

    fun gaussSum(n: Int) = n * (n + 1) / 2

    fun distanceTo(source: Int, nodes: Map<Int, Int>, calc: (Int) -> Int): Int =
        nodes.keys.filter { it != source }.sumOf(calc)

    fun solve(nodes: Map<Int, Int>, shortestPath: (Int) -> Int): Int =
        (nodes.minOf { it.key } .. nodes.maxOf { it.key }).minOf(shortestPath)

    fun part1(nodes: Map<Int, Int>): Int = solve(nodes) { source ->
        distanceTo(source, nodes) { abs(it - source) * nodes[it]!! }
    }

    fun part2(nodes: Map<Int, Int>): Int = solve(nodes) { source ->
        distanceTo(source, nodes) { gaussSum(abs(it - source)) * nodes[it]!! }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val nodes = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    println(part1(nodes).also { check(it == 336701) }) // verified solution
    println(part2(nodes).also { check(it == 95167302) }) // verified solution
}
