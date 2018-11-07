package top.spencer.crabscore.view.fragment.administrator;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.*;
import top.spencer.crabscore.R;
import top.spencer.crabscore.model.entity.User;

import java.util.Objects;

/**
 * @author spencercjh
 */
public class AdminEditUserInfoDialogFragment extends DialogFragment {
    private Unbinder unbinder;
    @BindView(R.id.edit_username)
    EditText username;
    @BindView(R.id.edit_display_name)
    EditText displayName;
    @BindView(R.id.edit_phone)
    EditText phone;
    @BindView(R.id.spinner_role)
    Spinner roleSpinner;
    @BindArray(R.array.roles)
    String[] roles;
    private User user;

    /**
     * 取得实例
     *
     * @param user 用户对象
     * @return dialogFragment
     */
    public static AdminEditUserInfoDialogFragment newInstance(User user) {
        Bundle args = new Bundle();
        args.putSerializable("USER", user);
        AdminEditUserInfoDialogFragment dialogFragment = new AdminEditUserInfoDialogFragment();
        dialogFragment.setArguments(args);
        return dialogFragment;
    }

    /**
     * 创建视图
     *
     * @param inflater           inflater
     * @param container          container
     * @param savedInstanceState savedInstanceState
     * @return view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        //设置背景透明
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        unbinder = ButterKnife.bind(this, Objects.requireNonNull(view));
        return view;
    }

    /**
     * 创建dialog
     *
     * @param savedInstanceState savedInstanceState
     * @return dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_admin_edit_user_info, null);
        builder.setView(view);
        initSpinner();
        Bundle bundle = getArguments();
        user = (User) bundle.get("USER");
        if (user != null) {
            username.setText(user.getUserName().trim());
            displayName.setText(user.getDisplayName().trim());
            phone.setText(user.getEmail());
            roleSpinner.setSelection(user.getRoleId());
        }
        return builder.create();
    }

    /**
     * 重写onDestroyView 断开binder
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, roles);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roleSpinner.setAdapter(adapter);
        roleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                user.setRoleId(pos);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}