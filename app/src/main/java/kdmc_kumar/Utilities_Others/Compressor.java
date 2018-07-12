package kdmc_kumar.Utilities_Others;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by dell on 15/7/17.
 */

class Compressor {

    private static int height = 0, width = 0, inSampleSize = 0;
    private static String encodedfile = null;

    Compressor() {
    }

    public static File compressImageFile(File imageFile, int reqHeight, int reqWidth,
                                         String filePath, int quality,
                                         Bitmap.CompressFormat compressFormat, int orientation) throws IOException, java.io.FileNotFoundException {
        FileOutputStream fileOutputStream = null;
        File file = new File(filePath).getParentFile();
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            fileOutputStream = new FileOutputStream(filePath);
            decodeBitmapAndCompress(imageFile, reqHeight, reqWidth, orientation)
                    .compress(compressFormat, quality, fileOutputStream);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        return new File(filePath);
    }

    public static Bitmap decodeBitmapAndCompress(File imageFile, int reqHeight, int reqWidth, int reqOrientation)
            throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);
        //Calculating Sample Size
        options.inSampleSize = calculateSampleSize(options, reqHeight, reqWidth);

        options.inJustDecodeBounds = false;

        Bitmap scaledBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(), options);

        ExifInterface exifInterface;
        exifInterface = new ExifInterface(imageFile.getAbsolutePath());
        int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0);
        Matrix matrix = new Matrix();
        switch (orientation) {
            case 6:
                matrix.postRotate(90);
                break;
            case 3:
                matrix.postRotate(180);
                break;
            case 8:
                matrix.postRotate(270);
                break;
        }
        if (reqOrientation > 0) {
            matrix.postRotate((float) reqOrientation);
        }
        scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth()
                , scaledBitmap.getHeight(), matrix, true);
        return scaledBitmap;
    }


    private static int calculateSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {

        height = options.outHeight;
        width = options.outWidth;
        inSampleSize = 1;

        int halfHeight = height / 2;
        int halfWidth = width / 2;

        if (height > reqHeight || width > reqWidth) {
            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static String getBase64forCompressedImage(File compressFile) {

        FileInputStream fileInputStreamReader = null;
        byte[] bytes = new byte[(int) compressFile.length()];
        try {
            fileInputStreamReader = new FileInputStream(compressFile);
            fileInputStreamReader.read(bytes);
            encodedfile = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return encodedfile;
    }


}
