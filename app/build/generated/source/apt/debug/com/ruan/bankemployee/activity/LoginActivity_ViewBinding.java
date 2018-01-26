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

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  private View view2131558565;

  private View view2131558567;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(final LoginActivity target, View source) {
    this.target = target;

    View view;
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.etPassword = Utils.findRequiredViewAsType(source, R.id.et_password, "field 'etPassword'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_login, "field 'btnLogin' and method 'onClick'");
    target.btnLogin = Utils.castView(view, R.id.btn_login, "field 'btnLogin'", Button.class);
    view2131558565 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.tvForget = Utils.findRequiredViewAsType(source, R.id.tv_forget, "field 'tvForget'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tv_register, "field 'tvRegister' and method 'onClick'");
    target.tvRegister = Utils.castView(view, R.id.tv_register, "field 'tvRegister'", TextView.class);
    view2131558567 = view;
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
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etPhone = null;
    target.etPassword = null;
    target.btnLogin = null;
    target.tvForget = null;
    target.tvRegister = null;
    target.progress = null;

    view2131558565.setOnClickListener(null);
    view2131558565 = null;
    view2131558567.setOnClickListener(null);
    view2131558567 = null;
  }
}
