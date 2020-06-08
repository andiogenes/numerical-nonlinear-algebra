import breeze.linalg.{DenseMatrix, DenseVector}

object Assignment {
  /**
   * Столбец f_i(x)
   */
  val F: DenseMatrix[Vector[Double] => Double] = DenseVector(
    (x: Vector[Double]) => x.head,
    (x: Vector[Double]) => x.head
  ).toDenseMatrix.t

  /**
   * Матрица Якоби, соответствующая набору функций F.
   */
  val J: DenseMatrix[Vector[Double] => Double] = DenseMatrix(
    ((x: Vector[Double]) => x.head, (x: Vector[Double]) => x.head),
    ((x: Vector[Double]) => x.head, (x: Vector[Double]) => x.head)
  )
}
