package wamcs.runningbattle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//数值储存类
public class Utils {
    final static String[] monster={"史矛格","史莱姆","格李瑞"};
    final static String[] information={"在5分中内完成1000米跑步","在10秒内完成100米冲刺","在半小时内完成5000米马拉松"};
    final static String[] reward={"积分2000，卡鲁石60","积分3000，卡鲁石60","积分2000，卡鲁石160"};
    final static int[] challenge={5,3,1};
    final static int[] challengeRemind={5,3,1};
    final static int[] image={R.drawable.lain,R.drawable.lian2,R.drawable.lian3};
    final static int[] time={300,10,1800};
    final static int[] meter={1000,100,5000};
    final static int[] times={0,0,0};
    final static int[] rewardNumber={2000,3000,2000};
    final static int[] rewardThing={60,60,160};
    public static int rank;

    public static ArrayList<Map<String,Object>> getMap(){
        ArrayList<Map<String,Object>> list=new ArrayList<>();
        for (int i=0;i<monster.length;i++){
            Map<String,Object> map=new HashMap<>();
            map.put("monster",monster[i]);
            map.put("information",information[i]);
            map.put("reward",reward[i]);
            map.put("challenge",challenge[i]);
            map.put("challengeRemind",challengeRemind[i]);
            map.put("image",image[i]);
            map.put("time",time[i]);
            map.put("meter",meter[i]);
            map.put("times",times[i]);
            map.put("rewardNumber",rewardNumber[i]);
            map.put("rewardThing",rewardThing[i]);
            map.put("percent",0);
            map.put("isSelected",0);
            list.add(map);
        }
        return list;
    }
}
