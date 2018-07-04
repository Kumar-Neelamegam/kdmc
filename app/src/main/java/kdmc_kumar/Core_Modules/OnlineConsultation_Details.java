package kdmc_kumar.Core_Modules;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Adapters_GetterSetter.OnlineConsultation_Detailed_Adapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Utilities_Others.Validation1;

public class OnlineConsultation_Details extends AppCompatActivity {

    private static Bitmap reportimg = null;
    TextView scrolltitle = null;
    private TextView Patient_Id = null;
    private TextView patient_name = null;
    private TextView Age = null;
    private TextView Gender = null;
    private TextView treatmentfor = null;
    private TextView patient_query = null;
    private TextView visiteddate = null;
    private EditText Docreply = null;
    private ImageView Patient_photo = null;
    private Button reject = null;
    private Button cancel = null;
    private Button reply = null;
    ArrayList<HashMap<String, String>> titles_list = null;
    private ArrayAdapter<CommonDataObjects.OnlineConsultationReport> adapter = null;
    private ListView listView = null;
    private List<CommonDataObjects.OnlineConsultationReport> rowItems = null;
    private Bundle b = null;
    private String ServerId = null;
    private String PatientId = null;
    private String MedId = null;

    private WebView profile_webvw = null;
    /**********************************************************************************************/


    /*
      Code rewriten by Muthukumar
      06/04/2017
      @param savedInstanceState
     */
    /***********************************************************************************************/
    private Toolbar toolbar = null;
    private ImageView home = null;
    private ImageView exit = null;
    private ImageView back = null;

