package kafka.stream.wordcount

import java.time.Duration

import config.ConsumerConfig.props
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters.{asJavaCollection, asScalaIterator}

object WordCountConsumer extends App {

  private val logger = LoggerFactory.getLogger(getClass)
  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(asJavaCollection(List(Constants.wordCountResultTopic)))

  while (true) {
    val polledValues: ConsumerRecords[String, String] = consumer.poll(Duration.ofSeconds(100))
    for (record <- asScalaIterator(polledValues.iterator())) {
      logger.info(s"Received message: (${record.key()} , ${record.value()}) at offset ${record.offset()})")
    }
  }
}