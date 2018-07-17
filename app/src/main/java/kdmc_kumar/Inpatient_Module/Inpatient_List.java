package kdmc_kumar.Inpatient_Module;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.simplecityapps.recyclerview_fastscroll.views.FastScrollRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.MyPatientGetSet;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.InpatientRecyclerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.customenu.ListViewType;

public class Inpatient_List extends AppCompatActivity {


    private static final int LoadBack_ACTIVITY = 2;
    private static final int RESULT_SPEECH = 1;
    private static final int DATE_DIALOG_ID_1 = 1;
    public static int mcYear = 0;
    public static int mcMonth = 0;
    public static int mcDay = 0;
    private static int mYear = 0;
    private static int mMonth = 0;
    private static int mDay = 0;

    private final String QueryInPatient = "select id,prefix,patientname as name,Patid,age,gender,PC as photo  from Patreg  where enable_inpatient=1 and Docid='"+BaseConfig.doctorId+"' order by patientname, id ";

    private final DatePickerDialog.OnDateSetListener mDateSetListener1 = (datePicker, i, i1, i2) -> {
        mYear = i;
        mMonth = i1;
        mDay = i2;
        updateDisplay();
    };
    @BindView(R.id.inpatient_parent_layout)
    CoordinatorLayout inpatientParentLayout;
    @BindView(R.id.inpatient_toolbar)
    Toolbar inpatientToolbar;
    @BindView(R.id.inpatient_back)
    AppCompatImageView inpatientBack;
    @BindView(R.id.txvw_title)
    TextView txvwTitle;
    @BindView(R.id.listgrid)
    AppCompatImageView listgrid;
    @BindView(R.id.admin_request)
    AppCompatImageView adminRequest;
    @BindView(R.id.operation_theater)
    AppCompatImageView operationTheater;
    @BindView(R.id.imgvw_exit)
    AppCompatImageView imgvwExit;
    @BindView(R.id.cardvw_search_bar)
    CardView cardvwSearchBar;
    @BindView(R.id.edittext_search)
    EditText edittextSearch;
    @BindView(R.id.button_clear)
    ImageButton btClear;
    @BindView(R.id.button_refresh)
    ImageButton btRefresh;
    @BindView(R.id.textvw_patient_count)
    TextView textvwPatientCount;
    @BindView(R.id.imagevw_no_data_available)
    AppCompatImageView imagevwNoDataAvailable;
    @BindView(R.id.inpatient_fastscrollview)
    FastScrollRecyclerView inpatientFastscrollview;
    //**********************************************************************************************
    Dialog builderDialog;
    private ArrayList<MyPatientGetSet> rowItems = null;
    private InpatientRecyclerAdapter inpatientRecyclerAdapter = null;

    public Inpatient_List() {
    }

