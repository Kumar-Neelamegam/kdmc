// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.CaseNotes_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class CaseNotes$LOAD_RENAL_ViewBinding implements Unbinder {
  private CaseNotes.LOAD_RENAL target;

  @UiThread
  public CaseNotes$LOAD_RENAL_ViewBinding(CaseNotes.LOAD_RENAL target, View source) {
    this.target = target;

    target.textViewDysuria = Utils.findRequiredViewAsType(source, R.id.textView_dysuria, "field 'textViewDysuria'", TextView.class);
    target.switchDysuria1 = Utils.findRequiredViewAsType(source, R.id.switch_dysuria1, "field 'switchDysuria1'", LabeledSwitch.class);
    target.textViewPyuria = Utils.findRequiredViewAsType(source, R.id.textView_pyuria, "field 'textViewPyuria'", TextView.class);
    target.switchPyuria1 = Utils.findRequiredViewAsType(source, R.id.switch_pyuria1, "field 'switchPyuria1'", LabeledSwitch.class);
    target.textViewOliguria = Utils.findRequiredViewAsType(source, R.id.textView_oliguria, "field 'textViewOliguria'", TextView.class);
    target.switchOliguria1 = Utils.findRequiredViewAsType(source, R.id.switch_oliguria1, "field 'switchOliguria1'", LabeledSwitch.class);
    target.textViewPolyuria = Utils.findRequiredViewAsType(source, R.id.textView_polyuria, "field 'textViewPolyuria'", TextView.class);
    target.switchPolyuria1 = Utils.findRequiredViewAsType(source, R.id.switch_polyuria1, "field 'switchPolyuria1'", LabeledSwitch.class);
    target.textViewAnuria = Utils.findRequiredViewAsType(source, R.id.textView_anuria, "field 'textViewAnuria'", TextView.class);
    target.switchAnuria1 = Utils.findRequiredViewAsType(source, R.id.switch_anuria1, "field 'switchAnuria1'", LabeledSwitch.class);
    target.textViewNocturia = Utils.findRequiredViewAsType(source, R.id.textView_nocturia, "field 'textViewNocturia'", TextView.class);
    target.switchNocturia1 = Utils.findRequiredViewAsType(source, R.id.switch_nocturia1, "field 'switchNocturia1'", LabeledSwitch.class);
    target.textViewUrethralDischarge = Utils.findRequiredViewAsType(source, R.id.textView_urethral_discharge, "field 'textViewUrethralDischarge'", TextView.class);
    target.switchUrethraldischarge1 = Utils.findRequiredViewAsType(source, R.id.switch_urethraldischarge1, "field 'switchUrethraldischarge1'", LabeledSwitch.class);
    target.textViewProstate = Utils.findRequiredViewAsType(source, R.id.textView_prostate, "field 'textViewProstate'", TextView.class);
    target.switchProstate = Utils.findRequiredViewAsType(source, R.id.switch_prostate, "field 'switchProstate'", LabeledSwitch.class);
    target.textViewHaematuria = Utils.findRequiredViewAsType(source, R.id.textView_haematuria, "field 'textViewHaematuria'", TextView.class);
    target.spinnerHaematuria = Utils.findRequiredViewAsType(source, R.id.spinner_haematuria, "field 'spinnerHaematuria'", Spinner.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaseNotes.LOAD_RENAL target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textViewDysuria = null;
    target.switchDysuria1 = null;
    target.textViewPyuria = null;
    target.switchPyuria1 = null;
    target.textViewOliguria = null;
    target.switchOliguria1 = null;
    target.textViewPolyuria = null;
    target.switchPolyuria1 = null;
    target.textViewAnuria = null;
    target.switchAnuria1 = null;
    target.textViewNocturia = null;
    target.switchNocturia1 = null;
    target.textViewUrethralDischarge = null;
    target.switchUrethraldischarge1 = null;
    target.textViewProstate = null;
    target.switchProstate = null;
    target.textViewHaematuria = null;
    target.spinnerHaematuria = null;
  }
}
