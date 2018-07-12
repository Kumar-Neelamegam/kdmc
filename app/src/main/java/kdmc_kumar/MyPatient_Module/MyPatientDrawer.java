package kdmc_kumar.MyPatient_Module;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.CaseNotes_Modules.CaseNotes;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Core_Modules.ClinicalInformation;
import kdmc_kumar.Core_Modules.Investigations;
import kdmc_kumar.Core_Modules.MedicinePrescription;
import kdmc_kumar.Core_Modules.MyPatient;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;

public class MyPatientDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    //Main layout
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.toolbar_mypatient)
    Toolbar toolbarMypatient;
    @BindView(R.id.txvw_title)
    TextView txvwTitle;
    @BindView(R.id.back_mp)
    AppCompatImageView backMp;
    @BindView(R.id.home_mp)
    AppCompatImageView homeMp;
    @BindView(R.id.exit_mp)
    AppCompatImageView exitMp;
    @BindView(R.id.nav_view)
    NavigationView navView;

    //frame-layout
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.back_drop)
    View backDrop;
    @BindView(R.id.lyt_inpatient)
    LinearLayout lytInpatient;
    @BindView(R.id.fab_admitinpatient)
    FloatingActionButton fabAdmitinpatient;
    @BindView(R.id.lyt_clinicalinfo)
    LinearLayout lytClinicalinfo;
    @BindView(R.id.fab_clinicalinfo)
    FloatingActionButton fabClinicalinfo;
    @BindView(R.id.lyt_casenotes)
    LinearLayout lytCasenotes;
    @BindView(R.id.fab_casenote)
    FloatingActionButton fabCasenote;
    @BindView(R.id.lyt_investigation)
    LinearLayout lytInvestigation;
    @BindView(R.id.fab_investigation)
    FloatingActionButton fabInvestigation;
    @BindView(R.id.lyt_Prescription)
    LinearLayout lytPrescription;
    @BindView(R.id.fab_Prescription)
    FloatingActionButton fabPrescription;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;


    //Drawer header
    ImageView profileImage;
    TextView patientName;
    TextView patientDetails;

    private Bundle b = null;
    private String BUNDLE_PATIENT_ID = "";
    private String FROM_TEST_REPORT = "False";
    private CharSequence[] items = null;

    private boolean rotate = false;

    public MyPatientDrawer() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patient_drawer);


        GETINITIALIZE();

        CONTROLLISTENERS();



    }


    public void GETINITIALIZE() {

        ButterKnife.bind(MyPatientDrawer.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        b = getIntent().getExtras();

        if (b != null) {

            BUNDLE_PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);
            FROM_TEST_REPORT = b.getString("TEST_REPORT");
        }

        ViewAnimation.initShowOut(lytInpatient);
        ViewAnimation.initShowOut(lytClinicalinfo);
        ViewAnimation.initShowOut(lytCasenotes);
        ViewAnimation.initShowOut(lytInvestigation);
        ViewAnimation.initShowOut(lytPrescription);

        backDrop.setVisibility(View.GONE);



        View headerview= navView.getHeaderView(0);

        setPatientDetails(headerview);

        if (FROM_TEST_REPORT != null && FROM_TEST_REPORT.equalsIgnoreCase("True")) {

            Fragment fragment = new Profile_Investigation();
            Bundle args = new Bundle();
            args.putString(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, "1");
            fragmentTransaction.commitAllowingStateLoss();
        } else {
            Fragment fragment = new Profile_Patient();
            Bundle args = new Bundle();
            args.putString(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
            fragment.setArguments(args);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, fragment, "1");
            fragmentTransaction.commitAllowingStateLoss();
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbarMypatient, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navView.setItemIconTintList(null);
        navView.setNavigationItemSelectedListener(this);

        boolean checkadmitreqstatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where enable_inpatient= '1' and Patid='" + BUNDLE_PATIENT_ID.trim() + '\'');

        if (checkadmitreqstatus) {
            lytInpatient.setVisibility(View.GONE);
        } else {
            lytInpatient.setVisibility(View.VISIBLE);
        }


    }


    public void CONTROLLISTENERS() {


        backDrop.setOnClickListener(v -> toggleFabMode(fabAdd));

        homeMp.setOnClickListener(v -> {

            MyPatientDrawer.this.finish();
            Intent intent1 = new Intent(MyPatientDrawer.this, Dashboard_NavigationMenu.class);
            startActivity(intent1);
        });

        exitMp.setOnClickListener(v -> BaseConfig.ExitSweetDialog(MyPatientDrawer.this, null));

        backMp.setOnClickListener(view -> LoadBack());

        fabAdd.setOnClickListener(this::toggleFabMode);

        fabAdmitinpatient.setOnClickListener(view -> ShowRequestDialog());

        fabClinicalinfo.setOnClickListener(view -> {

            Intent i = new Intent(MyPatientDrawer.this, ClinicalInformation.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_PATIENT_ID", BUNDLE_PATIENT_ID);
            startActivity(i);
            finish();
        });

        fabCasenote.setOnClickListener(view -> {
            Intent i = new Intent(MyPatientDrawer.this, CaseNotes.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", BUNDLE_PATIENT_ID);
            startActivity(i);
            finish();

        });

        fabInvestigation.setOnClickListener(view -> {

            Intent i = new Intent(MyPatientDrawer.this, Investigations.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", BUNDLE_PATIENT_ID);
            startActivity(i);
            finish();
        });

        fabPrescription.setOnClickListener(view -> {
            Intent i = new Intent(MyPatientDrawer.this, MedicinePrescription.class);
            i.putExtra("FROM", "MYPATIENT");
            i.putExtra("CONTINUE_STATUS", "True");
            i.putExtra("PASSING_TREATMENTFOR", "");
            i.putExtra("PASSING_DIAGNOSIS", "");
            i.putExtra("PASSING_PATIENT_ID", BUNDLE_PATIENT_ID);
            startActivity(i);
            finish();
        });


    }


    private final void LoadBack() {

        if (FROM_TEST_REPORT != null && FROM_TEST_REPORT.equalsIgnoreCase("True")) {
            BaseConfig.globalStartIntent(this, MyPatient.class, null);
        }else
        {
            this.finish();
        }

    }


    public final void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        LoadBack();
    }


    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lytInpatient);
            ViewAnimation.showIn(lytClinicalinfo);
            ViewAnimation.showIn(lytCasenotes);
            ViewAnimation.showIn(lytInvestigation);
            ViewAnimation.showIn(lytPrescription);
            backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lytInpatient);
            ViewAnimation.showOut(lytClinicalinfo);
            ViewAnimation.showOut(lytCasenotes);
            ViewAnimation.showOut(lytInvestigation);
            ViewAnimation.showOut(lytPrescription);
            backDrop.setVisibility(View.GONE);
        }
    }


    @Override
    public final boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Bundle args;

        switch (id) {

            case R.id.patient_profile:
                // TODO: 4/1/2017 change Patient  Profile Muthukumar Sir

                fragment = new Profile_Patient();

                break;

            case R.id.clinical_information:

                // TODO: 3/31/2017 Change New Web Page Ponnusamy M
                fragment = new Profile_ClinicalInformation();

                break;

            case R.id.casenotes:

                // TODO: 4/1/2017 Change  New Web page Mutu Kumar sir
//            fragment = new CasenotesWeb();
                fragment = new Profile_Casenotes();

                break;

            case R.id.investigation:
                fragment = new Profile_Investigation();

                break;
            case R.id.previous_precription:

                fragment = new Profile_Prescription();

                break;

            case R.id.reports:

                fragment = new Patient_Reports();

                break;

            case R.id.immunization:

                fragment = new Patient_Immunization();


                break;
            case R.id.inpatient_summary:
                fragment = new InpatientSummary_Profile();


                break;


            case R.id.pdf_summary:
                fragment = new PDFReport_Profile();
                break;


        }


        args = new Bundle();
        args.putString(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
        fragment.setArguments(args);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "1");
        fragmentTransaction.commitAllowingStateLoss();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);


        return true;
    }


    private final void setPatientDetails(View view) {

        profileImage = view.findViewById(R.id.profile_image_header);
        patientName = view.findViewById(R.id.patient_name_header);
        patientDetails = view.findViewById(R.id.patient_details_header);


        String Patient_Name1 = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
        String Patient_AgeGender1 = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
        String Patient_Photo1 = BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');

        if (Patient_Photo1.length() > 1) {
            BaseConfig.Glide_LoadImageView(profileImage, Patient_Photo1);


        } else {

            BaseConfig.Glide_LoadDefaultImageView(profileImage);
        }


        patientName.setText(String.valueOf(Patient_Name1 + '-' + BUNDLE_PATIENT_ID));
        patientDetails.setText(String.valueOf(Patient_AgeGender1));

    }

    private final void ShowRequestDialog() {


        try {
            if (BaseConfig.doctorId == null || BaseConfig.doctorId.equalsIgnoreCase("")) {
            //    BaseConfig.LoadDoctorValues();
            }


            View view = getLayoutInflater().inflate(R.layout.inpatient_request_layout, null);
            final AutoCompleteTextView patientAuto = view.findViewById(R.id.patient_detail_values);
            patientAuto.setThreshold(1);

            patientAuto.setText(BUNDLE_PATIENT_ID.trim());
            patientAuto.setFocusableInTouchMode(false);

            final EditText remarksEdt = view.findViewById(R.id.request_remarks_edt);
            Button submitBtn = view.findViewById(R.id.submit);
            Button cancelBtn = view.findViewById(R.id.cancel);
            Spinner WardCategory = view.findViewById(R.id.ward_category);

            TextView Title_PatientName, Title_WarCategory, Title_Remarks;
            Title_PatientName = view.findViewById(R.id.txtvw_patname);
            Title_WarCategory = view.findViewById(R.id.txtvw_wardcategory);
            Title_Remarks = view.findViewById(R.id.txtvw_remarks);

            String first2 = "Patient Id";
            String next2 = "<font color='#EE0000'><b>*</b></font>";
            Title_PatientName.setText(Html.fromHtml(first2 + next2));

            String first3 = "Choose ward category";
            String next3 = "<font color='#EE0000'><b>*</b></font>";
            Title_WarCategory.setText(Html.fromHtml(first3 + next3));

            String first4 = "Request Remarks";
            String next4 = "<font color='#EE0000'><b>*</b></font>";
            Title_Remarks.setText(Html.fromHtml(first4 + next4));

            BaseConfig.LoadValuesSpinner(WardCategory, MyPatientDrawer.this, "select distinct WardCatName as dvalue from Mstr_WardCategory where HID='" + BaseConfig.HID + "' order by ServerId;", "select ward category");


            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(view);
            submitBtn.setOnClickListener(v -> {

                if (Validation1.hasText(patientAuto)) {
                    if (WardCategory.getSelectedItemPosition() > 0) {
                        if (BUNDLE_PATIENT_ID.equalsIgnoreCase("")) {
                            Toast.makeText(MyPatientDrawer.this, R.string.valid_patient_details, Toast.LENGTH_LONG).show();
                        } else {

                            if (Validation1.hasText(remarksEdt)) {

                                //String patId = patientAuto.getText().toString().split("-")[1];
                                String selectedWard = WardCategory.getSelectedItem().toString();

                                ContentValues values = new ContentValues();
                                values.put("patid", BUNDLE_PATIENT_ID.trim());
                                values.put("request_remarks", remarksEdt.getText().toString());
                                values.put("WardCategory", selectedWard);
                                values.put("WardCategoryId", BaseConfig.GetValues("select ServerId as ret_values from Mstr_WardCategory where WardCatName='" + selectedWard + "' and HID='" + BaseConfig.HID + "'"));
                                values.put("IsActive", "1");
                                values.put("IsUpdate", "0");
                                values.put("docid", BaseConfig.doctorId);
                                values.put("ActDate", BaseConfig.DeviceDate());

                                SQLiteDatabase Db = BaseConfig.GetDb();//);
                                Db.insert("inpatient_request", null, values);
                                dialog.dismiss();

                                ShowSuccessFullyPlacedRequest(submitBtn);

                            } else {
                                BaseConfig.SnackBar(submitBtn.getContext(), "Enter remarks", submitBtn, 2);
                            }

                        }
                    } else {
                        BaseConfig.SnackBar(submitBtn.getContext(), getString(R.string.choose_ward), submitBtn, 2);
                    }
                } else {
                    BaseConfig.SnackBar(submitBtn.getContext(), getString(R.string.pl_select_patient), submitBtn, 2);
                }


            });
            cancelBtn.setOnClickListener(v -> dialog.dismiss());

            //dialog.getWindow().setLayout(350, 500);
            DisplayMetrics metrics = getResources().getDisplayMetrics();
            int width = metrics.widthPixels;
            int height = metrics.heightPixels;
            dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

            dialog.show();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private final void ShowSuccessFullyPlacedRequest(View v) {

        new CustomKDMCDialog(v.getContext())
                .setLayoutColor(R.color.green_500).setNegativeButtonVisible(View.GONE)
                .setImage(R.drawable.ic_success_done)
                .setTitle(v.getContext().getString(R.string.title_inpatient))
                .setDescription(v.getContext().getString(R.string.request_placed))
                .setPossitiveButtonTitle(v.getContext().getString(R.string.ok));


    }


}//End