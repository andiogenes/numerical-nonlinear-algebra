import com.jakewharton.fliptables.FlipTable

import scala.io.StdIn.readLine

object Entry extends App {
  val approx = readLine("Введите приближение: ")
    .drop(1)
    .dropRight(1)
    .replaceAllLiterally(" ", "")
    .split(',')
    .map(v => v.toDouble)
    .toVector

  val eps = readLine("Введите точность: ").toDouble

  val solution = NewtonMethod.findSolution(approx, eps)

  val headers = Array("Решение", "Невязка")
  val data = Array(
    Array(
      Utils.formatVector(solution),
      Utils.formatVector(Assignment.F
        .map(f => f(solution))
        .toDenseVector
        .toScalaVector)
    )
  )

  println(FlipTable.of(headers, data))
}

object Utils {
  /**
   * Конвертирует вектор в строку в формате (x0, x1, ..., xn)
   *
   * @param vector Вектор
   * @return Форматированная строка
   */
  def formatVector(vector: Vector[Any]): String =
    s"(${vector.tail.foldLeft(vector.head.toString) { (x, y) => s"$x, $y" }})"
}