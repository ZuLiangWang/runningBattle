package wamcs.runningbattle.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import wamcs.runningbattle.NormalBattle.MainScene;
import wamcs.runningbattle.R;

public class MainFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.run_layout,container,false);
        Activity activity = getActivity();
        View view1 = inflater.inflate(R.layout.run_layout,container,false);
        MainScene mainScene = new MainScene(getActivity(),view, (ImageView)view1.findViewById(R.id.fighter),
                (ImageView)view1.findViewById(R.id.road));

        return view;
    }
}
