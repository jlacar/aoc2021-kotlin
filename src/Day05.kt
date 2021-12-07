data class Point(val x: Int, val y: Int) {
    override fun toString(): String = "$x,$y"
}

fun toPoint(xy: String): Point = xy.split(",")
    .let { Point(it[0].toInt(), it[1].toInt()) }

fun toEnds(desc: String) = desc.split(" -> ").let {
    Pair(toPoint(it[0]), toPoint(it[1]))
}

fun toLineSegments(s: List<String>): List<LineSegment> =
    s.map { desc -> LineSegment(toEnds(desc)) }

fun main() {
    fun solve(input: List<String>, criteria: (LineSegment) -> Boolean = { true }): Int =
        with (Graph()) {
            toLineSegments(input).filter(criteria).forEach { plot(it) }
            intersectCount()
        }

    fun part1(input: List<String>) = solve(input) { it.isHorizontal() || it.isVertical() }
    fun part2(input: List<String>) = solve(input)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)
    runOtherTests()

    val input = readInput("Day05")
    println(part1(input).also { check(it == 5608) }) // solved
    println(part2(input).also { check(it == 20299) }) // solved
}

private fun runOtherTests() {
    with(LineSegment(toEnds("1,2 -> 3,4"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(!isVertical())
    }

    with(LineSegment(toEnds("1,2 -> 1,4"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(isVertical())
        check(points() == listOf(Point(1, 2), Point(1, 3), Point(1, 4)))
    }

    with(LineSegment(toEnds("1,4 -> 1,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(isVertical())
        check(points() == listOf(Point(1, 4), Point(1, 3), Point(1, 2)))
    }

    with(LineSegment(toEnds("1,2 -> 4,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(isHorizontal())
        check(!isVertical())
        check(points() == listOf(Point(1, 2), Point(2, 2), Point(3, 2), Point(4, 2)))
    }

    with(LineSegment(toEnds("4,2 -> 1,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(isHorizontal())
        check(!isVertical())
        check(points() == listOf(Point(4, 2), Point(3, 2), Point(2, 2), Point(1, 2)))
    }
}

enum class Direction(val arrow: Char) {
    LEFT('\u2190'),
    RIGHT('\u2192'),
    UP('\u2191'),
    DOWN('\u2193'),
    UP_LEFT('\u2196'),
    UP_RIGHT('\u2197'),
    DOWN_LEFT('\u2199'),
    DOWN_RIGHT('\u2198'),
    UNKNOWN('?')
}

class LineSegment(val ends: Pair<Point, Point>) {
    fun isHorizontal(): Boolean = ends.first.y == ends.second.y
    fun isVertical(): Boolean = ends.first.x == ends.second.x

    fun points(): List<Point> = when {
        isHorizontal() -> horizontalPoints()
        isVertical() -> verticalPoints()
        else -> diagonalPoints()
    }

    private fun rangeOf(start: Int, finish: Int) =
        if (start <= finish) (start..finish) else (start downTo finish)

    private fun diagonalPoints(): List<Point> =
        rangeOf(ends.first.x, ends.second.x)
            .zip(rangeOf(ends.first.y, ends.second.y)) { x, y -> Point(x, y) }

    private fun horizontalPoints(): List<Point> = rangeOf(ends.first.x, ends.second.x)
        .map { x -> Point(x, ends.first.y) }

    private fun verticalPoints(): List<Point> = rangeOf(ends.first.y, ends.second.y)
        .map { y -> Point(ends.first.x, y) }

    // utility methods
    override fun toString(): String =
        "${ends.first} -> ${ends.second} (${direction().arrow})"

    fun lineType() = if (isHorizontal()) '—' else if (isVertical()) '|' else '/'

    private fun direction(): Direction = when {
        isHorizontal() -> if (fromLeft()) Direction.RIGHT else Direction.LEFT
        isVertical() -> if (fromBelow()) Direction.UP else Direction.DOWN
        fromLeft() -> if (fromBelow()) Direction.UP_RIGHT else Direction.DOWN_RIGHT
        fromRight() -> if (fromBelow()) Direction.UP_LEFT else Direction.DOWN_LEFT
        else -> Direction.UNKNOWN
    }

    private fun fromAbove() = ends.first.y > ends.second.y
    private fun fromBelow() = ends.first.y < ends.second.y
    private fun fromLeft() = ends.first.x < ends.second.x
    private fun fromRight() = ends.first.x > ends.second.x
}

class Graph {
    private val graph = mutableListOf<Point>()
    fun plot(line: LineSegment) = graph.addAll(line.points())
    fun intersectCount() = graph.groupingBy { it }.eachCount().filter { it.value >= 2 }.count()
}


/*

1
.
X (0, 8)
.\
. \
+  \
.   \
.    \
.     \
.      \ (8, 0)
0....+..O.1....+....2....+....3

.
0....+..O.1....+....2....+....3
.      / (8, 0)
.     /
.    /
.   /
+  /
. /
./
X (0, 8)
.
*

Part 2:

 0....+....1....+....2....+....3
 .
 .   A----B       D----C
 .
 .    F       G      J  K
 +    |       |     /    \
 .    |       |    /      \
 .    E   M   H   I   P    L
 .       /             \
 .      /               \
 1     N                 O

Directions:
A-B - right (→)
C-D - left (←)
E-F - up (↑)
G-H - down (↓)
I-J - up-right (↗)
K-L - down-right (↘)
M-N - down-left (↙)
O-P - up-left (↖)

 */