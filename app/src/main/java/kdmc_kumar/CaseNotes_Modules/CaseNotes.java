package kdmc_kumar.CaseNotes_Modules;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_Weight;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_Weight;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Inpatient_Module.Inpatient_Detailed_View;
import kdmc_kumar.Inpatient_Module.Inpatient_Inputs;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Helper;
import kdmc_kumar.Utilities_Others.SqliteReader;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;
import kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;

/**
 * Muthukumar N
 * 27-4-2018
 */
public class CaseNotes extends AppCompatActivity {


    @BindView(R.id.appbar_layout)
    AppBarLayout appbar_layout;
    @BindView(R.id.casenotes_parent_layout)
    CoordinatorLayout casenotesParentLayout;
    @BindView(R.id.casenotes_nestedscrollview)
    NestedScrollView casenotesNestedscrollview;
    @BindView(R.id.upperlayout)
    LinearLayout upperlayout;

    @BindView(R.id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;

    @BindView(R.id.tvw_agegender)
    TextView tvwAgegender;

    @BindView(R.id.txtvw_title_patientname)
    TextView txtvwTitlePatientname;

    @BindView(R.id.autocomplete_patientname)
    AutoCompleteTextView autocompletePatientname;

    @BindView(R.id.txtvw_treatmentfor)
    TextView txtvwTreatmentfor;

    @BindView(R.id.multiauto_treatmentfor)
    MultiAutoCompleteTextView multiautoTreatmentfor;

    @BindView(R.id.txtvw_signs)
    TextView txtvwSigns;

    @BindView(R.id.multiauto_signs)
    MultiAutoCompleteTextView multiautoSigns;

    @BindView(R.id.txtvw_diagnosis)
    TextView txtvwDiagnosis;

    @BindView(R.id.multiauto_diagnosis)
    MultiAutoCompleteTextView multiautoDiagnosis;

    @BindView(R.id.upper_parent)
    LinearLayout upperParent;
    @BindView(R.id.autocomplt_weight)
    AutoCompleteTextView autocompltWeight;


    @BindView(R.id.autocomplt_height)
    AutoCompleteTextView autocompltHeight;

    @BindView(R.id.autocomplt_bmi)
    AutoCompleteTextView autocompltBmi;
    @BindView(R.id.autocomplt_temperature)
    AutoCompleteTextView autocompltTemperature;

    @BindView(R.id.autocomplt_bps)
    AutoCompleteTextView autocompltBps;

    @BindView(R.id.autocomplt_bpd)
    AutoCompleteTextView autocompltBpd;

    @BindView(R.id.autocomplt_fbs)
    AutoCompleteTextView autocompltFbs;

    @BindView(R.id.autocomplt_ppbs)
    AutoCompleteTextView autocompltPpbs;

    @BindView(R.id.autocomplt_rbs)
    AutoCompleteTextView autocompltRbs;


    @BindView(R.id.all_casenotes_layouts)
    LinearLayout allCasenotesLayouts;


    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;


    @BindView(R.id.imgvw_bloodsugar_bar)
    AppCompatImageView imgvwBloodsugarBar;

    @BindView(R.id.imgvw_bloodsugar_line)
    AppCompatImageView imgvwBloodsugarLine;

    @BindView(R.id.imgvw_bloodpressure_bar)
    AppCompatImageView imgvwBloodpressureBar;

    @BindView(R.id.imgvw_bloodpressure_line)
    AppCompatImageView imgvwBloodpressureLine;

    @BindView(R.id.imgvw_bloodweight_bar)
    AppCompatImageView imgvwBloodweightBar;

    @BindView(R.id.imgvw_bloodweight_line)
    AppCompatImageView imgvwBloodweightLine;

    @BindView(R.id.button_cancel)
    Button buttonCancel;

    @BindView(R.id.button_report_upload)
    Button buttonReportUpload;

    @BindView(R.id.button_submit)
    Button buttonSubmit;


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    String[] values = new String[]{"Select", "Good", "Fair", "Bad"};
    String[] values1;
    String[] shapeofchest;
    String[] abdomenshape;
    String[] abdomenpalpation;
    String[] reflexes;
    String[] haematuriaarry;
    String Diagnosis_Query = "select distinct diagnosisdata as dvalue from diagonisdetails where (isactive='true' or isactive='1') order by id desc;";
    String Treatment_Query = "select distinct treatmentfor as dvalue from trmntfor where (isactive='true' or isactive='1') order by id desc;";
    String Signs_Query = "select distinct signs as dvalue from Mstr_Signs where (IsActive='true' or IsActive='1') order by id desc;";
    String LoadPatientQuery = "select name,Patid from patreg order by name";
    List<String> list_Treatment;
    List<String> list_Signs;
    List<String> list_Diagnosis;
    String FLAG_MYPATIENT = "N/A";
    String COMMON_PATIENT_ID;
    LOAD_GENERAL_INFORMATION general = null;
    LOAD_CARDIO_VASCULAR cardio = null;
    LOAD_RESPIRATORY respiratory = null;
    LOAD_GASTRO gastro = null;
    LOAD_NEUROLOGY neurology = null;
    LOAD_MOTOR locomotor = null;
    LOAD_RENAL renal = null;
    LOAD_ENDOSYS endosys = null;
    LOAD_CLINICAL clinical = null;
    LOAD_OTHERSYS othersys = null;
    LOAD_DENTAL dental = null;
    LOAD_PNC pnc = null;
    String ReturnValues = "";
    CharSequence[] items_abdomen;
    ArrayList<CharSequence> selectedAbdomen = new ArrayList<CharSequence>();
    ArrayList<CharSequence> selectedEndocrine = new ArrayList<CharSequence>();
    List<String> abdomen_list = new ArrayList<String>();
    List<String> endocrine_list = new ArrayList<String>();

    private static final boolean toggleArrow(View view) {
        if (view.getRotation() == (float) 0) {
            view.animate().setDuration(200L).rotation(180.0F);
            return true;
        } else {
            view.animate().setDuration(200L).rotation((float) 0);
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_casenotes_main_activity);

        try {

            GETINITIALIZE();

            CONTROLLISTENERS();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }//END ON-CREATE

    private void CONTROLLISTENERS() {


        try {
            LOAD_CASENOTES_SYSTEMS_DYNAMICALLY();
        } catch (Exception e) {
            e.printStackTrace();
        }

        casenotesParentLayout.setFocusableInTouchMode(true);

        casenotesParentLayout.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);


        autocompltWeight.setOnClickListener(view -> showPopup_PatientWeight());


        autocompltHeight.setOnClickListener(view -> showPopup_PatientHeight());


        multiautoTreatmentfor.setOnItemClickListener((adapterView, view, i, l) -> {

            if (multiautoTreatmentfor.getText().length() > 0) {
                //Write code to hide and view dentail layout
                if (multiautoTreatmentfor.getText().toString().contains(BaseConfig.TreatmentFor_Dental)) {

                    dental = new LOAD_DENTAL();
                }

            }
        });

        multiautoTreatmentfor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                multiautoTreatmentfor.setAdapter(new ArrayAdapter<>(CaseNotes.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, BaseConfig.convertListtoStringArray(list_Treatment))));


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        multiautoDiagnosis.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String text;
                text = String.valueOf(charSequence);

                multiautoDiagnosis.setAdapter(new ArrayAdapter<>(CaseNotes.this, android.R.layout.simple_dropdown_item_1line, BaseConfig.filterValues(text, BaseConfig.convertListtoStringArray(list_Diagnosis))));


            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        autocompletePatientname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (autocompletePatientname.getText().toString().length() > 0) {

                    String Query = LoadPatientQuery;
                    BaseConfig.SelectedGetPatientDetailsFilter(Query, CaseNotes.this, autocompletePatientname, charSequence.toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

                Drawable rightDrawable = AppCompatResources.getDrawable(autocompletePatientname.getContext(), R.drawable.ic_clear_button_white);
                if (autocompletePatientname.getText().length() > 0) {
                    autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);

                    autocompletePatientname.setOnTouchListener((v, event) -> {
                        final int DRAWABLE_LEFT = 0;
                        final int DRAWABLE_TOP = 1;
                        final int DRAWABLE_RIGHT = 2;
                        final int DRAWABLE_BOTTOM = 3;

                        if (event.getAction() == MotionEvent.ACTION_UP) {
                            if (event.getRawX() >= (autocompletePatientname.getRight() - autocompletePatientname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                                ClearAll();
                                return true;
                            }
                        }
                        return false;
                    });

                } else {
                    autocompletePatientname.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                    autocompletePatientname.setOnTouchListener(null);

                }


            }
        });


        autocompltHeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    if (autocompltHeight.getText().length() > 0) {
                        if (autocompltWeight.getText().length() > 0) {
                            float weight1 = Float.parseFloat(autocompltWeight.getText().toString());


                            float height1 = Float.parseFloat(autocompltHeight.getText().toString());

                            /*if (String.valueOf(height1)!="") {
                             */
                            // calculate the bmi value
                            float bmiValue = calculateBMI(weight1, height1);

                            // interpret the meaning of the bmi value
                            String bmiInterpretation = interpretBMI(bmiValue);

                            if (bmiInterpretation.equalsIgnoreCase("Normal")) {
                                autocompltBmi.setTextColor(Color.GREEN);
                                autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                            } else {
                                autocompltBmi.setTextColor(Color.RED);
                                autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                            }

                            // now set the value in the result text
                            autocompltBmi.setText(String.valueOf(bmiValue));

                        }
                    } else if (autocompltHeight.getText().length() <= 0) {
                        autocompltBmi.setText("0");
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }


        });

        autocompltWeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                try {
                    if (autocompltWeight.getText().length() > 0) {
                        float weight1 = Float.parseFloat(autocompltWeight.getText().toString());


                        float height1 = Float.parseFloat(autocompltHeight.getText().toString());

                        /*if (String.valueOf(height1)!="") {
                         */
                        // calculate the bmi value
                        float bmiValue = calculateBMI(weight1, height1);

                        // interpret the meaning of the bmi value
                        String bmiInterpretation = interpretBMI(bmiValue);

                        if (bmiInterpretation.equalsIgnoreCase("Normal")) {
                            autocompltBmi.setTextColor(Color.GREEN);
                            autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                        } else {
                            autocompltBmi.setTextColor(Color.RED);
                            autocompltBmi.setText(String.format("BMI(%s)", bmiInterpretation));
                        }

                        // now set the value in the result text
                        autocompltBmi.setText(String.valueOf(bmiValue));
                    } else if (autocompltWeight.getText().length() <= 0) {
                        autocompltBmi.setText("0");
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });


        autocompltHeight.setOnFocusChangeListener((v, hasFocus) -> {

            if (autocompltHeight.isFocused()) {
                autocompltHeight.setText(null);
                autocompltHeight.requestFocus();

            }

        });


        autocompltFbs.setOnFocusChangeListener((v, hasFocus) -> {

            if (autocompltFbs.isFocused()) {
                autocompltFbs.setText(null);
                autocompltFbs.requestFocus();

            }
        });


        autocompltPpbs.setOnFocusChangeListener((v, hasFocus) -> {
            // TODO Auto-generated method stub
            if (autocompltPpbs.isFocused()) {
                autocompltPpbs.setText(null);
                autocompltPpbs.requestFocus();

            }
        });


        autocompltRbs.setOnFocusChangeListener((v, hasFocus) -> {

            if (autocompltRbs.isFocused()) {
                autocompltRbs.setText(null);
                autocompltRbs.requestFocus();
            }
        });


        buttonCancel.setOnClickListener(new View.OnClickListener() {
            int count = 0;

            public void onClick(View v) {

                if (count == 1) {
                    count = 0;

                    CaseNotes.this.finish();
                    Intent intent = new Intent(v.getContext(), Dashboard_NavigationMenu.class);
                    startActivity(intent);
                } else {
                    //Toast.makeText(getApplicationContext(), getString(R.string.press_again_cancel), Toast.LENGTH_SHORT).show();
                    BaseConfig.SnackBar(CaseNotes.this, getString(R.string.press_again_cancel), upperParent, 1);
                    count++;
                }
            }
        });


        buttonSubmit.setOnClickListener(view -> SAVE_DATA());


        autocompletePatientname.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {

            try {

                SelectedGetPatientDetails();

            } catch (Exception e) {
                e.printStackTrace();

            }
        });


        imgvwPatientphoto.setOnClickListener(v -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.Show_Patient_PhotoInDetail(autocompletePatientname.getText().toString().split("-")[0], autocompletePatientname.getText().toString().split("-")[1],
                        tvwAgegender.getText().toString(), CaseNotes.this);


            }
        });


        imgvwBloodsugarBar.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Bar_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


        imgvwBloodsugarLine.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Line_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


        imgvwBloodpressureBar.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Bar_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


        imgvwBloodpressureLine.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Line_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


        imgvwBloodweightBar.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "true";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Bar_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


        imgvwBloodweightLine.setOnClickListener(view -> {
            if (autocompletePatientname.getText().length() > 0
                    && tvwAgegender.getText().length() > 0) {

                BaseConfig.temp_flag = "false";

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + autocompletePatientname.getText().toString().split("-")[1].trim() + "' order by id desc");
                if (status == true) {
                    Intent intent = new Intent(CaseNotes.this, Chart_Line_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, autocompletePatientname.getText().toString().split("-")[1].trim());
                    intent.putExtra("PATIENT_NAME", autocompletePatientname.getText().toString().split("-")[0].trim());
                    intent.putExtra("PATIENT_AGEGENDER", tvwAgegender.getText().toString());
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            } else {
                BaseConfig.KDMC_COMMON_DILOAGS(4, CaseNotes.this, CaseNotes.this.getResources().getString(R.string.information), getString(R.string.select_patientname), getString(R.string.ok));
                autocompletePatientname.requestFocus();
            }

        });


    }//Controllisteners End



