package kdmc_kumar.MyPatient_Module;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.CommonDataObjects.TestItem;
import kdmc_kumar.Adapters_GetterSetter.DateTimelineRowitemAdapter;
import kdmc_kumar.Adapters_GetterSetter.TestRecylerAdapter;
import kdmc_kumar.Adapters_GetterSetter.Timeline_Objects;
import kdmc_kumar.Core_Modules.BaseConfig;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_ANCTestReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_Blood_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_HIV_Report;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_StoolReport;
import kdmc_kumar.MyPatient_Module.ExtraReports.Profile_UrineReport;

/**
 * Created by Ponnusamy M on 3/31/2017.
 */

public class Profile_Investigation extends Fragment {


    @BindView(R.id.investigation_parent_layout) LinearLayout investigationParentLayout;
    @BindView(R.id.investigation_profile_patientid) TextView investigationProfilePatientid;
    @BindView(R.id.investigation_profile_recycler) RecyclerView investigationProfileRecycler;
    @BindView(R.id.investigation_profile_img_nodata) AppCompatImageView investigationProfileImgNodata;
    @BindView(R.id.investigation_profile_nodatatext) TextView investigationProfileNodatatext;
    @BindView(R.id.webvw_investigation_profile) WebView webvwInvestigationProfile;
    @BindView(R.id.investigation_reports_layout) LinearLayout investigationReportsLayout;
    @BindView(R.id.investigation_profile_reports) RecyclerView investigationProfileReports;


    private String Ecg = "";
    private String Rate = "-";
    private String Rhythm = "-";
    private String PRInterval = "-";
    private String QRS = "-";
    private String ST = "-";
    private String TWave = "-";
    private String AxisDeviation = "-";
    private String BundleBranch = "-";
    private String ConductionDefects = "-";
    private String TreadMill = "-";
    private String EEGFor = "-";
    private String EEGSummary = "-";
    private StringBuilder testtablerows = null;
    private StringBuilder testscantablerows = null;
    private StringBuilder testxrayrows = null;


    private ArrayList<TestItem> testItems = null;
    private TestRecylerAdapter testRecylerAdapter = null;
    private GridLayoutManager lLayout = null;
    private String BUNDLE_PATIENT_ID = null;

    // ///////////////////////////////////////////////////////////////////////////////////////////////
    //Investigations
    // ///////////////////////////////////////////////////////////////////////////////////////////////
    //Load Investigation
    private StringBuilder str1 = null;
    //Load ANGIOGRAM
    private String Coronary = "-";
    private String Brain = "-";
    private String Lower_limb = "-";
    private String Upper_limb = "-";
    private String Mesenteric = "-";
    private StringBuilder strTestDetails = new StringBuilder();
    /**
     * Static string values to get the subtest
     */
    private final String compareRBSName = "blood sug random";
    private final String compareFBSName = "blood sug fasting";
    private final String comparePBSName = "blood sug pp";
    private final String compareExamination = "All examination of blood";
    private final String compareStoolReport = "All stool test";
    private final String compareUrineReport = "All urine test";
    private final String compareAncfpReport = "All anc fp test";

    //Load SCAN
    //List<String> list1 = new ArrayList<String>();
    private final String compareHIVReport = "All hiv test";

    //Load TEST
    //List<String> list2 = new ArrayList<String>();
    //Load XRAY
    private StringBuilder strXrayDetails = new StringBuilder();
    private StringBuilder strScanDetails = new StringBuilder();
    private StringBuilder strAngiogramDetails = new StringBuilder();
    //Load ECG
    private StringBuilder strECGDetails = new StringBuilder();
    //Load EEG
    private StringBuilder strEEGDetails = new StringBuilder();
    private String XraySummary;

    public Profile_Investigation() {
    }

