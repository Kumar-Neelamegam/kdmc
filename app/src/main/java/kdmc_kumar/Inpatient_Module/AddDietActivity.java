package kdmc_kumar.Inpatient_Module;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.Addgetset;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Adapters_GetterSetter.DietAdapter;
import kdmc_kumar.Core_Modules.BaseConfig;

public class AddDietActivity extends AppCompatActivity {

    public static TextView calories_total = null;
    private final ArrayList<Addgetset> rowItems = new ArrayList<>();
    public Button cancel_btns = null;
    DietAdapter adapter2 = null;
    BaseConfig bc = null;
    List<String> rowItemsId = new ArrayList<>();
    private Spinner spn_diet = null;
    private AutoCompleteTextView edt_breakfast = null;
    private Button add_button_img = null;
    private Button submit_datas = null;
    private int total = 0;
    private String[] Server_Id = null;
    private String[] Diet_items = null;
    private String PATIENT_ID = null;
    private String AlreadYLoadedDietItemList = "";
    private List<Addgetset> movieList = new ArrayList<>();
    private RecyclerView recyclerView = null;
    private DietAdapter mAdapter = null;

    public AddDietActivity() {
    }

    private static final int randomIdGen() {
        Random r = new Random(System.currentTimeMillis());
        return 10000 + r.nextInt(20000);
    }

