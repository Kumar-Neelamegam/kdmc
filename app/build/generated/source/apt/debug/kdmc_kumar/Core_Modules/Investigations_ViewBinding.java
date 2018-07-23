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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class Investigations_ViewBinding implements Unbinder {
  private Investigations target;

  @UiThread
  public Investigations_ViewBinding(Investigations target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Investigations_ViewBinding(Investigations target, View source) {
    this.target = target;

    target.investigationParentLayout = Utils.findRequiredViewAsType(source, R.id.investigation_parent_layout, "field 'investigationParentLayout'", CoordinatorLayout.class);
    target.investigationNesetedscrollview = Utils.findRequiredViewAsType(source, R.id.investigation_nesetedscrollview, "field 'investigationNesetedscrollview'", NestedScrollView.class);
    target.upperlayout = Utils.findRequiredViewAsType(source, R.id.upperlayout, "field 'upperlayout'", LinearLayout.class);
    target.imgvwPatientphoto = Utils.findRequiredViewAsType(source, R.id.imgvw_patientphoto, "field 'imgvwPatientphoto'", CircleImageView.class);
    target.tvwAgegender = Utils.findRequiredViewAsType(source, R.id.tvw_agegender, "field 'tvwAgegender'", TextView.class);
    target.txtvwTitlePatientname = Utils.findRequiredViewAsType(source, R.id.txtvw_title_patientname, "field 'txtvwTitlePatientname'", TextView.class);
    target.autocompletePatientname = Utils.findRequiredViewAsType(source, R.id.autocomplete_patientname, "field 'autocompletePatientname'", AutoCompleteTextView.class);
    target.txtvwTreatmentfor = Utils.findRequiredViewAsType(source, R.id.txtvw_treatmentfor, "field 'txtvwTreatmentfor'", TextView.class);
    target.multiautoTreatmentfor = Utils.findRequiredViewAsType(source, R.id.multiauto_treatmentfor, "field 'multiautoTreatmentfor'", MultiAutoCompleteTextView.class);
    target.txtvwDiagnosis = Utils.findRequiredViewAsType(source, R.id.txtvw_diagnosis, "field 'txtvwDiagnosis'", TextView.class);
    target.multiautoDiagnosis = Utils.findRequiredViewAsType(source, R.id.multiauto_diagnosis, "field 'multiautoDiagnosis'", MultiAutoCompleteTextView.class);
    target.cardTest = Utils.findRequiredViewAsType(source, R.id.card_test, "field 'cardTest'", CardView.class);
    target.arrowTest = Utils.findRequiredViewAsType(source, R.id.arrow_test, "field 'arrowTest'", ImageButton.class);
    target.primaryInvestigationTest = Utils.findRequiredViewAsType(source, R.id.primary_investigation_test, "field 'primaryInvestigationTest'", LinearLayout.class);
    target.buttonMyPreferredTestlist = Utils.findRequiredViewAsType(source, R.id.button_my_preferred_testlist, "field 'buttonMyPreferredTestlist'", Button.class);
    target.buttonAllTest = Utils.findRequiredViewAsType(source, R.id.button_all_test, "field 'buttonAllTest'", Button.class);
    target.txtvwTotaltestcount = Utils.findRequiredViewAsType(source, R.id.txtvw_totaltestcount, "field 'txtvwTotaltestcount'", TextView.class);
    target.hideInvestigationTest = Utils.findRequiredViewAsType(source, R.id.hide_investigation_test, "field 'hideInvestigationTest'", Button.class);
    target.arrowInvestigationScan = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_scan, "field 'arrowInvestigationScan'", ImageButton.class);
    target.primaryInvestigationScan = Utils.findRequiredViewAsType(source, R.id.primary_investigation_scan, "field 'primaryInvestigationScan'", LinearLayout.class);
    target.spinnerScan = Utils.findRequiredViewAsType(source, R.id.spinner_scan, "field 'spinnerScan'", Spinner.class);
    target.scanforEdt = Utils.findRequiredViewAsType(source, R.id.scanfor_edt, "field 'scanforEdt'", MultiAutoCompleteTextView.class);
    target.buttonScanforadd = Utils.findRequiredViewAsType(source, R.id.button_scanforadd, "field 'buttonScanforadd'", Button.class);
    target.hideInvestigationScan = Utils.findRequiredViewAsType(source, R.id.hide_investigation_scan, "field 'hideInvestigationScan'", Button.class);
    target.arrowInvestigationXray = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_xray, "field 'arrowInvestigationXray'", ImageButton.class);
    target.primaryInvestigationXray = Utils.findRequiredViewAsType(source, R.id.primary_investigation_xray, "field 'primaryInvestigationXray'", LinearLayout.class);
    target.multiautoXray = Utils.findRequiredViewAsType(source, R.id.multiauto_xray, "field 'multiautoXray'", AutoCompleteTextView.class);
    target.buttonXrayadd = Utils.findRequiredViewAsType(source, R.id.button_xrayadd, "field 'buttonXrayadd'", Button.class);
    target.listviewInvestigationXray = Utils.findRequiredViewAsType(source, R.id.listview_investigation_xray, "field 'listviewInvestigationXray'", ListView.class);
    target.hideInvestigationXray = Utils.findRequiredViewAsType(source, R.id.hide_investigation_xray, "field 'hideInvestigationXray'", Button.class);
    target.arrowInvestigationEcg = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_ecg, "field 'arrowInvestigationEcg'", ImageButton.class);
    target.primaryInvestigationEcg = Utils.findRequiredViewAsType(source, R.id.primary_investigation_ecg, "field 'primaryInvestigationEcg'", LinearLayout.class);
    target.checkboxEcgOption1 = Utils.findRequiredViewAsType(source, R.id.checkbox_ecg_option1, "field 'checkboxEcgOption1'", CheckBox.class);
    target.checkboxEcgOption2 = Utils.findRequiredViewAsType(source, R.id.checkbox_ecg_option2, "field 'checkboxEcgOption2'", CheckBox.class);
    target.edtEcgComments = Utils.findRequiredViewAsType(source, R.id.edt_ecg_comments, "field 'edtEcgComments'", EditText.class);
    target.arrowInvestigationEeg = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_eeg, "field 'arrowInvestigationEeg'", ImageButton.class);
    target.primaryInvestigationEeg = Utils.findRequiredViewAsType(source, R.id.primary_investigation_eeg, "field 'primaryInvestigationEeg'", LinearLayout.class);
    target.checkboxEegOption1 = Utils.findRequiredViewAsType(source, R.id.checkbox_eeg_option1, "field 'checkboxEegOption1'", CheckBox.class);
    target.edtEegComment = Utils.findRequiredViewAsType(source, R.id.edt_eeg_comment, "field 'edtEegComment'", EditText.class);
    target.hideInvestigationEeg = Utils.findRequiredViewAsType(source, R.id.hide_investigation_eeg, "field 'hideInvestigationEeg'", Button.class);
    target.hideInvestigationEcg = Utils.findRequiredViewAsType(source, R.id.hide_investigation_ecg, "field 'hideInvestigationEcg'", Button.class);
    target.arrowInvestigationAngiogram = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_angiogram, "field 'arrowInvestigationAngiogram'", ImageButton.class);
    target.primaryInvestigationAngio = Utils.findRequiredViewAsType(source, R.id.primary_investigation_angio, "field 'primaryInvestigationAngio'", LinearLayout.class);
    target.primaryInvestigationPreferred = Utils.findRequiredViewAsType(source, R.id.primary_preflayout, "field 'primaryInvestigationPreferred'", LinearLayout.class);
    target.checkboxAngioCoronary = Utils.findRequiredViewAsType(source, R.id.checkbox_angio_coronary, "field 'checkboxAngioCoronary'", CheckBox.class);
    target.checkboxAngioBrain = Utils.findRequiredViewAsType(source, R.id.checkbox_angio_brain, "field 'checkboxAngioBrain'", CheckBox.class);
    target.checkboxAngioLowerlimb = Utils.findRequiredViewAsType(source, R.id.checkbox_angio_lowerlimb, "field 'checkboxAngioLowerlimb'", CheckBox.class);
    target.checkboxAngioMesenteric = Utils.findRequiredViewAsType(source, R.id.checkbox_angio_mesenteric, "field 'checkboxAngioMesenteric'", CheckBox.class);
    target.checkboxAngioUpperlimb = Utils.findRequiredViewAsType(source, R.id.checkbox_angio_upperlimb, "field 'checkboxAngioUpperlimb'", CheckBox.class);
    target.edtAngioComments = Utils.findRequiredViewAsType(source, R.id.edt_angio_comments, "field 'edtAngioComments'", EditText.class);
    target.hideInvestigationAngio = Utils.findRequiredViewAsType(source, R.id.hide_investigation_angio, "field 'hideInvestigationAngio'", Button.class);
    target.arrowInvestigationPrefadddiagcentre = Utils.findRequiredViewAsType(source, R.id.arrow_investigation_prefadddiagcentre, "field 'arrowInvestigationPrefadddiagcentre'", ImageButton.class);
    target.autocompleteDiagcenterName = Utils.findRequiredViewAsType(source, R.id.autocomplete_diagcenter_name, "field 'autocompleteDiagcenterName'", AutoCompleteTextView.class);
    target.autocompleteDiagcenterAddress = Utils.findRequiredViewAsType(source, R.id.autocomplete_diagcenter_address, "field 'autocompleteDiagcenterAddress'", AutoCompleteTextView.class);
    target.setasdefault = Utils.findRequiredViewAsType(source, R.id.setasdefault, "field 'setasdefault'", CheckBox.class);
    target.hidePrefdiagcenterinfo = Utils.findRequiredViewAsType(source, R.id.hide_prefdiagcenterinfo, "field 'hidePrefdiagcenterinfo'", Button.class);
    target.buttonCancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'buttonCancel'", Button.class);
    target.buttonSkiptoprescription = Utils.findRequiredViewAsType(source, R.id.button_skiptoprescription, "field 'buttonSkiptoprescription'", Button.class);
    target.buttonSubmit = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'buttonSubmit'", Button.class);
    target.recylerView_test = Utils.findRequiredViewAsType(source, R.id.recylerView_test, "field 'recylerView_test'", RecyclerView.class);
    target.recylerView_scan = Utils.findRequiredViewAsType(source, R.id.recylerView_scan, "field 'recylerView_scan'", RecyclerView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Investigations target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.investigationParentLayout = null;
    target.investigationNesetedscrollview = null;
    target.upperlayout = null;
    target.imgvwPatientphoto = null;
    target.tvwAgegender = null;
    target.txtvwTitlePatientname = null;
    target.autocompletePatientname = null;
    target.txtvwTreatmentfor = null;
    target.multiautoTreatmentfor = null;
    target.txtvwDiagnosis = null;
    target.multiautoDiagnosis = null;
    target.cardTest = null;
    target.arrowTest = null;
    target.primaryInvestigationTest = null;
    target.buttonMyPreferredTestlist = null;
    target.buttonAllTest = null;
    target.txtvwTotaltestcount = null;
    target.hideInvestigationTest = null;
    target.arrowInvestigationScan = null;
    target.primaryInvestigationScan = null;
    target.spinnerScan = null;
    target.scanforEdt = null;
    target.buttonScanforadd = null;
    target.hideInvestigationScan = null;
    target.arrowInvestigationXray = null;
    target.primaryInvestigationXray = null;
    target.multiautoXray = null;
    target.buttonXrayadd = null;
    target.listviewInvestigationXray = null;
    target.hideInvestigationXray = null;
    target.arrowInvestigationEcg = null;
    target.primaryInvestigationEcg = null;
    target.checkboxEcgOption1 = null;
    target.checkboxEcgOption2 = null;
    target.edtEcgComments = null;
    target.arrowInvestigationEeg = null;
    target.primaryInvestigationEeg = null;
    target.checkboxEegOption1 = null;
    target.edtEegComment = null;
    target.hideInvestigationEeg = null;
    target.hideInvestigationEcg = null;
    target.arrowInvestigationAngiogram = null;
    target.primaryInvestigationAngio = null;
    target.primaryInvestigationPreferred = null;
    target.checkboxAngioCoronary = null;
    target.checkboxAngioBrain = null;
    target.checkboxAngioLowerlimb = null;
    target.checkboxAngioMesenteric = null;
    target.checkboxAngioUpperlimb = null;
    target.edtAngioComments = null;
    target.hideInvestigationAngio = null;
    target.arrowInvestigationPrefadddiagcentre = null;
    target.autocompleteDiagcenterName = null;
    target.autocompleteDiagcenterAddress = null;
    target.setasdefault = null;
    target.hidePrefdiagcenterinfo = null;
    target.buttonCancel = null;
    target.buttonSkiptoprescription = null;
    target.buttonSubmit = null;
    target.recylerView_test = null;
    target.recylerView_scan = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
