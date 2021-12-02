fun mapToPairs(input: List<String>) : List<Pair<String, Int>> =
    input.map {
        val (d, x) = it.split(" ")
        Pair(d, x.toInt())
    }

inline fun sumOf(values: List<Pair<String, Int>>, key: String) =
    values.filter { it.first == key }
          .sumOf { it.second }

fun main() {
    fun forward(input: List<Pair<String, Int>>) =
        sumOf(input, "forward")

    fun depth(input: List<Pair<String, Int>>) =
        sumOf(input, "down") - sumOf(input, "up")

    fun part1(input: List<Pair<String, Int>>) = forward(input) * depth(input)

    fun part2(input: List<Pair<String, Int>>): Int {
        var forward = 0
        var depth = 0
        var aim = 0
        input.forEach {
            when (it.first) {
                "down" -> aim += it.second
                "up" -> aim -= it.second
                else -> {
                    forward += it.second
                    depth += aim * it.second
                }
            }
        }
        return forward * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = mapToPairs(readInput("Day02_test"))
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = mapToPairs(readInput("Day02"))
    println(part1(input))
    println(part2(input))
}
