package wamcs.runningbattle.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Map;

import wamcs.runningbattle.R;
import wamcs.runningbattle.Utils;
import wamcs.runningbattle.datebase.BaseDateHelper;
import wamcs.runningbattle.fragment.MainFragment;
import wamcs.runningbattle.fragment.SelfInformationFragment;
import wamcs.runningbattle.fragment.TaskFragment;
import wamcs.runningbattle.view.CircleView;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private CircleView circleView1;
    private CircleView circleView2;
    private CircleView circleView3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BaseDateHelper baseDateHelper=new BaseDateHelper(this,"table.db",1);
        if(!getSharedPreferences("init", Context.MODE_PRIVATE).getBoolean("isInit",false)) {
            ArrayList<Map<String, Object>> list = Utils.getMap();
            for (int i = 0; i < list.size(); i++) {
                baseDateHelper.addTask(list.get(i));
            }
            getSharedPreferences("init", Context.MODE_PRIVATE).edit().putBoolean("isInit",true).commit();
        }
        init();
        setupFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }
    //控件加载
    private void init(){
        viewPager= (ViewPager) findViewById(R.id.viewPager);
        circleView1= (CircleView) findViewById(R.id.circle1);
        circleView2= (CircleView) findViewById(R.id.circle2);
        circleView3= (CircleView) findViewById(R.id.circle3);
    }

    //fragment加载
    private void setupFragment(){
        final ArrayList<Fragment> list=new ArrayList<>();
        Fragment mainFragment=new MainFragment();
        Fragment selfInformationFragment=new SelfInformationFragment();
        Fragment taskFragment=new TaskFragment();
        list.add(taskFragment);
        list.add(mainFragment);
        list.add(selfInformationFragment);

        FragmentPagerAdapter adapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        circleView1.setImageDrawable(getResources().getDrawable(R.color.circleSelected));
                        circleView2.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        circleView3.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        break;
                    case 1:
                        circleView1.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        circleView2.setImageDrawable(getResources().getDrawable(R.color.circleSelected));
                        circleView3.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        break;
                    case 2:
                        circleView1.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        circleView2.setImageDrawable(getResources().getDrawable(R.color.circleUnSelected));
                        circleView3.setImageDrawable(getResources().getDrawable(R.color.circleSelected));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
