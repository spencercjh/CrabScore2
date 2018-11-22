package top.spencer.crabscore.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.TasteScore;

import java.util.List;

/**
 * @author spencercjh
 */
public class TasteScoreListAdapter extends RecyclerView.Adapter<TasteScoreListViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<TasteScore> tasteScoreList;

    public TasteScoreListAdapter(List<TasteScore> tasteScoreList) {
        this.tasteScoreList = tasteScoreList;
    }

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public TasteScoreListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_admin, parent, false);
        TasteScoreListViewHolder tasteScoreListViewHolder = new TasteScoreListViewHolder(v);
        if (myOnItemClickListener != null) {
            tasteScoreListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClick(v);
                }
            });
            tasteScoreListViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnItemClickListener.onItemLongClick(v);
                    return true;
                }
            });
        }
        return tasteScoreListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasteScoreListViewHolder holder, int position) {
        if (tasteScoreList.get(position) != null) {
            TasteScore tasteScore = tasteScoreList.get(position);
            holder.itemView.setTag(tasteScore);
            holder.crabId.setText(String.valueOf(tasteScore.getScoreId()));
            holder.crabSex.setText((tasteScore.getCrabSex().equals(CommonConstant.CRAB_MALE) ? "雄性" : "雌性"));
            holder.userId.setText(String.valueOf(tasteScore.getUserId()));
            holder.scoreFin.setText(String.valueOf(tasteScore.getScoreFin()));
            holder.scoreYgys.setText(String.valueOf(tasteScore.getScoreYgys()));
            holder.scoreSys.setText(String.valueOf(tasteScore.getScoreSys()));
            holder.scoreGhys.setText(String.valueOf(tasteScore.getScoreGhys()));
            holder.scoreXwxw.setText(String.valueOf(tasteScore.getScoreXwxw()));
            holder.scoreGh.setText(String.valueOf(tasteScore.getScoreGh()));
            holder.scoreFbjr.setText(String.valueOf(tasteScore.getScoreFbjr()));
            holder.scoreBzjr.setText(String.valueOf(tasteScore.getScoreBzjr()));
        }
    }

    @Override
    public int getItemCount() {
        return tasteScoreList.size();
    }
}