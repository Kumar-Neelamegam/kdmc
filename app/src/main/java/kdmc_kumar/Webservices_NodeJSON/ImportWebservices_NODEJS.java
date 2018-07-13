package kdmc_kumar.Webservices_NodeJSON;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.RemoteViews;

import com.bumptech.glide.Glide;
import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.MyPatientDrawer;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.controller.api.PatientMapping;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.controller.api.PatientMappingFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.model.beans.GetPatientMappingRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getPatientMapping.model.beans.PatientMappingResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.controller.api.UpdateDB;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.controller.api.UpdateDBFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.model.beans.GetUpdateDBRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.getUpdateDB.model.beans.UpdateDBResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNew;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNewFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.controller.api.PostPatId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.controller.api.PostPatIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.model.beans.PosPatIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.posPatId.model.beans.PosPatIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.DoctorIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.PostDoctorIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.controller.api.PostImmunizationInfo;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.controller.api.PostImmunizationInfoFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.model.beans.PosImmunizationInfoRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.model.beans.PosImmunizationInfoResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMax;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMaxFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.IsUpdateMaxResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.PostIsUpdateMaxRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.controller.api.PostIsUpdateMaxDocId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.controller.api.PostIsUpdateMaxDocIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.model.beans.IsUpdateMaxDocIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMaxDocId.model.beans.PostIsUpdateMaxDocIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestid;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestidFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PatidMtestidResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PostPatidMtestidRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdList;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdListFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PatientIdListResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PostPatientIdListRequest;

import static kdmc_kumar.Core_Modules.BaseConfig.LoadDoctorValues;

public class ImportWebservices_NODEJS {

    private static Context ctx = null;
    /**
     * Ponnu
     * Notification - kdmc for investigation
     */

    //============================================================================================================
    //============================================================================================================

    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    BaseConfig bc = new BaseConfig();
    private String sndserverid = "";
    private String patient_Id = "";
    private String MedId = "";

    public ImportWebservices_NODEJS(final Context ctx) {
        super();
        this.ctx = ctx;
    }

    public static void LoadForVaccination(final String pid, final String pname, final String agegen, final String mob) {
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        final int upid = 0;

        final Cursor c = db
                .rawQuery(
                        "select distinct vaccinename,schedule from listofvaccine",
                        null);

        if (null != c) {
            if (c.moveToFirst()) {
                do {

                    final String InsertQuery = "Insert into Vaccination(patid,pname,agegen,mobnum,drid,vaccinename,schedule,Isupdate,isactive) values"
                            + "('"
                            + pid
                            + "','"
                            + pname
                            + "','"
                            + agegen
                            + "','"
                            + mob
                            + "','"
                            + ""
                            + "','"
                            + c.getString(c.getColumnIndex("vaccinename"))
                            + "','"
                            + c.getString(c.getColumnIndex("schedule"))
                            + "','0','0');";
                    db.execSQL(InsertQuery);

                } while (c.moveToNext());
            }

            c.close();
            db.close();
        }
    }

    private static String getPatientList() throws JSONException {
        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();
        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (null != c) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }
        // JSONObject parentData = new JSONObject();
        //  parentData.put("PatientList", patientIdList);
        Objects.requireNonNull(c).close();
        db.close();

