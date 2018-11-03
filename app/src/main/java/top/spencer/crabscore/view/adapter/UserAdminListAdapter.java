package top.spencer.crabscore.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.model.entity.User;

import java.util.List;

/**
 * 用户管理页面用户列表适配器
 *
 * @author spencercjh
 */
public class UserAdminListAdapter extends RecyclerView.Adapter<UserListItemViewHolder> {

    private List<User> userList;

    public UserAdminListAdapter(List<User> data) {
        this.userList = data;
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @NonNull
    @Override
    public UserListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_admin, parent, false);
        return new UserListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListItemViewHolder holder, int position) {
        if (userList.get(position) != null) {
            User user = userList.get(position);
            holder.username.setText(user.getUserName());
            holder.displayName.setText(user.getDisplayName());
            if (user.getStatus().equals(CommonConstant.USER_STATUS_NORMAL)) {
                if (user.getCompetitionId().equals(CommonConstant.USER_COMPETITION_ALL)) {
                    holder.status.setText("永久有效");
                } else {
                    holder.status.setText("仅当前赛事有效");
                }
            } else if (user.getStatus().equals(CommonConstant.USER_STATUS_LOCK)) {
                holder.status.setText("未启用");
            }
            if (user.getRoleId().equals(CommonConstant.USER_TYPE_ADMIN)) {
                holder.role.setText("管理员");
            } else if (user.getRoleId().equals(CommonConstant.USER_TYPE_JUDGE)) {
                holder.role.setText("评委");
            } else if (user.getRoleId().equals(CommonConstant.USER_TYPE_STAFF)) {
                holder.role.setText("工作人员");
            } else if (user.getRoleId().equals(CommonConstant.USER_TYPE_COMPANY)) {
                holder.role.setText("参选单位");
            }
        }
    }

}
