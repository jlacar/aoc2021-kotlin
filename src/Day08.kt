/**
 * Day 8: Seven Segment Search
 */
fun main() {

    val lengthsOfDigits1478 = listOf(2, 3, 4, 7)

    fun find9(codes: List<Set<Char>>, c4andc7: Set<Char>): Set<Char> = codes
        .first { it.size == 6 && (it subtract c4andc7).size == 1 }

    fun find6(codes: List<Set<Char>>, c7: Set<Char>): Set<Char> = codes
        .first { it.size == 6 && (it subtract c7).size == 4 }

    fun find0(codes: List<Set<Char>>, c69: List<Set<Char>>) = codes
        .first { it.size == 6 && it !in c69 }

    fun find5(codes: List<Set<Char>>, c23: List<Set<Char>>) = codes
        .first { it.size == 5 && it !in c23 }

    fun find3(codes: List<Set<Char>>, c4sub2: Set<Char>) = codes
        .first { it.size == 5 && (c4sub2 subtract it).size == 1 }

    fun find2(codes: List<Set<Char>>, c4: Set<Char>) = codes
        .first { it.size == 5 && (c4 - it).size == 2 }

    fun translateLine(input: List<String>, decoder: List<Set<Char>>) = input
            .map { it.toSet() }.map { decoder.indexOf(it) }

    fun scrambledDigits(input: String) = input
            .split(" | ").first().split(" ")

    fun displayedDigits(input: String) = input
            .split(" | ").last().split(" ")

    fun part1(input: List<String>): Int = input
        .flatMap { displayedDigits(it) }
        .count { it.length in lengthsOfDigits1478 }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")

    fun codesFrom(input: String): List<Set<Char>> = scrambledDigits(input).map { it.toSet() }

    fun decoderFor(codes: List<Set<Char>>): List<Set<Char>> =
        buildList<Set<Char>>(10) {
            repeat(10) { add(emptySet()) }
            set(1, codes.first { it.size == 2 }!!.toSet())
            set(4, codes.first { it.size == 4 }!!.toSet())
            set(7, codes.first { it.size == 3 }!!.toSet())
            set(8, codes.first { it.size == 7 }!!.toSet())
            set(2, find2(codes, this[4]))
            set(6, find6(codes, this[7]))
            set(3, find3(codes, this[4] subtract this[2]))
            set(9, find9(codes, this[4] union this[7]))
            set(5, find5(codes, listOf(this[2], this[3])))
            set(0, find0(codes, listOf(this[6], this[9])))
        }

    fun translateSegment(input: String): List<Int> =
        translateLine(displayedDigits(input), decoderFor(codesFrom(input)))

    fun part2(input: List<String>): Int = input
            .map { translateSegment(it).fold(0, { acc, n -> acc * 10 + n }) }.sum()

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