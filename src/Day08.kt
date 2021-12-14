/**
 * Day 8: Seven Segment Search
 */
fun main() {

    val lengthsOfDigits1478 = listOf(2, 4, 3, 7)

    /* signals with size == 5 : (2, 3, 5) */

    fun find2(signals: List<Set<Char>>, s4: Set<Char>) = signals
        .first { it.size == 5 && (s4 subtract it).size == 2 }

    fun find3(signals: List<Set<Char>>, s4subs2: Set<Char>) = signals
        .first { it.size == 5 && (s4subs2 subtract it).size == 1 }

    fun find5(signals: List<Set<Char>>, s2_s3: List<Set<Char>>) = signals
        .first { it.size == 5 && it !in s2_s3 }

    /* signals with size == 6 : (6, 9, 0) */

    fun find6(signals: List<Set<Char>>, s7: Set<Char>): Set<Char> = signals
        .first { it.size == 6 && (it subtract s7).size == 4 }

    fun find9(signals: List<Set<Char>>, s4unions7: Set<Char>): Set<Char> = signals
        .first { it.size == 6 && (it subtract s4unions7).size == 1 }

    fun find0(signals: List<Set<Char>>, s6_s9: List<Set<Char>>) = signals
        .first { it.size == 6 && it !in s6_s9 }

    /* other stuff */

    fun signalPatternsIn(entry: String) = entry
            .split(" | ").first().split(" ")

    fun outputValuesIn(entry: String) = entry
            .split(" | ").last().split(" ")

    fun decoderFor(signals: List<Set<Char>>): List<Set<Char>> =
        buildList<Set<Char>>(10) {
            repeat(10) { add(emptySet()) }
            set(1, signals.first { it.size == 2 })
            set(4, signals.first { it.size == 4 })
            set(7, signals.first { it.size == 3 })
            set(8, signals.first { it.size == 7 })
            set(2, find2(signals, this[4]))
            set(6, find6(signals, this[7]))
            set(3, find3(signals, this[4] subtract this[2]))
            set(9, find9(signals, this[4] union this[7]))
            set(5, find5(signals, listOf(this[2], this[3])))
            set(0, find0(signals, listOf(this[6], this[9])))
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


/*
 Part 1:

 1 -> cf
 2 -> acdef
 3 -> acdfg
 4 -> bcdf
 5 -> abdfg
 6 -> abdefg
 7 -> acf
 8 -> abcdefg
 9 -> abcdfg

*/