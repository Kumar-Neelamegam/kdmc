package kdmc_kumar.CaseNotes_Charts_new

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

import java.util.ArrayList

import displ.mobydocmarathi.com.R
import displ.mobydocmarathi.com.R.id
import displ.mobydocmarathi.com.R.layout
import displ.mobydocmarathi.com.R.string
import kdmc_kumar.Core_Modules.BaseConfig

/**
 * Created by Android on 4/9/2017.
 */

class Chart_Line_BloodSugar : AppCompatActivity() {

    private var lineChart: LineChart? = null


    /**
     * Created @ Muthukumar & Vidhya
     * 08/04/2017
     */


    //**********************************************************************************************
    private var b: Bundle? = null
    private var Close: Button? = null
    //**********************************************************************************************
    private var Chart_Id: String? = null
    private var Patient_Id: String? = null
    private var Patient_Name: String? = null
    private var Patient_AgeGender: String? = null

    //**********************************************************************************************
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setContentView(layout.new_line_chart_layout)

        try {
            this.GetInitialize()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }


    }

    private fun GetInitialize() {


        this.lineChart = this.findViewById(id.chart)
        this.lineChart!!.setDescription("")
        val name = this.findViewById<TextView>(id.chart_name)
        name.setText(string.bsph)

        this.Close = this.findViewById(id.cancel)

        val pat_id = this.findViewById<TextView>(id.tv_patient_id)
        val pat_name = this.findViewById<TextView>(id.tv_patient_name)
        val pat_age = this.findViewById<TextView>(id.tv_patient_agegender)


        this.b = this.intent.extras

        if (this.b != null) {

            this.Chart_Id = this.b!!.getString("ID")
            this.Patient_Id = this.b!!.getString(BaseConfig.BUNDLE_PATIENT_ID)
            this.Patient_Name = this.b!!.getString("PATIENT_NAME")
            this.Patient_AgeGender = this.b!!.getString("PATIENT_AGEGENDER")

            pat_id.text = this.Patient_Id
            pat_name.text = this.Patient_Name
            pat_age.text = this.Patient_AgeGender

        }

        this.Close!!.setOnClickListener { view -> finish() }


        val data = LineData(this.getXAxisValues(this.Chart_Id), this.getDataSet(this.Chart_Id))

        if (data != null) {
            this.lineChart!!.data = data
            this.lineChart!!.animateXY(2000, 2000)
            this.lineChart!!.invalidate()

        }


    }
    //**********************************************************************************************


    private fun getDataSet(Id: String?): ArrayList<LineDataSet> {

        val dataSets = ArrayList<LineDataSet>()
        val valueSet1 = ArrayList<Entry>()
        val valueSet2 = ArrayList<Entry>()
        val valueSet3 = ArrayList<Entry>()


        var v1: Entry
        var v2: Entry
        var v3: Entry

        val lineDataSet1: LineDataSet? = null
        val lineDataSet2: LineDataSet? = null
        val lineDataSet3: LineDataSet? = null

        val dataSets1 = ArrayList<LineDataSet>()

        val db = BaseConfig.GetDb()//Chart_Line_BloodSugar.this);

        val Query = "select FBS,PPS,RBS from Diagonis where Ptid='" + this.Patient_Id!!.trim { it <= ' ' } + "' order by id desc"
        val c = db.rawQuery(Query, null)
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = BarEntry(Integer.parseInt(c.getString(c.getColumnIndex("FBS"))).toFloat(), c.position) // Jan
                    valueSet1.add(v1)

                    v2 = BarEntry(Integer.parseInt(c.getString(c.getColumnIndex("PPS"))).toFloat(), c.position) // Jan
                    valueSet2.add(v2)

                    v3 = BarEntry(Integer.parseInt(c.getString(c.getColumnIndex("RBS"))).toFloat(), c.position) // Jan
                    valueSet3.add(v3)


                } while (c.moveToNext())

            }

        }
        c!!.close()
        db.close()

        val lineDataSet11 = LineDataSet(valueSet1, "FBS")
        lineDataSet11.color = Color.rgb(200, 0, 0)
        lineDataSet11.setDrawFilled(true)
        lineDataSet11.fillColor = Color.rgb(200, 0, 0)//Fill Color


        val lineDataSet21 = LineDataSet(valueSet2, "PPBS")
        lineDataSet21.color = Color.rgb(218, 165, 32)
        lineDataSet21.setDrawFilled(true)
        //lineDataSet2.setDrawCubic(true);
        //lineDataSet2.setDrawValues(true);
        lineDataSet21.fillColor = Color.rgb(218, 165, 32)//Fill Color
        //lineDataSet2.setLineWidth(2f);// Line With
        //lineDataSet2.setDrawCircles(true);


        val lineDataSet31 = LineDataSet(valueSet3, "RBS")
        lineDataSet31.color = Color.rgb(0, 200, 0)
        lineDataSet31.setDrawFilled(true)
        lineDataSet31.fillColor = Color.rgb(0, 200, 0)//Fill Color
        /* lineDataSet3.setDrawCubic(true);
        lineDataSet3.setDrawValues(true);

        lineDataSet3.setLineWidth(2f);// Line With
        lineDataSet3.setDrawCircles(true);
*/
        dataSets1.add(lineDataSet11)
        dataSets1.add(lineDataSet21)
        dataSets1.add(lineDataSet31)


        return dataSets1


    }


    //**********************************************************************************************
    private fun getXAxisValues(Id: String?): ArrayList<String> {
        val xAxis1 = ArrayList<String>()


        val db = BaseConfig.GetDb()//Chart_Line_BloodSugar.this);
        val Query = "select  Actdate from Diagonis where Ptid='" + this.Patient_Id!!.trim { it <= ' ' } + "' order by id desc"
        val c = db.rawQuery(Query, null)
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    xAxis1.add(c.getString(c.getColumnIndex("Actdate")))

                } while (c.moveToNext())

            }

        }
        c!!.close()
        db.close()
        return xAxis1
    }
    //**********************************************************************************************


}
