/* See also https://youtu.be/4A2WwniJdNc */

data class Command(val direction: String, val amount: Int)

fun commands(input: List<String>) : List<Command> =
    input.map { it.split(" ") }.map { Command(it[0], it[1].toInt()) }

inline fun sumOf(commands: List<Command>, direction: String) =
    commands.filter { it.direction == direction }.sumOf { it.amount }

fun main() {
    fun forward(commands: List<Command>) =
        sumOf(commands, "forward")

    fun depth(commands: List<Command>) =
        sumOf(commands, "down") - sumOf(commands, "up")

    fun part1(commands: List<Command>) = forward(commands) * depth(commands)

    fun part2(commands: List<Command>): Int {
        var forward = 0
        var depth = 0
        var aim = 0
        commands.forEach {
            when (it.direction) {
                "down" -> aim += it.amount
                "up" -> aim -= it.amount
                else -> {
                    forward += it.amount
                    depth += aim * it.amount
                }
            }
        }
        return forward * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = commands(readInput("Day02_test"))
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val commands = commands(readInput("Day02"))
    println(part1(commands))
    println(part2(commands))
}
