// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Inpatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class Inpatient_Inputs_ViewBinding implements Unbinder {
  private Inpatient_Inputs target;

  private View view2131361966;

  private View view2131361959;

  private View view2131361985;

  private View view2131361984;

  private View view2131361974;

  private View view2131362079;

  private View view2131362081;

  private View view2131362080;

  private View view2131362072;

  private View view2131362071;

  private View view2131362073;

  private View view2131362102;

  private View view2131362101;

  private View view2131362103;

  private View view2131362099;

  private View view2131362098;

  private View view2131362100;

  private View view2131362082;

  private View view2131362084;

  private View view2131362083;

  private View view2131362085;

  @UiThread
  public Inpatient_Inputs_ViewBinding(Inpatient_Inputs target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Inpatient_Inputs_ViewBinding(final Inpatient_Inputs target, View source) {
    this.target = target;

    View view;
    target.clinicalinformationParentLayout = Utils.findRequiredViewAsType(source, R.id.clinicalinformation_parent_layout, "field 'clinicalinformationParentLayout'", CoordinatorLayout.class);
    target.inpatiententryNesetedscrollview = Utils.findRequiredViewAsType(source, R.id.inpatiententry_nesetedscrollview, "field 'inpatiententryNesetedscrollview'", NestedScrollView.class);
    target.upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field 'upperlayout'", LinearLayout.class);
    target.imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field 'imgvwPatientphoto'", CircleImageView.class);
    target.tvwAgegender = Utils.findRequiredViewAsType(source, R.id.tvw_agegender, "field 'tvwAgegender'", TextView.class);
    target.txtPatid = Utils.findRequiredViewAsType(source, R.id.txt_patid, "field 'txtPatid'", TextView.class);
    target.textView12 = Utils.findRequiredViewAsType(source, R.id.textView12, "field 'textView12'", TextView.class);
    target.icomeText = Utils.findRequiredViewAsType(source, R.id.icome_text, "field 'icomeText'", TextView.class);
    target.casteText = Utils.findRequiredViewAsType(source, R.id.caste_text, "field 'casteText'", TextView.class);
    target.caretakerText = Utils.findRequiredViewAsType(source, R.id.caretaker_text, "field 'caretakerText'", TextView.class);
    target.wardNo = Utils.findRequiredViewAsType(source, R.id.ward_no, "field 'wardNo'", TextView.class);
    target.roomNo = Utils.findRequiredViewAsType(source, R.id.room_no, "field 'roomNo'", TextView.class);
    target.bedNo = Utils.findRequiredViewAsType(source, R.id.bed_no, "field 'bedNo'", TextView.class);
    target.textviewWard = Utils.findRequiredViewAsType(source, R.id.textview_ward, "field 'textviewWard'", TextView.class);
    target.textviewRoomnumber = Utils.findRequiredViewAsType(source, R.id.textview_roomnumber, "field 'textviewRoomnumber'", TextView.class);
    target.textviewBednumber = Utils.findRequiredViewAsType(source, R.id.textview_bednumber, "field 'textviewBednumber'", TextView.class);
    target.textviewAdmitteddate = Utils.findRequiredViewAsType(source, R.id.textview_admitteddate, "field 'textviewAdmitteddate'", TextView.class);
    target.edittextInaptientchartDate = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_date, "field 'edittextInaptientchartDate'", EditText.class);
    target.edittextInaptientchartTime = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_time, "field 'edittextInaptientchartTime'", EditText.class);
    target.edittextInaptientchartBpsytolic = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_bpsytolic, "field 'edittextInaptientchartBpsytolic'", EditText.class);
    target.edittextInaptientchartBpdiastolic = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_bpdiastolic, "field 'edittextInaptientchartBpdiastolic'", EditText.class);
    target.edittextInaptientchartPulseperminute = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_pulseperminute, "field 'edittextInaptientchartPulseperminute'", EditText.class);
    target.spinnerInaptientchartTemperature = Utils.findRequiredViewAsType(source, R.id.spinner_inaptientchart_temperature, "field 'spinnerInaptientchartTemperature'", Spinner.class);
    target.edittextInaptientchartResp = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_resp, "field 'edittextInaptientchartResp'", EditText.class);
    target.edittextInaptientchartSpo2 = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_spo2, "field 'edittextInaptientchartSpo2'", EditText.class);
    target.edittextInaptientchartDrugorder = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_drugorder, "field 'edittextInaptientchartDrugorder'", EditText.class);
    target.edittextInaptientchartNursingInstruction = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_nursing_instruction, "field 'edittextInaptientchartNursingInstruction'", EditText.class);
    target.edittextInaptientchartOral = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_oral, "field 'edittextInaptientchartOral'", EditText.class);
    target.edittextInaptientchartFluids = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_fluids, "field 'edittextInaptientchartFluids'", EditText.class);
    target.edittextInaptientchartRvles = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_rvles, "field 'edittextInaptientchartRvles'", EditText.class);
    target.edittextInaptientchartMotion = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_motion, "field 'edittextInaptientchartMotion'", EditText.class);
    target.edittextInaptientchartUrine = Utils.findRequiredViewAsType(source, R.id.edittext_inaptientchart_urine, "field 'edittextInaptientchartUrine'", EditText.class);
    target.edittextDiabetichartDate = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_date, "field 'edittextDiabetichartDate'", EditText.class);
    target.edittextDiabetichartTime = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_time, "field 'edittextDiabetichartTime'", EditText.class);
    target.edittextDiabetichartSpecialiInstruction = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_speciali_instruction, "field 'edittextDiabetichartSpecialiInstruction'", EditText.class);
    target.autoCompleteTextViewDiabetichartUrineSugar = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_diabetichart_urine_sugar, "field 'autoCompleteTextViewDiabetichartUrineSugar'", EditText.class);
    target.edittextDiabetichartLente = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_lente, "field 'edittextDiabetichartLente'", EditText.class);
    target.edittextDiabetichartInsulinPlain = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_insulin_plain, "field 'edittextDiabetichartInsulinPlain'", EditText.class);
    target.edittextDiabetichartBloodSugar = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_blood_sugar, "field 'edittextDiabetichartBloodSugar'", EditText.class);
    target.edittextDiabetichartKetoneBodies = Utils.findRequiredViewAsType(source, R.id.edittext_diabetichart_ketone_bodies, "field 'edittextDiabetichartKetoneBodies'", EditText.class);
    target.edittextTemperaturechartDate = Utils.findRequiredViewAsType(source, R.id.edittext_temperaturechart_date, "field 'edittextTemperaturechartDate'", EditText.class);
    target.edittextTemperaturechartTime = Utils.findRequiredViewAsType(source, R.id.edittext_temperaturechart_time, "field 'edittextTemperaturechartTime'", EditText.class);
    target.spinnerTemperaturechartSelecttemperature = Utils.findRequiredViewAsType(source, R.id.spinner_temperaturechart_selecttemperature, "field 'spinnerTemperaturechartSelecttemperature'", Spinner.class);
    target.edittextTemperaturechartVisitSummary = Utils.findRequiredViewAsType(source, R.id.edittext_temperaturechart_visit_summary, "field 'edittextTemperaturechartVisitSummary'", EditText.class);
    target.edittextSurgeryDate = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_date, "field 'edittextSurgeryDate'", EditText.class);
    target.edittextSurgeryPreoperativeDiagnosis = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_preoperative_diagnosis, "field 'edittextSurgeryPreoperativeDiagnosis'", EditText.class);
    target.editextSurgeryOperativenotes = Utils.findRequiredViewAsType(source, R.id.editext_surgery_operativenotes, "field 'editextSurgeryOperativenotes'", EditText.class);
    target.edittextSurgeryPosition = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_position, "field 'edittextSurgeryPosition'", EditText.class);
    target.edittextSurgeryProcedure = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_procedure, "field 'edittextSurgeryProcedure'", EditText.class);
    target.edittextSurgeryClosure = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_closure, "field 'edittextSurgeryClosure'", EditText.class);
    target.edittextSurgeryPostoperativeDiagnosis = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_postoperative_diagnosis, "field 'edittextSurgeryPostoperativeDiagnosis'", EditText.class);
    target.edittextSurgerySurgeon = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_surgeon, "field 'edittextSurgerySurgeon'", EditText.class);
    target.edittextSurgeryAnaesthetist = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_anaesthetist, "field 'edittextSurgeryAnaesthetist'", EditText.class);
    target.edittextSurgeryAsst = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_asst, "field 'edittextSurgeryAsst'", EditText.class);
    target.edittextSurgeryBloodLoss = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_blood_loss, "field 'edittextSurgeryBloodLoss'", EditText.class);
    target.edittextSurgeryHistoPathological = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_histo_pathological, "field 'edittextSurgeryHistoPathological'", EditText.class);
    target.edittextSurgeryPostOpInstruction = Utils.findRequiredViewAsType(source, R.id.edittext_surgery_post_op_instruction, "field 'edittextSurgeryPostOpInstruction'", EditText.class);
    target.spinnerMedicalcaseRecordUndercareof = Utils.findRequiredViewAsType(source, R.id.Spinner_medicalcase_record_undercareof, "field 'spinnerMedicalcaseRecordUndercareof'", Spinner.class);
    target.edittextMedicalcaseRecordStudent = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_record_student, "field 'edittextMedicalcaseRecordStudent'", EditText.class);
    target.autocompleteMedicalcaseProvisionalDiagnosis = Utils.findRequiredViewAsType(source, R.id.autocomplete_medicalcase_provisional_diagnosis, "field 'autocompleteMedicalcaseProvisionalDiagnosis'", AutoCompleteTextView.class);
    target.edittextMedicalcaseFinalDiagnosis = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_final_diagnosis, "field 'edittextMedicalcaseFinalDiagnosis'", EditText.class);
    target.spinnerMedicalcaseSelectResult = Utils.findRequiredViewAsType(source, R.id.spinner_medicalcase_select_result, "field 'spinnerMedicalcaseSelectResult'", Spinner.class);
    target.edittextMedicalcaseDate = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_date, "field 'edittextMedicalcaseDate'", EditText.class);
    target.edittextMedicalcaseTime = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_time, "field 'edittextMedicalcaseTime'", EditText.class);
    target.edittextMedicalcaseClinicalnotes = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_clinicalnotes, "field 'edittextMedicalcaseClinicalnotes'", EditText.class);
    target.edittextMedicalcaseTreatementAndDiet = Utils.findRequiredViewAsType(source, R.id.edittext_medicalcase_treatement_and_diet, "field 'edittextMedicalcaseTreatementAndDiet'", EditText.class);
    target.inpatientchart_primary = Utils.findRequiredViewAsType(source, R.id.inpatientchart_primary, "field 'inpatientchart_primary'", LinearLayout.class);
    target.diabetic_primary = Utils.findRequiredViewAsType(source, R.id.diabetic_primary, "field 'diabetic_primary'", LinearLayout.class);
    target.temperature_primary = Utils.findRequiredViewAsType(source, R.id.temperature_primary, "field 'temperature_primary'", LinearLayout.class);
    target.surgery_primary = Utils.findRequiredViewAsType(source, R.id.surgery_primary, "field 'surgery_primary'", LinearLayout.class);
    target.medicalcase_primary = Utils.findRequiredViewAsType(source, R.id.medicalcase_primary, "field 'medicalcase_primary'", LinearLayout.class);
    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyler_View, "field 'recyclerView'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
    view = Utils.findRequiredView(source, R.id.arrow_inpatient_chart, "method 'onArrowInpatientChartClick'");
    view2131361966 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowInpatientChartClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.arrow_diabetic_chart, "method 'onArrowDiabeticChartClick'");
    view2131361959 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowDiabeticChartClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.arrow_temperature_chart, "method 'onArrowTemperatureChartClick'");
    view2131361985 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowTemperatureChartClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.arrow_surgery_record, "method 'onArrowSurgeryRecordClick'");
    view2131361984 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowSurgeryRecordClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.arrow_medicalcase_record, "method 'onArrowMedicalcaseRecordClick'");
    view2131361974 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onArrowMedicalcaseRecordClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_inaptientchart_clear, "method 'onButtonInaptientchartClearClick'");
    view2131362079 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonInaptientchartClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_inaptientchart_save, "method 'onButtonInaptientchartSaveClick'");
    view2131362081 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonInaptientchartSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_inaptientchart_hide, "method 'onButtonInaptientchartHideClick'");
    view2131362080 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonInaptientchartHideClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_diabetichart_hide, "method 'onButtonDiabetichartHideClick'");
    view2131362072 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonDiabetichartHideClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_diabetichart_clear, "method 'onButtonDiabetichartClearClick'");
    view2131362071 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonDiabetichartClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_diabetichart_save, "method 'onButtonDiabetichartSaveClick'");
    view2131362073 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonDiabetichartSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_temperaturechart_hide, "method 'onButtonTemperaturechartHideClick'");
    view2131362102 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonTemperaturechartHideClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_temperaturechart_clear, "method 'onButtonTemperaturechartClearClick'");
    view2131362101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonTemperaturechartClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_temperaturechart_save, "method 'onButtonTemperaturechartSaveClick'");
    view2131362103 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonTemperaturechartSaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_surgery_hide, "method 'onButtonSurgeryHideClick'");
    view2131362099 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonSurgeryHideClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_surgery_clear, "method 'onButtonSurgeryClearClick'");
    view2131362098 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonSurgeryClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_surgery_save, "method 'onButtonSurgerySaveClick'");
    view2131362100 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonSurgerySaveClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_medicalcase_add, "method 'onButtonMedicalcaseAddClick'");
    view2131362082 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonMedicalcaseAddClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_medicalcase_hide, "method 'onButtonMedicalcaseHideClick'");
    view2131362084 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonMedicalcaseHideClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_medicalcase_clear, "method 'onButtonMedicalcaseClearClick'");
    view2131362083 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonMedicalcaseClearClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.button_medicalcase_save, "method 'onButtonMedicalcaseSaveClick'");
    view2131362085 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonMedicalcaseSaveClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    Inpatient_Inputs target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.clinicalinformationParentLayout = null;
    target.inpatiententryNesetedscrollview = null;
    target.upperlayout = null;
    target.imgvwPatientphoto = null;
    target.tvwAgegender = null;
    target.txtPatid = null;
    target.textView12 = null;
    target.icomeText = null;
    target.casteText = null;
    target.caretakerText = null;
    target.wardNo = null;
    target.roomNo = null;
    target.bedNo = null;
    target.textviewWard = null;
    target.textviewRoomnumber = null;
    target.textviewBednumber = null;
    target.textviewAdmitteddate = null;
    target.edittextInaptientchartDate = null;
    target.edittextInaptientchartTime = null;
    target.edittextInaptientchartBpsytolic = null;
    target.edittextInaptientchartBpdiastolic = null;
    target.edittextInaptientchartPulseperminute = null;
    target.spinnerInaptientchartTemperature = null;
    target.edittextInaptientchartResp = null;
    target.edittextInaptientchartSpo2 = null;
    target.edittextInaptientchartDrugorder = null;
    target.edittextInaptientchartNursingInstruction = null;
    target.edittextInaptientchartOral = null;
    target.edittextInaptientchartFluids = null;
    target.edittextInaptientchartRvles = null;
    target.edittextInaptientchartMotion = null;
    target.edittextInaptientchartUrine = null;
    target.edittextDiabetichartDate = null;
    target.edittextDiabetichartTime = null;
    target.edittextDiabetichartSpecialiInstruction = null;
    target.autoCompleteTextViewDiabetichartUrineSugar = null;
    target.edittextDiabetichartLente = null;
    target.edittextDiabetichartInsulinPlain = null;
    target.edittextDiabetichartBloodSugar = null;
    target.edittextDiabetichartKetoneBodies = null;
    target.edittextTemperaturechartDate = null;
    target.edittextTemperaturechartTime = null;
    target.spinnerTemperaturechartSelecttemperature = null;
    target.edittextTemperaturechartVisitSummary = null;
    target.edittextSurgeryDate = null;
    target.edittextSurgeryPreoperativeDiagnosis = null;
    target.editextSurgeryOperativenotes = null;
    target.edittextSurgeryPosition = null;
    target.edittextSurgeryProcedure = null;
    target.edittextSurgeryClosure = null;
    target.edittextSurgeryPostoperativeDiagnosis = null;
    target.edittextSurgerySurgeon = null;
    target.edittextSurgeryAnaesthetist = null;
    target.edittextSurgeryAsst = null;
    target.edittextSurgeryBloodLoss = null;
    target.edittextSurgeryHistoPathological = null;
    target.edittextSurgeryPostOpInstruction = null;
    target.spinnerMedicalcaseRecordUndercareof = null;
    target.edittextMedicalcaseRecordStudent = null;
    target.autocompleteMedicalcaseProvisionalDiagnosis = null;
    target.edittextMedicalcaseFinalDiagnosis = null;
    target.spinnerMedicalcaseSelectResult = null;
    target.edittextMedicalcaseDate = null;
    target.edittextMedicalcaseTime = null;
    target.edittextMedicalcaseClinicalnotes = null;
    target.edittextMedicalcaseTreatementAndDiet = null;
    target.inpatientchart_primary = null;
    target.diabetic_primary = null;
    target.temperature_primary = null;
    target.surgery_primary = null;
    target.medicalcase_primary = null;
    target.recyclerView = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;

    view2131361966.setOnClickListener(null);
    view2131361966 = null;
    view2131361959.setOnClickListener(null);
    view2131361959 = null;
    view2131361985.setOnClickListener(null);
    view2131361985 = null;
    view2131361984.setOnClickListener(null);
    view2131361984 = null;
    view2131361974.setOnClickListener(null);
    view2131361974 = null;
    view2131362079.setOnClickListener(null);
    view2131362079 = null;
    view2131362081.setOnClickListener(null);
    view2131362081 = null;
    view2131362080.setOnClickListener(null);
    view2131362080 = null;
    view2131362072.setOnClickListener(null);
    view2131362072 = null;
    view2131362071.setOnClickListener(null);
    view2131362071 = null;
    view2131362073.setOnClickListener(null);
    view2131362073 = null;
    view2131362102.setOnClickListener(null);
    view2131362102 = null;
    view2131362101.setOnClickListener(null);
    view2131362101 = null;
    view2131362103.setOnClickListener(null);
    view2131362103 = null;
    view2131362099.setOnClickListener(null);
    view2131362099 = null;
    view2131362098.setOnClickListener(null);
    view2131362098 = null;
    view2131362100.setOnClickListener(null);
    view2131362100 = null;
    view2131362082.setOnClickListener(null);
    view2131362082 = null;
    view2131362084.setOnClickListener(null);
    view2131362084 = null;
    view2131362083.setOnClickListener(null);
    view2131362083 = null;
    view2131362085.setOnClickListener(null);
    view2131362085 = null;
  }
}
