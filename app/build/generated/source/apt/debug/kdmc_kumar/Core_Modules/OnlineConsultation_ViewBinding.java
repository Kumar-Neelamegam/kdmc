// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Core_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OnlineConsultation_ViewBinding implements Unbinder {
  private OnlineConsultation target;

  @UiThread
  public OnlineConsultation_ViewBinding(OnlineConsultation target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OnlineConsultation_ViewBinding(OnlineConsultation target, View source) {
    this.target = target;

    target.parentLayout = Utils.findRequiredViewAsType(source, R.id.parent_layout, "field 'parentLayout'", CoordinatorLayout.class);
    target.imgNodata = Utils.findRequiredViewAsType(source, R.id.img_nodata, "field 'imgNodata'", AppCompatImageView.class);
    target.swipeRefreshLayout = Utils.findRequiredViewAsType(source, R.id.swipeRefreshLayout, "field 'swipeRefreshLayout'", SwipeRefreshLayout.class);
    target.list = Utils.findRequiredViewAsType(source, R.id.list, "field 'list'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OnlineConsultation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentLayout = null;
    target.imgNodata = null;
    target.swipeRefreshLayout = null;
    target.list = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
