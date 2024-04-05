package com.liuyang.mqtt2kafka.controller;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MqttClientController {
    @Autowired
    MqttClient mqttClient;
}