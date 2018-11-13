package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 参选单位小组列表适配器的ViewHolder
 *
 * @author spencercjh
 */
class CompanyCheckGroupListItemViewHolder extends RecyclerView.ViewHolder {
    TextView groupId;
    TextView companyName;
    TextView fatnessScore;
    TextView qualityScore;
    TextView tasteScore;

    CompanyCheckGroupListItemViewHolder(View view) {
        super(view);
        groupId = view.findViewById(R.id.textview_group_id);
        companyName = view.findViewById(R.id.textview_company_name);
        fatnessScore = view.findViewById(R.id.textview_fatness_score);
        qualityScore = view.findViewById(R.id.textview_quality_score);
        tasteScore = view.findViewById(R.id.textview_taste_score);
    }
}


