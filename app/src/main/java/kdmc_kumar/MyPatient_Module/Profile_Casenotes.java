package kdmc_kumar.MyPatient_Module;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import displ.mobydocmarathi.com.R;
import kdmc_kumar.Adapters_GetterSetter.DateTimelineRowitemAdapter;
import kdmc_kumar.Adapters_GetterSetter.Timeline_Objects;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Bar_Weight;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodPressure;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_BloodSugar;
import kdmc_kumar.CaseNotes_Charts_new.Chart_Line_Weight;
import kdmc_kumar.Core_Modules.BaseConfig;

/**
 * Created by Android on 3/31/2017.
 */

public class Profile_Casenotes extends Fragment {


    /**
     * Muthukumar N
     * 10-5-2018
     * ENHANCEMENT V2
     */
    //**********************************************************************************************
    //Declaration

    private final String[] orderabc = new String[]{"A", "B", "C", "D", "E"};
    private final String[] reorderabc = new String[]{"E", "D", "C", "B", "A"};

    @BindView(R.id.casenotes_profile_parent_layout)
    LinearLayout casenotesProfileParentLayout;
    @BindView(R.id.casenotes_profile_patientid)
    TextView casenotesProfilePatientid;
    @BindView(R.id.casenotes_profile_recycler)
    RecyclerView casenotesProfileRecycler;
    @BindView(R.id.casenotes_profile_img_nodata)
    AppCompatImageView imgNodata;
    @BindView(R.id.casenotes_profile_nodatatext)
    TextView casenotesNodatatext;
    @BindView(R.id.casenotes_profile_webvw_profile)
    WebView casenotesProfileWebvwProfile;
    @BindView(R.id.imgvw_bloodsugar_bar)
    AppCompatImageView imgvwBloodsugarBar;
    @BindView(R.id.imgvw_bloodsugar_line)
    AppCompatImageView imgvwBloodsugarLine;
    @BindView(R.id.imgvw_bloodpressure_bar)
    AppCompatImageView imgvwBloodpressureBar;
    @BindView(R.id.imgvw_bloodpressure_line)
    AppCompatImageView imgvwBloodpressureLine;
    @BindView(R.id.imgvw_weight_bar)
    AppCompatImageView imgvwWeightBar;
    @BindView(R.id.imgvw_weight_line)
    AppCompatImageView imgvwWeightLine;
    private String BUNDLE_PATIENT_ID = null;
    private StringBuilder str1 = null;
    private StringBuilder str2 = null;
    private StringBuilder str3 = null;
    private StringBuilder str4 = null;
    private StringBuilder str5 = null;
    private StringBuilder str6 = null;
    private StringBuilder str7 = null;
    private StringBuilder str8 = null;
    private StringBuilder str9 = null;
    private StringBuilder str10 = null;
    private StringBuilder str11 = null;
    private StringBuilder str12 = null;
    private StringBuilder str13 = null;
    private StringBuilder str14 = null;
    private String PATIENT_NAME = "";
    private String PATIENT_AGEGENDER = "";


    DateTimelineRowitemAdapter adapter;
    ArrayList<Timeline_Objects> datetime_arrayList;



    public Profile_Casenotes() {
    }

