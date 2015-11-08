package wamcs.runningbattle.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import wamcs.runningbattle.datebase.BaseDateHelper;
import wamcs.runningbattle.mapDistance.DistanceMaster;

/**
 * Created by zuliangwang on 15/10/25.
 */
public class DistanceService extends Service {

    private BaseDateHelper baseDateHelper;
    private double distance;
    private int time;
    private int askDistance;
    private int askTime;
    private DistanceMaster master;
    private int id;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Log.e("TAg","service");
        super.onCreate();
        baseDateHelper=new BaseDateHelper(this,"table.db",1);
        id=baseDateHelper.getTaskID();
        askDistance=baseDateHelper.getDetailDemand(id).get("meter");
        askTime=baseDateHelper.getDetailDemand(id).get("time");
        master=new DistanceMaster(this);
        master.beginCountDistance();
        ExecutorService es= Executors.newSingleThreadExecutor();
        es.execute(new listener());

    }

    class listener implements Runnable{

        @Override
        public void run() {
            int i=0;
            int percent=0;
            while (i<askTime){
                distance=master.getDistance();
                percent=(int)(distance*100/askDistance);
                baseDateHelper.updateTaskPercent(percent,id);
                baseDateHelper.updateDistance(Integer.parseInt(baseDateHelper.getInformation().get("distance")
                        .toString()) + (int) distance);
                i++;
                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            if(percent>=100) {
                baseDateHelper.deleteTaskList(id);
                int stoneNumber=baseDateHelper.getDetailReward(id).get("rewardThing");
                int score= baseDateHelper.getDetailReward(id).get("rewardNumber");
                baseDateHelper.updateScore(Integer.parseInt(baseDateHelper.getInformation().get("score")
                        .toString()) + score);
                baseDateHelper.updateStoneNumber(Integer.parseInt(baseDateHelper.getInformation().get("stoneNumber")
                        .toString()) + stoneNumber);
                baseDateHelper.updateTaskSelected(0, id);
                baseDateHelper.deleteTaskList(id);

            }

            master.stopCountDistance();
            onDestroy();
        }
    }


}