    private static void LoadPatentIDnameForAuto(AutoCompleteTextView autoSpName) {

        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(
                "select *  from Patreg  where enable_inpatient != '1' order by name;", null);

        List<String> list = new ArrayList<>();

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    String pname = c.getString(c.getColumnIndex("name"));
                    String str2 = c.getString(c.getColumnIndex("Patid"));
                    list.add(pname + " - " + str2);


                } while (c.moveToNext());
            }
        }

        BaseConfig.loadSpinner(autoSpName, list);

        db.close();
        c.close();


    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.kdmc_layout_inpatient_list);


        try {
            GETINITIALIZE();
            CONTROLLISTENERS();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void CONTROLLISTENERS() {


        edittextSearch.addTextChangedListener(new TextWatcher() {

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1,
                                      int i2) {


                if (edittextSearch.getText().toString().length() > 0) {
                    boolean istext = false;
                    try {
                        Integer.parseInt(charSequence.toString());
                        istext = false;

                    } catch (NumberFormatException ignored) {
                        istext = true;
                    }
                    SelectedGetPatientDetailsFilter(QueryInPatient, charSequence.toString(), istext);
                } else {
                    textvwPatientCount.setText(String.format("%s: %d", getString(R.string.no_of_pat), rowItems.size()));
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (edittextSearch.getText().toString().length() > 0) {


                } else {
                    SelectedGetPatientDetails(QueryInPatient);
                    textvwPatientCount.setText(String.format("%s: %d", getString(R.string.no_of_pat), rowItems.size()));
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i,
                                          int i1, int i2) {

                boolean istext = false;

                try {
                    Integer.parseInt(charSequence.toString());
                    istext = false;

                } catch (NumberFormatException ignored) {
                    // e.printStackTrace();
                    istext = true;
                }


                SelectedGetPatientDetailsFilter(QueryInPatient, charSequence.toString(), istext);

            }
        });

        btClear.setOnClickListener(view -> {

            if (edittextSearch.getText().toString().length() > 0) {

                edittextSearch.setText("");
                inpatientRecyclerAdapter = new InpatientRecyclerAdapter(rowItems);
                inpatientFastscrollview.setAdapter(inpatientRecyclerAdapter);
                textvwPatientCount.setText(String.format("%s:%s", getString(R.string.no_of_pat), String.valueOf(rowItems.size())));


            }

        });


        inpatientBack.setOnClickListener(v -> {

            BaseConfig.globalStartIntent(Inpatient_List.this, Dashboard_NavigationMenu.class, null);

        });

        adminRequest.setOnClickListener(v -> ShowRequestDialog());

        operationTheater.setOnClickListener(v -> {

            BaseConfig.globalStartIntent(Inpatient_List.this, OperationTheater.class, null);

        });


        imgvwExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(Inpatient_List.this, null));


        listgrid.setOnClickListener(v -> {
            if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
                BaseConfig.listViewType = ListViewType.GrideView;
                inpatientFastscrollview.setLayoutManager(new GridLayoutManager(Inpatient_List.this, 2));
                inpatientFastscrollview.setHasFixedSize(true);
                inpatientFastscrollview.setNestedScrollingEnabled(false);
                SelectedGetPatientDetails(QueryInPatient);
                listgrid.setImageResource(R.drawable.list);
            } else {
                BaseConfig.listViewType = ListViewType.ListView;
                listgrid.setImageResource(R.drawable.grid);
                inpatientFastscrollview.setLayoutManager(new LinearLayoutManager(Inpatient_List.this));
                inpatientFastscrollview.setHasFixedSize(true);
                inpatientFastscrollview.setNestedScrollingEnabled(false);
                SelectedGetPatientDetails(QueryInPatient);
            }
        });


        assert inpatientFastscrollview != null;


        final Animation animRotate = AnimationUtils.loadAnimation(this, R.anim.anim_rotate);

        btRefresh.setOnClickListener(v -> {

            try {
                edittextSearch.setText("");
                v.startAnimation(animRotate);
                SelectedGetPatientDetails(QueryInPatient);

                Toast.makeText(Inpatient_List.this, R.string.refresh_txt, Toast.LENGTH_LONG).show();


            } catch (Exception e) {
                e.printStackTrace();
            }

        });


    }

    private void GETINITIALIZE() {


        ButterKnife.bind(this);


        setSupportActionBar(inpatientToolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(inpatientToolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        BaseConfig.welcometoast = 0;
        if (BaseConfig.HOSPITALNAME.length() > 0) {
            txvwTitle.setText(String.format("%s  -  %s", BaseConfig.HOSPITALNAME, getString(R.string.title_inpatient)));
        } else {
            txvwTitle.setText(getString(R.string.title_inpatient));
        }


        rowItems = new ArrayList<>();

        GridLayoutManager lLayout = new GridLayoutManager(Inpatient_List.this, 2);
        inpatientFastscrollview.setHasFixedSize(true);
        inpatientFastscrollview.setLayoutManager(lLayout);
        inpatientFastscrollview.setNestedScrollingEnabled(false);


        if (BaseConfig.listViewType.equals(ListViewType.ListView)) {
            inpatientFastscrollview.setLayoutManager(new LinearLayoutManager(this));
        } else {
            inpatientFastscrollview.setLayoutManager(new GridLayoutManager(Inpatient_List.this, 2));
        }


        imagevwNoDataAvailable.setVisibility(View.GONE);


        new LoadPatientInfo(QueryInPatient, 1).execute();


    }

    private void SelectedGetPatientDetails(String Query) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);
            rowItems.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String PREFIX = c.getString(c.getColumnIndex("prefix"));
                        String NAME = c.getString(c.getColumnIndex("name"));
                        String PATID = c.getString(c.getColumnIndex("Patid"));
                        String AGE = c.getString(c.getColumnIndex("age"));
                        String GENDER = c.getString(c.getColumnIndex("gender"));
                        String PHOTO = c.getString(c.getColumnIndex("photo"));


                        MyPatientGetSet item = new MyPatientGetSet(PREFIX, PREFIX+"."+NAME, PATID, AGE, GENDER, PHOTO);
                        item.IsOnlinePatient = false;
                        rowItems.add(item);


                    } while (c.moveToNext());
                }
            }


            c.close();
            db.close();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //**********************************************************************************************

    private void SelectedGetPatientDetailsFilter(String Query, String filtervalue, boolean isName) {
        try {

            SQLiteDatabase db = BaseConfig.GetDb();//);
            Cursor c = db.rawQuery(Query, null);
            rowItems.clear();

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String PREFIX = c.getString(c.getColumnIndex("prefix"));
                        String NAME = c.getString(c.getColumnIndex("name"));
                        String PATID = c.getString(c.getColumnIndex("Patid"));
                        String AGE = c.getString(c.getColumnIndex("age"));
                        String GENDER = c.getString(c.getColumnIndex("gender"));
                        String PHOTO = c.getString(c.getColumnIndex("photo"));


                        MyPatientGetSet item = new MyPatientGetSet(PREFIX, PREFIX+"."+NAME, PATID, AGE, GENDER, PHOTO);
                        String val = c.getString(c.getColumnIndex("name"));
                        String name[] = val.split("\\.");
                        item.IsOnlinePatient = false;
                        String val1=null;
                        try {
                            val1 = name[1].trim();
                        } catch (Exception e) {
                            val1 = name[0].trim();
                            // e.printStackTrace();
                        }
                        if (isName) {
                            if (val1.toLowerCase().startsWith(filtervalue)) {
                                rowItems.add(item);
                            }

                        } else {

                            if (c.getString(c.getColumnIndex("Patid")).split("/")[3].startsWith(filtervalue)) {
                                rowItems.add(item);
                            }
                        }


                    } while (c.moveToNext());
                }
            }


            c.close();
            db.close();

            inpatientRecyclerAdapter = new InpatientRecyclerAdapter(rowItems);

            inpatientFastscrollview.setAdapter(inpatientRecyclerAdapter);

            textvwPatientCount.setText(String.format("%s: %s", getString(R.string.no_of_pat), String.valueOf(rowItems.size())));

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    @Override
    public final void onBackPressed() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }

    @Nullable
    @Override
    protected final Dialog onCreateDialog(int id) {
        switch (id) {

            case DATE_DIALOG_ID_1:

                return new DatePickerDialog(this, mDateSetListener1, mYear, mMonth, mDay);


        }
        return null;
    }

    private void updateDisplay() {

        Calendar c1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String strdate1 = sdf1.format(c1.getTime());
        Date got;
        try {
            got = dateFormat.parse(mDay + "/" + (mMonth + 1) + '/' + mYear);
            int result = dateFormat.parse(strdate1).compareTo(got);

            if (result < 0) {

                BaseConfig.showSimplePopUp("Invalid Selection. Please select correct date", "Warning!", this, R.drawable.ic_warning_black_24dp, R.color.orange_500);

            }
        } catch (Exception e) {

        }

    }

    private void ShowSuccessFullyPlacedRequest() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle(getString(R.string.title_inpatient));
        alertBuilder.setMessage(R.string.request_placed);
        alertBuilder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {

        });
        alertBuilder.show();
    }

    private void ShowRequestDialog() {

        View view = getLayoutInflater().inflate(R.layout.inpatient_request_layout, null);
        final AutoCompleteTextView patientAuto = view.findViewById(R.id.patient_detail_values);
        patientAuto.setThreshold(1);
        LoadPatentIDnameForAuto(patientAuto);
        final EditText remarksEdt = view.findViewById(R.id.request_remarks_edt);
        Button submitBtn = view.findViewById(R.id.submit);
        Button cancelBtn = view.findViewById(R.id.cancel);
        Spinner WardCategory = view.findViewById(R.id.ward_category);
        TextView Title_PatientName, Title_WarCategory, Title_Remarks;
        Title_PatientName = view.findViewById(R.id.txtvw_patname);
        Title_WarCategory = view.findViewById(R.id.txtvw_wardcategory);
        Title_Remarks = view.findViewById(R.id.txtvw_remarks);

        String first2 = "Patient Name";
        String next2 = "<font color='#EE0000'><b>*</b></font>";
        Title_PatientName.setText(Html.fromHtml(first2 + next2));

        String first3 = "Choose ward category";
        String next3 = "<font color='#EE0000'><b>*</b></font>";
        Title_WarCategory.setText(Html.fromHtml(first3 + next3));

        String first4 = "Request Remarks";
        String next4 = "<font color='#EE0000'><b>*</b></font>";
        Title_Remarks.setText(Html.fromHtml(first4 + next4));

        BaseConfig.LoadValuesSpinner(WardCategory, Inpatient_List.this, "select distinct WardCatName as dvalue from Mstr_WardCategory where HID='" + BaseConfig.HID + "' order by ServerId;", "select ward category");

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        submitBtn.setOnClickListener(v -> {


            if (Validation1.hasText(patientAuto)) {
                if (WardCategory.getSelectedItemPosition() > 0) {
                    if (patientAuto.getText().toString().contains("-")) {

                        if (Validation1.hasText(remarksEdt)) {

                            String patId = patientAuto.getText().toString().split("-")[1].trim();
                            String selectedWard = WardCategory.getSelectedItem().toString();


                            //Check for valid patient id
                            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + patId + '\'');
                            if (chk) {

                                ContentValues values = new ContentValues();
                                values.put("patid", patId);
                                values.put("request_remarks", remarksEdt.getText().toString());
                                values.put("WardCategory", selectedWard);
                                values.put("WardCategoryId", BaseConfig.GetValues("select ServerId as ret_values from Mstr_WardCategory where WardCatName='" + selectedWard + "' and HID='" + BaseConfig.HID + '\''));
                                values.put("IsActive", "1");
                                values.put("IsUpdate", "0");
                                values.put("docid", BaseConfig.doctorId);
                                values.put("ActDate", BaseConfig.DeviceDate());

                                SQLiteDatabase Db = BaseConfig.GetDb();//);
                                Db.insert("inpatient_request", null, values);
                                dialog.dismiss();

                                ShowSuccessFullyPlacedRequest();

                            } else {
//                                Toast.makeText(Inpatient_List.this, "Selected Patient Id/Name is not valid!", Toast.LENGTH_LONG).show();
                                BaseConfig.SnackBar(this, "Selected Patient Id/Name is not valid!", view, 2);

                            }


                        } else {
                            //Toast.makeText(Inpatient_List.this, "Enter remarks", Toast.LENGTH_LONG).show();
                            BaseConfig.SnackBar(this, "Enter remarks", view, 2);


                        }

                    } else {
                        Toast.makeText(Inpatient_List.this, R.string.valid_patient_details, Toast.LENGTH_LONG).show();
                        BaseConfig.SnackBar(this, getResources().getString(R.string.valid_patient_details), view, 2);

                    }

                } else {

                    Toast.makeText(Inpatient_List.this, R.string.choose_ward, Toast.LENGTH_LONG).show();
                    BaseConfig.SnackBar(this, getResources().getString(R.string.choose_ward), view, 2);

                }

            } else {
                Toast.makeText(Inpatient_List.this, R.string.pl_select_patient, Toast.LENGTH_LONG).show();
                BaseConfig.SnackBar(this, getResources().getString(R.string.pl_select_patient), view, 2);

            }

        });
        cancelBtn.setOnClickListener(v -> dialog.dismiss());

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        dialog.getWindow().setLayout((6 * width) / 7, ViewGroup.LayoutParams.WRAP_CONTENT);

        dialog.show();


    }

    public class LoadPatientInfo extends AsyncTask<Void, Void, Void> {

        String PASSING_QUERY = "";
        int PASSING_ID = 1;

        LoadPatientInfo(String select_MyPatient_Query, int Id) {
            this.PASSING_ID = Id;
            this.PASSING_QUERY = select_MyPatient_Query;
        }

        @Override
        protected void onPreExecute() {
            builderDialog = BaseConfig.showCustomDialog(getString(R.string.please_wait), "Loading My Patients..", Inpatient_List.this);
            builderDialog.setCancelable(false);
            builderDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String Query = "select count(*)as ret_values  from Patreg where enable_inpatient=1 order by patientname, id ";
            int PresentCount = Integer.valueOf(BaseConfig.GetValues(Query));

            Log.e("Present Count: ", String.valueOf(PresentCount));

            if (PresentCount > rowItems.size()) {
                SelectedGetPatientDetails(PASSING_QUERY);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            if (builderDialog.isShowing() && builderDialog != null) {
                builderDialog.dismiss();
            }


            inpatientRecyclerAdapter = new InpatientRecyclerAdapter(rowItems);

            inpatientFastscrollview.setAdapter(inpatientRecyclerAdapter);

            textvwPatientCount.setText(String.format("%s: %s", getString(R.string.noofpatient), String.valueOf(rowItems.size())));


            super.onPostExecute(aVoid);
        }
    }


}//END
