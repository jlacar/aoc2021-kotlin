/**
 * Day 8: Seven Segment Search
 */
fun main() {

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

    val knownSignalLengths = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)

    fun MutableList<Set<Char>>.setKnownSignalPatterns(signals: List<Set<Char>>) {
        knownSignalLengths.forEach { (digit, length) ->
            this[digit] = signals.first { it.size == length } }
    }

    fun MutableList<Set<Char>>.deduce5segmentSignals(signals: List<Set<Char>>) {
        val selectors = mapOf<Int, (Set<Char>) -> Boolean> (
            2 to { segments -> (this[4] - segments).size == 2},
            3 to { segments -> (this[4] - this[2] - segments).size == 1},
            5 to { segments -> segments !in this.slice(listOf(2,3)) }
        )
        selectors.forEach { (digit, deduce) ->
            this[digit] = signals.first { it.size == 5 && deduce(it) } }
    }

    fun MutableList<Set<Char>>.deduce6segmentSignals(signals: List<Set<Char>>) {
        val selectors = mapOf<Int, (Set<Char>) -> Boolean> (
            6 to { segments -> (segments - this[7]).size == 4 },
            9 to { segments -> (segments - (this[4] union this[7])).size == 1},
            0 to { segments -> segments !in this.slice(setOf(6,9)) }
        )
        selectors.forEach { (digit, deduce) ->
            this[digit] = signals.first { it.size == 6 && deduce(it) } }

        this[6] = deduce6(signals, this)
        this[9] = deduce9(signals, this)
        this[0] = deduce0(signals, this) // must be called last!
    }

    fun decoderFor(signals: List<Set<Char>>): List<Set<Char>> = buildList() {
        addAll(List(10) { emptySet() })
        setKnownSignalPatterns(signals)  // must be called first!
        deduce5segmentSignals(signals)
        deduce6segmentSignals(signals)
    }

    fun decode(outputValues: List<String>, decoder: List<Set<Char>>): List<Int> = outputValues
        .map { it.toSet() }.map { decoder.indexOf(it) }

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(outputValuesIn(entry), decoderFor(signalPatternsIn(entry).map { it.toSet() }))

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { outputValuesIn(it) }
        .count { it.length in knownSignalLengths.values }

    fun part2(input: List<String>): Int = input
        .map { digitsInOutputDisplay(it).fold(0) { acc, n -> acc * 10 + n } }.sum()

    /* Tests */

    val oneEntry = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
    part2(oneEntry).also(::println).also { check(it == 5353) }

    val exampleEntries = readInput("Day08_test")
    part1(exampleEntries).also(::println).also { check(it == 26) }
    part2(exampleEntries).also(::println).also { check(it == 61229)}

    /* Go for Gold! */

    val input = readInput("Day08")

    part1(input).also { print("Part 1 answer = $it ") }
        .also { check(it == 421) }
        .also { println("(*)")} // gold star

    part2(input).also { print("Part 2 answer = $it ") }
        .also { check(it == 986163) }
        .also { println("(*)")} // gold star
}