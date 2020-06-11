import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => x(0) * x(0) + x(1) * x(1) / 4 - 1,
    (x: Vector[Double]) => (x(0) + 0.1) * (x(0) + 0.1) + (x(1) + 1) * (x(1) + 1) - 1
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix(
    ((x: Vector[Double]) => 2 * x(0), (x: Vector[Double]) => x(1) / 2),
    ((x: Vector[Double]) => 2 * (x(0) + 0.1), (x: Vector[Double]) => 2 * (x(1) + 1))
  )
}
