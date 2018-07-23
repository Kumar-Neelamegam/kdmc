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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class Patient_Registration_ViewBinding implements Unbinder {
  private Patient_Registration target;

  @UiThread
  public Patient_Registration_ViewBinding(Patient_Registration target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Patient_Registration_ViewBinding(Patient_Registration target, View source) {
    this.target = target;

    target.patientParentLayout = Utils.findRequiredViewAsType(source, R.id.patient_parent_layout, "field 'patientParentLayout'", CoordinatorLayout.class);
    target.patientregistrationNesetedscrollview = Utils.findRequiredViewAsType(source, R.id.patientregistration_nesetedscrollview, "field 'patientregistrationNesetedscrollview'", NestedScrollView.class);
    target.imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field 'imgvwPatientphoto'", ImageView.class);
    target.imgvwCamera = Utils.findRequiredViewAsType(source, R.id.imgvw_camera, "field 'imgvwCamera'", ImageView.class);
    target.imgvwBrowse = Utils.findRequiredViewAsType(source, R.id.imgvw_browse, "field 'imgvwBrowse'", ImageView.class);
    target.upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field 'upperlayout'", LinearLayout.class);
    target.textvwPatientName = Utils.findRequiredViewAsType(source, R.id.textvw_patient_name, "field 'textvwPatientName'", TextView.class);
    target.edtPatientName = Utils.findRequiredViewAsType(source, R.id.edt_patient_name, "field 'edtPatientName'", EditText.class);
    target.textvwAge = Utils.findRequiredViewAsType(source, R.id.textvw_age, "field 'textvwAge'", TextView.class);
    target.edtAge = Utils.findRequiredViewAsType(source, R.id.edt_age, "field 'edtAge'", EditText.class);
    target.textvwGender = Utils.findRequiredViewAsType(source, R.id.textvw_gender, "field 'textvwGender'", TextView.class);
    target.spinnerGender = Utils.findRequiredViewAsType(source, R.id.spinner_gender, "field 'spinnerGender'", Spinner.class);
    target.textvwCountry = Utils.findRequiredViewAsType(source, R.id.textvw_country, "field 'textvwCountry'", TextView.class);
    target.autocompleteCountry = Utils.findRequiredViewAsType(source, R.id.autocomplete_country, "field 'autocompleteCountry'", AutoCompleteTextView.class);
    target.textvwState = Utils.findRequiredViewAsType(source, R.id.textvw_state, "field 'textvwState'", TextView.class);
    target.autocompleteState = Utils.findRequiredViewAsType(source, R.id.autocomplete_state, "field 'autocompleteState'", AutoCompleteTextView.class);
    target.textvwCity = Utils.findRequiredViewAsType(source, R.id.textvw_city, "field 'textvwCity'", TextView.class);
    target.autocompleteCity = Utils.findRequiredViewAsType(source, R.id.autocomplete_city, "field 'autocompleteCity'", AutoCompleteTextView.class);
    target.textvwWillingSmsalert = Utils.findRequiredViewAsType(source, R.id.textvw_willing_smsalert, "field 'textvwWillingSmsalert'", TextView.class);
    target.spinnerWillingReceive = Utils.findRequiredViewAsType(source, R.id.spinner_willing_receive, "field 'spinnerWillingReceive'", LabeledSwitch.class);
    target.textvwFeeExemption = Utils.findRequiredViewAsType(source, R.id.textvw_fee_exemption, "field 'textvwFeeExemption'", TextView.class);
    target.spinnerFeeExemption = Utils.findRequiredViewAsType(source, R.id.spinner_fee_exemption, "field 'spinnerFeeExemption'", LabeledSwitch.class);
    target.textvwAddress1 = Utils.findRequiredViewAsType(source, R.id.textvw_address1, "field 'textvwAddress1'", TextView.class);
    target.edtAddress1 = Utils.findRequiredViewAsType(source, R.id.edt_address1, "field 'edtAddress1'", EditText.class);
    target.spinnerBloodgroup = Utils.findRequiredViewAsType(source, R.id.spinner_bloodgroup, "field 'spinnerBloodgroup'", Spinner.class);
    target.spinnerMartialStatus = Utils.findRequiredViewAsType(source, R.id.spinner_martial_status, "field 'spinnerMartialStatus'", Spinner.class);
    target.spinnerPrefix = Utils.findRequiredViewAsType(source, R.id.spinner_prefix, "field 'spinnerPrefix'", Spinner.class);
    target.txtvw_FatherName = Utils.findRequiredViewAsType(source, R.id.txtvw_fathername, "field 'txtvw_FatherName'", TextView.class);
    target.edtFathername = Utils.findRequiredViewAsType(source, R.id.edt_fathername, "field 'edtFathername'", EditText.class);
    target.edtDateofbirth = Utils.findRequiredViewAsType(source, R.id.edt_dateofbirth, "field 'edtDateofbirth'", EditText.class);
    target.edtCaste = Utils.findRequiredViewAsType(source, R.id.edt_caste, "field 'edtCaste'", EditText.class);
    target.edtIncome = Utils.findRequiredViewAsType(source, R.id.edt_income, "field 'edtIncome'", EditText.class);
    target.edtOccupation = Utils.findRequiredViewAsType(source, R.id.edt_occupation, "field 'edtOccupation'", EditText.class);
    target.edtAddress2 = Utils.findRequiredViewAsType(source, R.id.edt_address2, "field 'edtAddress2'", EditText.class);
    target.edtPincode = Utils.findRequiredViewAsType(source, R.id.edt_pincode, "field 'edtPincode'", EditText.class);
    target.edtEmail = Utils.findRequiredViewAsType(source, R.id.edt_email, "field 'edtEmail'", EditText.class);
    target.edtMobilenumber2 = Utils.findRequiredViewAsType(source, R.id.edt_mobilenumber2, "field 'edtMobilenumber2'", EditText.class);
    target.edtCaretaker = Utils.findRequiredViewAsType(source, R.id.edt_caretaker, "field 'edtCaretaker'", EditText.class);
    target.edtCaretakerNumber = Utils.findRequiredViewAsType(source, R.id.edt_caretaker_number, "field 'edtCaretakerNumber'", EditText.class);
    target.edtRelationship = Utils.findRequiredViewAsType(source, R.id.edt_relationship, "field 'edtRelationship'", AutoCompleteTextView.class);
    target.edtPreferredNumber = Utils.findRequiredViewAsType(source, R.id.preferrednumber, "field 'edtPreferredNumber'", RadioGroup.class);
    target.rbtn_caretaker = Utils.findRequiredViewAsType(source, R.id.rbtn_caretaker, "field 'rbtn_caretaker'", RadioButton.class);
    target.rbtn_self = Utils.findRequiredViewAsType(source, R.id.rbtn_self, "field 'rbtn_self'", RadioButton.class);
    target.toggleDonateBlood = Utils.findRequiredViewAsType(source, R.id.toggle_donate_blood, "field 'toggleDonateBlood'", LabeledSwitch.class);
    target.toggleDonateEyes = Utils.findRequiredViewAsType(source, R.id.toggle_donate_eyes, "field 'toggleDonateEyes'", LabeledSwitch.class);
    target.edtRefdocName = Utils.findRequiredViewAsType(source, R.id.edt_refdoc_name, "field 'edtRefdocName'", EditText.class);
    target.edtRefDoctorMobileNo = Utils.findRequiredViewAsType(source, R.id.edt_ref_doctor_mobile_no, "field 'edtRefDoctorMobileNo'", EditText.class);
    target.edtPolicyName = Utils.findRequiredViewAsType(source, R.id.edt_policy_name, "field 'edtPolicyName'", EditText.class);
    target.edtNameofInsurance = Utils.findRequiredViewAsType(source, R.id.edt_nameof_insurance, "field 'edtNameofInsurance'", EditText.class);
    target.edtAmountInsured = Utils.findRequiredViewAsType(source, R.id.edt_amount_insured, "field 'edtAmountInsured'", EditText.class);
    target.edtValidYear = Utils.findRequiredViewAsType(source, R.id.edt_valid_year, "field 'edtValidYear'", EditText.class);
    target.edtAuthorizedHospital = Utils.findRequiredViewAsType(source, R.id.edt_authorized_hospital, "field 'edtAuthorizedHospital'", EditText.class);
    target.edtAadhardcard = Utils.findRequiredViewAsType(source, R.id.edt_aadhardcard, "field 'edtAadhardcard'", EditText.class);
    target.edtBplCard = Utils.findRequiredViewAsType(source, R.id.edt_bpl_card, "field 'edtBplCard'", EditText.class);
    target.buttonCancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'buttonCancel'", Button.class);
    target.buttonSubmit = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'buttonSubmit'", Button.class);
    target.layout_mobile1 = Utils.findRequiredViewAsType(source, R.id.layout_mobile1, "field 'layout_mobile1'", LinearLayout.class);
    target.layout_fee_exemption_category = Utils.findRequiredViewAsType(source, R.id.layout_fee_exemption, "field 'layout_fee_exemption_category'", LinearLayout.class);
    target.TxtvwMobile1 = Utils.findRequiredViewAsType(source, R.id.textvw_mobile1, "field 'TxtvwMobile1'", TextView.class);
    target.TxtvwFeeExemption = Utils.findRequiredViewAsType(source, R.id.textvw_fee_exemption_category, "field 'TxtvwFeeExemption'", TextView.class);
    target.edtMobile1 = Utils.findRequiredViewAsType(source, R.id.edt_mobile1, "field 'edtMobile1'", EditText.class);
    target.spinnerFeeExemption_category = Utils.findRequiredViewAsType(source, R.id.spinner_fee_category, "field 'spinnerFeeExemption_category'", Spinner.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Patient_Registration target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.patientParentLayout = null;
    target.patientregistrationNesetedscrollview = null;
    target.imgvwPatientphoto = null;
    target.imgvwCamera = null;
    target.imgvwBrowse = null;
    target.upperlayout = null;
    target.textvwPatientName = null;
    target.edtPatientName = null;
    target.textvwAge = null;
    target.edtAge = null;
    target.textvwGender = null;
    target.spinnerGender = null;
    target.textvwCountry = null;
    target.autocompleteCountry = null;
    target.textvwState = null;
    target.autocompleteState = null;
    target.textvwCity = null;
    target.autocompleteCity = null;
    target.textvwWillingSmsalert = null;
    target.spinnerWillingReceive = null;
    target.textvwFeeExemption = null;
    target.spinnerFeeExemption = null;
    target.textvwAddress1 = null;
    target.edtAddress1 = null;
    target.spinnerBloodgroup = null;
    target.spinnerMartialStatus = null;
    target.spinnerPrefix = null;
    target.txtvw_FatherName = null;
    target.edtFathername = null;
    target.edtDateofbirth = null;
    target.edtCaste = null;
    target.edtIncome = null;
    target.edtOccupation = null;
    target.edtAddress2 = null;
    target.edtPincode = null;
    target.edtEmail = null;
    target.edtMobilenumber2 = null;
    target.edtCaretaker = null;
    target.edtCaretakerNumber = null;
    target.edtRelationship = null;
    target.edtPreferredNumber = null;
    target.rbtn_caretaker = null;
    target.rbtn_self = null;
    target.toggleDonateBlood = null;
    target.toggleDonateEyes = null;
    target.edtRefdocName = null;
    target.edtRefDoctorMobileNo = null;
    target.edtPolicyName = null;
    target.edtNameofInsurance = null;
    target.edtAmountInsured = null;
    target.edtValidYear = null;
    target.edtAuthorizedHospital = null;
    target.edtAadhardcard = null;
    target.edtBplCard = null;
    target.buttonCancel = null;
    target.buttonSubmit = null;
    target.layout_mobile1 = null;
    target.layout_fee_exemption_category = null;
    target.TxtvwMobile1 = null;
    target.TxtvwFeeExemption = null;
    target.edtMobile1 = null;
    target.spinnerFeeExemption_category = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
