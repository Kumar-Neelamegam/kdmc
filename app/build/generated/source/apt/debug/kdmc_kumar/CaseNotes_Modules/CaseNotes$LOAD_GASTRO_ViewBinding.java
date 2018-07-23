// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.CaseNotes_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class CaseNotes$LOAD_GASTRO_ViewBinding implements Unbinder {
  private CaseNotes.LOAD_GASTRO target;

  @UiThread
  public CaseNotes$LOAD_GASTRO_ViewBinding(CaseNotes.LOAD_GASTRO target, View source) {
    this.target = target;

    target.textViewShapeOfTheAbdomen = Utils.findRequiredViewAsType(source, R.id.textView_shape_of_the_abdomen, "field 'textViewShapeOfTheAbdomen'", TextView.class);
    target.spinnerShapeOfTheAbdomen = Utils.findRequiredViewAsType(source, R.id.spinner_shape_of_the_abdomen, "field 'spinnerShapeOfTheAbdomen'", Spinner.class);
    target.textViewAbdomen = Utils.findRequiredViewAsType(source, R.id.textView_abdomen, "field 'textViewAbdomen'", TextView.class);
    target.buttonAbdomen = Utils.findRequiredViewAsType(source, R.id.abdon_select, "field 'buttonAbdomen'", Button.class);
    target.listView1 = Utils.findRequiredViewAsType(source, R.id.listView1, "field 'listView1'", ListView.class);
    target.textViewVisiblePulsation1 = Utils.findRequiredViewAsType(source, R.id.textView_visible_pulsation1, "field 'textViewVisiblePulsation1'", TextView.class);
    target.switchVisiblePulsation1 = Utils.findRequiredViewAsType(source, R.id.switch_visible_pulsation1, "field 'switchVisiblePulsation1'", LabeledSwitch.class);
    target.textViewSwitchVisiblePerist1 = Utils.findRequiredViewAsType(source, R.id.textView_switch_visible_perist1, "field 'textViewSwitchVisiblePerist1'", TextView.class);
    target.switchVisiblePerist1 = Utils.findRequiredViewAsType(source, R.id.switch_visible_perist1, "field 'switchVisiblePerist1'", LabeledSwitch.class);
    target.textViewAbdominalPalpation = Utils.findRequiredViewAsType(source, R.id.textView_abdominal_palpation, "field 'textViewAbdominalPalpation'", TextView.class);
    target.spinnerAbdominalPalpation = Utils.findRequiredViewAsType(source, R.id.spinner_abdominal_palpation, "field 'spinnerAbdominalPalpation'", Spinner.class);
    target.textViewSplenomegaly = Utils.findRequiredViewAsType(source, R.id.textView_splenomegaly, "field 'textViewSplenomegaly'", TextView.class);
    target.autoCompleteTextViewSplenomegaly = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_splenomegaly, "field 'autoCompleteTextViewSplenomegaly'", AutoCompleteTextView.class);
    target.textViewHepatomegaly = Utils.findRequiredViewAsType(source, R.id.textView_hepatomegaly, "field 'textViewHepatomegaly'", TextView.class);
    target.autoCompleteTextViewHepatomegaly = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_hepatomegaly, "field 'autoCompleteTextViewHepatomegaly'", AutoCompleteTextView.class);
    target.textViewPalpableMasses = Utils.findRequiredViewAsType(source, R.id.textView_palpable_masses, "field 'textViewPalpableMasses'", TextView.class);
    target.autoCompleteTextViewPalpableMasses = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_palpable_masses, "field 'autoCompleteTextViewPalpableMasses'", AutoCompleteTextView.class);
    target.textViewBowelSounds = Utils.findRequiredViewAsType(source, R.id.textView_bowel_sounds, "field 'textViewBowelSounds'", TextView.class);
    target.autoCompleteTextViewBowelSounds = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_bowel_sounds, "field 'autoCompleteTextViewBowelSounds'", AutoCompleteTextView.class);
    target.textViewHernial = Utils.findRequiredViewAsType(source, R.id.textView_hernial, "field 'textViewHernial'", TextView.class);
    target.checkBoxOrifices = Utils.findRequiredViewAsType(source, R.id.checkBox_orifices, "field 'checkBoxOrifices'", CheckBox.class);
    target.checkBoxFree = Utils.findRequiredViewAsType(source, R.id.checkBox_free, "field 'checkBoxFree'", CheckBox.class);
    target.textView = Utils.findRequiredViewAsType(source, R.id.textView, "field 'textView'", TextView.class);
    target.spinner3 = Utils.findRequiredViewAsType(source, R.id.spinner3, "field 'spinner3'", Spinner.class);
    target.textViewSpleen = Utils.findRequiredViewAsType(source, R.id.textView_spleen, "field 'textViewSpleen'", TextView.class);
    target.switchSpleen = Utils.findRequiredViewAsType(source, R.id.switch_spleen, "field 'switchSpleen'", LabeledSwitch.class);
    target.textView16 = Utils.findRequiredViewAsType(source, R.id.textView16, "field 'textView16'", TextView.class);
    target.switchLiverNormal = Utils.findRequiredViewAsType(source, R.id.switch_liver_normal, "field 'switchLiverNormal'", LabeledSwitch.class);
    target.textViewOrganomegely = Utils.findRequiredViewAsType(source, R.id.textView_organomegely, "field 'textViewOrganomegely'", TextView.class);
    target.autoCompleteTextViewOrganomegely = Utils.findRequiredViewAsType(source, R.id.AutoCompleteTextView_organomegely, "field 'autoCompleteTextViewOrganomegely'", AutoCompleteTextView.class);
    target.textViewFreeFluidInTheAbdomen = Utils.findRequiredViewAsType(source, R.id.textView_free_fluid_in_the_abdomen, "field 'textViewFreeFluidInTheAbdomen'", TextView.class);
    target.autoCompleteTextViewFreeFluidInTheAbdomen = Utils.findRequiredViewAsType(source, R.id.AutoCompleteTextView_free_fluid_in_the_abdomen, "field 'autoCompleteTextViewFreeFluidInTheAbdomen'", AutoCompleteTextView.class);
    target.textViewDistension = Utils.findRequiredViewAsType(source, R.id.textView_distension, "field 'textViewDistension'", TextView.class);
    target.autoCompleteTextViewDistension = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_distension, "field 'autoCompleteTextViewDistension'", AutoCompleteTextView.class);
    target.textViewTenderness = Utils.findRequiredViewAsType(source, R.id.textView_tenderness, "field 'textViewTenderness'", TextView.class);
    target.autoCompleteTextViewTenderness = Utils.findRequiredViewAsType(source, R.id.autoCompleteTextView_tenderness, "field 'autoCompleteTextViewTenderness'", AutoCompleteTextView.class);
    target.textViewScrotum = Utils.findRequiredViewAsType(source, R.id.textView_scrotum, "field 'textViewScrotum'", TextView.class);
    target.autoCompleteTextViewScrotum = Utils.findRequiredViewAsType(source, R.id.AutoCompleteTextView_scrotum, "field 'autoCompleteTextViewScrotum'", AutoCompleteTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaseNotes.LOAD_GASTRO target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewShapeOfTheAbdomen = null;
    target.spinnerShapeOfTheAbdomen = null;
    target.textViewAbdomen = null;
    target.buttonAbdomen = null;
    target.listView1 = null;
    target.textViewVisiblePulsation1 = null;
    target.switchVisiblePulsation1 = null;
    target.textViewSwitchVisiblePerist1 = null;
    target.switchVisiblePerist1 = null;
    target.textViewAbdominalPalpation = null;
    target.spinnerAbdominalPalpation = null;
    target.textViewSplenomegaly = null;
    target.autoCompleteTextViewSplenomegaly = null;
    target.textViewHepatomegaly = null;
    target.autoCompleteTextViewHepatomegaly = null;
    target.textViewPalpableMasses = null;
    target.autoCompleteTextViewPalpableMasses = null;
    target.textViewBowelSounds = null;
    target.autoCompleteTextViewBowelSounds = null;
    target.textViewHernial = null;
    target.checkBoxOrifices = null;
    target.checkBoxFree = null;
    target.textView = null;
    target.spinner3 = null;
    target.textViewSpleen = null;
    target.switchSpleen = null;
    target.textView16 = null;
    target.switchLiverNormal = null;
    target.textViewOrganomegely = null;
    target.autoCompleteTextViewOrganomegely = null;
    target.textViewFreeFluidInTheAbdomen = null;
    target.autoCompleteTextViewFreeFluidInTheAbdomen = null;
    target.textViewDistension = null;
    target.autoCompleteTextViewDistension = null;
    target.textViewTenderness = null;
    target.autoCompleteTextViewTenderness = null;
    target.textViewScrotum = null;
    target.autoCompleteTextViewScrotum = null;
  }
}
