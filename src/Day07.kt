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

    fun costToAlignAll2(crabs: Map<Int, Int>, position: Int, fuelUsed: (Int, Int) -> Int): Int =
        crabs.keys.filter { it != position }.sumOf { fuelUsed(it, position) * crabs[it]!! }

    fun allPossiblePositionsOf(crabs: Map<Int, Int>) = (crabs.minOf { it.key }..crabs.maxOf { it.key })

    fun cheapestAlignmentOf(crabs: Map<Int, Int>, costToAlign: (Int) -> Int): Int =
        allPossiblePositionsOf(crabs).minOf(costToAlign)

    fun fuelToMove(crab: Int, toPosition: Int) = abs(crab - toPosition)

    val linearCostFormula: (Int, Int) -> Int = { crab: Int, position: Int -> fuelToMove(crab, position) }

    val compoundCostFormula: (Int, Int) -> Int = { crab: Int, position: Int -> compounding(fuelToMove(crab, position)) }

    fun part1(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs) { position ->
        costToAlignAll2(crabs, position, linearCostFormula) }

    fun part2(crabs: Map<Int, Int>): Int = cheapestAlignmentOf(crabs) { position ->
        costToAlignAll2(crabs, position, compoundCostFormula) }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val crabs = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    println(part1(crabs).also { check(it == 336701) })         // accepted solution, Part 1
    println(part2(crabs).also { check(it == 95167302) })   // accepted solution, Part 2
}
