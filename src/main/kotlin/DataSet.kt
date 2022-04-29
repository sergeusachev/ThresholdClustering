class Point(
    val x: Double,
    val y: Double,
    val z: Double
) {
    override fun toString() = "(${x.toInt()}, ${y.toInt()}, ${z.toInt()})"
}

fun getDataSet() = arrayOf(
    Point(15.0, 6.0, 15.0),
    Point(1.0, 19.0, 0.0),
    Point(-8.0, -1.0, 4.0),
    Point(13.0, 19.0, 15.0),
    Point(15.0, 17.0, -14.0),
    Point(-3.0, 9.0, -35.0),
    Point(12.0, 4.0, 16.0),
    Point(8.0, 14.0, 9.0),
    Point(-6.0, 0.0, 5.0),
    Point(11.0, 17.0, 10.0),
    Point(12.0, 17.0, -10.0),
    Point(-1.0, 10.0, -25.0),
    Point(18.0, 17.0, -11.0),
    Point(-4.0, 9.0, -31.0),
    Point(19.0, 4.0, 13.0),
    Point(8.0, 14.0, 10.0),
    Point(-6.0, -5.0, 1.0),
    Point(20.0, 20.0, 20.0),
    Point(7.0, 16.0, -17.0),
    Point(-1.0, 7.0, -26.0),
    Point(15.0, 1.0, 10.0),
    Point(0.0, 11.0, 8.0),
    Point(-8.0, -1.0, 5.0),
    Point(10.0, 10.0, 10.0),
    Point(12.0, 15.0, -10.0),
    Point(-4.0, 5.0, -27.0),
    Point(-7.0, -1.0, 4.0),
    Point(3.0, 17.0, 11.0),
    Point(0.0, 1.0, 3.0),
    Point(7.0, 2.0, 0.0),
)