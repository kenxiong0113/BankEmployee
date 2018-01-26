package com.ruan.bankemployee.adapter;

/**
 * @author by ruan on 2018/1/26.
 * 个人资料适配器
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.MySelect;
import com.ruan.bankemployee.javabean.User;
import com.ruan.bankemployee.javabean.UserInfo;

import java.util.List;



public class FixInfoAdapter extends RecyclerView.Adapter<FixInfoAdapter.ViewHolder>{
    private List<UserInfo> list;
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvInfo;
        public ViewHolder(View view){
            super(view);
            tvInfo = (TextView)view.findViewById(R.id.tv_info);

        }
    }

    public FixInfoAdapter(List<UserInfo> list){
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_info,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        UserInfo userInfo = list.get(position);
        holder.tvInfo.setText(userInfo.getUserInfo());
        holder.tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
