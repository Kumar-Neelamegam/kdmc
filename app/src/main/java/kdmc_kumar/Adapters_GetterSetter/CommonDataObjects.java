package kdmc_kumar.Adapters_GetterSetter;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Android on 4/6/2017.
 */

public class CommonDataObjects {
    public CommonDataObjects() {
    }

    //**********************************************************************************************
    public static class Fav_MedicineList {

        String Medicine;
        String Id;
        public Fav_MedicineList(String medicine, String id) {
            Medicine = medicine;
            Id = id;
        }

        public final String getMedicine() {
            return Medicine;
        }

        public final void setMedicine(String medicine) {
            Medicine = medicine;
        }

        public final String getId() {
            return Id;
        }

        public final void setId(String id) {
            Id = id;
        }
    }


    //**********************************************************************************************

    public static class Fav_TestList {


        String TestName;
        String SubTestName;
        String Id;

        public Fav_TestList(String id, String testName, String subTestName) {

            Id = id;
            TestName = testName;
            SubTestName = subTestName;
        }

        public final String getTestName() {
            return TestName;
        }

        public final void setTestName(String testName) {
            TestName = testName;
        }

        public final String getSubTestName() {
            return SubTestName;
        }

        public final void setSubTestName(String subTestName) {
            SubTestName = subTestName;
        }

        public final String getId() {
            return Id;
        }

        public final void setId(String id) {
            Id = id;
        }


    }
    //**********************************************************************************************

    static class Item {

        private String title;
        private String description;


        public Item(String title, String description) {
            super();
            this.title = title;
            this.description = description;
        }

        public final String getTitle() {
            return title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }

        public final String getDescription() {
            return description;
        }

        public final void setDescription(String description) {
            this.description = description;
        }


    }
    //**********************************************************************************************

    public static class ObjectDrawerItem {

        public final int icon;
        public final String name;

        // Constructor.
        public ObjectDrawerItem(int icon, String name) {

            this.icon = icon;
            this.name = name;
        }
    }

    //**********************************************************************************************

    /**
     * Online Consulatation Reports
     */
    public static class OnlineConsultationReport {
        String ReportName;
        String ReportPhoto;
        public OnlineConsultationReport(String reportName, String reportPhoto) {
            ReportName = reportName;
            ReportPhoto = reportPhoto;
        }

        public final String getReportName() {
            return ReportName;
        }

        public final void setReportName(String reportName) {
            ReportName = reportName;
        }

        public final String getReportPhoto() {
            return ReportPhoto;
        }

        public final void setReportPhoto(String reportPhoto) {
            ReportPhoto = reportPhoto;
        }
    }
    //**********************************************************************************************

    /**
     * Created by Ponnusamy M on 4/6/2017.
     */

    public static class DiagnosisItem {

        String S_No, Doctor, OPerationNotes, Position, OperationProcedure, PostOPerativeDiagnosis, PostOpertiveinstruction, Closure, PreOperativeDiagnosis;
        String Remark_Image;

        public DiagnosisItem(String s_No, String doctor, String OPerationNotes, String position, String operationProcedure, String postOPerativeDiagnosis, String postOpertiveinstruction, String closure, String preOperativeDiagnosis, String remark_Image) {
            S_No = s_No;
            Doctor = doctor;
            this.OPerationNotes = OPerationNotes;
            Position = position;
            OperationProcedure = operationProcedure;
            PostOPerativeDiagnosis = postOPerativeDiagnosis;
            PostOpertiveinstruction = postOpertiveinstruction;
            Closure = closure;
            PreOperativeDiagnosis = preOperativeDiagnosis;
            Remark_Image = remark_Image;
        }

        public final String getS_No() {
            return S_No;
        }

        public final void setS_No(String s_No) {
            S_No = s_No;
        }

        public final String getDoctor() {
            return Doctor;
        }

        public final void setDoctor(String doctor) {
            Doctor = doctor;
        }

        public final String getOPerationNotes() {
            return OPerationNotes;
        }

        public final void setOPerationNotes(String OPerationNotes) {
            this.OPerationNotes = OPerationNotes;
        }

        public final String getPosition() {
            return Position;
        }

        public final void setPosition(String position) {
            Position = position;
        }

        public final String getOperationProcedure() {
            return OperationProcedure;
        }

