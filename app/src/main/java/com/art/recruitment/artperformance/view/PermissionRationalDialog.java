package com.art.recruitment.artperformance.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.art.recruitment.common.R;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

import static android.text.TextUtils.join;

/**
 * @company: Coolbit
 * created by {Android-Dev01} on 2018/4/8 0008 下午 6:26
 * @desc: AndPermission申请权限的rational dailog
 */

public class PermissionRationalDialog implements Rationale {

    @Override
    public void showRationale(Context context, List<String> permissions, final RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = "允许以下权限以便程序继续执行：" + TextUtils.join("\n", permissionNames);

        AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(context);
        mDialogBuilder.
                setCancelable(false).
                setTitle("提示").
                setMessage(message).
                setPositiveButton("继续", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.execute();
                    }
                }).
                setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        executor.cancel();
                    }
                }).
                show();

    }
}
