package top.spencer.crabscore.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 分数排名列表适配器的ViewHolder
 * //TODO Add user phone
 *
 * @author spencercjh
 */
class UserListItemViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    TextView displayName;
    TextView role;
    TextView status;

    UserListItemViewHolder(View view) {
        super(view);
        username = view.findViewById(R.id.textview_user_name);
        displayName = view.findViewById(R.id.textview_display_name);
        role = view.findViewById(R.id.textview_role);
        status = view.findViewById(R.id.textview_status);
    }
}


