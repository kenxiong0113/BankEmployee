// Generated code from Butter Knife. Do not modify!
package com.ruan.bankemployee.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.amap.api.maps.MapView;
import com.ruan.bankemployee.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BankLocationActivity_ViewBinding implements Unbinder {
  private BankLocationActivity target;

  @UiThread
  public BankLocationActivity_ViewBinding(BankLocationActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BankLocationActivity_ViewBinding(BankLocationActivity target, View source) {
    this.target = target;

    target.mapView = Utils.findRequiredViewAsType(source, R.id.map_view, "field 'mapView'", MapView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BankLocationActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mapView = null;
  }
}
