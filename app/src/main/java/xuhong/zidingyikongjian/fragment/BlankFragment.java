package xuhong.zidingyikongjian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xuhong.zidingyikongjian.R;

/**
 * Created by BHKJ on 2016/6/12.
 */

// 只为展示  没卵用
public class BlankFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.activity_blank,null);

        return view;
    }
}
