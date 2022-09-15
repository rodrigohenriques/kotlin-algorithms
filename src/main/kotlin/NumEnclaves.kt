fun numEnclaves(grid: Array<IntArray>): Int {
    // Returns null if island is not an enclave
    fun dfs(a: Int, b: Int): Int? {
        // out of bounds must return null
        if (a < 0 || a >= grid.size || b < 0 || b >= grid[0].size) return null

        if (grid[a][b] == 0) return 0

        grid[a][b] = 0

        val up = dfs(a - 1, b)
        val down = dfs(a + 1, b)
        val left = dfs(a, b - 1)
        val right = dfs(a, b + 1)

        if (up == null) return null
        if (down == null) return null
        if (left == null) return null
        if (right == null) return null

        return up + down + left + right + 1
    }

    var enclaves = 0

    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (grid[i][j] == 1) {
                enclaves += dfs(i, j) ?: 0
            }
        }
    }

    return enclaves
}

fun main() {
    val input1 = arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(1, 0, 1, 0),
        intArrayOf(0, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
    )

    val input2 = arrayOf(
        intArrayOf(0, 0, 1, 0),
        intArrayOf(0, 0, 1, 0),
        intArrayOf(0, 1, 1, 0),
        intArrayOf(0, 0, 0, 0),
    )

    assert(numEnclaves(input1).also { println(it) } == 3)
    assert(numEnclaves(input2).also { println(it) } == 0)
}