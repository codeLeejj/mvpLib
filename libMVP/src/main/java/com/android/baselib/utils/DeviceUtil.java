package com.android.baselib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class DeviceUtil {
    /**
     * 设备唯一号存储
     */
    public static final String MOBILE_SETTING = "MMARKET_MOBILE_SETTING";
    /**
     * 设备唯一表示
     */
    public static final String MOBILE_UUID = "MMARKET_MOBILE_UUID";

    /**
     * 从磁盘读取设备ID,如果没有则先去生成
     *
     * @param context
     * @return
     */
    public static String getDeviceIDAndCacheDisk(Context context) {
        File file;
        // SD卡路径
        if (Environment.isExternalStorageEmulated()) {
            String SD_PATH = Environment.getExternalStorageDirectory().getPath();
            file = new File(SD_PATH + "/sxyh/device/id");
        } else {
            file = new File(context.getExternalCacheDir(), "/sxyh/device/id");
        }
        if (!file.exists()) {//不存在,生成后,写入
            String id = getUUID16Byte(context);
            //创建文件
            try {
                File dir = file.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                boolean b = file.createNewFile();
                if (b) {
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.append(id);
                    fileWriter.flush();
                    fileWriter.close();
                }
                return id;
            } catch (IOException e) {
                return id;
            }
        } else {//存在->读取
            FileReader fileReader = null;
            try {
                StringBuffer buffer = new StringBuffer();
                int length = 0;
                char[] bytes = new char[256];
                fileReader = new FileReader(file);
                while ((length = fileReader.read(bytes)) != -1) {
                    buffer.append(new String(bytes, 0, length));
                }
                return buffer.toString();
            } catch (Exception e) {
                return getUUID16Byte(context);
            } finally {
                try {
                    if (fileReader != null) {
                        fileReader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * SharedPreferences 中读取,如果没有那就生成
     * 得到全局唯一UUID
     */
    private static String getUUID16Byte(Context context) {
        SharedPreferences mShare = context.getSharedPreferences(MOBILE_SETTING, Context.MODE_PRIVATE);
        String uuid = "";
        if (mShare != null && !TextUtils.isEmpty(mShare.getString(MOBILE_UUID, ""))) {
            uuid = mShare.getString(MOBILE_UUID, "");
        }
        if (TextUtils.isEmpty(uuid)) {
            uuid = UUID.randomUUID().toString();
            mShare.edit().putString(MOBILE_UUID, uuid).apply();
        }
        return uuid;
    }
}
