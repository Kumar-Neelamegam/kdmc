// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.MyPatient_Module;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MyPatientDrawer_ViewBinding implements Unbinder {
  private MyPatientDrawer target;

  @UiThread
  public MyPatientDrawer_ViewBinding(MyPatientDrawer target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MyPatientDrawer_ViewBinding(MyPatientDrawer target, View source) {
    this.target = target;

    target.drawerLayout = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawerLayout'", DrawerLayout.class);
    target.toolbarMypatient = Utils.findRequiredViewAsType(source, R.id.toolbar_mypatient, "field 'toolbarMypatient'", Toolbar.class);
    target.txvwTitle = Utils.findRequiredViewAsType(source, R.id.txvw_title, "field 'txvwTitle'", TextView.class);
    target.backMp = Utils.findRequiredViewAsType(source, R.id.back_mp, "field 'backMp'", AppCompatImageView.class);
    target.homeMp = Utils.findRequiredViewAsType(source, R.id.home_mp, "field 'homeMp'", AppCompatImageView.class);
    target.exitMp = Utils.findRequiredViewAsType(source, R.id.exit_mp, "field 'exitMp'", AppCompatImageView.class);
    target.navView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navView'", NavigationView.class);
    target.frame = Utils.findRequiredViewAsType(source, R.id.frame, "field 'frame'", FrameLayout.class);
    target.backDrop = Utils.findRequiredView(source, R.id.back_drop, "field 'backDrop'");
    target.lytInpatient = Utils.findRequiredViewAsType(source, R.id.lyt_inpatient, "field 'lytInpatient'", LinearLayout.class);
    target.fabAdmitinpatient = Utils.findRequiredViewAsType(source, R.id.fab_admitinpatient, "field 'fabAdmitinpatient'", FloatingActionButton.class);
    target.lytClinicalinfo = Utils.findRequiredViewAsType(source, R.id.lyt_clinicalinfo, "field 'lytClinicalinfo'", LinearLayout.class);
    target.fabClinicalinfo = Utils.findRequiredViewAsType(source, R.id.fab_clinicalinfo, "field 'fabClinicalinfo'", FloatingActionButton.class);
    target.lytCasenotes = Utils.findRequiredViewAsType(source, R.id.lyt_casenotes, "field 'lytCasenotes'", LinearLayout.class);
    target.fabCasenote = Utils.findRequiredViewAsType(source, R.id.fab_casenote, "field 'fabCasenote'", FloatingActionButton.class);
    target.lytInvestigation = Utils.findRequiredViewAsType(source, R.id.lyt_investigation, "field 'lytInvestigation'", LinearLayout.class);
    target.fabInvestigation = Utils.findRequiredViewAsType(source, R.id.fab_investigation, "field 'fabInvestigation'", FloatingActionButton.class);
    target.lytPrescription = Utils.findRequiredViewAsType(source, R.id.lyt_Prescription, "field 'lytPrescription'", LinearLayout.class);
    target.fabPrescription = Utils.findRequiredViewAsType(source, R.id.fab_Prescription, "field 'fabPrescription'", FloatingActionButton.class);
    target.fabAdd = Utils.findRequiredViewAsType(source, R.id.fab_add, "field 'fabAdd'", FloatingActionButton.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MyPatientDrawer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.drawerLayout = null;
    target.toolbarMypatient = null;
    target.txvwTitle = null;
    target.backMp = null;
    target.homeMp = null;
    target.exitMp = null;
    target.navView = null;
    target.frame = null;
    target.backDrop = null;
    target.lytInpatient = null;
    target.fabAdmitinpatient = null;
    target.lytClinicalinfo = null;
    target.fabClinicalinfo = null;
    target.lytCasenotes = null;
    target.fabCasenote = null;
    target.lytInvestigation = null;
    target.fabInvestigation = null;
    target.lytPrescription = null;
    target.fabPrescription = null;
    target.fabAdd = null;
  }
}
