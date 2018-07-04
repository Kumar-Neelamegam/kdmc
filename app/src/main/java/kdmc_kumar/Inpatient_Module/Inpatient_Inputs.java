package kdmc_kumar.Inpatient_Module;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.KDMCRecyclerAdapter;
import kdmc_kumar.Utilities_Others.ViewAnimation;


public class Inpatient_Inputs extends AppCompatActivity {

    private String patienname = "";
    private String patientagegen = "";

    public Inpatient_Inputs() {
    }

    // #######################################################################################################
    @BindView(R.id.clinicalinformation_parent_layout)
    CoordinatorLayout clinicalinformationParentLayout;
    @BindView(R.id.inpatiententry_nesetedscrollview)
    NestedScrollView inpatiententryNesetedscrollview;
    @BindView(R.id.upperlayout)
    LinearLayout upperlayout;
    @BindView(R.id.imgvw_patientphoto)
    CircleImageView imgvwPatientphoto;
    @BindView(R.id.tvw_agegender)
    TextView tvwAgegender;
    @BindView(R.id.txt_patid)
    TextView txtPatid;
    @BindView(R.id.textView12)
    TextView textView12;
    @BindView(R.id.icome_text)
    TextView icomeText;
    @BindView(R.id.caste_text)
    TextView casteText;
    @BindView(R.id.caretaker_text)
    TextView caretakerText;
    @BindView(R.id.ward_no)
    TextView wardNo;
    @BindView(R.id.room_no)
    TextView roomNo;
    @BindView(R.id.bed_no)
    TextView bedNo;
    @BindView(R.id.textview_ward)
    TextView textviewWard;
    @BindView(R.id.textview_roomnumber)
    TextView textviewRoomnumber;
    @BindView(R.id.textview_bednumber)
    TextView textviewBednumber;
    @BindView(R.id.textview_admitteddate)
    TextView textviewAdmitteddate;
    @BindView(R.id.edittext_inaptientchart_date)
    EditText edittextInaptientchartDate;
    @BindView(R.id.edittext_inaptientchart_time)
    EditText edittextInaptientchartTime;
    @BindView(R.id.edittext_inaptientchart_bpsytolic)
    EditText edittextInaptientchartBpsytolic;
    @BindView(R.id.edittext_inaptientchart_bpdiastolic)
    EditText edittextInaptientchartBpdiastolic;
    @BindView(R.id.edittext_inaptientchart_pulseperminute)
    EditText edittextInaptientchartPulseperminute;
    @BindView(R.id.spinner_inaptientchart_temperature)
    Spinner spinnerInaptientchartTemperature;
    @BindView(R.id.edittext_inaptientchart_resp)
    EditText edittextInaptientchartResp;
    @BindView(R.id.edittext_inaptientchart_spo2)
    EditText edittextInaptientchartSpo2;
    @BindView(R.id.edittext_inaptientchart_drugorder)
    EditText edittextInaptientchartDrugorder;
    @BindView(R.id.edittext_inaptientchart_nursing_instruction)
    EditText edittextInaptientchartNursingInstruction;
    @BindView(R.id.edittext_inaptientchart_oral)
    EditText edittextInaptientchartOral;
    @BindView(R.id.edittext_inaptientchart_fluids)
    EditText edittextInaptientchartFluids;
    @BindView(R.id.edittext_inaptientchart_rvles)
    EditText edittextInaptientchartRvles;
    @BindView(R.id.edittext_inaptientchart_motion)
    EditText edittextInaptientchartMotion;
    @BindView(R.id.edittext_inaptientchart_urine)
    EditText edittextInaptientchartUrine;
    @BindView(R.id.edittext_diabetichart_date)
    EditText edittextDiabetichartDate;
    @BindView(R.id.edittext_diabetichart_time)
    EditText edittextDiabetichartTime;
    @BindView(R.id.edittext_diabetichart_speciali_instruction)
    EditText edittextDiabetichartSpecialiInstruction;
    @BindView(R.id.autoCompleteTextView_diabetichart_urine_sugar)
    EditText autoCompleteTextViewDiabetichartUrineSugar;
    @BindView(R.id.edittext_diabetichart_lente)
    EditText edittextDiabetichartLente;
    @BindView(R.id.edittext_diabetichart_insulin_plain)
    EditText edittextDiabetichartInsulinPlain;
    @BindView(R.id.edittext_diabetichart_blood_sugar)
    EditText edittextDiabetichartBloodSugar;
    @BindView(R.id.edittext_diabetichart_ketone_bodies)
    EditText edittextDiabetichartKetoneBodies;
    @BindView(R.id.edittext_temperaturechart_date)
    EditText edittextTemperaturechartDate;
    @BindView(R.id.edittext_temperaturechart_time)
    EditText edittextTemperaturechartTime;
    @BindView(R.id.spinner_temperaturechart_selecttemperature)
    Spinner spinnerTemperaturechartSelecttemperature;
    @BindView(R.id.edittext_temperaturechart_visit_summary)
    EditText edittextTemperaturechartVisitSummary;
    @BindView(R.id.edittext_surgery_date)
    EditText edittextSurgeryDate;
    @BindView(R.id.edittext_surgery_preoperative_diagnosis)
    EditText edittextSurgeryPreoperativeDiagnosis;
    @BindView(R.id.editext_surgery_operativenotes)
    EditText editextSurgeryOperativenotes;
    @BindView(R.id.edittext_surgery_position)
    EditText edittextSurgeryPosition;
    @BindView(R.id.edittext_surgery_procedure)
    EditText edittextSurgeryProcedure;
    @BindView(R.id.edittext_surgery_closure)
    EditText edittextSurgeryClosure;
    @BindView(R.id.edittext_surgery_postoperative_diagnosis)
    EditText edittextSurgeryPostoperativeDiagnosis;
    @BindView(R.id.edittext_surgery_surgeon)
    EditText edittextSurgerySurgeon;
    @BindView(R.id.edittext_surgery_anaesthetist)
    EditText edittextSurgeryAnaesthetist;
    @BindView(R.id.edittext_surgery_asst)
    EditText edittextSurgeryAsst;
    @BindView(R.id.edittext_surgery_blood_loss)
    EditText edittextSurgeryBloodLoss;
    @BindView(R.id.edittext_surgery_histo_pathological)
    EditText edittextSurgeryHistoPathological;
    @BindView(R.id.edittext_surgery_post_op_instruction)
    EditText edittextSurgeryPostOpInstruction;
    @BindView(R.id.Spinner_medicalcase_record_undercareof)
    Spinner spinnerMedicalcaseRecordUndercareof;
    @BindView(R.id.edittext_medicalcase_record_student)
    EditText edittextMedicalcaseRecordStudent;
    @BindView(R.id.autocomplete_medicalcase_provisional_diagnosis)
    AutoCompleteTextView autocompleteMedicalcaseProvisionalDiagnosis;
    @BindView(R.id.edittext_medicalcase_final_diagnosis)
    EditText edittextMedicalcaseFinalDiagnosis;
    @BindView(R.id.spinner_medicalcase_select_result)
    Spinner spinnerMedicalcaseSelectResult;
    @BindView(R.id.edittext_medicalcase_date)
    EditText edittextMedicalcaseDate;
    @BindView(R.id.edittext_medicalcase_time)
    EditText edittextMedicalcaseTime;
    @BindView(R.id.edittext_medicalcase_clinicalnotes)
    EditText edittextMedicalcaseClinicalnotes;
    @BindView(R.id.edittext_medicalcase_treatement_and_diet)
    EditText edittextMedicalcaseTreatementAndDiet;


