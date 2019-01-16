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
import top.spencer.crabscore.model.entity.Crab;

import java.util.List;
import java.util.Objects;

/**
 * 参选单位用户组某一小组的全部螃蟹列表适配器
 *
 * @author spencercjh
 */
public class StaffCrabListAdapter extends RecyclerView.Adapter<StaffCrabListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private List<Crab> crabList;
    private Context context;

    public StaffCrabListAdapter(List<Crab> data, Context context) {
        this.crabList = data;
        this.context = context;
    }

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return crabList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public StaffCrabListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_crab, parent, false);
        StaffCrabListItemViewHolder staffCrabListItemViewHolder = new StaffCrabListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            staffCrabListItemViewHolder.itemView.setOnClickListener(v1 -> myOnItemClickListener.onItemClick(v1));
            staffCrabListItemViewHolder.itemView.setOnLongClickListener(v12 -> {
                myOnItemClickListener.onItemLongClick(v12);
                return true;
            });
        }
        return staffCrabListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StaffCrabListItemViewHolder holder, int position) {
        if (crabList.get(position) != null) {
            Crab crab = crabList.get(position);
            holder.itemView.setTag(crab);
            Glide.with(Objects.requireNonNull(context))
                    .load(crab.getAvatarUrl())
                    .into(holder.avatar);
            holder.crabId.setText(String.valueOf(crab.getCrabId()));
            holder.crabSex.setText(crab.getCrabSex().equals(CommonConstant.CRAB_MALE) ? "雄性" : "雌性");
            holder.groupId.setText(String.valueOf(crab.getGroupId()));
            holder.label.setText(crab.getCrabLabel());
            holder.weight.setText(String.valueOf(crab.getCrabWeight()));
            holder.length.setText(String.valueOf(crab.getCrabLength()));
            holder.fatness.setText(String.valueOf(crab.getCrabFatness()));
        }
    }
}
