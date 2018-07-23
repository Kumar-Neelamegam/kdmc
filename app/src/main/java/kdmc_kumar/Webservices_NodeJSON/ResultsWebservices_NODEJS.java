package kdmc_kumar.Webservices_NodeJSON;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.util.Log;

import com.magnet.android.mms.MagnetMobileClient;
import com.magnet.android.mms.async.Call;
import com.magnet.android.mms.exception.SchemaException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNew;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.api.PosPatIdDocIdMtestIdNewFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.newPosPatIdDocIdMtestIdNew.beans.PosPatIdDocIdMtestIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorId;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.controller.api.PostDoctorIdFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.DoctorIdResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postDoctorId.model.beans.PostDoctorIdRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMax;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.controller.api.PostIsUpdateMaxFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.IsUpdateMaxResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postIsUpdateMax.model.beans.PostIsUpdateMaxRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestid;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.controller.api.PostPatidMtestidFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PatidMtestidResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatidMtestid.model.beans.PostPatidMtestidRequest;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdList;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.controller.api.PostPatientIdListFactory;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PatientIdListResult;
import kdmc_kumar.Webservices_NodeJSON.importREST_Services.postPatientIdList.model.beans.PostPatientIdListRequest;

import static kdmc_kumar.Webservices_NodeJSON.ImportWebservices_NODEJS.CheckNodeServer;

public class ResultsWebservices_NODEJS {


    private static Context ctx = null;

    public ResultsWebservices_NODEJS(final Context ctx) {
        super();
        this.ctx = ctx;
    }


    public final void ExecuteAll() {

        if (BaseConfig.CheckNetwork(this.ctx)) {

            try {

                if (CheckNodeServer()) {

                    Log.e("###########", "################");
                    Log.e("MobyDoctor BackGround", "Thread Import Result Service running (4)");
                    Log.e("MobyDoctor BackGround", "Thread Import Result Service running (4)");
                    Log.e("MobyDoctor BackGround", "Thread Import Result Service running (4)");
                    Log.e("###########", "################");


                    //Patid Mtestid

                    this.Import_ExaminationBlood_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationStool_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationUrine_Test(); // TODO: 1/24/2018 Completed
                    this.Import_ExaminationANC_Test();   // TODO: 1/24/2018 Completed
                    this.Import_ExaminationHIV_Test();   // TODO: 1/24/2018 Completed

                    //Docid Patid Mtest id list
                    ImportupdatedTestDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedScanDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedXrayDtls();  // TODO: 1/24/2018 Completed
                    ImportupdatedEEGDtls();   // TODO: 1/24/2018 Completed
                    ImportupdatedECGDtls();   // TODO: 1/24/2018 Completed
                    ImportupdatedAngiogram(); // TODO: 1/24/2018 Completed

                    this.Importmast_Upload();            // TODO: 1/24/2018 Completed

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
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

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");

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

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");


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

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");

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

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");

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

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");

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
                                        db.update("Angiogram", values, "Ptid='" + ptid + "' and IsActive='1' and mtestid='" + mtestid + '\'', null);

                                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + ptid + "' and mtestid='" + mtestid + "'");


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


    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    private void Importmast_Upload() {


        try {
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
        } catch (Exception e) {
            e.printStackTrace();
        }

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
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='true' or b.IsActive='True' or b.IsActive='1')";
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


                        ExaminationOfBlood(PatId, MtestId, MethodName, export_jsonarray, db);

                        //break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ExaminationOfBlood(String patId, String mtestId, String methodName, JSONArray export_jsonarray, SQLiteDatabase db) throws JSONException, SchemaException, InterruptedException, ExecutionException {
        final JSONObject parentData = new JSONObject();
        parentData.put("MtestIdList", export_jsonarray);

        //Sending data
        if ((parentData != null) && (parentData.length() > 0)) {


            final String resultsRequestSOAP = this.postPatidMtestId(patId, mtestId, methodName);


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

                        db.execSQL("update Medicaltest set IsResultAvailable=2, Result_Count=Result_Count+1 where Ptid='" + Patid + "' and mtestid='" + Mtestid + "'");




                    }


                }

            }

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
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='true' or b.IsActive='True' or b.IsActive='1')";
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


                        ExaminationStoolTest(PatId, MtestId, MethodName, export_jsonarray, db);



