package wamcs.runningbattle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import wamcs.runningbattle.R;
import wamcs.runningbattle.adapter.RankAdapter;
import wamcs.runningbattle.datebase.BaseDateHelper;

public class RankActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_rank);
        BaseDateHelper helper=new BaseDateHelper(this,"table.db",1);
        recyclerView= (RecyclerView) findViewById(R.id.rank_recyclerview);
        RankAdapter adapter=new RankAdapter(this,helper.getRank());
        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }


}
