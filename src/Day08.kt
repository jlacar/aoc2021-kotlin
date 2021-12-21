data class Signal(val segments: Set<Char>) {
    constructor(value: String = ""): this(value.toSet())

    val segmentCount = segments.size

    override fun equals(other: Any?) =
        other is Signal && segments == other.segments

    override fun hashCode(): Int = segments.hashCode()

    override fun toString() = segments.toString()

    operator fun minus(other: Signal): Signal =
        Signal(segments - other.segments)

    operator fun contains(col: Collection<Signal>): Boolean =
        col.contains(this)
}

/**
 * Day 8: Seven Segment Search
 *
 * For the complete problem description, see https://adventofcode.com/2021/day/8
 */
fun main() {

    /* under the hood, where the power of Kotlin roars and good sh*t happens */

    val obviousPatterns = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)

    val assertUniqueMatchFound: (List<Signal>) -> Unit = { check(it.size == 1) }

    fun MutableList<Signal>.mapObviousPatterns(signals: List<Signal>) {
        obviousPatterns.forEach { (digit, knownCount) ->
            this[digit] = signals.filter { it.segmentCount == knownCount }
                .also(assertUniqueMatchFound)
                .first()
        }
    }

    fun MutableList<Signal>.deduceSegments(segments: List<Signal>, selectors: Map<Int, (Signal) -> Boolean>) =
        selectors.forEach { (digit, whatUniquelyIdentifies) ->
            this[digit] = segments.filter { whatUniquelyIdentifies(it) }
                .also(assertUniqueMatchFound)
                .first()
        }

    fun MutableList<Signal>.deduce5and6segment(signals: List<Signal>) {
        deduceSegments(
            signals, selectors = mapOf<Int, (Signal) -> Boolean>(
                2 to { signal -> signal.segmentCount == 5 && (this[4] - signal).segmentCount == 2 },
                3 to { signal -> signal.segmentCount == 5 && (this[7] - signal).segmentCount == 0 },
                6 to { signal -> signal.segmentCount == 6 && (this[7] - signal).segmentCount == 1 },
                9 to { signal -> signal.segmentCount == 6 && (this[4] - signal).segmentCount == 0 },
            )
        )
    }

    fun MutableList<Signal>.deduceOtherUnmapped(signals: List<Signal>) {
        deduceSegments(
            signals, selectors = mapOf<Int, (Signal) -> Boolean>(
                5 to { signal -> signal.segmentCount == 5 && signal !in this.slice(setOf(2, 3)) },
                0 to { signal -> signal.segmentCount == 6 && signal !in this.slice(setOf(6, 9)) },
            )
        )
    }

    fun decoderFor(signals: List<Signal>): List<Signal> =
        buildList() {
            addAll(List(10) { Signal() })
            mapObviousPatterns(signals)
            deduce5and6segment(signals)
            deduceOtherUnmapped(signals)
        }

    fun decode(digits: List<Signal>, decoder: List<Signal>): List<Int> = digits
        .map { segments -> decoder.indexOf(segments) }

    fun tenUniqueSignalPatternsIn(entry: String) = entry
        .split(" | ").first().split(" ").map { Signal(it) }

    fun fourDigitOutputValueIn(entry: String) = entry
        .split(" | ").last().split(" ").map { Signal(it) }

    fun digitsInOutputDisplay(entry: String): List<Int> =
        decode(fourDigitOutputValueIn(entry), decoderFor(tenUniqueSignalPatternsIn(entry)))

    fun numberFormedBy(digitsInOutput: List<Int>) =
        digitsInOutput.fold(0) { acc, n -> acc * 10 + n }

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { fourDigitOutputValueIn(it) }
        .count { it.segmentCount in obviousPatterns.values }

    fun part2(entries: List<String>): Int =
        entries.sumOf { numberFormedBy(digitsInOutputDisplay(it)) }

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