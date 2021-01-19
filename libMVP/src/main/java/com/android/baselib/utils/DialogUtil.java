package com.android.baselib.utils;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.android.baselib.R;
import com.android.libview.utils.DisplayUtil;

import java.util.Date;

/**
 * 统一api 方便后期统一修改样式
 */
public class DialogUtil {
    Context mContext;

    public DialogUtil(Context context) {
        mContext = context;
    }

    /**
     * 允许的最大
     */
    private static final int EXCEPT_WIDTH = 700;
    /**
     * 期待的手机竖屏宽度比
     */
    private static final float EXCEPT_WIDTH_RATIO = 0.85f;
    private static int MAX_WIDTH = 0;
    AlertDialog alertDialog;

    /******************选择框***********************/
    public AlertDialog showChooser(String title, String message,
                                   View.OnClickListener positiveListener, View.OnClickListener negativeListener) {
        return showChooser(title, message, "确定", positiveListener, "返回", negativeListener);
    }

    public AlertDialog showChooser(String title, String message,
                                   String positiveText, View.OnClickListener positiveListener,
                                   String negativeText, View.OnClickListener negativeListener) {
        DialogFragment dialogFragment = new DialogFragment();
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            View view = getView(title, message,
                    positiveText, positiveListener,
                    negativeText, negativeListener, true);

            builder.setView(view);
            builder.setCancelable(false);
            alertDialog = builder.show();

            setLayoutParams();
            return alertDialog;
        } catch (RuntimeException e) {
            DebugLog.w("DialogUtil", "showChooser", e.getMessage());
            return null;
        }
    }

    /*******************对话框***********************/
    public AlertDialog showAffirm(String message,
                                  View.OnClickListener positiveListener) {
        return showAffirm(false, "温馨提示", message, positiveListener);
    }

    public AlertDialog showAffirm(boolean cancel, String message,
                                  View.OnClickListener positiveListener) {
        return showAffirm(cancel, "温馨提示", message, positiveListener);
    }

    public AlertDialog showAffirm(String title, String message,
                                  View.OnClickListener positiveListener) {
        return showAffirm(false, title, message, "确定", positiveListener);
    }

    public AlertDialog showAffirm(boolean cancel, String title, String message,
                                  View.OnClickListener positiveListener) {
        return showAffirm(cancel, title, message, "确定", positiveListener);
    }

    public AlertDialog showAffirm(boolean cancel, String title, String message,
                                  String buttonText, View.OnClickListener positiveListener) {
        DebugLog.w("DialogUtil", "显示时间", new Date(), mContext.toString());
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

            View view = getView(title, message,
                    buttonText, positiveListener,
                    null, null, false);

            builder.setView(view);
            builder.setCancelable(cancel);
            alertDialog = builder.show();

            setLayoutParams();
            return alertDialog;
        } catch (RuntimeException e) {
            DebugLog.w("DialogUtil", "showAffirm", e.getMessage());
            return null;
        }
    }

    private View getView(String title, String message,
                         String positiveText, View.OnClickListener positiveListener,
                         String negativeText, View.OnClickListener negativeListener,
                         boolean isChooser) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.part_custom_dialog, null, false);
        ((TextView) view.findViewById(R.id.tvTitle)).setText(title);
        ((TextView) view.findViewById(R.id.tvContent)).setText(message);

        Button btOk = null;
        if (isChooser) {
            btOk = view.findViewById(R.id.bt_OK);
        } else {
            btOk = view.findViewById(R.id.btOK);
        }
        btOk.setText(positiveText);
        btOk.setOnClickListener(v -> {
            if (positiveListener != null) {
                positiveListener.onClick(v);
            }
            try {
                alertDialog.dismiss();
            } catch (RuntimeException e) {

            }
        });

        if (isChooser) {
            (view.findViewById(R.id.btOK)).setVisibility(View.GONE);

            Button btCancel = view.findViewById(R.id.bt_Cancel);
            btCancel.setText(negativeText);
            btCancel.setOnClickListener(v -> {
                if (negativeListener != null) {
                    negativeListener.onClick(v);
                }
                try {
                    alertDialog.dismiss();
                } catch (RuntimeException e) {

                }
            });
        }
        return view;
    }

    private void setLayoutParams() {
        MAX_WIDTH = DisplayUtil.dpToPx(mContext, EXCEPT_WIDTH);
        WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);//activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
        int expectWidth = (int) (display.getWidth() * EXCEPT_WIDTH_RATIO);
        if (expectWidth > MAX_WIDTH) {
            expectWidth = MAX_WIDTH;
        }
        lp.width = expectWidth; //设置宽度
        alertDialog.getWindow().setAttributes(lp);
    }

//    public void dismissDialog() {
//        try {
//            alertDialog.dismiss();
//        } catch (RuntimeException e) {
//
//        }
//    }
}
