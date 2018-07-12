package kdmc_kumar.Notification;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.NotificationGetSet;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.NotificationListGetSet;
import kdmc_kumar.Core_Modules.BaseConfig;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    private RecyclerView recyclerView = null;
    private ArrayList<NotificationGetSet> notificationGetSets = null;
    private Button ok = null;
    private LinearLayoutManager linearLayoutManager = null;
    private NotificationAdapter notificationAdapter = null;

    public NotificationActivity() {
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

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifiation);
        setTitle(BaseConfig.Appname + " Notification");

        init();
        ok.setOnClickListener(this);

        notificationAdapter = new NotificationAdapter(getPatientDetails());
        recyclerView.setAdapter(notificationAdapter);

    }

    private final ArrayList<NotificationGetSet> getPatientDetails() {
        ArrayList<NotificationGetSet> value = new ArrayList<>();
        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        String PATIENT_ID = c.getString(c.getColumnIndex("Ptid"));
                        String MEDICAL_TEST_ID = c.getString(c.getColumnIndex("mtestid"));
                        String PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PATIENT_ID + "'");

                        value.add(new NotificationGetSet(PATIENT_NAME, PATIENT_ID, MEDICAL_TEST_ID, getTableData(PATIENT_ID)));

                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        }

        return value;
    }

    private ArrayList<NotificationListGetSet> getTableData(String patientid) {
        ArrayList<NotificationListGetSet> values = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') and Ptid='" + patientid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String va = "";

                    if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareRBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("temperature")), c.getString(c.getColumnIndex("testsummary"))));


                    } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareFBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bps")), c.getString(c.getColumnIndex("testsummary"))));

                    } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(comparePBSName)) {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bpd")), c.getString(c.getColumnIndex("testsummary"))));

                    } else {
                        values.add(new NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("testvalue")), c.getString(c.getColumnIndex("testsummary"))));
                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        return values;
    }

    private void init() {
        recyclerView = findViewById(R.id.recyler_view);
        ok = findViewById(R.id.ok);
        notificationGetSets = new ArrayList<>();

        linearLayoutManager = new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public final void onClick(View view) {
        NotificationActivity.this.finish();

        SQLiteDatabase db = BaseConfig.GetDb();
        ContentValues values = new ContentValues();
        values.put("seen", "1");
        db.update("Medicaltestdtls", values, "", null);
    }
}
