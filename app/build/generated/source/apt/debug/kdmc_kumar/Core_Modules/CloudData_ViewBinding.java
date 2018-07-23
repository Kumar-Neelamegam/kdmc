// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Core_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CloudData_ViewBinding implements Unbinder {
  private CloudData target;

  @UiThread
  public CloudData_ViewBinding(CloudData target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CloudData_ViewBinding(CloudData target, View source) {
    this.target = target;

    target.parentLayout = Utils.findRequiredViewAsType(source, R.id.parent_layout, "field 'parentLayout'", CoordinatorLayout.class);
    target.imagevwServerconnectivity = Utils.findRequiredViewAsType(source, R.id.imagevw_serverconnectivity, "field 'imagevwServerconnectivity'", ImageView.class);
    target.textviewServertitle = Utils.findRequiredViewAsType(source, R.id.textview_servertitle, "field 'textviewServertitle'", TextView.class);
    target.imagevwInternet = Utils.findRequiredViewAsType(source, R.id.imagevw_internet, "field 'imagevwInternet'", ImageView.class);
    target.textvwInternet = Utils.findRequiredViewAsType(source, R.id.textvw_internet, "field 'textvwInternet'", TextView.class);
    target.webvwProfile = Utils.findRequiredViewAsType(source, R.id.webvw_profile, "field 'webvwProfile'", WebView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CloudData target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentLayout = null;
    target.imagevwServerconnectivity = null;
    target.textviewServertitle = null;
    target.imagevwInternet = null;
    target.textvwInternet = null;
    target.webvwProfile = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
