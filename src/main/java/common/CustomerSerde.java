package common;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;

public class CustomerSerde implements Serde<Customer> {

    public CustomerSerde() {
    }

    public static class CustomerSerializer implements Serializer<Customer> {
        private final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        @Override
        public byte[] serialize(String topic, Customer data) {
            return gson.toJson(data).getBytes(StandardCharsets.UTF_8);
        }
    }

    @Override
    public Serializer<Customer> serializer() {
        return new CustomerSerde.CustomerSerializer();
    }

    public static class CustomerDeserializer implements Deserializer<Customer> {
        private final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        @Override
        public Customer deserialize(String topic, byte[] data) {
            if (data == null) return null;
            return gson.fromJson(new String(data, StandardCharsets.UTF_8), Customer.class);
        }
    }

    @Override
    public Deserializer<Customer> deserializer() {
        return new CustomerSerde.CustomerDeserializer();
    }
}

