package top.spencer.crabscore.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 分数排名列表适配器的ViewHolder
 *
 * @author spencercjh
 */
class GroupListItemViewHolder extends RecyclerView.ViewHolder {
    TextView groupId;
    TextView companyName;
    TextView gradeStatus;

    GroupListItemViewHolder(View view) {
        super(view);
        groupId = view.findViewById(R.id.textview_group_id);
        companyName = view.findViewById(R.id.textview_company_name);
        gradeStatus = view.findViewById(R.id.textview_grade_status);
    }
}


