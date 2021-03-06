package setup

import java.util.Arrays.asList
import java.util.Properties

import Constants.{brokers, sentenceProducerTopic, wordCountResultTopic}
import org.apache.kafka.clients.admin.AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG
import org.apache.kafka.clients.admin.{AdminClient, CreateTopicsResult, NewTopic}
import org.slf4j.LoggerFactory

import scala.collection.JavaConversions._
import scala.collection.JavaConverters

object KafkaAdminClient extends App {
  private val logger = LoggerFactory.getLogger(getClass)

  val topicProperties: Properties = new Properties()
  topicProperties.setProperty(BOOTSTRAP_SERVERS_CONFIG, brokers)
  createTopicList()

  private def createTopicList() : Unit = {
    logger.info("Topics will be created")
    val result: CreateTopicsResult = AdminClient
      .create(topicProperties)
      .createTopics(
        asList(
          getNewTopic(wordCountResultTopic),
          getNewTopic(sentenceProducerTopic)))

    for (entry <- result.values.entrySet) {
      try {
        entry.getValue.get
        logger.info(s"topic ${entry.getKey} created")
      } catch {
        case _: Throwable =>
          logger.warn(s"Unable to create topic ${entry.getKey}")
      }
    }
  }

  private def getNewTopic(topic: String) = {
    val partitions = 3
    val replication: Short = 1
    new NewTopic(topic, partitions, replication).configs(JavaConverters.mapAsJavaMap(Map()))
  }
}
