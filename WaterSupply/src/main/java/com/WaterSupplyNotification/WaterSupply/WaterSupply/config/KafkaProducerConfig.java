
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import com.WaterSupplyNotification.WaterSupply.WaterSupply.entity.WaterSupplyEvent;
import com.fasterxml.jackson.databind.ser.std.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


@Configuration
public  class KafkaProducerConfig{

    @Bean
    public ProducerFactory<String, WaterSupplyEvent> producerFactory() {
    Map<String, Object> config = new HashMap<>();
    config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
    config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
    config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, false); // Important!
    return new DefaultKafkaProducerFactory<>(config);
}


    @Bean
    public KafkaTemplate<String,WaterSupplyEvent> kafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
}