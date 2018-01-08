package com.ruan.bankemployee.util;

import android.util.Log;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FetchUserInfoListener;

/**
 * @author  by ruan on 2018/1/7.
 */

public class SynchronizeLocalCaching {

    /**
     * 更新本地用户信息
     * 注意：需要先登录，否则会报9024错误
     * Synchronize local caching
     * @see cn.bmob.v3.helper.ErrorCode#E9024S
     */
    public  void fetchUserInfo() {
        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    Log.e("1111","Newest UserInfo is " + s);
                } else {
                    Log.e("WelcomeActivity", "e.getErrorCode():" + e.getErrorCode());
                }
            }
        });
    }
}
