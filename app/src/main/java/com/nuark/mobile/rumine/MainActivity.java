package com.nuark.mobile.rumine;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.nuark.mobile.rumine.lib.ForumWorker;
import com.nuark.mobile.rumine.utils.Callback;
import com.nuark.mobile.rumine.utils.Globals;
import com.nuark.mobile.rumine.utils.Message;
import com.nuark.mobile.rumine.utils.MessagesListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static final String APP_PREFERENCES = "PREFS";
    SharedPreferences mSettings;
    FloatingActionButton fab_login, fab_user, fab_message, fab_bugreport;
    ListView lv_messagesList;
    ArrayList<Message> messageArrayList = new ArrayList<>();
    Activity act = this;
    public static Context cont;
    MessagesListAdapter mla;
    SwipeRefreshLayout srl_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cont = getApplicationContext();

        FloatingActionMenu fam = findViewById(R.id.fam);
        fab_login = findViewById(R.id.mi_login);
        fab_user = findViewById(R.id.mi_user);
        fab_message = findViewById(R.id.mi_sendmessage);
        fab_bugreport = findViewById(R.id.mi_sendugreport);
        lv_messagesList = findViewById(R.id.lv_messagesList);
        lv_messagesList.setAdapter(mla);
        srl_load = findViewById(R.id.srl_load);
        srl_load.setOnRefreshListener(this);
        srl_load.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);

        fabsSwitcher();
        new Getter().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fabsSwitcher();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, About.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public void famHandler(View view) {
        switch (view.getId()){
            case R.id.mi_login:
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                break;
            case R.id.mi_user:
                startActivity(new Intent(getApplicationContext(), UserInfoActivity.class).putExtra("user", Globals.CurrentUser.getCurrentUser()).putExtra("cu", true));
                break;
            case R.id.mi_sendmessage:
                startActivity(new Intent(getApplicationContext(), SendMessageActivity.class).putExtra("TOUSER", ""));
                break;
            case R.id.mi_sendugreport:
                Toast.makeText(act, "Not implemented yet.", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void fabsSwitcher(){
        if (Globals.CurrentUser.getCurrentUser() != null){
            fab_login.setVisibility(View.GONE);
            fab_user.setVisibility(View.VISIBLE);
            fab_message.setVisibility(View.VISIBLE);
            fab_bugreport.setVisibility(View.VISIBLE);
        } else {
            fab_login.setVisibility(View.VISIBLE);
            fab_user.setVisibility(View.GONE);
            fab_message.setVisibility(View.GONE);
            fab_bugreport.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        System.out.println("Refreshing...");
        new Getter().execute();
    }

    private class Getter extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            srl_load.setRefreshing(false);
            lv_messagesList.setAdapter(mla);
            if (messageArrayList != null)
                Toast.makeText(act, "Ошибка при загрузке. Проверьте интернет соединение.", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!srl_load.isRefreshing()) srl_load.setRefreshing(true);
        }

        @Override
        protected Void doInBackground(Void... params) {
            messageArrayList = ForumWorker.MGrabber.getMessages("15361", 100000);
            if (ForumWorker.MGrabber.status == ""){
                System.out.println("ФВ отработал успешно!");
                System.out.println("MC: " + messageArrayList.size());
                messageArrayList.remove(messageArrayList.size()-1);
                mla = new MessagesListAdapter(act, messageArrayList);
                mla.notifyDataSetChanged();
            } else {
                System.out.println("ФВ отработал с ошибкой!");
            }
            return null;
        }
    }
}
