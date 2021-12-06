/**
 * Lanternfish
 *
 * https://adventofcode.com/2021/day/6
 */
fun main() {
    fun part1(input: List<Int>): Int {
        var school = mutableListOf<Int>().also { it.addAll(input) }
        for (day in 1..80) {
            val newFish = school.count { it == 0 }
            school.replaceAll { timer -> when(timer) {
                in 1..8 -> timer - 1
                0 -> 6
                else -> timer
            } }
            school.addAll(List(newFish) {8})
        }
        return school.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList(readInput("Day06_test").first())
    check(part1(testInput) == 5934)

    val input = toIntList(readInput("Day06").first())
    println(part1(input).also { check(it == 345793) })
    println(part2(input))
}
