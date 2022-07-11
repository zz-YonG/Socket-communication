/**
 * 该类为用户类，可以作为属性进行传输
 */
package com.gzy.qqcommon;

import java.io.Serializable;

public class User implements Serializable {
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public User(String userID, String passWord) {
        this.userID = userID;
        this.passWord = passWord;
    }

    public  User(){};

    private static final long serialVersionUID = 1L;
    private String userID;
    private String passWord;

}
