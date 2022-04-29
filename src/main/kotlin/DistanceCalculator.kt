import CovMatrixHelper.getCovMatrix
import org.ejml.simple.SimpleMatrix
import kotlin.math.pow
import kotlin.math.sqrt

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