package kdmc_kumar.MyPatient_Module.ExtraReports;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Android on 8/21/2017.
 */

public class Profile_HIV_Report extends AppCompatActivity {

    private WebView blood_report = null;
    private Toolbar toolbar = null;

    private ImageView ic_back = null;
    private String MTESTID = "";
    private String PTID = "";

    public Profile_HIV_Report() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__blood__report);


        Bundle b = getIntent().getExtras();

        PTID = b.getString("PTID");
        MTESTID = b.getString("MTESTID");

        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.txvw_title);
        title.setText(getString(R.string.report_hiv));

        ic_back = toolbar.findViewById(R.id.ic_back);

        ic_back.setOnClickListener(view -> Profile_HIV_Report.this.finish());

        blood_report = findViewById(R.id.blood_report);

        blood_report.getSettings().setJavaScriptEnabled(true);
        blood_report.setWebChromeClient(new WebChromeClient());
        blood_report.getSettings().setJavaScriptEnabled(true);
        blood_report.setLayerType(View.LAYER_TYPE_NONE, null);
        blood_report.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        blood_report.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        blood_report.getSettings().setDefaultTextEncodingName("utf-8");

        blood_report.setWebChromeClient(new MyWebChromeClient());

        blood_report.setBackgroundColor(0x00000000);
        blood_report.setVerticalScrollBarEnabled(true);
        blood_report.setHorizontalScrollBarEnabled(true);

        blood_report.getSettings().setJavaScriptEnabled(true);
        blood_report.getSettings().setAllowContentAccess(true);


        blood_report.addJavascriptInterface(new WebAppInterface(this), "android");
        try {

            blood_report.loadDataWithBaseURL("file:///android_asset/", LoadInvetigationData(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String LoadInvetigationData() {

        String values = "";
        String Id = "", Patid = "", Docid = "", Actdate = "", IsActive = "", IsUpdate = "", ServerId = "", Mtestid = "", Testid = "", Subtestid = "", HID = "", IsPaid = "", date_time = "", LabId = "", blood_datetime = "", NameHIVTestKit1 = "", BatchNo1 = "", ExpDate1 = "", Antibodies11 = "", Antibodies21 = "", Antibodies31 = "", NameHIVTestKit2 = "", BatchNo2 = "", ExpDate2 = "", Antibodies12 = "", Antibodies22 = "", Antibodies32 = "", NameHIVTestKit3 = "", BatchNo3 = "", ExpDate3 = "", Antibodies13 = "", Antibodies23 = "", Antibodies33 = "", negative_HIV = "", positive_HIV_1 = "", positive_HIV = "", HIV_Anitibodies = "";


        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = "select * from Bind_HIV_Report where Patid='" + PTID + "' and Mtestid='" + MTESTID + '\'';
        //String Query = "select * from Bind_examination_blood_test where Patid='PID/2017/188/2870' and Mtestid='MTID/2017/188/DOCID/2017/05/81/4C19/2'";
        //Log.e("Load Invetigation Data: ", Query);

        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    Id = c.getString(c.getColumnIndex("Id"));
                    Patid = c.getString(c.getColumnIndex("Patid"));
                    Docid = c.getString(c.getColumnIndex("Docid"));
                    Actdate = c.getString(c.getColumnIndex("Actdate"));
                    IsActive = c.getString(c.getColumnIndex("IsActive"));
                    IsUpdate = c.getString(c.getColumnIndex("IsUpdate"));
                    ServerId = c.getString(c.getColumnIndex("ServerId"));
                    Mtestid = c.getString(c.getColumnIndex("Mtestid"));
                    Testid = c.getString(c.getColumnIndex("Testid"));
                    Subtestid = c.getString(c.getColumnIndex("Subtestid"));
                    HID = c.getString(c.getColumnIndex("HID"));
                    IsPaid = c.getString(c.getColumnIndex("IsPaid"));
                    date_time = c.getString(c.getColumnIndex("date_time"));
                    LabId = c.getString(c.getColumnIndex("LabId"));
                    blood_datetime = c.getString(c.getColumnIndex("blood_datetime"));

                    NameHIVTestKit1 = c.getString(c.getColumnIndex("NameHIVTestKit1"));
                    BatchNo1 = c.getString(c.getColumnIndex("BatchNo1"));
                    ExpDate1 = c.getString(c.getColumnIndex("ExpDate1"));
                    Antibodies11 = c.getString(c.getColumnIndex("Antibodies11"));
                    Antibodies21 = c.getString(c.getColumnIndex("Antibodies21"));
                    Antibodies31 = c.getString(c.getColumnIndex("Antibodies31"));

                    NameHIVTestKit2 = c.getString(c.getColumnIndex("NameHIVTestKit2"));
                    BatchNo2 = c.getString(c.getColumnIndex("BatchNo2"));
                    ExpDate2 = c.getString(c.getColumnIndex("ExpDate2"));
                    Antibodies12 = c.getString(c.getColumnIndex("Antibodies12"));
                    Antibodies22 = c.getString(c.getColumnIndex("Antibodies22"));
                    Antibodies32 = c.getString(c.getColumnIndex("Antibodies32"));

                    NameHIVTestKit3 = c.getString(c.getColumnIndex("NameHIVTestKit3"));
                    BatchNo3 = c.getString(c.getColumnIndex("BatchNo3"));
                    ExpDate3 = c.getString(c.getColumnIndex("ExpDate3"));
                    Antibodies13 = c.getString(c.getColumnIndex("Antibodies13"));
                    Antibodies23 = c.getString(c.getColumnIndex("Antibodies23"));
                    Antibodies33 = c.getString(c.getColumnIndex("Antibodies33"));
                    negative_HIV = c.getString(c.getColumnIndex("negative_HIV"));
                    positive_HIV_1 = c.getString(c.getColumnIndex("positive_HIV_1"));
                    positive_HIV = c.getString(c.getColumnIndex("positive_HIV"));
                    HIV_Anitibodies = c.getString(c.getColumnIndex("HIV_Anitibodies"));

                } while (c.moveToNext());

            }

        }

        c.close();
        String name = "", age = "", gender = "", DOB = "", Country = "", State = "", FatherName = "";
        String Query1 = " Select name,age,gender,DOB,Country,State,FatherName from Patreg where Patid='" + Patid + "' ";
        //String Query = "select * from Bind_examination_blood_test where Patid='PID/2017/188/2870' and Mtestid='MTID/2017/188/DOCID/2017/05/81/4C19/2'";
        //Log.e("Load Invetigation Data: ", Query1);

        Cursor c1 = db.rawQuery(Query1, null);
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    name = c1.getString(c1.getColumnIndex("name"));
                    age = c1.getString(c1.getColumnIndex("age"));
                    gender = c1.getString(c1.getColumnIndex("gender"));
                    DOB = c1.getString(c1.getColumnIndex("DOB"));
                    Country = c1.getString(c1.getColumnIndex("Country"));
                    State = c1.getString(c1.getColumnIndex("State"));
                    FatherName = c1.getString(c1.getColumnIndex("FatherName"));

                } while (c1.moveToNext());

            }

        }
        c.close();
        db.close();

        String values1 = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\"/>\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\"/>\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\"></script>\n" +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                '\n' +
                "<div class=\"table-responsive\">          \n" +
                "<h4><b style=\"color:#3d5987;\"><u><align><center>\n" +
                ' ' + getString(R.string.profile_hiv_hivtestreport) + "</u></b></align></h4>\n" +
                " \n" +
                " <br>\n" +

                "</div>\n" +
                "<div class=\"table-responsive\">\n" +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>" + getString(R.string.profile_hiv_surname) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + name + "</td> \n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>   " + getString(R.string.profile_hiv_firstname) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + FatherName + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_middlename) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + name + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_gender) + " </b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + gender + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_age) + " </b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + age + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_pid) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + Patid + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_labid) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + LabId + "</td> \n" +
                '\n' +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>" + getString(R.string.profile_hiv_dateandtimeblood) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + date_time + "</td> \n" +
                '\n' +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                "<br>\n" +
                "<P style=\"color:#3d5987;\"><b>" + getString(R.string.profile_hiv_testdetails) + "</b></P>\n" +
                "<P style=\"color:#3d5987;\">" + getString(R.string.profile_hiv_speimenttype) + "</P>\n" +
                "<div class=\"table-responsive\">\n" +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\"> <b>  " + getString(R.string.profile_hiv_dateandtimespeciment) + " </b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#3d5987;\">    " + blood_datetime + "</td> \n" +
                '\n' +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<p>" + getString(R.string.profile_hiv_note) + ":</p>\n" +
                "<ul>\n" +
                "  <li>" + getString(R.string.profile_hiv_columnand) + "</li>\n" +
                "  <li>" + getString(R.string.profile_hiv_nocell) + "</li>\n" +
                "</ul>\n" +
                '\n' +
                '\n' +
                '\n' +

                "<div class=\"table-responsive\">\n" +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +


                '\n' +
                "<p><b>" + getString(R.string.profile_hiv_differentialcount) + " :- </b></p>\n" +
                '\n' +
                "<tr style=\"background-color: #3d5987\">\n" +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b><align><center>  " + getString(R.string.profile_hiv_nameofhiv) + "</b></align></th> \n" +
                '\n' +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b><align><center>  " + getString(R.string.profile_hiv_batchno) + "</b></align></th> \n" +
                '\n' +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b><align><center>   " + getString(R.string.profile_hiv_expdate) + "</b></align></th> \n" +
                '\n' +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b> <align><center>  " + getString(R.string.profile_hiv_hiv1antibodies) + "</b></align></th> \n" +
                '\n' +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b><align><center>    " + getString(R.string.profile_hiv_hiv2antibodies) + "</b></align></th> \n" +
                '\n' +
                "<th height=\"15\" width=\"15%\" style=\"color:#ffffff;\"> <b> <align><center>   " + getString(R.string.profile_hiv_hiv3antibodies) + "</b></align></th> \n" +
                '\n' +
                '\n' +
                "</tr>\n" +
                '\n' +
                '\n' +


                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + getString(R.string.profile_hiv_test) + " I </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + BatchNo1 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + ExpDate1 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies11 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies12 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"> <b><align><center> " + Antibodies13 + "  </b></align></td> \n" +
                '\n' +
                '\n' +
                "</tr>\n" +


                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + getString(R.string.profile_hiv_test) + " II </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + BatchNo2 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + ExpDate2 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies21 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies22 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"> <b><align><center> " + Antibodies23 + "  </b></align></td> \n" +
                '\n' +
                '\n' +
                "</tr>\n" +


                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + getString(R.string.profile_hiv_test) + " III </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + BatchNo3 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + ExpDate3 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies31 + " </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"><align><center> <b> " + Antibodies32 + "  </b></align></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#000000;\"> <b><align><center> " + Antibodies33 + "  </b></align></td> \n" +
                '\n' +
                '\n' +
                "</tr>\n" +

                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<P style=\"color:#3d5987;\"><b>" + getString(R.string.profile_hiv_interpretation) + "</b></P>\n" +
                '\n' +
                "<div class=\"table-responsive\">\n" +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\"> <b>" + getString(R.string.profile_hiv_negative_hiv_antibodies) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\">    " + negative_HIV + "</td> \n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\"> <b>   " + getString(R.string.profile_hiv_positive_hiv_1_antibodies) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\">    " + positive_HIV_1 + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\"> <b>  " + getString(R.string.profile_hiv_positive_hive1_hiv) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\">    " + positive_HIV + "</td> \n" +
                '\n' +
                "</tr>\n" +
                "<tr>\n" +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\"> <b> " + getString(R.string.profile_hiv_specimenisdeterminate) + "</b></td> \n" +
                '\n' +
                "<td height=\"15\" width=\"15%\" style=\"color:#fffff;\">    " + HIV_Anitibodies + "</td> \n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
               /* "\n" +
                "<!---------------------------->\n" +
                "\n" +
                "<div align=\"right\">\n" +
                " <a >"+getString(R.string.profile_hiv_nameandsign)+" <br> "+getString(R.string.profile_hiv_laboratory)+"<br></a>\n" +
              //  "    <a align=\"right\"><img align=\"right\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIA...\"></a>\n" +
                "\n" +
                "</div>\n" +*/
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html> ";


        return values1;
    }

    @Override
    public final void onBackPressed() {
        //super.onBackPressed();
        this.finish();

    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    static class WebAppInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public final void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }
}
