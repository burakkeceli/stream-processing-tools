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
      AdminClient.create(topicProperties).createTopics(util.Arrays.asList(getNewTopic))
    }

    def getNewTopic = {
      val partitions = 3
      val replication: Short = 1
      new NewTopic(wordCountResultTopic, partitions, replication).configs(JavaConverters.mapAsJavaMap(Map()))
    }
  }
}
