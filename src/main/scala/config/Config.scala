package config

import com.typesafe.config._

object Config {

  private val env = if (System.getenv("SCALA_ENV") == null) "local" else System.getenv("SCALA_ENV")
  private val conf = ConfigFactory.load()
  def apply() = conf.getConfig(env)
}