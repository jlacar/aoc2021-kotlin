/* See also: https://coderanch.com/t/747456/AoC-Day-Solutions-Spoilers for other solutions */

/* Refactored to leverage power of Kotlin */

fun valuesFrom(input: List<String>) = input.map { it.toInt() }

fun timesIncreased(values: List<Int>): Int =
    values.windowed(size = 2).count { it[0] < it[1] }

fun slidingWindowSums(values: List<Int>): List<Int> =
    values.windowed(size = 3).map { it.sum() }

/* BRUTE FORCE methods */

fun timesIncreasedBrute(nums: List<Int>): Int {
    var last = 0
    var count = 0
    nums.forEach {
        if (it > last) {
            count += 1
        }
        last = it
    }
    return count - 1
}

fun slidingWindowSumsBrute(input: List<String>): MutableList<Int> {
    val sums = mutableListOf<Int>()
    val triplet = mutableListOf<Int>()
    valuesFrom(input).forEach {
        triplet.add(it)
        if (triplet.size == 3) {
            sums.add(triplet.sum())
            triplet.removeAt(0)
        }
    }
    return sums
}

fun main() {
    /**
     * Counts the number of times a measurement increases from the previous measurement
     */
    fun part1(input: List<String>): Int = timesIncreased(valuesFrom(input))

    /**
     * Counts the number of times a three-measurement sliding window increases from the
     * previous window; stop when not enough measurements to form a triplet
     */
    fun part2(input: List<String>): Int {
        val values = valuesFrom(input)
        return (3..values.lastIndex).count { values[it] > values[it - 3] }
    }
    // timesIncreased(slidingWindowSums(valuesFrom(input)))

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
