package me.next.one;

import android.app.Application;

/**
 * Created by NeXT on 16/9/21.
 */

public class OneApplication extends Application {

    private static OneApplication mOneApplication;

    public static OneApplication getOneApplication() {
        if (mOneApplication == null) {
            synchronized (OneApplication.class) {
                if (mOneApplication == null)
                    mOneApplication = new OneApplication();
            }
        }
        return mOneApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOneApplication = this;
    }
}
