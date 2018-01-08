// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SetPasswordActivity_ViewBinding implements Unbinder {
  private SetPasswordActivity target;

  private View view2131558545;

  @UiThread
  public SetPasswordActivity_ViewBinding(SetPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SetPasswordActivity_ViewBinding(final SetPasswordActivity target, View source) {
    this.target = target;

    View view;
    target.tvPassword = Utils.findRequiredViewAsType(source, R.id.tv_password, "field 'tvPassword'", TextView.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", EditText.class);
    target.tvRPassword = Utils.findRequiredViewAsType(source, R.id.tv_r_password, "field 'tvRPassword'", TextView.class);
    target.etRPassword = Utils.findRequiredViewAsType(source, R.id.et_r_password, "field 'etRPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_ok, "field 'btnOk' and method 'onClick'");
    target.btnOk = Utils.castView(view, R.id.btn_ok, "field 'btnOk'", Button.class);
    view2131558545 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SetPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvPassword = null;
    target.etPassword = null;
    target.tvRPassword = null;
    target.etRPassword = null;
    target.btnOk = null;
    target.progress = null;

    view2131558545.setOnClickListener(null);
    view2131558545 = null;
  }
}