        return patientIdList.toString();
    }

    private static String GetDocid() {
        String str = "";

        try {
            final SQLiteDatabase db = BaseConfig.GetDb();

            // database = dbHelper.getWritableDatabase();
            final Cursor c = db.rawQuery("Select distinct Docid from Drreg", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        /*
                         * str.append(c.getString(c.getColumnIndex("token")));
                         * str.append("^");
                         */
                        str = c.getString(c.getColumnIndex("Docid"));

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
        } catch (final Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return str;
    }

    private static String GetCurrentSeenValue(final String patId, final String mTestId, final String allTest) {


        String bedIdStr = "";
        try {

            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c = db.rawQuery("select * from Medicaltestdtls where Ptid ='" + patId.trim() + "' and alltest = '" + allTest + "' and mTestId = '" + mTestId + '\'', null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        bedIdStr = c.getString(c.getColumnIndex("seen"));

                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (final Exception e) {
            e.printStackTrace();
        }
        if (bedIdStr == null) {
            return "0";
        }
        return bedIdStr;
    }

    static boolean CheckNodeServer() {

        boolean statusvalue = false;
        try {
            HttpURLConnection conn = (HttpURLConnection) (new URL(BaseConfig.AppNodeIP)).openConnection();
            conn.setUseCaches(false);
            conn.connect();
            int status = conn.getResponseCode();

            if (status == 200) {
                statusvalue = true;
                BaseConfig.server_connectivity_status = true;
            } else {
                BaseConfig.server_connectivity_status = false;
            }
            conn.disconnect();
            return statusvalue;
        } catch (Exception e) {


        }
        return statusvalue;
    }

    public static void CheckDbUpdatesNodeJs(Context context) {
        try {

            final UpdateDB updateDB;
            final String DoctorID = ImportWebservices_NODEJS.GetDocid();


            final SQLiteDatabase db = BaseConfig.GetDb();

            // Instantiate a controller
            final MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(context);
            final UpdateDBFactory controllerFactory = new UpdateDBFactory(magnetClient);
            updateDB = controllerFactory.obtainInstance();


            // FIXME : set proper value for the parameters
            final String contentType = "application/json";
            final GetUpdateDBRequest body = new GetUpdateDBRequest();
            body.setDocid(DoctorID);
            body.setImei(BaseConfig.Imeinum);
            body.setMac(BaseConfig.MacId);

            final Call<UpdateDBResult> callObject = updateDB.getUpdateDB(
                    contentType,
                    body, null);

            final UpdateDBResult result = callObject.get();

            final String res = result.getResults();


            final JSONArray jsonArray = new JSONArray(res);


            for (int i = 0; i < jsonArray.length(); i++) {
                String Insert_TABLE_ps = ((JSONObject) jsonArray.get(i)).getString("query");
                db.execSQL(Insert_TABLE_ps);

            }


            db.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return returns_Val;
    }

    @SuppressLint("LongLogTag")
    public final void ExecuteAll() {

        if (BaseConfig.CheckNetwork(this.ctx)) {

            try {

                if (CheckNodeServer()) {


                    Log.e("###########", "################");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("MobyDoctor BackGround", "Thread Import Service running 1");
                    Log.e("###########", "################");


                    LoadDoctorValues();// TODO: 1/24/2018 Note Used

                    //HID IsUpdateMax
                    this.InsertPatientMapping(); // TODO: 1/24/2018 Completed

                    //Docid
                    this.ImportDoctorInfo();         // TODO: 1/24/2018 Completed
                    this.ImportReportGallery();      // TODO: 1/24/2018 Completed
                   // this.ImportPatientInfo();        // TODO: 1/24/2018 Completed
                    this.ImportAppointment();        // TODO: 1/24/2018 Completed
                    this.ImportOnlineConsultation(); // TODO: 1/24/2018 Completed


                    //IsUpdateMax
                    this.ImportScanMaster();           // TODO: 1/24/2018 Completed
                    this.ImportDoctorsList();          // TODO: 1/24/2018 Completed
                    this.ImportTest();                 // TODO: 1/24/2018 Completed
                    this.ImportPharmacyDtls();         // TODO: 1/24/2018 Completed -- waste
                    this.ImportDiagnosticCentreDtls(); // TODO: 1/24/2018 Completed -- waste

                    //IsUpdateMax DocId
                    this.ImportOperationSchedule();          // TODO: 1/24/2018 Completed

                    //PatientIdList
                    this.ImportBind_ClinicalInformation();   // TODO: 1/24/2018 Completed


                    //Investigation
                    this.ImportBind_MedicalTestDtls();       // TODO: 1/24/2018 Completed
                    this.ImportBind_MedicalTest();           // TODO: 1/24/2018 Completed
                    this.ImportScanDtls();                   // TODO: 1/24/2018 Completed
                    this.ImportXrayDtls();                   // TODO: 1/24/2018 Completed
                    this.ImportEEGDtls();                    // TODO: 1/24/2018 Completed
                    this.ImportECGDtls();                    // TODO: 1/24/2018 Completed
                    this.ImportAngiogram();                  // TODO: 1/24/2018 Completed


                    this.ImportMprescribed();                // TODO: 1/24/2018 Completed


                    //Casenotes
                    this.ImportBind_Diagnosis();             // TODO: 1/24/2018 Completed
                    this.ImportBind_GeneralExamination();    // TODO: 1/24/2018 Completed
                    this.ImportBind_Cardiovascular();        // TODO: 1/24/2018 Completed
                    this.ImportBind_RespiratorySystem();     // TODO: 1/24/2018 Completed
                    this.ImportBind_Gastrointestinal();      // TODO: 1/24/2018 Completed
                    this.ImportBind_Neurology();             // TODO: 1/24/2018 Completed
                    this.ImportCaseNote_Renal();             // TODO: 1/24/2018 Completed
                    this.ImportCaseNote_Endocrine();         // TODO: 1/24/2018 Completed
                    this.ImportCaseNote_ClinicalData();      // TODO: 1/24/2018 Completed
                    this.ImportCaseNote_Locomotor();         // TODO: 1/24/2018 Completed
                    this.ImportBind_CaseNote_OtherSystem();  // TODO: 1/24/2018 Completed
                    this.ImportCaseNote_Dental();  // TODO: 1/24/2018 Completed

                    //Docid Ptid
                    this.InsertImmunizationInformation();  // TODO: 1/24/2018 Completed

                    //Docid Patid Mtest id list
                    this.ImportupdatedTestDtls();  // TODO: 1/24/2018 Completed
                    this.ImportupdatedScanDtls();  // TODO: 1/24/2018 Completed
                    this.ImportupdatedXrayDtls();  // TODO: 1/24/2018 Completed
                    this.ImportupdatedEEGDtls();   // TODO: 1/24/2018 Completed //err
                    this.ImportupdatedECGDtls();   // TODO: 1/24/2018 Completed
                    this.ImportupdatedAngiogram(); // TODO: 1/24/2018 Completed


                    //Patid
                    this.Importmast_Operations(); // TODO: 1/24/2018 Completed


                    //Patid Mtestid
                    this.Importmast_Upload();            // TODO: 1/24/2018 Completed
                    this.Import_ExaminationBlood_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationStool_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationUrine_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationANC_Test();   // TODO: 1/24/2018 Completed
                    this.Import_ExaminationHIV_Test();   // TODO: 1/24/2018 Completed


                    LoadInvestigation_Notification(); // Investigation Notification

                    this.CheckDbUpdatesNodeJs(ctx);  // TODO: 1/24/2018 Completed




                }
            } catch (final Exception e) {
                e.printStackTrace();
            }


        }

    }

    private void InsertPatientMapping() {


        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;

        try {


            final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
            final PatientMappingFactory patientMappingFactory = new PatientMappingFactory(magnetMobileClient);
            final PatientMapping patientMapping = patientMappingFactory.obtainInstance();

            final GetPatientMappingRequest getPatientMappingRequest = new GetPatientMappingRequest();
            getPatientMappingRequest.sethID(BaseConfig.doctorId);
            getPatientMappingRequest.setIsUpdateMax("0");

            final Call<PatientMappingResult> resultCall = patientMapping.getPatientMapping(getPatientMappingRequest, null);

            final String resultsRequestSOAP = resultCall.get().getResults();

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    final String id_data = String.valueOf(objJson.getString("id"));
                    final String Patid_data = String.valueOf(objJson.getString("Patid"));
                    final String Docid_data = String.valueOf(objJson.getString("Docid"));
                    final String reffered_data = String.valueOf(objJson.getString("reffered"));
                    final String name_data = String.valueOf(objJson.getString("name"));
                    final String age_data = String.valueOf(objJson.getString("age"));
                    final String gender_data = String.valueOf(objJson.getString("gender"));
                    final String DOB_data = String.valueOf(objJson.getString("DOB"));
                    final String weight_data = String.valueOf(objJson.getString("weight"));
                    final String Country_data = String.valueOf(objJson.getString("Country"));
                    final String State_data = String.valueOf(objJson.getString("State"));
                    final String District_data = String.valueOf(objJson.getString("District"));
                    final String Address_data = String.valueOf(objJson.getString("Address"));
                    final String pincode_data = String.valueOf(objJson.getString("pincode"));
                    final String phone_data = String.valueOf(objJson.getString("phone"));
                    final String PMH_data = String.valueOf(objJson.getString("PMH"));
                    //String PC_data = String.valueOf(objJson.getString("PC"));//Commented because to save base64 into images

                    final String Allergy_data = String.valueOf(objJson.getString("Allergy"));
                    final String IsActive_data = String.valueOf(objJson.getString("IsActive"));
                    final String imei_data = String.valueOf(objJson.getString("imei"));
                    final String city_data = String.valueOf(objJson.getString("city"));
                    final String altphone_data = String.valueOf(objJson.getString("altphone"));
                    final String Actdate_data = String.valueOf(objJson.getString("Actdate"));
                    final String Isupdate_data = String.valueOf(objJson.getString("Isupdate"));
                    final String Address1_data = String.valueOf(objJson.getString("Address1"));
                    final String email_data = String.valueOf(objJson.getString("email"));
                    final String caretaker_data = String.valueOf(objJson.getString("caretaker"));
                    final String crtknum_data = String.valueOf(objJson.getString("crtknum"));
                    final String relationship_data = String.valueOf(objJson.getString("relationship"));
                    final String willingsms_data = String.valueOf(objJson.getString("willingsms"));
                    final String smsto_data = String.valueOf(objJson.getString("smsto"));
                    final String smsfor_data = String.valueOf(objJson.getString("smsfor"));
                    final String willingblood_data = String.valueOf(objJson.getString("willingblood"));
                    final String willingeye_data = String.valueOf(objJson.getString("willingeye"));
                    final String hereditary_data = String.valueOf(objJson.getString("hereditary"));
                    final String mbid_data = String.valueOf(objJson.getString("mbid"));
                    final String pinno_data = String.valueOf(objJson.getString("pinno"));
                    final String issms_data = String.valueOf(objJson.getString("issms"));
                    final String bloodgroup_data = String.valueOf(objJson.getString("bloodgroup"));
                    final String Policyname_data = String.valueOf(objJson.getString("Policyname"));
                    final String Inscompany_data = String.valueOf(objJson.getString("Inscompany"));
                    final String Insamount_data = String.valueOf(objJson.getString("Insamount"));
                    final String Insvalidity_data = String.valueOf(objJson.getString("Insvalidity"));
                    final String Authorhospital_data = String.valueOf(objJson.getString("Authorhospital"));
                    final String Isprfupdate_data = String.valueOf(objJson.getString("Isprfupdate"));
                    final String Pages_data = String.valueOf(objJson.getString("Pages"));
                    final String docReferName_data = String.valueOf(objJson.getString("docReferName"));
                    final String docReferNo_data = String.valueOf(objJson.getString("docReferNo"));
                    String enable_inpatient_data = String.valueOf(objJson.getString("enable_inpatient"));
                    if ("true".equalsIgnoreCase(enable_inpatient_data)) {
                        enable_inpatient_data = "1";
                    } else if ("false".equalsIgnoreCase(enable_inpatient_data)) {
                        enable_inpatient_data = "0";
                    }

                    final String admitdt_data = String.valueOf(objJson.getString("admitdt"));
                    final String admittime_data = String.valueOf(objJson.getString("admittime"));
                    final String Ward_data = String.valueOf(objJson.getString("Ward"));
                    final String Bed_data = String.valueOf(objJson.getString("Bed"));
                    final String roomno_data = String.valueOf(objJson.getString("roomno"));
                    final String dischargedt_data = String.valueOf(objJson.getString("dischargedt"));
                    final String HospitalId_data = String.valueOf(objJson.getString("HospitalId"));
                    final String Presentwithco_data = String.valueOf(objJson.getString("Presentwithco"));
                    final String doc_refer_name_data = String.valueOf(objJson.getString("doc_refer_name"));
                    final String doc_refer_no_data = String.valueOf(objJson.getString("doc_refer_no"));
                    final String IsFeeExemp_data = String.valueOf(objJson.getString("IsFeeExemp"));
                    final String FeeExempCateg_data = String.valueOf(objJson.getString("FeeExempCateg"));
                    final String bplCardNo_data = String.valueOf(objJson.getString("bplCardNo"));
                    final String AadharCardNo_data = String.valueOf(objJson.getString("AadharCardNo"));
                    final String FeeExempReason_data = String.valueOf(objJson.getString("FeeExempReason"));
                    final String caste_data = String.valueOf(objJson.getString("caste"));
                    final String income_data = String.valueOf(objJson.getString("income"));
                    final String under_care_of_data = String.valueOf(objJson.getString("under_care_of"));
                    final String Occupation_data = String.valueOf(objJson.getString("Occupation"));
                    final String distime = String.valueOf(objJson.getString("distime"));
                    final String disdate = String.valueOf(objJson.getString("disdate"));
                    final String Fathername_data = String.valueOf(objJson.getString("Fathername"));
                    final String spouse = String.valueOf(objJson.getString("spouse"));

                    values1 = new ContentValues();

                    values1.put("Patid", Patid_data);
                    values1.put("Docid", Docid_data);
                    values1.put("referred_by", reffered_data);
                    values1.put("name", name_data);
                    try {
                        if (name_data.contains(".")) {
                            final String[] prefixData = name_data.split("\\.");
                            values1.put("prefix", prefixData[0]);
                            values1.put("patientname", prefixData[1].trim());
                        }
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    String PatientPhoto = "";
                    try {
                        final Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ctx, String.valueOf(objJson.getString("PC")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("PC"))).asBitmap().into(-1, -1).get();

                        PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, Patid_data.replace("/", "-"));

                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    values1.put("age", age_data);
                    values1.put("gender", gender_data);
                    values1.put("DOB", DOB_data);
                    values1.put("weight", weight_data);
                    values1.put("Country", Country_data);
                    values1.put("State", State_data);
                    values1.put("District", District_data);
                    values1.put("Address", Address_data);
                    values1.put("pincode", pincode_data);
                    values1.put("phone", phone_data);
                    values1.put("PMH", PMH_data);
                    values1.put("PC", PatientPhoto);
                    values1.put("Allergy", Allergy_data);
                    values1.put("IsActive", IsActive_data);
                    values1.put("imei", imei_data);
                    values1.put("city", city_data);
                    values1.put("altphone", altphone_data);
                    values1.put("Actdate", Actdate_data);
                    values1.put("Isupdate", Isupdate_data);
                    values1.put("Address1", Address1_data);
                    values1.put("email", email_data);
                    values1.put("caretaker", caretaker_data);
                    values1.put("crtknum", crtknum_data);
                    values1.put("relationship", relationship_data);
                    values1.put("willingsms", willingsms_data);
                    values1.put("smsto", smsto_data);
                    values1.put("smsfor", smsfor_data);
                    values1.put("willingblood", willingblood_data);
                    values1.put("willingeye", willingeye_data);
                    values1.put("hereditary", hereditary_data);
                    values1.put("PatientPin", pinno_data);
                    values1.put("bloodgroup", bloodgroup_data);
                    values1.put("policyname", Policyname_data);
                    values1.put("insurancecompany", Inscompany_data);
                    values1.put("amountinsured", Insamount_data);
                    values1.put("validity", Insvalidity_data);
                    values1.put("inshosp", Authorhospital_data);
                    values1.put("enable_inpatient", enable_inpatient_data);
                    values1.put("admitdt", admitdt_data);
                    values1.put("admit_time", admittime_data);
                    values1.put("wardno", Ward_data);
                    values1.put("bedno", Bed_data);
                    values1.put("roomno", roomno_data);
                    values1.put("discharge_date", disdate);
                    values1.put("discharge_time", distime);
                    values1.put("HospitalId", HospitalId_data);
                    values1.put("Presentwithco", Presentwithco_data);
                    values1.put("doc_refer_name", doc_refer_name_data);
                    values1.put("doc_refer_no", doc_refer_no_data);
                    values1.put("IsFeeExemp", IsFeeExemp_data.toString().equalsIgnoreCase("true")? "Yes" : "No");
                    values1.put("FeeExempCateg", FeeExempCateg_data);
                    values1.put("bplCardNo", bplCardNo_data);
                    values1.put("AadharCardNo", AadharCardNo_data);
                    values1.put("FeeExempReason", FeeExempReason_data);
                    values1.put("caste", caste_data);
                    values1.put("income", income_data);
                    values1.put("under_care_of", under_care_of_data);
                    values1.put("referred_by", doc_refer_name_data);
                    values1.put("Occupation", Occupation_data);
                    values1.put("Fathername", Fathername_data);
                    values1.put("spouse", spouse);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Patreg where Patid='" + Patid_data + '\'');

                    if (!GetStatus) {

                        db.insert("Patreg", null, values1);
                        ImportWebservices_NODEJS.LoadForVaccination(Patid_data, name_data, age_data + "-" + gender_data, phone_data);

                    } else {

                        db.update("Patreg", values1, "Patid ='" + Patid_data + '\'', null);

                        String Update_Vaccination = "update  Vaccination set pname='"+name_data+ "',agegen='"+age_data+ '-'+ gender_data+ "',mobnum='"+phone_data+ "' where patid='"+Patid_data+ "';";
                        BaseConfig.SaveData(Update_Vaccination);

                    }


                }
            }

        } catch (final Exception e) {
            e.printStackTrace();
        }

        db.close();

    }

    private void ImportDoctorInfo() {
        try {

            final SQLiteDatabase db = BaseConfig.GetDb();
            final String DoctorID = ImportWebservices_NODEJS.GetDocid();
            final String MethodName = "infodrupdateJSON";
            try {
                final String resultsRequestSOAP = this.getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;


                    for (int i = 0; i < jsonArray.length(); i++) {


                        objJson = jsonArray.getJSONObject(i);
                        final String Id = String.valueOf(objJson.getString("Id"));
                        final String Docid = String.valueOf(objJson.getString("Docid"));
                        final String name = String.valueOf(objJson.getString("name"));
                        final String age = String.valueOf(objJson.getString("age"));
                        final String gender = String.valueOf(objJson.getString("gender"));
                        final String DOB = String.valueOf(objJson.getString("DOB"));
                        final String RegId = String.valueOf(objJson.getString("RegId"));
                        final String TaxNo = String.valueOf(objJson.getString("TaxNo"));
                        final String Academic = String.valueOf(objJson.getString("Academic"));
                        final String Academic_text = String.valueOf(objJson.getString("Academic_text"));
                        final String Specialization = String.valueOf(objJson.getString("Specialization"));
                        final String Specialization_text = String.valueOf(objJson.getString("Specialization_text"));
                        final String Country = String.valueOf(objJson.getString("Country"));
                        final String State = String.valueOf(objJson.getString("State"));
                        final String District = String.valueOf(objJson.getString("District"));
                        final String Username = String.valueOf(objJson.getString("Username"));
                        final String Password = String.valueOf(objJson.getString("Password"));
                        final String Address = String.valueOf(objJson.getString("Address"));
                        final String pincode = String.valueOf(objJson.getString("pincode"));
                        final String Lanline = String.valueOf(objJson.getString("Lanline"));
                        final String Mobile = String.valueOf(objJson.getString("Mobile"));
                        final String Time = String.valueOf(objJson.getString("Time"));
                        final String consultation = String.valueOf(objJson.getString("consultation"));
                        final String clinicname = String.valueOf(objJson.getString("clinicname"));
                        final String photo = String.valueOf(objJson.getString("photo"));
                        final String mail = String.valueOf(objJson.getString("mail"));
                        final String Ismail = String.valueOf(objJson.getString("Ismail"));
                        final String homephone = String.valueOf(objJson.getString("homephone"));
                        final String IsActive = String.valueOf(objJson.getString("IsActive"));
                        final String Actdate = String.valueOf(objJson.getString("InsertedDt"));
                        final String Imei = String.valueOf(objJson.getString("Imei"));
                        String docsign = String.valueOf(objJson.getString("docsign"));
                        final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                        final String Issms = String.valueOf(objJson.getString("Issms"));
                        final String paymenttype = String.valueOf(objJson.getString("paymenttype"));
                        final String pay = String.valueOf(objJson.getString("pay"));
                        final String paymentdate = String.valueOf(objJson.getString("paymentdate"));
                        final String IsPaid = String.valueOf(objJson.getString("IsPaid"));
                        final String MCI_No = String.valueOf(objJson.getString("MCI_No"));
                        final String panno = String.valueOf(objJson.getString("panno"));
                        final String Dr_serviceno = String.valueOf(objJson.getString("Dr_serviceno"));
                        final String promotion = String.valueOf(objJson.getString("promotion"));
                        final String editdate = String.valueOf(objJson.getString("editdate"));
                        final String MAC = String.valueOf(objJson.getString("MAC"));
                        final String AppLicenseCode = String.valueOf(objJson.getString("AppLicenseCode"));
                        final String OnlineFee = String.valueOf(objJson.getString("OnlineFee"));
                        final String OnlineFeeOthers = String.valueOf(objJson.getString("OnlineFeeOthers"));
                        final String Isprfupdate = String.valueOf(objJson.getString("Isprfupdate"));
                        final String HID = String.valueOf(objJson.getString("HID"));
                        final String InsertedDt = String.valueOf(objJson.getString("InsertedDt"));
                        final String Insertedby = String.valueOf(objJson.getString("Insertedby"));


                        Bitmap theBitmap = null;
                        try {
                            theBitmap = Glide.
                                    with(this.ctx).asBitmap().
                                    load(photo).
                                    into(150, 150). // Width and height
                                    get();
                        } catch (final InterruptedException e) {
                            e.printStackTrace();
                        } catch (final Exception e) {
                            e.printStackTrace();
                        }

                        final ContentValues cv = new ContentValues();

                        //cv.put("id",);
                        cv.put("Docid", Docid);
                        cv.put("prefix", "");
                        cv.put("drname", name);
                        cv.put("name", name);
                        cv.put("cliname", clinicname);
                        cv.put("age", age);
                        cv.put("gender", gender);
                        cv.put("DOB", DOB);
                        cv.put("PAN", panno);
                        cv.put("MCI", MCI_No);
                        cv.put("RegId", RegId);
                        cv.put("ClinicTaxNo", TaxNo);
                        cv.put("TaxNo", TaxNo);
                        cv.put("Academic", Academic_text);
                        cv.put("Specialization", Specialization_text);
                        cv.put("Country", Country);
                        cv.put("State", State);
                        cv.put("District", District);
                        cv.put("Address", Address);
                        cv.put("Address2", Address);
                        cv.put("pincode", pincode);
                        cv.put("Lanline", Lanline);
                        cv.put("Mobile", Mobile);
                        cv.put("Homephone", homephone);
                        cv.put("Actdate", Actdate);
                        cv.put("Imei", Imei);
                        cv.put("docimage", BaseConfig.saveURLImagetoSDcard(theBitmap,
                                Docid.replace("/", "")));      //
                        cv.put("IsActive", "1");
                        cv.put("emailid", mail);


                        try {

                            final Bitmap theBitmap1 = BaseConfig.Glide_GetBitmap(ctx, docsign);

                            docsign = BaseConfig.saveURLImagetoSDcard(theBitmap1, Docid.replace("/", "") + "_sign");

                        } catch (final Exception e) {

                        }

                        cv.put("docsign", docsign);      //docsign


                        cv.put("IsRegistered", "1");
                        cv.put("IsSmsSent", Issms);
                        cv.put("IsEmailSent", Ismail);
                        cv.put("Paymentpaid", pay);
                        cv.put("Isupdate", "1");
                        cv.put("IsEdited", Isupdate);
                        cv.put("EditedDT", Isupdate);
                        cv.put("promotion", promotion);
                        cv.put("MAC", MAC);
                        cv.put("AppLicenseCode", AppLicenseCode);
                        cv.put("OnlineFee", OnlineFee);
                        cv.put("OnlineFeeOthers", OnlineFeeOthers);
                        cv.put("device_deactivate", IsActive);
                        cv.put("device_status", IsActive);
                        cv.put("hospital_id", HID);
                        cv.put("dept", "");
                        cv.put("HID", HID);

                        // BaseConfig.GetDb().insert("Drreg", null , cv);

                        final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Drreg where Docid='" + Docid + '\'');

                        if (!GetStatus) {
                            db.insert("Drreg", null, cv);
                        } else {
                            db.update("Drreg", cv, "Docid='" + Docid + '\'', null);
                        }

                        String Update_Query1 = "update users set doctorname='"
                                + name + '\'';
                        BaseConfig.SaveData(Update_Query1);

                        String Update_Query2 = "update drsettings set drname='"
                                + name + '\'';
                        BaseConfig.SaveData(Update_Query2);
                    }
                }


            } catch (final Exception e) {
                e.printStackTrace();

            }
            db.close();

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportReportGallery() {
        try {

            final String DoctorID = ImportWebservices_NODEJS.GetDocid();

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


            final String MethodName = "infoBindUpload";
            try {
                final String resultsRequestSOAP = this.getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject objJson = jsonArray.getJSONObject(i);
                        String returnid = String.valueOf(objJson.getString("id"));

                        final ContentValues values;

                        String PatientPhoto = null;
                        try {
                            Log.e("Report Image URL: ", objJson.getString("Upload"));
                            final Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ctx, String.valueOf(objJson.getString("Upload")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("Upload"))).asBitmap().into(-1, -1).get();
                            PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("Patid")).replace("/", ""));

                        } catch (final Exception e) {

                        }


                        values = new ContentValues();
                        values.put("Docid", String.valueOf(objJson.getString("Docid")));
                        values.put("Patid", String.valueOf(objJson.getString("Patid")));
                        values.put("Name", "");
                        values.put("agegender", "");
                        values.put("Diagnosisid", "");
                        values.put("dt", "");
                        values.put("Remarks", "");
                        values.put("IsUpdate", 1);
                        values.put("patientphoto", "");
                        values.put("ReportPhoto", PatientPhoto);
                        values.put("ReportType", String.valueOf(objJson.getString("Fname")));
                        values.put("ServerId", returnid);
                        // db.insert(BaseConfig.TABLE_REPORTGALLERY, null, values);

                        final String Query = "select Docid as dstatus1 from ReportGallery where ServerId='" + returnid + '\'';
                        final boolean q = BaseConfig.LoadReportsBooleanStatus(Query);

                        if (!q) {
                            db.insert(BaseConfig.TABLE_REPORTGALLERY, null, values);
                        } else {
                            db.update(BaseConfig.TABLE_REPORTGALLERY, values, "ServerId='" + returnid + '\'', null);

                        }
                    }


                }

                db.close();

            } catch (final Exception e) {
                e.printStackTrace();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportPatientInfo() {

        try {


            final String DoctorID = ImportWebservices_NODEJS.GetDocid();

            final SQLiteDatabase db = BaseConfig.GetDb();
            try {

                final String MethodName = "infopatupdate";
                final String resultsRequestSOAP = this.getDoctorIdRESTCALL(DoctorID, MethodName);

                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject objJson = jsonArray.getJSONObject(i);

                        String Update_Query = "update patreg set name='"
                                + String.valueOf(objJson.getString("name")) + "',age='"
                                + String.valueOf(objJson.getString("age"))
                                + "',gender='"
                                + String.valueOf(objJson.getString("gender")) + "',DOB='"
                                + String.valueOf(objJson.getString("dob"))
                                + "',Country='"
                                + String.valueOf(objJson.getString("country"))
                                + "',Address='"
                                + String.valueOf(objJson.getString("address"))
                                + "',pincode='"
                                + String.valueOf(objJson.getString("pincode")) + "',PC='"
                                + String.valueOf(objJson.getString("pc"))
                                + "',city='"
                                + String.valueOf(objJson.getString("city"))
                                + "',phone='"
                                + String.valueOf(objJson.getString("phone"))
                                + "',altphone='"
                                + String.valueOf(objJson.getString("altphone"))
                                + "',Address1='"
                                + String.valueOf(objJson.getString("address1"))
                                + "',email='"
                                + String.valueOf(objJson.getString("email"))
                                + "' where Patid='"
                                + String.valueOf(objJson.getString("Patid")) + "';";

                        BaseConfig.SaveData(Update_Query);


                        String Update_Vaccination = "update  Vaccination set pname='"
                                + String.valueOf(objJson.getString("name"))
                                + "',agegen='"
                                + String.valueOf(objJson.getString("age"))
                                + '-'
                                + String.valueOf(objJson.getString("gender"))
                                + "',mobnum='"
                                + String.valueOf(objJson.getString("phone"))
                                + "' where patid='"
                                + String.valueOf(objJson.getString("Patid")) + "';";
                        BaseConfig.SaveData(Update_Vaccination);

                    }

                    db.close();

                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportAppointment() {

        final String DoctorID = ImportWebservices_NODEJS.GetDocid();

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        try {

            ContentValues values;

            final String MethodName = "infobookappoinment";
            final String resultsRequestSOAP = this.getDoctorIdRESTCALL(DoctorID, MethodName);

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {

                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject objJson = jsonArray.getJSONObject(i);
                    values = new ContentValues();
                    values.put("ServerId", String.valueOf(objJson.getString("Id")));
                    values.put("patid", String.valueOf(objJson.getString("PatId")));
                    values.put("name", String.valueOf(objJson.getString("Patname")));
                    values.put("mobnum", String.valueOf(objJson.getString("Phone")));
                    values.put("agegen", String.valueOf(objJson.getString("Agegen")));
                    values.put("docid", String.valueOf(objJson.getString("DocId")));
                    values.put("Actdt", String.valueOf(objJson.getString("Bookingdate")));
                    values.put("Appoimentdt", String.valueOf(objJson.getString("Appdate")));
                    values.put("token", String.valueOf(objJson.getString("Tokenno")));
                    values.put("Isupdate", 1);

                    Bitmap theBitmap = null;
                    try {
                        theBitmap = BaseConfig.Glide_GetBitmap(ctx, String.valueOf(objJson.getString("Patphoto")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("Patphoto"))).asBitmap().into(-1, -1).get();

                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    final String PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("PatId")).replace("/", ""));//+System.currentTimeMillis());

                    values.put("photo", PatientPhoto);
                    values.put("Iscancel", "False");

                    final String Query = "select Id as dstatus1 from Appoinment where ServerId='" + String.valueOf(objJson.getString("Id")) + "' " +
                            "and patid='" + String.valueOf(objJson.getString("PatId")) + "' " +
                            "and Appoimentdt='" + String.valueOf(objJson.getString("Appdate")) + '\'';

                    final boolean q = BaseConfig.LoadReportsBooleanStatus(Query);

                    if (!q) {
                        db.insert(BaseConfig.TABLE_APPOINTMENTS, null, values);
                    } else {
                        db.update(BaseConfig.TABLE_APPOINTMENTS, values, "patid='" + String.valueOf(objJson.getString("PatId")) + "' and Appoimentdt='" + String.valueOf(objJson.getString("Appdate")) + '\'', null);

                    }


                }

            }
            db.close();

        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }
    }

    private void ImportOnlineConsultation() {

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;

        int flag;
        try {

            String MethodName = "GetOnlinesharePatdetails";
            String resultsRequestSOAP = this.getDoctorIdRESTCALL(BaseConfig.doctorId, MethodName);

            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                flag = 1;
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject objJson = jsonArray.getJSONObject(i);

                    this.sndserverid = String.valueOf(objJson.getString("Id"));
                    this.patient_Id = String.valueOf(objJson.getString("Patid"));
                    this.MedId = String.valueOf(objJson.getString("medid"));


                    final Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ctx, String.valueOf(objJson.getString("PC")));//Glide.with(this.ctx).load(String.valueOf(objJson.getString("PC"))).asBitmap().into(-1, -1).get();
                    final String PatientPhoto = BaseConfig.saveURLImagetoSDcard(theBitmap, String.valueOf(objJson.getString("Patid")).replace("/", ""));//+System.currentTimeMillis());


                    values1 = new ContentValues();
                    values1.put("pid", String.valueOf(objJson.getString("Patid")));
                    values1.put("age", String.valueOf(objJson.getString("age")));
                    values1.put("gender", String.valueOf(objJson.getString("gender")));
                    values1.put("pname", String.valueOf(objJson.getString("name")));
                    values1.put("pphoto", PatientPhoto);
                    values1.put("docid", String.valueOf(objJson.getString("docid")));
                    values1.put("healthsummary", String.valueOf(objJson.getString("ptsummary")));
                    values1.put("ServerId", String.valueOf(objJson.getString("Id")));
                    values1.put("Actdt", String.valueOf(objJson.getString("dates")));
                    values1.put("IsActive", "True");
                    values1.put("Medid", String.valueOf(objJson.getString("medid")));
                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineConsultancy where ServerId='" + String.valueOf(objJson.getString("Id")) + '\'');

                    if (!GetStatus) {
                        db.insert("OnlineConsultancy", null, values1);
                    } else {
                        db.update("OnlineConsultancy", values1, "ServerId='" + String.valueOf(objJson.getString("Id")) + "' and IsUpdate!='0' and IsActive!='False'", null);
                    }


                }
            } else {
                flag = 0;
            }


            //sending serverid
            if (1 == flag) {

                //New Method is UPdated
                final String IsUpdateMax = this.sndserverid;
               // String updateOnlinesharePatdetails = "UpdateOnlinesharePatdetails";
               // this.postIsUpdateMaxRESTMethod(IsUpdateMax, updateOnlinesharePatdetails);


                try {


                    String updateOnlinesharePatdetails = "GetOnlineshareMprescribed";
                    final String PatId = this.patient_Id;
                    final String MtestId = this.MedId;
                    resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, updateOnlinesharePatdetails);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                        JSONObject objJson;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);
                            final String ServerId = objJson.getString("Id");

                            values1 = new ContentValues();


                            values1.put("pid", String.valueOf(objJson.getString("ptid")));
                            values1.put("pname", String.valueOf(objJson.getString("pname")));
                            values1.put("status", String.valueOf(objJson.getString("Medid")));
                            values1.put("TreatmentFor", String.valueOf(objJson.getString("treatmentfor")));
                            values1.put("Medicinename", String.valueOf(objJson.getString("medicinename")));
                            values1.put("Actdt", String.valueOf(objJson.getString("Actdate")));
                            values1.put("pphoto", this.sndserverid);

                            final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineSharedMedicine where pphoto='" + String.valueOf(objJson.getString("Id")) +"'");

                            if (GetStatus) {

                                //Update Details
                                db.update("OnlineSharedMedicine", values1,"pphoto='"+ServerId+"'",null);

                            }else
                            {
                                db.insert("OnlineSharedMedicine", null, values1);
                            }
                        }
                    }
                } catch (final Exception e) {

                    e.printStackTrace();
                }


                //inserting report from server

                String getOnlineshareReports = "GetOnlineshareReports";
                final String DoctorID = this.patient_Id + "##" + BaseConfig.doctorId + "##" + this.sndserverid;
                String doctorIdRESTCALL = this.getDoctorIdRESTCALL(DoctorID, getOnlineshareReports);

                if (!"[]".equalsIgnoreCase(doctorIdRESTCALL) && !"".equalsIgnoreCase(doctorIdRESTCALL)) {

                    final JSONArray jsonArray = new JSONArray(doctorIdRESTCALL);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject objJson = jsonArray.getJSONObject(i);

                        values1 = new ContentValues();

                        final Bitmap theBitmap = BaseConfig.Glide_GetBitmap(ctx, String.valueOf(objJson.getString("Upload")));//Glide.with(this.ctx).load(objJson.getString("Upload")).asBitmap().into(-1, -1).get();
                        final String ReportPhoto = BaseConfig.saveURLImagetoSDcard1(theBitmap, UUID.randomUUID() + objJson.getString("ptid").replace("/", ""));//+System.currentTimeMillis();

                        values1.put("ServerId", objJson.getString("shid"));
                        values1.put("pid", objJson.getString("ptid"));
                        values1.put("docid", objJson.getString("docid"));
                        values1.put("Actdt", objJson.getString("dates"));
                        values1.put("ReportGallery", ReportPhoto);
                        values1.put("FileName", objJson.getString("Fname"));
                        values1.put("FileType", objJson.getString("Filetype"));

                        final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from OnlineConsultancyDtls where ServerId='" + String.valueOf(objJson.getString("shid")) +"'");

                        if (GetStatus) {

                            //Update Details
                            db.update("OnlineConsultancyDtls",  values1,"ServerId='" + String.valueOf(objJson.getString("shid")) +"'",null);

                        }else
                        {
                            db.insert("OnlineConsultancyDtls", null, values1);
                        }
                    }
                }


            }

            db.close();
        } catch (final Exception e) {
            e.printStackTrace();
        }


    }

    private void ImportScanMaster() {

        String IsActive;
        String str = "";

        final String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from scan";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (null != c) {
            if (c.moveToFirst()) {
                do {

                    if (null != c.getString(c.getColumnIndex("IsUpdateMax"))) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }

        final String IsUpdateMax = str;
        final String MethodName = "ImportMstrScan";


        ContentValues values1;

        try {


            final String resultsRequestSOAP = this.postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    ///{ "Id": "1", "ScanFor": "Head", "IsActive": "True", "IsUpdate": "1" }
                    final String ServerId = String.valueOf(objJson.getString("Id"));
                    final String ScanFor = String.valueOf(objJson.getString("Scan"));
                    final String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));

                    final String e = objJson.getString("IsActive");

                    if ("true".equalsIgnoreCase(e)) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("scantype", ScanFor);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from scan where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("scan", null, values1);
                    } else {
                        db.update("scan", values1, "ServerId='" + ServerId + '\'', null);
                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }

        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportDoctorsList() {

        String str = "";

        final String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_DoctorsList";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }


        ContentValues values1;

        final String IsUpdateMax = str;
        final String MethodName = "ImportMstrDoctorsList";

        try {


            final String resultsRequestSOAP = this.postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    //Id,Docid,name,Academic_text,Specialization_text,Country,[State],District,Address,pincode,Mobile,clinicname,mail,HID
                    final String ServerId = String.valueOf(objJson.getString("Id"));
                    final String Docid = String.valueOf(objJson.getString("Docid"));
                    final String name = String.valueOf(objJson.getString("name"));
                    final String Academic_text = String.valueOf(objJson.getString("Academic_text"));
                    final String Specialization_text = String.valueOf(objJson.getString("Specialization_text"));
                    final String Country = String.valueOf(objJson.getString("Country"));
                    final String State = String.valueOf(objJson.getString("State"));
                    final String District = String.valueOf(objJson.getString("District"));
                    final String Address = String.valueOf(objJson.getString("Address"));
                    final String pincode = String.valueOf(objJson.getString("pincode"));
                    final String Mobile = String.valueOf(objJson.getString("Mobile"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    final String mail = String.valueOf(objJson.getString("mail"));
                    final String HID = String.valueOf(objJson.getString("HID"));

                    String IsActive;

                    String isActive = objJson.getString("IsActive");

                    if ("true".equalsIgnoreCase(isActive)) {
                        isActive = "1";
                    } else {
                        isActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Docid", Docid);
                    values1.put("name", name);
                    values1.put("Academic_text", Academic_text);
                    values1.put("Specialization_text", Specialization_text);
                    values1.put("Country", Country);
                    values1.put("State", State);
                    values1.put("District", District);
                    values1.put("Address", Address);
                    values1.put("pincode", pincode);
                    values1.put("Mobile", Mobile);
                    values1.put("clinicname", clinicname);
                    values1.put("mail", mail);
                    values1.put("HID", HID);
                    values1.put("IsActive", isActive);
                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_DoctorsList where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("Mstr_DoctorsList", null, values1);
                    } else {
                        db.update("Mstr_DoctorsList", values1, "ServerId='" + ServerId + '\'', null);
                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportTest() {


        String IsActive;
        String str = "";

        final String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from testname";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }


        ContentValues values1;

        final String IsUpdateMax = str;
        final String MethodName = "ImportMstrTest";

        try {


            final String resultsRequestSOAP = this.postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    ///{ "IsActive": "True", "IsUpdate": "1", "SubTest": "HiB 3D", "Id": "1", "TestName": "HiB", "TestFor": "Hand" }
                    final String ServerId = String.valueOf(objJson.getString("Id"));
                    final String SubTest = String.valueOf(objJson.getString("SubTest"));
                    //String TestFor = String.valueOf(objJson.getString("TestFor"));
                    final String testName = String.valueOf(objJson.getString("TestName"));
                    final String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    final String e = objJson.getString("IsActive");
                    final String Unit = objJson.getString("Unit");
                    final String NormalRange = objJson.getString("NormalRange");
                    final String TemplateName = objJson.getString("TemplateName");

                    if ("true".equalsIgnoreCase(e)) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("SubTest", SubTest);
                    /// values1.put("TestFor", TestFor);
                    values1.put("testName", testName);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("Unit", Unit);
                    values1.put("NormalRange", NormalRange);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Testname where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("Testname", null, values1);
                    } else {
                        db.update("Testname", values1, "ServerId='" + ServerId + '\'', null);
                    }

                    // TODO: 8/23/2017  Insert Fav Subtest names in MyFavTable
                    final String IsFav = objJson.getString("IsFav");


                    if ("True".equalsIgnoreCase(IsFav) || "1".equalsIgnoreCase(IsFav)) {
                        //Insert My Fav Table Details
                        final ContentValues values22 = new ContentValues();
                        values22.put("Testname", testName);
                        values22.put("SubTest", SubTest);
                        values22.put("IsUpdate", IsUpdate);
                        values22.put("TemplateName", TemplateName);
                        db.insert("MyFavTest", null, values22);

                    }

                }


            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportPharmacyDtls() {
        try {

            final StringBuilder str = new StringBuilder();
            final String Query = "select IFNULL(max(ServerId),'0') as ServerId from Pharmacy";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;
            // database = dbHelper.getWritableDatabase();
            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        if (c.getString(c.getColumnIndex("ServerId")) != null) {

                            str.append(c.getString(c.getColumnIndex("ServerId")));

                        } else {
                            str.append('0');

                        }

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
            if (!"".equals(str.toString())) {

                try {

                    final String IsUpdateMax = str.toString();
                    final String MethodName = "pharmachyname_select";

                    final String resultsRequestSOAP = this.postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                        JSONObject objJson;

                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            final ContentValues values;
                            values = new ContentValues();
                            values.put("ServerId", String.valueOf(objJson.getString("Id")));
                            values.put("pharmacyname", String.valueOf(objJson.getString("pharmacyname")));
                            values.put("pharmaddr", String.valueOf(objJson.getString("address")));
                            values.put("contactnum", String.valueOf(objJson.getString("contactnum")));
                            db.insert("Pharmacy", null, values);

                        }

                    }

                } catch (final InterruptedException e) {
                    e.printStackTrace();
                } catch (final ExecutionException e) {
                    e.printStackTrace();
                } catch (final JSONException e) {
                    e.printStackTrace();
                } catch (final SchemaException e) {
                    e.printStackTrace();
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportDiagnosticCentreDtls() {
        try {

            final StringBuilder str = new StringBuilder();
            final String Query = "select IFNULL(max(ServerId),'0') as ServerId from Laboratory";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;
            // database = dbHelper.getWritableDatabase();
            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        // upid=Integer.parseInt(c.getString(c.getColumnIndex("id")));
                        if (c.getString(c.getColumnIndex("ServerId")) != null) {
                            str.append(c.getString(c.getColumnIndex("ServerId")));
                        } else {
                            str.append('0');
                        }
                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }
            if (!"".equals(str.toString())) {

                try {

                    final String IsUpdateMax = str.toString();
                    final String MethodName = "labname_select";

                    final String resultsRequestSOAP = this.postIsUpdateMaxRESTMethod(IsUpdateMax, MethodName);


                    if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                        final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                        JSONObject objJson;
                        ContentValues values;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            objJson = jsonArray.getJSONObject(i);

                            //Id,labname,contactnum,address
                            values = new ContentValues();
                            values.put("ServerId", objJson.getString("Id"));
                            values.put("labname", objJson.getString("labname"));
                            values.put("labaddr", objJson.getString("address"));
                            values.put("contactnum", objJson.getString("contactnum"));
                            db.insert("Laboratory", null, values);

                        }

                    }

                } catch (final InterruptedException e) {
                    e.printStackTrace();
                } catch (final ExecutionException e) {
                    e.printStackTrace();
                } catch (final JSONException e) {
                    e.printStackTrace();
                } catch (final SchemaException e) {
                    e.printStackTrace();
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportOperationSchedule() {

	/*	Id
                ServerId
		SessionName
				IsActive
		IsUpdate*/


        String str = "";

        final String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from operation_details";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {


                    if (c.getString(c.getColumnIndex("IsUpdateMax")) != null) {
                        str = c.getString(c.getColumnIndex("IsUpdateMax"));
                    } else {
                        str = "0";
                    }

                } while (c.moveToNext());

            }
        }

        ContentValues values1;

        try {


            final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
            final PostIsUpdateMaxDocIdFactory postDoctorIdFactory = new PostIsUpdateMaxDocIdFactory(magnetMobileClient);
            final PostIsUpdateMaxDocId postDoctorId = postDoctorIdFactory.obtainInstance();

            //Set Body Data
            final PostIsUpdateMaxDocIdRequest body = new PostIsUpdateMaxDocIdRequest();
            body.setDocId(BaseConfig.doctorId);
            body.setIsUpdateMax(str);

            final Call<IsUpdateMaxDocIdResult> resultCall = postDoctorId.postIsUpdateMaxDocId(body, null);

            final String resultsRequestSOAP = resultCall.get().getResults();


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    //{ "FoodItems": [ { "Expr1": 1, "Docid": "23", "operationId": 1, "OperationNo": "234", "OperationName": "Appendicitis", "patid": "PID/2014/1511/13353", "PatientName": "Kiran", "Gender": "Male", "ScheduleDate": "2016-09-19T19:15:23.557", "Fromtime": null, "Totime": null, "Rate": null, "IsActive": true, "curdate": "2016-09-19T19:15:23.557", "IsUpdate": 1, "Status": "Completed" } ] }
                    objJson = jsonArray.getJSONObject(i);

                    final String ServerId = String.valueOf(objJson.getString("operationId"));
                    final String DocList = String.valueOf(objJson.getString("Doctors"));
                    final String DocId = String.valueOf(objJson.getString("docref"));
                    final String OperationNo = objJson.getString("OperationNo");
                    final String OperationName = objJson.getString("OperationName");
                    final String patid = objJson.getString("patid");
                    final String PatientName = objJson.getString("PatientName");
                    final String ScheduleDate = objJson.getString("ScheduleDate");
                    final String Fromtime = objJson.getString("Fromtime");
                    final String rate = objJson.getString("Rate");
                    final String Totime = objJson.getString("Totime");
                    final String Status = objJson.getString("Status");
                    final String IsActive = objJson.getString("IsActive");
                    final String IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("operation_name", OperationName);
                    values1.put("operation_no", OperationNo);
                    values1.put("patient_name", patid);
                    values1.put("operation_date", ScheduleDate);
                    values1.put("from_time", Fromtime);
                    values1.put("to_time", Totime);
                    values1.put("doctors", DocList);
                    values1.put("DocId", DocId);
                    values1.put("Rate", rate);
                    values1.put("Status", Status);
                    values1.put("patient_name", PatientName);



                    /*if already serverid irunthal update
                        else
                    serverid ilana insert*/

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from operation_details where ServerId='" + ServerId + '\'');

                    if (!GetStatus) {
                        db.insert("operation_details", null, values1);
                    } else {
                        db.update("operation_details", values1, "ServerId='" + ServerId + '\'', null);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_ClinicalInformation() throws JSONException {


        String IsActive;
        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        final Cursor c = db.rawQuery(Query, null);


        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }


        ContentValues values1;

        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_ClinicalInformation";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String Previous_MedicalHistory = String.valueOf(objJson.getString("Previous_MedicalHistory"));
                    final String Hereditary = String.valueOf(objJson.getString("Hereditary"));
                    final String AllergicTo = String.valueOf(objJson.getString("AllergicTo"));
                    final String Perhis_Smoking = String.valueOf(objJson.getString("Perhis_Smoking"));
                    final String Perhis_Alcohol = String.valueOf(objJson.getString("Perhis_Alcohol"));
                    final String Perhis_Tobacco = String.valueOf(objJson.getString("Perhis_Tobacco"));
                    final String Perhis_Others = String.valueOf(objJson.getString("Perhis_Others"));
                    final String Medication_History = String.valueOf(objJson.getString("Medication_History"));
                    final String Family_Med_History = String.valueOf(objJson.getString("Family_Med_History"));
                    String Actdate = String.valueOf(objJson.getString("Actdate"));
                    try {

                        Actdate = BaseConfig.DateFormatter3(Actdate);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    //
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String Perhis_BowelHabits = String.valueOf(objJson.getString("Perhis_BowelHabits"));
                    final String Perhis_Micturition = String.valueOf(objJson.getString("Perhis_Micturition"));
                    final String present_illness = String.valueOf(objJson.getString("present_illness"));
                    final String past_illness = String.valueOf(objJson.getString("past_illness"));
                    final String any_medication = String.valueOf(objJson.getString("any_medication"));
                    final String obstetric = String.valueOf(objJson.getString("obstetric"));
                    final String gynaec = String.valueOf(objJson.getString("gynaec"));
                    final String surgical_notes = String.valueOf(objJson.getString("surgical_notes"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    values1.put("ServerId", Id);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Previous_MedicalHistory", Previous_MedicalHistory);
                    values1.put("Hereditary", Hereditary);
                    values1.put("AllergicTo", AllergicTo);
                    values1.put("Perhis_Smoking", Perhis_Smoking);
                    values1.put("Perhis_Alcohol", Perhis_Alcohol);
                    values1.put("Perhis_Tobacco", Perhis_Tobacco);
                    values1.put("Perhis_Others", Perhis_Others);
                    values1.put("Medication_History", Medication_History);
                    values1.put("Family_Med_History", Family_Med_History);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);

                    values1.put("Perhis_BowelHabits", Perhis_BowelHabits);
                    values1.put("Perhis_Micturition", Perhis_Micturition);
                    values1.put("present_illness", present_illness);
                    values1.put("past_illness", past_illness);
                    values1.put("any_medication", any_medication);
                    values1.put("obstetric", obstetric);
                    values1.put("gynaec", gynaec);
                    values1.put("surgical_notes", surgical_notes);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);

                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from ClinicalInformation where ServerId=" + Id);

                    if (GetStatus) {
                        db.update("ClinicalInformation", values1, "ServerId=" + Id, null);

                    } else {
                        db.insert("ClinicalInformation", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_MedicalTestDtls() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();
        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_MedicalTestDtls";

        ContentValues values1;
        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("id"));
                    final String Ptid = String.valueOf(objJson.getString("Ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docname = String.valueOf(objJson.getString("docname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String alltest = String.valueOf(objJson.getString("alltest"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String diagid = String.valueOf(objJson.getString("diagid"));
                    final String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    final String dsign = String.valueOf(objJson.getString("dsign"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String imei = String.valueOf(objJson.getString("imei"));
                    final String testname = String.valueOf(objJson.getString("testname"));
                    final String subtestname = String.valueOf(objJson.getString("subtestname"));
                    final String testvalue = String.valueOf(objJson.getString("testvalue"));
                    final String testsummary = String.valueOf(objJson.getString("testsummary"));
                    final String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    final String testreportimg = String.valueOf(objJson.getString("testreportimg"));
                    final String scanname = String.valueOf(objJson.getString("scanname"));
                    final String subscanname = String.valueOf(objJson.getString("subscanname"));
                    final String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    final String scansummary = String.valueOf(objJson.getString("scansummary"));
                    final String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String WUP = String.valueOf(objJson.getString("WUP"));
                    final String subtestid = String.valueOf(objJson.getString("subtestid"));
                    final String bps = String.valueOf(objJson.getString("bps"));
                    final String bpd = String.valueOf(objJson.getString("bpd"));
                    final String temperature = String.valueOf(objJson.getString("temperature"));
                    final String weight = String.valueOf(objJson.getString("weight"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsPaid = String.valueOf(objJson.getString("IsPaid"));


                    String ResultValue = "0";
                    if ("0".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Nil";
                    } else if ("1".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Positive";

                    } else if ("2".equalsIgnoreCase(String.valueOf(objJson.getString("result")))) {
                        ResultValue = "Negative";
                    }

                    values1 = new ContentValues();

                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("alltest", alltest);
                    values1.put("mtestid", mtestid);
                    values1.put("testname", testname);
                    values1.put("subtestname", subtestname);
                    values1.put("testvalue", testvalue);
                    values1.put("testsummary", testsummary);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("testreportimg", testreportimg);
                    values1.put("subtestid", subtestid);
                    values1.put("bps", bps);
                    values1.put("bpd", bpd);
                    values1.put("temperature", temperature);
                    values1.put("weight", weight);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("HID", HID);
                    values1.put("Isupdate", "1");
                    values1.put("ServerId", Id);
                    values1.put("result", ResultValue);


                    // TODO: 3/28/2017 Adding New Column in IsNew
                    values1.put("IsNew", 1);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltestdtls where ServerId=" + Id);

                    if (GetStatus) {

                        if (BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltestdtls where ServerId=" + Id + " and seen='1'")) {


                            values1.put("ReadytoUpdate", 2);
                            values1.put("seen", "1");
                        } else {
                            if (!"".equals(testvalue) && !"".equalsIgnoreCase(testvalue) && !"null".equalsIgnoreCase(testvalue)) {
                                values1.put("ReadytoUpdate", 2);
                                values1.put("seen", "0");
                            } else {
                                values1.put("ReadytoUpdate", 0);
                            }

                        }


                        db.update("Medicaltestdtls", values1, "ServerId=" + Id + ' ', null);

                    } else {

                        if (!"".equals(testvalue) && !"".equalsIgnoreCase(testvalue) && !"null".equalsIgnoreCase(testvalue)) {
                            values1.put("ReadytoUpdate", 2);
                            values1.put("seen", "0");
                        } else {
                            values1.put("ReadytoUpdate", 0);
                        }

                        db.insert("Medicaltestdtls", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportBind_MedicalTest() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_MedicalTest";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("id"));
                    final String Ptid = String.valueOf(objJson.getString("Ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docname = String.valueOf(objJson.getString("docname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String diagid = String.valueOf(objJson.getString("diagid"));
                    final String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    final String dsign = String.valueOf(objJson.getString("dsign"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String imei = String.valueOf(objJson.getString("imei"));
                    final String testname = String.valueOf(objJson.getString("testname"));
                    final String subtestname = String.valueOf(objJson.getString("subtestname"));
                    final String testvalue = String.valueOf(objJson.getString("testvalue"));
                    final String testsummary = String.valueOf(objJson.getString("testsummar" +
                            'y'));
                    // String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    final String testreportimg = String.valueOf(objJson.getString("testreportimg"));
                    final String scanname = String.valueOf(objJson.getString("scanname"));
                    final String subscanname = String.valueOf(objJson.getString("subscanname"));
                    final String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    final String scansummary = String.valueOf(objJson.getString("scansummary"));
                    final String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String Dig_Center = String.valueOf(objJson.getString("Dig_Center"));
                    final String HID = String.valueOf(objJson.getString("HID"));

                    final String Diagnosis = String.valueOf(objJson.getString("Diagnosis"));
                    final String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));


                    values1 = new ContentValues();
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("Diagnosis", Diagnosis);
                    values1.put("HID", HID);
                    values1.put("Isupdate", "1");


                              /*  if (!testvalue.equals("") && !testvalue.equalsIgnoreCase("") && !testvalue.equalsIgnoreCase("null")) {
                                    values1.put("ReadytoUpdate", 2);
                                } else {
                                    values1.put("ReadytoUpdate", 0);
                                }*/

                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Medicaltest where (ServerId=" + Id + " or ServerId!=null) and Ptid='" + Ptid + "' and mtestid='" + mtestid + '\'');

                    if (GetStatus) {
                        db.update("Medicaltest", values1, "ServerId=" + Id + ' ', null);

                    } else {
                        db.insert("Medicaltest", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();

    }

    private void ImportScanDtls() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();

        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_ScanDetailsJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Ptid = String.valueOf(objJson.getString("Ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docname = String.valueOf(objJson.getString("docname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    final String dsign = String.valueOf(objJson.getString("dsign"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String imei = String.valueOf(objJson.getString("imei"));
                    final String scanname = String.valueOf(objJson.getString("scanname"));
                    final String subscanname = String.valueOf(objJson.getString("subscanname"));
                    final String scanvalue = String.valueOf(objJson.getString("scanvalue"));
                    final String scansummary = String.valueOf(objJson.getString("scansummary"));
                    final String scanreportimg = String.valueOf(objJson.getString("scanreportimg"));
                    //String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    //String ReadytoUpdate = String.valueOf(objJson.getString("ReadytoUpdate"));
                    //String ValueUpdated = String.valueOf(objJson.getString("ValueUpdated"));
                    final String testid = String.valueOf(objJson.getString("testid"));
                    final String ServerId = String.valueOf(objJson.getString("id"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("scanname", scanname);
                    values1.put("subscanname", subscanname);
                    values1.put("scanvalue", scanvalue);
                    values1.put("scansummary", scansummary);
                    values1.put("scanreportimg", scanreportimg);
                    values1.put("Isupdate", 1);

                    if (!scanvalue.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("testid", testid);
                    values1.put("ServerId", ServerId);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Scantest where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Scantest", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("Scantest", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportXrayDtls() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }


        ContentValues values1;

        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_XrayDetailsJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docname = String.valueOf(objJson.getString("docname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String pmobnum = String.valueOf(objJson.getString("pmobnum"));
                    final String dsign = String.valueOf(objJson.getString("dsign"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String imei = String.valueOf(objJson.getString("imei"));

                    final String xray = String.valueOf(objJson.getString("xray"));
                    final String xrayvalue = String.valueOf(objJson.getString("xrayvalue"));
                    final String xraysummary = String.valueOf(objJson.getString("xraysummary"));
                    final String xrayimg = String.valueOf(objJson.getString("xrayimg"));
                    final String xrayid = String.valueOf(objJson.getString("xrayid"));
                    final String ServerId = String.valueOf(objJson.getString("Id"));

                    values1 = new ContentValues();

                    // values1.put("id", id);
                    values1.put("Ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docname", docname);
                    values1.put("docid", docid);
                    values1.put("clinicname", clinicname);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("pmobnum", pmobnum);
                    values1.put("dsign", dsign);
                    values1.put("mtestid", mtestid);
                    values1.put("imei", imei);
                    values1.put("xray", xray);
                    values1.put("xrayvalue", xrayvalue);
                    values1.put("xraysummary", xraysummary);
                    values1.put("xrayimg", xrayimg);
                    values1.put("Isupdate", 1);
                    values1.put("xrayid", xrayid);
                    values1.put("ServerId", ServerId);

                    if (!xrayvalue.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from XRAY where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("XRAY", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("XRAY", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportEEGDtls() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_EEGDetailsJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
//                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String ECGfor = String.valueOf(objJson.getString("ECGfor"));
                    final String Comment = String.valueOf(objJson.getString("Comment"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String Summary = String.valueOf(objJson.getString("Summary"));
                    final String Valuedate = String.valueOf(objJson.getString("Valuedate"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String ServerId = String.valueOf(objJson.getString("id"));

                    values1 = new ContentValues();


                    values1.put("mtestid", mtestid);
                    values1.put("ptid", Ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //        values1.put("pagegen", pagegen);
                    values1.put("Isupdate", 1);
                    values1.put("EEGFor", ECGfor);
                    values1.put("Comment", Comment);
                    values1.put("Summary", Summary);
                    values1.put("Valuedata", Valuedate);
                    values1.put("HID", HID);
                    values1.put("ServerId", ServerId);


                    if (!Summary.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from EEG where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("EEG", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("EEG", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportECGDtls() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_ECGDetailsJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String ServerId = String.valueOf(objJson.getString("id"));
                    final String Docid = String.valueOf(objJson.getString("Docid"));
                    final String Ptid = String.valueOf(objJson.getString("Ptid"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String gender = String.valueOf(objJson.getString("gender"));
                    final String age = String.valueOf(objJson.getString("age"));
                    final String mobnum = String.valueOf(objJson.getString("mobnum"));
                    final String ECGTestFor = String.valueOf(objJson.getString("ECGTestFor"));
                    final String Comment = String.valueOf(objJson.getString("Comment"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    final String PRate = String.valueOf(objJson.getString("PRate"));
                    final String Ecg = String.valueOf(objJson.getString("Ecg"));
                    final String Ecgfile = String.valueOf(objJson.getString("Ecgfile"));
                    final String Scan = String.valueOf(objJson.getString("Scan"));
                    final String Scanfile = String.valueOf(objJson.getString("Scanfile"));
                    final String Treadmill = String.valueOf(objJson.getString("Treadmill"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    final String bloodgroup = String.valueOf(objJson.getString("bloodgroup"));
                    final String ecgrate = String.valueOf(objJson.getString("ecgrate"));
                    final String ecgqrs = String.valueOf(objJson.getString("ecgqrs"));
                    final String ecgst = String.valueOf(objJson.getString("ecgst"));
                    final String ecgt = String.valueOf(objJson.getString("ecgt"));
                    final String ecgrhyrhm = String.valueOf(objJson.getString("ecgrhyrhm"));
                    final String ecgaxis = String.valueOf(objJson.getString("ecgaxis"));
                    final String ecgpulse = String.valueOf(objJson.getString("ecgpulse"));
                    final String imeino = String.valueOf(objJson.getString("imeino"));
                    final String docsign = String.valueOf(objJson.getString("docsign"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String Bundlebranchblock = String.valueOf(objJson.getString("Bundlebranchblock"));
                    final String Conductiondefects = String.valueOf(objJson.getString("Conductiondefects"));
                    final String PR = String.valueOf(objJson.getString("PR"));
                    final String uid = String.valueOf(objJson.getString("uid"));
                    final String WUP = String.valueOf(objJson.getString("WUP"));
                    final String ecgText = String.valueOf(objJson.getString("ecgText"));
                    final String ecgTreadmillText = String.valueOf(objJson.getString("ecgTreadmillText"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsPaid = String.valueOf(objJson.getString("IsPaid"));

                    values1 = new ContentValues();


                    // values1.put("id", id);
                    values1.put("Docid", Docid);
                    values1.put("Ptid", Ptid);
                    values1.put("mtestid", mtestid);
                    values1.put("pname", pname);
                    values1.put("gender", gender);
                    values1.put("age", age);
                    values1.put("mobnum", mobnum);
                    values1.put("ECGTestFor", ECGTestFor);
                    values1.put("Comment", Comment);
                    values1.put("clinicname", clinicname);
                    values1.put("PRate", PRate);
                    values1.put("Ecg", Ecg);
                    values1.put("Ecgfile", Ecgfile);
                    values1.put("Scan", Scan);
                    values1.put("Scanfile", Scanfile);
                    values1.put("Treadmill", Treadmill);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("treatmentfor", treatmentfor);
                    values1.put("bloodgroup", bloodgroup);
                    values1.put("ecgrate", ecgrate);
                    values1.put("ecgqrs", ecgqrs);
                    values1.put("ecgst", ecgst);
                    values1.put("ecgt", ecgt);
                    values1.put("ecgrhyrhm", ecgrhyrhm);
                    values1.put("ecgaxis", ecgaxis);
                    values1.put("ecgpulse", ecgpulse);
                    values1.put("imeino", imeino);
                    values1.put("docsign", docsign);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Bundlebranchblock", Bundlebranchblock);
                    values1.put("Conductiondefects", Conductiondefects);
                    values1.put("PR", PR);
                    // values1.put("ReadytoUpdate", ReadytoUpdate);
                    // values1.put("ValueUpdated", ValueUpdated);
                    values1.put("ecgText", ecgText);
                    values1.put("ecgTreadmillText", ecgTreadmillText);
                    values1.put("ServerId", ServerId);


                    if (!Treadmill.isEmpty() || !ecgrate.isEmpty() || !ecgqrs.isEmpty() || !ecgst.isEmpty() || !ecgt.isEmpty() || !ecgrhyrhm.isEmpty() || !ecgaxis.isEmpty() || !Bundlebranchblock.isEmpty() || !Conductiondefects.isEmpty() || !PR.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from ECGTEST where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("ECGTEST", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("ECGTEST", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportAngiogram() throws JSONException {


        final String str = "";

        final String Query = "select  Patid from  Patreg ";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        db.isOpen();


        final Cursor c = db.rawQuery(Query, null);
        final JSONArray patientIdList = new JSONArray();
        final JSONObject singleobj = new JSONObject();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    patientIdList.put(new JSONObject().put("PatientId", PatientId));

                } while (c.moveToNext());
            }
        }

        ContentValues values1;
        final String PatientIdLIst = patientIdList.toString();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportBind_AngiogramDetailsJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);

                    final String ServerId = String.valueOf(objJson.getString("id"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String AngioFor = String.valueOf(objJson.getString("AngioFor"));
                    final String Comment = String.valueOf(objJson.getString("Comment"));
                    final String Coronary = String.valueOf(objJson.getString("Coronary"));
                    final String Brain = String.valueOf(objJson.getString("Brain"));
                    final String Upperlimb = String.valueOf(objJson.getString("Upperlimb"));
                    final String Lowerlimb = String.valueOf(objJson.getString("Lowerlimb"));
                    final String Mesenteric = String.valueOf(objJson.getString("Mesenteric"));
                    final String uid = String.valueOf(objJson.getString("uid"));
                    final String WUP = String.valueOf(objJson.getString("WUP"));
                    final String CoronaryText = String.valueOf(objJson.getString("CoronaryText"));
                    final String BrainText = String.valueOf(objJson.getString("BrainText"));
                    final String UpperlimbText = String.valueOf(objJson.getString("UpperlimbText"));
                    final String LowerlimbText = String.valueOf(objJson.getString("LowerlimbText"));
                    final String MesentericText = String.valueOf(objJson.getString("MesentericText"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsPaid = String.valueOf(objJson.getString("IsPaid"));


                    values1 = new ContentValues();


                    values1.put("mtestid", mtestid);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("AngioFor", AngioFor);
                    values1.put("Comment", Comment);
                    values1.put("Coronary", Coronary);
                    values1.put("Brain", Brain);
                    values1.put("Upperlimb", Upperlimb);
                    values1.put("Lowerlimb", Lowerlimb);
                    values1.put("Mesenteric", Mesenteric);
                    // values1.put("ReadytoUpdate", ReadytoUpdate);
                    // values1.put("ValueUpdated", ValueUpdated);
                    values1.put("CoronaryText", CoronaryText);
                    values1.put("BrainText", BrainText);
                    values1.put("UpperlimbText", UpperlimbText);
                    values1.put("LowerlimbText", LowerlimbText);
                    values1.put("MesentericText", MesentericText);
                    values1.put("HID", HID);
                    values1.put("ServerId", ServerId);


                    if (!Brain.isEmpty() || !Upperlimb.isEmpty() || !Lowerlimb.isEmpty() || !Mesenteric.isEmpty()) {

                        values1.put("ReadytoUpdate", 2);
                        values1.put("ValueUpdated ", 0);
                    } else {

                        values1.put("ReadytoUpdate", 0);
                        values1.put("ValueUpdated ", 0);
                    }


                    values1.put("ServerId", ServerId);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1   from Angiogram where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Angiogram", values1, "ServerId=" + ServerId + ' ', null);

                    } else {
                        db.insert("Angiogram", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        Objects.requireNonNull(c).close();
        db.close();


    }

    private void ImportMprescribed() throws JSONException {


        String IsActive;
        final String str = "";

        final String Query = "select  Patid from  Patreg";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();//.replaceAll("\"","\\\"");
        final String MethodName = "ImportMprescribedJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);
                    final String id = String.valueOf(objJson.getString("id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String refdocname = String.valueOf(objJson.getString("refdocname"));
                    final String clinicname = String.valueOf(objJson.getString("clinicname"));
                    final String Medid = String.valueOf(objJson.getString("Medid"));
                    final String Dose = String.valueOf(objJson.getString("Dose"));
                    final String Freq = String.valueOf(objJson.getString("Freq"));
                    final String Duration = String.valueOf(objJson.getString("Duration"));
                    final String remarks = String.valueOf(objJson.getString("remarks"));
                    final String dsign = String.valueOf(objJson.getString("dsign"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final JSONException e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String medicinename = String.valueOf(objJson.getString("medicinename"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String diagnosis = String.valueOf(objJson.getString("diagnosis"));
                    final String diagnosisdtls = String.valueOf(objJson.getString("diagnosisdtls"));
                    final String nextvisit = String.valueOf(objJson.getString("nextvisit"));
                    final String fee = String.valueOf(objJson.getString("fee"));
                    final String mobnum = String.valueOf(objJson.getString("mobnum"));
                    final String imei = String.valueOf(objJson.getString("imei"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String NVsms = String.valueOf(objJson.getString("NVsms"));
                    final String MPsms = String.valueOf(objJson.getString("MPsms"));
                    final String Diabeticdiet = String.valueOf(objJson.getString("Diabeticdiet"));
                    final String Diabeticrenaldiet = String.valueOf(objJson.getString("Diabeticrenaldiet"));
                    final String Lowcholesterol_Cardiacdiet = String.valueOf(objJson.getString("Lowcholesterol_Cardiacdiet"));
                    final String Hypertensivediet = String.valueOf(objJson.getString("Hypertensivediet"));
                    final String Diet_Warfarin = String.valueOf(objJson.getString("Diet_Warfarin"));
                    final String prepharma = String.valueOf(objJson.getString("prepharma"));
                    final String medicineId = String.valueOf(objJson.getString("medicineId"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsIssued = String.valueOf(objJson.getString("IsIssued"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("refdocname", refdocname);
                    values1.put("clinicname", clinicname);
                    values1.put("Medid", Medid);
                    values1.put("Dose", Dose);
                    values1.put("Freq", Freq);
                    values1.put("Duration", Duration);
                    values1.put("remarks", remarks);
                    values1.put("dsign", dsign);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("medicinename", medicinename);
                    values1.put("pagegen", pagegen);
                    values1.put("diagnosis", diagnosis);
                    values1.put("diagnosisdtls", diagnosisdtls);
                    values1.put("nextvisit", nextvisit);
                    values1.put("fee", fee);
                    values1.put("mobnum", mobnum);
                    values1.put("imei", imei);
                    //values1.put("Isupdate", Isupdate);
                    values1.put("treatmentfor", treatmentfor);
                    //values1.put("upid", upid);
                    //values1.put("NVsms", NVsms);
                    //values1.put("MPsms", MPsms);

                    values1.put("Diabeticdiet", Diabeticdiet);
                    values1.put("Diabeticrenaldiet", Diabeticrenaldiet);
                    values1.put("Lowcholesterol_Cardiacdiet", Lowcholesterol_Cardiacdiet);
                    values1.put("Hypertensivediet", Hypertensivediet);
                    values1.put("Diet_Warfarin", Diet_Warfarin);
                    values1.put("prefpharma", prepharma);
                    values1.put("medicineId", medicineId);
                    values1.put("HID", HID);
                    values1.put("ServerId", id);

                    // TODO: 3/4/2017   IsUpadateMax Value insert

                    values1.put("IsUpdateMax", Isupdate);


                    // values1.put("IsIssued", IsIssued);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mprescribed where ServerId=" + id);

                    if (GetStatus) {
                        db.update("Mprescribed", values1, "ServerId=" + id, null);

                    } else {
                        db.insert("Mprescribed", null, values1);
                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Diagnosis() {
        try {

            final String Query = "select  Patid from  Patreg";

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            ContentValues values1;
            final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
            final String MethodName = "ImportBind_DiagnosisJSON";

            try {


                final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {


                        objJson = jsonArray.getJSONObject(i);


                        final String Id = String.valueOf(objJson.getString("id"));
                        final String Ptid = String.valueOf(objJson.getString("Ptid"));
                        final String DiagId = String.valueOf(objJson.getString("DiagId"));
                        final String pname = String.valueOf(objJson.getString("pname"));
                        final String gender = String.valueOf(objJson.getString("gender"));
                        final String age = String.valueOf(objJson.getString("age"));
                        final String mobnum = String.valueOf(objJson.getString("mobnum"));
                        final String refdocname = String.valueOf(objJson.getString("refdocname"));
                        final String clinicname = String.valueOf(objJson.getString("clinicname"));
                        final String Diagnosis = String.valueOf(objJson.getString("Diagnosis"));
                        final String Bp = String.valueOf(objJson.getString("Bp"));
                        final String FBS = String.valueOf(objJson.getString("FBS"));
                        final String PPS = String.valueOf(objJson.getString("PPS"));
                        final String RBS = String.valueOf(objJson.getString("RBS"));
                        final String PWeight = String.valueOf(objJson.getString("PWeight"));
                        final String temperature = String.valueOf(objJson.getString("temperature"));
                        final String Actdate = String.valueOf(objJson.getString("Actdate"));
                        final String IsActive = String.valueOf(objJson.getString("IsActive"));
                        final String treatmentfor = String.valueOf(objJson.getString("treatmentfor"));
                        final String Docid = String.valueOf(objJson.getString("Docid"));
                        final String BPD = String.valueOf(objJson.getString("BPD"));
                        final String bmi = String.valueOf(objJson.getString("bmi"));
                        final String height = String.valueOf(objJson.getString("height"));
                        final String HID = String.valueOf(objJson.getString("HID"));
                        final String Signs = String.valueOf(objJson.getString("Signs"));


                        values1 = new ContentValues();
                        values1.put("ServerId", Id);
                        values1.put("Docid", Docid);
                        values1.put("Ptid", Ptid);
                        values1.put("DiagId", DiagId);
                        values1.put("pname", pname);
                        values1.put("gender", gender);
                        values1.put("age", age);
                        values1.put("mobnum", mobnum);
                        values1.put("refdocname", refdocname);
                        values1.put("clinicname", clinicname);
                        values1.put("Diagnosis", Diagnosis);
                        values1.put("BpS", Bp);
                        values1.put("BpD", BPD);
                        values1.put("FBS", FBS);
                        values1.put("PPS", PPS);
                        values1.put("RBS", RBS);
                        values1.put("PWeight", PWeight);
                        values1.put("temperature", temperature);
                        values1.put("Actdate", BaseConfig.DateFormatter3(Actdate));
                        values1.put("IsActive", IsActive);
                        values1.put("treatmentfor", treatmentfor);
                        values1.put("Isupdate", "1");
                        values1.put("height", height);
                        values1.put("bmi", bmi);
                        values1.put("HID", HID);
                        values1.put("Signs", Signs);

                        final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Diagonis where  Ptid='" + Ptid + "' and DiagId='" + DiagId + "' and ServerId='" + Id + '\'');//(ServerId=" + Id + " or ServerId!=null)

                        if (GetStatus == false) {

                            db.insert("Diagonis", null, values1);

                        } else {
                            db.update("Diagonis", values1, "ServerId=" + Id, null);

                        }

                    }

                }

            } catch (final Exception e) {
                e.printStackTrace();
            }


            db.close();

        } catch (final Exception e) {
            e.printStackTrace();

        }


    }

    private void ImportBind_GeneralExamination() throws JSONException {


        String IsActive;
        final String str = "";

        final String Query = "select  Patid from  Patreg";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_GeneralExaminationJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Anaemic = String.valueOf(objJson.getString("Anaemic"));
                    final String Icterus = String.valueOf(objJson.getString("Icterus"));
                    final String Stenosis = String.valueOf(objJson.getString("Stenosis"));
                    final String Clubbing = String.valueOf(objJson.getString("Clubbing"));
                    final String Limbphantom = String.valueOf(objJson.getString("Limbphantom"));
                    final String Vericoveins = String.valueOf(objJson.getString("Vericoveins"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String Pedal_edema = String.valueOf(objJson.getString("Pedal_edema"));
                    final String built = String.valueOf(objJson.getString("built"));
                    final String extra_generalexam = String.valueOf(objJson.getString("extra_generalexam"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Anaemic", Anaemic);
                    values1.put("Icterus", Icterus);
                    values1.put("Stenosis", Stenosis);
                    values1.put("Clubbing", Clubbing);
                    values1.put("Limbphantom", Limbphantom);
                    values1.put("Vericoveins", Vericoveins);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("Pedal_edema", Pedal_edema);
                    values1.put("built", built);
                    values1.put("extra_generalexam", extra_generalexam);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_GeneralExamination where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_GeneralExamination", null, values1);

                    } else {
                        db.update("CaseNote_GeneralExamination", values1, "ServerId=" + Id, null);

                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }

        db.close();

    }

    private void ImportBind_Cardiovascular() throws JSONException {


        String IsActive;

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_CardiovascularJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Beat = String.valueOf(objJson.getString("Beat"));
                    final String Murmur = String.valueOf(objJson.getString("Murmur"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String Pericardial_Rub = String.valueOf(objJson.getString("Pericardial_Rub"));
                    final String Pulserate = String.valueOf(objJson.getString("Pulserate"));
                    final String heartrate = String.valueOf(objJson.getString("heartrate"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    ///values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Beat", Beat);
                    values1.put("Murmur", Murmur);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("Pericardial_Rub", Pericardial_Rub);
                    values1.put("Pulserate", Pulserate);
                    values1.put("heartrate", heartrate);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Cardiovascular where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Cardiovascular", null, values1);

                    } else {
                        db.update("CaseNote_Cardiovascular", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_RespiratorySystem() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_RespiratorySystemJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Breathsound = String.valueOf(objJson.getString("Breathsound"));
                    final String Trachea = String.valueOf(objJson.getString("Trachea"));
                    final String Kyphosis = String.valueOf(objJson.getString("Kyphosis"));
                    final String Crepitation = String.valueOf(objJson.getString("Crepitation"));
                    final String Bronchi = String.valueOf(objJson.getString("Bronchi"));
                    final String Pulserate = String.valueOf(objJson.getString("Pulserate"));
                    final String Note = String.valueOf(objJson.getString("Note"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String shapeofchest = String.valueOf(objJson.getString("shapeofchest"));
                    final String spo2 = String.valueOf(objJson.getString("spo2"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    // values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Breathsound", Breathsound);
                    values1.put("Trachea", Trachea);
                    values1.put("Kyphosis_Scoliosis", Kyphosis);
                    values1.put("Crepitation", Crepitation);
                    values1.put("Bronchi", Bronchi);
                    values1.put("Pulserate", Pulserate);
                    values1.put("Note", Note);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("shapeofchest", shapeofchest);
                    values1.put("spo2", spo2);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_RespiratorySystem where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_RespiratorySystem", null, values1);

                    } else {
                        db.update("CaseNote_RespiratorySystem", values1, "ServerId=" + Id, null);

                    }


                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Gastrointestinal() throws JSONException {


        String IsActive;

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_GastrointestinalJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Abdomen = String.valueOf(objJson.getString("Abdomen"));
                    final String Bowelsound = String.valueOf(objJson.getString("Bowelsound"));
                    final String Spleen = String.valueOf(objJson.getString("Spleen"));
                    final String Liver = String.valueOf(objJson.getString("Liver"));
                    final String PalpableMasses = String.valueOf(objJson.getString("PalpableMasses"));
                    final String Hernial = String.valueOf(objJson.getString("Hernial"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String shapeofabdomen = String.valueOf(objJson.getString("shapeofabdomen"));
                    final String Visible_Pulsations = String.valueOf(objJson.getString("Visible_Pulsations"));
                    final String Visual_Peristalsis = String.valueOf(objJson.getString("Visual_Peristalsis"));
                    final String Abdominal_Palpation = String.valueOf(objJson.getString("Abdominal_Palpation"));
                    final String Splenomegaly = String.valueOf(objJson.getString("Splenomegaly"));
                    final String Hepatomegaly = String.valueOf(objJson.getString("Hepatomegaly"));
                    final String organomegely = String.valueOf(objJson.getString("organomegely"));
                    final String freefuild = String.valueOf(objJson.getString("freefuild"));
                    final String distension = String.valueOf(objJson.getString("distension"));
                    final String tenderness = String.valueOf(objJson.getString("tenderness"));
                    final String scrotum = String.valueOf(objJson.getString("scrotum"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    // values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Abdomen", Abdomen);
                    values1.put("Bowelsound", Bowelsound);
                    values1.put("Spleen", Spleen);
                    values1.put("Liver", Liver);
                    values1.put("PalpableMasses", PalpableMasses);
                    values1.put("Hernial", Hernial);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("shapeofabdomen", shapeofabdomen);
                    values1.put("Visible_Pulsations", Visible_Pulsations);
                    values1.put("Visual_Peristalsis", Visual_Peristalsis);
                    values1.put("Abdominal_Palpation", Abdominal_Palpation);
                    values1.put("Splenomegaly", Splenomegaly);
                    values1.put("Hepatomegaly", Hepatomegaly);
                    values1.put("organomegely", organomegely);
                    values1.put("freefuild", freefuild);
                    values1.put("distension", distension);
                    values1.put("tenderness", tenderness);
                    values1.put("scrotum", scrotum);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Gastrointestinal where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Gastrointestinal", null, values1);

                    } else {
                        db.update("CaseNote_Gastrointestinal", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_Neurology() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_NeurologyJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Pupilsize = String.valueOf(objJson.getString("Pupilsize"));
                    final String Speech = String.valueOf(objJson.getString("Speech"));
                    final String Carodit = String.valueOf(objJson.getString("Carodit"));
                    final String Amnesia = String.valueOf(objJson.getString("Amnesia"));
                    final String Apraxia = String.valueOf(objJson.getString("Apraxia"));
                    final String Cognitive_Function = String.valueOf(objJson.getString("Cognitive_Function"));
                    final String Bulkdata = String.valueOf(objJson.getString("Bulkdata"));
                    final String Tone = String.valueOf(objJson.getString("Tone"));
                    final String Power_LUL = String.valueOf(objJson.getString("Power_LUL"));
                    final String Power_RUL = String.valueOf(objJson.getString("Power_RUL"));
                    final String Power_LLL = String.valueOf(objJson.getString("Power_LLL"));
                    final String Power_RLL = String.valueOf(objJson.getString("Power_RLL"));
                    final String Sensory = String.valueOf(objJson.getString("Sensory"));
                    final String Reflexes_Corneal = String.valueOf(objJson.getString("Reflexes_Corneal"));
                    final String Reflexes_Biceps = String.valueOf(objJson.getString("Reflexes_Biceps"));
                    final String Reflexes_Triceps = String.valueOf(objJson.getString("Reflexes_Triceps"));
                    final String Reflexes_Supinator = String.valueOf(objJson.getString("Reflexes_Supinator"));
                    final String Reflexes_Knee = String.valueOf(objJson.getString("Reflexes_Knee"));
                    final String Reflexes_Ankle = String.valueOf(objJson.getString("Reflexes_Ankle"));
                    final String Reflexes_Plantor = String.valueOf(objJson.getString("Reflexes_Plantor"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String congentail_abnormality = String.valueOf(objJson.getString("congentail_abnormality"));
                    final String mentalstatus = String.valueOf(objJson.getString("mentalstatus"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Pupilsize", Pupilsize);
                    values1.put("Speech", Speech);
                    values1.put("Carodit", Carodit);
                    values1.put("Amnesia", Amnesia);
                    values1.put("Apraxia", Apraxia);
                    values1.put("Cognitive_Function", Cognitive_Function);
                    values1.put("Bulk", Bulkdata);
                    values1.put("Tone", Tone);
                    values1.put("Power_LUL", Power_LUL);
                    values1.put("Power_RUL", Power_RUL);
                    values1.put("Power_LLL", Power_LLL);
                    values1.put("Power_RLL", Power_RLL);
                    values1.put("Sensory", Sensory);
                    values1.put("Reflexes_Corneal", Reflexes_Corneal);
                    values1.put("Reflexes_Biceps", Reflexes_Biceps);
                    values1.put("Reflexes_Triceps", Reflexes_Triceps);
                    values1.put("Reflexes_Supinator", Reflexes_Supinator);
                    values1.put("Reflexes_Knee", Reflexes_Knee);
                    values1.put("Reflexes_Ankle", Reflexes_Ankle);
                    values1.put("Reflexes_Plantor", Reflexes_Plantor);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("congentail_abnormality", congentail_abnormality);
                    values1.put("mentalstatus", mentalstatus);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Neurology where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Neurology", null, values1);

                    } else {
                        db.update("CaseNote_Neurology", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Renal() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportCaseNote_RenalJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String id = String.valueOf(objJson.getString("id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String dysuria = String.valueOf(objJson.getString("dysuria"));
                    final String pyuria = String.valueOf(objJson.getString("pyuria"));
                    final String haematuria = String.valueOf(objJson.getString("haematuria"));
                    final String oliguria = String.valueOf(objJson.getString("oliguria"));
                    final String polyuria = String.valueOf(objJson.getString("polyuria"));
                    final String anuria = String.valueOf(objJson.getString("anuria"));
                    final String nocturia = String.valueOf(objJson.getString("nocturia"));
                    final String urethraldischarge = String.valueOf(objJson.getString("urethraldischarge"));
                    final String prostate = String.valueOf(objJson.getString("prostate"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("dysuria", dysuria);
                    values1.put("pyuria", pyuria);
                    values1.put("haematuria", haematuria);
                    values1.put("oliguria", oliguria);
                    values1.put("polyuria", polyuria);
                    values1.put("anuria", anuria);
                    values1.put("nocturia", nocturia);
                    values1.put("urethraldischarge", urethraldischarge);
                    values1.put("prostate", prostate);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Renal where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Renal", null, values1);

                    } else {
                        db.update("CaseNote_Renal", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Endocrine() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportCaseNote_EndocrineJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String id = String.valueOf(objJson.getString("id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    final String Endocrine = String.valueOf(objJson.getString("Endocrine"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    // values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Endocrine", Endocrine);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Endocrine where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Endocrine", null, values1);

                    } else {
                        db.update("CaseNote_Endocrine", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_ClinicalData() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportCaseNote_ClinicalDataJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String id = String.valueOf(objJson.getString("id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Heamoglobin = String.valueOf(objJson.getString("Heamoglobin"));
                    final String wbc = String.valueOf(objJson.getString("wbc"));
                    final String rbc = String.valueOf(objJson.getString("rbc"));
                    final String esr = String.valueOf(objJson.getString("esr"));
                    final String polymorphs = String.valueOf(objJson.getString("polymorphs"));
                    final String lymphocytes = String.valueOf(objJson.getString("lymphocytes"));
                    final String monocytes = String.valueOf(objJson.getString("monocytes"));
                    final String basophils = String.valueOf(objJson.getString("basophils"));
                    final String eosinophils = String.valueOf(objJson.getString("eosinophils"));
                    final String urea = String.valueOf(objJson.getString("urea"));
                    final String creatinine = String.valueOf(objJson.getString("creatinine"));
                    final String UricAcid = String.valueOf(objJson.getString("UricAcid"));
                    final String Sodium = String.valueOf(objJson.getString("Sodium"));
                    final String Potassium = String.valueOf(objJson.getString("Potassium"));
                    final String Chloride = String.valueOf(objJson.getString("Chloride"));
                    final String Bicarbonate = String.valueOf(objJson.getString("Bicarbonate"));
                    final String TotalCholesterol = String.valueOf(objJson.getString("TotalCholesterol"));
                    final String LDL = String.valueOf(objJson.getString("LDL"));
                    final String HDL = String.valueOf(objJson.getString("HDL"));
                    final String VLDL = String.valueOf(objJson.getString("VLDL"));
                    final String Triglycerides = String.valueOf(objJson.getString("Triglycerides"));
                    final String Serumbilirubin = String.valueOf(objJson.getString("Serumbilirubin"));
                    final String Direct = String.valueOf(objJson.getString("Direct"));
                    final String Indirect = String.valueOf(objJson.getString("Indirect"));
                    final String Totalprotein = String.valueOf(objJson.getString("Totalprotein"));
                    final String Albumin = String.valueOf(objJson.getString("Albumin"));
                    final String Globulin = String.valueOf(objJson.getString("Globulin"));
                    final String SGOT = String.valueOf(objJson.getString("SGOT"));
                    final String SGPT = String.valueOf(objJson.getString("SGPT"));
                    final String AlkalinePhosphatase = String.valueOf(objJson.getString("AlkalinePhosphatase"));
                    final String FreeT3 = String.valueOf(objJson.getString("FreeT3"));
                    final String FreeT4 = String.valueOf(objJson.getString("FreeT4"));
                    final String TSH = String.valueOf(objJson.getString("TSH"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("Heamoglobin", Heamoglobin);
                    values1.put("wbc", wbc);
                    values1.put("rbc", rbc);
                    values1.put("esr", esr);
                    values1.put("polymorphs", polymorphs);
                    values1.put("lymphocytes", lymphocytes);
                    values1.put("monocytes", monocytes);
                    values1.put("basophils", basophils);
                    values1.put("eosinophils", eosinophils);
                    values1.put("urea", urea);
                    values1.put("creatinine", creatinine);
                    values1.put("UricAcid", UricAcid);
                    values1.put("Sodium", Sodium);
                    values1.put("Potassium", Potassium);
                    values1.put("Chloride", Chloride);
                    values1.put("Bicarbonate", Bicarbonate);
                    values1.put("TotalCholesterol", TotalCholesterol);
                    values1.put("LDL", LDL);
                    values1.put("HDL", HDL);
                    values1.put("VLDL", VLDL);
                    values1.put("Triglycerides", Triglycerides);
                    values1.put("Serumbilirubin", Serumbilirubin);
                    values1.put("Direct", Direct);
                    values1.put("Indirect", Indirect);
                    values1.put("Totalprotein", Totalprotein);
                    values1.put("Albumin", Albumin);
                    values1.put("Globulin", Globulin);
                    values1.put("SGOT", SGOT);
                    values1.put("SGPT", SGPT);
                    values1.put("AlkalinePhosphatase", AlkalinePhosphatase);
                    values1.put("FreeT3", FreeT3);
                    values1.put("FreeT4", FreeT4);
                    values1.put("TSH", TSH);
                    // values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_ClinicalData where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_ClinicalData", null, values1);

                    } else {
                        db.update("CaseNote_ClinicalData", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Locomotor() throws JSONException {

        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportCaseNote_LocomotorJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String id = String.valueOf(objJson.getString("id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String symmetry = String.valueOf(objJson.getString("symmetry"));
                    final String smooth_movement = String.valueOf(objJson.getString("smooth_movement"));
                    final String arms_swing = String.valueOf(objJson.getString("arms_swing"));
                    final String pelvic_tilt = String.valueOf(objJson.getString("pelvic_tilt"));
                    final String stride_length = String.valueOf(objJson.getString("stride_length"));
                    final String cervical_lordosis = String.valueOf(objJson.getString("cervical_lordosis"));
                    final String lumbar_lordosis = String.valueOf(objJson.getString("lumbar_lordosis"));
                    final String kyphosis = String.valueOf(objJson.getString("kyphosis"));
                    final String scoliosis = String.valueOf(objJson.getString("scoliosis"));
                    final String llswelling = String.valueOf(objJson.getString("llswelling"));
                    final String lldeformity = String.valueOf(objJson.getString("lldeformity"));
                    final String lllimbshortening = String.valueOf(objJson.getString("lllimbshortening"));
                    final String llmuscle_wasting = String.valueOf(objJson.getString("llmuscle_wasting"));
                    final String llremarks = String.valueOf(objJson.getString("llremarks"));
                    final String ulswelling = String.valueOf(objJson.getString("ulswelling"));
                    final String uldeformity = String.valueOf(objJson.getString("uldeformity"));
                    final String ullimbshortening = String.valueOf(objJson.getString("ullimbshortening"));
                    final String ulmuscle_wasting = String.valueOf(objJson.getString("ulmuscle_wasting"));
                    final String ulremarks = String.valueOf(objJson.getString("ulremarks"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));

                    values1 = new ContentValues();
                    //values1.put("id", id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    values1.put("pagegen", pagegen);
                    values1.put("symmetry", symmetry);
                    values1.put("smooth_movement", smooth_movement);
                    values1.put("arms_swing", arms_swing);
                    values1.put("pelvic_tilt", pelvic_tilt);
                    values1.put("stride_length", stride_length);
                    values1.put("cervical_lordosis", cervical_lordosis);
                    values1.put("lumbar_lordosis", lumbar_lordosis);
                    values1.put("kyphosis", kyphosis);
                    values1.put("scoliosis", scoliosis);
                    values1.put("llswelling", llswelling);
                    values1.put("lldeformity", lldeformity);
                    values1.put("lllimbshortening", lllimbshortening);
                    values1.put("llmuscle_wasting", llmuscle_wasting);
                    values1.put("llremarks", llremarks);
                    values1.put("ulswelling", ulswelling);
                    values1.put("uldeformity", uldeformity);
                    values1.put("ullimbshortening", ullimbshortening);
                    values1.put("ulmuscle_wasting", ulmuscle_wasting);
                    values1.put("ulremarks", ulremarks);
                    //values1.put("upid", upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_Locomotor where ServerId=" + id);

                    if (!GetStatus) {

                        db.insert("CaseNote_Locomotor", null, values1);

                    } else {
                        db.update("CaseNote_Locomotor", values1, "ServerId=" + id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportBind_CaseNote_OtherSystem() throws JSONException {


        String IsActive;
        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportBind_CaseNote_OtherSystemJSON";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);


                    final String Id = String.valueOf(objJson.getString("Id"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String Othersystem = String.valueOf(objJson.getString("Othersystem"));
                    final String AdditionalInfo = String.valueOf(objJson.getString("AdditionalInfo"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String Upid = String.valueOf(objJson.getString("Upid"));
                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    values1 = new ContentValues();
                    //values1.put("Id", Id);
                    values1.put("DiagId", DiagId);
                    values1.put("ptid", ptid);
                    values1.put("pname", pname);
                    values1.put("docid", docid);
                    values1.put("pagegen", pagegen);
                    values1.put("Othersystem", Othersystem);
                    values1.put("AdditionalInfo", AdditionalInfo);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);
                    values1.put("IsActive", IsActive);
                    //values1.put("Upid", Upid);
                    values1.put("HID", HID);
                    values1.put("IsUpdateMax", IsUpdateMax);
                    values1.put("ServerId", Id);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_OtherSystem where ServerId=" + Id);

                    if (!GetStatus) {

                        db.insert("CaseNote_OtherSystem", null, values1);

                    } else {
                        db.update("CaseNote_OtherSystem", values1, "ServerId=" + Id, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void ImportCaseNote_Dental() throws JSONException {


        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        ContentValues values1;
        final String PatientIdLIst = ImportWebservices_NODEJS.getPatientList();
        final String MethodName = "ImportCaseNote_Dental";

        try {


            final String resultsRequestSOAP = this.postPatientIdListRESTMethod(PatientIdLIst, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);

                    final String IsUpdateMax = String.valueOf(objJson.getString("IsUpdateMax"));
                    final String upper_right_jaw = String.valueOf(objJson.getString("upper_right_jaw"));
                    final String upper_left_jaw = String.valueOf(objJson.getString("upper_left_jaw"));
                    final String upid = String.valueOf(objJson.getString("upid"));
                    final String pagegen = String.valueOf(objJson.getString("pagegen"));
                    final String DiagId = String.valueOf(objJson.getString("DiagId"));

                    final String HID = String.valueOf(objJson.getString("HID"));
                    final String lower_left_jaw = String.valueOf(objJson.getString("lower_left_jaw"));
                    final String id = String.valueOf(objJson.getString("id"));
                    final String docid = String.valueOf(objJson.getString("docid"));
                    final String ptid = String.valueOf(objJson.getString("ptid"));
                    // String Actdate = String.valueOf(objJson.getString("Actdate"));
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String pname = String.valueOf(objJson.getString("pname"));
                    final String lower_right_jaw = String.valueOf(objJson.getString("lower_right_jaw"));
                    final String Isupdate = String.valueOf(objJson.getString("Isupdate"));
                    String Actdate = "";
                    try {
                        Actdate = BaseConfig.DateFormatter3(String.valueOf(objJson.getString("Actdate")));
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", IsUpdateMax);
                    values1.put("upper_right_jaw", upper_right_jaw);
                    values1.put("upper_left_jaw", upper_left_jaw);
                    //values1.put("upid", upid);
                    values1.put("pagegen", pagegen);
                    values1.put("DiagId", DiagId);
                    values1.put("HID", HID);
                    values1.put("lower_left_jaw", lower_left_jaw);
                    //values1.put("id", id);
                    values1.put("docid", docid);
                    values1.put("ptid", ptid);
                    values1.put("IsActive", IsActive);
                    values1.put("pname", pname);
                    values1.put("lower_right_jaw", lower_right_jaw);
                    values1.put("Isupdate", Isupdate);
                    values1.put("Actdate", Actdate);

                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from CaseNote_OtherSystem where ServerId=" + IsUpdateMax);

                    if (!GetStatus) {

                        db.insert("CaseNote_DentalSystem", null, values1);

                    } else {
                        db.update("CaseNote_DentalSystem", values1, "ServerId=" + IsUpdateMax, null);

                    }

                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }


        db.close();

    }

    private void InsertImmunizationInformation() {
        try {

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

            final StringBuilder str = new StringBuilder();

            final String Query = "select ptid,docid from ClinicalInformation where vaccine_update='1'";

            String DocId = "";
            String PatId = "";

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        str.append(c.getString(c.getColumnIndex("docid")));
                        str.append('^');
                        str.append(c.getString(c.getColumnIndex("ptid")));

                        DocId = c.getString(c.getColumnIndex("docid"));
                        PatId = c.getString(c.getColumnIndex("ptid"));

                        break;

                    } while (c.moveToNext());
                }

                c.close();
                db.close();
            }


            String resultsRequestSOAP = null;
            try {
                final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
                final PostImmunizationInfoFactory postDoctorIdFactory = new PostImmunizationInfoFactory(magnetMobileClient);
                final PostImmunizationInfo postDoctorId = postDoctorIdFactory.obtainInstance();

                //Set Body Data
                final PosImmunizationInfoRequest body = new PosImmunizationInfoRequest();
                body.setDocId(DocId);
                body.setPatId(PatId);

                final Call<PosImmunizationInfoResult> resultCall = postDoctorId.posImmunizationInfo(body, null);

                resultsRequestSOAP = resultCall.get().getResults();
            } catch (final SchemaException e) {
                e.printStackTrace();
            } catch (final InterruptedException e) {
                e.printStackTrace();
            } catch (final ExecutionException e) {
                e.printStackTrace();
            }


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);

                    //Id,patid,pname,agegen,mobnum,drid,vaccinename,schedule,duedt,givendt,weight,dt

                    String Insert_Query_TestDtls = "update Vaccination set givendt='"
                            + objJson.getString("givendt")
                            + "',isactive='1',Isupdate='1',dt='"
                            + objJson.getString("dt")
                            + "' where vaccinename='"
                            + objJson.getString("vaccinename")
                            + "' and schedule='"
                            + objJson.getString("givendt")
                            + "' and patid='"
                            + objJson.getString("patid")
                            + "' and isactive='0' and Isupdate='0'";

                    BaseConfig.SaveData(Insert_Query_TestDtls);


                }

            }


        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedTestDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {

            final StringBuilder str = new StringBuilder();

            final String Query = "select mtestid,docid,Ptid from medicaltestdtls where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();

            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);
            String MethodName;

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        MethodName = "fromlab_Medicaltestdts";

                        if (!"".equals(mtestid)) {

                            String resultsRequestSOAP = "";

                            resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);


                            try {


                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //result,weight,temperature,bpd,bps,id,testvalue,testsummary,alltest


                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        int seenInt = 0;

                                        try {

                                            seenInt = Integer.parseInt(ImportWebservices_NODEJS.GetCurrentSeenValue(ptid, mtestid, objJson.getString("alltest")));
                                        } catch (final Exception e) {
                                            e.printStackTrace();

                                            //Log.e("SEEN VALUE EXCEPTION WARNING", e.getMessage());
                                        }

                                        String ResultValue = "0";
                                        if ("0".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Nil";
                                        } else if ("1".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Positive";

                                        } else if ("2".equalsIgnoreCase(objJson.getString("result"))) {
                                            ResultValue = "Negative";
                                        }


                                        final ContentValues values = new ContentValues();
                                        values.put("testvalue", objJson.getString("testvalue"));
                                        values.put("testsummary", objJson.getString("testsummary"));
                                        values.put("IsActive", "0");
                                        values.put("seen", seenInt);
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        values.put("bps", objJson.getString("bps"));
                                        values.put("bpd", objJson.getString("bpd"));
                                        values.put("temperature", objJson.getString("temperature"));
                                        values.put("weight", objJson.getString("weight"));
                                        values.put("result", ResultValue);
                                        db.update("Medicaltestdtls", values, "Ptid='" + ptid + "' and alltest='" + objJson.getString("alltest") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                        this.UpdateInCaseNotes(ptid, mtestid, objJson.getString("alltest"));



                                    }

                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }


            }


        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedScanDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedScanDtls");

            final String Query = "select mtestid,docid,Ptid  from Scantest where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        if (!"".equals(mtestid)) {

                            final String MethodName = "fromlab_Scantest";


                            try {

                                String resultsRequestSOAP = "";

                                resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        // id,scanvalue,scanname,scansummary


                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        final ContentValues values = new ContentValues();
                                        values.put("scanvalue", objJson.getString("scanvalue"));
                                        values.put("scansummary", objJson.getString("scansummary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("Scantest", values, "where Ptid='" + ptid + "' and scanname='" + objJson.getString("scanname") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");


                                    }
                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedXrayDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedXrayDtls");

            final String Query = "select mtestid,docid,Ptid  from XRAY where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));

                        if (!"".equals(mtestid)) {

                            final String MethodName = "fromlab_XRAY";

                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,xrayvalue,xraysummary,xray

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';

                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update


                                        final ContentValues values = new ContentValues();
                                        values.put("xrayvalue", objJson.getString("xrayvalue"));
                                        values.put("xraysummary", objJson.getString("xraysummary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("XRAY", values, "where Ptid='" + ptid + "' and xray='" + objJson.getString("xray") + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportupdatedEEGDtls() {
// TODO: 3/26/2017 checking EEG Details
        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedEEGDtls");

            final String Query = "select mtestid,docid,ptid  from EEG where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("ptid"));

                        if (!"".equals(mtestid)) {

                            final String MethodName = "fromlab_EEG";

                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Summary

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update
                                        final ContentValues values = new ContentValues();
                                        values.put("Summary", objJson.getString("Summary"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("EEG", values, "Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final Exception e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }

        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void ImportupdatedECGDtls() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";
// TODO: 3/26/2017 checking ECG Details
        try {
            //Log.e("Data Sent", "Entered ImportupdatedECGDtls");

            final String Query = "select mtestid,Docid,Ptid  from ECGTEST where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("Docid"));
                        ptid = c.getString(c.getColumnIndex("Ptid"));


                        if (!"".equals(mtestid)) {


                            final String MethodName = "fromlab_ECGTEST";


                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Ecg,Treadmill,ecgrate,ecgqrs,ecgrate,ecgst,ecgt,ecgrhyrhm,ecgaxis,Bundlebranchblock,Conductiondefects,PR

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update

                                        // Ecg,Treadmill,ecgrate, ecgqrs,ecgst,ecgt,
                                        // ecgrhyrhm,ecgaxis,
                                        // Bundlebranchblock,Conductiondefects,PR
                                        final ContentValues values = new ContentValues();
                                        values.put("Ecg", objJson.getString("Ecg"));
                                        values.put("Treadmill", objJson.getString("Treadmill"));
                                        values.put("ecgrate", objJson.getString("ecgrate"));
                                        values.put("ecgqrs", objJson.getString("ecgqrs"));
                                        values.put("ecgst", objJson.getString("ecgst"));
                                        values.put("ecgt", objJson.getString("ecgt"));
                                        values.put("ecgrhyrhm", objJson.getString("ecgrhyrhm"));
                                        values.put("ecgaxis", objJson.getString("ecgaxis"));
                                        values.put("Bundlebranchblock", objJson.getString("Bundlebranchblock"));
                                        values.put("Conductiondefects", objJson.getString("Conductiondefects"));
                                        values.put("PR", objJson.getString("PR"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("ECGTEST", values, " Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");

                                    }

                                }

                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final JSONException e) {
                                e.printStackTrace();
                            }

                            // break;

                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void ImportupdatedAngiogram() {

        String mtestid;
        String docid;
        String ptid;

        String ServerId = "";

        try {
            //Log.e("Data Sent", "Entered ImportupdatedAngiogram");

            final String Query = "select mtestid,docid,ptid from Angiogram where ReadytoUpdate='0'";

            final SQLiteDatabase db = BaseConfig.GetDb();
            final int upid = 0;

            final Cursor c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        mtestid = c.getString(c.getColumnIndex("mtestid"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        ptid = c.getString(c.getColumnIndex("ptid"));

                        if (!"".equals(mtestid)) {

                            final String MethodName = "fromlab_Angiogram";


                            String resultsRequestSOAP = "";
                            resultsRequestSOAP = this.postMtestIdDidPId(mtestid, docid, ptid, MethodName, resultsRequestSOAP);

                            try {

                                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);

                                    JSONObject objJson;

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        objJson = jsonArray.getJSONObject(i);

                                        //id,Coronary,Brain,Upperlimb,Lowerlimb,Mesenteric

                                        //noinspection StringConcatenationInLoop
                                        ServerId += objJson.getString("id") + '^';


                                        // Note:
                                        // Here updating and changing the value of ReadytoUpdate
                                        // to 2
                                        // 0->on update
                                        // 1->updated
                                        // 2->web update

                                        // Ecg,Treadmill,ecgrate, ecgqrs,ecgst,ecgt,
                                        // ecgrhyrhm,ecgaxis,
                                        // Bundlebranchblock,Conductiondefects,PR
                                        final ContentValues values = new ContentValues();
                                        values.put("Coronary", objJson.getString("Coronary"));
                                        values.put("Brain", objJson.getString("Brain"));
                                        values.put("Upperlimb", objJson.getString("Upperlimb"));
                                        values.put("Lowerlimb", objJson.getString("Lowerlimb"));
                                        values.put("Mesenteric", objJson.getString("Mesenteric"));
                                        values.put("IsActive", "0");
                                        values.put("ReadytoUpdate", "2");
                                        values.put("ValueUpdated", "0");
                                        db.update("Angiogram", values, "where Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                       db.execSQL("update Medicaltest set IsResultAvailable=2 where Ptid='"+ptid+"' and mtestid='"+mtestid+"'");


                                    }

                                }


                                //Log.e("Testing", String.valueOf(upid));
                            } catch (final JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    } while (c.moveToNext());

                    c.close();
                    db.close();
                }

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void Importmast_Operations() {


        final String str = "";

        final String Query = "select  Patid from  Patreg";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String PatientId = c.getString(c.getColumnIndex("Patid"));

                    this.getOperationDetails(PatientId);


                } while (c.moveToNext());

            }
        }

        Objects.requireNonNull(c).close();

        db.close();

    }


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void Importmast_Upload() {


        final String str = "";

        final String Query = "select Ptid,mtestid from Medicaltest  group by mtestid";

        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        final Cursor c = db.rawQuery(Query, null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    final String Ptid = c.getString(c.getColumnIndex("Ptid"));
                    final String mtestid = c.getString(c.getColumnIndex("mtestid"));

                    this.getBindUpload(Ptid, mtestid);


                } while (c.moveToNext());

            }
        }

        Objects.requireNonNull(c).close();

        db.close();

    }

    private void Import_ExaminationBlood_Test() throws JSONException {
        String PatId = "";
        String MtestId = "";
        final String MethodName = "Import_ExaminationBlood_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            final JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            //Query = "select Patid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);


                        break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            final JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                final String resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        final String ServerId = objJson.getString("Id");
                        final String Patid = objJson.getString("Patid");
                        final String Docid = objJson.getString("Docid");
                        final String Actdate = objJson.getString("Actdate");
                        final String IsActive = objJson.getString("IsActive");
                        final String IsUpdate = objJson.getString("IsUpdate");
                        final String Mtestid = objJson.getString("Mtestid");
                        final String Testid = objJson.getString("Testid");
                        final String Subtestid = objJson.getString("Subtestid");
                        final String HID = objJson.getString("HID");
                        final String IsPaid = objJson.getString("IsPaid");
                        final String maemogiobin = objJson.getString("Haemogiobin");
                        final String packed_cell_volume = objJson.getString("packed_cell_volume");
                        final String total_count = objJson.getString("total_count");
                        final String rbc_count = objJson.getString("rbc_count");
                        final String polymorphs = objJson.getString("polymorphs");
                        final String lymphocytes = objJson.getString("lymphocytes");
                        final String eosinophilis = objJson.getString("eosinophilis");
                        final String monocytes = objJson.getString("monocytes");
                        final String basophils = objJson.getString("basophils");
                        final String band_from = objJson.getString("band_from");
                        final String platelets_count = objJson.getString("platelets_count");
                        final String psmp = objJson.getString("psmp");
                        final String esr = objJson.getString("esr");
                        final String STyphi_O = objJson.getString("STyphi_O");
                        final String STyphi_H = objJson.getString("STyphi_H");
                        final String SParatyphi_AH = objJson.getString("SParatyphi_AH");
                        final String SParatyphi_BH = objJson.getString("SParatyphi_BH");
                        String Result_Status = objJson.getString("Blood_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        final ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("maemogiobin", maemogiobin);
                        values.put("packed_cell_volume", packed_cell_volume);
                        values.put("total_count", total_count);
                        values.put("rbc_count", rbc_count);
                        values.put("polymorphs", polymorphs);
                        values.put("lymphocytes", lymphocytes);
                        values.put("eosinophilis", eosinophilis);
                        values.put("monocytes", monocytes);
                        values.put("basophils", basophils);
                        values.put("band_from", band_from);
                        values.put("platelets_count", platelets_count);
                        values.put("psmp", psmp);
                        values.put("esr", esr);
                        values.put("STyphi_O", STyphi_O);
                        values.put("STyphi_H", STyphi_H);
                        values.put("SParatyphi_AH", SParatyphi_AH);
                        values.put("SParatyphi_BH", SParatyphi_BH);
                        values.put("Result_Status", Result_Status);

                        //data iruntha update else insert
                        final boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_examination_blood_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_examination_blood_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_examination_blood_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }


        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }

    }

    private void Import_ExaminationStool_Test() {
        String PatId = "";
        String MtestId = "";
        final String MethodName = "Import_ExaminationStool_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            final JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));

                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            final JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                final String resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);


                        final String Id = objJson.getString("Id");
                        final String Patid = objJson.getString("Patid");
                        final String Docid = objJson.getString("Docid");
                        final String Actdate = objJson.getString("Actdate");
                        final String IsActive = objJson.getString("IsActive");
                        final String IsUpdate = objJson.getString("IsUpdate");
                        final String ServerId = objJson.getString("Id");
                        final String Mtestid = objJson.getString("Mtestid");
                        final String Testid = objJson.getString("Mtestid");
                        final String Subtestid = objJson.getString("Subtestid");
                        final String HID = objJson.getString("HID");
                        final String IsPaid = objJson.getString("IsPaid");
                        final String colour = objJson.getString("colour");
                        final String consistency = objJson.getString("consistency");
                        final String musus = objJson.getString("musus");
                        final String blood = objJson.getString("blood");
                        final String parasites = objJson.getString("parasites");
                        final String occult_blood_test = objJson.getString("occult_blood_test");
                        final String cpi_cells = objJson.getString("cpi_cells");
                        final String pus_cells = objJson.getString("pus_cells");
                        final String red_blood_cells = objJson.getString("red_blood_cells");
                        final String macrophages = objJson.getString("macrophages");
                        final String fat = objJson.getString("fat");
                        final String starch = objJson.getString("starch");
                        final String undigeted_food = objJson.getString("undigeted_food_matter");
                        final String any_other_finding = objJson.getString("any_other_finding");
                        final String vegetative_forms = objJson.getString("vegetative_forms");
                        final String protozoa = objJson.getString("protozoa");
                        final String flagellates = objJson.getString("flagellates");
                        final String cyst = objJson.getString("cyst");
                        final String ova = objJson.getString("ova");
                        final String larvae = objJson.getString("larvae");
                        final String result = objJson.getString("result");
                        String Result_Status = objJson.getString("Stool_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        final ContentValues values = new ContentValues();
                        values.put("Id", Id);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("ServerId", ServerId);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Mtestid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("colour", colour);
                        values.put("consistency", consistency);
                        values.put("musus", musus);
                        values.put("blood", blood);
                        values.put("parasites", parasites);
                        values.put("occult_blood_test", occult_blood_test);
                        values.put("cpi_cells", cpi_cells);
                        values.put("pus_cells", pus_cells);
                        values.put("red_blood_cells", red_blood_cells);
                        values.put("macrophages", macrophages);
                        values.put("fat", fat);
                        values.put("starch", starch);
                        values.put("undigeted_food", undigeted_food);
                        values.put("any_other_finding", any_other_finding);
                        values.put("vegetative_forms", vegetative_forms);
                        values.put("protozoa", protozoa);
                        values.put("flagellates", flagellates);
                        values.put("cyst", cyst);
                        values.put("ova", ova);
                        values.put("larvae", larvae);
                        values.put("result", result);
                        values.put("Result_Status", Result_Status);

                        //data iruntha update else insert
                        final boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_stool_report where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_stool_report", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_stool_report", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationUrine_Test() {
        String PatId = "";
        String MtestId = "";
        final String MethodName = "Import_ExaminationUrine_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            final JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);


                        break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            final JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {

                final String resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        final String ServerId = objJson.getString("Id");
                        final String Patid = objJson.getString("Patid");
                        final String Docid = objJson.getString("Docid");
                        final String Actdate = objJson.getString("Actdate");
                        final String IsActive = objJson.getString("IsActive");
                        final String IsUpdate = objJson.getString("IsUpdate");
                        final String Mtestid = objJson.getString("Mtestid");
                        final String Testid = objJson.getString("Testid");
                        final String Subtestid = objJson.getString("Subtestid");
                        final String HID = objJson.getString("HID");
                        final String IsPaid = objJson.getString("IsPaid");

                        final String quantity = objJson.getString("Quantity");
                        final String colour = objJson.getString("colour");
                        final String appearance = objJson.getString("appearance");
                        final String sediment = objJson.getString("sediment");
                        final String specific_gravity = objJson.getString("specific_gravity");
                        final String ph_reaction = objJson.getString("ph_reaction");
                        final String proteins = objJson.getString("proteins");
                        final String sugar = objJson.getString("sugar");
                        final String occult_blood = objJson.getString("occult_blood");
                        final String acetone = objJson.getString("acetone");
                        final String bite_salf = objJson.getString("bite_salf");
                        final String bile_pigment = objJson.getString("bile_pigment");
                        final String epithelial_cells = objJson.getString("epithelial_cells");
                        final String pus_cells = objJson.getString("pus_cells");
                        final String red_blood_cells = objJson.getString("red_blood_cells");
                        final String any_other_finding = objJson.getString("any_other_finding");
                        final String amorphous_material = objJson.getString("amorphous_material");
                        final String trichomans_vaginalis = objJson.getString("trichomans_vaginalis");
                        final String casts = objJson.getString("casts");
                        String Result_Status = objJson.getString("Urine_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        final ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);
                        values.put("quantity", quantity);
                        values.put("colour", colour);
                        values.put("appearance", appearance);
                        values.put("sediment", sediment);
                        values.put("specific_gravity", specific_gravity);
                        values.put("ph_reaction", ph_reaction);
                        values.put("proteins", proteins);
                        values.put("sugar", sugar);
                        values.put("occult_blood", occult_blood);
                        values.put("acetone", acetone);
                        values.put("bite_salf", bite_salf);
                        values.put("bile_pigment", bile_pigment);
                        values.put("epithelial_cells", epithelial_cells);
                        values.put("pus_cells", pus_cells);
                        values.put("red_blood_cells", red_blood_cells);
                        values.put("any_other_finding", any_other_finding);
                        values.put("amorphous_material", amorphous_material);
                        values.put("trichomans_vaginalis", trichomans_vaginalis);
                        values.put("casts", casts);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        final boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_urine_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_urine_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_urine_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationANC_Test() {
        String PatId = "";
        String MtestId = "";
        final String MethodName = "Import_ExaminationANC_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            final JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));

                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            final JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                final String resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        final String ServerId = objJson.getString("Id");
                        final String Patid = objJson.getString("Patid");
                        final String Docid = objJson.getString("Docid");
                        final String Actdate = objJson.getString("Actdate");
                        final String IsActive = objJson.getString("IsActive");
                        final String IsUpdate = objJson.getString("IsUpdate");
                        final String Mtestid = objJson.getString("Mtestid");
                        final String Testid = objJson.getString("Testid");
                        final String Subtestid = objJson.getString("Subtestid");
                        final String HID = objJson.getString("HID");
                        final String IsPaid = objJson.getString("IsPaid");

                        final String haemogiobin = objJson.getString("Haemogiobin");
                        final String bloodgroup = objJson.getString("bloodgroup");
                        final String vdpl = objJson.getString("vdpl");
                        final String colour = objJson.getString("colour");
                        final String apperance = objJson.getString("apperance");
                        final String albumin = objJson.getString("albumin");
                        final String sugar = objJson.getString("sugar");
                        final String bsbp = objJson.getString("bsbp");
                        final String epithelialcells = objJson.getString("epithelialcells");
                        final String puscells = objJson.getString("puscells");
                        final String redcells = objJson.getString("redcells");
                        final String yeastcells = objJson.getString("redcells");
                        final String bacteria = objJson.getString("bacteria");
                        final String amarphousmatenal = objJson.getString("amarphousmatenal");
                        final String trichomonas = objJson.getString("trichomonas");
                        final String casts = objJson.getString("casts");
                        final String crystals = objJson.getString("crystals");
                        final String australia_antigen = objJson.getString("australia_antigen");
                        final String upt = objJson.getString("upt");
                        final String tc = objJson.getString("tc");
                        final String dcn = objJson.getString("dcn");
                        final String dcl = objJson.getString("dcl");
                        final String dce = objJson.getString("dce");
                        final String dcm = objJson.getString("dcm");
                        String Result_Status = objJson.getString("ANC_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        final ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);

                        values.put("haemogiobin", haemogiobin);
                        values.put("bloodgroup", bloodgroup);
                        values.put("vdpl", vdpl);
                        values.put("colour", colour);
                        values.put("apperance", apperance);
                        values.put("albumin", albumin);
                        values.put("sugar", sugar);
                        values.put("bsbp", bsbp);
                        values.put("epithelialcells", epithelialcells);
                        values.put("puscells", puscells);
                        values.put("redcells", redcells);
                        values.put("yeastcells", redcells);
                        values.put("bacteria", bacteria);
                        values.put("amarphousmatenal", amarphousmatenal);
                        values.put("trichomonas", trichomonas);
                        values.put("casts", casts);
                        values.put("crystals", crystals);
                        values.put("australia_antigen", australia_antigen);
                        values.put("upt", upt);
                        values.put("tc", tc);
                        values.put("dcn", dcn);
                        values.put("dcl", dcl);
                        values.put("dce", dce);
                        values.put("dcm", dcm);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        final boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_anc_fp_test where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_anc_fp_test", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_anc_fp_test", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }

        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }
    }

    private void Import_ExaminationHIV_Test() {
        String PatId = "";
        String MtestId = "";
        final String MethodName = "Import_ExaminationHIV_Test";
        try {
            JSONObject from_db_obj = new JSONObject();
            final JSONArray export_jsonarray = new JSONArray();

            //getting data from server
            String Query;
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='True' or b.IsActive='1')";
            final SQLiteDatabase db = BaseConfig.GetDb();
            final Cursor c;
            c = db.rawQuery(s, null);
            if (c != null) {

                if (c.moveToFirst()) {
                    do {

                        from_db_obj = new JSONObject();

                        from_db_obj.put("Patid", c.getString(c.getColumnIndex("Ptid")));
                        from_db_obj.put("Mtestid", c.getString(c.getColumnIndex("mtestid")));
                        PatId = c.getString(c.getColumnIndex("Ptid"));
                        MtestId = c.getString(c.getColumnIndex("mtestid"));
                        export_jsonarray.put(from_db_obj);

                        break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();

            final JSONObject parentData = new JSONObject();
            parentData.put("MtestIdList", export_jsonarray);

            //Sending data
            if ((parentData != null) && (parentData.length() > 0)) {


                final String resultsRequestSOAP = this.postPatidMtestId(PatId, MtestId, MethodName);


                if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                    final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                    JSONObject objJson;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        final String ServerId = objJson.getString("Id");
                        final String Patid = objJson.getString("Patid");
                        final String Docid = objJson.getString("Docid");
                        final String Actdate = objJson.getString("Actdate");
                        final String IsActive = objJson.getString("IsActive");
                        final String IsUpdate = objJson.getString("IsUpdate");
                        final String Mtestid = objJson.getString("Mtestid");
                        final String Testid = objJson.getString("Testid");
                        final String Subtestid = objJson.getString("Subtestid");
                        final String HID = objJson.getString("HID");
                        final String IsPaid = objJson.getString("IsPaid");


                        //String date_time= objJson.getString("date_time");
                        final String LabId = objJson.getString("LabId");
                        final String blood_datetime = objJson.getString("blood_datetime");

                        final String NameHIVTestKit1 = objJson.getString("NameHIVTestKit1");
                        final String BatchNo1 = objJson.getString("BatchNo1");
                        final String ExpDate1 = objJson.getString("ExpDate1");
                        final String Antibodies11 = objJson.getString("Antibodies11");
                        final String Antibodies21 = objJson.getString("Antibodies21");
                        final String Antibodies31 = objJson.getString("Antibodies31");

                        final String NameHIVTestKit2 = objJson.getString("NameHIVTestKit2");
                        final String BatchNo2 = objJson.getString("BatchNo2");
                        final String ExpDate2 = objJson.getString("ExpDate2");
                        final String Antibodies12 = objJson.getString("Antibodies12");
                        final String Antibodies22 = objJson.getString("Antibodies22");
                        final String Antibodies32 = objJson.getString("Antibodies32");

                        final String NameHIVTestKit3 = objJson.getString("NameHIVTestKit3");
                        final String BatchNo3 = objJson.getString("BatchNo3");
                        final String ExpDate3 = objJson.getString("ExpDate3");
                        final String Antibodies13 = objJson.getString("Antibodies13");
                        final String Antibodies23 = objJson.getString("Antibodies23");
                        final String Antibodies33 = objJson.getString("Antibodies33");

                        final String negative_HIV = objJson.getString("negative_HIV");
                        final String positive_HIV_1 = objJson.getString("positive_HIV_1");
                        final String positive_HIV = objJson.getString("positive_HIV");
                        final String HIV_Anitibodies = objJson.getString("HIV_Anitibodies");

                        String Result_Status = objJson.getString("HIV_Result");

                        if ("0".equalsIgnoreCase(Result_Status))//Nil
                        {
                            Result_Status = "Nil";
                        } else if ("1".equalsIgnoreCase(Result_Status))//Positive
                        {
                            Result_Status = "Positive";
                        } else if ("2".equalsIgnoreCase(Result_Status))//Negative
                        {
                            Result_Status = "Negative";
                        }

                        final ContentValues values = new ContentValues();
                        values.put("ServerId", ServerId);
                        values.put("Patid", Patid);
                        values.put("Docid", Docid);
                        values.put("Actdate", Actdate);
                        values.put("IsActive", IsActive);
                        values.put("IsUpdate", IsUpdate);
                        values.put("Mtestid", Mtestid);
                        values.put("Testid", Testid);
                        values.put("Subtestid", Subtestid);
                        values.put("HID", HID);
                        values.put("IsPaid", IsPaid);

                        values.put("date_time", Actdate);
                        values.put("LabId", LabId);
                        values.put("blood_datetime", blood_datetime);

                        values.put("NameHIVTestKit1", NameHIVTestKit1);
                        values.put("BatchNo1", BatchNo1);
                        values.put("ExpDate1", ExpDate1);
                        values.put("Antibodies11", Antibodies11);
                        values.put("Antibodies21", Antibodies21);
                        values.put("Antibodies31", Antibodies31);

                        values.put("NameHIVTestKit2", NameHIVTestKit2);
                        values.put("BatchNo2", BatchNo2);
                        values.put("ExpDate2", ExpDate2);
                        values.put("Antibodies12", Antibodies12);
                        values.put("Antibodies22", Antibodies22);
                        values.put("Antibodies32", Antibodies32);

                        values.put("NameHIVTestKit3", NameHIVTestKit3);
                        values.put("BatchNo3", BatchNo3);
                        values.put("ExpDate3", ExpDate3);
                        values.put("Antibodies13", Antibodies13);
                        values.put("Antibodies23", Antibodies23);
                        values.put("Antibodies33", Antibodies33);

                        values.put("negative_HIV", negative_HIV);
                        values.put("positive_HIV_1", positive_HIV_1);
                        values.put("positive_HIV", positive_HIV);
                        values.put("HIV_Anitibodies", HIV_Anitibodies);
                        values.put("Result_Status", Result_Status);


                        //data iruntha update else insert
                        final boolean q = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Bind_HIV_Report where Mtestid='" + Mtestid + "' and ServerId='" + ServerId + "' and Patid='" + Patid + '\'');
                        if (q) //update
                        {
                            db.update("Bind_HIV_Report", values, "ServerId='" + ServerId + "' and Patid='" + Patid + '\'', null);
                        } else //insert
                        {
                            db.insert("Bind_HIV_Report", null, values);

                            String Update_Query_Mpre = "update Medicaltestdtls set "
                                    + "testvalue ='More',"
                                    + "testsummary='-',"
                                    + "IsActive='0',"
                                    + "seen = '0',"
                                    + "ReadytoUpdate='2',"
                                    + "ValueUpdated='0',IsNew=1,result='" + Result_Status + "' where Ptid='" + Patid + "' and IsActive='1' and mtestid='" + Mtestid + "' and subtestid='" + Subtestid + '\'';
                            db.execSQL(Update_Query_Mpre);

                        }


                    }

                }


                db.close();


            }
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        } catch (final JSONException e) {
            e.printStackTrace();
        } catch (final SchemaException e) {
            e.printStackTrace();
        }

    }

    private String postIsUpdateMaxRESTMethod(final String isUpdateMax, final String methodName) throws SchemaException, InterruptedException, ExecutionException {
        final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
        final PostIsUpdateMaxFactory postDoctorIdFactory = new PostIsUpdateMaxFactory(magnetMobileClient);
        final PostIsUpdateMax postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        final PostIsUpdateMaxRequest body = new PostIsUpdateMaxRequest();
        body.setIsUpdateMax(isUpdateMax);
        body.setMethodName(methodName);

        final Call<IsUpdateMaxResult> resultCall = postDoctorId.postIsUpdateMax(body, null);

        return resultCall.get().getResults();
    }

    private String postPatientIdListRESTMethod(final String patientIdLIst, final String methodName) throws SchemaException, InterruptedException, ExecutionException {
        final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
        final PostPatientIdListFactory postDoctorIdFactory = new PostPatientIdListFactory(magnetMobileClient);
        final PostPatientIdList postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        final PostPatientIdListRequest body = new PostPatientIdListRequest();
        body.setPatientList(patientIdLIst);
        body.setMethodName(methodName);

        final Call<PatientIdListResult> resultCall = postDoctorId.postPatientIdList(body, null);

        return resultCall.get().getResults();
    }

    private String getDoctorIdRESTCALL(final String doctorID, final String methodName) throws SchemaException, InterruptedException, ExecutionException {
        final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
        final PostDoctorIdFactory postDoctorIdFactory = new PostDoctorIdFactory(magnetMobileClient);
        final PostDoctorId postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        final PostDoctorIdRequest body = new PostDoctorIdRequest();
        body.setDoctorID(doctorID);
        body.setMethodName(methodName);

        final Call<DoctorIdResult> resultCall = postDoctorId.postDoctorId(body, null);

        return resultCall.get().getResults();
    }

    private String postMtestIdDidPId(final String mtestid, final String docid, final String ptid, final String methodName, String resultsRequestSOAP) {
        try {
            final PosPatIdDocIdMtestIdNew posPatIdDocIdMtestIdNew;
            final MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(this.ctx);
            final PosPatIdDocIdMtestIdNewFactory controllerFactory = new PosPatIdDocIdMtestIdNewFactory(magnetClient);
            posPatIdDocIdMtestIdNew = controllerFactory.obtainInstance();
            final String contentType = "application/json";
            //Set Body Data
            final kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdRequest body = new kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdRequest();
            body.setDocId(docid);
            body.setMethodName(methodName);
            body.setPatId(ptid);
            body.setMtestId(mtestid);

            final Call<kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdResult> resultCall = posPatIdDocIdMtestIdNew.posPatIdDocIdMtestId(
                    contentType,
                    body, null);

            resultsRequestSOAP = resultCall.get().getResults();
        } catch (final SchemaException e) {
            e.printStackTrace();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
        return resultsRequestSOAP;
    }

    private void UpdateInCaseNotes(final String PatId, final String mTestId, final String alltest) {
        String subtestName = "";
        String subtestValue = "";
        String bps = "0";
        String bpd = "0";
        String temperature = "0";
        String weight = "0";

        try {
            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            final Cursor c = db.rawQuery("select subtestname,IFNULL(testvalue,'0')as testvalue,IFNULL(bps,'0')as bps,IFNULL(bpd,'0')as bpd,IFNULL(temperature,'0')as temperature,weight from Medicaltestdtls where Ptid = '" + PatId + "' and mtestid = '" + mTestId + "' and alltest = '" + alltest + "' ", null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        subtestName = c.getString(c.getColumnIndex("subtestname"));
                        subtestValue = c.getString(c.getColumnIndex("testvalue"));

                        bps = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("bps")));
                        bpd = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("bpd")));
                        temperature = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("temperature")));

                        weight = BaseConfig.CheckDBString(c.getString(c.getColumnIndex("weight")));


                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (final Exception e) {
            e.printStackTrace();
        }

        final ImportWebservices_NODEJS.PatientData data = this.getPatientName(PatId);

        final String[] split_subtest;

        split_subtest = alltest.split("/");

        String compareRBSName = "blood sug random";
        if (split_subtest[1].trim().equalsIgnoreCase(compareRBSName)) {

            final ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", 0);//fbs->bps
            values.put("PPS", 0);//pps->bpd
            values.put("RBS", temperature);//rbs->temperature

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        String compareFBSName = "blood sug fasting";
        if (split_subtest[1].trim().equalsIgnoreCase(compareFBSName)) {


            final ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", bps);
            values.put("PPS", 0);
            values.put("RBS", 0);

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }
        String comparePBSName = "blood sug pp";
        if (split_subtest[1].trim().equalsIgnoreCase(comparePBSName)) {


            final ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("pname", data.pName);
            values.put("gender", data.gender);
            values.put("age", data.age);
            values.put("mobnum", data.mobNum);
            values.put("clinicname", BaseConfig.clinicname);

            values.put("FBS", 0);
            values.put("PPS", bpd);
            values.put("RBS", 0);

            values.put("BpS", 0);
            values.put("BpD", 0);
            values.put("temperature", 0);

            values.put("PWeight", weight);
            values.put("Actdate", BaseConfig.DeviceDate());

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            db.insert("Diagonis", null, values);


        }

    }

    private ImportWebservices_NODEJS.PatientData getPatientName(final String patId) {
        final ImportWebservices_NODEJS.PatientData data = new PatientData();


        try {

            final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
            final Cursor c = db.rawQuery("select * from patreg where patid  = '" + patId.trim() + '\'', null);


            if (c != null) {
                if (c.moveToFirst()) {
                    do {
                        data.pName = c.getString(c.getColumnIndex("patientname"));
                        data.age = c.getString(c.getColumnIndex("age"));
                        data.gender = c.getString(c.getColumnIndex("gender"));
                        data.mobNum = c.getString(c.getColumnIndex("phone"));


                    } while (c.moveToNext());
                }
            }

            db.close();
            Objects.requireNonNull(c).close();


        } catch (final Exception e) {
            e.printStackTrace();
        }


        return data;

    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void getOperationDetails(final String patids) {
        //String url = BaseConfig.URL + "/GetPatientOperationInfo?PatientId=" + patid;
        //Log.e("Mast Operation url", url);

        // TODO: with response

        ContentValues values1;
        try {
            final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
            final PostPatIdFactory postDoctorIdFactory = new PostPatIdFactory(magnetMobileClient);
            final PostPatId postDoctorId = postDoctorIdFactory.obtainInstance();

            //Set Body Data
            final PosPatIdRequest body = new PosPatIdRequest();
            body.setPatientId(patids);


            final Call<PosPatIdResult> resultCall = postDoctorId.posPatId(body, null);

            final String resultsRequestSOAP = resultCall.get().getResults();


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {


                    objJson = jsonArray.getJSONObject(i);
                    String Remark_Image;
                    final String ServerId = String.valueOf(objJson.getString("operationId"));
                    final String OperationNo = String.valueOf(objJson.getString("OperationNo"));
                    final String OperationName = String.valueOf(objJson.getString("OperationName"));
                    final String patid = String.valueOf(objJson.getString("patid"));
                    final String PatientName = String.valueOf(objJson.getString("PatientName"));
                    final String Gender = String.valueOf(objJson.getString("Gender"));
                    final String ScheduleDate = String.valueOf(objJson.getString("ScheduleDate"));
                    final String Fromtime = String.valueOf(objJson.getString("Fromtime"));
                    final String Totime = String.valueOf(objJson.getString("Totime"));
                    final String Rate = String.valueOf(objJson.getString("Rate"));
                    final String IsActive = String.valueOf(objJson.getString("IsActive"));
                    final String curdate = String.valueOf(objJson.getString("curdate"));
                    final String IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    final String IsCancel = String.valueOf(objJson.getString("IsCancel"));
                    final String Remarks_Cancel = String.valueOf(objJson.getString("Remarks_Cancel"));
                    final String Status = String.valueOf(objJson.getString("Status"));

                    final String Oper_Notes = String.valueOf(objJson.getString("Oper_Notes"));
                    final String Position = String.valueOf(objJson.getString("Position"));
                    final String OperationProcedure = String.valueOf(objJson.getString("OperationProcedure"));
                    final String Post_Oper_Diagnosis = String.valueOf(objJson.getString("Post_Oper_Diagnosis"));
                    final String Post_Oper_Instruction = String.valueOf(objJson.getString("Post_Oper_Instruction"));
                    final String Closure = String.valueOf(objJson.getString("Closure"));
                    final String Pre_Oper_Diagnosis = String.valueOf(objJson.getString("Pre_Oper_Diagnosis"));


                    final String Doctor_Name = String.valueOf(objJson.getString("doctorname"));
                    Remark_Image = String.valueOf(objJson.getString("Remark_Image"));

                    Remark_Image = BaseConfig.saveImageToSDCard(Remark_Image, ServerId);

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("OperationNo", OperationNo);
                    values1.put("OperationName", OperationName);
                    values1.put("patid", patid);
                    values1.put("PatientName", PatientName);
                    values1.put("Gender", Gender);
                    values1.put("ScheduleDate", ScheduleDate);
                    values1.put("Fromtime", Fromtime);
                    values1.put("Totime", Totime);
                    values1.put("Rate", Rate);
                    values1.put("IsActive", IsActive);
                    values1.put("curdate", curdate);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsCancel", IsCancel);
                    values1.put("Remarks_Cancel", Remarks_Cancel);
                    values1.put("Status", Status);
                    values1.put("DoctorName", Doctor_Name);
                    values1.put("Oper_Notes", Oper_Notes);
                    values1.put("Position", Position);
                    values1.put("OperationProcedure", OperationProcedure);
                    values1.put("Post_Oper_Diagnosis", Post_Oper_Diagnosis);
                    values1.put("Post_Oper_Instruction", Post_Oper_Instruction);
                    values1.put("Closure", Closure);
                    values1.put("Pre_Oper_Diagnosis", Pre_Oper_Diagnosis);
                    values1.put("Remark_Image", Remark_Image);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from mast_Operation where ServerId=" + ServerId);

                    final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
                    if (GetStatus) {
                        db.update("mast_Operation", values1, "ServerId=" + ServerId, null);

                    } else {
                        db.insert("mast_Operation", null, values1);
                    }


                }

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }

    }

    private void getBindUpload(final String patid, final String mtestid1) {


        final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


        ContentValues values1;

        try {

            final String MethodName = "Import_Upload";
            final String resultsRequestSOAP = this.postPatidMtestId(patid, mtestid1, MethodName);


            if (!"[]".equalsIgnoreCase(resultsRequestSOAP) && !"".equalsIgnoreCase(resultsRequestSOAP)) {
                final JSONArray jsonArray = new JSONArray(resultsRequestSOAP);
                JSONObject objJson;

                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);
                    String Remark_Image;
                    final String ServerId = String.valueOf(objJson.getString("id"));
                    final String Patid = String.valueOf(objJson.getString("Patid"));
                    final String Fname = String.valueOf(objJson.getString("Fname"));
                    final String Filetype = String.valueOf(objJson.getString("Filetype"));
                    final String Type = String.valueOf(objJson.getString("Type"));
                    final String Cdtime = String.valueOf(objJson.getString("Cdtime"));
                    final String Docid = String.valueOf(objJson.getString("Docid"));
                    final String Upload_Base64 = String.valueOf(objJson.getString("Upload_Base64"));
                    final String mtestid = String.valueOf(objJson.getString("mtestid"));
                    final String Report_Remarks = String.valueOf(objJson.getString("image_remarks"));


                    //Log.e("getBindUpload: Upload_Base64: ", Upload_Base64);

                    Bitmap theBitmap = null;

                    theBitmap = BaseConfig.Glide_GetBitmap(ctx, Upload_Base64);//Glide.with(this.ctx).load(Upload_Base64).asBitmap().into(-1, -1).get();

                    String ReportPhoto = null;
                    try {
                        ReportPhoto = BaseConfig.saveURLImagetoSDcard1(theBitmap, ServerId);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Patid", Patid);
                    values1.put("Fname", Fname);
                    values1.put("Filetype", Filetype);
                    values1.put("Type", Type);
                    values1.put("Cdtime", Cdtime);
                    values1.put("Docid", Docid);
                    values1.put("Remarks", Report_Remarks);
                    values1.put("Upload_Base64", ReportPhoto);
                    values1.put("mtestid", mtestid);


                    final boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Bind_Upload where ServerId=" + ServerId);

                    if (GetStatus) {
                        db.update("Bind_Upload", values1, "ServerId=" + ServerId, null);

                    } else {
                        db.insert("Bind_Upload", null, values1);
                    }

                }

                db.close();

            }
        } catch (final Exception e) {
            e.printStackTrace();
        }


    }

    private String postPatidMtestId(final String patId, final String mtestId, final String methodName) throws SchemaException, InterruptedException, ExecutionException {
        final MagnetMobileClient magnetMobileClient = MagnetMobileClient.getInstance(this.ctx);
        final PostPatidMtestidFactory postDoctorIdFactory = new PostPatidMtestidFactory(magnetMobileClient);
        final PostPatidMtestid postDoctorId = postDoctorIdFactory.obtainInstance();

        //Set Body Data
        final PostPatidMtestidRequest body = new PostPatidMtestidRequest();
        body.setMethodName(methodName);
        body.setMtestId(mtestId);
        body.setPatId(patId);

        final Call<PatidMtestidResult> resultCall = postDoctorId.postPatidMtestid(body, null);

        return resultCall.get().getResults();
    }

    private void LoadInvestigation_Notification() {

        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {

            getPatientDetails();
        }

    }

    private final ArrayList<CommonDataObjects.NotificationGetSet> getPatientDetails() {
        ArrayList<CommonDataObjects.NotificationGetSet> value = new ArrayList<>();
        //Original Query
        String Query = "select Id as dstatus1  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "')";
        //Testing Purpose
        //String Query = "select Id as dstatus1  from Medicaltestdtls ";

        if (BaseConfig.LoadReportsBooleanStatus(Query)) {
            SQLiteDatabase db = BaseConfig.GetDb();
            Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') group by Ptid", null);
            //Cursor c = db.rawQuery("select *  from Medicaltestdtls limit 6", null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        int ID = Integer.parseInt(c.getString(c.getColumnIndex("id")));
                        String PATIENT_ID = c.getString(c.getColumnIndex("Ptid"));
                        String MEDICAL_TEST_ID = c.getString(c.getColumnIndex("mtestid"));
                        String PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + PATIENT_ID + "'");

                        CreateNewNotification(PATIENT_ID, PATIENT_NAME +"-"+ PATIENT_ID, MEDICAL_TEST_ID, ID);


                    }
                    while (c.moveToNext());
                }
            }

            c.close();
            db.close();
        }

        return value;
    }

    private void CreateNewNotification(String patienid, String patid_name, String medid, int ids) {
        Notification foregroundNote;
        RemoteViews bigView = new RemoteViews(ctx.getPackageName(), R.layout.notification_layout_big);

        bigView.setImageViewBitmap(R.id.patient_image, getPatientBitmpaImage(patienid));
        bigView.setTextViewText(R.id.patientnotification_id, medid);
        bigView.setTextViewText(R.id.patient_name, patid_name);


        Intent showFullQuoteIntent = new Intent(ctx, MyPatientDrawer.class);
        showFullQuoteIntent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, patienid);

        // both of these approaches now work: FLAG_CANCEL, FLAG_UPDATE; the uniqueInt may be the real solution.
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        int uniqueInt = (int) (System.currentTimeMillis() & 0xfffffff);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, uniqueInt, showFullQuoteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // bigView.setOnClickPendingIntent() etc..
        Notification.Builder mNotifyBuilder = new Notification.Builder(ctx);
        foregroundNote = mNotifyBuilder.setContentTitle("Investigation Results - Expand to view")
                .setContentText(patid_name)
                .setSmallIcon(R.drawable.logo_high_pix)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build();

        foregroundNote.bigContentView = bigView;

        // now show notification..
        NotificationManager mNotifyManager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        // tapping notification will open MainActivity


        mNotifyManager.notify(ids, foregroundNote);
    }

    public Bitmap getPatientBitmpaImage(String patient_Id) {

        SQLiteDatabase db = BaseConfig.GetDb();
        Cursor c = db.rawQuery("select PC from Patreg where Patid='" + patient_Id + "'", null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {

                    try {
                        File sd = Environment.getExternalStorageDirectory();
                        File image = new File(c.getString(c.getColumnIndex("PC")));
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(image.getAbsolutePath(), bmOptions);
                        return bitmap;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                } while (c.moveToNext());
            }
        }
        return null;
    }


    private ArrayList<CommonDataObjects.NotificationListGetSet> getTableData(String patientid) {
        ArrayList<CommonDataObjects.NotificationListGetSet> values = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();

        Cursor c = db.rawQuery("select *  from Medicaltestdtls where seen = '0' and IsNew=1 and (substr(Actdate,0,11)='" + BaseConfig.CompareWithDeviceDate() + "' or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDate() + "'  or substr(Actdate,0,11)='" + BaseConfig.Device_OnlyDateWithHypon() + "') and Ptid='" + patientid + '\'', null);

        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    String va = "";

                    if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareRBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("temperature")), c.getString(c.getColumnIndex("testsummary"))));


                    } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareFBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bps")), c.getString(c.getColumnIndex("testsummary"))));

                    } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(comparePBSName)) {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("bpd")), c.getString(c.getColumnIndex("testsummary"))));

                    } else {
                        values.add(new CommonDataObjects.NotificationListGetSet(String.valueOf(c.getString(c.getColumnIndex("alltest"))), c.getString(c.getColumnIndex("testvalue")), c.getString(c.getColumnIndex("testsummary"))));
                    }


                } while (c.moveToNext());
            }
        }
        c.close();
        return values;
    }

    static class PatientData {
        // --Commented out by Inspection (25-Apr-18 4:36 PM):String patId = "";
        String pName = "";
        String gender = "";
        String age = "";
        String mobNum = "";


        PatientData() {
        }

    }

    //============================================================================================================
    //============================================================================================================


}//END
