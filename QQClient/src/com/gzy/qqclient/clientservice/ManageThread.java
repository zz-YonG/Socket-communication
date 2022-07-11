package com.gzy.qqclient.clientservice;

import java.util.HashMap;

/**
 * 此类用于客户端管理连接到服务器端的线程
 */
public class ManageThread {
    //将多个线程放到Hashmap中，key是用户id，value是线程
    private static HashMap<String,ClientReaderThread> hm = new HashMap<>();
    //将一个线程加入集合
    public static void addThread(String UserID,ClientReaderThread clientReaderThread){
        hm.put(UserID,clientReaderThread);
    }
    //通过UserID查找线程
    public static ClientReaderThread getThread(String UserID){
        return hm.get(UserID);
    }

}
