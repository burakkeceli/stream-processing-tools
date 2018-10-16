package kafka.stream

import java.util.Properties
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import java.util.regex.Pattern
import org.apache.kafka.streams.kstream.KStream

object WordCount {

  def main(args: Array[String]) {
    val config: Properties = {
      val p = new Properties()
      p.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-scala-application")
      p.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
      p
    }

    val inputTopic = "word-length-input"

    val builder = new StreamsBuilder()
    val textLines: KStream[String, String] = builder.stream(inputTopic)
    val pattern = Pattern.compile("\\W+", Pattern.UNICODE_CHARACTER_CLASS)

    //val wordCounts = textLines
      //.mapValues[String](value => value.substring(1))

    def convert(string: String): Int = 6

    //wordCounts.toStream.to("word-length-output")

    val streams: KafkaStreams = new KafkaStreams(builder.build(), config)
    streams.cleanUp()
    streams.start()
  }
}
