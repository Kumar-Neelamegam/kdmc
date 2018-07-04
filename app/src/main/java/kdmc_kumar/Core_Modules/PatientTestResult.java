package kdmc_kumar.Core_Modules;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Inpatient_Module.OperationTheater;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_ANCTestReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_Blood_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_StoolReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_UrineReport;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;


public class PatientTestResult extends AppCompatActivity {
    private static final int WRAP_CONTENT = 21;
    private final ArrayList<Dashboard_NavigationMenu.NotificationClass> notificationListTest = new ArrayList<>();
    private ImageView lab_patitent_image = null;
    private String value = null;
    private TextView patientname = null;
    private TextView patientid = null;
    private TextView patientage = null;
    private TextView patientgender = null;


    private Toolbar toolbar = null;
    private ImageView back = null;
    private ImageView exit = null;
    private TextView Title = null;

    private Button more_details = null;
    String strPatid = "", strMtestId = "";
    private final String compareExamination = "All examination of blood";
    private final String compareStoolReport = "All stool test";
    private final String compareUrineReport = "All urine test";
    private final String compareAncfpReport = "All anc fp test";
    private final String compareHIVReport = "All hiv test";

    public PatientTestResult() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_test_result);


        // #######################################################################################################

        toolbar = findViewById(R.id.toolbar);
        exit = toolbar.findViewById(R.id.ic_exit);
        back = toolbar.findViewById(R.id.ic_back);
        Title = toolbar.findViewById(R.id.txvw_title);
        Title.setText(getString(R.string.app_name) + " - " + "Lab Reports");

        setSupportActionBar(toolbar);

        exit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(PatientTestResult.this, null));


        back.setOnClickListener(view -> LoadBack());
        // #######################################################################################################


        value = getIntent().getExtras().getString("PatientId");

        lab_patitent_image = findViewById(R.id.lab_patitent_image);
        patientname = findViewById(R.id.lab_patient_name);
        patientid = findViewById(R.id.lab_patient_id);
        patientage = findViewById(R.id.lab_patient_age);
        patientgender = findViewById(R.id.lab_patient_gender);

        more_details = findViewById(R.id.more_info);

        more_details.setOnClickListener(v -> {

            ((Activity) v.getContext()).finish();
            Intent lib = new Intent(v.getContext(), MyPatientDrawer.class);
            lib.putExtra(BaseConfig.BUNDLE_PATIENT_ID, value);
            lib.putExtra("TEST_REPORT", "True");
            v.getContext().startActivity(lib);

        });

        LinearLayout parentLayout = findViewById(R.id.insert_rows);

        // TODO: 3/24/2017 set Patient Details
        setPatientDetails(value, lab_patitent_image, patientname, patientid, patientage, patientgender);


        // TODO: 3/24/2017 Checked M.Ponnusamy in Medical Test Notification
        notificationListTest.clear();

        boolean status_value;


        String compareRBSName = "blood sug random";
        String compareFBSName = "blood sug fasting";
        String comparePBSName = "blood sug pp";


        String Query = "select Id as dstatus1  from Medicaltestdtls where Ptid='" + value + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        //Log.e("Test result query: ", Query);
        //Log.e("Test result query: ", Query);
        //Log.e("Test result query: ", Query);

        status_value = LoadReportsBooleanStatus(Query);


        if (status_value) {
            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery("select *  from Medicaltestdtls where Ptid='" + value + "' and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        Dashboard_NavigationMenu.NotificationClass objClass = new Dashboard_NavigationMenu.NotificationClass();


                        if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareRBSName)) {
                            objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                            objClass.TestValues = String.valueOf(checkNull(c.getString(c.getColumnIndex("temperature"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareFBSName)) {
                            objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                            objClass.TestValues = String.valueOf(checkNull(c.getString(c.getColumnIndex("bps"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(comparePBSName)) {
                            objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                            objClass.TestValues = String.valueOf(checkNull(c.getString(c.getColumnIndex("bpd"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();


                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareExamination)) //EXAMINATION OF BLOOD - All examination of blood
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_examination_blood_test where Patid='" + value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareStoolReport)) //STOOL REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_stool_report where Patid='" + value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareUrineReport)) //URINE REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_urine_test where Patid='" + value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareAncfpReport)) //ANC FP REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_anc_fp_test where Patid='" + value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareHIVReport)) //HIV REPORT
                        {
                            String MTESTID = c.getString(c.getColumnIndex("mtestid"));
                            String QueryCheck = "select Id as dstatus1 from Bind_HIV_Report where Patid='" + value + "' and Mtestid='" + MTESTID.trim() + '\'';

                            boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                            if (q) {//==true

                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "More";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();
                                objClass.Patid = c.getString(c.getColumnIndex("Ptid"));
                                objClass.MtestId = c.getString(c.getColumnIndex("mtestid"));

                            } else//false
                            {
                                objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                                objClass.TestValues = "-";
                                objClass.destinationClass = OperationTheater.class.getSimpleName();

                            }

                        } else {
                            objClass.TestName = String.valueOf(c.getString(c.getColumnIndex("testname")) + " - " + checkNull(c.getString(c.getColumnIndex("subtestname"))));
                            objClass.TestValues = String.valueOf(checkNull(c.getString(c.getColumnIndex("testvalue"))));
                            objClass.destinationClass = OperationTheater.class.getSimpleName();

                        }


                        notificationListTest.add(objClass);
                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();


        }

        try {
            showNotificationForTestValues(parentLayout);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private static final void setPatientDetails(String patientid, ImageView image, TextView name, TextView id, TextView age, TextView gender) {

        try {
            String str = "";


            String Query = "select name,Patid,gender,age,PC from Patreg where Patid='" + patientid + '\'';

            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        name.setText(c.getString(c.getColumnIndex("name")));
                        id.setText(c.getString(c.getColumnIndex("Patid")));
                        gender.setText(c.getString(c.getColumnIndex("gender")));
                        age.setText(c.getString(c.getColumnIndex("age")));


                        BaseConfig.Glide_LoadImageView( image,  c.getString(c.getColumnIndex("PC")));




                    } while (c.moveToNext());

                }
            }
            c.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    private final void showNotificationForTestValues(LinearLayout parentLayout) {

        LinearLayout rootLayout = new LinearLayout(PatientTestResult.this);
        rootLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutInflater inflator = PatientTestResult.this.getLayoutInflater();


        for (int i = 0; i <= notificationListTest.size() - 1; i++) {


            LinearLayout layout = (LinearLayout) inflator.inflate(R.layout.patient_test_result_row, null);
            LinearLayout viewRoot = layout.findViewById(R.id.row_lay);
            TextView TestName = layout.findViewById(R.id.lab_testname);
            TextView TestValue = layout.findViewById(R.id.lab_testvalue);
            final Dashboard_NavigationMenu.NotificationClass notifyObj = notificationListTest.get(i);

            TestName.setText(notifyObj.TestName);
            TestValue.setText(notifyObj.TestValues);


            TestValue.setOnClickListener(view -> {

                String[] testname = TestName.getText().toString().split("-");

                String str_testname = testname[1];
                String testvalues = TestValue.getText().toString();

                if (str_testname.equalsIgnoreCase(compareExamination))//blood test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        startActivity(new Intent(PatientTestResult.this, Profile_Blood_Report.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(compareStoolReport))//stool test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        startActivity(new Intent(PatientTestResult.this, Profile_StoolReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(compareUrineReport))//urine test
                {
                    if (testvalues.equalsIgnoreCase("More")) {
                        startActivity(new Intent(PatientTestResult.this, Profile_UrineReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }
                } else if (str_testname.equalsIgnoreCase(compareAncfpReport)) {
                    if (testvalues.equalsIgnoreCase("More")) {
                        startActivity(new Intent(PatientTestResult.this, Profile_ANCTestReport.class).putExtra("PTID", notifyObj.Patid).putExtra("MTESTID", notifyObj.MtestId));

                    }

                }

            });


            viewRoot.setOnClickListener(view -> {
                Intent iStart = null;


            });


            rootLayout.addView(layout);


        }


        if (notificationListTest.size() > 0) {
            parentLayout.addView(rootLayout);
            DisplayMetrics metrics = PatientTestResult.this.getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;

        }


    }

    private static final boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;

            SQLiteDatabase db = BaseConfig.GetDb();//PatientTestResult.this);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return havcount > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public final void onBackPressed() {
        super.onBackPressed();

        LoadBack();

    }

    private final void LoadBack() {

        BaseConfig.globalStartIntent(this, MyPatient.class, null);

    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return returns_Val;
    }


}//end
