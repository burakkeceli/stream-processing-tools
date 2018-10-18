package setup

import java.util
import java.util.Properties

import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.{AdminClient, NewTopic}

import scala.collection.JavaConverters

object KafkaAdminClient {
  def main(args: Array[String]) {
    val topicProperties: Properties = new Properties()
    topicProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers)
    createTopic

    def createTopic = {
      println("Topic will be created")
      AdminClient.create(topicProperties).createTopics(util.Arrays.asList(getNewTopic(wordCountResultTopic), getNewTopic(sentenceProducerTopic)))
      println("Topic is created")
    }

    def getNewTopic(topic: String) = {
      val partitions = 3
      val replication: Short = 1
      new NewTopic(topic, partitions, replication).configs(JavaConverters.mapAsJavaMap(Map()))
    }
  }
}
