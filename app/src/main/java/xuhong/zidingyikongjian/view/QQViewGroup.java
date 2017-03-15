package xuhong.zidingyikongjian.view;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.view.menu.MenuView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by BHKJ on 2016/5/5.
 */
public class QQViewGroup extends FrameLayout {


    // 控制是否拦截点击事件
    private boolean isOpenEvent = true;
    //主要的2个view
    private View mMainView, mMenuView;
    //view的滑动处理
    private ViewDragHelper mViewDragHelper;
    //用到的view 宽高
    private int mMenuWidth, mMenuHeight, mMainWidth;
    //    mainview 距离屏幕左边的距离
    private int layoutToLeft;
    //    按下的位子
    private int downY, downX;
    //    侧滑栏是否打开
    private boolean isOpen;


    public QQViewGroup(Context context) {
        this(context, null);
    }

    public QQViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public QQViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mViewDragHelper = ViewDragHelper.create(this, callback);

    }

    public void setOpenEvent(boolean openEvent) {
        isOpenEvent = openEvent;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //ViewDragHelper 的固定写法
        boolean helper = mViewDragHelper.shouldInterceptTouchEvent(ev);
        boolean result = false;

        if (isOpenEvent) {
            switch (MotionEventCompat.getActionMasked(ev)) {
                case MotionEvent.ACTION_DOWN:
                    downX = (int) ev.getX();
                    downY = (int) ev.getY();
                    if (downX>=mMenuWidth){
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_MOVE:

                    int moveX = (int) ev.getX() - downX;
                    int moveY = (int) ev.getY() - downY;

                    //  如果横向的滑动方向正确 并且滑动距离超过8 则拦截
                    if (isOpen) {
                        if (moveX < -8 && Math.abs(moveX) > Math.abs(moveY)) {
                            result = true;
//                            通过这句话来通知ViewDragHelper 回调
                            mViewDragHelper.captureChildView(mMainView, ev.getPointerId(0));
                        }

                    } else {
                        if (moveX > 8 && Math.abs(moveX) > Math.abs(moveY)) {
                            result = true;
                            mViewDragHelper.captureChildView(mMainView, ev.getPointerId(0));
                        }
                    }
                    break;
            }
            return helper || result;
        }

        return false;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //ViewDragHelper 的固定写法
        mViewDragHelper.processTouchEvent(event);
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            //如果侧滑栏打开 并且点击的是主界面的位置 则关闭侧滑栏
            int move = Math.abs((int) event.getX() - downX);
            if (isOpen && downX > mMenuWidth && move < 8) {
                isOpen = false;
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                ViewCompat.postInvalidateOnAnimation(QQViewGroup.this);

            }
        }
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        //ViewDragHelper 的固定写法
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    //ViewDragHelper 的回调
    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {


        //        该方法是判断点击的哪个view会执行回调
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            //这里我们手动回调  使我们可以在点击侧滑栏的时候 回调
            mViewDragHelper.captureChildView(mMainView, pointerId);
            return mMainView == child;
        }

        //该方法是 检测横向滑动的距离
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            if (left >= mMenuWidth) {
                return mMenuWidth;

            } else if (left < mMenuWidth && left >= 0) {
                return left;
            } else {
                return 0;
            }

        }

        //改方法会在滑动完成（up）时调用
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

            //判断位置  是否打开侧滑栏
            if (isOpen) {
                if (mMainView.getLeft() < mMenuWidth * 2 / 3) {
                    isOpen = false;
                    mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(QQViewGroup.this);
                } else {
                    mViewDragHelper.smoothSlideViewTo(mMainView, mMenuWidth, 0);
                    ViewCompat.postInvalidateOnAnimation(QQViewGroup.this);
                }

            } else {
                if (mMainView.getLeft() < mMenuWidth / 3) {
                    mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                    ViewCompat.postInvalidateOnAnimation(QQViewGroup.this);
                } else {
                    isOpen = true;
                    mViewDragHelper.smoothSlideViewTo(mMainView, mMenuWidth, 0);
                    ViewCompat.postInvalidateOnAnimation(QQViewGroup.this);

                }
            }

        }

        //        该方法会在mainview的位置发生变化时调用
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            layoutToLeft = left;
            if (left == 0) {
                mMenuView.layout(-mMenuWidth / 2, 0, mMenuWidth / 2, mMenuHeight);
            }
            if (left > 0 && left < mMenuWidth) {
                mMenuView.layout(-mMenuWidth / 2 + left / 2, 0, mMenuWidth / 2 + left / 2, mMenuHeight);
            }
            if (left == mMenuWidth) {
                mMenuView.layout(0, 0, mMenuWidth, mMenuHeight);
            }
        }
    };


    //初始化view
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.e("onFinishInflate", "onFinishInflate");
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    //获得view的宽高
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.e("onSizeChanged", "onSizeChanged");
        DisplayMetrics metrics = getResources().getDisplayMetrics();
//        measureChild(mMenuView, (int) (metrics.widthPixels*0.75),heightMeasureSpec);
        mMenuWidth = (int) (metrics.widthPixels * 0.75);
        mMenuHeight = mMenuView.getMeasuredHeight();
        mMainWidth = mMainView.getMeasuredWidth();
    }

    //设置我们需要的布局
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e("onLayout", "onLayout");
        mMenuView.layout((layoutToLeft - mMenuWidth) / 2, 0, (layoutToLeft - mMenuWidth) / 2 + mMenuWidth, mMenuHeight);
        mMainView.layout(layoutToLeft, 0, layoutToLeft + mMainWidth, mMenuHeight);
    }
}
