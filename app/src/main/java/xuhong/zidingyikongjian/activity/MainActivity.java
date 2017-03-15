package xuhong.zidingyikongjian.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import xuhong.zidingyikongjian.adapter.MenuAdapter;
import xuhong.zidingyikongjian.adapter.OnItemClickListener;
import xuhong.zidingyikongjian.R;
import xuhong.zidingyikongjian.fragment.BlankFragment;
import xuhong.zidingyikongjian.fragment.NewsFragment;
import xuhong.zidingyikongjian.view.ElasticRecyclerView;
import xuhong.zidingyikongjian.view.QQViewGroup;
import xuhong.zidingyikongjian.utils.StatusBarUtils;

public class MainActivity extends AppCompatActivity {


    //主界面
    @BindViews({R.id.button1, R.id.button2, R.id.button3})
    Button[] buttons;
    @BindView(R.id.relative_main)
    RelativeLayout relativeMain;
    @BindView(R.id.qq_viewgroup)
    QQViewGroup qqViewgroup;
    private NewsFragment newsFragment;
    private BlankFragment[] blankFragments;

    public QQViewGroup getQQViewGroup() {
        return this.qqViewgroup;
    }

    //  菜单栏
    private RelativeLayout mMenu;
    private ElasticRecyclerView mMenuRecyclerView;
    private MenuAdapter mMenuAdapter;
    private List<String> mMenuData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        // 使我们的布局可以升入到 状态栏
        StatusBarUtils.setTranslucent(this);

        mMenu = (RelativeLayout) findViewById(R.id.menu);
        mMenu.getLayoutParams().width = (int) (getResources().getDisplayMetrics().widthPixels * 0.75);
        mMenuRecyclerView = (ElasticRecyclerView) findViewById(R.id.recyclerview_menu);

        mMenuData.add("我的qq会员");
        mMenuData.add("qq钱包");
        mMenuData.add("个性装扮");
        mMenuData.add("我的收藏");
        mMenuData.add("我的相册");
        mMenuData.add("我的文件");
        mMenuData.add("我的名片夹");
        mMenuData.add("我的qq会员");
        mMenuData.add("qq钱包");
        mMenuData.add("个性装扮");
        mMenuData.add("我的收藏");
        mMenuData.add("我的相册");
        mMenuData.add("我的文件");
        mMenuData.add("我的名片夹");
//        mMenuData.add("");

        //菜单栏的一系列绑定事件
        mMenuRecyclerView.setHasFixedSize(true);
        mMenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mMenuRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMenuAdapter = new MenuAdapter(this, mMenuData);
        mMenuRecyclerView.setAdapter(mMenuAdapter);
        mMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void click(View view, int postion) {
                Intent intent = new Intent(MainActivity.this, BlankActivity.class);
                startActivity(intent);
            }
        });
        initMain();
    }

    // 主界面的一系列绑定事件
    private void initMain() {

        relativeMain.setPadding(0, StatusBarUtils.getStatusBarHeight(this), 0, 0);
        Log.e("aaa", StatusBarUtils.getStatusBarHeight(this) + "");
        newsFragment = new NewsFragment();
        blankFragments = new BlankFragment[]{
                new BlankFragment(),
                new BlankFragment(),
        };
        final FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.framelayout_main, newsFragment);
        transaction.add(R.id.framelayout_main, blankFragments[0]);
        transaction.add(R.id.framelayout_main, blankFragments[1]);
        transaction.show(newsFragment).hide(blankFragments[0]).hide(blankFragments[1]);
        transaction.commit();

        buttons[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(newsFragment).hide(blankFragments[0]).hide(blankFragments[1]).commit();
            }
        });
        buttons[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(blankFragments[0]).hide(newsFragment).hide(blankFragments[1]).commit();
            }
        });
        buttons[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.show(blankFragments[1]).hide(blankFragments[0]).hide(newsFragment).commit();

            }
        });


    }
}
