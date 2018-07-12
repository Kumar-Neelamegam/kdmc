package kdmc_kumar.Inpatient_Module;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CircleImageView;

public class OperationTheater extends AppCompatActivity {


    private static final String TEMP_PHOTO_FILE_NAME = "temp_photo.jpg";
    private static final int REQUEST_CODE_GALLERY = 0x4;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    public static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private RecyclerView mRecyclerview = null;
    private LinearLayout imageNoData = null;
    private ImageView remark_image_view = null;
    private RecyclerView.LayoutManager mLayoutManager = null;
    private File mFileTemp = null;

    public OperationTheater() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operation_theater);

       // getActionBar();

       // ActionBar bar = getSupportActionBar();
//        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3d5987")));


        GetInitialize();

        ControlListener();


    }

    private void GetInitialize() {

        imageNoData =
                findViewById(R.id.no_data_image);

        mRecyclerview = findViewById(R.id.my_recycler_view);
// Camera
        String state = Environment.getExternalStorageState();
        mFileTemp = Environment.MEDIA_MOUNTED.equals(state) ? new File(Environment.getExternalStorageDirectory(),
                TEMP_PHOTO_FILE_NAME) : new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);

    }


    private void ControlListener() {
        mRecyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerview.setLayoutManager(mLayoutManager);
        mRecyclerview.setLayoutManager(mLayoutManager);
        ArrayList<DietItem> items = getDataSet();
        OperationListViewAdapter mAdapter = new OperationListViewAdapter(items, this);
        mRecyclerview.setAdapter(mAdapter);
        if (items.size() == 0) {
            imageNoData.setVisibility(View.VISIBLE);
        } else if (items.size() > 0) {
            imageNoData.setVisibility(View.GONE);
        }


    }

    private ArrayList getDataSet() {

        ArrayList results = new ArrayList<DietItem>();

        String Query = "select  * from operation_details ";


        SQLiteDatabase db = BaseConfig.GetDb();//OperationTheater.this);

        Cursor c = db.rawQuery("select  * from operation_details where doctors like '%" + BaseConfig.doctorname + "%'", null);

        c.moveToFirst();
       /* StringBuilder stringBuilder = new StringBuilder();
        boolean isThere = false;*/

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    DietItem item1 = new DietItem();
                    item1.operation_name = c.getString(c.getColumnIndex("operation_name"));
                    item1.patient_name = c.getString(c.getColumnIndex("patient_name"));
                    item1.operation_date = c.getString(c.getColumnIndex("operation_date"));
                    item1.from_time = c.getString(c.getColumnIndex("from_time"));
                    item1.to_time = c.getString(c.getColumnIndex("to_time"));
                    item1.doctors = c.getString(c.getColumnIndex("doctors"));
                    item1.operation_no = c.getString(c.getColumnIndex("operation_no"));
                    results.add(item1);

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return results;
    }

    public final void showPopup() {
        Dialog dialog = new Dialog(this);


    }

    @Override
    public final void onBackPressed() {
        // Do Here what ever you want do on back press;

        OperationTheater.this.finish();
        Intent in = new Intent(OperationTheater.this, Inpatient_List.class);
        startActivity(in);
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

    private boolean MenuSelecciona(MenuItem item) {

        switch (item.getItemId()) {

            case 0:

                BaseConfig.globalStartIntent(OperationTheater.this, Inpatient_List.class, null);


                return true;
            case 1:

                BaseConfig.globalStartIntent(OperationTheater.this, Dashboard_NavigationMenu.class, null);

                return true;

            case android.R.id.home:

                return true;

        }
        return false;
    }

    private void showPopup(final Activity context, final String operation_nam, final String time, final String valuess) {
//      LayoutInflater factory = LayoutInflater.from(this);

        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(
                R.layout.operation_remarks_layout, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        deleteDialog.setTitle("Operation Remarks");
        TextView operation_name = deleteDialogView.findViewById(R.id.operation_name);
        TextView scheduleValue = deleteDialogView.findViewById(R.id.scheduleValue);

        operation_name.setText(operation_nam);
        scheduleValue.setText(time);

        final EditText remarks = deleteDialogView.findViewById(R.id.operation_remarks_value);

        /**
         @Ponnusamy
          * 5.11.2016
          * 4:44PM
          * ***/

        //new Fields
        final EditText pre_operation_diagnosis_value = deleteDialogView.findViewById(R.id.pre_operation_diagnosis_value);
        final EditText operation_notes_value = deleteDialogView.findViewById(R.id.operation_notes_value);
        final EditText position_value = deleteDialogView.findViewById(R.id.position_value);
        final EditText procedure_value = deleteDialogView.findViewById(R.id.procedure_value);
        final EditText closure_value = deleteDialogView.findViewById(R.id.closure_value);
        final EditText post_operative_diagnosis_value = deleteDialogView.findViewById(R.id.post_operative_diagnosis_value);
        final EditText post_operative_instruction = deleteDialogView.findViewById(R.id.post_operative_instruction);

        /*****
         @Ponnusamy
          * Date 22.11.2016
          * Time 2:00
          *
          * *******/
        remark_image_view = deleteDialogView.findViewById(R.id.remark_image_view);


        remark_image_view.setOnClickListener(view -> {
            //ImagePicker Code
            add_image();

        });

        Button button_submit = deleteDialogView.findViewById(R.id.submit_pop);
        Button button_cancel = deleteDialogView.findViewById(R.id.cancel_pop);


        getPopupData(remarks, pre_operation_diagnosis_value, operation_notes_value, position_value, procedure_value, closure_value, post_operative_diagnosis_value, post_operative_instruction, valuess);

        button_cancel.setOnClickListener(view -> deleteDialog.dismiss());

        button_submit.setOnClickListener(view -> {

            try {


                if (remarks.getText().toString().isEmpty()) {
                    remarks.setError("Enter Value");
                } else if (pre_operation_diagnosis_value.getText().toString().isEmpty()) {
                    pre_operation_diagnosis_value.setError("Enter Value");
                } else if (operation_notes_value.getText().toString().isEmpty()) {
                    operation_notes_value.setError("Enter Value");
                } else if (position_value.getText().toString().isEmpty()) {
                    position_value.setError("Enter Value");
                } else if (procedure_value.getText().toString().isEmpty()) {
                    procedure_value.setError("Enter Value");
                } else if (closure_value.getText().toString().isEmpty()) {
                    closure_value.setError("Enter Value");
                } else if (post_operative_diagnosis_value.getText().toString().isEmpty()) {
                    post_operative_diagnosis_value.setError("Enter Value");
                } else if (post_operative_instruction.getText().toString().isEmpty()) {
                    post_operative_instruction.setError("Enter Value");
                } else {
                    SQLiteDatabase db = BaseConfig.GetDb();//OperationTheater.this);
                    /*

                      	`Pre_Oper_Diagnosis`	TEXT,
                     `Oper_Notes`	TEXT,
                     `Position`	TEXT,
                     `Procedure`	TEXT,
                     `Closure`	TEXT,
                     `Post_Oper_Diagnosis`	TEXT,
                     `Post_Oper_Instruction`	TEXT

                      **/


                    /******
                     Conver Image to Base 64 encod
                     @Ponnusamy
                      * Date 22.11.2016
                      * *****/
                    BitmapDrawable drawable = (BitmapDrawable) remark_image_view.getDrawable();
                    Bitmap bm = drawable.getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] b = baos.toByteArray();
                    String Remark_image_base64 = Base64.encodeToString(b, Base64.DEFAULT);


                    String Query = "UPDATE operation_details SET Remarks = '" + remarks.getText().toString() + "' , IsUpdateLocal =  '0',Pre_Oper_Diagnosis='" + pre_operation_diagnosis_value.getText().toString() + '\'' +
                            ",Oper_Notes='" + operation_notes_value.getText().toString() + "',Position='" + position_value.getText().toString() + "',Procedure='" + procedure_value.getText().toString() + '\'' +
                            ",Closure='" + closure_value.getText().toString() + "',Post_Oper_Diagnosis='" + post_operative_diagnosis_value.getText().toString() + "',Post_Oper_Instruction='" + post_operative_instruction.getText().toString() + "' , Remark_Image='" + Remark_image_base64 + "' WHERE  operation_no='" + valuess + "' ";

                    db.execSQL(Query);
                    //Log.e("delete Query", Query);

                    Toast.makeText(OperationTheater.this, "Successfully Updated Remarks", Toast.LENGTH_SHORT).show();
//                        BaseConfig.SnackBar(this,  "Successfully Updated Remarks" , parentLayout);

                    deleteDialog.dismiss();

                }


            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });


        deleteDialog.show();
    }

    private final void getPopupData(EditText remark, EditText operationdiagnosis, EditText oper_notes, EditText position, EditText procedure, EditText closure, EditText post_oper_diagno, EditText post_oper_inst, String value) {

        try {


            SQLiteDatabase db = BaseConfig.GetDb();

            //Cursor c = db.rawQuery("select  substr(Actdate,1,10) ||' - '|| mtestid  as acdate from medicaltest where Ptid='"+arry[1].trim()+"' order by id desc;", null);

            //modified on 25/02/2015
            Cursor c = db.rawQuery("select  * from operation_details where operation_no='" + value + "';", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        /**


                         `Remarks`	TEXT,
                         `IsNotified`	TEXT,
                         `IsUpdateLocal`	TEXT,
                         `Pre_Oper_Diagnosis`	TEXT,
                         `Oper_Notes`	TEXT,
                         `Position`	TEXT,
                         `Procedure`	TEXT,
                         `Closure`	TEXT,
                         `Post_Oper_Diagnosis`	TEXT,
                         `Post_Oper_Instruction`	TEXT


                         **/

                        remark.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Remarks")))));
                        operationdiagnosis.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Pre_Oper_Diagnosis")))));
                        oper_notes.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Oper_Notes")))));

                        position.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Position")))));
                        procedure.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Procedure")))));
                        closure.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Closure")))));

                        post_oper_diagno.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Post_Oper_Diagnosis")))));
                        post_oper_inst.setText(BaseConfig.CheckDBString(String.valueOf(c.getString(c.getColumnIndex("Post_Oper_Instruction")))));


                        //Set ImageView <code></code>

                        String image_value = c.getString(c.getColumnIndex("Remark_Image"));

                        byte[] decodedString = Base64.decode(image_value, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        remark_image_view.setImageBitmap(decodedByte);


                        //  c.getString(c.getColumnIndex("acdate"));


                    } while (c.moveToNext());
                }

            }

            db.close();
            c.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    public static final String CheckIsNull(String data) {
        String val = "";

        if (data.isEmpty() || data == null || data == null || data.equalsIgnoreCase(null)) {
            return "";
        } else if (!data.isEmpty() && data != null) {
            return data;
        }
        return val;
    }

    /*********
     * @Ponnusamy Date 22.11.2016
     * Time 3:00 PM
     * IMage Picker Code
     ************/
    private void add_image() {

        final CharSequence[] items = {"Take Photo", "Choose from Library"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(OperationTheater.this);

        builder.setTitle("Select Image");
        builder.setItems(items, (dialog, item) -> {


            try {

                if (item == 0) {
                    // camera
                    takePicture();

                } else {
                    // gallery
                    openGallery();
                }

            } catch (RuntimeException e) {
                e.printStackTrace();

            }

        });
        //builder.show();
        final android.app.AlertDialog dialog = builder.create();
        // Change the title divider
        dialog.show();

    }

    //capture image
    private void takePicture() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
           /* Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(state)) {
                mImageCaptureUri = Uri.fromFile(mFileTemp);
            } else {
				*//*
                 * The solution is taken from here:
				 * http://stackoverflow.com/questions
				 * /10042695/how-to-get-camera-result-as-a-uri-in-data-folder
				 *//*
                mImageCaptureUri = InternalStorageContentProvider.CONTENT_URI;
            }
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            intent.putExtra("return-data", true);*/

            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (ActivityNotFoundException e) {

            Log.d("Image Picker", "cannot take picture", e);
        }
    }

    private void openGallery() {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_CODE_GALLERY);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected final void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                remark_image_view.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (requestCode == REQUEST_CODE_TAKE_PICTURE && resultCode == RESULT_OK) {
            try {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                remark_image_view.setImageBitmap(photo);
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
    }

    static class DietItem {
        String operation_name = null;
        String patient_name = null;
        String operation_date = null;
        String from_time = null;
        String to_time = null;
        String doctors = null;
        String Actate = null;
        Bitmap image = null;
        String operation_no = null;

        DietItem() {
        }
    }

    public class OperationListViewAdapter extends RecyclerView
            .Adapter<OperationListViewAdapter
            .DataObjectHolder> {
        View view = null;
        private String LOG_TAG = "MyRecyclerViewAdapter";
        private final ArrayList<DietItem> mDataset;


        OperationListViewAdapter(ArrayList<DietItem> myDataset, Context context) {
            mDataset = myDataset;

        }

        @NonNull
        @Override
        public final DataObjectHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                         int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.each_operation_layout, parent, false);
            this.view = view;
            return new DataObjectHolder(view);
        }

        @Override
        public final void onBindViewHolder(@NonNull final DataObjectHolder holder, final int position) {

            final DietItem movie = mDataset.get(position);
            holder.Operation_name_textview.setText(movie.operation_name);
            String OperationDate = movie.operation_date;
            String finalDate = "";
            if (OperationDate.contains("T")) {
                String OpDate[] = OperationDate.split("T");
                // holder.operation_date_time.setText(movie.operation_date+" / "+movie.from_time+" - "+movie.to_time);

                finalDate = String.valueOf(OpDate[0]);
            }

            String fromDTime = "";
            if (movie.from_time.contains("T")) {
                String fromTimeS[] = movie.from_time.split("T");
                fromDTime = fromTimeS[1];
            }

            if (!movie.from_time.contains("T")) {
                fromDTime = movie.from_time;
            }

            String toDTime = "";
            if (movie.to_time.contains("T")) {
                String toTimeS[] = movie.to_time.split("T");
                toDTime = toTimeS[1];
            }

            if (!movie.to_time.contains("T")) {
                toDTime = movie.to_time;
            }

            String finalTime = "";
            String finalTime1 = String.valueOf(fromDTime + " to " + toDTime);
            holder.operation_date_time.setText(finalDate + " / " + finalTime1);

            if (movie.doctors.contains(",")) {
                String drnames[] = movie.doctors.split(",");
                StringBuilder DrNames = new StringBuilder();
                DrNames.append("Doctor(s)  :  ");
                StringBuilder nurseName = new StringBuilder();
                nurseName.append("Assistant(s) : ");
                for (int i = 0; i <= drnames.length - 1; i++) {
                    if (drnames[i].contains("Dr")) {

                        DrNames.append(drnames[i]).append(String.valueOf(","));

                    } else {
                        nurseName.append(drnames[i]);
                    }

                }

                String wholeString = String.valueOf(DrNames + "\n" + nurseName);
                holder.Operation_doctor_name.setText(wholeString);

            } else {
                holder.Operation_doctor_name.setText(movie.doctors);

            }


            //holder.Operation_patient_name.setText(BaseConfig.GetPatientNameFromId(movie.patient_name, OperationTheater.this));
            holder.Operation_patient_name.setText(movie.patient_name);


            holder.reportBtn.setOnClickListener(view -> {

               /* Intent i = new Intent(OperationTheater.this, OperationReport.class);
                startActivity(i);
                finish();*/


            });
            holder.rooLayout.setOnClickListener(view -> showPopup(OperationTheater.this, String.valueOf(holder.Operation_name_textview.getText()), String.valueOf(holder.operation_date_time.getText()), movie.operation_no));



/*


           */
/* holder.DoctorName.setText("Dr."+mDataset.get(position).doctorNameL);*//*

            holder.PatientName.setText(String.valueOf(mDataset.get(position).name));
            holder.PatientBmi.setText(String.valueOf("BMI - "+mDataset.get(position).bmi));
            if(mDataset.get(position).userIcon!= null)
            {
                holder.imageView.setImageBitmap(mDataset.get(position).userIcon);
            }
            holder.outerLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(OperationTheater.this,AddDietActivity.class);

                    startActivity(i);
                    finish();


                }
            });

*/


        }

        public final void addItem(DietItem dataObj, int index) {
            mDataset.add(index, dataObj);
            notifyItemInserted(index);
        }

        public final void deleteItem(int index) {
            mDataset.remove(index);
            notifyItemRemoved(index);
        }

        @Override
        public final int getItemCount() {
            return mDataset.size();
        }

        public class DataObjectHolder extends RecyclerView.ViewHolder
                implements View
                .OnClickListener {
            final TextView Operation_name_textview;
            final TextView operation_date_time;
            final TextView Operation_doctor_name;
            final TextView Operation_patient_name;
            CircleImageView imageView = null;
            final Button reportBtn;
            final LinearLayout rooLayout;

            LinearLayout outerLayout = null;


            DataObjectHolder(View itemView) {
                super(itemView);
                reportBtn = itemView.findViewById(R.id.report_btn);
                Operation_name_textview = itemView.findViewById(R.id.Operation_name_textview);
                operation_date_time = itemView.findViewById(R.id.operation_date_time);
                Operation_doctor_name = itemView.findViewById(R.id.Operation_doctor_name);
                Operation_patient_name = itemView.findViewById(R.id.Operation_patient_name);
                rooLayout = itemView.findViewById(R.id.layout_root);
            }

            @Override
            public void onClick(View view) {

            }
        }

    }
}
