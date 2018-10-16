package setup

import java.util.Properties

import org.apache.kafka.clients.producer.ProducerConfig.{BOOTSTRAP_SERVERS_CONFIG, KEY_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER_CLASS_CONFIG}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import org.apache.kafka.common.serialization.StringSerializer

object Producer extends App {

  private val props = producerConfig(brokers)
  val producer = new KafkaProducer[String, String](props)
  sendData

  private def sendData = {
    var counter = 0
    val data = new ProducerRecord[String, String](sentenceProducerTopic, null, "Ali veli 49 50 Ali veli 123 asd")
    producer.send(data)
    println("Data has been sent")
    counter += 1
  }

  def producerConfig(brokers: String): Properties = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS_CONFIG, brokers)
    props.put(KEY_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props.put(VALUE_SERIALIZER_CLASS_CONFIG, classOf[StringSerializer])
    props
  }
}