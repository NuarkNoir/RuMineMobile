package com.nuark.mobile.rumine.lib;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.nuark.mobile.rumine.MainActivity;
import com.nuark.mobile.rumine.utils.Globals;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * ** Created by Nuark on 25.03.2017 w/ love.
 **/

public class ImageDownloadAsyncTask extends AsyncTask<Void, Void, Void> {
    private String source;
    private String message;
    private TextView textView;

    public ImageDownloadAsyncTask(String source, String message, TextView textView) {
        this.source = source;
        this.message = message;
        this.textView = textView;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (!Globals.DrawableCache.getDrawableCache().containsKey(source)) {
            try {
                //Скачиваем картинку в наш кэш
                if (!source.contains("http:")) source = "http:" + source;
                URL url = new URL(source);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                //Drawable drawable = Drawable.createFromStream(is, "src");

                // Если нужно, чтобы рисунки не масштабировались,
                // закомментируйте строчку выше и расскомментируйте код
                // ниже.


				Bitmap bmp = BitmapFactory.decodeStream(is);
				DisplayMetrics dm = MainActivity.cont.getResources().getDisplayMetrics();
				bmp.setDensity(dm.densityDpi);
                Drawable drawable = new BitmapDrawable(MainActivity.cont.getResources(), bmp);


                is.close();

                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                Globals.DrawableCache.getDrawableCache().put(source, new WeakReference<Drawable>(
                        drawable));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
        return null;
    }

    private Html.ImageGetter igCached = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            //Просто возвращаем наш рисунок из кеша
            if (Globals.DrawableCache.getDrawableCache().containsKey(source))
                return Globals.DrawableCache.getDrawableCache().get(source).get();
            return null;
        }
    };

    @Override
    protected void onPostExecute(Void result) {
        textView.setText(Html.fromHtml(message, igCached, null));
    }
}