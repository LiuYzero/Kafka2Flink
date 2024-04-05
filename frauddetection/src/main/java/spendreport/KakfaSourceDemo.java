package spendreport;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

import java.util.Date;

public class KakfaSourceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        KafkaSource<String> source = KafkaSource.<String>builder()
                .setBootstrapServers("middlerware01:9092")
                .setTopics("espiot")
                .setGroupId("flinkdemo_espiot01")
                .setStartingOffsets(OffsetsInitializer.earliest())
                .setValueOnlyDeserializer(new SimpleStringSchema())
                .build();

        DataStream<String> stream = env.fromSource(source, WatermarkStrategy.noWatermarks(), "Kafka Source");

        DataStream<EspIot> espIotDataStream = stream.flatMap(new FlatMapFunction<String, EspIot>() {
            @Override
            public void flatMap(String value, Collector<EspIot> collector) throws Exception {
                if(StringUtils.isNotBlank(value)){
                    EspIot espIot = new EspIot();
                    JSONObject data = JSONObject.parseObject(value);
                    espIot.setSourceDevice(data.getString("source_device"));
                    espIot.setTargetDevice(data.getString("target_device"));
                    espIot.setMsgType(data.getString("msg_type"));
                    if(data.containsKey("report_time")) {
                        espIot.setReportTime(new Date(data.getLong("report_time")));
                    }
                    espIot.setObject(data);
                    collector.collect(espIot);
                }
            }
        });

        espIotDataStream.print();

        env.execute("Flink Kafka Integration");
    }
}
