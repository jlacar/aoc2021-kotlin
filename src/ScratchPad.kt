fun main() {
    day8Part2()
}

fun day8Part2() {

    val input = listOf(
        "abcefg cf acdeg acdfg bcdf abdfg abdefg acf abcdefg abcdfg | abdfg acdfg abdfg acdfg",  // 5353
//        "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf",  // 5353
//        "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe", // 8394
//        "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc", // 9781
//        "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef", // 1625
    )

    val segments = input.first().split(" | ").first().split(" ")
    val codes = segments.map { it.toSet() }

    val decode = buildList<Set<Char>>(10) {
        repeat(10) { add(emptySet()) }
        set(1, segments.first { it.length == 2 }!!.toSet())
        set(4, segments.first { it.length == 4 }!!.toSet())
        set(7, segments.first { it.length == 3 }!!.toSet())
        set(8, segments.first { it.length == 7 }!!.toSet())
        set(2, find2(codes, this[4]))
        set(3, find3(codes, this[4] subtract this[2]))
        set(5, find5(codes, listOf(this[2], this[3])))
        set(6, find6(codes, this[7]) )
        set(0, find0(codes, this[4] union this[7], this[6]) )
        set(9, find9(codes, this[4] union this[7]) )
    }
    listOf(
        1 to 2,
        4 to 2,
        7 to 2,
        8 to 2,
        1 to 5,
        4 to 5,
        7 to 5,
        8 to 5,
        1 to 3,
        4 to 3,
        7 to 3,
        8 to 3,
        1 to 6,
        4 to 6,
        7 to 6,
        8 to 6,
        1 to 9,
        4 to 9,
        7 to 9,
        8 to 9,
        1 to 0,
        4 to 0,
        7 to 0,
        8 to 0,
    ).forEach { (index, sig) ->
        (decode[index] - decode[sig]).also { println("test $index - *$sig = $it") };
        (decode[sig] - decode[index]).also { println("test *$sig - $index = $it") } }

    println(segments.joinToString(",", prefix = "[", postfix = "]"))
    println(codes)
    println(decode)

    translate(input.first(), decode).also { println("translated: $it") }
}

fun find9(codes: List<Set<Char>>, c4andc7: Set<Char>): Set<Char> = codes
    .first { it.size == 6 && (it subtract c4andc7).size == 1 }

fun find6(codes: List<Set<Char>>, c7: Set<Char>): Set<Char> = codes
    .first { it.size == 6 && (it subtract c7).size == 4 }

fun find0(codes: List<Set<Char>>, c4andc7: Set<Char>, c6: Set<Char>): Set<Char> = codes
    .first { it.size == 6 && it != c6 && (it subtract c4andc7).size == 2 }

fun translate(input: String, decode: List<Set<Char>>) =
    input.split(" | ").last().split(" ")
        .map { it.toSet() }
        .map { decode.indexOf(it) }

fun find5(codes: List<Set<Char>>, c23: List<Set<Char>>) = codes
    .first { it.size == 5 && it !in c23}

fun find3(codes: List<Set<Char>>, c4sub2: Set<Char>) = codes
    .first { it.size == 5 && (c4sub2 subtract it).size == 1 }

fun find2(codes: List<Set<Char>>, c4: Set<Char>) = codes
    .first { it.size == 5 && (c4 - it).size == 2 }