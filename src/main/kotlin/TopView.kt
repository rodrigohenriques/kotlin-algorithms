import java.util.*

typealias Breadth = Int

data class ResultData(
    val depth: Int,
    val breadth: Int,
    val data: Int,
)

fun topView(root: Node) {
    val sortedMap = sortedMapOf<Int, ResultData>()

    traverse(root, 0, 0, sortedMap)

    println(sortedMap.values.joinToString { "${it.data} " })
}

fun bfs(node: Node?, depth: Int, breadth: Int, sortedMap: SortedMap<Int, ResultData>) {
    if (node == null) return

    val current = sortedMap[breadth]

    if (current == null || current.depth > depth) {
        sortedMap[breadth] = ResultData(depth, breadth, node.data)
    }
}

fun traverse(node: Node, depth: Int, breadth: Int, sortedMap: SortedMap<Int, ResultData>) {
    if (sortedMap.isEmpty()) {
        sortedMap[breadth] = ResultData(depth, breadth, node.data)
    }

    bfs(node.left, depth + 1, breadth - 1, sortedMap)
    bfs(node.right, depth + 1 , breadth + 1, sortedMap)

    if (node.left != null) {
        traverse(node.left!!, depth + 1, breadth - 1, sortedMap)
    }
    if (node.right != null) {
        traverse(node.right!!, depth + 1, breadth + 1, sortedMap)
    }
}


fun main(args: Array<String>) {
    //val scan = Scanner(System.`in`)
    var t: Int = 50
    var root: Node? = null

    val values = arrayOf(
        //1, 14, 3, 7, 4, 5, 15, 6, 13, 10, 11, 2, 12, 8, 9
        47, 2, 40, 20, 38, 30, 14, 28, 10, 16, 19, 44, 39, 27, 7, 9, 31, 12, 43, 21, 5, 41, 34, 49, 13, 33, 3, 4, 25, 22, 29, 15, 32, 35, 6, 24, 23, 26, 1, 11, 42, 36, 37, 17, 18, 8, 45, 48, 50, 46,
    )

    for (data in values) {
        root = insert(root, data)
    }

    topView(root!!)
}

fun insert(root: Node?, data: Int): Node {
    return if (root == null) {
        Node(data)
    } else {
        val cur: Node
        if (data <= root.data) {
            cur = insert(root.left, data)
            root.left = cur
        } else {
            cur = insert(root.right, data)
            root.right = cur
        }
        root
    }
}

class Node(var data: Int) {
    var left: Node? = null
    var right: Node? = null
}