/*
  File generated by Magnet rest2mobile 1.1 - Nov 13, 2017 6:36:07 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.importREST_Services.postImmunizationInfo.model.beans;


/**
 * Generated from json example
 {
 "Results" : ""
 }

 */

public class PosImmunizationInfoResult {


    @com.google.gson.annotations.SerializedName("Results")
    private String results = null;

    public PosImmunizationInfoResult() {
    }

    public final String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    /**
     * Builder for PosImmunizationInfoResult
     **/
    public static class PosImmunizationInfoResultBuilder {
        private final PosImmunizationInfoResult toBuild = new PosImmunizationInfoResult();

        public PosImmunizationInfoResultBuilder() {
        }

        public final PosImmunizationInfoResult build() {
            return toBuild;
        }

        public final PosImmunizationInfoResultBuilder results(String value) {
            toBuild.setResults(value);
            return this;
        }
    }
}
