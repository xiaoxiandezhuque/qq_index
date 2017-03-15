package xuhong.zidingyikongjian.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BHKJ on 2016/6/13.
 */
public class DeletedRecyclerview extends RecyclerView {


//    public static final int TYPE_RIGHT = 110;

    //右边的view 是否出现
    private boolean isShow;
    //是否是拖动  这个view
    private boolean isMoveRight;

    private LinearLayoutManager mLinearLayoutManager;
    //右边view的宽度
    private int mRightWidth = 300;
    //按下的位置
    private int downX, downY;
    //按下的是哪一个position
    private int downPosition;
    //分隔线的高度
    private int lineItem = 12;
    //按下的是哪一个view
    private View downView;
    //如果右边的view出现   按下的view和出现的view  position 是否一样
    private boolean isDownPosition = true;
    //用作松开手指的动画效果需要的变量
    private int handlerFrom;
    private int handlerTo;
    private boolean isAnimation;
    private View handlerView;
    private int handlerCount;

    //通知上层（qqviewgroup和refreshview）不要拦截事件
    private IsInterceptTouchEventListener isInterceptTouchEventListener;

    public void setIsInterceptTouchEventListener(IsInterceptTouchEventListener isInterceptTouchEventListener) {
        this.isInterceptTouchEventListener = isInterceptTouchEventListener;
    }

    public interface IsInterceptTouchEventListener {
        void doSomeThing(boolean isInterceptTouchEvent);
    }


    public DeletedRecyclerview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    public void setRightWidth(int rightWidth) {
        this.mRightWidth = rightWidth;
    }

    public void setLineItem(int lineItem) {
        this.lineItem = lineItem;
    }

    //隐藏右边的view
    public void hideRightView() {
        if (isInterceptTouchEventListener != null) {
            isInterceptTouchEventListener.doSomeThing(false);
        }
        isShow = false;
        hideRight();
    }

    // 显示右边的view
    private void showRightView() {
        isShow = true;
        showRight();
    }

