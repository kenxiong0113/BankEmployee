// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BusinessFragment_ViewBinding implements Unbinder {
  private WindowsFragment target;

  @UiThread
  public BusinessFragment_ViewBinding(WindowsFragment target, View source) {
    this.target = target;

    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", ProgressBar.class);
    target.rLayoutNo = Utils.findRequiredViewAsType(source, R.id.r_layout_no, "field 'rLayoutNo'", RelativeLayout.class);
    target.tvBank = Utils.findRequiredViewAsType(source, R.id.tv_bank, "field 'tvBank'", TextView.class);
    target.gvWindows = Utils.findRequiredViewAsType(source, R.id.gv_windows, "field 'gvWindows'", GridView.class);
    target.rLayoutYes = Utils.findRequiredViewAsType(source, R.id.r_layout_yes, "field 'rLayoutYes'", RelativeLayout.class);
    target.srl = Utils.findRequiredViewAsType(source, R.id.srl, "field 'srl'", SwipeRefreshLayout.class);
    target.btnOpenWindows = Utils.findRequiredViewAsType(source, R.id.btn_open_Windows, "field 'btnOpenWindows'", Button.class);
    target.btnCloseWindows = Utils.findRequiredViewAsType(source, R.id.btn_close_Windows, "field 'btnCloseWindows'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WindowsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.progress = null;
    target.rLayoutNo = null;
    target.tvBank = null;
    target.gvWindows = null;
    target.rLayoutYes = null;
    target.srl = null;
    target.btnOpenWindows = null;
    target.btnCloseWindows = null;
  }
}
