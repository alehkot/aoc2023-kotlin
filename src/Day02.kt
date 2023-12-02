fun main() {
    fun part1(input: List<String>): Int {
        val limits = mapOf("red" to 12, "green" to 13, "blue" to 14)

        return input.sumOf { game ->
            val (gameNumberData, gameIterationsData) = game.split(": ")
            val gameNumber = gameNumberData.split(" ").last().substringBefore(":").toInt()
            val iterations = gameIterationsData.split("; ")

            if (iterations.all { iteration ->
                    iteration.split(", ").all { colorSet ->
                        val (numOfColorStr, color) = colorSet.split(" ")
                        numOfColorStr.toInt() <= limits.getOrDefault(color, 0)
                    }
                }) gameNumber else 0
        }
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        games@ for (game in input) {
            val (_, gameIterationsData) = game.split(": ")
            val iterations = gameIterationsData.split("; ")
            val maxSet = mutableMapOf<String, Int>()
            for (iteration in iterations) {
                val colorSets = iteration.split(", ")
                for (colorSet in colorSets) {
                    val (numOfColorStr, color) = colorSet.split(" ")
                    val numOfColorInt = numOfColorStr.toInt()
                    maxSet.merge(color, numOfColorInt) { oldVal, newVal -> oldVal.coerceAtLeast(newVal) }
                }
            }

            sum += maxSet.values.fold(1) { u, v -> u * v }
        }
        return sum
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test_1")
    check(part1(testInput) == 8)
//
//    val input = readInput("Day02")
//    part1(input).println()

//    val testInput = readInput("Day02_test_1")
//    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    part2(input).println()
}
