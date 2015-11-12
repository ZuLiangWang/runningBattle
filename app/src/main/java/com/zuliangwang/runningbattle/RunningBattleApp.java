package com.zuliangwang.runningbattle;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by zuliangwang on 15/11/9.
 */
public class RunningBattleApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.initialize(this,"oXwzfAxEQoJr4TvMsRpL6F1q","D6GnKnq4yWOXNDEAf6yc2Ye0");
    }
}
