// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Inpatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Inpatient_Inputs$MedicalCaseViewHolder_ViewBinding implements Unbinder {
  private Inpatient_Inputs.MedicalCaseViewHolder target;

  @UiThread
  public Inpatient_Inputs$MedicalCaseViewHolder_ViewBinding(Inpatient_Inputs.MedicalCaseViewHolder target,
      View source) {
    this.target = target;

    target.dateMc = Utils.findRequiredViewAsType(source, R.id.date_mc, "field 'dateMc'", TextView.class);
    target.clinicalNotesValue = Utils.findRequiredViewAsType(source, R.id.clinical_notes_value, "field 'clinicalNotesValue'", TextView.class);
    target.treatmentDietValue = Utils.findRequiredViewAsType(source, R.id.treatment_diet_value, "field 'treatmentDietValue'", TextView.class);
    target.delete = Utils.findRequiredViewAsType(source, R.id.ic_delete, "field 'delete'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Inpatient_Inputs.MedicalCaseViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.dateMc = null;
    target.clinicalNotesValue = null;
    target.treatmentDietValue = null;
    target.delete = null;
  }
}
