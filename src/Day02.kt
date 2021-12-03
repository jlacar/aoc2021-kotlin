fun commands(input: List<String>) : List<Pair<String, Int>> =
    input.map {
        val (d, x) = it.split(" ")
        Pair(d, x.toInt())
    }

inline fun sumOf(commands: List<Pair<String, Int>>, direction: String) =
    commands.filter { it.first == direction }.sumOf { it.second }

fun main() {
    fun forward(commands: List<Pair<String, Int>>) =
        sumOf(commands, "forward")

    fun depth(commands: List<Pair<String, Int>>) =
        sumOf(commands, "down") - sumOf(commands, "up")

    fun part1(commands: List<Pair<String, Int>>) = forward(commands) * depth(commands)

    fun part2(commands: List<Pair<String, Int>>): Int {
        var forward = 0
        var depth = 0
        var aim = 0
        commands.forEach { (direction, x) ->
            when (direction) {
                "down" -> aim += x
                "up" -> aim -= x
                else -> {
                    forward += x
                    depth += aim * x
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
