package top.spencer.crabscore.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.vo.GroupResult;

import java.util.List;

/**
 * 评委用户组全部小组页面小组列表适配器
 *
 * @author spencercjh
 */
public class GroupJudgeListAdapter extends RecyclerView.Adapter<GroupListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<GroupResult> groupList;

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    public GroupJudgeListAdapter(List<GroupResult> data) {
        this.groupList = data;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public GroupListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_judge, parent, false);
        GroupListItemViewHolder groupListItemViewHolder = new GroupListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            groupListItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myOnItemClickListener.onItemClick(v);
                }
            });
            groupListItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    myOnItemClickListener.onItemLongClick(v);
                    return true;
                }
            });
        }
        return groupListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupListItemViewHolder holder, int position) {
        if (groupList.get(position) != null) {
            GroupResult groupResult = groupList.get(position);
            holder.itemView.setTag(groupResult);
            holder.groupId.setText(String.valueOf(groupResult.getGroupId()));
            holder.companyName.setText(groupResult.getCompanyName());
            if (groupResult.getFatnessScoreF() == 0 ||
                    groupResult.getFatnessScoreM() == 0 ||
                    groupResult.getQualityScoreF() == 0 ||
                    groupResult.getQualityScoreM() == 0 ||
                    groupResult.getTasteScoreM() == 0 ||
                    groupResult.getTasteScoreF() == 0) {
                holder.gradeStatus.setText("已部分评分");
            } else if (groupResult.getFatnessScoreF() == 0 &&
                    groupResult.getFatnessScoreM() == 0 &&
                    groupResult.getQualityScoreF() == 0 &&
                    groupResult.getQualityScoreM() == 0 &&
                    groupResult.getTasteScoreM() == 0 &&
                    groupResult.getTasteScoreF() == 0) {
                holder.gradeStatus.setText("没有评分");
            } else if (groupResult.getFatnessScoreF() != 0 &&
                    groupResult.getFatnessScoreM() != 0 &&
                    groupResult.getQualityScoreF() != 0 &&
                    groupResult.getQualityScoreM() != 0 &&
                    groupResult.getTasteScoreM() != 0 &&
                    groupResult.getTasteScoreF() != 0) {
                holder.gradeStatus.setText("已全部评分");
            }
        }
    }
}