    private static final String getFood_id(String food_name) {
        @Nullable String re_value = "";

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

            Cursor c = db.rawQuery("select food_name,calories,food_id from Mstr_foodItems where food_name='" + food_name + "'  and  (food_name!=null or food_name!='')  and (IsActive='true' or IsActive='1' or IsActive='True')", null);

            c.moveToFirst();

            String value = c.getString(c.getColumnIndex("food_id"));
            //Log.e("calori", value);
            if (value.isEmpty() || value == null) {
                re_value = null;

                return re_value;
            } else if (!value.isEmpty() && !value.isEmpty()) {
                re_value = c.getString(c.getColumnIndex("food_id")) + '/' + c.getString(c.getColumnIndex("calories"));

                return re_value;
            }
            c.close();
            return re_value;
        } catch (RuntimeException ignored) {
            //Log.e("Getfood_id method", ex.toString());
        }
        return re_value;
    }

    private static final String getFood_calc(String food_id) {
        @Nullable String re_value = " / ";

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

            Cursor c = db.rawQuery("select food_name,calories,food_id from Mstr_foodItems where food_id='" + food_id + "' and  (food_name!=null or food_name!='')  and (IsActive='true' or IsActive='1' or IsActive='True')", null);

            c.moveToFirst();

            String value = c.getString(c.getColumnIndex("food_name"));
            //Log.e("calori", value);
            if (value.isEmpty() || value == null) {
                re_value = null;

                return re_value;
            } else if (!value.isEmpty() && !value.isEmpty()) {
                re_value = c.getString(c.getColumnIndex("food_name")) + '/' + c.getString(c.getColumnIndex("calories"));

                return re_value;
            }
            c.close();
            return re_value;
        } catch (RuntimeException ignored) {
            //Log.e("Getfood_id method", ex.toString());
        }
        return re_value;
    }

    private static final void removeOldDiet(String Patient_ids) {
        try {


            SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

            ///
            //
            String Query = "UPDATE diet_entry SET isActive = '0' WHERE  pat_id='" + Patient_ids + "' ";

            ///  String Query = "delete from diet_entry where  pat_id ='" + Patient_ids + "'";

            db.execSQL(Query);
            //Log.e("delete Query", Query);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void CreateMenu(Menu menu) {

        MenuItem item1 = menu.add(0, 0, 0, "Item 1");
        {
            // --Copio las imagenes que van en cada item
            item1.setIcon(R.drawable.prev_icon);
            item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }

        MenuItem item2 = menu.add(0, 1, 1, "Item 2");
        {

            item2.setIcon(R.drawable.home_ico);
            item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

        }


    }

    private static final String selectDietName(int diet_id) {

        String str1 = "";
        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);


        String Query = "select SessionName from Mstr_dietSession where ServerId='" + diet_id + "' ";


        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str1 = c.getString(c.getColumnIndex("SessionName"));

                    return str1;

                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();

        return str1;
    }

    private static final String selectDietServerId(String sessionname) {

        String str1 = "";
        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);


        String Query = "select ServerId from Mstr_dietSession where SessionName='" + sessionname.trim() + "' ";


        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str1 = c.getString(c.getColumnIndex("ServerId"));

                    return str1;

                } while (c.moveToNext());

            }
        }
        c.close();
        db.close();

        return str1;
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_diet);


        //initilize
        init();

        //getfooditems
        getfoodItems();

        //Listeneners
        listeners();

        rowItems.clear();
        selectDietList(BaseConfig.Patent_Id);


        String diet_list[] = {"Select Diet", "Diet1", "Diet2", "Diet3"};

        getdietitemlist();

        ArrayAdapter<String> aa = new ArrayAdapter<>(AddDietActivity.this, android.R.layout.simple_list_item_1, Diet_items);

        spn_diet.setAdapter(aa);

        calories_total.setText("0");

    }

    private void listeners() {


        cancel_btns.setOnClickListener(view -> {
            AddDietActivity.this.finish();

        });
        spn_diet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                edt_breakfast.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        edt_breakfast.setThreshold(1);


        submit_datas.setOnClickListener(view -> {

            Print_AdapterDatas();
            if (mAdapter.getItemCount() < 0) {
                Toast.makeText(AddDietActivity.this, "Please Add Diet entry.....", Toast.LENGTH_SHORT).show();

            } else if (mAdapter.getItemCount() > 0) {

                try {

                    //Remove Previous rows
                    removeOldDiet(BaseConfig.Patent_Id);

                    int randomId = randomIdGen();


                    int tot = 0;
                    for (int i = 0; i < mAdapter.getItemCount(); i++) {

                        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

                        String value[] = getFood_id(mAdapter.moviesList.get(i).getFood_name()).split("/");

                        tot += Integer.valueOf(mAdapter.moviesList.get(i).getCalc()).intValue();

                        Calendar c = Calendar.getInstance();

                        ContentValues values = new ContentValues();
                        values.put("pat_id", PATIENT_ID);
                        String dietIdVar = "";
                        if (AlreadYLoadedDietItemList.length() > 0) {
                            String dietIdVar1 = AlreadYLoadedDietItemList;
                            /// values.put("diet_id", AlreadYLoadedDietItemList);
                        } else if (AlreadYLoadedDietItemList.length() == 0) {
                            String dietIdVar1 = String.valueOf(randomId);

                        }
                        values.put("diet_id", Integer.valueOf(randomId));
                        values.put("doc_id", BaseConfig.doctorId);
                        values.put("food_id", value[0]);
                        values.put("ActDate", BaseConfig.DeviceDate());
                        values.put("IsActive", "1");

                        values.put("IsUpdate", "0");
                        values.put("IsDelete", "0");
                        values.put("HID", BaseConfig.HID);
                        values.put("diet_session_id", selectDietServerId(mAdapter.moviesList.get(i).getDiet_name()));
                        db.delete("diet_entry", "pat_id = '" + PATIENT_ID + "' and diet_id != '" + randomId + '\'', null);
                        db.insert("diet_entry", null, values);
                        db.close();


                    }


                    selectDietList(BaseConfig.Patent_Id);
                    calories_total.setText(String.valueOf(tot));
                    showDialog();

                    View view2 = AddDietActivity.this.getCurrentFocus();
                    if (view2 != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                    }


                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (Exception ignored) {
                    //Log.e("Insert db", ex.toString());
                }
                /* popup(mAdapter.getItemCount());*/
            }
            selectDietList(PATIENT_ID);

        });
        add_button_img.setOnClickListener(view -> {
            if (spn_diet.getSelectedItemPosition() == 0) {
                Toast.makeText(AddDietActivity.this, "Please Select Diet.......", Toast.LENGTH_SHORT).show();
                // BaseConfig.SnackBar(AddDietActivity.this,  "Please Select Diet.......", parentLayout);

                spn_diet.setFocusable(true);
            } else if (edt_breakfast.getText().toString().equalsIgnoreCase("") || edt_breakfast.getText().toString().isEmpty() || edt_breakfast.getText().toString().equalsIgnoreCase(null)) {
                Toast.makeText(AddDietActivity.this, "Enter Food name........", Toast.LENGTH_SHORT).show();

                edt_breakfast.setFocusable(true);
            } else if (spn_diet.getSelectedItemPosition() != 0 && !edt_breakfast.getText().toString().isEmpty()) {
                Insertlistview(spn_diet.getSelectedItem().toString(), edt_breakfast.getText().toString(), null);

                // Toast.makeText(AddDietActivity.this, spn_diet.getSelectedItem() + "      " + edt_breakfast.getText().toString(), Toast.LENGTH_SHORT).show();

                mAdapter = new DietAdapter(rowItems, AddDietActivity.this);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mAdapter);

                View view2 = AddDietActivity.this.getCurrentFocus();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }


                /**

                 clearly item


                 */
                spn_diet.setSelection(0);
                edt_breakfast.setText("");
                calcDiet();


            }
        });

    }

    private void init() {
        calories_total = findViewById(R.id.calories_total_tv);

        recyclerView = findViewById(R.id.card_recycler_view);
        submit_datas = findViewById(R.id.submit_datas);
        spn_diet = findViewById(R.id.spn_select_diet_new);

        edt_breakfast = findViewById(R.id.edt_breakfast);

        add_button_img = findViewById(R.id.add_button_img);
        cancel_btns = findViewById(R.id.cancel_btns);

        Bundle b = getIntent().getExtras();

        PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

    }

    private final void showDialog() {
        /// BaseConfig.showSimplePopUp("","Diet Plan",);

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(AddDietActivity.this);
        helpBuilder.setTitle("Diet Plan");
        helpBuilder.setMessage("Patient Diet Items Updated");


        helpBuilder.setPositiveButton("Ok",
                (dialog, which) -> {
                    finish();


                });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();


    }

    private void Print_AdapterDatas() {


        for (int i = 0; i < mAdapter.getItemCount(); i++) {

            Log.i("Select Diet", mAdapter.moviesList.get(i).getDiet_name());
            Log.i("Select Diet", mAdapter.moviesList.get(i).getFood_name());
            Log.i("Select Diet", mAdapter.moviesList.get(i).getCalc());


        }
    }

    @Override
    protected final void onResume() {
        selectDietList(PATIENT_ID);
        super.onResume();
    }

    // #######################################################################################################
    @Override
    public final void onBackPressed() {

        AddDietActivity.this.finish();


    }

    private final void Insertlistview(String Diet_name, String food_name, String calc) {
        try {


            int i = 0;
            String values[] = getFood_id(food_name).split("/");
            Addgetset item = new Addgetset(Diet_name, food_name, String.valueOf(values[1]));

            rowItems.add(item);

            total += Integer.parseInt(values[1]);

            calories_total.setText(total);
            i++;


        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (Exception ignored)

        {
            //Log.e("List add", ex.toString());
        }

    }

    private final void calcDiet() {
        int totalStr = 0;
        for (int i = 0; i <= rowItems.size() - 1; i++) {
            try {

                String values[] = getFood_id(rowItems.get(i).getFood_name()).split("/");
                totalStr += Integer.parseInt(values[1]);

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (calories_total != null) {
            calories_total.setText(String.valueOf(totalStr));
        }
    }

    public final void popup(int count) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddDietActivity.this);
        alert.setTitle("Insert");
        alert.setMessage("Do you want Insert Table?");
        alert.setPositiveButton(getString(R.string.ok), (dialog, which) -> {


//                InsertDb();

            rowItems.clear();
           /* recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);

            mAdapter = new DietAdapter(rowItems);*/
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            selectDietList(PATIENT_ID);

        });
        alert.setNegativeButton("CANCEL", (dialog, which) -> dialog.cancel());
        alert.show();
    }

    public final void UpdateListView() {
        if (recyclerView != null) {
            recyclerView.setAdapter(mAdapter);
        }
    }

    private final void selectDietList(String Patient_id) {

        int calc_total = 0;
        rowItems.clear();
        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);


        String Query = "select diet_id,Id,food_id,diet_session_id from diet_entry where pat_id='" + Patient_id + "' and IsDelete='0' AND IsActive = '1' ";


        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    String str1, str2, str3, str4;

                    str4 = c.getString(c.getColumnIndex("Id"));
                    str1 = c.getString(c.getColumnIndex("food_id"));

                    int ids = Integer.parseInt(c.getString(c.getColumnIndex("diet_session_id")));
                    str2 = selectDietName(ids);
                    AlreadYLoadedDietItemList = String.valueOf(c.getString(c.getColumnIndex("diet_id")));

                    String values[] = getFood_calc(str1).split("/");

                    Addgetset item = new Addgetset(str4, str2, values[0], String.valueOf(values[1]));

                    rowItems.add(item);


                } while (c.moveToNext());

            }
        }
        c.close();
        mAdapter = new DietAdapter(rowItems, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        db.close();
        calcDiet();


    }

    private final void getfoodItems() {
        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

        Cursor c = db.rawQuery("Select food_name,calories,food_id from Mstr_foodItems where  (food_name!=null or food_name!='')  and (IsActive='true' or IsActive='1' or IsActive='True')", null);

        Server_Id = new String[c.getCount()];
        c.moveToFirst();
        for (int i = 0; i < Server_Id.length; i++) {
            Server_Id[i] = c.getString(c.getColumnIndex("food_name"));
            System.out.println(Server_Id[i]);
            c.moveToNext();
        }


        for (String aServer_Id : Server_Id) {
            System.out.println("Array Values=" + aServer_Id);
        }

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Server_Id);
        edt_breakfast.setAdapter(aa);
        c.close();
        db.close();
    }

    @Override
    public final boolean onOptionsItemSelected(MenuItem item) {

        return MenuSelecciona(item);

    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater();

        CreateMenu(menu);
        return true;
    }

    private boolean MenuSelecciona(MenuItem item) {

        switch (item.getItemId()) {

            case 0:

                Bundle b = new Bundle();
                b.putString(BaseConfig.BUNDLE_PATIENT_ID, PATIENT_ID);
                BaseConfig.globalStartIntent(AddDietActivity.this, Inpatient_Detailed_View.class, b);


                return true;
            case 1:

                BaseConfig.globalStartIntent(AddDietActivity.this, Dashboard_NavigationMenu.class, null);

                return true;

            case android.R.id.home:

                return true;

        }
        return false;
    }

    private final void getdietitemlist() {
        SQLiteDatabase db = BaseConfig.GetDb();//AddDietActivity.this);

        Cursor c = db.rawQuery("Select DISTINCT SessionName from Mstr_dietSession where IsActive = '1'", null);

        Diet_items = new String[c.getCount() + 1];
        Diet_items[0] = "Select Diet";
        c.moveToFirst();
        for (int i = 1; i <= c.getCount(); i++) {
            Diet_items[i] = c.getString(c.getColumnIndex("SessionName"));
            System.out.println(Diet_items[i]);
            c.moveToNext();
        }
        c.close();
    }
}

