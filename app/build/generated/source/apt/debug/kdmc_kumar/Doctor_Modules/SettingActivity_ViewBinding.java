// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Doctor_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SettingActivity_ViewBinding implements Unbinder {
  private SettingActivity target;

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SettingActivity_ViewBinding(SettingActivity target, View source) {
    this.target = target;

    target.languageGroup = Utils.findRequiredViewAsType(source, R.id.language_radio, "field 'languageGroup'", RadioGroup.class);
    target.marathiCheck = Utils.findRequiredViewAsType(source, R.id.marathiCheck, "field 'marathiCheck'", RadioButton.class);
    target.englishCheck = Utils.findRequiredViewAsType(source, R.id.englishCheck, "field 'englishCheck'", RadioButton.class);
    target.setttingsParentLayout = Utils.findRequiredViewAsType(source, R.id.setttings_parent_layout, "field 'setttingsParentLayout'", CoordinatorLayout.class);
    target.settingsToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'settingsToolbar'", Toolbar.class);
    target.icBack = Utils.findRequiredViewAsType(source, R.id.ic_back, "field 'icBack'", AppCompatImageView.class);
    target.txvwTitle = Utils.findRequiredViewAsType(source, R.id.txvw_title, "field 'txvwTitle'", TextView.class);
    target.icChngpwd = Utils.findRequiredViewAsType(source, R.id.ic_changepassword, "field 'icChngpwd'", AppCompatImageView.class);
    target.icExit = Utils.findRequiredViewAsType(source, R.id.ic_exit, "field 'icExit'", AppCompatImageView.class);
    target.settingsNesetedscroll = Utils.findRequiredViewAsType(source, R.id.settings_nesetedscroll, "field 'settingsNesetedscroll'", NestedScrollView.class);
    target.settingsCardlayout1 = Utils.findRequiredViewAsType(source, R.id.settings_cardlayout1, "field 'settingsCardlayout1'", LinearLayout.class);
    target.settingsChkbxPinnumber = Utils.findRequiredViewAsType(source, R.id.settings_chkbx_pinnumber, "field 'settingsChkbxPinnumber'", CheckBox.class);
    target.settingsEdtxtPinnumber = Utils.findRequiredViewAsType(source, R.id.settings_edtxt_pinnumber, "field 'settingsEdtxtPinnumber'", EditText.class);
    target.checkboxCasenotesGeneralexamination = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_generalexamination, "field 'checkboxCasenotesGeneralexamination'", CheckBox.class);
    target.checkboxCasenotesCardio = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_cardio, "field 'checkboxCasenotesCardio'", CheckBox.class);
    target.checkboxCasenotesRespiratory = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_respiratory, "field 'checkboxCasenotesRespiratory'", CheckBox.class);
    target.checkboxCasenotesGastrointestinal = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_gastrointestinal, "field 'checkboxCasenotesGastrointestinal'", CheckBox.class);
    target.checkboxCasenotesNeurology = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_neurology, "field 'checkboxCasenotesNeurology'", CheckBox.class);
    target.checkboxCasenotesLocomotor = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_locomotor, "field 'checkboxCasenotesLocomotor'", CheckBox.class);
    target.checkboxCasenotesRenal = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_renal, "field 'checkboxCasenotesRenal'", CheckBox.class);
    target.checkboxCasenotesEndocrine = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_endocrine, "field 'checkboxCasenotesEndocrine'", CheckBox.class);
    target.checkboxCasenotesClinical = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_clinical, "field 'checkboxCasenotesClinical'", CheckBox.class);
    target.checkboxCasenotesPostnatal = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_postnatal, "field 'checkboxCasenotesPostnatal'", CheckBox.class);
    target.checkboxCasenotesDental = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_dental, "field 'checkboxCasenotesDental'", CheckBox.class);
    target.checkboxCasenotesOther = Utils.findRequiredViewAsType(source, R.id.checkbox_casenotes_other, "field 'checkboxCasenotesOther'", CheckBox.class);
    target.settingsTxtvwWorkingdays = Utils.findRequiredViewAsType(source, R.id.settings_txtvw_workingdays, "field 'settingsTxtvwWorkingdays'", TextView.class);
    target.editTextWorkingdays = Utils.findRequiredViewAsType(source, R.id.editText_workingdays, "field 'editTextWorkingdays'", EditText.class);
    target.textviewMrngtimeView = Utils.findRequiredViewAsType(source, R.id.textview_mrngtime_view, "field 'textviewMrngtimeView'", EditText.class);
    target.edittextEvengtimeView = Utils.findRequiredViewAsType(source, R.id.edittext_evengtime_view, "field 'edittextEvengtimeView'", EditText.class);
    target.editTextSlot = Utils.findRequiredViewAsType(source, R.id.editText_slot, "field 'editTextSlot'", EditText.class);
    target.Appointment_Slot = Utils.findRequiredViewAsType(source, R.id.layout_appointment_noofpatients, "field 'Appointment_Slot'", LinearLayout.class);
    target.textViewPreferredpharmacy = Utils.findRequiredViewAsType(source, R.id.textView_preferredpharmacy, "field 'textViewPreferredpharmacy'", TextView.class);
    target.autocompletePrefpharmacy = Utils.findRequiredViewAsType(source, R.id.autocomplete_prefpharmacy, "field 'autocompletePrefpharmacy'", AutoCompleteTextView.class);
    target.prefpharmacyAddress = Utils.findRequiredViewAsType(source, R.id.prefpharmacy_address, "field 'prefpharmacyAddress'", AutoCompleteTextView.class);
    target.prefLabs = Utils.findRequiredViewAsType(source, R.id.pref_labs, "field 'prefLabs'", AutoCompleteTextView.class);
    target.autocompletePrefLabsAddress = Utils.findRequiredViewAsType(source, R.id.autocomplete_pref_labs_address, "field 'autocompletePrefLabsAddress'", AutoCompleteTextView.class);
    target.edittextLetterSpaceHead = Utils.findRequiredViewAsType(source, R.id.edittext_letter_space_head, "field 'edittextLetterSpaceHead'", EditText.class);
    target.textvw_pharmacyname = Utils.findRequiredViewAsType(source, R.id.textvw_pharmacydetails, "field 'textvw_pharmacyname'", TextView.class);
    target.textvw_labname = Utils.findRequiredViewAsType(source, R.id.textvw_laboratorydetails, "field 'textvw_labname'", TextView.class);
    target.Add_WorkingDays = Utils.findRequiredViewAsType(source, R.id.button_add, "field 'Add_WorkingDays'", Button.class);
    target.Cancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'Cancel'", Button.class);
    target.Submit = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'Submit'", Button.class);
    target.CheckAppUpdate = Utils.findRequiredViewAsType(source, R.id.check_app_update, "field 'CheckAppUpdate'", TextView.class);
    target.ServerInfo = Utils.findRequiredViewAsType(source, R.id.server_info, "field 'ServerInfo'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SettingActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.languageGroup = null;
    target.marathiCheck = null;
    target.englishCheck = null;
    target.setttingsParentLayout = null;
    target.settingsToolbar = null;
    target.icBack = null;
    target.txvwTitle = null;
    target.icChngpwd = null;
    target.icExit = null;
    target.settingsNesetedscroll = null;
    target.settingsCardlayout1 = null;
    target.settingsChkbxPinnumber = null;
    target.settingsEdtxtPinnumber = null;
    target.checkboxCasenotesGeneralexamination = null;
    target.checkboxCasenotesCardio = null;
    target.checkboxCasenotesRespiratory = null;
    target.checkboxCasenotesGastrointestinal = null;
    target.checkboxCasenotesNeurology = null;
    target.checkboxCasenotesLocomotor = null;
    target.checkboxCasenotesRenal = null;
    target.checkboxCasenotesEndocrine = null;
    target.checkboxCasenotesClinical = null;
    target.checkboxCasenotesPostnatal = null;
    target.checkboxCasenotesDental = null;
    target.checkboxCasenotesOther = null;
    target.settingsTxtvwWorkingdays = null;
    target.editTextWorkingdays = null;
    target.textviewMrngtimeView = null;
    target.edittextEvengtimeView = null;
    target.editTextSlot = null;
    target.Appointment_Slot = null;
    target.textViewPreferredpharmacy = null;
    target.autocompletePrefpharmacy = null;
    target.prefpharmacyAddress = null;
    target.prefLabs = null;
    target.autocompletePrefLabsAddress = null;
    target.edittextLetterSpaceHead = null;
    target.textvw_pharmacyname = null;
    target.textvw_labname = null;
    target.Add_WorkingDays = null;
    target.Cancel = null;
    target.Submit = null;
    target.CheckAppUpdate = null;
    target.ServerInfo = null;
  }
}
