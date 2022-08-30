package com.springconcepts.ordermicroservice.config;

import com.springconcepts.sharedmodel.OrderEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

  @Value("${CLOUDKARAFKA_SERVERS}")
  private String servers;

  @Value("${CLOUDKARAFKA_USERNAME}")
  private String username;

  @Value("${CLOUDKARAFKA_PASSWORD}")
  private String password;

  @Bean
  public Map<String, Object> getKafkaProps() {
    var props = new HashMap<String, Object>();
    props.put("bootstrap.servers", servers);
    props.put("key.serializer", StringSerializer.class);
    props.put("value.serializer", JsonSerializer.class);
    props.put("security.protocol", "SASL_SSL");
    props.put("sasl.mechanism", "SCRAM-SHA-256");
    String jaasTemplate =
        "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    String jaasCfg = String.format(jaasTemplate, username, password);
    props.put("sasl.jaas.config", jaasCfg);
    return props;
  }

  @Bean
  public ConsumerFactory<String, OrderEvent> newOrderEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
            getKafkaProps(), new StringDeserializer(), new JsonDeserializer<>(OrderEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, OrderEvent>
  orderKafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderEvent>();
    factory.setConsumerFactory(newOrderEventConsumerFactory());
    return factory;
  }

  @Bean
  public KafkaTemplate<String, Object> kafkaTemplate() {
    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(getKafkaProps()));
  }
}
