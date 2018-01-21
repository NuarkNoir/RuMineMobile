package com.nuark.mobile.rumine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nuark.mobile.rumine.lib.ForumWorker;
import com.nuark.mobile.rumine.utils.Callback;
import com.nuark.mobile.rumine.utils.Globals;

import java.io.IOException;
import java.util.ArrayList;

import static android.view.View.GONE;

/**
 * ** Created by Nuark on 21.03.2017 w/ love.
 **/

public class SendMessageActivity extends AppCompatActivity {

    Button btn_send_message, btn_util_bold, btn_util_italic, btn_util_underline, btn_util_emoji;
    EditText et_message;
    ProgressBar pb_sending;
    ArrayList<String> backup_msg;
    static int changes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);

        backup_msg = new ArrayList<>();

        pb_sending = findViewById(R.id.pb_sending);
        btn_send_message = findViewById(R.id.btn_send_message);
        et_message = findViewById(R.id.et_message);
        btn_send_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_send_message.setVisibility(GONE);
                pb_sending.setVisibility(View.VISIBLE);
                sendMessage(new Callback(){
                    @Override
                    public void onSuccess() {
                        System.out.println("Success!");
                        finish();
                    }

                    @Override
                    public void onFail(String message) {
                        super.onFail(message);
                        System.out.println(message);
                    }
                }, "15361");
            }
        });
        btn_send_message.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                btn_send_message.setVisibility(GONE);
                pb_sending.setVisibility(View.VISIBLE);
                sendMessage(new Callback(){
                    @Override
                    public void onSuccess() {
                        System.out.println("Success!");
                        finish();
                    }

                    @Override
                    public void onFail(String message) {
                        super.onFail(message);
                        System.out.println(message);
                    }
                }, "15781");
                return false;
            }
        });

        et_message.setText(getIntent().getExtras().getString("TOUSER"));
    }

    public void sendMessage(final Callback callback, final String topic_id) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ForumWorker.Messenger.sendMessage(et_message.getText().toString(), topic_id, Globals.CurrentUser.getCurrentUser().getCookies());
                    if (ForumWorker.Messenger.status == ""){
                        callback.onSuccess();
                    } else {
                        callback.onFail(ForumWorker.Messenger.status);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void btnUtilsHandler(View view) {
        switch (view.getId()){
            case R.id.btn_util_bold:
                changes++;
                backup_msg.add(et_message.getText().toString());
                et_message.setText(et_message.getText().insert(et_message.getSelectionStart(), "[b]").insert(et_message.getSelectionStart(), "[/b]"));
                break;
            case R.id.btn_util_italic:
                changes++;
                backup_msg.add(et_message.getText().toString());
                et_message.setText(et_message.getText().insert(et_message.getSelectionStart(), "[i]").insert(et_message.getSelectionStart(), "[/i]"));
                break;
            case R.id.btn_util_underline:
                changes++;
                backup_msg.add(et_message.getText().toString());
                et_message.setText(et_message.getText().insert(et_message.getSelectionStart(), "[u]").insert(et_message.getSelectionStart(), "[/u]"));
                break;
            case R.id.btn_util_undo:
                if (changes <= 0) break;
                et_message.setText(backup_msg.get(changes-1));
                backup_msg.remove(changes-1);
                changes--;
                break;
            case R.id.btn_util_emoji:
                break;
        }
    }
}
