import config.Config

package object setup {
  val brokers : String = Config().getString("consumer.brokers")
  val topicName : String = Config().getString("consumer.topic")
}