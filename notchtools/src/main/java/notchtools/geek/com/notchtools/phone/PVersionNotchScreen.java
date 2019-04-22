package notchtools.geek.com.notchtools.phone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.DisplayCutout;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import notchtools.geek.com.notchtools.core.AbsNotchScreenSupport;
import notchtools.geek.com.notchtools.core.OnNotchCallBack;
import notchtools.geek.com.notchtools.helper.NotchStatusBarUtils;

/**
 * targetApi>=28才能使用API，有的手机厂商在P上会放弃O适配方案，暂时针对P手机不做特殊处理
 * @author zhangzhun
 * @date 2018/11/5
 */

public class PVersionNotchScreen extends AbsNotchScreenSupport {

    @RequiresApi(api = 28)
    @Override
    public boolean isNotchScreen(Window window) {
        WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            return false;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if(displayCutout == null || displayCutout.getBoundingRects() == null){
            return false;
        }

        return true;
    }

    @RequiresApi(api = 28)
    @Override
    public int getNotchHeight(Window window) {
        int notchHeight = 0;
        WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Log.d("PVersionNotchScreen", "getNotchHeight windowInsets null ");
            return 0;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if(displayCutout == null || displayCutout.getBoundingRects() == null){
            Log.d("PVersionNotchScreen", "getNotchHeight displayCutout " + displayCutout);
            return 0;
        }

        notchHeight = displayCutout.getSafeInsetTop();
        displayCutout.getSafeInsetBottom();
        Log.d("PVersionNotchScreen", "getNotchHeight top " + displayCutout.getSafeInsetTop() + " bottom " + displayCutout.getSafeInsetBottom() + " left " + displayCutout.getSafeInsetLeft() + " right " + displayCutout.getSafeInsetRight());
        Log.d("PVersionNotchScreen", "getNotchHeight result " + notchHeight);

        return notchHeight;
    }

    @RequiresApi(api = 28)
    @Override
    public Rect getNotchRect(Window window) {
        Rect rect = new Rect(0, 0, 0, 0);
        WindowInsets windowInsets = window.getDecorView().getRootWindowInsets();
        if (windowInsets == null) {
            Log.d("PVersionNotchScreen", "getNotchRect windowInsets null ");
            return rect;
        }

        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        if(displayCutout == null || displayCutout.getBoundingRects() == null){
            Log.d("PVersionNotchScreen", "getNotchRect displayCutout " + displayCutout);
            return rect;
        }

        rect.left = displayCutout.getSafeInsetLeft();
        rect.right = displayCutout.getSafeInsetRight();
        rect.top = displayCutout.getSafeInsetTop();
        rect.bottom = displayCutout.getSafeInsetBottom();

        Log.d("PVersionNotchScreen", "getNotchRect top " + rect.top + " bottom " + rect.bottom + " left " + rect.left + " right " + rect.right);

        return  rect;
    }

    @RequiresApi(api = 28)
    @Override
    public void fullScreenDontUseStatus(Activity activity, OnNotchCallBack notchCallBack) {
        super.fullScreenDontUseStatus(activity, notchCallBack);
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        activity.getWindow().setAttributes(attributes);
        NotchStatusBarUtils.setFakeNotchView(activity.getWindow());
    }

    @RequiresApi(api = 28)
    @Override
    public void fullScreenUseStatus(Activity activity, OnNotchCallBack notchCallBack) {
        super.fullScreenUseStatus(activity, notchCallBack);
        WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
        attributes.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        activity.getWindow().setAttributes(attributes);
    }
}
