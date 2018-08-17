package com.demo;

import android.graphics.Typeface;
import android.support.multidex.MultiDexApplication;

public class AppApplication extends MultiDexApplication {

    //public static Typeface JOSE_FIN_SANS_BOLD;
    public static Typeface HELVETICA_NEUE;
    //public static Typeface JOSE_FIN_SANS_SEMI_BOLD;

    public static AppApplication app = null;

    public static boolean isAppBackground = false;

    public static AppApplication getInstance() {
        if (app == null) {
            app = new AppApplication();
        }
        return app;
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        app = this;
//        //JOSE_FIN_SANS_BOLD = Utils.getTypeface(this, 1);
//        //JOSE_FIN_SANS_SEMI_BOLD = Utils.getTypeface(this, 2);
//        HELVETICA_NEUE = Utils.getTypeface(this, 2);
//
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//
//        // create Picasso.Builder object
//        Picasso.Builder picassoBuilder = new Picasso.Builder(this);
//        // Picasso.Builder creates the Picasso object to do the actual requests
//        Picasso picasso = picassoBuilder.build();

        // set the global instance to use this Picasso object
        // all following Picasso (with Picasso.with(Context context) requests will use this Picasso object
        // you can only use the setSingletonInstance() method once!
//        try {
//            Picasso.setSingletonInstance(picasso);
//        } catch (IllegalStateException ignored) {
            // Picasso instance was already set
            // cannot set it after Picasso.with(Context) was already in use
        }
//        Commons.APP_VERSION = com.eworkplaceapps.platform.utils.Utils.getVersionName(this);
    }

//    @Override
//    protected void attachBaseContext(Context base) {
//        super.attachBaseContext(base);
//        MultiDex.install(base);
//    }
//
//    @Override
//    public void onTrimMemory(int level) {
//        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
//            // Get called every-time when application went to background.
//            isAppBackground = true;
//            LoggerOnlineOps.printLog(ANALYTICS, "applicaton going background then call sendAnalyticLogToServer Function");
//            SyncAnalyticsLog.getInstance().sendAnalyticLogToServer();
//            try {
//                if (EwpSession.getSharedInstance().isUserLoggedIn()) {
//                    BadgeCount.getInstance().refreshBadgeCounts();
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (level == ComponentCallbacks2.TRIM_MEMORY_COMPLETE) {
//
//        }
//        super.onTrimMemory(level);
//    }