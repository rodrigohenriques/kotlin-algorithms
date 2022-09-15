import java.io.File
import java.util.*
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow

/**
441888214
138801080
229478982
412262340
0
 */

private class Dummy

fun main(args: Array<String>) {
    val inputURL = Dummy::class.java.getResource("input_1_1.txt") ?: throw IllegalStateException("input not found")
    val lines = ArrayDeque(File(inputURL.toURI()).readLines())

    fun readLine() = lines.remove()

    val (n, q) = readLine().split(" ").map { it.toInt() }

    val parents = Array<Int>(n + 1) { -1 }

    for (i in 1 until n) {
        val (parent, child) = readLine().split(" ").map { it.toInt() }.sorted()

        parents[child] = parent
    }

    val queries = Array(q) {
        val k = readLine()!!.toInt()
        val querySet = readLine()!!.split(" ").map { it.toInt() }.toSet()
        querySet
    }

    val distanceMemo = mutableMapOf<Pair<Int, Int>, Int>()
    val parentsMemo = mutableMapOf<Int, Set<Int>>()

    fun pair(a: Int, b: Int) = min(a, b) to max(a, b)

    fun addToMemo(a: Int, b: Int, distance: Int) {
        distanceMemo[pair(a, b)] = distance
    }

    fun ancestors(a: Int): Set<Int> {
        val parent = parents[a]

        val result = if (parent == -1) emptySet() else setOf(parent) + ancestors(parent)

        parentsMemo[a] = result

        return result
    }

    // Returns -1 if no path exists
    fun dist(
        origin: Int,
        destination: Int,
    ): Int {
        if (origin == destination) return 0

        val memo = distanceMemo[pair(origin, destination)]

        if (memo != null) return memo

        val originAncestors = ancestors(origin)
        val destinationAncestors = ancestors(destination)

        val commonAncestors = originAncestors.intersect(destinationAncestors)

        val lca = commonAncestors.firstOrNull() ?: -1

        if (lca == -1) {
            return max(originAncestors.size, destinationAncestors.size).also { addToMemo(origin, destination, it) }
        }

        val distA = dist(origin, lca)
        val distB = dist(lca, destination)

        val result = distA + distB

        addToMemo(origin, destination, result)

        return result
    }

    fun safeMultiply(a: Int, b: Int): Long {
        val p1 = (a.toLong() * b.toLong()) % MOD
        return (p1 * dist(a, b).toLong()) % MOD
    }

    for (querySet in queries) {
        var productSum = 0L

        val uniquePairs = querySet.flatMap { a ->
            (querySet - a).map { b -> pair(a, b) }
        }.toSet()

        for ((a, b) in uniquePairs) {
            productSum = productSum % MOD + safeMultiply(a, b) % MOD
        }

        val result = productSum % MOD

        println(result)
    }
}

private val MOD = (10.0.pow(9) + 7.0).toLong()