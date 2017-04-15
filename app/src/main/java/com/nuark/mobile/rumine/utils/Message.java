package com.nuark.mobile.rumine.utils;

/**
 * ** Created by Nuark on 25.03.2017 w/ love.
 **/

public class Message {
    private String User, Text, Date;

    public Message(String User, String Text, String Date) {
        this.User = User;
        this.Text = Text;
        this.Date = Date;
    }

    public String getUser() {
        return User;
    }

    public String getText() {
        return Text;
    }

    public String getDate() {
        return Date;
    }
}