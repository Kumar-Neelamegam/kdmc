package kdmc_kumar.CaseNotes_Charts_new;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

public class Chart_Bar_Weight extends AppCompatActivity {


    /**
     * Created @ Muthukumar & Vidhya
     * 08/04/2017
     */


    //**********************************************************************************************
    private Bundle b = null;
    private Button Close = null;
    //**********************************************************************************************
    private String Chart_Id = null;
    private String Patient_Id = null;
    private String Patient_Name = null;
    private String Patient_AgeGender = null;

    public Chart_Bar_Weight() {
    }

    //**********************************************************************************************
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        name.setText(R.string.bwph);

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

        Close.setOnClickListener(view -> Chart_Bar_Weight.this.finish());


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


        BarEntry v1;

        BarDataSet barDataSet1 = null, barDataSet2 = null;

        ArrayList<BarDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Bar_Weight.this);

        String Query = "select PWeight from Diagonis where Ptid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("PWeight"))), c.getPosition()); // Jan
                    valueSet1.add(v1);


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        BarDataSet barDataSet11 = new BarDataSet(valueSet1, "Weight");
        barDataSet11.setColor(Color.rgb(200, 0, 0));


        dataSets1.add(barDataSet11);


        return dataSets1;


    }


    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Bar_Weight.this);
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