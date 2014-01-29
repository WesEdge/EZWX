package EZWX.mq;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import EZWX.core.Serializer;

public class MQArgs implements java.io.Serializable {

    private String _queueHost;
    private String _queueName;
    private byte[] _bytes;

    public MQArgs() {
    }

    public MQArgs(String message){
        this.setBytes(message);
    }

    public MQArgs(byte[] bytes){
        this.setBytes(bytes);
    }

    public void setQueueHost(String queueHost){
        this._queueHost = queueHost;
    }
    public String getQueueHost() {
        return this._queueHost;
    }

    public void setQueueName(String queueName){
        this._queueName = queueName;
    }

    public String getQueueName() {
        return this._queueName;
    }

    public byte[] getBytes() {
        return this._bytes;
    }

    public void setBytes(byte[] bytes) {
        this._bytes = bytes;
    }

    public void setBytes(String message) {
        this.setMessage(message);
    }

    public URL getUrl() throws MalformedURLException, UnsupportedEncodingException {
        return new URL(this.getMessage());
    }

    public String getMessage() throws UnsupportedEncodingException {
        return new String(this.getBytes(), "UTF-8");
    }

    public void setMessage(String message) {
        this.setBytes(message.getBytes());
    }

    public byte[] serialize() throws IOException {
        return Serializer.serialize((Object)this);
    }

    public static MQArgs deserialize(byte[] bytes) throws ClassNotFoundException, IOException {
        MQArgs args = (MQArgs) Serializer.deserialize(bytes);
        return args;
    }

}