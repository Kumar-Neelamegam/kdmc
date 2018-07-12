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

public class Profile_UrineReport extends AppCompatActivity {
    private WebView blood_report = null;
    private Toolbar toolbar = null;

    private ImageView ic_back = null;
    private String MTESTID = "";
    private String PTID = "";

    public Profile_UrineReport() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__blood__report);
        toolbar = findViewById(R.id.toolbar);
        TextView title = toolbar.findViewById(R.id.txvw_title);
        title.setText(getString(R.string.report_urine));

        ic_back = toolbar.findViewById(R.id.ic_back);
        Bundle b = getIntent().getExtras();

        PTID = b.getString("PTID");
        MTESTID = b.getString("MTESTID");

        ic_back.setOnClickListener(view -> Profile_UrineReport.this.finish());

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

        //MyDynamicToast.informationMessage(this, getString(R.string.loading_blood_report));
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


        String Id = "", Patid = "", Docid = "", Actdate = "", IsActive = "", IsUpdate = "", ServerId = "", Mtestid = "", Testid = "", Subtestid = "", HID = "", IsPaid = "", quantity = "", colour = "", appearance = "", sediment = "", specific_gravity = "", ph_reaction = "", proteins = "", sugar = "", occult_blood = "", acetone = "", bite_salf = "", bile_pigment = "", epithelial_cells = "", pus_cells = "", red_blood_cells = "", any_other_finding = "", amorphous_material = "", trichomans_vaginalis = "", casts = "";


        SQLiteDatabase db = BaseConfig.GetDb();
        String Query = "select * from Bind_urine_test where Patid='" + PTID + "' and Mtestid='" + MTESTID + '\'';
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
                    quantity = c.getString(c.getColumnIndex("quantity"));
                    colour = c.getString(c.getColumnIndex("colour"));
                    appearance = c.getString(c.getColumnIndex("appearance"));
                    sediment = c.getString(c.getColumnIndex("sediment"));
                    specific_gravity = c.getString(c.getColumnIndex("specific_gravity"));
                    ph_reaction = c.getString(c.getColumnIndex("ph_reaction"));
                    proteins = c.getString(c.getColumnIndex("proteins"));
                    sugar = c.getString(c.getColumnIndex("sugar"));
                    occult_blood = c.getString(c.getColumnIndex("occult_blood"));
                    acetone = c.getString(c.getColumnIndex("acetone"));
                    bite_salf = c.getString(c.getColumnIndex("bite_salf"));
                    bile_pigment = c.getString(c.getColumnIndex("bile_pigment"));
                    epithelial_cells = c.getString(c.getColumnIndex("epithelial_cells"));
                    pus_cells = c.getString(c.getColumnIndex("pus_cells"));
                    red_blood_cells = c.getString(c.getColumnIndex("red_blood_cells"));
                    any_other_finding = c.getString(c.getColumnIndex("any_other_finding"));
                    amorphous_material = c.getString(c.getColumnIndex("amorphous_material"));
                    trichomans_vaginalis = c.getString(c.getColumnIndex("trichomans_vaginalis"));
                    casts = c.getString(c.getColumnIndex("casts"));

                } while (c.moveToNext());

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


                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-puzzle-piece fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> " + getString(R.string.profile_urine_physicalexam) + "</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>   " + getString(R.string.profile_urine_quantity) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + quantity + " </td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_sediment) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + sediment + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_colour) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + colour + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_specificgravity) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + specific_gravity + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_appearance) + " </b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + appearance + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-flask fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i> CHEMICAL EXAMINATION</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_phreaction) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + ph_reaction + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_acetone) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + acetone + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_proteins) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + proteins + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_bitesalf) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + bite_salf + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_sugar) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + sugar + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_bitepigment) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + bile_pigment + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_occultblood) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + occult_blood + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "</table>\n" +
                "</div>\n" +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                "<div class=\"table-responsive\"> \n" +
                '\n' +
                "<font class=\"sub\">\n" +
                "<i class=\"fa fa-snowflake-o fa-2x\" \n" +
                "aria-hidden=\"true\">\n" +
                "</i>  " + getString(R.string.profile_urine_microscopic) + "</font>\n" +
                '\n' +
                '\n' +
                "<table class=\"table table-bordered\">\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_epithellial) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + epithelial_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_amorphous) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + amorphous_material + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_puscells) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + pus_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b> " + getString(R.string.profile_urine_trichomansvaginalls) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + trichomans_vaginalis + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_redblood) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + red_blood_cells + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_casts) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + casts + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"></i><b>  " + getString(R.string.profile_urine_anyother) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">   " + any_other_finding + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                "<!---------------------------->\n" +
                '\n' +
                '\n' +
                "<!---------------------------->\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html> ";


        //Toast.makeText(this, "Doctor profile loaded successfully..", Toast.LENGTH_SHORT).show();
        // MyDynamicToast.successMessage(this, getString(R.string.Blood_loaded_success));

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
