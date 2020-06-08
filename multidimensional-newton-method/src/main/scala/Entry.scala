import java.beans.BeanProperty
import java.io.{File, FileInputStream}

import org.yaml.snakeyaml.Yaml
import org.yaml.snakeyaml.constructor.Constructor

object Entry extends App {
  val options = CliParser.parse(args)

  val source = options(CliParser.Source).toString
  val destination = options(CliParser.Destination).toString

  /**
   * Загружает YAML-данные из `filename` в формате `Config`
   *
   * @param filename Относительный путь к файлу
   * @return Объект с загруженными данными
   */
  def loadData(filename: String) = {
    val input = new FileInputStream(new File(filename))
    val yaml = new Yaml(new Constructor(classOf[Config]))

    yaml.load(input).asInstanceOf[Config]
  }

  val config = loadData(source)

  val eps = config.eps

  println(eps)
}

class Config {
  @BeanProperty var eps: Double = 0
}