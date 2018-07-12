package kdmc_kumar.Utilities_Others;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Imageutils {


    private final Context context;
    private final Activity current_activity;

    private final ImageAttachmentListener imageAttachment_callBack;

    @Nullable
    private
    String selected_path = "";
    private Uri imageUri = null;
    private File path = null;

    private int from = 0;

    public Imageutils(Activity act) {

        this.context = act;
        this.current_activity = act;
        imageAttachment_callBack = (ImageAttachmentListener) context;
    }

    /**
     * Get file name from path
     *
     * @param path
     * @return
     */

    public static final String getfilename_from_path(String path) {
        return path.substring(path.lastIndexOf('/') + 1, path.length());

    }

    /**
     * Get Image URI from Bitmap
     *
     * @param context
     * @param photo
     * @return
     */

    public static final Uri getImageUri(Context context, Bitmap photo) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), photo, "Title", null);
        return Uri.parse(path);
    }

    /**
     * Get Path from Image URI
     *
     * @param uri
     * @return
     */

    private final String getPath(Uri uri) {
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = this.context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            int column_index1 = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index1);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }

    /**
     * Bitmap from String
     *
     * @param encodedString
     * @return
     */
    @Nullable
    public static final Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }


    /**
     * Get String from Bitmap
     *
     * @param bitmap
     * @return
     */

    public static final String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    /**
     * Check Camera Availability
     *
     * @return
     */

    private final boolean isDeviceSupportCamera() {
        // this device has a camera
// no camera on this device
        return this.context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }


    /**
     * Compress Imgae
     *
     * @param imageUri
     * @param height
     * @param width
     * @return
     */


    @Nullable
    private final Bitmap compressImage(String imageUri, float height, float width) {

        String filePath = getRealPathFromURI(imageUri);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.

        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as 816x612

        float imgRatio = (float) (actualWidth / actualHeight);
        float maxRatio = width / height;

        // width and height values are set maintaining the aspect ratio of the image

        if ((float) actualHeight > height || (float) actualWidth > width) {
            if (imgRatio < maxRatio) {
                float imgRatio1 = height / (float) actualHeight;
                actualWidth = (int) (imgRatio1 * (float) actualWidth);
                actualHeight = (int) height;
            } else if (imgRatio > maxRatio) {
                float imgRatio1 = width / (float) actualWidth;
                actualHeight = (int) (imgRatio1 * (float) actualHeight);
                actualWidth = (int) width;
            } else {
                actualHeight = (int) height;
                actualWidth = (int) width;

            }
        }

        //  setting inSampleSize value allows to load a scaled down version of the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight);

        //  inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low on memory

        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            //  load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = (float) actualWidth / (float) options.outWidth;
        float ratioY = (float) actualHeight / (float) options.outHeight;
        float middleX = (float) actualWidth / 2.0f;
        float middleY = (float) actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - (float) (bmp.getWidth() / 2), middleY - (float) (bmp.getHeight() / 2), new Paint(Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly

        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            switch (orientation) {
                case 6:
                    matrix.postRotate(90);
                    Log.d("EXIF", "Exif: " + 6);
                    break;
                case 3:
                    matrix.postRotate(180);
                    Log.d("EXIF", "Exif: " + 3);
                    break;
                case 8:
                    matrix.postRotate(270);
                    Log.d("EXIF", "Exif: " + 8);
                    break;
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);

            return scaledBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Get RealPath from Content URI
     *
     * @param contentURI
     * @return
     */
    private String getRealPathFromURI(String contentURI) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = context.getContentResolver().query(contentUri, null, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.MediaColumns.DATA);

            return cursor.getString(index);
        }

    }


    /**
     * ImageSize Calculation
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */

    private static final int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / reqHeight);
            final int widthRatio = Math.round((float) width / reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = (float) (width * height);
        final float totalReqPixelsCap = (float) (reqWidth * reqHeight * 2);
        while (totalPixels / (float) (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    /**
     * Launch Camera
     *
     * @param from
     */

    private final void launchCamera(int from) {
        this.from = from;

        if (Build.VERSION.SDK_INT >= 23) {
            permission_check(1);
        } else {
            camera_call();
        }
    }

    /**
     * Launch Gallery
     *
     * @param from
     */

    private final void launchGallery(int from) {

        this.from = from;

        if (Build.VERSION.SDK_INT >= 23) {
            permission_check(2);
        } else {
            galley_call();
        }
    }

    /**
     * Show AlertDialog with the following options
     * <p>
     * Camera
     * Gallery
     *
     * @param from
     */

    public final void imagepicker(final int from) {
        this.from = from;

        final CharSequence[] items;

        if (isDeviceSupportCamera()) {
            items = new CharSequence[2];
            items[0] = "Camera";
            items[1] = "Gallery";
        } else {
            items = new CharSequence[1];
            items[0] = "Gallery";
        }

        android.app.AlertDialog.Builder alertdialog = new android.app.AlertDialog.Builder(current_activity);
        alertdialog.setTitle("Add Image");
        alertdialog.setItems(items, (dialog, item) -> {
            if (items[item].equals("Camera")) {
                launchCamera(from);
            } else if (items[item].equals("Gallery")) {
                launchGallery(from);
            }
        });
        alertdialog.show();
    }

    /**
     * Check permission
     *
     * @param code
     */

    private final void permission_check(final int code) {
        int hasWriteContactsPermission = ContextCompat.checkSelfPermission(current_activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (hasWriteContactsPermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(current_activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                showMessageOKCancel(
                        (dialog, which) -> ActivityCompat.requestPermissions(current_activity,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                code));
                return;
            }

            ActivityCompat.requestPermissions(current_activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    code);
            return;
        }

        if (code == 1)
            camera_call();
        else if (code == 2)
            galley_call();
    }


    private void showMessageOKCancel(DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(current_activity)
                .setMessage("For adding images , You need to provide permission to access your files")
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }


    /**
     * Capture image from camera
     */

    private final void camera_call() {
        ContentValues values = new ContentValues();
        imageUri = current_activity.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        current_activity.startActivityForResult(intent1, 0);
    }

    /**
     * pick image from Gallery
     */

    private final void galley_call() {

        Intent intent2 = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent2.setType("image/*");
        current_activity.startActivityForResult(intent2, 1);

    }


    /**
     * Activity PermissionResult
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    public final void request_permission_result(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera_call();
                } else {
                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;

            case 2:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    galley_call();
                } else {

                    Toast.makeText(current_activity, "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    /**
     * Intent ActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    public final void onActivityResult(int requestCode, int resultCode, Intent data) {
        String file_name;
        Bitmap bitmap;

        switch (requestCode) {
            case 0:

                if (resultCode == Activity.RESULT_OK) {

                    //Log.e("Camera Selected","Photo");

                    try {
                        selected_path = null;
                        selected_path = getPath(imageUri);

                        file_name = selected_path.substring(selected_path.lastIndexOf('/') + 1);
                        //Log.e("file","name"+file_name);
                        //Log.e("selected","path"+selected_path);
                        bitmap = compressImage(imageUri.toString(), 816.0F, 612.0F);
                        imageAttachment_callBack.image_attachment(from, file_name, bitmap, imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    //BaseConfig.Patient_ImgPath=selected_path;

                }
                break;
            case 1:
                if (resultCode == Activity.RESULT_OK) {
                    //Log.e("Gallery","Photo");
                    Uri selectedImage = data.getData();

                    try {
                        selected_path = null;
                        selected_path = getPath(selectedImage);
                        file_name = selected_path.substring(selected_path.lastIndexOf('/') + 1);
                        bitmap = compressImage(selectedImage.toString(), 816.0F, 612.0F);
                        imageAttachment_callBack.image_attachment(from, file_name, bitmap, selectedImage);
                        //Log.e("file","name"+file_name);
                        //Log.e("selected","path"+selected_path);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //BaseConfig.Patient_ImgPath=selected_path;

                }
                break;
        }


    }

    /**
     * Get image from Uri
     *
     * @param uri
     * @param height
     * @param width
     * @return
     */
    public final Bitmap getImage_FromUri(Uri uri, float height, float width) {
        Bitmap bitmap = null;

        try {
            bitmap = compressImage(uri.toString(), height, width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    /**
     * Get filename from URI
     *
     * @param uri
     * @return
     */
    @Nullable
    public final String getFileName_from_Uri(Uri uri) {
        String path = null, file_name = null;

        try {

            path = getRealPathFromURI(uri.getPath());
            file_name = path.substring(path.lastIndexOf('/') + 1);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return file_name;

    }


    /**
     * Check Image Exist (or) Not
     *
     * @param file_name
     * @param file_path
     * @return
     */

    public final boolean checkimage(String file_name, String file_path) {
        boolean flag;
        path = new File(file_path);

        File file = new File(path, file_name);
        if (file.exists()) {
            Log.i("file", "exists");
            flag = true;
        } else {
            Log.i("file", "not exist");
            flag = false;
        }

        return flag;
    }


    /**
     * Get Image from the given path
     *
     * @param file_name
     * @param file_path
     * @return
     */

    @Nullable
    public final Bitmap getImage(String file_name, String file_path) {

        path = new File(file_path);
        File file = new File(path, file_name);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = 2;
        options.inTempStorage = new byte[16 * 1024];

        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

        return bitmap != null ? bitmap : null;
    }

    /**
     * Create an image
     *
     * @param bitmap
     * @param file_name
     * @param filepath
     * @param file_replace
     */


    public final void createImage(Bitmap bitmap, String file_name, String filepath, boolean file_replace) {

        try {
            path = new File(filepath);

            if (!path.exists()) {
                path.mkdirs();
            }

            File file = new File(path, file_name);

            if (file.exists()) {
                if (file_replace) {
                    file.delete();
                    File file1 = new File(path, file_name);
                    store_image(file1, bitmap);
                    Log.i("file", "replaced");
                }
            } else {
                store_image(file, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * @param file
     * @param bmp
     */
    private static final void store_image(File file, Bitmap bmp) {

        //Toast.makeText(context, "Path:"+file , Toast.LENGTH_LONG).show();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 80, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Image Attachment Callback

    public interface ImageAttachmentListener {
        void image_attachment(int from, String filename, Bitmap file, Uri uri);
    }


}
