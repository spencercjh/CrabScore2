package top.spencer.crabscore.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * @author spencercjh
 */
public class RankListItemViewHolder extends RecyclerView.ViewHolder {
    public TextView order;
    public TextView groupId;
    public TextView companyName;
    public TextView score;

    public RankListItemViewHolder(View view) {
        super(view);
        order = view.findViewById(R.id.textview_order);
        groupId = view.findViewById(R.id.textview_group_id);
        companyName = view.findViewById(R.id.textview_company_name);
        score = view.findViewById(R.id.textview_score);
    }
}


