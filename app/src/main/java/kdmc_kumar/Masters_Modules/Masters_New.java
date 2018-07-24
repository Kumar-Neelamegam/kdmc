package kdmc_kumar.Masters_Modules;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;
import kdmc_kumar.Utilities_Others.ViewAnimation;

public class Masters_New extends AppCompatActivity {

    //**********************************************************************************************
    public boolean rotate = false;
    /**
     * Muthukumar N
     * 08-05-2018
     */
    //**********************************************************************************************
    //Declaration
    @BindView(R.id.content)
    RelativeLayout content;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.simpleFrameLayout)
    FrameLayout simpleFrameLayout;
    @BindView(R.id.back_drop)
    View backDrop;
    @BindView(R.id.lyt_1)
    LinearLayout lyt1;
    @BindView(R.id.fab_1)
    FloatingActionButton fab1;
    @BindView(R.id.lyt_2)
    LinearLayout lyt2;
    @BindView(R.id.fab_2)
    FloatingActionButton fab2;
    @BindView(R.id.lyt_3)
    LinearLayout lyt3;
    @BindView(R.id.fab_3)
    FloatingActionButton fab3;
    @BindView(R.id.fab_add)
    FloatingActionButton fabAdd;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    //**********************************************************************************************

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_masters);


        try {
            GETINITIALIZE();
            CONTROLLISTENERS();
        } catch (Exception e) {
            //e.printStackTrace();
        }


    }
   public static TabLayout.Tab Category=null,Brand=null;
    //**********************************************************************************************
    private void CONTROLLISTENERS() {


        TabLayout.Tab Items = tabLayout.newTab();
        Items.setText("Prescription Templates");
        Items.setIcon(R.drawable.dashboard_ic_prescription);
        tabLayout.addTab(Items);

         Category = tabLayout.newTab();
        Category.setText("My Preferred Medicine List");
        Category.setIcon(R.drawable.dashboard_ic_case_book);
        tabLayout.addTab(Category);

         Brand = tabLayout.newTab();
        Brand.setText("My Preferred Test List");
        Brand.setIcon(R.drawable.dashboard_ic_medical_test);
        tabLayout.addTab(Brand);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab.getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {

                    case 0:
                        fragment = new Fragment_Prescription_Templates();
                        break;

                    case 1:
                        fragment = new Fragment_My_Preferred_Medicine_List();
                        break;

                    case 2:
                        fragment = new Fragment_My_Preferred_Test_List();
                        break;


                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.grey_20), PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                // get the current selected tab's position and replace the fragment accordingly
                Fragment fragment = null;
                switch (tab.getPosition()) {

                    case 0:
                        fragment = new Fragment_Prescription_Templates();
                        break;

                    case 1:
                        fragment = new Fragment_My_Preferred_Medicine_List();
                        break;

                    case 2:
                        fragment = new Fragment_My_Preferred_Test_List();
                        break;


                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.simpleFrameLayout, fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();

            }
        });


        Fragment fragment = new Fragment_Prescription_Templates();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.simpleFrameLayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();


        ViewAnimation.initShowOut(lyt1);
        ViewAnimation.initShowOut(lyt2);
        ViewAnimation.initShowOut(lyt3);

        backDrop.setVisibility(View.GONE);

        fabAdd.setOnClickListener(v -> toggleFabMode(v));

        backDrop.setOnClickListener(v -> toggleFabMode(fabAdd));

        fab1.setOnClickListener(v -> {
            toggleFabMode(v);

            Intent intent = new Intent(Masters_New.this,
                    templates_addnew.class);
            startActivityForResult(intent , templates_addnew.Data_Inserted);


            //startActivity(new Intent(Masters_New.this, templates_addnew.class));
            BaseConfig.temp_flag="true";
        });

        fab2.setOnClickListener(v -> {
            toggleFabMode(v);
            addMedicineNamePopupDialog((Activity) fab1.getContext());
        });

        fab3.setOnClickListener(v -> {

            toggleFabMode(v);

            addTestPopupDialog((Activity) fab3.getContext());


        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == templates_addnew.Data_Inserted && resultCode  == RESULT_OK) {

                    Fragment_Prescription_Templates.checkNewDataNotify();


            }
        } catch (Exception ex) {
           // Toast.makeText(Activity.this, ex.toString(),
                   // Toast.LENGTH_SHORT).show();
        }
    }

    public void addMedicineNamePopupDialog(Activity activity) {
        try {
            AlertDialog alertDialog =null;
            LayoutInflater li = LayoutInflater.from(getApplicationContext());
            final View prompt = li.inflate(R.layout.popup_medicine_layout, null);
            final AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            final AutoCompleteTextView medicine_name = prompt.findViewById(R.id.medcineName);
            final Button add = prompt.findViewById(R.id.add);
            alertDialogBuilder.setView(prompt);

            medicine_name.setThreshold(1);

            medicine_name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (medicine_name.getText().toString().length() > 0) {
                        String Query = "select distinct dvalue from (SELECT id,(CASE WHEN DOSAGE!='' then  MARKETNAMEOFDRUG ||'-'|| DOSAGE else MARKETNAMEOFDRUG END) as dvalue FROM cims  order by id) as temp order by dvalue";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, activity, medicine_name, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });


            alertDialog=alertDialogBuilder.show();

            AlertDialog finalAlertDialog = alertDialog;
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (checkValidation(medicine_name)) {

                        String Get_Medicine = medicine_name.getText().toString();

                        boolean b = BaseConfig.LoadBooleanStatus("select medicine from NewMedicine where medicine='" + Get_Medicine + '\'');

                        if (b) {

                            medicine_name.setText("");
                            medicine_name.setFocusable(true);
                            BaseConfig.KDMC_COMMON_DILOAGS(3, activity, "Information", "Already selected medicine added in mypreferred list..", getString(R.string.ok));

                        } else {
                            InsertTODB(medicine_name);
                            finalAlertDialog.cancel();
                        }


                    } else {
                        Toast.makeText(activity,
                                "check for missing & valid Data", Toast.LENGTH_LONG)
                                .show();
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addTestPopupDialog(Activity activity) {
        try {

            AlertDialog alertDialog =null;

                    LayoutInflater li = LayoutInflater.from(getApplicationContext());
            final View prompt = li.inflate(R.layout.popup_test_layout, null);
            final AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(this);
            final AutoCompleteTextView auto_test = prompt.findViewById(R.id.auto_test);
            final AutoCompleteTextView auto_subtest = prompt.findViewById(R.id.auto_subtest);
            final EditText templatename = prompt.findViewById(R.id.edt_templatename);
            final Button add = prompt.findViewById(R.id.ic_add);
            alertDialogBuilder.setView(prompt);
            alertDialog=alertDialogBuilder.show();

            auto_subtest.setThreshold(1);
            auto_test.setThreshold(1);

            auto_test.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                     if (auto_test.getText().toString().length() > 0) {
                        String Query = "select distinct Testname as dvalue  from Testname where IsActive='1'";
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query, activity, auto_test, charSequence.toString());

                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });

            auto_subtest.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                    if (auto_subtest.getText().toString().length() > 0) {
                        String Query = "select SubTest as dvalue from Testname where IsActive=1 and Testname='" + auto_test.getText().toString() + '\'';
                        BaseConfig.SelectedGetPatientDetailsFilterOthers(Query,activity, auto_subtest, charSequence.toString());
                        //LoadValues(SubTest, prefered_test.this, Query, 1);
                        //spinner2meth(prefered_test.this, Loadlist1, SubTest);
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {


                }
            });


            AlertDialog finalAlertDialog = alertDialog;
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkValidation(templatename)&&checkValidation(auto_test)&&checkValidation(auto_subtest)) {
                        String Get_TemplateName = templatename.getText().toString();
                        String Get_TestName = auto_test.getText().toString();
                        String Get_SubTestName = auto_subtest.getText().toString();

                        boolean b = BaseConfig.LoadBooleanStatus("select Testname from MyFavTest where TemplateName='" + Get_TemplateName + "' and Testname='" + Get_TestName + "' and SubTest='" + Get_SubTestName + '\'');

                        if (b) {

                            auto_test.setText("");
                            auto_subtest.setText("");
                            auto_test.setFocusable(true);
                            BaseConfig.KDMC_COMMON_DILOAGS(3, activity, "Information", "Already selected testname and subtest added in mypreferred list..", getString(R.string.ok));

                        } else {
                            SQLiteDatabase db = BaseConfig.GetDb();
                             Get_TemplateName = templatename.getText().toString();
                             Get_TestName = auto_test.getText().toString();
                             Get_SubTestName = auto_subtest.getText().toString();
                            ContentValues values = new ContentValues();
                            values.put("TemplateName", Get_TemplateName);
                            values.put("Testname", Get_TestName);
                            values.put("SubTest", Get_SubTestName);
                            values.put("Isupdate", Integer.valueOf(1));
                            db.insert("MyFavTest", null, values);
                            db.close();

                            finalAlertDialog.cancel();

                            new CustomKDMCDialog(activity)
                                    .setLayoutColor(R.color.green_500)
                                    .setImage(R.drawable.ic_success_done)
                                    .setNegativeButtonVisible(View.GONE)
                                    .setTitle(activity.getString(R.string.information))
                                    .setOnPossitiveListener(() -> {

                                    })
                                    .setDescription("Preferred Test & Subtest - Saved Successfully")
                                    .setPossitiveButtonTitle(activity.getString(R.string.ok));

                            Brand.select();
                        }


                    } else {
                        Toast.makeText(activity,
                                "check for missing & valid Data", Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final void InsertTODB(AutoCompleteTextView medicinename) {

        SQLiteDatabase db = BaseConfig.GetDb();


        String Get_MedicineName = medicinename.getText().toString();

        ContentValues values = new ContentValues();
        values.put("medicine", Get_MedicineName);
        values.put("Isupdate", Integer.valueOf(1));
        db.insert("NewMedicine", null, values);

        db.close();

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setOnPossitiveListener(() -> {

                })
                .setNegativeButtonVisible(View.GONE)
                .setDescription("Preferred Medicine - Saved Successfully")
                .setPossitiveButtonTitle(this.getString(R.string.ok));

        Category.select();

    }

    private boolean checkValidation(AutoCompleteTextView medicinename) {

        boolean ret = true;

        if (!Validation1.hasText(medicinename))
            ret = false;


        return ret;
    }
    private boolean checkValidation(EditText medicinename) {

        boolean ret = true;

        if (!Validation1.hasText(medicinename))
            ret = false;


        return ret;
    }
    private void toggleFabMode(View v) {
        rotate = ViewAnimation.rotateFab(v, !rotate);
        if (rotate) {
            ViewAnimation.showIn(lyt1);
            ViewAnimation.showIn(lyt2);
            ViewAnimation.showIn(lyt3);
            backDrop.setVisibility(View.VISIBLE);
        } else {
            ViewAnimation.showOut(lyt1);
            ViewAnimation.showOut(lyt2);
            ViewAnimation.showOut(lyt3);
            backDrop.setVisibility(View.GONE);
        }
    }


    //**********************************************************************************************
    private void GETINITIALIZE() {
        BaseConfig.welcometoast = 0;

        ButterKnife.bind(Masters_New.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.master)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }


        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(Masters_New.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());



    }

    //**********************************************************************************************

    private void LoadBack()
    {

        Masters_New.this.finish();
        Intent intent = new Intent(Masters_New.this,Dashboard_NavigationMenu.class);
        startActivity(intent);
        BaseConfig.Druglistselindex = "";

    }

    //**********************************************************************************************


    @Override
    public void onBackPressed() {

        Masters_New.this.finish();
        Intent intent = new Intent(Masters_New.this, Dashboard_NavigationMenu.class);
        startActivity(intent);
        CustomIntent.customType(this, 4);

    }
    //**********************************************************************************************


}//END
