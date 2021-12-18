/**
 * Day 8: Seven Segment Search
 */
fun main() {

    /* under the hood, where the power of Kotlin roars and good sh*t happens */

    val knownSignalLengths = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)

    fun MutableList<Set<Char>>.setKnownSignalPatterns(signals: List<Set<Char>>) {
        knownSignalLengths.forEach { (digit, length) ->
            this[digit] = signals.first { it.size == length } }
    }

    fun MutableList<Set<Char>>.deduceSegments(signals: List<Set<Char>>, selectors: Map<Int, (Set<Char>) -> Boolean>) =
        selectors.forEach { (digit, deduce) -> this[digit] = signals.first { deduce(it) } }

    fun MutableList<Set<Char>>.deduce5and6Segment(signals: List<Set<Char>>) {
        deduceSegments(signals, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
            2 to { signal -> signal.size == 5 && (this[4] - signal).size == 2 },
            3 to { signal -> signal.size == 5 && (this[7] - signal).isEmpty() },
            6 to { signal -> signal.size == 6 && (this[7] - signal).size == 1 },
            9 to { signal -> signal.size == 6 && (this[4] - signal).isEmpty() },
        ))
    }

    fun MutableList<Set<Char>>.deduceRemaining(signals: List<Set<Char>>) {
        deduceSegments(signals, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
            5 to { signal -> signal.size == 5 && signal !in this.slice(setOf(2, 3)) },
            0 to { signal -> signal.size == 6 && signal !in this.slice(setOf(6, 9)) },
        ))
    }

    fun decoderFor(signals: List<Set<Char>>): List<Set<Char>> = buildList() {
        addAll(List(10) { emptySet() })
        setKnownSignalPatterns(signals)
        deduce5and6Segment(signals)
        deduceRemaining(signals)
    }

    fun decode(outputValues: List<String>, decoder: List<Set<Char>>): List<Int> = outputValues
        .map { it.toSet() }.map { decoder.indexOf(it) }

    fun scrambledSignalPatternsIn(entry: String) = entry
        .split(" | ").first().split(" ")

    fun outputValuesIn(entry: String) = entry
        .split(" | ").last().split(" ")

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(outputValuesIn(entry), decoderFor(scrambledSignalPatternsIn(entry).map { it.toSet() }))

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { outputValuesIn(it) }
        .count { it.length in knownSignalLengths.values }

    fun part2(input: List<String>): Int = input
        .map { digitsInOutputDisplay(it).fold(0) { acc, n -> acc * 10 + n } }.sum()

    /* Tests */

    val oneEntry = listOf("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf")
    part2(oneEntry).also(::println).also(assertAnswerIs(5353))

    val exampleEntries = readInput("Day08_test")
    part1(exampleEntries).also(::println).also(assertAnswerIs(26))
    part2(exampleEntries).also(::println).also(assertAnswerIs(61229))

    /* Go for Gold! */

    val input = readInput("Day08")

    part1(input).also { print("Part 1 answer = $it ") }
        .also(assertAnswerIs(421))
        .also(earnedGoldStar)

    part2(input).also { print("Part 2 answer = $it ") }
        .also(assertAnswerIs(986163))
        .also(earnedGoldStar)
}