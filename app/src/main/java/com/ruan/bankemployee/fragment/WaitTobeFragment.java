package com.ruan.bankemployee.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.Bank;
import com.ruan.bankemployee.javabean.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author by ruan
 *         待办界面，实时显示排队人数，当前排队客户的排队号
 */

public class WaitTobeFragment extends Fragment {

    View view;
    static String ARG = "arg";
    @BindView(R.id.bankName_windowsId)
    TextView bankNameWindowsId;
    @BindView(R.id.queue_num)
    TextView queueNum;
    @BindView(R.id.num)
    TextView num;
    @BindView(R.id.tv_personal)
    TextView tvPersonal;
    @BindView(R.id.r_layout)
    RelativeLayout rLayout;
    @BindView(R.id.present_num)
    TextView presentNum;
    @BindView(R.id.tv_present)
    TextView tvPresent;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.rl_wait_to_be)
    RelativeLayout rlWaitToBe;
    Unbinder unbinder;
    @BindView(R.id.r_layout_yes)
    RelativeLayout rLayoutYes;
    @BindView(R.id.r_layout_no)
    RelativeLayout rLayoutNo;
    @BindView(R.id.progress)
    ProgressBar progress;
    private String myObjectId;

    public WaitTobeFragment() {
        super();
    }

    public static WaitTobeFragment newInstance(String param) {
        WaitTobeFragment fragment = new WaitTobeFragment();
        Bundle args = new Bundle();
        args.putString(ARG, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wait_to_be, container, false);
        unbinder = ButterKnife.bind(this, view);
        getPresentWindowsId();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getPresentWindowsId() {
        progress.setVisibility(View.VISIBLE);
        User user = BmobUser.getCurrentUser(User.class);
        myObjectId = user.getObjectId();
        BmobQuery<Bank> query = new BmobQuery<Bank>();
        query.addWhereEqualTo("employeeObjectId", myObjectId);
        query.addWhereEqualTo("state", true);
        query.findObjects(new FindListener<Bank>() {
            @Override
            public void done(List<Bank> list, BmobException e) {
                if (e == null) {
                    if (list.size() == 0) {
                        rLayoutNo.setVisibility(View.VISIBLE);
                    } else {
                        rLayoutYes.setVisibility(View.VISIBLE);
                        bankNameWindowsId.setText(list.get(0).getBankName() + list.get(0).getWindows());
                    }

                } else {
                    Log.e("WaitTobeFragment", e.getErrorCode() + e.getMessage());
                }
                progress.setVisibility(View.GONE);
            }
        });
    }
}
