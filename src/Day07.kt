import kotlin.math.abs
import kotlin.math.min

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
        crabs.keys.filter { it != position }
            .sumOf { costFormula(it, position) * crabs[it]!! }

    fun compounding(n: Int) = n * (n + 1) / 2

    val linearFormula: (Int, Int) -> Int = { crab: Int, position: Int -> distance(crab, position) }
    val compoundFormula: (Int, Int) -> Int = { crab: Int, position: Int -> compounding(distance(crab, position)) }

    fun leastCostLinear(crabs: Map<Int, Int>): Int = cheapestWayToAlign(crabs) { position ->
        costToMove(crabs, position, linearFormula) // .also { println("cost($position) = $it") }
    } // .also { println("min cost = $it") }

    fun pietsWayPart1(crabs: Map<Int, Int>): Int {
        val firstPos = crabs.minOf { it.key }
        val median = crabs.keys.average().toInt()
        var crabsLeft = 0
        var crabsRight = crabs.map { it.value }.sum()
        var cheapest = costToMove(crabs, firstPos, linearFormula)
        var cost = cheapest + crabsRight
        (firstPos..median).forEach { pos ->
            cost += crabsLeft - crabsRight
            cheapest = min(cheapest, cost)
            crabsLeft += crabs.getOrDefault(pos, 0)
            crabsRight -= crabs.getOrDefault(pos, 0)
        }
        return cheapest
    }

    fun leastCostCompounded(crabs: Map<Int, Int>): Int = cheapestWayToAlign(crabs) { position ->
        costToMove(crabs, position, compoundFormula)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14").groupingBy { it }.eachCount()
    leastCostLinear(testInput).also(::println).also { check( it == 34) }
    pietsWayPart1(testInput).also(::println).also { check(it == 37) }

    check(leastCostCompounded(testInput) == 168)

    val crabs = toIntList(readInput("Day07").first()).groupingBy { it }.eachCount()
    pietsWayPart1(crabs).also(::println)
        .also { check(it == 336701) }     // accepted solution, Part 1
    leastCostLinear(crabs).also(::println)
        .also { check(it == 336701) }     // accepted solution, Part 1

    leastCostCompounded(crabs).also(::println)
        .also { check(it == 95167302) }   // accepted solution, Part 2
}
/**
 * 0, 1, 2, 2, 5, 6, 7, 8, 25, 40
 */