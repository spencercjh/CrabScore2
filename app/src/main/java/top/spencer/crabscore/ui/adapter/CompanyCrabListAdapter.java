package top.spencer.crabscore.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import top.spencer.crabscore.R;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.dto.CrabScoreResult;

import java.util.List;
import java.util.Objects;

/**
 * 参选单位用户组某一小组的全部螃蟹列表适配器
 *
 * @author spencercjh
 */
public class CompanyCrabListAdapter extends RecyclerView.Adapter<CompanyCrabListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<CrabScoreResult> crabScoreResultList;
    private Context context;

    public CompanyCrabListAdapter(List<CrabScoreResult> data, Context context) {
        this.crabScoreResultList = data;
        this.context = context;
    }

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return crabScoreResultList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public CompanyCrabListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crab_score_result, parent, false);
        CompanyCrabListItemViewHolder companyCrabListItemViewHolder = new CompanyCrabListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            companyCrabListItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClick(v);
                }
            });
            companyCrabListItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnItemClickListener.onItemLongClick(v);
                    return true;
                }
            });
        }
        return companyCrabListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyCrabListItemViewHolder holder, int position) {
        if (crabScoreResultList.get(position) != null) {
            CrabScoreResult crabScoreResult = crabScoreResultList.get(position);
            Glide.with(Objects.requireNonNull(context))
                    .load(crabScoreResult.getAvatarUrl())
                    .into(holder.avatar);
            holder.itemView.setTag(crabScoreResult);
            holder.crabId.setText(String.valueOf(crabScoreResult.getCrabId()));
            holder.groupId.setText(String.valueOf(crabScoreResult.getGroupId()));
            holder.crabSex.setText(crabScoreResult.getCrabSex().equals(CommonConstant.CRAB_MALE) ? "雄性" : "雌性");
            holder.label.setText(crabScoreResult.getCrabLabel());
            holder.weight.setText(String.valueOf(crabScoreResult.getCrabWeight()));
            holder.length.setText(String.valueOf(crabScoreResult.getCrabLength()));
            holder.fatness.setText(String.valueOf(crabScoreResult.getCrabFatness()));
            holder.qualityScore.setText(String.valueOf(crabScoreResult.getQualityScoreFin()));
            holder.tasteScore.setText(String.valueOf(crabScoreResult.getTasteScoreFin()));
        }
    }
}
