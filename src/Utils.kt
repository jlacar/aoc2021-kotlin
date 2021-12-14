import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Converts a comma separated list of values into a List<Int>
 */
fun toIntList(csv: String): List<Int> = csv.split(",").map { it.toInt() }

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Returns a check function to guard against regressions. Use with .also()
 *
 * Example:
 *    part1(input)
 *       .also { println("Part 1 answer is $it") }
 *       .also(assertAnswerIs(27))
 *
 *    part2(input)
 *       .also { println("Part 2 answer is $it") }
 *       .also(assertAnswerIs(5353))
 */
fun assertAnswerIs(expected: Int): (Int) -> Unit = { check(it == expected) }

/**
 * For marking solutions that have been verified. Use with .also()
 *
 * Example:
 *    part1(input)
 *       .also { println("Part 1 answer is $it") }
 *       .also(earnedGoldStar)
 *
 *    part2(input)
 *       .also { println("Part 2 answer is $it") }
 *       .also(earnedGoldStar)
 */
val earnedGoldStar: (Int) -> Unit = { println("(*)") }