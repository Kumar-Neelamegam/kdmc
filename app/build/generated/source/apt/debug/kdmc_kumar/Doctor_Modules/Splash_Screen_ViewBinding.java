// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Doctor_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Splash_Screen_ViewBinding implements Unbinder {
  private Splash_Screen target;

  @UiThread
  public Splash_Screen_ViewBinding(Splash_Screen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Splash_Screen_ViewBinding(Splash_Screen target, View source) {
    this.target = target;

    target.top_banner = Utils.findRequiredViewAsType(source, R.id.top_banner_splash, "field 'top_banner'", ImageView.class);
    target.app_logo = Utils.findRequiredViewAsType(source, R.id.app_logo, "field 'app_logo'", ImageView.class);
    target.mLogo = Utils.findRequiredViewAsType(source, R.id.app_title, "field 'mLogo'", TextView.class);
    target.progress_status = Utils.findRequiredViewAsType(source, R.id.textprgrs, "field 'progress_status'", TextView.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Splash_Screen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.top_banner = null;
    target.app_logo = null;
    target.mLogo = null;
    target.progress_status = null;
    target.progressBar = null;
  }
}
