package xuhong.zidingyikongjian.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by BHKJ on 2016/6/8.
 */

public class ElasticRecyclerView extends RecyclerView {
    public ElasticRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    //本来以为在RecyclerView中设置这个就可以实现跟listview中一样 有弹性效果的    结果没用
    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, 200, isTouchEvent);
    }
}
