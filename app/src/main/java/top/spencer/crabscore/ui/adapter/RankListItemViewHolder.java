package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 分数排名列表适配器的ViewHolder
 *
 * @author spencercjh
 */
class RankListItemViewHolder extends RecyclerView.ViewHolder {
    TextView order;
    TextView groupId;
    TextView companyName;
    TextView score;

    RankListItemViewHolder(View view) {
        super(view);
        order = view.findViewById(R.id.textview_order);
        groupId = view.findViewById(R.id.textview_group_id);
        companyName = view.findViewById(R.id.textview_company_name);
        score = view.findViewById(R.id.textview_score);
    }
}


