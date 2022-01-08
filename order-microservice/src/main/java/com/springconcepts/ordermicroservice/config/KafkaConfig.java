package com.springconcepts.ordermicroservice.config;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import java.util.HashMap;

// @Configuration
public class KafkaConfig {

//  @Value("${CLOUDKARAFKA_SERVERS}")
//  private String servers;
//
//  @Value("${CLOUDKARAFKA_USERNAME}")
//  private String username;
//
//  @Value("${CLOUDKARAFKA_PASSWORD}")
//  private String password;
//
//  @Bean
//  public ProducerFactory<String, Object> newOrderEventProducerFactory() {
//    var props = new HashMap<String, Object>();
//    props.put("bootstrap.servers", servers);
//    props.put("key.serializer", StringSerializer.class);
//    props.put("value.serializer", JsonSerializer.class);
//    props.put("security.protocol", "SASL_SSL");
//    props.put("sasl.mechanism", "SCRAM-SHA-256");
//    String jaasTemplate =
//        "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
//    String jaasCfg = String.format(jaasTemplate, username, password);
//    props.put("sasl.jaas.config", jaasCfg);
//    return new DefaultKafkaProducerFactory<>(props);
//  }
//
//  @Bean
//  public KafkaTemplate<String, Object> newOrderEventKafkaTemplate() {
//    return new KafkaTemplate<>(newOrderEventProducerFactory());
//  }
}
