package com.ruan.bankemployee.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ruan.bankemployee.R;
import com.ruan.bankemployee.adapter.BankWindowsAdapter;
import com.ruan.bankemployee.application.MyApplication;
import com.ruan.bankemployee.javabean.Bank;
import com.ruan.bankemployee.javabean.HeadCount;
import com.ruan.bankemployee.javabean.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.ErrorCode;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author by ruan
 *         业务界面
 */

public class WindowsFragment extends Fragment {
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
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.btn_open_Windows)
    Button btnOpenWindows;
    @BindView(R.id.btn_close_Windows)
    Button btnCloseWindows;
    private Context context;
    Unbinder unbinder;
    private ProgressDialog dialog;
    String bankName;
    int sum = 0;
    private int num;
    private int selectTheWindowNumber = -1;
    private String myObjectId;
    private static final int WINDOWS_SUM = 9;
    private static final int MAX_WINDOWS_SUM = 16;
    private BankWindowsAdapter adapter;
    private boolean isShowDelete;
    private boolean isWindowsState = false;
    private String windowsObjectId;
    private List<Bank> windows = new ArrayList<Bank>();
    public WindowsFragment() {
        super();
    }

    public static WindowsFragment newInstance(String param) {
        WindowsFragment fragment = new WindowsFragment();
        Bundle args = new Bundle();
        args.putString(ARG, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_windows, container, false);
        unbinder = ButterKnife.bind(this, view);
        context = view.getContext();
        progress.setVisibility(View.VISIBLE);
        fetchUserInfo();
        setSwipeRefreshLayout();
        setBtnWindows();
        return view;
    }

    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     * Synchronize local caching
     * @see ErrorCode#E9024S
     */
    public void fetchUserInfo() {
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
                            getOpenWindowsCount();
                        } else {
                            rLayoutNo.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e1) {
                        rLayoutNo.setVisibility(View.VISIBLE);
                        e1.printStackTrace();
                    } finally {
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
        MyApplication.exit();
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
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 设置网格布局的监听事件
     */
    private void setGridViewListener() {

        gvWindows.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
            //窗口设置最多16个
                if (position == (windows.size()) && windows.size() == MAX_WINDOWS_SUM) {
                    Toast.makeText(getActivity(), "别添加了，你那里有这么多窗口吗", Toast.LENGTH_SHORT).show();
                } else if (position == windows.size()) {
                    Bank bank = new Bank();
                    bank.setBankName(bankName);
                    bank.setState(false);
                    bank.setWindows(String.valueOf(position + 1) + "号窗口");
                    bank.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            Toast.makeText(getActivity(), "添加窗口成功", Toast.LENGTH_SHORT).show();
                            num = position;
                            Message message1 = new Message();
                            message1.what = 0x0002;
                            handler.sendMessage(message1);
                        }
                    });
                } else {
                    adapter.setSelection(position);
                    adapter.notifyDataSetChanged();
                    selectTheWindowNumber = position;
                }
            }
        });
        /**
         * 设置长按窗口监听
         */
        // TODO: 2018/1/10 此功能待确认！！！
        /**
         gvWindows.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        if(position<windows.size()) {
        //删除图片显示时，长按隐藏
        if (isShowDelete) {
        isShowDelete = false;
        adapter.setIsShowDelete(isShowDelete);
        } else {
        //删除图片隐藏时，长按显示
        isShowDelete = true;
        adapter.setIsShowDelete(isShowDelete);
        }
        }
        return false;
        }
        });
         */
    }

    /**
     * 查询当前银行的窗口数量
     */
    private void checkTheNumberOfWindowsInTheCurrentBank() {
        BmobQuery<Bank> query = new BmobQuery<Bank>();
        //查询playerName叫“比目”的数据
        query.addWhereEqualTo("bankName", bankName);
        //返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(20);
        //排序查询
        query.order("createdAt");
        //执行查询方法
        query.findObjects(new FindListener<Bank>() {
            @Override
            public void done(List<Bank> object, BmobException e) {
                if (e == null) {
                    //获取窗口总数
                    sum = object.size();
                    for (Bank banks : object) {
                        if (banks.isState()){
                            windows.add(new Bank(banks.getWindows(),R.drawable.ic_windows_open));
                        }else {
                            Bank bank = new Bank(banks.getWindows(), R.drawable.ic_windows_close);
                            windows.add(bank);
                        }
                    }
                    if (sum != 0) {
                        adapter = new BankWindowsAdapter(context, windows);
                        gvWindows.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        setGridViewListener();
                    } else {
                        setsTheDefaultWindowNumber();
                    }
                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    /**
     * 设置默认窗口数量 （9个）
     * 批量添加数据 保证添加数据的顺序
     */
    private void setsTheDefaultWindowNumber() {
        List<BmobObject> banks = new ArrayList<BmobObject>();
        for (int i = 1; i <= WINDOWS_SUM; i++) {
            Bank bank = new Bank();
            bank.setBankName(bankName);
            bank.setWindows(String.valueOf(i) + "号窗口");
            bank.setState(false);
            banks.add(bank);
        }
        //第二种方式：v3.5.0开始提供
        new BmobBatch().insertBatch(banks).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> o, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < o.size(); i++) {
                        BatchResult result = o.get(i);
                        BmobException ex = result.getError();
                        if (ex == null) {
                            Log.e("第" + i + "个数据批量添加成功：", result.getObjectId() + result.getUpdatedAt());
                        } else {
                            Log.e("第" + i + "个数据批量添加失败：", ex.getMessage() + ex.getErrorCode());
                        }
                    }
                } else {
                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x0001:
                    if (sum != 0) {
                        adapter = new BankWindowsAdapter(context, windows);
                        gvWindows.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        setGridViewListener();
                    } else {
                        setsTheDefaultWindowNumber();
                    }
                    break;
                case 0x0002:
                    Bank bank1 = new Bank(String.valueOf(num + 1) + "号窗口", R.drawable.ic_windows_close);
                    windows.add(bank1);
                    adapter = new BankWindowsAdapter(context, windows);
                    gvWindows.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
                case 0x0003:
                    Bank bank = new Bank();
                    bank.setValue("state", true);
                    bank.setValue("employeeObjectId",myObjectId);
                    bank.update(windowsObjectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                MyApplication.openWindows = MyApplication.openWindows +1;
                                windows.get(selectTheWindowNumber).setImg(R.drawable.ic_windows_open);
                                adapter.notifyDataSetChanged();
                                btnCloseWindows.setVisibility(View.VISIBLE);
                                btnOpenWindows.setVisibility(View.GONE);
                                Log.i("bmob", "更新成功");
                            } else {
                                Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                            dialog.dismiss();
                        }
                    });

                    break;
                case 0x0004:
                    Bank bank2 = new Bank();
                    bank2.setValue("state", false);
                    bank2.setValue("employeeObjectId","");
                    bank2.update(windowsObjectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            Log.e("WindowsFragment", "2---" + MyApplication.openWindows);
                            if (e == null) {
                                MyApplication.openWindows = MyApplication.openWindows -1;
                                windows.get(selectTheWindowNumber).setImg(R.drawable.ic_windows_close);
                                adapter.notifyDataSetChanged();
                                btnCloseWindows.setVisibility(View.GONE);
                                btnOpenWindows.setVisibility(View.VISIBLE);
                                Log.i("bmob", "更新成功");
                            } else {
                                Log.i("bmob", "更新失败：" + e.getMessage() + "," + e.getErrorCode());
                            }
                            dialog.dismiss();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };

    private void setSwipeRefreshLayout() {
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // TODO: 2018/1/10 设置刷新内容
                windows.clear();
                checkTheNumberOfWindowsInTheCurrentBank();
                srl.setRefreshing(false);
            }
        });
    }

    /**
     * 设置按钮点击事件
     */
    private void setBtnWindows() {
            //首先检查当前职员是否有窗口开启
            getsAnOpenWindowForTheCurrentEmployee();
            //开启窗口的按钮
            btnOpenWindows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (windows.get(selectTheWindowNumber).getImg() == R.drawable.ic_windows_open){
                        Toast.makeText(context, "该窗口是打开的", Toast.LENGTH_SHORT).show();
                    }else {
                        dialog = new ProgressDialog(getActivity());
                        dialog.setMessage("正在开启......");
                        dialog.setCancelable(true);
                        dialog.show();
                        Log.e("WindowsFragment", "1---" + MyApplication.openWindows);
                        if (MyApplication.openWindows == 0){
                            HeadCount headCount = new HeadCount();
                            headCount.setCount(0);
                            headCount.setBankName(bankName);
                            headCount.save(new SaveListener<String>() {
                                @Override
                                public void done(String s, BmobException e) {
                                    if (e == null){

                                    }else {
                                        Log.e("WindowsFragment", e.getMessage());
                                        return;
                                    }
                                }
                            });
                        }
                        BmobQuery<Bank> query = new BmobQuery<Bank>();
                        query.addWhereEqualTo("bankName", bankName);
                        query.addWhereEqualTo("windows", String.valueOf(selectTheWindowNumber + 1) + "号窗口");
                        //执行查询方法
                        query.findObjects(new FindListener<Bank>() {
                            @Override
                            public void done(List<Bank> object, BmobException e) {
                                if (e == null) {
                                    windowsObjectId = object.get(0).getObjectId();
                                    Message message2 = new Message();
                                    message2.what = 0x0003;
                                    handler.sendMessage(message2);
                                } else {
                                    dialog.dismiss();
                                    Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                }
            });
            //关闭窗口的按钮
            btnCloseWindows.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (windows.get(selectTheWindowNumber).getImg() == R.drawable.ic_windows_close) {
                        Toast.makeText(context, "该窗口是关闭的", Toast.LENGTH_SHORT).show();
                    }else {
                        BmobQuery<Bank> query = new BmobQuery<Bank>();
                        query.addWhereEqualTo("windows",windows.get(selectTheWindowNumber).getWindows());
                        query.addWhereEqualTo("state",true);
                        query.addWhereEqualTo("employeeObjectId",BmobUser.getCurrentUser().getObjectId());
                        query.findObjects(new FindListener<Bank>() {
                            @Override
                            public void done(List<Bank> list, BmobException e) {
                             if (e == null){
                                 if (list.size() == 0){
                                     Toast.makeText(context, "你无权关闭该窗口", Toast.LENGTH_SHORT).show();
                                 }else {
                                     dialog = new ProgressDialog(getActivity());
                                     dialog.setMessage("正在关闭......");
                                     dialog.setCancelable(true);
                                     dialog.show();
                                     BmobQuery<Bank> query = new BmobQuery<Bank>();
                                     //查询playerName叫“比目”的数据
                                     query.addWhereEqualTo("employeeObjectId", myObjectId);
                                     //执行查询方法
                                     query.findObjects(new FindListener<Bank>() {
                                         @Override
                                         public void done(List<Bank> object, BmobException e) {
                                             if (e == null) {
                                                 windowsObjectId = object.get(0).getObjectId();
                                                 Message message3 = new Message();
                                                 message3.what = 0x0004;
                                                 handler.sendMessage(message3);
                                             } else {
                                                 dialog.dismiss();
                                                 Toast.makeText(context, e.getMessage() + e.getErrorCode(),
                                                         Toast.LENGTH_SHORT).show();
                                                 Log.e("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                                             }
                                         }
                                     });
                                 }
                             }else {
                                 Toast.makeText(context, e.getMessage() + e.getErrorCode(),
                                         Toast.LENGTH_SHORT).show();
                                 Log.d("WindowsFragment", e.getMessage() + e.getErrorCode());
                             }
                            }
                        });

                    }

                }
            });
    }
    /**
     * 获取当前职员是否有开启窗口，避免多次开启
     */
    private void getsAnOpenWindowForTheCurrentEmployee(){
        User user = BmobUser.getCurrentUser(User.class);
        myObjectId = user.getObjectId();
        BmobQuery<Bank> query = new BmobQuery<Bank>();
        query.addWhereEqualTo("employeeObjectId", myObjectId);
        query.addWhereEqualTo("state", true);
        query.findObjects(new FindListener<Bank>() {
            @Override
            public void done(List<Bank> list, BmobException e) {
                if (e == null){
                    if (list.size() == 0) {
                        btnOpenWindows.setVisibility(View.VISIBLE);
                        btnCloseWindows.setVisibility(View.GONE);
                    } else {
                        MyApplication.openWindows = MyApplication.openWindows + 1;
                        btnCloseWindows.setVisibility(View.VISIBLE);
                        btnOpenWindows.setVisibility(View.GONE);
                    }
                }else {
                    Log.e("WindowsFragment", e.getErrorCode() + e.getMessage());
                    Toast.makeText(context, e.getMessage() + e.getErrorCode(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    /**
     * 获取该银行开启窗口的总数
     */
    private void getOpenWindowsCount(){
        BmobQuery<Bank> query = new BmobQuery<Bank>();
        query.addWhereEqualTo("state",true);
        query.addWhereEqualTo("bankName",bankName);
        query.findObjects(new FindListener<Bank>() {
            @Override
            public void done(List<Bank> list, BmobException e) {
                if (e == null){
                    MyApplication.openWindows = 0;
                    Log.e("WindowsFragment", "list.size():" + list.size());
                    for (Bank banks : list)  {
                        MyApplication.openWindows = MyApplication.openWindows + 1;
                        Log.e("WindowsFragment", banks.getObjectId());

                    }
                } else {
                    Log.e("WindowsFragment", e.getMessage() + e.getErrorCode());
                }

            }
        });
    }

}