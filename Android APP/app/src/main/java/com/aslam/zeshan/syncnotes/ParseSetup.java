package com.aslam.zeshan.syncnotes;

import android.app.Application;
import android.content.Context;

import com.aslam.zeshan.syncnotes.Util.SettingsManager;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;

public class ParseSetup extends Application {

    Context con;

    @Override
    public void onCreate() {
        super.onCreate();
        con = this;

        com.parse.Parse.enableLocalDatastore(con);
        //ParseCrashReporting.enable(this);

        // Register subclasses
        ParseObject.registerSubclass(Note.class);

        Parse.initialize(con, getResources().getString(R.string.parse_app_id), getResources().getString(R.string.parse_client_key));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                SettingsManager settingsManager = new SettingsManager(con);
                try {
                    ParseInstallation.getCurrentInstallation().save();
                } catch (com.parse.ParseException e) {
                    e.printStackTrace();
                }

                if (!settingsManager.contains("ID")) {
                    settingsManager.set("ID", ParseInstallation.getCurrentInstallation().getInstallationId());
                }
            }
        });
        thread.start();
    }
}
