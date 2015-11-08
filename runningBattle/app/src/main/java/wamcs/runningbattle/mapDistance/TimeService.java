package wamcs.runningbattle.mapDistance;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Calendar;

/**
 * Created by zuliangwang on 15/10/24.
 */
public class TimeService extends Service {

    private Calendar mCalender;
    private int day =-1;
    private int lastDay =-1;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(){
            @Override
            public void run() {
                while (true){
                    if (!compareTime()){
                        ((MapApplication)getApplication()).resetDistance();
                    }
                    try {
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private boolean compareTime(){
        if (mCalender == null){
            mCalender = Calendar.getInstance();
        }

        if (day==-1){
            day = mCalender.get(Calendar.DATE);
            return true;
        }
        else {
            if (day == mCalender.get(Calendar.DATE)){
                return true;
            }
            else {
                day = mCalender.get(Calendar.DATE);
                return false;
            }
        }

    }
}
