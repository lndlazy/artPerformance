package com.art.recruitment.common.base.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.widget.FrameLayout;

import com.art.recruitment.common.R;

public class BaseBottomSheetDialog extends BottomSheetDialog {
    public BaseBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    public BaseBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected BaseBottomSheetDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout bottomSheet = this.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        bottomSheet.setBackgroundResource(R.color.color_transparent);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
