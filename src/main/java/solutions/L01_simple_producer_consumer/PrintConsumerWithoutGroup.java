package solutions.L01_simple_producer_consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PrintConsumerWithoutGroup {
    public static void main(final String[] args) throws IllegalStateException {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "nevermind");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        final String TOPIC = "transactions";

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        List<PartitionInfo> partitionInfos = consumer.partitionsFor(TOPIC);

        if (partitionInfos == null) {
            throw new IllegalStateException("Cannot connect or topic does not exist");
        }

        List<TopicPartition> topicPartitions = partitionInfos.stream()
                .map(partitionInfo -> new TopicPartition(partitionInfo.topic(), partitionInfo.partition()))
                .collect(Collectors.toList());


        try (consumer) {
            consumer.assign(topicPartitions);
            System.out.println("Started…");
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    String key = record.key();
                    String value = record.value();
                    System.out.println(key + ": " + value);
                }
            }
        }
    }
}
