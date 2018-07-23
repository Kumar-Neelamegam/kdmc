// Generated code from Butter Knife. Do not modify!
package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import displ.mobydocmarathi.com.R;
import java.lang.IllegalStateException;
import java.lang.Override;
import kdmc_kumar.Utilities_Others.NotificationBadge;

public class Dashboard_NavigationMenu_ViewBinding implements Unbinder {
  private Dashboard_NavigationMenu target;

  @UiThread
  public Dashboard_NavigationMenu_ViewBinding(Dashboard_NavigationMenu target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Dashboard_NavigationMenu_ViewBinding(Dashboard_NavigationMenu target, View source) {
    this.target = target;

    target.dashboardDrawerLayout = Utils.findRequiredViewAsType(source, R.id.dashboard_drawer_layout, "field 'dashboardDrawerLayout'", DrawerLayout.class);
    target.dashboardToolbar = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar, "field 'dashboardToolbar'", Toolbar.class);
    target.dashboardToolbarTxvwTitle = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_txvw_title, "field 'dashboardToolbarTxvwTitle'", TextView.class);
    target.dashboardToolbarIcCloud = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_ic_cloud, "field 'dashboardToolbarIcCloud'", AppCompatImageView.class);
    target.dashboardToolbarCloudNotificationbatch = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_cloud_notificationbatch, "field 'dashboardToolbarCloudNotificationbatch'", NotificationBadge.class);
    target.dashboardToolbarIcNotification = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_ic_notification, "field 'dashboardToolbarIcNotification'", AppCompatImageView.class);
    target.dashboardToolbarOperationsNotificationbatch = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_operations_notificationbatch, "field 'dashboardToolbarOperationsNotificationbatch'", NotificationBadge.class);
    target.dashboardToolbarIcSettings = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_ic_settings, "field 'dashboardToolbarIcSettings'", AppCompatImageView.class);
    target.dashboardToolbarIcExit = Utils.findRequiredViewAsType(source, R.id.dashboard_toolbar_ic_exit, "field 'dashboardToolbarIcExit'", AppCompatImageView.class);
    target.dashboardLeftDrawer = Utils.findRequiredViewAsType(source, R.id.dashboard_left_drawer, "field 'dashboardLeftDrawer'", ListView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Dashboard_NavigationMenu target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.dashboardDrawerLayout = null;
    target.dashboardToolbar = null;
    target.dashboardToolbarTxvwTitle = null;
    target.dashboardToolbarIcCloud = null;
    target.dashboardToolbarCloudNotificationbatch = null;
    target.dashboardToolbarIcNotification = null;
    target.dashboardToolbarOperationsNotificationbatch = null;
    target.dashboardToolbarIcSettings = null;
    target.dashboardToolbarIcExit = null;
    target.dashboardLeftDrawer = null;
  }
}
