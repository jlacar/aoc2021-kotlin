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

    fun differentWayPart1(input: List<Int>, days: Int = 80): Long {
        val counts = LongArray(9) { 0L }
        input.forEach { counts.set(it, counts.get(it) + 1) }
        repeat(days) {
            val newFish = counts[0]
            for (i in 0..7) {
                counts[i] = counts[i+1]
            }
            counts[6] += newFish
            counts[8] = newFish
        }
        return counts.sum()
    }

    fun part1(input: List<Int>, days: Int = 80): Long =
//        bruteForcePart1(input, days)
        differentWayPart1(input)

    fun part2(input: List<Int>): Long {
        return differentWayPart1(input, 256)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList(readInput("Day06_test").first())
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539)

    val input = toIntList(readInput("Day06").first())
    println(part1(input).also { check(it == 345793L) })
    println(part2(input).also { check(it == 1572643095893L)})
}