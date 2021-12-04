package aoc2015

import readInput

fun main() {
    fun open(s: String): Int = s.count { it == '('}
    fun closed(s: String): Int = s.count { it == ')'}

    fun part1(commands: String): Int {
        return open(commands) - closed(commands)
    }

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
