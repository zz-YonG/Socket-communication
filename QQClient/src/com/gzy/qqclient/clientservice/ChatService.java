package com.gzy.qqclient.clientservice;

import com.gzy.qqcommon.Message;
import com.gzy.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;

public class ChatService {
    public void chatToOne(String content,String senderID,String getterID){
        //构建message对象
        Message message = new Message();
        message.setSenderID(senderID);
        message.setGetterID(getterID);
        message.setContent(content);
        message.setMessageType(MessageType.MESSAGE_COM_MESSAGE);
        message.setSendTime(new Date().toString());//发送时间
        System.out.println("("+message.getSendTime()+")"+senderID+"对"+getterID+"说："+content);

        //发送到服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(senderID).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void chatToAll(String content,String senderID){
        //构建message对象
        Message message = new Message();
        message.setSenderID(senderID);
        message.setContent(content);
        message.setMessageType(MessageType.MESSAGE_TOALL_MESSAGE);
        message.setSendTime(new Date().toString());//发送时间
        System.out.println("("+message.getSendTime()+")"+senderID+"说："+content);

        //发送到服务器
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(senderID).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
