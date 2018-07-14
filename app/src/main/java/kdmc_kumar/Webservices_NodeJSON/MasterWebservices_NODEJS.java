package kdmc_kumar.Webservices_NodeJSON;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.NewImportMaster.controller.api.NewImportMaster;
import kdmc_kumar.Webservices_NodeJSON.NewImportMaster.controller.api.NewImportMasterFactory;
import kdmc_kumar.Webservices_NodeJSON.NewImportMaster.model.beans.GetNewMastersRequest;
import kdmc_kumar.Webservices_NodeJSON.NewImportMaster.model.beans.NewMastersResult;
import kdmc_kumar.Webservices_NodeJSON.getMasters.controller.api.GetMaster;
import kdmc_kumar.Webservices_NodeJSON.getMasters.controller.api.GetMasterFactory;
import kdmc_kumar.Webservices_NodeJSON.getMasters.model.beans.GetMastersRequest;
import kdmc_kumar.Webservices_NodeJSON.getMasters.model.beans.MastersResult;

/**
 * Created by Android on 9/19/2017.
 */

public class MasterWebservices_NODEJS {

    Context ctx;
    SQLiteDatabase db = BaseConfig.GetDb();
    String Query = "";
    String ServerId, IsUpdate, IsActive, WardCategoryName, HID, Medicine, curDate;
    private String Data = "";