        public final void setOperationProcedure(String operationProcedure) {
            OperationProcedure = operationProcedure;
        }

        public final String getPostOPerativeDiagnosis() {
            return PostOPerativeDiagnosis;
        }

        public final void setPostOPerativeDiagnosis(String postOPerativeDiagnosis) {
            PostOPerativeDiagnosis = postOPerativeDiagnosis;
        }

        public final String getPostOpertiveinstruction() {
            return PostOpertiveinstruction;
        }

        public final void setPostOpertiveinstruction(String postOpertiveinstruction) {
            PostOpertiveinstruction = postOpertiveinstruction;
        }

        public final String getClosure() {
            return Closure;
        }

        public final void setClosure(String closure) {
            Closure = closure;
        }

        public final String getPreOperativeDiagnosis() {
            return PreOperativeDiagnosis;
        }

        public final void setPreOperativeDiagnosis(String preOperativeDiagnosis) {
            PreOperativeDiagnosis = preOperativeDiagnosis;
        }

        public final String getRemark_Image() {
            return Remark_Image;
        }

        public final void setRemark_Image(String remark_Image) {
            Remark_Image = remark_Image;
        }
    }


    //**********************************************************************************************
    public static class InjectionGetSet {

        String InjectionName = "";
        String Dosage = "";
        String Quantity = "";
        String InjectionId ="";

        public InjectionGetSet() {
            this.InjectionId = InjectionId;
            this.InjectionName = InjectionName;
            this.Dosage = Dosage;
            this.Quantity = Quantity;

        }

        public final String getInjectionId() {
            return InjectionId;
        }

        public final void setInjectionId(String injectionId) {
            InjectionId = injectionId;
        }

        public final String getInjectionName() {
            return InjectionName;
        }

        public final void setInjectionName(String injectionName) {
            InjectionName = injectionName;
        }

        public final String getDosage() {
            return Dosage;
        }

        public final void setDosage(String dosage) {
            Dosage = dosage;
        }

        public final String getQuantity() {
            return Quantity;
        }

        public final void setQuantity(String quantity) {
            Quantity = quantity;
        }
    }


    //**********************************************************************************************

    /**
     * Created by Ponnusamy M on 4/19/2017.
     */

    public static class MedicineGetSet {

        String Medicine_Name = "";
        String Morning_Value = "";
        String Afternoon_Value = "";
        String Evening_Value = "";
        String Night_Value = "";
        String Intake_Value = "";
        String Days_Value = "";

        String Medicine_Type = "";

        public MedicineGetSet() {

        }

        public MedicineGetSet(String medicine_Type, String medicine_Name, String morning_Value, String afternoon_Value, String evening_Value, String night_Value, String intake_Value, String days_Value) {
            Medicine_Type = medicine_Type;
            Medicine_Name = medicine_Name;
            Morning_Value = morning_Value;
            Evening_Value = evening_Value;
            Afternoon_Value = afternoon_Value;
            Night_Value = night_Value;
            Intake_Value = intake_Value;
            Days_Value = days_Value;
        }


        public final String getMedicine_Type() {
            return Medicine_Type;
        }

        public final void setMedicine_Type(String medicine_Type) {
            Medicine_Type = medicine_Type;
        }

        public final String getAfternoon_Value() {
            return Afternoon_Value;
        }

        public final void setAfternoon_Value(String afternoon_Value) {
            Afternoon_Value = afternoon_Value;
        }

        public final String getMedicine_Name() {
            return Medicine_Name;
        }

        public final void setMedicine_Name(String medicine_Name) {
            Medicine_Name = medicine_Name;
        }

        public final String getMorning_Value() {
            return Morning_Value;
        }

        public final void setMorning_Value(String morning_Value) {
            Morning_Value = morning_Value;
        }

        public final String getEvening_Value() {
            return Evening_Value;
        }

        public final void setEvening_Value(String evening_Value) {
            Evening_Value = evening_Value;
        }

        public final String getNight_Value() {
            return Night_Value;
        }

        public final void setNight_Value(String night_Value) {
            Night_Value = night_Value;
        }

        public final String getIntake_Value() {
            return Intake_Value;
        }

        public final void setIntake_Value(String intake_Value) {
            Intake_Value = intake_Value;
        }

