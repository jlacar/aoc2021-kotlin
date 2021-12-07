/**
 * Additional challenges related to Day 05
 */
fun main() {
    val arrows = "\u2190\u2192\u2191\u2193\u2196\u2197\u2199\u2198"

    /* Return a string that represents the given line segments */
    fun part1(input: List<String>) {
        toLineSegments(input).forEach { println(it) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    part1(listOf(
        "0,9 -> 5,9",
        "9,4 -> 3,4",
        "7,0 -> 7,4",
        "2,2 -> 2,1",
        "8,0 -> 0,8",
        "0,0 -> 8,8",
        "6,4 -> 2,0",
        "5,5 -> 8,2"
    ))
}

/*
   Day 05 Extra Challenges

   Part 1 - interpret the input as directed line segments.
 */