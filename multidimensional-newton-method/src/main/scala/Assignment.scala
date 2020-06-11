import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => x.head * x.head + (x(1) - 0.5) * (x(1) - 0.5) + x(2) * x(2) - 1,
    (x: Vector[Double]) => x.head * x.head + (x(1) + 0.5) * (x(1) + 0.5) + x(2) * x(2) - 1,
    (x: Vector[Double]) => x.head - x(1)
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix(
    ((x: Vector[Double]) => 2 * x.head, (x: Vector[Double]) => 2 * (x(1) - 0.5), (x: Vector[Double]) => 2 * x(2)),
    ((x: Vector[Double]) => 2 * x.head, (x: Vector[Double]) => 2 * (x(1) + 0.5), (x: Vector[Double]) => 2 * x(2)),
    ((_: Vector[Double]) => 1d, (_: Vector[Double]) => -1d, (_: Vector[Double]) => 0d)
  )
}
