//467..114..
//...*......
//..35..633.
//......#...
//617*......
//.....+.58.
//..592.....
//......755.
//...$.*....
//.664.598..

fun main() {
    fun part1(input: List<String>): Int {
        var sumPartNumbers = 0
        val directions = listOf(
            -1 to 0,
            1 to 0,
            0 to -1,
            0 to 1,
            -1 to -1,
            1 to 1,
            -1 to 1,
            1 to -1,
        )

        for (i in input.indices) {
            var isPartNumber = false
            val numberParts = StringBuilder()
            for (j in input[i].indices) {
                if (input[i][j].isDigit()) {
                    numberParts.append(input[i][j])
                    if (!isPartNumber) {
                        for (direction in directions) {
                            val adjacentBlock = input.getOrNull(i + direction.first)?.getOrNull(j + direction.second)
                            if (adjacentBlock != null && !adjacentBlock.isDigit() && adjacentBlock != '.') {
                                isPartNumber = true
                            }
                        }
                    }
                } else {
                    if (isPartNumber) {
                        sumPartNumbers += numberParts.toString().toInt()
                        isPartNumber = false
                    }
                    if (numberParts.isNotEmpty()) {
                        numberParts.clear()
                    }
                }
            }
            if (isPartNumber) {
                sumPartNumbers += numberParts.toString().toInt()
            }
            if (numberParts.isNotEmpty()) {
                numberParts.clear()
            }
        }
        return sumPartNumbers
    }

    fun part2(input: List<String>): Int {
        val directions = listOf(
            -1 to 0,
            1 to 0,
            0 to -1,
            0 to 1,
            -1 to -1,
            1 to 1,
            -1 to 1,
            1 to -1,
        )
        val partsWithAdjacentNumbers = mutableMapOf<Pair<Int, Int>, MutableList<Int>>()

        fun registerNumberForPart(
            numberParts: StringBuilder,
            v: MutableList<Int>?
        ): MutableList<Int> {
            val number = numberParts.toString().toInt()
            return if (v == null) {
                mutableListOf(number)
            } else {
                v.add(number)
                v
            }
        }

        for (i in input.indices) {
            var isPartNumber = false
            var partIndices: Pair<Int, Int>? = null
            val numberParts = StringBuilder()
            for (j in input[i].indices) {
                if (input[i][j].isDigit()) {
                    numberParts.append(input[i][j])
                    if (!isPartNumber) {
                        for (direction in directions) {
                            val adjacentBlock = input.getOrNull(i + direction.first)?.getOrNull(j + direction.second)
                            if (adjacentBlock != null && !adjacentBlock.isDigit() && adjacentBlock != '.') {
                                isPartNumber = true
                                partIndices = (i + direction.first) to (j + direction.second)
                            }
                        }
                    }
                } else {
                    if (isPartNumber) {
                        isPartNumber = false
                        partsWithAdjacentNumbers.compute(checkNotNull(partIndices)) { _, v ->
                            registerNumberForPart(numberParts, v)
                        }
                        partIndices = null
                    }
                    if (numberParts.isNotEmpty()) {
                        numberParts.clear()
                    }
                }
            }
            if (isPartNumber) {
                partsWithAdjacentNumbers.compute(checkNotNull(partIndices)) { _: Pair<Int, Int>, v: MutableList<Int>? ->
                    registerNumberForPart(numberParts, v)
                }
            }
            if (numberParts.isNotEmpty()) {
                numberParts.clear()
            }
        }
        return partsWithAdjacentNumbers.filter { entry -> entry.value.size == 2 }.entries.sumOf {
            it.value.fold(1) { acc, num -> acc * num }
                .toInt()
        }
    }

    // test if implementation meets criteria from the description, like:
//    val testInput = readInput("Day03_test_1")
//    check(part1(testInput) == 4361)
////
//    val input = readInput("Day03")
//    part1(input).println()

    val testInput2 = readInput("Day03_test_1")
    check(part2(testInput2) == 467835)

    val input = readInput("Day03")
    part2(input).println()
}
