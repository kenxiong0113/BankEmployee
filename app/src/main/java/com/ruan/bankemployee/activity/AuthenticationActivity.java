package com.ruan.bankemployee.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author by ruan 2018/1/5
 */
public class AuthenticationActivity extends BaseActivity {
    protected String bank, name, sex, age, number, officePhone, email;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_time)
    TextView tvSex;
    @BindView(R.id.et_sex)
    EditText etSex;
    @BindView(R.id.tv_age)
    TextView tvAge;
    @BindView(R.id.et_age)
    EditText etAge;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    @BindView(R.id.et_number)
    EditText etNumber;
    @BindView(R.id.checkBox)
    CheckBox checkBox;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    Context context;
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rl_info)
    RelativeLayout rlInfo;
    private Message message;
    private static final String man = "男";
    private static final String woman = "女";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        context = getApplicationContext();
        message = new Message();
        setTitle("认证银行职员身份");
        initData();
        setTopLeftButton(R.drawable.ic_return, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private void initData() {
        bank = getIntent().getStringExtra("bank");
        tvBank.setText(bank);
    }

    private void getEditViewData() {
        name = etName.getText().toString();
        sex = etSex.getText().toString();
        age = etAge.getText().toString();
        number = etNumber.getText().toString();
        email = etEmail.getText().toString();
        officePhone = etPhone.getText().toString();
    }

    @OnClick({R.id.btn_commit})
    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btn_commit:
                getEditViewData();
                if (checkBox.isChecked()) {
                    if ("".equals(name) || "".equals(sex) || "".equals(age)
                            || "".equals(number) || "".equals(officePhone) || "".equals(email)) {
                        Toast.makeText(this, "以上内容不能为空", Toast.LENGTH_SHORT).show();
                    } else if (!man.equals(sex) && !woman.equals(sex)) {
                        Toast.makeText(context, "性别填写 ‘男’或‘女’", Toast.LENGTH_SHORT).show();
                    } else {
                        User newUser = new User();
                        progress.setVisibility(View.VISIBLE);
                        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                        newUser.setEmail(email);
                        newUser.setName(name);
                        newUser.setSex(sex);
                        newUser.setAge(age);
                        newUser.setNumber(number);
                        newUser.setOfficePhone(officePhone);
                        newUser.setBankName(bank);
                        newUser.setCommit(true);
                        newUser.setPendingTrial(false);
                        newUser.setEmailVerified(false);
                        newUser.setIdentityVerified(false);
                        newUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(AuthenticationActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    rlInfo.setVisibility(View.VISIBLE);
                                    btnCommit.setEnabled(false);
                                } else {
                                    Log.e("AuthenticationActivity", "创建数据失败：" + e.getMessage());

                                }
                                message.what = 0x0001;
                                handler.sendMessage(message);
                            }
                        });
                    }
                } else {
                    Toast.makeText(this, "请勾选确认信息", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    progress.setVisibility(View.GONE);
                    BmobUser.requestEmailVerify(email, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(AuthenticationActivity.this,
                                        "请求验证邮件成功，请到" + email + "邮箱中进行激活。", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("AuthenticationActivity", "失败:" + e.getMessage() + e.getErrorCode());
                            }
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };
}