    //**********************************************************************************************
    public final View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.new_casenotes_profile, container, false);

        try {

            GETINITIALIZE(rootView);

            CONTROLLISTENERS(rootView);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private final void GETINITIALIZE(View rootView) {


        ButterKnife.bind(this, rootView);

        Bundle args = getArguments();
        BUNDLE_PATIENT_ID = args.getString(BaseConfig.BUNDLE_PATIENT_ID);

        casenotesProfileWebvwProfile.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        casenotesProfileWebvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        casenotesProfileWebvwProfile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);

        casenotesProfileWebvwProfile.setOnLongClickListener(v -> true);

        casenotesProfileWebvwProfile.setLongClickable(false);

        String Query = "select name||' - '|| Patid as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'';
        casenotesProfilePatientid.setText(BaseConfig.GetValues(Query));
        PATIENT_NAME = BaseConfig.GetValues("select name as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
        PATIENT_AGEGENDER = BaseConfig.GetValues("select age||' - '|| gender as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');




        ArrayList<Timeline_Objects> datetime_arrayList = LoadDateTimeLine();

        casenotesProfileRecycler.setHasFixedSize(true);
        casenotesProfileRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        if (datetime_arrayList.size() > 0) {

            imgNodata.setVisibility(View.GONE);
            casenotesNodatatext.setVisibility(View.GONE);
            casenotesProfileWebvwProfile.setVisibility(View.VISIBLE);
            casenotesProfileRecycler.setVisibility(View.VISIBLE);
        } else {

            imgNodata.setVisibility(View.VISIBLE);
            casenotesNodatatext.setVisibility(View.VISIBLE);
            casenotesProfileWebvwProfile.setVisibility(View.GONE);
            casenotesProfileRecycler.setVisibility(View.GONE);
        }

        adapter = new DateTimelineRowitemAdapter(getActivity(), datetime_arrayList);
        casenotesProfileRecycler.setAdapter(adapter);// set adapter on recyclerview
        adapter.notifyDataSetChanged();// Notify the adapter


    }




    private ArrayList<Timeline_Objects> LoadDateTimeLine() {

        datetime_arrayList = new ArrayList<>();
        SQLiteDatabase db = BaseConfig.GetDb();//);

        String Query = "select id,Actdate as date,DiagId as DID from Diagonis where ptid='" + BUNDLE_PATIENT_ID + "' order by id desc;";

        Cursor c = db.rawQuery(Query, null);

        if (c.moveToFirst()) {
            do {

                String ID = c.getString(c.getColumnIndex("id"));
                String DATE = c.getString(c.getColumnIndex("date"));
                DATE = DATE.contains("/") ? DATE.replace("/","-") : DATE;
                String DIAGID = c.getString(c.getColumnIndex("DID"));

                datetime_arrayList.add(new Timeline_Objects(ID, DATE, DIAGID));

            } while (c.moveToNext());
        }
        c.close();
        db.close();

        return datetime_arrayList;
    }


    //**********************************************************************************************

    private final void CONTROLLISTENERS(View rootView) {

        //casenotesProfileWebvwProfile.setOnTouchListener();

        //Passing DID and Date
        adapter.setOnItemClickListener((view, position, items) -> LoadWebview(items.get(position).getUnique_Id(), items.get(position).getVisitedDate()));

        imgvwBloodsugarBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Bar_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getResources().getString(R.string.information), getString(R.string.no_data), getString(R.string.ok));
                }


            }
        });

        imgvwBloodsugarLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Line_BloodSugar.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    Profile_Casenotes.this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getContext().getResources().getString(R.string.information), Profile_Casenotes.this.getString(R.string.no_data), Profile_Casenotes.this.getString(R.string.ok));
                }


            }
        });

        imgvwBloodpressureBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseConfig.temp_flag = "true";
                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Bar_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    Profile_Casenotes.this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getContext().getResources().getString(R.string.information), Profile_Casenotes.this.getString(R.string.no_data), Profile_Casenotes.this.getString(R.string.ok));
                }


            }
        });

        imgvwBloodpressureLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Line_BloodPressure.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    Profile_Casenotes.this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getContext().getResources().getString(R.string.information), Profile_Casenotes.this.getString(R.string.no_data), Profile_Casenotes.this.getString(R.string.ok));
                }


            }
        });


        imgvwWeightBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Bar_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    Profile_Casenotes.this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getContext().getResources().getString(R.string.information), Profile_Casenotes.this.getString(R.string.no_data), Profile_Casenotes.this.getString(R.string.ok));
                }


            }
        });


        imgvwWeightLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean status = BaseConfig.LoadReportsBooleanStatus("select id as dstatus1 from Diagonis where Ptid='" + BUNDLE_PATIENT_ID + "' order by id desc");
                if (status) {
                    Intent intent = new Intent(rootView.getContext(), Chart_Line_Weight.class);
                    intent.putExtra("ID", "1");// To load blood sugar value
                    intent.putExtra(BaseConfig.BUNDLE_PATIENT_ID, BUNDLE_PATIENT_ID);
                    intent.putExtra("PATIENT_NAME", PATIENT_NAME);
                    intent.putExtra("PATIENT_AGEGENDER", PATIENT_AGEGENDER);
                    Profile_Casenotes.this.startActivity(intent);
                } else {
                    BaseConfig.KDMC_COMMON_DILOAGS(4, rootView.getContext(), rootView.getContext().getResources().getString(R.string.information), Profile_Casenotes.this.getString(R.string.no_data), Profile_Casenotes.this.getString(R.string.ok));
                }


            }
        });

    }

    private final void LoadWebview(String str1, String str2) {

        casenotesProfileWebvwProfile.getSettings().setJavaScriptEnabled(true);
        casenotesProfileWebvwProfile.setLayerType(View.LAYER_TYPE_NONE, null);
        casenotesProfileWebvwProfile.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        casenotesProfileWebvwProfile.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        casenotesProfileWebvwProfile.getSettings().setDefaultTextEncodingName("utf-8");

        casenotesProfileWebvwProfile.setWebChromeClient(new MyWebChromeClient());

        casenotesProfileWebvwProfile.setBackgroundColor(0x00000000);
        casenotesProfileWebvwProfile.setVerticalScrollBarEnabled(true);
        casenotesProfileWebvwProfile.setHorizontalScrollBarEnabled(true);

        Toast.makeText(getActivity(), "Please wait casenotes loading..", Toast.LENGTH_SHORT).show();

        casenotesProfileWebvwProfile.getSettings().setJavaScriptEnabled(true);

        casenotesProfileWebvwProfile.getSettings().setAllowContentAccess(true);

        casenotesProfileWebvwProfile.addJavascriptInterface(new WebAppInterface(getActivity()), "android");
        try {

            casenotesProfileWebvwProfile.loadDataWithBaseURL("file:///android_asset/", LoadDiagnosisDetails(str1, str2).toString(), "text/html", "utf-8", null);

        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    //**********************************************************************************************

    private final StringBuilder LoadDiagnosisDetails(String value1, String value2) {

        str1 = new StringBuilder();

        StringBuilder values = new StringBuilder();

        SQLiteDatabase db = BaseConfig.GetDb();//getActivity());
        String ProvisionalDiagnosis = "";
        Cursor c = null;
        try {

            String Query = "select * from Diagonis where DiagId='" + value1.trim() + "' and Ptid='" + BUNDLE_PATIENT_ID + "';";

            boolean chk = BaseConfig.LoadReportsBooleanStatus("select Id as dstatus1 from Diagonis where DiagId='" + value1.trim() + "' and Ptid='" + BUNDLE_PATIENT_ID + "';");
            if (chk) {
                c = db.rawQuery(Query, null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Symptoms = checkNullEmpty(c.getString(c.getColumnIndex("treatmentfor")));
                            ProvisionalDiagnosis = checkNullEmpty(c.getString(c.getColumnIndex("Diagnosis")));
                            String Signs = checkNullEmpty(c.getString(c.getColumnIndex("Signs")));
                            String Weight_kg = checkNullEmpty(c.getString(c.getColumnIndex("PWeight")));
                            String Height = checkNullEmpty(c.getString(c.getColumnIndex("height")));
                            String BMI = checkNullEmpty(c.getString(c.getColumnIndex("bmi")));
                            String temperature = checkNullEmpty(c.getString(c.getColumnIndex("temperature")));
                            String bloodPressure = checkNullEmpty(c.getString(c.getColumnIndex("BpS"))) + '/' + (checkNullEmpty(c.getString(c.getColumnIndex("BpD"))));

                            String FBS = checkNullEmpty(c.getString(c.getColumnIndex("FBS")));
                            String PPS = checkNullEmpty(c.getString(c.getColumnIndex("PPS")));
                            String RBS = checkNullEmpty(c.getString(c.getColumnIndex("RBS")));


                            str1.append("<div >\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr >\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.symptoms)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Symptoms).append("</td> \n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\"  width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.provisional_diagnosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ProvisionalDiagnosis).append("</td></t > \n")

                                    .append("<tr ><td height=\"20\"  width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.signs)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Signs).
                                    append("</td> \n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.weight_kg)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Weight_kg).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.height_cms)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Height).append("</td>\n").append("</tr> \n").append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  BMI</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(BMI).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.temperature_f)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(temperature).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.blood_pressure)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(bloodPressure).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  FBS</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(FBS).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  PPBS</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PPS).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  RBS</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(RBS).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append("</div>");


                        } while (c.moveToNext());
                    }
                }
                c.close();
            }


        } catch (RuntimeException e) {
            e.printStackTrace();
        }


        //##################### //#####################
        //1. General Examination
        str2 = new StringBuilder();
        boolean chk = BaseConfig.LoadReportsBooleanStatus("select skin_infection as dstatus1 from caseNote_GeneralExamination where DiagId='" + value1.trim() + "' and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk) {

            str2.append("<font class=\"sub\">  \n" + '\n' + "<i class=\"fa fa-stethoscope fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.general_examination)).append("</font>\n");

            try {


                c = db.rawQuery("select skin_infection,short_stat,pog,pallor,oedema,fundalheight,lie,fetalmovement,fetalheight,pv,anycompl,Anaemic,Icterus,Stenosis,Clubbing,Limbphantom,Vericoveins,Pedal_edema,built,extra_generalexam from CaseNote_GeneralExamination where DiagId='" + value1.trim() + "' and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Anaemic = checkNullEmpty(c.getString(c.getColumnIndex("Anaemic")));
                            String Lcetrus = checkNullEmpty(c.getString(c.getColumnIndex("Icterus")));
                            String Cyanosis = checkNullEmpty(c.getString(c.getColumnIndex("Stenosis")));
                            String Clubbing = checkNullEmpty(c.getString(c.getColumnIndex("Clubbing")));
                            String Lymphadenopathy = checkNullEmpty(c.getString(c.getColumnIndex("Limbphantom")));
                            String VaricoseVeins = checkNullEmpty(c.getString(c.getColumnIndex("Vericoveins")));
                            String SkinInfection = checkNullEmpty(c.getString(c.getColumnIndex("skin_infection")));
                            String ShortStatured = checkNullEmpty(c.getString(c.getColumnIndex("short_stat")));
                            String Pedaledema = checkNullEmpty(c.getString(c.getColumnIndex("Pedal_edema")));
                            String Built = checkNullEmpty(c.getString(c.getColumnIndex("built")));

                            String OtherSystem = checkNullEmpty(c.getString(c.getColumnIndex("extra_generalexam")));
                            String POG = checkNullEmpty(c.getString(c.getColumnIndex("pog")));
                            String Pallor = checkNullEmpty(c.getString(c.getColumnIndex("pallor")));
                            String Oedema = checkNullEmpty(c.getString(c.getColumnIndex("oedema")));
                            String Fundal_height = checkNullEmpty(c.getString(c.getColumnIndex("fundalheight")));
                            String LiePresentation = checkNullEmpty(c.getString(c.getColumnIndex("lie")));
                            String Fetal_movements = checkNullEmpty(c.getString(c.getColumnIndex("fetalmovement")));
                            String Fetal_heart = checkNullEmpty(c.getString(c.getColumnIndex("fetalheight")));
                            String PV = checkNullEmpty(c.getString(c.getColumnIndex("pv")));
                            String any_complaints = checkNullEmpty(c.getString(c.getColumnIndex("anycompl")));


                            str2.append("<div >\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr >\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.anaemic)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Anaemic).append("</td> \n").append("</tr>\n").append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.icterus)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Lcetrus).append("</td> \n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.cyanosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Cyanosis).append("</td>\n").append("</tr>\n").append('\n').append(" <tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.clubbing)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Clubbing).append("</td>\n").append("</tr> \n").append('\n').append("<tr > \n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.lymphadenopathy)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:  ").append(Lymphadenopathy).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.varicose_veins)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(VaricoseVeins).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string._pedal_edema)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Pedaledema).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append("<tr >\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Skin Infection</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(SkinInfection).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Short Statured</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ShortStatured).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.built)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Built).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.others)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(OtherSystem).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  POG(Weeks)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(POG).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Pallor</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Pallor).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Oedema</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Oedema).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Fundal height(Weeks/cm)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Fundal_height).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Lie/Presentation</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(LiePresentation).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Fetal movements</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Fetal_movements).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Fetal heart rate (Per minute)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Fetal_heart).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  P/V if done</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PV).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Any complaints</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(any_complaints).append("</td>\n").append("</tr>").append("</tbody>\n").append("</table>\n").append("</div>");


                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //Set table values here - 2


        //##################### //#####################
        //2.Loadcardiovascular
        str3 = new StringBuilder();
        boolean chk12 = BaseConfig.LoadReportsBooleanStatus("select Beat as dstatus1 from caseNote_Cardiovascular where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk12) {

            str3.append("<font class=\"sub\"><i class=\"fa fa-heartbeat fa-2x\" aria-hidden=\"true\"></i>  ").append(getString(R.string.cardio_vascular_system)).append("</font>\n");

            try {
                c = db.rawQuery("select Beat,Murmur,Pericardial_Rub,Pulserate,heartrate from caseNote_Cardiovascular where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Beat = checkNullEmpty(c.getString(c.getColumnIndex("Beat")));
                            String Murmur = checkNullEmpty(c.getString(c.getColumnIndex("Murmur")));
                            String PeriodicalRub = checkNullEmpty(c.getString(c.getColumnIndex("Pericardial_Rub")));
                            String PulseRate = checkNullEmpty(c.getString(c.getColumnIndex("Pulserate")));
                            String HeartRate = checkNullEmpty(c.getString(c.getColumnIndex("heartrate")));

                            str3.append("<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.beat)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Beat).append("</td> \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.murmur)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Murmur).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.pericardial_rub)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PeriodicalRub).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.pulse_rate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PulseRate).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.heart_rate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(HeartRate).append("</td>\n").append("</tr>\n").append('\n').append("  \n").append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div>");


                        } while (c.moveToNext());
                    }
                }
                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        //set values


        //##################### //#####################
        //3.Loadrespiratorysystem
        str4 = new StringBuilder();
        boolean chk11 = BaseConfig.LoadReportsBooleanStatus("select Breathsound as dstatus1 from caseNote_RespiratorySystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk11) {

            str4.append("<font class=\"sub\">  ").append(getString(R.string.respiratory_system)).append("</font>\n");

            try {
                c = db.rawQuery("select Breathsound,Trachea,Kyphosis_Scoliosis,Crepitation,Bronchi,Pulserate,Note,shapeofchest,spo2,BreathSoundEqual,BreathSoundEqualOptions from caseNote_RespiratorySystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String BreathSound = checkNullEmpty(c.getString(c.getColumnIndex("Breathsound")));
                            String Trachae = checkNullEmpty(c.getString(c.getColumnIndex("Trachea")));
                            ///stringGetter.  checkNullEmpty(c.getString(c.getColumnIndex("Kyphosis_Scoliosis")));
                            String Crepitation = checkNullEmpty(c.getString(c.getColumnIndex("Crepitation")));

                            String Kyphosis_Scoliosis = checkNullEmpty(c.getString(c.getColumnIndex("Kyphosis_Scoliosis")));

                            //bronchi.setText(checkNullEmpty(c.getString(c.getColumnIndex("Bronchi")));
                            //pulsre.setText(checkNullEmpty(c.getString(c.getColumnIndex("Pulserate")));
                            String Bronchi = checkNullEmpty(c.getString(c.getColumnIndex("Bronchi")));
                            String PulseRate = checkNullEmpty(c.getString(c.getColumnIndex("Pulserate")));
                            String RespiratoryRate = checkNullEmpty(c.getString(c.getColumnIndex("Note")));
                            String ShapeofChest = checkNullEmpty(c.getString(c.getColumnIndex("shapeofchest")));
                            String SPO2 = checkNullEmpty(c.getString(c.getColumnIndex("spo2")));
                            String BreathSoundEqual = c.getString(c.getColumnIndex("BreathSoundEqual"));
                            String BreathSoundEqualOptions = c.getString(c.getColumnIndex("BreathSoundEqualOptions"));


                            str4.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.breath_sound_equal)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(BreathSoundEqual).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.breath_sound_options)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(BreathSoundEqualOptions)

                                    .append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.breath_sound_txt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(BreathSound).append("</td>\n").append("</tr>\n").append('\n')

                                    .append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.trachea)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Trachae).append("</td>\n").append("</tr>\n").append('\n').append(

                                    // TODO: 6/19/2017 new Field Adding
                                    '\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Kyphosis,Scoliosis</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Kyphosis_Scoliosis).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.crepitation)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Crepitation).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.rhonchi)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Bronchi).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.shape_of_the_chest)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ShapeofChest).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.respiratory_rate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(RespiratoryRate).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  SPO2</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(SPO2).append("</td>\n").append("</tr>\n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div>\n").append('\n');


                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //set values


        //##################### //#####################
        //4.Loadgastrointestinalsystem
        str5 = new StringBuilder();
        boolean chk10 = BaseConfig.LoadReportsBooleanStatus("select Abdomen as dstatus1 from caseNote_Gastrointestinal where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk10) {

            str5.append("<font class=\"sub\">  ").append(getString(R.string.gastrointestinal_system)).append("</font>\n");

            try {
                c = db.rawQuery("select Abdomen,Bowelsound,Spleen,Liver,PalpableMasses,Hernial,shapeofabdomen,Visible_Pulsations,Visual_Peristalsis,Abdominal_Palpation,Splenomegaly,Hepatomegaly,organomegely,freefuild,distension,tenderness,scrotum from caseNote_Gastrointestinal where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Abdomen = checkNullEmpty(c.getString(c.getColumnIndex("Abdomen")));
                            String BowelSound = checkNullEmpty(c.getString(c.getColumnIndex("Bowelsound")));
                            String Spleen = checkNullEmpty(c.getString(c.getColumnIndex("Spleen")));
                            String Liver = checkNullEmpty(c.getString(c.getColumnIndex("Liver")));
                            String PalpableMasses = checkNullEmpty(c.getString(c.getColumnIndex("PalpableMasses")));
                            String Hernial = checkNullEmpty(c.getString(c.getColumnIndex("Hernial")));
                            String ShapeOfAbdomen = checkNullEmpty(c.getString(c.getColumnIndex("shapeofabdomen")));
                            String VisiblePulsations = checkNullEmpty(c.getString(c.getColumnIndex("Visible_Pulsations")));
                            String VisiblePeristalsis = checkNullEmpty(c.getString(c.getColumnIndex("Visual_Peristalsis")));
                            String AbdominalPalpation = checkNullEmpty(c.getString(c.getColumnIndex("Abdominal_Palpation")));
                            String Splenomegaly = checkNullEmpty(c.getString(c.getColumnIndex("Splenomegaly")));
                            String Heptomegaly = checkNullEmpty(c.getString(c.getColumnIndex("Hepatomegaly")));
                            String Organomegaly = checkNullEmpty(c.getString(c.getColumnIndex("organomegely")));
                            String FreeFluidAbdomen = checkNullEmpty(c.getString(c.getColumnIndex("freefuild")));
                            String Distension = checkNullEmpty(c.getString(c.getColumnIndex("distension")));
                            String Tenderness = checkNullEmpty(c.getString(c.getColumnIndex("tenderness")));
                            String Scrotum = checkNullEmpty(c.getString(c.getColumnIndex("scrotum")));

                            str5.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.shape_of_the_abdomen)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ShapeOfAbdomen).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.abdomen)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Abdomen).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.visible_pulsations)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(VisiblePulsations).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.visible_peristalsis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(VisiblePeristalsis).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.abdominal_palpation)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(AbdominalPalpation).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.splenomegaly)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Splenomegaly).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.hepatomegaly)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Heptomegaly).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.palpable_masses)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PalpableMasses).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.bowel_sound)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(BowelSound).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.hernial)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Hernial).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.spleen)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Spleen).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.liver)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Liver).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.organomegaly)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Organomegaly).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.free_fluid_in_the_abdomen)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(FreeFluidAbdomen).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.distension)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Distension).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.tenderness)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Tenderness).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.scrotum)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Scrotum).append("</td>\n").append("</tr>\n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div>\n").append('\n');

                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            //set values
        }

        String CongentialAbnormality = "";
        String Mentalstatus = "";
        //##################### //#####################
        //5.Loadneurologysystem
        str6 = new StringBuilder();

        boolean chk9 = BaseConfig.LoadReportsBooleanStatus("select Pupilsize as dstatus1 from caseNote_Neurology where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;");
        if (chk9) {

            str6.append("<font class=\"sub\">  ").append(getString(R.string.neurology_system)).append("</font>\n");

            try {
                c = db.rawQuery("select Pupilsize,Speech,Carodit,Amnesia,Apraxia,Cognitive_Function,Bulk,Tone,Power_LUL,Power_RUL,Power_LLL,Power_RLL,Sensory,Reflexes_Corneal,Reflexes_Biceps,Reflexes_Triceps,Reflexes_Supinator,Reflexes_Knee,Reflexes_Ankle,Reflexes_Plantor,congentail_abnormality,mentalstatus from caseNote_Neurology where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String PupilSize = checkNullEmpty(c.getString(c.getColumnIndex("Pupilsize")));
                            String Speech = checkNullEmpty(c.getString(c.getColumnIndex("Speech")));
                            String Cartoid = checkNullEmpty(c.getString(c.getColumnIndex("Carodit")));
                            String Amnesia = checkNullEmpty(c.getString(c.getColumnIndex("Amnesia")));
                            String Apraxia = checkNullEmpty(c.getString(c.getColumnIndex("Apraxia")));
                            String CognitiveFunction = checkNullEmpty(c.getString(c.getColumnIndex("Cognitive_Function")));
                            String Bulk = checkNullEmpty(c.getString(c.getColumnIndex("Bulk")));
                            String Tone = checkNullEmpty(c.getString(c.getColumnIndex("Tone")));

                            String Power_LUL = checkNullEmpty(c.getString(c.getColumnIndex("Power_LUL")));
                            String Power_RUL = checkNullEmpty(c.getString(c.getColumnIndex("Power_RUL")));


                            String Power_LLL = checkNullEmpty(c.getString(c.getColumnIndex("Power_LLL")));
                            String Power_RLL = checkNullEmpty(c.getString(c.getColumnIndex("Power_RLL")));

                            String Sensory = checkNullEmpty(c.getString(c.getColumnIndex("Sensory")));

                            String Corneal = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Corneal")));
                            String Biceps = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Biceps")));
                            String Triceps = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Triceps")));
                            String Supinator = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Supinator")));
                            String Knee = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Knee")));
                            String Ankle = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Ankle")));
                            String Plantar = checkNullEmpty(c.getString(c.getColumnIndex("Reflexes_Plantor")));
                            CongentialAbnormality = checkNullEmpty(c.getString(c.getColumnIndex("congentail_abnormality")));
                            Mentalstatus = checkNullEmpty(c.getString(c.getColumnIndex("mentalstatus")));


                            //mental_status.setText();

                            str6.append("<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.pupil_size)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(PupilSize).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.speech)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Speech).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.cartoid)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Cartoid).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.amnesia)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Amnesia).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.apraxia)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Apraxia).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.cognitive_function)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(CognitiveFunction).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.bulk)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Bulk).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.tone)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Tone).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div>\n");


                            str6.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.power)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.left_upper_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Power_LUL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.right_upper_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Power_RUL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.left_lower_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Power_LLL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.right_lower_limb)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Power_RLL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.sensory)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Sensory).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div> ");


                            str6.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.reflexes)).append("</b></td>  \n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.corneal)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Corneal).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.biceps)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Biceps).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.triceps)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Triceps).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.supinator)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Supinator).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.knee)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Knee).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.ankle)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Ankle).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.plantar)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Plantar).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n");

                        } while (c.moveToNext());
                    }
                }


                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //set values

        str7 = new StringBuilder();

        boolean chk8 = BaseConfig.LoadReportsBooleanStatus("select oriented as dstatus1 from CaseNote_CNS where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk8) {

            str7.append("<font class=\"sub\">  ").append(getString(R.string.central_nervous_system)).append("</font>\n");

            try {
                c = db.rawQuery("select oriented,neckrigidity,confused,exaggerated,extensor,gsscore,incomprehensible,depressed,flexor,coma from CaseNote_CNS where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Oriented = checkNullEmpty(c.getString(c.getColumnIndex("oriented")));
                            String NeckRigidity = checkNullEmpty(c.getString(c.getColumnIndex("neckrigidity")));
                            String Confused = checkNullEmpty(c.getString(c.getColumnIndex("confused")));
                            String Exaggerated = checkNullEmpty(c.getString(c.getColumnIndex("exaggerated")));
                            String Extenstor = checkNullEmpty(c.getString(c.getColumnIndex("extensor")));
                            String GSScore = checkNullEmpty(c.getString(c.getColumnIndex("gsscore")));
                            String Incomprehensible = checkNullEmpty(c.getString(c.getColumnIndex("incomprehensible")));
                            String Depressed = checkNullEmpty(c.getString(c.getColumnIndex("depressed")));
                            String Flexor = checkNullEmpty(c.getString(c.getColumnIndex("flexor")));
                            String Coma = checkNullEmpty(c.getString(c.getColumnIndex("coma")));

                            str7.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.oriented)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Oriented).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.neck_rigidity)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(NeckRigidity).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.confused)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Confused).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.exaggerated)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Exaggerated).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.extenstor)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Extenstor).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.gs_score)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(GSScore).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.incomprehensible)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Incomprehensible).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append("  <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.depressed)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Depressed).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" <tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n");

                        } while (c.moveToNext());
                    }
                }


                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }


        //Set values


        //##################### //#####################
        //6.Loadlocomotorsystems
        str8 = new StringBuilder();
        boolean chk7 = BaseConfig.LoadReportsBooleanStatus("select symmetry as dstatus1 from CaseNote_Locomotor where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk7) {

            str8.append("<font class=\"sub\">  ").append(getString(R.string.locomotor_system)).append("</font>\n");

            try {
                c = db.rawQuery("select symmetry,smooth_movement,arms_swing,pelvic_tilt,stride_length,cervical_lordosis,lumbar_lordosis,kyphosis,scoliosis,llswelling,lldeformity,lllimbshortening,llmuscle_wasting,llremarks,ulswelling,uldeformity,ullimbshortening,ulmuscle_wasting,ulremarks from CaseNote_Locomotor where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            //gait
                            String Symmetry = checkNullEmpty(c.getString(c.getColumnIndex("symmetry")));
                            String Smooth_movement = checkNullEmpty(c.getString(c.getColumnIndex("smooth_movement")));
                            String Arms_strong = checkNullEmpty(c.getString(c.getColumnIndex("arms_swing")));
                            String Pelvic_tilt = checkNullEmpty(c.getString(c.getColumnIndex("pelvic_tilt")));
                            String Stride_length = checkNullEmpty(c.getString(c.getColumnIndex("stride_length")));

                            //spine
                            String Cervical_Lordosis = checkNullEmpty(c.getString(c.getColumnIndex("cervical_lordosis")));
                            String Lumbar_Lordosis = checkNullEmpty(c.getString(c.getColumnIndex("lumbar_lordosis")));
                            String Kyphosis = checkNullEmpty(c.getString(c.getColumnIndex("kyphosis")));
                            String Scoliosis = checkNullEmpty(c.getString(c.getColumnIndex("scoliosis")));

                            //lower limb
                            String Swelling = checkNullEmpty(c.getString(c.getColumnIndex("llswelling")));
                            String Deformity = checkNullEmpty(c.getString(c.getColumnIndex("lldeformity")));
                            String Limb_shortening = checkNullEmpty(c.getString(c.getColumnIndex("lllimbshortening")));
                            String Muscle_wasting = checkNullEmpty(c.getString(c.getColumnIndex("llmuscle_wasting")));
                            String Remarks = checkNullEmpty(c.getString(c.getColumnIndex("llremarks")));

                            //upper limb
                            String Swelling2 = checkNullEmpty(c.getString(c.getColumnIndex("ulswelling")));
                            String Deformity2 = checkNullEmpty(c.getString(c.getColumnIndex("uldeformity")));
                            String Limb_shortening2 = checkNullEmpty(c.getString(c.getColumnIndex("ullimbshortening")));
                            String Muscle_wasting2 = checkNullEmpty(c.getString(c.getColumnIndex("ulmuscle_wasting")));
                            String Remarks2 = checkNullEmpty(c.getString(c.getColumnIndex("ulremarks")));

                            str8.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.gait)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.symmetry)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Symmetry).append("</td>\n").append("</tr>\n").append(" \n").append(

                                    // TODO: 6/19/2017 add new coloumn Ponnusamy M
                                    "<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.smoothmovement)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Smooth_movement).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.arms_swing)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Arms_strong).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.pelvic_tilt)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Pelvic_tilt).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.stride_length)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Stride_length).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.spine)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\"  style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.cervical_lordosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Cervical_Lordosis).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.lumbar_lordosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Lumbar_Lordosis).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.kyphosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Kyphosis).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.scoliosis)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Scoliosis).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("  \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.lower_limb)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.swelling)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Swelling).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.deformity)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Deformity).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.limb_shortening)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Limb_shortening).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.muscle_wasting)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Muscle_wasting).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.remarks)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Remarks).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.upper_limb)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.swelling)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Swelling2).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.deformity)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Deformity2).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.limb_shortening)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Limb_shortening2).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.muscle_wasting)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Muscle_wasting2).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.remarks)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Remarks2).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div>");

                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //Set values

        //##################### //#####################
        //7.Loadrenalsystems
        str9 = new StringBuilder();
        boolean chk6 = BaseConfig.LoadReportsBooleanStatus("select dysuria as dstatus1 from CaseNote_Renal where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk6) {

            str9.append("<font class=\"sub\">  ").append(getString(R.string.renal_system)).append("</font>\n");

            try {
                c = db.rawQuery("select dysuria,pyuria,haematuria,oliguria,polyuria,anuria,nocturia,urethraldischarge,prostate from CaseNote_Renal where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Dysuria = checkNullEmpty(c.getString(c.getColumnIndex("dysuria")));
                            String Pyuria = checkNullEmpty(c.getString(c.getColumnIndex("pyuria")));
                            String Haematuria = checkNullEmpty(c.getString(c.getColumnIndex("haematuria")));
                            String Oliguria = checkNullEmpty(c.getString(c.getColumnIndex("oliguria")));
                            String Polyuria = checkNullEmpty(c.getString(c.getColumnIndex("polyuria")));
                            String Anuria = checkNullEmpty(c.getString(c.getColumnIndex("anuria")));
                            String Nocturia = checkNullEmpty(c.getString(c.getColumnIndex("nocturia")));
                            String Urethral_discharge = checkNullEmpty(c.getString(c.getColumnIndex("urethraldischarge")));
                            String Prostate = checkNullEmpty(c.getString(c.getColumnIndex("prostate")));

                            str9.append("<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.dysuria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Dysuria).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.pyuria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Pyuria).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.haematuria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Haematuria).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.oliguria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Oliguria).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.polyuria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Polyuria).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.anuria)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Anuria).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.nocturia)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Nocturia).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append("  <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.urethral_discharge)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Urethral_discharge).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.prostate)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Prostate).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div> ");

                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }


        //Set values

        //##################### //#####################
        //8.LoadEndocrinesystems
        str10 = new StringBuilder();

        boolean chk5 = BaseConfig.LoadReportsBooleanStatus("select Endocrine as dstatus1 from CaseNote_Endocrine where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;");
        if (chk5) {

            str10.append("<font class=\"sub\">  ").append(getString(R.string.endocrine_system)).append(" </font>\n");

            try {
                c = db.rawQuery("select Endocrine from CaseNote_Endocrine where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String EndocrineSystem = checkNullEmpty(c.getString(c.getColumnIndex("Endocrine")));

                            str10.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.endocrine_system)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(EndocrineSystem).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append(" \n").append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div> ");

                        } while (c.moveToNext());
                    }
                }


                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        //Set values

        //##################### //#####################
        //9.Loadothersystems
        str11 = new StringBuilder();

        boolean chk4 = BaseConfig.LoadReportsBooleanStatus("select Othersystem as dstatus1 from caseNote_OtherSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;");
        if (chk4) {

            str11.append("<font class=\"sub\">  ").append(getString(R.string.other_systems)).append(" </font>\n");

            try {
                c = db.rawQuery("select Othersystem,AdditionalInfo from caseNote_OtherSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "' ;", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String OtherSystem = checkNullEmpty(c.getString(c.getColumnIndex("Othersystem")));
                            String AdditionalInformationFinding = checkNullEmpty(c.getString(c.getColumnIndex("AdditionalInfo")));


                            str11.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.other_systems)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(OtherSystem).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.additional_information_finding)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(AdditionalInformationFinding).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("</tbody>\n").append("</table>\n").append('\n').append("</div> ");


                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }

        //Set values

        //##################### //#####################
        //10.Loadclinicaldata
        str12 = new StringBuilder();
        boolean chk3 = BaseConfig.LoadReportsBooleanStatus("select Heamoglobin as dstatus1 from caseNote_ClinicalData where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk3) {

            str12.append("<font class=\"sub\">  ").append(getString(R.string.clinical_data)).append("</font>\n");

            try {
                c = db.rawQuery("select Heamoglobin,wbc,rbc,esr,polymorphs,lymphocytes,monocytes,basophils,eosinophils,urea,creatinine,UricAcid,Sodium,Potassium,Chloride,Bicarbonate,TotalCholesterol,LDL,HDL,VLDL,Triglycerides,Serumbilirubin,Direct,Indirect,Totalprotein,Albumin,Globulin,SGOT,SGPT,AlkalinePhosphatase,FreeT3,FreeT4,TSH from caseNote_ClinicalData where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {


                            String Haemoglobin = checkNullEmpty(c.getString(c.getColumnIndex("Heamoglobin")));
                            String WBC = checkNullEmpty(c.getString(c.getColumnIndex("wbc")));
                            String RBC = checkNullEmpty(c.getString(c.getColumnIndex("rbc")));
                            String ESR = checkNullEmpty(c.getString(c.getColumnIndex("esr")));
                            String Polymorphs = checkNullEmpty(c.getString(c.getColumnIndex("polymorphs")));

                            String Lymphocytes = checkNullEmpty(c.getString(c.getColumnIndex("lymphocytes")));
                            String Monocytes = checkNullEmpty(c.getString(c.getColumnIndex("monocytes")));
                            String Basophils = checkNullEmpty(c.getString(c.getColumnIndex("basophils")));
                            String Eosinophils = checkNullEmpty(c.getString(c.getColumnIndex("eosinophils")));
                            String Urea = checkNullEmpty(c.getString(c.getColumnIndex("urea")));
                            String Creatinine = checkNullEmpty(c.getString(c.getColumnIndex("creatinine")));
                            String UricAcid = checkNullEmpty(c.getString(c.getColumnIndex("UricAcid")));
                            String Sodium = checkNullEmpty(c.getString(c.getColumnIndex("Sodium")));
                            String Potasium = checkNullEmpty(c.getString(c.getColumnIndex("Potassium")));
                            String Chloride = checkNullEmpty(c.getString(c.getColumnIndex("Chloride")));

                            String Bicarbonate = checkNullEmpty(c.getString(c.getColumnIndex("Bicarbonate")));
                            String TotalCholesterol = checkNullEmpty(c.getString(c.getColumnIndex("TotalCholesterol")));
                            String LDL = checkNullEmpty(c.getString(c.getColumnIndex("LDL")));
                            String HDL = checkNullEmpty(c.getString(c.getColumnIndex("HDL")));
                            String VLDL = checkNullEmpty(c.getString(c.getColumnIndex("VLDL")));
                            String Triglycerides = checkNullEmpty(c.getString(c.getColumnIndex("Triglycerides")));
                            String Serumbilirubin = checkNullEmpty(c.getString(c.getColumnIndex("Serumbilirubin")));
                            String Direct = checkNullEmpty(c.getString(c.getColumnIndex("Direct")));
                            String InDirect = checkNullEmpty(c.getString(c.getColumnIndex("Indirect")));
                            String Totalprotein = checkNullEmpty(c.getString(c.getColumnIndex("Totalprotein")));
                            String Albumin = checkNullEmpty(c.getString(c.getColumnIndex("Albumin")));
                            String Globulin = checkNullEmpty(c.getString(c.getColumnIndex("Globulin")));
                            String SGOT = checkNullEmpty(c.getString(c.getColumnIndex("SGOT")));
                            String SGPT = checkNullEmpty(c.getString(c.getColumnIndex("SGPT")));
                            String AlkalinePhospatase = checkNullEmpty(c.getString(c.getColumnIndex("AlkalinePhosphatase")));
                            String FreeT3 = checkNullEmpty(c.getString(c.getColumnIndex("FreeT3")));
                            String FreeT4 = checkNullEmpty(c.getString(c.getColumnIndex("FreeT4")));
                            String TSH = checkNullEmpty(c.getString(c.getColumnIndex("TSH")));


                            str12.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + '\n' + '\n' + '\n' + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  ").append(getString(R.string.haemogram)).append("</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Haemoglobin (g/dl)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Haemoglobin).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  WBC</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(WBC).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Monocytes</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Monocytes).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Basophils</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Basophils).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  RBC</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(RBC).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ESR</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(ESR).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Polymorphs</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Polymorphs).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Lymphocytes</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Lymphocytes).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  Renal Markers</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Eosinophils</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Eosinophils).append("</td>\n").append("</tr>\n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Urea</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Urea).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Creatinine</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Creatinine).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Uric Acid</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(UricAcid).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append('\n').append('\n').append('\n').append("  \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  Electrolyte</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Sodium</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Sodium).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Potasium</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Potasium).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Chloride</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Chloride).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Bicarbonate</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Bicarbonate).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append(" \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  Lipo Profile</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Total Cholesterol</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(TotalCholesterol).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  LDL</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(LDL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  HDL</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(HDL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  VLDL</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(VLDL).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Triglycerides</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Triglycerides).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" \n").append(" \n").append(" \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  Liver Function Test</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Serum bilirubin<br/>(Total)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Serumbilirubin).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Direct</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Direct).append("</td>\n").append("</tr>\n").append("  \n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  InDirect</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(InDirect).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Total protein</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Totalprotein).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Albumin</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Albumin).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Globulin</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Globulin).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  SGOT</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(SGOT).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  SGPT</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(SGPT).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Alkaline Phospatase</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(AlkalinePhospatase).append("</td>\n").append("</tr>\n").append('\n').append(" \n").append(" \n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  Thyroid Profile</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Free T3</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(FreeT3).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Free T4</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(FreeT4).append("</td>\n").append("</tr>\n").append("  \n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  TSH</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(TSH).append("</td>\n").append("</tr>\n").append(" \n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n").append('\n');

                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }


        //***************
        //PNC **************************************************************************************
        String str_dateofdelivery = "", str_placeofdelivery = "", str_tod_n = "", str_tod_instr = "", str_tod_cs = "", str_terms = "", str_postdelivery = "",
                str_complications = "", str_genderofchild = "", str_weight_baby_kg = "", str_weight_baby_gms = "", str_criedafter_birth = "", str_initi_breastfeeding = "";

        //PPC **************************************************************************************
        String str_any_comapl = "", str_edt_ppc_pallor = "", str_edt_pulse = "", str_edt_bloodpres = "", str_edt_temperature = "", str_edt_familyplanning = "", str_edt_anyothers = "";
        String str_rg_breast = "", str_rg_nipples = "", str_rg_uterus = "", str_rg_bleednig = "", str_rg_lochia = "", str_rg_epi = "";

        //COB **************************************************************************************
        String str_edt_urinepassed = "", str_edt_stoolpassed = "", str_edt_diarrhea = "", str_edt_vomiting = "", str_edt_convulions = "", str_edt_cb_temp = "", str_edt_cb_jaundice = "", str_edt_anyothercompli = "";
        String str_rg_activity = "", str_eg_sucking = "", str_rg_breathing = "", str_rg_chest = "", str_rg_skinpus = "";
        String str_umbilical = "";

        JSONArray arr1 = new JSONArray();
        JSONArray arr2 = new JSONArray();
        JSONArray arr3 = new JSONArray();

        JSONObject obj = new JSONObject();

        //12. Load PNC Systems
        str13 = new StringBuilder();
        boolean chk2 = BaseConfig.LoadReportsBooleanStatus("select pnc_details as dstatus1 from CaseNote_PNCSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk2) {

            str13.append("<font class=\"sub\">  ").append(getString(R.string.pnc_expn)).append(" </font>\n");

            try {
                c = db.rawQuery("select pnc_details,ppc_details,cob_details from CaseNote_PNCSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';", null);

                if (c != null) {
                    if (c.moveToFirst()) {
                        do {

                            String Array1 = c.getString(c.getColumnIndex("pnc_details"));
                            arr1 = new JSONArray(Array1);

                            for (int i = 0; i < arr1.length(); i++) {
                                obj = arr1.getJSONObject(i);
                                str_dateofdelivery = obj.get("str_dateofdelivery").toString();
                                str_placeofdelivery = obj.get("str_placeofdelivery").toString();
                                str_tod_n = obj.get("str_tod_n").toString();
                                str_tod_instr = obj.get("str_tod_instr").toString();
                                str_tod_cs = obj.get("str_tod_cs").toString();
                                str_terms = obj.get("str_terms").toString();
                                str_postdelivery = obj.get("str_postdelivery").toString();
                                str_complications = obj.get("str_complications").toString();
                                str_genderofchild = obj.get("str_genderofchild").toString();
                                str_weight_baby_kg = obj.get("str_weight_baby_kg").toString();
                                str_weight_baby_gms = obj.get("str_weight_baby_gms").toString();
                                str_criedafter_birth = obj.get("str_criedafter_birth").toString();
                                str_initi_breastfeeding = obj.get("str_initi_breastfeeding").toString();
                            }


                            String Array2 = c.getString(c.getColumnIndex("ppc_details"));
                            arr2 = new JSONArray(Array2);
                            for (int i = 0; i < arr2.length(); i++) {
                                obj = arr2.getJSONObject(i);
                                str_any_comapl = obj.get("str_any_comapl").toString();
                                str_edt_ppc_pallor = obj.get("str_edt_ppc_pallor").toString();
                                str_edt_pulse = obj.get("str_edt_pulse").toString();
                                str_edt_bloodpres = obj.get("str_edt_bloodpres").toString();
                                str_edt_temperature = obj.get("str_edt_temperature").toString();
                                str_edt_familyplanning = obj.get("str_edt_familyplanning").toString();
                                str_edt_anyothers = obj.get("str_edt_anyothers").toString();
                                str_rg_breast = obj.get("str_rg_breast").toString();
                                str_rg_nipples = obj.get("str_rg_nipples").toString();
                                str_rg_uterus = obj.get("str_rg_uterus").toString();
                                str_rg_bleednig = obj.get("str_rg_bleednig").toString();
                                str_rg_lochia = obj.get("str_rg_lochia").toString();
                                str_rg_epi = obj.get("str_rg_epi").toString();

                            }

                            String Array3 = c.getString(c.getColumnIndex("cob_details"));
                            arr3 = new JSONArray(Array3);
                            for (int i = 0; i < arr3.length(); i++) {
                                obj = arr3.getJSONObject(i);
                                str_edt_urinepassed = obj.get("str_edt_urinepassed").toString();
                                str_edt_stoolpassed = obj.get("str_edt_stoolpassed").toString();
                                str_edt_diarrhea = obj.get("str_edt_diarrhea").toString();
                                str_edt_vomiting = obj.get("str_edt_vomiting").toString();
                                str_edt_convulions = obj.get("str_edt_convulions").toString();
                                str_edt_cb_temp = obj.get("str_edt_cb_temp").toString();
                                str_edt_cb_jaundice = obj.get("str_edt_cb_jaundice").toString();
                                str_umbilical = obj.get("str_umbilical").toString();
                                str_edt_anyothercompli = obj.get("str_edt_anyothercompli").toString();
                                str_rg_activity = obj.get("str_rg_activity").toString();
                                str_eg_sucking = obj.get("str_eg_sucking").toString();
                                str_rg_breathing = obj.get("str_rg_breathing").toString();
                                str_rg_chest = obj.get("str_rg_chest").toString();
                                str_rg_skinpus = obj.get("str_rg_skinpus").toString();
                            }

                            str13.append('\n' + "<div class=\"bs-example\">\n" + '\n' + '\n' + "<table style=\"width:100%\" class=\"table\">\n" + "   \n" + "<tbody>\n" + "<tr>\n" + "<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Date of delivery </b></td> \n" + "<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_dateofdelivery).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Place of delivery </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_placeofdelivery).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Type of delivery</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_tod_n).append('\n').append(str_tod_instr).append('\n').append(str_tod_cs).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Term/Preterm</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_terms).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  If at institution period of stay post delivery</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_postdelivery).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Complications, if any (Specify)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_complications).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Sex of baby</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_genderofchild).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Weight of baby (Kg)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_weight_baby_kg).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Weight of baby (gms)</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_weight_baby_gms).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Cried immedietely after birth</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_criedafter_birth).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Initiated exclusive breast feeding within 1 hour of birth</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_initi_breastfeeding).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  POST PORTUM CARE</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Any Complaints</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_any_comapl).append("</td>\n").append("</tr>\n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Pallor</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_ppc_pallor).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Pulse rate</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_pulse).append("</td>\n").append("</tr>\n").append(" \n").append(" \n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Blood pressure</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_bloodpres).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Temperature</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_temperature).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Breasts</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_breast).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Nipples</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_nipples).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Uterus Tenderness</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_uterus).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Bleeding P/V</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_bleednig).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Lochia</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_lochia).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Episiotomy/Tear</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_epi).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Family planning counselling</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_familyplanning).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Any other complications and referral</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_anyothers).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#fff; background:#777575\"> <b>  CARE OF BABY</b></td>  \n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Urine Passed</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_urinepassed).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Stool Passed</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_stoolpassed).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Diarrhea</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_diarrhea).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Vomiting</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_vomiting).append("</td>\n").append("</tr>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Convulsions</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_convulions).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Activity</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_activity).append("</td>\n").append("</tr>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Sucking</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_eg_sucking).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Breathing</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_breathing).append("</td>\n").append("</tr>\n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Chest indrawing</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_chest).append("</td>\n").append("</tr>\n").append(" <tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Temperature</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_cb_temp).append("</td>\n").append("</tr>\n").append("  \n").append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Jaundice</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_cb_jaundice).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Condition of umbilical stump</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_umbilical).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Skin Pustules</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_rg_skinpus).append("</td>\n").append("</tr>\n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Any other complications</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_edt_anyothercompli).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n").append('\n');

                        } while (c.moveToNext());
                    }
                }

                c.close();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //*******************************************************************************************************
        //13. Dental System
        //13. Dental System
        str14 = new StringBuilder();
        boolean chk1 = BaseConfig.LoadReportsBooleanStatus("select dental_sys as dstatus1 from CaseNote_DentalSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';");
        if (chk1) {
            try {

                str14.append("<font class=\"sub\">  ").append(getString(R.string.dental_system)).append(" </font>\n");

                String Query_Dental = "select dental_sys from CaseNote_DentalSystem where DiagId='" + value1.trim() + "'   and ptid='" + BUNDLE_PATIENT_ID + "';";
                String adult_upper_right_jaw = "";
                String adult_upper_left_jaw = "";
                String adult_lower_right_jaw = "";
                String adult_lower_left_jaw = "";


                String child_upper_right_jaw = "";
                String child_upper_left_jaw = "";
                String child_lower_right_jaw = "";
                String child_lower_left_jaw = "";

                Cursor dental_cursor = db.rawQuery(Query_Dental, null);

                if (dental_cursor != null) {
                    if (dental_cursor.moveToFirst()) {
                        do {

                            String Dental_SYSTEM = dental_cursor.getString(dental_cursor.getColumnIndex("dental_sys"));

                            JSONArray jsonArray = new JSONArray(Dental_SYSTEM);

                            JSONObject jsonObject1=jsonArray.getJSONObject(0);
                            JSONObject jsonObject2=jsonArray.getJSONObject(1);

                            String result1=jsonObject2.getString("Adult");
                            String result2=jsonObject1.getString("Child");

                            JSONArray adultValue = new JSONArray(result1);
                            JSONArray childValue = new JSONArray(result2);


                            adult_upper_right_jaw = adultValue.getJSONObject(0).getString("upper_right_jaw");
                            adult_upper_left_jaw = adultValue.getJSONObject(1).getString("upper_left_jaw");
                            adult_lower_right_jaw = adultValue.getJSONObject(2).getString("lower_right_jaw");
                            adult_lower_left_jaw = adultValue.getJSONObject(3).getString("lower_left_jaw");

                            child_upper_right_jaw = childValue.getJSONObject(0).getString("upper_right_jaw");
                            child_upper_left_jaw = childValue.getJSONObject(1).getString("upper_left_jaw");
                            child_lower_right_jaw = childValue.getJSONObject(2).getString("lower_right_jaw");
                            child_lower_left_jaw = childValue.getJSONObject(3).getString("lower_left_jaw");




                            /*upper_right_jaw = dental_cursor.getString(dental_cursor.getColumnIndex("upper_right_jaw"));
                            upper_left_jaw = dental_cursor.getString(dental_cursor.getColumnIndex("upper_left_jaw"));
                            lower_right_jaw = dental_cursor.getString(dental_cursor.getColumnIndex("lower_right_jaw"));
                            lower_left_jaw = dental_cursor.getString(dental_cursor.getColumnIndex("lower_left_jaw"));
*/

                            adult_upper_right_jaw = ((adult_upper_right_jaw == null || adult_upper_right_jaw.length() == 0) ? "" : adult_upper_right_jaw.substring(0, adult_upper_right_jaw.length() - 1));
                            adult_upper_left_jaw = ((adult_upper_left_jaw == null || adult_upper_left_jaw.length() == 0) ? "" : adult_upper_left_jaw.substring(0, adult_upper_left_jaw.length() - 1));
                            adult_lower_right_jaw = ((adult_lower_right_jaw == null || adult_lower_right_jaw.length() == 0) ? "" : adult_lower_right_jaw.substring(0, adult_lower_right_jaw.length() - 1));
                            adult_lower_left_jaw = ((adult_lower_left_jaw == null || adult_lower_left_jaw.length() == 0) ? "" : adult_lower_left_jaw.substring(0, adult_lower_left_jaw.length() - 1));

                            String str_upper_right_jaw = adult_upper_right_jaw;
                            String str_upper_left_jaw = adult_upper_left_jaw;
                            String str_lower_right_jaw = adult_lower_right_jaw;
                            String str_lower_left_jaw = adult_lower_left_jaw;


                            String[] str1 = adult_upper_right_jaw.split(",");//8,7,5
                            String[] str2 = adult_upper_left_jaw.split(",");//1,2,3
                            String[] str3 = adult_lower_right_jaw.split(",");//5,6,7
                            String[] str4 = adult_lower_left_jaw.split(",");//4,5,7

                            String PatientAge = BaseConfig.GetValues("select age as ret_values from Patreg where Patid='" + BUNDLE_PATIENT_ID + '\'');
                            if (PatientAge != null) {
                                // if (Integer.parseInt(PatientAge) >= BaseConfig.Adult_childAge)//Adult show dental adult layout

                                //Child layout

                                // Forward 1 BackWard 2
                                int size = 8;


                                adult_upper_right_jaw = convertHTML_DentalCodeAdult(2, size, str1);
                                adult_upper_left_jaw = convertHTML_DentalCodeAdult(1, size, str2);
                                adult_lower_right_jaw = convertHTML_DentalCodeAdult(2, size, str3);
                                adult_lower_left_jaw = convertHTML_DentalCodeAdult(1, size, str4);

                                str14.append("<table width=\"100%\" class=\"tftable\">\n" + "\t\t<thead>\n" + "\t\t\t<tr>\n" + "\t\t\t\t<td colspan=\"8\" class=\"noborderright\">Upper Right Jaw Line</td>\n" + "\t\t\t\t<td colspan=\"8\" class=\"noborderleft\">Upper Left Jaw Line</td>\n" + "\t\t\t</tr>\n" + "\t\t\t<tr>" + "").append(adult_upper_right_jaw).append(adult_upper_left_jaw).append("</tr>\n").append("\t\t\t<tr>").append("").append("").append(adult_lower_right_jaw).append(adult_lower_left_jaw).append("</tr>\n").append("\t\t\t<tr>\n").append("\t\t\t\t<td colspan=\"8\" class=\"noborderright\">Lower Right Jaw Line</td>\n").append("\t\t\t\t<td colspan=\"8\" class=\"noborderleft\">Lower Left Jaw Line</td>\n").append("\t\t\t</tr>\n").append("\t\t</thead>\n").append("\t</table>").append("").append("<div class=\"bs-example\">\n").append('\n').append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append(" teeth in - Upper right jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_upper_right_jaw).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append(" teeth in - Upper left jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_upper_left_jaw).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append("  teeth in - Lower right jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_lower_right_jaw).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append("  teeth in - Lower left jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(str_lower_left_jaw).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n").append('\n');


                                //else if (Integer.parseInt(PatientAge) < BaseConfig.Adult_childAge)//Child show dental child layout

                                //  String[] orderabc=new String[]{"A","B","C","D","E","F"};
                                //String[] reorderabc=new String[]{"F","E","D","C","B","A"};

                                // Forward 1 BackWard 2
                                // int size = 8;


                                child_upper_right_jaw = ((child_upper_right_jaw == null || child_upper_right_jaw.length() == 0) ? "" : child_upper_right_jaw.substring(0, child_upper_right_jaw.length() - 1));
                                child_upper_left_jaw = ((child_upper_left_jaw == null || child_upper_left_jaw.length() == 0) ? "" : child_upper_left_jaw.substring(0, child_upper_left_jaw.length() - 1));
                                child_lower_right_jaw = ((child_lower_right_jaw == null || child_lower_right_jaw.length() == 0) ? "" : child_lower_right_jaw.substring(0, child_lower_right_jaw.length() - 1));
                                child_lower_left_jaw = ((child_lower_left_jaw == null || child_lower_left_jaw.length() == 0) ? "" : child_lower_left_jaw.substring(0, child_lower_left_jaw.length() - 1));

                                String child_str_upper_right_jaw = child_upper_right_jaw;
                                String child_str_upper_left_jaw = child_upper_left_jaw;
                                String child_str_lower_right_jaw = child_lower_right_jaw;
                                String child_str_lower_left_jaw = child_lower_left_jaw;


                                String[] child_str1 = child_upper_right_jaw.split(",");//8,7,5
                                String[] child_str2 = child_upper_left_jaw.split(",");//1,2,3
                                String[] child_str3 = child_lower_right_jaw.split(",");//5,6,7
                                String[] child_str4 = child_lower_left_jaw.split(",");//4,5,7

                                child_upper_right_jaw = convertHTML_DentalCodeChild(reorderabc, child_str1);
                                child_upper_left_jaw = convertHTML_DentalCodeChild(orderabc, child_str2);
                                child_lower_right_jaw = convertHTML_DentalCodeChild(reorderabc, child_str3);
                                child_lower_left_jaw = convertHTML_DentalCodeChild(orderabc, child_str4);

                                str14.append("<table width=\"100%\" class=\"tftable\">\n" + "\t\t<thead>\n" + "\t\t\t<tr>\n" + "\t\t\t\t<td colspan=\"5\" class=\"noborderright\">Upper Right Jaw Line</td>\n" + "\t\t\t\t<td colspan=\"5\" class=\"noborderleft\">Upper Left Jaw Line</td>\n" + "\t\t\t</tr>\n" + "\t\t\t<tr>" + "").append(child_upper_right_jaw).append(child_upper_left_jaw).append("</tr>\n").append("\t\t\t<tr>").append("").append("").append(child_lower_right_jaw).append(child_lower_left_jaw).append("</tr>\n").append("\t\t\t<tr>\n").append("\t\t\t\t<td colspan=\"5\" class=\"noborderright\">Lower Right Jaw Line</td>\n").append("\t\t\t\t<td colspan=\"5\" class=\"noborderleft\">Lower Left Jaw Line</td>\n").append("\t\t\t</tr>\n").append("\t\t</thead>\n").append("\t</table>").append("").append("<div class=\"bs-example\">\n").append('\n').append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append(" teeth in - Upper right jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(child_str_upper_right_jaw).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append(" teeth in - Upper left jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(child_str_upper_left_jaw).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append("  teeth in - Lower right jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(child_str_lower_right_jaw).append("</td>\n").append("</tr>\n").append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  Having ").append(ProvisionalDiagnosis).append("  teeth in - Lower left jaw line </b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(child_str_lower_left_jaw).append("</td>\n").append("</tr>\n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n").append('\n');


                            }


                        } while (dental_cursor.moveToNext());
                    }
                }

                dental_cursor.close();
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //#######################################################################################################
        //Load Main Html File
        values.append("<!DOCTYPE html>\n" + '\n' + "<html lang=\"en\">\n" + "<head>\n" + '\n' + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/>\n" + "<link rel=\"stylesheet\"  type=\"text/css\" href=\"file:///android_asset/Doctor_Profile/css/english.css\"/>\n" + '\n' + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.css\" />\n" + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/bootstrap-theme.min.css\" />\n" + '\n' + "<link rel=\"stylesheet\" href=\"file:///android_asset/Doctor_Profile/css/font-awesome.min.css\" type=\"text/css\" />\n" + '\n' + "<script src=\"file:///android_asset/Doctor_Profile/css/jquery.min.js\"></script>\n" + "<script src=\"file:///android_asset/Doctor_Profile/css/bootstrap.min.js\" ></script>\n" + '\n' + "\n\n" + "<style>\n" + ".tftable {\n" + "\tborder-collapse: collapse;\n" + "\tfont-weight: bold;\n" + "}\n" + '\n' + "td {\n" + "\tpadding: 6px;\n" + "}\n" + '\n' + ".noborderright {\n" + "\tborder-right: 2px solid black;\n" + "}\n" + '\n' + ".noborderleft {\n" + "\tborder-left: 2px solid black;\n" + "}\n" + '\n' + ".lefttop {\n" + "\tborder-right: 2px solid black;\n" + "\tborder-bottom: 2px solid black;\n" + "}\n" + '\n' + ".righttop {\n" + "\tborder-left: 2px solid black;\n" + "\tborder-bottom: 2px solid black;\n" + "}\n" + '\n' + ".rightbottom {\n" + "\tborder-top: 2px solid black;\n" + "\tborder-left: 2px solid black;\n" + "}\n" + '\n' + ".leftbottom {\n" + "\tborder-right: 2px solid black;\n" + "\tborder-top: 2px solid black;\n" + "}\n" + "</style>" + "</head>\n" + "<body>  \n" + " \n" + "<font class=\"sub\">  \n" + '\n' + "<i class=\"fa fa-stethoscope fa-2x\" aria-hidden=\"true\"></i> ").append(getString(R.string.diagnosis_details)).append("</font>\n").append('\n').append('\n').append('\n').append('\n').append(' ').append(str1.toString()).append('\n').append('\n').append('\n').append(' ').append(str2.toString()).append('\n').append('\n').append("<br/>\n").append("<!--------------------------------------------------------------------->\n").append("  ").append(str3.toString()).append('\n').append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                // "<font class=\"sub\">  " + getString(R.string.respiratory_system) + "</font>\n" +
                '\n').append('\n').append('\n').append(' ').append(str4.toString()).append('\n').append('\n').append('\n').append("<!----------------------------------------------------------------->\n").append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //   "<font class=\"sub\">  " + getString(R.string.gastrointestinal_system) + "</font>\n" +
                '\n').append('\n').append('\n').append(' ').append(str5.toString()).append('\n').append('\n').append('\n').append('\n').append("<!----------------------------------------------------------------->\n").append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //  "<font class=\"sub\">  " + getString(R.string.neurology_system) + "</font>\n" +
                " \n").append('\n').append(' ').append(str6.toString()).append('\n').append(" \n").append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //  "<font class=\"sub\">  " + getString(R.string.central_nervous_system) + "</font>\n" +
                '\n').append("").append(str7.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append("<font class=\"sub\">  ").append(getString(R.string.musclo_skeletal_systems)).append("</font>\n").append('\n').append('\n').append("<div class=\"bs-example\">\n").append('\n').append('\n').append("<table style=\"width:100%\" class=\"table\">\n").append("   \n").append("<tbody>\n").append('\n').append('\n').append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.congential_abnormality)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(CongentialAbnormality).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append('\n').append("<tr>\n").append("<td height=\"20\" width=\"50%\" style=\"color:#3d5987;\"> <b>  ").append(getString(R.string.mental_status)).append("</b></td> \n").append("<td height=\"20\" width=\"50%\" style=\"color:#000000;\">:   ").append(Mentalstatus).append("</td>\n").append("</tr>\n").append('\n').append('\n').append(" \n").append("</tbody>\n").append("</table>\n").append('\n').append("</div> \n").append("<!----------------------------------------------------------------->\n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //  "<font class=\"sub\">  " + getString(R.string.locomotor_system) + "</font>\n" +
                '\n').append('\n').append("").append(str8.toString()).append('\n').append(" \n").append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //  "<font class=\"sub\">  " + getString(R.string.renal_system) + "</font>\n" +
                '\n').append("").append(str9.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                // "<font class=\"sub\">  " + getString(R.string.endocrine_system) + " </font>\n" +
                '\n').append('\n').append('\n').append("").append(str10.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                //  "<font class=\"sub\">  " + getString(R.string.clinical_data) + "</font>\n" +
                '\n').append('\n').append('\n').append("").append(str12.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append("<br/>\n").append("<!----------------------------------------------------------------->\n").append('\n').append(
                // "<font class=\"sub\"> "+getString(R.string.pnc_expn)+"</font>\n" +
                '\n').append('\n').append('\n').append("").append(str13.toString()).append('\n').append(" \n").append('\n').append('\n').append("<!----------------------------------------------------------------->\n").append('\n').append(
                // "<font class=\"sub\">  " + getString(R.string.dental_system) + " </font>\n" +
                '\n').append('\n').append('\n').append("").append(str14.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append('\n').append("<!----------------------------------------------------------------->\n").append('\n').append(
                // "<font class=\"sub\">  " + getString(R.string.other_systems) + " </font>\n" +
                '\n').append('\n').append('\n').append("").append(str11.toString()).append('\n').append(" \n").append('\n').append('\n').append('\n').append('\n').append("</body>\n").append("</html>                             ");


        //#######################################################################################################


        db.close();
        Toast.makeText(getActivity(), getString(R.string.casenotes_loaded_successfully), Toast.LENGTH_SHORT).show();
        //MyDynamicToast.successMessage(getActivity(), );

        return values;

    }

    //**********************************************************************************************


    private static class MyWebChromeClient extends WebChromeClient {
    }

    static class WebAppInterface {
        final Context mContext;

        WebAppInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void showToast(String toast) {

        }
    }

    private static boolean contains(String[] arr, String item) {
        for (String n : arr) {
            if (item.equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    private static boolean contains(String[] arr, int item) {
        for (String n : arr) {
            if (String.valueOf(item).equalsIgnoreCase(n)) {
                return true;
            }
        }
        return false;
    }

    private static String convertHTML_DentalCodeAdult(int types, int size, String[] value) {
        StringBuilder result = new StringBuilder();
        if (types == 1) {

            for (int i1 = 1; i1 <= size; i1++) {

                //for (String s : value) {
                boolean status = contains(value, i1);

                if (status) {

                    //Add Red Color
                    result.append("<td bgcolor=\"#f00\" class=\"lefttop\"><span style=\"color: #fff;\">").append(i1).append("</span></td> \n");

                } else {
                    result.append("<td bgcolor=\"#fff\" class=\"lefttop\"><span style=\"color: #000;\">").append(i1).append("</span></td> \n");
                }
                // }


                if (i1 == 8) {
                    break;
                }

            }

            // return result;


        } else {
            for (int i1 = size; size >= 1; i1--) {

                //for (String s : value) {
                boolean status = contains(value, i1);

                if (status) {

                    //Add Red Color
                    result.append("<td bgcolor=\"#f00\" class=\"lefttop\"><span style=\"color: #fff;\">").append(i1).append("</span></td> \n");

                } else {
                    result.append("<td bgcolor=\"#fff\" class=\"lefttop\"><span style=\"color: #000;\">").append(i1).append("</span></td> \n");
                }
                // }

                if (i1 == 1) {
                    break;
                }


            }

            //return result;
        }

        return result.toString();
    }

    private static String convertHTML_DentalCodeChild(String[] orderabccba, String[] value) {

        StringBuilder result = new StringBuilder();


        for (String i1 : orderabccba) {

            //for (String s : value) {
            boolean status = contains(value, i1);

            if (status) {

                //Add Red Color
                result.append("<td bgcolor=\"#f00\" class=\"lefttop\"><span style=\"color: #fff;\">").append(i1).append("</span></td> \n");

            } else {
                result.append("<td bgcolor=\"#fff\" class=\"lefttop\"><span style=\"color: #000;\">").append(i1).append("</span></td> \n");
            }
        }

        return result.toString();
    }

    public static final String checkTrueFalse(String values) {

        return values.equalsIgnoreCase("True") || values.equals("True") ? "Yes" : "No";

    }

    private static final String checkNullEmpty(String val) {
        return val == null || val.isEmpty() || val.equals("null") ? "  -" : val;
    }


    //**********************************************************************************************

}//END
