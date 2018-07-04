package kdmc_kumar.MyPatient_Module;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.DataObject;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.RowItemVaccination;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.Validation1;


public class Patient_Immunization extends Fragment {

    static final int DATE_DIALOG_ID = 1;
    private static final String TAG = "MainActivity";
    public static int mYear = 0;
    public static int mMonth = 0;
    public static int mDay = 0;
    public static int mcYear = 0;
    public static int mcDay = 0;
    public static int mcMonth = 0;
    // Declaration -------------------
    BaseConfig bcnfg = new BaseConfig();
    // /////////////////////////////////////////
    ArrayList<HashMap<String, String>> titles_list = null;
    ArrayAdapter<RowItemVaccination> adapter = null;
    private List<DataObject> filteredList = null;
    ArrayList<RowItemVaccination> rowItems = null;
    // /////////////////////////////////////////
    private ImageView imgNoMedia = null;
    TextView info = null;
    // /////////////////////////////////////////
    ListView savedvaccineList = null;
    private GridLayoutManager lLayout = null;

    /*// RecycleView adapter object
    private RecyclerAdapter mAdapter;*/
    private EditText srchbx = null;
    Button search = null;
    Toolbar toolbar = null;
    private RecyclerView recyclerView = null;
    private String BUNDLE_PATIENT_ID = null;
    private ArrayList<DataObject> results = new ArrayList<>();
    // List of all dictionary words
    private ArrayList<DataObject> dictionaryWords = null;
    // RecycleView adapter object
    private SimpleItemRecyclerViewAdapter mAdapter = null;

    public Patient_Immunization() {
    }

