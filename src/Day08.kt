/**
 * Day 8: Seven Segment Search
 *
 * For the complete problem description, see https://adventofcode.com/2021/day/8
 */
fun main() {

    /* under the hood, where the power of Kotlin roars and good sh*t happens */

    val obviousPatterns = mapOf(1 to 2, 4 to 4, 7 to 3, 8 to 7)

    fun segmentsIn(signal: Set<Char>) = signal.size

    val assertUniquePatternIdentified: (List<Set<Char>>) -> Unit = { check(it.size == 1) }

    fun MutableList<Set<Char>>.assignObviousPatterns(segments: List<Set<Char>>) {
        obviousPatterns.forEach { (digit, count) ->
            this[digit] = segments.filter { segmentsIn(it) == count }
                .also(assertUniquePatternIdentified)
                .first()
        }
    }

    fun MutableList<Set<Char>>.deduceSegments(segments: List<Set<Char>>, selectors: Map<Int, (Set<Char>) -> Boolean>) =
        selectors.forEach { (digit, uniquelyIdentifies) ->
            this[digit] = segments.filter { uniquelyIdentifies(it) }
                .also(assertUniquePatternIdentified)
                .first()
        }

    fun MutableList<Set<Char>>.deduceSignalsWith5and6(segments: List<Set<Char>>) {
        deduceSegments(
            segments, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
                2 to { signal -> segmentsIn(signal) == 5 && segmentsIn(this[4] - signal) == 2 },
                3 to { signal -> segmentsIn(signal) == 5 && (this[7] - signal).isEmpty() },
                6 to { signal -> segmentsIn(signal) == 6 && segmentsIn(this[7] - signal) == 1 },
                9 to { signal -> segmentsIn(signal) == 6 && (this[4] - signal).isEmpty() },
            )
        )
    }

    fun MutableList<Set<Char>>.deducesDigitsForRemaining(signals: List<Set<Char>>) {
        deduceSegments(
            signals, selectors = mapOf<Int, (Set<Char>) -> Boolean>(
                5 to { signal -> segmentsIn(signal) == 5 && signal !in this.slice(setOf(2, 3)) },
                0 to { signal -> segmentsIn(signal) == 6 && signal !in this.slice(setOf(6, 9)) },
            )
        )
    }

    fun decoderFor(signals: List<String>): List<Set<Char>> {
        val segments = signals.map { it.toSet() }
        return buildList() {
            addAll(List(10) { emptySet() })
            assignObviousPatterns(segments)
            deduceSignalsWith5and6(segments)
            deducesDigitsForRemaining(segments)
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

    fun numberFormedBy(digitsInOutput: List<Int>) =
        digitsInOutput.fold(0) { acc, n -> acc * 10 + n }

    /* Main solution functions */

    fun part1(input: List<String>): Int = input
        .flatMap { fourDigitOutputValueIn(it) }
        .count { it.length in obviousPatterns.values }

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