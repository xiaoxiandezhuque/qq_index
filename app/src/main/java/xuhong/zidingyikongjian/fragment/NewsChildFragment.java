package xuhong.zidingyikongjian.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import xuhong.zidingyikongjian.adapter.DividerItemDecoration;
import xuhong.zidingyikongjian.activity.MainActivity;
import xuhong.zidingyikongjian.R;
import xuhong.zidingyikongjian.view.DeletedRecyclerview;
import xuhong.zidingyikongjian.adapter.MyAdapter;
import xuhong.zidingyikongjian.view.RefreshView;

/**
 * Created by BHKJ on 2016/6/12.
 */

//主界面最下层的fragment
public class NewsChildFragment extends Fragment {


    @BindView(R.id.refresh_news)
    RefreshView mRefreshView;

    private DeletedRecyclerview mRecyclerView;
    private List<String> mData;
    private MyAdapter mAdapter;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_child, null);
        ButterKnife.bind(this, view);

        mRecyclerView = mRefreshView.getRecycleView();
        mData = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            mData.add("第" + i + "条数据");
        }
        mAdapter = new MyAdapter(getActivity(), mData);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
        float  density =getActivity().getResources().getDisplayMetrics().density;
        mRecyclerView.setRightWidth((int)(100*density));
        mRecyclerView.setLineItem((int)(4*density));
        //点击事件
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void click(View v, int position) {
                mRecyclerView.hideRightView();
                Toast.makeText(getActivity(), mData.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void deletedClick(View v, int position) {
                Toast.makeText(getActivity(), mData.get(position) + "删除", Toast.LENGTH_SHORT).show();
            }
        });

        //当我们的滑动删除的时候 需要通知上层不要拦截我们的事件
        mRecyclerView.setIsInterceptTouchEventListener(new DeletedRecyclerview.IsInterceptTouchEventListener() {
            @Override
            public void doSomeThing(boolean isInterceptTouchEvent) {
                if (isInterceptTouchEvent) {
                    mRefreshView.setOpenEvent(false);
                    mainActivity.getQQViewGroup().setOpenEvent(false);
                } else {
                    mRefreshView.setOpenEvent(true);
                    mainActivity.getQQViewGroup().setOpenEvent(true);
                }

            }
        });

        //下拉刷新
        mRefreshView.setOnRefreshListener(new RefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(0,3000);
            }
        });
        //当我们的下拉刷新的时候 需要通知上层不要拦截我们的事件
        mRefreshView.setIsInterceptTouchEventListener(new RefreshView.IsInterceptTouchEventListener() {
            @Override
            public void doSomeThing(boolean isInterceptTouchEvent) {
                if (isInterceptTouchEvent) {
                    mainActivity.getQQViewGroup().setOpenEvent(false);
                } else {
                    mainActivity.getQQViewGroup().setOpenEvent(true);
                }

            }
        });

        return view;
    }

    Handler  mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mRefreshView.onRefreshComplete();
        }
    };


    //  获得  mainactivity的引用
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mainActivity = (MainActivity) context;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
