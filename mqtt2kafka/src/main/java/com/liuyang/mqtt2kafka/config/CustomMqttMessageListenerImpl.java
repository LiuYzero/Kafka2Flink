package com.liuyang.mqtt2kafka.config;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class CustomMqttMessageListenerImpl implements IMqttMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(CustomMqttMessageListenerImpl.class);

    private static ExecutorService executorService = Executors.newFixedThreadPool(2);

    private static final String KAFKA_MESSAGE = "http://KAFKAPRODUCTER/sendMessage";

    @Autowired
    RestTemplate restTemplate;

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        logger.info("{}:{}", s, new String(mqttMessage.getPayload(), StandardCharsets.UTF_8));
        executorService.execute(()->kafkaMessage("espiot", new String(mqttMessage.getPayload(), StandardCharsets.UTF_8)));
    }

    private void kafkaMessage(String topic, String message){
        JSONObject data = new JSONObject();
        data.put("topic",topic);
        JSONObject espData = JSON.parseObject(message);
        espData.put("report_time",System.currentTimeMillis());

        data.put("message",espData);
        JSONObject response = restTemplate.postForObject(KAFKA_MESSAGE, data, JSONObject.class, new Object[0]);
        logger.info("repsonse {}", response);

    }

}
