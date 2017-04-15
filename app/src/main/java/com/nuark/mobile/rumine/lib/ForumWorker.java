package com.nuark.mobile.rumine.lib;

import com.nuark.mobile.rumine.utils.Message;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * ** Created by Nuark on 21.03.2017 w/ love.
 **/

public class ForumWorker {

    public static class Messenger {
        public static String status = "";

        public static boolean sendMessage(String message, String topicId, Map<String, String> cookies) throws IOException {
            String url = "http://ru-minecraft.ru/index.php?do=forum&action=newpost&id=" + topicId + "&param=post";
            Connection conn = HttpConnection.connect(url).cookies(cookies)
                    .data("text_msg", message)
                    .data("topic_id", topicId)
                    .data("recaptcha_response_field", "")
                    .data("recaptcha_challenge_field", "")
                    .ignoreHttpErrors(true).timeout(10000);
            Connection.Response resp = conn.execute();
            if (resp.parse().select(".be_error") == null){
                return true;
            } else {
                status = resp.parse().select(".be_error").text();
                return false;
            }
        }
    }

    public static class MGrabber {
        public static String status = "";

        public static ArrayList<Message> getMessages(String topic_id, int i) {
            ArrayList<Message> messagesArray = new ArrayList<>();
            String url = "http://ru-minecraft.ru/forum/showtopic-" + topic_id + "/page-" + i;
            try {
                Connection connection1 = HttpConnection.connect(url).followRedirects(true).ignoreHttpErrors(true).timeout(10000);
                Connection.Response response1 = connection1.execute();
                Elements messages = response1.parse().select("li.msg");
                ArrayList<String> author = new ArrayList<>();
                ArrayList<String> text = new ArrayList<>();
                ArrayList<String> date = new ArrayList<>();
                for (Element e : messages) {
                    for (Element aut : e.select(".msgAutorInfo p:eq(0) a[href*=user]")) {
                        author.add(aut.text());
                    }
                    for (Element txt : e.select("div[id^=MsgTextBox-]")) {
                        for (Element n : txt.select("div")) {
                            if (n.is(".clr") || n.text().contains("нравится") || n.text().contains("Сообщение отредактировал ")) {
                                txt.select(n.cssSelector()).remove();
                            }
                        }
                        text.add(txt.html());
                    }
                    for (Element dat : e.select(".msgInfo")) {
                        dat.select(".clr").remove();
                        dat.select(".getMessageLinck").remove();
                        String str = dat.text();
                        char[] chra = str.toCharArray();
                        chra[str.length()-1] = ' ';
                        chra[str.length()-2] = ' ';
                        str = new String(chra);
                        str = str.trim();
                        date.add(str);
                    }
                }
                Collections.reverse(author);
                Collections.reverse(text);
                Collections.reverse(date);
                for (int iter = 0; iter != author.size(); iter++) {
                    messagesArray.add(new Message(author.get(iter), text.get(iter), date.get(iter)));
                }
                return messagesArray;
            } catch (IOException e) {
                status = e.getLocalizedMessage();
                return null;
            }
        }
    }
}