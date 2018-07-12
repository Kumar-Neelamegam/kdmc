package kdmc_kumar.CaseNotes_Charts_new;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;


/**
 * Created @ Muthukumar & Vidhya
 * 08/04/2017
 */
public class Chart_Bar_BloodSugar extends AppCompatActivity {

    //**********************************************************************************************
    private Bundle b = null;
    private Button Close = null;
    //**********************************************************************************************
    private String Chart_Id = null;
    private String Patient_Id = null;
    private String Patient_Name = null;
    private String Patient_AgeGender = null;

    public Chart_Bar_BloodSugar() {
    }

    //**********************************************************************************************
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.new_bar_chart_layout);

        try {
            GetInitialize();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    private void GetInitialize() {
        BarChart chart = findViewById(R.id.chart);

        chart.setDescription("");

        final TextView name = findViewById(R.id.chart_name);

        name.setText(R.string.bsph);

        Close = findViewById(R.id.cancel);

        final TextView pat_id = findViewById(R.id.tv_patient_id);
        final TextView pat_name = findViewById(R.id.tv_patient_name);
        final TextView pat_age = findViewById(R.id.tv_patient_agegender);


        b = getIntent().getExtras();

        if (b != null) {
            Chart_Id = b.getString("ID");
            Patient_Id = b.getString(BaseConfig.BUNDLE_PATIENT_ID);
            Patient_Name = b.getString("PATIENT_NAME");
            Patient_AgeGender = b.getString("PATIENT_AGEGENDER");

            pat_id.setText(Patient_Id);
            pat_name.setText(Patient_Name);
            pat_age.setText(Patient_AgeGender);


        }

        Close.setOnClickListener(view -> Chart_Bar_BloodSugar.this.finish());


        BarData data = new BarData(getXAxisValues(Chart_Id), getDataSet(Chart_Id));

        if (data != null) {
            chart.setData(data);
            chart.animateXY(2000, 2000);
            chart.invalidate();

        }

    }
//**********************************************************************************************

    private ArrayList<BarDataSet> getDataSet(String Id) {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        ArrayList<BarEntry> valueSet2 = new ArrayList<>();
        ArrayList<BarEntry> valueSet3 = new ArrayList<>();


        BarEntry v1, v2, v3;
        BarDataSet barDataSet1 = null, barDataSet2 = null, barDataSet3 = null;
        ArrayList<BarDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Bar_BloodSugar.this);


        String Query = "select FBS,PPS,RBS from Diagonis where Ptid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("FBS"))), c.getPosition()); // Jan
                    valueSet1.add(v1);

                    v2 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("PPS"))), c.getPosition()); // Jan
                    valueSet2.add(v2);

                    v3 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("RBS"))), c.getPosition()); // Jan
                    valueSet3.add(v3);


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        BarDataSet barDataSet11 = new BarDataSet(valueSet1, "FBS");
        barDataSet11.setColor(Color.rgb(200, 0, 0));

        BarDataSet barDataSet21 = new BarDataSet(valueSet2, "PPBS");
        barDataSet21.setColor(Color.rgb(218, 165, 32));

        BarDataSet barDataSet31 = new BarDataSet(valueSet3, "RBS");
        barDataSet31.setColor(Color.rgb(0, 200, 0));


        dataSets1.add(barDataSet11);
        dataSets1.add(barDataSet21);
        dataSets1.add(barDataSet31);


        return dataSets1;


    }

    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Bar_BloodSugar.this);
        String Query = "select  Actdate from Diagonis where Ptid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    xAxis1.add(c.getString(c.getColumnIndex("Actdate")));

                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();
        return xAxis1;
    }
//**********************************************************************************************

}