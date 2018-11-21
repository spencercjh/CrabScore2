package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * @author spencercjh
 */
class QualityScoreListViewHolder extends RecyclerView.ViewHolder {
    TextView crabId;
    TextView crabSex;
    TextView userId;
    TextView scoreFin;
    TextView scoreBts;
    TextView scoreFts;
    TextView scoreEc;
    TextView scoreDscc;
    TextView scoreBbyzt;

    QualityScoreListViewHolder(View itemView) {
        super(itemView);
        crabId = itemView.findViewById(R.id.textview_crab_id);
        crabSex = itemView.findViewById(R.id.textview_crab_sex);
        userId = itemView.findViewById(R.id.textview_judge_id);
        scoreFin = itemView.findViewById(R.id.textview_final_score);
        scoreBts = itemView.findViewById(R.id.textview_score_bts);
        scoreFts = itemView.findViewById(R.id.textview_score_fts);
        scoreEc = itemView.findViewById(R.id.textview_score_ec);
        scoreDscc = itemView.findViewById(R.id.textview_score_dscc);
        scoreBbyzt = itemView.findViewById(R.id.textview_score_bbyzt);
    }
}
