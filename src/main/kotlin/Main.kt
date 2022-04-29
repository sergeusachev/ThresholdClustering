import CovMatrixKt.getCovMatrix
import org.ejml.simple.SimpleMatrix
import java.util.*
import kotlin.math.pow
import kotlin.math.sqrt

class Point(
    val x: Double,
    val y: Double,
    val z: Double
) {

    override fun toString() = "(${x.toInt()}, ${y.toInt()}, ${z.toInt()})"
}

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

interface DistanceCalculator {
    fun distance(
        cluster: Cluster,
        point: Point
    ): Double
}

class EuclideanDistanceCalculator : DistanceCalculator {

    override fun distance(cluster: Cluster, point: Point): Double {
        val clusterCenter = cluster.center()
        return sqrt(
            (clusterCenter.x - point.x).pow(2) +
                    (clusterCenter.y - point.y).pow(2) +
                    (clusterCenter.z - point.z).pow(2)
        )
    }
}

class MahalanobisDistanceCalculator : DistanceCalculator {

    private val euclideanDistanceCalculator = EuclideanDistanceCalculator()

    override fun distance(cluster: Cluster, point: Point): Double {
        return if (cluster.elements.size > 1) {
            val clusterCenter = cluster.center()
            val invertedCovMatrix = getCovMatrix(cluster, true)
            val mData = arrayOf(
                doubleArrayOf(point.x - clusterCenter.x),
                doubleArrayOf(point.y - clusterCenter.y),
                doubleArrayOf(point.z - clusterCenter.z)
            )
            val m = SimpleMatrix(mData)
            val transposedM = m.transpose()
            val mahalanobisMatrix = transposedM.mult(invertedCovMatrix).mult(m)
            sqrt(mahalanobisMatrix.get(0, 0))
        } else {
            euclideanDistanceCalculator.distance(cluster, point)
        }
    }
}

fun main() {
    val scanner = Scanner(System.`in`)
    val dataSet = getDataSet()
    println("Какое расстояние будем использовать?")
    println("1 - Евклидово, 2 - Махаланобиса")
    val distanceType = scanner.next().toInt()
    val distanceCalculator: DistanceCalculator = when (distanceType) {
        1 -> EuclideanDistanceCalculator()
        2 -> MahalanobisDistanceCalculator()
        else -> throw RuntimeException()
    }
    println("Введите пороговое значение расстояния:")
    val threshold = scanner.next().toDouble()
    val result = thresholdClustering(distanceCalculator, dataSet, threshold)
    println("--- Результаты кластеризации ---")
    result.forEachIndexed { index, cluster ->
        println("Кластер №${index + 1} (${cluster.elements.size} элементов)")
        println(cluster.elements.joinToString())
        println()
    }
}

fun thresholdClustering(
    dstCalculator: DistanceCalculator,
    dataSet: Array<Point>,
    threshold: Double
): List<Cluster> {
    val clusters = mutableListOf<Cluster>()
    clusters.add(Cluster())
    clusters[0].add(dataSet[0])

    for (i in 1 until dataSet.size) {
        var minDistance = dstCalculator.distance(
            clusters[0],
            dataSet[i]
        )
        var clusterIndex = 0
        clusters.forEachIndexed { index, cluster ->
            val currentDistance = dstCalculator.distance(
                cluster,
                dataSet[i]
            )
            if (currentDistance < minDistance) {
                minDistance = currentDistance
                clusterIndex = index
            }
        }
        if (minDistance > threshold) {
            val newCluster = Cluster()
            newCluster.add(dataSet[i])
            clusters.add(newCluster)
        } else {
            clusters[clusterIndex].add(dataSet[i])
        }
    }

    return clusters
}



