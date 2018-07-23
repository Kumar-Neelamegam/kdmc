// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Adapters_GetterSetter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DateTimelineRowitemAdapter$MyViewHolder_ViewBinding implements Unbinder {
  private DateTimelineRowitemAdapter.MyViewHolder target;

  @UiThread
  public DateTimelineRowitemAdapter$MyViewHolder_ViewBinding(DateTimelineRowitemAdapter.MyViewHolder target,
      View source) {
    this.target = target;

    target.txtvwDaymonth = Utils.findRequiredViewAsType(source, R.id.txtvw_daymonth, "field 'txtvwDaymonth'", TextView.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.fab, "field 'fab'", ImageView.class);
    target.txtvwYear = Utils.findRequiredViewAsType(source, R.id.txtvw_year, "field 'txtvwYear'", TextView.class);
    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.linearlayout, "field 'linearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DateTimelineRowitemAdapter.MyViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txtvwDaymonth = null;
    target.fab = null;
    target.txtvwYear = null;
    target.linearLayout = null;
  }
}
