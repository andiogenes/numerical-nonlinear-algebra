import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => x(0) * x(0) + x(1) * x(1) - 25,
    (x: Vector[Double]) => 5 + x(1)
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix(
    ((x: Vector[Double]) => x.head * 2, (x: Vector[Double]) => x.head * 2),
    ((x: Vector[Double]) => 0.0, (x: Vector[Double]) => 1.0)
  )
}
