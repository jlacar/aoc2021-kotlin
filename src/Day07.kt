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

    fun distanceTo(position: Int, crabs: Map<Int, Int>, fuelUsed: (Int) -> Int): Int =
        crabs.keys.filter { it != position }.sumOf(fuelUsed)

    fun cheapestAlignmentOf(nodes: Map<Int, Int>, fuelUsed: (Int) -> Int): Int =
        (nodes.minOf { it.key } .. nodes.maxOf { it.key }).minOf(fuelUsed)

    fun part1(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs)
        { position -> distanceTo(position, crabs) { abs(it - position) * crabs[it]!! } }

    fun part2(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs)
        { position -> distanceTo(position, crabs) { gaussSum(abs(it - position)) * crabs[it]!! } }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val crabs = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    println(part1(crabs).also { check(it == 336701) }) // verified solution
    println(part2(crabs).also { check(it == 95167302) }) // verified solution
}
