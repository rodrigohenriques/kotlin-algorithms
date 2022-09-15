class Solution {
    class QuickUnionPathCompression(size: Int) {
        private val root: Array<Int>
        private val rank: Array<Int>

        init {
            root = Array(size) { it }
            rank = Array(size) { 1 }
        }

        fun find(a: Int): Int {
            val rootA = root[a]

            if (rootA == a) return rootA

            root[a] = find(rootA)

            return root[a]
        }

        fun union(a: Int, b: Int) {
            val aRoot = find(a)
            val bRoot = find(b)

            if (aRoot == bRoot) {
                return
            }

            if (rank[aRoot] < rank[bRoot]) {
                root[aRoot] = bRoot
            } else if (rank[aRoot] > rank[bRoot]) {
                root[bRoot] = aRoot
            } else {
                root[bRoot] = aRoot
                rank[aRoot]++
            }
        }

        fun connected(a: Int, b: Int): Boolean {
            return find(a) == find(b)
        }
    }

    fun findCircleNum(isConnected: Array<IntArray>): Int {
        val n = isConnected.size
        val quickUnion = QuickUnionPathCompression(n)

        for (i in 0 until n) {
            for (j in 0 until n) {
                if (i == j) continue
                if (isConnected[i][j] == 0) continue
                if (quickUnion.connected(i, j)) continue

                quickUnion.union(i, j)
            }
        }

        val roots = mutableSetOf<Int>()

        for (i in 0 until n) {
            val root = quickUnion.find(i)
            roots.add(root)
        }

        return roots.size
    }
}

fun main() {
    val s = Solution()
    println(
        s.findCircleNum(
            arrayOf(
                intArrayOf(1, 1, 0),
                intArrayOf(1, 1, 0),
                intArrayOf(0, 0, 1),
            )
        )
    )
}