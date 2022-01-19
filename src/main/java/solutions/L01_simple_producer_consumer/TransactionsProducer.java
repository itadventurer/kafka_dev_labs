package solutions.L01_simple_producer_consumer;

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
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                TransactionSerde.TransactionSerializer.class);

        try (Producer<String, TransactionEvent> producer = new KafkaProducer<>(props)) {
            final String TOPIC = "transactions";
            Stream<Map.Entry<String, TransactionEvent>> transactions =
                    Stream.generate(new RandomTransactionsSupplier());
            transactions.forEach(transactionEntry -> {
                ProducerRecord<String, TransactionEvent> producerRecord =
                        new ProducerRecord<>(TOPIC,
                                transactionEntry.getKey(),
                                transactionEntry.getValue());
                producer.send(producerRecord);
            });
        }
    }
}
