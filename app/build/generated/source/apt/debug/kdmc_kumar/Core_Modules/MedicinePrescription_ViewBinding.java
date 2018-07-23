// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Core_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class MedicinePrescription_ViewBinding implements Unbinder {
  private MedicinePrescription target;

  @UiThread
  public MedicinePrescription_ViewBinding(MedicinePrescription target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MedicinePrescription_ViewBinding(MedicinePrescription target, View source) {
    this.target = target;

    target._medicinePrescriptionParentLayout = Utils.findRequiredViewAsType(source, R.id.medicine_prescription_parent_layout, "field '_medicinePrescriptionParentLayout'", CoordinatorLayout.class);
    target._medicinePrescriptionNesetedscrollview = Utils.findRequiredViewAsType(source, R.id.medicine_prescription_nesetedscrollview, "field '_medicinePrescriptionNesetedscrollview'", NestedScrollView.class);
    target._upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field '_upperlayout'", LinearLayout.class);
    target._imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field '_imgvwPatientphoto'", CircleImageView.class);
    target._tvwAgegender = Utils.findRequiredViewAsType(source, R.id.tvw_agegender, "field '_tvwAgegender'", TextView.class);
    target._txtvwTitlePatientname = Utils.findRequiredViewAsType(source, R.id.txtvw_title_patientname, "field '_txtvwTitlePatientname'", TextView.class);
    target._autocompletePatientname = Utils.findRequiredViewAsType(source, R.id.autocomplete_patientname, "field '_autocompletePatientname'", AutoCompleteTextView.class);
    target._txtvwTreatmentfor = Utils.findRequiredViewAsType(source, R.id.txtvw_treatmentfor, "field '_txtvwTreatmentfor'", TextView.class);
    target._multiautoTreatmentfor = Utils.findRequiredViewAsType(source, R.id.multiauto_treatmentfor, "field '_multiautoTreatmentfor'", MultiAutoCompleteTextView.class);
    target._txtvwDiagnosis = Utils.findRequiredViewAsType(source, R.id.txtvw_diagnosis, "field '_txtvwDiagnosis'", TextView.class);
    target._multiautoDiagnosis = Utils.findRequiredViewAsType(source, R.id.multiauto_diagnosis, "field '_multiautoDiagnosis'", MultiAutoCompleteTextView.class);
    target.PreviousMedicineLayout = Utils.findRequiredViewAsType(source, R.id.layout_prevmedhistory, "field 'PreviousMedicineLayout'", LinearLayout.class);
    target._cardMedicineDetails = Utils.findRequiredViewAsType(source, R.id.card_medicine_details, "field '_cardMedicineDetails'", CardView.class);
    target._txvwMedicineDetailsTitle = Utils.findRequiredViewAsType(source, R.id.txvw_medicine_details_title, "field '_txvwMedicineDetailsTitle'", TextView.class);
    target.imgvw_medicine_options = Utils.findRequiredViewAsType(source, R.id.imgvw_medicine_options, "field 'imgvw_medicine_options'", ImageView.class);
    target._medicineTextinputlayout = Utils.findRequiredViewAsType(source, R.id.medicine_textinputlayout, "field '_medicineTextinputlayout'", TextView.class);
    target.txtview_visiting_title = Utils.findRequiredViewAsType(source, R.id.txtview_visiting_title, "field 'txtview_visiting_title'", TextView.class);
    target._autocompleteMedicineName = Utils.findRequiredViewAsType(source, R.id.autocomplete_medicine_name, "field '_autocompleteMedicineName'", AutoCompleteTextView.class);
    target._txtviewChooseFrequency = Utils.findRequiredViewAsType(source, R.id.txtview_choose_frequency, "field '_txtviewChooseFrequency'", TextView.class);
    target._chkbxCheckall = Utils.findRequiredViewAsType(source, R.id.chkbx_checkall, "field '_chkbxCheckall'", CheckBox.class);
    target._chkbxMorning = Utils.findRequiredViewAsType(source, R.id.chkbx_morning, "field '_chkbxMorning'", CheckBox.class);
    target._txtvwMorning = Utils.findRequiredViewAsType(source, R.id.txtvw_morning, "field '_txtvwMorning'", TextView.class);
    target._edttxtMorningFrequency = Utils.findRequiredViewAsType(source, R.id.edttxt_morning_frequency, "field '_edttxtMorningFrequency'", EditText.class);
    target._spinnerMorning = Utils.findRequiredViewAsType(source, R.id.spinner_morning, "field '_spinnerMorning'", Spinner.class);
    target._chkbxAfternoon = Utils.findRequiredViewAsType(source, R.id.chkbx_afternoon, "field '_chkbxAfternoon'", CheckBox.class);
    target._txtvwAfternoon = Utils.findRequiredViewAsType(source, R.id.txtvw_afternoon, "field '_txtvwAfternoon'", TextView.class);
    target._edttxtAfternoonFrequency = Utils.findRequiredViewAsType(source, R.id.edttxt_afternoon_frequency, "field '_edttxtAfternoonFrequency'", EditText.class);
    target._spinnerAfternoon = Utils.findRequiredViewAsType(source, R.id.spinner_afternoon, "field '_spinnerAfternoon'", Spinner.class);
    target._chkbxEvening = Utils.findRequiredViewAsType(source, R.id.chkbx_evening, "field '_chkbxEvening'", CheckBox.class);
    target._txtvwEvening = Utils.findRequiredViewAsType(source, R.id.txtvw_evening, "field '_txtvwEvening'", TextView.class);
    target._edttxtEvening = Utils.findRequiredViewAsType(source, R.id.edttxt_evening, "field '_edttxtEvening'", EditText.class);
    target._spinnerEvening = Utils.findRequiredViewAsType(source, R.id.spinner_evening, "field '_spinnerEvening'", Spinner.class);
    target._chkbxNite = Utils.findRequiredViewAsType(source, R.id.chkbx_nite, "field '_chkbxNite'", CheckBox.class);
    target._textvwNite = Utils.findRequiredViewAsType(source, R.id.textvw_nite, "field '_textvwNite'", TextView.class);
    target._edttxtNite = Utils.findRequiredViewAsType(source, R.id.edttxt_nite, "field '_edttxtNite'", EditText.class);
    target._spinnerNite = Utils.findRequiredViewAsType(source, R.id.spinner_nite, "field '_spinnerNite'", Spinner.class);
    target._layoutChooseIntake = Utils.findRequiredViewAsType(source, R.id.layout_choose_intake, "field '_layoutChooseIntake'", LinearLayout.class);
    target._textvwChooseIntakeTitle = Utils.findRequiredViewAsType(source, R.id.textvw_choose_intake_title, "field '_textvwChooseIntakeTitle'", TextView.class);
    target._radiogrpIntakeOptions = Utils.findRequiredViewAsType(source, R.id.radiogrp_intake_options, "field '_radiogrpIntakeOptions'", RadioGroup.class);
    target._radiobtnAfterFood = Utils.findRequiredViewAsType(source, R.id.radiobtn_after_food, "field '_radiobtnAfterFood'", RadioButton.class);
    target._radiobtnBeforeFood = Utils.findRequiredViewAsType(source, R.id.radiobtn_before_food, "field '_radiobtnBeforeFood'", RadioButton.class);
    target._radiobtnWithFood = Utils.findRequiredViewAsType(source, R.id.radiobtn_with_food, "field '_radiobtnWithFood'", RadioButton.class);
    target._layoutChooseDuration = Utils.findRequiredViewAsType(source, R.id.layout_choose_duration, "field '_layoutChooseDuration'", LinearLayout.class);
    target._textvwChooseDurationTitle = Utils.findRequiredViewAsType(source, R.id.textvw_choose_duration_title, "field '_textvwChooseDurationTitle'", TextView.class);
    target._spinnerDuration = Utils.findRequiredViewAsType(source, R.id.spinner_duration, "field '_spinnerDuration'", Spinner.class);
    target._buttonAddMedicine = Utils.findRequiredViewAsType(source, R.id.button_add_medicine, "field '_buttonAddMedicine'", Button.class);
    target._recyclerMedicineList = Utils.findRequiredViewAsType(source, R.id.recycler_medicine_list, "field '_recyclerMedicineList'", RecyclerView.class);
    target._injectionLayout = Utils.findRequiredViewAsType(source, R.id.injection_layout, "field '_injectionLayout'", LinearLayout.class);
    target._autocmpltxtInjection = Utils.findRequiredViewAsType(source, R.id.autocmpltxt_injection, "field '_autocmpltxtInjection'", AutoCompleteTextView.class);
    target._edtInjecdosage = Utils.findRequiredViewAsType(source, R.id.edt_injecdosage, "field '_edtInjecdosage'", EditText.class);
    target._edtInjquantity = Utils.findRequiredViewAsType(source, R.id.edt_injquantity, "field '_edtInjquantity'", EditText.class);
    target._btnAddinj = Utils.findRequiredViewAsType(source, R.id.btn_addinj, "field '_btnAddinj'", Button.class);
    target._injectionRecycler = Utils.findRequiredViewAsType(source, R.id.injection_recycler, "field '_injectionRecycler'", RecyclerView.class);
    target._nebulizationLayout = Utils.findRequiredViewAsType(source, R.id.nebulization_layout, "field '_nebulizationLayout'", LinearLayout.class);
    target._chkNrmsaline = Utils.findRequiredViewAsType(source, R.id.chk_nrmsaline, "field '_chkNrmsaline'", CheckBox.class);
    target._chkAsthasaline = Utils.findRequiredViewAsType(source, R.id.chk_asthasaline, "field '_chkAsthasaline'", CheckBox.class);
    target._edtDosage1 = Utils.findRequiredViewAsType(source, R.id.edt_dosage1, "field '_edtDosage1'", EditText.class);
    target._edtDuration1 = Utils.findRequiredViewAsType(source, R.id.edt_duration1, "field '_edtDuration1'", EditText.class);
    target._edtQuantity1 = Utils.findRequiredViewAsType(source, R.id.edt_quantity1, "field '_edtQuantity1'", EditText.class);
    target._edtDosage2 = Utils.findRequiredViewAsType(source, R.id.edt_dosage2, "field '_edtDosage2'", EditText.class);
    target._edtDuration2 = Utils.findRequiredViewAsType(source, R.id.edt_duration2, "field '_edtDuration2'", EditText.class);
    target._edtQuantity2 = Utils.findRequiredViewAsType(source, R.id.edt_quantity2, "field '_edtQuantity2'", EditText.class);
    target._suturingLayout = Utils.findRequiredViewAsType(source, R.id.suturing_layout, "field '_suturingLayout'", LinearLayout.class);
    target._muliautcompltxtSuturing = Utils.findRequiredViewAsType(source, R.id.muliautcompltxt_suturing, "field '_muliautcompltxtSuturing'", MultiAutoCompleteTextView.class);
    target._plasteringLayout = Utils.findRequiredViewAsType(source, R.id.plastering_layout, "field '_plasteringLayout'", LinearLayout.class);
    target._muliautcompltxtPlastering = Utils.findRequiredViewAsType(source, R.id.muliautcompltxt_plastering, "field '_muliautcompltxtPlastering'", MultiAutoCompleteTextView.class);
    target._chkSlab = Utils.findRequiredViewAsType(source, R.id.chk_slab, "field '_chkSlab'", CheckBox.class);
    target._chkCast = Utils.findRequiredViewAsType(source, R.id.chk_cast, "field '_chkCast'", CheckBox.class);
    target._chkInjection = Utils.findRequiredViewAsType(source, R.id.chkbx_injection_layout, "field '_chkInjection'", CheckBox.class);
    target._chkNebulization = Utils.findRequiredViewAsType(source, R.id.chkbx_nebu_layout, "field '_chkNebulization'", CheckBox.class);
    target._chkSuturing = Utils.findRequiredViewAsType(source, R.id.chkbx_suturing_layout, "field '_chkSuturing'", CheckBox.class);
    target._chkPlastering = Utils.findRequiredViewAsType(source, R.id.chkbx_plastering_layout, "field '_chkPlastering'", CheckBox.class);
    target._edtxtvwVisitafter = Utils.findRequiredViewAsType(source, R.id.edtxtvw_visitafter, "field '_edtxtvwVisitafter'", EditText.class);
    target._textView4 = Utils.findRequiredViewAsType(source, R.id.textView4, "field '_textView4'", TextView.class);
    target._edtxtvwVisitedon = Utils.findRequiredViewAsType(source, R.id.edtxtvw_visitedon, "field '_edtxtvwVisitedon'", EditText.class);
    target._textView6 = Utils.findRequiredViewAsType(source, R.id.textView6, "field '_textView6'", TextView.class);
    target._edtxtvwNextvisit = Utils.findRequiredViewAsType(source, R.id.edtxtvw_nextvisit, "field '_edtxtvwNextvisit'", EditText.class);
    target._textView2 = Utils.findRequiredViewAsType(source, R.id.textView2, "field '_textView2'", TextView.class);
    target._edtxtvwRemarks = Utils.findRequiredViewAsType(source, R.id.edtxtvw_remarks, "field '_edtxtvwRemarks'", EditText.class);
    target._textView1 = Utils.findRequiredViewAsType(source, R.id.textView1, "field '_textView1'", TextView.class);
    target._autocompletePharmacyName = Utils.findRequiredViewAsType(source, R.id.autocomplete_pharmacy_name, "field '_autocompletePharmacyName'", AutoCompleteTextView.class);
    target._textView7 = Utils.findRequiredViewAsType(source, R.id.textView7, "field '_textView7'", TextView.class);
    target._autocompletePharmacyAddress = Utils.findRequiredViewAsType(source, R.id.autocomplete_pharmacy_address, "field '_autocompletePharmacyAddress'", AutoCompleteTextView.class);
    target._buttonCancelPrescription = Utils.findRequiredViewAsType(source, R.id.button_cancel_prescription, "field '_buttonCancelPrescription'", Button.class);
    target._buttonSubmitPrescription = Utils.findRequiredViewAsType(source, R.id.button_submit_prescription, "field '_buttonSubmitPrescription'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
    target.SaveMedicineTemplate = Utils.findRequiredViewAsType(source, R.id.txtvw_savemedicine_template, "field 'SaveMedicineTemplate'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MedicinePrescription target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target._medicinePrescriptionParentLayout = null;
    target._medicinePrescriptionNesetedscrollview = null;
    target._upperlayout = null;
    target._imgvwPatientphoto = null;
    target._tvwAgegender = null;
    target._txtvwTitlePatientname = null;
    target._autocompletePatientname = null;
    target._txtvwTreatmentfor = null;
    target._multiautoTreatmentfor = null;
    target._txtvwDiagnosis = null;
    target._multiautoDiagnosis = null;
    target.PreviousMedicineLayout = null;
    target._cardMedicineDetails = null;
    target._txvwMedicineDetailsTitle = null;
    target.imgvw_medicine_options = null;
    target._medicineTextinputlayout = null;
    target.txtview_visiting_title = null;
    target._autocompleteMedicineName = null;
    target._txtviewChooseFrequency = null;
    target._chkbxCheckall = null;
    target._chkbxMorning = null;
    target._txtvwMorning = null;
    target._edttxtMorningFrequency = null;
    target._spinnerMorning = null;
    target._chkbxAfternoon = null;
    target._txtvwAfternoon = null;
    target._edttxtAfternoonFrequency = null;
    target._spinnerAfternoon = null;
    target._chkbxEvening = null;
    target._txtvwEvening = null;
    target._edttxtEvening = null;
    target._spinnerEvening = null;
    target._chkbxNite = null;
    target._textvwNite = null;
    target._edttxtNite = null;
    target._spinnerNite = null;
    target._layoutChooseIntake = null;
    target._textvwChooseIntakeTitle = null;
    target._radiogrpIntakeOptions = null;
    target._radiobtnAfterFood = null;
    target._radiobtnBeforeFood = null;
    target._radiobtnWithFood = null;
    target._layoutChooseDuration = null;
    target._textvwChooseDurationTitle = null;
    target._spinnerDuration = null;
    target._buttonAddMedicine = null;
    target._recyclerMedicineList = null;
    target._injectionLayout = null;
    target._autocmpltxtInjection = null;
    target._edtInjecdosage = null;
    target._edtInjquantity = null;
    target._btnAddinj = null;
    target._injectionRecycler = null;
    target._nebulizationLayout = null;
    target._chkNrmsaline = null;
    target._chkAsthasaline = null;
    target._edtDosage1 = null;
    target._edtDuration1 = null;
    target._edtQuantity1 = null;
    target._edtDosage2 = null;
    target._edtDuration2 = null;
    target._edtQuantity2 = null;
    target._suturingLayout = null;
    target._muliautcompltxtSuturing = null;
    target._plasteringLayout = null;
    target._muliautcompltxtPlastering = null;
    target._chkSlab = null;
    target._chkCast = null;
    target._chkInjection = null;
    target._chkNebulization = null;
    target._chkSuturing = null;
    target._chkPlastering = null;
    target._edtxtvwVisitafter = null;
    target._textView4 = null;
    target._edtxtvwVisitedon = null;
    target._textView6 = null;
    target._edtxtvwNextvisit = null;
    target._textView2 = null;
    target._edtxtvwRemarks = null;
    target._textView1 = null;
    target._autocompletePharmacyName = null;
    target._textView7 = null;
    target._autocompletePharmacyAddress = null;
    target._buttonCancelPrescription = null;
    target._buttonSubmitPrescription = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
    target.SaveMedicineTemplate = null;
  }
}
