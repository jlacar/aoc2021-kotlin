fun mapToPairs(input: List<String>) : List<Pair<String, Int>> =
    input.map {
        val (d, x) = it.split(" ")
        Pair(d, x.toInt())
    }

fun sumOf(values: List<Pair<String, Int>>, key: String) =
    values.filter { it.first == key }
          .map { it.second }
          .sum()

fun main() {
    fun part1(input: List<Pair<String, Int>>): Int {
        val acc = mapOf("forward" to sumOf(input, "forward"),
            "down" to sumOf(input, "down"),
            "up" to sumOf(input, "up"))
        return acc["forward"]!!.times(acc["down"]!!.minus(acc["up"]!!))
    }

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
