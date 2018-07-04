package kdmc_kumar.ReportFileUpload;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CropImage;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.InternalStorageContentProvider;


public class UploadDocument extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUEST_CODE_GALLERY = 0x4;
    private static final int REQUEST_CODE_TAKE_PICTURE = 0x2;
    private static final int REQUEST_CODE_CROP_IMAGE = 0x3;
    private static final String TEMP_PHOTO_FILE_NAME = "/temp_photo.jpg";
    private static final int REQUEST_CODE = 123;
    private static int Image_ID = 0;
    private TextView patientid = null;
    private TextView patientname = null;
    private TextView patientagegen = null;
    private ImageView patientimage = null;
    private ImageView ic_back = null;
    private Spinner report_type = null;
    private EditText report_details = null;
    private EditText filepath = null;
    private Button browse = null;
    private Button add_item = null;
    private RecyclerView recyler_view = null;
    private TextView cancel = null;
    private TextView submit = null;
    private Toolbar toolbar = null;
    private final String[] Report_Type = {"Select Report", "Scan Report", "Test Report", "EEG Report", "ECG Report", "Angiogram Report", "Others"};
    private File mFileTemp = null;
    private FileAdapter fileAdapter = null;
    private ArrayList<FileGetSet> fileGetSets = null;
    private String BUNDLE_PATIENT_ID = null;
    private TextView txt_report_type = null;
    private TextView txt_report_details = null;
    private TextView txt_file_path1 = null;
    private TextView txt_file_path2 = null;
    private final String Str_FileName = "";
    private final String Str_FileExtension = "";
    private GridLayoutManager lLayout = null;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    public UploadDocument() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_document);
        toolbar = findViewById(R.id.toolbar);

        Bundle b = getIntent().getExtras();

        BUNDLE_PATIENT_ID = b.getString(BaseConfig.BUNDLE_PATIENT_ID);

        //delete previous contact inserts
        deleteContacts();

        txt_report_type = findViewById(R.id.spn_title);
        txt_report_details = findViewById(R.id.txt_report_dtls);
        txt_file_path1 = findViewById(R.id.robotoTextView);
        txt_file_path2 = findViewById(R.id.filepath);

        String firstt = getString(R.string.report_type);
        String second = getString(R.string.report_details);
        String third = getString(R.string.file_path);
        String fourth = getString(R.string.file_path);


        String nextt = "<font color='#EE0000'><b>*</b></font>";
        txt_report_type.setText(Html.fromHtml(firstt + nextt));
        txt_report_details.setText(Html.fromHtml(second + nextt));
        txt_file_path1.setText(Html.fromHtml(third + nextt));
        txt_file_path2.setText(Html.fromHtml(fourth + nextt));


        recyler_view = findViewById(R.id.recyler_view);

        lLayout = new GridLayoutManager(UploadDocument.this, 2);
        recyler_view.setHasFixedSize(true);
        recyler_view.setLayoutManager(lLayout);
        recyler_view.setNestedScrollingEnabled(false);


        ic_back = toolbar.findViewById(R.id.ic_back);
        ic_back.setOnClickListener(this);

        patientid = findViewById(R.id.patient_id);
        patientname = findViewById(R.id.patient_name);
        patientagegen = findViewById(R.id.patient_agegen);
        patientimage = findViewById(R.id.patient_image);
        report_details = findViewById(R.id.report_details);
        report_type = findViewById(R.id.report_type);
        browse = findViewById(R.id.browse);
        filepath = findViewById(R.id.filepath);
        add_item = findViewById(R.id.add_item);
        add_item.setOnClickListener(this);

        browse.setOnClickListener(this);

        submit = findViewById(R.id.submit);
        cancel = findViewById(R.id.cancel);
        submit.setOnClickListener(this);
        cancel.setOnClickListener(this);

        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
        String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');

        patientid.setText(BUNDLE_PATIENT_ID);
        patientname.setText(Patient_Name);
        patientagegen.setText(Patient_AgeGender);

        Glide.with(patientimage.getContext()).asBitmap().load(new File(filePath(BUNDLE_PATIENT_ID))).into(patientimage);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(UploadDocument.this, android.R.layout.simple_list_item_1, Report_Type);

        report_type.setAdapter(adapter);


        try {
            String state = Environment.getExternalStorageState();
            mFileTemp = Environment.MEDIA_MOUNTED.equals(state) ? new File(Environment.getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE_NAME) : new File(getFilesDir(), TEMP_PHOTO_FILE_NAME);

            mFileTemp = new File(Environment
                    .getExternalStorageDirectory(),
                    TEMP_PHOTO_FILE_NAME);
            if (mFileTemp.exists()) {
                mFileTemp.delete();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }


    //**********************************************************************************************

    @Override
    public final void onBackPressed() {
        super.onBackPressed();
        UploadDocument.this.finish();
    }


    private void startCropImage() {

        try {
            Intent intent = new Intent(UploadDocument.this, CropImage.class);
            intent.putExtra(CropImage.IMAGE_PATH, mFileTemp.getPath());
            intent.putExtra(CropImage.SCALE, true);

            intent.putExtra(CropImage.ASPECT_X, 1);
            intent.putExtra(CropImage.ASPECT_Y, 1);
            intent.putExtra(CropImage.OUTPUT_X, 270);
            intent.putExtra(CropImage.OUTPUT_Y, 270);

            startActivityForResult(intent, REQUEST_CODE_CROP_IMAGE);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public final void onClick(View view) {

        switch (view.getId()) {
            case R.id.ic_back:
                UploadDocument.this.finish();
                break;
            case R.id.cancel:
                UploadDocument.this.finish();
                break;
            case R.id.submit:

                if (fileAdapter != null && fileAdapter.getItemCount() > 0) {
                    ContactstoReportGallery();

                    deleteContacts();

                    new CustomKDMCDialog(UploadDocument.this)
                            .setImage(R.drawable.ic_success_done)
                            .setTitle("Information")
                            .setLayoutColor(R.color.green_500)
                            .setDescription("Insert Successfully")
                            .setPossitiveButtonTitle("OK")
                            .setOnPossitiveListener(UploadDocument.this::finish);


                } else {
                    //Toast.makeText(UploadDocument.this, "Atleast add one report or click cancel..", Toast.LENGTH_LONG).show();

                    new CustomKDMCDialog(UploadDocument.this)
                            .setLayoutColor(R.color.orange_500)
                            .setImage(R.drawable.ic_warning_black_24dp)
                            .setTitle("Information")
                            .setDescription("Atleast add one report or click cancel..")
                            .setPossitiveButtonTitle("OK");


                }


                break;
            case R.id.browse:

                ImageChooser();
                break;
            case R.id.add_item:

                if (report_type.getSelectedItemId() == 0L) {
                    Toast.makeText(this, "Select Report Type...", Toast.LENGTH_SHORT).show();
                } else if (report_details.getText().toString().isEmpty()) {
                    report_details.setError("Enter Value");
                } else if (filepath.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Browse Image...", Toast.LENGTH_SHORT).show();
                } else {

                    //add contacts table to show listview
                    insertContacts(Str_FileName, Str_FileExtension, filepath.getText().toString(), report_type.getSelectedItem().toString(), report_details.getText().toString());
                    showRecyclerListView();
                    filepath.setText("");
                    report_details.setText("");
                    report_type.setSelection(0);

                }
                break;
        }

    }

    private static final String filePath(String Doctorid) {

        String filepath = "";
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select * from Patreg where Patid='" + Doctorid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    filepath = c.getString(c.getColumnIndex("PC"));
                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return filepath;
    }


    private final void ImageChooser() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton("File Chooser", (dialog12, which) -> showChooser());
        dialog.setNegativeButton("Take Photo", (dialog1, which) -> {

            // cameraIntent();
            takePicture();

        });
        dialog.setTitle("Choose Image");
        dialog.setMessage("Select option");
        dialog.show();

    }


    //Browse

    private void showChooser() {

      /*  // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(target, "Choose Dialog");
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (Exception e) {
            // The reason for the existence of aFileChooser
            e.printStackTrace();
        }*/

    }

    private final void takePicture() {

        ++Image_ID;

        long time = System.currentTimeMillis();
        mFileTemp = new File(Environment.getExternalStorageDirectory().toString() + "/temp" + Image_ID + '-' + time + ".jpg");

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            Uri mImageCaptureUri = null;
            String state = Environment.getExternalStorageState();
            mImageCaptureUri = Environment.MEDIA_MOUNTED.equals(state) ? Uri.fromFile(mFileTemp) : InternalStorageContentProvider.CONTENT_URI;
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    mImageCaptureUri);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, REQUEST_CODE_TAKE_PICTURE);
        } catch (RuntimeException ignored) {
        }
    }

    private final void showRecyclerListView() {
        fileGetSets = new ArrayList<>();

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select * from contacts", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    FileGetSet item = new FileGetSet();
                    item.setImageName(c.getString(c.getColumnIndex("Name")));
                    item.setImagePath(c.getString(c.getColumnIndex("ImageUrl")));
                    item.setId(c.getString(c.getColumnIndex("Id")));
                    item.setReportType(c.getString(c.getColumnIndex("Type")));
                    item.setUploadFileDetail(c.getString(c.getColumnIndex("ReportDetail")));

                    fileGetSets.add(item);

                } while (c.moveToNext());
            }
        }
        c.close();
        fileAdapter = new FileAdapter(fileGetSets, recyler_view);
        recyler_view.setAdapter(fileAdapter);
    }


    private static final void insertContacts(String image_name, String image_extns, String imagepath, String type, String reportdetail) {

        SQLiteDatabase db = BaseConfig.GetDb();
        ContentValues cv = new ContentValues();
        cv.put("FileName", image_name);
        cv.put("FileExtension", image_extns);
        cv.put("Name", type);
        cv.put("Image", imagepath);
        cv.put("ImageUrl", imagepath);
        cv.put("Type", type);
        cv.put("ReportDetail", reportdetail);
        db.insert("contacts", null, cv);
        db.close();

    }

    private static final void deleteContacts() {

        SQLiteDatabase db = BaseConfig.GetDb();
        db.delete("contacts", null, null);


    }

    // Getting All Contacts
    private final void ContactstoReportGallery() {

        SimpleDateFormat dateformt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date();
        String dttm = dateformt.format(date);

        // Select All Query
        SQLiteDatabase db = BaseConfig.GetDb();

        String selectQuery = "SELECT  * FROM contacts ORDER BY Name";

        Cursor c = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {

                String LTitle = c.getString(c.getColumnIndex("Name"));

                String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
                String Patient_AgeGender = BaseConfig.GetValues("select age||'-'||gender as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');


                final String Insert_Query = "Insert into ReportGallery(Docid,Patid,Name,agegender,Diagnosisid,dt,Remarks,IsUpdate,patientphoto,ReportPhoto,ReportType,ImageUrl,FileName,FileExtension)"
                        + " Values('" + BaseConfig.doctorId + "','" + BUNDLE_PATIENT_ID + "','" + Patient_Name + "','" + Patient_AgeGender + "','" + BaseConfig.digid + "','" + dttm + "','','0',''," +
                        '\'' + BaseConfig.ReportyGal + "','" + LTitle + "','" + c.getString(c.getColumnIndex("ImageUrl")) + "','" + c.getString(c.getColumnIndex("FileName")) + "'," +
                        '\'' + c.getString(c.getColumnIndex("FileExtension")) + "');";

                BaseConfig.SaveData(Insert_Query);
                Log.d("Title: ", Insert_Query);

            } while (c.moveToNext());
        }

        c.close();

        db.execSQL("delete from contacts");

        db.close();

    }
}