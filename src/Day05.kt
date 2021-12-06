data class Point(val x: Int, val y: Int)

fun toPoint(xy: String): Point = xy.split(",")
    .let { Point(it[0].toInt(), it[1].toInt()) }

fun toEnds(desc: String) = desc.split(" -> ").let {
    Pair(toPoint(it[0]), toPoint(it[1]))
}

fun toLineSegments(s: List<String>): List<LineSegment> =
    s.map { desc -> LineSegment(toEnds(desc)) }

fun main() {
    fun part1(input: List<String>): Int {
        toLineSegments(input).filter { it.isHorizontal() || it.isVertical() }
            .forEach { println("$it : ${it.lineType()}") }
        return input.size
    }

    fun part2(input: List<String>): Int {
        toLineSegments(input)
        return input.size
    }

    with(LineSegment(toEnds("1,2 -> 3,4"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(!isVertical())
    }

    with(LineSegment(toEnds("1,2 -> 1,4"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(isVertical())
        check(points() == listOf(Point(1,2), Point(1,3), Point(1,4)))
    }

    with(LineSegment(toEnds("1,4 -> 1,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(!isHorizontal())
        check(isVertical())
        check(points() == listOf(Point(1,4), Point(1,3), Point(1,2)))
    }

    with(LineSegment(toEnds("1,2 -> 4,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(isHorizontal())
        check(!isVertical())
        check(points() == listOf(Point(1,2), Point(2,2), Point(3,2), Point(4,2)))
    }

    with(LineSegment(toEnds("4,2 -> 1,2"))) {
        println("${ends} - Type: ${lineType()}")
        check(isHorizontal())
        check(!isVertical())
        check(points() == listOf(Point(4,2), Point(3,2), Point(2,2), Point(1,2)))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

class LineSegment(val ends: Pair<Point, Point>) {
    fun isHorizontal(): Boolean = ends.first.y == ends.second.y

    fun isVertical(): Boolean = ends.first.x == ends.second.x

    fun points(): List<Point> {
        if (isHorizontal()) {
            val xRange = if (ends.first.x < ends.second.x) (ends.first.x..ends.second.x)
                else (ends.first.x downTo ends.second.x)
            return xRange.map { x -> Point(x, ends.first.y) }
        }
        if (isVertical()) {
            val yRange = if (ends.first.y < ends.second.y) (ends.first.y..ends.second.y)
                else (ends.first.y downTo ends.second.y)
            return yRange.map { y -> Point(ends.first.x, y) }
        }
        return emptyList()
    }

    override fun toString(): String = "${ends.first.x},${ends.first.y} -> ${ends.second.x},${ends.second.y}"

    fun lineType() = if (isHorizontal()) '—' else if (isVertical()) '|' else '?'

    fun reversed(): LineSegment = LineSegment(Pair(ends.second, ends.first))
}