                        // break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();
            db.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExaminationStoolTest(String patId, String mtestId, String methodName, JSONArray export_jsonarray, SQLiteDatabase db) throws JSONException, SchemaException, InterruptedException, ExecutionException {
        final JSONObject parentData = new JSONObject();
        parentData.put("MtestIdList", export_jsonarray);

        //Sending data
        if ((parentData != null) && (parentData.length() > 0)) {


            final String resultsRequestSOAP = this.postPatidMtestId(patId, mtestId, methodName);


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

                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + Patid + "' and mtestid='" + Mtestid + "'");

                    }


                }

            }

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
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='true' or b.IsActive='True' or b.IsActive='1')";
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


                        ExaminationUrineTest(PatId, MtestId, MethodName, export_jsonarray, db);

                        // break;
                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExaminationUrineTest(String patId, String mtestId, String methodName, JSONArray export_jsonarray, SQLiteDatabase db) throws JSONException, SchemaException, InterruptedException, ExecutionException {
        final JSONObject parentData = new JSONObject();
        parentData.put("MtestIdList", export_jsonarray);

        //Sending data
        if ((parentData != null) && (parentData.length() > 0)) {

            final String resultsRequestSOAP = this.postPatidMtestId(patId, mtestId, methodName);


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

                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + Patid + "' and mtestid='" + Mtestid + "'");

                    }


                }

            }

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
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='true' or b.IsActive='True' or b.IsActive='1')";
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


                        ExaminationANCTest(PatId, MtestId, MethodName, export_jsonarray, db);
                       // break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void ExaminationANCTest(String patId, String mtestId, String methodName, JSONArray export_jsonarray, SQLiteDatabase db) throws JSONException, SchemaException, InterruptedException, ExecutionException {
        final JSONObject parentData = new JSONObject();
        parentData.put("MtestIdList", export_jsonarray);

        //Sending data
        if ((parentData != null) && (parentData.length() > 0)) {


            final String resultsRequestSOAP = this.postPatidMtestId(patId, mtestId, methodName);


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

                        db.execSQL("update Medicaltest set IsResultAvailable=2, Result_Count=Result_Count+1  where Ptid='" + Patid + "' and mtestid='" + Mtestid + "'");

                    }


                }

            }

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
            String s = "select b.Ptid,b.mtestid from Patreg a inner join Medicaltest b on a.Patid=b.Ptid where b.Isupdate='1' and (b.IsActive='true' or b.IsActive='True' or b.IsActive='1')";
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


                        ExaminationHIVTest(PatId, MtestId, MethodName, export_jsonarray, db);

                        //break;

                    }
                    while (c.moveToNext());

                }

            }
            Objects.requireNonNull(c).close();
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

    private void ExaminationHIVTest(String patId, String mtestId, String methodName, JSONArray export_jsonarray, SQLiteDatabase db) throws JSONException, SchemaException, InterruptedException, ExecutionException {
        final JSONObject parentData = new JSONObject();
        parentData.put("MtestIdList", export_jsonarray);

        //Sending data
        if ((parentData != null) && (parentData.length() > 0)) {


            final String resultsRequestSOAP = this.postPatidMtestId(patId, mtestId, methodName);


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

                        db.execSQL("update Medicaltest set IsResultAvailable=2 , Result_Count=Result_Count+1 where Ptid='" + Patid + "' and mtestid='" + Mtestid + "'");

                    }


                }

            }
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

            final Call<PosPatIdDocIdMtestIdResult> resultCall = posPatIdDocIdMtestIdNew.posPatIdDocIdMtestId(
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

        String UNIQUE_DIAGNOSIS_ID = BaseConfig.GenerateCaseNotesID();


        final String[] split_subtest;

        split_subtest = alltest.split("/");

        String compareRBSName = "blood sug random";
        if (split_subtest[1].trim().equalsIgnoreCase(compareRBSName)) {



            final ContentValues values = new ContentValues();
            values.put("Docid", BaseConfig.doctorId);
            values.put("Ptid", PatId);
            values.put("DiagId", UNIQUE_DIAGNOSIS_ID);
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
            values.put("DiagId", UNIQUE_DIAGNOSIS_ID);
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
            values.put("DiagId", UNIQUE_DIAGNOSIS_ID);
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
        final ImportWebservices_NODEJS.PatientData data = new ImportWebservices_NODEJS.PatientData();


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


    private void getBindUpload(final String patid, final String mtestid1) {


        try {
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
        } catch (Exception e) {
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


}//END