    public MasterWebservices_NODEJS(Context ctx) {
        this.ctx = ctx;
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
            }else
            {
                BaseConfig.server_connectivity_status = false;
            }
            conn.disconnect();
            return statusvalue;
        } catch (Exception e) {
        }
        return statusvalue;
    }

    @SuppressLint("LongLogTag")
    public void ExecuteAll() {

        if (BaseConfig.CheckNetwork(ctx)) {

            try {

                if (CheckNodeServer()) {

                    Log.e("###########", "################");
                    Log.e("MobyDoctor BackGround", "Thread Master Service running 2");
                    Log.e("MobyDoctor BackGround", "Thread Master Service running 2");
                    Log.e("MobyDoctor BackGround", "Thread Master Service running 2");
                    Log.e("###########", "################");


                    Mstr_current_patient_list();
                    Mstr_diagonisdetails();
                    Mstr_Symptoms();
                    ImportPharamcy();
                    Mstr_listofvaccine();
                    Mstr_Allergy();
                    Mstr_MedicalHistory();
                    Mstr_FamilyHistory();
                    Mstr_HereditaryDiseases();

                    //New masters
                    Mstr_Gender();
                    Mstr_BloodGroup();
                    Mstr_MaritalStatus();
                    Mstr_Prefix();
                    Mstr_FeeExemptionCategory();
                    Mstr_Caste();
                    Mstr_Occupation();
                    Mstr_Signs();


                    Mstr_MultipleHospital();
                    Mstr_Academic();
                    Mstr_Special();
                    Mstr_xray();
                    Mstr_RoomType();
                    Mstr_FoodItems();
                    Mstr_DietSession();
                    Mstr_ward();
                    Mstr_room();
                    Mstr_bed();
                    Mstr_WardCategory();
                    Mstr_diet_entry();


                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    private StringBuilder getMasterTableREST_Result(StringBuilder result, String tableId, String isUpdateMax) {
        //NodeJs convert
        try {
            GetMaster postTableData;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            GetMasterFactory controllerFactory = new GetMasterFactory(magnetClient);
            postTableData = controllerFactory.obtainInstance();
            String contentType = "application/json";
            GetMastersRequest body = new GetMastersRequest();


            body.setTableName(tableId);
            body.setIsUpdateMax(isUpdateMax);

            Call<MastersResult> callObject = postTableData.getMasters(contentType, body, null);


            MastersResult results = callObject.get();
            result = new StringBuilder(results.getResults());

           // Log.e("Master Results: ", result.toString());

        } catch (SchemaException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


    private StringBuilder getMasterTableREST_ResultNew(StringBuilder result, String tableId, String isUpdateMax, String masterId) {
        //NodeJs convert
        try {
            NewImportMaster postTableData;
            // Instantiate a controller
            MagnetMobileClient magnetClient = MagnetMobileClient.getInstance(ctx);
            NewImportMasterFactory controllerFactory = new NewImportMasterFactory(magnetClient);
            postTableData = controllerFactory.obtainInstance();
            String contentType = "application/json";
            GetNewMastersRequest body = new GetNewMastersRequest();


            body.setMasterId(masterId);
            body.setIsUpdateMax(isUpdateMax);
            body.setTableId(tableId);

            body.setIsUpdateMax(isUpdateMax);

            Call<NewMastersResult> callObject = postTableData.getNewMasters(contentType, body, null);


            NewMastersResult results = callObject.get();

            result = new StringBuilder(results.getResults());

           // Log.e("Master Results: ", result.toString());

        } catch (SchemaException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }


    private int GetMaxUpdateValueFromTable(String TableName, String MasterTableId, String ColumnName) {
        int MaxRet = 0;

        String query = "select IFNULL(max(" + ColumnName + "),'0') as ret_values from " + TableName + " where MasterId=" + MasterTableId + "";
        MaxRet = Integer.parseInt(BaseConfig.GetValues(query));

        return MaxRet;
    }


    private void ImportPharamcy() {

        StringBuilder result = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);

        String IsActive = "";
        String str = "";

        String Query = "select IFNULL(max(serverid),'0') as IsUpdateMax from cims";
        str =    BaseConfig.GetMaxValues(Query);

        String tableId = "ImportPharamcy";
        String isUpdateMax = str;

        result = getMasterTableREST_Result(result, tableId, isUpdateMax);


        ContentValues values1;

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());

                JSONObject objJson = null;

                for (int i = 0; i < jsonArray.length(); i++) {
                    objJson = jsonArray.getJSONObject(i);
                    //{ "ScanID": "1", "SantypeID": "1", "ScanforID": "5", "Scan": "3D", "IsActive": "True", "curDate": "", "IsUpdate": "1" }

                    String ServerId = String.valueOf(objJson.getString("id"));
                    String IsUpdate = String.valueOf(objJson.getString("isupdate"));
                    String DOSAGE = String.valueOf(objJson.getString("DOSAGE"));
                    String MEDICINEINTAKETYPE = String.valueOf(objJson.getString("MEDICINEINTAKETYPE"));
                    String MARKETNAMEOFDRUG = String.valueOf(objJson.getString("MARKETNAMEOFDRUG"));
                    String Schedule_Category = String.valueOf(objJson.getString("Schedule_Category"));

                    String e = objJson.getString("IsActive");
                    if (e.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {

                        IsActive = "0";

                    }

                    values1 = new ContentValues();
                    values1.put("serverid", ServerId);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("MEDICINEINTAKETYPE", MEDICINEINTAKETYPE);
                    values1.put("MARKETNAMEOFDRUG", MARKETNAMEOFDRUG);
                    values1.put("DOSAGE", DOSAGE);
                    values1.put("Schedule_Category", Schedule_Category);


                    boolean GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from cims where serverid='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("cims", null, values1);
                    } else {
                        db.update("cims", values1, "serverid='" + ServerId + "'", null);
                    }


                }


            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

    }

    private void Mstr_diagonisdetails() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from diagonisdetails";
        str = BaseConfig.GetMaxValues(Query);

        String tableId = "1";
        String isUpdateMax = str;


        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);


        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("ProvdiagID"));
                    Medicine = String.valueOf(objJson.getString("DiagnosisName"));
                    IsActive = objJson.getString("IsActive");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("diagnosisdata", Medicine);
                    values1.put("isactive", IsActive);

                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from diagonisdetails where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("diagonisdetails", null, values1);
                    } else {
                        db.update("diagonisdetails", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void Mstr_Symptoms() {


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from trmntfor";


        str = BaseConfig.GetMaxValues(Query);


        String tableId = "2";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    Medicine = String.valueOf(objJson.getString("treatmentfor"));
                    IsActive = objJson.getString("IsActive");

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("treatmentfor", Medicine);
                    values1.put("isactive", IsActive);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from trmntfor where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("trmntfor", null, values1);
                    } else {
                        db.update("trmntfor", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_current_patient_list() {


        String str = "";

        String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from current_patient_list";

        str = BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "19";
        String isUpdateMax = str+"^"+BaseConfig.doctorId+"^"+BaseConfig.HID;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);
               // db.execSQL("Delete from current_patient_list");

                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    String date = String.valueOf(objJson.getString("date"));
                    String pat_id = String.valueOf(objJson.getString("patid"));
                    String doc_id = String.valueOf(objJson.getString("docid"));
                    String IsUpdate = String.valueOf(objJson.get("IsUpdate"));
                    String ServerId = String.valueOf(objJson.get("Id"));
                    String status = String.valueOf(objJson.get("status"));
                    String HID = String.valueOf(objJson.get("HID"));

                    values1 = new ContentValues();
                    values1.put("date", date);
                    values1.put("patid", pat_id);
                    values1.put("docid", doc_id);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("ServerId", ServerId);
                    values1.put("status", status);
                    values1.put("closed", "0");
                    values1.put("HID", HID);


                    if (HID.equalsIgnoreCase(BaseConfig.HID)) {

                        GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from current_patient_list where ServerId='" + ServerId + "'");

                        if (!GetStatus) {
                            db.insert("current_patient_list", null, values1);
                        }/* else {
                            db.update("current_patient_list", values1, "ServerId='" + ServerId + "'", null);
                        }*/
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_MultipleHospital() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_MultipleHospital";
        str = BaseConfig.GetMaxValues(Query);


        // str = "0";
        String tableId = "16";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Hospital_Name = String.valueOf(objJson.getString("Hospital_Name"));
                    String Hospital_Code = String.valueOf(objJson.getString("Hospital_Code"));
                    String Email = String.valueOf(objJson.getString("Email"));
                    String Phone = String.valueOf(objJson.getString("Phone"));
                    String Mobile = String.valueOf(objJson.getString("Mobile"));
                    String Contact_Person = String.valueOf(objJson.getString("Contact_Person"));
                    String Fax = String.valueOf(objJson.getString("Fax"));
                    String ServiceTax = String.valueOf(objJson.getString("ServiceTax"));
                    String Country = String.valueOf(objJson.getString("Country"));
                    String State = String.valueOf(objJson.getString("State"));
                    String City = String.valueOf(objJson.getString("City"));
                    String Pincode = String.valueOf(objJson.getString("Pincode"));
                    String Address = String.valueOf(objJson.getString("Address"));
                    String Hospital_Logo = String.valueOf(objJson.getString("Hospital_Logo"));
                    IsActive = String.valueOf(objJson.getString("IsActive"));
                    String HID = String.valueOf(objJson.getString("HID"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("Hospital_Name", Hospital_Name);
                    values1.put("Hospital_Code", Hospital_Code);
                    values1.put("Phone", Phone);
                    values1.put("Mobile", Mobile);
                    values1.put("Contact_Person", Contact_Person);
                    values1.put("Fax", Fax);
                    values1.put("ServiceTax", ServiceTax);
                    values1.put("Country", Country);
                    values1.put("State", State);
                    values1.put("City", City);
                    values1.put("Pincode", Pincode);
                    values1.put("Address", Address);
                    values1.put("Hospital_Logo", Hospital_Logo);
                    values1.put("Address", Address);
                    values1.put("HID", HID);
                    values1.put("Email", Email);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_MultipleHospital where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_MultipleHospital", null, values1);
                    } else {
                        db.update("Mstr_MultipleHospital", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Mstr_Academic() {


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Academic";

        str =  BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "9";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Academic = String.valueOf(objJson.getString("Academic"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));


                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Academic", Academic);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Academic where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_Academic", null, values1);
                    } else {
                        db.update("Mstr_Academic", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void Mstr_Special() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Special";
        str = BaseConfig.GetMaxValues(Query);

        //str = "0";
        String tableId = "10";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String specialization = String.valueOf(objJson.getString("specialization"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));


                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("specialization", specialization);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Special where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_Special", null, values1);
                    } else {
                        db.update("Mstr_Special", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_xray() {

        String str = "0";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from xray_mstr";

        str =         BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "15";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("id"));
                    IsUpdate = String.valueOf(objJson.getString("Isupdate"));
                    String xraydetail = String.valueOf(objJson.getString("xraydetail"));


                    IsActive = objJson.getString("IsActive");
                    if (IsActive.equalsIgnoreCase("1")) {
                        IsActive = "1";
                    } else {

                        IsActive = "0";

                    }


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("xraydetail", xraydetail);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from xray_mstr where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("xray_mstr", null, values1);
                    } else {
                        db.update("xray_mstr", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Mstr_RoomType() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_RoomType";
        str =  BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "3";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Roomtypeid"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    String roomName = String.valueOf(objJson.getString("Roomtypename"));


                    String e = objJson.getString("IsActive");
                    if (e.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {

                        IsActive = "0";

                    }


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("roomt_type_name", roomName);

                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_RoomType where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_RoomType", null, values1);
                    } else {
                        db.update("Mstr_RoomType", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_FoodItems() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_FoodItems";
        str =   BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "4";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Dietitemid"));
                    String DietName = String.valueOf(objJson.getString("DietName"));
                    String Calories = objJson.getString("Calories");
                    IsActive = objJson.getString("IsActive");
                    IsUpdate = objJson.getString("IsUpdate");

                    values1 = new ContentValues();
                    values1.put("food_id", ServerId);
                    values1.put("food_name", DietName);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("calories", Calories);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_FoodItems where food_id='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_FoodItems", null, values1);
                    } else {
                        db.update("Mstr_FoodItems", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_DietSession() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_DietSession";
        str =  BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "5";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("DietsessId"));
                    String SessionName = String.valueOf(objJson.getString("Sessionname"));
                    IsActive = objJson.getString("IsActive");
                    IsUpdate = objJson.getString("IsUpdate");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else if (IsActive.equalsIgnoreCase("false")) {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("SessionName", SessionName);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);

                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_DietSession where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_DietSession", null, values1);
                    } else {
                        db.update("Mstr_DietSession", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_ward() {
        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from mast_ward";
        str =   BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "6";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Ward_id"));
                    String WardcatID = String.valueOf(objJson.getString("WardcatID"));
                    String WardName_NO = String.valueOf(objJson.getString("WardName_NO"));
                    String No_of_beds = String.valueOf(objJson.getString("No_of_beds"));
                    String DepartmentID = String.valueOf(objJson.getString("DepartmentID"));
                    String BlockID = String.valueOf(objJson.getString("BlockID"));
                    String FloorID = String.valueOf(objJson.getString("FloorID"));
                    curDate = String.valueOf(objJson.getString("curDate"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));

                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("WardcatID", WardcatID);
                    values1.put("WardName_NO", WardName_NO);
                    values1.put("No_of_beds", No_of_beds);
                    values1.put("DepartmentID", DepartmentID);
                    values1.put("BlockID", BlockID);
                    values1.put("FloorID", FloorID);
                    values1.put("curDate", curDate);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from mast_ward where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("mast_ward", null, values1);
                    } else {
                        db.update("mast_ward", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void Mstr_room() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from mast_room";
        str =   BaseConfig.GetMaxValues(Query);

        // str = "0";
        String tableId = "7";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("RoomeID"));
                    String WardID = String.valueOf(objJson.getString("WardID"));
                    String RoomtypeID = String.valueOf(objJson.getString("RoomtypeID"));
                    String Roomno = String.valueOf(objJson.getString("Roomno"));
                    curDate = String.valueOf(objJson.getString("curDate"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    String WardcatId = String.valueOf(objJson.getString("WardcatId"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("RoomtypeID", RoomtypeID);
                    values1.put("WardID", WardID);
                    values1.put("Roomno", Roomno);
                    values1.put("curDate", curDate);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("WardcatId", WardcatId);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from mast_room where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("mast_room", null, values1);

                    } else {

                        db.update("mast_room", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_bed() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from mast_bed";
        str =  BaseConfig.GetMaxValues(Query);


        // str = "0";
        String tableId = "8";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("BedID"));
                    String WardID = String.valueOf(objJson.getString("WardID"));
                    String RoomID = String.valueOf(objJson.getString("RoomID"));
                    String Bed = String.valueOf(objJson.getString("Bed"));
                    curDate = String.valueOf(objJson.getString("curDate"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    String WardcatId = String.valueOf(objJson.getString("WardcatId"));
                    String status = String.valueOf(objJson.getString("Status"));


                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("RoomID", RoomID);
                    values1.put("WardID", WardID);
                    values1.put("Bed", Bed);
                    values1.put("WardcatId", WardcatId);
                    values1.put("curDate", curDate);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("status", status);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from mast_bed where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("mast_bed", null, values1);
                    } else {
                        db.update("mast_bed", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_WardCategory() {


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_WardCategory";
        str =   BaseConfig.GetMaxValues(Query);


        // str = "0";
        String tableId = "13";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("WardcatID"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));
                    WardCategoryName = String.valueOf(objJson.getString("WardCategoryName"));
                    HID = String.valueOf(objJson.getString("HID"));


                    IsActive = objJson.getString("IsActive");
                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {

                        IsActive = "0";

                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsActive", IsActive);
                    values1.put("WardCatName", WardCategoryName);
                    values1.put("HID", HID);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_WardCategory where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_WardCategory", null, values1);
                    } else {
                        db.update("Mstr_WardCategory", values1, "ServerId='" + ServerId + "'", null);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_listofvaccine() {

        String str = "";

        String Query = "select IFNULL(max(IsUpdate),'0') as IsUpdateMax from listofvaccine";
        str =  BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "14";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    String Vaccinename = String.valueOf(objJson.getString("Vaccinename"));
                    String Docid = String.valueOf(objJson.getString("Schedule"));
                    String OperationNo = objJson.getString("Isactive");
                    String OperationName = objJson.getString("Dt");
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();

                    values1.put("vaccinename", Vaccinename);
                    values1.put("schedule", Docid);
                    values1.put("isactive", OperationNo);
                    values1.put("dt", OperationName);
                    values1.put("IsUpdate", IsUpdate);

                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from listofvaccine where IsUpdate='" + IsUpdate + "'");

                    if (!GetStatus) {
                        db.insert("listofvaccine", null, values1);
                    } else {
                        db.update("listofvaccine", values1, "IsUpdate='" + IsUpdate + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_Room_avaliability() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Room_avaliability";


        Cursor c = db.rawQuery(Query, null);

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

        //str = "0";
        String tableId = "17";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("RoomAval_Id"));
                    String Patid = String.valueOf(objJson.getString("Patid"));
                    String WardId = String.valueOf(objJson.getString("WardId"));
                    String RoomId = String.valueOf(objJson.getString("RoomId"));
                    String BedId = String.valueOf(objJson.getString("BedId"));
                    String Status = String.valueOf(objJson.getString("Status"));
                    curDate = String.valueOf(objJson.getString("curDate"));
                    IsUpdate = String.valueOf(objJson.getString("IsUpdate"));


                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {
                        IsActive = "1";
                    } else {
                        IsActive = "0";
                    }

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Patid", Patid);
                    values1.put("WardId", WardId);
                    values1.put("RoomId", RoomId);

                    values1.put("Status", Status);

                    values1.put("BedId", BedId);

                    values1.put("curDate", curDate);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Room_avaliability where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Room_avaliability", null, values1);
                    } else {
                        db.update("Room_avaliability", values1, "ServerId='" + ServerId + "'", null);
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void Mstr_diet_entry() {

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from diet_entry";
        str =   BaseConfig.GetMaxValues(Query);


        //str = "0";
        String tableId = "18";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String diet_id = String.valueOf(objJson.getString("diet_id"));
                    String pat_id = String.valueOf(objJson.getString("pat_id"));
                    String doc_id = String.valueOf(objJson.getString("doc_id"));
                    String diet_session_id = String.valueOf(objJson.getString("DietsessionID"));
                    String food_id = String.valueOf(objJson.getString("DietitemID"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("diet_id", diet_id);
                    values1.put("pat_id", pat_id);
                    values1.put("doc_id", doc_id);
                    values1.put("diet_session_id", diet_session_id);
                    values1.put("food_id", food_id);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);
                    values1.put("IsDelete", 0);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from diet_entry where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("diet_entry", null, values1);

                    } else {
                        db.update("diet_entry", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void Mstr_Allergy() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String tableId = "0";
        String isUpdateMax = String.valueOf(GetMaxUpdateValueFromTable("Mstr_Others", "1", "ServerId"));

        String masterId = "1";

        try {

            StringBuilder results = new StringBuilder();
            results = getMasterTableREST_ResultNew(results, tableId, masterId, isUpdateMax);
            JSONArray jsonArray = new JSONArray(results.toString());

            JSONObject objJson = null;
            ContentValues values1;
            boolean GetStatus;


            for (int i = 0; i < jsonArray.length(); i++) {

                objJson = jsonArray.getJSONObject(i);

                ServerId = String.valueOf(objJson.getString("Id"));
                String MasterId = String.valueOf(objJson.getString("MasterId"));
                String MasterName = String.valueOf(objJson.getString("Mastername"));
                Data = String.valueOf(objJson.getString("Name"));
                IsActive = objJson.getString("IsActive");
                IsUpdate = objJson.getString("IsUpdate");

                values1 = new ContentValues();
                values1.put("ServerId", ServerId);
                values1.put("MasterId", MasterId);
                values1.put("MasterName", MasterName);
                values1.put("DataVal", Data);
                values1.put("IsActive", IsActive);
                values1.put("IsUpdate", IsUpdate);

                GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Others where ServerId='" + ServerId + "'");

                if (!GetStatus) {
                    db.insert("Mstr_Others", null, values1);
                } else {
                    db.update("Mstr_Others", values1, "ServerId='" + ServerId + "'", null);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }


    private void Mstr_MedicalHistory() {
        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String tableId = "0";
        String isUpdateMax = String.valueOf(GetMaxUpdateValueFromTable("Mstr_Others", "2", "ServerId"));

        String masterId = "2";

        // Call<String> call = masterothers_RESTAPI.table1(tableId, masterId, isUpdateMax, null);

        try {
            //  String result = call.get();


            StringBuilder results = new StringBuilder();
            results = getMasterTableREST_ResultNew(results, tableId, masterId, isUpdateMax);
            JSONArray jsonArray = new JSONArray(results.toString());


            JSONObject objJson = null;
            ContentValues values1;
            boolean GetStatus;


            for (int i = 0; i < jsonArray.length(); i++) {

                objJson = jsonArray.getJSONObject(i);

                ServerId = String.valueOf(objJson.getString("Id"));
                String MasterId = String.valueOf(objJson.getString("MasterId"));
                String MasterName = String.valueOf(objJson.getString("Mastername"));
                Data = String.valueOf(objJson.getString("Name"));
                IsActive = objJson.getString("IsActive");
                IsUpdate = objJson.getString("IsUpdate");

                values1 = new ContentValues();
                values1.put("ServerId", ServerId);
                values1.put("MasterId", MasterId);
                values1.put("MasterName", MasterName);
                values1.put("DataVal", Data);
                values1.put("IsActive", IsActive);
                values1.put("IsUpdate", IsUpdate);

                GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Others where ServerId='" + ServerId + "'");

                if (!GetStatus) {
                    db.insert("Mstr_Others", null, values1);
                } else {
                    db.update("Mstr_Others", values1, "ServerId='" + ServerId + "'", null);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }


    private void Mstr_FamilyHistory() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String tableId = "0";
        String isUpdateMax = String.valueOf(GetMaxUpdateValueFromTable("Mstr_Others", "3", "ServerId"));
        String masterId = "3";

        //Call<String> call = masterothers_RESTAPI.table1(tableId, masterId, isUpdateMax, null);

        try {
            // String result = call.get();


            StringBuilder results = new StringBuilder();
            results = getMasterTableREST_ResultNew(results, tableId, masterId, isUpdateMax);


            JSONArray jsonArray = new JSONArray(results.toString());
            JSONObject objJson = null;
            ContentValues values1;
            boolean GetStatus;


            for (int i = 0; i < jsonArray.length(); i++) {

                objJson = jsonArray.getJSONObject(i);

                ServerId = String.valueOf(objJson.getString("Id"));
                String MasterId = String.valueOf(objJson.getString("MasterId"));
                String MasterName = String.valueOf(objJson.getString("Mastername"));
                Data = String.valueOf(objJson.getString("Name"));
                IsActive = objJson.getString("IsActive");
                IsUpdate = objJson.getString("IsUpdate");

                values1 = new ContentValues();
                values1.put("ServerId", ServerId);
                values1.put("MasterId", MasterId);
                values1.put("MasterName", MasterName);
                values1.put("DataVal", Data);
                values1.put("IsActive", IsActive);
                values1.put("IsUpdate", IsUpdate);

                GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Others where ServerId='" + ServerId + "'");

                if (!GetStatus) {
                    db.insert("Mstr_Others", null, values1);
                } else {
                    db.update("Mstr_Others", values1, "ServerId='" + ServerId + "'", null);
                }


            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        db.close();

    }


    private void Mstr_HereditaryDiseases() {

        SQLiteDatabase db = BaseConfig.GetDb();//ctx);
        String tableId = "0";
        String isUpdateMax = String.valueOf(GetMaxUpdateValueFromTable("Mstr_Others", "4", "ServerId"));

        String masterId = "4";


        try {
            // String result = call.get();


            StringBuilder results = new StringBuilder();
            results = getMasterTableREST_ResultNew(results, tableId, masterId, isUpdateMax);

            if (!results.toString().equalsIgnoreCase("") && !results.toString().equalsIgnoreCase("[]")) {


                JSONArray jsonArray = new JSONArray(results.toString());

                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String MasterId = String.valueOf(objJson.getString("MasterId"));
                    String MasterName = String.valueOf(objJson.getString("Mastername"));
                    Data = String.valueOf(objJson.getString("Name"));
                    IsActive = objJson.getString("IsActive");
                    IsUpdate = objJson.getString("IsUpdate");

                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("MasterId", MasterId);
                    values1.put("MasterName", MasterName);
                    values1.put("DataVal", Data);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);

                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Mstr_Others where ServerId='" + ServerId + "'");

                    if (!GetStatus) {
                        db.insert("Mstr_Others", null, values1);
                    } else {
                        db.update("Mstr_Others", values1, "ServerId='" + ServerId + "'", null);
                    }


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        db.close();

    }



    // ******************************* New Master Services *****************************************

    public void Mstr_Gender(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Gender";

        str =  BaseConfig.GetMaxValues(Query);


        String tableId = "20";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Gender = String.valueOf(objJson.getString("Gender"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Gender", Gender);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_Gender where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_Gender", null, values1);

                    } else {
                        db.update("Mstr_Gender", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Mstr_BloodGroup(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_BloodGroup";

        str =  BaseConfig.GetMaxValues(Query);


        String tableId = "21";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Bloodgroup = String.valueOf(objJson.getString("Bloodgroup"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Bloodgroup", Bloodgroup);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_BloodGroup where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_BloodGroup", null, values1);

                    } else {
                        db.update("Mstr_BloodGroup", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Mstr_MaritalStatus(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_MaritalStatus";
        str =  BaseConfig.GetMaxValues(Query);


        String tableId = "22";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Marital_Status = String.valueOf(objJson.getString("MaritalStatus"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Marital_Status", Marital_Status);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_MaritalStatus where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_MaritalStatus", null, values1);

                    } else {
                        db.update("Mstr_MaritalStatus", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Mstr_Prefix(){

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Prefix";

        str =  BaseConfig.GetMaxValues(Query);


        String tableId = "23";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Prefix = String.valueOf(objJson.getString("Prefix"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Prefix", Prefix);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_Prefix where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_Prefix", null, values1);

                    } else {
                        db.update("Mstr_Prefix", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void Mstr_FeeExemptionCategory(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_FeeExemptions";

        str =   BaseConfig.GetMaxValues(Query);


        String tableId = "24";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String FeeExemption = String.valueOf(objJson.getString("FeeExemption"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("FeeExemption", FeeExemption);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_FeeExemptions where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_FeeExemptions", null, values1);

                    } else {
                        db.update("Mstr_FeeExemptions", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void Mstr_Caste(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Caste";

        str =    BaseConfig.GetMaxValues(Query);


        String tableId = "25";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Caste = String.valueOf(objJson.getString("Caste"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Caste", Caste);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_Caste where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_Caste", null, values1);

                    } else {
                        db.update("Mstr_Caste", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void Mstr_Occupation(){


        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Occupation";

        str =  BaseConfig.GetMaxValues(Query);

        String tableId = "26";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String Occupation = String.valueOf(objJson.getString("Occupation"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("Occupation", Occupation);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_Occupation where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_Occupation", null, values1);

                    } else {
                        db.update("Mstr_Occupation", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }


    public void Mstr_Signs(){

        String str = "";

        String Query = "select IFNULL(max(ServerId),'0') as IsUpdateMax from Mstr_Signs";

        str =  BaseConfig.GetMaxValues(Query);

        String tableId = "27";
        String isUpdateMax = str;

        StringBuilder result = new StringBuilder();
        result = getMasterTableREST_Result(result, tableId, isUpdateMax);

        try {
            if (!result.toString().equalsIgnoreCase("") && !result.toString().equalsIgnoreCase("[]")) {
                JSONArray jsonArray = new JSONArray(result.toString());
                JSONObject objJson = null;
                ContentValues values1;
                boolean GetStatus;
                final SQLiteDatabase db = BaseConfig.GetDb();//ctx);


                for (int i = 0; i < jsonArray.length(); i++) {

                    objJson = jsonArray.getJSONObject(i);

                    ServerId = String.valueOf(objJson.getString("Id"));
                    String signs = String.valueOf(objJson.getString("Signs"));
                    IsActive = objJson.getString("IsActive");

                    if (IsActive.equalsIgnoreCase("true") || IsActive.equalsIgnoreCase("1")) {

                        IsActive = "1";

                    } else {
                        IsActive = "0";
                    }
                    IsUpdate = objJson.getString("IsUpdate");


                    values1 = new ContentValues();
                    values1.put("ServerId", ServerId);
                    values1.put("signs", signs);
                    values1.put("IsActive", IsActive);
                    values1.put("IsUpdate", IsUpdate);


                    GetStatus = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1   from Mstr_Signs where ServerId='" + ServerId + "'");

                    if (!GetStatus) {

                        db.insert("Mstr_Signs", null, values1);

                    } else {
                        db.update("Mstr_Signs", values1, "ServerId='" + ServerId + "'", null);

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}//End
