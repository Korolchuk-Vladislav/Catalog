package ru.korolchuk.catalog.other;

import android.app.Application;
import android.content.Context;

public class Catalog extends Application {
    private static Context context;

    public void onCreate() {
        super.onCreate();
        Catalog.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Catalog.context;
    }
}
