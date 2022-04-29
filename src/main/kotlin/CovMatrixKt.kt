import org.ejml.simple.SimpleMatrix

object CovMatrixKt {

    fun getCovMatrix(cluster: Cluster, invert: Boolean): SimpleMatrix {
        val d: List<Point> = cluster.elements
        val count = d.size
        val data = Array(count) { DoubleArray(3) }
        for (row in 0 until count) {
            data[row] = doubleArrayOf(d[row].x, d[row].y, d[row].z)
        }
        val X = SimpleMatrix(data)
        val n = X.numRows()
        val Xt = X.transpose()
        val m = Xt.numRows()

        // Means:
        val x = SimpleMatrix(m, 1)
        for (r in 0 until m) {
            x.set(r, 0, Xt.extractVector(true, r).elementSum() / n)
        }

        // Covariance matrix:
        val S = SimpleMatrix(m, m)
        for (r in 0 until m) {
            for (c in 0 until m) {
                if (r > c) {
                    S.set(r, c, S.get(c, r))
                } else {
                    val cov = Xt.extractVector(true, r)
                        .minus(x[r, 0])
                        .dot(
                            Xt.extractVector(true, c)
                                .minus(x[c, 0])
                                .transpose()
                        )
                    S.set(r, c, (cov / n))
                }
            }
        }
        return if (invert) S.pseudoInverse() else S
    }
}