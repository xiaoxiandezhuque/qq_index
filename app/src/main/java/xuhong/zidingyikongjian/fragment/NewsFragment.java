package xuhong.zidingyikongjian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import xuhong.zidingyikongjian.R;

/**
 * Created by BHKJ on 2016/6/12.
 */
//  主界面中的fragment  下面还有一层fragment
public class NewsFragment extends Fragment {


    @BindView(R.id.btn_news)
    Button btnNews;
    @BindView(R.id.btn_phone)
    Button btnPhone;



    private NewsChildFragment newsChildFragment;
    private BlankFragment blankFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        ButterKnife.bind(this, view);
        newsChildFragment = new NewsChildFragment();
        blankFragment = new BlankFragment();

        final FragmentManager  manager = getActivity().getSupportFragmentManager();

        FragmentTransaction  transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout_news,newsChildFragment).add(R.id.framelayout_news,blankFragment).show(newsChildFragment)
                .hide(blankFragment).commit();

        btnNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction  transaction = manager.beginTransaction();
                transaction.show(newsChildFragment)
                        .hide(blankFragment).commit();
            }
        });
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction  transaction = manager.beginTransaction();
                transaction.show(blankFragment)
                        .hide(newsChildFragment).commit();
            }
        });

        return view;
    }
}
