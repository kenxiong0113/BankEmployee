package com.ruan.bankemployee.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.User;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.ErrorCode;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * @author by ruan 2018/1/6
 *         审核结果 活动界面
 */
public class AuditResultActivity extends BaseActivity {
    @BindView(R.id.r_layout)
    RelativeLayout rLayout;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.textView7)
    TextView textView7;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.textView9)
    TextView textView9;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.r_layout_1)
    RelativeLayout rLayout1;
    @BindView(R.id.r_layout_yes)
    RelativeLayout rLayoutYes;
    @BindView(R.id.r_layout_2)
    RelativeLayout rLayout2;
    @BindView(R.id.btn_ok2)
    Button btnOk2;
    @BindView(R.id.r_layout_no)
    RelativeLayout rLayoutNo;
    @BindView(R.id.progress)
    ProgressBar progress;
    private boolean isVisibility;
    private String name,sex,age,number,email,officePhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_audit_result;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        progress.setVisibility(View.VISIBLE);
        initToolbar();
        fetchUserInfo();

    }

    private void initToolbar() {
        setTitle("审核结果");
        setTopLeftButton(R.drawable.ic_return, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
    //
    private void initData() {
        User user = BmobUser.getCurrentUser(User.class);
        //判断银行职员身份是否审核通过
        if (isVisibility) {
            rLayoutYes.setVisibility(View.VISIBLE);
            rLayoutNo.setVisibility(View.GONE);
        } else {
            rLayoutYes.setVisibility(View.GONE);
            rLayoutNo.setVisibility(View.VISIBLE);
        }
        tvName.setText(user.getName());
        tvSex.setText(user.getSex());
        tvAge.setText(user.getAge());
        tvNumber.setText(user.getNumber());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getOfficePhone());

        progress.setVisibility(View.GONE);
    }

    @OnClick({R.id.btn_ok, R.id.btn_ok2})
    public void onClickBtn(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                finish();
                break;
            case R.id.btn_ok2:
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     * Synchronize local caching
     *
     * @see ErrorCode#E9024S
     */
    public void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {

                    try {
                        JSONObject object = new JSONObject(s);
                        isVisibility = object.getBoolean("identityVerified");
                        initData();
                        Log.e("AuditResultActivity", String.valueOf(isVisibility));
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    Log.e("1111", "Newest UserInfo is " + s);
                } else {
                    Log.e("WelcomeActivity", "e.getErrorCode():" + e.getErrorCode());
                }
            }
        });
    }
}
