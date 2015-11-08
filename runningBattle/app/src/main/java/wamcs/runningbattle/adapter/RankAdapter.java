package wamcs.runningbattle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import wamcs.runningbattle.R;

public class RankAdapter extends RecyclerView.Adapter<RankAdapter.RankViewHolder> {
    private Context mContext;
    private ArrayList<Map<String ,Object>> list;
    public class RankViewHolder extends RecyclerView.ViewHolder{
        private TextView leftTextView;
        private TextView rightTextView;
        private TextView number;
        public RankViewHolder(View itemView) {
            super(itemView);
            leftTextView = (TextView) itemView.findViewById(R.id.rank_item_left_text);
            rightTextView = (TextView) itemView.findViewById(R.id.rank_item_right_text);
            number= (TextView) itemView.findViewById(R.id.rank_item_number);

        }
    }

    public RankAdapter(Context context,ArrayList<Map<String ,Object>> list) {
        this.mContext = context;
        this.list=list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(RankViewHolder holder, int position) {
        holder.number.setText(list.get(position).get("position").toString());
        holder.leftTextView.setText(list.get(position).get("name").toString());
        holder.rightTextView.setText(list.get(position).get("score").toString());
    }

    @Override
    public RankViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recyclerview_rank_item,parent,false);
        return new RankViewHolder(view);

    }
}
