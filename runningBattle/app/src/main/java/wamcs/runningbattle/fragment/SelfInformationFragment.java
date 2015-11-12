package wamcs.runningbattle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import wamcs.runningbattle.R;
import wamcs.runningbattle.Utils;
import wamcs.runningbattle.activity.RankActivity;
import wamcs.runningbattle.activity.ScoreActivity;
import wamcs.runningbattle.datebase.BaseDateHelper;
import wamcs.runningbattle.view.CircleView;

public class SelfInformationFragment extends Fragment {
    private CircleView circleView;
    private LinearLayout scoreLayout;
    private LinearLayout rankLayout;
    private LinearLayout aboutLayout;
    private TextView scoreTv;
    private TextView rankTv;
    private BaseDateHelper helper;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        helper=new BaseDateHelper(getActivity(),"table.db",1);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_selfinformation,container,false);
        circleView= (CircleView) view.findViewById(R.id.self_image);
        scoreLayout= (LinearLayout) view.findViewById(R.id.self_score_layout);
        scoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), ScoreActivity.class);
                getActivity().startActivity(intent);
            }
        });
        rankLayout= (LinearLayout) view.findViewById(R.id.self_rank_layout);
        rankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RankActivity.class);
                getActivity().startActivity(intent);
            }
        });
        aboutLayout= (LinearLayout) view.findViewById(R.id.self_about_layout);
        scoreTv= (TextView) view.findViewById(R.id.self_score);
        scoreTv.setText(helper.getScore()+"");
        rankTv= (TextView) view.findViewById(R.id.self_rank);
        rankTv.setText(Utils.rank+"");
        return view;
    }
}
