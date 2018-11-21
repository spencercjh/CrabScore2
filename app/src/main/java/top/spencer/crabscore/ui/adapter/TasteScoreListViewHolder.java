package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * @author spencercjh
 */
class TasteScoreListViewHolder extends RecyclerView.ViewHolder {
    TextView crabId;
    TextView crabSex;
    TextView userId;
    TextView scoreFin;
    TextView scoreYgys;
    TextView scoreSys;
    TextView scoreGhys;
    TextView scoreXwxw;
    TextView scoreGh;
    TextView scoreFbjr;
    TextView scoreBzjr;


    TasteScoreListViewHolder(View itemView) {
        super(itemView);
        crabId = itemView.findViewById(R.id.textview_crab_id);
        crabSex = itemView.findViewById(R.id.textview_crab_sex);
        userId = itemView.findViewById(R.id.textview_judge_id);
        scoreFin = itemView.findViewById(R.id.textview_final_score);
        scoreYgys = itemView.findViewById(R.id.textview_score_ygys);
        scoreSys = itemView.findViewById(R.id.textview_score_sys);
        scoreGhys = itemView.findViewById(R.id.textview_score_ghys);
        scoreXwxw = itemView.findViewById(R.id.textview_score_xwxw);
        scoreGh = itemView.findViewById(R.id.textview_score_gh);
        scoreFbjr = itemView.findViewById(R.id.textview_score_fbjr);
        scoreBzjr = itemView.findViewById(R.id.textview_score_bzjr);
    }
}
