import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => Math.sin(x.head),
    (x: Vector[Double]) => Math.sin(x.head * 2),
    (x: Vector[Double]) => Math.sin(x.head * 4)
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix((
    (x: Vector[Double]) => Math.cos(x.head),
    (x: Vector[Double]) => Math.cos(2 * x.head) * 2,
    (x: Vector[Double]) => Math.cos(x.head * 4) * 4
  )).t
}
