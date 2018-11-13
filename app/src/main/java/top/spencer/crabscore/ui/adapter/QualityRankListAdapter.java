package top.spencer.crabscore.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.vo.GroupResult;

import java.util.List;

/**
 * 种质分数排名列表适配器
 *
 * @author spencercjh
 */
public class QualityRankListAdapter extends RecyclerView.Adapter<RankListItemViewHolder> {

    private List<GroupResult> groupList;

    public QualityRankListAdapter(List<GroupResult> data) {
        this.groupList = data;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @NonNull
    @Override
    public RankListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_rank, parent, false);
        return new RankListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RankListItemViewHolder holder, int position) {
        holder.order.setText(String.valueOf(position + 1));
        if (groupList.get(position) != null) {
            GroupResult group = groupList.get(position);
            holder.groupId.setText(String.valueOf(group.getGroupId()));
            holder.companyName.setText(group.getCompanyName());
            holder.score.setText(String.valueOf((group.getFatnessScoreF() + group.getFatnessScoreM()) / 2.0));
        }
    }

}