    @BindView(R.id.inpatientchart_primary)
    LinearLayout inpatientchart_primary;


    @BindView(R.id.diabetic_primary)
    LinearLayout diabetic_primary;

    @BindView(R.id.temperature_primary)
    LinearLayout temperature_primary;

    @BindView(R.id.surgery_primary)
    LinearLayout surgery_primary;

    @BindView(R.id.medicalcase_primary)
    LinearLayout medicalcase_primary;

    @BindView(R.id.recyler_View)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;

    // #######################################################################################################
    private String PATIENT_NAME = null;
    private String PATIENT_AGEGENDER = null;
    private String PATIENT_INCOME = null;
    private String PATIENT_CASTE = null;
    private String PATIENT_CARETAKER = null;
    private String PATIENT_ID = null;
    // #######################################################################################################
    ArrayList<CommonDataObjects.MedicalCaseRecords> medicalCaseRecords = new ArrayList<>();
    KDMCRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_inpatiententry);
        ButterKnife.bind(this);


        loadInpatientDetails();


    }


    private void loadInpatientDetails() {

        Bundle b = getIntent().getExtras();
        PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);
        PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');
        PATIENT_AGEGENDER = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');
        PATIENT_INCOME = BaseConfig.GetValues("select income as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');
        PATIENT_CASTE = BaseConfig.GetValues("select caste as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');
        PATIENT_CARETAKER = BaseConfig.GetValues("select caretaker as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');

        String ward_values = BaseConfig.CheckDBString(BaseConfig.getwardNameFromId(BaseConfig.GetValues("select wardno as ret_values from Patreg where Patid='" + PATIENT_ID + "'"), this));
        String room_values = BaseConfig.CheckDBString(BaseConfig.getRoomNameFromId(BaseConfig.GetValues("select roomno as ret_values from Patreg where Patid='" + PATIENT_ID + "'"), this));
        String bed_values = BaseConfig.CheckDBString(BaseConfig.getBedNameFromId(BaseConfig.GetValues("select bedno as ret_values from Patreg where Patid='" + PATIENT_ID + "'"), this));


        String admitDate = BaseConfig.GetValues("select admitdt as ret_values from Patreg where Patid='" + PATIENT_ID + '\'');


        try {
            if (admitDate.contains("T")) {
                admitDate = admitDate.split("T")[0];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        txtPatid.setText(PATIENT_ID);
        textView12.setText(PATIENT_NAME);
        tvwAgegender.setText(PATIENT_AGEGENDER);
        icomeText.setText(PATIENT_INCOME);
        casteText.setText(PATIENT_CASTE);
        caretakerText.setText(PATIENT_CARETAKER);
        wardNo.setText(ward_values);
        roomNo.setText(room_values);
        bedNo.setText(bed_values);
        textviewAdmitteddate.setText(admitDate);



        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        String arrs1[] = {"Select Under Care of", "Medical Officer", "Student"};
        String arrs[] = {"Select Result", "Cured", "Relieved", "Unrelieved", "Absconded", "Died"};
        ArrayAdapter cc = new ArrayAdapter<>(Inpatient_Inputs.this, android.R.layout.simple_spinner_dropdown_item, arrs1);
        spinnerMedicalcaseRecordUndercareof.setAdapter(cc);
        ArrayAdapter<String> aa = new ArrayAdapter<>(Inpatient_Inputs.this, android.R.layout.simple_spinner_dropdown_item, arrs);
        spinnerMedicalcaseSelectResult.setAdapter(aa);


        getUserDetails(tvwAgegender, imgvwPatientphoto);


        //Load Pickers
        edittextInaptientchartDate.setOnClickListener(view -> openDatePickerDialog(edittextInaptientchartDate));
        edittextInaptientchartTime.setOnClickListener(view -> openTimePickerDialog(edittextInaptientchartTime));
        edittextInaptientchartDate.setText(getDate());
        edittextInaptientchartTime.setText(getTime());

        edittextDiabetichartTime.setOnClickListener(view -> openTimePickerDialog(edittextDiabetichartTime));
        edittextDiabetichartDate.setOnClickListener(view -> openDatePickerDialog(edittextDiabetichartDate));
        edittextDiabetichartDate.setText(getDate());
        edittextDiabetichartTime.setText(getTime());

        edittextTemperaturechartTime.setOnClickListener(view -> openTimePickerDialog(edittextTemperaturechartTime));
        edittextTemperaturechartDate.setOnClickListener(view -> openDatePickerDialog(edittextTemperaturechartDate));
        edittextTemperaturechartDate.setText(getDate());
        edittextTemperaturechartTime.setText(getTime());

        edittextSurgeryDate.setOnClickListener(view -> openDatePickerDialog(edittextSurgeryDate));
        edittextSurgeryDate.setText(getDate());

        edittextMedicalcaseDate.setOnClickListener(view -> openDatePickerDialog(edittextMedicalcaseDate));
        edittextMedicalcaseTime.setOnClickListener(view -> openTimePickerDialog(edittextMedicalcaseTime));
        edittextMedicalcaseDate.setText(getDate());
        edittextMedicalcaseTime.setText(getTime());

        toolbarBack.setOnClickListener(view -> Inpatient_Inputs.this.finish());
        toolbarExit.setOnClickListener(view -> BaseConfig.ExitSweetDialog(Inpatient_Inputs.this, null));

        toolbarTitle.setText("Inpatient Entry");

    }

    public class MedicalCaseViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.date_mc)
        TextView dateMc;
        @BindView(R.id.clinical_notes_value)
        TextView clinicalNotesValue;
        @BindView(R.id.treatment_diet_value)
        TextView treatmentDietValue;

        @BindView(R.id.ic_delete)
        ImageView delete;

        public MedicalCaseViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void openTimePickerDialog(EditText editText) {



        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(editText.getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                // Set a variable to hold the current time AM PM Status
                // Initially we set the variable value to AM
                String status = "AM";

                if(hourOfDay > 11)
                {
                    // If the hour is greater than or equal to 12
                    // Then the current AM PM status is PM
                    status = "PM";
                }

                // Initialize a new variable to hold 12 hour format hour value
                int hour_of_12_hour_format;

                if(hourOfDay > 11){

                    // If the hour is greater than or equal to 12
                    // Then we subtract 12 from the hour to make it 12 hour format time
                    hour_of_12_hour_format = hourOfDay - 12;
                }
                else {
                    hour_of_12_hour_format = hourOfDay;
                }

                // Display the 12 hour format time in app interface
                editText.setText(hour_of_12_hour_format + " : " + minute + " : " + status);


            }
        }, hour, minute, false);

        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }


    public static void openDatePickerDialog(EditText editText) {

        Calendar myCalendar = Calendar.getInstance();
        @SuppressLint("DefaultLocale")
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            editText.setText(String.format("%d/%d/%d", myCalendar.get(Calendar.DAY_OF_MONTH), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.YEAR)));

        };

        new DatePickerDialog(editText.getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private final void getUserDetails(TextView patientname, ImageView PatientImage) {
        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery("select * from patreg where Patid='"+ PATIENT_ID + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    patientname.setText(String.format("%s-%s", c.getString(c.getColumnIndex("age")), c.getString(c.getColumnIndex("gender"))));

                    String Photo_Path = c.getString(c.getColumnIndex("PC"));

                    BaseConfig.LoadPatientImage(Photo_Path, PatientImage, 100);
                    this.patienname = c.getString(c.getColumnIndex("name"));
                    this.patientagegen = c.getString(c.getColumnIndex("age")) + '-' + c.getString(c.getColumnIndex("gender"));

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
    }


    private void toggleSectionInput(View view, View PrimaryLayout) {
        boolean show = toggleArrow(view);
        if (show) {
           // ViewAnimation.expand(PrimaryLayout, () -> Tools.nestedScrollTo(inpatiententryNesetedscrollview, PrimaryLayout));
            ViewAnimation.expand(PrimaryLayout);
        } else {
            ViewAnimation.collapse(PrimaryLayout);
        }
    }


    public boolean toggleArrow(View view) {
        if (view.getRotation() == 0) {
            view.animate().setDuration(200).rotation(180);
            return true;
        } else {
            view.animate().setDuration(200).rotation(0);
            return false;
        }
    }


    //#####################################################################//
    //#####################################################################//

    @OnClick(R.id.arrow_inpatient_chart)
    void onArrowInpatientChartClick() {
        toggleSectionInput(findViewById(R.id.arrow_inpatient_chart), inpatientchart_primary);
    }

    @OnClick(R.id.arrow_diabetic_chart)
    void onArrowDiabeticChartClick() {
        toggleSectionInput(findViewById(R.id.arrow_diabetic_chart), diabetic_primary);
    }

    @OnClick(R.id.arrow_temperature_chart)
    void onArrowTemperatureChartClick() {
        toggleSectionInput(findViewById(R.id.arrow_temperature_chart), temperature_primary);

    }

    @OnClick(R.id.arrow_surgery_record)
    void onArrowSurgeryRecordClick() {
        toggleSectionInput(findViewById(R.id.arrow_surgery_record), surgery_primary);
    }

    @OnClick(R.id.arrow_medicalcase_record)
    void onArrowMedicalcaseRecordClick() {
        toggleSectionInput(findViewById(R.id.arrow_medicalcase_record), medicalcase_primary);
    }


    //#####################################################################//
    //#####################################################################//

    public static int mYear;
    public static int mMonth;
    public static int mDay;
    public static int mcYear;
    public static int mcMonth;
    public static int mcDay;

    public String getDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public String getTime() {
        Date c = Calendar.getInstance().getTime();
        String time = c.getHours() + ":" + c.getMinutes();
        return time;
    }

    protected void SaveLocal(int id) {

        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);

            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
            Date date = new Date();
            String dttm = dateformt.format(date);

            String InpatientChartid = BaseConfig.Patent_Id;


            if (id == 1) {

                String bp_txt1, bp_txt2, pulse_txt, temp_txt, resp_txt, spo2_txt, nursing_txt, drugorder_txt, iporal_txt,
                        ipfluid_txt, oprives_txt, opmotion_txt, opurine_txt, ipseltime = "-";


                bp_txt1 = CheckEdittextIsEmpty(edittextInaptientchartBpsytolic);
                bp_txt2 = CheckEdittextIsEmpty(edittextInaptientchartBpdiastolic);
                pulse_txt = CheckEdittextIsEmpty(edittextInaptientchartPulseperminute);
                temp_txt = CheckEdittextIsEmpty(spinnerInaptientchartTemperature);
                resp_txt = CheckEdittextIsEmpty(edittextInaptientchartResp);
                spo2_txt = CheckEdittextIsEmpty(edittextInaptientchartSpo2);
                nursing_txt = CheckEdittextIsEmpty(edittextInaptientchartNursingInstruction);
                drugorder_txt = CheckEdittextIsEmpty(edittextInaptientchartDrugorder);
                iporal_txt = CheckEdittextIsEmpty(edittextInaptientchartOral);
                ipfluid_txt = CheckEdittextIsEmpty(edittextInaptientchartFluids);
                oprives_txt = CheckEdittextIsEmpty(edittextInaptientchartRvles);
                opmotion_txt = CheckEdittextIsEmpty(edittextInaptientchartMotion);
                opurine_txt = CheckEdittextIsEmpty(edittextInaptientchartUrine);
                ipseltime = CheckEdittextIsEmpty(edittextInaptientchartTime);


                ContentValues cv = new ContentValues();
                cv.put("DiagId", InpatientChartid);
                cv.put("patid", PATIENT_ID);
                cv.put("pname", PATIENT_NAME);
                cv.put("pagegen", textView12.getText().toString());
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", edittextInaptientchartDate.getText().toString());
                cv.put("doc_visit_time", ipseltime);
                cv.put("bp", bp_txt1);
                cv.put("bpd", bp_txt2);
                cv.put("pulse", pulse_txt);
                cv.put("temp", temp_txt.split(" ")[0].trim());
                cv.put("resp", resp_txt);
                cv.put("spo2", spo2_txt);
                cv.put("drugorder", drugorder_txt);
                cv.put("nursing_instr", nursing_txt);
                cv.put("ip_oral", iporal_txt);
                cv.put("ip_fluids", ipfluid_txt);
                cv.put("op_rvies", oprives_txt);
                cv.put("op_motion", opmotion_txt);
                cv.put("op_urine", opurine_txt);
                cv.put("dischargedon", "");
                cv.put("dischrg_dttime", "");
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_MainChart", null, cv);

                showSuccess();


                try {
                    edittextInaptientchartBpsytolic.setText("");
                    edittextInaptientchartDate.setText("");
                    edittextInaptientchartBpdiastolic.setText("");
                    edittextInaptientchartPulseperminute.setText("");
                    spinnerInaptientchartTemperature.setSelection(0);
                    edittextInaptientchartResp.setText("");
                    edittextInaptientchartSpo2.setText("");
                    edittextInaptientchartNursingInstruction.setText("");
                    edittextInaptientchartDrugorder.setText("");
                    edittextInaptientchartOral.setText("");
                    edittextInaptientchartFluids.setText("");
                    edittextInaptientchartRvles.setText("");
                    edittextInaptientchartMotion.setText("");
                    edittextInaptientchartUrine.setText("");
                    edittextInaptientchartTime.setText("");

                    toggleSectionInput(findViewById(R.id.arrow_inpatient_chart), inpatientchart_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (id == 2) {/////////////////////////////////////////////////////////////////////

                String urinesgr_txt, lente_txt, insulinplain_txt, bloodsgr_txt, ketone_txt, ipdiab_time = "-";


                urinesgr_txt = CheckEdittextIsEmpty(edittextInaptientchartUrine);
                lente_txt = CheckEdittextIsEmpty(edittextDiabetichartLente);
                insulinplain_txt = CheckEdittextIsEmpty(edittextDiabetichartInsulinPlain);
                bloodsgr_txt = CheckEdittextIsEmpty(edittextDiabetichartBloodSugar);
                ketone_txt = CheckEdittextIsEmpty(edittextDiabetichartKetoneBodies);
                ipdiab_time = CheckEdittextIsEmpty(edittextDiabetichartTime);


                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", PATIENT_ID);
                cv.put("pname", PATIENT_NAME);
                cv.put("pagegen", tvwAgegender.getText().toString());
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", edittextDiabetichartDate.getText().toString());
                cv.put("doc_visit_time", ipdiab_time);
                cv.put("spl_instr", edittextDiabetichartSpecialiInstruction.getText().toString());
                cv.put("urine_sugar", urinesgr_txt);
                cv.put("lente", lente_txt);
                cv.put("insulin_plain", insulinplain_txt);
                cv.put("blood_sugar", bloodsgr_txt);
                cv.put("ketone_bodies", ketone_txt);
                cv.put("room_no", "");
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_DiabeticChart", null, cv);

                //final String Update_Query = "update InpatientChartmvalue set minpcnum=minpcnum +1";
                //BaseConfig.SaveData(Update_Query);

                showSuccess();


                try {
                    edittextDiabetichartLente.setText("");
                    edittextDiabetichartInsulinPlain.setText("");
                    edittextDiabetichartBloodSugar.setText("");
                    edittextDiabetichartKetoneBodies.setText("");
                    edittextDiabetichartDate.setText("");
                    edittextDiabetichartTime.setText("");
                    edittextDiabetichartSpecialiInstruction.setText("");
                    autoCompleteTextViewDiabetichartUrineSugar.setText("");
                    toggleSectionInput(findViewById(R.id.arrow_diabetic_chart), diabetic_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (id == 3) {
                String temperature_values = "0", temperature_time = "";

                if (spinnerTemperaturechartSelecttemperature.getSelectedItemPosition() > 0) {
                    temperature_values = spinnerTemperaturechartSelecttemperature.getSelectedItem().toString();
                }


                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", PATIENT_ID);
                cv.put("pname", PATIENT_NAME);
                cv.put("pagegen", PATIENT_AGEGENDER);
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", edittextTemperaturechartDate.getText().toString());
                cv.put("doc_visit_time", edittextTemperaturechartTime.getText().toString());
                cv.put("temperature", temperature_values);
                cv.put("visitsummary", edittextTemperaturechartVisitSummary.getText().toString());
                cv.put("temptakentime", temperature_time);
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_TemperatureChart", null, cv);
                //#######################################################


                showSuccess();


                try {
                    edittextTemperaturechartDate.setText("");
                    edittextTemperaturechartTime.setText("");
                    edittextTemperaturechartVisitSummary.setText("");
                    spinnerTemperaturechartSelecttemperature.setSelection(0);
                    toggleSectionInput(findViewById(R.id.arrow_temperature_chart), temperature_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else if (id == 4) {

                //#######################################################
                ContentValues cv = new ContentValues();

                cv.put("DiagId", InpatientChartid);
                cv.put("patid", PATIENT_ID);
                cv.put("pname", PATIENT_NAME);
                cv.put("pagegen", PATIENT_AGEGENDER);
                cv.put("docid", BaseConfig.doctorId);
                cv.put("docname", BaseConfig.doctorname);
                cv.put("Actdate", dttm);
                cv.put("IsActive", "1");
                cv.put("Isupdate", "0");
                cv.put("doc_visit_date", edittextSurgeryDate.getText().toString());
                cv.put("doc_visit_time", BaseConfig.GetCurrentTime());
                cv.put("pre_operativediag", edittextSurgeryPreoperativeDiagnosis.getText().toString());
                cv.put("operative_notes", editextSurgeryOperativenotes.getText().toString());
                cv.put("position", edittextSurgeryPosition.getText().toString());
                cv.put("procedure", edittextSurgeryProcedure.getText().toString());
                cv.put("closure", edittextSurgeryClosure.getText().toString());
                cv.put("post_operativediag", edittextSurgeryPostoperativeDiagnosis.getText().toString());
                cv.put("surgeon", edittextSurgerySurgeon.getText().toString());
                cv.put("anaesthetist", edittextSurgeryAnaesthetist.getText().toString());
                cv.put("asst", edittextSurgeryAsst.getText().toString());
                cv.put("bloodloss", edittextSurgeryBloodLoss.getText().toString());
                cv.put("histopathological", edittextSurgeryHistoPathological.getText().toString());
                cv.put("post_op_instruct", edittextSurgeryPostOpInstruction.getText().toString());
                cv.put("HID", BaseConfig.HID);
                db.insert("Inpatient_SurgeryRecord", null, cv);

                showSuccess();

                try {
                    edittextSurgeryDate.setText("");
                    edittextSurgeryPreoperativeDiagnosis.setText("");
                    editextSurgeryOperativenotes.setText("");
                    edittextSurgeryPosition.setText("");
                    edittextSurgeryClosure.setText("");
                    edittextSurgeryPostoperativeDiagnosis.setText("");
                    edittextSurgerySurgeon.setText("");
                    edittextSurgeryAnaesthetist.setText("");
                    edittextSurgeryAsst.setText("");
                    edittextSurgeryBloodLoss.setText("");
                    edittextSurgeryHistoPathological.setText("");
                    edittextSurgeryPostOpInstruction.setText("");

                    edittextSurgeryProcedure.setText("");
                    toggleSectionInput(findViewById(R.id.arrow_surgery_record), surgery_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    boolean CheckInPatientValidation() {
        boolean ret = true;


        if (edittextInaptientchartTime.getText().toString().length() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (edittextInaptientchartBpsytolic.getText().length() == 0 && edittextInaptientchartBpdiastolic.getText().length() == 0
                && edittextInaptientchartPulseperminute.getText().toString().length() == 0 && edittextInaptientchartResp.getText().toString().length() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "At least input one numeric value", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (spinnerInaptientchartTemperature.getSelectedItemPosition() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "Please Select Temperature", Toast.LENGTH_LONG).show();
            ret = false;
        }


        return ret;
    }


    //region InpatientChart
    @OnClick(R.id.button_inaptientchart_clear)
    void onButtonInaptientchartClearClick() {
        edittextInaptientchartBpsytolic.setText("");
        edittextInaptientchartBpdiastolic.setText("");
        edittextInaptientchartPulseperminute.setText("");
        spinnerInaptientchartTemperature.setSelection(0);
        edittextInaptientchartResp.setText("");
        edittextInaptientchartSpo2.setText("");
        edittextInaptientchartNursingInstruction.setText("");
        edittextInaptientchartDrugorder.setText("");
        edittextInaptientchartOral.setText("");
        edittextInaptientchartFluids.setText("");
        edittextInaptientchartRvles.setText("");
        edittextInaptientchartMotion.setText("");
        edittextInaptientchartUrine.setText("");
        edittextInaptientchartTime.setText("");
        edittextInaptientchartDate.setText("");
    }


    @OnClick(R.id.button_inaptientchart_save)
    void onButtonInaptientchartSaveClick() {
        if (CheckInPatientValidation()) {


            SaveLocal(1);


        }

    }


    @OnClick(R.id.button_inaptientchart_hide)
    void onButtonInaptientchartHideClick() {
        try {
            toggleSectionInput(findViewById(R.id.arrow_inpatient_chart), inpatientchart_primary);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //endregion


    //region Diabetic Chart
    @OnClick(R.id.button_diabetichart_hide)
    void onButtonDiabetichartHideClick() {
        toggleSectionInput(findViewById(R.id.arrow_diabetic_chart), diabetic_primary);
    }

    @OnClick(R.id.button_diabetichart_clear)
    void onButtonDiabetichartClearClick() {
        try {
            edittextDiabetichartBloodSugar.setText("");
            edittextDiabetichartLente.setText("");
            edittextDiabetichartInsulinPlain.setText("");
            edittextDiabetichartBloodSugar.setText("");
            edittextDiabetichartKetoneBodies.setText("");
            edittextDiabetichartDate.setText("");
            edittextDiabetichartTime.setText("");
            edittextDiabetichartSpecialiInstruction.setText("");
            autoCompleteTextViewDiabetichartUrineSugar.setText("");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.button_diabetichart_save)
    void onButtonDiabetichartSaveClick() {
        if (CheckDiabeticChartValidation()) {
            SaveLocal(2);
        }
    }

    private boolean CheckDiabeticChartValidation() {
        boolean ret = true;
        if (edittextDiabetichartTime.getText().length() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }

        if (autoCompleteTextViewDiabetichartUrineSugar.getText().toString().length() == 0 && edittextDiabetichartLente.getText().toString().length() == 0 && edittextDiabetichartInsulinPlain.getText().toString().length() == 0
                && edittextDiabetichartBloodSugar.getText().toString().length() == 0 && edittextDiabetichartKetoneBodies.getText().toString().length() == 0) {

            Toast.makeText(Inpatient_Inputs.this, "At least input one numeric value", Toast.LENGTH_LONG).show();
            ret = false;

        }

        return ret;

    }
    //endregion


    //region Temperature Chart
    @OnClick(R.id.button_temperaturechart_hide)
    void onButtonTemperaturechartHideClick() {
        toggleSectionInput(findViewById(R.id.arrow_temperature_chart), temperature_primary);
    }


    @OnClick(R.id.button_temperaturechart_clear)
    void onButtonTemperaturechartClearClick() {

        try {
            edittextTemperaturechartDate.setText("");
            edittextTemperaturechartTime.setText("");
            edittextTemperaturechartVisitSummary.setText("");
            spinnerTemperaturechartSelecttemperature.setSelection(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.button_temperaturechart_save)
    void onButtonTemperaturechartSaveClick() {
        if (CheckTemperatureChartValidation()) {
            SaveLocal(3);
        }
    }

    private boolean CheckTemperatureChartValidation() {
        boolean ret = true;
        if (edittextTemperaturechartTime.getText().length() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "please select time", Toast.LENGTH_LONG).show();
            ret = false;
        }
        if (spinnerTemperaturechartSelecttemperature.getSelectedItemPosition() == 0) {
            Toast.makeText(Inpatient_Inputs.this, "please select temperature", Toast.LENGTH_LONG).show();
            ret = false;
        }
        return ret;
    }
    //endregion


    //region Surgery
    @OnClick(R.id.button_surgery_hide)
    void onButtonSurgeryHideClick() {
        toggleSectionInput(findViewById(R.id.arrow_surgery_record), surgery_primary);
    }

    @OnClick(R.id.button_surgery_clear)
    void onButtonSurgeryClearClick() {
        try {
            edittextSurgeryDate.setText("");
            edittextSurgeryPreoperativeDiagnosis.setText("");
            editextSurgeryOperativenotes.setText("");
            edittextSurgeryPosition.setText("");
            edittextSurgeryClosure.setText("");
            edittextSurgeryPostoperativeDiagnosis.setText("");
            edittextSurgerySurgeon.setText("");
            edittextSurgeryAnaesthetist.setText("");
            edittextSurgeryAsst.setText("");
            edittextSurgeryBloodLoss.setText("");
            edittextSurgeryHistoPathological.setText("");
            edittextSurgeryPostOpInstruction.setText("");

            edittextSurgeryProcedure.setText("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @OnClick(R.id.button_surgery_save)
    void onButtonSurgerySaveClick() {
        SaveLocal(4);
    }
    //endregion


    //region Medical Case Record
    @OnClick(R.id.button_medicalcase_add)
    void onButtonMedicalcaseAddClick() {


        if (edittextMedicalcaseDate.getText().toString().length() == 0) {
            edittextMedicalcaseDate.setFocusable(true);
            edittextMedicalcaseDate.setError("Enter");
        } else if (edittextMedicalcaseTime.getText().toString().length() == 0) {
            edittextMedicalcaseTime.setFocusable(true);
            edittextMedicalcaseTime.setError("Enter");

        } else if (edittextMedicalcaseClinicalnotes.getText().toString().length() == 0) {
            edittextMedicalcaseClinicalnotes.setFocusable(true);
            edittextMedicalcaseClinicalnotes.setError("Enter");
        } else if (edittextMedicalcaseTreatementAndDiet.getText().toString().length() == 0) {
            edittextMedicalcaseTreatementAndDiet.setFocusable(true);
            edittextMedicalcaseTreatementAndDiet.setError("Enter");
        } else {

            medicalCaseRecords.add(new CommonDataObjects.MedicalCaseRecords(edittextMedicalcaseDate.getText().toString(), edittextMedicalcaseTime.getText().toString(), edittextMedicalcaseClinicalnotes.getText().toString(), edittextMedicalcaseTreatementAndDiet.getText().toString()));

            edittextMedicalcaseDate.setText("");
            edittextMedicalcaseClinicalnotes.setText("");
            edittextMedicalcaseTreatementAndDiet.setText("");
            edittextMedicalcaseTime.setText("");

            adapter = new KDMCRecyclerAdapter<>(medicalCaseRecords, R.layout.each_medical_records)
                    .setRowItemView(new KDMCRecyclerAdapter.AdapterView() {
                        @Override
                        public Object setAdapterView(ViewGroup parent, int viewType, int layoutId) {
                            return new MedicalCaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false));
                        }

                        @Override
                        public void onBindView(Object holder, int position, Object data, List<Object> dataList) {

                            MedicalCaseViewHolder viewholder = (MedicalCaseViewHolder) holder;
                            CommonDataObjects.MedicalCaseRecords value = (CommonDataObjects.MedicalCaseRecords) data;

                            viewholder.clinicalNotesValue.setText(value.getClinical_notes());
                            viewholder.dateMc.setText(value.getDate());
                            viewholder.treatmentDietValue.setText(value.getTreatment_diet());

                            viewholder.delete.setOnClickListener(view ->
                                    new CustomKDMCDialog(view.getContext())
                                            .setLayoutColor(R.color.red_400)
                                            .setImage(R.drawable.ic_delete_forever_black_24dp)
                                            .setTitle("Remove")
                                            .setDescription("Do you want to Remove?")
                                            .setPossitiveButtonTitle("YES")
                                            .setNegativeButtonTitle("NO")
                                            .setOnPossitiveListener(() -> {
                                                adapter.delete(position);
                                                medicalCaseRecords.remove(position);
                                            }));
                        }
                    });

            recyclerView.setAdapter(adapter);
        }

    }


    @OnClick(R.id.button_medicalcase_hide)
    void onButtonMedicalcaseHideClick() {
        toggleSectionInput(findViewById(R.id.arrow_medicalcase_record), medicalcase_primary);
    }


    @OnClick(R.id.button_medicalcase_clear)
    void onButtonMedicalcaseClearClick() {
        edittextMedicalcaseDate.setText("");
        edittextMedicalcaseClinicalnotes.setText("");
        edittextMedicalcaseTreatementAndDiet.setText("");
        edittextMedicalcaseTime.setText("");
        spinnerMedicalcaseRecordUndercareof.setSelection(0);
        edittextMedicalcaseRecordStudent.setText("");
        autocompleteMedicalcaseProvisionalDiagnosis.setText("");
        edittextMedicalcaseFinalDiagnosis.setText("");
        spinnerMedicalcaseSelectResult.setSelection(0);

        try {
            //first clear the recycler view so items are not populated twice
            for (int i1 = 0; i1 < adapter.getSize(); i1++) {
                adapter.delete(i1);
            }
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick(R.id.button_medicalcase_save)
    void onButtonMedicalcaseSaveClick() {

        SQLiteDatabase db = BaseConfig.GetDb();//);

        if (spinnerMedicalcaseRecordUndercareof.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please enter Please enter Under care of....", Toast.LENGTH_LONG).show();
            spinnerMedicalcaseRecordUndercareof.setFocusable(true);
        } else if (edittextMedicalcaseRecordStudent.getText().toString().isEmpty()) {
            edittextMedicalcaseRecordStudent.setError("Please enter Student name....");
            edittextMedicalcaseRecordStudent.setFocusable(true);
        } else if (autocompleteMedicalcaseProvisionalDiagnosis.getText().toString().isEmpty()) {
            autocompleteMedicalcaseProvisionalDiagnosis.setError("Please enter  Provisional Diagnosis....");
            autocompleteMedicalcaseProvisionalDiagnosis.setFocusable(true);
        } else if (edittextMedicalcaseFinalDiagnosis.getText().toString().isEmpty()) {
            edittextMedicalcaseFinalDiagnosis.setError("Please enter  Final Diagnosis....");
            edittextMedicalcaseFinalDiagnosis.setFocusable(true);
        } else if (medicalCaseRecords.size() == 0) {

            Toast.makeText(this, "Please add medical case record item using Plus(+) button", Toast.LENGTH_LONG).show();

        } else {
            String Datetime;

            for (int i = 0; i <= medicalCaseRecords.size() - 1; i++) {

                String dateMc = medicalCaseRecords.get(i).getDate();
                String clinicalNotes = medicalCaseRecords.get(i).getClinical_notes();
                String treatmentDiet = medicalCaseRecords.get(i).getTreatment_diet();


                // TODO: 3/14/2017 Tochange DropDown Value Remove AM/PM

                Datetime = medicalCaseRecords.get(i).getTime();


                ContentValues cv = new ContentValues();

                cv.put("ActDate", BaseConfig.DeviceDate());
                cv.put("pat_id", PATIENT_ID);
                cv.put("doc_id", BaseConfig.doctorId);
                cv.put("result", spinnerMedicalcaseSelectResult.getSelectedItem().toString());
                cv.put("under_care_of", spinnerMedicalcaseRecordUndercareof.getSelectedItem().toString());
                cv.put("under_care_name", edittextMedicalcaseRecordStudent.getText().toString());
                cv.put("date", Datetime);
                cv.put("referred_by", BaseConfig.doctorname);
                cv.put("clinical_notes", clinicalNotes);
                cv.put("treatment_diet", treatmentDiet);
                cv.put("provisional_diag", autocompleteMedicalcaseProvisionalDiagnosis.getText().toString());
                cv.put("final_diag", edittextMedicalcaseFinalDiagnosis.getText().toString());
                cv.put("IsUpdate", "0");
                cv.put("IsActive", "1");
                cv.put("HID", BaseConfig.HID);
                db.insert("InPatient_MedicalCaseRecords", null, cv);


                showSuccess();

                try {
                    edittextMedicalcaseDate.setText("");
                    edittextMedicalcaseClinicalnotes.setText("");
                    edittextMedicalcaseTreatementAndDiet.setText("");
                    edittextMedicalcaseTime.setText("");
                    spinnerMedicalcaseRecordUndercareof.setSelection(0);
                    edittextMedicalcaseRecordStudent.setText("");
                    autocompleteMedicalcaseProvisionalDiagnosis.setText("");
                    edittextMedicalcaseFinalDiagnosis.setText("");
                    spinnerMedicalcaseSelectResult.setSelection(0);

                    try {
                        medicalCaseRecords = new ArrayList<>();
                        //first clear the recycler view so items are not populated twice
                        for (int i1 = 0; i1 < adapter.getSize(); i1++) {
                            adapter.delete(i1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    toggleSectionInput(findViewById(R.id.arrow_medicalcase_record), medicalcase_primary);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
        db.close();
    }

    private void showSuccess() {
        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getResources().getString(R.string.information))
                .setDescription("Successfully Inserted")
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle("OK");
    }
    //endregion


    // #######################################################################################################
    public String CheckEdittextIsEmpty(AutoCompleteTextView autotxt) {
        String chartvaluse = "0";

        if (!autotxt.getText().toString().equalsIgnoreCase("")) {

            chartvaluse = autotxt.getText().toString();

        }
        return chartvaluse;
    }

    public String CheckEdittextIsEmpty(EditText edtxt) {
        String chartvaluse = "0";

        if (!edtxt.getText().toString().equalsIgnoreCase("")) {

            chartvaluse = edtxt.getText().toString();

        }

        return chartvaluse;
    }

    public String CheckEdittextIsEmpty(Spinner edtxt) {
        String chartvaluse = "0";

        if (edtxt.getSelectedItemPosition() != 0) {

            chartvaluse = edtxt.getSelectedItem().toString();

        }

        return chartvaluse;
    }
    // #######################################################################################################
}