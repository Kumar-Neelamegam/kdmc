package kdmc_kumar.CaseNotes_Charts_new;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Android on 4/9/2017.
 */

public class Chart_Line_BloodSugar extends AppCompatActivity {

    private LineChart lineChart = null;


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

    public Chart_Line_BloodSugar() {
    }

    //**********************************************************************************************
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_line_chart_layout);

        try {
            GetInitialize();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    private void GetInitialize() {


        lineChart = findViewById(R.id.chart);
        lineChart.setDescription("");
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

        Close.setOnClickListener(view -> Chart_Line_BloodSugar.this.finish());


        LineData data = new LineData(getXAxisValues(Chart_Id), getDataSet(Chart_Id));

        if (data != null) {
            lineChart.setData(data);
            lineChart.animateXY(2000, 2000);
            lineChart.invalidate();

        }


    }
//**********************************************************************************************


    private ArrayList<LineDataSet> getDataSet(String Id) {

        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSet2 = new ArrayList<>();
        ArrayList<Entry> valueSet3 = new ArrayList<>();


        Entry v1, v2, v3;

        LineDataSet lineDataSet1 = null, lineDataSet2 = null, lineDataSet3 = null;

        ArrayList<LineDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_BloodSugar.this);

        String Query = "select FBS,PPS,RBS from Diagonis where Ptid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("FBS"))), c.getPosition()); // Jan
                    valueSet1.add(v1);

                    v2 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("PPS"))), c.getPosition()); // Jan
                    valueSet2.add(v2);

                    v3 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("RBS")).equals("-")? "0" : c.getString(c.getColumnIndex("RBS"))), c.getPosition()); // Jan
                    valueSet3.add(v3);


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        LineDataSet lineDataSet11 = new LineDataSet(valueSet1, "FBS");
        lineDataSet11.setColor(Color.rgb(200, 0, 0));
        lineDataSet11.setDrawFilled(true);
        lineDataSet11.setFillColor(Color.rgb(200, 0, 0));//Fill Color


        LineDataSet lineDataSet21 = new LineDataSet(valueSet2, "PPBS");
        lineDataSet21.setColor(Color.rgb(218, 165, 32));
        lineDataSet21.setDrawFilled(true);
        //lineDataSet2.setDrawCubic(true);
        //lineDataSet2.setDrawValues(true);
        lineDataSet21.setFillColor(Color.rgb(218, 165, 32));//Fill Color
        //lineDataSet2.setLineWidth(2f);// Line With
        //lineDataSet2.setDrawCircles(true);


        LineDataSet lineDataSet31 = new LineDataSet(valueSet3, "RBS");
        lineDataSet31.setColor(Color.rgb(0, 200, 0));
        lineDataSet31.setDrawFilled(true);
        lineDataSet31.setFillColor(Color.rgb(0, 200, 0));//Fill Color
       /* lineDataSet3.setDrawCubic(true);
        lineDataSet3.setDrawValues(true);

        lineDataSet3.setLineWidth(2f);// Line With
        lineDataSet3.setDrawCircles(true);
*/
        dataSets1.add(lineDataSet11);
        dataSets1.add(lineDataSet21);
        dataSets1.add(lineDataSet31);


        return dataSets1;


    }


    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_BloodSugar.this);
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
