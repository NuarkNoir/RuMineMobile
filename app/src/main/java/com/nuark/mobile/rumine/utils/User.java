package com.nuark.mobile.rumine.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Map;

/**
 * ** Created by Nuark on 17.03.2017 w/ love.
 **/

public class User implements Parcelable {

    private String nick, login, password, avatar, link;
    private String rep_plus, rep_minus, rep_average, crm_joint, crm_act, crm_rep, crm_news, comm_rating, userGroup, news_count, topics_count, messages_count, comments_count, likes_count;
    private Map<String, String> cookies;

    public User(String nick, String login, String password, String avatar, String link, String rep_plus, String rep_minus, String rep_average, String crm_joint, String crm_act, String crm_rep, String crm_news, String comm_rating, String userGroup, String news_count, String topics_count, String messages_count, String comments_count, String likes_count, Map<String, String> cookies) {
        this.nick = nick;
        this.login = login;
        this.password = password;
        this.avatar = avatar;
        if (!link.contains("https:")) this.link = "https:" + link;
            else this.link = link;
        this.rep_plus = rep_plus;
        this.rep_minus = rep_minus;
        this.rep_average = rep_average;
        this.crm_joint = crm_joint;
        this.crm_act = crm_act;
        this.crm_rep = crm_rep;
        this.crm_news = crm_news;
        this.comm_rating = comm_rating;
        this.userGroup = userGroup;
        this.news_count = news_count;
        this.topics_count = topics_count;
        this.messages_count = messages_count;
        this.comments_count = comments_count;
        this.likes_count = likes_count;
        this.cookies = cookies;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public Map<String, String> getCookies() {
        return cookies;
    }

    public String getNick() {
        return nick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getLink() {
        return link;
    }

    public String getRep_plus() {
        return rep_plus;
    }

    public String getRep_minus() {
        return rep_minus;
    }

    public String getRep_average() {
        return rep_average;
    }

    public String getCrm_joint() {
        return crm_joint;
    }

    public String getCrm_act() {
        return crm_act;
    }

    public String getCrm_rep() {
        return crm_rep;
    }

    public String getCrm_news() {
        return crm_news;
    }

    public String getComm_rating() {
        return comm_rating;
    }

    public String getUserGroup() {
        return userGroup;
    }

    public String getNews_count() {
        return news_count;
    }

    public String getTopics_count() {
        return topics_count;
    }

    public String getMessages_count() {
        return messages_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public String getLikes_count() {
        return likes_count;
    }

    User(Parcel in) {
        nick = in.readString();
        login = in.readString();
        password = in.readString();
        avatar = in.readString();
        link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nick);
        dest.writeString(login);
        dest.writeString(password);
        dest.writeString(avatar);
        dest.writeString(link);
    }
}
