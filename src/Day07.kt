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

    fun compounding(n: Int) = n * (n + 1) / 2

    fun costToAlignAll(crabs: Map<Int, Int>, position: Int, fuelUsed: (Int) -> Int): Int =
        crabs.keys.filter { it != position }.sumOf(fuelUsed)

    fun allPossiblePositionsOf(crabs: Map<Int, Int>) = (crabs.minOf { it.key }..crabs.maxOf { it.key })

    fun cheapestAlignmentOf(crabs: Map<Int, Int>, costToAlign: (Int) -> Int): Int =
        allPossiblePositionsOf(crabs).minOf(costToAlign)

    fun fuelToMove(crab: Int, toPosition: Int) = abs(crab - toPosition)

    fun linearCost(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs)
        { position -> costToAlignAll(crabs, position) { fuelToMove(it, position) * crabs[it]!! } }

    fun compoundCost(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs)
        { position -> costToAlignAll(crabs, position) { compounding(fuelToMove(it, position)) * crabs[it]!! } }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    check(linearCost(testInput) == 37)
    check(compoundCost(testInput) == 168)

    val crabs = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    println(linearCost(crabs).also { check(it == 336701) })         // accepted solution, Part 1
    println(compoundCost(crabs).also { check(it == 95167302) })   // accepted solution, Part 2
}
