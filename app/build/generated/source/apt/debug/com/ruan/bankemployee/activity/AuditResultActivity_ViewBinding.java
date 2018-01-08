// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuditResultActivity_ViewBinding implements Unbinder {
  private AuditResultActivity target;

  private View view2131558545;

  private View view2131558548;

  @UiThread
  public AuditResultActivity_ViewBinding(AuditResultActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuditResultActivity_ViewBinding(final AuditResultActivity target, View source) {
    this.target = target;

    View view;
    target.rLayout = Utils.findRequiredViewAsType(source, R.id.r_layout, "field 'rLayout'", RelativeLayout.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.textView, "field 'textView'", TextView.class);
    target.textView3 = Utils.findRequiredViewAsType(source, R.id.textView3, "field 'textView3'", TextView.class);
    target.textView4 = Utils.findRequiredViewAsType(source, R.id.textView4, "field 'textView4'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.tvSex = Utils.findRequiredViewAsType(source, R.id.tv_sex, "field 'tvSex'", TextView.class);
    target.tvAge = Utils.findRequiredViewAsType(source, R.id.tv_age, "field 'tvAge'", TextView.class);
    target.textView6 = Utils.findRequiredViewAsType(source, R.id.textView6, "field 'textView6'", TextView.class);
    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.textView7 = Utils.findRequiredViewAsType(source, R.id.textView7, "field 'textView7'", TextView.class);
    target.tvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmail'", TextView.class);
    target.textView9 = Utils.findRequiredViewAsType(source, R.id.textView9, "field 'textView9'", TextView.class);
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_ok, "field 'btnOk' and method 'onClickBtn'");
    target.btnOk = Utils.castView(view, R.id.btn_ok, "field 'btnOk'", Button.class);
    view2131558545 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBtn(p0);
      }
    });
    target.rLayout1 = Utils.findRequiredViewAsType(source, R.id.r_layout_1, "field 'rLayout1'", RelativeLayout.class);
    target.rLayoutYes = Utils.findRequiredViewAsType(source, R.id.r_layout_yes, "field 'rLayoutYes'", RelativeLayout.class);
    target.rLayout2 = Utils.findRequiredViewAsType(source, R.id.r_layout_2, "field 'rLayout2'", RelativeLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_ok2, "field 'btnOk2' and method 'onClickBtn'");
    target.btnOk2 = Utils.castView(view, R.id.btn_ok2, "field 'btnOk2'", Button.class);
    view2131558548 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickBtn(p0);
      }
    });
    target.rLayoutNo = Utils.findRequiredViewAsType(source, R.id.r_layout_no, "field 'rLayoutNo'", RelativeLayout.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuditResultActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.rLayout = null;
    target.textView = null;
    target.textView3 = null;
    target.textView4 = null;
    target.tvName = null;
    target.tvSex = null;
    target.tvAge = null;
    target.textView6 = null;
    target.tvNumber = null;
    target.textView7 = null;
    target.tvEmail = null;
    target.textView9 = null;
    target.tvPhone = null;
    target.btnOk = null;
    target.rLayout1 = null;
    target.rLayoutYes = null;
    target.rLayout2 = null;
    target.btnOk2 = null;
    target.rLayoutNo = null;
    target.progress = null;

    view2131558545.setOnClickListener(null);
    view2131558545 = null;
    view2131558548.setOnClickListener(null);
    view2131558548 = null;
  }
}
