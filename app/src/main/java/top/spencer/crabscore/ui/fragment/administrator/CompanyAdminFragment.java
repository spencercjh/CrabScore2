package top.spencer.crabscore.ui.fragment.administrator;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import butterknife.BindView;
import com.alibaba.fastjson.JSONObject;
import top.spencer.crabscore.R;
import top.spencer.crabscore.base.BaseFragment;
import top.spencer.crabscore.common.CommonConstant;
import top.spencer.crabscore.common.util.SharedPreferencesUtil;
import top.spencer.crabscore.model.entity.Company;
import top.spencer.crabscore.presenter.AdministratorListPresenter;
import top.spencer.crabscore.presenter.CompanyAdminPresenter;
import top.spencer.crabscore.ui.activity.administrator.AdminCheckCompanyScoreActivity;
import top.spencer.crabscore.ui.adapter.CompanyAdminListAdapter;
import top.spencer.crabscore.ui.adapter.MyOnItemClickListener;
import top.spencer.crabscore.ui.view.CompanyAdminListView;
import top.spencer.crabscore.ui.widget.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 管理员用户组的参选单位管理页面
 *
 * @author spencercjh
 */
public class CompanyAdminFragment extends BaseFragment implements CompanyAdminListView, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.recycler_view_list)
    EmptyRecyclerView companyListView;
    @BindView(R.id.textview_empty)
    TextView emptyText;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    private AdministratorListPresenter administratorListPresenter;
    private CompanyAdminPresenter companyAdminPresenter;
    private String jwt;
    private String adminUsername;
    private CompanyAdminListAdapter companyAdminListAdapter;
    private List<Company> companyList = new ArrayList<>(pageSize);
    private int pageNum = 1;

    /**
     * 取得实例
     *
     * @param name 测试参数
     * @return fragment
     */
    public static CompanyAdminFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        CompanyAdminFragment fragment = new CompanyAdminFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * 重写onDestroyView 断开view
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        administratorListPresenter.detachView();
    }

    /**
     * 获得fragment的layout的Id
     *
     * @return layout Id
     */
    @Override
    public int getContentViewId() {
        return R.layout.fragment_list_has_empty_textview;
    }

    /**
     * 初始化视图
     *
     * @param view               view
     * @param savedInstanceState saveInstanceState
     */
    @SuppressWarnings("Duplicates")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        administratorListPresenter = new AdministratorListPresenter();
        administratorListPresenter.attachView(this);
        companyAdminPresenter = new CompanyAdminPresenter();
        companyAdminPresenter.attachView(this);
        SharedPreferencesUtil.getInstance(getContext(), "PROPERTY");
        adminUsername = (String) (SharedPreferencesUtil.getData("USERNAME", ""));
        jwt = (String) (SharedPreferencesUtil.getData("JWT", ""));
        setRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    @Override
    public void setRecycleView() {
        initAdminListAdapter();
        if (companyList.size() == 0) {
            companyListView.setEmptyView(emptyText);
        }
        companyListView.setAdapter(companyAdminListAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        companyListView.setLayoutManager(layoutManager);
        companyListView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()),
                DividerItemDecoration.VERTICAL));
        final int[] lastVisibleItemPosition = {0};
        companyListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItemPosition[0] + 1 == companyAdminListAdapter.getItemCount()) {
                    administratorListPresenter.getAllCompany(pageNum, pageSize, jwt);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItemPosition[0] = layoutManager.findLastVisibleItemPosition();
            }
        });
    }

    /**
     * 初始化列表adapter，设置单击监听
     */
    private void initAdminListAdapter() {
        companyAdminListAdapter = new CompanyAdminListAdapter(companyList, getContext());
        companyAdminListAdapter.setOnItemClickListener(new MyOnItemClickListener() {

            @Override
            public void onItemClick(View view) {
                final Company companyInPopupMenu = (Company) view.getTag();
                final PopupMenu popupMenu = new PopupMenu(getContext(), view);
                final MenuInflater inflater = popupMenu.getMenuInflater();
                inflater.inflate(R.menu.pop_menu_company_admin, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit_company_info: {
                                initEditCompanyInfoDialog(companyInPopupMenu);
                                popupMenu.dismiss();
                                break;
                            }
                            case R.id.menu_check_score: {
                                Intent intent = new Intent(getContext(), AdminCheckCompanyScoreActivity.class);
                                intent.putExtra("company", companyInPopupMenu);
                                startActivity(intent);
                                break;
                            }
                            case R.id.menu_delete_company: {
                                companyAdminPresenter.deleteCompany(companyInPopupMenu.getCompanyId(), jwt);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

            @Override
            public void onItemLongClick(View view) {

            }
        });
    }

    /**
     * 编辑参选单位信息AlertDialog
     *
     * @param companyInDialog 参选单位对象
     */
    private void initEditCompanyInfoDialog(final Company companyInDialog) {
        @SuppressLint("InflateParams")
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_admin_edit_company_info, null);
        final EditText companyName = dialogView.findViewById(R.id.edit_company_name);
        companyName.setText(companyInDialog.getCompanyName());
        AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        Window dialogWindow = dialog.getWindow();
        Objects.requireNonNull(dialogWindow).setGravity(Gravity.CENTER);
        dialogWindow.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        dialog.setIcon(R.drawable.app_logo);
        dialog.setTitle("修改参选单位信息");
        dialog.setView(dialogView);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "修改", (dialog1, which) -> {
            String companyNameString = companyName.getText().toString().trim();
            companyInDialog.setCompanyName(companyNameString);
            companyInDialog.setUpdateDate(new Date(System.currentTimeMillis()));
            companyInDialog.setUpdateUser(adminUsername);
            companyAdminPresenter.updateCompanyProperty(companyInDialog,
                    (String) SharedPreferencesUtil.getData("JWT", ""));

            dialog1.dismiss();
        });
        dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", (dialog12, which) -> dialog12.dismiss());
        dialog.show();
    }

    /**
     * 初始发起请求
     *
     * @param savedInstanceState savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        administratorListPresenter.getAllCompany(pageNum, pageSize, jwt);
    }

    /**
     * getAllCompany请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showData(JSONObject successData) {
        pageNum++;
        administratorListPresenter.dealCompanyListJSON(successData.getJSONArray("result"), companyList);
        new Handler(Looper.getMainLooper()).post(() -> {
            swipeRefreshLayout.setRefreshing(false);
            companyAdminListAdapter.notifyDataSetChanged();
        });
    }

    /**
     * swipeRefreshLayout刷新监听
     */
    @Override
    public void onRefresh() {
        administratorListPresenter.getAllCompany(pageNum, pageSize, jwt);
        new Handler(Looper.getMainLooper()).post(() -> swipeRefreshLayout.setRefreshing(false));
    }

    /**
     * updateCompanyProperty请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showUpdateCompanyPropertyResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
            resetList();
        }
    }

    /**
     * 更新过数据后重置列表
     */
    private void resetList() {
        companyList.clear();
        pageNum = 1;
        administratorListPresenter.getAllCompany(pageNum, pageSize, jwt);
    }

    /**
     * deleteCompany请求成功
     *
     * @param successData 成功数据源
     */
    @Override
    public void showDeleteCompanyResponse(JSONObject successData) {
        if (successData.getInteger(CommonConstant.CODE).equals(CommonConstant.SUCCESS)) {
            showToast(successData.getString(CommonConstant.MESSAGE));
            resetList();
        }
    }
}