/**
 * Lanternfish
 *
 * https://adventofcode.com/2021/day/6
 */
fun main() {
    fun bruteForcePart1(input: List<Int>, days: Int): Long {
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

    fun differentWayPart1(input: List<Int>): Long {
        val counts = LongArray(9) { 1L }
        // println("""${counts.joinToString(",", "[", "]")} == ${counts.sum()}""")
        for (day in 1..80) {}
        return counts.sum()
    }

    fun part1(input: List<Int>, days: Int = 80): Long =
        bruteForcePart1(input, days)
//        differentWayPart1(input)

    fun part2(input: List<Int>): Long {
        return part1(input, 180)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList(readInput("Day06_test").first())
    check(part1(testInput) == 5934L)
//    check(part2(testInput) == 26984457539)

    val input = toIntList(readInput("Day06").first())
    println(part1(input).also { check(it == 345793L) })
//    println(part2(input))
}