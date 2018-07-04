package kdmc_kumar.Drug_Directory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.DrugItem;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItemVaccination;
import kdmc_kumar.Adapters_GetterSetter.DrugRecylerAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;

public class Cims extends AppCompatActivity {


    private final Context context = this;
    //declaration
    BaseConfig bcnfg = new BaseConfig();
    String[] values1 = new String[]{"1001"};
    String[] values2 = new String[]{"1001  Gastrointestinal and hepatobiliary System   		7-LA"};
    private DownloadFilesIssue1 objbgtsk1 = new DownloadFilesIssue1();
    private DownloadFilesIssue2 objbgtsk2 = new DownloadFilesIssue2();
    private DownloadFilesIssue3 objbgtsk3 = new DownloadFilesIssue3();
    private DownloadFilesIssue4 objbgtsk4 = new DownloadFilesIssue4();
    private DownloadFilesIssue5 objbgtsk5 = new DownloadFilesIssue5();
    private DownloadFilesIssue6 objbgtsk6 = new DownloadFilesIssue6();
    ArrayList<HashMap<String, String>> titles_list = null;
    ArrayAdapter<RowItemVaccination> adapter = null;
    private AutoCompleteTextView system = null;
    private AutoCompleteTextView druggeneric = null;
    private AutoCompleteTextView chemicalname = null;
    private AutoCompleteTextView brandname = null;
    private AutoCompleteTextView odm = null;
    private AutoCompleteTextView composition = null;
    private TextView counttxt = null;
    private List<RowItemVaccination> rowItems = null;
    private final List<String> list = new ArrayList<>();
    private final List<String> listcnt = new ArrayList<>();
    private final ArrayList<DrugItem> drugItemList = new ArrayList<>();
    private Button search = null;
    private Button clear = null;
    private RecyclerView medlist = null;
    //Testing datatable layout
    TableRow tablerw = null;
    private DrugRecylerAdapter drugRecylerAdapter = null;
    LinearLayoutManager mLayoutManager = null;


    private final List<String> list1 = new ArrayList<>();
    private final List<String> list2 = new ArrayList<>();
    private final List<String> list3 = new ArrayList<>();
    private final List<String> list4 = new ArrayList<>();
    private final List<String> list5 = new ArrayList<>();
    private final List<String> list6 = new ArrayList<>();
    private Typeface tfavv = null;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    // #######################################################################################################
    private GridLayoutManager lLayout = null;

    public Cims() {
    }