    @Nullable
    @Override
    public final View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.new_investigation_profile, container, false);


        try {
            GETINITIALIZATION(rootView);

            CONTROLLISTENERS(rootView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }




    private void GETINITIALIZATION(View rootView) {


        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);


        webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        webvwInvestigationProfile.setWebChromeClient(new WebChromeClient());


        lLayout = new GridLayoutManager(getActivity(), 2);
        investigationProfileReports.setHasFixedSize(true);
        investigationProfileReports.setLayoutManager(lLayout);
        investigationProfileReports.setNestedScrollingEnabled(false);


        String Patient_Name = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');

        investigationProfilePatientid.setText(String.format("%s - %s", Patient_Name, BUNDLE_PATIENT_ID));



        assert investigationProfileReports != null;

        setRecylerViewDetails(investigationProfileReports);


        investigationProfileRecycler.setHasFixedSize(true);
        investigationProfileRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        ArrayList<Timeline_Objects> datetime_arrayList = LoadDateTimeLine();


        if (datetime_arrayList.size() > 0) {
            investigationProfileImgNodata.setVisibility(View.GONE);
            investigationProfileNodatatext.setVisibility(View.GONE);
            webvwInvestigationProfile.setVisibility(View.VISIBLE);
            investigationProfileRecycler.setVisibility(View.VISIBLE);

        } else {
            investigationProfileRecycler.setVisibility(View.GONE);
            webvwInvestigationProfile.setVisibility(View.GONE);
            investigationProfileImgNodata.setVisibility(View.VISIBLE);
            investigationProfileNodatatext.setVisibility(View.VISIBLE);

        }



        adapter = new DateTimelineRowitemAdapter(getActivity(), datetime_arrayList);
        investigationProfileRecycler.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter



    }



    DateTimelineRowitemAdapter adapter;
    ArrayList<Timeline_Objects> datetime_arrayList;

    private ArrayList<Timeline_Objects> LoadDateTimeLine() {

        datetime_arrayList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();//);

        Cursor c = db.rawQuery("select   id,Actdate as date,mtestid from medicaltest where ptid='" + BUNDLE_PATIENT_ID+ "' order by id desc;", null);

        if (c.moveToFirst()) {
            do {

                String ID = c.getString(c.getColumnIndex("id"));
                String DATE = c.getString(c.getColumnIndex("date"));
                DATE = DATE.contains("/") ? DATE.replace("/","-") : DATE;
                String INVESTIGATIONID = c.getString(c.getColumnIndex("mtestid"));

                datetime_arrayList.add(new Timeline_Objects(ID, DATE, INVESTIGATIONID));

            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return datetime_arrayList;
    }


    private void CONTROLLISTENERS(View rootView) {

        adapter.setOnItemClickListener((view, position, items) -> {

            try {
                LoadInvestigationInfo(items.get(position).getUnique_Id());

                LoadECG(items.get(position).getUnique_Id());

                LoadEEG(items.get(position).getUnique_Id());

                LoadAngiogram(items.get(position).getUnique_Id());

                LoadScanDtls(items.get(position).getUnique_Id());

                LoadTestDtls(items.get(position).getUnique_Id());

                LoadXrayDtls(items.get(position).getUnique_Id());

                LoadWebview();

            } catch (Exception e) {
                e.printStackTrace();
            }

        });



    }

    private final void setRecylerViewDetails(RecyclerView re) {

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        testItems = new ArrayList<>();

        Cursor c = db.rawQuery("select * from Bind_Upload where Patid='" + BUNDLE_PATIENT_ID + '\'', null);
        try {
            if (c != null) {
                if (c.moveToFirst()) {
                    do {

                        String Fname = c.getString(c.getColumnIndex("Fname"));
                        String Type = c.getString(c.getColumnIndex("Type"));
                        String Cdtime = c.getString(c.getColumnIndex("Cdtime"));
                        String Upload_Base64 = c.getString(c.getColumnIndex("Upload_Base64"));
                        String Remark_Summary = c.getString(c.getColumnIndex("Remarks"));


                        testItems.add(new TestItem(Fname, Type, Cdtime, Upload_Base64, Remark_Summary));


                    } while (c.moveToNext());
                }
            }

            testRecylerAdapter = new TestRecylerAdapter(testItems);
            re.setAdapter(testRecylerAdapter);
            db.close();
            c.close();

            if (testRecylerAdapter.getItemCount() > 0) {
                investigationReportsLayout.setVisibility(View.VISIBLE);
            } else {
                investigationReportsLayout.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private final void LoadInvestigationInfo(String mtest) {

        str1 = new StringBuilder();
        StringBuilder values = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

        Cursor c = null;
        try {


            String Query = "select * from Medicaltest where mtestid='" + mtest.trim() + "' and Ptid='" + BUNDLE_PATIENT_ID + "'";

            c = db.rawQuery(Query, null);

            if (c != null) {
                if (c.moveToFirst()) {
                    do {


                        String Symptoms = checkNullEmpty(c.getString(c.getColumnIndex("treatmentfor")));
                        String ProvisionalDiagnosis = checkNullEmpty(c.getString(c.getColumnIndex("Diagnosis")));

                        str1.append("<div >\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr >\n" +
                                "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.symptoms)).append("</b></td> \n").append("<td height=\"20\" " +
                                "width=\"50%\" style=\"color:#000000;\">:   ").append(Symptoms).append("</td> \n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\"" +
                                "  width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.provisional_diagnosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ProvisionalDiagnosis).append("</td> \n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div>");


                    } while (c.moveToNext());
                }
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void LoadECG( String dateval2) {

        try {
            strECGDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from ECGTEST where Ptid='" + BUNDLE_PATIENT_ID + "' " +
                    "and mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Ecg,PR,ecgrate,ecgrhyrhm,ecgpulse,ecgqrs,ecgst,ecgt,ecgaxis,Bundlebranchblock,Conductiondefects,Treadmill from ECGTEST where Ptid='" + BUNDLE_PATIENT_ID + "' " +
                        "and mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            Ecg = checkNullEmpty(c.getString(c.getColumnIndex("Ecg")));
                            Rate = checkNullEmpty(c.getString(c.getColumnIndex("ecgrate")));
                            Rhythm = checkNullEmpty(c.getString(c.getColumnIndex("ecgrhyrhm")));
                            PRInterval = checkNullEmpty(c.getString(c.getColumnIndex("PR")));
                            QRS = checkNullEmpty(c.getString(c.getColumnIndex("ecgqrs")));
                            ST = checkNullEmpty(c.getString(c.getColumnIndex("ecgst")));
                            TWave = checkNullEmpty(c.getString(c.getColumnIndex("ecgt")));
                            AxisDeviation = checkNullEmpty(c.getString(c.getColumnIndex("ecgaxis")));
                            BundleBranch = checkNullEmpty(c.getString(c.getColumnIndex("Bundlebranchblock")));
                            ConductionDefects = checkNullEmpty(c.getString(c.getColumnIndex("Conductiondefects")));
                            TreadMill = checkNullEmpty(c.getString(c.getColumnIndex("Treadmill")));

                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();

                strECGDetails.append("<font class=\"sub\">  \n" + "<i class=\"fa fa-user-circle-o fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.ecg_diagnostic_summary)).append("</font>\n").append('\n').append("</br>\n").append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.ecg_summary_details)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Ecg).append("</td> \n").append("</tr>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.rate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Rate).append("</td> \n").append("</tr>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>QRS</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(QRS).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.axis_deviation)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(AxisDeviation).append("</td>\n").append("</tr> \n").append('\n').append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.treadmill)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(TreadMill).append("</td>\n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.rhythm)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Rhythm).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>ST</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(ST).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.bundle_branch)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(BundleBranch).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.pr_interval_secs_t_t)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(PRInterval).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>T wave:</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(TWave).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.conduction_defects)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(ConductionDefects).append("</td>\n").append("</tr>\n").append('\n').append("</tbody>\n").append("</table>\n");

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final void LoadEEG(String dateval2) {


        try {
            strEEGDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from EEG where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ");
            if (chk) {
                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Actdate,IFNULL(EEGFor,'-')as EEGFor,IFNULL(Comment,'-')as Comment,IFNULL(Summary,'-')as Summary,IFNULL(Valuedata,'-')as Valuedata from EEG where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {
                            if (c.getString(c.getColumnIndex("EEGFor")).equalsIgnoreCase("")) {

                                EEGFor = "-";
                                EEGSummary = "-";


                            } else {
                                EEGFor = (checkNullEmpty(c.getString(c.getColumnIndex("EEGFor"))));
                                EEGSummary = (checkNullEmpty(c.getString(c.getColumnIndex("Summary"))));

                            }

                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();


                strEEGDetails.append('\n' + "<font class=\"sub\">  \n" + "<i class=\"fa fa-user-circle-o fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.eeg_summary)).append("</font>\n").append('\n').append('\n').append('\n').append("<div class=\"table-responsive\">\n").append('\n').append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> ").append(getString(R.string.eeg_for)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\"> : ").append(EEGFor).append("</td> \n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b> ").append(getString(R.string.eeg_summary)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\"> : ").append(EEGSummary).append("</td> \n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div>\n").append('\n');
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final void LoadAngiogram(String dateval2) {
        try {
            strAngiogramDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Angiogram where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ");
            if (chk) {


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select Actdate,IFNULL(AngioFor,'')as AngioFor,Comment,IFNULL(Coronary,'')as Coronary,IFNULL(Brain,'')as Brain,IFNULL(Upperlimb,'')as Upperlimb,IFNULL(Lowerlimb,'')as Lowerlimb,IFNULL(Mesenteric,'')as Mesenteric from Angiogram where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "' ";
                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            Coronary = c.getString(c.getColumnIndex("Coronary"));
                            Brain = c.getString(c.getColumnIndex("Brain"));
                            Lower_limb = c.getString(c.getColumnIndex("Upperlimb"));
                            Upper_limb = c.getString(c.getColumnIndex("Lowerlimb"));
                            Mesenteric = c.getString(c.getColumnIndex("Mesenteric"));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();

                strAngiogramDetails.append("<font class=\"sub\"><i class=\"fa fa-calendar fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.angiogram)).append("</font>\n").append('\n').append('\n').append("<div class=\"table-responsive\">          \n").append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.coronary)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(Coronary).append("</td> \n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.brain)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Brain).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.lower_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Lower_limb).append("</td>\n").append("</tr> \n").append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.upper_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Upper_limb).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"><i class=\"fa\" aria-hidden=\"true\"></i><b>").append(getString(R.string.mesenteric)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">: ").append(Mesenteric).append("</td>\n").append("</tr>\n").append('\n').append("</table>\n").append("   \n").append("</tbody>\n").append("</div>\n");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private final void LoadScanDtls(String dateval2) {

        try {
            strScanDetails = new StringBuilder();
            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Scantest where Ptid='" + BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                StringBuilder scanDtlsStr = new StringBuilder();


                testscantablerows = new StringBuilder();


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                //ArrayList<Item> items = new ArrayList<Item>();

                String Query = "select Actdate,scanvalue,scanname,scansummary from Scantest where Ptid='" + BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "'";

                //Log.e("Get Scan Query: ", Query);
                //Log.e("Get Scan Query: ", Query);

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String value[] = c.getString(c.getColumnIndex("scanname")).split("/");


                            testscantablerows.append("  <tr>\n" + "    <td>").append(value[0]).append("</td>\n").append("    <td>").append(value[1]).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("scanvalue")))).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("scansummary")))).append("</td>\n").append("  </tr>\n");


                        } while (c.moveToNext());
                    }
                }
                c.close();
                db.close();


                //ScanSummary = scanDtlsStr.toString();

                strScanDetails.append('\n' + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.scan)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.scan_name)).append("</font></th>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.scan_for)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.resultvalue)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.remarks)).append("</font></th>\n").append('\n').append("  </tr>\n").append(testscantablerows);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    //#######################################################################################################

    private final void LoadTestDtls(String dateval2) {

        strTestDetails = new StringBuilder();
        boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Medicaltestdtls where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "';");
        if (chk) {

            StringBuilder TestDtlsStr = new StringBuilder();
            try {


                testtablerows = new StringBuilder();


                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                String Query = "select * from Medicaltestdtls where Ptid='" + BUNDLE_PATIENT_ID + "' and mtestid='" + dateval2.trim() + "';";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String allTest = checkNullEmpty(c.getString(c.getColumnIndex("alltest")));

                            String[] values = allTest.split("/");


                            if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareRBSName)) {
                                testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("temperature")))).append("</td>\n").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");

                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareFBSName)) {
                                testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append(
                                        // "\t<td onClick=\"showAndroidToast('"+checkNullEmpty(c.getString(c.getColumnIndex("bps"))).toString()+"')\">"+checkNullEmpty(c.getString(c.getColumnIndex("bps"))).toString()+"</td>\n" +
                                        "\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("bps")))).append("</td>\n").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");
                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(comparePBSName)) {
                                testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("bpd")))).append("</td>\n").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");
                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareExamination)) //EXAMINATION OF BLOOD - All examination of blood
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_examination_blood_test where Patid='" + BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast('").append(checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareStoolReport)) //STOOL REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_stool_report where Patid='" + BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast1('").append(checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareUrineReport)) //URINE REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_urine_test where Patid='" + BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast2('").append(checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareAncfpReport)) //ANC FP REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_anc_fp_test where Patid='" + BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("").append("\t<td> - </td>\n").append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast3('").append(checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append("").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else if (checkNull(c.getString(c.getColumnIndex("subtestname"))).equalsIgnoreCase(compareHIVReport)) //HIV REPORT
                            {
                                String QueryCheck = "select Id as dstatus1 from Bind_HIV_Report where Patid='" + BUNDLE_PATIENT_ID + "' and Mtestid='" + dateval2.trim() + '\'';

                                boolean q = BaseConfig.LoadReportsBooleanStatus(QueryCheck);

                                if (!q) {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td> - </td>\n").append("\t<td> - </td>\n").append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                } else {
                                    testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td onClick=\"showAndroidToast4('").append(checkNullEmpty(c.getString(c.getColumnIndex("Ptid")))).append("','").append(checkNullEmpty(c.getString(c.getColumnIndex("mtestid")))).append('\'').append(")\"> More </td>\n").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("</tr>\n");
                                }


                            } else//other tests
                            {
                                testtablerows.append("  <tr>\n" + "    <td>").append(values[0]).append("</td>\n").append("    <td>").append(values[1]).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testvalue")))).append("</td>\n").append(GetResultValue(checkNullEmpty(c.getString(c.getColumnIndex("result"))))).append("\t<td> ").append(GetUnit(values[0], values[1])).append(" </td>\n").append("\t<td> ").append(GetNormalRange(values[0], values[1])).append(" </td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("testsummary")))).append("</td>\n").append("  </tr>\n");

                            }

                            String testDetails = getString(R.string.test_name) + String.valueOf("<b>" + allTest + "</b>" + " ,  " + getString(R.string.test_values) + ' ' + "<b>" + checkNullEmpty(c.getString(c.getColumnIndex("testvalue"))) + "</b>" + " , Test Remarks :<b> " + BaseConfig.CheckDBString(checkNullEmpty(c.getString(c.getColumnIndex("testsummary"))) + "</b>"));

                            //  TestDtlsStr.append(String.valueOf("<br><b>" + testDetails + "</b></br>"));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


            // TestSummary = TestDtlsStr.toString();

            strTestDetails.append("<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.test)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_name)).append("</font></th>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.subtest_name)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_values)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_result)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_units)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_normalrange)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.test_remark)).append("</font></th>\n").append('\n').append("  </tr>\n").append(testtablerows).append('\n').append("</table>\n").append('\n').append('\n').append("</div>\n");
        }

    }

    private final void LoadXrayDtls(String dateval2) {

        try {
            strXrayDetails = new StringBuilder();

            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from XRAY where Ptid='" + BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ");
            if (chk) {

                StringBuilder stringBuilderXray = new StringBuilder();


                testxrayrows = new StringBuilder();

                SQLiteDatabase db = BaseConfig.GetDb();//getActivity());

                db.isOpen();

                String Query = "select Actdate,IFNULL(xray,'')as xray,IFNULL(xrayvalue,'')as xrayvalue,IFNULL(xraysummary,'')as xraysummary  from XRAY where Ptid='" + BUNDLE_PATIENT_ID + "' and  mtestid='" + dateval2.trim() + "' ";

                Cursor c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            testxrayrows.append("  <tr>\n" + "    <td>").append(checkNullEmpty(c.getString(c.getColumnIndex("xray")))).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("xrayvalue")).split("-")[1])).append("</td>\n").append("\t<td>").append(checkNullEmpty(c.getString(c.getColumnIndex("xrayvalue")).split("-")[0])).append("</td>\n").append("  </tr>\n");

                            XraySummary = (": " + checkNullEmpty(c.getString(c.getColumnIndex("xraysummary"))));


                        } while (c.moveToNext());
                    }
                }

                db.close();
                c.close();


                strXrayDetails.append("</table>\n" + "</div>\n" + "<font class=\"sub\"><i class=\"fa fa-address-card-o fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.x_ray)).append("</font>\n").append('\n').append("<div class=\"table-responsive\">          \n").append("<table class=\"table table-bordered\">\n").append("  <tr>\n").append("    <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.xray_for)).append("</font></th>\n").append("\t <th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.resultvalue)).append("</font></th>\n").append("\t<th bgcolor=\"#3d5987\"><font color=\"#fff\">").append(getString(R.string.remarks)).append("</font></th>\n").append('\n').append("  </tr>\n").append(testxrayrows).append('\n').append("</table>\n").append("</div>\n").append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //#######################################################################################################

    private static final String GetResultValue(String chkstr) {
        String str = "";

        if (chkstr.equalsIgnoreCase("Negative")) {
            str = "\t<td style=\"color:#F00;\"> Negative </td>\n";
        } else if (chkstr.equalsIgnoreCase("Positive")) {
            str = "\t<td style=\"color:#0F0;\"> Positive </td>\n";
        }

        return str;
    }

    private static String GetNormalRange(String value, String value1) {
        String str = "";
        String str2 = BaseConfig.GetValues("select NormalRange as ret_values from Testname where Testname='" + value + "' and SubTest='" + value1 + '\'');
        return str2;
    }

    private static String GetUnit(String value, String value1) {
        String str = "";
        String str2 = BaseConfig.GetValues("select Unit as ret_values from Testname where Testname='" + value + "' and SubTest='" + value1 + '\'');
        return str2;
    }

    private static final String checkNull(String val) {
        String returns_Val = "";
        try {


            return val.equalsIgnoreCase("null") || val.equalsIgnoreCase(null) || val.equalsIgnoreCase("") ? " " : val;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return returns_Val;
    }

    private static final String checkNullEmpty(String val) {
        return val == null || val.isEmpty() || val.equalsIgnoreCase("null") ? " - " : val;
    }

    //#######################################################################################################
    private final void LoadWebview() {


        webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        webvwInvestigationProfile.setLayerType(View.LAYER_TYPE_NONE, null);
        webvwInvestigationProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webvwInvestigationProfile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webvwInvestigationProfile.getSettings().setDefaultTextEncodingName("utf-8");
        webvwInvestigationProfile.setWebChromeClient(new MyWebChromeClient());
        webvwInvestigationProfile.setBackgroundColor(0x00000000);
        webvwInvestigationProfile.setVerticalScrollBarEnabled(true);
        webvwInvestigationProfile.setHorizontalScrollBarEnabled(true);
        webvwInvestigationProfile.getSettings().setJavaScriptEnabled(true);
        webvwInvestigationProfile.getSettings().setAllowContentAccess(true);
        webvwInvestigationProfile.setOnLongClickListener(v -> true);
        webvwInvestigationProfile.setLongClickable(false);

        webvwInvestigationProfile.addJavascriptInterface(new WebAppInterface(getActivity()), "android");
        try {

            webvwInvestigationProfile.loadDataWithBaseURL("file:///android_asset/", LoadInvetigationData(), "text/html", "utf-8", null);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    //#######################################################################################################

    private final String LoadInvetigationData() {

        String values;


        values = "<!DOCTYPE html>\n" +
                '\n' +
                "<html lang=\"en\">\n" +
                "<head>\n" +

                "<script type=\"text/javascript\">\n" +

                "    function showAndroidToast(toast,toast1) {\n" +
                "        android.showToast(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast1(toast,toast1) {\n" +
                "        android.showToast1(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast2(toast,toast1) {\n" +
                "        android.showToast2(toast,toast1);\n" +
                "    }\n" +

                "    function showAndroidToast3(toast,toast1) {\n" +
                "        android.showToast3(toast,toast1);\n" +
                "    }\n" +


                "    function showAndroidToast4(toast,toast1) {\n" +
                "        android.showToast4(toast,toast1);\n" +
                "    }\n" +


                "</script>\n" +

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
                "</head>\n" +
                "<body>  \n" +
                " \n" +
                '\n' + "<font class=\"sub\">  \n" +
                '\n' +
                "<i class=\"fa fa-stethoscope fa-2x\" aria-hidden=\"true\"></i> " + getString(R.string.diagnosis_details) + "</font>\n" +
                '\n' +
                str1 +


                strTestDetails +
                "</br>\n" +

                strScanDetails +
                "</br>\n" +

                strXrayDetails +
                "</br>\n" +

                strECGDetails +
                '\n' +
                "</br>\n" +


                strEEGDetails +
                "</br>\n" +

                strAngiogramDetails +
                "<br/>\n" +
                "<!----------------------------------------------------------------->\n" +
                '\n' +
                "<!----------------------------------------------------------------->\n" +
                "</body>\n" +
                "</html>                                      ";


        return values;
    }

    private static class MyWebChromeClient extends WebChromeClient {
    }


    class WebAppInterface {
        final Context mContext;


        WebAppInterface(Context c) {
            mContext = c;
        }


        @JavascriptInterface
        public final void showToast(String Ptid, String Mtestid) {//Blood report

            startActivity(new Intent(mContext, Profile_Blood_Report.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast1(String Ptid, String Mtestid) {//Stool report

            startActivity(new Intent(mContext, Profile_StoolReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast2(String Ptid, String Mtestid) {//Urine report

            startActivity(new Intent(mContext, Profile_UrineReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast3(String Ptid, String Mtestid) {//ANCFP report

            startActivity(new Intent(mContext, Profile_ANCTestReport.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

        @JavascriptInterface
        public final void showToast4(String Ptid, String Mtestid) {//HIV

            startActivity(new Intent(mContext, Profile_HIV_Report.class).putExtra("PTID", Ptid).putExtra("MTESTID", Mtestid));

        }

    }


}//END
