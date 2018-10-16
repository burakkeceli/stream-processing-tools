package setup

import java.time.Duration
import java.util.Properties

import config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters.{asJavaCollection, asScalaIterator}

object Consumer extends App {

  private val groupId = Config().getString("consumer.groupId")
  private val props = consumerConfig(brokers, groupId)

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(asJavaCollection(List(wordCountResultTopic)))

  private def consumerConfig(brokers: String, groupId: String): Properties = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(GROUP_ID_CONFIG, groupId)
    props.put(ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props
  }

  while (true) {
    val polledValues: ConsumerRecords[String, String] = consumer.poll(Duration.ofSeconds(100))
    for (record <- asScalaIterator(polledValues.iterator())) {
      println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset())
    }
  }
}