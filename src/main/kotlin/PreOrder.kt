import java.util.Stack

private val nodes = mapOf(
    1 to listOf(2, 3, 4),
    2 to emptyList(),
    3 to listOf(5, 6),
    4 to emptyList(),
    5 to emptyList(),
    6 to emptyList(),
)

fun main() {
    println(dfs(1).joinToString(", ") { "$it" })
    println(dfs2(1).joinToString(", ") { "$it" })
    println(bfs(1).joinToString(", ") { "$it" })
}

fun dfs(node: Int, output: MutableList<Int> = mutableListOf()): List<Int> {
    val edges = nodes[node] ?: return output.also { it.add(node) }

    for (e in edges) {
        dfs(e, output)
    }
    return output.also { it.add(node) }
}

fun dfs2(node: Int): List<Int> {
    val result = mutableListOf<Int>()

    val stack = Stack<Int>()

    stack.add(node)

    while (stack.isNotEmpty()) {
        val top = stack.peek()


        val ints = nodes[top]
        ints?.forEach { stack.push(it) }

    }

    return result
}

fun bfs(node: Int): List<Int> {
    val result = mutableListOf<Int>()

    val queue = ArrayDeque<Int>()

    queue.add(node)

    while (queue.isNotEmpty()) {
        val current = queue.removeFirst()
        result.add(current)
        nodes[current]?.forEach { queue.add(it) }
    }

    return result
}