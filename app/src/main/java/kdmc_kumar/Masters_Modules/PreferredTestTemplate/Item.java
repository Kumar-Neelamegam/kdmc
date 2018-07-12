package kdmc_kumar.Masters_Modules.PreferredTestTemplate;

/**
 * Created by bpncool on 2/23/2016.
 */
public class Item {

    private final String Subtestname;
    private final String TestName;
    private final int id;

    public Item(String testName, String subtestname, int id) {
        TestName = testName;
        Subtestname = subtestname;
        this.id = id;
    }


    public final String getTestName() {
        return TestName;
    }

    public final String getSubtestname() {
        return Subtestname;
    }

    public final int getId() {
        return id;
    }

    public final String setTestName() {
        return TestName;
    }

    public final String setSubtestname() {
        return Subtestname;
    }

    public final int setId() {
        return id;
    }


}
