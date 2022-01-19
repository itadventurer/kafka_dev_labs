package L01_simple_producer_consumer;

import common.RandomTransactionsSupplier;
import common.TransactionEvent;
import common.TransactionSerde;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Stream;

public class TransactionsProducer {
    public static void main(final String[] args) {
        final Properties props = new Properties();
        // What do you need to configure?


        // How to initialize the producer?
        Producer<String, TransactionEvent> producer = ??
        try () {
            // This is an infinite Stream of transactions
            // Check the common package for more information about the TransactionEvents
            Stream<Map.Entry<String, TransactionEvent>> transactions =
                    Stream.generate(new RandomTransactionsSupplier());

            transactions.forEach(transactionEntry -> {
                // Build a record and send it
            });
        } finally {
            // DO NOT FORGET TO Close
            producer.close();
        }
    }
}
