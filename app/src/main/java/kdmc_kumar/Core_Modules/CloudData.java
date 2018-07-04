package kdmc_kumar.Core_Modules;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.DashBoardAdapter;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;


public class CloudData extends AppCompatActivity {


    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.imagevw_serverconnectivity)
    ImageView imagevwServerconnectivity;
    @BindView(R.id.textview_servertitle)
    TextView textviewServertitle;
    @BindView(R.id.imagevw_internet)
    ImageView imagevwInternet;
    @BindView(R.id.textvw_internet)
    TextView textvwInternet;
    @BindView(R.id.webvw_profile)
    WebView webvwProfile;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_back)
    AppCompatImageView toolbarBack;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar_exit)
    AppCompatImageView toolbarExit;
    BaseConfig bc = new BaseConfig();
    //#######################################################################################################
    int COUNT_PATIENT_REGISTRATION = 0;
    int COUNT_CLINICAL_INFORMATION = 0;
    int COUNT_CASENOTES = 0;
    int COUNT_INVESTIGATION = 0;
    int COUNT_PRESCRIPTION = 0;

    private Handler timerHandler;
    private Runnable timerRunnable;

    public CloudData() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_cloud);

            GETINITIALIZATION();

            CONTROLLISTNERS();

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    private void GETINITIALIZATION() {
        BaseConfig.welcometoast = 0;

        ButterKnife.bind(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        toolbarTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.cloud)));

        setSupportActionBar(toolbar);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ViewCompat.setTransitionName(toolbar, DashBoardAdapter.VIEW_NAME_HEADER_TITLE);
        }

        //  toolbar.setBackgroundColor(Color.parseColor(CloudData.this.getResources().getString(Integer.parseInt(BaseConfig.SetActionBarColour))));


    }

    private void CONTROLLISTNERS() {
        toolbarExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(CloudData.this, null));


        toolbarBack.setOnClickListener(v -> LoadBack());


        CheckServerConnectivity();

        AUTO_REFRESH_CLOUD_COUNT();

    }

    private void AUTO_REFRESH_CLOUD_COUNT() {


        timerHandler = new Handler();

        timerRunnable = new Runnable() {
            @Override
            public void run() {

                new CheckPendingData().execute();

                timerHandler.postDelayed(this, 1000); //run every second

            }
        };

        timerHandler.postDelayed(timerRunnable, 1000); //Start timer after 1 sec

    }


    @Override
    protected void onStop() {
        super.onStop();
        if (timerHandler != null) {
            timerHandler.removeCallbacks(timerRunnable);
        }

    }

    private void CheckServerConnectivity() {
        if (BaseConfig.CheckNetwork(CloudData.this)) {
            imagevwInternet.setImageBitmap(null);
            imagevwInternet.setImageResource(R.drawable.ic_status_ready);
        } else {
            imagevwInternet.setImageResource(R.drawable.ic_status_busy);
        }

        try {
            BaseConfig.checkNodeServer(imagevwServerconnectivity);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private final void LoadBack() {
        BaseConfig.globalStartIntent(this, Dashboard_NavigationMenu.class, null);

    }

    //#######################################################################################################
    @Override
    public final void onBackPressed() {

        LoadBack();

    }

    private final void CheckPending() {

        try {
            COUNT_PATIENT_REGISTRATION = CheckCount("select count(*) as ret_values  from Bind_Patient_Registration where Isupdate='0' group by PatientId");
            COUNT_CLINICAL_INFORMATION = CheckCount("select count(*) as ret_values from ClinicalInformation where Isupdate='0' group by ptid");
            COUNT_CASENOTES = CheckCount("select count(*) as ret_values from Diagonis where Isupdate='0' group by DiagId");
            COUNT_INVESTIGATION = CheckCount("select count(*)  ret_values from Medicaltest where Isupdate='0' group by mtestid");
            COUNT_PRESCRIPTION = CheckCount("select count(*) as ret_values from Mprescribed where Isupdate='0' group by Medid");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int CheckCount(String query)
    {

        int str = 0;
        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery(query, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    str = Integer.parseInt(c.getString(c.getColumnIndex("ret_values")));

                } while (c.moveToNext());

            }
        }

        c.close();
        db.close();
        return str;

    }

    @SuppressLint("AddJavascriptInterface")
    private final void LoadWebview() {

        webvwProfile = findViewById(R.id.webvw_profile);
        webvwProfile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvwProfile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webvwProfile.setOnLongClickListener(v -> true);
        webvwProfile.setLongClickable(false);
        webvwProfile.setLayerType(View.LAYER_TYPE_NONE, null);
        webvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvwProfile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webvwProfile.getSettings().setDefaultTextEncodingName("utf-8");
        webvwProfile.setWebChromeClient(new MyWebChromeClient());
        webvwProfile.setBackgroundColor(0x00000000);
        webvwProfile.setVerticalScrollBarEnabled(true);
        webvwProfile.setHorizontalScrollBarEnabled(true);
        webvwProfile.getSettings().setJavaScriptEnabled(true);
        webvwProfile.getSettings().setAllowContentAccess(true);
        webvwProfile.addJavascriptInterface(new WebAppInterface(CloudData.this), "android");

        try {

            webvwProfile.loadDataWithBaseURL("file:///android_asset/", LoadCloudDetails(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################

    private String LoadCloudDetails() {


        return "<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" +
                "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" +
                "\n" +
                "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" +
                "\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" +
                "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" +
                "\n" +
                "\n" +
                "\n" +
                "\n" +
                "</head>\n" +
                "<body>  \n" +
                "\n" +
                "\n" +
                "\n" +
                "<div class=\"table-responsive\"> \n" +
                "       \n" +
                "<table class=\"table table-bordered\">\n" +
                "  <tr>\n" +
                "  \n" +
                "  \n" +
                "    <th class=\"fixed\" bgcolor=\"#3d5987\"  ><font color=\"#fff\">" + getString(R.string.pending_data) + "</font></th>\n" +
                "    <th class=\"fixed\" bgcolor=\"#3d5987\" style=\"text-align:center\"><font color=\"#fff\">" + getString(R.string.count) + "</font></th> \n" +
                " \n" +
                "  </tr>\n" +
                "  \n" +

                "\t<tr>\n" +
                "\t<td><b>" + getString(R.string.patientregistration) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + COUNT_PATIENT_REGISTRATION + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +

                "\t<tr>\n" +
                "\t<td><b>" + getString(R.string.clinical_informaiton) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + COUNT_CLINICAL_INFORMATION + "</td> \n" +
                "\t</tr>  \n" +

                "\t\n" +
                "\t<tr>\n" +
                "\t<td><b>" + getString(R.string.case_notes) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + COUNT_CASENOTES + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +
                "\t<tr>\n" +
                "\t<td><b>" + getString(R.string.investigation) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + COUNT_INVESTIGATION + "</td> \n" +
                "\t</tr>  \n" +
                "\t\n" +
                "\t\t<tr>\n" +
                "\t<td><b>" + getString(R.string.prescription) + "</b></td>  \n" +
                "\t<td style=\"text-align:center\">" + COUNT_PRESCRIPTION + "</td> \n" +
                "\t</tr> \n" +
                " \n" +
                "</table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div>\n" +
                "  \n" +
                "<h4 style=\"padding:5px\";>\n" +
                "<b>Note:</b><br>" + getString(R.string.content_cloud) + "\n" +
                "</h4>\n" +
                "\n" +
                " \n" +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                             ";
    }

    //#######################################################################################################


    public final void onDestroy() {
        super.onDestroy();

    }

    private static class MyWebChromeClient extends WebChromeClient {
    }

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

    private class CheckPendingData extends AsyncTask<Void, Void, Void> {

        private CheckPendingData() {
        }

        protected final void onPostExecute(Void result) {

            try {
                LoadWebview();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected final Void doInBackground(Void... params) {

            CheckPending();

            return null;
        }

    }


}//END
