package com.zuliangwang.runningbattle.ui.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.zuliangwang.runningbattle.R;
import com.zuliangwang.runningbattle.adapter.MainViewPagerAdapter;
import com.zuliangwang.runningbattle.presenter.ShowStepPresenter;
import com.zuliangwang.runningbattle.presenter.impl.ShowStepPresenterImpl;
import com.zuliangwang.runningbattle.ui.fragment.MainFragment;
import com.zuliangwang.runningbattle.ui.fragment.SelfInfoFragment;
import com.zuliangwang.runningbattle.ui.fragment.TaskFragment;
import com.zuliangwang.runningbattle.view.LoginView;
import com.zuliangwang.runningbattle.view.ShowStepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements ShowStepView{


    @InjectView(R.id.main_view_pager)
    ViewPager viewPager;

    private List<Fragment> fragmentList;
    private ShowStepPresenter showStepPresenter = new ShowStepPresenterImpl(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initial() {
        ButterKnife.inject(this);
        //在这里初始化viewpager和它控制的fragment
        fragmentList = new ArrayList<Fragment>();
        fragmentList.add(new TaskFragment());
        fragmentList.add(new MainFragment());
        fragmentList.add(new SelfInfoFragment());
        viewPager.setAdapter(new MainViewPagerAdapter(getSupportFragmentManager(),fragmentList));
    }

    @Override
    public void showStep(TextView textView) {
        String step = showStepPresenter.getTotalStep();
        textView.setText(step);
    }
}
