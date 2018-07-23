// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Masters_Modules;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Masters_New_ViewBinding implements Unbinder {
  private Masters_New target;

  @UiThread
  public Masters_New_ViewBinding(Masters_New target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Masters_New_ViewBinding(Masters_New target, View source) {
    this.target = target;

    target.content = Utils.findRequiredViewAsType(source, R.id.content, "field 'content'", RelativeLayout.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    target.simpleFrameLayout = Utils.findRequiredViewAsType(source, R.id.simpleFrameLayout, "field 'simpleFrameLayout'", FrameLayout.class);
    target.backDrop = Utils.findRequiredView(source, R.id.back_drop, "field 'backDrop'");
    target.lyt1 = Utils.findRequiredViewAsType(source, R.id.lyt_1, "field 'lyt1'", LinearLayout.class);
    target.fab1 = Utils.findRequiredViewAsType(source, R.id.fab_1, "field 'fab1'", FloatingActionButton.class);
    target.lyt2 = Utils.findRequiredViewAsType(source, R.id.lyt_2, "field 'lyt2'", LinearLayout.class);
    target.fab2 = Utils.findRequiredViewAsType(source, R.id.fab_2, "field 'fab2'", FloatingActionButton.class);
    target.lyt3 = Utils.findRequiredViewAsType(source, R.id.lyt_3, "field 'lyt3'", LinearLayout.class);
    target.fab3 = Utils.findRequiredViewAsType(source, R.id.fab_3, "field 'fab3'", FloatingActionButton.class);
    target.fabAdd = Utils.findRequiredViewAsType(source, R.id.fab_add, "field 'fabAdd'", FloatingActionButton.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.toolbarBack = Utils.findRequiredViewAsType(source, R.id.toolbar_back, "field 'toolbarBack'", AppCompatImageView.class);
    target.toolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'toolbarTitle'", TextView.class);
    target.toolbarExit = Utils.findRequiredViewAsType(source, R.id.toolbar_exit, "field 'toolbarExit'", AppCompatImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Masters_New target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.content = null;
    target.tabLayout = null;
    target.simpleFrameLayout = null;
    target.backDrop = null;
    target.lyt1 = null;
    target.fab1 = null;
    target.lyt2 = null;
    target.fab2 = null;
    target.lyt3 = null;
    target.fab3 = null;
    target.fabAdd = null;
    target.toolbar = null;
    target.toolbarBack = null;
    target.toolbarTitle = null;
    target.toolbarExit = null;
  }
}
