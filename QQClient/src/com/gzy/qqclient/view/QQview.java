/**
 * 该类为演示界面
 */
package com.gzy.qqclient.view;

import com.gzy.qqclient.clientservice.ChatService;
import com.gzy.qqclient.clientservice.ClientService;
import com.gzy.qqclient.clientservice.FileService;
import com.gzy.qqclient.utils.Utility;

import java.sql.Time;

public class QQview {
    private boolean loop_1 = true;
    private boolean loop_2 = true;
    private ClientService clientService = new ClientService();//对象用于登录、注册
    private ChatService chatService = new ChatService();//对象用于私聊群聊
    private FileService fileService = new FileService();//对象用于文件传输


    public static void main(String[] args) {
        new QQview().mainMenu();
    }


    private void mainMenu() {
        while (loop_1) {
            System.out.println("==================欢迎登录网络通信系统===================");
            System.out.println("\t\t 输入1 登录系统");
            System.out.println("\t\t 输入9 离开系统");

            String key = Utility.readString(1);

            switch (key){
                case "1":
                    System.out.print("请输入用户名:");
                    String UserID = Utility.readString(50);
                    System.out.print("请输入密码:");
                    String pwd = Utility.readString(50);
                    //登录检查
                    if(clientService.checkUser(UserID,pwd)) {
                        System.out.println("==================欢迎用户("+UserID+")====================");
                        loop_2 = true;
                        while(loop_2){
                            System.out.println("\n===============网络通信系统二级界面(用户"+UserID+")=================");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入您的选择:");
                            key = Utility.readString(1);

                            switch (key){
                                case "1":
                                    clientService.onlineFriendList();
                                    break;
                                case"2":
                                    System.out.print("输入内容：");
                                    String content = Utility.readString(100);
                                    chatService.chatToAll(content,UserID);
                                    break;
                                case"3":
                                    System.out.print("输入私聊的在线用户：");
                                    String GetterID = Utility.readString(50);
                                    System.out.print("输入内容：");
                                    content = Utility.readString(100);
                                    chatService.chatToOne(content,UserID,GetterID);
                                    break;
                                case"4":
                                    System.out.println("输入文件发送用户：");
                                    GetterID = Utility.readString(50);
                                    System.out.println("输入源文件路径：");
                                    //D:\\src\\test.jpg
                                    String src = Utility.readString(100);
                                    System.out.println("输入文件传输位置");
                                    //D:\\dest\\new.jpg
                                    String dest = Utility.readString(100);
                                    fileService.sendFileToOne(src,dest,UserID,GetterID);
                                    break;
                                case"9":
                                    clientService.Logout();
                                    loop_2 = false;
                                    break;
                            }
                        }
                    }
                    else {
                        System.out.println("==============登录失败==============");
                    }
                    break;
                case"9":
                    loop_1 = false;
                    break;


            }

        }

    }

}
