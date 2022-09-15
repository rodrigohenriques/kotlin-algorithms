/*
 * Complete the 'legoBlocks' function below.
 *
 * The function is expected to return an INTEGER.
 * The function accepts following parameters:
 *  1. INTEGER n
 *  2. INTEGER m
 */

fun legoBlocks(n: Int, m: Int): Int {
    val rows = rowCombinations(m)

    val totalCombinations = rows.size * n

    return totalCombinations
}

private val blocks = arrayOf(1,2,3,4)

fun rowCombinations(w: Int, row: List<Int> = listOf()): Set<List<Int>> {
    val result = mutableSetOf<List<Int>>()

    if (w == 0) return setOf(row)
    if (w < 0) return emptySet()

    for (block in blocks) {
        val remainder = w - block

        var newPath = row

        repeat(block) {
            newPath = newPath + block
        }

        val r = rowCombinations(remainder, newPath)
        result.addAll(r)
    }

    return result
}


fun main() {
//   assert(legoBlocks(2,2) == 3)
//   assert(legoBlocks(3,2) == 7)
//   assert(legoBlocks(2,3) == 9)
   assert(legoBlocks(4,4) == 3375)
}