package kdmc_kumar.Doctor_Modules;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CustomIntent;
import kdmc_kumar.Utilities_Others.LocalSharedPref;

/////////////////////////////////////////////////////////////////////////////////
public class Splash_Screen extends AppCompatActivity {


    private static final String SPLASH_SCREEN_OPTION = "com.csform.android.uiapptemplate.SplashScreensActivity";
    private static final String SPLASH_SCREEN_OPTION_1 = "Option 1";
    private static final String SPLASH_SCREEN_OPTION_3 = "Option 3";
    private static final int REQUEST_PERMISSIONS = 20;
    private final Handler handler = new Handler();
    @BindView(R.id.top_banner_splash)
    ImageView top_banner;
    @BindView(R.id.app_logo)
    ImageView app_logo;
    TextView txvwTitle = null;
    @BindView(R.id.app_title)
    TextView mLogo;
    @BindView(R.id.textprgrs)
    TextView progress_status;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private int progress = 0;
    private int progressStatus = 0;
    private ProgressDialog pDialog = null;

    public Splash_Screen() {
    }

    public static boolean LoadReportsBooleanStatus(String Query) {
        try {
            int havcount = 0;


            SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        c.getString(c.getColumnIndex("dstatus1"));

                        havcount++;

                    } while (c.moveToNext());
                }
            }

            c.close();
            db.close();


            return havcount > 0;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_splash_layout);

        try {

            BaseConfig.bkproc = 1;


            BaseConfig.LoadAppConfiguration(Splash_Screen.this);



            GetIniliz();


        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }


    private void configApplicationLanguage() {
        // TODO: 22-06-2018 Load Language
        try {
            LocalSharedPref localSharedPref = new LocalSharedPref(this);
            String LanguageCode=localSharedPref.getValue("LocaleLanguage");
            if(!LanguageCode.equalsIgnoreCase("")&&!LanguageCode.isEmpty()) {
                Locale myLocale = new Locale(LanguageCode);
                Resources res = getResources();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                onConfigurationChanged(conf);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////////
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    private final void GetIMEI() {
        try {

            TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                return;
            }
            BaseConfig.Imeinum = tm.getDeviceId();


        } catch (RuntimeException ec) {
            BaseConfig.Imeinum = "";
            ec.printStackTrace();

        }
    }

    private final void GetMAC() {
        try {
            //Get Mac Address
            WifiManager manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = manager.getConnectionInfo();
            BaseConfig.MacId = info.getMacAddress();


        } catch (RuntimeException ec) {
            BaseConfig.MacId = "";
            ec.printStackTrace();

        }
    }


    private final void GetIniliz() {

        ButterKnife.bind(Splash_Screen.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        //Getting Imei and mac id....
        GetIMEI();
        GetMAC();


        String category = SPLASH_SCREEN_OPTION_3;

        Bundle extras = getIntent().getExtras();
        if (extras != null && extras.containsKey(SPLASH_SCREEN_OPTION)) {
            category = extras.getString(SPLASH_SCREEN_OPTION, SPLASH_SCREEN_OPTION_1);
        }

        mLogo.setAlpha((float) 0);
        setAnimation(category);

        Animation anim_app_banner = AnimationUtils.loadAnimation(this, R.anim.up_to_bottom_splash);
        top_banner.startAnimation(anim_app_banner);

        BaseConfig.Glide_LoadImageView(app_logo, BaseConfig.AppLogo);

        Animation anim_app_logo = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        app_logo.startAnimation(anim_app_logo);



        CallNextIntent();

        configApplicationLanguage();

    }

    private void CallNextIntent() {

        new Thread(new Runnable() {
            public void run() {

                while (progressStatus < 100) {
                    progressStatus = doSomeWork();

                    handler.post(() -> {
                        progressBar.setProgress(progressStatus);
                        progress_status.setText((String.valueOf(progressStatus)));
                    });
                }

                handler.post(() -> {

                    progressBar.setVisibility(View.GONE);

                    finish();

                    try {

                        String chkquery = "select enablepin as dstatus1 from drsettings where enablepin='true'";
                        boolean pinlay = BaseConfig.LoadReportsBooleanStatus(chkquery);
                        if (pinlay) {

                            finish();
                            String PINNO = BaseConfig.GetValues("select pin as ret_values from drsettings");
                            Intent intent = new Intent(getApplicationContext(), PinPassword.class);
                            intent.putExtra("PINNUMBER", PINNO);
                            startActivity(intent);
                            CustomIntent.customType(Splash_Screen.this, 1);
                        } else {
                            finish();
                            Intent intent = new Intent(getApplicationContext(), Login.class);
                            startActivity(intent);
                            CustomIntent.customType(Splash_Screen.this, 1);


                        }


                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }

                });
            }

            private int doSomeWork() {
                try {
                    // ---simulate doing some work---
                    Thread.sleep(30L);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ++progress;
                return
                        progress;
            }
        }).start();

    }


    /**
     * Animation depends on category.
     */
    private void setAnimation(String category) {

        animation1();

    }

    private void animation1() {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(mLogo, "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200L);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(mLogo, "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200L);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(mLogo, "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500L);
        animatorSet.start();


    }


}//END


