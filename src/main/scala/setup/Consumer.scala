package setup

import java.util.Properties

import config.Config
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters.asJavaCollection

object Consumer {

  private val groupId = Config().getString("consumer.groupId")
  private val props = consumerConfig(brokers, groupId)

  val consumer = new KafkaConsumer[String, String](props)
  consumer.subscribe(asJavaCollection(List(topicName)))

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
}