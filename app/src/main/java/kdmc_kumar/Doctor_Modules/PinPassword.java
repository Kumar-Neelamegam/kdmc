package kdmc_kumar.Doctor_Modules;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;


public class PinPassword extends AppCompatActivity {

    private static final String TAG = "PinLockView";
    private TextView profilename = null;
    private ImageView prfileimage = null;
    private ImageView switch_login = null;
    private PinLockView mPinLockView = null;
    private IndicatorDots mIndicatorDots = null;

    public PinPassword() {
    }

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin_password);


        switch_login = findViewById(R.id.switch_login);
        switch_login.setOnClickListener(view -> {

            PinPassword.this.finish();
            BaseConfig.Assistant_Task = "False";
            Intent intent = new Intent(PinPassword.this, Login.class);
            intent.putExtra("ALTERNATIVE", "TRUE");
            startActivityForResult(intent, 500);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


        });

        mPinLockView = findViewById(R.id.pin_lock_view);
        mIndicatorDots = findViewById(R.id.indicator_dots);

        mPinLockView.attachIndicatorDots(mIndicatorDots);

        String password = getIntent().getExtras().getString("PINNUMBER");

        String DecryptPin = new String(Base64.decode(password, Base64.DEFAULT));

        //Log.e("onCreate: Pin ", DecryptPin);

        PinLockListener mPinLockListener = new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                Log.d(TAG, "Pin complete: " + pin);


                // TODO: 7/20/2017 Pin 4 is Entered to check is valid or not

                if (pin.equalsIgnoreCase(DecryptPin)) {
                    //Sucess


                    PinPassword.this.finish();
                    BaseConfig.Assistant_Task = "False";
                    Intent intent = new Intent(PinPassword.this, Dashboard_NavigationMenu.class);
                    startActivityForResult(intent, 500);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    BaseConfig.StartWebservice_MasterWebservices_NODEJS(PinPassword.this,1);
                    BaseConfig.StartWebservice_ImportWebservices_NODEJS(PinPassword.this,1);
                    BaseConfig.StartWebservice_ExportWebservices_NODEJS(PinPassword.this,1);
                    BaseConfig.StartWebservice_UpdatedResults_scheduler(PinPassword.this,1);


                } else {

                    new CustomKDMCDialog(PinPassword.this)
                            .setLayoutColor(R.color.orange_500)
                            .setImage(R.drawable.ic_warning_black_24dp)
                            .setTitle(PinPassword.this.getString(R.string.warning))
                            .setDescription("Pin Number is incorrect Please Try again..")
                            .setPossitiveButtonTitle(PinPassword.this.getString(R.string.ok));

                    mIndicatorDots.requestFocus();


                }


            }

            @Override
            public void onEmpty() {
                Log.d(TAG, "Pin empty");
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                Log.d(TAG, "Pin changed, new length " + pinLength + " with intermediate pin " + intermediatePin);
            }
        };


        mPinLockView.setPinLockListener(mPinLockListener);

        profilename = findViewById(R.id.profile_name);
        prfileimage = findViewById(R.id.profile_image);


        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        //PinNumber length
        mPinLockView.setPinLength(4);

        //Loading Details
        LoadDoctorInfo(profilename, prfileimage);

        mPinLockView.setTextColor(ContextCompat.getColor(this, R.color.white));

        mIndicatorDots.setIndicatorType(IndicatorDots.IndicatorType.FILL_WITH_ANIMATION);
    }


    /**
     * Load doctor photo and name if exists
     * Muthukumar
     * 07/04/2017
     */
    private final void LoadDoctorInfo(TextView WelcomeTitle, ImageView DoctorPhoto) {
        boolean status = BaseConfig.LoadReportsBooleanStatus("select Docid as dstatus1 from Drreg");
        if (status) {
            String Photo_Path = BaseConfig.GetValues("select docimage as ret_values from Drreg");
            String Doctor_Name = BaseConfig.GetValues("select name as ret_values from Drreg");

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            WelcomeTitle.setText(getString(R.string.welcome) + " - " + Doctor_Name);


            try {

                BaseConfig.Glide_LoadImageView( DoctorPhoto,  Photo_Path);


            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }


    //End
}
