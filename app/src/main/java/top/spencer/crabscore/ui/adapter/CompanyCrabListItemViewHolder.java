package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 分数排名列表适配器的ViewHolder
 *
 * @author spencercjh
 */
class CompanyCrabListItemViewHolder extends RecyclerView.ViewHolder {
    ImageView avatar;
    TextView crabId;
    TextView groupId;
    TextView crabSex;
    TextView label;
    TextView weight;
    TextView length;
    TextView fatness;
    TextView qualityScore;
    TextView tasteScore;


    CompanyCrabListItemViewHolder(View view) {
        super(view);
        crabId = view.findViewById(R.id.textview_crab_id);
        groupId = view.findViewById(R.id.textview_group_id);
        crabSex = view.findViewById(R.id.textview_crab_sex);
        label = view.findViewById(R.id.textview_crab_label);
        weight = view.findViewById(R.id.textview_crab_weight);
        length = view.findViewById(R.id.textview_crab_length);
        fatness = view.findViewById(R.id.textview_crab_fatness);
        qualityScore = view.findViewById(R.id.textview_quality_score);
        tasteScore = view.findViewById(R.id.textview_taste_score);
        avatar = view.findViewById(R.id.imageview_crab);
    }
}