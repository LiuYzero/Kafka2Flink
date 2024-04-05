# Kafka2Flink
该工程是mqtt数据转发到Kafka,然后由flink处理的最小实现。  
工程仅仅考虑数据流转，未进行任何业务处理，也未考虑任何性能、安全要求。  

## 工程信息
## mqtt2kafa
mqtt消费端，通过http调用kafka生产者。  
JDK8+SpringBoot2.7.3

## KafkaProducter
Kafka生产者, 提供 /sendMessage 接口，传入topic(String),message(String)参数。
JDK11+Kafka2.14+SpringBoot3.2.4

## frauddetection
Flink2.12 官方quickstart教程
jdk1.8+Flink2.12
