package kdmc_kumar.MyPatient_Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Android on 3/31/2017.
 */

public class Profile_Patient extends Fragment {


    //#######################################################################################################

//http://www.explorehacking.com/2011/01/batch-files-art-of-creating-viruses.html
    /**
     * Declarations
     * Muthukumar N
     * 31-3-2017
     *
     * @param savedInstanceState
     */
    ImageView ic_previous = null;
   // private CircleImageView patient_photo = null;

    private WebView profile_webvw = null;
    Toolbar toolbar = null;

    private String BUNDLE_PATIENT_ID = null;

    //#######################################################################################################
    /*Oncreate Methods*/
    //#######################################################################################################
    private String str_PatientName = null;
    private String str_patientId = null;
    private String str_Agegender = null;

    public Profile_Patient() {
    }

    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.new_patient_profile, container, false);

        try {

            Bundle args = getArguments();
            BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

            GetInitialize(rootView);

         //   Controllisteners(rootView);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    //#######################################################################################################
    private final void GetInitialize(View rootView) {


        profile_webvw = rootView.findViewById(R.id.webvw_profile);

        profile_webvw.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        profile_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        profile_webvw.setOnLongClickListener(v -> true);

        profile_webvw.setLongClickable(false);


       // patient_photo = rootView.findViewById(R.id.imgvw_patient_photo);

        try {
            LoadWebview();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    private final void Controllisteners(View rootView) {

     //   patient_photo.setOnClickListener(view -> BaseConfig.Show_Patient_PhotoInDetail(str_PatientName, str_patientId, str_Agegender, getActivity()));


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

        // Toast.makeText(this, "Please wait doctor profile loading..", Toast.LENGTH_SHORT).show();

        //MyDynamicToast.informationMessage(getActivity(), getString(R.string.pleasewait_patient_loading));

        profile_webvw.getSettings().setJavaScriptEnabled(true);

        profile_webvw.getSettings().setAllowContentAccess(true);


        //Add Android Method to Java Script Class


        profile_webvw.addJavascriptInterface(new WebAppInterface(getActivity()), "android");
        try {

            profile_webvw.loadDataWithBaseURL("file:///android_asset/", LoadPatientProfile(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        // profile_webvw.loadUrl("file:///android_asset/Doctor_Profile/index.html"); //Loading from  assets


    }
    //#######################################################################################################

    private final String LoadPatientProfile() {

        String values = "";

        String PatientName = "", PatientId = "", contactNoStr = "", RelationshipStr = "", CityStr = "", AgeStr = "",
                GenderStr = "", DateOfBirth = "", Weight = "", CountryStr = "", StateStr = "", DistrictStr = "", Address1Str = "", Address2Str = "",
                PincodeStr = "", MobileNo1Str = "", MobileNo2Str = "", EmailStr = "", careTakerDetailsStr = "", BloodGroupStr = "",
                policyNameStr = "", NameOfCompany = "", AmountInsured = "", ValidityStr = "", FeeExemptionStr = "",
                FeeExemptionCategoryStr = "", BplCardNoStr = "", AadharCardNoStr = "", FeeExemptionReasonStr = "",
                caste = "", Income = "", referredBy = "", Occupation = "", FatherName = "", ProfileImage = "", AuthorisedHospitalsStr = "", Spouse = "";


        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        Cursor c = db.rawQuery("select * from Patreg where Patid='" + BUNDLE_PATIENT_ID + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    PatientName = checkNullEmpty(c.getString(c.getColumnIndex("name")));
                    PatientId = checkNullEmpty(c.getString(c.getColumnIndex("Patid")));
                    contactNoStr = checkNullEmpty(c.getString(c.getColumnIndex("crtknum")));
                    RelationshipStr = checkNullEmpty(c.getString(c.getColumnIndex("relationship")));

                    CityStr = checkNullEmpty(c.getString(c.getColumnIndex("city")));
                    AgeStr = checkNullEmpty(c.getString(c.getColumnIndex("age")));
                    GenderStr = checkNullEmpty(c.getString(c.getColumnIndex("gender")));

                    str_PatientName = PatientName;
                    str_patientId = PatientId;
                    str_Agegender = AgeStr + " - " + GenderStr;

                    DateOfBirth = checkNullEmpty(c.getString(c.getColumnIndex("DOB"))).split(" ")[0];

                    if (DateOfBirth.contains("1900")) {
                        DateOfBirth = "-";
                    }

                    CountryStr = checkNullEmpty(c.getString(c.getColumnIndex("Country")));
                    StateStr = checkNullEmpty(c.getString(c.getColumnIndex("State")));
                    DistrictStr = checkNullEmpty(c.getString(c.getColumnIndex("District")));
                    Address1Str = checkNullEmpty(c.getString(c.getColumnIndex("Address")));
                    Address2Str = checkNullEmpty(c.getString(c.getColumnIndex("Address1")));
                    PincodeStr = checkNullEmpty(c.getString(c.getColumnIndex("pincode")));
                    MobileNo1Str = checkNullEmpty(c.getString(c.getColumnIndex("phone")));
                    MobileNo2Str = checkNullEmpty(c.getString(c.getColumnIndex("altphone")));
                    EmailStr = checkNullEmpty(c.getString(c.getColumnIndex("email")));
                    careTakerDetailsStr = checkNullEmpty(c.getString(c.getColumnIndex("caretaker")));
                    contactNoStr = checkNullEmpty(c.getString(c.getColumnIndex("crtknum")));
                    RelationshipStr = checkNullEmpty(c.getString(c.getColumnIndex("relationship")));
                    BloodGroupStr = checkNullEmpty(c.getString(c.getColumnIndex("bloodgroup")));
                    policyNameStr = checkNullEmpty(c.getString(c.getColumnIndex("policyname")));
                    NameOfCompany = checkNullEmpty(c.getString(c.getColumnIndex("insurancecompany")));
                    AmountInsured = checkNullEmpty(c.getString(c.getColumnIndex("amountinsured")));
                    ValidityStr = checkNullEmpty(c.getString(c.getColumnIndex("validity")));
                    AuthorisedHospitalsStr = checkNullEmpty(c.getString(c.getColumnIndex("inshosp")));
                    FeeExemptionStr = checkNullEmpty(c.getString(c.getColumnIndex("IsFeeExemp")));
                    FeeExemptionCategoryStr = checkNullEmpty(c.getString(c.getColumnIndex("FeeExempCateg")));

                    if (FeeExemptionCategoryStr.equalsIgnoreCase("0")) {
                        FeeExemptionCategoryStr = "-";
                    }

                    BplCardNoStr = checkNullEmpty(c.getString(c.getColumnIndex("bplCardNo")));
                    AadharCardNoStr = checkNullEmpty(c.getString(c.getColumnIndex("AadharCardNo")));
                    FeeExemptionReasonStr = checkNullEmpty(c.getString(c.getColumnIndex("FeeExemptionRemarks")));
                    caste = checkNullEmpty(c.getString(c.getColumnIndex("caste")));
                    Income = checkNullEmpty(c.getString(c.getColumnIndex("income")));
                    referredBy = checkNullEmpty(c.getString(c.getColumnIndex("doc_refer_name")));
                    Occupation = checkNullEmpty(c.getString(c.getColumnIndex("occupation")));
                    FatherName = checkNullEmpty(c.getString(c.getColumnIndex("FatherName")));
                    ProfileImage = checkNullEmpty(c.getString(c.getColumnIndex("PC")));
                    Spouse = checkNullEmpty(c.getString(c.getColumnIndex("spouse")));

                    //Convert64ToImage(ProfileImage, patient_photo);
                    //Toast.makeText(getActivity(), "URL: "+ ProfileImage , Toast.LENGTH_SHORT).show();

                    /*if (ProfileImage.length() == 0) {

                        BaseConfig.Glide_LoadDefaultImageView(patient_photo);

                    } else {
                        BaseConfig.LoadPatientImage(ProfileImage, patient_photo, 100);
                    }*/


                } while (c.moveToNext());
            }

        }



        values="<!DOCTYPE html>\n" +
                "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "\n" +
                "<head>\n" +
                "    <title></title>\n" +
                "    <style type=\"text/css\">\n" +
                "        .column {\n" +
                "            float: left;\n" +
                "            width: 50%;\n" +
                "            padding: 10px;\n" +
                "        }\n" +
                "        \n" +
                "        .w3-card-4 {\n" +
                "            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19);\n" +
                "        }\n" +
                "    </style>\n" +
                "    <link rel=\"stylesheet\" href=\"file:///android_asset/new/w3.css\"/>\n" +
                "    <link rel=\"stylesheet\" href=\"file:///android_asset/new/fonts/font-awesome.min.css\"/>\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "    <div class=\"w3-container\">\n" +
                "        <p></p>\n" +
                "\n" +
                "        <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-user-circle-o\"></i> "+getString(R.string.txt_patients_details)+"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user-md fa-la\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_patient_id)+"</b></h6>\n" +
                "                        <p>"+PatientId+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user-md\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_name)+"</b></h6>\n" +
                "                        <p>"+PatientName+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-mars-stroke\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_father_name)+"</b></h6>\n" +
                "                        <p>"+FatherName+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-birthday-cake\" style=\"color: #3d5987;\"></i> <b>"+ getString(R.string.txt_spouse) +"</b></h6>\n" +
                "                        <p>"+Spouse+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-calendar\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_date_of_birth) +"</b></h6>\n" +
                "                        <p>"+DateOfBirth+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-graduation-cap\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_age)+"</b></h6>\n" +
                "                        <p>"+AgeStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-mars-stroke\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_gender)+"</b></h6>\n" +
                "                        <p>"+GenderStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff\">\n" +
                "                        <h6><i class=\"fa fa-registered\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_blood_group)+"</b></h6>\n" +
                "                        <p>"+BloodGroupStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #FDFEFE; \">\n" +
                "                        <h6><i class=\"fa fa-address-card-o\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_caste)+"</b></h6>\n" +
                "                        <p>"+caste+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <br>\n" +
                "\n" +
                "        <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-address-book\"></i> "+ getString(R.string.txt_patients_address)+"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-address-card\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_address1)+"</b></h6>\n" +
                "                        <p>"+Address1Str+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-address-card\" style=\"color: #3d5987;\"></i> <b>"+ getString(R.string.txt_address2)+"</b></h6>\n" +
                "                        <p>"+Address2Str+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-globe\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_state)+"</b></h6>\n" +
                "                        <p>"+StateStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-globe\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_city)+"</b></h6>\n" +
                "                        <p>"+CityStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-globe\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_country)+"</b></h6>\n" +
                "                        <p>"+CountryStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-map-pin\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_pincode)+"</b></h6>\n" +
                "                        <p>"+PincodeStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-phone\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_mobile_no1)+"</b></h6>\n" +
                "                        <p>"+MobileNo1Str+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-phone\" style=\"color: #3d5987;\"></i> <b>"+ getString(R.string.txt_mobile_no2)+"</b></h6>\n" +
                "                        <p>"+MobileNo2Str+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "                <div class=\"row\">\n" +
                "                  <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-envelope\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.docemail) +"</b></h6>\n" +
                "                        <p>"+EmailStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\n" +
                "        <BR>\n" +
                "\n" +
                "        <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-user-circle-o\"></i> "+getString(R.string.txt_care_taker_details) +"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_care_take_dependentname) +"</b></h6>\n" +
                "                        <p>"+careTakerDetailsStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-phone\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_contact_number)+"</b></h6>\n" +
                "                        <p>"+contactNoStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_relationship)+"</b></h6>\n" +
                "                        <p>"+RelationshipStr+"</p>\n" +
                "\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\t\t      <BR>\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-user-circle-o\"></i> "+ getString(R.string.txt_insurance_details) +"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color:#3d5987;\"></i> <b>"+getString(R.string.txt_policy_name)+"</b></h6>\n" +
                "                        <p>"+policyNameStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_nameoftheinsurancecompany) +"</b></h6>\n" +
                "                        <p>"+NameOfCompany+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-money\" style=\"color:#3d5987;\"></i> <b>"+getString(R.string.txt_amount_insured)+"</b></h6>\n" +
                "                        <p>"+AmountInsured+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_validity)+"</b></h6>\n" +
                "                        <p>"+ValidityStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_authorised_hospitals)+"</b></h6>\n" +
                "                        <p>"+AuthorisedHospitalsStr+"</p>\n" +
                "\n" +
                "                    </div>\n" +
                "                </div>\n" +
                "\n" +
                "            </div>\n" +
                "\n" +
                "        </div>\n" +
                "\t\t\n" +
                "\t\t      <BR>\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t\n" +
                "\t\t <div class=\"w3-card-4\" style=\"width:100%;\">\n" +
                "            <header class=\"w3-container w3-blue\">\n" +
                "                <h4><i class=\"fa fa-user-circle-o\"></i> "+getString(R.string.txt_other_details)+"</h4>\n" +
                "            </header>\n" +
                "\n" +
                "            <div class=\"w3-container\">\n" +
                "                <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color:#3d5987;\"></i> <b>"+getString(R.string.txt_fee_exemption)+"</b></h6>\n" +
                "                        <p>"+FeeExemptionStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_fee_exemption_category) +"</b></h6>\n" +
                "                        <p>"+FeeExemptionCategoryStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-id-card\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_bpl_card_no)+"</b></h6>\n" +
                "                        <p>"+BplCardNoStr+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-id-card\" style=\"color: #3d5987;\"></i> <b>"+ getString(R.string.txt_aadhar_card_no)+"</b></h6>\n" +
                "                        <p>"+AadharCardNoStr+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t \n" +
                "\t\t\t\t \n" +
                "\t\t\t\t  <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color:#FDFEFE;\">\n" +
                "                        <h6><i class=\"fa fa-money\" style=\"color:#3d5987;\"></i> <b>"+ getString(R.string.txt_income) +"</b></h6>\n" +
                "                        <p>"+Income+"</p>\n" +
                "                    </div>\n" +


                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t <div class=\"row\">\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-hand-o-right\" style=\"color:#3d5987;\"></i> <b>"+getString(R.string.txt_occupation)+"</b></h6>\n" +
                "                        <p>"+Occupation+"</p>\n" +
                "                    </div>\n" +
                "                    <div class=\"column\" style=\"background-color: #edf3ff;\">\n" +
                "                        <h6><i class=\"fa fa-user\" style=\"color: #3d5987;\"></i> <b>"+getString(R.string.txt_referred_by)+"</b></h6>\n" +
                "                        <p>"+referredBy+"</p>\n" +
                "                    </div>\n" +
                "\n" +
                "                </div>\n" +
                "\t\t\t\t\n" +
                "\t\t\t\t\n" +
                "\n" +
                "            </div>\n" +
                "\t\t\t\n" +
                "\n" +
                "        </div>\n" +
                "\t\t<br>\n" +
                "\n" +
                "    </div>\n" +
                "    \n" +
                "\n" +
                "</body>\n" +
                "\n" +
                "</html>";



      /*  String values1 = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +


                "<script type=\"text/javascript\">\n" +
                "    function showAndroidToast(toast) {\n" +
                "        android.showToast(toast);\n" +
                "    }\n" +
                "</script>\n" +


                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +

                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +

                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                " \n" +
                "<font class=\"sub\">  \n" +
                '\n' +
                "<i class=\"fa fa-user-circle-o fa-la\" aria-hidden=\"true\"></i> " + getString(R.string.txt_patients_details) + "</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<div >\n" +
                '\n' +
                '\n' +
                "<table style=\"width:100%\" class=\"table\">\n" +
                "   \n" +
                "<tbody>\n" +
                '\n' +
                "<tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-id-card-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_patient_id) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + PatientId + "</td> \n" +
                "</tr>\n" +
                '\n' +
                "<tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_name) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + PatientName + "</td> \n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_father_name) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + FatherName + "</td>\n" +
                "</tr>\n" +

                "<tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user-circle\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_spouse) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + Spouse + "</td>\n" +
                "</tr>\n" +


                '\n' +
                " <tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-calendar\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_date_of_birth) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + DateOfBirth + "</td>\n" +
                "</tr> \n" +
                '\n' +
                "<tr > \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-calendar-check-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_age) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  " + AgeStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr >\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-mars-stroke\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_gender) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + GenderStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                " \n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-asterisk\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_blood_group) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + BloodGroupStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-users\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_caste) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + caste + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "</tbody>\n" +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                "<br/>\n" +
                "<!--------------------------------------------------------------------->\n" +
                '\n' +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-la\" aria-hidden=\"true\"></i> " + getString(R.string.txt_patients_address) + "</font>\n" +
                '\n' +
                '\n' +
                "<div class=\"bs-example\">\n" +
                '\n' +
                '\n' +
                "<table style=\"width:100%\" class=\"table\">\n" +
                "   \n" +
                "<tbody>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_address1) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + Address1Str + "</td> \n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-address-card\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_address2) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + Address2Str + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_state) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + StateStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_city) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + CityStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-globe\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_country) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + CountryStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-map-marker\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_pincode) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  " + PincodeStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-mobile\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_mobile_no1) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + MobileNo1Str + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-phone\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_mobile_no2) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + MobileNo2Str + "</td>\n" +
                "</tr>\n" +


                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-phone\" aria-hidden=\"true\"></i><b>  " + getString(R.string.docemail) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + EmailStr + "</td>\n" +
                "</tr>\n" +


                '\n' +
                " \n" +
                '\n' +
                "</tbody>\n" +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-user fa-la\" aria-hidden=\"true\"></i> " + getString(R.string.txt_care_taker_details) + "</font>\n" +
                '\n' +
                '\n' +
                "<div class=\"bs-example\">\n" +
                '\n' +
                '\n' +
                "<table style=\"width:100%\" class=\"table\">\n" +
                "   \n" +
                "<tbody>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_care_take_dependentname) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + careTakerDetailsStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-mobile\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_contact_number) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + contactNoStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-users\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_relationship) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + RelationshipStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "</tbody>\n" +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-credit-card-alt fa-la\" aria-hidden=\"true\"></i> " + getString(R.string.txt_insurance_details) + "</font>\n" +
                '\n' +
                '\n' +
                "<div class=\"bs-example\">\n" +
                '\n' +
                '\n' +
                "<table style=\"width:100%\" class=\"table\">\n" +
                "   \n" +
                "<tbody>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-star-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_policy_name) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + policyNameStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-building\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_nameoftheinsurancecompany) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + NameOfCompany + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-money\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_amount_insured) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + AmountInsured + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-exclamation\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_validity) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + ValidityStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-hospital-o\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_authorised_hospitals) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + AuthorisedHospitalsStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "</tbody>\n" +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-la\" aria-hidden=\"true\"></i> " + getString(R.string.txt_other_details) + "</font>\n" +
                '\n' +
                '\n' +
                "<div class=\"bs-example\">\n" +
                '\n' +
                '\n' +
                "<table style=\"width:100%\" class=\"table\">\n" +
                "   \n" +
                "<tbody>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-money\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_fee_exemption) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + FeeExemptionStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                " \n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-money\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_fee_exemption_category) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + FeeExemptionCategoryStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_bpl_card_no) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + BplCardNoStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-credit-card-alt\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_aadhar_card_no) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + AadharCardNoStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-money\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_fee_exemption_reason) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + FeeExemptionReasonStr + "</td>\n" +
                "</tr>\n" +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-money\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_income) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + Income + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_occupation) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + Occupation + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                "<tr>\n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa fa-user\" aria-hidden=\"true\"></i><b>  " + getString(R.string.txt_referred_by) + "</b></td> \n" +
                "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   " + referredBy + "</td>\n" +
                "</tr>\n" +
                '\n' +
                '\n' +
                '\n' +
                "</tbody>\n" +
                "</table>\n" +
                '\n' +
                "</div>\n" +
                *//* "<input type=\"button\" value=\"Say hello\" onClick=\"showAndroidToast('Hello Android!')\" />\n" +*//*
                '\n' +
                '\n' +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                             ";*/


        c.close();
        db.close();
        //Toast.makeText(this, "Doctor profile loaded successfully..", Toast.LENGTH_SHORT).show();
        //MyDynamicToast.successMessage(getActivity(), getString(R.string.patient_profileloading));

        return values;

    }
    //#######################################################################################################

    public static final void Convert64ToImage(String base64, ImageView img) {
        // BaseConfig.Zoomimg=base64;

        byte[] decodedString = Base64.decode(base64.trim(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0,
                decodedString.length);

        // setting the decodedByte to ImageView
        img.setImageBitmap(decodedByte);


    }

    //#######################################################################################################
    private static final String checkTrueFalse(String values) {

        return values.equalsIgnoreCase("True") || values.equals("True") ? "Yes" : "No";

    }

    private static final String checkNullEmpty(String val) {
        return val == null || val.isEmpty() || val.equals("null") ? "  -" : val;
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

        /*
          Show a toast from the web page
         */

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public final void showToast(String toast) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();

            showDialogAndroid();
        }


        @JavascriptInterface
        final void showDialogAndroid() {
            AlertDialog.Builder ob = new AlertDialog.Builder(mContext);

            ob.setTitle("Title");
            ob.setMessage(" this is the Dialog Message");
            ob.setNegativeButton("Cancel", (dialogInterface, i) -> Toast.makeText(mContext, "Negative button is clicked", Toast.LENGTH_SHORT).show());

            ob.setPositiveButton("Ok", (dialogInterface, i) -> Toast.makeText(mContext, "Positive button is clicked", Toast.LENGTH_SHORT).show());


            AlertDialog obs = ob.create();

            obs.show();


        }
    }
    //#######################################################################################################

}
