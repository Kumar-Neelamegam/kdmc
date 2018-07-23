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

public class Profile_Casenotes_ViewBinding implements Unbinder {
  private Profile_Casenotes target;

  @UiThread
  public Profile_Casenotes_ViewBinding(Profile_Casenotes target, View source) {
    this.target = target;

    target.casenotesProfileParentLayout = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_parent_layout, "field 'casenotesProfileParentLayout'", LinearLayout.class);
    target.casenotesProfilePatientid = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_patientid, "field 'casenotesProfilePatientid'", TextView.class);
    target.casenotesProfileRecycler = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_recycler, "field 'casenotesProfileRecycler'", RecyclerView.class);
    target.imgNodata = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_img_nodata, "field 'imgNodata'", AppCompatImageView.class);
    target.casenotesNodatatext = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_nodatatext, "field 'casenotesNodatatext'", TextView.class);
    target.casenotesProfileWebvwProfile = Utils.findRequiredViewAsType(source, R.id.casenotes_profile_webvw_profile, "field 'casenotesProfileWebvwProfile'", WebView.class);
    target.imgvwBloodsugarBar = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodsugar_bar, "field 'imgvwBloodsugarBar'", AppCompatImageView.class);
    target.imgvwBloodsugarLine = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodsugar_line, "field 'imgvwBloodsugarLine'", AppCompatImageView.class);
    target.imgvwBloodpressureBar = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodpressure_bar, "field 'imgvwBloodpressureBar'", AppCompatImageView.class);
    target.imgvwBloodpressureLine = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodpressure_line, "field 'imgvwBloodpressureLine'", AppCompatImageView.class);
    target.imgvwWeightBar = Utils.findRequiredViewAsType(source, R.id.imgvw_weight_bar, "field 'imgvwWeightBar'", AppCompatImageView.class);
    target.imgvwWeightLine = Utils.findRequiredViewAsType(source, R.id.imgvw_weight_line, "field 'imgvwWeightLine'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Profile_Casenotes target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.casenotesProfileParentLayout = null;
    target.casenotesProfilePatientid = null;
    target.casenotesProfileRecycler = null;
    target.imgNodata = null;
    target.casenotesNodatatext = null;
    target.casenotesProfileWebvwProfile = null;
    target.imgvwBloodsugarBar = null;
    target.imgvwBloodsugarLine = null;
    target.imgvwBloodpressureBar = null;
    target.imgvwBloodpressureLine = null;
    target.imgvwWeightBar = null;
    target.imgvwWeightLine = null;
  }
}
