package spendreport;

import com.alibaba.fastjson.JSONObject;

import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EspIot {

    private String sourceDevice;
    private String targetDevice;
    private String msgType;
    private Date reportTime;

    private JSONObject object;

    public EspIot() {
    }

    public JSONObject getObject() {
        return object;
    }

    public void setObject(JSONObject object) {
        this.object = object;
    }

    public String getSourceDevice() {
        return sourceDevice;
    }

    public void setSourceDevice(String sourceDevice) {
        this.sourceDevice = sourceDevice;
    }

    public String getTargetDevice() {
        return targetDevice;
    }

    public void setTargetDevice(String targetDevice) {
        this.targetDevice = targetDevice;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    @Override
    public String toString() {
        return "EspIot{" +
                "sourceDevice='" + sourceDevice + '\'' +
                ", targetDevice='" + targetDevice + '\'' +
                ", msgType='" + msgType + '\'' +
                ", reportTime=" + reportTime +
                '}';
    }
}
