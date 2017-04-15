package com.nuark.mobile.rumine.utils;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * ** Created by Nuark on 17.03.2017 w/ love.
 **/

public class Globals {

    public static String getBaseUrl(){ return "http://ru-minecraft.ru/forum/"; }

    public static class CurrentUser{
        static User CurrentUser;

        public static User getCurrentUser() {
            return CurrentUser;
        }

        public static void setCurrentUser(User currentUser) {
            CurrentUser = currentUser;
        }
    }

    public static class DrawableCache implements Parcelable{
        static final Map<String, WeakReference<Drawable>> DrawableCache = Collections.synchronizedMap(new WeakHashMap<String, WeakReference<Drawable>>());

        public static Map<String, WeakReference<Drawable>> getDrawableCache() {
            return DrawableCache;
        }

        DrawableCache(Parcel in) {
        }

        public static final Creator<DrawableCache> CREATOR = new Creator<DrawableCache>() {
            @Override
            public DrawableCache createFromParcel(Parcel in) {
                return new DrawableCache(in);
            }

            @Override
            public DrawableCache[] newArray(int size) {
                return new DrawableCache[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
        }
    }
}
