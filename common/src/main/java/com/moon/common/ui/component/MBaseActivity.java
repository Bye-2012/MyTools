package com.moon.common.ui.component;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Date: 9/29/21 4:33 PM
 * Author: Moon
 * Desc: BaseActivity类
 */
public class MBaseActivity extends AppCompatActivity implements MBaseActionInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showToast(String message) {
        if (TextUtils.isEmpty(message)) return;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
