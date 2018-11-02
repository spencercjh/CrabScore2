package top.spencer.crabscore.fragment.rank;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import top.spencer.crabscore.R;

import java.util.Objects;

/**
 * @author spencercjh
 */
public class FatnessRankFragment extends Fragment {
    private View view;

    public static FatnessRankFragment newInstance(String name) {
        Bundle args = new Bundle();
        args.putString("name", name);
        FatnessRankFragment fragment = new FatnessRankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sub_fragment_test, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView tv = view.findViewById(R.id.fragment_test_tv);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = Objects.requireNonNull(bundle.get("name")).toString();
            tv.setText(name);
        }
    }
}