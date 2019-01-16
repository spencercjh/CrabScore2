package top.spencer.crabscore.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.Company;

import java.util.List;
import java.util.Objects;

/**
 * 参选单位管理页面参选单位列表适配器
 *
 * @author spencercjh
 */
public class CompanyAdminListAdapter extends RecyclerView.Adapter<CompanyListItemViewHolder> {
    private MyOnItemClickListener myOnItemClickListener;
    private Context context;
    private List<Company> companyList;

    public CompanyAdminListAdapter(List<Company> data, Context context) {
        this.companyList = data;
        this.context = context;
    }

    public void setOnItemClickListener(MyOnItemClickListener mListener) {
        this.myOnItemClickListener = mListener;
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    @SuppressWarnings("Duplicates")
    @NonNull
    @Override
    public CompanyListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_admin, parent, false);
        CompanyListItemViewHolder companyListItemViewHolder = new CompanyListItemViewHolder(v);
        if (myOnItemClickListener != null) {
            companyListItemViewHolder.itemView.setOnClickListener(v1 -> myOnItemClickListener.onItemClick(v1));
            companyListItemViewHolder.itemView.setOnLongClickListener(v12 -> {
                myOnItemClickListener.onItemLongClick(v12);
                return true;
            });
        }
        return companyListItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyListItemViewHolder holder, int position) {
        if (companyList.get(position) != null) {
            Company company = companyList.get(position);
            holder.itemView.setTag(company);
            Glide.with(Objects.requireNonNull(context))
                    .load(company.getAvatarUrl())
                    .into(holder.avatar);
            holder.companyId.setText(String.valueOf(company.getCompanyId()));
            holder.companyName.setText(company.getCompanyName());
            holder.status.setText(company.getCompetitionId() == 1 ? "全部大赛有效" : "当前大赛有效");
        }
    }
}