    public OnlineConsultation_Details() {
    }


    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stubsuper.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_onlineconsultation_details);


        //getSupportActionBar().setTitle(getString(R.string.welcome) + " - " + BaseConfig.doctorname);
        BaseConfig.welcometoast = 0;

        toolbar = findViewById(R.id.toolbar_mypatient);


        home = toolbar.findViewById(R.id.home_mp);
        exit = toolbar.findViewById(R.id.exit_mp);
        back = toolbar.findViewById(R.id.back_mp);


        home.setOnClickListener(v -> {

            OnlineConsultation_Details.this.finish();
            Intent intent = new Intent(OnlineConsultation_Details.this, Dashboard_NavigationMenu.class);
            startActivityForResult(intent, 500);
            overridePendingTransition(R.anim.abc_slide_in_top,
                    R.anim.abc_slide_in_top);

        });

        exit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(OnlineConsultation_Details.this, null));

        back.setOnClickListener(view -> LoadBack());


        try {
            try {

                b = getIntent().getExtras();

                if (b == null) {
                    ServerId = "";
                    PatientId = "";
                    MedId = "";
                } else {
                    ServerId = b.getString("ServerId");
                    PatientId = b.getString("PatientId");
                    MedId = b.getString("MedId");

                }
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

            getinitialize();
            SelectedGetPatientDetails();
            LoadWebview();
            String Query="select ReportGallery,FileName from OnlineConsultancyDtls where ServerId='" + ServerId + "' and pid='" + PatientId + "';";
            SelectedGetPatientReports(Query);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################
    private final void LoadWebview() {

        profile_webvw.getSettings().setJavaScriptEnabled(true);
        profile_webvw.setLayerType(View.LAYER_TYPE_NONE, null);
        profile_webvw.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        profile_webvw.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        profile_webvw.getSettings().setDefaultTextEncodingName("utf-8");

        profile_webvw.setWebChromeClient(new MyWebChromeClient());

        profile_webvw.setBackgroundColor(0x00000000);
        profile_webvw.setVerticalScrollBarEnabled(true);
        profile_webvw.setHorizontalScrollBarEnabled(true);


        profile_webvw.getSettings().setJavaScriptEnabled(true);

        profile_webvw.getSettings().setAllowContentAccess(true);


        profile_webvw.setOnLongClickListener(v -> true);

        profile_webvw.setLongClickable(false);


        profile_webvw.addJavascriptInterface(new WebAppInterface(OnlineConsultation_Details.this), "android");
        try {

            profile_webvw.loadDataWithBaseURL("file:///android_asset/", LoadPrescriptionDetails(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private final String LoadPrescriptionDetails() {
        String values = "";
        String[] Tabledata;
        StringBuilder stringBuilder = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        String Query = "select distinct Medicinename from OnlineSharedMedicine where pid='" + PatientId + "'  and status='" + MedId + "';";
        Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    Tabledata = c.getString(c.getColumnIndex("Medicinename")).split("_");

                    stringBuilder.append("  <tr>\n" + "                  <th><font color=\"#000\">").append(Tabledata[0].replace("[", "").replace("]", "")).append("</font></th>\n \n").append("                    <th><font color=\"#000\">").append(Tabledata[1]).append("</font></th>\n \n").append("               \t <th><font color=\"#000\">").append(Tabledata[2]).append("</font></th>\n \n").append("              \t <th><font color=\"#000\">").append(Tabledata[3]).append("</font></th>\n\n").append("                 </tr>\n+");

                } while (c.moveToNext());

                //medicine_name.setText(mednm.toString());
            }
        }
        String values1 = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                '\n' +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                '\n' +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                '\n' +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                '\n' +
                '\n' +
                '\n' +
                '\n' +
                "</head>\n" +
                "<body>  \n" +
                "<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> " + getString(R.string.patientmedicinehistory) + "</font>\n" +
                "<div class=\"table-responsive\"> <table class=\"table table-bordered\"><tr>\n" +
                "  <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.medicinename) + "</font></th>\n" +
                "  <th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.interval) + "</font></th>\n" +
                "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.frequency) + "</font></th>\n" +
                "\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">" + getString(R.string.duration) + "</font></th>\n" +
                "  </tr>\n" +
                " \n" + stringBuilder +
                " \n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html> ";


        c.close();
        db.close();


        return values1;
    }

    /***********************************************************************************************/


    private void SelectedGetPatientDetails() {

        try {
            String pimg64 = "";
            String name = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery("select * from OnlineSharedMedicine where pid='" + PatientId + "'  and status='" + MedId + "';", null);
            StringBuilder mednm = new StringBuilder();
            String photoo = "";

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        //mednm.append(c.getString(c.getColumnIndex("Medicinename")) + "\n");
                        treatmentfor.setText(c.getString(c.getColumnIndex("TreatmentFor")));

                    } while (c.moveToNext());

                    //medicine_name.setText(mednm.toString());
                }
            }


            c.close();

            Cursor c1 = db.rawQuery("select * from OnlineConsultancy where ServerId='" + ServerId + "' and pid='" + PatientId + "';", null);
            if (c1 != null) {
                if (c1.moveToFirst()) {
                    do {

                        patient_name.setText(c1.getString(c1.getColumnIndex("pname")));

                        patient_query.setText(c1.getString(c1.getColumnIndex("healthsummary")));
                        //Patient_photo.setImageBitmap(Convert64ToImage((c.getString(c.getColumnIndex("pphoto")))));
                        BaseConfig.LoadPatientImage(BaseConfig.GetValues("select PC as ret_values from Patreg where Patid='"+c1.getString(c1.getColumnIndex("pid"))+"'"), Patient_photo, 100);
                        Age.setText(c1.getString(c1.getColumnIndex("age")));
                        Gender.setText(c1.getString(c1.getColumnIndex("gender")));
                        visiteddate.setText(c1.getString(c1.getColumnIndex("Actdt")));
                        Patient_Id.setText(c1.getString(c1.getColumnIndex("pid")));
                        break;
                    } while (c1.moveToNext());
                }
            }

            db.close();
            c1.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }
    //#######################################################################################################

    /***********************************************************************************************/


    private void SelectedGetPatientReports(String Query) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                            report_name = c.getString(c.getColumnIndex("FileName"));
                            report64 = c.getString(c.getColumnIndex("ReportGallery"));

                            CommonDataObjects.OnlineConsultationReport item = new CommonDataObjects.OnlineConsultationReport(report_name, report64);

                            rowItems.add(item);

                    } while (c.moveToNext());

                }
            }

            adapter = new OnlineConsultation_Detailed_Adapter(OnlineConsultation_Details.this, R.layout.list_item, rowItems);
            listView.setAdapter(adapter);


            c.close();
            db.close();
        } catch (RuntimeException e) {

            e.printStackTrace();

        }
    }

    /***********************************************************************************************/


    private static void SelectedGetPatientReports_Image(String Query, ImageView imgvw) {

        try {
            String report_name = "";
            String report64 = "";
            SQLiteDatabase db = BaseConfig.GetDb();

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        BaseConfig.LoadPatientImage(c.getString(c.getColumnIndex("ReportGallery")), imgvw, 100);

                    } while (c.moveToNext());

                }
            }

            c.close();
            db.close();
        } catch (Exception e) {
e.printStackTrace();
        }
    }

    /***********************************************************************************************/


    private void getinitialize() {

        Patient_Id = findViewById(R.id.pid_txt);
        patient_name = findViewById(R.id.textView2);
        Age = findViewById(R.id.textView4);
        Gender = findViewById(R.id.textView6);
        treatmentfor = findViewById(R.id.textView9);
        patient_query = findViewById(R.id.textView15);
        visiteddate = findViewById(R.id.textView7);

        Docreply = findViewById(R.id.editText1);


        Patient_photo = findViewById(R.id.icon);

        reject = findViewById(R.id.Button01);
        cancel = findViewById(R.id.Button03);
        reply = findViewById(R.id.Button02);


        // initialize
        rowItems = new ArrayList<>();
        listView = findViewById(R.id.listView1);
        profile_webvw = findViewById(R.id.webvw_prescription_profile);
        profile_webvw.getSettings().setJavaScriptEnabled(true);
        profile_webvw.setWebChromeClient(new WebChromeClient());


        //***************************************************************************************
        //controllisteners
        //***************************************************************************************


        // //////////////////////////////////////////////////////////////////
        Docreply.setOnTouchListener((v, event) -> {
            if (Docreply.length() > 350) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
        listView.setOnTouchListener((v, event) -> {
            if (rowItems.size() > 5) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
            }
            return false;
        });
        // //////////////////////////////////////////////////////////////////


        reply.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            try {

                if (checkValidation())

                    savelocal();
                else

                   Toast.makeText(OnlineConsultation_Details.this, getString(R.string.check_missing_valid), Toast.LENGTH_LONG).show();
              //  BaseConfig.SnackBar(this,  getString(R.string.check_missing_valid) , parentLayout,2);

            } catch (RuntimeException e) {
                e.printStackTrace();

            }
        });

        cancel.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            OnlineConsultation_Details.this.finish();
            Intent intentnew = new Intent(getApplicationContext(), OnlineConsultation.class);
            startActivity(intentnew);

        });


        reject.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            //SaveLocal(2);
            showSimplePopUpExit(getString(R.string.reject_consultation));
        });


        ///////////////////////////////////////////////////////////////////////////////
        final Context context = this;
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // TODO Auto-generated method stub


            CommonDataObjects.OnlineConsultationReport selectedRow = (CommonDataObjects.OnlineConsultationReport) parent.getAdapter().getItem(position);

            //Log.e("ImageView", selectedRow.toString());

            String ReportName = selectedRow.getReportName();


            LayoutInflater layoutInflater = LayoutInflater.from(context);

            View promptView = layoutInflater.inflate(R.layout.reportimageview_zoom_dialog2, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            alertDialogBuilder.setView(promptView);


            Bitmap rotatedBitmap;

            final ImageView reportphoto = promptView.findViewById(R.id.imageZoom);

            String Query_Image="select ReportGallery,FileName from OnlineConsultancyDtls where ServerId='" + ServerId + "' and FileName='" + ReportName + "' and pid='" + PatientId + "';";
            SelectedGetPatientReports_Image(Query_Image, reportphoto);

            final TextView nameagegen1 = promptView.findViewById(R.id.nameagegen);

            final TextView patient_name = promptView.findViewById(R.id.amt);
            patient_name.setVisibility(View.INVISIBLE);
            final ZoomControls zoom = promptView.findViewById(R.id.zoomControls1);

            final ImageButton rotate_left, rotate_right, rotate_reset;

            rotate_left = promptView.findViewById(R.id.rotate_anticlock);
            rotate_right = promptView.findViewById(R.id.rotate_clock);
            rotate_reset = promptView.findViewById(R.id.rotate_reset);


            nameagegen1.setText(getString(R.string.report_name) + ReportName);
            reportphoto.setImageBitmap(reportimg);


            rotate_left.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub
                Matrix matrix = new Matrix();
                Matrix mat = reportphoto.getImageMatrix();
                matrix.set(mat);
                matrix.setRotate(-90.0F);
                reportimg = Bitmap.createBitmap(reportimg, 0, 0, reportimg.getWidth(), reportimg.getHeight(), matrix, true);
                reportphoto.setImageBitmap(reportimg);

            });


            rotate_right.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub
                Matrix matrix = new Matrix();
                Matrix mat = reportphoto.getImageMatrix();
                matrix.set(mat);
                matrix.setRotate(90.0F);
                reportimg = Bitmap.createBitmap(reportimg, 0, 0, reportimg.getWidth(), reportimg.getHeight(), matrix, true);
                reportphoto.setImageBitmap(reportimg);

            });
            rotate_reset.setOnClickListener(arg0 -> {
                // TODO Auto-generated method stub
                Matrix matrix = new Matrix();
                matrix.postRotate(90.0F);
                reportimg = Bitmap.createBitmap(reportimg, 0, 0, reportimg.getWidth(), reportimg.getHeight(), matrix, true);
                reportphoto.setImageBitmap(reportimg);
            });

            zoom.setOnZoomInClickListener(v -> {
                // TODO Auto-generated method stub

                int w = reportphoto.getWidth();
                int h = reportphoto.getHeight();

                RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(w + 10, h + 10);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                reportphoto.setLayoutParams(params);
            });

            zoom.setOnZoomOutClickListener(v -> {
                // TODO Auto-generated method stub

                int w = reportphoto.getWidth();
                int h = reportphoto.getHeight();

                RelativeLayout.LayoutParams params =
                        new RelativeLayout.LayoutParams(w - 10, h - 10);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);

                reportphoto.setLayoutParams(params);
            });


            alertDialogBuilder.setCancelable(false).setPositiveButton(getString(R.string.ok),
                    (dialog, id1) -> {


                    });


            AlertDialog alertD = alertDialogBuilder.create();
            alertD.show();

        });


    }

    private final boolean checkValidation() {
        boolean ret = true;

        if (!Validation1.hasText(Docreply))
            ret = false;


        return ret;
    }

    private final void savelocal() {

        try {
            final String Insert_Query = "update OnlineConsultancy set DocReply='" + Docreply.getText().toString() + "',IsUpdate='0',IsActive='False' where ServerId='" + ServerId + "' ";

            BaseConfig.SaveData(Insert_Query);
            //Log.e("Insert_Query", Insert_Query);

            showSimplePopUpExit(getString(R.string.response_send));
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void showSimplePopUpExit(String msg) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information))
                .setNegativeButtonVisible(View.GONE)
                .setDescription(msg)
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {
                    final String Insert_Query = "update OnlineConsultancy set IsUpdate='1',IsActive='False' where ServerId='" + ServerId + "' ";

                    BaseConfig.SaveData(Insert_Query);
                    //Log.e("Insert_Query", Insert_Query);


                    OnlineConsultation_Details.this.finish();
                    Intent intent = new Intent(getApplicationContext(), OnlineConsultation.class);
                    startActivity(intent);
                });


    }

    //#######################################################################################################
    @Override
    public final void onBackPressed() {

        LoadBack();

    }

    private final void LoadBack() {
        BaseConfig.globalStartIntent(this, OnlineConsultation.class, null);

    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

    //#######################################################################################################
    static class WebAppInterface {
        final Context mContext;

        /**
         * Instantiate the interface and set the context
         */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /**
         * Show a toast from the web page
         */
        @JavascriptInterface
        public void showToast(String toast) {
            //Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();


        }
    }

    //#######################################################################################################

}