package EZWX.core;


import java.util.Date;

public class StringWriter {

    private boolean _newLines;
    private String _text = "";

    public StringWriter(boolean newLines){
        this._newLines = newLines;
    }

    public void writeKVP(String key, boolean value){
        writeKVP(key, Boolean.toString(value));
    }

    public void writeKVP(String key, Date value){
        writeKVP(key, value.toString());
    }

    public void writeKVP(String key, float value){
        writeKVP(key, String.valueOf(value));
    }

    public void writeKVP(String key, int value){
        writeKVP(key, String.valueOf(value));
    }

    public void writeKVP(String key, String value){
        write(key + ": " + value);
    }

    public void write(String text){
        _text += this.newLine(text);
    }

    private String newLine(String text){

        if (this._newLines){
            text += "\n";
        }

        return text ;

    }

    public String toString(){
        return this._text;
    }

}
