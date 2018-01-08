package com.ruan.bankemployee.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.activity.BankLocationActivity;
import com.ruan.bankemployee.activity.LoginActivity;

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
    @BindView(R.id.button)
    Button button;
    Unbinder unbinder;
    @BindView(R.id.btn_authentication)
    Button btnAuthentication;

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

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.button,R.id.btn_authentication})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_authentication:
                Intent intent = new Intent(getActivity(), BankLocationActivity.class);
                startActivity(intent);
                break;
            case R.id.button:
                //清除缓存用户对象
                BmobUser.logOut();
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
            default:
                break;

        }
    }
}
