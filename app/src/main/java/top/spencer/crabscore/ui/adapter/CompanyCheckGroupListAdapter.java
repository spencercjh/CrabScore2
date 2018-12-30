package top.spencer.crabscore.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.dto.GroupResult;

import java.util.List;

/**
 * 评委用户组全部小组页面小组列表适配器
 *
 * @author spencercjh
 */
public class CompanyCheckGroupListAdapter extends RecyclerView.Adapter<CompanyCheckGroupListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<GroupResult> groupList;

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    public CompanyCheckGroupListAdapter(List<GroupResult> data) {
        this.groupList = data;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public CompanyCheckGroupListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_company, parent, false);
        CompanyCheckGroupListItemViewHolder companyCheckGroupListItemViewHolder = new CompanyCheckGroupListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            companyCheckGroupListItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClick(v);
                }
            });
            companyCheckGroupListItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnItemClickListener.onItemLongClick(v);
                    return true;
                }
            });
        }
        return companyCheckGroupListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyCheckGroupListItemViewHolder holder, int position) {
        if (groupList.get(position) != null) {
            GroupResult groupResult = groupList.get(position);
            holder.itemView.setTag(groupResult);
            holder.groupId.setText(String.valueOf(groupResult.getGroupId()));
            holder.companyName.setText(groupResult.getCompanyName());
            holder.fatnessScore.setText(String.valueOf((groupResult.getFatnessScoreF() +
                    groupResult.getFatnessScoreM()) / 2.0));
            holder.qualityScore.setText(String.valueOf((groupResult.getQualityScoreF() +
                    groupResult.getQualityScoreM()) / 2.0));
            holder.tasteScore.setText(String.valueOf((groupResult.getTasteScoreF() +
                    groupResult.getTasteScoreM()) / 2.0));
        }
    }
}
