package top.spencer.crabscore.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import top.spencer.crabscore.R;

/**
 * 分数排名列表适配器的ViewHolder
 *
 * @author spencercjh
 */
class CompanyListItemViewHolder extends RecyclerView.ViewHolder {
    TextView companyId;
    TextView companyName;
    TextView status;
    ImageView avatar;

    CompanyListItemViewHolder(View view) {
        super(view);
        companyId = view.findViewById(R.id.textview_company_id);
        companyName = view.findViewById(R.id.textview_company_name);
        status = view.findViewById(R.id.textview_status);
        avatar = view.findViewById(R.id.imageview_company);
    }
}