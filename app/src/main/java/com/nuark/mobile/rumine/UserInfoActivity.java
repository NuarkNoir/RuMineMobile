package com.nuark.mobile.rumine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nuark.mobile.rumine.utils.Globals;
import com.nuark.mobile.rumine.utils.User;

public class UserInfoActivity extends AppCompatActivity {
    TextView tv_nick, tv_group, tv_reputation_plus, tv_reputation_minus, tv_reputation_total, tv_carma_joint, tv_carma_news, tv_carma_reputation, tv_carma_activity,  tv_comm_rating, tv_a_news, tv_a_topics, tv_a_messages, tv_a_comments, tv_a_likes;
    ImageView iv_avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        User u = getIntent().getExtras().getParcelable("user");
        boolean cu = getIntent().getExtras().getBoolean("cu");
        if (cu) {
            u = Globals.CurrentUser.getCurrentUser();
        } else {
            //TODO Сделать обработчик
            System.out.println("Something");
        }

        iv_avatar = (ImageView) findViewById(R.id.iv_user_avatar);
        tv_nick = (TextView) findViewById(R.id.tv_nick);
        tv_group = (TextView) findViewById(R.id.tv_group);
        tv_reputation_plus = (TextView) findViewById(R.id.tv_reputation_plus);
        tv_reputation_minus = (TextView) findViewById(R.id.tv_reputation_minus);
        tv_reputation_total = (TextView) findViewById(R.id.tv_reputation_total);
        tv_carma_joint = (TextView) findViewById(R.id.tv_carma_joint);
        tv_carma_news = (TextView) findViewById(R.id.tv_carma_news);
        tv_carma_reputation = (TextView) findViewById(R.id.tv_carma_reputation);
        tv_carma_activity = (TextView) findViewById(R.id.tv_carma_activity);
        tv_comm_rating = (TextView) findViewById(R.id.tv_comm_rating);
        tv_a_news = (TextView) findViewById(R.id.tv_a_news);
        tv_a_topics = (TextView) findViewById(R.id.tv_a_topics);
        tv_a_messages = (TextView) findViewById(R.id.tv_a_messages);
        tv_a_comments = (TextView) findViewById(R.id.tv_a_comments);
        tv_a_likes = (TextView) findViewById(R.id.tv_a_likes);

        if (u != null) {
            Glide.with(this).load(u.getAvatar()).into(iv_avatar);
            tv_nick.setText(u.getNick());
            tv_group.setText(u.getUserGroup());
            tv_reputation_plus.setText(u.getRep_plus());
            tv_reputation_minus.setText(u.getRep_minus());
            tv_reputation_total.setText(u.getRep_average());
            tv_carma_joint.setText(u.getCrm_joint());
            tv_carma_news.setText(u.getCrm_news());
            tv_carma_reputation.setText(u.getCrm_rep());
            tv_carma_activity.setText(u.getCrm_act());
            tv_comm_rating.setText(u.getComm_rating());
            tv_a_news.setText(u.getNews_count());
            tv_a_topics.setText(u.getTopics_count());
            tv_a_messages.setText(u.getMessages_count());
            tv_a_comments.setText(u.getComments_count());
            tv_a_likes.setText(u.getLikes_count());
        } else {
            Toast.makeText(this, "Произошла какая-то ошибка. Опять.", Toast.LENGTH_LONG).show();
        }
    }
}
