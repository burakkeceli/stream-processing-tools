package setup

import java.util.Properties

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig.{BOOTSTRAP_SERVERS_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG}
import org.apache.kafka.common.serialization.StringSerializer

object Producer {

  private val props = producerConfig(brokers)
  val producer = new KafkaProducer[String, String](props)

  def producerConfig(brokers: String): Properties = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props
  }
}