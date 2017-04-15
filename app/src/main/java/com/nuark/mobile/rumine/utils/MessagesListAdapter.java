package com.nuark.mobile.rumine.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.nuark.mobile.rumine.LoginActivity;
import com.nuark.mobile.rumine.R;
import com.nuark.mobile.rumine.SendMessageActivity;
import com.nuark.mobile.rumine.lib.ImageDownloadAsyncTask;

import java.util.ArrayList;

/**
 * ** Created by Nuark on 25.03.2017 w/ love.
 **/

public class MessagesListAdapter extends BaseAdapter {

    private final Activity context;
    private ArrayList<Message> messageArrayList;

    public MessagesListAdapter(Activity context, ArrayList<Message> messageArrayList) {
        this.context = context;
        this.messageArrayList = messageArrayList;
    }

    @Override
    public int getCount() {
        return messageArrayList.size();
    }

    @Override
    public Message getItem(int position) {
        return messageArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.messageitem, null, true);
        Log.d("NOPE", "42::INFLATED CUSTOM LAYOUT");

        final TextView txtUser = (TextView) rowView.findViewById(R.id.txtUser);
        TextView txtDate = (TextView) rowView.findViewById(R.id.txtDate);
        final TextView txtMessage = (TextView) rowView.findViewById(R.id.txtMessage);

        txtUser.setText(messageArrayList.get(position).getUser());
        txtDate.setText(messageArrayList.get(position).getDate());


        Html.ImageGetter igLoader = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Log.d("NOPE", "getDrawable: " + source);
                String src = "http://ru-minecraft.ru" + source;
                if (Globals.DrawableCache.getDrawableCache().containsKey(src)){
                    return Globals.DrawableCache.getDrawableCache().get(src).get();
                } else {
                    new ImageDownloadAsyncTask(src, messageArrayList.get(position).getText().trim(), txtMessage).execute();
                    return new BitmapDrawable();
                }
            }
        };
        txtMessage.setText(Html.fromHtml(messageArrayList.get(position).getText().trim(), igLoader, null));

        Log.d("NOPE", "getView() called with: txtUser = [" + txtUser.getText() + "], txtMessage = [" + txtMessage.getText() + "]");

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Globals.CurrentUser.getCurrentUser() != null){
                    context.startActivity(new Intent(context, SendMessageActivity.class).putExtra("TOUSER", "[b]" + messageArrayList.get(position).getUser() + ",[/b]\n"));
                } else {
                    Toast.makeText(context, "Сначала авторизируйтесь", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return rowView;
    }
}
