package com.liuyang.KafkaProducter.controller;

import com.alibaba.fastjson.JSONObject;
import com.liuyang.KafkaProducter.service.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaController {


    private final KafkaProducer kafkaProducer;

    @Autowired
    public KafkaController(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping("/sendMessage")
    public JSONObject kafka(@RequestBody JSONObject object) {
        kafkaProducer.sendMessage(object.getString("topic"), object.getJSONObject("message").toString());

        return new JSONObject();
    }
}