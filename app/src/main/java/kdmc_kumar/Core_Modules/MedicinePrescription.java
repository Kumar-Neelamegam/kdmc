package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.joda.time.format.PeriodFormatterBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.InputFilterMinMax;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.asyn.AsynkTaskCustom;
import kdmc_kumar.Utilities_Others.asyn.onWriteCode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNode;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.controller.api.GeneratePDFNodeFactory;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.GetPDFNodeJsRequest;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.JsonValue;
import kdmc_kumar.Webservices_NodeJSON.generatePDFNode.model.beans.PDFNodeJsResult;

public class MedicinePrescription extends AppCompatActivity {

    private static int mcYear = 0;
    private static int mcMonth = 0;
    private static int mcDay = 0;
    private static int mYear = 0;
    private static int mMonth = 0;
    private static int mDay = 0;
    private final List<String> Loadlist1 = new ArrayList<>();
    private final List<String> Loadlist2 = new ArrayList<>();
    private final List<String> Loadlist3 = new ArrayList<>();
    private final List<String> Loadlist4 = new ArrayList<>();
    private final String[] Medicine_Type_Array = {"TAB", "SYR", "OINT", "DROPS", "CAP", "INJ"};
    private final String[] Medicine_Intake_Session = {"After Food", "Before Food", "With Food"};
    private final String[] Medicine_Frequency = {"Morning", "Afternoon", "Evening", "Night"};
    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final ArrayList<CommonDataObjects.MedicineGetSet> medicineGetSets = new ArrayList<>();//
    private final List<String> list = new ArrayList<>();
    //endregion
    private final List<String> listcnt = new ArrayList<>();
    private final List<String> listtottabcount = new ArrayList<>();
    private final ArrayList<CommonDataObjects.InjectionGetSet> injectionGetSets = new ArrayList<>();//
    private final StringBuilder sbM = new StringBuilder();
    //Declaration
    //Upper Layout
    @BindView(R.id.medicine_prescription_parent_layout)
    CoordinatorLayout _medicinePrescriptionParentLayout;
    @BindView(R.id.medicine_prescription_nesetedscrollview)
    NestedScrollView _medicinePrescriptionNesetedscrollview;
    @BindView(R.id.upperlayout)
    LinearLayout _upperlayout;
    @BindView(R.id.imgvw_patientphoto)
    CircleImageView _imgvwPatientphoto;
    @BindView(R.id.tvw_agegender)
    TextView _tvwAgegender;
    @BindView(R.id.txtvw_title_patientname)
    TextView _txtvwTitlePatientname;
    @BindView(R.id.autocomplete_patientname)
    AutoCompleteTextView _autocompletePatientname;
    @BindView(R.id.txtvw_treatmentfor)
    TextView _txtvwTreatmentfor;
    @BindView(R.id.multiauto_treatmentfor)
    MultiAutoCompleteTextView _multiautoTreatmentfor;
    @BindView(R.id.txtvw_diagnosis)
    TextView _txtvwDiagnosis;
    @BindView(R.id.multiauto_diagnosis)
    MultiAutoCompleteTextView _multiautoDiagnosis;
    @BindView(R.id.layout_prevmedhistory)
    LinearLayout PreviousMedicineLayout;
    //Medicine details
    @BindView(R.id.card_medicine_details)
    CardView _cardMedicineDetails;
    @BindView(R.id.txvw_medicine_details_title)
    TextView _txvwMedicineDetailsTitle;
    @BindView(R.id.imgvw_medicine_options)
    ImageView imgvw_medicine_options;
    @BindView(R.id.medicine_textinputlayout)
    TextView _medicineTextinputlayout;
    @BindView(R.id.txtview_visiting_title)
    TextView txtview_visiting_title;
    @BindView(R.id.autocomplete_medicine_name)
    AutoCompleteTextView _autocompleteMedicineName;
    @BindView(R.id.txtview_choose_frequency)
    TextView _txtviewChooseFrequency;
    @BindView(R.id.chkbx_checkall)
    CheckBox _chkbxCheckall;
    @BindView(R.id.chkbx_morning)
    CheckBox _chkbxMorning;
    @BindView(R.id.txtvw_morning)
    TextView _txtvwMorning;
    @BindView(R.id.edttxt_morning_frequency)
    EditText _edttxtMorningFrequency;
    @BindView(R.id.spinner_morning)
    Spinner _spinnerMorning;
    @BindView(R.id.chkbx_afternoon)
    CheckBox _chkbxAfternoon;
    @BindView(R.id.txtvw_afternoon)
    TextView _txtvwAfternoon;
    @BindView(R.id.edttxt_afternoon_frequency)
    EditText _edttxtAfternoonFrequency;
    @BindView(R.id.spinner_afternoon)
    Spinner _spinnerAfternoon;
    @BindView(R.id.chkbx_evening)
    CheckBox _chkbxEvening;
    @BindView(R.id.txtvw_evening)
    TextView _txtvwEvening;
    @BindView(R.id.edttxt_evening)
    EditText _edttxtEvening;
    @BindView(R.id.spinner_evening)
    Spinner _spinnerEvening;
    @BindView(R.id.chkbx_nite)
    CheckBox _chkbxNite;
    @BindView(R.id.textvw_nite)
    TextView _textvwNite;
    @BindView(R.id.edttxt_nite)
    EditText _edttxtNite;
    @BindView(R.id.spinner_nite)
    Spinner _spinnerNite;
    @BindView(R.id.layout_choose_intake)
    LinearLayout _layoutChooseIntake;
    @BindView(R.id.textvw_choose_intake_title)
    TextView _textvwChooseIntakeTitle;
    @BindView(R.id.radiogrp_intake_options)
    RadioGroup _radiogrpIntakeOptions;
    @BindView(R.id.radiobtn_after_food)
    RadioButton _radiobtnAfterFood;
    @BindView(R.id.radiobtn_before_food)
    RadioButton _radiobtnBeforeFood;
    @BindView(R.id.radiobtn_with_food)
    RadioButton _radiobtnWithFood;
    @BindView(R.id.layout_choose_duration)
    LinearLayout _layoutChooseDuration;
    @BindView(R.id.textvw_choose_duration_title)
    TextView _textvwChooseDurationTitle;
    @BindView(R.id.spinner_duration)
    Spinner _spinnerDuration;
    @BindView(R.id.button_add_medicine)
    Button _buttonAddMedicine;
    @BindView(R.id.recycler_medicine_list)
    RecyclerView _recyclerMedicineList;
    //Emergency
    @BindView(R.id.injection_layout)
    LinearLayout _injectionLayout;
    @BindView(R.id.autocmpltxt_injection)
    AutoCompleteTextView _autocmpltxtInjection;
    @BindView(R.id.edt_injecdosage)
    EditText _edtInjecdosage;
    @BindView(R.id.edt_injquantity)
    EditText _edtInjquantity;
    @BindView(R.id.btn_addinj)
    Button _btnAddinj;
    @BindView(R.id.injection_recycler)
    RecyclerView _injectionRecycler;
    @BindView(R.id.nebulization_layout)
    LinearLayout _nebulizationLayout;
    @BindView(R.id.chk_nrmsaline)
    CheckBox _chkNrmsaline;
    @BindView(R.id.chk_asthasaline)
    CheckBox _chkAsthasaline;
    @BindView(R.id.edt_dosage1)
    EditText _edtDosage1;
    @BindView(R.id.edt_duration1)
    EditText _edtDuration1;
    @BindView(R.id.edt_quantity1)
    EditText _edtQuantity1;
    @BindView(R.id.edt_dosage2)
    EditText _edtDosage2;
    @BindView(R.id.edt_duration2)
    EditText _edtDuration2;
    @BindView(R.id.edt_quantity2)
    EditText _edtQuantity2;
    @BindView(R.id.suturing_layout)
    LinearLayout _suturingLayout;
    @BindView(R.id.muliautcompltxt_suturing)
    MultiAutoCompleteTextView _muliautcompltxtSuturing;
    @BindView(R.id.plastering_layout)
    LinearLayout _plasteringLayout;
    @BindView(R.id.muliautcompltxt_plastering)
    MultiAutoCompleteTextView _muliautcompltxtPlastering;
    @BindView(R.id.chk_slab)
    CheckBox _chkSlab;
    @BindView(R.id.chk_cast)
    CheckBox _chkCast;
    @BindView(R.id.chkbx_injection_layout)
    CheckBox _chkInjection;
    @BindView(R.id.chkbx_nebu_layout)
    CheckBox _chkNebulization;
    @BindView(R.id.chkbx_suturing_layout)
    CheckBox _chkSuturing;
    @BindView(R.id.chkbx_plastering_layout)
    CheckBox _chkPlastering;
    //next visit info
    @BindView(R.id.edtxtvw_visitafter)
    EditText _edtxtvwVisitafter;
    @BindView(R.id.textView4)
    TextView _textView4;
    @BindView(R.id.edtxtvw_visitedon)
    EditText _edtxtvwVisitedon;
    @BindView(R.id.textView6)
    TextView _textView6;
    @BindView(R.id.edtxtvw_nextvisit)
    EditText _edtxtvwNextvisit;
    @BindView(R.id.textView2)
    TextView _textView2;
    @BindView(R.id.edtxtvw_remarks)
    EditText _edtxtvwRemarks;
    @BindView(R.id.textView1)
    TextView _textView1;
    //pharmacy details
    @BindView(R.id.autocomplete_pharmacy_name)
    AutoCompleteTextView _autocompletePharmacyName;
    @BindView(R.id.textView7)
    TextView _textView7;
    @BindView(R.id.autocomplete_pharmacy_address)
    AutoCompleteTextView _autocompletePharmacyAddress;
    @BindView(R.id.button_cancel_prescription)

    //button info
            Button _buttonCancelPrescription;
    @BindView(R.id.button_submit_prescription)
    Button _buttonSubmitPrescription;
    //Action Bar
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    @BindView(R.id.txtvw_savemedicine_template)
    TextView SaveMedicineTemplate;
    String str_PatientName = "";
    String str_Gender = "";
    String str_Age = "";
    String str_Symptoms = "";
    String str_ProvisionalDiagnosis = "";
    StringBuilder MedicalPrescriped = new StringBuilder();
    //endregion
    List<String> advlist = new ArrayList<>();
    private String session1 = null;
    private String session2 = null;
    private String session3 = null;
    private String session4 = null;
    private String session_set1 = "M";
    private String session_set2 = "A";
    private String session_set3 = "E";
    private String session_set4 = "N";
    private String FLAG_MYPATIENT = "N/A";
    private String COMMON_PATIENT_ID = "-";
    private String COMMON_INVESTIGATIONID = "-";
    private String COMMON_DIAGNOSISID = "-";
    //region MEDICINE PRESCRIPTION - INITIALIZATION
    private SimpleDateFormat mSimpleDateFormat = null;
    private String Medicine_Type = "";
    private LinearLayoutManager mLinearLayoutManager = null;
    private MedicineRecylerAdapter medicineRecylerAdapter = null;
    private InjectionRecyclerAdapter injectionRecyclerAdapter = null;
    //endregion
    private String concatpersonalhistrystr = "";
    private boolean statusvalue = false;
    //**********************************************************************************************
    private String SymptomsValue = null;
    private ImageView NoDataFound = null;
    private WebView profile_webvw = null;
    private List<String> mypatientprevmedicinehistory_medid = null;
    private int pos = 0;
    private String nxtdt = "";
    private String[] visiteddt = null;
    private ImageView next_btn = null;
    private ImageView pre_btn = null;
    private String refdocname = "";


    //endregion
    private TextView PatientNameId = null;


    //**********************************************************************************************
    //region MEDICINE PRESCRIPTION - CONTROLLISTENERS
    private Calendar now = Calendar.getInstance();
    private List<String> templates_list = null;

    public MedicinePrescription() {
    }

    //**********************************************************************************************

    private static final void LoadDeleteTempTest() {
        try {
            final String CREATE_TABLE_TempTest = "Delete from TempTest;";
            BaseConfig.SaveData(CREATE_TABLE_TempTest);
        } catch (Exception ignored) {

        }
    }

