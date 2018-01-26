package com.ruan.bankemployee.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.activity.BankLocationActivity;
import com.ruan.bankemployee.activity.LoginActivity;
import com.ruan.bankemployee.adapter.MySelectAdapter;
import com.ruan.bankemployee.javabean.MySelect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

/**
 * @author by ruan
 */

public class MyFragment extends Fragment {

    View view;
    static String ARG = "arg";
    Unbinder unbinder;
    List<MySelect> selects = new  ArrayList<MySelect>();
    public MyFragment() {
        super();
    }

    public static MyFragment newInstance(String param) {
        MyFragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(ARG, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        initSelect();
        setRecyclerView();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initSelect(){
        String[] s = new String[]{"认证身份","用户资料","退出登录"};
        for (int i = 0; i < 3;i++){
            MySelect select = new MySelect(s[i]);
            selects.add(select);
        }
    }

    private void setRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.rey_view);
        LinearLayoutManager manager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(manager);
        MySelectAdapter adapter = new MySelectAdapter(selects,getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
