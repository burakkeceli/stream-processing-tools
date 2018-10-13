package setup

import java.util
import java.util.Properties

import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.{AdminClient, NewTopic}

import scala.collection.JavaConverters

object KafkaAdminClient {

  private val topicProperties : Properties = new Properties()
  topicProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers)

  def createTopic = {
    AdminClient.create(topicProperties).createTopics(util.Arrays.asList(getNewTopic))
  }

  private def getNewTopic = {
    val partitions = 1
    val replication : Short = 1
    new NewTopic(topicName, partitions, replication).configs(JavaConverters.mapAsJavaMap(Map()))
  }
}
