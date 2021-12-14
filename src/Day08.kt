/**
 * Day 8: Seven Segment Search
 */
fun main() {

    val SIGNAL_LENGTHS_FOR_1_4_7_8 = listOf(2, 4, 3, 7)

    /* signals with size == 5 : (2, 3, 5) */

    fun deduce2(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && (decoder[4] subtract it).size == 2 }

    fun deduce3(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && (decoder[4] - decoder[2] - it).size == 1 }

    fun deduce5(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 5 && it !in decoder.slice(setOf(2,3)) }

    /* signals with size == 6 : (6, 9, 0) */

    fun deduce6(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 6 && (it subtract decoder[7]).size == 4 }

    fun deduce9(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 6 && (it - (decoder[4] union decoder[7])).size == 1 }

    fun deduce0(signals: List<Set<Char>>, decoder: List<Set<Char>>) = signals
        .first { it.size == 6 && it !in decoder.slice(setOf(6,9)) }

    /* solution engine - where the good sh*t happens */

    fun signalPatternsIn(entry: String) = entry
            .split(" | ").first().split(" ")

    fun outputValuesIn(entry: String) = entry
            .split(" | ").last().split(" ")

    fun MutableList<Set<Char>>.set1_4_7_8(signals: List<Set<Char>>) {
        this[1] = signals.first { it.size == 2 }
        this[4] = signals.first { it.size == 4 }
        this[7] = signals.first { it.size == 3 }
        this[8] = signals.first { it.size == 7 }
    }

    fun MutableList<Set<Char>>.deduce2_3_5(signals: List<Set<Char>>) {
        this[2] = deduce2(signals, this)
        this[3] = deduce3(signals, this)
        this[5] = deduce5(signals, this) // must be called last!
    }

    fun MutableList<Set<Char>>.deduce6_9_0(signals: List<Set<Char>>) {
        this[6] = deduce6(signals, this)
        this[9] = deduce9(signals, this)
        this[0] = deduce0(signals, this) // must be called last!
    }

    fun decoderFor(signals: List<Set<Char>>): List<Set<Char>> =
        buildList() {
            addAll(List(10) { emptySet() })
            set1_4_7_8(signals)  // must be called first!
            deduce2_3_5(signals)
            deduce6_9_0(signals)
        }

    fun decode(outputValues: List<String>, decoder: List<Set<Char>>): List<Int> = outputValues
        .map { it.toSet() }.map { decoder.indexOf(it) }

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(outputValuesIn(entry), decoderFor(signalPatternsIn(entry).map { it.toSet() }))

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { outputValuesIn(it) }
        .count { it.length in SIGNAL_LENGTHS_FOR_1_4_7_8 }

    fun part2(input: List<String>): Int = input
        .map { digitsInOutputDisplay(it).fold(0) { acc, n -> acc * 10 + n } }.sum()

    /* Tests */

    val testInput = readInput("Day08_test")
    part1(testInput).also(::println).also { check(it == 26) }
    part2(listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"))
        .also(::println).also { check(it == 5353) }
    part2(testInput).also(::println).also { check(it == 61229)}

    /* Go for Gold! */

    val input = readInput("Day08")
    part1(input).also(::println).also { check(it == 421) } // gold star
    part2(input).also(::println).also { check(it == 986163) } // gold star
}
