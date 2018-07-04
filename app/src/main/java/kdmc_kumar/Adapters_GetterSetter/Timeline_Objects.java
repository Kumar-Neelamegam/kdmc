package kdmc_kumar.Adapters_GetterSetter;


public class Timeline_Objects {

    public Timeline_Objects(String id, String visitedDate_, String year) {
        this.Id = id;
        VisitedDate = visitedDate_;
        Unique_Id = year;
    }

    String Id;
    String VisitedDate;
    String Unique_Id;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getVisitedDate() {
        return VisitedDate;
    }

    public void setVisitedDate(String visitedDate) {
        VisitedDate = visitedDate;
    }

    public String getUnique_Id() {
        return Unique_Id;
    }

    public void setUnique_Id(String unique_Id) {
        Unique_Id = unique_Id;
    }
}
