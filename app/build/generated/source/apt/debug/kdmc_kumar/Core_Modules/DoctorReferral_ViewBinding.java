// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Core_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class DoctorReferral_ViewBinding implements Unbinder {
  private DoctorReferral target;

  @UiThread
  public DoctorReferral_ViewBinding(DoctorReferral target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DoctorReferral_ViewBinding(DoctorReferral target, View source) {
    this.target = target;

    target.parentLayout = Utils.findRequiredViewAsType(source, R.id.parent_layout, "field 'parentLayout'", CoordinatorLayout.class);
    target.nesetedscrollviewDoctorreferral = Utils.findRequiredViewAsType(source, R.id.nesetedscrollview_doctorreferral, "field 'nesetedscrollviewDoctorreferral'", NestedScrollView.class);
    target.upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field 'upperlayout'", LinearLayout.class);
    target.imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field 'imgvwPatientphoto'", CircleImageView.class);
    target.tvwAgegender = Utils.findRequiredViewAsType(source, R.id.tvw_agegender, "field 'tvwAgegender'", TextView.class);
    target.txtvwTitlePatientname = Utils.findRequiredViewAsType(source, R.id.txtvw_title_patientname, "field 'txtvwTitlePatientname'", TextView.class);
    target.autocompletePatientname = Utils.findRequiredViewAsType(source, R.id.autocomplete_patientname, "field 'autocompletePatientname'", AutoCompleteTextView.class);
    target.txtvwTreatmentfor = Utils.findRequiredViewAsType(source, R.id.txtvw_treatmentfor, "field 'txtvwTreatmentfor'", TextView.class);
    target.multiautoTreatmentfor = Utils.findRequiredViewAsType(source, R.id.multiauto_treatmentfor, "field 'multiautoTreatmentfor'", MultiAutoCompleteTextView.class);
    target.txtvwDiagnosis = Utils.findRequiredViewAsType(source, R.id.txtvw_diagnosis, "field 'txtvwDiagnosis'", TextView.class);
    target.multiautoDiagnosis = Utils.findRequiredViewAsType(source, R.id.multiauto_diagnosis, "field 'multiautoDiagnosis'", MultiAutoCompleteTextView.class);
    target.primaryReferralDoctor = Utils.findRequiredViewAsType(source, R.id.primary_referral_doctor, "field 'primaryReferralDoctor'", LinearLayout.class);
    target.textviewDoctorname = Utils.findRequiredViewAsType(source, R.id.textview_doctorname, "field 'textviewDoctorname'", TextView.class);
    target.autocompleteDoctorname = Utils.findRequiredViewAsType(source, R.id.autocomplete_doctorname, "field 'autocompleteDoctorname'", AutoCompleteTextView.class);
    target.textviewSpecialization = Utils.findRequiredViewAsType(source, R.id.textview_specialization, "field 'textviewSpecialization'", TextView.class);
    target.spinnerSpecialist = Utils.findRequiredViewAsType(source, R.id.spinner_specialist, "field 'spinnerSpecialist'", Spinner.class);
    target.textviewSpecialist = Utils.findRequiredViewAsType(source, R.id.textview_specialist, "field 'textviewSpecialist'", TextView.class);
    target.textviewReferredfromHospital = Utils.findRequiredViewAsType(source, R.id.textview_referredfrom_hospital, "field 'textviewReferredfromHospital'", TextView.class);
    target.edttextReferredfromHospital = Utils.findRequiredViewAsType(source, R.id.edttext_referredfrom_hospital, "field 'edttextReferredfromHospital'", EditText.class);
    target.textviewReferredtoHospital = Utils.findRequiredViewAsType(source, R.id.textview_referredto_hospital, "field 'textviewReferredtoHospital'", TextView.class);
    target.autocompleteReferredtoHospital = Utils.findRequiredViewAsType(source, R.id.autocomplete_referredto_hospital, "field 'autocompleteReferredtoHospital'", AutoCompleteTextView.class);
    target.textviewClinicalname = Utils.findRequiredViewAsType(source, R.id.textview_clinicalname, "field 'textviewClinicalname'", TextView.class);
    target.edittextClinicalname = Utils.findRequiredViewAsType(source, R.id.edittext_clinicalname, "field 'edittextClinicalname'", EditText.class);
    target.textviewAddress = Utils.findRequiredViewAsType(source, R.id.textview_address, "field 'textviewAddress'", TextView.class);
    target.edittextAddress = Utils.findRequiredViewAsType(source, R.id.edittext_address, "field 'edittextAddress'", EditText.class);
    target.textviewEmailid = Utils.findRequiredViewAsType(source, R.id.textview_emailid, "field 'textviewEmailid'", TextView.class);
    target.edittextEmailid = Utils.findRequiredViewAsType(source, R.id.edittext_emailid, "field 'edittextEmailid'", EditText.class);
    target.textviewMobilenumber = Utils.findRequiredViewAsType(source, R.id.textview_mobilenumber, "field 'textviewMobilenumber'", TextView.class);
    target.edittextMobilenumber = Utils.findRequiredViewAsType(source, R.id.edittext_mobilenumber, "field 'edittextMobilenumber'", EditText.class);
    target.textviewRemarks = Utils.findRequiredViewAsType(source, R.id.textview_remarks, "field 'textviewRemarks'", TextView.class);
    target.edittextRemarks = Utils.findRequiredViewAsType(source, R.id.edittext_remarks, "field 'edittextRemarks'", EditText.class);
    target.textviewRemarkscontent = Utils.findRequiredViewAsType(source, R.id.textview_remarkscontent, "field 'textviewRemarkscontent'", TextView.class);
    target.multiautocompleteRemarksContent = Utils.findRequiredViewAsType(source, R.id.multiautocomplete_remarks_content, "field 'multiautocompleteRemarksContent'", MultiAutoCompleteTextView.class);
    target.buttonCancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'buttonCancel'", Button.class);
    target.buttonSubmit = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'buttonSubmit'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DoctorReferral target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentLayout = null;
    target.nesetedscrollviewDoctorreferral = null;
    target.upperlayout = null;
    target.imgvwPatientphoto = null;
    target.tvwAgegender = null;
    target.txtvwTitlePatientname = null;
    target.autocompletePatientname = null;
    target.txtvwTreatmentfor = null;
    target.multiautoTreatmentfor = null;
    target.txtvwDiagnosis = null;
    target.multiautoDiagnosis = null;
    target.primaryReferralDoctor = null;
    target.textviewDoctorname = null;
    target.autocompleteDoctorname = null;
    target.textviewSpecialization = null;
    target.spinnerSpecialist = null;
    target.textviewSpecialist = null;
    target.textviewReferredfromHospital = null;
    target.edttextReferredfromHospital = null;
    target.textviewReferredtoHospital = null;
    target.autocompleteReferredtoHospital = null;
    target.textviewClinicalname = null;
    target.edittextClinicalname = null;
    target.textviewAddress = null;
    target.edittextAddress = null;
    target.textviewEmailid = null;
    target.edittextEmailid = null;
    target.textviewMobilenumber = null;
    target.edittextMobilenumber = null;
    target.textviewRemarks = null;
    target.edittextRemarks = null;
    target.textviewRemarkscontent = null;
    target.multiautocompleteRemarksContent = null;
    target.buttonCancel = null;
    target.buttonSubmit = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
