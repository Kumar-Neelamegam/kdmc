package kdmc_kumar.Doctor_Modules;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;

/**
 * Created by Android on 3/29/2017.
 * Muthukumar N
 * 29/03/2017
 * New Webview Doctor Profile
 */

public class Doctor_Profile extends AppCompatActivity {

    //#######################################################################################################

    /**
     * Declarations
     *
     * @param savedInstanceState
     */
    private ImageView ic_previous = null;
    private ImageView doctor_photo = null;
    private ImageView doctor_sign = null;
    private WebView profile_webvw = null;
    private Toolbar toolbar = null;

    public Doctor_Profile() {
    }


    //#######################################################################################################
    /*Oncreate Methods*/
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_doctor_profile);

        try {

            GetInitialize();

            Controllisteners();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################
    private final void GetInitialize() {

        setSupportActionBar(toolbar);

        profile_webvw = findViewById(R.id.webvw_profile);

        profile_webvw.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        profile_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        profile_webvw.setOnLongClickListener(v -> true);

        profile_webvw.setLongClickable(false);

        toolbar = findViewById(R.id.toolbar);
        ic_previous = toolbar.findViewById(R.id.ic_back);


        doctor_photo = findViewById(R.id.imgvw_doctor_photo);
        doctor_sign = findViewById(R.id.imgvw_doctor_sign);

        try {
            LoadWebview();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    //#######################################################################################################
    private final void Controllisteners() {

        ic_previous.setOnClickListener(view -> {


            Doctor_Profile.this.finish();
            Intent taskselect = new Intent(Doctor_Profile.this, Dashboard_NavigationMenu.class);
            startActivity(taskselect);
            Doctor_Profile.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top


        });

    }

    //#######################################################################################################
    private final void LoadWebview() {
        profile_webvw.getSettings().setJavaScriptEnabled(true);
        profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        profile_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        profile_webvw.setWebChromeClient(new MyWebChromeClient());

        profile_webvw.setBackgroundColor(0x00000000);
        profile_webvw.setVerticalScrollBarEnabled(true);
        profile_webvw.setHorizontalScrollBarEnabled(true);

       Toast.makeText(this, "Please wait doctor profile loading..", Toast.LENGTH_SHORT).show();

      //  BaseConfig.SnackBar(this,  "Please wait doctor profile loading..", profile_webvw);


        profile_webvw.getSettings().setJavaScriptEnabled(true);

        profile_webvw.getSettings().setAllowContentAccess(true);

        profile_webvw.addJavascriptInterface(new WebAppInterface(this), "android");
        try {

            profile_webvw.loadDataWithBaseURL("file:///android_asset/", LoadDoctorProfile(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // profile_webvw.loadUrl("file:///android_asset/Doctor_Profile/index.html"); //Loading from  assets


    }
    //#######################################################################################################

    private final String LoadDoctorProfile() {
        String values = "";

        String Name = "", Gender = "", DOB = "", Age = "", Qualification = "", Specialization = "", MCI = "", PAN = "", ServTax = "", Mobile1 = "", Mobile2 = "", Email = "";

        String ClinicalName = "", ClinicPhone = "", ServTaxClinic = "", Add1 = "", Add2 = "", Country = "", State = "", City = "", Pincode = "";

        String str_Add1 = "", str_Add2 = "", str_Country = "", str_State = "", str_City = "", str_Pincode = "";

        String Onlineurpatient = "No", Onlineoutpatient = "No";

        String HID = "";

        String Doc_ID = "";


        SQLiteDatabase db = BaseConfig.GetDb();//Doctor_Profile.this);

        @Nullable Cursor c = db.rawQuery("SELECT HID,Docid,prefix,drname,name,cliname,age,gender,DOB,PAN,MCI,RegId,ClinicTaxNo,TaxNo,Academic,Specialization,Country,State,District,Address,Address2,pincode,Lanline,Mobile," +
                "Homephone,Actdate,Imei,docimage,IsActive,emailid,docsign,promotion,MAC,AppLicenseCode,OnlineFee,OnlineFeeOthers from Drreg where Docid='" + BaseConfig.doctorId + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    // String Prefix = c.getString(c.getColumnIndex("prefix"));

                    //String Set 1
                    HID = c.getString(c.getColumnIndex("HID"));
                    Doc_ID = checkNullEmpty(c.getString(c.getColumnIndex("Docid")));
                    Name = checkNullEmpty(c.getString(c.getColumnIndex("name")));
                    Gender = (c.getString(c.getColumnIndex("gender")));
                    DOB = checkNullEmpty(c.getString(c.getColumnIndex("DOB"))).split("T")[0];

                    if (DOB.contains("1900")) {
                        DOB = "-";
                    }

                    Age = checkNullEmpty(c.getString(c.getColumnIndex("age")));
                    Qualification = checkNullEmpty(c.getString(c.getColumnIndex("Academic")));
                    Specialization = checkNullEmpty(c.getString(c.getColumnIndex("Specialization")));
                    MCI = checkNullEmpty(c.getString(c.getColumnIndex("MCI")));
                    PAN = checkNullEmpty(c.getString(c.getColumnIndex("PAN")));
                    ServTax = checkNullEmpty(c.getString(c.getColumnIndex("TaxNo")));
                    Mobile1 = checkNullEmpty(c.getString(c.getColumnIndex("Mobile")));
                    Mobile2 = checkNullEmpty(c.getString(c.getColumnIndex("Homephone")));
                    Email = checkNullEmpty(c.getString(c.getColumnIndex("emailid")));


                    //String Set 3
                    String onlineFee_txt = (c.getString(c.getColumnIndex("OnlineFee")));
                    String onlineFeeOthers_txt = (c.getString(c.getColumnIndex("OnlineFeeOthers")));
                    if (!onlineFee_txt.isEmpty()) {
                        Onlineurpatient = "Yes";
                    }
                    if (!onlineFeeOthers_txt.isEmpty()) {
                        Onlineoutpatient = "Yes";
                    }


                    String DoctorPhoto = c.getString(c.getColumnIndex("docimage"));
                    String DoctorSign = c.getString(c.getColumnIndex("docsign"));

                    if (DoctorPhoto != null && DoctorPhoto.length() > 0) {
                        BaseConfig.LoadPatientImage(DoctorPhoto, doctor_photo, 100);

                    }
                    if (DoctorSign != null && DoctorSign.length() > 0) {
                        BaseConfig.LoadPatientImage(DoctorSign, doctor_sign, 100);

                    }

                    str_Add1 = checkNullEmpty(c.getString(c.getColumnIndex("Address")));
                    str_Country = checkNullEmpty(c.getString(c.getColumnIndex("Country")));
                    str_State = checkNullEmpty(c.getString(c.getColumnIndex("State")));
                    str_City = checkNullEmpty(c.getString(c.getColumnIndex("District")));
                    str_Pincode = checkNullEmpty(c.getString(c.getColumnIndex("pincode")));


                } while (c.moveToNext());
            }

        }




        /*
        Get Clinical Information
         */
        Cursor c2 = null;

        Cursor c1 = db.rawQuery("SELECT Hospital_Name,(Phone||','||Mobile) as Number,ServiceTax,Address,Country,State,City,Pincode from Mstr_MultipleHospital where ServerId='" + HID + '\'', null);

        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {

                    //String Set 2
                    ClinicalName = checkNullEmpty(c1.getString(c1.getColumnIndex("Hospital_Name")));
                    ClinicPhone = BaseConfig.CheckDBString(checkNullEmpty(c1.getString(c1.getColumnIndex("Number"))));
                    ServTaxClinic = checkNullEmpty(c1.getString(c1.getColumnIndex("ServiceTax")));
                    Add1 = checkNullEmpty(c1.getString(c1.getColumnIndex("Address")));
                    Country = checkNullEmpty(c1.getString(c1.getColumnIndex("Country")));
                    State = checkNullEmpty(c1.getString(c1.getColumnIndex("State")));
                    City = checkNullEmpty(c1.getString(c1.getColumnIndex("City")));
                    Pincode = checkNullEmpty(c1.getString(c1.getColumnIndex("Pincode")));


                } while (c1.moveToNext());
            }

        }


         values="<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\">\n" +
                "        .column {\n" +
                "            float: left;\n" +
                "            width: 50%;\n" +
                "            padding: 10px;\n" +
                "           \n" +
                "        }\n" +
                "        .w3-card-4 {\n" +
                "            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);\n" +
                "        }\n" +
                "\n" +
                "       \n" +
                "    </style>\n" +
                "    <link rel=\"stylesheet\" href=\"file:///android_asset/new/w3.css\"/>\n" +
                "    <link rel=\"stylesheet\" href=\"file:///android_asset/new/fonts/font-awesome.min.css\"/>\n" +
                "    \n" +
                "   \n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"w3-container\">\n" +
                "        <p></p>\n" +
                "\n" +
                "        <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-user-circle-o fa-la\"></i> "+getString(R.string.personal_details) +"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user-md fa-la\" style=\"color:#2196f3;\"></i> <b>"+ getString(R.string.practitioner_id)+"</b></h6>\n" +
                "                        <p>"+Doc_ID +"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user-md fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.practitioner_name)+"</b></h6>\n" +
                "                        <p>"+Name+"</p>\n" +
                "                    </div>\n" +
                "                  \n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-mars-stroke fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.gender)+"</b></h6>\n" +
                "                        <p>"+Gender +"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-birthday-cake fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.date_of_birth)+"</b></h6>\n" +
                "                        <p>"+DOB +"</p>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-calendar fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.age)+"</b></h6>\n" +
                "                        <p>"+Age+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-graduation-cap fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.academic_qualification)+"</b></h6>\n" +
                "                        <p>"+Qualification+"</p>\n" +
                "                    </div>\n" +
                "                    \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-book fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.splin)+"</b></h6>\n" +
                "                        <p>"+Specialization+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff\">\n" +
                "                        <h6><i class=\"fa fa-registered fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.mci_registration_no)+"</b></h6>\n" +
                "                        <p>"+MCI+"</p>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-address-card-o fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.pan)+"</b></h6>\n" +
                "                        <p>"+PAN+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-money fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.service_tax_no) +"</b></h6>\n" +
                "                        <p>"+ServTax+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-mobile fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.mobile_no1) +"</b></h6>\n" +
                "                        <p>"+Mobile1+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff\">\n" +
                "                        <h6><i class=\"fa fa-mobile fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.mobile_no2)+"</b></h6>\n" +
                "                        <p>"+Mobile2+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-envelope fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.email)+"</b></h6>\n" +
                "                        <p>"+Email+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-address-card fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.address1)+"</b></h6>\n" +
                "                        <p>"+Add1+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-address-card fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.address2)+"</b></h6>\n" +
                "                        <p>"+Add2+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff\">\n" +
                "                        <h6><i class=\"fa fa-globe fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.country)+"</b></h6>\n" +
                "                        <p>"+Country+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-address-card fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.state) +"</b></h6>\n" +
                "                        <p>"+State+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE\">\n" +
                "                        <h6><i class=\"fa fa-globe fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.pincode)+"</b></h6>\n" +
                "                        <p>"+Pincode+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\n" +
                "\t\t\t\t\n" +
                "            </div>\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "          \n" +
                "      \n" +
                "</div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "<br>\n" +
                "\n" +
                "        <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-address-card fa-la\"></i>"+getString(R.string.clinicaldetail)+"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-arrow-right fa-la\" style=\"color:#2196f3;\"></i> <b>"+ getString(R.string.clinicname) +"</b></h6>\n" +
                "                        <p>"+ClinicalName+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-phone fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.clinicphnm) +"</b></h6>\n" +
                "                        <p>"+ClinicPhone+"</p>\n" +
                "                    </div>\n" +
                "                  \n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-arrow-right fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.service_tax_no)+"</b></h6>\n" +
                "                        <p>"+ServTaxClinic+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-address-card fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.address1)+"</b></h6>\n" +
                "                        <p>"+str_Add1+"</p>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-address-card fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.address2)+"</b></h6>\n" +
                "                        <p>"+str_Add2+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-globe fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.country)+"</b></h6>\n" +
                "                        <p>"+str_Country+"</p>\n" +
                "                    </div>\n" +
                "                    \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-globe fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.state) +"</b></h6>\n" +
                "                        <p>"+str_State+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff\">\n" +
                "                        <h6><i class=\"fa fa-globe fa-la\" style=\"color: #2196f3;\"></i> <b>"+getString(R.string.city)+"</b></h6>\n" +
                "                        <p>"+str_City+"</p>\n" +
                "                    </div>\n" +
                "                   \n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-map-pin fa-la\" style=\"color: #2196f3;\"></i> <b>"+ getString(R.string.pincode) +"</b></h6>\n" +
                "                        <p>"+str_Pincode+"</p>\n" +
                "                    </div>\n" +
                "                    \n" +
                "\n" +
                "                </div>\n" +
                "               \n" +
                "                </div>\n" +
                "\t\t\n" +
                "\t\t\t\t\n" +
                "            </div>\n" +
                "\t\t\t\n" +
                "\t\t\t\n" +
                "\t\t\t<BR>\n" +
                "\t\t\t\n" +


                "     <br>     \n" +
                "      \n" +
                "</div>\n" +
                "\n" +
                "    </div>\n" +
                "\t\n" +
                "\t\n" +
                "\t\n" +
                "\t\n" +
                "</body>\n" +
                "</html>\n";

        c1.close();
        db.close();

        Toast.makeText(this, "Doctor profile loaded successfully..", Toast.LENGTH_SHORT).show();
        //MyDynamicToast.successMessage(this, "Doctor profile loaded successfully..");

       // BaseConfig.SnackBar(this,  "Doctor profile loaded successfully....", profile_webvw);

        return values;
    }
    //#######################################################################################################

    /*******
     * @Author Ponnusamy
     * *******/
    private static final String checkNullEmpty(String value) {
        return value == null || value.isEmpty() || value.equals("null") || value.equalsIgnoreCase("null") ? "-" : value;
    }
//#######################################################################################################

    // TODO: 3/9/2017 DOB Date formate parse
    public static final String getDOBDate(String datetime) {
        return "-";
    }
//#######################################################################################################
    // TODO: 3/9/2017 Check Is Empty and Null

    public static final void Convert64ToImage(String base64, ImageView img) {
        // BaseConfig.Zoomimg=base64;

        byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);

        // setting the decodedByte to ImageView
        img.setImageBitmap(decodedByte);


    }

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        //
        Doctor_Profile.this.finish();
        Intent taskselect = new Intent(Doctor_Profile.this, Dashboard_NavigationMenu.class);
        startActivity(taskselect);
        Doctor_Profile.this.overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);//Bottom to Top

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
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

    //#######################################################################################################

    static class JavaScriptInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        JavaScriptInterface(Context c) {
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


    //#######################################################################################################

}
