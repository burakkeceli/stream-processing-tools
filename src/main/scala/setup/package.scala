import config.Config

package object setup {
  val brokers : String = Config().getString("brokers")
  val wordCountResultTopic : String = Config().getString("word-count-result-topic")
  val sentenceProducerTopic : String = Config().getString("sentence-producer-topic")
}