/*
    private void DENTAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (dental!=null && dental.arrowDentalSystem.getVisibility() == View.VISIBLE) {
            if (dental!=null && dental.toothChildLayout.getVisibility() == View.VISIBLE) {
                //Upper jaw line right/left
                String str_cchkbxUr8 = CheckDentalSystem(dental.cchkbxUr8,1);
                String str_cchkbxUr7 = CheckDentalSystem(dental.cchkbxUr7, 1);
                String str_cchkbxUr6 = CheckDentalSystem(dental.cchkbxUr6, 1);
                String str_cchkbxUr5 = CheckDentalSystem(dental.cchkbxUr5, 1);
                String str_cchkbxUr4 = CheckDentalSystem(dental.cchkbxUr4, 1);

                String str_cchkbxUl1 = CheckDentalSystem(dental.chkbxUl1, 2);
                String str_cchkbxUl2 = CheckDentalSystem(dental.chkbxUl2, 2);
                String str_cchkbxUl3 = CheckDentalSystem(dental.chkbxUl3, 2);
                String str_cchkbxUl4 = CheckDentalSystem(dental.chkbxUl4, 2);
                String str_cchkbxUl5 = CheckDentalSystem(dental.chkbxUl5, 2);


                //Lower Jaw line right/left
                String str_cchkbxLr1 = CheckDentalSystem(dental.cchkbxLr1, 3);
                String str_cchkbxLr2 = CheckDentalSystem(dental.cchkbxLr2, 3);
                String str_cchkbxLr3 = CheckDentalSystem(dental.cchkbxLr3, 3);
                String str_cchkbxLr4 = CheckDentalSystem(dental.cchkbxLr4, 3);
                String str_cchkbxLr5 = CheckDentalSystem(dental.cchkbxLr5, 3);


                String str_cchkbxLl1 = CheckDentalSystem(dental.cchkbxLl1, 4);
                String str_cchkbxLl2 = CheckDentalSystem(dental.cchkbxLl2, 4);
                String str_cchkbxLl3 = CheckDentalSystem(dental.cchkbxLl3, 4);
                String str_cchkbxLl4 = CheckDentalSystem(dental.cchkbxLl4, 4);
                String str_cchkbxLl5 = CheckDentalSystem(dental.cchkbxLl5, 4);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("upper_right_jaw", str_cchkbxUr8 + str_cchkbxUr7 + str_cchkbxUr6 + str_cchkbxUr5 + str_cchkbxUr4);
                values.put("upper_left_jaw", str_cchkbxUl1 + str_cchkbxUl2 + str_cchkbxUl3 + str_cchkbxUl4 + str_cchkbxUl5);
                values.put("lower_right_jaw", str_cchkbxLr1 + str_cchkbxLr2 + str_cchkbxLr3 + str_cchkbxLr4 + str_cchkbxLr5);
                values.put("lower_left_jaw", str_cchkbxLl1 + str_cchkbxLl2 + str_cchkbxLl3 + str_cchkbxLl4 + str_cchkbxLl5);
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys","Child");
                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);
                //Log.e("Insert Dental Child : ", values.toString());
            }

            if (dental.toothAdultLayout.getVisibility() == View.VISIBLE) {

                //Upper jaw line right/left
                String str_chkbxUr8 = CheckDentalSystem(dental.chkbxUr8, 1);
                String str_chkbxUr7 = CheckDentalSystem(dental.chkbxUr7, 1);
                String str_chkbxUr6 = CheckDentalSystem(dental.chkbxUr6, 1);
                String str_chkbxUr5 = CheckDentalSystem(dental.chkbxUr5, 1);
                String str_chkbxUr4 = CheckDentalSystem(dental.chkbxUr4, 1);
                String str_chkbxUr3 = CheckDentalSystem(dental.chkbxUr3, 1);
                String str_chkbxUr2 = CheckDentalSystem(dental.chkbxUr2, 1);
                String str_chkbxUr1 = CheckDentalSystem(dental.chkbxUr1, 1);


                String str_chkbxUl1 = CheckDentalSystem(dental.chkbxUl1, 2);
                String str_chkbxUl2 = CheckDentalSystem(dental.chkbxUl2, 2);
                String str_chkbxUl3 = CheckDentalSystem(dental.chkbxUl3, 2);
                String str_chkbxUl4 = CheckDentalSystem(dental.chkbxUl4, 2);
                String str_chkbxUl5 = CheckDentalSystem(dental.chkbxUl5, 2);
                String str_chkbxUl6 = CheckDentalSystem(dental.chkbxUl6, 2);
                String str_chkbxUl7 = CheckDentalSystem(dental.chkbxUl7, 2);
                String str_chkbxUl8 = CheckDentalSystem(dental.chkbxUl8, 2);

                //Lower jaw line right/left
                String str_chkbxLr1 = CheckDentalSystem(dental.chkbxLr1, 3);
                String str_chkbxLr2 = CheckDentalSystem(dental.chkbxLr2, 3);
                String str_chkbxLr3 = CheckDentalSystem(dental.chkbxLr3, 3);
                String str_chkbxLr4 = CheckDentalSystem(dental.chkbxLr4, 3);
                String str_chkbxLr5 = CheckDentalSystem(dental.chkbxLr5, 3);
                String str_chkbxLr6 = CheckDentalSystem(dental.chkbxLr6, 3);
                String str_chkbxLr7 = CheckDentalSystem(dental.chkbxLr7, 3);
                String str_chkbxLr8 = CheckDentalSystem(dental.chkbxLr8, 3);


                String str_chkbxLl1 = CheckDentalSystem(dental.chkbxLl1, 4);
                String str_chkbxLl2 = CheckDentalSystem(dental.chkbxLl2, 4);
                String str_chkbxLl3 = CheckDentalSystem(dental.chkbxLl3, 4);
                String str_chkbxLl4 = CheckDentalSystem(dental.chkbxLl4, 4);
                String str_chkbxLl5 = CheckDentalSystem(dental.chkbxLl5, 4);
                String str_chkbxLl6 = CheckDentalSystem(dental.chkbxLl6, 4);
                String str_chkbxLl7 = CheckDentalSystem(dental.chkbxLl7, 4);
                String str_chkbxLl8 = CheckDentalSystem(dental.chkbxLl8, 4);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("upper_right_jaw", str_chkbxUr1 + str_chkbxUr2 + str_chkbxUr3 + str_chkbxUr4 + str_chkbxUr5 + str_chkbxUr6 + str_chkbxUr7 + str_chkbxUr8);//-
                values.put("upper_left_jaw", str_chkbxUl1 + str_chkbxUl2 + str_chkbxUl3 + str_chkbxUl4 + str_chkbxUl5 + str_chkbxUl6 + str_chkbxUl7 + str_chkbxUl8);
                values.put("lower_right_jaw", str_chkbxLr1 + str_chkbxLr2 + str_chkbxLr3 + str_chkbxLr4 + str_chkbxLr5 + str_chkbxLr6 + str_chkbxLr7 + str_chkbxLr8);
                values.put("lower_left_jaw", str_chkbxLl1 + str_chkbxLl2 + str_chkbxLl3 + str_chkbxLl4 + str_chkbxLl5 + str_chkbxLl6 + str_chkbxLl7 + str_chkbxLl8);//-
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys","Adult");
                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);

            }
        }
    }
*/

    public void SAVE_DATA() {

        if (autocompletePatientname.getText().length() > 0) {


            String[] Pat = autocompletePatientname.getText().toString().split("-");
            if (Pat.length == 2) {



                boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + Pat[1].trim() + '\''); if (q) {


                    if (buttonSubmit.getText().toString().equalsIgnoreCase(getString(R.string.submit))) {
                        SaveLocal();
                    } else if (buttonSubmit.getText().toString().equalsIgnoreCase(getString(R.string.update))) {

                        try {
                            if (checkValidation()) {
                                // UpdateData(Pat[1]);
                            } else {
                                Toast.makeText(CaseNotes.this,
                                        getString(R.string.check_missing_valid), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print(e);

                        }

                    }

                } else {
                    BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), CaseNotes.this, R.drawable.ic_warning_black_24dp, R.color.red_400);
                }


            }
            else if (Pat.length == 1) {

                boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + Pat[1].trim() + '\'');  if (q) {


                    if (buttonSubmit.getText().toString().equalsIgnoreCase(getString(R.string.submit))) {
                        SaveLocal();
                    } else if (buttonSubmit.getText().toString().equalsIgnoreCase(getString(R.string.update))) {

                        try {
                            if (checkValidation()) {
                                // UpdateData(Pat[1]);
                            } else {
                                Toast.makeText(CaseNotes.this,
                                        getString(R.string.check_missing_valid), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.print(e);

                        }

                    }

                } else {
                    BaseConfig.showSimplePopUp(getString(R.string.not_pat_regist), getString(R.string.info), CaseNotes.this, R.drawable.ic_warning_black_24dp, R.color.red_400);
                }


            }


        } else {
            BaseConfig.showSimplePopUp(getString(R.string.enter_pat_name), getString(R.string.information), CaseNotes.this, R.drawable.ic_warning_black_24dp, R.color.orange_400);
            autocompletePatientname.requestFocus();
        }


    }

    public void SaveLocal() {

        try {
            if (checkValidation()) {

                SUBMITFORM();

            } else {
                Toast.makeText(CaseNotes.this, CaseNotes.this.getResources().getString(R.string.missing), Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.print(e);

        }
    }

    private void SUBMITFORM() {


        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);

            ContentValues values;

            //Treatment master
            String[] tf = multiautoTreatmentfor.getText().toString().trim().split(",");
            String[] dg1 = multiautoDiagnosis.getText().toString().split(",");

            BaseConfig.INSERT_NEW_TREATMENT_FOR(tf, CaseNotes.this);
            BaseConfig.INSERT_NEW_PROVISIONAL_DIAGNOSIS(dg1, CaseNotes.this);

            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);
            String UNIQUE_DIAGNOSIS_ID = BaseConfig.GenerateCaseNotesID();

            BaseConfig.digid = UNIQUE_DIAGNOSIS_ID;

            String[] PatientInfo = autocompletePatientname.getText().toString().split("-");
            String PatientName = PatientInfo[0];
            String PatientID = PatientInfo[1];

            String[] PatientAgeGender = tvwAgegender.getText().toString().split("-");
            String PatientAge = PatientAgeGender[0];
            String PatientGender = PatientAgeGender[1];

            String Str_FBS = "0", Str_PPBS = "0", Str_RBS = "0";
            String Str_Weight = "0", Str_Height = "0", Str_BMI = "0", Str_Temperature = "0", Str_BPS = "0", Str_BPD = "0";
            String Str_Signs="";

            Str_Weight = BaseConfig.CheckCaseNotesString(autocompltWeight.getText().toString());
            Str_Height = BaseConfig.CheckCaseNotesString(autocompltHeight.getText().toString());
            Str_BMI = BaseConfig.CheckCaseNotesString(autocompltBmi.getText().toString());
            Str_Temperature = BaseConfig.CheckCaseNotesString(autocompltTemperature.getText().toString());
            Str_BPS = BaseConfig.CheckCaseNotesString(autocompltBps.getText().toString());
            Str_BPD = BaseConfig.CheckCaseNotesString(autocompltBpd.getText().toString());

            Str_FBS     =  BaseConfig.CheckCaseNotesString(autocompltFbs.getText().toString());
            Str_PPBS    =  BaseConfig.CheckCaseNotesString(autocompltPpbs.getText().toString());
            Str_RBS     =  BaseConfig.CheckCaseNotesString(autocompltRbs.getText().toString());

            Str_Signs = BaseConfig.CheckCaseNotesString(multiautoSigns.getText().toString());

            values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatientID);
            values.put("DiagId", UNIQUE_DIAGNOSIS_ID);
            values.put("pname", PatientName);
            values.put("gender", PatientGender);
            values.put("age", PatientAge);
            values.put("mobnum", BaseConfig.docmobile);
            values.put("refdocname", BaseConfig.doctorname);
            values.put("clinicname", BaseConfig.clinicname);
            values.put("Diagnosis", multiautoDiagnosis.getText().toString());
            values.put("BpS", Str_BPS);
            values.put("BpD", Str_BPD);
            values.put("FBS", Str_FBS);
            values.put("PPS", Str_PPBS);
            values.put("RBS", Str_RBS);
            values.put("PWeight", Str_Weight);
            values.put("temperature", Str_Temperature);
            values.put("Ecgfile", "");
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("treatmentfor", multiautoTreatmentfor.getText().toString());
            values.put("bloodgroup", "");
            values.put("imeino", BaseConfig.Imeinum);
            values.put("docsign", "");
            values.put("Isupdate", 0);
            values.put("height", Str_Height);
            values.put("bmi", Str_BMI);
            values.put("HID", BaseConfig.HID);
            values.put("Signs", Str_Signs);
            db.insert(BaseConfig.TABLE_DIAGONIS, null, values);


            final String Update_Query = "update Diagnosismvalue set mdiagnum=mdiagnum +1";
            BaseConfig.SaveData(Update_Query);


            //1.)//CaseNote_GeneralExamination
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            GENERAL_INFORMATION_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //2.)Insert_Query_CaseNote_Cardiovascular
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            CARDIOVASCULAR_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //3.)Insert_Query_CaseNote_RespiratorySystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            RESPIRATORY_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //4.)Insert_Query_CaseNote_Gastrointestinal
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            GASTRO_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //5.)Insert_Query_CaseNote_Neurology
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            NEURO_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //6.)Insert_Query_CaseNote_Motor
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            LOCOMOTOR_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //7.)Insert_Query_CaseNote_OtherSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            OTHERS_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            //8.)Insert_Query_CaseNote_ClinicalData
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            CLINICAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            //9.)Insert_Query_CaseNote_RenalSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            RENAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //10.)Insert_Query_CaseNote_EndocrineSystem
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            ENDOCRINE_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //11.)Insert_Query_CaseNote_PNC
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            PNC_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);


            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            //12.)Insert_Query_CaseNote_Dental
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            DENTAL_SYSTEM(db, UNIQUE_DIAGNOSIS_ID, PatientName, PatientID, PatientAge, PatientGender);

            ShowSweetAlert(getString(R.string.casenotes_saved), UNIQUE_DIAGNOSIS_ID);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void DENTAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (dental != null && dental.arrowDentalSystem.getVisibility() == View.VISIBLE) {
            if (dental != null && dental.toothChildLayout.getVisibility() == View.VISIBLE) {

                JSONArray jsonArraynew = null;
                jsonArraynew = new JSONArray();

                try {
                    //Upper jaw line right/left
                    String str_cchkbxUr8 = CheckDentalSystem(dental.cchkbxUr8, 1);
                    String str_cchkbxUr7 = CheckDentalSystem(dental.cchkbxUr7, 1);
                    String str_cchkbxUr6 = CheckDentalSystem(dental.cchkbxUr6, 1);
                    String str_cchkbxUr5 = CheckDentalSystem(dental.cchkbxUr5, 1);
                    String str_cchkbxUr4 = CheckDentalSystem(dental.cchkbxUr4, 1);

                    String str_cchkbxUl1 = CheckDentalSystem(dental.cchkbxUl1, 2);
                    String str_cchkbxUl2 = CheckDentalSystem(dental.cchkbxUl2, 2);
                    String str_cchkbxUl3 = CheckDentalSystem(dental.cchkbxUl3, 2);
                    String str_cchkbxUl4 = CheckDentalSystem(dental.cchkbxUl4, 2);
                    String str_cchkbxUl5 = CheckDentalSystem(dental.cchkbxUl5, 2);


                    //Lower Jaw line right/left
                    String str_cchkbxLr1 = CheckDentalSystem(dental.cchkbxLr1, 3);
                    String str_cchkbxLr2 = CheckDentalSystem(dental.cchkbxLr2, 3);
                    String str_cchkbxLr3 = CheckDentalSystem(dental.cchkbxLr3, 3);
                    String str_cchkbxLr4 = CheckDentalSystem(dental.cchkbxLr4, 3);
                    String str_cchkbxLr5 = CheckDentalSystem(dental.cchkbxLr5, 3);


                    String str_cchkbxLl1 = CheckDentalSystem(dental.cchkbxLl1, 4);
                    String str_cchkbxLl2 = CheckDentalSystem(dental.cchkbxLl2, 4);
                    String str_cchkbxLl3 = CheckDentalSystem(dental.cchkbxLl3, 4);
                    String str_cchkbxLl4 = CheckDentalSystem(dental.cchkbxLl4, 4);
                    String str_cchkbxLl5 = CheckDentalSystem(dental.cchkbxLl5, 4);


                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(new JSONObject().put("upper_right_jaw", str_cchkbxUr8 + str_cchkbxUr7 + str_cchkbxUr6 + str_cchkbxUr5 + str_cchkbxUr4));
                    jsonArray.put(new JSONObject().put("upper_left_jaw", str_cchkbxUl1 + str_cchkbxUl2 + str_cchkbxUl3 + str_cchkbxUl4 + str_cchkbxUl5));
                    jsonArray.put(new JSONObject().put("lower_right_jaw", str_cchkbxLr1 + str_cchkbxLr2 + str_cchkbxLr3 + str_cchkbxLr4 + str_cchkbxLr5));
                    jsonArray.put(new JSONObject().put("lower_left_jaw", str_cchkbxLl1 + str_cchkbxLl2 + str_cchkbxLl3 + str_cchkbxLl4 + str_cchkbxLl5));
                    jsonArraynew.put(new JSONObject().put("Child", jsonArray.toString()));

                    String str_chkbxUr8 = CheckDentalSystem(dental.chkbxUr8, 1);
                    String str_chkbxUr7 = CheckDentalSystem(dental.chkbxUr7, 1);
                    String str_chkbxUr6 = CheckDentalSystem(dental.chkbxUr6, 1);
                    String str_chkbxUr5 = CheckDentalSystem(dental.chkbxUr5, 1);
                    String str_chkbxUr4 = CheckDentalSystem(dental.chkbxUr4, 1);
                    String str_chkbxUr3 = CheckDentalSystem(dental.chkbxUr3, 1);
                    String str_chkbxUr2 = CheckDentalSystem(dental.chkbxUr2, 1);
                    String str_chkbxUr1 = CheckDentalSystem(dental.chkbxUr1, 1);


                    String str_chkbxUl1 = CheckDentalSystem(dental.chkbxUl1, 2);
                    String str_chkbxUl2 = CheckDentalSystem(dental.chkbxUl2, 2);
                    String str_chkbxUl3 = CheckDentalSystem(dental.chkbxUl3, 2);
                    String str_chkbxUl4 = CheckDentalSystem(dental.chkbxUl4, 2);
                    String str_chkbxUl5 = CheckDentalSystem(dental.chkbxUl5, 2);
                    String str_chkbxUl6 = CheckDentalSystem(dental.chkbxUl6, 2);
                    String str_chkbxUl7 = CheckDentalSystem(dental.chkbxUl7, 2);
                    String str_chkbxUl8 = CheckDentalSystem(dental.chkbxUl8, 2);

                    //Lower jaw line right/left
                    String str_chkbxLr1 = CheckDentalSystem(dental.chkbxLr1, 3);
                    String str_chkbxLr2 = CheckDentalSystem(dental.chkbxLr2, 3);
                    String str_chkbxLr3 = CheckDentalSystem(dental.chkbxLr3, 3);
                    String str_chkbxLr4 = CheckDentalSystem(dental.chkbxLr4, 3);
                    String str_chkbxLr5 = CheckDentalSystem(dental.chkbxLr5, 3);
                    String str_chkbxLr6 = CheckDentalSystem(dental.chkbxLr6, 3);
                    String str_chkbxLr7 = CheckDentalSystem(dental.chkbxLr7, 3);
                    String str_chkbxLr8 = CheckDentalSystem(dental.chkbxLr8, 3);


                    String str_chkbxLl1 = CheckDentalSystem(dental.chkbxLl1, 4);
                    String str_chkbxLl2 = CheckDentalSystem(dental.chkbxLl2, 4);
                    String str_chkbxLl3 = CheckDentalSystem(dental.chkbxLl3, 4);
                    String str_chkbxLl4 = CheckDentalSystem(dental.chkbxLl4, 4);
                    String str_chkbxLl5 = CheckDentalSystem(dental.chkbxLl5, 4);
                    String str_chkbxLl6 = CheckDentalSystem(dental.chkbxLl6, 4);
                    String str_chkbxLl7 = CheckDentalSystem(dental.chkbxLl7, 4);
                    String str_chkbxLl8 = CheckDentalSystem(dental.chkbxLl8, 4);


                    JSONArray jsonArray1 = new JSONArray();
                    jsonArray1.put(new JSONObject().put("upper_right_jaw", str_chkbxUr1 + str_chkbxUr2 + str_chkbxUr3 + str_chkbxUr4 + str_chkbxUr5 + str_chkbxUr6 + str_chkbxUr7 + str_chkbxUr8));
                    jsonArray1.put(new JSONObject().put("upper_left_jaw", str_chkbxUl1 + str_chkbxUl2 + str_chkbxUl3 + str_chkbxUl4 + str_chkbxUl5 + str_chkbxUl6 + str_chkbxUl7 + str_chkbxUl8));
                    jsonArray1.put(new JSONObject().put("lower_right_jaw", str_chkbxLr1 + str_chkbxLr2 + str_chkbxLr3 + str_chkbxLr4 + str_chkbxLr5 + str_chkbxLr6 + str_chkbxLr7 + str_chkbxLr8));
                    jsonArray1.put(new JSONObject().put("lower_left_jaw", str_chkbxLl1 + str_chkbxLl2 + str_chkbxLl3 + str_chkbxLl4 + str_chkbxLl5 + str_chkbxLl6 + str_chkbxLl7 + str_chkbxLl8));

                    jsonArraynew.put(new JSONObject().put("Adult", jsonArray1.toString()));



                } catch (JSONException e) {
                    e.printStackTrace();
                }

                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("HID", BaseConfig.HID);
                values.put("dental_sys", jsonArraynew.toString());

                db.insert(BaseConfig.TABLE_CASENOTES_DENTALSYSTEM, null, values);
                //Log.e("Insert Dental Child : ", values.toString());
            }


        }
    }

    private void PNC_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (pnc != null && pnc.arrowPnc.getVisibility() == View.VISIBLE) {
            try {
                String PNC_Json = "", PPC_Json = "", COB_Json = "";
                JSONArray jsonary1 = new JSONArray();
                JSONArray jsonary2 = new JSONArray();
                JSONArray jsonary3 = new JSONArray();
                JSONObject obj = new JSONObject();

                //PNC **************************************************************************************

                String str_dateofdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.editTextDateOfDelivery, 1));
                String str_placeofdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextPlaceOfDelivery, 1));
                String str_terms = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextTermPreterm, 1));
                String str_postdelivery = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextPostDelivery, 1));
                String str_complications = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextComplications, 1));
                String str_genderofchild = BaseConfig.GetWidgetOperations(pnc.radGrpSexofbaby, 1);
                String str_weight_baby_kg = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextKg, 1));
                String str_weight_baby_gms = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextGms, 1));
                String str_criedafter_birth = BaseConfig.GetWidgetOperations(pnc.radGrpCried, 1);
                String str_initi_breastfeeding = BaseConfig.GetWidgetOperations(pnc.radGrpFeed, 1);

                obj.put("str_dateofdelivery", str_dateofdelivery);
                obj.put("str_placeofdelivery", str_placeofdelivery);
                obj.put("str_tod_n", BaseConfig.GetWidgetOperations(pnc.chkNormal, 1));
                obj.put("str_tod_instr", BaseConfig.GetWidgetOperations(pnc.chkInst, 1));
                obj.put("str_tod_cs", BaseConfig.GetWidgetOperations(pnc.chkCs, 1));
                obj.put("str_terms", str_terms);
                obj.put("str_postdelivery", str_postdelivery);
                obj.put("str_complications", str_complications);
                obj.put("str_genderofchild", str_genderofchild);
                obj.put("str_weight_baby_kg", str_weight_baby_kg);
                obj.put("str_weight_baby_gms", str_weight_baby_gms);
                obj.put("str_criedafter_birth", str_criedafter_birth);
                obj.put("str_initi_breastfeeding", str_initi_breastfeeding);
                jsonary1.put(obj);

                //PPC **************************************************************************************
                String str_any_comapl = "", str_edt_ppc_pallor = "", str_edt_pulse = "", str_edt_bloodpres = "", str_edt_temperature = "", str_edt_familyplanning = "", str_edt_anyothers = "";

                String str_rg_breast = "", str_rg_nipples = "", str_rg_uterus = "", str_rg_bleednig = "", str_rg_lochia = "", str_rg_epi = "";

                str_any_comapl = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextAnyComplaints, 1));
                str_edt_ppc_pallor = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextPallor, 1));
                str_edt_pulse = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextPulseRate, 1));
                str_edt_bloodpres = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextBloodpressure, 1));
                str_edt_temperature = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextTemperature, 1));
                str_edt_familyplanning = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.editTextFamilyPlanningCounselling, 1));
                str_edt_anyothers = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextAnyOtherComplications, 1));


                str_rg_breast = BaseConfig.GetWidgetOperations(pnc.radGrpBreast, 1);

                str_rg_nipples = BaseConfig.GetWidgetOperations(pnc.radGrpNipple, 1);

                str_rg_uterus = BaseConfig.GetWidgetOperations(pnc.radGrpUterus, 1);

                str_rg_bleednig = BaseConfig.GetWidgetOperations(pnc.radGrpBleed, 1);

                str_rg_lochia = BaseConfig.GetWidgetOperations(pnc.radGrpLochia, 1);

                str_rg_epi = BaseConfig.GetWidgetOperations(pnc.radGrpTear, 1);

                obj = new JSONObject();
                obj.put("str_any_comapl", str_any_comapl);
                obj.put("str_edt_ppc_pallor", str_edt_ppc_pallor);
                obj.put("str_edt_pulse", str_edt_pulse);
                obj.put("str_edt_bloodpres", str_edt_bloodpres);
                obj.put("str_edt_temperature", str_edt_temperature);
                obj.put("str_edt_familyplanning", str_edt_familyplanning);
                obj.put("str_edt_anyothers", str_edt_anyothers);
                obj.put("str_rg_breast", str_rg_breast);
                obj.put("str_rg_nipples", str_rg_nipples);
                obj.put("str_rg_uterus", str_rg_uterus);
                obj.put("str_rg_bleednig", str_rg_bleednig);
                obj.put("str_rg_lochia", str_rg_lochia);
                obj.put("str_rg_epi", str_rg_epi);
                jsonary2.put(obj);


                //COB **************************************************************************************
                String str_edt_urinepassed = "", str_edt_stoolpassed = "", str_edt_diarrhea = "", str_edt_vomiting = "", str_edt_convulions = "", str_edt_cb_temp = "", str_edt_cb_jaundice = "", str_edt_anyothercompli = "";
                String str_rg_activity = "", str_eg_sucking = "", str_rg_breathing = "", str_rg_chest = "", str_rg_skinpus = "";
                String str_umbilical = "";

                str_edt_urinepassed = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edtUrine, 1));
                str_edt_stoolpassed = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edtStool, 1));
                str_edt_diarrhea = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edtDiarrhea, 1));
                str_edt_vomiting = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edtVomit, 1));
                str_edt_convulions = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edtConv, 1));
                str_edt_cb_temp = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextTemper, 1));
                str_edt_cb_jaundice = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextJaundice, 1));
                str_umbilical = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextStump, 1));
                str_edt_anyothercompli = BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(pnc.edittextAnyOtherComplications, 1));


                str_rg_activity = BaseConfig.GetWidgetOperations(pnc.radGrpActivity, 1);//getRadioChecked(rg_activity);

                str_eg_sucking = BaseConfig.GetWidgetOperations(pnc.radGrpSucking, 1);//getRadioChecked(eg_sucking);

                str_rg_breathing = BaseConfig.GetWidgetOperations(pnc.radGrpBreathing, 1);//getRadioChecked(rg_breathing);

                str_rg_chest = BaseConfig.GetWidgetOperations(pnc.radGrpChest, 1);//getRadioChecked(rg_chest);

                str_rg_skinpus = BaseConfig.GetWidgetOperations(pnc.radGrpSkin, 1);//getRadioChecked(rg_skinpus);

                obj = new JSONObject();
                obj.put("str_edt_urinepassed", str_edt_urinepassed);
                obj.put("str_edt_stoolpassed", str_edt_stoolpassed);
                obj.put("str_edt_diarrhea", str_edt_diarrhea);
                obj.put("str_edt_vomiting", str_edt_vomiting);
                obj.put("str_edt_convulions", str_edt_convulions);
                obj.put("str_edt_cb_temp", str_edt_cb_temp);
                obj.put("str_edt_cb_jaundice", str_edt_cb_jaundice);
                obj.put("str_umbilical", str_umbilical);
                obj.put("str_edt_anyothercompli", str_edt_anyothercompli);
                obj.put("str_rg_activity", str_rg_activity);
                obj.put("str_eg_sucking", str_eg_sucking);
                obj.put("str_rg_breathing", str_rg_breathing);
                obj.put("str_rg_chest", str_rg_chest);
                obj.put("str_rg_skinpus", str_rg_skinpus);
                jsonary3.put(obj);


                values = new ContentValues();
                values.put("DiagId", dignosisid);
                values.put("ptid", patientID);
                values.put("pname", patientName);
                values.put("docid", BaseConfig.doctorId);
                values.put("Actdate", BaseConfig.DeviceDate());
                values.put("IsActive", 1);
                values.put("pagegen", patientAge + patientGender);
                values.put("Isupdate", 0);
                values.put("pnc_details", jsonary1.toString());
                values.put("ppc_details", jsonary2.toString());
                values.put("cob_details", jsonary3.toString());
                values.put("HID", BaseConfig.HID);
                db.insert(BaseConfig.TABLE_CASENOTES_PNCSYSTEM, null, values);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void ENDOCRINE_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (endosys != null && endosys.arrowEndocrineSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Endocrine", BaseConfig.GetWidgetOperations(endosys.edtEndocrine, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_ENDOCRINE, null, values);


        }
    }

    private void RENAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (renal != null && renal.arrowRenalSystem.getVisibility() == View.VISIBLE) {

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("dysuria", BaseConfig.GetWidgetOperations(renal.switchDysuria1, 1));
            values.put("pyuria", BaseConfig.GetWidgetOperations(renal.switchPyuria1, 1));
            values.put("haematuria", BaseConfig.GetWidgetOperations(renal.spinnerHaematuria, 1));
            values.put("oliguria", BaseConfig.GetWidgetOperations(renal.switchOliguria1, 1));
            values.put("polyuria", BaseConfig.GetWidgetOperations(renal.switchPolyuria1, 1));
            values.put("anuria", BaseConfig.GetWidgetOperations(renal.switchAnuria1, 1));
            values.put("nocturia", BaseConfig.GetWidgetOperations(renal.switchNocturia1, 1));
            values.put("urethraldischarge", BaseConfig.GetWidgetOperations(renal.switchUrethraldischarge1, 1));
            values.put("prostate", BaseConfig.GetWidgetOperations(renal.switchProstate, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_RENAL, null, values);


        }
    }

    private void CLINICAL_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (clinical != null && clinical.arrowClinicalData.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Heamoglobin", BaseConfig.GetWidgetOperations(clinical.editTextHaemoglobinGDl, 1));
            values.put("wbc", BaseConfig.GetWidgetOperations(clinical.editTextWBC, 1));
            values.put("rbc", BaseConfig.GetWidgetOperations(clinical.editTextRbc, 1));
            values.put("esr", BaseConfig.GetWidgetOperations(clinical.editTextEsr, 1));
            values.put("polymorphs", BaseConfig.GetWidgetOperations(clinical.editTextPolymorphs, 1));
            values.put("lymphocytes", BaseConfig.GetWidgetOperations(clinical.editTextLymphocytes, 1));
            values.put("monocytes", BaseConfig.GetWidgetOperations(clinical.editTextMonocytes, 1));
            values.put("basophils", BaseConfig.GetWidgetOperations(clinical.editTextBasophils, 1));
            values.put("eosinophils", BaseConfig.GetWidgetOperations(clinical.editTextEosinophils, 1));
            values.put("urea", BaseConfig.GetWidgetOperations(clinical.editTextUrea, 1));
            values.put("creatinine", BaseConfig.GetWidgetOperations(clinical.editTextCreatinine, 1));
            values.put("UricAcid", BaseConfig.GetWidgetOperations(clinical.editTextUricAcid, 1));
            values.put("Sodium", BaseConfig.GetWidgetOperations(clinical.editTextSodium, 1));
            values.put("Potassium", BaseConfig.GetWidgetOperations(clinical.editTextPotassium, 1));
            values.put("Chloride", BaseConfig.GetWidgetOperations(clinical.editTextChloride, 1));
            values.put("Bicarbonate", BaseConfig.GetWidgetOperations(clinical.editTextBicarbonate, 1));
            values.put("TotalCholesterol", BaseConfig.GetWidgetOperations(clinical.editTextTotalCholesterol, 1));
            values.put("LDL", BaseConfig.GetWidgetOperations(clinical.editTextLdl, 1));
            values.put("HDL", BaseConfig.GetWidgetOperations(clinical.editTextHdl, 1));
            values.put("VLDL", BaseConfig.GetWidgetOperations(clinical.editTextVldl, 1));
            values.put("Triglycerides", BaseConfig.GetWidgetOperations(clinical.editTextTriglycerides, 1));
            values.put("Serumbilirubin", BaseConfig.GetWidgetOperations(clinical.editTextSerumBilirubinTotal, 1));
            values.put("Direct", BaseConfig.GetWidgetOperations(clinical.editTextDirect, 1));
            values.put("Indirect", BaseConfig.GetWidgetOperations(clinical.editTextIndirect, 1));
            values.put("Totalprotein", BaseConfig.GetWidgetOperations(clinical.editTextTotalProtein, 1));
            values.put("Albumin", BaseConfig.GetWidgetOperations(clinical.editTextAlbumin, 1));
            values.put("Globulin", BaseConfig.GetWidgetOperations(clinical.editTextGlobulin, 1));
            values.put("SGOT", BaseConfig.GetWidgetOperations(clinical.editTextSgot, 1));
            values.put("SGPT", BaseConfig.GetWidgetOperations(clinical.editTextSgpt, 1));
            values.put("AlkalinePhosphatase", BaseConfig.GetWidgetOperations(clinical.editTextAlkalinePhosphatase, 1));
            values.put("FreeT3", BaseConfig.GetWidgetOperations(clinical.editTextFreeT3, 1));
            values.put("FreeT4", BaseConfig.GetWidgetOperations(clinical.editTextFreeT4, 1));
            values.put("TSH", BaseConfig.GetWidgetOperations(clinical.editTextTsh, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CLINICALDATA, null, values);

        }
    }

    private void OTHERS_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (othersys != null && othersys.arrowOtherSystems.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Othersystem", BaseConfig.GetWidgetOperations(othersys.edtOtherSystems, 1));
            values.put("AdditionalInfo", BaseConfig.GetWidgetOperations(othersys.edtAdditionalInformation, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_OTHERSSYSTEMS, null, values);


        }
    }

    private void LOCOMOTOR_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (locomotor != null && locomotor.arrowLocomotorSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();

            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("symmetry", BaseConfig.GetWidgetOperations(locomotor.switchSymmetry1, 1));
            values.put("smooth_movement", BaseConfig.GetWidgetOperations(locomotor.switchSmoothMovement1, 1));
            values.put("arms_swing", BaseConfig.GetWidgetOperations(locomotor.switchArmsSwing1, 1));
            values.put("pelvic_tilt", BaseConfig.GetWidgetOperations(locomotor.switchPelvicTilt1, 1));
            values.put("stride_length", BaseConfig.GetWidgetOperations(locomotor.strideLength1, 1));
            values.put("cervical_lordosis", BaseConfig.GetWidgetOperations(locomotor.switchCervicalLordosis1, 1));
            values.put("lumbar_lordosis", BaseConfig.GetWidgetOperations(locomotor.switchLumbarLordosis1, 1));
            values.put("kyphosis", BaseConfig.GetWidgetOperations(locomotor.switchKyphosis1, 1));
            values.put("scoliosis", BaseConfig.GetWidgetOperations(locomotor.switchScoliosis1, 1));
            values.put("llswelling", BaseConfig.GetWidgetOperations(locomotor.switchLlSwelling1, 1));
            values.put("lldeformity", BaseConfig.GetWidgetOperations(locomotor.switchLlDeformity1, 1));
            values.put("lllimbshortening", BaseConfig.GetWidgetOperations(locomotor.switchLlLimbShortening1, 1));
            values.put("llmuscle_wasting", BaseConfig.GetWidgetOperations(locomotor.switchLlMuscleWasting1, 1));
            values.put("llremarks", BaseConfig.GetWidgetOperations(locomotor.editTextRemarks, 1));
            values.put("ulswelling", BaseConfig.GetWidgetOperations(locomotor.switchUpperLimbSwelling, 1));
            values.put("uldeformity", BaseConfig.GetWidgetOperations(locomotor.switchUpperLimbDeformity, 1));
            values.put("ullimbshortening", BaseConfig.GetWidgetOperations(locomotor.switchUpperLimbShortening, 1));
            values.put("ulmuscle_wasting", BaseConfig.GetWidgetOperations(locomotor.switchUpperLimbWasting1, 1));
            values.put("ulremarks", BaseConfig.GetWidgetOperations(locomotor.editTextRemarks1, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_LOCOMOTOR, null, values);

        }
    }

    private void NEURO_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (neurology != null && neurology.arrowNeurologySystem.getVisibility() == View.VISIBLE) {
            //***Cognitive Function
            String sensoryspnner = "", cornealytxt = "", bicepsntxt = "", tricepsntxt = "", supinatorytxt = "", kneeytxt = "", ankleytxt = "", plantarntxt = "";

//hi ram install apk to rucha
            //Insert CaseNote_Neurology
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Pupilsize", BaseConfig.GetWidgetOperations(neurology.radiogroupPupilsize, 1));
            values.put("Speech", BaseConfig.GetWidgetOperations(neurology.radiogroupSpeech, 1));
            values.put("Carodit", BaseConfig.GetWidgetOperations(neurology.radiogroupCarotid, 1));
            values.put("Amnesia", BaseConfig.GetWidgetOperations(neurology.switchAmnesiay, 1));
            values.put("Apraxia", BaseConfig.GetWidgetOperations(neurology.switchApraxiay, 1));
            values.put("Cognitive_Function", BaseConfig.GetWidgetOperations(neurology.radiogroupCognitiveFunction, 1));
            values.put("Bulk", BaseConfig.GetWidgetOperations(neurology.radiogroupBulk, 1));
            values.put("Tone", BaseConfig.GetWidgetOperations(neurology.radiogroupTones, 1));
            values.put("Power_LUL", BaseConfig.GetWidgetOperations(neurology.seekBarLeftUpperLimb, 1));
            values.put("Power_RUL", BaseConfig.GetWidgetOperations(neurology.seekBarRightUpperLimb, 1));
            values.put("Power_LLL", BaseConfig.GetWidgetOperations(neurology.seekBarLeftLowerLimb, 1));
            values.put("Power_RLL", BaseConfig.GetWidgetOperations(neurology.seekBarRightLowerLimb, 1));
            values.put("Sensory", BaseConfig.GetWidgetOperations(neurology.spinnerSensory, 1));
            values.put("Reflexes_Corneal", BaseConfig.GetWidgetOperations(neurology.spinnerCorneal, 1));
            values.put("Reflexes_Biceps", BaseConfig.GetWidgetOperations(neurology.spinnerBiceps, 1));
            values.put("Reflexes_Triceps", BaseConfig.GetWidgetOperations(neurology.spinnerTriceps, 1));
            values.put("Reflexes_Supinator", BaseConfig.GetWidgetOperations(neurology.spinnerSupinator, 1));
            values.put("Reflexes_Knee", BaseConfig.GetWidgetOperations(neurology.spinnerKnee, 1));
            values.put("Reflexes_Ankle", BaseConfig.GetWidgetOperations(neurology.spinnerAnkle, 1));
            values.put("Reflexes_Plantor", BaseConfig.GetWidgetOperations(neurology.spinnerPlantar, 1));
            values.put("congentail_abnormality", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewCongentialAbnormality, 1));
            values.put("mentalstatus", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewMentalStatus, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_NEUROLOGY, null, values);

            //Insert CaseNote_CNS
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("oriented", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewOriented, 1));
            values.put("neckrigidity", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewNeckRigidity, 1));
            values.put("confused", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewConfused, 1));
            values.put("exaggerated", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewExaggerated, 1));
            values.put("extensor", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewExtenstor, 1));
            values.put("gsscore", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewGsScore, 1));
            values.put("incomprehensible", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewIncomprehensible, 1));
            values.put("depressed", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewDepressed, 1));
            values.put("flexor", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewFlexor, 1));
            values.put("coma", BaseConfig.GetWidgetOperations(neurology.autoCompleteTextViewComa, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CNS, null, values);


        }
    }

    //All systems end

    private void GASTRO_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (gastro != null && gastro.arrowGastrointestinalSystem.getVisibility() == View.VISIBLE) {

            String Gastrointestinalcnt = concatpersonalhistrycnt(abdomen_list).toString();
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("shapeofabdomen", BaseConfig.GetWidgetOperations(gastro.spinnerShapeOfTheAbdomen, 1));
            values.put("Abdomen", Gastrointestinalcnt);
            values.put("Visible_Pulsations", BaseConfig.GetWidgetOperations(gastro.switchVisiblePulsation1, 1));
            values.put("Visual_Peristalsis", BaseConfig.GetWidgetOperations(gastro.switchVisiblePerist1, 1));
            values.put("Abdominal_Palpation", BaseConfig.GetWidgetOperations(gastro.spinnerAbdominalPalpation, 1));
            values.put("Splenomegaly", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewSplenomegaly, 1));
            values.put("Hepatomegaly", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewHepatomegaly, 1));
            values.put("PalpableMasses", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewPalpableMasses, 1));
            values.put("Bowelsound", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewBowelSounds, 1));
            values.put("Hernial", BaseConfig.GetWidgetOperations(gastro.checkBoxOrifices, 1) + BaseConfig.GetWidgetOperations(gastro.checkBoxFree, 1));
            values.put("Spleen", BaseConfig.GetWidgetOperations(gastro.switchSpleen, 1));
            values.put("Liver", BaseConfig.GetWidgetOperations(gastro.switchLiverNormal, 1));
            values.put("organomegely", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewOrganomegely, 1));
            values.put("freefuild", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewFreeFluidInTheAbdomen, 1));
            values.put("distension", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewDistension, 1));
            values.put("tenderness", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewTenderness, 1));
            values.put("scrotum	", BaseConfig.GetWidgetOperations(gastro.autoCompleteTextViewScrotum, 1));
            values.put("HID", BaseConfig.HID);

            db.insert(BaseConfig.TABLE_CASENOTES_GASTROINTESTINAL, null, values);

        }
    }

    private void RESPIRATORY_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (respiratory != null && respiratory.arrowRespiratorySystem.getVisibility() == View.VISIBLE) {

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("BreathSoundEqual", BaseConfig.GetWidgetOperations(respiratory.radiogroupBreathsoundequal, 1));
            values.put("BreathSoundEqualOptions", BaseConfig.GetWidgetOperations(respiratory.radiogroupBreathsoundequalOptns, 1));
            values.put("Breathsound", BaseConfig.GetWidgetOperations(respiratory.spinnerBreathSound, 1));
            values.put("Trachea", BaseConfig.GetWidgetOperations(respiratory.spinner_Trachea, 1));
            values.put("Kyphosis_Scoliosis", BaseConfig.GetWidgetOperations(respiratory.checkBoxKyphosis, 1) +"\n"+ BaseConfig.GetWidgetOperations(respiratory.checkBoxScoliosis, 1));
            values.put("Crepitation", BaseConfig.GetWidgetOperations(respiratory.checkBoxLeft, 1) +"\n"+ BaseConfig.GetWidgetOperations(respiratory.checkBoxRight, 1));
            values.put("Bronchi", BaseConfig.GetWidgetOperations(respiratory.checkBoxRhonchiLeft, 1) +"\n"+ BaseConfig.GetWidgetOperations(respiratory.checkBoxRhonchiRight, 1));
            values.put("Pulserate", "");
            values.put("Note", BaseConfig.GetWidgetOperations(respiratory.editTextRespiratoryRate, 1));
            values.put("shapeofchest", BaseConfig.GetWidgetOperations(respiratory.spinnerShapeOfTheChest, 1));
            values.put("spo2", BaseConfig.GetWidgetOperations(respiratory.autoCompleteTextViewSpo2, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_RESPIRATORYSYSTEMS, null, values);

        }
    }

    private void CARDIOVASCULAR_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (cardio != null && cardio.arrowCardioVascularSystem.getVisibility() == View.VISIBLE) {
            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Beat", BaseConfig.GetWidgetOperations(cardio.checkBoxS1, 1) +"\n"+ BaseConfig.GetWidgetOperations(cardio.checkBoxS2, 1) +"\n"+ BaseConfig.GetWidgetOperations(cardio.checkBoxS3, 1) +"\n"+ BaseConfig.GetWidgetOperations(cardio.checkBoxS4, 1));
            values.put("Murmur", BaseConfig.GetWidgetOperations(cardio.checkBoxSystolic, 1) +"\n"+ BaseConfig.GetWidgetOperations(cardio.checkBoxDiastolic, 1) +"\n"+ BaseConfig.GetWidgetOperations(cardio.checkBoxOthers, 1));
            values.put("Pericardial_Rub", BaseConfig.GetWidgetOperations(cardio.switchPericardialrub, 1));
            values.put("Pulserate", BaseConfig.GetWidgetOperations(cardio.switchPulserate, 1));
            values.put("heartrate", BaseConfig.GetWidgetOperations(cardio.editTextHeartrate, 1));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_CARDIOVASCULAR, null, values);

        }
    }

    public void GENERAL_INFORMATION_SYSTEM(SQLiteDatabase db, String dignosisid, String patientName, String patientID, String patientAge, String patientGender) {
        ContentValues values;
        if (general != null && general.arrowGeneralExamination.getVisibility() == View.VISIBLE) {
            //***Clubbing,Pedal_edema
            String tempstring = BaseConfig.GetWidgetOperations(general.checkBoxPallorness, 1) +
                    BaseConfig.GetWidgetOperations(general.checkBoxThyroid, 1) +
                    BaseConfig.GetWidgetOperations(general.checkBoxFacePuffiness, 1) +
                    BaseConfig.GetWidgetOperations(general.checkBoxJaundice, 1);

            values = new ContentValues();
            values.put("DiagId", dignosisid);
            values.put("ptid", patientID);
            values.put("pname", patientName);
            values.put("docid", BaseConfig.doctorId);
            values.put("Actdate", BaseConfig.DeviceDate());
            values.put("IsActive", 1);
            values.put("pagegen", patientAge + patientGender);
            values.put("Isupdate", 0);
            values.put("Anaemic", BaseConfig.GetWidgetOperations(general.switchAnaemic, 1));
            values.put("Icterus", BaseConfig.GetWidgetOperations(general.switchIcterus, 1));
            values.put("Stenosis", BaseConfig.GetWidgetOperations(general.switchCyanosis, 1));
            values.put("Clubbing", BaseConfig.GetWidgetOperations(general.switchClubbing, 1));
            values.put("Limbphantom", BaseConfig.GetWidgetOperations(general.switchLymphadenopathy, 1));
            values.put("Vericoveins", BaseConfig.GetWidgetOperations(general.switchVaricoseVeins, 1));
            values.put("Pedal_edema", BaseConfig.GetWidgetOperations(general.switchPedalEdema, 1));
            values.put("skin_infection", BaseConfig.GetWidgetOperations(general.switchSkininfection, 1));
            values.put("short_stat", BaseConfig.GetWidgetOperations(general.switchShortStatured, 1));
            values.put("built", BaseConfig.GetWidgetOperations(general.radiogroupBuilt, 1));
            values.put("extra_generalexam", tempstring);
            values.put("pog", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtPog, 1)));
            values.put("pallor", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtPallor, 1)));
            values.put("oedema", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtOedema, 1)));
            values.put("fundalheight", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtHeight, 1)));
            values.put("lie", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtLiePresentation, 1)));
            values.put("fetalmovement", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtFetalmovements, 1)));
            values.put("fetalheight", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtFetalheartrate, 1)));
            values.put("pv", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtPv, 1)));
            values.put("anycompl", BaseConfig.CheckDBString(BaseConfig.GetWidgetOperations(general.edtAnycomplaints, 1)));
            values.put("HID", BaseConfig.HID);
            db.insert(BaseConfig.TABLE_CASENOTES_GENERALEXAMINATION, null, values);

        }
    }

    public String CheckDentalSystem(CheckBox chkbx, int position) {
        String rt = "";

        if (position == 1)//Upper right jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText().toString() + ",";
            } else {
                rt = "";
            }

        } else if (position == 2)//Upper left jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText().toString() + ",";
            } else {
                rt = "";
            }

        } else if (position == 3)//Lower right jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText().toString() + ",";
            } else {
                rt = "";
            }

        } else if (position == 4)//Lower left jaw line
        {
            if (chkbx.isChecked()) {
                rt = chkbx.getText().toString() + ",";
            } else {
                rt = "";
            }
        }

        return rt;
    }

    @SuppressWarnings("StringConcatenationInLoop")
    private StringBuilder concatpersonalhistrycnt(List<String> personalarylist2) {

        StringBuilder concatpersonalhistrystr = new StringBuilder();

        for (int j = 0; j < personalarylist2.size(); j++) {
            personalarylist2.get(j);

            //noinspection StringConcatenationInLoop
            concatpersonalhistrystr.append(personalarylist2.get(j) + "\n");

        }
        return concatpersonalhistrystr;
    }

    public void LOAD_CASENOTES_SYSTEMS_DYNAMICALLY() throws Exception {

        String Query = "select * from drsettings";
        new SqliteReader().setQuery(Query).onDataReceiver(c -> ReturnValues = c.getString(c.getColumnIndex("CaseNotes_Systems")));

        Log.e("Settigns_Systems: ", ReturnValues);

        if (ReturnValues.length() > 0) {

            String[] arr = ReturnValues.substring(0, ReturnValues.length() - 1).split(",");

            for (String s : arr) {

                if (contains(arr, s)) {
                    switch (s) {
                        case "1":

                            general = new LOAD_GENERAL_INFORMATION();

                            break;

                        case "2":
                            cardio = new LOAD_CARDIO_VASCULAR();

                            break;

                        case "3":
                            respiratory = new LOAD_RESPIRATORY();

                            break;

                        case "4":
                            gastro = new LOAD_GASTRO();

                            break;

                        case "5":
                            neurology = new LOAD_NEUROLOGY();

                            break;

                        case "6":
                            locomotor = new LOAD_MOTOR();

                            break;

                        case "7":
                            renal = new LOAD_RENAL();

                            break;

                        case "8":
                            endosys = new LOAD_ENDOSYS();

                            break;

                        case "9":
                            clinical = new LOAD_CLINICAL();

                            break;

                        case "10":
                            othersys = new LOAD_OTHERSYS();
                            break;

                        case "11"://Dental
                            dental = new LOAD_DENTAL();
                            break;

                        case "12"://PNC
                            pnc = new LOAD_PNC();
                            break;

                    }
                }
            }

        }

    }

    public boolean contains(String[] arr, String item) {

        for (String n : arr) {
            if (item.equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = toggleArrow(view);
        if (show) {

            ViewAnimation.expand(PrimaryLayout);
        } else {
            ViewAnimation.collapse(PrimaryLayout);
        }
    }

    private void GETINITIALIZE() {

        ButterKnife.bind(CaseNotes.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.lbl_Casenotes)));


        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbarTitle, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        //   toolbar.setBackgroundColor(Color.parseColor(ClinicalInformation.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));

        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(CaseNotes.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());

        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {
            values1 = new String[]{"निवडा", "Normal", "Hypoesthesia", "Hyperesthesia"};
            shapeofchest = new String[]{"निवडा", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            abdomenshape = new String[]{"निवडा", "Normal", "Scaphoid", "Sunken", "Distended"};
            abdomenpalpation = new String[]{"निवडा", "Soft", "Guarding", "Rigidity"};
            reflexes = new String[]{"निवडा", "Normal", "Reduced", "Increased", "Absent"};
            haematuriaarry = new String[]{"निवडा", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

        } else {
            values1 = new String[]{"Select", "Normal", "Hypoesthesia", "Hyperesthesia"};
            shapeofchest = new String[]{"Select", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            abdomenshape = new String[]{"Select", "Normal", "Scaphoid", "Sunken", "Distended"};
            abdomenpalpation = new String[]{"Select", "Soft", "Guarding", "Rigidity"};
            reflexes = new String[]{"Select", "Normal", "Reduced", "Increased", "Absent"};
            haematuriaarry = new String[]{"Select", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

        }


        String first = getString(R.string.patient_name);
        String next = "<font color='#EE0000'><b>*</b></font>";
        txtvwTitlePatientname.setText(Html.fromHtml(first + next));

        String first1 = getString(R.string.symptoms);
        String next1 = "<font color='#EE0000'><b>*</b></font>";
        txtvwTreatmentfor.setText(Html.fromHtml(first1 + next1));

        String first2 = getString(R.string.provisional_diagnosis);
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        txtvwDiagnosis.setText(Html.fromHtml(first2 + next2));


        autocompletePatientname.setThreshold(1);

        multiautoDiagnosis.setText("");
        multiautoDiagnosis.setThreshold(1);
        multiautoDiagnosis.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        multiautoSigns.setText("");
        multiautoSigns.setThreshold(1);
        multiautoSigns.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        multiautoTreatmentfor.setText("");
        multiautoTreatmentfor.setThreshold(1);
        multiautoTreatmentfor.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        Bundle b = getIntent().getExtras();

        if (b != null) {
            String STATUS = b.getString("CONTINUE_STATUS");
            COMMON_PATIENT_ID = b.getString("PASSING_PATIENT_ID");

            FLAG_MYPATIENT = b.getString("FROM");

            assert STATUS != null;
            if (STATUS.equalsIgnoreCase("True")) {
                Load_Patient_Details(COMMON_PATIENT_ID);
            }
        } else {
            FLAG_MYPATIENT = "";
        }


        list_Diagnosis = BaseConfig.LoadMultiAutoValues(multiautoDiagnosis, CaseNotes.this, Diagnosis_Query);
        BaseConfig.loadSpinner(multiautoDiagnosis, list_Treatment);

        list_Treatment = BaseConfig.LoadMultiAutoValues(multiautoTreatmentfor, CaseNotes.this, Treatment_Query);
        BaseConfig.loadSpinner(multiautoTreatmentfor, list_Diagnosis);

        list_Signs = BaseConfig.LoadMultiAutoValues(multiautoSigns, CaseNotes.this, Signs_Query);
        BaseConfig.loadSpinner(multiautoSigns, list_Signs);


    }

    private void showPopup_PatientWeight() {

        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customView = layoutInflater.inflate(R.layout.kdmc_layout_weight, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setCancelable(false);
        alertDialogBuilder.setView(customView);


        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

        final kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar seekbar_weight = customView.findViewById(R.id.seek_bar);

        TextView setWeight = customView.findViewById(R.id.txtvw_weight);
        TextView setWeightHint = customView.findViewById(R.id.txtvw_weight_hint);
        LinearLayout panel = customView.findViewById(R.id.panelcolor);

        seekbar_weight.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                setWeight.setText(String.valueOf(value));


                if (value <= 53) {
                    setWeight.setTextColor(getResources().getColor(R.color.blue_300));
                    setWeightHint.setText("Underweight");
                    setWeightHint.setTextColor(getResources().getColor(R.color.blue_300));
                    seekbar_weight.setTrackColor(getResources().getColor(R.color.blue_300));
                    panel.setBackgroundColor(getResources().getColor(R.color.blue_300));
                } else if (value >= 53 && value <= 72) {
                    setWeight.setTextColor(getResources().getColor(R.color.green_400));
                    setWeightHint.setText("Normal");
                    setWeightHint.setTextColor(getResources().getColor(R.color.green_400));
                    seekbar_weight.setTrackColor(getResources().getColor(R.color.green_400));
                    panel.setBackgroundColor(getResources().getColor(R.color.green_400));
                } else if (value >= 72 && value <= 80) {
                    setWeight.setTextColor(getResources().getColor(R.color.purple_600));
                    setWeightHint.setText("Overweight");
                    setWeightHint.setTextColor(getResources().getColor(R.color.purple_600));
                    seekbar_weight.setTrackColor(getResources().getColor(R.color.purple_600));
                    panel.setBackgroundColor(getResources().getColor(R.color.purple_600));
                } else if (value >= 80 && value <= 92) {
                    setWeight.setTextColor(getResources().getColor(R.color.orange_500));
                    setWeightHint.setText("Obese I");
                    setWeightHint.setTextColor(getResources().getColor(R.color.orange_500));
                    seekbar_weight.setTrackColor(getResources().getColor(R.color.orange_500));
                    panel.setBackgroundColor(getResources().getColor(R.color.orange_500));
                } else if (value >= 92) {
                    setWeight.setTextColor(getResources().getColor(R.color.red_500));
                    setWeightHint.setText("Obese II");
                    setWeightHint.setTextColor(getResources().getColor(R.color.red_500));
                    seekbar_weight.setTrackColor(getResources().getColor(R.color.red_500));
                    panel.setBackgroundColor(getResources().getColor(R.color.red_500));
                }


            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });


        AppCompatButton button_ok = customView.findViewById(R.id.bt_close_yes);
        AppCompatButton button_cancel = customView.findViewById(R.id.bt_close_no);

        button_ok.setOnClickListener(v -> {

            int seekValue = seekbar_weight.getProgress();

            // Toast.makeText(v.getContext(), String.valueOf(seekValue), Toast.LENGTH_SHORT).show();

            autocompltWeight.setText(String.valueOf(seekValue));

            alertDialog.dismiss();


        });

        button_cancel.setOnClickListener(view -> alertDialog.dismiss());

    }

    private void showPopup_PatientHeight() {
        // Initialize a new instance of LayoutInflater service
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View customView = layoutInflater.inflate(R.layout.kdmc_layout_height, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this).setCancelable(false);
        alertDialogBuilder.setView(customView);


        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();


        alertDialog.show();

        final kdmc_kumar.Utilities_Others.seek.DiscreteSeekBar seekbar_height = customView.findViewById(R.id.seek_bar);

        TextView setHeight = customView.findViewById(R.id.txtvw_height);

        AppCompatButton button_ok = customView.findViewById(R.id.bt_close_yes);
        AppCompatButton button_cancel = customView.findViewById(R.id.bt_close_no);

        seekbar_height.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {

                setHeight.setText(String.valueOf(value));

            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {

            }
        });

        button_ok.setOnClickListener(v -> {

            int seekValue = seekbar_height.getProgress();
            // Toast.makeText(v.getContext(), String.valueOf(seekValue), Toast.LENGTH_SHORT).show();
            autocompltHeight.setText(String.valueOf(seekValue));
            alertDialog.dismiss();


        });

        button_cancel.setOnClickListener(v -> {

            alertDialog.dismiss();


        });

    }

    private void LoadBack() {

        if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("MYPATIENT")) {

            BaseConfig.HideKeyboard(CaseNotes.this);
            Bundle b = new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, MyPatientDrawer.class, b);


        } else if (FLAG_MYPATIENT != null && FLAG_MYPATIENT.equalsIgnoreCase("INPATIENT")) {

            BaseConfig.HideKeyboard(CaseNotes.this);
            Bundle b = new Bundle();
            b.putString(BaseConfig.BUNDLE_PATIENT_ID, COMMON_PATIENT_ID);
            BaseConfig.globalStartIntent(this, Inpatient_Detailed_View.class, b);

        } else {

            BaseConfig.HideKeyboard(CaseNotes.this);
            multiautoTreatmentfor.setText("");
            BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        }


    }

    public void SelectedGetPatientDetails() {


        try {
            String[] pna = autocompletePatientname.getText().toString().split("-");

            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + pna[1] + "'");
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + pna[1] + "'");


            tvwAgegender.setText(Patient_AgeGender);

            BaseConfig.Glide_LoadImageView(imgvwPatientphoto, Str_Patient_Photo);


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


    }

    public void ClearAll() {
        autocompletePatientname.setText("");
        BaseConfig.Glide_LoadDefaultImageView(imgvwPatientphoto);
        tvwAgegender.setText("-");
        multiautoTreatmentfor.setText("");
        multiautoDiagnosis.setText("");
        multiautoSigns.setText("");
        autocompletePatientname.setEnabled(true);
    }

    private float calculateBMI(float weight, float height) {
        String h1 = String.valueOf(height);
        String w1 = String.valueOf(height);

        if (h1.equals("0.0") && w1.equals("0.0")) {
            return 0;
        } else {
            return (weight / (height * height)) * 10000;
        }

    }
    //All systems end

    private String interpretBMI(float bmiValue) {
        if (bmiValue < 16) {
            return "Abnormal";
        } else if (bmiValue < 18.5) {
            return "Underweight";
        } else if (bmiValue < 25) {
            return "Normal";
        } else if (bmiValue < 30) {
            return "Overweight";
        } else {
            return "Obese";
        }
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation1.hasText(multiautoDiagnosis))
            ret = false;

        if (!Validation1.hasText(multiautoTreatmentfor))
            ret = false;

        if (!Validation1.hasText(autocompletePatientname))
            ret = false;

        return ret;
    }
    //******************************************************************************************

    public void ShowSweetAlert(String message, String DIAGNOSIS_ID) {


        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setDescription(message)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {
                  /*  CaseNotes.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Investigations.class);
                    intent.putExtra("CONTINUE_STATUS", "True");
                    intent.putExtra("PASSING_TREATMENTFOR", multiautoTreatmentfor.getText().toString());
                    intent.putExtra("PASSING_DIAGNOSIS", multiautoDiagnosis.getText().toString());
                    intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
                    startActivity(intent);*/

                    CaseNotes.this.finish();
                    Intent intent = new Intent(getApplicationContext(), ClinicalInformation.class);
                    intent.putExtra("CONTINUE_STATUS", "True");
                    intent.putExtra("PASSING_DIAGNOSISID", DIAGNOSIS_ID);
                    intent.putExtra("PASSING_PATIENT_ID", autocompletePatientname.getText().toString().split("-")[1]);
                    intent.putExtra("PASSING_TREATMENTFOR", multiautoTreatmentfor.getText().toString());
                    intent.putExtra("PASSING_DIAGNOSIS", multiautoDiagnosis.getText().toString());

                    startActivity(intent);

                    clear();
                });


    }

    //******************************************************************************************

    public void clear() {
        autocompletePatientname.setText(null);
        imgvwPatientphoto.setImageBitmap(null);
        tvwAgegender.setText(null);
        multiautoDiagnosis.setText(null);
        autocompltWeight.setText(null);
        autocompltTemperature.setText(null);
        autocompltBps.setText(null);
        autocompltBpd.setText(null);
        autocompltFbs.setText(null);
        autocompltPpbs.setText(null);
        autocompltRbs.setText(null);
    }

    public void Load_Patient_Details(String PatientId) {

        try {
            String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PatientId + "'");
            String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PatientId + "'");
            String Str_Patient_Photo = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + PatientId + "'");

            autocompletePatientname.setText(String.format("%s-%s", Patient_Name, PatientId));
            tvwAgegender.setText(Patient_AgeGender);
            BaseConfig.LoadPatientImage(Str_Patient_Photo, imgvwPatientphoto, 100);
            autocompletePatientname.setEnabled(false);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {


        try {
            LoadBack();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    //******************************************************************************************

    public class LOAD_GENERAL_INFORMATION {
        ImageButton arrowGeneralExamination;
        LinearLayout primaryGeneralExaminationLayout;
        Button hideGeneralInfo;
        LinearLayout parentGeneralExaminationLayout;


        @BindView(R.id.textView_anaemic)
        TextView textViewAnaemic;
        @BindView(R.id.switch_anaemic)
        LabeledSwitch switchAnaemic;
        @BindView(R.id.TextView_icterus)
        TextView textViewIcterus;
        @BindView(R.id.switch_icterus)
        LabeledSwitch switchIcterus;
        @BindView(R.id.TextView_cyanosis)
        TextView textViewCyanosis;
        @BindView(R.id.switch_cyanosis)
        LabeledSwitch switchCyanosis;
        @BindView(R.id.textView_clubbing)
        TextView textViewClubbing;
        @BindView(R.id.switch_clubbing)
        LabeledSwitch switchClubbing;
        @BindView(R.id.textView_lymphadenopathy)
        TextView textViewLymphadenopathy;
        @BindView(R.id.switch_lymphadenopathy)
        LabeledSwitch switchLymphadenopathy;
        @BindView(R.id.textView_varicose_veins)
        TextView textViewVaricoseVeins;
        @BindView(R.id.switch_varicose_veins)
        LabeledSwitch switchVaricoseVeins;
        @BindView(R.id.textView_pedal_edema)
        TextView textViewPedalEdema;
        @BindView(R.id.switch_pedal_edema)
        LabeledSwitch switchPedalEdema;
        @BindView(R.id.textview_skininfection)
        TextView textviewSkininfection;
        @BindView(R.id.switch_skininfection)
        LabeledSwitch switchSkininfection;
        @BindView(R.id.textView_ShortStatured)
        TextView textViewShortStatured;
        @BindView(R.id.switch_ShortStatured)
        LabeledSwitch switchShortStatured;
        @BindView(R.id.textView_Built)
        TextView textViewBuilt;
        @BindView(R.id.radiogroup_built)
        RadioGroup radiogroupBuilt;
        @BindView(R.id.radioButton_lean)
        RadioButton radioButtonLean;
        @BindView(R.id.radioButton_normal)
        RadioButton radioButtonNormal;
        @BindView(R.id.radioButton_obese)
        RadioButton radioButtonObese;
        @BindView(R.id.checkBox_pallorness)
        CheckBox checkBoxPallorness;
        @BindView(R.id.checkBox_thyroid)
        CheckBox checkBoxThyroid;
        @BindView(R.id.checkBox_face_puffiness)
        CheckBox checkBoxFacePuffiness;
        @BindView(R.id.checkBox_jaundice)
        CheckBox checkBoxJaundice;
        @BindView(R.id.textView_pog)
        TextView textViewPog;
        @BindView(R.id.edt_pog)
        EditText edtPog;
        @BindView(R.id.textView_pallor)
        TextView textViewPallor;
        @BindView(R.id.edt_pallor)
        EditText edtPallor;
        @BindView(R.id.textView_oedema)
        TextView textViewOedema;
        @BindView(R.id.edt_oedema)
        EditText edtOedema;
        @BindView(R.id.textView_fundalHeight)
        TextView textViewFundalHeight;
        @BindView(R.id.edt_height)
        EditText edtHeight;
        @BindView(R.id.textView_LiePresentation)
        TextView textViewLiePresentation;
        @BindView(R.id.edt_LiePresentation)
        EditText edtLiePresentation;
        @BindView(R.id.textView_Fetalmovements)
        TextView textViewFetalmovements;
        @BindView(R.id.edt_Fetalmovements)
        EditText edtFetalmovements;
        @BindView(R.id.textView_Fetalheartrate)
        TextView textViewFetalheartrate;
        @BindView(R.id.edt_Fetalheartrate)
        EditText edtFetalheartrate;
        @BindView(R.id.textView_pv)
        TextView textViewPv;
        @BindView(R.id.edt_pv)
        EditText edtPv;
        @BindView(R.id.textView_anycomplaints)
        TextView textViewAnycomplaints;
        @BindView(R.id.edt_anycomplaints)
        EditText edtAnycomplaints;


        LOAD_GENERAL_INFORMATION() throws InterruptedException {
            View general_information = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_general_info, null);
            //1. GENERAL INFORMATION
            ButterKnife.bind(this, general_information);

            parentGeneralExaminationLayout = general_information.findViewById(R.id.sub_cn_generalinfo);
            arrowGeneralExamination = general_information.findViewById(R.id.arrow_general_examination);
            primaryGeneralExaminationLayout = general_information.findViewById(R.id.primary_general_examination_layout);
            hideGeneralInfo = general_information.findViewById(R.id.hide_generalinfo);


            arrowGeneralExamination.setOnClickListener(v -> {

                toggleSectionInput(arrowGeneralExamination, primaryGeneralExaminationLayout);

            });

            hideGeneralInfo.setOnClickListener(view -> toggleSectionInput(arrowGeneralExamination, primaryGeneralExaminationLayout));


            allCasenotesLayouts.addView(general_information);



        }


    }

    public class LOAD_CARDIO_VASCULAR {


        @BindView(R.id.textView_beat)
        TextView textViewBeat;
        @BindView(R.id.checkBox_s1)
        CheckBox checkBoxS1;
        @BindView(R.id.checkBox_s2)
        CheckBox checkBoxS2;
        @BindView(R.id.checkBox_s3)
        CheckBox checkBoxS3;
        @BindView(R.id.checkBox_s4)
        CheckBox checkBoxS4;
        @BindView(R.id.textView_murmur)
        TextView textViewMurmur;
        @BindView(R.id.checkBox_systolic)
        CheckBox checkBoxSystolic;
        @BindView(R.id.checkBox_diastolic)
        CheckBox checkBoxDiastolic;
        @BindView(R.id.checkBox_others)
        CheckBox checkBoxOthers;
        @BindView(R.id.textView_pericardialrub)
        TextView textViewPericardialrub;
        @BindView(R.id.switch_pericardialrub)
        LabeledSwitch switchPericardialrub;
        @BindView(R.id.textView_pulserate)
        TextView textViewPulserate;
        @BindView(R.id.switch_pulserate)
        LabeledSwitch switchPulserate;
        @BindView(R.id.textView_heartrate)
        TextView textViewHeartrate;
        @BindView(R.id.editText_heartrate)
        EditText editTextHeartrate;

        ImageButton arrowCardioVascularSystem;
        LinearLayout primaryCardioVascularSystemLayout;
        Button hideCardioInfo;

        LOAD_CARDIO_VASCULAR() {
            View cardio_information = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_cardiovascular, null);
            //2.CARDIO VASCULAR


            arrowCardioVascularSystem = cardio_information.findViewById(R.id.arrow_cardio_vascular_system);
            primaryCardioVascularSystemLayout = cardio_information.findViewById(R.id.primary_cardio_vascular_system_layout);
            hideCardioInfo = cardio_information.findViewById(R.id.hide_cardioinfo);

            arrowCardioVascularSystem.setOnClickListener(view -> toggleSectionInput(arrowCardioVascularSystem, primaryCardioVascularSystemLayout));

            hideCardioInfo.setOnClickListener(view -> toggleSectionInput(arrowCardioVascularSystem, primaryCardioVascularSystemLayout));

            allCasenotesLayouts.addView(cardio_information);


            ButterKnife.bind(this, cardio_information);

        }

    }

    public class LOAD_RESPIRATORY {


        @BindView(R.id.textView_breathsound)
        TextView textViewBreathsound;
        @BindView(R.id.radiogroup_breathsoundequal)
        RadioGroup radiogroupBreathsoundequal;
        @BindView(R.id.rbtn_yes)
        RadioButton rbtnYes;
        @BindView(R.id.rbtn_no)
        RadioButton rbtnNo;
        @BindView(R.id.radiogroup_breathsoundequal_optns)
        RadioGroup radiogroupBreathsoundequalOptns;
        @BindView(R.id.rrbtn_DecreaseonLeft)
        RadioButton rrbtnDecreaseonLeft;
        @BindView(R.id.rrbtn_Decreaseonright)
        RadioButton rrbtnDecreaseonright;
        @BindView(R.id.textView_breath_sound)
        TextView textViewBreathSound;
        @BindView(R.id.spinner_breath_sound)
        Spinner spinnerBreathSound;
        @BindView(R.id.textView_trachea)
        TextView textViewTrachea;
        @BindView(R.id.spinner2_trachea)
        Spinner spinner_Trachea;
        @BindView(R.id.checkBox_kyphosis)
        CheckBox checkBoxKyphosis;
        @BindView(R.id.checkBox_scoliosis)
        CheckBox checkBoxScoliosis;
        @BindView(R.id.textView_crepitation)
        TextView textViewCrepitation;
        @BindView(R.id.checkBox_left)
        CheckBox checkBoxLeft;
        @BindView(R.id.checkBox_right)
        CheckBox checkBoxRight;
        @BindView(R.id.textView_rhonchi)
        TextView textViewRhonchi;
        @BindView(R.id.checkBox_rhonchi_left)
        CheckBox checkBoxRhonchiLeft;
        @BindView(R.id.checkBox__rhonchi_right)
        CheckBox checkBoxRhonchiRight;
        @BindView(R.id.TextView_shape_of_the_chest)
        TextView textViewShapeOfTheChest;
        @BindView(R.id.spinner_shape_of_the_chest)
        Spinner spinnerShapeOfTheChest;
        @BindView(R.id.textView_respiratory_rate)
        TextView textViewRespiratoryRate;
        @BindView(R.id.editText_respiratory_rate)
        EditText editTextRespiratoryRate;
        @BindView(R.id.textView_spo2)
        TextView textViewSpo2;
        @BindView(R.id.autoCompleteTextView_spo2)
        AutoCompleteTextView autoCompleteTextViewSpo2;
        ImageButton arrowRespiratorySystem;
        LinearLayout primaryRespiratorySystemLayout;
        Button hideRespiratoryinfo;


        LOAD_RESPIRATORY() {
            //3.RESPIRATORY SYSTEM
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_respiratory, null);


            arrowRespiratorySystem = current_view.findViewById(R.id.arrow_respiratory_system);
            primaryRespiratorySystemLayout = current_view.findViewById(R.id.primary_respiratory_system_layout);
            hideRespiratoryinfo = current_view.findViewById(R.id.hide_respiratoryinfo);

            arrowRespiratorySystem.setOnClickListener(view -> toggleSectionInput(arrowRespiratorySystem, primaryRespiratorySystemLayout));

            hideRespiratoryinfo.setOnClickListener(view -> toggleSectionInput(arrowRespiratorySystem, primaryRespiratorySystemLayout));

            allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);


            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                shapeofchest = new String[]{"निवडा", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            } else {

                shapeofchest = new String[]{"Select", "Normal", "Barrel shaped", "PectusCarinatum", "PectusExcavatum"};
            }


            //Load spinners
            BaseConfig.LoadValuesSpinner(spinnerBreathSound, CaseNotes.this, "select distinct Breath as dvalue from Mstr_Breath where IsActive=1 and Breath!='' order by Breath;", getString(R.string.select));

            BaseConfig.LoadValuesSpinner(spinner_Trachea, CaseNotes.this, "select distinct Trachea as dvalue from Mstr_Trachea where IsActive=1 and Trachea!='' order by Trachea;", getString(R.string.select));

            BaseConfig.loadSpinner(spinnerShapeOfTheChest, shapeofchest);


            radiogroupBreathsoundequal.setOnCheckedChangeListener((radioGroup, i) -> {
                if (radioGroup.getCheckedRadioButtonId() == R.id.rbtn_yes) {
                    radiogroupBreathsoundequalOptns.setVisibility(View.GONE);
                } else if (radioGroup.getCheckedRadioButtonId() == R.id.rbtn_no) {
                    radiogroupBreathsoundequalOptns.setVisibility(View.VISIBLE);
                    Toast.makeText(CaseNotes.this, "Select below options if no breath sound found...", Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    public class LOAD_GASTRO {

        @BindView(R.id.textView_shape_of_the_abdomen)
        TextView textViewShapeOfTheAbdomen;
        @BindView(R.id.spinner_shape_of_the_abdomen)
        Spinner spinnerShapeOfTheAbdomen;
        @BindView(R.id.textView_abdomen)
        TextView textViewAbdomen;
        @BindView(R.id.abdon_select)
        Button buttonAbdomen;
        @BindView(R.id.listView1)
        ListView listView1;
        @BindView(R.id.textView_visible_pulsation1)
        TextView textViewVisiblePulsation1;
        @BindView(R.id.switch_visible_pulsation1)
        LabeledSwitch switchVisiblePulsation1;
        @BindView(R.id.textView_switch_visible_perist1)
        TextView textViewSwitchVisiblePerist1;
        @BindView(R.id.switch_visible_perist1)
        LabeledSwitch switchVisiblePerist1;
        @BindView(R.id.textView_abdominal_palpation)
        TextView textViewAbdominalPalpation;
        @BindView(R.id.spinner_abdominal_palpation)
        Spinner spinnerAbdominalPalpation;
        @BindView(R.id.textView_splenomegaly)
        TextView textViewSplenomegaly;
        @BindView(R.id.autoCompleteTextView_splenomegaly)
        AutoCompleteTextView autoCompleteTextViewSplenomegaly;
        @BindView(R.id.textView_hepatomegaly)
        TextView textViewHepatomegaly;
        @BindView(R.id.autoCompleteTextView_hepatomegaly)
        AutoCompleteTextView autoCompleteTextViewHepatomegaly;
        @BindView(R.id.textView_palpable_masses)
        TextView textViewPalpableMasses;
        @BindView(R.id.autoCompleteTextView_palpable_masses)
        AutoCompleteTextView autoCompleteTextViewPalpableMasses;
        @BindView(R.id.textView_bowel_sounds)
        TextView textViewBowelSounds;
        @BindView(R.id.autoCompleteTextView_bowel_sounds)
        AutoCompleteTextView autoCompleteTextViewBowelSounds;
        @BindView(R.id.textView_hernial)
        TextView textViewHernial;
        @BindView(R.id.checkBox_orifices)
        CheckBox checkBoxOrifices;
        @BindView(R.id.checkBox_free)
        CheckBox checkBoxFree;
        @BindView(R.id.textView)
        TextView textView;
        @BindView(R.id.spinner3)
        Spinner spinner3;
        @BindView(R.id.textView_spleen)
        TextView textViewSpleen;
        @BindView(R.id.switch_spleen)
        LabeledSwitch switchSpleen;
        @BindView(R.id.textView16)
        TextView textView16;
        @BindView(R.id.switch_liver_normal)
        LabeledSwitch switchLiverNormal;
        @BindView(R.id.textView_organomegely)
        TextView textViewOrganomegely;
        @BindView(R.id.AutoCompleteTextView_organomegely)
        AutoCompleteTextView autoCompleteTextViewOrganomegely;
        @BindView(R.id.textView_free_fluid_in_the_abdomen)
        TextView textViewFreeFluidInTheAbdomen;
        @BindView(R.id.AutoCompleteTextView_free_fluid_in_the_abdomen)
        AutoCompleteTextView autoCompleteTextViewFreeFluidInTheAbdomen;
        @BindView(R.id.textView_distension)
        TextView textViewDistension;
        @BindView(R.id.autoCompleteTextView_distension)
        AutoCompleteTextView autoCompleteTextViewDistension;
        @BindView(R.id.textView_tenderness)
        TextView textViewTenderness;
        @BindView(R.id.autoCompleteTextView_tenderness)
        AutoCompleteTextView autoCompleteTextViewTenderness;
        @BindView(R.id.textView_scrotum)
        TextView textViewScrotum;
        @BindView(R.id.AutoCompleteTextView_scrotum)
        AutoCompleteTextView autoCompleteTextViewScrotum;

        ImageButton arrowGastrointestinalSystem;
        LinearLayout primaryGastrointestinalSystemLayout;
        Button hideGastroinfo;

        LOAD_GASTRO() {
            //4.GASTRO SYSTEM
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_gastro, null);

            arrowGastrointestinalSystem = current_view.findViewById(R.id.arrow_gastrointestinal_system);
            primaryGastrointestinalSystemLayout = current_view.findViewById(R.id.primary_gastrointestinal_system_layout);
            hideGastroinfo = current_view.findViewById(R.id.hide_gastroinfo);

            arrowGastrointestinalSystem.setOnClickListener(view -> toggleSectionInput(arrowGastrointestinalSystem, primaryGastrointestinalSystemLayout));

            hideGastroinfo.setOnClickListener(view -> toggleSectionInput(arrowGastrointestinalSystem, primaryGastrointestinalSystemLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);

            //load abdomen

            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                abdomenshape = new String[]{"निवडा", "Normal", "Scaphoid", "Sunken", "Distended"};
                abdomenpalpation = new String[]{"निवडा", "Soft", "Guarding", "Rigidity"};
            } else {
                abdomenshape = new String[]{"Select", "Normal", "Scaphoid", "Sunken", "Distended"};
                abdomenpalpation = new String[]{"Select", "Soft", "Guarding", "Rigidity"};
            }

            BaseConfig.loadSpinner(spinnerShapeOfTheAbdomen, abdomenshape);

            BaseConfig.loadSpinner(spinnerAbdominalPalpation, abdomenpalpation);

            buttonAbdomen.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    try {
                        LoadAbdomen();
                        showSMSalertDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                private void LoadAbdomen() {

                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    Cursor c = db.rawQuery("select distinct Abdomen from mstr_Abdomen order by Abdomen;", null);

                    List<String> list1 = new ArrayList<String>();

                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {

                                String pname = c.getString(c.getColumnIndex("Abdomen"));

                                list1.add(pname);

                            } while (c.moveToNext());
                        }
                    }

                    new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, list1);

                    items_abdomen = list1.toArray(new String[list1.size()]);

                    db.close();
                    c.close();
                }

                private void showSMSalertDialog() {

                    boolean[] checkedColours = new boolean[items_abdomen.length];
                    int count = items_abdomen.length;

                    for (int i = 0; i < count; i++)
                        checkedColours[i] = selectedAbdomen.contains(items_abdomen[i]);

                    DialogInterface.OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {

                        if (isChecked) {
                            selectedAbdomen.add(items_abdomen[which]);

                        } else {
                            selectedAbdomen.remove(items_abdomen[which]);
                        }

                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(CaseNotes.this);
                    builder.setTitle("Select Abdomen");
                    builder.setMultiChoiceItems(items_abdomen, checkedColours, coloursDialogListener);
                    builder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (CharSequence colour : selectedAbdomen) {
                            stringBuilder.append(colour + "\n");
                        }
                        String[] splt = stringBuilder.toString().split("\n");
                        abdomen_list = new ArrayList<String>();
                        abdomen_list.clear();

                        if (splt.length > 0) {

                            abdomen_list.addAll(Arrays.asList(splt));

                            if (abdomen_list.size() > 0) {
                                ArrayAdapter<String> listadapter = new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, abdomen_list);
                                listView1.setAdapter(listadapter);
                                Helper.getListViewSize(listView1);
                            }

                        }

                    });
                    builder.setNegativeButton(getResources().getString(R.string.cancel),
                            (dialog, which) -> new StringBuilder());

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            });


        }

    }

    public class LOAD_NEUROLOGY {

        @BindView(R.id.textView_pupil_size)
        TextView textViewPupilSize;
        @BindView(R.id.radiogroup_pupilsize)
        RadioGroup radiogroupPupilsize;
        @BindView(R.id.radioButton_normal)
        RadioButton radioButtonNormal;
        @BindView(R.id.radioButton_decrease)
        RadioButton radioButtonDecrease;
        @BindView(R.id.radioButton_increase)
        RadioButton radioButtonIncrease;
        @BindView(R.id.textView20)
        TextView textView20;
        @BindView(R.id.radiogroup_speech)
        RadioGroup radiogroupSpeech;
        @BindView(R.id.radioButton_speech_normal)
        RadioButton radioButtonSpeechNormal;
        @BindView(R.id.radioButton_dysphasia)
        RadioButton radioButtonDysphasia;
        @BindView(R.id.radioButton_aphasia)
        RadioButton radioButtonAphasia;
        @BindView(R.id.textView_carotid)
        TextView textViewCarotid;
        @BindView(R.id.radiogroup_carotid)
        RadioGroup radiogroupCarotid;
        @BindView(R.id.radioButton_bruit_present)
        RadioButton radioButtonBruitPresent;
        @BindView(R.id.radioButton_bruit_absent)
        RadioButton radioButtonBruitAbsent;
        @BindView(R.id.textView23)
        TextView textView23;
        @BindView(R.id.switch_amnesiay)
        LabeledSwitch switchAmnesiay;
        @BindView(R.id.textView24)
        TextView textView24;
        @BindView(R.id.switch_apraxiay)
        LabeledSwitch switchApraxiay;
        @BindView(R.id.textView_cognitive_function)
        TextView textViewCognitiveFunction;
        @BindView(R.id.radiogroup_cognitive_function)
        RadioGroup radiogroupCognitiveFunction;
        @BindView(R.id.radioButton_cognitive_function_normal)
        RadioButton radioButtonCognitiveFunctionNormal;
        @BindView(R.id.radioButton_impaired)
        RadioButton radioButtonImpaired;
        @BindView(R.id.textView_bulk)
        TextView textViewBulk;
        @BindView(R.id.radiogroup_bulk)
        RadioGroup radiogroupBulk;
        @BindView(R.id.radioButton_bulk_normal)
        RadioButton radioButtonBulkNormal;
        @BindView(R.id.radioButton_bulk_reduced)
        RadioButton radioButtonBulkReduced;
        @BindView(R.id.radioButton_bulk_increase)
        RadioButton radioButtonBulkIncrease;
        @BindView(R.id.textView_tone)
        TextView textViewTone;
        @BindView(R.id.rdgrp_tones)
        RadioGroup radiogroupTones;
        @BindView(R.id.radioButton_tone_normal)
        RadioButton radioButtonToneNormal;
        @BindView(R.id.radioButton_tone_reduced)
        RadioButton radioButtonToneReduced;
        @BindView(R.id.radioButton_tone_increased)
        RadioButton radioButtonToneIncreased;
        @BindView(R.id.textView_power)
        TextView textViewPower;
        @BindView(R.id.textView_left_upper_limb)
        TextView textViewLeftUpperLimb;
        @BindView(R.id.seekBar_left_upper_limb)
        SeekBar seekBarLeftUpperLimb;
        @BindView(R.id.textView_left_upper_limb_5_5)
        TextView textViewLeftUpperLimb55;
        @BindView(R.id.textView_right_upper_limb)
        TextView textViewRightUpperLimb;
        @BindView(R.id.seekBar_right_upper_limb)
        SeekBar seekBarRightUpperLimb;
        @BindView(R.id.textView_right_upper_limb_5_5)
        TextView textViewRightUpperLimb55;
        @BindView(R.id.textView_left_lower_limb)
        TextView textViewLeftLowerLimb;
        @BindView(R.id.seekBar__left_lower_limb)
        SeekBar seekBarLeftLowerLimb;
        @BindView(R.id.textView__left_lower_limb_5_5)
        TextView textViewLeftLowerLimb55;
        @BindView(R.id.textView_right_lower_limb)
        TextView textViewRightLowerLimb;
        @BindView(R.id.seekBar_right_lower_limb)
        SeekBar seekBarRightLowerLimb;
        @BindView(R.id.textView_right_lower_limb_5_5)
        TextView textViewRightLowerLimb55;
        @BindView(R.id.textView_sensory)
        TextView textViewSensory;
        @BindView(R.id.spinner_sensory)
        Spinner spinnerSensory;
        @BindView(R.id.textView_corneal)
        TextView textViewCorneal;
        @BindView(R.id.spinner_corneal)
        Spinner spinnerCorneal;
        @BindView(R.id.textView_biceps)
        TextView textViewBiceps;
        @BindView(R.id.spinner_biceps)
        Spinner spinnerBiceps;
        @BindView(R.id.textView_triceps)
        TextView textViewTriceps;
        @BindView(R.id.spinner_triceps)
        Spinner spinnerTriceps;
        @BindView(R.id.textView_supinator)
        TextView textViewSupinator;
        @BindView(R.id.spinner_supinator)
        Spinner spinnerSupinator;
        @BindView(R.id.textView_knee)
        TextView textViewKnee;
        @BindView(R.id.spinner_knee)
        Spinner spinnerKnee;
        @BindView(R.id.textView_ankle)
        TextView textViewAnkle;
        @BindView(R.id.spinner_ankle)
        Spinner spinnerAnkle;
        @BindView(R.id.textView_plantar)
        TextView textViewPlantar;
        @BindView(R.id.spinner_plantar)
        Spinner spinnerPlantar;
        @BindView(R.id.textView_pcentral_nervous_system)
        TextView textViewPcentralNervousSystem;
        @BindView(R.id.textView_oriented)
        TextView textViewOriented;
        @BindView(R.id.AutoCompleteTextView_oriented)
        AutoCompleteTextView autoCompleteTextViewOriented;
        @BindView(R.id.textView_neck_rigidity)
        TextView textViewNeckRigidity;
        @BindView(R.id.AutoCompleteTextView_neck_rigidity)
        AutoCompleteTextView autoCompleteTextViewNeckRigidity;
        @BindView(R.id.textView_confused)
        TextView textViewConfused;
        @BindView(R.id.AutoCompleteTextView_confused)
        AutoCompleteTextView autoCompleteTextViewConfused;
        @BindView(R.id.textView_exaggerated)
        TextView textViewExaggerated;
        @BindView(R.id.AutoCompleteTextView_exaggerated)
        AutoCompleteTextView autoCompleteTextViewExaggerated;
        @BindView(R.id.textView_extenstor)
        TextView textViewExtenstor;
        @BindView(R.id.AutoCompleteTextView_extenstor)
        AutoCompleteTextView autoCompleteTextViewExtenstor;
        @BindView(R.id.textView_gs_score)
        TextView textViewGsScore;
        @BindView(R.id.AutoCompleteTextView_gs_score)
        AutoCompleteTextView autoCompleteTextViewGsScore;
        @BindView(R.id.textView_incomprehensible)
        TextView textViewIncomprehensible;
        @BindView(R.id.AutoCompleteTextView_incomprehensible)
        AutoCompleteTextView autoCompleteTextViewIncomprehensible;
        @BindView(R.id.textView_depressed)
        TextView textViewDepressed;
        @BindView(R.id.AutoCompleteTextView_depressed)
        AutoCompleteTextView autoCompleteTextViewDepressed;
        @BindView(R.id.textView_flexor)
        TextView textViewFlexor;
        @BindView(R.id.AutoCompleteTextView_flexor)
        AutoCompleteTextView autoCompleteTextViewFlexor;
        @BindView(R.id.TextView_coma)
        TextView textViewComa;
        @BindView(R.id.AutoCompleteTextView_coma)
        AutoCompleteTextView autoCompleteTextViewComa;
        @BindView(R.id.textView_musclo_skeletal_systems)
        TextView textViewMuscloSkeletalSystems;
        @BindView(R.id.textView_congential_abnormality)
        TextView textViewCongentialAbnormality;
        @BindView(R.id.AutoCompleteTextView_congential_abnormality)
        AutoCompleteTextView autoCompleteTextViewCongentialAbnormality;
        @BindView(R.id.textView_mental_status)
        TextView textViewMentalStatus;
        @BindView(R.id.autoCompleteTextView_mental_status)
        AutoCompleteTextView autoCompleteTextViewMentalStatus;

        ImageButton arrowNeurologySystem;
        LinearLayout primaryNeurologySystemLayout;
        Button hideNeurologyinfo;

        LOAD_NEUROLOGY() {
            //5.NEUROLOGY
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_neurology, null);


            arrowNeurologySystem = current_view.findViewById(R.id.arrow_neurology_system);
            primaryNeurologySystemLayout = current_view.findViewById(R.id.primary_neurology_system_layout);
            hideNeurologyinfo = current_view.findViewById(R.id.hide_neurologyinfo);

            arrowNeurologySystem.setOnClickListener(view -> toggleSectionInput(arrowNeurologySystem, primaryNeurologySystemLayout));

            hideNeurologyinfo.setOnClickListener(view -> toggleSectionInput(arrowNeurologySystem, primaryNeurologySystemLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);

            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {
                reflexes = new String[]{"निवडा", "Normal", "Reduced", "Increased", "Absent"};
            } else {
                reflexes = new String[]{"Select", "Normal", "Reduced", "Increased", "Absent"};
            }

            BaseConfig.loadSpinner(spinnerSensory, reflexes);
            BaseConfig.loadSpinner(spinnerCorneal, reflexes);
            BaseConfig.loadSpinner(spinnerBiceps, reflexes);
            BaseConfig.loadSpinner(spinnerTriceps, reflexes);
            BaseConfig.loadSpinner(spinnerSupinator, reflexes);
            BaseConfig.loadSpinner(spinnerKnee, reflexes);
            BaseConfig.loadSpinner(spinnerAnkle, reflexes);
            BaseConfig.loadSpinner(spinnerPlantar, reflexes);

           seekBarLeftUpperLimb.setMax(5);
           seekBarRightUpperLimb.setMax(5);
           seekBarLeftLowerLimb.setMax(5);
           seekBarRightLowerLimb.setMax(5);

            seekBarLeftUpperLimb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   textViewLeftUpperLimb55.setText(String.valueOf(progress+"/5"));

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });

           seekBarRightUpperLimb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   textViewRightUpperLimb55.setText(String.valueOf(progress+"/5"));

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
           seekBarLeftLowerLimb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   textViewLeftLowerLimb55.setText(String.valueOf(progress+"/5"));

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });
           seekBarRightLowerLimb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
               @Override
               public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                   textViewRightLowerLimb55.setText(String.valueOf(progress+"/5"));

               }

               @Override
               public void onStartTrackingTouch(SeekBar seekBar) {

               }

               @Override
               public void onStopTrackingTouch(SeekBar seekBar) {

               }
           });


        }

    }

    public class LOAD_MOTOR {

        @BindView(R.id.Motor)
        LinearLayout motor;
        @BindView(R.id.TextView36)
        TextView textView36;
        @BindView(R.id.textView_symmetry)
        TextView textViewSymmetry;
        @BindView(R.id.switch_Symmetry1)
        LabeledSwitch switchSymmetry1;
        @BindView(R.id.textView_Smooth_movement1)
        TextView textViewSmoothMovement1;
        @BindView(R.id.switch_Smooth_movement1)
        LabeledSwitch switchSmoothMovement1;
        @BindView(R.id.textView_Arms_swing1)
        TextView textViewArmsSwing1;
        @BindView(R.id.switch_Arms_swing1)
        LabeledSwitch switchArmsSwing1;
        @BindView(R.id.textView_Pelvic_tilt1)
        TextView textViewPelvicTilt1;
        @BindView(R.id.switch_Pelvic_tilt1)
        LabeledSwitch switchPelvicTilt1;
        @BindView(R.id.textView__length1)
        TextView textViewLength1;
        @BindView(R.id.Stride_length1)
        LabeledSwitch strideLength1;
        @BindView(R.id.textView_spine)
        TextView textViewSpine;
        @BindView(R.id.textView_cervical_lordosis)
        TextView textViewCervicalLordosis;
        @BindView(R.id.switch_Cervical_Lordosis1)
        LabeledSwitch switchCervicalLordosis1;
        @BindView(R.id.textView_lumbar_lordosis)
        TextView textViewLumbarLordosis;
        @BindView(R.id.switch_Lumbar_Lordosis1)
        LabeledSwitch switchLumbarLordosis1;
        @BindView(R.id.textView_kyphosis)
        TextView textViewKyphosis;
        @BindView(R.id.switch_Kyphosis1)
        LabeledSwitch switchKyphosis1;
        @BindView(R.id.textView_Scoliosis1)
        TextView textViewScoliosis1;
        @BindView(R.id.switch_Scoliosis1)
        LabeledSwitch switchScoliosis1;
        @BindView(R.id.textView_lower_limb)
        TextView textViewLowerLimb;
        @BindView(R.id.textView_swelling)
        TextView textViewSwelling;
        @BindView(R.id.switch_llSwelling1)
        LabeledSwitch switchLlSwelling1;
        @BindView(R.id.textView_deformity)
        TextView textViewDeformity;
        @BindView(R.id.switch_llDeformity1)
        LabeledSwitch switchLlDeformity1;
        @BindView(R.id.textView_limb_shortening)
        TextView textViewLimbShortening;
        @BindView(R.id.switch_llLimb_shortening1)
        LabeledSwitch switchLlLimbShortening1;
        @BindView(R.id.textView_muscle_wasting)
        TextView textViewMuscleWasting;
        @BindView(R.id.switch_llMuscle_wasting1)
        LabeledSwitch switchLlMuscleWasting1;
        @BindView(R.id.textView_remarks)
        TextView textViewRemarks;
        @BindView(R.id.editText_remarks)
        EditText editTextRemarks;
        @BindView(R.id.textView_upper_limb)
        TextView textViewUpperLimb;
        @BindView(R.id.textView_upper_limb_swelling)
        TextView textViewUpperLimbSwelling;
        @BindView(R.id.switch_upper_limb_swelling)
        LabeledSwitch switchUpperLimbSwelling;
        @BindView(R.id.textView_upper_limb_deformity)
        TextView textViewUpperLimbDeformity;
        @BindView(R.id.switch__upper_limb_deformity)
        LabeledSwitch switchUpperLimbDeformity;
        @BindView(R.id.textView_upper_limb_shortening)
        TextView textViewUpperLimbShortening;
        @BindView(R.id.switch_upper_limb_shortening)
        LabeledSwitch switchUpperLimbShortening;
        @BindView(R.id.textView_upper_limb_muscle_wasting)
        TextView textViewUpperLimbMuscleWasting;
        @BindView(R.id.switch_upper_limb_wasting1)
        LabeledSwitch switchUpperLimbWasting1;
        @BindView(R.id.textView_remarks1)
        TextView textViewRemarks1;
        @BindView(R.id.editText_remarks1)
        EditText editTextRemarks1;

        ImageButton arrowLocomotorSystem;
        LinearLayout primaryLocomotorSystemLayout;
        Button hideLocomotorinfo;

        LOAD_MOTOR() {
            //6.LOCOMOTOR

            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_locomotor, null);

            arrowLocomotorSystem = current_view.findViewById(R.id.arrow_locomotor_system);
            primaryLocomotorSystemLayout = current_view.findViewById(R.id.primary_locomotor_system_layout);
            hideLocomotorinfo = current_view.findViewById(R.id.hide_locomotorinfo);

            arrowLocomotorSystem.setOnClickListener(view -> toggleSectionInput(arrowLocomotorSystem, primaryLocomotorSystemLayout));

            hideLocomotorinfo.setOnClickListener(view -> toggleSectionInput(arrowLocomotorSystem, primaryLocomotorSystemLayout));

            allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);

        }

    }

    public class LOAD_RENAL {

        @BindView(R.id.textView_dysuria)
        TextView textViewDysuria;
        @BindView(R.id.switch_dysuria1)
        LabeledSwitch switchDysuria1;
        @BindView(R.id.textView_pyuria)
        TextView textViewPyuria;
        @BindView(R.id.switch_pyuria1)
        LabeledSwitch switchPyuria1;
        @BindView(R.id.textView_oliguria)
        TextView textViewOliguria;
        @BindView(R.id.switch_oliguria1)
        LabeledSwitch switchOliguria1;
        @BindView(R.id.textView_polyuria)
        TextView textViewPolyuria;
        @BindView(R.id.switch_polyuria1)
        LabeledSwitch switchPolyuria1;
        @BindView(R.id.textView_anuria)
        TextView textViewAnuria;
        @BindView(R.id.switch_anuria1)
        LabeledSwitch switchAnuria1;
        @BindView(R.id.textView_nocturia)
        TextView textViewNocturia;
        @BindView(R.id.switch_nocturia1)
        LabeledSwitch switchNocturia1;
        @BindView(R.id.textView_urethral_discharge)
        TextView textViewUrethralDischarge;
        @BindView(R.id.switch_urethraldischarge1)
        LabeledSwitch switchUrethraldischarge1;
        @BindView(R.id.textView_prostate)
        TextView textViewProstate;
        @BindView(R.id.switch_prostate)
        LabeledSwitch switchProstate;
        @BindView(R.id.textView_haematuria)
        TextView textViewHaematuria;
        @BindView(R.id.spinner_haematuria)
        Spinner spinnerHaematuria;


        ImageButton arrowRenalSystem;
        LinearLayout primaryRenalSystemLayout;
        Button hideRenalinfo;

        LOAD_RENAL() {
            //7.RENAL SYSTEM
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_renal_system, null);

            arrowRenalSystem = current_view.findViewById(R.id.arrow_renal_system);
            primaryRenalSystemLayout = current_view.findViewById(R.id.primary_renal_system_layout);
            hideRenalinfo = current_view.findViewById(R.id.hide_renalinfo);

            arrowRenalSystem.setOnClickListener(view -> toggleSectionInput(arrowRenalSystem, primaryRenalSystemLayout));

            hideRenalinfo.setOnClickListener(view -> toggleSectionInput(arrowRenalSystem, primaryRenalSystemLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            if (BaseConfig.CurrentLangauges.toString().equalsIgnoreCase("mr")) {

                haematuriaarry = new String[]{"निवडा", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

            } else {

                haematuriaarry = new String[]{"Select", "Continuous", "Intermittent", "Microscopic", "Macroscopic", "Painful", "Painless"};

            }

            BaseConfig.loadSpinner(spinnerHaematuria, haematuriaarry);



        }

    }

    public class LOAD_ENDOSYS {
        ImageButton arrowEndocrineSystem;
        LinearLayout primaryEndocrineSystemLayout;
        Button buttonEndocrineAdd;
        EditText edtEndocrine;
        Button hideEndocrineinfo;

        LOAD_ENDOSYS() {
            //8.ENDOCRINE
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_endrocine, null);


            arrowEndocrineSystem = current_view.findViewById(R.id.arrow_endocrine_system);
            primaryEndocrineSystemLayout = current_view.findViewById(R.id.primary_endocrine_system_layout);
            buttonEndocrineAdd = current_view.findViewById(R.id.button_endocrine_add);
            edtEndocrine = current_view.findViewById(R.id.edt_endocrine);
            hideEndocrineinfo = current_view.findViewById(R.id.hide_endocrineinfo);


            arrowEndocrineSystem.setOnClickListener(view -> toggleSectionInput(arrowEndocrineSystem, primaryEndocrineSystemLayout));

            hideEndocrineinfo.setOnClickListener(view -> toggleSectionInput(arrowEndocrineSystem, primaryEndocrineSystemLayout));


            allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);



            buttonEndocrineAdd.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    try {
                        LoadAbdomen();
                        showSMSalertDialog();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                CharSequence[]   items_endocrine;
                private void LoadAbdomen() {

                    SQLiteDatabase db = BaseConfig.GetDb();//);
                    Cursor c = db.rawQuery("select distinct Endocrine from Mstr_Endocrine where IsActive=1 order by Endocrine;", null);

                    List<String> list1 = new ArrayList<String>();

                    if (c != null) {
                        if (c.moveToFirst()) {
                            do {

                                String pname = c.getString(c.getColumnIndex("Endocrine"));

                                list1.add(pname);

                            } while (c.moveToNext());
                        }
                    }

                    new ArrayAdapter<String>(CaseNotes.this, android.R.layout.simple_list_item_1, list1);

                    items_endocrine = list1.toArray(new String[list1.size()]);

                    db.close();
                    c.close();
                }

                private void showSMSalertDialog() {

                    boolean[] checkedColours = new boolean[items_endocrine.length];
                    int count = items_endocrine.length;

                    for (int i = 0; i < count; i++)
                        checkedColours[i] = selectedEndocrine.contains(items_endocrine[i]);

                    DialogInterface.OnMultiChoiceClickListener coloursDialogListener = (dialog, which, isChecked) -> {

                        if (isChecked) {
                            selectedEndocrine.add(items_endocrine[which]);

                        } else {
                            selectedEndocrine.remove(items_endocrine[which]);
                        }

                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(CaseNotes.this);
                    builder.setTitle("Select Endocrine");
                    builder.setMultiChoiceItems(items_endocrine, checkedColours, coloursDialogListener);
                    builder.setPositiveButton(getResources().getString(R.string.ok), (dialog, which) -> {
                        StringBuilder stringBuilder = new StringBuilder();

                        for (CharSequence colour : selectedEndocrine) {
                            stringBuilder.append(colour + "\n");
                        }
                        String[] splt = stringBuilder.toString().split("\n");

                        edtEndocrine.setText(stringBuilder.toString());

                    });
                    builder.setNegativeButton(getResources().getString(R.string.cancel),
                            (dialog, which) -> new StringBuilder());

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }


            });


        }

    }

    public class LOAD_CLINICAL {

        @BindView(R.id.Clinical)
        LinearLayout clinical;
        @BindView(R.id.textView_haemogram)
        TextView textViewHaemogram;
        @BindView(R.id.textView_haemoglobin_g_dl)
        TextView textViewHaemoglobinGDl;
        @BindView(R.id.editText_haemoglobin_g_dl)
        EditText editTextHaemoglobinGDl;
        @BindView(R.id.editText_wbc)
        EditText editTextWBC;
        @BindView(R.id.textView_monocytes)
        TextView textViewMonocytes;
        @BindView(R.id.editText_monocytes)
        EditText editTextMonocytes;
        @BindView(R.id.textView_basophils)
        TextView textViewBasophils;
        @BindView(R.id.editText_basophils)
        EditText editTextBasophils;
        @BindView(R.id.textView_rbc)
        TextView textViewRbc;
        @BindView(R.id.editText_rbc)
        EditText editTextRbc;
        @BindView(R.id.textView_esr)
        TextView textViewEsr;
        @BindView(R.id.editText_esr)
        EditText editTextEsr;
        @BindView(R.id.textView_polymorphs)
        TextView textViewPolymorphs;
        @BindView(R.id.editText_polymorphs)
        EditText editTextPolymorphs;
        @BindView(R.id.textView_lymphocytes)
        TextView textViewLymphocytes;
        @BindView(R.id.editText_lymphocytes)
        EditText editTextLymphocytes;
        @BindView(R.id.TextView_renal_markers)
        TextView textViewRenalMarkers;
        @BindView(R.id.textView_eosinophils)
        TextView textViewEosinophils;
        @BindView(R.id.editText_eosinophils)
        EditText editTextEosinophils;
        @BindView(R.id.textView_urea)
        TextView textViewUrea;
        @BindView(R.id.editText_urea)
        EditText editTextUrea;
        @BindView(R.id.textView_creatinine)
        TextView textViewCreatinine;
        @BindView(R.id.editText_creatinine)
        EditText editTextCreatinine;
        @BindView(R.id.textView_uric_acid)
        TextView textViewUricAcid;
        @BindView(R.id.editText_uric_acid)
        EditText editTextUricAcid;
        @BindView(R.id.TextView_electrolytes)
        TextView textViewElectrolytes;
        @BindView(R.id.TextView_sodium)
        TextView textViewSodium;
        @BindView(R.id.EditText_sodium)
        EditText editTextSodium;
        @BindView(R.id.TextView_potassium)
        TextView textViewPotassium;
        @BindView(R.id.EditText_potassium)
        EditText editTextPotassium;
        @BindView(R.id.TextView_chloride)
        TextView textViewChloride;
        @BindView(R.id.EditText_chloride)
        EditText editTextChloride;
        @BindView(R.id.TextView_bicarbonate)
        TextView textViewBicarbonate;
        @BindView(R.id.EditText_bicarbonate)
        EditText editTextBicarbonate;
        @BindView(R.id.TextView_lipo_profile)
        TextView textViewLipoProfile;
        @BindView(R.id.TextView_total_cholesterol)
        TextView textViewTotalCholesterol;
        @BindView(R.id.EditText_total_cholesterol)
        EditText editTextTotalCholesterol;
        @BindView(R.id.TextView_ldl)
        TextView textViewLdl;
        @BindView(R.id.EditText_ldl)
        EditText editTextLdl;
        @BindView(R.id.TextView_hdl)
        TextView textViewHdl;
        @BindView(R.id.EditText_hdl)
        EditText editTextHdl;
        @BindView(R.id.TextView_vldl)
        TextView textViewVldl;
        @BindView(R.id.EditText_vldl)
        EditText editTextVldl;
        @BindView(R.id.TextView_triglycerides)
        TextView textViewTriglycerides;
        @BindView(R.id.EditText_triglycerides)
        EditText editTextTriglycerides;
        @BindView(R.id.TextView_liver_function_test)
        TextView textViewLiverFunctionTest;
        @BindView(R.id.TextView_serum_bilirubin_total)
        TextView textViewSerumBilirubinTotal;
        @BindView(R.id.EditText_serum_bilirubin_total)
        EditText editTextSerumBilirubinTotal;
        @BindView(R.id.TextView_direct)
        TextView textViewDirect;
        @BindView(R.id.EditText_direct)
        EditText editTextDirect;
        @BindView(R.id.TextView_indirect)
        TextView textViewIndirect;
        @BindView(R.id.EditText_indirect)
        EditText editTextIndirect;
        @BindView(R.id.TextView_total_protein)
        TextView textViewTotalProtein;
        @BindView(R.id.EditText_total_protein)
        EditText editTextTotalProtein;
        @BindView(R.id.TextView_albumin)
        TextView textViewAlbumin;
        @BindView(R.id.EditText_albumin)
        EditText editTextAlbumin;
        @BindView(R.id.textView_globulin)
        TextView textViewGlobulin;
        @BindView(R.id.editText_globulin)
        EditText editTextGlobulin;
        @BindView(R.id.textView_sgot)
        TextView textViewSgot;
        @BindView(R.id.editText_sgot)
        EditText editTextSgot;
        @BindView(R.id.textView_sgpt)
        TextView textViewSgpt;
        @BindView(R.id.editText_sgpt)
        EditText editTextSgpt;
        @BindView(R.id.textView_alkaline_phosphatase)
        TextView textViewAlkalinePhosphatase;
        @BindView(R.id.editText_alkaline_phosphatase)
        EditText editTextAlkalinePhosphatase;
        @BindView(R.id.TextView_thyroid_profile)
        TextView textViewThyroidProfile;
        @BindView(R.id.TextView_free_t3)
        TextView textViewFreeT3;
        @BindView(R.id.EditText_free_t3)
        EditText editTextFreeT3;
        @BindView(R.id.TextView_free_t4)
        TextView textViewFreeT4;
        @BindView(R.id.EditText_free_t4)
        EditText editTextFreeT4;
        @BindView(R.id.TextView_tsh)
        TextView textViewTsh;
        @BindView(R.id.EditText_tsh)
        EditText editTextTsh;
        ImageButton arrowClinicalData;
        LinearLayout primaryClinicalDataLayout;
        Button hideClinicalinfo;

        LOAD_CLINICAL() {
            //10.CLINICAL DATA
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_clinicaldata, null);


            arrowClinicalData = current_view.findViewById(R.id.arrow_clinical_data);
            primaryClinicalDataLayout = current_view.findViewById(R.id.primary_clinical_data_layout);
            hideClinicalinfo = current_view.findViewById(R.id.hide_clinicalinfo);

            arrowClinicalData.setOnClickListener(view -> toggleSectionInput(arrowClinicalData, primaryClinicalDataLayout));

            hideClinicalinfo.setOnClickListener(view -> toggleSectionInput(arrowClinicalData, primaryClinicalDataLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


        }

    }

    public class LOAD_OTHERSYS {
        ImageButton arrowOtherSystems;
        LinearLayout primaryOtherSystemsLayout;
        EditText edtOtherSystems;
        EditText edtAdditionalInformation;
        Button hideOthersinfo;

        LOAD_OTHERSYS() {
            //13.OTHER SYSTEMS
            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_other_system, null);


            arrowOtherSystems = current_view.findViewById(R.id.arrow_other_systems);
            primaryOtherSystemsLayout = current_view.findViewById(R.id.primary_other_systems_layout);
            edtOtherSystems = current_view.findViewById(R.id.edt_other_systems);
            edtAdditionalInformation = current_view.findViewById(R.id.edt_additional_information);
            hideOthersinfo = current_view.findViewById(R.id.hide_othersinfo);

            arrowOtherSystems.setOnClickListener(view -> toggleSectionInput(arrowOtherSystems, primaryOtherSystemsLayout));

            hideOthersinfo.setOnClickListener(view -> toggleSectionInput(arrowOtherSystems, primaryOtherSystemsLayout));

            allCasenotesLayouts.addView(current_view);


            ButterKnife.bind(this, current_view);


        }

    }

    public class LOAD_DENTAL {

        @BindView(R.id.tooth_child_layout)
        LinearLayout toothChildLayout;
        @BindView(R.id.cchkbx_ur8)
        CheckBox cchkbxUr8;
        @BindView(R.id.cchkbx_ur7)
        CheckBox cchkbxUr7;
        @BindView(R.id.cchkbx_ur6)
        CheckBox cchkbxUr6;
        @BindView(R.id.cchkbx_ur5)
        CheckBox cchkbxUr5;
        @BindView(R.id.cchkbx_ur4)
        CheckBox cchkbxUr4;
        @BindView(R.id.cchkbx_ul1)
        CheckBox cchkbxUl1;
        @BindView(R.id.cchkbx_ul2)
        CheckBox cchkbxUl2;
        @BindView(R.id.cchkbx_ul3)
        CheckBox cchkbxUl3;
        @BindView(R.id.cchkbx_ul4)
        CheckBox cchkbxUl4;
        @BindView(R.id.cchkbx_ul5)
        CheckBox cchkbxUl5;
        @BindView(R.id.cchkbx_lr5)
        CheckBox cchkbxLr5;
        @BindView(R.id.cchkbx_lr4)
        CheckBox cchkbxLr4;
        @BindView(R.id.cchkbx_lr3)
        CheckBox cchkbxLr3;
        @BindView(R.id.cchkbx_lr2)
        CheckBox cchkbxLr2;
        @BindView(R.id.cchkbx_lr1)
        CheckBox cchkbxLr1;
        @BindView(R.id.cchkbx_ll1)
        CheckBox cchkbxLl1;
        @BindView(R.id.cchkbx_ll2)
        CheckBox cchkbxLl2;
        @BindView(R.id.cchkbx_ll3)
        CheckBox cchkbxLl3;
        @BindView(R.id.cchkbx_ll4)
        CheckBox cchkbxLl4;
        @BindView(R.id.cchkbx_ll5)
        CheckBox cchkbxLl5;

        @BindView(R.id.tooth_adult_layout)
        LinearLayout toothAdultLayout;
        @BindView(R.id.chkbx_ur8)
        CheckBox chkbxUr8;
        @BindView(R.id.chkbx_ur7)
        CheckBox chkbxUr7;
        @BindView(R.id.chkbx_ur6)
        CheckBox chkbxUr6;
        @BindView(R.id.chkbx_ur5)
        CheckBox chkbxUr5;
        @BindView(R.id.chkbx_ur4)
        CheckBox chkbxUr4;
        @BindView(R.id.chkbx_ur3)
        CheckBox chkbxUr3;
        @BindView(R.id.chkbx_ur2)
        CheckBox chkbxUr2;
        @BindView(R.id.chkbx_ur1)
        CheckBox chkbxUr1;
        @BindView(R.id.chkbx_ul1)
        CheckBox chkbxUl1;
        @BindView(R.id.chkbx_ul2)
        CheckBox chkbxUl2;
        @BindView(R.id.chkbx_ul3)
        CheckBox chkbxUl3;
        @BindView(R.id.chkbx_ul4)
        CheckBox chkbxUl4;
        @BindView(R.id.chkbx_ul5)
        CheckBox chkbxUl5;
        @BindView(R.id.chkbx_ul6)
        CheckBox chkbxUl6;
        @BindView(R.id.chkbx_ul7)
        CheckBox chkbxUl7;
        @BindView(R.id.chkbx_ul8)
        CheckBox chkbxUl8;
        @BindView(R.id.chkbx_lr8)
        CheckBox chkbxLr8;
        @BindView(R.id.chkbx_lr7)
        CheckBox chkbxLr7;
        @BindView(R.id.chkbx_lr6)
        CheckBox chkbxLr6;
        @BindView(R.id.chkbx_lr5)
        CheckBox chkbxLr5;
        @BindView(R.id.chkbx_lr4)
        CheckBox chkbxLr4;
        @BindView(R.id.chkbx_lr3)
        CheckBox chkbxLr3;
        @BindView(R.id.chkbx_lr2)
        CheckBox chkbxLr2;
        @BindView(R.id.chkbx_lr1)
        CheckBox chkbxLr1;
        @BindView(R.id.chkbx_ll1)
        CheckBox chkbxLl1;
        @BindView(R.id.chkbx_ll2)
        CheckBox chkbxLl2;
        @BindView(R.id.chkbx_ll3)
        CheckBox chkbxLl3;
        @BindView(R.id.chkbx_ll4)
        CheckBox chkbxLl4;
        @BindView(R.id.chkbx_ll5)
        CheckBox chkbxLl5;
        @BindView(R.id.chkbx_ll6)
        CheckBox chkbxLl6;
        @BindView(R.id.chkbx_ll7)
        CheckBox chkbxLl7;
        @BindView(R.id.chkbx_ll8)
        CheckBox chkbxLl8;

        ImageButton arrowDentalSystem;
        LinearLayout primaryDentalSystemLayout;
        Button hideDentalinfo;

        LOAD_DENTAL() {
            //12.DENTAL SYSTEM

            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_dental_system, null);

            arrowDentalSystem = current_view.findViewById(R.id.arrow_dental_system);
            primaryDentalSystemLayout = current_view.findViewById(R.id.primary_dental_system_layout);
            hideDentalinfo = current_view.findViewById(R.id.hide_dentalinfo);

            arrowDentalSystem.setOnClickListener(view -> toggleSectionInput(arrowDentalSystem, primaryDentalSystemLayout));

            hideDentalinfo.setOnClickListener(view -> toggleSectionInput(arrowDentalSystem, primaryDentalSystemLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            DentalCheckChangeListerners(cchkbxUr8);
            DentalCheckChangeListerners(cchkbxUr7);
            DentalCheckChangeListerners(cchkbxUr6);
            DentalCheckChangeListerners(cchkbxUr5);
            DentalCheckChangeListerners(cchkbxUr4);
            DentalCheckChangeListerners(cchkbxUl1);
            DentalCheckChangeListerners(cchkbxUl2);
            DentalCheckChangeListerners(cchkbxUl3);
            DentalCheckChangeListerners(cchkbxUl4);
            DentalCheckChangeListerners(cchkbxUl5);
            DentalCheckChangeListerners(cchkbxLr5);
            DentalCheckChangeListerners(cchkbxLr4);
            DentalCheckChangeListerners(cchkbxLr3);
            DentalCheckChangeListerners(cchkbxLr2);
            DentalCheckChangeListerners(cchkbxLr1);
            DentalCheckChangeListerners(cchkbxLl1);
            DentalCheckChangeListerners(cchkbxLl2);
            DentalCheckChangeListerners(cchkbxLl3);
            DentalCheckChangeListerners(cchkbxLl4);
            DentalCheckChangeListerners(cchkbxLl5);


            DentalCheckChangeListerners(chkbxUr8);
            DentalCheckChangeListerners(chkbxUr7);
            DentalCheckChangeListerners(chkbxUr6);
            DentalCheckChangeListerners(chkbxUr5);
            DentalCheckChangeListerners(chkbxUr4);
            DentalCheckChangeListerners(chkbxUr3);
            DentalCheckChangeListerners(chkbxUr2);
            DentalCheckChangeListerners(chkbxUr1);
            DentalCheckChangeListerners(chkbxUl1);
            DentalCheckChangeListerners(chkbxUl2);
            DentalCheckChangeListerners(chkbxUl3);
            DentalCheckChangeListerners(chkbxUl4);
            DentalCheckChangeListerners(chkbxUl5);
            DentalCheckChangeListerners(chkbxUl6);
            DentalCheckChangeListerners(chkbxUl7);
            DentalCheckChangeListerners(chkbxUl8);
            DentalCheckChangeListerners(chkbxLr8);
            DentalCheckChangeListerners(chkbxLr7);
            DentalCheckChangeListerners(chkbxLr6);
            DentalCheckChangeListerners(chkbxLr5);
            DentalCheckChangeListerners(chkbxLr4);
            DentalCheckChangeListerners(chkbxLr3);
            DentalCheckChangeListerners(chkbxLr2);
            DentalCheckChangeListerners(chkbxLr1);
            DentalCheckChangeListerners(chkbxLl1);
            DentalCheckChangeListerners(chkbxLl2);
            DentalCheckChangeListerners(chkbxLl3);
            DentalCheckChangeListerners(chkbxLl4);
            DentalCheckChangeListerners(chkbxLl5);
            DentalCheckChangeListerners(chkbxLl6);
            DentalCheckChangeListerners(chkbxLl7);
            DentalCheckChangeListerners(chkbxLl8);


        }


        public void DentalCheckChangeListerners(CheckBox chkbx) {
            try {

                chkbx.setOnCheckedChangeListener((compoundButton, b) -> {

                    if (compoundButton.isChecked()) {

                        compoundButton.setTextColor(CaseNotes.this.getResources().getColor(R.color.white));
                        compoundButton.setBackgroundColor(CaseNotes.this.getResources().getColor(R.color.red));

                    } else {
                        compoundButton.setTextColor(CaseNotes.this.getResources().getColor(R.color.ourcolour));
                        compoundButton.setBackgroundColor(CaseNotes.this.getResources().getColor(R.color.white));
                    }

                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }

    public class LOAD_PNC {

        //care of baby
        @BindView(R.id.edt_urine)
        EditText edtUrine;
        @BindView(R.id.edt_stool)
        EditText edtStool;
        @BindView(R.id.edt_diarrhea)
        EditText edtDiarrhea;
        @BindView(R.id.edt_vomit)
        EditText edtVomit;
        @BindView(R.id.edt_conv)
        EditText edtConv;
        @BindView(R.id.rad_grp_activity)
        RadioGroup radGrpActivity;
        @BindView(R.id.rad_btn_activity1)
        RadioButton radBtnActivity1;
        @BindView(R.id.rad_btn_activity2)
        RadioButton radBtnActivity2;
        @BindView(R.id.rad_grp_sucking)
        RadioGroup radGrpSucking;
        @BindView(R.id.rad_btn_sucking1)
        RadioButton radBtnSucking1;
        @BindView(R.id.rad_btn_sucking2)
        RadioButton radBtnSucking2;
        @BindView(R.id.rad_grp_breathing)
        RadioGroup radGrpBreathing;
        @BindView(R.id.rad_btn_breathing1)
        RadioButton radBtnBreathing1;
        @BindView(R.id.rad_btn_breathing2)
        RadioButton radBtnBreathing2;
        @BindView(R.id.rad_grp_chest)
        RadioGroup radGrpChest;
        @BindView(R.id.rad_btn_chest1)
        RadioButton radBtnChest1;
        @BindView(R.id.rad_btn_chest2)
        RadioButton radBtnChest2;
        @BindView(R.id.textView_temper)
        TextView textViewTemper;
        @BindView(R.id.edittext_temper)
        EditText edittextTemper;
        @BindView(R.id.textView_Jaundice)
        TextView textViewJaundice;
        @BindView(R.id.edittext_jaundice)
        EditText edittextJaundice;
        @BindView(R.id.textView_stump)
        TextView textViewStump;
        @BindView(R.id.edittext_stump)
        EditText edittextStump;
        @BindView(R.id.textView_Skin)
        TextView textViewSkin;
        @BindView(R.id.rad_grp_skin)
        RadioGroup radGrpSkin;
        @BindView(R.id.rad_btn_skin1)
        RadioButton radBtnSkin1;
        @BindView(R.id.rad_btn_skin2)
        RadioButton radBtnSkin2;
        @BindView(R.id.textView_Any_other_complications)
        TextView textViewAnyOtherComplications;
        @BindView(R.id.edittext__Any_other_complications)
        EditText edittextAnyOtherComplications;
        @BindView(R.id.textView_AnyComplaints)
        TextView textViewAnyComplaints;

        //post portum care
        @BindView(R.id.edittext_Any_Complaints)
        EditText edittextAnyComplaints;

        @BindView(R.id.textView_Pallor)
        TextView textViewPallor;
        @BindView(R.id.edittext_pallor)
        EditText edittextPallor;
        @BindView(R.id.textView_Pulse_rate)
        TextView textViewPulseRate;
        @BindView(R.id.edittext_pulse_rate)
        EditText edittextPulseRate;
        @BindView(R.id.textView_Bloodpressure)
        TextView textViewBloodpressure;
        @BindView(R.id.edittext_Bloodpressure)
        EditText edittextBloodpressure;
        @BindView(R.id.textView_Temperature)
        TextView textViewTemperature;
        @BindView(R.id.edittext_Temperature)
        EditText edittextTemperature;
        @BindView(R.id.textView_Breasts)
        TextView textViewBreasts;
        @BindView(R.id.rad_grp_breast)
        RadioGroup radGrpBreast;
        @BindView(R.id.rad_btn_breast1)
        RadioButton radBtnBreast1;
        @BindView(R.id.rad_btn_breast2)
        RadioButton radBtnBreast2;
        @BindView(R.id.textView_Nipples)
        TextView textViewNipples;
        @BindView(R.id.rad_grp_nipple)
        RadioGroup radGrpNipple;
        @BindView(R.id.rad_btn_nipple1)
        RadioButton radBtnNipple1;
        @BindView(R.id.rad_btn_nipple2)
        RadioButton radBtnNipple2;
        @BindView(R.id.textView_Uterus_Tenderness)
        TextView textViewUterusTenderness;
        @BindView(R.id.rad_grp_uterus)
        RadioGroup radGrpUterus;
        @BindView(R.id.rad_btn_uterus1)
        RadioButton radBtnUterus1;
        @BindView(R.id.rad_btn_uterus2)
        RadioButton radBtnUterus2;
        @BindView(R.id.textView_BleedingPV)
        TextView textViewBleedingPV;
        @BindView(R.id.rad_grp_bleed)
        RadioGroup radGrpBleed;
        @BindView(R.id.rad_btn_bleed1)
        RadioButton radBtnBleed1;
        @BindView(R.id.rad_btn_bleed2)
        RadioButton radBtnBleed2;
        @BindView(R.id.textView_Lochia)
        TextView textViewLochia;
        @BindView(R.id.rad_grp_lochia)
        RadioGroup radGrpLochia;
        @BindView(R.id.rad_btn_lochia1)
        RadioButton radBtnLochia1;
        @BindView(R.id.rad_btn_lochia2)
        RadioButton radBtnLochia2;
        @BindView(R.id.textView_Episiotomy_Tear)
        TextView textViewEpisiotomyTear;
        @BindView(R.id.rad_grp_tear)
        RadioGroup radGrpTear;
        @BindView(R.id.rad_btn_tear1)
        RadioButton radBtnTear1;
        @BindView(R.id.rad_btn_tear2)
        RadioButton radBtnTear2;
        @BindView(R.id.textView_Family_planning_counselling)
        TextView textViewFamilyPlanningCounselling;
        @BindView(R.id.editText_Family_planning_counselling)
        EditText editTextFamilyPlanningCounselling;
        @BindView(R.id.textView_other_complications)
        TextView textViewOtherComplications;
        @BindView(R.id.edittext_other_complications)
        EditText edittextOtherComplications;
        @BindView(R.id.textView_Date_of_delivery)
        TextView textViewDateOfDelivery;
        @BindView(R.id.editText__Date_of_delivery)
        EditText editTextDateOfDelivery;
        @BindView(R.id.textView_Place_of_delivery)
        TextView textViewPlaceOfDelivery;
        @BindView(R.id.edittext__Place_of_delivery)
        EditText edittextPlaceOfDelivery;
        @BindView(R.id.textView_Type_of_delivery)
        TextView textViewTypeOfDelivery;
        @BindView(R.id.chk_normal)
        CheckBox chkNormal;
        @BindView(R.id.chk_inst)
        CheckBox chkInst;
        @BindView(R.id.chk_cs)
        CheckBox chkCs;
        @BindView(R.id.textView_Term_Preterm)
        TextView textViewTermPreterm;
        @BindView(R.id.edittext_Term_Preterm)
        EditText edittextTermPreterm;
        @BindView(R.id.textView_postdelivery)
        TextView textViewPostdelivery;
        @BindView(R.id.edittext_post_delivery)
        EditText edittextPostDelivery;
        @BindView(R.id.textView_complications)
        TextView textViewComplications;
        @BindView(R.id.edittext_complications)
        EditText edittextComplications;
        @BindView(R.id.textView_sexofbaby)
        TextView textViewSexofbaby;
        @BindView(R.id.rad_grp__sexofbaby)
        RadioGroup radGrpSexofbaby;
        @BindView(R.id.rad_btn_male)
        RadioButton radBtnMale;
        @BindView(R.id.rad_btn_female)
        RadioButton radBtnFemale;
        @BindView(R.id.textView_Weight_of_Baby)
        TextView textViewWeightOfBaby;
        @BindView(R.id.edittext_kg)
        EditText edittextKg;
        @BindView(R.id.textView_kg)
        TextView textViewKg;
        @BindView(R.id.edittext_gms)
        EditText edittextGms;
        @BindView(R.id.textView_cried)
        TextView textViewCried;
        @BindView(R.id.rad_grp_cried)
        RadioGroup radGrpCried;
        @BindView(R.id.rad_cry_yes)
        RadioButton radCryYes;
        @BindView(R.id.rad_cry_no)
        RadioButton radCryNo;
        @BindView(R.id.textView_breast_feeding)
        TextView textViewBreastFeeding;
        @BindView(R.id.rad_grp_feed)
        RadioGroup radGrpFeed;
        @BindView(R.id.rad_feed_yes)
        RadioButton radFeedYes;
        @BindView(R.id.rad_feed_no)
        RadioButton radFeedNo;

        ImageButton arrowPnc;
        LinearLayout primaryPncLayout;
        Button hideGeneralinfo;

        LOAD_PNC() {
            //11.PNC SYSTEM

            View current_view = getLayoutInflater().inflate(R.layout.kdmc_layout_casenotes_pnc_system, null);

            arrowPnc = current_view.findViewById(R.id.arrow_pnc);
            primaryPncLayout = current_view.findViewById(R.id.primary_pnc_layout);
            hideGeneralinfo = current_view.findViewById(R.id.hide_generalinfo);

            arrowPnc.setOnClickListener(view -> toggleSectionInput(arrowPnc, primaryPncLayout));

            hideGeneralinfo.setOnClickListener(view -> toggleSectionInput(arrowPnc, primaryPncLayout));

            allCasenotesLayouts.addView(current_view);

            ButterKnife.bind(this, current_view);


            editTextDateOfDelivery.setOnClickListener(view -> Inpatient_Inputs.openDatePickerDialog(editTextDateOfDelivery));


        }

    }


//**************************************************************************************************
}//END
