/**
 * Lanternfish
 *
 * https://adventofcode.com/2021/day/6
 */
fun main() {
    fun naiveSolution(input: List<Int>, days: Int = 80): Long {
        var school = mutableListOf<Int>().also { it.addAll(input) }
        repeat(days) {
            val newFish = school.count { it == 0 }
            school.replaceAll { timer ->
                when (timer) {
                    in 1..8 -> timer - 1
                    0 -> 6
                    else -> timer
                }
            }
            school.addAll(List(newFish) { 8 })
        }
        return school.size.toLong()
    }

    fun moreEfficientSolution(input: List<Int>, days: Int = 80): Long {
        val timers = LongArray(9) { 0L }
        input.forEach { timers.set(it, timers.get(it) + 1) }
        repeat(days) {
            val newFish = timers[0]
            for (i in 0..7) {
                timers[i] = timers[i+1]
            }
            timers[6] += newFish
            timers[8] = newFish
        }
        return timers.sum()
    }

    fun part1(input: List<Int>): Long =
//        naiveSolution(input)
        moreEfficientSolution(input)

    fun part2(input: List<Int>): Long = moreEfficientSolution(input, 256)

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList(readInput("Day06_test").first())
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = toIntList(readInput("Day06").first())
    println(part1(input).also { check(it == 345793L) }) // solved
    println(part2(input).also { check(it == 1572643095893L)}) // solved
}