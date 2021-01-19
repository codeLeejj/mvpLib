package com.android.baselib.utils.file;

import android.content.Context;

import java.io.File;

public class PathManager {
    /**
     * 文件下载的缓存位置
     */
    private static final String DOWNLOAD_FILE_TEMP_DIR = "file_download_temp";
    /**
     * 文件下载的保存位置
     */
    private static final String DOWNLOAD_FILE_SAVE_DIR = "file_download_save";

    /**
     * APP 内部 路径管理
     */
    public static class Inside {

    }

    /**
     * APP 外部 路径管理
     */
    public static class External {
        public static File getFileTempDir(Context context) {
            File externalCacheDir = context.getExternalCacheDir();
            File dir = new File(externalCacheDir, DOWNLOAD_FILE_TEMP_DIR);
            if (!dir.exists()) {
                dir.mkdir();
            }
            return dir;
        }

        public static String getFileTempDirPath(Context context) {
            File dir = getFileTempDir(context);
            return dir.getAbsolutePath();
        }
    }
}
