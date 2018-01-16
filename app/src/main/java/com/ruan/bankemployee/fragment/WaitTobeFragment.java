package com.ruan.bankemployee.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.application.MyApplication;
import com.ruan.bankemployee.javabean.Bank;
import com.ruan.bankemployee.javabean.Queue;
import com.ruan.bankemployee.javabean.User;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
    /**
     * 当前排队的Objectid
     */
    private String presentObjectId;
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
        setBtnListener();
        //定时刷新，一分钟刷新
        handler.postDelayed(run,1000*60);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
                        getQueueSum();
                    }

                } else {
                    Log.e("WaitTobeFragment", e.getErrorCode() + e.getMessage());
                }
                progress.setVisibility(View.GONE);
            }
        });
    }
    /**
     * 定时查询当前排队人数 ，并更新界面
     * 5分钟更新一次
     */
    Handler handler = new Handler();
    private Runnable run = new Runnable() {
        @Override
        public void run() {
            this.update();
            handler.postDelayed(this,5000*60);
        }
        void update(){
            getQueueSum();
        }
    };

    /**
     *  获取当前排队人数和当前的排号码
     */

    private void getQueueSum(){
        BmobQuery<Queue> query = new BmobQuery<Queue>();
        Log.e("WaitTobeFragment", MyApplication.bankName);
        query.addWhereEqualTo("bankName", MyApplication.bankName);
        query.addWhereNotEqualTo("state",false);
        query.order("-createdAt");
        query.findObjects(new FindListener<Queue>() {
            @Override
            public void done(List<Queue> list, BmobException e) {
             if (e == null){
                 for (Queue queue:list){
                     Log.e("WaitTobeFragment", queue.getObjectId());
                 }
                 if (list.size() == 0){
                    num.setText("暂无");
                    num.setTextColor(getResources().getColor(R.color.red));
                    tvPresent.setText("暂无");
                    tvPresent.setTextColor(getResources().getColor(R.color.red));
                 }else {
                     num.setText(String.valueOf(list.size()));
                     int i = list.get(0).getLineCode();
                     presentObjectId = list.get(0).getObjectId();
                     if (i < 10){
                         tvPresent.setText("00"+String.valueOf(i));
                     }else if (i < 100){
                         tvPresent.setText("0"+String.valueOf(i));
                     }else {
                         tvPresent.setText(String.valueOf(i));
                     }
                 }
             }else {
                 Log.e("WaitTobeFragment", e.getMessage());
             }
            }
        });
    }
    private void setBtnListener(){
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress.setVisibility(View.VISIBLE);
                Queue queue1 = new Queue();
                queue1.setObjectId(presentObjectId);
                queue1.remove("user");
                queue1.setState(false);
                queue1.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null){
                            getQueueSum();
                        }else {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("QueueActivity", "e.getErrorCode():" + e.getErrorCode());
                        }
                        progress.setVisibility(View.GONE);
                    }
                });
            }
        });
    }
}
