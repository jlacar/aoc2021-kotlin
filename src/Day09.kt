fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    /* Scaffolding */

    fun result() {
        // use this to build out a solution
    }

    /* Tests */

    val testInput = readInput("DayXX_test")

    part1(testInput).also { println("Part 1 (test): $it")}
        .also(assertAnswerIs(15))

//    part2(testInput).also { println("Part 2 (test): $it")}
//        .also(assertAnswerIs(?))

    val input = readInput("DayXX")

    /* Go for gold! */

    part1(input).also { println("Part 1 (final): $it")}
        // .also(assertAnswerIs(?))
        // .also(earnedGoldStar)

    part2(input).also { println("Part 2 (final): $it")}
        // .also(assertAnswerIs(?))
        // .also(earnedGoldStar)
}
