package aoc2015

import readInput

/**
 * Santa elevator - Santa takes elevator that goes up a floor
 * on '(' and down a floor on ')'. Building starts at floor 0
 * and is tall and deep. Can't get to top or reach bottom.
 */
fun main() {

    /**
     * Calculate which floor Santa ends up on after all commands
     * have been executed. Starts at floor 0.
     *
     * Implementation note:
     * Assuming only '(' and ')' then:
     *    open = count('(')
     *    closed = commands.length - open
     *    open - closed
     *      = open - (commands.length - open)
     *      = open - commands.length + open
     *      = 2 * open - commands.length
     */
    fun part1(commands: String): Int {
        val open = commands.count { it == '(' }
        return 2 * open - commands.length
    }

    /**
     * Calculate when Santa first moves to basement (floor -1).
     * Return the position of the command that moves him into
     * the basement. Position is 1-based, starts at floor 0.
     */
    fun part2(commands: String): Int {
        var floor = 0
        var pos = 0
        val hasNotMovedIntoBasement: (Char) -> Boolean = {
            when (it) {
                '(' -> floor += 1
                ')' -> floor -= 1
            }
            pos += 1
            floor != -1
        }
        commands.takeWhile { hasNotMovedIntoBasement(it) }
        return pos
    }

    fun part1Test(data: List<String>, expected: Int): Unit =
        data.forEach { check(part1(it) == expected) }

    // test if implementation meets criteria from the description, like:
    part1Test(listOf("(())", "()()"), 0)
    part1Test(listOf("(((", "(()(()(", "))((((("), 3)
    part1Test(listOf(")))", ")())())"), -3)

    check(part2(")") == 1)
    check(part2("()())") == 5)

    val input = readInput("aoc2015/Day01")
    println(part1(input.first()))  // 280
    println(part2(input.first()))  // 1797
}
