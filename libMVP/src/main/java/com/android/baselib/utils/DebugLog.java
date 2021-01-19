package com.android.baselib.utils;


import android.util.Log;

import com.android.baselib.BuildConfig;
import com.android.baselib.constants.MyBuildConfig;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 强制 Debug模式 的 Log
 * #author lee
 */
public class DebugLog {
    private static int level;
    //规定每段显示的长度
    private static int LineMaxLength = 4000;//最大长度单行长度
    private static int LOG_MAXLENGTH = 4 * 1024;
    private static int LOG_AllowLength = 4000;//Log单条信息最大长度 小于真实值 4*1024 以防信息处理
    public static final String Default_Tag = "LogInfo";

    public static void v(Object message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 1;
        printLog(message);
    }

    public static void v(String tag, Object... message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 1;
        printLog(tag, message);
    }

    public static void v(String tag, Object message, RuntimeException re) {
        if (!MyBuildConfig.DEBUG) return;
        level = 1;
        printLog(tag, re, message);
    }

    public static void d(Object message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 2;
        printLog(message);
    }

    public static void d(String tag, Object... message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 2;
        printLog(tag, message);
    }

    public static void d(String tag, Object message, RuntimeException re) {
        if (!MyBuildConfig.DEBUG) return;
        level = 2;
        printLog(tag, re, message);
    }

    public static void i(Object message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 3;
        printLog(message);
    }

    public static void i(String tag, Object... message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 3;
        printLog(tag, message);
    }

    public static void i(String tag, Object message, RuntimeException re) {
        if (!MyBuildConfig.DEBUG) return;
        level = 3;
        printLog(tag, re, message);
    }

    public static void w(Object message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 4;
        printLog(message);
    }

    public static void w(String tag, Object... message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 4;
        printLog(tag, message);
    }

    public static void w(String tag, Object message, RuntimeException re) {
        if (!MyBuildConfig.DEBUG) return;
        level = 4;
        printLog(tag, re, message);
    }

    public static void e(Object message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 5;
        printLog(message);
    }

    public static void e(String tag, Object... message) {
        if (!MyBuildConfig.DEBUG) return;
        level = 5;
        printLog(tag, message);
    }

    public static void e(String tag, Object message, RuntimeException re) {
        if (!MyBuildConfig.DEBUG) return;
        level = 5;
        printLog(tag, re, message);
    }

    private static void printLog(Object message) {
        printLog(Default_Tag, message);
    }

    private static void printLog(String tag, Object... message) {
        printLog(tag, null, message);
    }

    public static void printLog(String tag, RuntimeException re, Object... message) {
        if (message.length == 1) {//单条数据
            String str_handle = getStringByObj(message[0]);
            if (re == null) {
                int length = str_handle.length();
                if (length > LOG_MAXLENGTH) {//超出Logcat单条信息最大长度
                    int last = length % LOG_AllowLength;
                    int countLine = length / LOG_AllowLength + (last > 0 ? 1 : 0);
                    for (int i = 0; i < countLine; i++) {
                        if ((i + 1) == countLine) {
//                            printLog(tag + "[" + (i + 1) + "/" + countLine + "]", i * LOG_AllowLength, length);
                            printLog(tag + "[" + (i + 1) + "/" + countLine + "]", str_handle.substring(i * LOG_AllowLength, length));
                        } else {
//                            printLog(tag + "[" + (i + 1) + "/" + countLine + "]", i * LOG_AllowLength, (i + 1) * LOG_AllowLength);
                            printLog(tag + "[" + (i + 1) + "/" + countLine + "]", str_handle.substring(i * LOG_AllowLength, (i + 1) * LOG_AllowLength));
                        }
                    }
                } else {//一条显示
                    //性能优化
                    if (length <= LineMaxLength) {//不超过一行的直接打印
                        printLogALine(tag, str_handle);
                    } else {
                        int last = length % LineMaxLength;
                        int countLine = length / LineMaxLength + (last > 0 ? 1 : 0);
                        StringBuffer buffer = new StringBuffer(length + countLine * 4);
                        for (int i = 0; i < countLine; i++) {
                            if ((i + 1) == countLine) {
                                buffer.append(str_handle.substring(i * LineMaxLength, length) + "\n");
                            } else {
                                buffer.append(str_handle.substring(i * LineMaxLength, (i + 1) * LineMaxLength) + "\n");
                            }
                        }
                        printLogALine(tag, buffer.toString());
                    }
                }
            } else {//re 不为空 不处理了
                printLogALine(tag, str_handle, re);
            }
        } else {//多条数据,当简短的集合处理
            StringBuffer buffer = new StringBuffer();
            for (Object item : message) {
                buffer.append(getStringByObj(item) + " -- ");
            }
            printLogALine(tag, buffer.toString(), re);
        }
    }

    private static void printLogALine(String tag, Object message) {
        printLogALine(tag, message, null);
    }


    private static void printLogALine(String tag, Object message, RuntimeException re) {
        String messageStr = getStringByObj(message);
        switch (level) {
            case 1:
                Log.v(tag, messageStr, re);
                break;
            case 2:
                Log.d(tag, messageStr, re);
                break;
            case 3:
                Log.i(tag, messageStr, re);
                break;
            case 4:
                Log.w(tag, messageStr, re);
                break;
            case 5:
                Log.e(tag, messageStr, re);
                break;
        }
    }

    private static String getStringByObj(Object message) {
        String messageStr;
        if (message instanceof Integer) {
            messageStr = message + "";
        } else if (message instanceof String) {
            messageStr = (String) message;
        } else if (message instanceof Long) {
            messageStr = message + "";
        } else if (message instanceof Float) {
            messageStr = message + "";
        } else if (message instanceof Double) {
            messageStr = message + "";
        } else if (message instanceof Short) {
            messageStr = message + "";
        } else if (message instanceof JSONObject) {
            messageStr = message.toString();
        } else if (message instanceof JSONArray) {
            messageStr = message.toString();
        } else {
            messageStr = new Gson().toJson(message);
        }
        return messageStr;
    }
}
