import java.util.*
import kotlin.math.max

fun maxDistance(grid: Array<IntArray>): Int {
    val n = grid.size
    val m = grid[0].size

    var result = -1

    val cost = Array(n) { IntArray(m) { 0 } }

    val queue = ArrayDeque<Pair<Int, Int>>()

    val visited = mutableSetOf<Pair<Int, Int>>()

    // Add all lands to the queue
    for (i in 0 until n) {
        for (j in 0 until m) {
            if (grid[i][j] == 1) {
                queue.add(Pair(i, j))
                visited.add(Pair(i, j))
            }
        }
    }

    var breadth = 0

    var stopSpreading: Boolean

    while (queue.isNotEmpty()) {
        val curr = queue.poll()
        val (i, j) = curr

        // Ignore visited nodes
        if (curr in visited) continue

        if (i < 0 || i >= n || j < 0 || j >= m) continue

        val data = grid[i][j]

        // Stop spreading whenever another piece of land is found
        stopSpreading = node != curr && data == 1

        if (data == 0) {
            updateShortestLandDistance(curr, breadth)
        }

        visited.add(curr)

        if (stopSpreading) continue

        breadth++

        // Add remaining edges to the queue ignoring nodes the ones that are out of bounds
        if (i > 0) queue.add((i - 1 to j)) // up
        if (i < n - 1) queue.add((i + 1 to j)) // down
        if (j > 0) queue.add((i to j - 1)) // left
        if (j < m - 1) queue.add((i to j + 1)) // right
    }

    for (i in 0 until n) {
        for (j in 0 until m) {
            // Runs BFS for each land
            if (grid[i][j] == 1) {
                bfs(i to j)
            }
        }
    }

    if (shortestDistanceFromLand.isEmpty()) return -1

    return shortestDistanceFromLand.values.reduce { acc, curr -> max(acc, curr) }
}

fun main() {
    val grid = arrayOf(
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1),
        intArrayOf(1, 1, 1)
    )
    println(maxDistance(grid))
}