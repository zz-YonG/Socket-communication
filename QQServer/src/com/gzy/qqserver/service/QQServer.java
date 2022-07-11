package com.gzy.qqserver.service;

import com.gzy.qqcommon.MessageType;
import com.gzy.qqcommon.User;
import com.gzy.qqcommon.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class QQServer {
    private ServerSocket ss = null;




    public static void main(String[] args) {
        new QQServer();
    }



    public QQServer(){
        try {
            ss = new ServerSocket(9999);
            while(true){
                Socket socket = ss.accept();//等待连接
                //得到对象输入流
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //读取客户端发送的User对象
                User u = (User) ois.readObject();
                //得到socket关联的对象输出流
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //Message对象用来回复客户端
                Message message = new Message();
                //验证
                //登录成功Message
//                if(u.getUserID().equals("100") && u.getPassWord().equals("123456")){
                if(u.getPassWord().equals("123456")){
                    message.setMessageType(MessageType.MESSAGE_LOGIN_SUCCESSD);
                    //将message回复给客户端
                    oos.writeObject(message);
                    //创建线程，与客户端来保持通信，该线程需要持有socket
                    ServerThread serverThread = new ServerThread(socket, u.getUserID());
                    //启动线程
                    serverThread.start();
                    //将该线程放入线程集合中进行管理
                    ManageThread.addThread(u.getUserID(),serverThread);

                //登录失败
                }else{
                    message.setMessageType(MessageType.MESSAGE_LOGIN_FAILD);
                    oos.writeObject(message);
                    socket.close();

                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //服务器端若退出while，则关闭serversocket
            try {
                ss.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

}
