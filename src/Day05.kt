fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    with(LineSegment("1,2 -> 3,4")) {
        println("${ends} - H?: ${isHorizontal()} V?: ${isVertical()}")
        check(!isHorizontal())
        check(!isVertical())
    }

    with(LineSegment("1,2 -> 1,4")) {
        println("${ends} - H?: ${isHorizontal()} V?: ${isVertical()}")
        check(!isHorizontal())
        check(isVertical())
    }

    with(LineSegment("1,2 -> 5,2")) {
        println("${ends} - H?: ${isHorizontal()} V?: ${isVertical()}")
        check(isHorizontal())
        check(!isVertical())
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 1)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Point(val x: Int, val y: Int)

fun toPoint(xy: String): Point = xy.split(",")
    .let { Point(it[0].toInt(), it[1].toInt()) }

class LineSegment(desc: String) {
    val ends: Pair<Point, Point>

    init {
        val (pt1, pt2) = desc.split(" -> ")
        ends = Pair(toPoint(pt1), toPoint(pt2))
    }

    fun isHorizontal(): Boolean = ends.first.y == ends.second.y
    fun isVertical(): Boolean = ends.first.x == ends.second.x
}