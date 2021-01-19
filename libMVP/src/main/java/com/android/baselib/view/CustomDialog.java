package com.android.baselib.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.android.baselib.R;

public class CustomDialog extends DialogFragment {
    /**
     * 允许的最大
     */
    private static final int EXCEPT_WIDTH = 700;
    /**
     * 期待的手机竖屏宽度比
     */
    private static final float EXCEPT_WIDTH_RATIO = 0.85f;
    private static int MAX_WIDTH = 0;
    static AlertDialog alertDialog;
    boolean isChooser;
    String mTitle, mMessage, positiveText, negativeText;
    View.OnClickListener positiveListener, negativeListener;

    public void setChooser(String title, String message,
                           String positiveText, View.OnClickListener positiveListener,
                           String negativeText, View.OnClickListener negativeListener) {
        mTitle = title;
        mMessage = message;
        this.positiveText = positiveText;
        this.positiveListener = positiveListener;
        this.negativeText = negativeText;
        this.negativeListener = negativeListener;
        isChooser = true;
    }

    public void setAffirm(String title, String message,
                          String positiveText, View.OnClickListener positiveListener) {
        mTitle = title;
        mMessage = message;
        this.positiveText = positiveText;
        this.positiveListener = positiveListener;
        isChooser = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.part_custom_dialog, null, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.tvTitle)).setText(mTitle);
        ((TextView) view.findViewById(R.id.tvContent)).setText(mMessage);
        (view.findViewById(R.id.btOK)).setVisibility(View.GONE);

        Button btOk = view.findViewById(R.id.bt_OK);
        btOk.setText(positiveText);
        btOk.setOnClickListener(v -> {
            if (positiveListener != null) {
                positiveListener.onClick(v);
            }
            dismiss();
        });
        Button btCancel = view.findViewById(R.id.bt_Cancel);
        btCancel.setText(negativeText);
        btCancel.setOnClickListener(v -> {
            if (negativeListener != null) {
                negativeListener.onClick(v);
            }
            dismiss();
        });

        setCancelable(false);

        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);//activity.getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = getDialog().getWindow().getAttributes();
        int expectWidth = (int) (display.getWidth() * EXCEPT_WIDTH_RATIO);
        if (expectWidth > MAX_WIDTH) {
            expectWidth = MAX_WIDTH;
        }
        lp.width = expectWidth; //设置宽度
        alertDialog.getWindow().setAttributes(lp);
    }

    private View getView(String title, String message,
                         String positiveText, View.OnClickListener positiveListener,
                         String negativeText, View.OnClickListener negativeListener,
                         boolean isChooser) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.part_custom_dialog, null, false);
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
}
