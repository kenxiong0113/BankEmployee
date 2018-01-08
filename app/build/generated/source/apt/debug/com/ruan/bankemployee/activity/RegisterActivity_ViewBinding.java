// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131558575;

  private View view2131558576;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    target.tvUsername = Utils.findRequiredViewAsType(source, R.id.tv_username, "field 'tvUsername'", TextView.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.view2 = Utils.findRequiredView(source, R.id.view2, "field 'view2'");
    target.tvVerificationCode = Utils.findRequiredViewAsType(source, R.id.tv_verification_code, "field 'tvVerificationCode'", TextView.class);
    target.etCode = Utils.findRequiredViewAsType(source, R.id.et_code, "field 'etCode'", EditText.class);
    target.view = Utils.findRequiredView(source, R.id.view, "field 'view'");
    view = Utils.findRequiredView(source, R.id.btn_code, "field 'btnCode' and method 'onClick'");
    target.btnCode = Utils.castView(view, R.id.btn_code, "field 'btnCode'", Button.class);
    view2131558575 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_next_step, "field 'btnNextStep' and method 'onClick'");
    target.btnNextStep = Utils.castView(view, R.id.btn_next_step, "field 'btnNextStep'", Button.class);
    view2131558576 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvUsername = null;
    target.etPhone = null;
    target.view2 = null;
    target.tvVerificationCode = null;
    target.etCode = null;
    target.view = null;
    target.btnCode = null;
    target.btnNextStep = null;

    view2131558575.setOnClickListener(null);
    view2131558575 = null;
    view2131558576.setOnClickListener(null);
    view2131558576 = null;
  }
}
