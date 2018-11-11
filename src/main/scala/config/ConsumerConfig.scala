package config

import java.util.Properties

import Constants.{brokers, groupId}
import org.apache.kafka.clients.consumer.ConsumerConfig._
import org.apache.kafka.common.serialization.{LongDeserializer, StringDeserializer}

object ConsumerConfig {

  val props = consumerConfig(brokers, groupId)

  private def consumerConfig(brokers: String, groupId: String): Properties = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(GROUP_ID_CONFIG, groupId)
    props.put(ENABLE_AUTO_COMMIT_CONFIG, "true")
    props.put(AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
    props.put(SESSION_TIMEOUT_MS_CONFIG, "30000")
    props.put(KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(VALUE_DESERIALIZER_CLASS_CONFIG, classOf[LongDeserializer])
    props
  }
}