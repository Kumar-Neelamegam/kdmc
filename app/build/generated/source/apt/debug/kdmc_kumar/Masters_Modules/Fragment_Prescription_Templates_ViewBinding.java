// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Masters_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Fragment_Prescription_Templates_ViewBinding implements Unbinder {
  private Fragment_Prescription_Templates target;

  @UiThread
  public Fragment_Prescription_Templates_ViewBinding(Fragment_Prescription_Templates target,
      View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyler_View, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Fragment_Prescription_Templates target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
  }
}