    private static String GetMedicinePrescriptionId() {
        String did = null;
        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select prescnum from presc ;", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    did = "MPID/" + mcYear + '/' + mcDay + mcMonth + '/' + BaseConfig.doctorId + '/' + UUID.randomUUID().toString().split("-")[1] + '/' + c.getString(c.getColumnIndex("prescnum"));

                } while (c.moveToNext());
            }
        }
        Objects.requireNonNull(c).close();
        db.close();
        return did != null ? did : null;
    }

    private static final void LoadDrWorkingDays() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db
                .rawQuery(
                        "select distinct workingdays,prefpharmacy,pharmdetails from drsettings order by workingdays;",
                        null);

        String[] countryname = null;
        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    countryname = c.getString(c.getColumnIndex("workingdays"))
                            .split(",");
                    //Log.e("Working Days Count", "" + countryname.length);

                    for (String aCountryname : countryname) {

                        switch (aCountryname) {
                            case "Monday":
                                BaseConfig.workingdays_mon = aCountryname;
                                break;
                            // //Monday,Tuesday,Wednesday,Thursday,Friday
                            case "Tuesday":
                                BaseConfig.workingdays_tue = aCountryname;
                                break;
                            case "Wednesday":
                                BaseConfig.workingdays_wed = aCountryname;
                                break;
                            case "Thursday":
                                BaseConfig.workingdays_thur = aCountryname;
                                break;
                            case "Friday":
                                BaseConfig.workingdays_fri = aCountryname;
                                break;
                            case "Saturday":

                                BaseConfig.workingdays_sat = aCountryname;
                                break;
                            case "Sunday":
                                BaseConfig.workingdays_sun = aCountryname;
                                break;
                            default:
                                break;
                        }

                    }

                } while (c.moveToNext());
            }
        }

        db.close();
        Objects.requireNonNull(c).close();
    }

    /**
     * Muthukumar N
     * Android Developer
     * 20-04-2018
     */
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.kdmc_layout_medicineprescription);

        try {
            GETINITIALIZATION();
            CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************

    private void GETINITIALIZATION() {

        ButterKnife.bind(MedicinePrescription.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.welcometoast = 0;

        BaseConfig.Glide_LoadDefaultImageView(_imgvwPatientphoto);

       // BaseConfig.LoadDoctorValues();

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.prescription)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

      //  toolbar.setBackgroundColor(Color.parseColor(MedicinePrescription.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));

        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(MedicinePrescription.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());

        String firstt = getString(R.string.symptoms);
        String nextt = "<font color='#EE0000'><b>*</b></font>";
        _txtvwTreatmentfor.setText(Html.fromHtml(firstt + nextt));

        String firstd = getString(R.string.provisional_diagnosis);
        String nextd = "<font color='#EE0000'><b>*</b></font>";
        _txtvwDiagnosis.setText(Html.fromHtml(firstd + nextd));

        String first = getString(R.string.pat_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        _txtvwTitlePatientname.setText(Html.fromHtml(first + next));

        String first1 = getString(R.string.vist_after_days);
        String next1 = "<font color='#EE0000'><b></b></font>";
        txtview_visiting_title.setText(Html.fromHtml(first1 + next1));


        String first3 = getString(R.string.med_details);
        String next3 = "<font color='#EE0000'><b>*</b></font>";
        _txvwMedicineDetailsTitle.setText(Html.fromHtml(first3 + next3));


        _autocompletePatientname.setThreshold(1);

        _multiautoTreatmentfor.setThreshold(1);
        _multiautoTreatmentfor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        _multiautoDiagnosis.setThreshold(1);
        _multiautoDiagnosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String strdate1 = sdf1.format(c1.getTime());
        _edtxtvwVisitedon.setText(strdate1);

        _edtxtvwVisitafter.setText("3");//CR 13

        mSimpleDateFormat = new SimpleDateFormat("dd/MM/yy", Locale.ENGLISH);
        new PeriodFormatterBuilder().appendYears().appendSuffix(" year(s) ").appendMonths().appendSuffix(" month(s) ").appendDays().appendSuffix(" day(s) ").printZeroNever().toFormatter();
        try {
            mSimpleDateFormat.parse(_edtxtvwVisitedon.getText().toString());

        } catch (ParseException e) {

            e.printStackTrace();
        }


        _autocompletePharmacyName.setThreshold(1);
        _autocompletePharmacyName.setText(String.valueOf(BaseConfig.HOSPITALNAME));

        String address = BaseConfig.GetValues("select Address as ret_values from Mstr_MultipleHospital where ServerId='" + BaseConfig.HID + '\'');

        if (address.length() > 0) {
            _autocompletePharmacyAddress.setText(address);
        }

        final Calendar cc = Calendar.getInstance();
        mcYear = cc.get(Calendar.YEAR);
        mcMonth = cc.get(Calendar.MONTH) + 1;
        mcDay = cc.get(Calendar.DAY_OF_MONTH);

        _autocompleteMedicineName.setThreshold(1);// will start working from first character

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        BaseConfig.LoadValuesSpinner(_spinnerDuration, this.getApplicationContext(), "select distinct duration as dvalue from Duration;", getString(R.string.select_duration));

        LoadValues(_multiautoDiagnosis, MedicinePrescription.this, "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='True' or isactive='true' or isactive='1') order by id desc;", 1);

        LoadValues(_multiautoTreatmentfor, MedicinePrescription.this, "select distinct treatmentfor as dvalue from trmntfor where (isactive='True' or isactive='true' or isactive='1') order by id desc;", 2);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            String STATUS = b.getString("CONTINUE_STATUS");
            String COMMON_TREATMENTFOR = b.getString("PASSING_TREATMENTFOR");
            String COMMON_DIAGNOSIS = b.getString("PASSING_DIAGNOSIS");
            COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");
            COMMON_INVESTIGATIONID = b.getString("PASSING_INVESTIGATIONID");
            COMMON_DIAGNOSISID = b.getString("PASSING_DIAGNOSISID");

            FLAG_MYPATIENT = b.getString("FROM");

            assert STATUS != null;
            if (STATUS.equalsIgnoreCase("True")) {

                LOAD_PATIENT_DETAILS(COMMON_PATIENT_ID, COMMON_TREATMENTFOR, COMMON_DIAGNOSIS);
                String Query = "select id as dstatus from Mprescribed where ptid='" + _autocompletePatientname.getText().toString().split("-")[1] + "';";

                boolean q1 = BaseConfig.LoadBooleanStatus(Query);
                if (q1) {

                    PreviousMedicineLayout.setVisibility(View.VISIBLE);

                }

            }
        } else {
            FLAG_MYPATIENT = "";
        }


        _autocmpltxtInjection.setThreshold(1);
        _edtDosage1.setEnabled(false);
        _edtDuration1.setEnabled(false);
        _edtQuantity1.setEnabled(false);


        _edtDosage2.setEnabled(false);
        _edtDuration2.setEnabled(false);
        _edtQuantity2.setEnabled(false);


    }

    private void CONTROLLISTENERS() {


        SaveMedicineTemplate.setOnClickListener(view -> ShowSaveTemplateDialog());

        imgvw_medicine_options.setOnClickListener(view -> {

            //final CharSequence[] items = {"Medicine Templates", "Favourite Medicines"};
            final CharSequence[] items = {"Medicine Templates"};
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MedicinePrescription.this);
            builder.setTitle(getString(R.string.options));
            builder.setItems(items, (dialog, item) -> {

                if (items[item].toString().equalsIgnoreCase("Medicine Templates")) {

                    LoadMedicineTemplate();

                } else if (items[item].toString().equalsIgnoreCase("Favourite Medicines")) {

                    LoadFavourtieMedicines();
                }


            });
            Objects.requireNonNull(builder.create().getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
            builder.create().show();


        });

        _edtxtvwVisitafter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                _edtxtvwVisitafter.setFilters(new InputFilter[]{new InputFilterMinMax("1", "180")});

                if (_edtxtvwVisitafter.getText().length() > 0) {

                    int getcount = Integer.parseInt(_edtxtvwVisitafter.getText().toString());

                    if (getcount >= 180) {

                        BaseConfig.customtoast(getApplicationContext(), MedicinePrescription.this, "Alert: Max 180 days");

                    }


                    now = Calendar.getInstance();
                    int noofdays1 = Integer.parseInt(_edtxtvwVisitafter.getText().toString());
                    now.add(Calendar.DATE, noofdays1);

                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
                    String strdate1 = sdf1.format(now.getTime());

                    SimpleDateFormat sdf2 = new SimpleDateFormat("EEEE",Locale.ENGLISH);
                    String formattedDay = sdf2.format(now.getTime());

                    if (formattedDay.equals(BaseConfig.workingdays_mon)
                            || formattedDay.equals(BaseConfig.workingdays_tue)
                            || formattedDay.equals(BaseConfig.workingdays_wed)
                            || formattedDay.equals(BaseConfig.workingdays_thur)
                            || formattedDay.equals(BaseConfig.workingdays_fri)
                            || formattedDay.equals(BaseConfig.workingdays_sat)
                            || formattedDay.equals(BaseConfig.workingdays_sun)) {
                        _edtxtvwNextvisit.setText(strdate1);
                    } else {
                        _edtxtvwNextvisit.setText("");
                        LayoutInflater inflator = getLayoutInflater();
                        View layout = inflator.inflate(R.layout.layout_toast_nxtvisit, findViewById(R.id.custom_toast_id));
                        TextView text = layout.findViewById(R.id.texttoast);
                        text.setText("Choose another day its Holiday");
                        Toast toast = new Toast(getApplicationContext());
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.setDuration(Toast.LENGTH_SHORT);
                        toast.setView(layout);
                        toast.show();

                    }

                } else {
                    _edtxtvwNextvisit.setText("");
                }

            }
        });


        PreviousMedicineLayout.setOnClickListener(view -> LoadPreviousMedHistory());


        _edttxtMorningFrequency.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (_edttxtMorningFrequency.isFocused()) {
                _edttxtMorningFrequency.setText("");
                _edttxtMorningFrequency.requestFocus();

            }

        });

        _edttxtAfternoonFrequency.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (_edttxtAfternoonFrequency.isFocused()) {
                _edttxtAfternoonFrequency.setText("");
                _edttxtAfternoonFrequency.requestFocus();

            }

        });

        _edttxtEvening.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (_edttxtEvening.isFocused()) {
                _edttxtEvening.setText("");
                _edttxtEvening.requestFocus();

            }

        });

        _edttxtNite.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (_edttxtNite.isFocused()) {
                _edttxtNite.setText("");
                _edttxtNite.requestFocus();

            }

        });

        _autocompletePatientname.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            String[] patid_nm = _autocompletePatientname.getText().toString().trim().split("-");
            BaseConfig.patid_mpopup = patid_nm[1].trim();


            String Query = "select id as dstatus from Mprescribed where ptid='" + _autocompletePatientname.getText().toString().split("-")[1] + "';";

            boolean q1 = BaseConfig.LoadBooleanStatus(Query);

            if (q1) {

                PreviousMedicineLayout.setVisibility(View.VISIBLE);

            }


            try {

                SelectedGetPatientDetails();


            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        _buttonSubmitPrescription.setOnClickListener(v -> {

            if (_autocompletePatientname.getText().length() > 0) {

                String[] Pat = _autocompletePatientname.getText().toString().split("-");
                if (Pat.length == 2) {

                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + "' ");
                    if (q) {
                        SAVE_LOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), MedicinePrescription.this, R.drawable.ic_patienticon, R.color.red_500);
                    }
                } else if (Pat.length == 1) {
                    boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where name='" + Pat[0].trim() + '\'');
                    if (q) {


                        SAVE_LOCAL();
                    } else {
                        BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), MedicinePrescription.this, R.drawable.ic_patienticon, R.color.red_400);
                    }
                }

            } else {
                BaseConfig.showSimplePopUp(getString(R.string.enter_pat_name), getString(R.string.information), MedicinePrescription.this, R.drawable.ic_warning_black_24dp, R.color.orange_500);
                _autocompletePatientname.requestFocus();
            }

        });


        _buttonAddMedicine.setOnClickListener(v -> {

            try {
                //changing the layout for prescription
                if (Medicine_Type.trim().equalsIgnoreCase("")) {
                    ADD_MEDICINE_LIST(1);

                } else {
                    if (Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[0])) {
                        ADD_MEDICINE_LIST(1);
                    } else if (Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[4])) {
                        ADD_MEDICINE_LIST(2);
                    } else if (Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[1])) {
                        ADD_MEDICINE_SYRUP();
                    } else if (Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[2]) || Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[3])) {
                        ADD_MEDICINE_OINTMENT();
                    } else if (Medicine_Type.equalsIgnoreCase(Medicine_Type_Array[5])) {
                        ADD_MEDICINE_LIST(3);
                    } else {
                        ADD_MEDICINE_LIST(1);
                    }

                }

                if (_recyclerMedicineList.getAdapter()!=null && _recyclerMedicineList.getAdapter().getItemCount() > 0) {
                    SaveMedicineTemplate.setVisibility(View.VISIBLE);
                } else {
                    SaveMedicineTemplate.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        });

        _buttonCancelPrescription.setOnClickListener(new View.OnClickListener() {

            int count = 0;

            public void onClick(View v) {

                if (count == 1) {
                    count = 0;


                    MedicinePrescription.this.finish();
                    Intent intent = new Intent(v.getContext(), Dashboard_NavigationMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.press_again_cancel, Toast.LENGTH_SHORT).show();
                    count++;
                }
            }
        });


        _spinnerMorning.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (_spinnerAfternoon.getSelectedItemPosition() < 1 || _spinnerEvening.getSelectedItemPosition() < 1 || _spinnerNite.getSelectedItemPosition() < 1) {
                    _spinnerAfternoon.setSelection(_spinnerMorning.getSelectedItemPosition());
                    _spinnerEvening.setSelection(_spinnerMorning.getSelectedItemPosition());
                    _spinnerNite.setSelection(_spinnerMorning.getSelectedItemPosition());
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        _spinnerAfternoon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                if (_spinnerMorning.getSelectedItemPosition() < 1 || _spinnerEvening.getSelectedItemPosition() < 1 || _spinnerNite.getSelectedItemPosition() < 1) {
                    _spinnerMorning.setSelection(_spinnerAfternoon.getSelectedItemPosition());
                    _spinnerEvening.setSelection(_spinnerAfternoon.getSelectedItemPosition());
                    _spinnerNite.setSelection(_spinnerAfternoon.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        _spinnerEvening.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                if (_spinnerMorning.getSelectedItemPosition() < 1 || _spinnerAfternoon.getSelectedItemPosition() < 1 || _spinnerNite.getSelectedItemPosition() < 1) {
                    _spinnerMorning.setSelection(_spinnerEvening.getSelectedItemPosition());
                    _spinnerAfternoon.setSelection(_spinnerEvening.getSelectedItemPosition());
                    _spinnerMorning.setSelection(_spinnerEvening.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }

        });

        _spinnerNite.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (_spinnerMorning.getSelectedItemPosition() < 1 && _spinnerAfternoon.getSelectedItemPosition() < 1 && _spinnerEvening.getSelectedItemPosition() < 1) {
                    _spinnerMorning.setSelection(_spinnerNite.getSelectedItemPosition());
                    _spinnerAfternoon.setSelection(_spinnerNite.getSelectedItemPosition());
                    _spinnerEvening.setSelection(_spinnerNite.getSelectedItemPosition());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        _edtxtvwVisitafter.addTextChangedListener(new MyTextWatcher());


        _autocompletePharmacyName.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            String pharmacy_address = BaseConfig.GetValues("select distinct pharmaddr||'-'||contactnum as ret_values from Pharmacy where pharmacyname='" + _autocompletePharmacyName.getText().toString() + "';");
            _autocompletePharmacyAddress.setText(pharmacy_address);

        });


        _autocompleteMedicineName.setOnItemClickListener((parent, view, position, id) -> {
            try {


                GET_MEDICINE_TYPE();

            } catch (Exception ignored) {

            }
        });


        _multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, Investigations.convertListtoStringArray(Loadlist2)));

        _multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String text;
                text = String.valueOf(s);

                _multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, Investigations.convertListtoStringArray(Loadlist2))));

                _multiautoTreatmentfor.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

        _multiautoDiagnosis.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, Investigations.convertListtoStringArray(Loadlist1)));

        _multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                String text;
                text = String.valueOf(s);

                _multiautoDiagnosis.setAdapter(new ArrayAdapter<>(MedicinePrescription.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, Investigations.convertListtoStringArray(Loadlist1))));

                _multiautoDiagnosis.showDropDown();

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        _autocompletePatientname.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                Drawable rightDrawable = AppCompatResources.getDrawable(_autocompletePatientname.getContext(), R.drawable.ic_clear_button_white);
                if (_autocompletePatientname.getText().length() > 0) {
                    _autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    _autocompletePatientname.setOnTouchListener((v, event) -> {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (float) (_autocompletePatientname.getRight() - _autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                ClearAll();
                                return true;
                            }
                        }
                        return false;
                    });

                } else {
                    _autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    _autocompletePatientname.setOnTouchListener(null);

                }


            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (_autocompletePatientname.getText().toString().length() > 0) {

                    String Query = "select name,Patid from patreg order by name";
                    BaseConfig.SelectedGetPatientDetailsFilter(Query, MedicinePrescription.this, _autocompletePatientname, s.toString());

                }
                _imgvwPatientphoto.setImageBitmap(null);
                _tvwAgegender.setText(null);

            }
        });


        _autocompleteMedicineName.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {



                Drawable rightDrawable = AppCompatResources.getDrawable(_autocompleteMedicineName.getContext(), R.drawable.ic_clear_button);
                if (_autocompleteMedicineName.getText().length() > 0) {
                    _autocompleteMedicineName.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    _autocompleteMedicineName.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            final int DRAWABLE_LEFT = 0;
                            final int DRAWABLE_TOP = 1;
                            final int DRAWABLE_RIGHT = 2;
                            final int DRAWABLE_BOTTOM = 3;

                            if (event.getAction() == MotionEvent.ACTION_UP) {
                                if (event.getRawX() >= (_autocompleteMedicineName.getRight() - _autocompleteMedicineName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                    _autocompleteMedicineName.setText("");

                                    return true;
                                }
                            }
                            return false;
                        }
                    });

                } else {
                    _autocompleteMedicineName.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

                    _autocompleteMedicineName.setOnTouchListener(null);

                }



            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                if (_autocompleteMedicineName.getText().toString().length() > 0) {
                    String Query = "SELECT (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims where (Schedule_Category='Schedule 2' or Schedule_Category='Schedule 3')";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, _autocompleteMedicineName, s.toString());

                }
            }
        });


        _imgvwPatientphoto.setOnClickListener(v -> {
            if (_autocompleteMedicineName.getText().length() > 0
                    && _tvwAgegender.getText().length() > 0) {

                BaseConfig.Show_Patient_PhotoInDetail(_autocompleteMedicineName.getText().toString().split("-")[0], _autocompleteMedicineName.getText().toString().split("-")[1], _tvwAgegender.getText().toString(), MedicinePrescription.this);
            }
        });


        _autocompletePharmacyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (_autocompletePharmacyName.getText().toString().length() > 0) {
                    String Query = "select distinct pharmacyname as dvalue from Pharmacy order by pharmacyname;";
                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, _autocompletePharmacyName, s.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        _autocmpltxtInjection.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (_autocmpltxtInjection.getText().toString().length() > 0) {

                    String Query = "SELECT (CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG||'-'||DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims where MEDICINEINTAKETYPE='INJ' and " +
                            "Schedule_Category='Schedule 1' and DOSAGE!=''";

                    BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, MedicinePrescription.this, _autocmpltxtInjection, s.toString());

                }

                if (_autocmpltxtInjection.getText().toString().length() == 0) {
                    _edtInjecdosage.setText("");
                    _edtInjecdosage.setFocusableInTouchMode(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });


        _autocmpltxtInjection.setOnItemClickListener((adapterView, view, i, l) -> {
            if (_autocmpltxtInjection.getText().toString().length() > 0) {

                String str = _autocmpltxtInjection.getText().toString();
                if (str.contains("-")) {
                    _edtInjecdosage.setText(str.split("-")[1]);
                    _autocmpltxtInjection.setText(str.split("-")[0]);
                    _edtInjecdosage.setFocusableInTouchMode(false);
                } else {
                    _edtInjecdosage.requestFocus();
                    Toast.makeText(MedicinePrescription.this, "Dosage not available.. please enter manually", Toast.LENGTH_SHORT).show();
                }

            }
        });


        _btnAddinj.setOnClickListener(view -> {

            if (_autocmpltxtInjection.getText().length() > 0) {
                if (_edtInjecdosage.getText().length() > 0) {
                    if (_edtInjquantity.getText().length() > 0) {

                        String Query = "SELECT serverid as ret_values FROM cims where MEDICINEINTAKETYPE='INJ' and MARKETNAMEOFDRUG='" + _autocmpltxtInjection.getText().toString() + "' and Schedule_Category='Schedule 1' and DOSAGE!=''";

                        String InjectionId = BaseConfig.GetValues(Query);


                        CommonDataObjects.InjectionGetSet obj = new CommonDataObjects.InjectionGetSet();

                        obj.setInjectionId(InjectionId);
                        obj.setInjectionName(_autocmpltxtInjection.getText().toString());
                        obj.setDosage(_edtInjecdosage.getText().toString());
                        obj.setQuantity(_edtInjquantity.getText().toString());
                        injectionGetSets.add(obj);

                        mLinearLayoutManager = new LinearLayoutManager(MedicinePrescription.this);
                        _injectionRecycler.setLayoutManager(mLinearLayoutManager);
                        injectionRecyclerAdapter = new InjectionRecyclerAdapter(injectionGetSets, _injectionRecycler);
                        _injectionRecycler.setAdapter(injectionRecyclerAdapter);
                        Toast.makeText(MedicinePrescription.this, "Injection and dosage added to list..", Toast.LENGTH_SHORT).show();

                        _autocmpltxtInjection.setText("");
                        _edtInjecdosage.setText("");
                        _edtInjquantity.setText("");


                    } else {

                        _edtInjquantity.setError("Required");
                        Toast.makeText(MedicinePrescription.this, "Enter Quantity fields..", Toast.LENGTH_LONG).show();
                    }
                } else {

                    _edtInjecdosage.setError("Required");
                    Toast.makeText(MedicinePrescription.this, "Enter Dosage..", Toast.LENGTH_LONG).show();
                }

            } else {
                _autocmpltxtInjection.setError("Required");
                Toast.makeText(MedicinePrescription.this, "Enter Injection Name..", Toast.LENGTH_LONG).show();
            }


        });

        _chkNrmsaline.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                _edtDosage1.setEnabled(true);
                _edtDuration1.setEnabled(true);
                _edtQuantity1.setEnabled(true);
            } else {
                _edtDosage1.setEnabled(false);
                _edtDuration1.setEnabled(false);
                _edtQuantity1.setEnabled(false);

                _edtDosage1.setError(null);
                _edtDuration1.setError(null);
                _edtQuantity1.setError(null);

            }


        });

        _chkAsthasaline.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                _edtDosage2.setEnabled(true);
                _edtDuration2.setEnabled(true);
                _edtQuantity2.setEnabled(true);
            } else {
                _edtDosage2.setEnabled(false);
                _edtDuration2.setEnabled(false);
                _edtQuantity2.setEnabled(false);

                _edtDosage2.setError(null);
                _edtDuration2.setError(null);
                _edtQuantity2.setError(null);
            }

        });


        _chkInjection.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                _injectionLayout.setVisibility(View.VISIBLE);
            else
                _injectionLayout.setVisibility(View.GONE);
        });

        _chkNebulization.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                _nebulizationLayout.setVisibility(View.VISIBLE);
            else
                _nebulizationLayout.setVisibility(View.GONE);
        });

        _chkSuturing.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                _suturingLayout.setVisibility(View.VISIBLE);
            else
                _suturingLayout.setVisibility(View.GONE);
            _muliautcompltxtSuturing.setError(null);
        });

        _chkPlastering.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b)
                _plasteringLayout.setVisibility(View.VISIBLE);
            else
                _plasteringLayout.setVisibility(View.GONE);
            _muliautcompltxtPlastering.setError(null);
        });



        final CompoundButton.OnCheckedChangeListener checkedChangeListenerall= (compoundButton, b) -> {
            if (b) {
                _chkbxMorning.setChecked(true);
                _chkbxAfternoon.setChecked(true);
                _chkbxEvening.setChecked(true);
                _chkbxNite.setChecked(true);
                _edttxtMorningFrequency.setEnabled(true);
                _edttxtAfternoonFrequency.setEnabled(true);
                _edttxtEvening.setEnabled(true);
                _edttxtNite.setEnabled(true);
                _spinnerMorning.setEnabled(true);
                _spinnerAfternoon.setEnabled(true);
                _spinnerEvening.setEnabled(true);
                _spinnerNite.setEnabled(true);


            } else {
                _chkbxMorning.setChecked(false);
                _chkbxAfternoon.setChecked(false);
                _chkbxEvening.setChecked(false);
                _chkbxNite.setChecked(false);
                _edttxtMorningFrequency.setEnabled(false);
                _edttxtMorningFrequency.setText("1");
                _edttxtAfternoonFrequency.setEnabled(false);
                _edttxtAfternoonFrequency.setText("1");
                _edttxtEvening.setEnabled(false);
                _edttxtEvening.setText("1");
                _edttxtNite.setEnabled(false);
                _edttxtNite.setText("1");
                _spinnerMorning.setEnabled(false);
                _spinnerMorning.setSelection(1);

                _spinnerAfternoon.setEnabled(false);
                _spinnerAfternoon.setSelection(1);

                _spinnerEvening.setEnabled(false);
                _spinnerEvening.setSelection(1);

                _spinnerNite.setEnabled(false);
                _spinnerNite.setSelection(1);

            }
        };

        _chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);

        _chkbxMorning.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                _edttxtMorningFrequency.setEnabled(true);
                _spinnerMorning.setEnabled(true);


            } else {
                _chkbxCheckall.setOnCheckedChangeListener(null);
                _chkbxCheckall.setChecked(false);
                _chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                _edttxtMorningFrequency.setEnabled(false);
                _edttxtMorningFrequency.setText("1");
                _spinnerMorning.setEnabled(false);
                _spinnerMorning.setSelection(1);

            }

        });


        _chkbxAfternoon.setOnCheckedChangeListener((compoundButton, b) -> {

            if (b) {
                _edttxtAfternoonFrequency.setEnabled(true);
                _spinnerAfternoon.setEnabled(true);


            } else {
                _chkbxCheckall.setOnCheckedChangeListener(null);
                _chkbxCheckall.setChecked(false);
                _chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                _edttxtAfternoonFrequency.setEnabled(false);
                _edttxtAfternoonFrequency.setText("1");
                _spinnerAfternoon.setEnabled(false);
                _spinnerAfternoon.setSelection(1);
            }

        });


        _chkbxEvening.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                _edttxtEvening.setEnabled(true);
                _spinnerEvening.setEnabled(true);


            } else {
                _chkbxCheckall.setOnCheckedChangeListener(null);
                _chkbxCheckall.setChecked(false);
                _chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                _edttxtEvening.setEnabled(false);
                _edttxtEvening.setText("1");
                _spinnerEvening.setEnabled(false);
                _spinnerEvening.setSelection(1);
            }
        });


        _chkbxNite.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                _edttxtNite.setEnabled(true);
                _spinnerNite.setEnabled(true);
            } else {
                _chkbxCheckall.setOnCheckedChangeListener(null);
                _chkbxCheckall.setChecked(false);
                _chkbxCheckall.setOnCheckedChangeListener(checkedChangeListenerall);
                _edttxtNite.setEnabled(false);
                _edttxtNite.setText("1");
                _spinnerNite.setEnabled(false);
                _spinnerNite.setSelection(1);
            }
        });

        try {


            LoadDrWorkingDays();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//End controlls

    private void LoadFavourtieMedicines() {

        Toast.makeText(this, "Inprocess..", Toast.LENGTH_SHORT).show();
    }

    private void LoadMedicineTemplate() {


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select distinct TemplateName from TemplateDtls order by id;", null);
        templates_list = new ArrayList<>();
        //templates_list.add(getString(R.string.medicine_template_txt));

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String firstName = c.getString(c.getColumnIndex("TemplateName"));
                    templates_list.add(firstName);

                } while (c.moveToNext());
            }
        }

        db.close();
        Objects.requireNonNull(c).close();

        if (templates_list.size() == 0) {
            //Toast.makeText(MedicinePrescription.this, "No medicine templates available..add it from masters", Toast.LENGTH_LONG).show();
            BaseConfig.SnackBar(MedicinePrescription.this, "No medicine templates available..add it from masters", _medicinePrescriptionParentLayout, 2);
        }


        if (templates_list.size() > 0 && templates_list != null) {

            CharSequence colors[] = templates_list.toArray(new CharSequence[templates_list.size()]);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Choose Medicine Template");
            builder.setItems(colors, (dialog, which) -> gettemplate(colors[which].toString()));
            builder.show();

        }

    }

    private final void LOAD_PATIENT_DETAILS(String PatientId, String TreatmentFor, String Diagnosis) {

        try {
            String Str_Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + '\'');
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + '\'');

            _autocompletePatientname.setText(String.format("%s-%s", Str_Patient_Name, PatientId));
            _tvwAgegender.setText(Patient_AgeGender);
            _multiautoTreatmentfor.setText(TreatmentFor);
            _multiautoDiagnosis.setText(Diagnosis);
            BaseConfig.LoadPatientImage(Str_Patient_Photo, _imgvwPatientphoto, 100);

            SelectedGetPatientDetails();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private final void SelectedGetPatientDetails() {


        try {
            String[] pna = _autocompletePatientname.getText().toString().split("-");

            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + '\'');
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + '\'');

            BaseConfig.patientEmail = BaseConfig.GetValues("select email as ret_values from Patreg where Patid='" + pna[1] + "'");

            _tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.Glide_LoadImageView(_imgvwPatientphoto, Str_Patient_Photo);


        } catch (Exception ignored) {

        }


    }

    private final void LoadValues(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        switch (id) {
                            case 1: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                Loadlist1.add(counrtyname);
                                break;
                            }
                            case 2: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                Loadlist2.add(counrtyname);
                                break;
                            }
                            case 3: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                Loadlist3.add(counrtyname);
                                break;
                            }
                            case 4: {
                                String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                                Loadlist4.add(counrtyname);
                                break;
                            }
                        }

                    } while (c.moveToNext());
                }
            }

            Objects.requireNonNull(c).close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private final void ClearAll() {
        _autocompletePatientname.setText("");
        _multiautoTreatmentfor.setText("");
        _multiautoDiagnosis.setText("");

        BaseConfig.Glide_LoadDefaultImageView(_imgvwPatientphoto);
        _tvwAgegender.setText("-");
        _autocompletePatientname.setEnabled(true);
        PreviousMedicineLayout.setVisibility(View.GONE);
    }
    //endregion

    //region MEDICINE PRESCRIPTION - SUBMIT CODE
    private boolean checkValidation() {
        boolean ret = true;


        if (!Validation1.hasText(_autocompletePharmacyName))
            ret = false;


        if (_chkSuturing.isChecked()) {
            if (_muliautcompltxtSuturing.getText().toString().length() == 0) {
                ret = false;
                _muliautcompltxtSuturing.setError("Required");
                Toast.makeText(MedicinePrescription.this, "Enter body part for plastering", Toast.LENGTH_SHORT).show();
            }

        }

        if (_chkPlastering.isChecked()) {
            if (_muliautcompltxtPlastering.getText().toString().length() == 0) {
                ret = false;
                _muliautcompltxtPlastering.setError("Required");
                Toast.makeText(MedicinePrescription.this, "Enter body part for suturing", Toast.LENGTH_SHORT).show();
            }

        }


        if (_chkNebulization.isChecked()) {
            if (_chkAsthasaline.isChecked()) {
                if (_edtDosage2.getText().toString().length() == 0 || _edtDuration2.getText().toString().length() == 0 || _edtQuantity2.getText().toString().length() == 0) {
                    ret = false;
                    _edtDosage2.setError("Required");
                    _edtDuration2.setError("Required");
                    _edtQuantity2.setError("Required");
                    Toast.makeText(MedicinePrescription.this, "Fill all fields of Asthaline saline", Toast.LENGTH_SHORT).show();
                }

            }
            if (_chkNrmsaline.isChecked()) {
                if (_edtDosage1.getText().toString().length() == 0 || _edtDuration1.getText().toString().length() == 0 || _edtQuantity1.getText().toString().length() == 0) {
                    ret = false;
                    _edtDosage1.setError("Required");
                    _edtDuration1.setError("Required");
                    _edtQuantity1.setError("Required");
                    Toast.makeText(MedicinePrescription.this, "Fill all fields of Normal saline", Toast.LENGTH_SHORT).show();
                }

            }
        }


        if (!Validation1.hasText(_multiautoDiagnosis))
            ret = false;
        if (!Validation1.hasText(_multiautoTreatmentfor))
            ret = false;
        if (!Validation1.hasText(_autocompletePatientname))
            ret = false;

        return ret;
    }

    private void SAVE_LOCAL() {

        try {
            if (checkValidation())

                SUBMIT_FORM();
            else
                Toast.makeText(MedicinePrescription.this, getString(R.string.check_missing), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void SUBMIT_FORM() throws InterruptedException, ExecutionException, SchemaException {
        SQLiteDatabase db = BaseConfig.GetDb();//MedicinePrescription.this);

        ContentValues values;


        String consulationfeee = "0";
        /////////////////////////////////////////////////////////////////////////////////////
        //Treatment master
        String[] tf = _multiautoTreatmentfor.getText().toString().trim().split(",");
        String[] dg1 = _multiautoDiagnosis.getText().toString().split(",");

        BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, this);
        BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, this);

        SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);

        Date date = new Date();



        String MedicinePrescriptionId = GetMedicinePrescriptionId();

        String[] PATIENT_INFO = _autocompletePatientname.getText().toString().split("-");

        String STR_PATIENT_NAME = PATIENT_INFO[0].trim();
        String STR_PATIENT_ID = PATIENT_INFO[1].trim();

        if (list.size() > 0) {

            //String dietrest = concatdietrestriction();

            String bulk_medicinename = concatpersonalhistrycnt();

            for (int j = 0; j < list.size(); j++) {

                String[] strmed = list.get(j).split("_");
                String Str = strmed[0].replace("[", "").replace("]", "").trim();

                String MedicineId = BaseConfig.GetMedicineIdFromName(Str, this);
                String remarkStr = "";
                remarkStr = _edtxtvwRemarks.getText().length() > 0 ? _edtxtvwRemarks.getText().toString() : "";


                values = new ContentValues();
                values.put("docid", BaseConfig.doctorId);
                values.put("DiagId", BaseConfig.digid);
                values.put("ptid", STR_PATIENT_ID);
                values.put("pname", STR_PATIENT_NAME);
                values.put("refdocname", BaseConfig.doctorname);
                values.put("clinicname", BaseConfig.clinicname);
                values.put("Medid", MedicinePrescriptionId);
                values.put("dsign", listcnt.get(j));
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", Integer.valueOf(1));
                values.put("medicinename", list.get(j));
                values.put("pagegen", _tvwAgegender.getText().toString());
                values.put("diagnosisdtls", _multiautoDiagnosis.getText().toString());
                values.put("treatmentfor", _multiautoTreatmentfor.getText().toString());
                values.put("nextvisit", _edtxtvwNextvisit.getText().toString());
                values.put("fee", consulationfeee);
                values.put("mobnum", BaseConfig.docmobile);
                values.put("diagnosis", _multiautoDiagnosis.getText().toString());
                values.put("imei", BaseConfig.Imeinum);
                values.put("Isupdate", Integer.valueOf(0));
                values.put("Dose", Str);
                values.put("Freq", "");
                values.put("Duration", listtottabcount.get(j));
                values.put("Diabeticdiet", "");
                values.put("Diabeticrenaldiet", "");
                values.put("Lowcholesterol_Cardiacdiet", "");
                values.put("Hypertensivediet", "");
                values.put("Diet_Warfarin", "");
                values.put("prefpharma", _autocompletePharmacyName.getText().toString());
                values.put("remarks", remarkStr);
                values.put("medicineId", MedicineId);
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_PRESCRIPTION, null, values);


            }

            // For sending bulk medicine for first time (Implemented on 4-12-14
            // / table name:SendBulkMedicine)
            values = new ContentValues();
            values.put("ptid", STR_PATIENT_ID);
            values.put("pname", STR_PATIENT_NAME);
            values.put("docid", BaseConfig.doctorId);
            values.put("refdocname", BaseConfig.doctorname);
            values.put("treatmentfor", _multiautoTreatmentfor.getText().toString());
            values.put("medicine", bulk_medicinename);
            values.put("IsActive", Integer.valueOf(1));
            values.put("Isupdate", Integer.valueOf(0));
            values.put("Medid", MedicinePrescriptionId);
            values.put("Pharmaname", _autocompletePharmacyName.getText().toString());
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_SEND_BULK_MEDICINE, null, values);

            final String Update_Query = "update presc set prescnum=prescnum +1";
            BaseConfig.SaveData(Update_Query);

            LoadDeleteTempTest();

            /**************************************************************************************
             Emergency / Causality
             */
            try {
                if (_chkInjection.isChecked() || _chkNebulization.isChecked() || _chkSuturing.isChecked() || _chkPlastering.isChecked()) {

                    String Str_InjectionJson = "";
                    String Str_Nebulization_Normal = "", Str_Nebulization_Asthaline = "";
                    String Str_Suturing = "", Str_Plastering = "", Str_Plastering_Slab = "", Str_Plastering_Cast = "";

                    //Injection
                    JSONObject from_db_obj = new JSONObject();
                    JSONArray export_jsonarray = new JSONArray();

                    if (_chkInjection.isChecked()) {
                        for (int i = 0; i < injectionGetSets.size(); i++) {
                            from_db_obj = new JSONObject();
                            from_db_obj.put("MedId", injectionGetSets.get(i).getInjectionId());
                            from_db_obj.put("InjectionName", injectionGetSets.get(i).getInjectionName());
                            from_db_obj.put("Dosage", injectionGetSets.get(i).getDosage());
                            from_db_obj.put("Quantity", injectionGetSets.get(i).getQuantity());
                            export_jsonarray.put(from_db_obj);
                        }
                    }


                    //Nebulization**********************************************************************
                    if (_chkNebulization.isChecked()) {
                        if (_chkNrmsaline.isChecked()) {
                            Str_Nebulization_Normal = "Normal saline - " + _edtDosage1.getText().toString() + " - " + _edtDuration1.getText().toString() + " - " + _edtQuantity1.getText().toString();
                        }

                        if (_chkAsthasaline.isChecked()) {
                            Str_Nebulization_Asthaline = "Asthaline saline - " + _edtDosage2.getText().toString() + " - " + _edtDuration2.getText() + " - " + _edtQuantity2.getText().toString();
                        }
                    }

                    //Suturing**************************************************************************
                    if (_chkSuturing.isChecked()) {
                        Str_Suturing = _muliautcompltxtSuturing.getText().toString();
                    }


                    //Plastering************************************************************************
                    JSONObject from_db_obj1 = new JSONObject();
                    JSONArray export_jsonarray1 = new JSONArray();
                    if (_chkPlastering.isChecked()) {

                        String Str_Plastering1 = _muliautcompltxtPlastering.getText().toString();
                        if (_chkSlab.isChecked()) {
                            String Str_Plastering_Slab1 = _chkSlab.getText().toString();
                        }
                        ;
                        if (_chkCast.isChecked()) {
                            String Str_Plastering_Cast1 = _chkCast.getText().toString();
                        }
                        ;
                        JSONObject from_db_obj11 = new JSONObject();
                        from_db_obj11.put("Plastering", Str_Plastering1);
                        from_db_obj11.put("Plastering_Slab", Str_Plastering_Slab);
                        from_db_obj11.put("Plastering_Cast", Str_Plastering_Cast);
                        export_jsonarray1.put(from_db_obj11);
                    }

                    values = new ContentValues();
                    values.put("Patid", STR_PATIENT_ID);
                    values.put("MPID", MedicinePrescriptionId);
                    values.put("DocId", BaseConfig.doctorId);
                    values.put("Injection", export_jsonarray.toString());
                    values.put("Nebulization_Normal", Str_Nebulization_Normal);
                    values.put("Nebulization_Asthaline", Str_Nebulization_Asthaline);
                    values.put("Suturing", Str_Suturing);
                    values.put("Plastering", export_jsonarray1.toString());
                    values.put("ActDate", BaseConfig.DeviceDate());
                    values.put("IsActive", Integer.valueOf(1));
                    values.put("IsUpdate", Integer.valueOf(0));
                    db.insert("Bind_EmergencyCausality", null, values);


                }

            } catch (Exception e) {
                e.printStackTrace();
            }

           // new GeneratePDF_From_Node(MedicinePrescriptionId).execute();

            Toast.makeText(MedicinePrescription.this, getString(R.string.saved_success_prescription), Toast.LENGTH_SHORT).show();
            MedicinePrescription.this.finish();
            Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
            startActivity(intent11);


        } else {
            showSimplePopUp(getString(R.string.add_medicine_details), 2);
            _autocompleteMedicineName.requestFocus();
        }


        db.close();
    }

    private final void ShowSweetAlert(String PDFLINK) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(getString(R.string.saved_success_prescription))
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {

                    MedicinePrescription.this.finish();
                    Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                    startActivity(intent11);

                    BaseConfig.PDFLINK = PDFLINK;

                });


    }

    private void NoNetworkConnectivity() {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_cloud_off_black_24dp)
                .setTitle(this.getString(R.string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(this.getString(R.string.saved_success_prescription_noconnectivity))
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {
                    MedicinePrescription.this.finish();
                    Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                    startActivity(intent11);
                });


    }

    private String concatpersonalhistrycnt() {
        // TODO Auto-generated method stub

        for (int j = 0; j < list.size(); j++) {
            list.get(j);

            concatpersonalhistrystr += list.get(j) + '\n';

        }
        return concatpersonalhistrystr;

    }

    private final boolean IsCheckBoxChecked() {
        return _chkbxMorning.isChecked() || _chkbxAfternoon.isChecked() || _chkbxEvening.isChecked() || _chkbxNite.isChecked();
    }

    private final void ADD_MEDICINE_LIST(int id) {

        if (BaseConfig.CheckTextView(_autocompleteMedicineName)) {

            String Query = "";

            String Query1 = "SELECT MARKETNAMEOFDRUG as dstatus1 FROM cims where MARKETNAMEOFDRUG='" + _autocompleteMedicineName.getText().toString() + "' or (MARKETNAMEOFDRUG||'-'||DOSAGE)='" + _autocompleteMedicineName.getText().toString() + '\'';

            String checkavaialblemedicine = Query1;

            Log.e("Medicine Status: ", checkavaialblemedicine);

            boolean b = BaseConfig.LoadReportsBooleanStatus(checkavaialblemedicine);
           // if (b) {
                if (BaseConfig.CheckTextView(_edttxtMorningFrequency) && BaseConfig.CheckTextView(_edttxtAfternoonFrequency) && BaseConfig.CheckTextView(_edttxtEvening) && BaseConfig.CheckTextView(_edttxtNite)) {
                    if (IsCheckBoxChecked()) {

                        if (BaseConfig.CheckSpinner(_spinnerDuration)) {

                            String remrks = null;

                            if (_radiobtnAfterFood.isChecked()) {
                                remrks = Medicine_Intake_Session[0];
                            } else if (_radiobtnBeforeFood.isChecked()) {
                                remrks = Medicine_Intake_Session[1];
                            } else if (_radiobtnWithFood.isChecked()) {
                                remrks = Medicine_Intake_Session[2];
                            }


                            if ((_chkbxMorning.isChecked()) && (_edttxtMorningFrequency.getText().length() >= 0)) {

                                session1 = Medicine_Frequency[0];
                                session_set1 = session1.substring(0, 1);

                            } else {

                                _edttxtMorningFrequency.setText("0");
                            }

                            if (_chkbxAfternoon.isChecked()) {
                                session2 = Medicine_Frequency[1];
                                session_set2 = session2.substring(0, 1);
                            } else {

                                _edttxtAfternoonFrequency.setText("0");
                            }

                            if (_chkbxEvening.isChecked()) {
                                session3 = Medicine_Frequency[2];
                                session_set3 = session3.substring(0, 1);
                            } else {

                                _edttxtEvening.setText("0");
                            }
                            if (_chkbxNite.isChecked()) {
                                session4 = Medicine_Frequency[3];
                                session_set4 = session4.substring(0, 1);
                            } else {

                                _edttxtNite.setText("0");
                            }


                            String mdub = "";
                            String mdub1 = '[' + _autocompleteMedicineName.getText().toString()
                                    + "]  _  "

                                    + session_set1 + '('
                                    + _edttxtMorningFrequency.getText().toString() + ')'

                                    + session_set2 + '('
                                    + _edttxtAfternoonFrequency.getText().toString() + ')'

                                    + session_set3 + '('
                                    + _edttxtEvening.getText().toString() + ')'

                                    + session_set4 + '('
                                    + _edttxtNite.getText().toString() + ')'

                                    + "  _ " + remrks + "  _   "
                                    + _spinnerDuration.getSelectedItem().toString();


                            if (checklistforduplicate1(mdub1)) {

                                list.add('[' + _autocompleteMedicineName.getText().toString()
                                        + "]  _  "

                                        + session_set1 + '('
                                        + _edttxtMorningFrequency.getText().toString() + ')'

                                        + session_set2 + '('
                                        + _edttxtAfternoonFrequency.getText().toString() + ')'

                                        + session_set3 + '('
                                        + _edttxtEvening.getText().toString() + ')'

                                        + session_set4 + '('
                                        + _edttxtNite.getText().toString() + ')'

                                        + "  _ " + remrks + "  _   "
                                        + _spinnerDuration.getSelectedItem().toString());


                                String IntakeType = "";
                                switch (id) {
                                    case 1:
                                        IntakeType = Medicine_Type_Array[0];
                                        break;
                                    case 2:
                                        IntakeType = Medicine_Type_Array[4];
                                        break;
                                    case 3:
                                        IntakeType = Medicine_Type_Array[5];
                                        break;
                                }

                                CommonDataObjects.MedicineGetSet obj = new CommonDataObjects.MedicineGetSet();
                                obj.setMedicine_Type(IntakeType);
                                obj.setMedicine_Name(_autocompleteMedicineName.getText().toString());
                                obj.setMorning_Value(session_set1 + '(' + _edttxtMorningFrequency.getText().toString() + ')');
                                obj.setAfternoon_Value(session_set2 + '(' + _edttxtAfternoonFrequency.getText().toString() + ')');
                                obj.setEvening_Value(session_set3 + '(' + _edttxtEvening.getText().toString() + ')');
                                obj.setNight_Value(session_set4 + '(' + _edttxtNite.getText().toString() + ')');
                                obj.setIntake_Value(remrks);
                                obj.setDays_Value(_spinnerDuration.getSelectedItem().toString());


                                medicineGetSets.add(obj);

                                mLinearLayoutManager = new LinearLayoutManager(this);
                                _recyclerMedicineList.setLayoutManager(mLinearLayoutManager);

                                medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, _recyclerMedicineList);
                                _recyclerMedicineList.setAdapter(medicineRecylerAdapter);


                                //for checking whether it is tablet
                                /*if 1 na tablet
                                 * if 2 na syrup
                                 * if 3 na oinment
                                 * */

                                if (id == 1) {
                                    listcnt.add("1-TAB");
                                } else if (id == 2) {
                                    listcnt.add("4-CAP");
                                }

                                int durcnt = 0;
                                StringBuilder durstr = new StringBuilder();
                                int imonth = 30;
                                int flag = 0;
                                int tottab = 0;


                                Double m1 = Double.valueOf(Double.parseDouble(_edttxtMorningFrequency.getText().toString()));
                                Double m2 = Double.valueOf(Double.parseDouble(_edttxtAfternoonFrequency.getText().toString()));
                                Double m3 = Double.valueOf(Double.parseDouble(_edttxtEvening.getText().toString()));
                                Double m4 = Double.valueOf(Double.parseDouble(_edttxtNite.getText().toString()));

                                int testval1 = (int) Math.round(m1.doubleValue());
                                int testval2 = (int) Math.round(m2.doubleValue());
                                int testval3 = (int) Math.round(m3.doubleValue());
                                int testval4 = (int) Math.round(m4.doubleValue());

                                int tottab1 = testval1 + testval2 + testval3 + testval4;


                                for (int i = 0; i < _spinnerDuration.getSelectedItem().toString().length(); i++) {

                                    String tablets = (String.valueOf(_spinnerDuration.getSelectedItem().toString().charAt(i)));
                                    try {
                                        if (tablets.equals("M")) {
                                            flag = 1;
                                        } else if (tablets.equals("T")) {
                                            flag = 2;
                                        }
                                        durcnt = Integer.parseInt(tablets);

                                        durstr.append(tablets);

                                    } catch (Exception ignored) {
                                        //e.printStackTrace();

                                    }

                                }


                                switch (flag) {
                                    case 1: {
                                        // tottab+=tabletval;
                                        durcnt = Integer.parseInt(durstr.toString());
                                        durcnt = tottab1 * imonth * durcnt;
                                        //int tottab1 = 0;
                                        break;
                                    }
                                    case 2: {
                                        durcnt = 30;
                                        // int tottab1 = 0;
                                        break;
                                    }
                                    default: {
                                        durcnt = Integer.parseInt(durstr.toString());
                                        durcnt = tottab1 * durcnt;
                                        // int tottab1 = 0;

                                        break;
                                    }
                                }


                                listtottabcount.add(String.valueOf(durcnt));

                                _autocompleteMedicineName.setText(null);
                                _spinnerDuration.setSelection(0);
                                _edttxtMorningFrequency.setText("1");
                                _edttxtAfternoonFrequency.setText("1");
                                _edttxtEvening.setText("1");
                                _edttxtNite.setText("1");
                                _edttxtMorningFrequency.setEnabled(false);
                                _edttxtAfternoonFrequency.setEnabled(false);
                                _edttxtEvening.setEnabled(false);
                                _edttxtNite.setEnabled(false);
                                _chkbxCheckall.setChecked(false);
                                _chkbxMorning.setChecked(false);
                                _chkbxAfternoon.setChecked(false);
                                _chkbxEvening.setChecked(false);
                                _chkbxNite.setChecked(false);
                                _radiobtnAfterFood.setChecked(true);
                                _radiobtnBeforeFood.setChecked(false);
                                _radiobtnWithFood.setChecked(false);
                                _spinnerDuration.setSelection(0);
                                _autocompleteMedicineName.requestFocus();

                            } else {
                                showSimplePopUp("Selected Medicine Already Added...", 2);
                            }

                        } else {
                            showSimplePopUp("Select Duration", 2);
                            _spinnerDuration.requestFocus();
                        }
                    } else {
                        showSimplePopUp("Select Frequency", 2);

                    }

                } else {
                    showSimplePopUp("Check Frequency", 2);
                }
          /*  } else {
                showSimplePopUp("Invalid medicine name\nSelect medicine from dropdown..", 2);
                _autocompleteMedicineName.requestFocus();
            }
*/

        } else {
            showSimplePopUp("Enter Medicine Name", 2);
            _autocompleteMedicineName.requestFocus();
        }

    }

    private final void ADD_MEDICINE_SYRUP() {

        if (BaseConfig.CheckTextView(_autocompleteMedicineName)) {

            if (BaseConfig.CheckSpinner(_spinnerMorning) && BaseConfig.CheckSpinner(_spinnerAfternoon) && BaseConfig.CheckSpinner(_spinnerEvening) && BaseConfig.CheckSpinner(_spinnerNite)) {


                if (IsCheckBoxChecked()) {

                    if (BaseConfig.CheckSpinner(_spinnerDuration)) {
                        // if(BaseConfig.CheckSpinner(remarks))
                        // {
                        String remrks = null;
                        if (_radiobtnAfterFood.isChecked()) {
                            remrks = Medicine_Intake_Session[0];

                        } else if (_radiobtnBeforeFood.isChecked()) {
                            remrks = Medicine_Intake_Session[1];
                        } else if (_radiobtnWithFood.isChecked()) {
                            remrks = Medicine_Intake_Session[2];
                        }

                        String session1 = null, session2 = null, session3 = null, session4 = null;

                        String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";


                        String syrmorning = "", syrafternn = "", syreve = "", syrnight = "";

                        if (_spinnerMorning.getSelectedItemPosition() > 0) {
                            syrmorning = _spinnerMorning.getSelectedItem().toString();
                        }

                        if (_spinnerAfternoon.getSelectedItemPosition() > 0) {
                            syrafternn = _spinnerAfternoon.getSelectedItem().toString();
                        }

                        if (_spinnerEvening.getSelectedItemPosition() > 0) {
                            syreve = _spinnerEvening.getSelectedItem().toString();
                        }

                        if (_spinnerNite.getSelectedItemPosition() > 0) {
                            syrnight = _spinnerNite.getSelectedItem().toString();
                        }


                        if ((_chkbxMorning.isChecked()) && (_spinnerMorning.getSelectedItemPosition() >= 0)) {
                            String session11 = Medicine_Frequency[0];
                            session_set1 = session11.substring(0, 1);
                        } else {
                            // session_set1 = "";
                            //et_mrng.setText("0");
                            syrmorning = "0";

                        }

                        if (_chkbxAfternoon.isChecked()) {
                            String session21 = Medicine_Frequency[1];
                            session_set2 = session21.substring(0, 1);
                        } else {
                            // session_set2 = "";
                            //et_aftn.setText("0");
                            syrafternn = "0";
                        }

                        if (_chkbxEvening.isChecked()) {
                            String session31 = Medicine_Frequency[2];
                            session_set3 = session31.substring(0, 1);
                        } else {
                            // session_set3 = "";
                            //et_eve.setText("0");
                            syreve = "0";
                        }
                        if (_chkbxNite.isChecked()) {
                            String session41 = Medicine_Frequency[3];
                            session_set4 = session41.substring(0, 1);
                        } else {
                            // session_set4 = "";
                            //et_night.setText("0");
                            syrnight = "0";
                        }


                        String mdub = "";
                        String mdub1 = '[' + _autocompleteMedicineName.getText().toString()
                                + "]  _  "

                                + session_set1 + '('
                                + syrmorning + ')'

                                + session_set2 + '('
                                + syrafternn + ')'

                                + session_set3 + '('
                                + syreve + ')'

                                + session_set4 + '('
                                + syrnight + ')'

                                + "  _ " + remrks + "  _   "
                                + _spinnerDuration.getSelectedItem().toString();

                        if (checklistforduplicate1(mdub1)) {

                            list.add('[' + _autocompleteMedicineName.getText().toString()
                                    + "]  _  "

                                    + session_set1 + '('
                                    + syrmorning + ')'

                                    + session_set2 + '('
                                    + syrafternn + ')'

                                    + session_set3 + '('
                                    + syreve + ')'

                                    + session_set4 + '('
                                    + syrnight + ')'

                                    + "  _ " + remrks + "  _   "
                                    + _spinnerDuration.getSelectedItem().toString());


                            CommonDataObjects.MedicineGetSet obj = new CommonDataObjects.MedicineGetSet();
                            obj.setMedicine_Type("SYR");
                            obj.setMedicine_Name(_autocompleteMedicineName.getText().toString());
                            obj.setMorning_Value(session_set1 + '(' + syrmorning + ')');
                            obj.setAfternoon_Value(session_set2 + '(' + syrafternn + ')');
                            obj.setEvening_Value(session_set3 + '(' + syreve + ')');
                            obj.setNight_Value(session_set4 + '(' + syrnight + ')');
                            obj.setIntake_Value(remrks);
                            obj.setDays_Value(_spinnerDuration.getSelectedItem().toString());


                            medicineGetSets.add(obj);

                            // use a linear layout manager
                            mLinearLayoutManager = new LinearLayoutManager(this);
                            _recyclerMedicineList.setLayoutManager(mLinearLayoutManager);

                            medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, _recyclerMedicineList);

                            _recyclerMedicineList.setAdapter(medicineRecylerAdapter);
                            //for checking whether it is tablet
                            /*if 1 na tablet
                             * if 2 na syrup
                             * if 3 na oinment
                             * */

                            listcnt.add("2-SYR");
                            listtottabcount.add("1");

                            _autocompleteMedicineName.setText(null);
                            _spinnerDuration.setSelection(0);

                            _spinnerMorning.setSelection(0);
                            _spinnerAfternoon.setSelection(0);
                            _spinnerEvening.setSelection(0);
                            _spinnerNite.setSelection(0);

                            _edttxtMorningFrequency.setEnabled(false);
                            _edttxtAfternoonFrequency.setEnabled(false);
                            _edttxtEvening.setEnabled(false);
                            _edttxtNite.setEnabled(false);

                            _chkbxCheckall.setChecked(false);
                            _chkbxMorning.setChecked(false);
                            _chkbxAfternoon.setChecked(false);
                            _chkbxEvening.setChecked(false);
                            _chkbxNite.setChecked(false);

                            _edttxtMorningFrequency.setVisibility(View.VISIBLE);
                            _edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
                            _edttxtEvening.setVisibility(View.VISIBLE);
                            _edttxtNite.setVisibility(View.VISIBLE);

                            _spinnerMorning.setVisibility(View.GONE);
                            _spinnerAfternoon.setVisibility(View.GONE);
                            _spinnerEvening.setVisibility(View.GONE);
                            _spinnerNite.setVisibility(View.GONE);


                            _radiobtnAfterFood.setChecked(true);
                            _radiobtnBeforeFood.setChecked(false);
                            _radiobtnWithFood.setChecked(false);

                            _autocompleteMedicineName.requestFocus();
                            _spinnerDuration.setSelection(0);

                        } else {
                            showSimplePopUp("Selected Medicine Already Added...", 2);
                        }

                    } else {
                        showSimplePopUp("Select Duration", 2);
                        _spinnerDuration.requestFocus();
                    }
                } else {
                    showSimplePopUp("Select Frequency", 2);

                }

            } else {
                showSimplePopUp("Check Frequency", 2);
            }

        } else {
            showSimplePopUp("Enter Medicine Name", 2);
            _autocompleteMedicineName.requestFocus();
        }

    }

    private final void ADD_MEDICINE_OINTMENT() {
        if (BaseConfig.CheckTextView(_autocompleteMedicineName)) {

            if (IsCheckBoxChecked()) {

                if (BaseConfig.CheckSpinner(_spinnerDuration)) {

                    String remrks = "-";

                    String session1 = null, session2 = null, session3 = null, session4 = null;
                    String session_set1 = "M", session_set2 = "A", session_set3 = "E", session_set4 = "N";

                    String oint_morning = "-", oint_afternn = "-", oint_eve = "-", oint_night = "-";

                    if (_spinnerMorning.getSelectedItemPosition() > 0) {
                        oint_morning = _spinnerMorning.getSelectedItem().toString();
                    }

                    if (_spinnerAfternoon.getSelectedItemPosition() > 0) {
                        oint_afternn = _spinnerAfternoon.getSelectedItem().toString();
                    }

                    if (_spinnerEvening.getSelectedItemPosition() > 0) {
                        oint_eve = _spinnerEvening.getSelectedItem().toString();
                    }

                    if (_spinnerNite.getSelectedItemPosition() > 0) {
                        oint_night = _spinnerNite.getSelectedItem().toString();
                    }


                    if ((_chkbxMorning.isChecked()) && (_spinnerMorning.getSelectedItemPosition() >= 0)) {
                        String session11 = Medicine_Frequency[0];
                        session_set1 = session11.substring(0, 1);

                    } else {

                        _edttxtMorningFrequency.setText("0");
                    }

                    if (_chkbxAfternoon.isChecked()) {
                        String session21 = Medicine_Frequency[1];
                        session_set2 = session21.substring(0, 1);

                    } else {

                        _edttxtAfternoonFrequency.setText("0");
                    }

                    if (_chkbxEvening.isChecked()) {
                        String session31 = Medicine_Frequency[2];
                        session_set3 = session31.substring(0, 1);

                    } else {

                        _edttxtEvening.setText("0");
                    }
                    if (_chkbxNite.isChecked()) {
                        String session41 = Medicine_Frequency[3];
                        session_set4 = session41.substring(0, 1);

                    } else {
                        _edttxtNite.setText("0");
                    }

                    String mdub = "";
                    String mdub1 = '[' + _autocompleteMedicineName.getText().toString()
                            + "]  _  "

                            + session_set1 + '('
                            + oint_morning + ')'

                            + session_set2 + '('
                            + oint_afternn + ')'

                            + session_set3 + '('
                            + oint_eve + ')'

                            + session_set4 + '('
                            + oint_night + ')'

                            + "  _ "
                            + _spinnerDuration.getSelectedItem().toString();


                    if (checklistforduplicate1(mdub1)) {

                        list.add('[' + _autocompleteMedicineName.getText().toString()
                                + "]  _  "

                                + session_set1 + '('
                                + oint_morning + ')'

                                + session_set2 + '('
                                + oint_afternn + ')'

                                + session_set3 + '('
                                + oint_eve + ')'

                                + session_set4 + '('
                                + oint_night + ')'
                                + "  _ "
                                + _spinnerDuration.getSelectedItem().toString());

                        CommonDataObjects.MedicineGetSet obj = new CommonDataObjects.MedicineGetSet();
                        obj.setMedicine_Type("OINT");
                        obj.setMedicine_Name(_autocompleteMedicineName.getText().toString());
                        obj.setMorning_Value(session_set1 + '(' + oint_morning + ')');
                        obj.setAfternoon_Value(session_set2 + '(' + oint_afternn + ')');
                        obj.setEvening_Value(session_set3 + '(' + oint_eve + ')');
                        obj.setNight_Value(session_set4 + '(' + oint_night + ')');
                        obj.setIntake_Value(remrks);
                        obj.setDays_Value(_spinnerDuration.getSelectedItem().toString());


                        medicineGetSets.add(obj);

                        // use this setting to improve performance if you know that changes
                        // in content do not change the layout size of the RecyclerView
                        //recyclerview.setHasFixedSize(true);

                        // use a linear layout manager
                        mLinearLayoutManager = new LinearLayoutManager(this);
                        _recyclerMedicineList.setLayoutManager(mLinearLayoutManager);

                        medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, _recyclerMedicineList);

                        _recyclerMedicineList.setAdapter(medicineRecylerAdapter);
                        //for checking whether it is tablet
                        /*if 1 na tablet
                         * if 2 na syrup
                         * if 3 na oinment
                         * */

                        listcnt.add("3-OINT");
                        listtottabcount.add("1");

                        _autocompleteMedicineName.setText(null);
                        _spinnerDuration.setSelection(0);

                        _edttxtMorningFrequency.setText("1");
                        _edttxtAfternoonFrequency.setText("1");
                        _edttxtEvening.setText("1");
                        _edttxtNite.setText("1");

                        _edttxtMorningFrequency.setEnabled(false);
                        _edttxtAfternoonFrequency.setEnabled(false);
                        _edttxtEvening.setEnabled(false);
                        _edttxtNite.setEnabled(false);

                        _chkbxCheckall.setChecked(false);
                        _chkbxMorning.setChecked(false);
                        _chkbxAfternoon.setChecked(false);
                        _chkbxEvening.setChecked(false);
                        _chkbxNite.setChecked(false);

                        _radiobtnAfterFood.setChecked(true);
                        _radiobtnBeforeFood.setChecked(false);
                        _radiobtnWithFood.setChecked(false);

                        _radiogrpIntakeOptions.setVisibility(View.VISIBLE);
                        _edttxtMorningFrequency.setVisibility(View.VISIBLE);
                        _edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
                        _edttxtEvening.setVisibility(View.VISIBLE);
                        _edttxtNite.setVisibility(View.VISIBLE);

                        _autocompleteMedicineName.requestFocus();
                        _spinnerDuration.setSelection(0);

                    } else {
                        showSimplePopUp("Selected Medicine Already Added...", 2);
                    }

                } else {
                    showSimplePopUp("Select Duration", 2);
                    _spinnerDuration.requestFocus();
                }
            } else {
                showSimplePopUp("Select Frequency", 2);

            }

        } else {
            showSimplePopUp("Enter Medicine Name", 2);
            _spinnerDuration.requestFocus();
        }

    }

    private final void GET_MEDICINE_TYPE() {


        String Query = "select distinct MEDICINEINTAKETYPE as ret_values from cims where (MARKETNAMEOFDRUG='" + _autocompleteMedicineName.getText().toString() + "' or (MARKETNAMEOFDRUG||'-'||DOSAGE)='" + _autocompleteMedicineName.getText().toString() + "') and (Schedule_Category='Schedule 2' or Schedule_Category='Schedule 3')";

        String values = BaseConfig.GetValues(Query);

        Medicine_Type = values;

        if (Medicine_Type_Array[0].equals(values)) {

            _spinnerMorning.setVisibility(View.GONE);
            _spinnerAfternoon.setVisibility(View.GONE);
            _spinnerEvening.setVisibility(View.GONE);
            _spinnerNite.setVisibility(View.GONE);


            _edttxtMorningFrequency.setVisibility(View.VISIBLE);
            _edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
            _edttxtEvening.setVisibility(View.VISIBLE);
            _edttxtNite.setVisibility(View.VISIBLE);

            //CQ 13
            _chkbxMorning.setChecked(true);
            _edttxtMorningFrequency.setEnabled(true);

            _chkbxNite.setChecked(true);
            _edttxtNite.setEnabled(true);

            _spinnerDuration.setSelection(3);


        } else if (Medicine_Type_Array[1].equals(values)) {//SYRUP ARRAY ADAPTER
            ArrayAdapter<CharSequence> syrup_adapter = ArrayAdapter.createFromResource(MedicinePrescription.this, R.array.syrup_measure, android.R.layout.simple_spinner_item);
            syrup_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            _spinnerMorning.setAdapter(syrup_adapter);
            _spinnerAfternoon.setAdapter(syrup_adapter);
            _spinnerEvening.setAdapter(syrup_adapter);
            _spinnerNite.setAdapter(syrup_adapter);

            _spinnerMorning.setVisibility(View.VISIBLE);
            _spinnerAfternoon.setVisibility(View.VISIBLE);
            _spinnerEvening.setVisibility(View.VISIBLE);
            _spinnerNite.setVisibility(View.VISIBLE);

            _spinnerMorning.setEnabled(false);
            _spinnerAfternoon.setEnabled(false);
            _spinnerEvening.setEnabled(false);
            _spinnerNite.setEnabled(false);

            _edttxtMorningFrequency.setVisibility(View.GONE);
            _edttxtAfternoonFrequency.setVisibility(View.GONE);
            _edttxtEvening.setVisibility(View.GONE);
            _edttxtNite.setVisibility(View.GONE);


            //CQ 13
            _chkbxMorning.setChecked(true);
            _spinnerMorning.setEnabled(true);

            _chkbxNite.setChecked(true);
            _spinnerNite.setEnabled(true);

            _spinnerDuration.setSelection(3);


        } else if (Medicine_Type_Array[2].equals(values)) {//OINTMENT ARRAY ADAPTER
            ArrayAdapter<CharSequence> oint_adapter = ArrayAdapter.createFromResource(MedicinePrescription.this, R.array.oint_measure, android.R.layout.simple_spinner_item);
            oint_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            _spinnerMorning.setAdapter(oint_adapter);
            _spinnerAfternoon.setAdapter(oint_adapter);
            _spinnerEvening.setAdapter(oint_adapter);
            _spinnerNite.setAdapter(oint_adapter);

            _spinnerMorning.setVisibility(View.VISIBLE);
            _spinnerAfternoon.setVisibility(View.VISIBLE);
            _spinnerEvening.setVisibility(View.VISIBLE);
            _spinnerNite.setVisibility(View.VISIBLE);


            _edttxtMorningFrequency.setVisibility(View.GONE);
            _edttxtAfternoonFrequency.setVisibility(View.GONE);
            _edttxtEvening.setVisibility(View.GONE);
            _edttxtNite.setVisibility(View.GONE);

            _radiogrpIntakeOptions.setVisibility(View.GONE);

            //CQ 13
            _chkbxMorning.setChecked(true);
            _chkbxNite.setChecked(true);
            _spinnerDuration.setSelection(3);


        } else if (Medicine_Type_Array[3].equals(values)) {//DROPS ARRAY ADAPTER
            ArrayAdapter<CharSequence> drops_adapter = ArrayAdapter.createFromResource(MedicinePrescription.this, R.array.drops_measure, android.R.layout.simple_spinner_item);
            drops_adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

            _spinnerMorning.setAdapter(drops_adapter);
            _spinnerAfternoon.setAdapter(drops_adapter);
            _spinnerEvening.setAdapter(drops_adapter);
            _spinnerNite.setAdapter(drops_adapter);

            _spinnerMorning.setVisibility(View.VISIBLE);
            _spinnerAfternoon.setVisibility(View.VISIBLE);
            _spinnerEvening.setVisibility(View.VISIBLE);
            _spinnerNite.setVisibility(View.VISIBLE);


            _edttxtMorningFrequency.setVisibility(View.GONE);
            _edttxtAfternoonFrequency.setVisibility(View.GONE);
            _edttxtEvening.setVisibility(View.GONE);
            _edttxtNite.setVisibility(View.GONE);

            _radiogrpIntakeOptions.setVisibility(View.GONE);

            //CQ 13
            _chkbxMorning.setChecked(true);
            _chkbxNite.setChecked(true);
            _spinnerDuration.setSelection(3);


        } else if (Medicine_Type_Array[4].equals(values) || Medicine_Type_Array[5].equals(values)) {
            _spinnerMorning.setVisibility(View.GONE);
            _spinnerAfternoon.setVisibility(View.GONE);
            _spinnerEvening.setVisibility(View.GONE);
            _spinnerNite.setVisibility(View.GONE);


            _edttxtMorningFrequency.setVisibility(View.VISIBLE);
            _edttxtAfternoonFrequency.setVisibility(View.VISIBLE);
            _edttxtEvening.setVisibility(View.VISIBLE);
            _edttxtNite.setVisibility(View.VISIBLE);

            //CQ 13
            _chkbxMorning.setChecked(true);
            _edttxtMorningFrequency.setEnabled(true);

            _chkbxNite.setChecked(true);
            _edttxtNite.setEnabled(true);

            _spinnerDuration.setSelection(3);


        }
    }

    private final void ConfirmDeleteInjection(final int position, RecyclerView recyclerView1) {


        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(this.getResources().getString(R.string.delete_Confirmation))
                .setDescription(this.getResources().getString(R.string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    injectionGetSets.remove(position);
                    recyclerView1.getAdapter().notifyDataSetChanged();

                });


    }

    private final void ConfirmDelete(final int position) {


        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.red_400)
                .setImage(R.drawable.ic_delete_forever_black_24dp)
                .setTitle(this.getResources().getString(R.string.delete_Confirmation))
                .setDescription(this.getResources().getString(R.string.do_you_want_to_delete))
                .setPossitiveButtonTitle("YES")
                .setNegativeButtonTitle("NO")
                .setOnPossitiveListener(() -> {

                    medicineGetSets.remove(position);

                    medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, _recyclerMedicineList);

                    _recyclerMedicineList.setAdapter(medicineRecylerAdapter);


                    list.remove(position);
                    listcnt.remove(position);
                    listtottabcount.remove(position);


                });


    }

    private void showSimplePopUp(String msg, int Id) {

      /*  AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Information");
        helpBuilder.setMessage(msg);
        helpBuilder.setPositiveButton("Ok",
                (dialog, which) -> {

                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();*/


        int image = R.drawable.ic_success_done;
        int color = R.color.green_500;

        if (Id == 1)//Success
        {
            image = R.drawable.ic_success_done;
            color = R.color.green_500;
        } else if (Id == 2)//Warning
        {
            image = R.drawable.ic_warning_black_24dp;
            color = R.color.orange_500;
        }
        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(color)
                .setImage(image)
                .setTitle(this.getString(R.string.information))
                .setDescription(msg)
                .setOnPossitiveListener(() -> {

                })
                .setPossitiveButtonTitle(this.getString(R.string.ok));


    }

    private final boolean checklistforduplicate1(String Str) {
        int flag = 1;
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                if (Str.split("_")[0].trim().equalsIgnoreCase(list.get(i).split("_")[0].trim())) {
                    flag = 0;
                    break;
                }
            }

        }

        return flag != 0;
    }

    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;
        LoadBack();
        System.gc();
    }

    @Override
    protected final void onDestroy() {

        super.onDestroy();
        System.gc();
    }

    private void LoadBack() {


        try {
            if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("MYPATIENT")) {

                BaseConfig.HideKeyboard(MedicinePrescription.this);

                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
                BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);



            } else if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("INPATIENT")) {

                BaseConfig.HideKeyboard(MedicinePrescription.this);
                Bundle b=new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
                BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, b);



            } else {

                BaseConfig.HideKeyboard(MedicinePrescription.this);
                LoadDeleteTempTest();
                BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);


            }
        } catch (Exception ignored) {

        }

    }

    //region MEDICINE PRESCRIPTION PDF GENERATE
    private final boolean CheckNodeServer() {

        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP))
                    .openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();

            if (status == 200) {
                statusvalue = true;
            }
            conn.disconnect();

            return statusvalue;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    //region LOAD PREVIOUS MEDICINE HISTORY
    private final void LoadPreviousMedHistory() {

        try {
            //Show Dialog
            View rootView = LayoutInflater.from(MedicinePrescription.this).inflate(R.layout.new_prescription_profile, null);

            profile_webvw = rootView.findViewById(R.id.webvw_prescription_profile);
            profile_webvw.getSettings().setJavaScriptEnabled(true);
            profile_webvw.setWebChromeClient(new WebChromeClient());

            NoDataFound = rootView.findViewById(R.id.img_nodata);

            PatientNameId = rootView.findViewById(R.id.patientid);

            String Query = "select name||' - '|| Patid as ret_values from Patreg where Patid='" + _autocompletePatientname.getText().toString().split("-")[1] + '\'';
            PatientNameId.setText(BaseConfig.GetValues(Query));

            LoadWebview(0);


            next_btn = rootView.findViewById(R.id.next);
            pre_btn = rootView.findViewById(R.id.prev);


            next_btn.setOnClickListener(arg0 -> Next());

            pre_btn.setOnClickListener(arg0 -> Previous());

            Current();

            final Dialog dialog = new Dialog(MedicinePrescription.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(rootView);
            Objects.requireNonNull(dialog.getWindow()).getAttributes().windowAnimations = R.style.DialogAnimation;
            dialog.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //endregion

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    private final void LoadWebview(int pos) {
        profile_webvw.getSettings().setJavaScriptEnabled(true);
        profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        profile_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        profile_webvw.setWebChromeClient(new MyWebChromeClient());

        profile_webvw.setBackgroundColor(0x00000000);
        profile_webvw.setVerticalScrollBarEnabled(true);
        profile_webvw.setHorizontalScrollBarEnabled(true);


        Toast.makeText(this, "Please wait previous medicine history loading..", Toast.LENGTH_SHORT).show();


        profile_webvw.getSettings().setJavaScriptEnabled(true);

        profile_webvw.getSettings().setAllowContentAccess(true);


        profile_webvw.setOnLongClickListener(v -> true);

        profile_webvw.setLongClickable(false);


        profile_webvw.addJavascriptInterface(new WebAppInterface(this), "android");
        try {

            profile_webvw.loadDataWithBaseURL("file:///android_asset/", LoadPrescriptionDetails(pos), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //endregion

    private final void Previous() {
        pos += 1;
        if (pos < mypatientprevmedicinehistory_medid.size()) {
            LoadWebview(pos);
            next_btn.setVisibility(View.VISIBLE);
        }
        if (pos == mypatientprevmedicinehistory_medid.size() - 1) {
            pre_btn.setVisibility(View.GONE);
            next_btn.setVisibility(View.VISIBLE);
        }
    }
    //endregion

    private final void Next() {
        pos -= 1;
        if (pos >= 0) {
            //SelectedGetPrevMedicineHistory(pos);
            LoadWebview(pos);
            pre_btn.setVisibility(View.VISIBLE);
        }
        if (pos == 0) {
            next_btn.setVisibility(View.GONE);
            pre_btn.setVisibility(View.VISIBLE);
        }
    }

    private final void Current() {
        if (mypatientprevmedicinehistory_medid.size() > 0) {

            LoadWebview(0);

        }
        if (mypatientprevmedicinehistory_medid.size() > 1) {
            pre_btn.setVisibility(View.VISIBLE);
            next_btn.setVisibility(View.GONE);
        }
    }

    private final String LoadPrescriptionDetails(int pos_val) {

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        mypatientprevmedicinehistory_medid = new ArrayList<>();

        Cursor c = db
                .rawQuery(
                        "select distinct Medid from Mprescribed where Ptid='" + _autocompletePatientname.getText().toString().split("-")[1] + "' order by Medid desc;",
                        null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    mypatientprevmedicinehistory_medid.add(c.getString(c.getColumnIndex("Medid")));

                } while (c.moveToNext());
            }
        }
        Objects.requireNonNull(c).close();
        //********************************************


        String values;
        String PreviousMedicalHistory = "", HereditaryDiseases = "";
        String AllergicTo = "", Smoking = "", Alcohol = "", Tobacco = "", Others = "", Medication = "", FamilyMedicalHistory = "";
        String Bowel = "", Micturition = "", PresentIllness = "", PastIllness = "", TreatmentforMedicineNamePeriod = "";
        String Obstetric = "", Gynaec = "";

        //ArrayList<PrescriptionGetSet> prescriptionGetSets = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();


        String[] Tabledata;
        String remarksStr, diagnosisStr = null, symStr;


        Cursor c1 = db.rawQuery("select remarks,refdocname,Medid,medicinename,treatmentfor,diagnosis,nextvisit,Actdate from Mprescribed where Ptid='" + _autocompletePatientname.getText().toString().split("-")[1] + "' and Medid='" + mypatientprevmedicinehistory_medid.get(pos_val) + "' order by Medid desc ;", null);

        if (c1.getCount() > 0) {
            NoDataFound.setVisibility(View.GONE);
        } else {
            NoDataFound.setVisibility(View.VISIBLE);
        }


        if (c1 != null) {
            if (c1.moveToFirst()) {
                do {

                    Tabledata = new String[c1.getCount()];

                    Tabledata = c1.getString(c1.getColumnIndex("medicinename")).split("_");

                    stringBuilder.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0].replace("[", "").replace("]", "")).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n");

                    sbM.append(c1.getString(c1.getColumnIndex("medicinename")));
                    sbM.append('\n');

                    nxtdt = c1.getString(c1.getColumnIndex("nextvisit"));

                    if (nxtdt.contains("1900"))//If next visit date empty na set agum @Kumar
                    {
                        nxtdt = "-";
                    }

                    visiteddt = c1.getString(c1.getColumnIndex("Actdate")).split(" ");
                    remarksStr = c1.getString(c1.getColumnIndex("remarks"));

                    refdocname = c1.getString(c1.getColumnIndex("refdocname"));
                    diagnosisStr = c1.getString(c1.getColumnIndex("diagnosis"));
                    SymptomsValue = c1.getString(c1.getColumnIndex("treatmentfor"));


                } while (c1.moveToNext());
            }

        }
        c1.close();


        values = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
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
                // "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>Patient Name:</b></td> \n" +
                //  "<td height=\"20\" style=\"color:#000000;\">"+BaseConfig.SelectedPName + "-" + BaseConfig.SelectedPID+"</td>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> Patient Medicine History</font>\n" +
                '\n' +
                '\n' +
                '\n' +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table table-bordered\">\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>Refered Doctor Name:</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">" + refdocname + "</td>\n" +
                "  <tr>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">Medicine Name</font></th>\n" +
                "    <th bgcolor=\"#3d5987\"><font color=\"#fff\">Interval</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Frequency</font></th>\n" +
                "\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">Duration</font></th>\n" +
                "  </tr>\n" +
                " \n" + stringBuilder +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Symptoms</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\">: " + SymptomsValue + "</td>\n" +
                '\n' + "</div>\n </br></br>" +
                "<td height=\"20\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> Diagnosis:</b></td> \n" +
                "<td height=\"20\" style=\"color:#000000;\"> " + diagnosisStr + "</td>\n" +
                "\n\n</br>" +
                "<div class=\"table-responsive\">          \n" +
                "<table class=\"table\">\n" +
                " <tr>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">Visited On: </font></th>\n" +
                "    <th  bgcolor=\"#3d5987\"><font color=\"#fff\">Next Visit On:</font></th>\n" +
                "\t </tr>\n" +
                "  <tr>\n" +
                "    <td>" + visiteddt[0] + "</td>\n" +
                "    <td>" + nxtdt + "</td>\n" +
                "\t  </tr>\n" +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                                                                               ";


        db.close();

        Toast.makeText(this, "Previous Medicine Prescription loaded successfully..", Toast.LENGTH_SHORT).show();


        return values;
    }


    //region MEDICINE TEMPLATE SAVE DIALOG AND INSERT

    private final void gettemplate(String str) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery(
                    "select MedicineName,TemplateId,TemplateName,Morning,Afternoon from TemplateDtls where TemplateName='"
                            + str.trim()
                            + "';", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        JSONArray jsonArray = new JSONArray(c.getString(c.getColumnIndex("MedicineName")));
                        JSONObject objJson = null;

                        //firstName = c.getString(c.getColumnIndex("MedicineName"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);


                            String medicinename = objJson.get("InjectionName").toString();

                            if (checklistforduplicate1(medicinename)) {

                                listcnt.add(objJson.get("TabType").toString());
                                listtottabcount.add(objJson.get("TabCount").toString());
                                list.add(medicinename);

                                String morningvalue, afternoonvalue, eveningvalue, nightvalue;

                                String values[] = medicinename.split("_");

                                String medicine_values = values[1].trim();

                                int mindex = medicine_values.indexOf('M');
                                int aindex = medicine_values.indexOf('A');
                                int eindex = medicine_values.indexOf('E');
                                int nindex = medicine_values.indexOf('N');


                                morningvalue = medicine_values.substring(mindex + 2, aindex - 1);
                                afternoonvalue = medicine_values.substring(aindex + 2, eindex - 1);
                                eveningvalue = medicine_values.substring(eindex + 2, nindex - 1);
                                nightvalue = medicine_values.substring(nindex + 2, medicine_values.length() - 1);


                                CommonDataObjects.MedicineGetSet obj = new CommonDataObjects.MedicineGetSet();
                                obj.setMedicine_Name(values[0]);
                                obj.setMorning_Value(session_set1 + '(' + morningvalue + ')');
                                obj.setAfternoon_Value(session_set2 + '(' + afternoonvalue + ')');
                                obj.setEvening_Value(session_set3 + '(' + eveningvalue + ')');
                                obj.setNight_Value(session_set4 + '(' + nightvalue + ')');
                                obj.setIntake_Value(values[2]);
                                obj.setDays_Value(values[3]);


                                medicineGetSets.add(obj);

                                // use a linear layout manager
                                mLinearLayoutManager = new LinearLayoutManager(this);
                                _recyclerMedicineList.setLayoutManager(mLinearLayoutManager);

                                medicineRecylerAdapter = new MedicineRecylerAdapter(medicineGetSets, _recyclerMedicineList);

                                _recyclerMedicineList.setAdapter(medicineRecylerAdapter);

                            } else {
                                showSimplePopUp(getString(R.string.select_medicine), 2);
                            }

                        }

                    } while (c.moveToNext());

                }
            }

            db.close();
            Objects.requireNonNull(c).close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void ShowSaveTemplateDialog() {
        CardView layoutRoot;
        TextView title;
        TextView templateId;
        TextView txtvwTitle;
        Button buttonOk, buttonCancel;
        EditText edtInputTemplatename;


        if (list.size() > 0) {


            LayoutInflater li = LayoutInflater.from(MedicinePrescription.this);
            View promptsView = li.inflate(R.layout.prompts, null);


            String tid = BaseConfig.GetValues("select tnum as ret_values from TemplateMax");
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MedicinePrescription.this);

            // set prompts.xml to alertdialog builder
            alertDialogBuilder.setView(promptsView);

            layoutRoot = promptsView.findViewById(R.id.layout_root);
            title = promptsView.findViewById(R.id.title);
            templateId = promptsView.findViewById(R.id.template_id);
            txtvwTitle = promptsView.findViewById(R.id.txtvw_title);
            buttonOk = promptsView.findViewById(R.id.button_ok);
            buttonCancel = promptsView.findViewById(R.id.button_cancel);
            edtInputTemplatename = promptsView.findViewById(R.id.edt_input_templatename);

            templateId.setText(String.format("Template No:%s", tid));

            // create alert dialog
            final AlertDialog alertDialog = alertDialogBuilder.create();

            alertDialog.show();

            buttonOk.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub


                if (edtInputTemplatename.getText().length() > 0) {

                    //select distinct TemplateName from TemplateDtls order by id;
                    //Checking duplicate template name
                    boolean q = BaseConfig.LoadReportsBooleanStatus("select TemplateName  as dstatus1 from TemplateDtls where UPPER(TemplateName)='" + edtInputTemplatename.getText().toString().toUpperCase() + "' order by id;");

                    if (q) {
                        Toast.makeText(MedicinePrescription.this, "Template name already exists..\nEnter new template name...", Toast.LENGTH_SHORT).show();
                        edtInputTemplatename.setText("");
                    } else {
                        BaseConfig.popuptemplate_name = edtInputTemplatename.getText().toString();
                        inserttemplate(edtInputTemplatename.getText().toString().toUpperCase());
                        alertDialog.cancel();

                    }

                } else {
                    edtInputTemplatename.setError("Required");
                    edtInputTemplatename.requestFocus();
                }


            });

            buttonCancel.setOnClickListener(arg0 -> alertDialog.cancel());

        } else {
            showSimplePopUp("Add Medicine Details", 2);
            _autocompleteMedicineName.requestFocus();
        }
    }

    private final void inserttemplate(String str) {

        SQLiteDatabase db = BaseConfig.GetDb();

        String tid = BaseConfig.GetValues("select tnum as ret_values from TemplateMax");
        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss",Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        if (list.size() > 0) {

            ContentValues contentValue;

            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();


            try {
                for (int j = 0; j < list.size(); j++) {
                    from_db_obj = new JSONObject();
                    from_db_obj.put("InjectionName", list.get(j));
                    from_db_obj.put("TabType", listcnt.get(j));
                    from_db_obj.put("TabCount", listtottabcount.get(j));
                    export_jsonarray.put(from_db_obj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            contentValue = new ContentValues();
            contentValue.put("Docid", BaseConfig.doctorId);
            contentValue.put("TemplateId", "Temp" + tid);
            contentValue.put("TemplateName", str);
            contentValue.put("MedicineName", export_jsonarray.toString());
            contentValue.put("dt", dttm);
            contentValue.put("Isupdate", "0");
            db.insert("TemplateDtls", null, contentValue);


            final String Update_Query = "update TemplateMax set tnum=tnum+1";
            db.execSQL(Update_Query);

            db.close();

            showSimplePopUp(getString(R.string.template_saved), 1);

        } else {
            showSimplePopUp(getString(R.string.add_medicines), 2);
            _autocompleteMedicineName.requestFocus();
        }

    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    private static class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    static class WebAppInterface {

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
        }

    }
    //endregion

    class GeneratePDF_From_Node extends AsyncTask<Void, Void, Void> {

        String medicinePrescriptionId = "";

        GeneratePDF_From_Node(String medicinePrescriptionId) {
            this.medicinePrescriptionId = medicinePrescriptionId;
        }

        protected final Void doInBackground(Void... params) {
            //Your implementation
            try {

                CheckNodeServer();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        protected final void onPostExecute(Void result) {
            // TODO: do something with the feed


            if (BaseConfig.CheckNetwork(MedicinePrescription.this)) {

                if (statusvalue) {

                    //region BACKGROUND SERVICE TO CHECK PDF
                    /*
                      New Method to get pdf from server
                     */
                    AsynkTaskCustom asynkTaskCustom = new AsynkTaskCustom(MedicinePrescription.this, "Please wait...Loading Pdf...");

                    asynkTaskCustom.execute(new onWriteCode<String>() {
                        @Override
                        public String onExecuteCode() throws SchemaException, InterruptedException, ExecutionException {

                            String results = "";


                            GeneratePDFNode generatePDFNode;
                            // Instantiate a controller
                            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(MedicinePrescription.this);
                            GeneratePDFNodeFactory controllerFactory = new GeneratePDFNodeFactory(magnetClient);
                            generatePDFNode = controllerFactory.obtainInstance();

                            String contentType = "application/json";
                            GetPDFNodeJsRequest body = new GetPDFNodeJsRequest();
                            JsonValue json = new JsonValue();

                            String PATIENTID = _autocompletePatientname.getText().toString().split("-")[1];
                            String PATIENTNAME = _autocompletePatientname.getText().toString().split("-")[0];

                            String INVESTIGATIONID = COMMON_INVESTIGATIONID;
                            String MEDICINEID = medicinePrescriptionId;
                            String DIAGNOSISID = COMMON_DIAGNOSISID;

                            String SYMPTOMS = _multiautoTreatmentfor.getText().toString();
                            String DIAGNOSIS = _multiautoDiagnosis.getText().toString();

                            //agegen.getText().toString()
                            String AGE = _tvwAgegender.getText().toString().split("-")[0];
                            String GENDER = _tvwAgegender.getText().toString().split("-")[1];

                            String DOCTORNAME = BaseConfig.doctorname;
                            String DOCTORID = BaseConfig.doctorId;
                            String DOCTORSPEC = BaseConfig.docspecli;
                            String DATETIME = BaseConfig.DeviceDate();
                            String NEXTVISITDATE = _edtxtvwVisitafter.getText().toString();
                            String HOSPITALNAME = BaseConfig.HOSPITALNAME;


                            json.setpATIENTID(BaseConfig.CheckDBString(PATIENTID));
                            json.setpATIENTNAME(BaseConfig.CheckDBString(PATIENTNAME));
                            json.setaGE(BaseConfig.CheckDBString(AGE));
                            json.setgENDER(BaseConfig.CheckDBString(GENDER));

                            json.setiNVESTIGATIONID(BaseConfig.CheckDBString(INVESTIGATIONID));
                            json.setmEDICINEID(BaseConfig.CheckDBString(MEDICINEID));
                            json.setdIAGNOSISID(BaseConfig.CheckDBString(DIAGNOSISID));

                            json.setsYMPTOMS(BaseConfig.CheckDBString(SYMPTOMS));
                            json.setdIAGNOSIS(BaseConfig.CheckDBString(DIAGNOSIS));

                            json.setdOCTORNAME(BaseConfig.CheckDBString(DOCTORNAME));
                            json.setdOCTORID(BaseConfig.CheckDBString(DOCTORID));
                            json.setdOCTORSPEC(BaseConfig.CheckDBString(DOCTORSPEC));
                            json.setdATETIME(BaseConfig.CheckDBString(DATETIME));

                            json.setnEXTVISITDATE(BaseConfig.CheckDBString(NEXTVISITDATE));
                            json.sethOSPITALNAME(BaseConfig.CheckDBString(HOSPITALNAME));

                            body.setJsonValue(json);

                            ContentValues pdfvalues = new ContentValues();
                            pdfvalues.put("PATIENTID", PATIENTID);
                            pdfvalues.put("PATIENTNAME", PATIENTNAME);
                            pdfvalues.put("INVESTIGATIONID", INVESTIGATIONID);
                            pdfvalues.put("MEDICINEID", MEDICINEID);
                            pdfvalues.put("DIAGNOSISID", DIAGNOSISID);
                            pdfvalues.put("SYMPTOMS", SYMPTOMS);
                            pdfvalues.put("DIAGNOSIS", DIAGNOSIS);
                            pdfvalues.put("AGE", AGE);
                            pdfvalues.put("GENDER", GENDER);
                            pdfvalues.put("DOCTORNAME", DOCTORNAME);
                            pdfvalues.put("DOCTORID", DOCTORID);
                            pdfvalues.put("DOCTORSPEC", DOCTORSPEC);
                            pdfvalues.put("ACT_DATETIME", DATETIME);
                            pdfvalues.put("NEXTVISITDATE", NEXTVISITDATE);
                            pdfvalues.put("HOSPITALNAME", HOSPITALNAME);
                            SQLiteDatabase db = BaseConfig.GetDb();
                            db.insert("Bind_PDFInfo", null, pdfvalues);


                            Call<PDFNodeJsResult> callObject = generatePDFNode.getPDFNodeJs(contentType, body, null);

                            PDFNodeJsResult result = callObject.get();

                            String resultvalue = result.getResult();
                            String resulturl = result.getURL();


                            results = !resultvalue.toString().equalsIgnoreCase("FAILED") ? result.getURL() : "";

                            return results;
                        }

                        @Override
                        public String onSuccess(String result) throws Exception {


                            try {
                                if (result != null && !result.equalsIgnoreCase("") ) {


                                    try {
                                        String PDFLink = BaseConfig.AppNodeIP + result;

                                        ShowSweetAlert(PDFLink);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }


                                } else {

                                    MedicinePrescription.this.finish();
                                    Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                                    startActivity(intent11);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(MedicinePrescription.this, getString(R.string.saved_success_prescription), Toast.LENGTH_SHORT).show();
                                MedicinePrescription.this.finish();
                                Intent intent11 = new Intent(MedicinePrescription.this, Dashboard_NavigationMenu.class);
                                startActivity(intent11);
                            }

                            return "";

                        }
                    });
                    //endregion


                } else {
                    NoNetworkConnectivity();

                }
            } else {
                NoNetworkConnectivity();

            }


        }
    }
    //endregion

    //region EMERGENCY - INJECTION ADAPTER
    public class InjectionRecyclerAdapter extends RecyclerView.Adapter<InjectionRecyclerAdapter.MyViewHolder> {

        final RecyclerView recyclerView;
        ArrayList<CommonDataObjects.InjectionGetSet> injectionGetSets = new ArrayList<>();

        InjectionRecyclerAdapter(ArrayList<CommonDataObjects.InjectionGetSet> injectionGetSets, RecyclerView recyclerView) {
            this.injectionGetSets = injectionGetSets;
            this.recyclerView = recyclerView;
        }

        @NonNull
        @Override
        public final InjectionRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_injection, parent, false);
            return new InjectionRecyclerAdapter.MyViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull InjectionRecyclerAdapter.MyViewHolder holder, final int position) {


            CommonDataObjects.InjectionGetSet item = injectionGetSets.get(position);

            holder.injection_name.setText(item.getInjectionName());
            holder.dosage_value.setText(item.getDosage());
            holder.quantity_count.setText(String.format("Qty: %s", item.getQuantity()));

            holder.card_view.setOnClickListener(v -> {

                //Removing the count of the tablet and total
                ConfirmDeleteInjection(position, recyclerView);

            });
        }

        @Override
        public final int getItemCount() {
            return injectionGetSets.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            final TextView injection_name;
            final TextView dosage_value;
            final TextView quantity_count;

            final LinearLayout card_view;

            MyViewHolder(View itemView) {
                super(itemView);

                injection_name = itemView.findViewById(R.id.injection_name);
                dosage_value = itemView.findViewById(R.id.dosage_value);
                quantity_count = itemView.findViewById(R.id.quantity_value);
                card_view = itemView.findViewById(R.id.injection_layout);

            }
        }

    }

    //region MEDICINE RECYCLER ADAPTER
    public class MedicineRecylerAdapter extends RecyclerView.Adapter<MedicineRecylerAdapter.MyViewHolder> {
        ArrayList<CommonDataObjects.MedicineGetSet> medicineGetSets = new ArrayList<>();

        MedicineRecylerAdapter(ArrayList<CommonDataObjects.MedicineGetSet> medicineGetSets, RecyclerView recyclerView) {
            this.medicineGetSets = medicineGetSets;
        }

        @NonNull
        @Override
        public final MedicineRecylerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow_medicine, parent, false);
            return new MedicineRecylerAdapter.MyViewHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull MedicineRecylerAdapter.MyViewHolder holder, final int position) {

            CommonDataObjects.MedicineGetSet item = medicineGetSets.get(position);

            if (Medicine_Type_Array[0].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(R.drawable.tablet);

            } else if (Medicine_Type_Array[1].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(R.drawable.syrup);

            } else if (Medicine_Type_Array[2].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(R.drawable.ointment);

            } else if (Medicine_Type_Array[3].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(R.drawable.tablet);

            } else if (Medicine_Type_Array[4].equals(item.getMedicine_Type())) {
                holder.medicine_type.setImageResource(R.drawable.injection);

            }

            holder.medicine_name.setText(item.getMedicine_Name());
            holder.morning.setText(item.getMorning_Value());
            holder.afternoon.setText(item.getAfternoon_Value());
            holder.evening.setText(item.getEvening_Value());
            holder.night.setText(item.getNight_Value());
            holder.intake.setText(item.getIntake_Value());
            holder.duration.setText(item.getDays_Value());
            holder.card_view.setOnClickListener(v -> ConfirmDelete(position));
        }

        @Override
        public final int getItemCount() {
            return medicineGetSets.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            final TextView medicine_name;
            final TextView morning;
            final TextView afternoon;
            final TextView evening;
            final TextView night;
            final TextView intake;
            final TextView duration;
            final CardView card_view;
            final ImageView medicine_type;

            MyViewHolder(View itemView) {
                super(itemView);

                medicine_type = itemView.findViewById(R.id.medicine_image);
                medicine_name = itemView.findViewById(R.id.medicine_name);
                morning = itemView.findViewById(R.id.medicine_moring);
                afternoon = itemView.findViewById(R.id.medicine_afternoon);
                evening = itemView.findViewById(R.id.medicine_eve);
                night = itemView.findViewById(R.id.medicine_night);
                intake = itemView.findViewById(R.id.medicine_food);
                duration = itemView.findViewById(R.id.medicine_days);
                card_view = itemView.findViewById(R.id.card_view);

            }
        }

    }
    //endregion


}//END
