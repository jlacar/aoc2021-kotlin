fun main() {
    fun part1(input: List<Int>): Int {
        return input.size
    }

    fun part2(input: List<Int>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = toIntList("16,1,2,0,4,2,7,1,2,14")
    check(part1(testInput) == 37)

    val input = toIntList(readInput("DayXX").first())
    println(part1(input))
    println(part2(input))
}
