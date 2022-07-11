/**
 * 该类用于登录成功后建立线程
 */
package com.gzy.qqclient.clientservice;

import com.gzy.qqcommon.Message;
import com.gzy.qqcommon.MessageType;

import java.io.*;
import java.net.Socket;


class ClientReaderThread extends Thread{

    private Socket socket;
    public ClientReaderThread(Socket socket){this.socket = socket; }

    @Override
    public void run(){
        while (true) {
            try {
                //客户端的线程等待服务器发来的消息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message ms = (Message)ois.readObject();

                //判断message类型
                //如果读取到的是服务端返回的在线好友列表
                if (ms.getMessageType().equals(MessageType.MESSAGE_RET_ONLINEFRIENDS)){
                    String[] onlineUsers = ms.getContent().split(" ");
                    System.out.println("\n==============在线用户列表===============");
                    for(int i = 0; i<onlineUsers.length; i++){
                        System.out.println("用户:"+onlineUsers[i]);
                    }
                } else if (ms.getMessageType().equals(MessageType.MESSAGE_COM_MESSAGE)) {
                    System.out.println("\n("+ms.getSendTime()+")"+ms.getSenderID()+"对你说："+ms.getContent());

                } else if (ms.getMessageType().equals(MessageType.MESSAGE_TOALL_MESSAGE)) {
                    System.out.println("\n("+ms.getSendTime()+")"+ms.getSenderID()+"说："+ms.getContent());
                    
                } else if (ms.getMessageType().equals(MessageType.MESSAGE_FILE_MESSAGE)) {
                    System.out.println("\n"+ms.getSenderID()+"向你发送文件至"+ms.getDest());
                    //取出message中的fileBytes
                    FileOutputStream fos = new FileOutputStream(ms.getDest());
                    fos.write(ms.getFileBytes());
                    fos.close();
                    System.out.println("保存文件成功");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Socket getSocket() {
        return socket;
    }
}