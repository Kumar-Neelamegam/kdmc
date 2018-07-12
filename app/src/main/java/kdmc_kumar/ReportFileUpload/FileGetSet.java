package kdmc_kumar.ReportFileUpload;

/**
 * Created by Ponnusamy M on 4/21/2017.
 */

class FileGetSet {

    private String ImageName = null;
    private String ImagePath = null;
    private String ImageData = null;
    private String Id = null;
    private String UploadFileDetail = null;
    private String ReportType = null;

    public FileGetSet(String imageName, String imagePath, String imageData, String id, String uploadFileDetail, String reportType) {
        ImageName = imageName;
        ImagePath = imagePath;
        ImageData = imageData;
        Id = id;
        UploadFileDetail = uploadFileDetail;
        ReportType = reportType;
    }

    public FileGetSet(String imageName, String imagePath, String imageData, String id, String uploadFileDetail) {
        ImageName = imageName;
        ImagePath = imagePath;
        ImageData = imageData;
        Id = id;
        UploadFileDetail = uploadFileDetail;
    }

    public FileGetSet() {
    }

    public FileGetSet(String imageName, String imagePath, String imageData) {
        ImageName = imageName;
        ImagePath = imagePath;
        ImageData = imageData;
    }

    public final String getReportType() {
        return ReportType;
    }

    public final void setReportType(String reportType) {
        ReportType = reportType;
    }

    public final String getId() {
        return Id;
    }

    public final void setId(String id) {
        Id = id;
    }

    public final String getUploadFileDetail() {
        return UploadFileDetail;
    }

    public final void setUploadFileDetail(String uploadFileDetail) {
        UploadFileDetail = uploadFileDetail;
    }

    public final String getImageName() {
        return ImageName;
    }

    public final void setImageName(String imageName) {
        ImageName = imageName;
    }

    public final String getImagePath() {
        return ImagePath;
    }

    public final void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public final String getImageData() {
        return ImageData;
    }

    public final void setImageData(String imageData) {
        ImageData = imageData;
    }
}