        public final String getDays_Value() {
            return Days_Value;
        }

        public final void setDays_Value(String days_Value) {
            Days_Value = days_Value;
        }


    }
    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/17/2017.
     */

    public static class MyPatientGetSet {
        public boolean IsOnlinePatient = false;
        public String IsLabReport = "0";
        private String Patient_Name;
        private String Patient_Id;
        private String Patient_Age;
        private String Patient_Gender;
        private String Patient_Image;
        private String Patient_Prefix;

        public MyPatientGetSet(String patient_prefix, String patient_Name, String patient_Id, String patient_Age, String patient_Gender, String Patient_Image) {
            Patient_Prefix = patient_prefix;
            Patient_Name = patient_Name;
            Patient_Id = patient_Id;
            Patient_Age = patient_Age;
            Patient_Gender = patient_Gender;
            this.Patient_Image = Patient_Image;

        }

        public final String getPatient_Prefix() {
            return Patient_Prefix;
        }

        public final void setPatient_Prefix(String patient_Prefix) {
            Patient_Prefix = patient_Prefix;
        }

        public final String getPatient_Image() {
            return Patient_Image;
        }

        public final void setPatient_Image(String patient_Image) {
            Patient_Image = patient_Image;
        }

        public final boolean isOnlinePatient() {
            return IsOnlinePatient;
        }

        public final void setOnlinePatient(boolean onlinePatient) {
            IsOnlinePatient = onlinePatient;
        }

        public final String getPatient_Name() {
            return Patient_Name;
        }

        public final void setPatient_Name(String patient_Name) {
            Patient_Name = patient_Name;
        }

        public final String getPatient_Id() {
            return Patient_Id;
        }

        public final void setPatient_Id(String patient_Id) {
            Patient_Id = patient_Id;
        }

        public final String getPatient_Age() {
            return Patient_Age;
        }

        public final void setPatient_Age(String patient_Age) {
            Patient_Age = patient_Age;
        }

        public final String getPatient_Gender() {
            return Patient_Gender;
        }

