package kafka.stream.wordcount

import Constants.sentenceProducerTopic
import config.ProducerConfig.props
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.slf4j.LoggerFactory

object WordCountProducer extends App {

  private val logger = LoggerFactory.getLogger(getClass)
  val producer = new KafkaProducer[String, String](props)
  sendData

  private def sendData = {
    val data = new ProducerRecord[String, String](sentenceProducerTopic, null, "Word count using kafka streams. Kafka count is 2")
    while (true) {
      producer.send(data)
      logger.info("Data has been sent")
      Thread.sleep(1000)
    }
  }
}