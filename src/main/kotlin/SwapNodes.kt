import kotlin.collections.*
import kotlin.io.*

/*
 * Complete the 'swapNodes' function below.
 *
 * The function is expected to return a 2D_INTEGER_ARRAY.
 * The function accepts following parameters:
 *  1. 2D_INTEGER_ARRAY indexes
 *  2. INTEGER_ARRAY queries
 */

private data class TreeNode(
    val value: Int,
    var left: TreeNode? = null,
    var right: TreeNode? = null,
    var depth: Int = 1
) {
    fun printInOrder() {
        left?.printInOrder()
        print("$value ")
        right?.printInOrder()
    }

    fun swapAtDepth(k: Int) {
        if (depth % k == 0) {
            val tempLeft = left
            left = right
            right = tempLeft
        }

        left?.swapAtDepth(k)

        right?.swapAtDepth(k)
    }
}

fun swapNodes(indexes: Array<Array<Int>>, queries: Array<Int>) {
    val nodes = Array(indexes.size) { TreeNode(it + 1) }

    for (i in indexes.indices) {
        val node = nodes[i]
        val (left, right) = indexes[i]

        if (left != -1) {
            node.left = nodes[left - 1].apply { depth = node.depth + 1 }
        }

        if (right != - 1) {
            node.right = nodes[right - 1].apply { depth = node.depth + 1 }
        }
    }

    val root = nodes[0]

    for (queryIdx in queries.indices) {
        val k = queries[queryIdx]
        root.swapAtDepth(k)
        root.printInOrder()
        println()
    }
}

fun main(args: Array<String>) {
    val n = readLine()!!.trim().toInt()

    val indexes = Array<Array<Int>>(n, { Array<Int>(2, { 0 }) })

    for (i in 0 until n) {
        indexes[i] = readLine()!!.trimEnd().split(" ").map{ it.toInt() }.toTypedArray()
    }

    val queriesCount = readLine()!!.trim().toInt()

    val queries = Array<Int>(queriesCount, { 0 })
    for (i in 0 until queriesCount) {
        val queriesItem = readLine()!!.trim().toInt()
        queries[i] = queriesItem
    }

    swapNodes(indexes, queries)
}
