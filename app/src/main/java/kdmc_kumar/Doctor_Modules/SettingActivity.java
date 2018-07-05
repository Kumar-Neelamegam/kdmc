package kdmc_kumar.Doctor_Modules;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.atzcx.appverupdater.AppVerUpdater;
import com.github.atzcx.appverupdater.UpdateErrors;
import com.github.atzcx.appverupdater.callback.Callback;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.LocalSharedPref;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.toggle.LabeledSwitch;
import kdmc_kumar.Utilities_Others.toggle.OnToggledListener;
import kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS;

public class SettingActivity extends AppCompatActivity {

    private static ProgressDialog pDialog;
    private final BaseConfig bcnfg = new BaseConfig();
    @BindView(R.id.language_radio)
    RadioGroup languageGroup;
    @BindView(R.id.marathiCheck)
    RadioButton marathiCheck;
    @BindView(R.id.englishCheck)
    RadioButton englishCheck;
    @BindView(R.id.setttings_parent_layout)
    CoordinatorLayout setttingsParentLayout;
    @BindView(R.id.toolbar)
    Toolbar settingsToolbar;
    @BindView(R.id.ic_back)
    AppCompatImageView icBack;
    @BindView(R.id.txvw_title)
    TextView txvwTitle;
    @BindView(R.id.ic_changepassword)
    AppCompatImageView icChngpwd;
    @BindView(R.id.ic_exit)
    AppCompatImageView icExit;
    @BindView(R.id.settings_nesetedscroll)
    NestedScrollView settingsNesetedscroll;
    @BindView(R.id.settings_cardlayout1)
    LinearLayout settingsCardlayout1;
    @BindView(R.id.settings_chkbx_pinnumber)
    CheckBox settingsChkbxPinnumber;
    @BindView(R.id.settings_edtxt_pinnumber)
    EditText settingsEdtxtPinnumber;
    @BindView(R.id.checkbox_casenotes_generalexamination)
    CheckBox checkboxCasenotesGeneralexamination;
    @BindView(R.id.checkbox_casenotes_cardio)
    CheckBox checkboxCasenotesCardio;
    @BindView(R.id.checkbox_casenotes_respiratory)
    CheckBox checkboxCasenotesRespiratory;
    @BindView(R.id.checkbox_casenotes_gastrointestinal)
    CheckBox checkboxCasenotesGastrointestinal;
    @BindView(R.id.checkbox_casenotes_neurology)
    CheckBox checkboxCasenotesNeurology;
    @BindView(R.id.checkbox_casenotes_locomotor)
    CheckBox checkboxCasenotesLocomotor;
    @BindView(R.id.checkbox_casenotes_renal)
    CheckBox checkboxCasenotesRenal;
    @BindView(R.id.checkbox_casenotes_endocrine)
    CheckBox checkboxCasenotesEndocrine;
    @BindView(R.id.checkbox_casenotes_clinical)
    CheckBox checkboxCasenotesClinical;
    @BindView(R.id.checkbox_casenotes_postnatal)
    CheckBox checkboxCasenotesPostnatal;
    @BindView(R.id.checkbox_casenotes_dental)
    CheckBox checkboxCasenotesDental;
    @BindView(R.id.checkbox_casenotes_other)
    CheckBox checkboxCasenotesOther;
    @BindView(R.id.settings_txtvw_workingdays)
    TextView settingsTxtvwWorkingdays;
    @BindView(R.id.editText_workingdays)
    EditText editTextWorkingdays;
    @BindView(R.id.textview_mrngtime_view)
    EditText textviewMrngtimeView;
    @BindView(R.id.edittext_evengtime_view)
    EditText edittextEvengtimeView;
    @BindView(R.id.editText_slot)
    EditText editTextSlot;
    @BindView(R.id.layout_appointment_noofpatients)
    LinearLayout Appointment_Slot;
    @BindView(R.id.textView_preferredpharmacy)
    TextView textViewPreferredpharmacy;
    @BindView(R.id.autocomplete_prefpharmacy)
    AutoCompleteTextView autocompletePrefpharmacy;
    @BindView(R.id.prefpharmacy_address)
    AutoCompleteTextView prefpharmacyAddress;
    @BindView(R.id.pref_labs)
    AutoCompleteTextView prefLabs;
    @BindView(R.id.autocomplete_pref_labs_address)
    AutoCompleteTextView autocompletePrefLabsAddress;
    @BindView(R.id.edittext_letter_space_head)
    EditText edittextLetterSpaceHead;
    @BindView(R.id.textvw_pharmacydetails)
    TextView textvw_pharmacyname;
    @BindView(R.id.textvw_laboratorydetails)
    TextView textvw_labname;
    @BindView(R.id.button_add)
    Button Add_WorkingDays;
    @BindView(R.id.button_cancel)
    Button Cancel;
    @BindView(R.id.button_submit)
    Button Submit;
    @BindView(R.id.check_app_update)
    TextView CheckAppUpdate;
    @BindView(R.id.server_info)
    TextView ServerInfo;
    private String STR_GENERALEXAMINATION = null;// = getResources().getString(R.string.general_examination);
    private String STR_CARDIOSYSTEM = null;// = getResources().getString(R.string.cardio_vascular_system);
    private String STR_RESPIRATORYSYSTEM = null;// = getResources().getString(R.string.respiratory_system);
    private String STR_GASTROSYSTEM = null;// = getResources().getString(R.string.gastrointestinal_system);
    private String STR_NEUROLOGY = null;// = getResources().getString(R.string.neurology_system);
    private String STR_LOCOMOTORSYSTEM = null;// = getResources().getString(R.string.locomotor_system);
    private String STR_RENLSYSTEM = null;// = getResources().getString(R.string.renal_system);
    private String STR_ENDOCRINESYSTEM = null;// = getResources().getString(R.string.endocrine_system);
    private String STR_CLINICAL = null;// = getResources().getString(R.string.clinical_informaiton);
    private String STR_OTHERS = null;// = getResources().getString(R.string.other_systems);
    private String STR_DENTAL = null;// = getResources().getString(R.string.dental_system);
    private String STR_PNC = null;// = getResources().getString(R.string.pnc_system);
    private String smstgltxt = null;
    //private String consul_feetxt = null;
    //private String appntschdltxt = null;
    //private String collectreprttxt = null;
    //private String labpahrcounttxt = null;
    //private String enableappointmenttxt = null;
    //private String noofpatientcount = null;
    private LabeledSwitch switchAppointments = null;
    private LabeledSwitch switchDailyAppointmentSMS = null;
    private LabeledSwitch switchReferralPharmacy = null;
    private LabeledSwitch switchCheckupdate = null;

