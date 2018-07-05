package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mlsdev.rximagepicker.RxImageConverters;
import com.mlsdev.rximagepicker.RxImagePicker;
import com.mlsdev.rximagepicker.Sources;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

public class Patient_Registration extends AppCompatActivity {

    //Declaration
    @BindView(R.id.patient_parent_layout)
    CoordinatorLayout patientParentLayout;
    @BindView(R.id.patientregistration_nesetedscrollview)
    NestedScrollView patientregistrationNesetedscrollview;
    @BindView(R.id.imgvw_patientphoto)
    ImageView imgvwPatientphoto;
    @BindView(R.id.imgvw_camera)
    ImageView imgvwCamera;
    @BindView(R.id.imgvw_browse)
    ImageView imgvwBrowse;
    @BindView(R.id.upperlayout)
    LinearLayout upperlayout;
    @BindView(R.id.textvw_patient_name)
    TextView textvwPatientName;
    @BindView(R.id.edt_patient_name)
    EditText edtPatientName;
    @BindView(R.id.textvw_age)
    TextView textvwAge;
    @BindView(R.id.edt_age)
    EditText edtAge;
    @BindView(R.id.textvw_gender)
    TextView textvwGender;
    @BindView(R.id.spinner_gender)
    Spinner spinnerGender;
    @BindView(R.id.textvw_country)
    TextView textvwCountry;
    @BindView(R.id.autocomplete_country)
    AutoCompleteTextView autocompleteCountry;
    @BindView(R.id.textvw_state)
    TextView textvwState;
    @BindView(R.id.autocomplete_state)
    AutoCompleteTextView autocompleteState;
    @BindView(R.id.textvw_city)
    TextView textvwCity;
    @BindView(R.id.autocomplete_city)
    AutoCompleteTextView autocompleteCity;
    @BindView(R.id.textvw_willing_smsalert)
    TextView textvwWillingSmsalert;
    @BindView(R.id.spinner_willing_receive)
    LabeledSwitch spinnerWillingReceive;
    @BindView(R.id.textvw_fee_exemption)
    TextView textvwFeeExemption;
    @BindView(R.id.spinner_fee_exemption)
    LabeledSwitch spinnerFeeExemption;
    @BindView(R.id.textvw_address1)
    TextView textvwAddress1;
    @BindView(R.id.edt_address1)
    EditText edtAddress1;
    @BindView(R.id.spinner_bloodgroup)
    Spinner spinnerBloodgroup;
    @BindView(R.id.spinner_martial_status)
    Spinner spinnerMartialStatus;
    @BindView(R.id.spinner_prefix)
    Spinner spinnerPrefix;
    @BindView(R.id.txtvw_fathername)
    TextView txtvw_FatherName;
    @BindView(R.id.edt_fathername)
    EditText edtFathername;
    @BindView(R.id.edt_dateofbirth)
    EditText edtDateofbirth;
    @BindView(R.id.edt_caste)
    EditText edtCaste;
    @BindView(R.id.edt_income)
    EditText edtIncome;
    @BindView(R.id.edt_occupation)
    EditText edtOccupation;
    @BindView(R.id.edt_address2)
    EditText edtAddress2;
    @BindView(R.id.edt_pincode)
    EditText edtPincode;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_mobilenumber2)
    EditText edtMobilenumber2;
    @BindView(R.id.edt_caretaker)
    EditText edtCaretaker;
    @BindView(R.id.edt_caretaker_number)
    EditText edtCaretakerNumber;
    @BindView(R.id.edt_relationship)
    AutoCompleteTextView edtRelationship;
    @BindView(R.id.preferrednumber)
    RadioGroup edtPreferredNumber;
    @BindView(R.id.rbtn_caretaker)
    RadioButton rbtn_caretaker;
    @BindView(R.id.rbtn_self)
    RadioButton rbtn_self;
    @BindView(R.id.toggle_donate_blood)
    LabeledSwitch toggleDonateBlood;
    @BindView(R.id.toggle_donate_eyes)
    LabeledSwitch toggleDonateEyes;
    @BindView(R.id.edt_refdoc_name)
    EditText edtRefdocName;
    @BindView(R.id.edt_ref_doctor_mobile_no)
    EditText edtRefDoctorMobileNo;
    @BindView(R.id.edt_policy_name)
    EditText edtPolicyName;
    @BindView(R.id.edt_nameof_insurance)
    EditText edtNameofInsurance;
    @BindView(R.id.edt_amount_insured)
    EditText edtAmountInsured;
    @BindView(R.id.edt_valid_year)
    EditText edtValidYear;
    @BindView(R.id.edt_authorized_hospital)
    EditText edtAuthorizedHospital;
    @BindView(R.id.edt_aadhardcard)
    EditText edtAadhardcard;
    @BindView(R.id.edt_bpl_card)
    EditText edtBplCard;
    @BindView(R.id.button_cancel)
    Button buttonCancel;
    @BindView(R.id.button_submit)
    Button buttonSubmit;


    @BindView(R.id.layout_mobile1)
    LinearLayout layout_mobile1;

    @BindView(R.id.layout_fee_exemption)
    LinearLayout layout_fee_exemption_category;


    @BindView(R.id.textvw_mobile1)
    TextView TxtvwMobile1;
    @BindView(R.id.textvw_fee_exemption_category)
    TextView TxtvwFeeExemption;

    @BindView(R.id.edt_mobile1)
    EditText edtMobile1;
    @BindView(R.id.spinner_fee_category)
    Spinner spinnerFeeExemption_category;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;

    String QUERY_COUNTRY = "select Country as dvalue FROM Mstr_Country";
    String PATIENT_PHOTO_PATH = "";
    String str_PatientId = "";
    private int DATE_DIALOG_ID = 123;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_patient_registration);

        try {
            GETINITIALIZE();

            CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void CONTROLLISTENERS() {

        //---------Autocomplete------
        autocompleteCountry.setThreshold(1);
        autocompleteState.setThreshold(1);
        autocompleteCity.setThreshold(1);
        edtRelationship.setThreshold(1);

        //Load Country
        BaseConfig.AutocompleteTextChange(autocompleteCountry, QUERY_COUNTRY);

        //Load relationship
        String QUERY_RELATIONSHIP = "select Relationship as dvalue from Mstr_Relationship";
        BaseConfig.AutocompleteTextChange(edtRelationship, QUERY_RELATIONSHIP);

        //-------Spinners---------
        //exemption category
        String Query_FeeExemption = "select FeeExemption as dvalue from Mstr_FeeExemptions where IsActive=1";
        BaseConfig.LoadValuesSpinner(spinnerFeeExemption_category, this, Query_FeeExemption, "Select");

        //Bloodgroup
        String Query_Bloodgroup = "select Bloodgroup as dvalue from Mstr_BloodGroup where IsActive=1";
        BaseConfig.LoadValuesSpinner(spinnerBloodgroup, this, Query_Bloodgroup, "Select");


        //Load Gender
        String Query_Gender = "select Gender as dvalue from Mstr_Gender where IsActive=1";
        BaseConfig.LoadValuesSpinner(spinnerGender, this, Query_Gender, "Select");

        //Load Martial status
        String Query_MaritalStatus = "select Marital_Status as dvalue from Mstr_MaritalStatus where IsActive=1";
        BaseConfig.LoadValuesSpinner(spinnerMartialStatus, this, Query_MaritalStatus, "Select");

        //Load prefix
        String Query_Prefix = "select Prefix as dvalue from Mstr_Prefix where IsActive=1";
        BaseConfig.LoadValuesSpinner(spinnerPrefix, this, Query_Prefix, "Select");

        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());

        autocompleteCountry.setOnItemClickListener((parent, view, position, id) -> {

            //Load State
            String Country_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_Country where Country='" + autocompleteCountry.getText().toString() + "' and IsActive=1");
            String QUERY_STATE = "select State as dvalue from Mstr_State where CountryID='" + Country_ID + "' and IsActive=1";
            BaseConfig.AutocompleteTextChange(autocompleteState, QUERY_STATE);

        });

        autocompleteState.setOnItemClickListener((parent, view, position, id) -> {

            //Load City
            String Country_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_Country where Country='" + autocompleteCountry.getText().toString() + "' and IsActive=1");
            String State_ID = BaseConfig.GetValues("select ServerId as ret_values from Mstr_State where State='" + autocompleteState.getText().toString() + "' and IsActive=1");
            String QUERY_CITY = "select City as dvalue from Mstr_City where CountryID='" + Country_ID + "' and StateID='" + State_ID + "' and IsActive=1";
            BaseConfig.AutocompleteTextChange(autocompleteCity, QUERY_CITY);


        });


        imgvwCamera.setOnClickListener(view -> pickImageFromSource(Sources.CAMERA));
        imgvwBrowse.setOnClickListener(view -> pickImageFromSource(Sources.GALLERY));


        buttonCancel.setOnClickListener(v -> LoadBack());

        buttonSubmit.setOnClickListener(v -> Savelocal());

        spinnerWillingReceive.setOnToggledListener((labeledSwitch, isOn) -> {
            if (isOn) {
                layout_mobile1.setVisibility(View.VISIBLE);
            } else {
                if (spinnerFeeExemption.isOn())
                    layout_mobile1.setVisibility(View.INVISIBLE);
                else
                    layout_mobile1.setVisibility(View.GONE);
            }
        });

        spinnerFeeExemption.setOnToggledListener((labeledSwitch, isOn) -> {

            if (isOn) {
                layout_fee_exemption_category.setVisibility(View.VISIBLE);
            } else {
                if (spinnerWillingReceive.isOn())
                    layout_fee_exemption_category.setVisibility(View.INVISIBLE);
                else
                    layout_fee_exemption_category.setVisibility(View.GONE);
            }

        });

        spinnerMartialStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (spinnerMartialStatus.getSelectedItemPosition() > 0) {
                    String str = spinnerMartialStatus.getSelectedItem().toString();
                    if (str.equalsIgnoreCase("Married")) {
                        //set title as spouse name
                        txtvw_FatherName.setText("Spouse Name");
                    } else {
                        //set title as father name
                        txtvw_FatherName.setText("Father Name");
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        edtDateofbirth.setFocusableInTouchMode(false);
        Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat,Locale.ENGLISH);
            edtDateofbirth.setText(sdf.format(myCalendar.getTime()));


            String AgeStr = getAge(year, monthOfYear, dayOfMonth);

            edtAge.setText(AgeStr);



        };

        edtDateofbirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Patient_Registration.this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


    }




    private String getAge(int year, int month, int day){
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)){
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }

    @SuppressLint("CheckResult")
    private void pickImageFromSource(Sources source) {
        RxImagePicker.with(Patient_Registration.this).requestImage(source)
                .flatMap(uri -> {


                    return RxImageConverters.uriToFile(Patient_Registration.this, uri, createTempFile());

                    //return RxImageConverters.uriToBitmap(Patient_Registration.this, uri);


                })
                .subscribe(this::onImagePicked);//Toast.makeText(Patient_Registration.this, String.format("Error: %s", throwable), Toast.LENGTH_LONG).show());
    }

    private void onImagePicked(Object result) {

        PATIENT_PHOTO_PATH = result.toString();

        // Toast.makeText(this, String.format("Result: %s", result), Toast.LENGTH_LONG).show();

        if (result instanceof Bitmap) {
            imgvwPatientphoto.setImageBitmap((Bitmap) result);
        } else {
            Glide.with(this)
                    .load(result) // works for File or Uri
                    .into(imgvwPatientphoto);
        }

    }

    private File createTempFile() {

        return new File(BaseConfig.Patient_Photo_Location, System.currentTimeMillis() + "_image.jpeg");

    }

    private void Savelocal() {

        if (CheckValidation()) {

            SUBMIT_FORM();

        } else {
            Toast.makeText(Patient_Registration.this, R.string.missing, Toast.LENGTH_LONG).show();

        }

    }

    public void SUBMIT_FORM() {

        try {
            String str_PatientName = "", str_Age = "", str_Gender = "", str_Country = "", str_State = "", str_City = "", str_Willingtosms = "", str_FeeExemption = "", str_Mobile1 = "", str_FeeExemption_Category = "",
                    str_Address1 = "", str_BloodGroup = "", str_MaritalStatus = "", str_Prefix = "", str_FatherName = "", str_DateOfBirth = "", str_Caste = "", str_Income = "", str_Occupation = "",

                    str_Address2 = "", str_Pincode = "", str_Email = "", str_MobileNo2 = "", str_CareTakerName = "", str_ContactNumber = "", str_Relationship = "", str_PreferredNumber = "", str_Willingtodonateblood = "",
                    str_Willingtodonateeyes = "", str_Referreddoctor = "", str_ReferredMobileNo = "", str_PolicyName = "", str_Nameofinsurance = "", str_AmountInsured = "", str_ValidTill = "", str_Authroized = "",
                    str_Aadhardcard = "", str_BPLCardNo = "", str_Patient_Prefix = "";

            String str_PhotoURL = "", str_SpouseName = "";

            String str_PatientPin = "", str_PtientNamePrefix = "";

            str_PatientName = BaseConfig.GetWidgetOperations(edtPatientName, 1);
            str_Age = BaseConfig.GetWidgetOperations(edtAge, 1);
            str_Gender = BaseConfig.GetWidgetOperations(spinnerGender, 1);
            str_Country = BaseConfig.GetWidgetOperations(autocompleteCountry, 1);
            str_State = BaseConfig.GetWidgetOperations(autocompleteState, 1);
            str_City = BaseConfig.GetWidgetOperations(autocompleteCity, 1);
            str_Willingtosms = BaseConfig.GetWidgetOperations(spinnerWillingReceive, 1);
            str_FeeExemption = BaseConfig.GetWidgetOperations(spinnerFeeExemption, 1);
            str_FeeExemption = str_FeeExemption.equalsIgnoreCase("Yes") ? "1" : "0";
            str_Mobile1 = BaseConfig.GetWidgetOperations(edtMobile1, 1);
            str_FeeExemption_Category = BaseConfig.GetWidgetOperations(spinnerFeeExemption_category, 1);
            str_Address1 = BaseConfig.GetWidgetOperations(edtAddress1, 1);
            str_BloodGroup = BaseConfig.GetWidgetOperations(spinnerBloodgroup, 1);
            str_MaritalStatus = BaseConfig.GetWidgetOperations(spinnerMartialStatus, 1);
            str_Prefix = BaseConfig.GetWidgetOperations(spinnerPrefix, 1);

            str_DateOfBirth = BaseConfig.GetWidgetOperations(edtDateofbirth, 1);
            str_Caste = BaseConfig.GetWidgetOperations(edtCaste, 1);
            str_Income = BaseConfig.GetWidgetOperations(edtIncome, 1);
            str_Occupation = BaseConfig.GetWidgetOperations(edtOccupation, 1);
            str_Address2 = BaseConfig.GetWidgetOperations(edtAddress2, 1);
            str_Pincode = BaseConfig.GetWidgetOperations(edtPincode, 1);
            str_Email = BaseConfig.GetWidgetOperations(edtEmail, 1);
            str_MobileNo2 = BaseConfig.GetWidgetOperations(edtMobilenumber2, 1);
            str_CareTakerName = BaseConfig.GetWidgetOperations(edtCaretaker, 1);
            str_ContactNumber = BaseConfig.GetWidgetOperations(edtCaretakerNumber, 1);
            str_Relationship = BaseConfig.GetWidgetOperations(edtRelationship, 1);
            str_PreferredNumber = BaseConfig.GetWidgetOperations(edtPreferredNumber, 1);
            str_Willingtodonateblood = BaseConfig.GetWidgetOperations(toggleDonateBlood, 1);
            str_Willingtodonateeyes = BaseConfig.GetWidgetOperations(toggleDonateEyes, 1);
            str_Referreddoctor = BaseConfig.GetWidgetOperations(edtRefdocName, 1);
            str_ReferredMobileNo = BaseConfig.GetWidgetOperations(edtRefDoctorMobileNo, 1);
            str_PolicyName = BaseConfig.GetWidgetOperations(edtPolicyName, 1);
            str_Nameofinsurance = BaseConfig.GetWidgetOperations(edtNameofInsurance, 1);
            str_AmountInsured = BaseConfig.GetWidgetOperations(edtAmountInsured, 1);
            str_ValidTill = BaseConfig.GetWidgetOperations(edtValidYear, 1);
            str_Authroized = BaseConfig.GetWidgetOperations(edtAuthorizedHospital, 1);
            str_Aadhardcard = BaseConfig.GetWidgetOperations(edtAadhardcard, 1);
            str_BPLCardNo = BaseConfig.GetWidgetOperations(edtBplCard, 1);
            str_PhotoURL = PATIENT_PHOTO_PATH;

            String DefaultPhotoPath = BaseConfig.AppDBFolderName + "/male_avatar.jpg";

            str_PhotoURL = str_PhotoURL.equalsIgnoreCase("") ? DefaultPhotoPath : str_PhotoURL;

            str_Patient_Prefix = GetGenderPrefix(str_Gender, str_MaritalStatus, str_Patient_Prefix);

            if (str_MaritalStatus.equalsIgnoreCase("Married")) {
                str_SpouseName = BaseConfig.GetWidgetOperations(edtFathername, 1);
            } else if (str_MaritalStatus.equalsIgnoreCase("Unmarried")) {
                str_FatherName = BaseConfig.GetWidgetOperations(edtFathername, 1);
            }else
            {
                str_FatherName = BaseConfig.GetWidgetOperations(edtFathername, 1);
            }

            str_PatientPin = BaseConfig.GeneratePatientLoginPin();

            str_PtientNamePrefix = str_Patient_Prefix+" . "+str_PatientName;

            str_PatientId = BaseConfig.GeneratePatientRegistrationID();

            SQLiteDatabase db = BaseConfig.GetDb();
            ContentValues value = new ContentValues();
            value.put("PatientName", str_PtientNamePrefix);
            value.put("Patient_Prefix", str_Patient_Prefix);
            value.put("PatientId", str_PatientId);
            value.put("Age", str_Age);
            value.put("Gender", str_Gender);
            value.put("Country", str_Country);
            value.put("State", str_State);
            value.put("City", str_City);
            value.put("WillingtoReceive", str_Willingtosms);
            value.put("FeeExemption", str_FeeExemption);
            value.put("Mobile1", str_Mobile1);
            value.put("FeeExemptionCategory", str_FeeExemption_Category);
            value.put("Address1", str_Address1);
            value.put("Bloodgroup", str_BloodGroup);
            value.put("Marital", str_MaritalStatus);
            value.put("RelationShipPrefix", str_Prefix);
            value.put("FatherName", str_FatherName);
            value.put("DateOfBirth", str_DateOfBirth);
            value.put("Caste", str_Caste);
            value.put("Income", str_Income);
            value.put("Occupation", str_Occupation);
            value.put("Address2", str_Address2);
            value.put("Pincode", str_Pincode);
            value.put("Email", str_Email);
            value.put("MobileNo2", str_MobileNo2);
            value.put("Caretaker", str_CareTakerName);
            value.put("CaretakerNumber", str_ContactNumber);
            value.put("Relationship", str_Relationship);
            value.put("PreferredNumber", str_PreferredNumber);
            value.put("WillingtoDonateBlood", str_Willingtodonateblood);
            value.put("WillingtoDonateEyes", str_Willingtodonateeyes);
            value.put("ReferredDoctorName", str_Referreddoctor);
            value.put("ReferredDoctorMobileNo", str_ReferredMobileNo);
            value.put("PolicyName", str_PolicyName);
            value.put("NameOfInsuranceCompany", str_Nameofinsurance);
            value.put("AmountInsured", str_AmountInsured);
            value.put("ValidTill", str_ValidTill);
            value.put("AuthorizedHospitals", str_Authroized);
            value.put("AadhardCard", str_Aadhardcard);
            value.put("BPLCard", str_BPLCardNo);
            value.put("IsUpdate", 0);
            value.put("IsActive", 1);
            value.put("ServerId", 0);
            value.put("ActDate", BaseConfig.DeviceDate());
            value.put("Photo_Path", str_PhotoURL);
            value.put("SpouseName", str_SpouseName);
            value.put("PinPass", str_PatientPin);
            value.put("HID", BaseConfig.HID);
            value.put("DoctorId", BaseConfig.doctorId);
            value.put("NamePrefix", str_PtientNamePrefix);
            db.insert("Bind_Patient_Registration", null, value);



            str_FeeExemption = str_FeeExemption.equalsIgnoreCase("Yes") ? "Yes" : "No";
            value = new ContentValues();
            value.put("Patid", str_PatientId);
            value.put("Docid", BaseConfig.doctorId);
            value.put("name", str_PtientNamePrefix);
            value.put("prefix", str_Patient_Prefix);
            value.put("patientname", str_PatientName);
            value.put("PC", str_PhotoURL);
            value.put("age", str_Age);
            value.put("gender", str_Gender);
            value.put("DOB", str_DateOfBirth);
            value.put("Country", str_Country);
            value.put("State", str_State);
            value.put("District", str_City);
            value.put("Address", str_Address1);
            value.put("pincode", str_Pincode);
            value.put("phone", str_Mobile1);
            value.put("PMH", str_PtientNamePrefix);
            value.put("IsActive", 1);
            value.put("city", str_City);
            value.put("altphone", str_MobileNo2);
            value.put("Actdate", BaseConfig.DeviceDate());
            value.put("Isupdate", 1);
            value.put("inshosp", str_Authroized);
            value.put("Address1", str_Address1+","+str_Address2);
            value.put("email", str_Email);
            value.put("caretaker", str_CareTakerName);
            value.put("crtknum", str_ContactNumber);
            value.put("relationship", str_Relationship);
            value.put("willingblood", str_Willingtodonateblood);
            value.put("willingeye", str_Willingtodonateeyes);
            value.put("PatientPin", str_PatientPin);
            value.put("bloodgroup", str_BloodGroup);
            value.put("policyname", str_PolicyName);
            value.put("insurancecompany", str_Nameofinsurance);
            value.put("amountinsured", str_AmountInsured);
            value.put("validity", str_ValidTill);
            value.put("enable_inpatient", 0);
            value.put("HospitalId", BaseConfig.HID);
            value.put("doc_refer_name", str_Referreddoctor);
            value.put("doc_refer_no", str_ReferredMobileNo);
            value.put("bplCardNo", str_BPLCardNo);
            value.put("AadharCardNo", str_Aadhardcard);
            value.put("caste", str_Caste);
            value.put("income", str_Income);
            value.put("referred_by", str_Referreddoctor);
            value.put("Occupation", str_Occupation);
            value.put("Fathername", str_FatherName);
            value.put("spouse", str_SpouseName);
            value.put("IsFeeExemp", str_FeeExemption);
            value.put("FeeExempCateg", str_FeeExemption_Category);

            db.insert("Patreg", null, value);


            //Insert as current patient list
            value = new ContentValues();
            value.put("date", BaseConfig.Device_OnlyDate2());
            value.put("patid", str_PatientId);
            value.put("docid", BaseConfig.doctorId);
            value.put("IsUpdate", 0);
            value.put("ServerId", 0);
            value.put("status", "true");
            value.put("closed", "0");
            value.put("HID", BaseConfig.HID);
            db.insert("current_patient_list", null, value);

            db.close();

            GotoClinicalInfo();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String GetGenderPrefix(String str_Gender, String str_MaritalStatus, String str_Patient_Prefix) {
        if (str_Gender.equalsIgnoreCase("Male")) {
            str_Patient_Prefix = "Mr";
        } else if (str_Gender.equalsIgnoreCase("Female")) {
            if (str_MaritalStatus.equalsIgnoreCase("Married")) {
                str_Patient_Prefix = "Mrs";
            } else {
                str_Patient_Prefix = "Ms";
            }
        } else if (str_Gender.equalsIgnoreCase("Baby")) {
            str_Patient_Prefix = "Baby";
        } else if (str_Gender.equalsIgnoreCase("Trans-gender")) {
            str_Patient_Prefix = "Tr";
        }
        return str_Patient_Prefix;
    }

    public void GotoClinicalInfo() {

        try {
            Toast.makeText(this, R.string.success_message_patient_registration, Toast.LENGTH_SHORT).show();
            Patient_Registration.this.finish();
            Intent intent = new Intent(getApplicationContext(), CaseNotes.class);
            intent.putExtra("CONTINUE_STATUS", "True");
            intent.putExtra("PASSING_PATIENT_ID", str_PatientId);
            CustomIntent.customType(Patient_Registration.this, 4);
            startActivity(intent);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean CheckValidation() {

        boolean ret = true;

        if (!Validation1.isEmailAddressChk(edtEmail, false)) {
            ret = false;
        }

        if (edtAddress1.getText().length() == 0) {
            edtAddress1.requestFocus();
            edtAddress1.setError("Required");
            edtAddress1.setHint("Enter patient address");
            ret = false;
        }

        if (spinnerFeeExemption.isOn()) {
            if (spinnerFeeExemption_category.getSelectedItemPosition() == 0) {
                BaseConfig.CheckSpinner2(spinnerFeeExemption_category);
                ret = false;
            }
        }


        if (spinnerWillingReceive.isOn()) {
            if (edtMobile1.getText().length() == 0) {
                edtMobile1.requestFocus();
                edtMobile1.setError("Required");
                edtMobile1.setHint("Enter mobile no");
                ret = false;
            } else if (!Validation1.isMobileNumber(edtMobile1, false)) {
                ret = false;
            }

        }


        if (autocompleteCity.getText().length() == 0) {

            autocompleteCity.requestFocus();
            autocompleteCity.setError("Required");
            autocompleteCity.setHint("Enter city name");
            ret = false;
        }

        if (autocompleteState.getText().length() == 0) {
            autocompleteState.requestFocus();
            autocompleteState.setError("Required");
            autocompleteState.setHint("Enter state name");
            ret = false;
        }

        if (autocompleteCountry.getText().length() == 0) {
            autocompleteCountry.requestFocus();
            autocompleteCountry.setError("Required");
            autocompleteCountry.setHint("Enter country name");
            ret = false;
        }

        if (spinnerGender.getSelectedItemPosition() == 0) {
            BaseConfig.CheckSpinner2(spinnerGender);
            ret = false;
        }


        if (edtAge.getText().length() == 0) {
            edtAge.requestFocus();
            edtAge.setError("Required");
            edtAge.setHint("Enter age");
            ret = false;
        }

        int Age = 0;
        if (edtAge.getText().length() > 0) {
            Age = Integer.parseInt(String.valueOf(edtAge.getText()));
        }

        if (Integer.parseInt(String.valueOf(Age)) > 110) {
            edtAge.requestFocus();
            edtAge.setError("Age value should be within 110");
            edtAge.setHint("Enter valid age");
            ret = false;
        }

        if (edtPatientName.getText().toString().trim().length() == 0 && edtPatientName.getText().toString().trim().isEmpty()) {
            edtPatientName.requestFocus();
            edtPatientName.setError("Required");
            edtPatientName.setHint("Enter patient name");
            ret = false;
        }


        return ret;

    }


    private void GETINITIALIZE() {

        ButterKnife.bind(Patient_Registration.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        BaseConfig.welcometoast = 0;

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.patientregistration)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        BaseConfig.HighlightMandatory(getString(R.string.str_patient_name), textvwPatientName);
        BaseConfig.HighlightMandatory(getString(R.string.str_age), textvwAge);
        BaseConfig.HighlightMandatory(getString(R.string.gender), textvwGender);
        BaseConfig.HighlightMandatory(getString(R.string.str_country), textvwCountry);
        BaseConfig.HighlightMandatory(getString(R.string.str_state), textvwState);
        BaseConfig.HighlightMandatory(getString(R.string.str_city), textvwCity);
        BaseConfig.HighlightMandatory(getString(R.string.str_willingsms), textvwWillingSmsalert);
        BaseConfig.HighlightMandatory(getString(R.string.str_feeexemp), textvwFeeExemption);
        BaseConfig.HighlightMandatory(getString(R.string.str_address1), textvwAddress1);

        BaseConfig.HighlightMandatory(getString(R.string.mobile_1), TxtvwMobile1);
        BaseConfig.HighlightMandatory(getString(R.string.txt_fee_exemption_category), TxtvwFeeExemption);

        BaseConfig.ClearError(edtPatientName);
        BaseConfig.ClearError(edtAge);
        BaseConfig.ClearError(autocompleteCountry);
        BaseConfig.ClearError(autocompleteState);
        BaseConfig.ClearError(autocompleteCity);
        BaseConfig.ClearError(edtAddress1);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoadBack();
        System.gc();
    }


    public void LoadBack() {
       BaseConfig.globalStartIntent2(Patient_Registration.this, Dashboard_NavigationMenu.class, null);
    }


}//END
