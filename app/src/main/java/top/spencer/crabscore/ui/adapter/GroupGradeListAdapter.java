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
public class GroupGradeListAdapter extends RecyclerView.Adapter<GroupGradeListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<GroupResult> groupList;

    public GroupGradeListAdapter(List<GroupResult> data) {
        this.groupList = data;
    }

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public GroupGradeListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_judge, parent, false);
        GroupGradeListItemViewHolder groupGradeListItemViewHolder = new GroupGradeListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            groupGradeListItemViewHolder.itemView.setOnClickListener(v1 -> myOnItemClickListener.onItemClick(v1));
            groupGradeListItemViewHolder.itemView.setOnLongClickListener(v12 -> {
                myOnItemClickListener.onItemLongClick(v12);
                return true;
            });
        }
        return groupGradeListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GroupGradeListItemViewHolder holder, int position) {
        if (groupList.get(position) != null) {
            GroupResult groupResult = groupList.get(position);
            holder.itemView.setTag(groupResult);
            holder.groupId.setText(String.valueOf(groupResult.getGroupId()));
            holder.companyName.setText(groupResult.getCompanyName());
            if (groupResult.getQualityScoreF() == 0 &&
                    groupResult.getQualityScoreM() == 0 &&
                    groupResult.getTasteScoreM() != 0 &&
                    groupResult.getTasteScoreF() != 0) {
                holder.gradeStatus.setText("口感分已出");
            } else if (groupResult.getQualityScoreF() != 0 &&
                    groupResult.getQualityScoreM() != 0 &&
                    groupResult.getTasteScoreM() == 0 &&
                    groupResult.getTasteScoreF() == 0) {
                holder.gradeStatus.setText("种质分已出");
            } else if (groupResult.getQualityScoreF() == 0 &&
                    groupResult.getQualityScoreM() == 0 &&
                    groupResult.getTasteScoreM() == 0 &&
                    groupResult.getTasteScoreF() == 0) {
                holder.gradeStatus.setText("没有出分");
            } else if (groupResult.getQualityScoreF() != 0 &&
                    groupResult.getQualityScoreM() != 0 &&
                    groupResult.getTasteScoreM() != 0 &&
                    groupResult.getTasteScoreF() != 0) {
                holder.gradeStatus.setText("已全部出分");
            }
        }
    }
}
