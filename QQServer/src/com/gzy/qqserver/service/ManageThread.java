package com.gzy.qqserver.service;

import java.util.HashMap;
import java.util.Iterator;

public class ManageThread {
    private static HashMap<String,ServerThread> hm = new HashMap<>();

    public static HashMap<String, ServerThread> getHm() {
        return hm;
    }

    //添加线程
    public static void addThread(String UserID,ServerThread serverThread){
        hm.put(UserID,serverThread);
    }

    //根据UserID返回线程
    public static ServerThread getServerThread(String UserID){
        return hm.get(UserID);
    }

    //获取在线用户列表
    public static String getOnlineUser(){
        Iterator<String> iterator = hm.keySet().iterator();
        String onlineUserList = "";
        while(iterator.hasNext()){
            onlineUserList += iterator.next().toString() + " ";
        }
        return onlineUserList;
    }

    //移除线程
    public static void removeThread(String UserID){
        hm.remove(UserID);
    }
}