    // 初始化按下的view
    private void initDown(int thisDownPosition) {
//        if (mAdapter ==null){
//            mAdapter = getAdapter();
//        }
//        if (mAdapter.getItemViewType(thisDownPosition)== TYPE_RIGHT){
//            isType = true;
        downPosition = thisDownPosition;
        downView = mLinearLayoutManager.findViewByPosition(downPosition);
//        }else {
//            isType = false;
//        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

        switch (e.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                // 首先取的按下的点，然后根据view的高度 取得按下的是哪一个位子，如果一开始右边的view出现，则判断点击的是不是出现view的位子
                //如果不是 则直接隐藏
                int thisDownPosition = 0;
                isMoveRight = false;
                downX = (int) (e.getX() + 0.5f);
                downY = (int) (e.getY() + 0.5f);
                if (mLinearLayoutManager == null) {
                    mLinearLayoutManager = (LinearLayoutManager) getLayoutManager();
                }
                int postion = mLinearLayoutManager.findFirstVisibleItemPosition();
                View view = mLinearLayoutManager.findViewByPosition(postion);
                if (view != null) {
                    int height = view.getHeight() + lineItem;
                    int buttom = view.getBottom() + lineItem;
                    if (downY <= view.getBottom()) {
                        thisDownPosition = postion;
                    } else {
                        int a = (downY - buttom) / height;
                        thisDownPosition = a + 1 + postion;
                    }

                    if (isShow) {
                        if (thisDownPosition != downPosition) {
                            isDownPosition = false;
                            hideRight();
                            return true;
                        } else {
                            initDown(thisDownPosition);
                            isDownPosition = true;
                        }
                    } else {
                        initDown(thisDownPosition);
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
//                if (isType){

                int moveX = (int) (e.getX() + 0.5f) - downX;
                int moveY = (int) (e.getY() + 0.5f) - downY;
                if (isShow) {
                    if (!isDownPosition) {
                        return true;
                    }
                    if (moveX > 8 && Math.abs(moveX) > Math.abs(moveY)) {
                        isMoveRight = true;
                        return true;
                    }
                } else {
                    if (moveX < -8 && Math.abs(moveX) > Math.abs(moveY)) {

                        if (isInterceptTouchEventListener != null) {
                            isInterceptTouchEventListener.doSomeThing(true);
                        }
                        isMoveRight = true;
                        return true;
                    }
                }
//                }

                break;
            case MotionEvent.ACTION_CANCEL:

                break;

        }

        return super.onInterceptTouchEvent(e);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {
//        if (isType){
        switch (e.getActionMasked()) {

            case MotionEvent.ACTION_MOVE:
                int moveX = (int) (e.getX() + 0.5f) - downX;
                int moveY = (int) (e.getY() + 0.5f) - downY;
                if (isShow) {
                    if (isDownPosition) {
                        if (isMoveRight) {
                            if (downView != null) {
                                if (moveX >= 0 && moveX <= mRightWidth) {
                                    downView.scrollTo(mRightWidth - moveX, 0);
                                }
                            }
                        } else {
                            if (moveX > 8 && Math.abs(moveX) > Math.abs(moveY)) {
                                isMoveRight = true;
                            }
                        }
                    }
                    return true;

                } else {
                    if (isMoveRight) {
                        if (downView != null) {
                            if (moveX <= 0 && moveX >= -mRightWidth) {
                                downView.scrollTo(-moveX, 0);
                            }
                        }
                        return true;
                    } else {
                        if (moveX < -8 && Math.abs(moveX) > Math.abs(moveY)) {
                            isMoveRight = true;
                            return true;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                int moveXUP = (int) (e.getX() + 0.5f) - downX;
                if (isShow) {
                    if (isDownPosition) {
                        if (isMoveRight) {
                            if (moveXUP >= mRightWidth / 2) {
                                hideRightView();
                            } else {
                                showRightView();
                            }
                            return true;
                        } else {
                            hideRightView();
                        }

                    } else {
                        if (isInterceptTouchEventListener != null) {
                            isInterceptTouchEventListener.doSomeThing(false);
                        }
                        isShow = false;
                        return true;
                    }
                } else {
                    if (isMoveRight) {
                        if (-moveXUP >= mRightWidth / 2) {
                            showRightView();
                        } else {
                            hideRightView();
                        }
                        return true;
                    }
                }

                break;
        }

//        }

        return super.onTouchEvent(e);
    }


    private void hideRight() {
        Message message = Message.obtain();
        message.what = 1;
        message.obj = downView;
        message.arg1 = downView.getScrollX();
        message.arg2 = 0;
        moveHandler.sendMessage(message);
    }

    private void showRight() {

        Message message = Message.obtain();
        message.what = 1;
        message.obj = downView;
        message.arg1 = downView.getScrollX();
        message.arg2 = mRightWidth;
        moveHandler.sendMessage(message);
    }

    //  用作手指离开时的动画效果
    private Handler moveHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (isAnimation) {
                        Message message = Message.obtain();
                        message.what = msg.what;
                        message.obj = msg.obj;
                        message.arg1 = msg.arg1;
                        message.arg2 = msg.arg2;

                        this.sendMessageDelayed(message, mRightWidth / 20);
                        return;
                    }
                    isAnimation = true;
                    handlerView = (View) msg.obj;
                    handlerFrom = msg.arg1;
                    handlerTo = msg.arg2;
                    handlerCount = (handlerTo - handlerFrom) / 10;
                    if (handlerCount == 0) {
                        isAnimation = false;
                        handlerView.scrollTo(handlerTo, 0);
                    } else {
                        this.sendEmptyMessageDelayed(0, 10);
                    }
                    break;
                default:
                    if (handlerCount > 0) {
                        handlerCount--;
                        handlerFrom += 10;
                        handlerView.scrollTo(handlerFrom, 0);
                        this.sendEmptyMessageDelayed(0, 10);
                    } else if (handlerCount < 0) {
                        handlerCount++;
                        handlerFrom -= 10;
                        handlerView.scrollTo(handlerFrom, 0);
                        this.sendEmptyMessageDelayed(0, 10);
                    } else {
                        isAnimation = false;
                        handlerView.scrollTo(handlerTo, 0);
                    }

            }

        }
    };

}
