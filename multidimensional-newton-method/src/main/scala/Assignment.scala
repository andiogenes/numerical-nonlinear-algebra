import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => x.head * x.head - 25,
    (x: Vector[Double]) => x.head * x.head * x.head + 125
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix(
    ((x: Vector[Double]) => 2 * x.head, (x: Vector[Double]) => 3 * x.head * x.head)
  ).t
}
