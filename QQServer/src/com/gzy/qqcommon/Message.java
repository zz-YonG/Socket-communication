/**
 * 该类为消息类，作为载体传输用户端与服务器端的各种消息
 */
package com.gzy.qqcommon;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;
    private String senderID;
    private String getterID;
    private String messageType;
    private String sendTime;
    private String content;

    //文件传输相关变量
    private byte[] fileBytes;
    private int filelen;
    private String dest;
    private String src;

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFilelen() {
        return filelen;
    }

    public void setFilelen(int filelen) {
        this.filelen = filelen;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public Message(String senderID, String getterID, String messageType, String sendTime, String content) {
        this.senderID = senderID;
        this.getterID = getterID;
        this.messageType = messageType;
        this.sendTime = sendTime;
        this.content = content;
    }

    public Message(){}

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getGetterID() {
        return getterID;
    }

    public void setGetterID(String getterID) {
        this.getterID = getterID;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getContent() { return content; }

    public void setContent(String content) {this.content = content;}




}
