package kafka.stream.wordcount

import java.util.Properties
import java.util.concurrent.TimeUnit

import Constants.{brokers, sentenceProducerTopic, wordCountResultTopic}
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.{KafkaStreams, StreamsConfig}
import org.slf4j.LoggerFactory

object WordCountApplication extends App {
  import Serdes._

  val logger = LoggerFactory.getLogger(getClass)

  val props: Properties = {
    val p = new Properties()
    p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-application")
    p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, brokers)
    p.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    p.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String.getClass)
    p.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String.getClass)
    p
  }

  val builder: StreamsBuilder = new StreamsBuilder
  val textLines: KStream[String, String] = builder.stream[String, String](sentenceProducerTopic)

  // val textLinesInTable: KTable[String, String] = builder.table[String, String](sentenceProducerTopic) -> if you want to create KTable

  //textLines.map((key, value) => (key.toLowerCase(), value.toLowerCase())) => if you want to change both key and value

  val value: KStream[String, String] = textLines
    //.mapValues(textLine => textLine.toLowerCase()) => could have been done like this as well
    .flatMapValues(textLine => textLine.toLowerCase().split("\\W+"))

  // Another way: value.selectKey((_, word) => word).groupByKey.count() -> selectKey changes the key here.

  val wordCounts: KTable[String, Long] = value
    .groupBy((_, word) => word)
    .count()

  wordCounts.toStream.to(wordCountResultTopic)
  val streams: KafkaStreams = new KafkaStreams(builder.build(), props)
  streams.cleanUp()
  streams.start()

  sys.ShutdownHookThread {
    streams.close(100, TimeUnit.SECONDS)
  }
}
