import java.time.Duration
import java.util.UUID

import org.apache.kafka.clients.consumer.ConsumerRecords
import org.apache.kafka.clients.producer.ProducerRecord
import setup.Consumer.consumer
import setup.Producer.producer
import setup.{KafkaAdminClient, topicName}

import scala.collection.JavaConverters.asScalaIterator

object Main {

  def main(args: Array[String]) {
    KafkaAdminClient.createTopic

    val data = new ProducerRecord[String, String](topicName, null, UUID.randomUUID().toString)
    producer.send(data)

    val polledValues: ConsumerRecords[String, String] = consumer.poll(Duration.ofSeconds(100))
    for (record <- asScalaIterator(polledValues.iterator())) {
      println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset())
    }
  }
}
