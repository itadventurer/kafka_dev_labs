package common;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class TransactionSerde implements Serde<TransactionEvent> {

    public TransactionSerde() {
    }

    public static class TransactionSerializer implements Serializer<TransactionEvent> {
        private final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        @Override
        public byte[] serialize(String topic, TransactionEvent data) {
            return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public Serializer<TransactionEvent> serializer() {
        return new TransactionSerializer();
    }

    public static class TransactionDeserializer implements Deserializer<TransactionEvent> {
        private final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        @Override
        public TransactionEvent deserialize(String topic, byte[] data) {
            if (data == null) return null;
            return gson.fromJson(new String(data, StandardCharsets.UTF_8), TransactionEvent.class);
        }
    }

    @Override
    public Deserializer<TransactionEvent> deserializer() {
        return new TransactionDeserializer();
    }
}