    private String mLanguageCode_marathi = "mr";
    private String mLanguageCode_english = "en";

    //**********************************************************************************************
    private AppVerUpdater appVerUpdater;
    //**********************************************************************************************
    private String ApkName;

    public SettingActivity() {
    }

    //**********************************************************************************************

    //*******************************************************************************************
    private static final boolean contains(String[] arr, String item) {

        for (String n : arr) {
            if (item.equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    // //////////////////////////////////////////////////////////////////
    public static final void LoadContime(AutoCompleteTextView mrng1) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select hrs from contime order by hrs;", null);

        List<String> list = new ArrayList<>();
        // list.add("Select State");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String hrs = c.getString(c.getColumnIndex("hrs"));
                    list.add(hrs);

                } while (c.moveToNext());
            }
        }


        // BaseConfig.spinner2meth(context, list, mrng1);
        BaseConfig.loadSpinner(mrng1, list);

        db.close();
        c.close();
    }

    private static boolean checkValidation1(AutoCompleteTextView mrngfrom, AutoCompleteTextView mrngto, AutoCompleteTextView evefrom, AutoCompleteTextView eveto) {
        boolean ret = true;


        if (!Validation1.hasText(mrngfrom)) ret = false;

        if (!Validation1.hasText(mrngto)) ret = false;

        if (!Validation1.hasText(evefrom)) ret = false;

        if (!Validation1.hasText(eveto)) ret = false;


        return ret;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_settings_activity);

        try {



            GETINITIALIZE();

            CONTROLLISTENERS();

            LOADSETTINGSPIN();

            //      BaseConfig.LoadDoctorValues();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    Locale myLocale;


    public void setLocale(String lang)
    {
        BaseConfig.CurrentLangauges=lang;
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;

        onConfigurationChanged(conf);

        res.updateConfiguration(conf, dm);


        Intent refresh = new Intent(this, Dashboard_NavigationMenu.class);
        startActivity(refresh);
        this.finish();
    }



    private void CONTROLLISTENERS() {

        CheckAppUpdate.setOnClickListener(v -> {
            if (BaseConfig.server_connectivity_status) {
                new LoginBackgroundProcesses().execute();
            } else {
                BaseConfig.SnackBar(SettingActivity.this, "No server connectivity available..\ntry later..", setttingsParentLayout, 1);
            }
        });

        ServerInfo.setOnClickListener(view -> {

            StringBuilder str = new StringBuilder();
            str.append("Appname: " + BaseConfig.Appname);
            str.append("\n\nAppLogo: " + BaseConfig.AppLogo);
            str.append("\n\nAppLanguage: " + BaseConfig.AppLanguage);
            str.append("\n\nAppWebServiceType: " + BaseConfig.AppWebServiceType);
            str.append("\n\nAppNodeIP: " + BaseConfig.AppNodeIP);
            str.append("\n\nAppImagephpURL: " + BaseConfig.AppImagephpURL);
            str.append("\n\nAppDBFolderName: " + BaseConfig.AppDBFolderName);
            str.append("\n\nAppDatabaseName: " + BaseConfig.AppDatabaseName);
            str.append("\n\nAppDirectoryPictures: " + BaseConfig.AppDirectoryPictures);
            str.append("\n\nAppUpdateJson: " + BaseConfig.AppUpdateJson);


            new CustomKDMCDialog(SettingActivity.this)
                    .setLayoutColor(R.color.green_500)
                    .setImage(R.drawable.ic_server_info)
                    .setTitle(SettingActivity.this.getString(R.string.information))
                    .setNegativeButtonVisible(View.GONE)
                    .setOnPossitiveListener(() -> {

                    })
                    .setDescription(str.toString())
                    .setPossitiveButtonTitle(SettingActivity.this.getString(R.string.ok));


        });


        settingsChkbxPinnumber.setOnCheckedChangeListener((arg0, arg1) -> {

            if (settingsChkbxPinnumber.isChecked()) {
                String first2 = getString(R.string.nebale_pin_no);
                String next2 = "<font color='#EE0000'><b>*</b></font>";
                settingsChkbxPinnumber.setText(Html.fromHtml(first2 + next2));

                settingsEdtxtPinnumber.setEnabled(true);
                settingsEdtxtPinnumber.requestFocus();

            } else {
                String first2 = getString(R.string.nebale_pin_no);
                String next2 = "<font color='#EE0000'><b></b></font>";
                settingsChkbxPinnumber.setText(Html.fromHtml(first2 + next2));

                settingsEdtxtPinnumber.setEnabled(false);
            }

        });

        Add_WorkingDays.setOnClickListener(this::LoadWorkingDays);

        icChngpwd.setOnClickListener(view -> {

            SettingActivity.this.finish();
            Intent intent = new Intent(getApplicationContext(), ChangePassword.class);
            startActivity(intent);

        });

        icExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(SettingActivity.this, null));


        icBack.setOnClickListener(view -> LoadBack());


        LocalSharedPref localSharedPref = new LocalSharedPref(this);
        String LanguageCode=localSharedPref.getValue("LocaleLanguage");

        if (!LanguageCode.equalsIgnoreCase("")&&!LanguageCode.isEmpty()) {
            if (localSharedPref.getValue("LocaleLanguage").contentEquals("mr")) {
                marathiCheck.setChecked(true);
            }else
            {
                englishCheck.setChecked(true);
            }
        }


        
      /*  if (LocaleHelper.getLanguage(this).equalsIgnoreCase("en")) {
            englishCheck.setChecked(true);
            BaseConfig.CurrentLangauges=mLanguageCode_english;
        } else if (LocaleHelper.getLanguage(this).equalsIgnoreCase("mr")) {
            marathiCheck.setChecked(true);
            BaseConfig.CurrentLangauges=mLanguageCode_marathi;
        }

        if (BaseConfig.CurrentLangauges.equalsIgnoreCase("mr")) {

            marathiCheck.setChecked(true);
        } else {
            englishCheck.setChecked(true);
        }*/


        languageGroup.setOnCheckedChangeListener((group, checkedId) -> {

            if (marathiCheck.isChecked()) {

                //LocaleHelper.setLocale(SettingActivity.this, mLanguageCode_marathi);
                BaseConfig.CurrentLangauges=mLanguageCode_marathi;
                setLocale(mLanguageCode_marathi);
                localSharedPref.setValue("LocaleLanguage","mr");
            } else {

                setLocale(mLanguageCode_english);
               // LocaleHelper.setLocale(SettingActivity.this, mLanguageCode_english);
                BaseConfig.CurrentLangauges=mLanguageCode_english;
                localSharedPref.setValue("LocaleLanguage","en");
            }

        });


        // ///////////////////////////////////////////////////////////////////////////////

        autocompletePrefpharmacy.setOnItemClickListener((arg0, arg1, arg2, arg3) -> SelectedPharmacyAddress());
        prefLabs.setOnItemClickListener((arg0, arg1, arg2, arg3) -> SelectedLaboratoryAddress());

        Submit.setOnClickListener(v -> {

            try {
                Savelocal();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        });

        Cancel.setOnClickListener(v -> {
            SettingActivity.this.finish();
            Intent intent = new Intent(SettingActivity.this, Dashboard_NavigationMenu.class);
            startActivity(intent);

        });

        switchAppointments.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(LabeledSwitch labeledSwitch, boolean isOn) {
                if (switchAppointments.isOn()) {

                    Appointment_Slot.setVisibility(View.VISIBLE);

                } else {

                    Appointment_Slot.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (BaseConfig.CurrentLangauges != null){

            Locale myLocale = new Locale(BaseConfig.CurrentLangauges);
            newConfig.locale = myLocale;
            Locale.setDefault(myLocale);
            getBaseContext().getResources().updateConfiguration(newConfig, getBaseContext().getResources().getDisplayMetrics());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private final void LoadWorkingDays(View v) {
        String[] loadtime = {"9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30"};

        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        View promptView = layoutInflater.inflate(R.layout.workngdays_popup_new, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
        alertDialogBuilder.setView(promptView);


        final CheckBox chk1 = promptView.findViewById(R.id.checkBox_Monday);
        final CheckBox chk2 = promptView.findViewById(R.id.checkBox_Tuesday);
        final CheckBox chk3 = promptView.findViewById(R.id.checkBox_wednesday);
        final CheckBox chk4 = promptView.findViewById(R.id.checkBox_thursday);
        final CheckBox chk5 = promptView.findViewById(R.id.checkBox_friday);
        final CheckBox chk6 = promptView.findViewById(R.id.checkBox_saturday);
        final CheckBox chk7 = promptView.findViewById(R.id.checkBox_sunday);

        final Button save = promptView.findViewById(R.id.button_save);
        final Button cancel = promptView.findViewById(R.id.button_cancel);

        save.setText(getResources().getString(R.string.save));


        final AutoCompleteTextView mrngfrom = promptView.findViewById(R.id.AutoCompleteTextView_MorningFrom);
        mrngfrom.setThreshold(1);
        final AutoCompleteTextView mrngto = promptView.findViewById(R.id.AutoCompleteTextView_MorningTo);
        mrngto.setThreshold(1);
        final AutoCompleteTextView evefrom = promptView.findViewById(R.id.AutoCompleteTextView__EveningFrom);
        evefrom.setThreshold(1);
        final AutoCompleteTextView eveto = promptView.findViewById(R.id.AutoCompleteTextView_EveningTo);
        eveto.setThreshold(1);

        ArrayAdapter<String> listadapter = new ArrayAdapter<>(SettingActivity.this, android.R.layout.simple_list_item_1, loadtime);
        mrngfrom.setAdapter(listadapter);
        mrngto.setAdapter(listadapter);
        evefrom.setAdapter(listadapter);
        eveto.setAdapter(listadapter);


        // create alert dialog
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();


        mrngfrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        mrngto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        evefrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });
        eveto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int i, long l) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // TODO Auto-generated method stub
                InputMethodManager imm = (InputMethodManager) getSystemService(
                        INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });


        //loading popup values
        LoadpopupTime(chk1, chk2, chk3, chk4, chk5, chk6, chk7, mrngfrom, mrngto, evefrom, eveto);


        save.setOnClickListener(v12 -> {
            // TODO Auto-generated method stub
            Integer m1 = null, m2 = null, e1 = null, e2 = null;


            if (checkValidation1(mrngfrom, mrngto, evefrom, eveto)) {

                Integer m11 = Integer.valueOf(Integer.parseInt(mrngfrom.getText().toString().replace(":", "")));
                Integer m21 = Integer.valueOf(Integer.parseInt(mrngto.getText().toString().replace(":", "")));

                Integer e11 = Integer.valueOf(Integer.parseInt(evefrom.getText().toString().replace(":", "")));
                Integer e21 = Integer.valueOf(Integer.parseInt(eveto.getText().toString().replace(":", "")));

                if (m21.intValue() > m11.intValue()) {
                    if (e21.intValue() > e11.intValue()) {
                        editTextWorkingdays.setText(null);
                        if (chk1.isChecked()) {
                            String Mon = chk1.getText()
                                    .toString();
                            if (editTextWorkingdays.getText().length() > 0) {
                                editTextWorkingdays.setText(',' + Mon);
                            } else {
                                editTextWorkingdays.setText(Mon);
                            }

                        }
                        if (chk2.isChecked()) {
                            String Tue = chk2.getText()
                                    .toString();
                            if (editTextWorkingdays.getText()
                                    .length() > 0) {
                                editTextWorkingdays.append(',' + Tue);
                            } else {
                                editTextWorkingdays.append(Tue);
                            }

                        }
                        if (chk3.isChecked()) {
                            String Wed = chk3.getText().toString();
                            if (editTextWorkingdays.getText().length() > 0) {
                                editTextWorkingdays.append(',' + Wed);
                            } else {
                                editTextWorkingdays.append(Wed);
                            }

                        }
                        if (chk4.isChecked()) {
                            String Thur = chk4.getText()
                                    .toString();
                            if (editTextWorkingdays.getText()
                                    .length() > 0) {
                                editTextWorkingdays
                                        .append(',' + Thur);
                            } else {
                                editTextWorkingdays.append(Thur);
                            }

                        }
                        if (chk5.isChecked()) {
                            String Fri = chk5.getText()
                                    .toString();
                            if (editTextWorkingdays.getText()
                                    .length() > 0) {
                                editTextWorkingdays.append(',' + Fri);
                            } else {
                                editTextWorkingdays.append(Fri);
                            }

                        }
                        if (chk6.isChecked()) {
                            String Sat = chk6.getText()
                                    .toString();
                            if (editTextWorkingdays.getText()
                                    .length() > 0) {
                                editTextWorkingdays.append(',' + Sat);
                            } else {
                                editTextWorkingdays.append(Sat);
                            }

                        }
                        if (chk7.isChecked()) {
                            String Sun = chk7.getText()
                                    .toString();
                            if (editTextWorkingdays.getText()
                                    .length() > 0) {
                                editTextWorkingdays.append(',' + Sun);
                            } else {
                                editTextWorkingdays.append(Sun);
                            }

                        }

                        final String mrngtime = ("From " + mrngfrom.getText().toString() + "-To " + mrngto.getText().toString());
                        final String evetime = ("From " + evefrom.getText().toString() + "-To " + eveto.getText().toString());

                        textviewMrngtimeView.setText(mrngtime);
                        edittextEvengtimeView.setText(evetime);

                        alertDialog.cancel();


                    } else {
                        Toast toast = Toast.makeText(SettingActivity.this, R.string.selct_valid_timing, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();

                        //Toast.makeText(context, "Select valid timing", Toast.LENGTH_LONG).show();
                        evefrom.setError("Required");
                        eveto.setError("Required");
                        evefrom.setText("");
                        eveto.setText("");

                    }
                } else {
                    Toast toast = Toast.makeText(SettingActivity.this, R.string.selct_valid_timing, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();

                    mrngfrom.setError("Required");
                    mrngto.setError("Required");
                    mrngfrom.setText("");
                    mrngto.setText("");

                }


            } else {
                Toast.makeText(SettingActivity.this, R.string.ched_mis_valid_data, Toast.LENGTH_LONG).show();
            }

        });


        cancel.setOnClickListener(v1 -> {
            // TODO Auto-generated method stub
            alertDialog.cancel();
        });
    }

    private void GETINITIALIZE() {


        ButterKnife.bind(SettingActivity.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        switchAppointments = findViewById(R.id.switch_appointments);
        switchDailyAppointmentSMS = findViewById(R.id.switch_DailyAppointmentSMS);
        switchReferralPharmacy = findViewById(R.id.Switch_ReferralPharmacy);
        switchCheckupdate = findViewById(R.id.Switch_Checkupdate);


        STR_GENERALEXAMINATION = getResources().getString(R.string.general_examination);
        STR_CARDIOSYSTEM = getResources().getString(R.string.cardio_vascular_system);
        STR_RESPIRATORYSYSTEM = getResources().getString(R.string.respiratory_system);
        STR_GASTROSYSTEM = getResources().getString(R.string.gastrointestinal_system);
        STR_NEUROLOGY = getResources().getString(R.string.neurology_system);
        STR_LOCOMOTORSYSTEM = getResources().getString(R.string.locomotor_system);
        STR_RENLSYSTEM = getResources().getString(R.string.renal_system);
        STR_ENDOCRINESYSTEM = getResources().getString(R.string.endocrine_system);
        STR_CLINICAL = getResources().getString(R.string.clinical_informaiton);
        STR_OTHERS = getResources().getString(R.string.other_systems);
        STR_DENTAL = getResources().getString(R.string.dental_system);
        STR_PNC = getResources().getString(R.string.pnc_system);

        txvwTitle.setText(getString(R.string.moby_settings));

        setSupportActionBar(settingsToolbar);

        String first = getString(R.string.prefred_pharmacies);
        String next = "<font color='#EE0000'><b></b></font>";
        textvw_pharmacyname.setText(Html.fromHtml(first + next));


        String first1 = getString(R.string.prefer_diag);
        String next1 = "<font color='#EE0000'><b></b></font>";
        textvw_labname.setText(Html.fromHtml(first1 + next1));

        settingsEdtxtPinnumber.setEnabled(false);


        autocompletePrefpharmacy.setThreshold(1);
        prefLabs.setThreshold(1);

    }

    //*******************************************************************************************
    private final void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);
    }

    //*******************************************************************************************
    @Override
    public final void onBackPressed() {

        LoadBack();

    }

    //*******************************************************************************************
    private final void LOADSETTINGSPIN() {

        boolean status = BaseConfig.LoadReportsBooleanStatus("select count(Id) as dstatus1 from drsettings");
        if (status) {
            checkboxCasenotesGeneralexamination.setChecked(false);
            checkboxCasenotesClinical.setChecked(false);
            checkboxCasenotesOther.setChecked(false);

        }

        String aboveSpace = "";
        SQLiteDatabase db = BaseConfig.GetDb();//);

        String Query = "select EnableAutoUpdate,CaseNotes_Systems,above_space_for_letterhead,enablepin,pin,workingdays,consultationhrs,enableappointment,noofpatients,dailyappointmentviasms,professional_charges_column,referral_report,prefpharmacy,pharmdetails,preflab,labdetails,isactive,Isupdate from drsettings;";

        Cursor c = db
                .rawQuery(
                        Query,
                        null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    CheckAllSystems(c.getString(c.getColumnIndex("CaseNotes_Systems")));

                    aboveSpace = c.getString(c.getColumnIndex("above_space_for_letterhead"));


                    if (c.getString(c.getColumnIndex("enableappointment")).equalsIgnoreCase("Yes")) {

                        switchAppointments.setOn(true);//setChecked(true);

                        String patientslot = (c.getString(c.getColumnIndex("noofpatients")));

                        editTextSlot.setText(patientslot);

                    } else {

                        switchAppointments.setOn(false);
                    }

                    if (c.getString(c.getColumnIndex("enablepin")).equalsIgnoreCase("true")) {
                        settingsChkbxPinnumber.setChecked(true);


                        String decryptpin = new String(Base64.decode((c.getString(c.getColumnIndex("pin"))).trim(), Base64.DEFAULT));

                        settingsEdtxtPinnumber.setText(decryptpin);

                    }

                    if (c.getString(c.getColumnIndex("dailyappointmentviasms")).equalsIgnoreCase("Yes")) {

                        switchDailyAppointmentSMS.setOn(true);
                    } else {

                        switchDailyAppointmentSMS.setOn(false);
                    }
                    // ///////////////////////////
                    if (c.getString(c.getColumnIndex("EnableAutoUpdate")).equalsIgnoreCase("1")) {

                        switchCheckupdate.setOn(true);
                    } else {

                        switchCheckupdate.setOn(false);
                    }

                    if (c.getString(c.getColumnIndex("referral_report")).equalsIgnoreCase("Yes")) {

                        switchReferralPharmacy.setOn(true);
                    } else {

                        switchReferralPharmacy.setOn(false);
                    }


                } while (c.moveToNext());
            }
        }
        c.close();

        Cursor cc = db.rawQuery(
                "select workingdays,morning,evening  from Consultationhrs;",
                null);
        if (cc != null) {
            if (cc.moveToFirst()) {
                do {
                    editTextWorkingdays.setText(cc.getString(cc.getColumnIndex("workingdays")));
                    textviewMrngtimeView.setText(cc.getString(cc.getColumnIndex("morning")));
                    edittextEvengtimeView.setText(cc.getString(cc.getColumnIndex("evening")));

                } while (cc.moveToNext());
            }
        }
        cc.close();
        db.close();

    }

    private final void CheckAllSystems(String str) {

        if (str.length() > 0) {
            String[] arr = str.substring(0, str.length() - 1).split(",");

            for (String s : arr) {

                if (contains(arr, s)) {
                    switch (s) {
                        case "1":
                            checkboxCasenotesGeneralexamination.setChecked(true);
                            break;

                        case "2":
                            checkboxCasenotesCardio.setChecked(true);
                            break;

                        case "3":
                            checkboxCasenotesRespiratory.setChecked(true);
                            break;

                        case "4":
                            checkboxCasenotesGastrointestinal.setChecked(true);
                            break;

                        case "5":
                            checkboxCasenotesNeurology.setChecked(true);
                            break;

                        case "6":
                            checkboxCasenotesLocomotor.setChecked(true);
                            break;

                        case "7":
                            checkboxCasenotesRenal.setChecked(true);
                            break;

                        case "8":
                            checkboxCasenotesEndocrine.setChecked(true);
                            break;

                        case "9":
                            checkboxCasenotesClinical.setChecked(true);
                            break;

                        case "10":
                            checkboxCasenotesOther.setChecked(true);

                            break;

                        case "11":
                            checkboxCasenotesDental.setChecked(true);

                            break;

                        case "12":
                            checkboxCasenotesPostnatal.setChecked(true);
                            break;

                    }
                }
            }

        }

    }

    //*******************************************************************************************
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private final void LoadpopupTime(CheckBox mon, CheckBox tue, CheckBox wed,
                                     CheckBox thu, CheckBox fri, CheckBox sat, CheckBox sun,
                                     AutoCompleteTextView acmrngfrm, AutoCompleteTextView acmrngto,
                                     AutoCompleteTextView acmrngevng, AutoCompleteTextView acevngto) {
        try {
            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(
                    "select workingdays,morning,evening  from Consultationhrs;",
                    null);
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String[] wkdays = c.getString(
                                c.getColumnIndex("workingdays")).split(",");
                        for (String wkday : wkdays) {
                            if (wkday.equals("Monday")) {
                                mon.setChecked(true);
                            }
                            if (wkday.equals("Tuesday")) {
                                tue.setChecked(true);
                            }
                            if (wkday.equals("Wednesday")) {
                                wed.setChecked(true);
                            }
                            if (wkday.equals("Thursday")) {
                                thu.setChecked(true);
                            }
                            if (wkday.equals("Friday")) {
                                fri.setChecked(true);
                            }
                            if (wkday.equals("Saturday")) {
                                sat.setChecked(true);
                            }
                            if (wkday.equals("Sunday")) {
                                sun.setChecked(true);
                            }
                        }

                        if (!Objects.equals(c.getString(c.getColumnIndex("morning")), "")) {

                            String[] mrng = textviewMrngtimeView.getText().toString().split("-");
                            if (mrng.length > 0) {

                                String[] mrngfrmtime = mrng[0].split(" ");
                                if (mrngfrmtime.length == 2) {

                                    String[] mrngtotime = mrng[1].split(" ");

                                    acmrngfrm.setText(mrngfrmtime[1]);
                                    acmrngto.setText(mrngtotime[1]);
                                }
                            }
                        }
                        if (!Objects.equals(c.getString(c.getColumnIndex("evening")), "")) {

                            String[] evng = edittextEvengtimeView.getText().toString().split("-");

                            if (evng.length > 0) {
                                String[] evngfrmtime = evng[0].split(" ");
                                if (evngfrmtime.length == 2) {
                                    String[] evngtotime = evng[1].split(" ");
                                    acmrngevng.setText(evngfrmtime[1]);
                                    acevngto.setText(evngtotime[1]);
                                }
                            }
                        }

                    } while (c.moveToNext());
                }
            }
            c.close();
            db.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    public final void clear() {

        autocompletePrefpharmacy.setText(null);
        prefpharmacyAddress.setText(null);
        prefLabs.setText(null);
        autocompletePrefLabsAddress.setText(null);

    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void SelectedPharmacyAddress() {
        SQLiteDatabase db = BaseConfig.GetDb();//);

        //String[] pna = prefpharmacy.getText().toString().split(",");

        Cursor c = db.rawQuery(
                "select distinct pharmaddr,contactnum from Pharmacy where pharmacyname='"
                        + autocompletePrefpharmacy.getText().toString() + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    prefpharmacyAddress.setText(c.getString(c.getColumnIndex("pharmaddr")) + " - " + c.getString(c.getColumnIndex("contactnum")));


                } while (c.moveToNext());
            }
        }

        c.close();
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void SelectedLaboratoryAddress() {
        SQLiteDatabase db = BaseConfig.GetDb();//);

        //String[] pna = pref_labs.getText().toString().split(",");

        Cursor c = db.rawQuery(
                "select distinct labaddr,contactnum from Laboratory where labname='"
                        + prefLabs.getText().toString() + "';", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    autocompletePrefLabsAddress.setText(c.getString(c
                            .getColumnIndex("labaddr"))
                            + " - "
                            + c.getString(c.getColumnIndex("contactnum")));

                } while (c.moveToNext());
            }
        }

        c.close();
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////
    // methods autocomplete pharmacy/labs
    // relationship
    public final void LoadPharmacy() {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select distinct pharmacyname from Pharmacy order by pharmacyname;",
                null);

        List<String> list = new ArrayList<>();
        list.add("Select Pharmacy");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String counrtyname = c.getString(c
                            .getColumnIndex("pharmacyname"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }

//        BaseConfig.spinner2meth(this, list, prefpharmacy);
        BaseConfig.loadSpinner(autocompletePrefpharmacy, list);

        db.close();
        c.close();
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private final void ShowSweetAlert(String str) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setDescription(str)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {
                    SettingActivity.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Dashboard_NavigationMenu.class);
                    startActivity(intent);

                });

    }

    /////////////////////////////////////////////////////////////////////////////////
    private final void Savelocal() {

        try {
            if (checkValidation())

                submitForm();
            else
                Toast.makeText(SettingActivity.this, R.string.check_missing, Toast.LENGTH_LONG).show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void submitForm() {


        SQLiteDatabase db = BaseConfig.GetDb();//);

        try {
            SimpleDateFormat dateformt = new SimpleDateFormat(
                    "dd/MM/yyyy HH:MM:SS.SSS",Locale.ENGLISH);
            Date date = new Date();
            dateformt.format(date);
            String dttm = dateformt.format(date);

            //String Count = null;
            String smsp = null;

            // ///////////////////////////
            String enablepin = "false";


            if (settingsChkbxPinnumber.isChecked()) {
                enablepin = "true";

            }
            // ///////////////////////////


            final String Del_Query = "delete from drsettings;";
            // //Log.e("Log test",Insert_Query);
            BaseConfig.SaveData(Del_Query);
            final String Del_Query2 = "delete from Consultationhrs;";
            // //Log.e("Log test",Insert_Query);
            BaseConfig.SaveData(Del_Query2);


            String EncPin = (Base64.encodeToString(settingsEdtxtPinnumber.getText().toString().getBytes(), Base64.DEFAULT));
            String enableappointmenttxt="";
            String noofpatientcount="";


            if (switchAppointments.isOn()) {

                enableappointmenttxt = "Yes";
                switchAppointments.setVisibility(View.VISIBLE);
                noofpatientcount = editTextSlot.getText().toString();

            } else {

                enableappointmenttxt = "No";
                switchAppointments.setVisibility(View.GONE);
                noofpatientcount = "";
            }

            int str_enableautoupdate = 0;
            if (switchCheckupdate.isOn()) {
                str_enableautoupdate = 1;
            }



            String str_casenotes_generalinfo = enableCaseNoteCheckBox(checkboxCasenotesGeneralexamination);//1 default
            String str_casenotes_cardio = enableCaseNoteCheckBox(checkboxCasenotesCardio);//2
            String str_casenotes_respiratory = enableCaseNoteCheckBox(checkboxCasenotesRespiratory);//3
            String str_casenotes_gastrointestinal = enableCaseNoteCheckBox(checkboxCasenotesGastrointestinal);//4
            String str_casenotes_neurology = enableCaseNoteCheckBox(checkboxCasenotesNeurology);//5
            String str_casenotes_locomotor = enableCaseNoteCheckBox(checkboxCasenotesLocomotor);//6
            String str_casenotes_renal = enableCaseNoteCheckBox(checkboxCasenotesRenal);//7
            String str_casenotes_endocrine = enableCaseNoteCheckBox(checkboxCasenotesEndocrine);//8
            String str_casenotes_clinical = enableCaseNoteCheckBox(checkboxCasenotesClinical);//9 default
            String str_casenotes_other = enableCaseNoteCheckBox(checkboxCasenotesOther);//10 default
            String str_casenotes_dental = enableCaseNoteCheckBox(checkboxCasenotesDental);//11
            String str_casenotes_postnatal = enableCaseNoteCheckBox(checkboxCasenotesPostnatal);//12


            String CaseNoteSystems = str_casenotes_generalinfo + str_casenotes_cardio + str_casenotes_respiratory + str_casenotes_gastrointestinal + str_casenotes_neurology + str_casenotes_locomotor + str_casenotes_renal + str_casenotes_endocrine + str_casenotes_clinical + str_casenotes_dental + str_casenotes_postnatal + str_casenotes_other;


            ContentValues settings_values = new ContentValues();
            settings_values.put("EnableAutoUpdate", Integer.valueOf(str_enableautoupdate));
            settings_values.put("CaseNotes_Systems", CaseNoteSystems);
            settings_values.put("docid", BaseConfig.doctorId);
            settings_values.put("drregno", BaseConfig.doctorRegId);
            settings_values.put("drname", BaseConfig.doctorname);
            settings_values.put("dremail", BaseConfig.docemail);
            settings_values.put("dt", dttm);
            settings_values.put("enablepin", enablepin);
            settings_values.put("pin", EncPin);
            settings_values.put("workingdays", editTextWorkingdays.getText().toString());
            settings_values.put("consultationhrs", (textviewMrngtimeView.getText().toString()) + '\n' + (edittextEvengtimeView.getText().toString()));
            settings_values.put("enableappointment", enableappointmenttxt);
            settings_values.put("noofpatients", noofpatientcount);
            settings_values.put("dailyappointmentviasms", BaseConfig.GetWidgetOperations(switchDailyAppointmentSMS,1));
            settings_values.put("professional_charges_column", "");
            settings_values.put("referral_report", BaseConfig.GetWidgetOperations(switchReferralPharmacy,1));
            settings_values.put("prefpharmacy", autocompletePrefpharmacy.getText().toString());
            settings_values.put("pharmdetails", prefpharmacyAddress.getText().toString());
            settings_values.put("preflab", prefLabs.getText().toString());
            settings_values.put("labdetails", autocompletePrefLabsAddress.getText().toString());
            settings_values.put("isactive", Integer.valueOf(1));
            settings_values.put("Isupdate", Integer.valueOf(0));
            settings_values.put("above_space_for_letterhead", "5");
            settings_values.put("HID", BaseConfig.HID);

            db.insert(BaseConfig.TABLE_SETTINGS, null, settings_values);


            final String Insert_Query1 = "Insert into Consultationhrs(workingdays,morning,evening,isactive)"
                    + " Values('"
                    + editTextWorkingdays.getText()
                    + "','"
                    + textviewMrngtimeView.getText()
                    + "','"
                    + edittextEvengtimeView.getText()
                    + "','0');";
            BaseConfig.SaveData(Insert_Query1);


            ShowSweetAlert(getString(R.string.all_settings));

            db.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private final String enableCaseNoteCheckBox(CheckBox checkBox) {

        if (checkBox.isChecked()) {

            String str = checkBox.getText().toString();
            String ret = "";


            if (str.equalsIgnoreCase(STR_GENERALEXAMINATION)) {
                ret = "1,";


            } else if (str.equalsIgnoreCase(STR_CARDIOSYSTEM)) {
                ret = "2,";

            } else if (str.equalsIgnoreCase(STR_RESPIRATORYSYSTEM)) {
                ret = "3,";

            } else if (str.equalsIgnoreCase(STR_GASTROSYSTEM)) {
                ret = "4,";

            } else if (str.equalsIgnoreCase(STR_NEUROLOGY)) {
                ret = "5,";

            } else if (str.equalsIgnoreCase(STR_LOCOMOTORSYSTEM)) {
                ret = "6,";

            } else if (str.equalsIgnoreCase(STR_RENLSYSTEM)) {
                ret = "7,";

            } else if (str.equalsIgnoreCase(STR_ENDOCRINESYSTEM)) {
                ret = "8,";

            } else if (str.equalsIgnoreCase(STR_CLINICAL)) {
                ret = "9,";

            } else if (str.equalsIgnoreCase(STR_OTHERS)) {
                ret = "10,";

            } else if (str.equalsIgnoreCase(STR_DENTAL)) {
                ret = "11,";

            } else if (str.equalsIgnoreCase(STR_PNC)) {
                ret = "12,";

            }

            return ret;
        }

        return "";
    }


    // ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private boolean checkValidation() {
        boolean ret = true;

        if (settingsChkbxPinnumber.isChecked()) {
            if (!Validation1.isPinNumber(settingsEdtxtPinnumber, true)) ret = false;
        } else if (!settingsChkbxPinnumber.isChecked()) {
            settingsEdtxtPinnumber.setError(null);
        }


        return ret;
    }

    //*******************************************************************************************
    // methods autocomplete pharmacy/labs
    // relationship
    public final void LoadLaboratory() {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select distinct labname from Laboratory order by labname;", null);

        List<String> list = new ArrayList<>();
        list.add("Select Laboratory");

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String counrtyname = c.getString(c
                            .getColumnIndex("labname"));
                    list.add(counrtyname);

                } while (c.moveToNext());
            }
        }

        //BaseConfig.spinner2meth(this, list, pref_labs);
        BaseConfig.loadSpinner(prefLabs, list);

        db.close();
        c.close();
    }

    /**
     * Muthukumar N
     */

    private void CHECK_APP_UPDATE() {

        appVerUpdater = new AppVerUpdater()
                .setUpdateJSONUrl(BaseConfig.AppUpdateJson)
                .setShowNotUpdated(true)
                .setViewNotes(true)
                .setCallback(new Callback() {
                    @Override
                    public void onFailure(UpdateErrors error) {

                        if (error == UpdateErrors.NETWORK_NOT_AVAILABLE) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"No internet connection.", setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_CHECKING_UPDATES) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"An error occurred while checking for updates.", setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.ERROR_DOWNLOADING_UPDATES) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"An error occurred when downloading updates.", setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.JSON_FILE_IS_MISSING) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"File missing", setttingsParentLayout, 2);
                        } else if (error == UpdateErrors.FILE_JSON_NO_DATA) {
                            BaseConfig.SnackBar(SettingActivity.this, "\"The file containing information about the updates are empty.", setttingsParentLayout, 2);
                        }
                    }

                    @Override
                    public void onDownloadSuccess() {
                        // for example, record/reset license
                        UnInstallApplication(getPackageName());
                    }

                    @Override
                    public void onUpdateChecked(boolean downloading) {
                        // Happens after an update check, immediately after if update check was successful and there
                        // were no dialogs, or, when an update dialog is presented and user explicitly dismissed the dialog.
                        // "downloading" is true if user accepted the update
                        // Typically used for resetting next update check time
                    }
                })
                .setAlertDialogCancelable(true)
                .build(this);

    }


    //**********************************************************************************************

    //Uninstall Apk
    private void UnInstallApplication(String packageName) {

        try {
            Uri packageURI = Uri.parse(packageName.toString());
            Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
            startActivity(uninstallIntent);

            BaseConfig.SnackBar(this, "Updated version of apk is available in download folder..", setttingsParentLayout, 1);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    //**********************************************************************************************

    //Install Apk
    private void InstallApplication(String AppPackageName) {
        try {
            Uri packageURI = Uri.parse(AppPackageName.toString());
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    packageURI);

            intent.setDataAndType(
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory()
                            + "/download/" + ApkName.toString())),
                    "application/vnd.android.package-archive");

            startActivity(intent);


        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public class LoginBackgroundProcesses extends AsyncTask<Void, Void, Void> {

        String docid, ServerId = "";
        String[] db_updatequery;

        protected void onPostExecute(Void v) {

            if (pDialog.isShowing())
                pDialog.dismiss();

            if (BaseConfig.CheckNetwork(SettingActivity.this)) {

                CHECK_APP_UPDATE();

            } else {

                BaseConfig.SnackBar(SettingActivity.this, "Data Connection Not Available - Please Enable to check update next time..", setttingsParentLayout, 2);

            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            pDialog = new ProgressDialog(SettingActivity.this);
            pDialog.setTitle("Checking updates");
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();


        }

        @Override
        protected Void doInBackground(Void... params) {


            try {

                ImportWebservices_NODEJS.CheckDbUpdatesNodeJs(SettingActivity.this);

            } catch (Exception e) {

                e.printStackTrace();
            }


            return null;

        }

    }


}//END
