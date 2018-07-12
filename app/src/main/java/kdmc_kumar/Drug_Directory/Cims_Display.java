package kdmc_kumar.Drug_Directory;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
 * Created by Vidhya-Android on 4/11/2017.
 */

public class Cims_Display extends AppCompatActivity {
    /**
     * @ Vidhya
     * 13/04/2017
     * Created a new webview to display cims data
     */


    //**********************************************************************************************


    private Toolbar toolbar = null;
    private ImageView back = null;//
    private TextView Title = null;

    private WebView Web_CimsData = null;
    private TextView tvw_System_Name = null;
    private ImageView imgvw_Medicine_Logo = null;

    public Cims_Display() {
    }

    //**********************************************************************************************
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.new_cims_display);


        try {

            GetIniliaze();

            Controllisteners();


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //**********************************************************************************************
    private final void GetIniliaze() {


        toolbar = findViewById(R.id.toolbar);
        back = toolbar.findViewById(R.id.ic_back);
        Title = toolbar.findViewById(R.id.txvw_title);
        Title.setText("CIMS");

        setSupportActionBar(toolbar);


        back.setOnClickListener(view -> LoadBack());

        Web_CimsData = findViewById(R.id.webvw_cims_display);
        tvw_System_Name = findViewById(R.id.txtvw_title);
        imgvw_Medicine_Logo = findViewById(R.id.imgvw_logo);

        Web_CimsData = findViewById(R.id.webvw_cims_display);
        Web_CimsData.getSettings().setJavaScriptEnabled(true);
        Web_CimsData.setWebChromeClient(new WebChromeClient());
        Web_CimsData.getSettings().setJavaScriptEnabled(true);
        Web_CimsData.setLayerType(View.LAYER_TYPE_NONE, null);
        Web_CimsData.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        Web_CimsData.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        Web_CimsData.getSettings().setDefaultTextEncodingName("utf-8");

        Web_CimsData.setWebChromeClient(new MyWebChromeClient());

        Web_CimsData.setBackgroundColor(0x00000000);
        Web_CimsData.setVerticalScrollBarEnabled(true);
        Web_CimsData.setHorizontalScrollBarEnabled(true);

        Toast.makeText(this, getString(R.string.please_wait_drug), Toast.LENGTH_SHORT).show();


        Web_CimsData.getSettings().setJavaScriptEnabled(true);

        Web_CimsData.getSettings().setAllowContentAccess(true);


        Web_CimsData.setOnLongClickListener(v -> true);

        Web_CimsData.setLongClickable(false);


        Web_CimsData.addJavascriptInterface(new WebAppInterface(Cims_Display.this), "android");
        try {

            Web_CimsData.loadDataWithBaseURL("file:///android_asset/", LoadCimsDisplay(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public final void onBackPressed() {
        //
        LoadBack();
    }
    //**********************************************************************************************

    private final String LoadCimsDisplay() {

        String values = "";

        StringBuilder str0 = new StringBuilder();
        StringBuilder str1 = new StringBuilder();
        StringBuilder str2 = new StringBuilder();
        String marketnameofdrug1 = "", cimsdruggeneric_name1 = "", cimsdruggeneric_name2 = "";


        SQLiteDatabase db = BaseConfig.GetDb();

        //Get Main Data
        String Query = "select * from cims1 where serverid='" + BaseConfig.Druglistselindex + '\'';
        Cursor c = db.rawQuery(Query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    String iconchange = (c.getString(c.getColumnIndex("MEDICINEINTAKETYPE")).trim());
                    switch (iconchange) {
                        case "TAB":
                            imgvw_Medicine_Logo.setImageResource(R.drawable.tablet);
                            break;

                        case "SYR":
                            imgvw_Medicine_Logo.setImageResource(R.drawable.syrup);
                            break;

                        case "OINT":
                            imgvw_Medicine_Logo.setImageResource(R.drawable.ointment);
                            break;

                        case "CAP":
                            imgvw_Medicine_Logo.setImageResource(R.drawable.capsule);
                            break;

                        case "INJ":
                            imgvw_Medicine_Logo.setImageResource(R.drawable.injection);
                            break;
                    }

                    cimsdruggeneric_name1 = (c.getString(c.getColumnIndex("DRUGGENERICNAME")));

                    tvw_System_Name.setText(cimsdruggeneric_name1);

                    //Str 0
                    str0.append('\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card-o\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.system)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("SYSTEM"))).append("</td> \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.category_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("DRUGGENERICNAME"))).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.chemical_name_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("CHEMICALNAME"))).append("</td>\n").append("</tr>");


                    //Str1
                    str1.append('\n' + "<tr >\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-id-card-o\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.brand_name_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("MARKETNAMEOFDRUG"))).append("</td> \n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle-o\" aria-hidden=\"true\"></i><b> ").append(getString(R.string.pharma_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("PHARMACOMPANY"))).append("</td> \n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle\" aria-hidden=\"true\"></i><b> ").append(getString(R.string.composition_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("COMPOSITION"))).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-calendar\" aria-hidden=\"true\"></i><b> ").append(getString(R.string.dosage_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("DOSAGE"))).append("</td>\n").append("</tr> \n").append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.medicine_intake_type_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(c.getString(c.getColumnIndex("MEDICINEINTAKETYPE"))).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-mars-stroke\" aria-hidden=\"true\"></i><b>  ").append(getString(R.string.units_per_pack_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("UNITSPERPACK"))).append('-').append(c.getString(c.getColumnIndex("MEDICINEINTAKETYPE"))).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  PRICE MRP</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("PRICEMRP"))).append("</td>\n").append("</tr>");


                    // INDICATIONS AND DOSAGE
                    if (!c.getString(c.getColumnIndex("ORAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tORAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("ORAL")).replace(". ", ".\n").replace(".", ".\n")).append("</td>\n").append("</tr>");

                    }
                    if (!c.getString(c.getColumnIndex("BUCCAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tBUCCAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("BUCCAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("ENDRACHAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tENDRACHAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("ENDRACHAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("EPIDURAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tEPIDURAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("EPIDURAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("EXTRAAMNIOTIC")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tEXTRAAMNIOTIC</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("EXTRAAMNIOTIC"))).append("</td>\n").append("</tr>");
                    }
                    //=======================
                    if (!c.getString(c.getColumnIndex("OPHTHALMIC")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tOPHTHALMIC</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("OPHTHALMIC"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INHALATION")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINHALATION</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INHALATION"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INJECTION")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINJECTION</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INJECTION"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRACORONARY")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRACORONARY</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRACORONARY"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRAMUSCULAR")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAMUSCULAR</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAMUSCULAR"))).append("</td>\n").append("</tr>");
                    }
                    //=======================
                    if (!c.getString(c.getColumnIndex("INTRAPLEURAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAPLEURAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAPLEURAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRATHECAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRATHECAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRATHECAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRAUTERINE")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAUTERINE</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAUTERINE"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRAVENOUS")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAVENOUS</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAVENOUS"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("INTRAARTERIAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAARTERIAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAARTERIAL"))).append("</td>\n").append("</tr>");
                    }
                    //=======================
                    if (!c.getString(c.getColumnIndex("INTRAARTICULAR")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tINTRAARTICULAR</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("INTRAARTICULAR"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("IRRIGATION")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tIRRIGATION</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("IRRIGATION"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("NASEL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tNASEL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("NASEL"))).append("</td>\n").append("</tr>");
                    }

                    if (!c.getString(c.getColumnIndex("PARENTERAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tPARENTERAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("PARENTERAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("RECTAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tRECTAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("RECTAL"))).append("</td>\n").append("</tr>");
                    }
                    //=======================
                    if (!c.getString(c.getColumnIndex("SUBCUTANEOUS")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tSUBCUTANEOUS</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("SUBCUTANEOUS"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("SUBLINGUAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tSUBLINGUAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("SUBLINGUAL"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("TOPICALCUTANEOUS")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tTOPICALCUTANEOUS</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("TOPICALCUTANEOUS"))).append("</td>\n").append("</tr>");
                    }
                    if (!c.getString(c.getColumnIndex("TRANSDERMAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tTRANSDERMAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("TRANSDERMAL"))).append("</td>\n").append("</tr>");
                    }

                    if (!c.getString(c.getColumnIndex("VAGINAL")).isEmpty()) {

                        str2.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tVAGINAL</b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(c.getString(c.getColumnIndex("VAGINAL"))).append("</td>\n").append("</tr>");
                    }


                    marketnameofdrug1 = c.getString(c.getColumnIndex("MARKETNAMEOFDRUG"));

                } while (c.moveToNext());
            }
        }


        c.close();


        Cursor c1 = db
                .rawQuery(
                        "select distinct CHEMICALNAME,MARKETNAMEOFDRUG from cims1 where MARKETNAMEOFDRUG='"
                                + marketnameofdrug1 + '\'', null);
        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {
                    cimsdruggeneric_name2 = (c1.getString(c1
                            .getColumnIndex("CHEMICALNAME")));


                    break;
                } while (c1.moveToNext());

            }
        }

        c1.close();


        //get data 3
        StringBuilder str6 = new StringBuilder();
        Resources res = getResources();

        String Query3 = "select genericname,Roa,Category from pregnaacysafety where genericname like '%" + cimsdruggeneric_name2 + "%' order by id";
        Cursor c3 = db.rawQuery(Query3, null);
        if (c3 != null) {
            if (c3.moveToFirst()) {
                do {

                    /*str6.append("<tr>\n" +
                            "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                            "<td height=\"20\" style=\"color:#000000;\">:   "+c.getString(c.getColumnIndex("Roa"))+"</td>\n" +
                            "</tr>");*/

                    String category_chk = (c3.getString(c3.getColumnIndex("Category")));

                    String str = "";

                    if (category_chk.equalsIgnoreCase("A")) {

                      /*  str6.append("Category : "+ String.format(res.getString(R.string.Category_A)));
                        str6.append(c2.getString(c2.getColumnIndex("Roa")));
                        str6.append("\n");*/

                        str = "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\t" + getString(R.string.category_txt) + "</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + res.getString(R.string.Category_A) + "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + c3.getString(c3.getColumnIndex("Roa")) + "</td>\n" +
                                "</tr>\n";

                    } else if (category_chk.equalsIgnoreCase("B")) {

                        str = "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\t" + getString(R.string.category_txt) + "</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + res.getString(R.string.Category_B) + "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + c3.getString(c3.getColumnIndex("Roa")) + "</td>\n" +
                                "</tr>\n";


                    } else if (category_chk.equalsIgnoreCase("C")) {

                        str = "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\t" + getString(R.string.category_txt) + "</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + res.getString(R.string.Category_C) + "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + c3.getString(c3.getColumnIndex("Roa")) + "</td>\n" +
                                "</tr>\n";

                    } else if (category_chk.equalsIgnoreCase("D")) {

                        str = "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\t" + getString(R.string.category_txt) + "</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + res.getString(R.string.Category_D) + "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + c3.getString(c3.getColumnIndex("Roa")) + "</td>\n" +
                                "</tr>\n";


                    } else if (category_chk.equalsIgnoreCase("X")) {

                        str = "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\t" + getString(R.string.category_txt) + "</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + res.getString(R.string.Category_X) + "</td>\n" +
                                "</tr>\n" +
                                "<tr>\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>\tROA</b></td> \n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + c3.getString(c3.getColumnIndex("Roa")) + "</td>\n" +
                                "</tr>\n";

                    }


                    str6.append(str);


                } while (c3.moveToNext());
            }
        }


        //get data 4


        StringBuilder str8 = new StringBuilder();

        String Query4 = "select Generic,Foodcode,Additionalcomments from postprandialadvice where Generic like '%" + cimsdruggeneric_name2 + "%' order by id";
        Cursor c2 = db.rawQuery(Query4, null);
        if (c2 != null) {
            if (c2.moveToFirst()) {
                do {

                    String foodcodes = (c2.getString(c2.getColumnIndex("Foodcode")));
                    String str = "-";


                    if (foodcodes.equalsIgnoreCase("1")) {
                        str = "Take with food";
                    } else if (foodcodes.equalsIgnoreCase("2")) {
                        str = "Take on empty stomach";
                    } else if (foodcodes.equalsIgnoreCase("3")) {
                        str = "Take with or without food";
                    }


                    str8.append("<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\t").append(getString(R.string.post_food)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>\t").append(getString(R.string.remarks_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(c2.getString(c2.getColumnIndex("Additionalcomments"))).append("</td>\n").append("</tr>\n").append(' ');


                    break;


                } while (c2.moveToNext());
            }
        }

        db.close();


        String values1 = "<!DOCTYPE html>\n" + "\n" + "<html lang=\"en\">\n" + "<head>\n" + "\n" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" + "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" + "\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" + "\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" + "\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" + "\n" + "\n" + "\n" + "\n" + "</head>\n" + "<body>  \n" + " \n" + "\n" + "\n" + "<div >\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> " + getString(R.string.system) + "</font>\n" + "\n" + "\n" + "<div class=\"bs-example\">\n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "" + str0.toString() + "\n" + "\n" + "\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div>\n" + " \n" + "<!----------------------------------------------------------------->\n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "" + str1.toString() + "\n" + "\n" + "\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div>\n" + "\n" + "\n" + "<br/>\n" + "<!--------------------------------------------------------------------->\n" + "\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-user fa-2x\" aria-hidden=\"true\"></i>\t" + getString(R.string.indications_and_dosage) + "</font>\n" + "\n" + "\n" + "<div class=\"bs-example\">\n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "" + str2.toString() + "\n" + "\n" + "\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div>\n" + "\n" + "\n" + "\n" + "<!----------------------------------------------------------------->\n" + "\n" + "<br/>\n" + "<!----------------------------------------------------------------->\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-credit-card-alt fa-2x\" aria-hidden=\"true\"></i>\tPre and Post Prandial Advice</font>\n" + "\n" + "\n" + "<div class=\"bs-example\">\n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "\n" + "" + str8.toString() + "\n" + "\n" + "\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div> \n" + "<br/> \n" + "<!----------------------------------------------------------------->\n" + "<!----------------------------------------------------------------->\n" + "\n" + "<font class=\"sub\"><i class=\"fa fa-credit-card-alt fa-2x\" aria-hidden=\"true\"></i>\tSafety Alerts - Pregnancy</font>\n" + "\n" + "\n" + "<div class=\"bs-example\">\n" + "\n" + "\n" + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "\n" + "\n" + "\n" + str6.toString() + "\n" + "\n" + "\n" + "\n" + "\n" + "</tbody>\n" + "</table>\n" + "\n" + "</div>\n" + "\n" + "\n" + "\n" + "<!----------------------------------------------------------------->\n" + "\n" + "<br/>\n" + "</body>\n" + "</html>                             ";


        return values1;

    }

    private final void LoadBack() {
        Cims_Display.this.finish();
        BaseConfig.Druglistselindex = "";

    }
    //**********************************************************************************************

    private void Controllisteners()

    {


    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    //**********************************************************************************************

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
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

        /*   <3<3        <3<3    <3<3<3<3<3<3   <3<3<3<3<3<3<3<3   <3<3        <3<3    <3<3      <3<3  <3<3<3<3<3<3<3<3
             <3<3        <3<3    <3<3<3<3<3<3   <3<3<3<3<3<3<3<3   <3<3        <3<3    <3<3      <3<3  <3<3<3<3<3<3<3<3
             <3<3        <3<3        <3<3         <3<3      <3<3   <3<3        <3<3    <3<3      <3<3  <3<3        <3<3
             <3<3        <3<3        <3<3         <3<3      <3<3   <3<3<3<3<3<3<3<3    <3<3<3<3<3<3<3  <3<3<3<3<3<3<3<3
             <3<3        <3<3        <3<3         <3<3      <3<3   <3<3<3<3<3<3<3<3    <3<3<3<3<3<3<3  <3<3<3<3<3<3<3<3
             <3<3        <3<3        <3<3         <3<3      <3<3   <3<3        <3<3              <3<3  <3<3        <3<3
               <3<3    <3<3      <3<3<3<3<3<3   <3<3<3<3<3<3<3<3   <3<3        <3<3    <3<3<3<3<3<3<3  <3<3        <3<3
                 <3<3<3<3        <3<3<3<3<3<3   <3<3<3<3<3<3<3<3   <3<3        <3<3    <3<3<3<3<3<3<3  <3<3        <3<3  */

    //**********************************************************************************************
}
