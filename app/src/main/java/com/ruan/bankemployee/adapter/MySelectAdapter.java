package com.ruan.bankemployee.adapter;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.activity.BankLocationActivity;
import com.ruan.bankemployee.activity.FixInfoActivity;
import com.ruan.bankemployee.activity.LoginActivity;
import com.ruan.bankemployee.javabean.MySelect;
import java.util.List;
import cn.bmob.v3.BmobUser;

/**
 * @author by ruan on 2018/1/26.
 * 我的界面，选项适配器
 */

public class MySelectAdapter extends RecyclerView.Adapter<MySelectAdapter.ViewHolder>{
    private List<MySelect> list;
    private Activity  activity;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvSelect;
        public ViewHolder(View view){
            super(view);
            tvSelect = (TextView)view.findViewById(R.id.select);

        }
    }

    public MySelectAdapter(List<MySelect> list,Activity activity){
        this.list = list;
        this.activity = activity;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        MySelect mySelect = list.get(position);
        holder.tvSelect.setText(mySelect.getSelect());
        holder.tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position == 0){
                    // 认证身份
                    activity.startActivity(new Intent(activity, BankLocationActivity.class));
                }else if (position == 1){
                    //修改认证信息
                    activity.startActivity(new Intent(activity, FixInfoActivity.class));
                }else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                    dialog.setTitle("确认退出账号！");
                    dialog.setIcon(R.drawable.ic_x);
                    dialog.setCancelable(true);
                    dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //清除缓存用户对象
                            BmobUser.logOut();
                            activity.startActivity(new Intent(activity, LoginActivity.class));
                            activity.finish();
                        }
                    });
                    dialog.setNegativeButton("取消",null);
                    dialog.show();

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
