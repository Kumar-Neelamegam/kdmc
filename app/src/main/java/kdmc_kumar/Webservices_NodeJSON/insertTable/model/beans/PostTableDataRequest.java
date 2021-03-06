/*
  File generated by Magnet rest2mobile 1.1 - Nov 2, 2017 12:56:35 PM

  @see {@link http://developer.magnet.com}
 */

package kdmc_kumar.Webservices_NodeJSON.insertTable.model.beans;


/**
 * Generated from json example
 {
 "JsonValue" : "[{\"ptid\":\"123\",\"Actdate\":\"\",\"id\":\"5\"},{\"ptid\":\"1323\",\"Actdate\":\"\",\"id\":\"54\"}]",
 "TableName" : "ClinicalInformation"
 }

 */

public class PostTableDataRequest {


    @com.google.gson.annotations.SerializedName("JsonValue")
    private String jsonValue = null;


    @com.google.gson.annotations.SerializedName("TableName")
    private String tableName = null;

    public PostTableDataRequest() {
    }

    public final String getJsonValue() {
        return jsonValue;
    }

    public final void setJsonValue(String jsonValue) {
        this.jsonValue = jsonValue;
    }

    public final String getTableName() {
        return tableName;
    }

    public final void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Builder for PostTableDataRequest
     **/
    public static class PostTableDataRequestBuilder {
        private final PostTableDataRequest toBuild = new PostTableDataRequest();

        public PostTableDataRequestBuilder() {
        }

        public final PostTableDataRequest build() {
            return toBuild;
        }

        public final PostTableDataRequestBuilder jsonValue(String value) {
            toBuild.setJsonValue(value);
            return this;
        }

        public final PostTableDataRequestBuilder tableName(String value) {
            toBuild.setTableName(value);
            return this;
        }
    }
}
