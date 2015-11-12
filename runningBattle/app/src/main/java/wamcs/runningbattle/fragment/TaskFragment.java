package wamcs.runningbattle.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import wamcs.runningbattle.R;
import wamcs.runningbattle.activity.TasksActivity;
import wamcs.runningbattle.adapter.TaskAdapter;
import wamcs.runningbattle.datebase.BaseDateHelper;

public class TaskFragment extends Fragment {
    private BaseDateHelper baseDateHelper;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseDateHelper=new BaseDateHelper(getActivity(),"table.db",1);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_task,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.task_recyclerview);
        TaskAdapter adapter=new TaskAdapter(getActivity(),baseDateHelper.getTaskInformation());
        adapter.setListener(new TaskAdapter.selectedListener() {
            @Override
            public void selected(int id, int remind) {
                baseDateHelper.addTaskList(id);
                baseDateHelper.updateTaskSelected(1, id);
                baseDateHelper.updateTaskChallengeRemind(remind - 1, id);
            }
        });
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        return view;
    }

}
