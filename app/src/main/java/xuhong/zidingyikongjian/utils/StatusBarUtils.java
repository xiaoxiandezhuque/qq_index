package xuhong.zidingyikongjian.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * ״̬��������
 * <p/>
 * Created by Jaeger on 16/2/14.
 * StatusBarDemo
 */
public class StatusBarUtils {
    /**
     * ����״̬����ɫ
     *
     * @param activity ��Ҫ���õ�activity
     * @param color    ״̬����ɫֵ
     */
    public static void setColor(Activity activity, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ����״̬��͸��
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // ����һ��״̬����С�ľ���
            View statusView = createStatusBarView(activity, color);
            // ��� statusView ��������
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(statusView);
            // ���ø����ֵĲ���
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    /**
     * ʹ״̬��͸��
     * <p/>
     * ������ͼƬ��Ϊ�����Ľ���,��ʱ��ҪͼƬ��䵽״̬��
     *
     * @param activity ��Ҫ���õ�activity
     */
    public static void setTranslucent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ����״̬��͸��
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // ���ø����ֵĲ���
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
//            rootView.setFitsSystemWindows(true);
//            rootView.setClipToPadding(true);
        }
    }

    /**
     * ΪDrawerLayout ��������״̬����ɫ
     *
     * @param activity     ��Ҫ���õ�activity
     * @param drawerLayout DrawerLayout
     * @param color        ״̬����ɫֵ
     */
    public static void setColorForDrawerLayout(Activity activity, DrawerLayout drawerLayout, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // ����һ��״̬����С�ľ���
            View statusBarView = createStatusBarView(activity, color);
            // ��� statusBarView ��������
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.addView(statusBarView, 0);
            // ���ݲ��ֲ��� LinearLayout ʱ,����padding top
            if (!(contentLayout instanceof LinearLayout) && contentLayout.getChildAt(1) != null) {
                contentLayout.getChildAt(1).setPadding(0, getStatusBarHeight(activity), 0, 0);
            }
            // ��������
            ViewGroup drawer = (ViewGroup) drawerLayout.getChildAt(1);
            drawerLayout.setFitsSystemWindows(false);
            contentLayout.setFitsSystemWindows(false);
            contentLayout.setClipToPadding(true);
            drawer.setFitsSystemWindows(false);
        }
    }

    /**
     * Ϊ DrawerLayout ��������״̬��͸��
     *
     * @param activity     ��Ҫ���õ�activity
     * @param drawerLayout DrawerLayout
     */
    public static void setTranslucentForDrawerLayout(Activity activity, DrawerLayout drawerLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // ����״̬��͸��
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // �������ݲ�������
            ViewGroup contentLayout = (ViewGroup) drawerLayout.getChildAt(0);
            contentLayout.setFitsSystemWindows(true);
            contentLayout.setClipToPadding(true);
            // ���ó��벼������
            ViewGroup vg = (ViewGroup) drawerLayout.getChildAt(1);
            vg.setFitsSystemWindows(false);
            // ���� DrawerLayout ����
            drawerLayout.setFitsSystemWindows(false);
        }
    }

    /**
     * ����һ����״̬����С��ͬ�ľ�����
     *
     * @param activity ��Ҫ���õ�activity
     * @param color    ״̬����ɫֵ
     * @return ״̬��������
     */
    private static View createStatusBarView(Activity activity, int color) {
        // ����һ����״̬��һ���ߵľ���
        View statusBarView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                getStatusBarHeight(activity));
        statusBarView.setLayoutParams(params);
        statusBarView.setBackgroundColor(color);
        return statusBarView;
    }

    /**
     * ��ȡ״̬���߶�
     *
     * @param context context
     * @return ״̬���߶�
     */
    public  static int getStatusBarHeight(Context context) {
        // ���״̬���߶�
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }
}
