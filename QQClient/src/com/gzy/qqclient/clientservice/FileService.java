package com.gzy.qqclient.clientservice;

import com.gzy.qqcommon.Message;
import com.gzy.qqcommon.MessageType;

import java.io.*;

/**
 * 该类完成文件传输
 */
public class FileService {

    public void sendFileToOne(String src,String dest,String senderID,String getterID){
        //读取src文件
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_FILE_MESSAGE);
        message.setSenderID(senderID);
        message.setGetterID(getterID);
        message.setSrc(src);
        message.setDest(dest);

        //读取文件
        FileInputStream fileInputStream = null;
        byte[] fileBytes = new byte[(int)new File(src).length()];

        try {
            fileInputStream = new FileInputStream(src);
            fileInputStream.read(fileBytes);//将src文件读入byte数组中
            //将文件对应的字节数组设置为message
            message.setFileBytes(fileBytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(fileInputStream != null){
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //提示消息
        System.out.println("\n"+senderID+"给"+getterID+"发送文件至"+dest);
        //发送
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(senderID).getSocket().getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
