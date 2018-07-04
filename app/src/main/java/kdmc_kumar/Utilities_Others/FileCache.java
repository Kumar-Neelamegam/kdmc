package kdmc_kumar.Utilities_Others;

import android.content.Context;

import java.io.File;

class FileCache {

    private final File cacheDir;

    public FileCache(Context context) {
        // Find the dir to save cached images
        cacheDir = android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED) ? new File(
                android.os.Environment.getExternalStorageDirectory(),
                "LazyList") : context.getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
    }

    public final File getFile(String url) {
        // I identify images by hashcode. Not a perfect solution, good for the
        // demo.
        String filename = String.valueOf(url.hashCode());
        // Another possible solution (thanks to grantland)
        // String filename = URLEncoder.encode(url);
        return new File(cacheDir, filename);

    }

    public final void clear() {
        File[] files = cacheDir.listFiles();
        if (files == null)
            return;
        for (File f : files)
            f.delete();
    }

}