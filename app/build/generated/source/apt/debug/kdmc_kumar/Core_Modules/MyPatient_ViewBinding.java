// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Core_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyPatient_ViewBinding implements Unbinder {
  private MyPatient target;

  @UiThread
  public MyPatient_ViewBinding(MyPatient target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyPatient_ViewBinding(MyPatient target, View source) {
    this.target = target;

    target.mypatientParentLayout = Utils.findRequiredViewAsType(source, R.id.mypatient_parent_layout, "field 'mypatientParentLayout'", CoordinatorLayout.class);
    target.mypatientToolbar = Utils.findRequiredViewAsType(source, R.id.mypatient_toolbar, "field 'mypatientToolbar'", Toolbar.class);
    target.mypatientBack = Utils.findRequiredViewAsType(source, R.id.mypatient_back, "field 'mypatientBack'", AppCompatImageView.class);
    target.txvwTitle = Utils.findRequiredViewAsType(source, R.id.txvw_title, "field 'txvwTitle'", TextView.class);
    target.mypatientBtnHome = Utils.findRequiredViewAsType(source, R.id.mypatient_btn_home, "field 'mypatientBtnHome'", AppCompatImageView.class);
    target.imgvwListToGrid = Utils.findRequiredViewAsType(source, R.id.imgvw_list_to_grid, "field 'imgvwListToGrid'", AppCompatImageView.class);
    target.imgvwMenuOption = Utils.findRequiredViewAsType(source, R.id.imgvw_menu_option, "field 'imgvwMenuOption'", AppCompatImageView.class);
    target.imgvwExit = Utils.findRequiredViewAsType(source, R.id.imgvw_exit, "field 'imgvwExit'", AppCompatImageView.class);
    target.cardvwSearchBar = Utils.findRequiredViewAsType(source, R.id.cardvw_search_bar, "field 'cardvwSearchBar'", CardView.class);
    target.etSearch = Utils.findRequiredViewAsType(source, R.id.edittext_search, "field 'etSearch'", EditText.class);
    target.btClear = Utils.findRequiredViewAsType(source, R.id.button_clear, "field 'btClear'", ImageButton.class);
    target.btRefresh = Utils.findRequiredViewAsType(source, R.id.button_refresh, "field 'btRefresh'", ImageButton.class);
    target.txtCount = Utils.findRequiredViewAsType(source, R.id.textvw_patient_count, "field 'txtCount'", TextView.class);
    target.imgNoMedia = Utils.findRequiredViewAsType(source, R.id.imagevw_no_data_available, "field 'imgNoMedia'", AppCompatImageView.class);
    target.patient_list = Utils.findRequiredViewAsType(source, R.id.mypatient_fastscrollview, "field 'patient_list'", FastScrollRecyclerView.class);
    target.patientshow_options = Utils.findRequiredViewAsType(source, R.id.radiogroup_patients_options, "field 'patientshow_options'", RadioGroup.class);
    target.radio_today_patients = Utils.findRequiredViewAsType(source, R.id.radio_today_patients, "field 'radio_today_patients'", RadioButton.class);
    target.radio_showall_patients = Utils.findRequiredViewAsType(source, R.id.radio_show_all_patients, "field 'radio_showall_patients'", RadioButton.class);
    target.btnPrev = Utils.findRequiredViewAsType(source, R.id.prev_limit, "field 'btnPrev'", ImageButton.class);
    target.btnNext = Utils.findRequiredViewAsType(source, R.id.next_limit, "field 'btnNext'", ImageButton.class);
    target.curLimit = Utils.findRequiredViewAsType(source, R.id.current_limit, "field 'curLimit'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyPatient target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mypatientParentLayout = null;
    target.mypatientToolbar = null;
    target.mypatientBack = null;
    target.txvwTitle = null;
    target.mypatientBtnHome = null;
    target.imgvwListToGrid = null;
    target.imgvwMenuOption = null;
    target.imgvwExit = null;
    target.cardvwSearchBar = null;
    target.etSearch = null;
    target.btClear = null;
    target.btRefresh = null;
    target.txtCount = null;
    target.imgNoMedia = null;
    target.patient_list = null;
    target.patientshow_options = null;
    target.radio_today_patients = null;
    target.radio_showall_patients = null;
    target.btnPrev = null;
    target.btnNext = null;
    target.curLimit = null;
  }
}
