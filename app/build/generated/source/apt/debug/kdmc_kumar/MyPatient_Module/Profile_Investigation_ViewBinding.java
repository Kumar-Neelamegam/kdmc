// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.MyPatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Profile_Investigation_ViewBinding implements Unbinder {
  private Profile_Investigation target;

  @UiThread
  public Profile_Investigation_ViewBinding(Profile_Investigation target, View source) {
    this.target = target;

    target.investigationParentLayout = Utils.findRequiredViewAsType(source, R.id.investigation_parent_layout, "field 'investigationParentLayout'", LinearLayout.class);
    target.investigationProfilePatientid = Utils.findRequiredViewAsType(source, R.id.investigation_profile_patientid, "field 'investigationProfilePatientid'", TextView.class);
    target.investigationProfileRecycler = Utils.findRequiredViewAsType(source, R.id.investigation_profile_recycler, "field 'investigationProfileRecycler'", RecyclerView.class);
    target.investigationProfileImgNodata = Utils.findRequiredViewAsType(source, R.id.investigation_profile_img_nodata, "field 'investigationProfileImgNodata'", AppCompatImageView.class);
    target.investigationProfileNodatatext = Utils.findRequiredViewAsType(source, R.id.investigation_profile_nodatatext, "field 'investigationProfileNodatatext'", TextView.class);
    target.webvwInvestigationProfile = Utils.findRequiredViewAsType(source, R.id.webvw_investigation_profile, "field 'webvwInvestigationProfile'", WebView.class);
    target.investigationReportsLayout = Utils.findRequiredViewAsType(source, R.id.investigation_reports_layout, "field 'investigationReportsLayout'", LinearLayout.class);
    target.investigationProfileReports = Utils.findRequiredViewAsType(source, R.id.investigation_profile_reports, "field 'investigationProfileReports'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Profile_Investigation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.investigationParentLayout = null;
    target.investigationProfilePatientid = null;
    target.investigationProfileRecycler = null;
    target.investigationProfileImgNodata = null;
    target.investigationProfileNodatatext = null;
    target.webvwInvestigationProfile = null;
    target.investigationReportsLayout = null;
    target.investigationProfileReports = null;
  }
}