    // /////////////////////////////////////////////////////////////////////////////
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState) {

        final View rootView = inflater.inflate(
                R.layout.fragment_mypatient_vaccinationfragment, container,
                false);

        Bundle args = getArguments();
        BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);


        GetIniliz(rootView);


        srchbx = rootView.findViewById(R.id.edt);
        // search suggestions using the edittext widget
        srchbx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                //Log.e("onTextChanged: ", String.valueOf(dictionaryWords));


                mAdapter.getFilter().filter(charSequence.toString());


            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        return rootView;
    }


    //**********************************************************************************************

    private void GetIniliz(View rootView) {
        // TODO Auto-generated method stub
        imgNoMedia = rootView.findViewById(R.id.imgNoMedia);
        imgNoMedia.setVisibility(View.GONE);


        recyclerView = rootView.findViewById(R.id.listVaccination);

        dictionaryWords = getDataSet();
        filteredList = new ArrayList<>();
        filteredList.addAll(dictionaryWords);

        //recyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(getActivity());
        //recyclerView.setLayoutManager(mLayoutManager);


        lLayout = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(lLayout);
        recyclerView.setNestedScrollingEnabled(false);


        assert recyclerView != null;
        mAdapter = new SimpleItemRecyclerViewAdapter(filteredList);
        recyclerView.setAdapter(mAdapter);


        srchbx = rootView.findViewById(R.id.edt);
        // search suggestions using the edittext widget
        srchbx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                mAdapter.getFilter().filter(charSequence.toString());


                dictionaryWords = getDataSetSearch(charSequence.toString());
                filteredList = new ArrayList<>();
                filteredList.addAll(dictionaryWords);

                lLayout = new GridLayoutManager(getActivity(), 2);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(lLayout);
                recyclerView.setNestedScrollingEnabled(false);


                assert recyclerView != null;
                mAdapter = new SimpleItemRecyclerViewAdapter(filteredList);
                recyclerView.setAdapter(mAdapter);

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


    }

    private ArrayList<DataObject> getDataSet() {

        results = new ArrayList<>();

        String query;

        query = "select vaccinename,schedule,givendt,weight from vaccination where patid='"
                + BUNDLE_PATIENT_ID + '\'';


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DataObject obj = new DataObject();
                    obj.setVaccinename(c.getString(c.getColumnIndex("vaccinename")));
                    obj.setGivendt(c.getString(c.getColumnIndex("givendt")));
                    obj.setSchedule(c.getString(c.getColumnIndex("schedule")));
                    obj.setWeight(c.getString(c.getColumnIndex("weight")));


                    results.add(obj);

                }
                while (c.moveToNext());
            }
        }

        c.close();
        db.close();


        return results;

    }

    private ArrayList<DataObject> getDataSetSearch(String str) {

        results = new ArrayList<>();

        String query;

        query = "select vaccinename,schedule,IFNULL(givendt,'')as givendt,IFNULL(weight,'')as weight from vaccination where patid='"
                + BUNDLE_PATIENT_ID + '\'';


        SQLiteDatabase db = BaseConfig.GetDb();//);
        Cursor c = db.rawQuery(query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    DataObject obj = new DataObject();
                    obj.setVaccinename(c.getString(c.getColumnIndex("vaccinename")));
                    obj.setGivendt(BaseConfig.CheckDBString(c.getString(c.getColumnIndex("givendt"))));
                    obj.setSchedule(c.getString(c.getColumnIndex("schedule")));
                    obj.setWeight(BaseConfig.CheckDBString(c.getString(c.getColumnIndex("weight"))));

                    if (c.getString(c.getColumnIndex("vaccinename")).toLowerCase().startsWith(str)) {
                        results.add(obj);
                    }


                }
                while (c.moveToNext());
            }
        }

        c.close();
        db.close();


        return results;

    }

    //**********************************************************************************************
    //Custom Filterable Recylcer View
    /*
    @Muthukumar
    14-7-16
    reference:
    http://inducesmile.com/android/android-recyclerview-filter-search-functionality-with-search-suggest/
     */

    // create a custom RecycleViewAdapter class

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> implements Filterable {

        Context context = null;
        final CustomFilter mFilter;
        RecyclerView myrecyler = null;
        View view = null;
        private final List<DataObject> mValues;


        SimpleItemRecyclerViewAdapter(List<DataObject> items) {
            mValues = items;
            mFilter = new CustomFilter(SimpleItemRecyclerViewAdapter.this);
        }


        @NonNull
        @Override
        public final ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listrowimmunization, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public final void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            DataObject rowItem = mValues.get(position);

            holder.vaccinename.setText(BaseConfig.CheckDBString(rowItem.getVaccinename()));
            holder.schedule.setText(BaseConfig.CheckDBString(rowItem.getSchedule()));
            holder.givenon.setText(BaseConfig.CheckDBString(rowItem.getGivendt()));
            holder.weight.setText(BaseConfig.CheckDBString(rowItem.getWeight()));

            if (holder.givenon.getText().equals("")) {
                holder.vaccinename.setTextColor(Color.RED);
                holder.schedule.setTextColor(Color.RED);
                holder.givenon.setTextColor(Color.RED);
                holder.weight.setTextColor(Color.RED);
            } else {
                holder.vaccinename.setTextColor(Color.BLUE);
                holder.schedule.setTextColor(Color.BLUE);
                holder.givenon.setTextColor(Color.BLUE);
                holder.weight.setTextColor(Color.BLUE);
            }

            ////Log.e("RowItem Vaccination: ", rowItem.getGivendt().toString()+"/"+rowItem.getWeight().toString());


            holder.card_view.setOnClickListener(v -> {


                SimpleDateFormat dateformt = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);
                Date date = new Date();
                String dttm = dateformt.format(date);

                LayoutInflater li = LayoutInflater.from(v.getContext());
                View promptsView = li.inflate(R.layout.vaccipopup, null);

                ////Log.e("RowItem Vaccination: ", rowItem.getGivendt().toString()+"/"+rowItem.getWeight().toString());

                if (holder.givenon.getText().equals("")
                        && holder.weight.getText().equals("")) {

                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            v.getContext());

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final Button cancelbtn = promptsView
                            .findViewById(R.id.cancel_button);
                    final Button replybtn = promptsView
                            .findViewById(R.id.ok_button);
                    TextView pid, pname, agegenn;
                    pid = promptsView.findViewById(R.id.textView5);
                    pname = promptsView.findViewById(R.id.textView6);
                    agegenn = promptsView
                            .findViewById(R.id.textView7);

                    pname.setText(rowItem.getVaccinename());
                    pid.setText(rowItem.getSchedule());

                    String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
                    agegenn.setText(Patient_AgeGender);

                    final EditText vn;
                    final EditText sch;
                    final EditText gvon;
                    final EditText wei;
                    vn = promptsView.findViewById(R.id.editText1);
                    sch = promptsView.findViewById(R.id.editText2);
                    gvon = promptsView.findViewById(R.id.editText3);
                    wei = promptsView.findViewById(R.id.editText4);

                    vn.setText(rowItem.getVaccinename());
                    sch.setText(rowItem.getSchedule());

                    gvon.setText(dttm);
                    if (holder.weight.getText().toString().equals("null")) {
                        wei.setText("");
                    } else {

                        wei.setText(holder.weight.getText().toString());
                    }

                    // create alert dialog
                    final AlertDialog alertDialog = alertDialogBuilder.create();

                    alertDialog.show();

                    replybtn.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {
                            // TODO Auto-generated method stub

                            if (gvon.getText().length() > 0) {
                                if (wei.getText().length() > 0) {
                                    SaveLocal(vn, sch, gvon, wei);
                                    alertDialog.cancel();
                                } else {
                                    wei.setError("Required");
                                }
                            } else {
                                gvon.setError("Required");

                            }

                        }

                        private void SaveLocal(EditText vn, EditText sch,
                                               EditText gvon, EditText wei) {
                            // TODO Auto-generated method stub
                            try {
                                if (checkValidation(vn, sch, gvon, wei))

                                    submitForm(vn, sch, gvon, wei);
                                else

                                    Toast.makeText(getContext(), "check for missing & valid Data", Toast.LENGTH_SHORT).show();
                                //BaseConfig.SnackBar(this,  "check for missing & valid Data", parentLayout);


                            } catch (RuntimeException ignored) {
                                // e.printStackTrace();

                            }
                        }

                        private void submitForm(EditText vn, EditText sch,
                                                EditText gvon, EditText wei) {

                            SimpleDateFormat dateformt = new SimpleDateFormat(
                                    "dd/MM/yyyy HH:mm:ss", Locale.ENGLISH);
                            Date date = new Date();
                            String dttm = dateformt.format(date);

                            final String Insert_Query = "update vaccination set givendt='"
                                    + gvon.getText().toString()
                                    + "',weight='"
                                    + wei.getText().toString()
                                    + "',dt='"
                                    + dttm
                                    + "',isactive='1',Isupdate='1',duedt = '00/00/0000 00:00:00',HID = '" + BaseConfig.HID + '\''
                                    + "where vaccinename='"
                                    + vn.getText().toString()
                                    + "' and schedule='"
                                    + sch.getText().toString()
                                    + "' and patid='"
                                    + BUNDLE_PATIENT_ID
                                    + "' and isactive='0' and Isupdate='0'";

                            BaseConfig.SaveData(Insert_Query);

                            showSimplePopUpExit();
                        }

                        private void showSimplePopUpExit() {
                            // TODO Auto-generated method stub

                            final AlertDialog.Builder helpBuilder = new AlertDialog.Builder(
                                    v.getContext());
                            helpBuilder.setTitle("Information");
                            helpBuilder.setMessage("Vaccination Added Successfully");
                            helpBuilder.setPositiveButton("Ok",
                                    (dialog, which) -> {


                                        dialog.cancel();

                                        dictionaryWords = getDataSet();
                                        filteredList = new ArrayList<>();
                                        filteredList.addAll(dictionaryWords);

                                        lLayout = new GridLayoutManager(getActivity(), 2);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(lLayout);
                                        recyclerView.setNestedScrollingEnabled(false);


                                        assert recyclerView != null;
                                        mAdapter = new SimpleItemRecyclerViewAdapter(filteredList);
                                        recyclerView.setAdapter(mAdapter);

                                    });

                            // Remember, create doesn't show the dialog
                            AlertDialog helpDialog = helpBuilder.create();
                            helpDialog.show();

                        }

                        private boolean checkValidation(EditText vn,
                                                        EditText sch, EditText gvon, EditText wei) {
                            boolean ret = true;
                            if (!Validation1.hasText(vn)) {
                                ret = false;
                            }

                            return ret;
                        }
                    });

                    cancelbtn.setOnClickListener(arg0 -> {
                        // TODO Auto-generated method stub

                        alertDialog.cancel();
                    });

                } else {

                    Toast.makeText(getContext(), "Already Vaccination Given", Toast.LENGTH_SHORT).show();
                    //BaseConfig.SnackBar(this,  "Already Vaccination Given", parentLayout);

                }


            });
        }


        @Override
        public final int getItemCount() {
            return mValues.size();
        }


        @Override
        public final Filter getFilter() {
            return mFilter;
        }


        public class ViewHolder extends RecyclerView.ViewHolder {


            public DataObject mItem = null;
            final TextView vaccinename;
            final TextView schedule;
            final TextView givenon;
            final TextView weight;
            final CardView card_view;


            ViewHolder(View view) {
                super(view);


                vaccinename = view.findViewById(R.id.textView1);
                schedule = view.findViewById(R.id.textView2);
                givenon = view.findViewById(R.id.textView3);
                weight = view.findViewById(R.id.textView4);
                card_view = view.findViewById(R.id.card_view);


            }


        }


        class CustomFilter extends Filter {

            private final SimpleItemRecyclerViewAdapter mAdapter;


            private CustomFilter(SimpleItemRecyclerViewAdapter mAdapter) {
                super();
                this.mAdapter = mAdapter;

            }

            @Override
            protected final FilterResults performFiltering(CharSequence charSequence) {
                filteredList = new ArrayList<>();
                filteredList.clear();
                final FilterResults results = new Filter.FilterResults();
                if (charSequence.length() == 0) {
                    filteredList.addAll(dictionaryWords);
                } else {
                    final String filterPattern = charSequence.toString().toLowerCase().trim();

                    for (Iterator<DataObject> iterator = dictionaryWords.iterator(); iterator.hasNext(); ) {
                        DataObject mWords = iterator.next();
                        if (mWords.getVaccinename().toLowerCase().startsWith(filterPattern)) {
                            filteredList.add(mWords);
                        }
                    }
                }


                System.out.println("Count Number (filteredList)" + filteredList.size());

                results.values = filteredList;
                results.count = filteredList.size();


                return results;
            }

            @Override
            protected final void publishResults(CharSequence charSequence, FilterResults filterResults) {
                System.out.println("Count Number " + ((List<DataObject>) filterResults.values).size());
                this.mAdapter.notifyDataSetChanged();


                if (filteredList.size() > 0) {
                    System.out.println("Count Number " + filteredList.get(0).getVaccinename());
                    //Log.e("Total : ",String.valueOf( filteredList.size()));
                    // Count.setTextColor(getResources().getColor(R.color.colorPrimary));
                } else if (filteredList.size() == 0 && ((List<DataObject>) filterResults.values).size() == 0) {

                    //Log.e("filteredList","No search keyword found..click refresh");
                    //Count.setTextColor(getResources().getColor(R.color.red_btn_bg_color));
                } else {
                    //Log.e("filteredList","Total : " + filteredList.size());
                    //Count.setTextColor(getResources().getColor(R.color.colorPrimary));
                }

            }

        }
    }
    //**********************************************************************************************


}
