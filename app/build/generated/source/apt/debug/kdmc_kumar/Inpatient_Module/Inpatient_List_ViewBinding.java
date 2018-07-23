// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Inpatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Inpatient_List_ViewBinding implements Unbinder {
  private Inpatient_List target;

  @UiThread
  public Inpatient_List_ViewBinding(Inpatient_List target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Inpatient_List_ViewBinding(Inpatient_List target, View source) {
    this.target = target;

    target.inpatientParentLayout = Utils.findRequiredViewAsType(source, R.id.inpatient_parent_layout, "field 'inpatientParentLayout'", CoordinatorLayout.class);
    target.inpatientToolbar = Utils.findRequiredViewAsType(source, R.id.inpatient_toolbar, "field 'inpatientToolbar'", Toolbar.class);
    target.inpatientBack = Utils.findRequiredViewAsType(source, R.id.inpatient_back, "field 'inpatientBack'", AppCompatImageView.class);
    target.txvwTitle = Utils.findRequiredViewAsType(source, R.id.txvw_title, "field 'txvwTitle'", TextView.class);
    target.listgrid = Utils.findRequiredViewAsType(source, R.id.listgrid, "field 'listgrid'", AppCompatImageView.class);
    target.adminRequest = Utils.findRequiredViewAsType(source, R.id.admin_request, "field 'adminRequest'", AppCompatImageView.class);
    target.operationTheater = Utils.findRequiredViewAsType(source, R.id.operation_theater, "field 'operationTheater'", AppCompatImageView.class);
    target.imgvwExit = Utils.findRequiredViewAsType(source, R.id.imgvw_exit, "field 'imgvwExit'", AppCompatImageView.class);
    target.cardvwSearchBar = Utils.findRequiredViewAsType(source, R.id.cardvw_search_bar, "field 'cardvwSearchBar'", CardView.class);
    target.edittextSearch = Utils.findRequiredViewAsType(source, R.id.edittext_search, "field 'edittextSearch'", EditText.class);
    target.btClear = Utils.findRequiredViewAsType(source, R.id.button_clear, "field 'btClear'", ImageButton.class);
    target.btRefresh = Utils.findRequiredViewAsType(source, R.id.button_refresh, "field 'btRefresh'", ImageButton.class);
    target.textvwPatientCount = Utils.findRequiredViewAsType(source, R.id.textvw_patient_count, "field 'textvwPatientCount'", TextView.class);
    target.imagevwNoDataAvailable = Utils.findRequiredViewAsType(source, R.id.imagevw_no_data_available, "field 'imagevwNoDataAvailable'", AppCompatImageView.class);
    target.inpatientFastscrollview = Utils.findRequiredViewAsType(source, R.id.inpatient_fastscrollview, "field 'inpatientFastscrollview'", FastScrollRecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Inpatient_List target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.inpatientParentLayout = null;
    target.inpatientToolbar = null;
    target.inpatientBack = null;
    target.txvwTitle = null;
    target.listgrid = null;
    target.adminRequest = null;
    target.operationTheater = null;
    target.imgvwExit = null;
    target.cardvwSearchBar = null;
    target.edittextSearch = null;
    target.btClear = null;
    target.btRefresh = null;
    target.textvwPatientCount = null;
    target.imagevwNoDataAvailable = null;
    target.inpatientFastscrollview = null;
  }
}
