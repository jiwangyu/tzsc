package com.jwy.bjtumidas.customize;

import android.content.Context;
import android.widget.Toast;

/**
 * 弹出Toast工具类
 */
public class ToastUtils {
    public static void showInfo(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
