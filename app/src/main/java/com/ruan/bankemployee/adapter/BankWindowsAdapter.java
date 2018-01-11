package com.ruan.bankemployee.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.javabean.Bank;
import java.util.List;

/**
 * @author  by ruan on 2018/1/7.
 * 银行窗口适配器
 */

public class BankWindowsAdapter extends BaseAdapter {
    private Context context;
    private List<Bank> windows;
    LayoutInflater layoutInflater;
    private boolean isShowDelete;
    public BankWindowsAdapter(Context context,List<Bank> windows){
        super();
        this.context = context;
        this.windows = windows;
        layoutInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        if (windows != null){
            return (windows.size()+1);
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
          viewHolder = new ViewHolder();
            // 获得容器
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_gv_bank, null);
            // 初始化组件
            viewHolder.tvWindows = (TextView) convertView.findViewById(R.id.tv_windows);
            viewHolder.imgWindows = (ImageView)convertView.findViewById(R.id.img_windows);
            viewHolder.deleteImage = (ImageView)convertView.findViewById(R.id.delete);
            viewHolder.frameLayout = (FrameLayout)convertView.findViewById(R.id.fm);

            // 给convertHolder附加一个对象
            convertView.setTag(viewHolder);
        }else {
            // 取得convertHolder附加的对象
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (clickTemp == position) {
            convertView.setBackgroundColor(Color.rgb(119,136,153));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        if (position <windows.size()){
            Bank win = windows.get(position);
            viewHolder.imgWindows.setImageResource(win.getImg());
            viewHolder.tvWindows.setText(win.getWindows());
            viewHolder.deleteImage.setVisibility(isShowDelete ? View.VISIBLE : View.GONE);
            if(isShowDelete) {
                viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        windows.remove(position);
                        setIsShowDelete(false);
                    }
                });
            }
        }else {
            viewHolder.imgWindows.setImageResource(R.drawable.ic_add_windows);
            viewHolder.tvWindows.setText("添加窗口");
        }
        return convertView;
    }

    class ViewHolder{
        public TextView tvWindows;
        public ImageView imgWindows,deleteImage;
        public FrameLayout frameLayout;
    }
    public void setIsShowDelete(boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        notifyDataSetChanged();
    }

    private int clickTemp = -1;
    /**
     * 标识选择的item
     */
    public void setSelection(int position) {
        clickTemp = position;
    }
}
