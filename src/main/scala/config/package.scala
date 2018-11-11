import config.EnvConfig

package object Constants {
  val brokers : String = EnvConfig().getString("brokers")
  val groupId : String = EnvConfig().getString("groupId")
  val wordCountResultTopic : String = EnvConfig().getString("word-count-result-topic")
  val sentenceProducerTopic : String = EnvConfig().getString("sentence-producer-topic")
}