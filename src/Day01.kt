fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            val digits = line.filter { it.isDigit() }
            (digits.first() + digits.last().toString()).toInt()
        }
    }

    fun findFirstOrLast(line: String, isFirst: Boolean): Int {
        val map = (1..9).associate { it.toString() to it.toString() } + mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9",
        )
        var best: String? = null
        var bestPosition: Int = if (isFirst) Int.MAX_VALUE else Int.MIN_VALUE
        for ((key, value) in map.entries) {
            val pos = if (isFirst) line.indexOf(key) else line.lastIndexOf(key)
            if (pos == -1) continue
            if ((isFirst && pos < bestPosition) || (!isFirst && pos > bestPosition)) {
                bestPosition = pos
                best = value
            }
        }
        return checkNotNull(best).toInt()
    }

    fun part2(input: List<String>): Int {
        return input.sumOf { line ->
            val firstDigit = findFirstOrLast(line, true)
            val lastDigit = findFirstOrLast(line, false)
            (firstDigit.toString() + lastDigit.toString()).toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day01_test_1")
//    check(part1(testInput) == 142)
//
    val input = readInput("Day01")
    part1(input).println()

//    val testInput = readInput("Day01_test_2")
//    check(part2(testInput) == 281)

//    val input = readInput("Day01")
//    part1(input).println()
//    part2(input).println()
}
