/**
 * Day 8: Seven Segment Search
 */
fun main() {

    val lengthsOfDigits1478 = listOf(2, 4, 3, 7)

    /* signals with size == 5 : (2, 3, 5) */

    fun find2(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && (decoder[4] subtract it).size == 2 }

    fun find3(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && (decoder[4] - decoder[2] - it).size == 1 }

    fun find5(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && it !in decoder.slice(setOf(2,3)) }

    /* signals with size == 6 : (6, 9, 0) */

    fun find6(signals: List<Set<Char>>, decoder: List<Set<Char>>): Set<Char> = signals
        .first { it.size == 6 && (it subtract decoder[7]).size == 4 }

    fun find9(signals: List<Set<Char>>, decoder: List<Set<Char>>): Set<Char> = signals
        .first { it.size == 6 && (it - (decoder[4] union decoder[7])).size == 1 }

    fun find0(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 6 && it !in decoder.slice(setOf(6,9)) }

    /* other stuff */

    fun signalPatternsIn(entry: String) = entry
            .split(" | ").first().split(" ")

    fun outputValuesIn(entry: String) = entry
            .split(" | ").last().split(" ")

    fun decoderFor(signals: List<Set<Char>>): List<Set<Char>> =
        buildList<Set<Char>>(10) {
            // placeholders: each digit maps to corresponding index
            repeat(10) { add(emptySet()) }
            // digits with unique lengths
            set(1, signals.first { it.size == 2 })
            set(4, signals.first { it.size == 4 })
            set(7, signals.first { it.size == 3 })
            set(8, signals.first { it.size == 7 })
            // deduce others: order of these calls matters!
            set(2, find2(signals, this))
            set(3, find3(signals, this))
            set(5, find5(signals, this))
            set(6, find6(signals, this))
            set(9, find9(signals, this))
            set(0, find0(signals, this))
        }

    fun decode(outputValues: List<String>, decoder: List<Set<Char>>) = outputValues
            .map { it.toSet() }.map { decoder.indexOf(it) }

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(outputValuesIn(entry), decoderFor(signalPatternsIn(entry).map { it.toSet() }))

    fun part1(input: List<String>): Int = input
        .flatMap { outputValuesIn(it) }
        .count { it.length in lengthsOfDigits1478 }

    fun part2(input: List<String>): Int = input
            .map { digitsInOutputDisplay(it).fold(0, { acc, n -> acc * 10 + n }) }.sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    part1(testInput).also(::println).also { check(it == 26) }

    part2(listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))
        .also(::println).also { check(it == 5353) }

    part2(testInput).also(::println).also { check(it == 61229)}

    val input = readInput("Day08")
    part1(input).also(::println).also { check(it == 421) } // gold star
    part2(input).also(::println).also { check(it == 986163) } // gold star
}
