fun main() {
    fun inv(num: Int): Int =
        num.toString(2)
            .map { if (it == '1') '0' else '1' }
            .joinToString("")
            .toInt(2)

    fun countBits(input: List<String>): IntArray {
        val bitCounts = IntArray(input.first().length)
        input.forEach {
            it.forEachIndexed { i, ch ->
                if (ch == '1') {
                    bitCounts[i] += 1
                }
            }
        }
        return bitCounts
    }

    fun part1(input: List<String>): Int {
        val half = input.size / 2
        val gamma = countBits(input).map {
            if (it > half) '1' else '0'
        }.joinToString("").toInt(2)

        return gamma * inv(gamma)
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
