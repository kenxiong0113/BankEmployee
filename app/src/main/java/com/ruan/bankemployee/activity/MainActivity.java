package com.ruan.bankemployee.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ruan.bankemployee.R;
import com.ruan.bankemployee.fragment.WindowsFragment;
import com.ruan.bankemployee.fragment.MyFragment;
import com.ruan.bankemployee.fragment.WaitTobeFragment;
import com.ruan.bankemployee.other.ExitPressed;

import java.util.ArrayList;

/**
 * @author by ruan
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{
    BottomNavigationBar navigation;
    WindowsFragment windowsFragment;
    MyFragment myFragment;
    WaitTobeFragment waitTobeFragment;
    private ArrayList<Fragment> fragments;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //移除 ActionBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initBottomNavigationBar();
        context =getApplicationContext();
    }


    /**
     * 设置底部导航图片和图标，添加底部导航的点击事件
     */
    private void initBottomNavigationBar() {
        navigation = (BottomNavigationBar) findViewById(R.id.navigation);
        navigation.setMode(BottomNavigationBar.MODE_FIXED);
        navigation.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        navigation.addItem(new BottomNavigationItem(R.drawable.ic_business, getString(R.string.title_business)).setActiveColorResource(R.color.bottomBar))
                .addItem(new BottomNavigationItem(R.drawable.ic_subscribe, getString(R.string.title_subscribe)).setActiveColorResource(R.color.bottomBar))
                .addItem(new BottomNavigationItem(R.drawable.ic_my, getString(R.string.title_my)).setActiveColorResource(R.color.bottomBar))
                .setFirstSelectedPosition(0)
                .initialise();
        setDefaultFragment();
        navigation.setTabSelectedListener(this);
    }

    /**
     * 设置默认fragment
     */
    private void setDefaultFragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        windowsFragment = WindowsFragment.newInstance(getString(R.string.title_business));
        transaction.replace(R.id.content, windowsFragment);
        transaction.commit();

    }

    /**
     * 底部点击事件
     * @param position
     */
    @Override
    public void onTabSelected(int position) {
        FragmentManager fm = getSupportFragmentManager();
        //开启事务
        FragmentTransaction transaction = fm.beginTransaction();
        switch (position) {
            case 0:
                windowsFragment = WindowsFragment.newInstance(getString(R.string.title_business));
                transaction.replace(R.id.content, windowsFragment);
                break;
            case 1:
                waitTobeFragment = WaitTobeFragment.newInstance(getString(R.string.title_subscribe));
                transaction.replace(R.id.content, waitTobeFragment);
                break;
            case 2:
                myFragment = MyFragment.newInstance(getString(R.string.title_my));
                transaction.replace(R.id.content, myFragment);
                break;
            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                ft.remove(fragment);
                ft.commitAllowingStateLoss();
            }
        }

    }

    @Override
    public void onTabReselected(int position) {

    }
    /**
     * 按两次返回退出程序
     */
    @Override
    public void onBackPressed() {
        ExitPressed pressed = new ExitPressed();
        boolean isPressed = pressed.exitPressed(context);
        if (isPressed){
            finish();
        }
    }

}
