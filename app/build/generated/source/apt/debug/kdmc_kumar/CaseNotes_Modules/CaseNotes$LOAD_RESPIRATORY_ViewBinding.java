// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.CaseNotes_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CaseNotes$LOAD_RESPIRATORY_ViewBinding implements Unbinder {
  private CaseNotes.LOAD_RESPIRATORY target;

  @UiThread
  public CaseNotes$LOAD_RESPIRATORY_ViewBinding(CaseNotes.LOAD_RESPIRATORY target, View source) {
    this.target = target;

    target.textViewBreathsound = Utils.findRequiredViewAsType(source, R.id.textView_breathsound, "field 'textViewBreathsound'", TextView.class);
    target.radiogroupBreathsoundequal = Utils.findRequiredViewAsType(source, R.id.radiogroup_breathsoundequal, "field 'radiogroupBreathsoundequal'", RadioGroup.class);
    target.rbtnYes = Utils.findRequiredViewAsType(source, R.id.rbtn_yes, "field 'rbtnYes'", RadioButton.class);
    target.rbtnNo = Utils.findRequiredViewAsType(source, R.id.rbtn_no, "field 'rbtnNo'", RadioButton.class);
    target.radiogroupBreathsoundequalOptns = Utils.findRequiredViewAsType(source, R.id.radiogroup_breathsoundequal_optns, "field 'radiogroupBreathsoundequalOptns'", RadioGroup.class);
    target.rrbtnDecreaseonLeft = Utils.findRequiredViewAsType(source, R.id.rrbtn_DecreaseonLeft, "field 'rrbtnDecreaseonLeft'", RadioButton.class);
    target.rrbtnDecreaseonright = Utils.findRequiredViewAsType(source, R.id.rrbtn_Decreaseonright, "field 'rrbtnDecreaseonright'", RadioButton.class);
    target.textViewBreathSound = Utils.findRequiredViewAsType(source, R.id.textView_breath_sound, "field 'textViewBreathSound'", TextView.class);
    target.spinnerBreathSound = Utils.findRequiredViewAsType(source, R.id.spinner_breath_sound, "field 'spinnerBreathSound'", Spinner.class);
    target.textViewTrachea = Utils.findRequiredViewAsType(source, R.id.textView_trachea, "field 'textViewTrachea'", TextView.class);
    target.spinner_Trachea = Utils.findRequiredViewAsType(source, R.id.spinner2_trachea, "field 'spinner_Trachea'", Spinner.class);
    target.checkBoxKyphosis = Utils.findRequiredViewAsType(source, R.id.checkBox_kyphosis, "field 'checkBoxKyphosis'", CheckBox.class);
    target.checkBoxScoliosis = Utils.findRequiredViewAsType(source, R.id.checkBox_scoliosis, "field 'checkBoxScoliosis'", CheckBox.class);
    target.textViewCrepitation = Utils.findRequiredViewAsType(source, R.id.textView_crepitation, "field 'textViewCrepitation'", TextView.class);
    target.checkBoxLeft = Utils.findRequiredViewAsType(source, R.id.checkBox_left, "field 'checkBoxLeft'", CheckBox.class);
    target.checkBoxRight = Utils.findRequiredViewAsType(source, R.id.checkBox_right, "field 'checkBoxRight'", CheckBox.class);
    target.textViewRhonchi = Utils.findRequiredViewAsType(source, R.id.textView_rhonchi, "field 'textViewRhonchi'", TextView.class);
    target.checkBoxRhonchiLeft = Utils.findRequiredViewAsType(source, R.id.checkBox_rhonchi_left, "field 'checkBoxRhonchiLeft'", CheckBox.class);
    target.checkBoxRhonchiRight = Utils.findRequiredViewAsType(source, R.id.checkBox__rhonchi_right, "field 'checkBoxRhonchiRight'", CheckBox.class);
    target.textViewShapeOfTheChest = Utils.findRequiredViewAsType(source, R.id.TextView_shape_of_the_chest, "field 'textViewShapeOfTheChest'", TextView.class);
    target.spinnerShapeOfTheChest = Utils.findRequiredViewAsType(source, R.id.spinner_shape_of_the_chest, "field 'spinnerShapeOfTheChest'", Spinner.class);
    target.textViewRespiratoryRate = Utils.findRequiredViewAsType(source, R.id.textView_respiratory_rate, "field 'textViewRespiratoryRate'", TextView.class);
    target.editTextRespiratoryRate = Utils.findRequiredViewAsType(source, R.id.editText_respiratory_rate, "field 'editTextRespiratoryRate'", EditText.class);
    target.textViewSpo2 = Utils.findRequiredViewAsType(source, R.id.textView_spo2, "field 'textViewSpo2'", TextView.class);
    target.autoCompleteTextViewSpo2 = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_spo2, "field 'autoCompleteTextViewSpo2'", AutoCompleteTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaseNotes.LOAD_RESPIRATORY target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewBreathsound = null;
    target.radiogroupBreathsoundequal = null;
    target.rbtnYes = null;
    target.rbtnNo = null;
    target.radiogroupBreathsoundequalOptns = null;
    target.rrbtnDecreaseonLeft = null;
    target.rrbtnDecreaseonright = null;
    target.textViewBreathSound = null;
    target.spinnerBreathSound = null;
    target.textViewTrachea = null;
    target.spinner_Trachea = null;
    target.checkBoxKyphosis = null;
    target.checkBoxScoliosis = null;
    target.textViewCrepitation = null;
    target.checkBoxLeft = null;
    target.checkBoxRight = null;
    target.textViewRhonchi = null;
    target.checkBoxRhonchiLeft = null;
    target.checkBoxRhonchiRight = null;
    target.textViewShapeOfTheChest = null;
    target.spinnerShapeOfTheChest = null;
    target.textViewRespiratoryRate = null;
    target.editTextRespiratoryRate = null;
    target.textViewSpo2 = null;
    target.autoCompleteTextViewSpo2 = null;
  }
}
