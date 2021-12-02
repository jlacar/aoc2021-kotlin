fun mapToPairs(input: List<String>) : List<Pair<String, Int>> =
    input.map {
        val (d, x) = it.split(" ")
        Pair(d, x.toInt())
    }

inline fun sumOf(values: List<Pair<String, Int>>, key: String) =
    values.filter { it.first == key }
          .sumOf { it.second }

fun main() {
    fun part1(input: List<Pair<String, Int>>) =
        sumOf(input, "forward") * (sumOf(input, "down") - sumOf(input, "up"))

    fun part2(input: List<Pair<String, Int>>): Int {
        var depth = 0
        var aim = 0
        var pos = 0
        input.forEach {
            when (it.first) {
                "down" -> aim += it.second
                "up" -> aim -= it.second
                else -> {
                    pos += it.second
                    depth += aim * it.second
                }
            }
        }
        return pos * depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = mapToPairs(readInput("Day02_test"))
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = mapToPairs(readInput("Day02"))
    println(part1(input))
    println(part2(input))
}
