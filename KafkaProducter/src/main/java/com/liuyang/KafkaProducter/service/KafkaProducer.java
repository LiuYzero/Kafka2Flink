package com.liuyang.KafkaProducter.service;

import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String message) {

        CompletableFuture<SendResult<String, String>> result = kafkaTemplate.send(topic, message);
        try {
            logger.info("{}",result.get().toString());
        } catch (InterruptedException | ExecutionException e) {
            logger.info("{}",e.getMessage());
        }
    }
}