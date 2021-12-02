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

fun countIncreases(nums: List<Int>, distance: Int): Int =
    (0..nums.lastIndex - distance).count { nums[it] < nums[it + distance] }

fun countBigger(nums: List<Int>, sampleSize: Int): Int =
    nums.windowed(size = sampleSize).count { it.first() < it.last() }

fun main() {
    /**
     * Counts the number of times a measurement increases from the previous measurement
     */
    fun part1(input: List<Int>): Int = timesIncreased(input)

    /**
     * Counts the number of times a three-measurement sliding window increases from the
     * previous window; stop when not enough measurements to form a triplet
     */
    fun part2(input: List<Int>): Int = timesIncreased(slidingWindowSums(input))

    /* mike simmons' solution from CodeRanch */
    /*
    {
    val values = valuesFrom(input)
    return (3..values.lastIndex).count { values[it] > values[it - 3] }
    }
    */

    // test if implementation meets criteria from the description, like:
    val testInput = valuesFrom(readInput("Day01_test"))
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = valuesFrom(readInput("Day01"))
    println(part1(input))
    println(part2(input))

    println(countIncreases(input, 1))
    println(countIncreases(input, 3))

    println(countBigger(input, 2))
    println(countBigger(input, 4))
}
