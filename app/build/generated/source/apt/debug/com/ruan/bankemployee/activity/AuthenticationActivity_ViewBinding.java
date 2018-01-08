// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuthenticationActivity_ViewBinding implements Unbinder {
  private AuthenticationActivity target;

  private View view2131558560;

  @UiThread
  public AuthenticationActivity_ViewBinding(AuthenticationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AuthenticationActivity_ViewBinding(final AuthenticationActivity target, View source) {
    this.target = target;

    View view;
    target.tvBank = Utils.findRequiredViewAsType(source, R.id.tv_bank, "field 'tvBank'", TextView.class);
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tv_name, "field 'tvName'", TextView.class);
    target.etName = Utils.findRequiredViewAsType(source, R.id.et_name, "field 'etName'", EditText.class);
    target.tvSex = Utils.findRequiredViewAsType(source, R.id.tv_time, "field 'tvSex'", TextView.class);
    target.etSex = Utils.findRequiredViewAsType(source, R.id.et_sex, "field 'etSex'", EditText.class);
    target.tvAge = Utils.findRequiredViewAsType(source, R.id.tv_age, "field 'tvAge'", TextView.class);
    target.etAge = Utils.findRequiredViewAsType(source, R.id.et_age, "field 'etAge'", EditText.class);
    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.etNumber = Utils.findRequiredViewAsType(source, R.id.et_number, "field 'etNumber'", EditText.class);
    target.checkBox = Utils.findRequiredViewAsType(source, R.id.checkBox, "field 'checkBox'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.btn_commit, "field 'btnCommit' and method 'onClickButton'");
    target.btnCommit = Utils.castView(view, R.id.btn_commit, "field 'btnCommit'", Button.class);
    view2131558560 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickButton(p0);
      }
    });
    target.tvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'tvPhone'", TextView.class);
    target.etPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'etPhone'", EditText.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", ProgressBar.class);
    target.tvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'tvEmail'", TextView.class);
    target.etEmail = Utils.findRequiredViewAsType(source, R.id.et_email, "field 'etEmail'", EditText.class);
    target.rlInfo = Utils.findRequiredViewAsType(source, R.id.rl_info, "field 'rlInfo'", RelativeLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthenticationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvBank = null;
    target.tvName = null;
    target.etName = null;
    target.tvSex = null;
    target.etSex = null;
    target.tvAge = null;
    target.etAge = null;
    target.tvNumber = null;
    target.etNumber = null;
    target.checkBox = null;
    target.btnCommit = null;
    target.tvPhone = null;
    target.etPhone = null;
    target.progress = null;
    target.tvEmail = null;
    target.etEmail = null;
    target.rlInfo = null;

    view2131558560.setOnClickListener(null);
    view2131558560 = null;
  }
}
