import breeze.linalg.sum

object NewtonMethod {
  def findSolution(approx: Vector[Double], eps: Double): (Vector[Double], Int) = {
    var x = approx
    var iterations = 0

    var norm = Double.MaxValue

    while (norm > eps) {
      val jApplied = Assignment.J.map(f => f(x))
      val fApplied = -Assignment.F.map(f => f(x))

      val delta_k = jApplied \ fApplied
      norm = Math.sqrt(sum(delta_k.map(x => x * x)))

      x = delta_k
        .toArray
        .zipWithIndex
        .map {
          case (v, i) => v + x(i)
        }
        .toVector

      iterations = iterations + 1
    }

    (x, iterations)
  }
}
