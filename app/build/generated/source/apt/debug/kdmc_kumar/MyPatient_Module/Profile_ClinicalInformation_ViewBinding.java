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

public class Profile_ClinicalInformation_ViewBinding implements Unbinder {
  private Profile_ClinicalInformation target;

  @UiThread
  public Profile_ClinicalInformation_ViewBinding(Profile_ClinicalInformation target, View source) {
    this.target = target;

    target.clinicalProfileParentLayout = Utils.findRequiredViewAsType(source, R.id.clinical_profile_parent_layout, "field 'clinicalProfileParentLayout'", LinearLayout.class);
    target.clinicalProfilePatientid = Utils.findRequiredViewAsType(source, R.id.clinical_profile_patientid, "field 'clinicalProfilePatientid'", TextView.class);
    target.recycler = Utils.findRequiredViewAsType(source, R.id.recycler, "field 'recycler'", RecyclerView.class);
    target.clinicalProfileImgNodata = Utils.findRequiredViewAsType(source, R.id.clinical_profile_img_nodata, "field 'clinicalProfileImgNodata'", AppCompatImageView.class);
    target.clinicalProfileNodatatext = Utils.findRequiredViewAsType(source, R.id.clinical_profile_nodatatext, "field 'clinicalProfileNodatatext'", TextView.class);
    target.clinicalProfileClinicalinfo = Utils.findRequiredViewAsType(source, R.id.clinical_profile_clinicalinfo, "field 'clinicalProfileClinicalinfo'", WebView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Profile_ClinicalInformation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.clinicalProfileParentLayout = null;
    target.clinicalProfilePatientid = null;
    target.recycler = null;
    target.clinicalProfileImgNodata = null;
    target.clinicalProfileNodatatext = null;
    target.clinicalProfileClinicalinfo = null;
  }
}
