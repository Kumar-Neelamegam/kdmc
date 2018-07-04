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

/**
 * Created by Android on 4/9/2017.
 */

public class Chart_Line_InputChart extends AppCompatActivity {

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

    public Chart_Line_InputChart() {
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
        name.setText("Inpatient - Input Chart");

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

        Close.setOnClickListener(view -> Chart_Line_InputChart.this.finish());


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


        Entry v1, v2;

        LineDataSet lineDataSet1 = null, lineDataSet2 = null;

        ArrayList<LineDataSet> dataSets1 = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_InputChart.this);

        String Query = "select ip_oral,ip_fluids from Inpatient_MainChart where patid='" + Patient_Id.trim() + "' order by id desc";
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("ip_oral"))), c.getPosition()); // Jan
                    valueSet1.add(v1);

                    v2 = new BarEntry((float) Integer.parseInt(c.getString(c.getColumnIndex("ip_fluids"))), c.getPosition()); // Jan
                    valueSet2.add(v2);


                } while (c.moveToNext());

            }

        }
        c.close();
        db.close();

        LineDataSet lineDataSet11 = new LineDataSet(valueSet1, "IP_ORAL");
        lineDataSet11.setColor(Color.rgb(200, 0, 0));
        lineDataSet11.setDrawFilled(true);
        lineDataSet11.setFillColor(Color.rgb(200, 0, 0));//Fill Color
       /* lineDataSet1.setDrawCubic(true);
        lineDataSet1.setDrawValues(true);

        lineDataSet1.setLineWidth(2f);// Line With
        lineDataSet1.setDrawCircles(true);
*/
        LineDataSet lineDataSet21 = new LineDataSet(valueSet2, "IP_FLUIDS");
        lineDataSet21.setColor(Color.rgb(218, 165, 32));
        lineDataSet21.setDrawFilled(true);
        lineDataSet21.setFillColor(Color.rgb(218, 165, 32));//Fill Color
      /*  lineDataSet2.setDrawCubic(true);
        lineDataSet2.setDrawValues(true);

        lineDataSet2.setLineWidth(2f);// Line With
        lineDataSet2.setDrawCircles(true);

*/
        dataSets1.add(lineDataSet11);
        dataSets1.add(lineDataSet21);


        return dataSets1;


    }


    //**********************************************************************************************
    private ArrayList<String> getXAxisValues(String Id) {
        ArrayList<String> xAxis1 = new ArrayList<>();


        SQLiteDatabase db = BaseConfig.GetDb();//Chart_Line_InputChart.this);
        String Query = "select  Actdate from Inpatient_MainChart where patid='" + Patient_Id.trim() + "' order by id desc";
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
