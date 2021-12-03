package aoc2015

import readInput

fun main() {
    fun open(s: String): Int = s.count { it == '('}
    fun closed(s: String): Int = s.count { it == ')'}

    fun part1(input: String): Int {
        return open(input) - closed(input)
    }

    fun part2(input: String): Int {
        return input.length
    }

    fun part1Test(data: List<String>, expected: Int): Unit =
        data.forEach { check(part1(it) == expected) }

    // test if implementation meets criteria from the description, like:
    part1Test(listOf("(())", "()()"), 0)
    part1Test(listOf("(((", "(()(()(", "))((((("), 3)
    part1Test(listOf(")))", ")())())"), -3)

    val input = readInput("aoc2015/Day01")
    println(part1(input.first()))
    println(part2(input.first()))
}
