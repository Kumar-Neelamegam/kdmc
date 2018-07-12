package kdmc_kumar.Inpatient_Module.Inpatient_Charts;

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

import static displ.mobydocmarathi.com.R.id.chart;

/**
 * Created by Android on 4/9/2017.
 */

public class Chart_Line_TemperatureChart extends AppCompatActivity {

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
    String Patient_Name = null;
    String Patient_AgeGender = null;

    public Chart_Line_TemperatureChart() {
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


        lineChart = findViewById(chart);
        lineChart.setDescription("");
        final TextView name = findViewById(R.id.chart_name);
        name.setText("Inpatient - Temperature Chart");


        Close = findViewById(R.id.cancel);

        final TextView pat_id = findViewById(R.id.tv_patient_id);
        final TextView pat_name = findViewById(R.id.tv_patient_name);
        final TextView pat_age = findViewById(R.id.tv_patient_agegender);


        b = getIntent().getExtras();

        if (b != null) {

            Chart_Id = b.getString("ID");
            Patient_Id = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

            String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + Patient_Id + '\'');
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + Patient_Id + '\'');

            pat_id.setText(Patient_Id);
            pat_name.setText(Patient_Name);
            pat_age.setText(Patient_AgeGender);


        }

        Close.setOnClickListener(view -> Chart_Line_TemperatureChart.this.finish());


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


        Entry v1;

        LineDataSet lineDataSet1 = null;

        ArrayList<LineDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_TemperatureChart.this);

        String Query = "select temperature from Inpatient_TemperatureChart where patid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (!c.getString(c.getColumnIndex("temperature")).equalsIgnoreCase("0")) {
                        String[] value = c.getString(c.getColumnIndex("temperature")).trim().split("-");
                        String[] valuefinal = value[1].trim().split(" ");
                        int temp = Double.valueOf(valuefinal[0]).intValue();

                        v1 = new BarEntry((float) temp, c.getPosition());
                        valueSet1.add(v1);
                    }


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        LineDataSet lineDataSet11 = new LineDataSet(valueSet1, "TEMPERATURE (°F)");
        lineDataSet11.setColor(Color.rgb(200, 0, 0));
        lineDataSet11.setDrawFilled(true);
        lineDataSet11.setFillColor(Color.rgb(200, 0, 0));//Fill Color
       /* lineDataSet1.setDrawCubic(true);
        lineDataSet1.setDrawValues(true);

        lineDataSet1.setLineWidth(2f);// Line With
        lineDataSet1.setDrawCircles(true);

       */
        dataSets1.add(lineDataSet11);


        return dataSets1;


    }


    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_TemperatureChart.this);
        String Query = "select  Actdate from Inpatient_TemperatureChart where patid='" + Patient_Id.trim() + "' order by id desc";
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
