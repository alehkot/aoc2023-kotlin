import kotlin.math.pow

//Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
//Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
//Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
//Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
//Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
//Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11

fun main() {
    fun findOverlap(line: String): List<Int> {
        val (_, card) = line.split(": ")
        val (part1, part2) = card.split(" | ")

        val part2Numbers = part2.split(Regex("\\s+")).filter { it.isNotBlank() }.map { it.toInt() }.toHashSet()
        val overlap = part1.split(Regex("\\s+")).filter { it.isNotBlank() }.map { it.toInt() }
            .filter { part2Numbers.contains(it) }
        return overlap
    }

    fun part1(input: List<String>): Int {
        var result = 0.0
        for (line in input) {
            val overlap = findOverlap(line)

            if (overlap.isNotEmpty()) {
                result += 2.0.pow(overlap.size.toDouble() - 1)
            }
        }
        return result.toInt()
    }

    fun part2(input: List<String>): Int {
        val winners = input.indices.associateWith { 1 }.toMutableMap()
        for (i in input.indices) {
            val line = input[i]
            val overlap = findOverlap(line)

            if (overlap.isNotEmpty()) {
                val countOfThisCard = checkNotNull(winners[i])
                for (k in 0..<countOfThisCard) {
                    for (j in (i + 1).coerceAtMost(input.size)..<((i + 1) + overlap.size).coerceAtMost(input.size)) {
                        winners.compute(j) { _, value -> checkNotNull(value) + 1 }
                    }
                }
            }
        }

        return winners.values.sum()
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day04_test_1")
//    check(part1(testInput) == 13)


////
//    val input = readInput("Day04")
//    part1(input).println()
//
    val testInput2 = readInput("Day04_test_1")
    check(part2(testInput2) == 30)
//
    val input = readInput("Day04")
    part2(input).println()
}
