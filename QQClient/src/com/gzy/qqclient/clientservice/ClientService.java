/**
 * 该类用于进行用户登录校验
 */
package com.gzy.qqclient.clientservice;

import com.gzy.qqcommon.Message;
import com.gzy.qqcommon.MessageType;
import com.gzy.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientService {
    private User u = new User();
    Socket socket = new Socket();
    public boolean checkUser(String UserID,String pwd){

        u.setUserID(UserID);
        u.setPassWord(pwd);

        boolean check_flag = false;//返回值

        try{
            socket = new Socket("127.0.0.1",9999);

            //输出流 向服务器写User对象
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(u);
            //输入流 读服务器返回的消息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message) ois.readObject();

            //登录成功
            if (ms.getMessageType().equals(MessageType.MESSAGE_LOGIN_SUCCESSD)){
                check_flag = true;
                //创建一个与服务器之间保持通信的线程用来装socket->创建一个类ClientReaderThread
                ClientReaderThread clientReaderThread = new ClientReaderThread(socket);
                //启动此线程
                clientReaderThread.start();
                //将线程存入线程集合
                ManageThread.addThread(UserID,clientReaderThread);
            }else{
                socket.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return check_flag;
    }



    public void onlineFriendList(){

        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_GET_ONLINEFRIENDS);
        message.setSenderID(u.getUserID());

        //根据用户ID从线程管理集合中取出线程并得到线程中的socket，之后取得输出流，准备write message
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(u.getUserID()).getSocket().getOutputStream());

            //发送请求，向服务器请求在线用户列表
            oos.writeObject(message);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    //退出客户端，并给服务端发送退出系统的message
    public void Logout(){
        Message message = new Message();
        message.setMessageType(MessageType.MESSAGE_CLIENT_EXIT);
        message.setSenderID(u.getUserID());

        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageThread.getThread(u.getUserID()).getSocket().getOutputStream());
            oos.writeObject(message);
            System.out.println("退出系统");
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
