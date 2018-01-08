package com.ruan.bankemployee.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.adapter.BankWindowsAdapter;
import com.ruan.bankemployee.application.MyApplication;
import com.ruan.bankemployee.javabean.Bank;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.ErrorCode;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author by ruan
 *         业务界面
 */

public class BusinessFragment extends Fragment {
    View view;
    static String ARG = "arg";
    private static String identityVerified = "identityVerified";
    @BindView(R.id.progress)
    ProgressBar progress;
    @BindView(R.id.r_layout_no)
    RelativeLayout rLayoutNo;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.gv_windows)
    GridView gvWindows;
    @BindView(R.id.r_layout_yes)
    RelativeLayout rLayoutYes;
    private Context context;
    Unbinder unbinder;
    String bankName;
    int sum = 0;
    private static final int WINDOWS_SUM = 9;
    private Message message = new Message();
    private List<Bank>windows;
    public BusinessFragment() {
        super();
    }

    public static BusinessFragment newInstance(String param) {
        BusinessFragment fragment = new BusinessFragment();
        Bundle args = new Bundle();
        args.putString(ARG, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_business, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        progress.setVisibility(View.VISIBLE);
        fetchUserInfo();
        return view;
    }

    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     * Synchronize local caching
     *
     * @see ErrorCode#E9024S
     */
    public  void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    try {
                        JSONObject object = new JSONObject(s);
                        bankName = object.getString("bankName");
                        if (object.getBoolean(identityVerified)) {
                            rLayoutYes.setVisibility(View.VISIBLE);
                            tvBank.setText(bankName);
                            checkTheNumberOfWindowsInTheCurrentBank();
                        } else {
                            rLayoutNo.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }finally {
                        progress.setVisibility(View.GONE);
                    }
                } else {
                    Log.e("WelcomeActivity", "e.getErrorCode():" + e.getErrorCode());
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MyApplication.eixt();
    }

    private void setGridViewListener(){
        gvWindows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击了第" + (position+1) + "号窗口", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询当前银行的窗口数量
     */
    private void checkTheNumberOfWindowsInTheCurrentBank(){
        BmobQuery<Bank> query = new BmobQuery<Bank>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("bankName", bankName);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(50);
        //排序查询
        query.order("-score,windows");
        //执行查询方法
        query.findObjects(new FindListener<Bank>() {
            @Override
            public void done(List<Bank> object, BmobException e) {
                if(e==null){
                    //获取窗口总数
                    sum = object.size();
                    for (Bank banks : object) {
                        MyApplication.windows.add(banks.getWindows());
                    }
                }else{
                    Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                }
                message.what = 0x0001;
                handler.sendMessage(message);
            }
        });
    }
    /**
     * 设置默认窗口数量 （9个）
     */
    private void setsTheDefaultWindowNumber(){
        Bank bank = new Bank();
       for (int i =0;i<WINDOWS_SUM; i++){
           //注意：不能调用gameScore.setObjectId("")方法
           bank.setBankName(bankName);
           bank.setWindows(String.valueOf(i+1)+"号窗口");
           bank.setState(false);
           bank.save(new SaveListener<String>() {
               @Override
               public void done(String objectId, BmobException e) {
                   if(e==null){
                       Log.e("BusinessFragment", "创建数据成功：" + objectId);
                   }else{
                       Log.e("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                   }
               }
           });
       }
       }
    public Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0x0001:
                    if (sum != 0){
                        BankWindowsAdapter adapter = new BankWindowsAdapter(context,sum);
                        gvWindows.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        setGridViewListener();
                    }else {
                        setsTheDefaultWindowNumber();
                    }
                    break;
                case 0x0002:

                    break;
                default:
                    break;
            }
        }
    };
}