        public final void setPatient_Gender(String patient_Gender) {
            Patient_Gender = patient_Gender;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Kumar on 4/6/2017.
     */

    public static class OnlineConsultation_DataObjects {

        String Id;
        String Patient_Name;
        String Patient_Id;
        String Patient_Age;
        String Patient_Gender;
        String Patient_Photo;
        String ConsultationId;
        String MedId;

        public OnlineConsultation_DataObjects(String id, String patient_Name, String patient_Id, String patient_Age, String patient_Gender, String patient_Photo, String consultationId, String Medid) {
            Id = id;
            Patient_Name = patient_Name;
            Patient_Id = patient_Id;
            Patient_Age = patient_Age;
            Patient_Gender = patient_Gender;
            Patient_Photo = patient_Photo;
            ConsultationId = consultationId;
            MedId = Medid;
        }

        public final String getMedId() {
            return MedId;
        }

        public final void setMedId(String medId) {
            MedId = medId;
        }

        public final String getId() {
            return Id;
        }

        public final void setId(String id) {
            Id = id;
        }

        public final String getPatient_Name() {
            return Patient_Name;
        }

        public final void setPatient_Name(String patient_Name) {
            Patient_Name = patient_Name;
        }

        public final String getPatient_Id() {
            return Patient_Id;
        }

        public final void setPatient_Id(String patient_Id) {
            Patient_Id = patient_Id;
        }

        public final String getPatient_Age() {
            return Patient_Age;
        }

        public final void setPatient_Age(String patient_Age) {
            Patient_Age = patient_Age;
        }

        public final String getPatient_Gender() {
            return Patient_Gender;
        }

        public final void setPatient_Gender(String patient_Gender) {
            Patient_Gender = patient_Gender;
        }

        public final String getPatient_Photo() {
            return Patient_Photo;
        }

        public final void setPatient_Photo(String patient_Photo) {
            Patient_Photo = patient_Photo;
        }

        public final String getConsultationId() {
            return ConsultationId;
        }

        public final void setConsultationId(String consultationId) {
            ConsultationId = consultationId;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/6/2017.
     */

    public static class OperationItem {
        String SerialNo = "";
        String Operation_name = "";
        String Operation_date = "";
        String From_time = "";
        String To_Time = "";
        String Reference_doc = "";

        public OperationItem(String serialNo, String operation_name, String operation_date, String from_time, String to_Time, String reference_doc) {
            SerialNo = serialNo;
            Operation_name = operation_name;
            Operation_date = operation_date;
            From_time = from_time;
            To_Time = to_Time;
            Reference_doc = reference_doc;
        }

        public final String getSerialNo() {
            return SerialNo;
        }

        public final void setSerialNo(String serialNo) {
            SerialNo = serialNo;
        }

        public final String getOperation_name() {
            return Operation_name;
        }

        public final void setOperation_name(String operation_name) {
            Operation_name = operation_name;
        }

        public final String getOperation_date() {
            return Operation_date;
        }

        public final void setOperation_date(String operation_date) {
            Operation_date = operation_date;
        }

        public final String getFrom_time() {
            return From_time;
        }

        public final void setFrom_time(String from_time) {
            From_time = from_time;
        }

        public final String getTo_Time() {
            return To_Time;
        }

        public final void setTo_Time(String to_Time) {
            To_Time = to_Time;
        }

        public final String getReference_doc() {
            return Reference_doc;
        }

        public final void setReference_doc(String reference_doc) {
            Reference_doc = reference_doc;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/7/2017.
     */

    public static class PrescriptionItem {
        String Medicine_Name, Interval, Frequency, Duration, Doctorname, Sno;

        public PrescriptionItem(String sno, String medicine_Name, String interval, String frequency, String duration, String doctorname) {
            this.Sno = sno;
            Medicine_Name = medicine_Name;
            Interval = interval;
            Frequency = frequency;
            Duration = duration;
            Doctorname = doctorname;
        }

        public final String getSno() {
            return Sno;
        }

        public final void setSno(String sno) {
            Sno = sno;
        }

        public final String getMedicine_Name() {
            return Medicine_Name;
        }

        public final void setMedicine_Name(String medicine_Name) {
            Medicine_Name = medicine_Name;
        }

        public final String getInterval() {
            return Interval;
        }

        public final void setInterval(String interval) {
            Interval = interval;
        }

        public final String getFrequency() {
            return Frequency;
        }

        public final void setFrequency(String frequency) {
            Frequency = frequency;
        }

        public final String getDuration() {
            return Duration;
        }

        public final void setDuration(String duration) {
            Duration = duration;
        }

        public final String getDoctorname() {
            return Doctorname;
        }

        public final void setDoctorname(String doctorname) {
            Doctorname = doctorname;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/8/2017.
     */

    public static class TestItem {
        String Report_Name = "";
        String Report_Type = "";
        String Report_Date = "";
        String Report_Image = "";
        String Report_Summary = "";

        public TestItem(String report_Name, String report_Type, String report_Date, String report_Image, String report_Summary) {
            Report_Name = report_Name;
            Report_Type = report_Type;
            Report_Date = report_Date;
            Report_Image = report_Image;
            Report_Summary = report_Summary;
        }

        public final String getReport_Summary() {
            return Report_Summary;
        }

        public final void setReport_Summary(String report_Summary) {
            Report_Summary = report_Summary;
        }

        public final String getReport_Name() {
            return Report_Name;
        }

        public final void setReport_Name(String report_Name) {
            Report_Name = report_Name;
        }

        public final String getReport_Type() {
            return Report_Type;
        }

        public final void setReport_Type(String report_Type) {
            Report_Type = report_Type;
        }

        public final String getReport_Date() {
            return Report_Date;
        }

        public final void setReport_Date(String report_Date) {
            Report_Date = report_Date;
        }

        public final String getReport_Image() {
            return Report_Image;
        }

        public final void setReport_Image(String report_Image) {
            Report_Image = report_Image;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/2/2017.
     */

    public static class DataObject {
        String vaccinename = "";
        String schedule = "";
        String givendt = "";
        String weight = "";

        public DataObject() {
        }

        public final String getVaccinename() {
            return vaccinename;
        }

        public final void setVaccinename(String vaccinename) {
            this.vaccinename = vaccinename;
        }

        public final String getSchedule() {
            return schedule;
        }

        public final void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public final String getGivendt() {
            return givendt;
        }

        public final void setGivendt(String givendt) {
            this.givendt = givendt;
        }

        public final String getWeight() {
            return weight;
        }

        public final void setWeight(String weight) {
            this.weight = weight;
        }

    }
    //**********************************************************************************************


    public static class DrugItem {


        String System = "";
        String BrandName = "";
        String PharmaCompany = "";
        String Dosage = "";
        String serverid = "";

        public DrugItem() {
        }

        public final String getSystem() {
            return System;
        }

        public final void setSystem(String system) {
            System = system;
        }

        public final String getBrandName() {
            return BrandName;
        }

        public final void setBrandName(String brandName) {
            BrandName = brandName;
        }

        public final String getPharmaCompany() {
            return PharmaCompany;
        }

        public final void setPharmaCompany(String pharmaCompany) {
            PharmaCompany = pharmaCompany;
        }

        public final String getDosage() {
            return Dosage;
        }

        public final void setDosage(String dosage) {
            Dosage = dosage;
        }

        public final String getServerid() {
            return serverid;
        }

        public final void setServerid(String serverid) {
            this.serverid = serverid;
        }
    }

    //**********************************************************************************************


    public static class RowItem {

        public boolean IsOnlinePatient = false;
        String File_Path;
        public String UniqueId = "";
        private Bitmap imageId;
        private String title;
        private String desc;


        public RowItem(Bitmap imageId, String title, String desc, String File_Path) {
            this.imageId = imageId;
            this.title = title;
            this.desc = desc;
            this.File_Path = File_Path;
        }

        public final String getFile_Path() {
            return File_Path;
        }

        public final void setFile_Path(String file_Path) {
            File_Path = file_Path;
        }

        public final Bitmap getImageId() {
            return imageId;
        }

        public final void setImageId(Bitmap imageId) {

            this.imageId = imageId;
        }

        public final String getDesc() {
            return desc;
        }

        public final void setDesc(String desc) {
            this.desc = desc;
        }

        public final String getTitle() {
            return title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }

        @Override
        public final String toString() {
            return title + '\n' + desc;
        }
    }
    //**********************************************************************************************


    public static class RowItemVaccination {

        private String title;
        private String desc;
        private String gvn;
        private String weight;


        public RowItemVaccination(String title, String desc, String gvn, String weight) {

            this.title = title;
            this.desc = desc;
            this.gvn = gvn;
            this.weight = weight;
        }


        public final String getTitle() {
            return title;
        }

        public final void setTitle(String title) {
            this.title = title;
        }


        public final String getDesc() {
            return desc;
        }

        public final void setDesc(String desc) {
            this.desc = desc;
        }


        public final String getgvn() {
            return gvn;
        }

        public final void setgvn(String gvn) {
            this.gvn = gvn;
        }


        public final String getweight() {
            return weight;
        }

        public final void setweight(String weight) {
            this.weight = weight;
        }

        @Override
        public final String toString() {
            return title + ',' + desc + ',' + gvn + ',' + weight;
        }
    }

    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/24/2017.
     */

    public static class NotificationGetSet {
        String _patientName;
        String _patientId;
        String _mTestId = null;
        ArrayList<NotificationListGetSet> notificationListGetSets;

        public NotificationGetSet(String _patientName, String _patientId, String _mTestId, ArrayList<NotificationListGetSet> notificationListGetSets) {
            this._patientName = _patientName;
            this._patientId = _patientId;
            this._mTestId = _mTestId;
            this.notificationListGetSets = notificationListGetSets;
        }

        public NotificationGetSet(String _patientName, String _patientId, ArrayList<NotificationListGetSet> notificationListGetSets) {
            this._patientName = _patientName;
            this._patientId = _patientId;
            this.notificationListGetSets = notificationListGetSets;
        }

        public final String get_mTestId() {
            return _mTestId;
        }

        public final void set_mTestId(String _mTestId) {
            this._mTestId = _mTestId;
        }

        public final String get_patientName() {
            return _patientName;
        }

        public final void set_patientName(String _patientName) {
            this._patientName = _patientName;
        }

        public final String get_patientId() {
            return _patientId;
        }

        public final void set_patientId(String _patientId) {
            this._patientId = _patientId;
        }

        public final ArrayList<NotificationListGetSet> getNotificationListGetSets() {
            return notificationListGetSets;
        }

        public final void setNotificationListGetSets(ArrayList<NotificationListGetSet> notificationListGetSets) {
            this.notificationListGetSets = notificationListGetSets;
        }
    }
    //**********************************************************************************************


    /**
     * Created by Ponnusamy M on 4/24/2017.
     */

    public static class NotificationListGetSet {
        String _testName;
        String _testValue;
        String _testRemark;

        public NotificationListGetSet(String _testName, String _testValue, String _testRemark) {
            this._testName = _testName;
            this._testValue = _testValue;
            this._testRemark = _testRemark;
        }

        public final String get_testName() {
            return _testName;
        }

        public final void set_testName(String _testName) {
            this._testName = _testName;
        }

        public final String get_testValue() {
            return _testValue;
        }

        public final void set_testValue(String _testValue) {
            this._testValue = _testValue;
        }

        public final String get_testRemark() {
            return _testRemark;
        }

        public final void set_testRemark(String _testRemark) {
            this._testRemark = _testRemark;
        }
    }
    //**********************************************************************************************


    public  static class Clinical_MHData
    {
        String TreatmentFor="";
        String Medicine_Name="";
        String Period="";

        public Clinical_MHData(String treatmentFor, String medicine_Name, String period) {
            this.TreatmentFor = treatmentFor;
            this.Medicine_Name = medicine_Name;
            this.Period = period;
        }

        public String getTreatmentFor() {
            return TreatmentFor;
        }

        public void setTreatmentFor(String treatmentFor) {
            TreatmentFor = treatmentFor;
        }

        public String getMedicine_Name() {
            return Medicine_Name;
        }

        public void setMedicine_Name(String medicine_Name) {
            Medicine_Name = medicine_Name;
        }

        public String getPeriod() {
            return Period;
        }

        public void setPeriod(String period) {
            Period = period;
        }
    }

    public  static class TwoData
    {
        String Data1="";
        String Data2="";

        public TwoData(String data1, String data2) {
            Data1 = data1;
            Data2 = data2;
        }

        public String getData1() {
            return Data1;
        }

        public void setData1(String data1) {
            Data1 = data1;
        }

        public String getData2() {
            return Data2;
        }

        public void setData2(String data2) {
            Data2 = data2;
        }
    }



    public static class TemplateGetSet {
        String name;
        String sno;

        public TemplateGetSet(String name, String sno) {
            this.name = name;
            this.sno = sno;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSno() {
            return sno;
        }

        public void setSno(String sno) {
            this.sno = sno;
        }
    }



    public static class MedicalCaseRecords {
        String date = "";
        String time = "";
        String clinical_notes = "";
        String treatment_diet = "";

        public MedicalCaseRecords(String date,String time, String clinical_notes, String treatment_diet) {
            this.date = date;
            this.time=time;
            this.clinical_notes = clinical_notes;
            this.treatment_diet = treatment_diet;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getClinical_notes() {
            return clinical_notes;
        }

        public void setClinical_notes(String clinical_notes) {
            this.clinical_notes = clinical_notes;
        }

        public String getTreatment_diet() {
            return treatment_diet;
        }

        public void setTreatment_diet(String treatment_diet) {
            this.treatment_diet = treatment_diet;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }


    public static class Addgetset {
        private String diet_name = null;
        private String food_name = null;
        private String calc = null;
        private String id = null;

        public Addgetset() {
        }

        public Addgetset(String title, String genre, String year) {
            this.diet_name = title;
            this.food_name = genre;
            this.calc = year;
        }

        public Addgetset(String id, String title, String genre, String year) {
            this.id = id;
            this.diet_name = title;
            this.food_name = genre;
            this.calc = year;
        }

        public final String getId() {
            return id;
        }

        public final void setId(String id) {
            this.id = id;
        }

        public final String getDiet_name() {
            return diet_name;
        }

        public final void setDiet_name(String diet_name) {
            this.diet_name = diet_name;
        }

        public final String getFood_name() {
            return food_name;
        }

        public final void setFood_name(String food_name) {
            this.food_name = food_name;
        }

        public final String getCalc() {
            return calc;
        }

        public final void setCalc(String calc) {
            this.calc = calc;
        }


    }


}//END
