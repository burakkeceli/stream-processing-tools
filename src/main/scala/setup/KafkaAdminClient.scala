package setup

import java.util.Arrays.asList
import java.util.Properties

import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.{AdminClient, CreateTopicsResult, NewTopic}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters

object KafkaAdminClient {
  val topicProperties: Properties = new Properties()
  topicProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers)

  def main(args: Array[String]) {
    createTopicList()
  }

  private def createTopicList() : Unit = {
    println("Topics will be created")
    val result: CreateTopicsResult = AdminClient
      .create(topicProperties)
      .createTopics(
        asList(
          getNewTopic(wordCountResultTopic),
          getNewTopic(sentenceProducerTopic)))

    for (entry <- result.values.entrySet) {
      try {
        entry.getValue.get
        println(s"topic ${entry.getKey} created")
      } catch {
        case _: Throwable =>
          println("Unable to create topic")
      }
    }
  }

  private def getNewTopic(topic: String) = {
    val partitions = 3
    val replication: Short = 1
    new NewTopic(topic, partitions, replication).configs(JavaConverters.mapAsJavaMap(Map()))
  }
}
