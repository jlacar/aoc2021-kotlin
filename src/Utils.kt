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
 * Returns a check function to guard against regressions
 */
fun assertSolutionIs(expectedValue: Int): (Int) -> Unit = { check(it == expectedValue) }