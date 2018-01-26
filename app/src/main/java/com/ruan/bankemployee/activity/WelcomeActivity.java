package com.ruan.bankemployee.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.WelcomePicture;
import com.ruan.bankemployee.other.BaseConstants;
import com.ruan.bankemployee.other.ExitPressed;
import com.ruan.bankemployee.other.GetPicThread;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;

/**
 * @author by ruan 2017-12-1
 */

public class WelcomeActivity extends AppCompatActivity {
    @BindView(R.id.btn_jump)
    Button btnJump;
    Context context;
    BmobUser bmobUser;
    @BindView(R.id.img_picture)
    ImageView imgPicture;
    @BindView(R.id.progress)
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        Bmob.initialize(this, "1a64430728c9d575b7eb3117f2cf7e63");
        context = getApplicationContext();
        bmobUser = BmobUser.getCurrentUser();
        progress.setVisibility(View.VISIBLE);
        checkTheUserCache();

    }
    /**
     * 检查用户缓存
     */
    private void checkTheUserCache() {
        if (bmobUser != null) {
            // 允许用户使用应用
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            //缓存用户对象为空时， 可打开用户登录界面…
            Intent intent = new Intent(getApplication(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /**
     * 按两次返回退出程序
     */
    @Override
    public void onBackPressed() {
        ExitPressed pressed = new ExitPressed();
        boolean isPressed = pressed.exitPressed(context);
        if (isPressed) {
            finish();
        }
    }
}
