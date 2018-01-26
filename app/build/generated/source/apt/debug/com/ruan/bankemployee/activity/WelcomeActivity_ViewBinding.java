// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WelcomeActivity_ViewBinding implements Unbinder {
  private WelcomeActivity target;

  @UiThread
  public WelcomeActivity_ViewBinding(WelcomeActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WelcomeActivity_ViewBinding(WelcomeActivity target, View source) {
    this.target = target;

    target.btnJump = Utils.findRequiredViewAsType(source, R.id.btn_jump, "field 'btnJump'", Button.class);
    target.imgPicture = Utils.findRequiredViewAsType(source, R.id.img_picture, "field 'imgPicture'", ImageView.class);
    target.progress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'progress'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WelcomeActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnJump = null;
    target.imgPicture = null;
    target.progress = null;
  }
}
