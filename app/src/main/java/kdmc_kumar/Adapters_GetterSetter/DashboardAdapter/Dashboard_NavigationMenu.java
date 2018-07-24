package kdmc_kumar.Adapters_GetterSetter.DashboardAdapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DrawerItemCustomAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.CloudData;
import kdmc_kumar.Doctor_Modules.Doctor_Profile;
import kdmc_kumar.Doctor_Modules.Login;
import kdmc_kumar.Doctor_Modules.SettingActivity;
import kdmc_kumar.Inpatient_Module.OperationTheater;
import kdmc_kumar.Notification.NotificationActivity;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.NotificationBadge;


public class Dashboard_NavigationMenu extends AppCompatActivity {


    public static int flag = 0;
    private final String dt = BaseConfig.timedate;
    private final ArrayList<NotificationClass> notificationList = new ArrayList<>();
    private final ArrayList<NotificationClass> notificationListTest = new ArrayList<>();
    public String Text = "";
    int COUNT_PATIENT_REGISTRATION = 0;
    int COUNT_CLINICAL_INFORMATION = 0;
    int COUNT_CASENOTES = 0;
    int COUNT_INVESTIGATION = 0;
    int COUNT_PRESCRIPTION = 0;
    @BindView(R.id.dashboard_drawer_layout)
    DrawerLayout dashboardDrawerLayout;
    @BindView(R.id.dashboard_toolbar)
    Toolbar dashboardToolbar;
    @BindView(R.id.dashboard_toolbar_txvw_title)
    TextView dashboardToolbarTxvwTitle;
    @BindView(R.id.dashboard_toolbar_ic_cloud)
    AppCompatImageView dashboardToolbarIcCloud;
    @BindView(R.id.dashboard_toolbar_cloud_notificationbatch)
    NotificationBadge dashboardToolbarCloudNotificationbatch;
    @BindView(R.id.dashboard_toolbar_ic_notification)
    AppCompatImageView dashboardToolbarIcNotification;
    @BindView(R.id.dashboard_toolbar_operations_notificationbatch)
    NotificationBadge dashboardToolbarOperationsNotificationbatch;
    @BindView(R.id.dashboard_toolbar_ic_settings)
    AppCompatImageView dashboardToolbarIcSettings;
    @BindView(R.id.dashboard_toolbar_ic_exit)
    AppCompatImageView dashboardToolbarIcExit;

    @BindView(R.id.dashboard_left_drawer)
    ListView dashboardLeftDrawer;

    FrameLayout dashboardContentFrame;

    private CheckTrail Expiry = new CheckTrail();
    private CheckApplcationExpiry AppExpiry = new CheckApplcationExpiry();
    private ActionBarDrawerToggle mDrawerToggle = null;
    private Handler timerHandler;
    private Runnable timerRunnable;


    public Dashboard_NavigationMenu() {
    }

