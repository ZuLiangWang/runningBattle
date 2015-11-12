package wamcs.runningbattle.datebase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//数据库
public class BaseDateHelper extends SQLiteOpenHelper{

    private Context context;
    /**
     * _id:id序号
     * monster:怪物名
     * information：副本信息
     * reward：奖励信息
     * challenge：挑战次数
     * challengeRemind:挑战剩余次数
     * image：怪物图片
     *  percent:完成度
     *
     * time：任务时间
     * meter：任务要求距离
     * times：任务要求次数
     *
     * rewardNUmber：任务积分
     * rewardThing：奖励物品数量
     *
     * task:所属任务的id;
     */
    private final String CREATE_TASK_TABLE="create table task(_id integer primary " +
            "key autoincrement,monster text,information text,reward text,challenge integer," +
            "challengeRemind integer,image integer,time integer,meter integer,times integer," +
            "rewardNumber integer,rewardThing integer,percent integer,isSelected integer)";
    private final String CREATE_TASK_LIST="create table tasklist(_id integer primary" +
            " key autoincrement,task integer)";

    /**
     * distance:距离
     * score：积分
     * taskNumber：任务数量
     * exp:经验
     * equipmentRank：武器等级
     * stoneNumber：石头数量
     * stronger：力量属性
     * agile：敏捷属性
     *
     */
    private final String CREATE_INFORMATION="create table information(id integer," +
            "distance integer,score integer,taskNumber integer,exp integer," +
            "equipmentRank integer,stoneNumber integer,stronger integer,agile integer)";
    private final String CREATE_RANK="create table rank(position integer,score integer,name text)";

