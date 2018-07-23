// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Doctor_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Login_ViewBinding implements Unbinder {
  private Login target;

  @UiThread
  public Login_ViewBinding(Login target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Login_ViewBinding(Login target, View source) {
    this.target = target;

    target.parentLayout = Utils.findRequiredViewAsType(source, R.id.parent_layout, "field 'parentLayout'", CoordinatorLayout.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.exit = Utils.findRequiredViewAsType(source, R.id.ic_exit, "field 'exit'", ImageView.class);
    target.serverImage = Utils.findRequiredViewAsType(source, R.id.help_img2, "field 'serverImage'", ImageView.class);
    target.netwrk = Utils.findRequiredViewAsType(source, R.id.help_img6, "field 'netwrk'", ImageView.class);
    target.DoctorPhoto = Utils.findRequiredViewAsType(source, R.id.imgvw_doctorphoto1, "field 'DoctorPhoto'", ImageView.class);
    target.WelcomeTitle = Utils.findRequiredViewAsType(source, R.id.title_welcome1, "field 'WelcomeTitle'", TextView.class);
    target.Field_Username = Utils.findRequiredViewAsType(source, R.id.txtdocname, "field 'Field_Username'", AutoCompleteTextView.class);
    target.Field_Password = Utils.findRequiredViewAsType(source, R.id.txtpname, "field 'Field_Password'", EditText.class);
    target.remember = Utils.findRequiredViewAsType(source, R.id.checkBox1, "field 'remember'", CheckBox.class);
    target.forgotpassword = Utils.findRequiredViewAsType(source, R.id.txt_frgtpswd, "field 'forgotpassword'", TextView.class);
    target.passwordlayout = Utils.findRequiredViewAsType(source, R.id.pinlayout, "field 'passwordlayout'", LinearLayout.class);
    target.loginpinnum = Utils.findRequiredViewAsType(source, R.id.editText1, "field 'loginpinnum'", EditText.class);
    target.login = Utils.findRequiredViewAsType(source, R.id.button4, "field 'login'", Button.class);
    target.LoginCard = Utils.findRequiredViewAsType(source, R.id.LoginCard, "field 'LoginCard'", CardView.class);
    target.Logo = Utils.findRequiredViewAsType(source, R.id.moby, "field 'Logo'", ImageView.class);
    target.Title_Username = Utils.findRequiredViewAsType(source, R.id.txtvw_title_username, "field 'Title_Username'", TextView.class);
    target.Title_Password = Utils.findRequiredViewAsType(source, R.id.txtvw_title_password, "field 'Title_Password'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Login target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.parentLayout = null;
    target.toolbar = null;
    target.exit = null;
    target.serverImage = null;
    target.netwrk = null;
    target.DoctorPhoto = null;
    target.WelcomeTitle = null;
    target.Field_Username = null;
    target.Field_Password = null;
    target.remember = null;
    target.forgotpassword = null;
    target.passwordlayout = null;
    target.loginpinnum = null;
    target.login = null;
    target.LoginCard = null;
    target.Logo = null;
    target.Title_Username = null;
    target.Title_Password = null;
  }
}
