package kdmc_kumar.Doctor_Modules

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.util.Log
import android.view.Window
import android.view.WindowManager.LayoutParams
import android.widget.ImageView
import android.widget.TextView
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.IndicatorDots.IndicatorType
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import displ.mobydocmarathi.com.R.*
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu
import kdmc_kumar.Core_Modules.BaseConfig
import kdmc_kumar.Utilities_Others.CustomKDMCDialog


class PinPassword : AppCompatActivity() {
    private var profilename: TextView? = null
    private var prfileimage: ImageView? = null
    private var switch_login: ImageView? = null
    private var mPinLockView: PinLockView? = null
    private var mIndicatorDots: IndicatorDots? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(LayoutParams.FLAG_FULLSCREEN,
                LayoutParams.FLAG_FULLSCREEN)
        this.setContentView(layout.activity_pin_password)


        this.switch_login = this.findViewById(id.switch_login)
        this.switch_login!!.setOnClickListener { view ->

            finish()
            BaseConfig.Assistant_Task = "False"
            val intent = Intent(this, Login::class.java)
            intent.putExtra("ALTERNATIVE", "TRUE")
            this.startActivityForResult(intent, 500)
            this.overridePendingTransition(anim.slide_in_right, anim.slide_out_left)


        }

        this.mPinLockView = this.findViewById(id.pin_lock_view)
        this.mIndicatorDots = this.findViewById(id.indicator_dots)

        this.mPinLockView!!.attachIndicatorDots(this.mIndicatorDots)

        val password = this.intent.extras!!.getString("PINNUMBER")

        val DecryptPin = String(Base64.decode(password, Base64.DEFAULT))

        //Log.e("onCreate: Pin ", DecryptPin);

        val mPinLockListener = object : PinLockListener {
            override fun onComplete(pin: String) {
                Log.d(PinPassword.TAG, "Pin complete: $pin")


                // TODO: 7/20/2017 Pin 4 is Entered to check is valid or not

                if (pin.equals(DecryptPin, ignoreCase = true)) {
                    //Sucess


                    finish()
                    BaseConfig.Assistant_Task = "False"
                    val intent = Intent(this@PinPassword, Dashboard_NavigationMenu::class.java)
                    this@PinPassword.startActivityForResult(intent, 500)
                    this@PinPassword.overridePendingTransition(anim.slide_in_right, anim.slide_out_left)


                    BaseConfig.StartWebservice_MasterWebservices_NODEJS(this@PinPassword, 1)
                    BaseConfig.StartWebservice_ImportWebservices_NODEJS(this@PinPassword, 1)
                    BaseConfig.StartWebservice_ExportWebservices_NODEJS(this@PinPassword, 1)
                    BaseConfig.StartWebservice_UpdatedResults_scheduler(this@PinPassword, 1)


                } else {

                    CustomKDMCDialog(this@PinPassword)
                            .setLayoutColor(color.orange_500)
                            .setImage(drawable.ic_warning_black_24dp)
                            .setTitle(getString(string.warning))
                            .setDescription("Pin Number is incorrect Please Try again..")
                            .setPossitiveButtonTitle(getString(string.ok))

                    this@PinPassword.mIndicatorDots!!.requestFocus()


                }


            }

            override fun onEmpty() {
                Log.d(PinPassword.TAG, "Pin empty")
            }

            override fun onPinChange(pinLength: Int, intermediatePin: String) {
                Log.d(PinPassword.TAG, "Pin changed, new length $pinLength with intermediate pin $intermediatePin")
            }
        }


        this.mPinLockView!!.setPinLockListener(mPinLockListener)

        this.profilename = this.findViewById(id.profile_name)
        this.prfileimage = this.findViewById(id.profile_image)


        //mPinLockView.setCustomKeySet(new int[]{2, 3, 1, 5, 9, 6, 7, 0, 8, 4});
        //mPinLockView.enableLayoutShuffling();

        //PinNumber length
        this.mPinLockView!!.pinLength = 4

        //Loading Details
        this.LoadDoctorInfo(this.profilename, this.prfileimage)

        this.mPinLockView!!.textColor = ContextCompat.getColor(this, color.white)

        this.mIndicatorDots!!.indicatorType = IndicatorType.FILL_WITH_ANIMATION
    }


    /**
     * Load doctor photo and name if exists
     * Muthukumar
     * 07/04/2017
     */
    private fun LoadDoctorInfo(WelcomeTitle: TextView?, DoctorPhoto: ImageView?) {
        val status = BaseConfig.LoadReportsBooleanStatus("select Docid as dstatus1 from Drreg")
        if (status) {
            val Photo_Path = BaseConfig.GetValues("select docimage as ret_values from Drreg")
            val Doctor_Name = BaseConfig.GetValues("select name as ret_values from Drreg")

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            //Log.e("LoadDoctorInfo: ", Doctor_Name);

            WelcomeTitle!!.text = this.getString(string.welcome) + " - " + Doctor_Name


            try {

                BaseConfig.Glide_LoadImageView(DoctorPhoto, Photo_Path)


            } catch (e: Exception) {
                e.printStackTrace()
            }


        }

    }

    override fun onBackPressed() {
        //super.onBackPressed();
    }

    companion object {

        private val TAG = "PinLockView"
    }


    //End
}
