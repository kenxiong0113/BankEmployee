package com.ruan.bankemployee.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.adapter.FixInfoAdapter;
import com.ruan.bankemployee.javabean.User;
import com.ruan.bankemployee.javabean.UserInfo;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by ruan 2018/1/26
 * 修改用户资料
 */
public class FixInfoActivity extends BaseActivity {
    private List<UserInfo> list = new ArrayList<UserInfo>();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_fix_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        user = User.getCurrentUser(User.class);
        initToolbar();
        initData();
        setRecyclerView();

    }

    private void initToolbar(){
        if (user.getBankName() == null){
            setTitle("请认证银行职员信息");
        }else {
            setTitle(user.getBankName());
        }
        setTopLeftButton(R.drawable.ic_return, new OnClickListener() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
    private void initData(){
        list.add(new UserInfo("用户名"+"\t\t\t\t\t\t\t"+user.getUsername()));
        list.add(new UserInfo("姓名"+"\t\t\t\t\t\t\t\t\t"+user.getName()));
        list.add(new UserInfo("年龄"+"\t\t\t\t\t\t\t\t\t"+user.getAge()));
        list.add(new UserInfo("性别"+"\t\t\t\t\t\t\t\t\t"+user.getSex()));
        list.add(new UserInfo("电子邮箱"+"\t\t\t\t\t"+user.getEmail()));
        list.add(new UserInfo("单位电话"+"\t\t\t\t\t"+user.getOfficePhone()));
        list.add(new UserInfo("工号"+"\t\t\t\t\t\t\t\t\t"+user.getNumber()));
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.fix_rey_view);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        FixInfoAdapter adapter = new FixInfoAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
