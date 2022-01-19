package L01_simple_producer_consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class PrintConsumer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // What do you need to configure here?
        props.put(ConsumerConfig.??, ??);

        // Initialize the consumer
        final Consumer<String, String> consumer = ??;

        try (consumer) {
            //subscribe to Topic(s)
            
            System.out.println("Started");
            // while what?
            while (??) {
                // get the records
                ConsumerRecords<String, String> records = consumer.??
                for (??) {
                    System.out.println(key + ": " + value);
                }
            }
        }
    }
}
