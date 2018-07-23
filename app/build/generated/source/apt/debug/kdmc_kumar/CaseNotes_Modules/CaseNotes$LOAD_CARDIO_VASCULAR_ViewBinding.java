// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.CaseNotes_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class CaseNotes$LOAD_CARDIO_VASCULAR_ViewBinding implements Unbinder {
  private CaseNotes.LOAD_CARDIO_VASCULAR target;

  @UiThread
  public CaseNotes$LOAD_CARDIO_VASCULAR_ViewBinding(CaseNotes.LOAD_CARDIO_VASCULAR target,
      View source) {
    this.target = target;

    target.textViewBeat = Utils.findRequiredViewAsType(source, R.id.textView_beat, "field 'textViewBeat'", TextView.class);
    target.checkBoxS1 = Utils.findRequiredViewAsType(source, R.id.checkBox_s1, "field 'checkBoxS1'", CheckBox.class);
    target.checkBoxS2 = Utils.findRequiredViewAsType(source, R.id.checkBox_s2, "field 'checkBoxS2'", CheckBox.class);
    target.checkBoxS3 = Utils.findRequiredViewAsType(source, R.id.checkBox_s3, "field 'checkBoxS3'", CheckBox.class);
    target.checkBoxS4 = Utils.findRequiredViewAsType(source, R.id.checkBox_s4, "field 'checkBoxS4'", CheckBox.class);
    target.textViewMurmur = Utils.findRequiredViewAsType(source, R.id.textView_murmur, "field 'textViewMurmur'", TextView.class);
    target.checkBoxSystolic = Utils.findRequiredViewAsType(source, R.id.checkBox_systolic, "field 'checkBoxSystolic'", CheckBox.class);
    target.checkBoxDiastolic = Utils.findRequiredViewAsType(source, R.id.checkBox_diastolic, "field 'checkBoxDiastolic'", CheckBox.class);
    target.checkBoxOthers = Utils.findRequiredViewAsType(source, R.id.checkBox_others, "field 'checkBoxOthers'", CheckBox.class);
    target.textViewPericardialrub = Utils.findRequiredViewAsType(source, R.id.textView_pericardialrub, "field 'textViewPericardialrub'", TextView.class);
    target.switchPericardialrub = Utils.findRequiredViewAsType(source, R.id.switch_pericardialrub, "field 'switchPericardialrub'", LabeledSwitch.class);
    target.textViewPulserate = Utils.findRequiredViewAsType(source, R.id.textView_pulserate, "field 'textViewPulserate'", TextView.class);
    target.switchPulserate = Utils.findRequiredViewAsType(source, R.id.switch_pulserate, "field 'switchPulserate'", LabeledSwitch.class);
    target.textViewHeartrate = Utils.findRequiredViewAsType(source, R.id.textView_heartrate, "field 'textViewHeartrate'", TextView.class);
    target.editTextHeartrate = Utils.findRequiredViewAsType(source, R.id.editText_heartrate, "field 'editTextHeartrate'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaseNotes.LOAD_CARDIO_VASCULAR target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewBeat = null;
    target.checkBoxS1 = null;
    target.checkBoxS2 = null;
    target.checkBoxS3 = null;
    target.checkBoxS4 = null;
    target.textViewMurmur = null;
    target.checkBoxSystolic = null;
    target.checkBoxDiastolic = null;
    target.checkBoxOthers = null;
    target.textViewPericardialrub = null;
    target.switchPericardialrub = null;
    target.textViewPulserate = null;
    target.switchPulserate = null;
    target.textViewHeartrate = null;
    target.editTextHeartrate = null;
  }
}
