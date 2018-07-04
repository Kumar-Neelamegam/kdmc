package kdmc_kumar.Doctor_Modules;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DashboardAdapter.Dashboard_NavigationMenu;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Utilities_Others.CircleImageView;
import kdmc_kumar.Utilities_Others.ConnectionDetector;
import kdmc_kumar.Utilities_Others.CustomKDMCDialog;
import kdmc_kumar.Webservices_NodeJSON.ExportWebservices_NODEJS;


public class ChangePassword extends AppCompatActivity {


    @BindView(R.id.parent_layout)
    CoordinatorLayout parentLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbarChangepassword;
    @BindView(R.id.ic_back)
    AppCompatImageView icBack;
    @BindView(R.id.txvw_title)
    TextView txvwTitle;
    @BindView(R.id.ic_home)
    AppCompatImageView icHome;
    @BindView(R.id.ic_exit)
    AppCompatImageView icExit;
    @BindView(R.id.nestedscrollview_changepassword)
    NestedScrollView nestedscrollviewChangepassword;
    @BindView(R.id.imgvw_doctor_photo)
    CircleImageView imgvwDoctorPhoto;
    @BindView(R.id.txtvw_doctorname)
    TextView txtvwDoctorname;
    @BindView(R.id.txtvw_hospitalname)
    TextView txtvwHospitalname;
    @BindView(R.id.txtvw_specialization)
    TextView txtvwSpecialization;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_entercurrent_password)
    EditText edtEntercurrentPassword;
    @BindView(R.id.edt_enter_new_password)
    EditText edtEnterNewPassword;
    @BindView(R.id.edt_enter_confirm_password)
    EditText edtEnterConfirmPassword;
    @BindView(R.id.button_cancel)
    Button Btn_cancel;
    @BindView(R.id.button_submit)
    Button Btn_update;

    private ConnectionDetector cd = null;

    public ChangePassword() {
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected final void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.kdmc_layout_change_password);


        try {
            GETINITIALIZE();

            CONTROLLISTENERS();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }



        String Query="select docimage as ret_values from Drreg where Docid='" + BaseConfig.doctorId + '\'';
        String DoctorPhoto = BaseConfig.GetValues(Query);
        if (DoctorPhoto != null && DoctorPhoto.length() > 0) {
            BaseConfig.LoadPatientImage(DoctorPhoto, imgvwDoctorPhoto, 100);
        }
        txtvwDoctorname.setText(String.format("%s - %s", BaseConfig.doctorname, BaseConfig.docacademic));
        txtvwHospitalname.setText(BaseConfig.HOSPITALNAME);
        txtvwSpecialization.setText(BaseConfig.docspecli);

        edtUsername.setText(BaseConfig.username);

        Btn_cancel.setOnClickListener(v -> {
            ChangePassword.this.finish();
            Intent changepassword = new Intent(ChangePassword.this, Dashboard_NavigationMenu.class);
            startActivity(changepassword);
        });


        Btn_update.setOnClickListener(v -> {

            if (cd.isConnectingToInternet()) {
                if (edtEntercurrentPassword.getText().length() > 0) {
                    if (GetCurrentPassword()) {
                        if (edtEnterNewPassword.getText().length() > 0 && edtEnterConfirmPassword.getText().length() > 0) {
                            if (edtEnterNewPassword.getText().length() >= 6 && edtEnterNewPassword.getText().length() <= 10) {
                                if (edtEnterNewPassword.getText().toString().equalsIgnoreCase(edtEnterConfirmPassword.getText().toString())) {


                                    UpdatePasswordProcess();


                                } else {
                                    showSimplePopUp("New and confirm password fields does not match");
                                }
                            } else {
                                Toast.makeText(ChangePassword.this, "length should be between 6 and 10", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            showSimplePopUp("Enter new and confirm password");
                            edtEnterConfirmPassword.setText(null);
                            edtEnterConfirmPassword.requestFocus();
                            edtEnterNewPassword.setText(null);
                            edtEnterNewPassword.requestFocus();

                        }
                    } else {
                        showSimplePopUp("Invalid current password");
                        edtEntercurrentPassword.setText(null);
                        edtEntercurrentPassword.requestFocus();
                    }
                } else {
                    showSimplePopUp("Enter current password");
                    edtEntercurrentPassword.setError("Required");
                    edtEntercurrentPassword.requestFocus();
                }
            } else {
                Toast.makeText(ChangePassword.this, "Data Connection Not Available - Please Enable", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void UpdatePasswordProcess() {
        String cnfpswd = Base64.encodeToString(edtEnterConfirmPassword.getText().toString().getBytes(), Base64.DEFAULT).trim();

        final String Update_Query_Password = "update users set password='" + cnfpswd + "',dt='1',status='1' where username='" + edtUsername.getText().toString() + '\'';

        BaseConfig.SaveData(Update_Query_Password);

        try {
            ExportMobyDoctorUpdatedInformation();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        ShowSweetAlert("New password saved successfully - please re-login");

        updateSharedPreferencePassword(edtEnterConfirmPassword.getText().toString().trim());
    }


    private void GETINITIALIZE() {

        ButterKnife.bind(ChangePassword.this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        txvwTitle.setText(String.format("%s - %s", getString(R.string.app_name), getString(R.string.change_pwd)));

        setSupportActionBar(toolbarChangepassword);


        cd = new ConnectionDetector(getApplicationContext());

    }


    private void CONTROLLISTENERS() {

        icHome.setOnClickListener(view -> {

            ChangePassword.this.finish();
            Intent intent = new Intent(getApplicationContext(), Dashboard_NavigationMenu.class);
            startActivity(intent);

        });


        icExit.setOnClickListener(v -> BaseConfig.ExitSweetDialog(ChangePassword.this, null));


        icBack.setOnClickListener(view -> LoadBack());


    }

    private final void LoadBack()
    {

        BaseConfig.globalStartIntent(this, SettingActivity.class, null);

    }

    //#######################################################################################################
    private final void ShowSweetAlert(String str) {

        new CustomKDMCDialog(this)
                .setLayoutColor(R.color.green_500)
                .setImage(R.drawable.ic_success_done)
                .setTitle(this.getString(R.string.information)).setNegativeButtonVisible(View.GONE)
                .setDescription(str)
                .setNegativeButtonVisible(View.GONE)
                .setPossitiveButtonTitle(this.getString(R.string.ok))
                .setOnPossitiveListener(() -> {

                    ChangePassword.this.finish();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
                });

    }

    //#######################################################################################################
    private final boolean GetCurrentPassword() {
        int count = 0;
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery(
                "select password from users where username='" + edtUsername.getText() + "';", null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String oldpswd = Base64.encodeToString(edtEntercurrentPassword.getText().toString().getBytes(), Base64.DEFAULT).trim();

                    if (oldpswd.equals(c.getString(c.getColumnIndex("password")))) {

                        count++;
                        break;
                    }

                } while (c.moveToNext());
            }
        }
        c.close();
        db.close();
        return count != 0;
    }

    private void
    showSimplePopUp(String msg) {

        int image=R.drawable.ic_success_done;
        int color=R.color.green_500;
        if(2 ==1)//Success
        {
            image=R.drawable.ic_success_done;
            color=R.color.green_500;
        }else if(2 ==2)//Warning
        {
            image=R.drawable.ic_warning_black_24dp;
            color=R.color.orange_500;
        }

        new CustomKDMCDialog(this)
                .setNegativeButtonVisible(View.GONE)
                .setLayoutColor(color)
                .setImage(image).setNegativeButtonVisible(View.GONE)
                .setTitle(this.getString(R.string.information))
                .setDescription(msg)
                .setOnPossitiveListener(() -> {

                })
                .setPossitiveButtonTitle(this.getString(R.string.ok));


    }

    @Override
    public final void onBackPressed() {

        LoadBack();

    }


    private void updateSharedPreferencePassword(String cnfpswd) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("PWD", cnfpswd);
        editor.apply();
        editor.commit();
    }


    public void ExportMobyDoctorUpdatedInformation() throws IOException, XmlPullParserException {

        try {
            SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            JSONObject from_db_obj = new JSONObject();
            JSONArray export_jsonarray = new JSONArray();

            StringBuilder str = new StringBuilder();
            String Query = "select id,username,drid,IFNULL(imeinum,'')as imeinum,password from users where (status='1' or status='true') and dt='1';";


            String Local_id = "";
            Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        from_db_obj = new JSONObject();

                        from_db_obj.put("id", c.getString(c.getColumnIndex("id")));
                        from_db_obj.put("drid", c.getString(c.getColumnIndex("drid")));
                        from_db_obj.put("username", c.getString(c.getColumnIndex("username")));
                        from_db_obj.put("imeinum", c.getString(c.getColumnIndex("imeinum")));
                        from_db_obj.put("password", c.getString(c.getColumnIndex("password")));

                        export_jsonarray.put(from_db_obj);

                    } while (c.moveToNext());
                }

                c.close();

            }

            if (export_jsonarray != null && export_jsonarray.length() > 0) {

                String methodName = "updatepasswordDr";
                String jsonData = export_jsonarray.toString();

                String results = ExportWebservices_NODEJS.postDataExportData(methodName, jsonData, ChangePassword.this);

                JSONArray jsonArray = new JSONArray(results);

                for (int i = 0; i < jsonArray.length(); i++) {

                    //Exectue  Array of Result in one by one
                    JSONArray jsonArray1 = jsonArray.getJSONArray(i);

                    JSONObject jsonObject = jsonArray1.getJSONObject(0);


                }


                String ReturnValue = "";//= BaseConfig.ExportDataToServer("updatepasswordDr", export_jsonarray.toString());//Modified for exporting data to server @ MK
                JSONObject mainJson = new JSONObject(ReturnValue);
                //  JSONArray jsonArray = mainJson.getJSONArray("ReturnValue");
                JSONObject objJson = null;
                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    String ServerId = objJson.getString("ServerId");
                    String Id = objJson.getString("Id");

                    ContentValues values = new ContentValues();

                    values.put("dt", "0");
                    values.put("ServerId", ServerId);
                    db.update("users", values, "id=" + Id + "", null);

                }

            }

            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}//END
