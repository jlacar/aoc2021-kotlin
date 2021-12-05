import java.util.*

/**
 * Giant Squid Bingo
 *
 * 5x5 grids, only complete rows and columns win, no diagonals.
 */
fun main() {
    fun numberPicker(input: List<String>) = input.first().split(",").map { it.toInt() }

    fun boardsFrom(input: List<String>) =
        input.slice(2..input.lastIndex).chunked(6).map { Board(it.take(5)) }

    fun noBingo(boards: List<Board>, pick: Int): Boolean {
        for (board in boards) {
            if (board.mark(pick).isBingo) return false
        }
        return true
    }

    /**
     * Final score is sum of unmarked numbers * winning number called
     */
    fun part1(input: List<String>): Int {
        val boards: List<Board> = boardsFrom(input)
        numberPicker(input).takeWhile { pick -> noBingo(boards, pick) }
        return boards.first { it.isBingo }.score
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testBoard = Board(listOf("1 2 3", "4 5 6", "7 8 9"))
    check(!testBoard.isBingo && testBoard.score == 45)

    testBoard.mark(11)
    check(!testBoard.isBingo && testBoard.score == 45)

    testBoard.mark(4)
    check(!testBoard.isBingo)

    testBoard.mark(5)
    check(!testBoard.isBingo)

    testBoard.mark(6)
    check(testBoard.isBingo)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)

    val input = readInput("Day04")

    val part1answer = part1(input)

    // for refactoring: we know what the answer is
    check(part1answer == 63552)
    println(part1answer) // 63552

    // println(part2(input))
}

fun toIntArray(s: String): IntArray = s.trim()
    .split(Regex("""\s+"""))
    .map { it.toInt() }.toIntArray()

class Board(lines: List<String>) {
    private val markedPicks: MutableList<Int> = mutableListOf()
    private val numbers: List<IntArray> = lines.map { toIntArray(it) }

    private var _score: Int = numbers.sumOf { it.sum() }
    val score: Int get() = _score * (markedPicks.lastOrNull() ?: 1)

    private var _isBingo: Boolean = false
    val isBingo: Boolean get() = _isBingo

    private fun isFound(pick: Int) = numbers.any { it.contains(pick) }

    private fun allPicked(line: List<Int>) = markedPicks.containsAll(line)

    private fun colNums(col: Int): List<Int> = numbers.map { it[col] }

    private fun checkBingo(pick: Int) {
        numbers.forEachIndexed { row, rowNums ->
            if (rowNums.contains(pick)) {
                _isBingo = allPicked(rowNums.toList()) || allPicked(colNums(rowNums.indexOf(pick)))
            }
        }
    }

    fun mark(pick: Int): Board {
        if (isFound(pick)) {
            markedPicks.add(pick).also {
                _score -= pick
                checkBingo(pick)
            }
        }
        return this
    }
}

//    override fun toString(): String =
//        numbers.map { it.joinToString(separator = ",") }
//            .joinToString(",\n")