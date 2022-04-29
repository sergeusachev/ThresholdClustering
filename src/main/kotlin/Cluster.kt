class Cluster {
    val elements: MutableList<Point> = mutableListOf()

    fun add(point: Point) = elements.add(point)

    fun center(): Point {
        val count = elements.size
        val centerX = elements.sumOf { it.x } / count
        val centerY = elements.sumOf { it.y } / count
        val centerZ = elements.sumOf { it.z } / count

        return Point(centerX, centerY, centerZ)
    }
}