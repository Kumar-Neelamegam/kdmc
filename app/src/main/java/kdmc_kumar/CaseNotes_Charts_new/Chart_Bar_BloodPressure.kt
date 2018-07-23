package kdmc_kumar.CaseNotes_Charts_new

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

import java.util.ArrayList

import displ.mobydocmarathi.com.R
import displ.mobydocmarathi.com.R.id
import displ.mobydocmarathi.com.R.layout
import displ.mobydocmarathi.com.R.string
import kdmc_kumar.Core_Modules.BaseConfig

class Chart_Bar_BloodPressure : AppCompatActivity() {


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
        this.setContentView(layout.new_bar_chart_layout)

        try {
            this.GetInitialize()
        } catch (e: RuntimeException) {
            e.printStackTrace()
        }


    }

    private fun GetInitialize() {

        val chart = this.findViewById<BarChart>(id.chart)
        chart.setDescription("")

        val name = this.findViewById<TextView>(id.chart_name)
        name.setText(string.bpph)

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


        val data = BarData(this.getXAxisValues(this.Chart_Id), this.getDataSet(this.Chart_Id))


        if (data != null) {
            chart.data = data
            chart.animateXY(2000, 2000)
            chart.invalidate()

        }


    }
    //**********************************************************************************************


    private fun getDataSet(Id: String?): ArrayList<BarDataSet> {

        val dataSets = ArrayList<BarDataSet>()
        val valueSet1 = ArrayList<BarEntry>()
        val valueSet2 = ArrayList<BarEntry>()

        var v1: BarEntry
        var v2: BarEntry

        val barDataSet1: BarDataSet? = null
        val barDataSet2: BarDataSet? = null

        val dataSets1 = ArrayList<BarDataSet>()

        val db = BaseConfig.GetDb()//Chart_Bar_BloodPressure.this);

        val Query = "select BpS,BpD from Diagonis where Ptid='" + this.Patient_Id!!.trim { it <= ' ' } + "' order by id desc"
        val c = db.rawQuery(Query, null)
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    v1 = BarEntry(Integer.parseInt(c.getString(c.getColumnIndex("BpS"))).toFloat(), c.position) // Jan
                    valueSet1.add(v1)

                    v2 = BarEntry(Integer.parseInt(c.getString(c.getColumnIndex("BpD"))).toFloat(), c.position) // Jan
                    valueSet2.add(v2)


                } while (c.moveToNext())

            }

        }
        c!!.close()
        db.close()

        val barDataSet11 = BarDataSet(valueSet1, "Systolic")
        barDataSet11.color = Color.rgb(200, 0, 0)

        val barDataSet21 = BarDataSet(valueSet2, "Diastolic")
        barDataSet21.color = Color.rgb(218, 165, 32)


        dataSets1.add(barDataSet11)
        dataSets1.add(barDataSet21)


        return dataSets1


    }


    //**********************************************************************************************
    private fun getXAxisValues(Id: String?): ArrayList<String> {
        val xAxis1 = ArrayList<String>()


        val db = BaseConfig.GetDb()//Chart_Bar_BloodPressure.this);
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