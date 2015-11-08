package wamcs.runningbattle.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import wamcs.runningbattle.R;
import wamcs.runningbattle.activity.TasksActivity;
import wamcs.runningbattle.service.DistanceService;
import wamcs.runningbattle.view.CircleView;

//taskFragment中recyclerview的adapter
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Map<String,Object>> list;
    private selectedListener listener;

    public TaskAdapter(Context context, ArrayList<Map<String, Object>> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.recyclerview_task_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.circleView.setImageDrawable(context.getResources()
                .getDrawable(Integer.parseInt(list.get(position).get("image").toString())));
        holder.monster.setText(list.get(position).get("monster").toString());
        holder.information.setText(list.get(position).get("information").toString());
        holder.reward.setText(list.get(position).get("reward").toString());
        String challengeReminder="剩余"+list.get(position).get("challengeRemind").toString()+"次";
        holder.challengeReminder.setText(challengeReminder);
        holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.tiaozhan2));
        if(Integer.parseInt(list.get(position).get("isSelected").toString())==0) {
            holder.imageButton.setImageDrawable(context.getResources().getDrawable(R.drawable.tiaozhan));
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("imageButtonChanllenge","chanllenge");
                    listener.selected(Integer.parseInt(list.get(position).get("id").toString()),
                            Integer.parseInt(list.get(position).get("challengeRemind").toString()));
                    Intent intent1=new Intent(context, DistanceService.class);
                    context.startService(intent1);
                    Intent intent2=new Intent(context, TasksActivity.class);
                    context.startActivity(intent2);

                }
            });
        }


    }

    @Override
    public int getItemCount() {
        Log.e("TAg",list.size()+"");
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleView circleView;
        private TextView monster;
        private TextView information;
        private TextView reward;
        private ImageButton imageButton;
        private TextView challengeReminder;
        public ViewHolder(View itemView) {
            super(itemView);
            circleView= (CircleView) itemView.findViewById(R.id.task_item_monster_image);
            monster= (TextView) itemView.findViewById(R.id.task_monster_name);
            information= (TextView) itemView.findViewById(R.id.task_information);
            reward= (TextView) itemView.findViewById(R.id.task_reward);
            challengeReminder= (TextView) itemView.findViewById(R.id.task_challenge_reminder);
            imageButton= (ImageButton) itemView.findViewById(R.id.task_item_image_button);
        }
    }

    public interface selectedListener{
         void selected(int id, int remind);
    }

    public void setListener(selectedListener listener){
        this.listener=listener;
    }
}
