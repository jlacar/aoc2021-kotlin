/**
 * Day 8: Seven Segment Search
 */
fun main() {

    /* under the hood, where the power of Kotlin roars and good sh*t happens */

    val knownSignalLengths = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)

    fun segmentsIn(signal: Set<Char>) = signal.size

    fun MutableList<Set<Char>>.setKnownSignalPatterns(signals: List<Set<Char>>) {
        knownSignalLengths.forEach { (digit, length) ->
            this[digit] = signals.first { segmentsIn(it) == length }
        }
    }

    fun MutableList<Set<Char>>.deduceSegments(
        signals: List<Set<Char>>,
        selectors: Map<Int, (Set<Char>) -> Boolean>
    ) = selectors.forEach { (digit, deduce) -> this[digit] = signals.first { deduce(it) } }

    fun MutableList<Set<Char>>.deduce5and6Segment(signals: List<Set<Char>>) {
        deduceSegments(
            signals, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
                2 to { sig -> segmentsIn(sig) == 5 && segmentsIn(this[4] - sig) == 2 },
                3 to { sig -> segmentsIn(sig) == 5 && (this[7] - sig).isEmpty() },
                6 to { sig -> segmentsIn(sig) == 6 && segmentsIn(this[7] - sig) == 1 },
                9 to { sig -> segmentsIn(sig) == 6 && (this[4] - sig).isEmpty() },
            )
        )
    }

    fun MutableList<Set<Char>>.deduceRemaining(signals: List<Set<Char>>) {
        deduceSegments(
            signals, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
                5 to { sig -> segmentsIn(sig) == 5 && sig !in this.slice(setOf(2, 3)) },
                0 to { sig -> segmentsIn(sig) == 6 && sig !in this.slice(setOf(6, 9)) },
            )
        )
    }

    fun decoderFor(signals: List<String>): List<Set<Char>> {
        val segments = signals.map { it.toSet() }
        return buildList() {
            addAll(List(10) { emptySet() })
            setKnownSignalPatterns(segments)
            deduce5and6Segment(segments)
            deduceRemaining(segments)
        }
    }

    fun decode(outputValues: List<String>, decoder: List<Set<Char>>): List<Int> = outputValues
        .map { it.toSet() }.map { decoder.indexOf(it) }

    fun tenUniqueSignalPatternsIn(entry: String) = entry
        .split(" | ").first().split(" ")

    fun fourDigitOutputValueIn(entry: String) = entry
        .split(" | ").last().split(" ")

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(fourDigitOutputValueIn(entry), decoderFor(tenUniqueSignalPatternsIn(entry)))

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { fourDigitOutputValueIn(it) }
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