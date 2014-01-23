package EZWX.mq;

import java.net.URL;

public class MQArgs  {

    private String _queueHost;
    private String _queueName;
    private String _message;
    private URL _url;
    private byte[] _bytes;

    public MQArgs(){
    }

    public MQArgs(String message){
        this._message = message;
    }

    public MQArgs(byte[] bytes){
        this._bytes = bytes;
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

    public void setMessage(String message){
        this._message = message;
    }
    public String getMessage() {
        return this._message;
    }
    public byte[] getBytes() {
        return this._bytes;
    }
    public void setBytes(byte[] bytes) {
        this._bytes = bytes;
    }

    public void setUrl(String url) throws java.net.MalformedURLException  {
        this._url = new URL(url);
    }
    public URL getUrl() {
        return this._url;
    }

}