    // #######################################################################################################
    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cims);

        try {

            GETINITIALIZE();

            CONTROLLISTENERS();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void LoadBack() {

        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

        BaseConfig.Druglistselindex = "";

    }

    private void GETINITIALIZE() {


        BaseConfig.welcometoast = 0;

        ButterKnife.bind(Cims.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.drug_dir2)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

       // toolbar.setBackgroundColor(Color.parseColor(Cims.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(Cims.this, null));

        toolbarBack.setOnClickListener(view -> LoadBack());


        rowItems = new ArrayList<>();

        search = findViewById(R.id.button1);
        clear = findViewById(R.id.button2);
        medlist = findViewById(R.id.listView1);

        lLayout = new GridLayoutManager(Cims.this, 2);
        medlist.setHasFixedSize(true);
        medlist.setLayoutManager(lLayout);
        medlist.setNestedScrollingEnabled(false);
        medlist.setItemAnimator(new DefaultItemAnimator());


        assert medlist != null;

        counttxt = findViewById(R.id.textView7);

        system = findViewById(R.id.AutoCompleteTextView1);
        system.setThreshold(1);
        druggeneric = findViewById(R.id.AutoCompleteTextView2);
        druggeneric.setThreshold(1);
        chemicalname = findViewById(R.id.AutoCompleteTextView3);
        chemicalname.setThreshold(1);
        brandname = findViewById(R.id.AutoCompleteTextView4);
        brandname.setThreshold(1);
        odm = findViewById(R.id.AutoCompleteTextView5);
        odm.setThreshold(1);
        composition = findViewById(R.id.AutoCompleteTextView6);
        composition.setThreshold(1);

        counttxt.setText(R.string.total_records);


        if (!BaseConfig.Druglistselindex.isEmpty()) {
            LoadBackData();
        }


        //Loading cims1 data background....
        objbgtsk1.execute();
        objbgtsk2.execute();
        objbgtsk3.execute();
        objbgtsk4.execute();
        objbgtsk5.execute();
        objbgtsk6.execute();

    }

    private final void LoadBackData() {
        system.setText(BaseConfig.b.getString("system"));
        druggeneric.setText(BaseConfig.b.getString("druggeneric"));
        chemicalname.setText(BaseConfig.b.getString("chemicalname"));
        brandname.setText(BaseConfig.b.getString("brandname"));
        odm.setText(BaseConfig.b.getString("odm"));
        composition.setText(BaseConfig.b.getString("composition"));

        SelectedDrugDetails();

        search.requestFocus();

    }

    // #######################################################################################################
    private void CONTROLLISTENERS() {


        search.setOnClickListener(v -> {
            if (system.getText().toString().isEmpty() && druggeneric.getText().toString().isEmpty() && chemicalname.getText().toString().isEmpty() && brandname.getText().toString().isEmpty() && odm.getText().toString().isEmpty() && composition.getText().toString().isEmpty()) {
                BaseConfig.showSimplePopUp("Enter values for drug informations", "Warning", context, R.drawable.ic_warning_black_24dp, R.color.orange_500);
            } else {
                SelectedDrugDetails();
            }

            BaseConfig.HideKeyboard(Cims.this);

        });


        clear.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            system.setText("");
            druggeneric.setText("");
            chemicalname.setText("");
            brandname.setText("");
            odm.setText("");
            composition.setText("");
            counttxt.setText("Total Records :");
            list.clear();

            BaseConfig.Druglistselindex = "";
        });


    }

    // =================================
    private final void SelectedDrugDetails() {
        SQLiteDatabase db = BaseConfig.GetDb();//);
        String WhereCondition = "";

        if (!system.getText().toString().isEmpty()) {
            WhereCondition = "where SYSTEM='" + system.getText().toString() + '\'';


        }

        if (!druggeneric.getText().toString().isEmpty()) {
            WhereCondition = !system.getText().toString().equals("") ? WhereCondition + "and DRUGGENERICNAME='" + druggeneric.getText().toString() + "'" : "where DRUGGENERICNAME='" + druggeneric.getText().toString() + "'";
        }

        if (!chemicalname.getText().toString().isEmpty()) {
            WhereCondition = !system.getText().toString().equals("") || !druggeneric.getText().toString().equals("") ? WhereCondition + "and CHEMICALNAME='" + chemicalname.getText().toString() + "'" : "where CHEMICALNAME='" + chemicalname.getText().toString() + "'";
        }

        if (!brandname.getText().toString().isEmpty()) {
            WhereCondition = !system.getText().toString().equals("") || !druggeneric.getText().toString().equals("") || !chemicalname.getText().toString().equals("") ? WhereCondition + "and MARKETNAMEOFDRUG='" + brandname.getText().toString() + "'" : "where MARKETNAMEOFDRUG='" + brandname.getText().toString() + "'";
        }

        if (!odm.getText().toString().isEmpty()) {
            WhereCondition = !system.getText().toString().equals("") || !druggeneric.getText().toString().equals("") || !chemicalname.getText().toString().equals("") || !brandname.getText().toString().equals("") ? WhereCondition + "and PHARMACOMPANY='" + odm.getText().toString() + "'" : "where PHARMACOMPANY='" + odm.getText().toString() + "'";
        }

        if (!composition.getText().toString().isEmpty()) {
            WhereCondition = !system.getText().toString().equals("") || !druggeneric.getText().toString().equals("") || !chemicalname.getText().toString().equals("") || !brandname.getText().toString().equals("") || !odm.getText().toString().equals("") ? WhereCondition + "and COMPOSITION='" + composition.getText().toString() + "'" : "where COMPOSITION='" + composition.getText().toString() + "'";
        }

        String Query = "select distinct MARKETNAMEOFDRUG,serverid,SYSTEM,PHARMACOMPANY,DOSAGE from cims1 " + WhereCondition + ';';
        Cursor c = db.rawQuery(Query, null);

        int count = 1;
        list.clear();
        listcnt.clear();
        drugItemList.clear();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DrugItem item = new DrugItem();
                    item.setBrandName(c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")));
                    item.setDosage(c.getString(c.getColumnIndex("DOSAGE")));
                    item.setPharmaCompany(c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")));
                    item.setSystem(c.getString(c.getColumnIndex("SYSTEM")));
                    item.setServerid(c.getString(c.getColumnIndex("serverid")));
                    drugItemList.add(item);
                    String pdtls = String.valueOf(count) + ". \t" + c.getString(c.getColumnIndex("SYSTEM")) + " \t- \t" + c.getString(c.getColumnIndex("MARKETNAMEOFDRUG")) + " \t- \t" + c.getString(c.getColumnIndex("PHARMACOMPANY")) + " \t- \t" + c.getString(c.getColumnIndex("DOSAGE"));//+" - "+c.getString(c.getColumnIndex("MARKETNAMEOFDRUG"));
                    String dataindx = c.getString(c.getColumnIndex("serverid"));

                    list.add(pdtls);

                    listcnt.add(dataindx);

                    count++;

                } while (c.moveToNext());
            }
        }


        ArrayAdapter<String> CountryDataAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, android.R.id.text1, list);
        CountryDataAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        drugRecylerAdapter = new DrugRecylerAdapter(drugItemList);

        assert medlist != null;


        medlist.setAdapter(drugRecylerAdapter);

        counttxt.setText(String.format("%s %s", getString(R.string.total_records_txt), String.valueOf(list.size())));
        c.close();
        db.close();

        if (String.valueOf(list.size()).equals("0")) {

            Toast.makeText(this, getResources().getString(R.string.no_details), Toast.LENGTH_SHORT).show();
        }

    }

    // #######################################################################################################
    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;
        LoadBack();
    }

    private void LoadData(AutoCompleteTextView autotxt, Context cntxt, String Query, int id) {
        // TODO Auto-generated method stub
        ArrayList<String> spnlist1;

        SQLiteDatabase db = BaseConfig.GetDb();
        spnlist1 = new ArrayList<>();

        Cursor c = db.rawQuery(Query, null);

        if (id == 1) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list1.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 2) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list2.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 3) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list3.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 4) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list4.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 5) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list5.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        if (id == 6) {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String counrtyname = c.getString(c.getColumnIndex("dvalue"));
                        list6.add(counrtyname);

                    } while (c.moveToNext());
                }

            }
        }

        c.close();
        db.close();

    }

    private final void spinner2meth(final Context cntxt, List<String> list, AutoCompleteTextView spinnertxt) {
        // TODO Auto-generated method stub
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(cntxt,/*android.R.layout.simple_spinner_dropdown_item*/R.layout.simple_list, list) {
            @NonNull
            public View getView(int position, View convertView, @NonNull android.view.ViewGroup parent) {

                TextView v = (TextView) super.getView(position, convertView, parent);

                v.setTextSize(14.0F);
                return v;
            }

            public View getDropDownView(int position, View convertView, @NonNull android.view.ViewGroup parent) {
                TextView v = (TextView) super.getView(position, convertView, parent);
                v.setTypeface(tfavv);
                //v.setTextColor(Color.BLACK);
                v.setTextSize(14.0F);
                return v;
            }
        };

        adapter.setDropDownViewResource(R.layout.simple_list);

        spinnertxt.setAdapter(adapter);

    }


    //**********************************************************************************************************
    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue1 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue1() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 1.0");

            spinner2meth(Cims.this, list1, system);
            /*

             */


            objbgtsk1.cancel(true);
            objbgtsk1 = new DownloadFilesIssue1();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 1.0");

            LoadData(system, context, "select distinct SYSTEM  as dvalue from cims1  where SYSTEM!='' order by SYSTEM;", 1);
            /*LoadData(druggeneric,context,"select distinct DRUGGENERICNAME  as dvalue from cims1  where DRUGGENERICNAME!='' order by DRUGGENERICNAME;",2);


             */

            return null;
        }


    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue2 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue2() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 2.0");

            spinner2meth(Cims.this, list2, druggeneric);

            objbgtsk2.cancel(true);
            objbgtsk2 = new DownloadFilesIssue2();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 2.0");

            LoadData(druggeneric, context, "select distinct DRUGGENERICNAME  as dvalue from cims1  where DRUGGENERICNAME!='' order by DRUGGENERICNAME;", 2);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue3 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue3() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 3.0");
            spinner2meth(Cims.this, list3, chemicalname);
            objbgtsk3.cancel(true);
            objbgtsk3 = new DownloadFilesIssue3();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 3.0");

            LoadData(chemicalname, context, "select distinct CHEMICALNAME  as dvalue from cims1 where CHEMICALNAME!='' order by CHEMICALNAME;", 3);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue4 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue4() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 4.0");

            spinner2meth(Cims.this, list4, brandname);


            objbgtsk4.cancel(true);
            objbgtsk4 = new DownloadFilesIssue4();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 4.0");

            LoadData(brandname, context, "select distinct MARKETNAMEOFDRUG  as dvalue from cims1 where MARKETNAMEOFDRUG!='' order by MARKETNAMEOFDRUG;", 4);

            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue5 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue5() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 5.0");

            spinner2meth(Cims.this, list5, odm);

            objbgtsk5.cancel(true);
            objbgtsk5 = new DownloadFilesIssue5();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 5.0");

            LoadData(odm, context, "select distinct PHARMACOMPANY  as dvalue from cims1 where PHARMACOMPANY!='' order by PHARMACOMPANY;", 5);


            return null;
        }

    }


    //Background Task for sms notification receiving
    //**********************************************************************************************************
    private class DownloadFilesIssue6 extends AsyncTask<Void, Void, Void> {
        private DownloadFilesIssue6() {
        }

        protected final void onPostExecute(Void result) {
            //Log.e("Drug Background", "Testing Drug Bk 6.0");

            spinner2meth(Cims.this, list6, composition);


            objbgtsk6.cancel(true);
            objbgtsk6 = new DownloadFilesIssue6();
        }

        @Override
        protected final Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            //Log.e("Drug Background", "Testing Drug Bk 6.0");

            LoadData(composition, context, "select distinct COMPOSITION  as dvalue from cims1 where COMPOSITION!='' order by COMPOSITION;", 6);

            return null;
        }

    }

    //**********************************************************************************************************
}
