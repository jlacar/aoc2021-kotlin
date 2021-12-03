fun main() {

    /* From https://kalkicode.com/find-1s-complement-of-a-number-in-kotlin */
    fun totalBits(num: Int): Int = (Math.log(num.toDouble()) / Math.log(2.0)).toInt() + 1
    fun onesComplement(num: Int): Int = ((1 shl totalBits(num)) - 1) xor num
    /*----*/

    fun inv(num: Int) = num.toString(2)
        .map { if (it == '1') '0' else '1' }
        .joinToString("").toInt(2)

    fun countBits(input: List<String>): IntArray {
        val bitCounts = IntArray(input.first().length)
        input.forEach { it.forEachIndexed { i, ch -> if (ch == '1') { bitCounts[i] += 1 } } }
        return bitCounts
    }

    fun gamma(input: List<String>): Int {
        val half = input.size / 2
        return countBits(input).map { if (it > half) '1' else '0' }
            .joinToString("").toInt(2)
    }

    fun part1(input: List<String>): Int = gamma(input).let { it * onesComplement(it) } // { it * inv(it) }

    fun countBit(input: List<String>, place: Int): Int = input.count { it[place] == '1' }

    fun bitCriteria(size: Int, count: Int, seq: CharSequence): Char =
        if (count >= size - count) seq.first() else seq.last()

    fun rating(input: List<String>, seq: CharSequence): Int {
        var numbers = input
        var place = 0
        while (numbers.size > 1) {
            val bitCriteria = bitCriteria(numbers.size, countBit(numbers, place), seq)
            numbers = numbers.filter { it[place] == bitCriteria }
            place += 1
        }
        return numbers[0].toInt(2) // .also { println(it) }
    }

    fun o2Rating(input: List<String>) = rating(input, "10")

    fun co2Rating(input: List<String>) = rating(input, "01")

    fun part2(input: List<String>): Int = o2Rating(input) * co2Rating(input)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}