    public BaseDateHelper(Context context, String name, int version) {
        super(context, name, null, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TASK_TABLE);
        db.execSQL(CREATE_TASK_LIST);
        db.execSQL(CREATE_INFORMATION);
        db.execSQL(CREATE_RANK);
        db.execSQL("insert into information values(1,0,0,0,0,1,0,5,5)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //获取adpter需要数据
    public ArrayList<Map<String,Object>> getTaskInformation(){
        ArrayList<Map<String,Object>> list=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from task",null);
        for(;cursor.moveToNext();cursor.isAfterLast()){
            Map<String,Object> map=new HashMap<>();
            int id=cursor.getInt(cursor.getColumnIndex("_id"));
            String monster=cursor.getString(cursor.getColumnIndex("monster"));
            String information=cursor.getString(cursor.getColumnIndex("information"));
            String reward=cursor.getString(cursor.getColumnIndex("reward"));
            int challenge=cursor.getInt(cursor.getColumnIndex("challenge"));
            int challengeRemind=cursor.getInt(cursor.getColumnIndex("challengeRemind"));
            int image=cursor.getInt(cursor.getColumnIndex("image"));
            int percent=cursor.getInt(cursor.getColumnIndex("percent"));
            int isSelected=cursor.getInt(cursor.getColumnIndex("isSelected"));
            map.put("id",id);
            map.put("monster",monster);
            map.put("information",information);
            map.put("reward",reward);
            map.put("challenge",challenge);
            map.put("challengeRemind",challengeRemind);
            map.put("image",image);
            map.put("percent",percent);
            map.put("isSelected",isSelected);
            list.add(map);
        }
        cursor.close();
        Log.e("tag",list.size()+"");
        return list;
    }

    //获取任务详细要求
    public Map<String,Integer> getDetailDemand(int id){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from task where _id="+id,null);
        cursor.moveToNext();
        Map<String,Integer> map=new HashMap<>();
        int time=cursor.getInt(cursor.getColumnIndex("time"));
        int meter=cursor.getInt(cursor.getColumnIndex("meter"));
        int times=cursor.getInt(cursor.getColumnIndex("times"));
        map.put("time",time);
        map.put("meter",meter);
        map.put("times",times);
        cursor.close();
        return map;
    }

    //获取奖励详细要求
    public Map<String,Integer> getDetailReward(int id){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from task where _id="+id,null);
        cursor.moveToNext();
        Map<String, Integer> map = new HashMap<>();
        int rewardNumber=cursor.getInt(cursor.getColumnIndex("rewardNumber"));
        int rewardThing=cursor.getInt(cursor.getColumnIndex("rewardThing"));
        map.put("rewardNumber",rewardNumber);
        map.put("rewardThing",rewardThing);
        return map;
    }

    //获取tasklist中任务对应于task表的id
    public Integer getTaskID(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select task from tasklist",null);
        cursor.moveToNext();
        int id=cursor.getInt(cursor.getColumnIndex("task"));

        return id;
    }

    //添加任务
    public void addTask(Map<String,Object> map){
        SQLiteDatabase db=getWritableDatabase();
        String monster=map.get("monster").toString();
        String information=map.get("information").toString();
        String reward=map.get("reward").toString();
        int challenge=Integer.parseInt(map.get("challenge").toString());
        int challengeRemind=Integer.parseInt(map.get("challengeRemind").toString());
        int image=Integer.parseInt(map.get("image").toString());
        int percent=Integer.parseInt(map.get("percent").toString());
        int time=Integer.parseInt(map.get("time").toString());
        int meter=Integer.parseInt(map.get("meter").toString());
        int times=Integer.parseInt(map.get("times").toString());
        int rewardNumber=Integer.parseInt(map.get("rewardNumber").toString());
        int rewardThing=Integer.parseInt(map.get("rewardThing").toString());
        int isSelected=Integer.parseInt(map.get("isSelected").toString());
        db.execSQL("insert into task values(null,'" + monster + "','" + information + "'" +
                ",'" + reward + "'," + challenge + "," + challengeRemind + "," + image + "," + time + "," +
                "" + meter + "," + times + "," + rewardNumber + "," + rewardThing + "," + percent +
                "," + isSelected + ")");
    }

    //添加任务列表
    public void addTaskList(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("insert into tasklist values(null,"+id+")");
    }

    //删除任务列表
    public void deleteTaskList(int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete from tasklist where task="+id);
    }

    //获取information表
    public Map<String,Object> getInformation(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from information", null);
        cursor.moveToNext();
        Map<String,Object> map=new HashMap<>();
        int distance=cursor.getInt(cursor.getColumnIndex("distance"));
        int score=cursor.getInt(cursor.getColumnIndex("score"));
        int taskNumber=cursor.getInt(cursor.getColumnIndex("taskNumber"));
        int exp=cursor.getInt(cursor.getColumnIndex("exp"));
        int equipmentRank=cursor.getInt(cursor.getColumnIndex("equipmentRank"));
        int stoneNumber=cursor.getInt(cursor.getColumnIndex("stoneNumber"));
        int stronger=cursor.getInt(cursor.getColumnIndex("stronger"));
        int agile=cursor.getInt(cursor.getColumnIndex("agile"));
        map.put("distance",distance);
        map.put("score",score);
        map.put("taskNumber",taskNumber);
        map.put("exp",exp);
        map.put("equipmentRank",equipmentRank);
        map.put("stoneNumber",stoneNumber);
        map.put("stronger",stronger);
        map.put("agile",agile);
        cursor.close();
        return map;
    }

    public int getScore(){
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select score from information", null);
        cursor.moveToNext();
        return cursor.getInt(cursor.getColumnIndex("score"));
    }

    //information更新
    public void updateDistance(int distance){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set distance=" + distance + " where id=1");
    }

    public void updateScore(int score){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set score=" + score+" where id=1");
    }

    public void updatetaskNumber(int number){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set taskNumber=" + number+" where id=1");
    }

    public void updateExp(int exp){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set exp=" + exp+" where id=1");
    }

    public void updateEquipmentRank(int rank){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set equipmentRank=" +rank+" where id=1");
    }

    public void updateStoneNumber(int number){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set stoneNumber=" + number+" where id=1");
    }

    public void updateStronger(int stronger){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set stronger=" +stronger+" where id=1");
    }

    public void updateAgile(int agile){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update information set agile=" + agile+" where id=1");
    }

    //更新表task
    public void updateTaskPercent(int percent,int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update task set percent=" +percent+" where _id="+id);
    }

    public void updateTaskSelected(int isSelected,int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update task set isSelected="+isSelected+" where _id="+id);
    }

    public void updateTaskChallengeRemind(int challengeRemind,int id){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("update task set challengeRemind="+challengeRemind+" where _id="+id);
    }

    //获取rank表
    public ArrayList<Map<String,Object>> getRank(){
        ArrayList<Map<String,Object>> list=new ArrayList<>();
        SQLiteDatabase db=getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from rank order by position",null);
        for(;cursor.moveToNext();cursor.isAfterLast()){
            Map<String,Object> map=new HashMap<>();
            int position=cursor.getInt(cursor.getColumnIndex("position"));
            int score=cursor.getInt(cursor.getColumnIndex("score"));
            String name=cursor.getString(cursor.getColumnIndex("name"));
            map.put("position",position);
            map.put("score",score);
            map.put("name",name);
            list.add(map);
        }
        return list;
    }

    //
    public void deleteRank(){
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL("delete * from rank");
    }
}
