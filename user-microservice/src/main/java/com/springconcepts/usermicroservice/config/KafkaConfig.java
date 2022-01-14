package com.springconcepts.usermicroservice.config;

import com.springconcepts.usermicroservice.model.shared.NewOrderEvent;
import com.springconcepts.usermicroservice.model.shared.OrderPaidEvent;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
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

  public Map<String, Object> getKafkaProps() {
    var props = new HashMap<String, Object>();
    props.put("bootstrap.servers", servers);
    props.put("security.protocol", "SASL_SSL");
    props.put("sasl.mechanism", "SCRAM-SHA-256");
    String jaasTemplate =
        "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
    String jaasCfg = String.format(jaasTemplate, username, password);
    props.put("sasl.jaas.config", jaasCfg);
    props.put("group.id", username + "-consumers");
    props.put("spring.json.trusted.packages", "com.springconcepts.usermicroservice.model.shared");
    props.put("spring.json.use.type.headers", "false");
    return props;
  }

  @Bean
  public ConsumerFactory<String, NewOrderEvent> newOrderEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        getKafkaProps(), new StringDeserializer(), new JsonDeserializer<>(NewOrderEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, NewOrderEvent>
      newOrderKafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, NewOrderEvent>();
    factory.setConsumerFactory(newOrderEventConsumerFactory());
    return factory;
  }

  @Bean
  public ConsumerFactory<String, OrderPaidEvent> orderPaidEventConsumerFactory() {
    return new DefaultKafkaConsumerFactory<>(
        getKafkaProps(), new StringDeserializer(), new JsonDeserializer<>(OrderPaidEvent.class));
  }

  @Bean
  public ConcurrentKafkaListenerContainerFactory<String, OrderPaidEvent>
      orderPaidKafkaListenerContainerFactory() {
    var factory = new ConcurrentKafkaListenerContainerFactory<String, OrderPaidEvent>();
    factory.setConsumerFactory(orderPaidEventConsumerFactory());
    return factory;
  }
}
