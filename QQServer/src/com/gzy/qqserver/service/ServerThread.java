package com.gzy.qqserver.service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.CookieStore;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

import com.gzy.qqcommon.Message;
import com.gzy.qqcommon.MessageType;

/**
 * 该类的一个对象与某个客户端保持通信
 */
public class ServerThread extends Thread{
    private Socket socket;
    private String userID;

    public ServerThread(Socket socket, String userID) {
        this.socket = socket;
        this.userID = userID;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        while(true){
            try {
                //一直接受客户端消息
                System.out.println("服务端与客户端"+userID+"保持通信");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                //使用消息

                //判断消息类型
                //请求用户列表
                if (message.getMessageType().equals(MessageType.MESSAGE_GET_ONLINEFRIENDS)){
                    System.out.println(userID+"请求用户列表");
                    String onlineUser = ManageThread.getOnlineUser();
                    //返回message
                    Message ret_ms = new Message();
                    ret_ms.setMessageType(MessageType.MESSAGE_RET_ONLINEFRIENDS);
                    ret_ms.setContent(onlineUser);
                    ret_ms.setGetterID(message.getSenderID());
                    //返回给客户端
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(ret_ms);

                //请求退出
                } else if (message.getMessageType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    System.out.println("客户端"+message.getSenderID()+"退出");
                    ManageThread.removeThread(message.getSenderID());
                    socket.close();
                    //退出线程
                    break;

                //用户私聊
                } else if (message.getMessageType().equals(MessageType.MESSAGE_COM_MESSAGE)) {
                    //根据message得到getterID，之后根据getterID取得对应线程
                    ServerThread serverThread = ManageThread.getServerThread(message.getGetterID());
                    //得到对应的socket的对象输出流，转发message
                    ObjectOutputStream oos = new ObjectOutputStream(serverThread.getSocket().getOutputStream());
                    oos.writeObject(message);

                //用户群聊
                } else if (message.getMessageType().equals(MessageType.MESSAGE_TOALL_MESSAGE)) {
                    //遍历线程集合，向所有socket转发message
                    HashMap<String, ServerThread> hm = ManageThread.getHm();
                    Iterator<String> iterator = hm.keySet().iterator();
                    while(iterator.hasNext()){
                        //取出在线用户的ID
                        String onlineUserID = iterator.next().toString();
                        //不转发给自己
                        if(!onlineUserID.equals(message.getSenderID())){
                            ObjectOutputStream oos = new ObjectOutputStream(hm.get(onlineUserID).getSocket().getOutputStream());
                            oos.writeObject(message);
                        }
                    }

                //文件传输
                } else if (message.getMessageType().equals(MessageType.MESSAGE_FILE_MESSAGE)) {
                    //从线程集合中根据getterID取出线程
                    ServerThread serverThread = ManageThread.getServerThread(message.getGetterID());
                    //根据线程得到输出流对象
                    ObjectOutputStream oos = new ObjectOutputStream(serverThread.getSocket().getOutputStream());
                    //转发
                    oos.writeObject(message);


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }
}
