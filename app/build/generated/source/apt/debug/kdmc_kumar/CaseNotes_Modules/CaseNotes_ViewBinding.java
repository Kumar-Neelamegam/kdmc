// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.CaseNotes_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class CaseNotes_ViewBinding implements Unbinder {
  private CaseNotes target;

  @UiThread
  public CaseNotes_ViewBinding(CaseNotes target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CaseNotes_ViewBinding(CaseNotes target, View source) {
    this.target = target;

    target.appbar_layout = Utils.findRequiredViewAsType(source, R.id.appbar_layout, "field 'appbar_layout'", AppBarLayout.class);
    target.casenotesParentLayout = Utils.findRequiredViewAsType(source, R.id.casenotes_parent_layout, "field 'casenotesParentLayout'", CoordinatorLayout.class);
    target.casenotesNestedscrollview = Utils.findRequiredViewAsType(source, R.id.casenotes_nestedscrollview, "field 'casenotesNestedscrollview'", NestedScrollView.class);
    target.upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field 'upperlayout'", LinearLayout.class);
    target.imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field 'imgvwPatientphoto'", CircleImageView.class);
    target.tvwAgegender = Utils.findRequiredViewAsType(source, R.id.tvw_agegender, "field 'tvwAgegender'", TextView.class);
    target.txtvwTitlePatientname = Utils.findRequiredViewAsType(source, R.id.txtvw_title_patientname, "field 'txtvwTitlePatientname'", TextView.class);
    target.autocompletePatientname = Utils.findRequiredViewAsType(source, R.id.autocomplete_patientname, "field 'autocompletePatientname'", AutoCompleteTextView.class);
    target.txtvwTreatmentfor = Utils.findRequiredViewAsType(source, R.id.txtvw_treatmentfor, "field 'txtvwTreatmentfor'", TextView.class);
    target.multiautoTreatmentfor = Utils.findRequiredViewAsType(source, R.id.multiauto_treatmentfor, "field 'multiautoTreatmentfor'", MultiAutoCompleteTextView.class);
    target.txtvwSigns = Utils.findRequiredViewAsType(source, R.id.txtvw_signs, "field 'txtvwSigns'", TextView.class);
    target.multiautoSigns = Utils.findRequiredViewAsType(source, R.id.multiauto_signs, "field 'multiautoSigns'", MultiAutoCompleteTextView.class);
    target.txtvwDiagnosis = Utils.findRequiredViewAsType(source, R.id.txtvw_diagnosis, "field 'txtvwDiagnosis'", TextView.class);
    target.multiautoDiagnosis = Utils.findRequiredViewAsType(source, R.id.multiauto_diagnosis, "field 'multiautoDiagnosis'", MultiAutoCompleteTextView.class);
    target.upperParent = Utils.findRequiredViewAsType(source, R.id.upper_parent, "field 'upperParent'", LinearLayout.class);
    target.autocompltWeight = Utils.findRequiredViewAsType(source, R.id.autocomplt_weight, "field 'autocompltWeight'", AutoCompleteTextView.class);
    target.autocompltHeight = Utils.findRequiredViewAsType(source, R.id.autocomplt_height, "field 'autocompltHeight'", AutoCompleteTextView.class);
    target.autocompltBmi = Utils.findRequiredViewAsType(source, R.id.autocomplt_bmi, "field 'autocompltBmi'", AutoCompleteTextView.class);
    target.autocompltTemperature = Utils.findRequiredViewAsType(source, R.id.autocomplt_temperature, "field 'autocompltTemperature'", AutoCompleteTextView.class);
    target.autocompltBps = Utils.findRequiredViewAsType(source, R.id.autocomplt_bps, "field 'autocompltBps'", AutoCompleteTextView.class);
    target.autocompltBpd = Utils.findRequiredViewAsType(source, R.id.autocomplt_bpd, "field 'autocompltBpd'", AutoCompleteTextView.class);
    target.autocompltFbs = Utils.findRequiredViewAsType(source, R.id.autocomplt_fbs, "field 'autocompltFbs'", AutoCompleteTextView.class);
    target.autocompltPpbs = Utils.findRequiredViewAsType(source, R.id.autocomplt_ppbs, "field 'autocompltPpbs'", AutoCompleteTextView.class);
    target.autocompltRbs = Utils.findRequiredViewAsType(source, R.id.autocomplt_rbs, "field 'autocompltRbs'", AutoCompleteTextView.class);
    target.allCasenotesLayouts = Utils.findRequiredViewAsType(source, R.id.all_casenotes_layouts, "field 'allCasenotesLayouts'", LinearLayout.class);
    target.contentLayout = Utils.findRequiredViewAsType(source, R.id.contentLayout, "field 'contentLayout'", LinearLayout.class);
    target.imgvwBloodsugarBar = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodsugar_bar, "field 'imgvwBloodsugarBar'", AppCompatImageView.class);
    target.imgvwBloodsugarLine = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodsugar_line, "field 'imgvwBloodsugarLine'", AppCompatImageView.class);
    target.imgvwBloodpressureBar = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodpressure_bar, "field 'imgvwBloodpressureBar'", AppCompatImageView.class);
    target.imgvwBloodpressureLine = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodpressure_line, "field 'imgvwBloodpressureLine'", AppCompatImageView.class);
    target.imgvwBloodweightBar = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodweight_bar, "field 'imgvwBloodweightBar'", AppCompatImageView.class);
    target.imgvwBloodweightLine = Utils.findRequiredViewAsType(source, R.id.imgvw_bloodweight_line, "field 'imgvwBloodweightLine'", AppCompatImageView.class);
    target.buttonCancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'buttonCancel'", Button.class);
    target.buttonReportUpload = Utils.findRequiredViewAsType(source, R.id.button_report_upload, "field 'buttonReportUpload'", Button.class);
    target.buttonSubmit = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'buttonSubmit'", Button.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaseNotes target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.appbar_layout = null;
    target.casenotesParentLayout = null;
    target.casenotesNestedscrollview = null;
    target.upperlayout = null;
    target.imgvwPatientphoto = null;
    target.tvwAgegender = null;
    target.txtvwTitlePatientname = null;
    target.autocompletePatientname = null;
    target.txtvwTreatmentfor = null;
    target.multiautoTreatmentfor = null;
    target.txtvwSigns = null;
    target.multiautoSigns = null;
    target.txtvwDiagnosis = null;
    target.multiautoDiagnosis = null;
    target.upperParent = null;
    target.autocompltWeight = null;
    target.autocompltHeight = null;
    target.autocompltBmi = null;
    target.autocompltTemperature = null;
    target.autocompltBps = null;
    target.autocompltBpd = null;
    target.autocompltFbs = null;
    target.autocompltPpbs = null;
    target.autocompltRbs = null;
    target.allCasenotesLayouts = null;
    target.contentLayout = null;
    target.imgvwBloodsugarBar = null;
    target.imgvwBloodsugarLine = null;
    target.imgvwBloodpressureBar = null;
    target.imgvwBloodpressureLine = null;
    target.imgvwBloodweightBar = null;
    target.imgvwBloodweightLine = null;
    target.buttonCancel = null;
    target.buttonReportUpload = null;
    target.buttonSubmit = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
