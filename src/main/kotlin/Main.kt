import java.util.*

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



