package com.nuark.mobile.rumine.lib;

import android.util.Xml;

import com.nuark.mobile.rumine.utils.Globals;
import com.nuark.mobile.rumine.utils.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * ** Created by Nuark on 17.03.2017 w/ love.
 **/

public final class LoginInAPP {

    private static String uri = "http://ru-minecraft.ru/", login, pass;
    private static Boolean isLogedIn = false;
    private static Map<String, String> cookies;
    private String nick, avatar, link, rep_plus, rep_averrage, rep_minus, crm_joint, crm_act, crm_rep, crm_news, comm_rating, userGroup, cnt_news, cnt_topics, cnt_messages, cnt_comments, cnt_likes;

    public LoginInAPP(String l, String p) {
        login = l;
        pass = p;
        execute();
    }

    private void execute() {
        try {
            System.out.println("Начало авторизации...");
            Connection connection1 = HttpConnection.connect(uri).followRedirects(true).ignoreHttpErrors(true).timeout(10000);
            Connection.Response response1 = connection1.execute();
            System.out.println("Cookies: " + response1.cookies());
            System.out.println("Первый этап авторизации пройден...");

            connection1 = connection1.url(uri).ignoreHttpErrors(true)
                    .data("login_name", login)
                    .data("login_password", pass)
                    .data("login", "submit")
                    .method(Connection.Method.POST).followRedirects(true).cookies(response1.cookies()).timeout(10000);
            Connection.Response response2 = connection1.execute();
            System.out.println("Cookies: " + response2.cookies());
            System.out.println("Второй этап авторизации пройден, отправлены данные...");

            connection1 = connection1.followRedirects(true).cookies(response2.cookies()).ignoreHttpErrors(true).timeout(10000);
            Connection.Response response3 = connection1.execute();
            System.out.println("Cookies: " + response3.cookies());
            Document d = response3.parse();
            String isloged = d.select(".loginname a").text();
            System.out.println("Третий этап авторизации пройден.");
            System.out.println(isloged);

            if (isloged.length() > 0) {
                System.out.println("Авторизация прошла успешно!");
                isLogedIn = true;
                cookies = response2.cookies();
                nick = d.select(".loginname a").text();
                avatar = uri + d.select(".lavatar img").attr("src");
                link = "http:" + d.select(".loginname a").attr("href");
                Connection connection4 = HttpConnection.connect(link).cookies(response2.cookies()).ignoreHttpErrors(true).timeout(10000);
                Connection.Response response4 = connection4.execute();
                parse(response4.parse());
                Globals.CurrentUser.setCurrentUser(new User(
                        nick, login, pass, avatar, link,
                        rep_plus, rep_minus, rep_averrage,
                        crm_joint, crm_act, crm_rep, crm_news, comm_rating,
                        userGroup,
                        cnt_news, cnt_topics, cnt_messages, cnt_comments, cnt_likes,
                        cookies
                ));
            } else {
                System.out.println("Авторизация неудачна!");
                isLogedIn = false;
                cookies = null;
            }
        } catch (IOException e) {
            System.out.println("Произошла ошибка:" + e.getLocalizedMessage());
        }
    }

    private void parse(Document d) {
        rep_plus = d.select(".reputation_positive").text();
        rep_averrage = d.select(".reputation_general").text();
        rep_minus = d.select(".reputation_negative").text();
        crm_joint = d.select(".userrepakarma span").text();
        crm_act = ((Element) d.select(".userrepakarma2 span").toArray()[0]).text();
        crm_rep = ((Element) d.select(".userrepakarma2 span").toArray()[1]).text();
        crm_news = d.select(".userrepakarma2 span").last().text();
        comm_rating = d.select(".userrepakarma span").text();
        userGroup = d.select(".usergroup").text();
        cnt_news = ((Element) d.select(".stats2 .stats1 span").toArray()[0]).text();
        cnt_topics = d.select(".forumstats a[href*=/topic/]").text();
        cnt_messages = d.select(".forumstats a[href*=/message/]").text();
        cnt_comments = ((Element) d.select(".stats2 .stats1 span").toArray()[2]).text();
        cnt_likes = d.select(".forumstats a[href*=/like/]").text();
        debugger();
    }

    public void debugger() {
        System.out.println("------------------INFO------------------");
        System.out.println(uri);
        System.out.println(nick);
        System.out.println(login);
        System.out.println(pass);
        System.out.println(avatar);
        System.out.println(link);
        System.out.println(rep_plus);
        System.out.println(rep_averrage);
        System.out.println(rep_minus);
        System.out.println(crm_joint);
        System.out.println(crm_act);
        System.out.println(crm_rep);
        System.out.println(crm_news);
        System.out.println(comm_rating);
        System.out.println(comm_rating);
        System.out.println(cnt_news);
        System.out.println(cnt_topics);
        System.out.println(cnt_messages);
        System.out.println(cnt_comments);
        System.out.println(cnt_likes);
        System.out.println(cookies);
        System.out.println("------------------INFO------------------");
    }

    public static Boolean getIsLogedIn() {
        return isLogedIn;
    }
}
