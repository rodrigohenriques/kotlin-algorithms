import java.io.File
import java.util.*
import kotlin.math.pow

private data class GraphNode(val label: Int, val parent: Int?, val depth: Int)

private val nodes = sortedMapOf<Int, GraphNode>()

/**
441888214
138801080
229478982
412262340
0
 */

fun main(args: Array<String>) {
    val inputURL = GraphNode::class.java.getResource("input_1_1.txt") ?: throw IllegalStateException("input not found")
    val lines = ArrayDeque(File(inputURL.toURI()).readLines())

    fun readLine() = lines.remove()

    val (n, q) = readLine().split(" ").map { it.toInt() }

    for (i in 1 until n) {
        val (parent, child) = readLine().split(" ").map { it.toInt() }.sorted()
        val parentNode = nodes[parent] ?: GraphNode(parent, null, 0).also {
            nodes[parent] = it
        }

        val childNode = nodes[child] ?: GraphNode(child, parent, parentNode.depth + 1).also {
            nodes[child] = it
        }

        if (childNode.parent != parent) {
            throw IllegalStateException("child node parent is not correct")
        }
    }

    val queries = Array(q) {
        val k = readLine()!!.toInt()
        val querySet = readLine()!!.split(" ").map { it.toInt() }.toSet()
        querySet
    }

    for (querySet in queries) {
        var productSum = 0L

        val uniquePairs = querySet.flatMap { a ->
            (querySet - a).map { b -> sortedPair(a, b) }
        }.toSet()

        for ((a, b) in uniquePairs) {
            val d = distance(sortedPair(a, b))

            val longA = a.toLong()
            val longB = b.toLong()
            val longD = d.toLong()

            val modProduct = (longA * longB) % M

            productSum += (modProduct * longD) % M
        }

        val result = productSum % M

        println(result)
    }
}

private val M = (10.0.pow(9) + 7.0).toInt()

private val memoizedAncestors = mutableMapOf<Int, Set<Int>>()

private fun ancestors(node: GraphNode): Set<Int> {
    if (memoizedAncestors[node.label] != null) return memoizedAncestors[node.label]!!
    val parent = node.parent?.let { nodes[it] } ?: return emptySet()
    val ancestors = ancestors(parent) + setOf(parent.label)
    return ancestors.also { memoizedAncestors[node.label] = it }
}

private val memoizedDistances = mutableMapOf<Pair<Int, Int>, Int>()

private fun distance(pair: Pair<Int, Int>): Int {
    if (pair in memoizedDistances) return memoizedDistances[pair]!!

    val (origin, target) = pair

    val originNode = nodes[origin] ?: throw IllegalStateException("Node not found for id $origin")
    val targetNode = nodes[target] ?: throw IllegalStateException("Node not found for id $target")

    val ancestorsOfOrigin = ancestors(originNode).toList()
    val ancestorsOfTarget = ancestors(targetNode).toList()

    var i = 0

    while (i < ancestorsOfOrigin.size && i < ancestorsOfTarget.size && ancestorsOfOrigin[i] == ancestorsOfTarget[i]) {
        i++
    }

    val lca = nodes[ancestorsOfOrigin[i - 1]] ?: throw IllegalStateException("Node not found for id ${ancestorsOfOrigin[i - 1]}")

    val originToLCA = originNode.depth - lca.depth
    val targetToLCA = targetNode.depth - lca.depth

    memoizeDistance(origin, lca.label, originToLCA)
    memoizeDistance(target, lca.label, targetToLCA)
    memoizeDistance(origin, target, originToLCA + targetToLCA)

    return originToLCA + targetToLCA
}

private fun sortedPair(a: Int, b: Int) = if (a < b) a to b else b to a

private fun memoizeDistance(a: Int, b: Int, value: Int) {
    memoizedDistances[sortedPair(a, b)] = value
}