    @Override
    public final void onResume() {

        BaseConfig.IsFromInPatient = false;

        BaseConfig.LoadDoctorValues();

        if (BaseConfig.PDFLINK.length() > 0 && BaseConfig.PDFLINK != null && !BaseConfig.PDFLINK_FLAG) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BaseConfig.PDFLINK));
            startActivity(browserIntent);
            BaseConfig.PDFLINK_FLAG = true;
        }

        super.onResume();
    }

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_main_navigation_layout);


        try {


            GETINITIALIZE(savedInstanceState);

            CONTROLLISTENERS();

            AUTO_REFRESH_CLOUD_COUNT();

            setOperationNotificationBatch();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }
    }

    private void AUTO_REFRESH_CLOUD_COUNT() {


        timerHandler = new Handler();

        timerRunnable = new Runnable() {
            @Override
            public void run() {
                new CheckPendingData().execute();
                setOperationNotificationBatch();
                timerHandler.postDelayed(this, 1000); //run every second

            }
        };

        timerHandler.postDelayed(timerRunnable, 1000); //Start timer after 1 sec

    }


    public void CONTROLLISTENERS() {

        dashboardToolbarIcCloud.setOnClickListener(v -> {

            Dashboard_NavigationMenu.this.finish();
            Intent setting = new Intent(getApplicationContext(), CloudData.class);
            startActivity(setting);

        });

        dashboardToolbarIcExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(Dashboard_NavigationMenu.this, null));

        dashboardToolbarIcSettings.setOnClickListener(view -> {

            Dashboard_NavigationMenu.this.finish();
            Intent setting = new Intent(getApplicationContext(), SettingActivity.class);
            startActivity(setting);


        });

        dashboardToolbarIcNotification.setOnClickListener(view -> prepareListNotificationList());

    }


    public void GETINITIALIZE(Bundle savedInstanceState) {

        ButterKnife.bind(this);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.HideKeyboard(Dashboard_NavigationMenu.this);


        dashboardContentFrame = findViewById(R.id.dashboard_content_frame);


        CommonDataObjects.ObjectDrawerItem[] drawerItem = new CommonDataObjects.ObjectDrawerItem[5];

        drawerItem[0] = new CommonDataObjects.ObjectDrawerItem(0, "");
        drawerItem[1] = new CommonDataObjects.ObjectDrawerItem(R.drawable.ic_drawer_calendar, dt);
        drawerItem[2] = new CommonDataObjects.ObjectDrawerItem(R.drawable.ic_drawer_profile, getString(R.string.view_profile));
        drawerItem[3] = new CommonDataObjects.ObjectDrawerItem(R.drawable.ic_drawer_settings, getString(R.string.title_activity_setting));
        drawerItem[4] = new CommonDataObjects.ObjectDrawerItem(R.drawable.ic_drawer_signout, getString(R.string.sign_out));

        getSupportActionBar();

        setSupportActionBar(dashboardToolbar);

        dashboardToolbarTxvwTitle.setText(String.format("%s - %s", getResources().getString(R.string.welcome), BaseConfig.doctorname));

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(Dashboard_NavigationMenu.this, R.layout.navigation_drawer_list_item, drawerItem);

        dashboardLeftDrawer.setAdapter(adapter);

        dashboardLeftDrawer.setOnItemClickListener(new DrawerItemClickListener());

        dashboardDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);


        mDrawerToggle = new ActionBarDrawerToggle(this, dashboardDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Objects.requireNonNull(getSupportActionBar()).setTitle(String.format("%s %s", getResources().getString(R.string.welcome), BaseConfig.doctorname));
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Objects.requireNonNull(getSupportActionBar()).setTitle(getResources().getString(R.string.welcome) + ' ' + BaseConfig.doctorname);
            }
        };

        dashboardDrawerLayout.setDrawerListener(mDrawerToggle);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            selectItem(0);
        }


        if (BaseConfig.welcometoast == 1) {

            BaseConfig.SnackBar(Dashboard_NavigationMenu.this, getResources().getString(R.string.welcome) + ' ' + BaseConfig.doctorname + " - Choose Your Task", dashboardDrawerLayout, 1);

        }


        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            startActivity(new Intent(this, NotificationActivity.class));
        }


    }

    @Override
    public final void onBackPressed() {

        System.gc();
        BaseConfig.ExitSweetDialog(Dashboard_NavigationMenu.this, Dashboard_NavigationMenu.class);

    }

    /**
     * @Ponnusamy
     * @since 24-07-2018
     * @implNote Show Ooperation Notification set in BatchView
     */
    public final void setOperationNotificationBatch()
    {
        int notification_Count=0;
        Cursor cursor=BaseConfig.GetDb().rawQuery("select operation_date from operation_details",null);

        try {
            if (cursor!=null) {
                if (cursor.moveToFirst())
                {
                    do {

                        String notification_date= cursor.getString(cursor.getColumnIndex("operation_date"));
                        String date=notification_date.split("T")[0];
                        String sdate=BaseConfig.getDeviceDate();

                        if (sdate.equalsIgnoreCase(date)) {
                            ++notification_Count;
                        }

                    }while (cursor.moveToNext());

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(notification_Count==0)
        {
            dashboardToolbarOperationsNotificationbatch.setVisibility(View.GONE);
            dashboardToolbarIcNotification.setVisibility(View.GONE);
        }
        else {
            dashboardToolbarOperationsNotificationbatch.setVisibility(View.GONE);
            dashboardToolbarIcNotification.setVisibility(View.GONE);
            dashboardToolbarOperationsNotificationbatch.setNumber(notification_Count,true);
        }

    }

    public final void prepareListNotificationList() {

        notificationList.clear();

        if (BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from operation_details where (IsNotified = 'false'  or IsNotified IS NULL)")) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from operation_details where  doctors like '%" + BaseConfig.doctorname + ",%' and (IsNotified = 'false' or IsNotified IS NULL)", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String operationDateStr = c.getString(c.getColumnIndex("operation_date"));
                        String operationFromTimeStr = c.getString(c.getColumnIndex("from_time"));
                        String operationToTimeStr = c.getString(c.getColumnIndex("to_time"));


                        if (operationFromTimeStr.contains("T")) {
                            operationFromTimeStr = operationFromTimeStr.split("T")[1];

                        }
                        if (operationToTimeStr.contains("T")) {
                            operationToTimeStr = operationToTimeStr.split("T")[1];
                        }
                        if (operationDateStr.contains("T")) {
                            operationDateStr = operationDateStr.split("T")[0];
                        }

                        StringBuilder msgString = new StringBuilder();

                        msgString.append(getString(R.string.patient_name)).append(c.getString(c.getColumnIndex("patient_name"))).append('\n');
                        msgString.append(getString(R.string.operationdate)).append(": ").append(operationDateStr).append(' ').append(operationFromTimeStr).append(" to ").append(operationToTimeStr).append('\n');
                        String doctors = c.getString(c.getColumnIndex("doctors"));
                        if (doctors.contains(",")) {
                            String drnames[] = doctors.split(",");
                            StringBuilder DrNames = new StringBuilder();
                            DrNames.append(getString(R.string.doctorss));
                            StringBuilder nurseName = new StringBuilder();
                            nurseName.append(getString(R.string.assistants));
                            for (int i = 0; i <= drnames.length - 1; i++) {
                                if (drnames[i].contains("Dr")) {

                                    DrNames.append(drnames[i]).append(String.valueOf(","));

                                } else {
                                    nurseName.append(drnames[i]);
                                }

                            }

                            String wholeString = String.valueOf(DrNames + "\n" + nurseName);
                            msgString.append(wholeString);


                        } else {
                            msgString.append(doctors);

                        }


                        NotificationClass objClass = new NotificationClass();
                        objClass.TestName = String.valueOf(msgString.toString());

                        objClass.TestValues = String.valueOf(c.getString(c.getColumnIndex("operation_name")));
                        objClass.destinationClass = OperationTheater.class.getSimpleName();
                        try {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            Date date1 = sdf.parse(operationDateStr + " 00:00:00");

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            String date = df.format(Calendar.getInstance().getTime());
                            Date date2 = sdf.parse(date + " 00:00:00");

                            if (!date1.before(date2)) {
                                System.out.println("Date1 is not before Date2");
                            }

                            if (!date1.before(date2)) {
                                notificationList.add(objClass);
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                    while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();


        }

        showNotification();


    }

    public final void prepareMedicalTestListNotificationList() {

        // TODO: 3/24/2017 Checking M.POnnusamy in Medical Test Notification
        notificationListTest.clear();

        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        NotificationClass objClass = new NotificationClass();
                        objClass.TestName = String.valueOf("Patient Name " + c.getString(c.getColumnIndex("pname")) + ' ' + c.getString(c.getColumnIndex("Ptid")) + "\nTestName - " + c.getString(c.getColumnIndex("subtestname")) + "\nTest Value - " + c.getString(c.getColumnIndex("testvalue")));
                        objClass.TestValues = String.valueOf(c.getString(c.getColumnIndex("alltest")));
                        objClass.destinationClass = OperationTheater.class.getSimpleName();
                        objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                        objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));
                        notificationListTest.add(objClass);
                    }
                    while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();


        }

        showNotificationForTestValues();


    }

    public final void showNotificationForTestValues() {

        final Dialog dialog = new Dialog(this);
        View rootLayoutParent = this.getLayoutInflater().inflate(R.layout.parent_dialog_root, null);
        LinearLayout parentLayout = rootLayoutParent.findViewById(R.id.root_layout);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = this.getLayoutInflater();


        for (int i = 0; i <= notificationListTest.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.dialog_each_notify, null);
            LinearLayout viewRoot = layout.findViewById(R.id.each_layout_parent);
            TextView messageTitle = layout.findViewById(R.id.message_title);
            TextView messageStrView = layout.findViewById(R.id.message_title_explanation);
            final NotificationClass notifyObj = notificationListTest.get(i);
            messageStrView.setText(notifyObj.TestName);
            messageTitle.setText(notifyObj.TestValues);


            viewRoot.setOnClickListener(view -> {

            });


            rootLayout.addView(layout);


        }


        if (notificationListTest.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            final Button okButon = rootLayoutParent.findViewById(R.id.btn_ok);
            okButon.setOnClickListener(view -> {

                SQLiteDatabase db = BaseConfig.GetDb();//);
                ContentValues values = new ContentValues();
                values.put("seen", "1");
                db.update("Medicaltestdtls", values, "", null);

                dialog.dismiss();
            });
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(rootLayoutParent);
            Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.show();
        }


    }

    public final void showNotification() {

        final Dialog dialog = new Dialog(this);
        View rootLayoutParent = this.getLayoutInflater().inflate(R.layout.parent_dialog_root, null);
        LinearLayout parentLayout = rootLayoutParent.findViewById(R.id.root_layout);
        LinearLayout rootLayout = new LinearLayout(this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = this.getLayoutInflater();


        for (int i = 0; i <= notificationList.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.dialog_each_notify, null);
            LinearLayout viewRoot = layout.findViewById(R.id.each_layout_parent);

            TextView messageTitle = layout.findViewById(R.id.message_title);
            TextView messageStrView = layout.findViewById(R.id.message_title_explanation);

            final NotificationClass notifyObj = notificationList.get(i);
            messageStrView.setText(notifyObj.TestName);
            messageTitle.setText(notifyObj.TestValues);


            messageTitle.setOnClickListener(view -> {


                /*String testvalues=messageTitle.getText().toString();

                if(testvalues.toString().equalsIgnoreCase("More"))
                {
                    startActivity(new Intent(Dashboard_NavigationMenu.this, Profile_Blood_Report.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                }*/


            });


            viewRoot.setOnClickListener(view -> {
                Intent iStart = null;
                try {


                    iStart = new Intent(Dashboard_NavigationMenu.this, OperationTheater.class);
                    ///iStart = new Intent();

                    startActivity(iStart);
                    finish();

                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            });


            rootLayout.addView(layout);


        }


        if (notificationList.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            final Button okButon = rootLayoutParent.findViewById(R.id.btn_ok);
            okButon.setOnClickListener(view -> dialog.dismiss());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(rootLayoutParent);
            Objects.requireNonNull(dialog.getWindow()).setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.show();
        } else {

            Toast.makeText(this, "No operations available at this time", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return true;

    }

    public final void selectItem(int position) {

        Fragment fragment = null;


        switch (position) {
            case 0:

                fragment = new TaskSelection_Final();

                break;

            case 1:

                break;

            case 2:
                BaseConfig.temp_updateflag = "true";
                BaseConfig.globalStartIntent(Dashboard_NavigationMenu.this, Doctor_Profile.class, null);
                break;

            case 3:

                BaseConfig.globalStartIntent(Dashboard_NavigationMenu.this, SettingActivity.class, null);

                break;

            case 4:

                new CustomKDMCDialog(this)
                        .setLayoutColor(R.color.green_500)
                        .setImage(R.drawable.ic_exit_to_app_black_24dp)
                        .setTitle(this.getResources().getString(R.string.message_title))
                        .setDescription(getString(R.string.str_signout_msg))
                        .setPossitiveButtonTitle("YES")
                        .setNegativeButtonTitle("NO")
                        .setOnPossitiveListener(() -> {

                            BaseConfig.StartWebservice_MasterWebservices_NODEJS(getApplicationContext(), 2);
                            BaseConfig.StartWebservice_ImportWebservices_NODEJS(getApplicationContext(), 2);
                            BaseConfig.StartWebservice_ExportWebservices_NODEJS(getApplicationContext(), 2);
                            BaseConfig.StartWebservice_UpdatedResults_scheduler(getApplicationContext(), 2);

                            Log.e("Webservices", "************Stopped***********");
                            Dashboard_NavigationMenu.this.finish();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);

                        });

                break;


        }


        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.dashboard_content_frame, fragment).commit();
            dashboardLeftDrawer.setItemChecked(position, true);
            dashboardLeftDrawer.setSelection(position);
            dashboardDrawerLayout.closeDrawer(dashboardLeftDrawer);

        }

    }

    @Override
    public final void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    public void CheckPending() {

        try {
            COUNT_PATIENT_REGISTRATION = CheckCount("select count(*) as ret_values  from Bind_Patient_Registration where Isupdate='0' group by PatientId");
            COUNT_CLINICAL_INFORMATION = CheckCount("select count(*) as ret_values from ClinicalInformation where Isupdate='0' group by ptid");
            COUNT_CASENOTES = CheckCount("select count(*) as ret_values from Diagonis where Isupdate='0' group by DiagId");
            COUNT_INVESTIGATION = CheckCount("select count(*)  ret_values from Medicaltest where Isupdate='0' group by mtestid");
            COUNT_PRESCRIPTION = CheckCount("select count(*) as ret_values from Mprescribed where Isupdate='0' group by Medid");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int CheckCount(String query)
    {

        int str = 0;
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str = Integer.parseInt(c.getString(c.getColumnIndex("ret_values")));

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();
        return str;

    }


    public static class NotificationClass {
        public String TestName = "";
        public String TestValues = "";
        public String destinationClass = "";
        public String MtestId = "";
        public String Patid = "";

        public NotificationClass() {
        }
    }


    public class DrawerItemClickListener implements AdapterView.OnItemClickListener {

        private DrawerItemClickListener() {

        }

        @Override
        public final void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            selectItem(i);

        }

    }


    public class CheckTrail extends AsyncTask<Void, Void, Void> {

        CheckTrail() {
        }

        protected final void onPostExecute(Void result) {


            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor cc = db.rawQuery("select isactive from TrailCheck where isupdate='1'", null);
            if (cc != null) {
                if (cc.moveToFirst()) {
                    do {

                        if (cc.getString(cc.getColumnIndex("isactive")).equals("0")) {


                            //sound
                            MediaPlayer mp = null;
                            if (mp != null) {
                                mp.release();
                            }
                            MediaPlayer mp1 = MediaPlayer.create(getApplicationContext(), R.raw.notify);
                            mp1.start();


                            View v1 = null;

                            View v11 = getWindow().getDecorView();
                            v11.setBackgroundResource(android.R.color.transparent);
                            LayoutInflater li = LayoutInflater.from(Dashboard_NavigationMenu.this);
                            View promptsView = li.inflate(R.layout.blockscreen, null);

                            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dashboard_NavigationMenu.this).setCancelable(false);


                            alertDialogBuilder.setView(promptsView);


                            final TextView status_txt = promptsView.findViewById(R.id.textView1);
                            final Button exit_btn = promptsView.findViewById(R.id.button1);

                            status_txt.setText(R.string.trail_label);


                            // create alert dialog

                            final AlertDialog alertDialog = alertDialogBuilder.create();
                            Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.anim.myanimation;
                            alertDialog.show();

                            exit_btn.setOnClickListener(v2 -> Dashboard_NavigationMenu.this.finish());
                        }


                    } while (cc.moveToNext());
                }
            }
            Objects.requireNonNull(cc).close();

            db.close();


            Expiry.cancel(true);
            Expiry = new CheckTrail();


        }


        @Override
        protected final Void doInBackground(Void... params) {


            return null;
        }
    }

    class CheckApplcationExpiry extends AsyncTask<Void, Void, Void> {

        CheckApplcationExpiry() {
        }


        protected final void onPostExecute(Void result) {

            try {

                SQLiteDatabase db = BaseConfig.GetDb();
                Cursor cc = db.rawQuery("select * from AppValidityCheck where isupdate='1'", null);
                if (cc != null) {
                    if (cc.moveToFirst()) {
                        do {

                            if (cc.getString(cc.getColumnIndex("isactive")).equals("0")) {


                                //sound
                                MediaPlayer mp = null;
                                if (mp != null) {
                                    mp.release();
                                }
                                MediaPlayer mp1 = MediaPlayer.create(getApplicationContext(), R.raw.notify);
                                mp1.start();


                                View v1 = null;

                                View v11 = getWindow().getDecorView();
                                v11.setBackgroundResource(android.R.color.transparent);
                                LayoutInflater li = LayoutInflater.from(Dashboard_NavigationMenu.this);
                                View promptsView = li.inflate(R.layout.blockscreen, null);

                                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Dashboard_NavigationMenu.this).setCancelable(false);


                                alertDialogBuilder.setView(promptsView);

                                final TextView status_txt = promptsView.findViewById(R.id.textView1);
                                final Button exit_btn = promptsView.findViewById(R.id.button1);

                                status_txt.setText(String.format("%s%s\nExpiry date:%s\nTo continue, please contact mobydoctor.\nWebsite: http://www.mobydoctor.com/ \nEmail: help@mobydoctor.com", getString(R.string.app_expired), cc.getString(cc.getColumnIndex("installed_date")), cc.getString(cc.getColumnIndex("expiry_date"))));
                                status_txt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20.0F);
                                status_txt.setGravity(Gravity.LEFT);
                                status_txt.setPadding(10, 10, 10, 10);
                                status_txt.setLineSpacing(1.0F, 1.0F);

                                // create alert dialog
                                final AlertDialog alertDialog = alertDialogBuilder.create();
                                Objects.requireNonNull(alertDialog.getWindow()).getAttributes().windowAnimations = R.anim.myanimation;
                                alertDialog.show();

                                exit_btn.setOnClickListener(v2 -> Dashboard_NavigationMenu.this.finish());
                            }


                        } while (cc.moveToNext());
                    }
                }
                Objects.requireNonNull(cc).close();

                db.close();


                AppExpiry.cancel(true);
                AppExpiry = new CheckApplcationExpiry();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected Void doInBackground(Void... params) {


            return null;
        }
    }

    public class CheckPendingData extends AsyncTask<Void, Void, Void> {

        protected void onPostExecute(Void result) {

            super.onPostExecute(result);


            try {

                int total = COUNT_PATIENT_REGISTRATION + COUNT_CLINICAL_INFORMATION + COUNT_PRESCRIPTION + COUNT_CASENOTES + COUNT_INVESTIGATION;

                if (total > 0) {

                    dashboardToolbarIcCloud.setVisibility(View.VISIBLE);
                    dashboardToolbarCloudNotificationbatch.setVisibility(View.VISIBLE);
                    dashboardToolbarCloudNotificationbatch.setText(String.valueOf(total));

                } else {

                    dashboardToolbarIcCloud.setVisibility(View.GONE);
                    dashboardToolbarCloudNotificationbatch.setVisibility(View.GONE);

                }

                // notify the adapter
                total = 0;


            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        @Override
        protected void onPreExecute() {


        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                CheckPending();
            } catch (Exception e) {

                e.printStackTrace();
            }

            return null;
        }

    }


}//END

