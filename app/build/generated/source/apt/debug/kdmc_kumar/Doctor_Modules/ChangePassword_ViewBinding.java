// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Doctor_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class ChangePassword_ViewBinding implements Unbinder {
  private ChangePassword target;

  @UiThread
  public ChangePassword_ViewBinding(ChangePassword target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public ChangePassword_ViewBinding(ChangePassword target, View source) {
    this.target = target;

    target.parentLayout = Utils.findRequiredViewAsType(source, R.id.parent_layout, "field 'parentLayout'", CoordinatorLayout.class);
    target.toolbarChangepassword = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbarChangepassword'", Toolbar.class);
    target.icBack = Utils.findRequiredViewAsType(source, R.id.ic_back, "field 'icBack'", AppCompatImageView.class);
    target.txvwTitle = Utils.findRequiredViewAsType(source, R.id.txvw_title, "field 'txvwTitle'", TextView.class);
    target.icHome = Utils.findRequiredViewAsType(source, R.id.ic_home, "field 'icHome'", AppCompatImageView.class);
    target.icExit = Utils.findRequiredViewAsType(source, R.id.ic_exit, "field 'icExit'", AppCompatImageView.class);
    target.nestedscrollviewChangepassword = Utils.findRequiredViewAsType(source, R.id.nestedscrollview_changepassword, "field 'nestedscrollviewChangepassword'", NestedScrollView.class);
    target.imgvwDoctorPhoto = Utils.findRequiredViewAsType(source, R.id.imgvw_doctor_photo, "field 'imgvwDoctorPhoto'", CircleImageView.class);
    target.txtvwDoctorname = Utils.findRequiredViewAsType(source, R.id.txtvw_doctorname, "field 'txtvwDoctorname'", TextView.class);
    target.txtvwHospitalname = Utils.findRequiredViewAsType(source, R.id.txtvw_hospitalname, "field 'txtvwHospitalname'", TextView.class);
    target.txtvwSpecialization = Utils.findRequiredViewAsType(source, R.id.txtvw_specialization, "field 'txtvwSpecialization'", TextView.class);
    target.edtUsername = Utils.findRequiredViewAsType(source, R.id.edt_username, "field 'edtUsername'", EditText.class);
    target.edtEntercurrentPassword = Utils.findRequiredViewAsType(source, R.id.edt_entercurrent_password, "field 'edtEntercurrentPassword'", EditText.class);
    target.edtEnterNewPassword = Utils.findRequiredViewAsType(source, R.id.edt_enter_new_password, "field 'edtEnterNewPassword'", EditText.class);
    target.edtEnterConfirmPassword = Utils.findRequiredViewAsType(source, R.id.edt_enter_confirm_password, "field 'edtEnterConfirmPassword'", EditText.class);
    target.Btn_cancel = Utils.findRequiredViewAsType(source, R.id.button_cancel, "field 'Btn_cancel'", Button.class);
    target.Btn_update = Utils.findRequiredViewAsType(source, R.id.button_submit, "field 'Btn_update'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ChangePassword target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentLayout = null;
    target.toolbarChangepassword = null;
    target.icBack = null;
    target.txvwTitle = null;
    target.icHome = null;
    target.icExit = null;
    target.nestedscrollviewChangepassword = null;
    target.imgvwDoctorPhoto = null;
    target.txtvwDoctorname = null;
    target.txtvwHospitalname = null;
    target.txtvwSpecialization = null;
    target.edtUsername = null;
    target.edtEntercurrentPassword = null;
    target.edtEnterNewPassword = null;
    target.edtEnterConfirmPassword = null;
    target.Btn_cancel = null;
    target.Btn_update = null;
  }
}
