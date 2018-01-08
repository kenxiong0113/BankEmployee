package com.ruan.bankemployee.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.application.MyApplication;
import com.ruan.bankemployee.javabean.Bank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  by ruan on 2018/1/7.
 * 银行窗口适配器
 */

public class BankWindowsAdapter extends BaseAdapter {
    private Context context;
    private List<Bank> windows = new ArrayList<Bank>();
    private int[] windowsImg = {R.drawable.ic_windows,R.drawable.ic_windows,R.drawable.ic_windows
            ,R.drawable.ic_windows,R.drawable.ic_windows,R.drawable.ic_windows
            ,R.drawable.ic_windows,R.drawable.ic_windows,R.drawable.ic_windows};
    private int sumWindows ;
    public BankWindowsAdapter(Context context,int sum){
        super();
        this.context = context;
        this.sumWindows = sum;
        for (int i = 0; i < sumWindows; i++) {
            Bank win = new Bank(MyApplication.windows.get(i),windowsImg[i]);
            windows.add(win);
            if (i == sumWindows-1){
                windows.add(new Bank("",R.drawable.ic_add_windows));
            }
        }
    }
    @Override
    public int getCount() {
        if (windows != null){
            Log.e("BankWindowsAdapter", "windows.size():" + windows.size());
            return windows.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return windows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
          viewHolder = new ViewHolder();
            // 获得容器
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_gv_bank, null);
            // 初始化组件
            viewHolder.tvWindows = (TextView) convertView.findViewById(R.id.tv_windows);
            viewHolder.imgWindows = (ImageView)convertView.findViewById(R.id.img_windows);
            // 给convertHolder附加一个对象
            convertView.setTag(viewHolder);
        }else {
            // 取得convertHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bank win = windows.get(position);

        viewHolder.imgWindows.setImageResource(win.getImg());
        if (position == sumWindows){
            viewHolder.tvWindows.setText("添加窗口");
        }else {
            viewHolder.tvWindows.setText(win.getWindows());
        }
        return convertView;
    }

    class ViewHolder{
        public TextView tvWindows;
        public ImageView imgWindows;
    }
}
