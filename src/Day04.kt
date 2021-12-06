/**
 * Giant Squid Bingo
 *
 * 5x5 grids, only complete rows and columns win, no diagonals.
 */
fun main() {
    fun numberPicker(csv: String) = toIntList(csv)

    fun boardsFrom(input: List<String>): List<Board> =
        input.slice(2..input.lastIndex).chunked(6).map { Board(it.take(5)) }

    fun noBingo(boards: List<Board>, pick: Int): Boolean {
        for (board in boards) {
            if (board.mark(pick).isBingo) return false
        }
        return true
    }

    fun part1(input: List<String>): Int {
        val boards = boardsFrom(input)
        numberPicker(input.first()).takeWhile { pick -> noBingo(boards, pick) }
        return boards.first { it.isBingo }.score
    }

    fun hasMoreToPlay(
        boards: List<Board>,
        bingoed: MutableList<Board>,
        pick: Int
    ): Boolean {
        boards.filter { !it.isBingo }.forEach { board ->
            board.mark(pick)
            if (board.isBingo) {
                bingoed.add(board)
            }
        }
        return bingoed.size < boards.size
    }

    /**
     * Let the Giant Squid win: find score of last board that Bingos
     */
    fun part2(input: List<String>): Int {
        val boards = boardsFrom(input)
        val bingoed = mutableListOf<Board>()
        numberPicker(input.first()).takeWhile { pick ->
            hasMoreToPlay(boards, bingoed, pick)
        }
        return bingoed.last().score
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
    check(part2(testInput) == 1924)

    val input = readInput("Day04")

    // for refactoring (answer = 63552)
    val part1answer = part1(input)
    check(part1answer == 63552)
    println(part1answer)

    // for refactoring (answer = 9020)
    val part2answer = part2(input)
    check(part2answer == 9020)
    println(part2(input))
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

    private fun bingo(line: List<Int>) = markedPicks.containsAll(line)

    private fun column(n: Int): List<Int> = numbers.map { it[n] }

    private fun hasBingoIn(row: IntArray, pick: Int) =
        bingo(row.toList()) || bingo(column(row.indexOf(pick)))

    private fun checkBingo(pick: Int) {
        numbers.forEach { row ->
            if (row.contains(pick)) {
                _isBingo = hasBingoIn(row, pick)
                return
            }
        }
    }

    fun mark(pick: Int): Board {
        if (numbers.any { it.contains(pick) }) {
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