import config.Config

package object setup {
  val brokers : String = Config().getString("brokers")
  val wordCountResultTopic : String = Config().getString("result-topic")
  val sentenceProducerTopic : String = Config().getString("sentence-producer-topic")
}