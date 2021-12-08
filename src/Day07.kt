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

    fun allPossiblePositionsOf(crabs: Map<Int, Int>) = (crabs.minOf { it.key }..crabs.maxOf { it.key })

    fun cheapestWayToAlign(crabs: Map<Int, Int>, costCalculation: (Int) -> Int): Int =
        allPossiblePositionsOf(crabs).minOf(costCalculation)

    fun distance(crab: Int, toPosition: Int) = abs(crab - toPosition)

    /**
     * Calculates the cost to move all the given crabs to the given position
     * using the given formula to calculate the fuel cost.
     *
     * @param crabs Map where each key is a position and the value is the
     *              number of crabs in that position
     * @param position the position at which to align all the crabs
     * @param costFormula the formula used to calculate the amount of fuel
     *              needed to move a crab to the given position
     */
    fun costToMove(crabs: Map<Int, Int>, position: Int, costFormula: (Int, Int) -> Int): Int =
        crabs.keys.filter { it != position }.sumOf { costFormula(it, position) * crabs[it]!! }

    fun compounding(n: Int) = n * (n + 1) / 2

    val linearFormula: (Int, Int) -> Int = { crab: Int, position: Int -> distance(crab, position) }
    val compoundFormula: (Int, Int) -> Int = { crab: Int, position: Int -> compounding(distance(crab, position)) }

    fun leastCostLinear(crabs: Map<Int, Int>): Int = cheapestWayToAlign(crabs) { position ->
        costToMove(crabs, position, linearFormula)
    }

    fun leastCostCompounded(crabs: Map<Int, Int>): Int = cheapestWayToAlign(crabs) { position ->
        costToMove(crabs, position, compoundFormula)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    check(leastCostLinear(testInput) == 37)
    check(leastCostCompounded(testInput) == 168)

    val crabs = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    println(leastCostLinear(crabs).also { check(it == 336701) })       // accepted solution, Part 1
    println(leastCostCompounded(crabs).also { check(it == 95167302) })   // accepted solution, Part 2
}