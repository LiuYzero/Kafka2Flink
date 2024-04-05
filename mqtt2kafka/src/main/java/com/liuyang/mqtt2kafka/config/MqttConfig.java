package com.liuyang.mqtt2kafka.config;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class MqttConfig {
    private static final Logger log = LoggerFactory.getLogger(MqttConfig.class);

    @Value("${spring.mqtt.url}")
    private String serverURI;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @Value("${spring.mqtt.username}")
    private String username;

    @Value("${spring.mqtt.password}")
    private String password;

    @Value("${spring.mqtt.default_topic}")
    private String topic;

    private static MqttClient mqttClient;

    private static MqttClient sendMqttClient;

    static String serverURI2;

    @Resource
    private CustomMqttMessageListenerImpl mqttMessageListenerImpl;

    static MqttConnectOptions opts;

    public static MqttClient getSendClient() {
        return sendMqttClient;
    }

    @Bean
    MqttConnectOptions mqttConnectOptions() {
        try {
            opts = new MqttConnectOptions();
            opts.setMqttVersion(4);
            opts.setMaxReconnectDelay(5000);
            opts.setConnectionTimeout(2000);
            opts.setHttpsHostnameVerificationEnabled(false);
            opts.setCleanSession(false);
            opts.setKeepAliveInterval(5);
            opts.setAutomaticReconnect(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        serverURI2 = this.serverURI;
        return opts;
    }

    @Bean
    MqttClient sendMqttClient() {
        MqttClient sendClient = null;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            sendClient = new MqttClient(this.serverURI, this.clientId + "send", (MqttClientPersistence)persistence);
            IMqttToken token = sendClient.connectWithResult(opts);
            token.waitForCompletion();
            sendMqttClient = sendClient;
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
        return sendMqttClient;
    }

    @Bean
    MqttClient mqttClient() {
        MqttClient client = null;
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            client = new MqttClient(this.serverURI, this.clientId, (MqttClientPersistence)persistence);
            IMqttToken token = client.connectWithResult(opts);
            token.waitForCompletion();
            log.info("=====================");
            IMqttToken iMqttToken = client.subscribeWithResponse(this.topic, 2, (IMqttMessageListener)this.mqttMessageListenerImpl);
            iMqttToken.waitForCompletion();
            String str = new String(token.getResponse().getPayload());
            System.out.println("============================" + str);
            mqttClient = client;
        } catch (MqttException e) {
            log.error(e.getMessage());
        }
        return client;
    }
}
