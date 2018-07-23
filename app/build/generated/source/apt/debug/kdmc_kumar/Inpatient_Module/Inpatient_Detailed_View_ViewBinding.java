// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Inpatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
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

public class Inpatient_Detailed_View_ViewBinding implements Unbinder {
  private Inpatient_Detailed_View target;

  @UiThread
  public Inpatient_Detailed_View_ViewBinding(Inpatient_Detailed_View target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Inpatient_Detailed_View_ViewBinding(Inpatient_Detailed_View target, View source) {
    this.target = target;

    target.inpatientParentLayout = Utils.findRequiredViewAsType(source, R.id.inpatient_parent_layout, "field 'inpatientParentLayout'", CoordinatorLayout.class);
    target.inpatientImgvwPatientPhoto = Utils.findRequiredViewAsType(source, R.id.inpatient_imgvw_patient_photo, "field 'inpatientImgvwPatientPhoto'", ImageView.class);
    target.inpatientWebvwProfile = Utils.findRequiredViewAsType(source, R.id.inpatient_webvw_profile, "field 'inpatientWebvwProfile'", WebView.class);
    target.inpatientFab = Utils.findRequiredViewAsType(source, R.id.inpatient_fab, "field 'inpatientFab'", FloatingActionButton.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Inpatient_Detailed_View target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inpatientParentLayout = null;
    target.inpatientImgvwPatientPhoto = null;
    target.inpatientWebvwProfile = null;
    target.inpatientFab = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
