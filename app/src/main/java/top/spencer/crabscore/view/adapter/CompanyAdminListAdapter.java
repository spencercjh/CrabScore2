package top.spencer.crabscore.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.Company;

import java.util.List;

/**
 * 参选单位管理页面参选单位列表适配器
 *
 * @author spencercjh
 */
public class CompanyAdminListAdapter extends RecyclerView.Adapter<CompanyListItemViewHolder> {

    private List<Company> companyList;

    public CompanyAdminListAdapter(List<Company> data) {
        this.companyList = data;
    }

    @Override
    public int getItemCount() {
        return companyList.size();
    }

    @NonNull
    @Override
    public CompanyListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_admin, parent, false);
        return new CompanyListItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CompanyListItemViewHolder holder, int position) {
        if (companyList.get(position) != null) {
            Company company = companyList.get(position);
            holder.companyId.setText(String.valueOf(company.getCompanyId()));
            holder.companyName.setText(company.getCompanyName());
            holder.status.setText(company.getCompetitionId() == 1 ? "全部大赛有效" : "当前大赛有效");
        }
    }
}
