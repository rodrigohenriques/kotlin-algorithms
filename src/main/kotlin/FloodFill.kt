fun floodFill(image: Array<IntArray>, sr: Int, sc: Int, color: Int): Array<IntArray> {
    val R = image.size
    val C = image[0].size

    val queue = ArrayDeque<Pair<Int, Int>>()

    queue.add(Pair(sr, sc))

    val colorToFill = image[sr][sc]

    if (colorToFill == color) {
        return image
    }

    while (queue.isNotEmpty()) {
        val (i, j) = queue.removeFirst()

        if (i < 0) continue
        if (i >= R) continue
        if (j < 0) continue
        if (j >= C) continue
        if (image[i][j] != colorToFill) continue

        queue.add(Pair(i - 1, j))
        queue.add(Pair(i + 1, j))
        queue.add(Pair(i, j - 1))
        queue.add(Pair(i, j + 1))

        image[i][j] = color
    }

    return image
}

fun main() {
    val image = arrayOf(
        intArrayOf(0, 0, 0),
        intArrayOf(0, 0, 0)
    )
    val sr = 1
    val sc = 0
    val color = 2
    val result = floodFill(image, sr, sc, color)

    println(result.joinToString("\n") { it.joinToString(", ") })
}