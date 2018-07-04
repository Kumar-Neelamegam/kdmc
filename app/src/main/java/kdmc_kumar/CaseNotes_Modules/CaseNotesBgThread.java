/*

 */
package kdmc_kumar.CaseNotes_Modules;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;

/**
 * @author user4
 *         Developer name: S.Nabeel Ahamed
 *         Email: nabeel
 *         10-Jul-2015 4:27:29 PM
 */


public class CaseNotesBgThread extends AsyncTask<String, String, String> {
    private final String Patient_Id;
    private final Context context;
    private ProgressDialog pDialog = null;
    private CaseNotes caseNotes = null;
    /**
     * progress dialog to show user that the backup is processing.
     */
    private final ProgressDialog dialog;

    public CaseNotesBgThread(Context context, String PatientId) {
        this.context = context;
        dialog = new ProgressDialog(context);
        this.Patient_Id = PatientId;

    }

    @Override
    protected final void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Loading casenote details...");
        pDialog.show();
    }

    /* (non-Javadoc)
     * @see android.os.AsyncTask#doInBackground(Params[])
     */
    @Override
    protected final String doInBackground(String... params) {

        return null;
    }


    /* (non-Javadoc)
     * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
     */
    @Override
    protected final void onPostExecute(String result) {
        super.onPostExecute(result);
       // caseNotes.UpdateAllValues();
        pDialog.dismiss();
    }


    public static class DiagnosisData {
        String Docid = "";
        String Ptid = "";
        String DiagId = "";
        String pname = "";
        String gender = "";
        String age = "";
        String mobnum = "";
        String refdocname = "";
        String clinicname = "";
        public String Diagnosis = "";
        public String BpS = "";
        public String BpD = "";
        public String FBS = "";
        public String PPS = "";
        public String RBS = "";
        public String PWeight = "";
        public String temperature = "";
        String testresult = "";
        String Ecg = "";
        String Ecgfile = "";
        String Scan = "";
        String Scanfile = "";
        String Test = "";
        String Actdate = "";
        String IsActive = "";
        public String treatmentfor = "";
        String bloodgroup = "";
        String ecgrate = "";
        String ecgrhyrhm = "";
        String ecgpulserate = "";
        String ecgqrs = "";
        String ecgst = "";
        String ecgt = "";
        String ecgaxis = "";
        String ecgbundlebranch = "";
        public String ecgconductiondefects = "";
        String ecgtreadmill = "";
        String imeino = "";
        String docsign = "";
        String Isupdate = "";
        public String height = "";
        public String bmi = "";
        public String xrayremrk = "";


        Cursor c = null;

        public DiagnosisData() {
        }


        final void SetData() {

            //Log.e("Dataa", "SetData");
            if (c == null) {
                //Log.e("c==", "null");
            }

            if (c != null) {
                //Log.e("c!=", "null");
                if (c.moveToFirst()) {
                    do {
                        //Log.e("c!=", "moved to first");

                        String clinicName = c.getString(c.getColumnIndex("clinicname"));
                        //Log.e("Clinic Name", clinicName);

                        Actdate = c.getString(c.getColumnIndex("Actdate"));
                        age = c.getString(c.getColumnIndex("age"));
                        // TODO: 3/9/2017 Adding Blood Presser value init
                        BpS = c.getString(c.getColumnIndex("BpS"));
                        bloodgroup = c.getString(c.getColumnIndex("bloodgroup"));
                        bmi = c.getString(c.getColumnIndex("bmi"));
                        BpD = c.getString(c.getColumnIndex("BpD"));
                        clinicname = c.getString(c.getColumnIndex("clinicname"));
                        DiagId = c.getString(c.getColumnIndex("DiagId"));
                        Diagnosis = c.getString(c.getColumnIndex("Diagnosis"));
                        Docid = c.getString(c.getColumnIndex("Docid"));
                        docsign = c.getString(c.getColumnIndex("docsign"));
                        Ecg = c.getString(c.getColumnIndex("Ecg"));
                        ecgaxis = c.getString(c.getColumnIndex("ecgaxis"));
                        ecgbundlebranch = c.getString(c.getColumnIndex("ecgbundlebranch"));
                        Ecgfile = c.getString(c.getColumnIndex("Ecgfile"));
                        ecgpulserate = c.getString(c.getColumnIndex("ecgpulserate"));
                        ecgqrs = c.getString(c.getColumnIndex("ecgqrs"));
                        ecgrate = c.getString(c.getColumnIndex("ecgrate"));
                        ecgrhyrhm = c.getString(c.getColumnIndex("ecgrhyrhm"));
                        ecgst = c.getString(c.getColumnIndex("ecgst"));
                        ecgt = c.getString(c.getColumnIndex("ecgt"));
                        ecgtreadmill = c.getString(c.getColumnIndex("ecgtreadmill"));
                        FBS = c.getString(c.getColumnIndex("FBS"));
                        gender = c.getString(c.getColumnIndex("gender"));
                        height = c.getString(c.getColumnIndex("height"));
                        imeino = c.getString(c.getColumnIndex("imeino"));
                        IsActive = c.getString(c.getColumnIndex("IsActive"));
                        Isupdate = c.getString(c.getColumnIndex("Isupdate"));
                        mobnum = c.getString(c.getColumnIndex("mobnum"));
                        PPS = c.getString(c.getColumnIndex("PPS"));
                        pname = c.getString(c.getColumnIndex("pname"));
                        Ptid = c.getString(c.getColumnIndex("Ptid"));
                        PWeight = c.getString(c.getColumnIndex("PWeight"));
                        RBS = c.getString(c.getColumnIndex("RBS"));
                        refdocname = c.getString(c.getColumnIndex("refdocname"));
                        Scan = c.getString(c.getColumnIndex("Scan"));
                        Scanfile = c.getString(c.getColumnIndex("Scanfile"));
                        temperature = c.getString(c.getColumnIndex("temperature"));
                        Test = c.getString(c.getColumnIndex("Test"));
                        testresult = c.getString(c.getColumnIndex("testresult"));
                        treatmentfor = c.getString(c.getColumnIndex("treatmentfor"));

                    } while (c.moveToNext());
                }
            }

        }
    }


    public static class GeneralExaminationData {
        String DiagId = "";
        String ptid = "";
        String pname = "";
        String docid = "";
        String Actdate = "";
        String IsActive = "";
        String pagegen = "";
        String Isupdate = "";
        String Anaemic = "";
        String Icterus = "";
        String Stenosis = "";
        String Clubbing = "";
        String Limbphantom = "";
        String Vericoveins = "";
        String Pedal_edema = "";
        String built = "";
        String extra_generalexam = "";

        String skin_infection = "", short_stat = "", pog = "", pallor = "", oedema = "", fundalheight = "", lie = "", fetalmovement = "", fetalheight = "", pv = "", anycompl = "";

        Cursor c = null;

        public GeneralExaminationData() {
        }

        final void SetData() {

            //Log.e("GeneralExaminationData Dataa", "SetData");
            if (c == null) {
                //Log.e("GeneralExaminationData c==", "null");
            }

            if (c != null) {
                //Log.e("GeneralExaminationData c!=", "null");
                if (c.moveToFirst()) {

                    do {


                        Actdate = c.getString(c.getColumnIndex("Actdate"));
                        Anaemic = c.getString(c.getColumnIndex("Anaemic"));
                        Clubbing = c.getString(c.getColumnIndex("Clubbing"));
                        DiagId = c.getString(c.getColumnIndex("DiagId"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        Icterus = c.getString(c.getColumnIndex("Icterus"));
                        IsActive = c.getString(c.getColumnIndex("IsActive"));
                        Isupdate = c.getString(c.getColumnIndex("Isupdate"));
                        Limbphantom = c.getString(c.getColumnIndex("Limbphantom"));
                        pagegen = c.getString(c.getColumnIndex("pagegen"));
                        Pedal_edema = c.getString(c.getColumnIndex("Pedal_edema"));
                        pname = c.getString(c.getColumnIndex("pname"));
                        ptid = c.getString(c.getColumnIndex("ptid"));
                        Stenosis = c.getString(c.getColumnIndex("Stenosis"));
                        Vericoveins = c.getString(c.getColumnIndex("Vericoveins"));
                        built = c.getString(c.getColumnIndex("built"));
                        extra_generalexam = c.getString(c.getColumnIndex("extra_generalexam"));

                        skin_infection = c.getString(c.getColumnIndex("skin_infection"));
                        short_stat = c.getString(c.getColumnIndex("short_stat"));
                        pog = c.getString(c.getColumnIndex("pog"));
                        pallor = c.getString(c.getColumnIndex("pallor"));
                        oedema = c.getString(c.getColumnIndex("oedema"));
                        fundalheight = c.getString(c.getColumnIndex("fundalheight"));
                        lie = c.getString(c.getColumnIndex("lie"));
                        fetalmovement = c.getString(c.getColumnIndex("fetalmovement"));
                        fetalheight = c.getString(c.getColumnIndex("fetalheight"));
                        pv = c.getString(c.getColumnIndex("pv"));
                        anycompl = c.getString(c.getColumnIndex("anycompl"));


                    }

                    while (c.moveToNext());
                }
            }


        }
    }


    public static class CardioValues {
        String DiagId = "";
        String ptid = "";
        String pname = "";
        String docid = "";
        String Actdate = "";
        String IsActive = "";
        String pagegen = "";
        String Isupdate = "";
        public String Beat = "";
        public String Murmur = "";
        public String Pericardial_Rub = "";
        public String Pulserate = "";
        public String heartrate = "";
        Cursor c = null;

        public CardioValues() {
        }

        final void SetData() {

            //Log.e("CardioValues Dataa", "SetData");
            if (c == null) {
                //Log.e("CardioValues c==", "null");
            }

            if (c != null) {
                //Log.e("CardioValues c!=", "null");
                if (c.moveToFirst()) {

                    do {


                        Actdate = c.getString(c.getColumnIndex("Actdate"));
                        Beat = c.getString(c.getColumnIndex("Beat"));
                        Murmur = c.getString(c.getColumnIndex("Murmur"));
                        DiagId = c.getString(c.getColumnIndex("DiagId"));
                        docid = c.getString(c.getColumnIndex("docid"));
                        Pericardial_Rub = c.getString(c.getColumnIndex("Pericardial_Rub"));
                        IsActive = c.getString(c.getColumnIndex("IsActive"));
                        Isupdate = c.getString(c.getColumnIndex("Isupdate"));
                        Pulserate = c.getString(c
                                .getColumnIndex("Pulserate"));
                        pagegen = c.getString(c.getColumnIndex("pagegen"));
                        heartrate = c.getString(c
                                .getColumnIndex("heartrate"));
                        pname = c.getString(c.getColumnIndex("pname"));
                        ptid = c.getString(c.getColumnIndex("ptid"));


                    }

                    while (c.moveToNext());
                }
            }


        }
    }


    public static class RespiratoryValues {
        public String Breathsound = "";
        public String Trachea = "";
        public String Kyphosis_Scoliosis = "";
        public String Crepitation = "";
        public String Note = "";
        public String shapeofchest = "";
        public String spo2 = "";
        Cursor c = null;
        String Bronchi = "";
        String Pulserate = "";

        public RespiratoryValues() {
        }

        final void SetData() {
            //Log.e("RespiratoryValues Dataa", "SetData");
            if (c == null) {
                //Log.e("RespiratoryValues c==", "null");
            }

            if (c != null) {
                //Log.e("RespiratoryValues c!=", "null");
                if (c.moveToFirst()) {

                    do {
                        Breathsound = c.getString(c.getColumnIndex("Breathsound"));
                        Trachea = c.getString(c.getColumnIndex("Trachea"));
                        Kyphosis_Scoliosis = c.getString(c.getColumnIndex("Kyphosis_Scoliosis"));
                        Crepitation = c.getString(c.getColumnIndex("Crepitation"));
                        Bronchi = c.getString(c.getColumnIndex("Bronchi"));
                        Pulserate = c.getString(c.getColumnIndex("Pulserate"));
                        Note = c.getString(c.getColumnIndex("Note"));
                        shapeofchest = c.getString(c.getColumnIndex("shapeofchest"));
                        spo2 = c.getString(c.getColumnIndex("spo2"));


                    }

                    while (c.moveToNext());
                }
            }

        }
    }

    public static class OtherSystemValues {
        public String Othersystem = "";
        public String AdditionalInfo = "";


        Cursor c = null;

        public OtherSystemValues() {
        }

        final void SetData() {
            //Log.e("OtherSystemValues Dataa", "SetData");
            if (c == null) {
                //Log.e("OtherSystemValues c==", "null");
            }

            if (c != null) {
                //Log.e("OtherSystemValues c!=", "null");
                if (c.moveToFirst()) {

                    do {
                        Othersystem = c.getString(c.getColumnIndex("Othersystem"));
                        AdditionalInfo = c.getString(c.getColumnIndex("AdditionalInfo"));

                    }

                    while (c.moveToNext());
                }
            }

        }
    }

    public static class ClinicalDataValues {
        public String Heamoglobin = "";
        public String wbc = "";
        public String rbc = "";
        public String esr = "";
        public String polymorphs = "";
        public String lymphocytes = "";
        public String monocytes = "";
        public String basophils = "";
        public String eosinophils = "";
        public String urea = "";
        public String creatinine = "";
        public String UricAcid = "";
        public String Sodium = "";
        public String Potassium = "";
        public String Chloride = "";
        public String Bicarbonate = "";
        public String TotalCholesterol = "";
        public String LDL = "";
        public String HDL = "";
        public String VLDL = "";
        public String Triglycerides = "";
        public String Serumbilirubin = "";
        public String Direct = "";
        public String Indirect = "";
        public String Totalprotein = "";
        public String Albumin = "";
        public String Globulin = "";
        public String SGOT = "";
        public String SGPT = "";
        public String AlkalinePhosphatase = "";
        public String FreeT3 = "";
        public String FreeT4 = "";
        public String TSH = "";

        Cursor c = null;

        public ClinicalDataValues() {
        }

        final void SetData() {
            //Log.e("ClinicalDataValues Data", "SetData");
            if (c == null) {
                //Log.e("ClinicalDataValues c==", "null");
            }

            if (c != null) {
                //Log.e("ClinicalDataValues c!=", "null");
                if (c.moveToFirst()) {

                    do {

                        Heamoglobin = c.getString(c.getColumnIndex("Heamoglobin"));
                        wbc = c.getString(c.getColumnIndex("wbc"));
                        rbc = c.getString(c.getColumnIndex("rbc"));
                        esr = c.getString(c.getColumnIndex("esr"));
                        polymorphs = c.getString(c.getColumnIndex("polymorphs"));
                        lymphocytes = c.getString(c.getColumnIndex("lymphocytes"));
                        monocytes = c.getString(c.getColumnIndex("monocytes"));
                        basophils = c.getString(c.getColumnIndex("basophils"));
                        eosinophils = c.getString(c.getColumnIndex("eosinophils"));
                        urea = c.getString(c.getColumnIndex("urea"));
                        creatinine = c.getString(c.getColumnIndex("creatinine"));
                        UricAcid = c.getString(c.getColumnIndex("UricAcid"));

                        Sodium = c.getString(c.getColumnIndex("Sodium"));
                        Potassium = c.getString(c.getColumnIndex("Potassium"));
                        Chloride = c.getString(c.getColumnIndex("Chloride"));
                        Bicarbonate = c.getString(c.getColumnIndex("Bicarbonate"));
                        TotalCholesterol = c.getString(c.getColumnIndex("TotalCholesterol"));
                        LDL = c.getString(c.getColumnIndex("LDL"));
                        HDL = c.getString(c.getColumnIndex("HDL"));
                        VLDL = c.getString(c.getColumnIndex("VLDL"));
                        Triglycerides = c.getString(c.getColumnIndex("Triglycerides"));
                        Serumbilirubin = c.getString(c.getColumnIndex("Serumbilirubin"));
                        Direct = c.getString(c.getColumnIndex("Direct"));
                        Indirect = c.getString(c.getColumnIndex("Indirect"));
                        Totalprotein = c.getString(c.getColumnIndex("Totalprotein"));
                        Albumin = c.getString(c.getColumnIndex("Albumin"));
                        Globulin = c.getString(c.getColumnIndex("Globulin"));
                        SGOT = c.getString(c.getColumnIndex("SGOT"));
                        SGPT = c.getString(c.getColumnIndex("SGPT"));
                        AlkalinePhosphatase = c.getString(c.getColumnIndex("AlkalinePhosphatase"));
                        FreeT3 = c.getString(c.getColumnIndex("FreeT3"));
                        FreeT4 = c.getString(c.getColumnIndex("FreeT4"));
                        TSH = c.getString(c.getColumnIndex("TSH"));

                    }

                    while (c.moveToNext());
                }
            }

        }
    }

    public static class EndocrineValues {
        public String Endocrine = "";


        Cursor c = null;

        public EndocrineValues() {
        }

        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (c.moveToFirst()) {

                    do {
                        Endocrine = c.getString(c.getColumnIndex("Endocrine"));


                    }

                    while (c.moveToNext());
                }
            }

        }
    }

    public static class RenalSystemValues {

        public String dysuria = "";
        public String pyuria = "";
        public String haematuria = "";
        public String oliguria = "";
        public String polyuria = "";
        public String anuria = "";
        public String nocturia = "";
        public String urethraldischarge = "";
        public String prostate = "";

        Cursor c = null;

        public RenalSystemValues() {
        }

        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (c.moveToFirst()) {

                    do {

                        dysuria = c.getString(c.getColumnIndex("dysuria"));
                        pyuria = c.getString(c.getColumnIndex("pyuria"));
                        haematuria = c.getString(c.getColumnIndex("haematuria"));
                        oliguria = c.getString(c.getColumnIndex("oliguria"));
                        polyuria = c.getString(c.getColumnIndex("polyuria"));
                        anuria = c.getString(c.getColumnIndex("anuria"));
                        nocturia = c.getString(c.getColumnIndex("nocturia"));
                        urethraldischarge = c.getString(c.getColumnIndex("urethraldischarge"));
                        prostate = c.getString(c.getColumnIndex("prostate"));

                    }

                    while (c.moveToNext());
                }
            }

        }
    }


    public static class LocomotorSystemValues {
        public String symmetry = "";
        public String smooth_movement = "";
        public String arms_swing = "";
        public String pelvic_tilt = "";
        public String stride_length = "";
        public String cervical_lordosis = "";
        public String lumbar_lordosis = "";
        public String kyphosis = "";
        public String scoliosis = "";
        public String llswelling = "";
        String lldeformity = "";
        public String lllimbshortening = "";
        public String llmuscle_wasting = "";
        public String llremarks = "";
        public String ulswelling = "";
        public String uldeformity = "";
        public String ullimbshortening = "";
        String ulmuscle_wasting = "";
        public String ulremarks = "";


        Cursor c = null;

        public LocomotorSystemValues() {
        }

        final void SetData() {
            //Log.e("LocomotorSystemValues Dataa", "SetData");
            if (c == null) {
                //Log.e("LocomotorSystemValues c==", "null");
            }

            if (c != null) {
                //Log.e("LocomotorSystemValues c!=", "null");
                if (c.moveToFirst()) {

                    do {
                        symmetry = c.getString(c.getColumnIndex("symmetry"));
                        smooth_movement = c.getString(c.getColumnIndex("smooth_movement"));
                        arms_swing = c.getString(c.getColumnIndex("arms_swing"));
                        pelvic_tilt = c.getString(c.getColumnIndex("pelvic_tilt"));
                        stride_length = c.getString(c.getColumnIndex("stride_length"));
                        cervical_lordosis = c.getString(c.getColumnIndex("cervical_lordosis"));
                        lumbar_lordosis = c.getString(c.getColumnIndex("lumbar_lordosis"));
                        kyphosis = c.getString(c.getColumnIndex("kyphosis"));
                        scoliosis = c.getString(c.getColumnIndex("scoliosis"));
                        llswelling = c.getString(c.getColumnIndex("llswelling"));
                        lldeformity = c.getString(c.getColumnIndex("lldeformity"));
                        lllimbshortening = c.getString(c.getColumnIndex("lllimbshortening"));
                        llmuscle_wasting = c.getString(c.getColumnIndex("llmuscle_wasting"));
                        llremarks = c.getString(c.getColumnIndex("llremarks"));
                        ulswelling = c.getString(c.getColumnIndex("ulswelling"));
                        uldeformity = c.getString(c.getColumnIndex("uldeformity"));
                        ullimbshortening = c.getString(c.getColumnIndex("ullimbshortening"));
                        ulmuscle_wasting = c.getString(c.getColumnIndex("ulmuscle_wasting"));
                        ulremarks = c.getString(c.getColumnIndex("ulremarks"));


                    }

                    while (c.moveToNext());
                }
            }

        }
    }


    public static class NeurologySystemValues {


        Cursor c = null;
        Cursor c1 = null;
        public String Pupilsize = "";
        public String Speech = "";
        public String Carodit = "";
        public String Amnesia = "";

        public String Apraxia = "";
        public String Cognitive_Function = "";
        public String Bulk = "";

        public String Tone = "";
        public String Power_LUL = "";
        public String Power_RUL = "";

        public String Power_LLL = "";
        public String Power_RLL = "";
        public String Sensory = "";
        public String Reflexes_Corneal = "";

        public String Reflexes_Biceps = "";
        public String Reflexes_Triceps = "";
        public String Reflexes_Supinator = "";

        public String Reflexes_Knee = "";
        public String Reflexes_Ankle = "";
        public String Reflexes_Plantor = "";

        public String congentail_abnormality = "";
        public String mentalstatus = "";

        public String oriented = "";
        public String neckrigidity = "";
        public String confused = "";
        public String exaggerated = "";
        public String extensor = "";
        public String gsscore = "";
        public String incomprehensible = "";
        public String depressed = "";
        public String flexor = "";
        public String coma = "";

        public NeurologySystemValues() {
        }


        final void SetData() {
            //Log.e("EndocrineValues Dataa", "SetData");
            if (c == null) {
                //Log.e("EndocrineValues c==", "null");
            }

            if (c != null) {
                //Log.e("EndocrineValues c!=", "null");
                if (c.moveToFirst()) {

                    do {

                        Pupilsize = c.getString(c.getColumnIndex("Pupilsize"));
                        Speech = c.getString(c.getColumnIndex("Speech"));
                        Carodit = c.getString(c.getColumnIndex("Carodit"));
                        Amnesia = c.getString(c.getColumnIndex("Amnesia"));
                        Apraxia = c.getString(c.getColumnIndex("Apraxia"));
                        Cognitive_Function = c.getString(c.getColumnIndex("Cognitive_Function"));
                        Bulk = c.getString(c.getColumnIndex("Bulk"));
                        Tone = c.getString(c.getColumnIndex("Tone"));
                        Power_LUL = c.getString(c.getColumnIndex("Power_LUL"));
                        Power_RUL = c.getString(c.getColumnIndex("Power_RUL"));
                        Power_LLL = c.getString(c.getColumnIndex("Power_LLL"));
                        Power_RLL = c.getString(c.getColumnIndex("Power_RLL"));
                        Sensory = c.getString(c.getColumnIndex("Sensory"));
                        Reflexes_Corneal = c.getString(c.getColumnIndex("Reflexes_Corneal"));
                        Reflexes_Biceps = c.getString(c.getColumnIndex("Reflexes_Biceps"));
                        Reflexes_Triceps = c.getString(c.getColumnIndex("Reflexes_Triceps"));
                        Reflexes_Supinator = c.getString(c.getColumnIndex("Reflexes_Supinator"));
                        Reflexes_Knee = c.getString(c.getColumnIndex("Reflexes_Knee"));
                        Reflexes_Ankle = c.getString(c.getColumnIndex("Reflexes_Ankle"));
                        Reflexes_Plantor = c.getString(c.getColumnIndex("Reflexes_Plantor"));
                        congentail_abnormality = c.getString(c.getColumnIndex("congentail_abnormality"));
                        mentalstatus = c.getString(c.getColumnIndex("mentalstatus"));

                    }

                    while (c.moveToNext());
                }
            }


            //Log.e("CNSValues Dataa", "SetData");
            if (c1 == null) {
                //Log.e("CNSValues c1==", "null");
            }

            if (c1 != null) {
                //Log.e("CNS Values c1!=", "null");
                if (c1.moveToFirst()) {

                    do {

                        oriented = c1.getString(c1.getColumnIndex("oriented"));
                        neckrigidity = c1.getString(c1.getColumnIndex("neckrigidity"));
                        confused = c1.getString(c1.getColumnIndex("confused"));
                        exaggerated = c1.getString(c1.getColumnIndex("exaggerated"));
                        extensor = c1.getString(c1.getColumnIndex("extensor"));
                        gsscore = c1.getString(c1.getColumnIndex("gsscore"));
                        incomprehensible = c1.getString(c1.getColumnIndex("incomprehensible"));
                        depressed = c1.getString(c1.getColumnIndex("depressed"));
                        flexor = c1.getString(c1.getColumnIndex("flexor"));
                        coma = c1.getString(c1.getColumnIndex("coma"));

                    }

                    while (c1.moveToNext());
                }
            }

        }
    }


    public static class GastroIntestinalSystem {
        Cursor c = null;
        public String Abdomen = "";
        public String Bowelsound = "";
        public String Spleen = "";
        public String Liver = "";
        public String PalpableMasses = "";
        public String Hernial = "";
        public String shapeofabdomen = "";
        public String Visible_Pulsations = "";
        public String Visual_Peristalsis = "";
        public String Abdominal_Palpation = "";
        public String Splenomegaly = "";
        public String Hepatomegaly = "";
        public String organomegely = "";
        public String freefuild = "";
        public String distension = "";
        public String tenderness = "";
        public String scrotum = "";

        public GastroIntestinalSystem() {
        }


        final void SetData() {


            //Log.e("LocomotorSystemValues Dataa", "SetData");
            if (c == null) {
                //Log.e("LocomotorSystemValues c==", "null");
            }

            if (c != null) {
                //Log.e("LocomotorSystemValues c!=", "null");
                if (c.moveToFirst()) {

                    do {
                        Abdomen = c.getString(c.getColumnIndex("Abdomen"));
                        Bowelsound = c.getString(c.getColumnIndex("Bowelsound"));
                        Spleen = c.getString(c.getColumnIndex("Spleen"));
                        Liver = c.getString(c.getColumnIndex("Liver"));
                        PalpableMasses = c.getString(c.getColumnIndex("PalpableMasses"));
                        Hernial = c.getString(c.getColumnIndex("Hernial"));
                        shapeofabdomen = c.getString(c.getColumnIndex("shapeofabdomen"));
                        Visible_Pulsations = c.getString(c.getColumnIndex("Visible_Pulsations"));
                        Visual_Peristalsis = c.getString(c.getColumnIndex("Visual_Peristalsis"));
                        Abdominal_Palpation = c.getString(c.getColumnIndex("Abdominal_Palpation"));
                        Splenomegaly = c.getString(c.getColumnIndex("Splenomegaly"));
                        Hepatomegaly = c.getString(c.getColumnIndex("Hepatomegaly"));
                        organomegely = c.getString(c.getColumnIndex("organomegely"));
                        freefuild = c.getString(c.getColumnIndex("freefuild"));
                        distension = c.getString(c.getColumnIndex("distension"));
                        tenderness = c.getString(c.getColumnIndex("tenderness"));
                        scrotum = c.getString(c.getColumnIndex("scrotum"));


                    }

                    while (c.moveToNext());
                }
            }

        }

    }


    //*********************************************************************************************
    public static class PNCValues {

        public String pnc_details = "";
        public String ppc_details = "";
        public String cob_details = "";

        Cursor c = null;

        public PNCValues() {
        }

        final void SetData() {


            //Log.e("PNCValues Dataa", "SetData");
            if (c == null) {
                //Log.e("PNCValues c==", "null");
            }

            if (c != null) {
                //Log.e("PNCValues c!=", "null");
                if (c.moveToFirst()) {

                    do {

                        pnc_details = c.getString(c.getColumnIndex("pnc_details"));
                        ppc_details = c.getString(c.getColumnIndex("ppc_details"));
                        cob_details = c.getString(c.getColumnIndex("cob_details"));

                    }

                    while (c.moveToNext());
                }
            }

        }
    }

    //*********************************************************************************************


}
