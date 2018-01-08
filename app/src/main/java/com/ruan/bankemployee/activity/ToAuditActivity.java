package com.ruan.bankemployee.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * @author by ken  2018/1/6
 *         待审核 活动界面
 */
public class ToAuditActivity extends BaseActivity {
    String name, phone, time;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.rl_1)
    RelativeLayout rl1;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_ok)
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_to_audit;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        setTitle("审核状态");
        initData();
    }

    private void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        name = user.getName();
        phone = user.getOfficePhone();
        time = user.getUpdatedAt();
        tvName.setText(name);
        tvPhone.setText(phone);
        tvTime.setText(time);
    }

    @OnClick ({R.id.btn_ok})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_ok:
                    finish();
                break;
            default:
                break;

        }
    }
}
