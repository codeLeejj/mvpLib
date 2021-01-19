package com.android.baselib.utils;

import android.content.Context;
import android.provider.DocumentsContract;
import android.text.TextUtils;

import com.scottyab.rootbeer.RootBeer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 * 检查设备是否root
 * Created by sven on 2015/7/15.
 */
public class EnvironmentUtil {
    private static String LOG_TAG = EnvironmentUtil.class.getName();

    /**
     * 真实设备检测
     *
     * @return true:真机,false:模拟器
     */
    public static boolean isTrulyDevice() {
        //ro.radio.use-ppp—>yes or ro.product.cpu.abi—>x86 一定是模拟器
        //ro.radio.use-ppp—>null or init.svc.console->null 一定是真机
        String abi = properties("ro.product.cpu.abi");
        String usePPP = properties("ro.radio.use-ppp");
        String console = properties("init.svc.console");

        boolean emulator1 = "x86".equals(abi);
        boolean emulator2 = "yes".equals(usePPP);

        boolean device1 = TextUtils.isEmpty(usePPP);
        boolean device2 = TextUtils.isEmpty(console);

        return !(emulator1 || emulator2) && (device1 || device2);
    }

    private static String properties(String key) {
        try {
            Process process = Runtime.getRuntime().exec("getprop " + key);
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer buffer = new StringBuffer();
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (IOException e) {

            return "";
        }
    }

    /**
     * 判断手机是否ROOT
     */
    public static boolean isRootSystem(Context context) {
        RootBeer rootBeer = new RootBeer(context);
        return rootBeer.detectRootCloakingApps()
                || rootBeer.detectPotentiallyDangerousApps()
                || rootBeer.detectRootCloakingApps()
                || rootBeer.detectTestKeys()
                || rootBeer.checkForDangerousProps()
                || rootBeer.checkForSuBinary()
                || rootBeer.checkForRWPaths()
                || rootBeer.checkForRootNative()
                || rootBeer.checkForBusyBoxBinary();
    }

//    public static boolean isRootSystem() {
//        if(isRootSystem1()||isRootSystem2()){
//            //TODO 可加其他判断 如是否装了权限管理的apk，大多数root 权限 申请需要app配合，也有不需要的，这个需要改su源码。因为管理su权限的app太多，无法列举所有的app，特别是国外的，暂时不做判断是否有root权限管理app
//            //多数只要su可执行就是root成功了，但是成功后用户如果删掉了权限管理的app，就会造成第三方app无法申请root权限，此时是用户删root权限管理app造成的。
//            //市场上常用的的权限管理app的包名   com.qihoo.permmgr  com.noshufou.android.su  eu.chainfire.supersu   com.kingroot.kinguser  com.kingouser.com  com.koushikdutta.superuser
//            //com.dianxinos.superuser  com.lbe.security.shuame com.geohot.towelroot 。。。。。。
//            return true;
//        }else{
//            return false;
//        }
//    }
//    private static boolean isRootSystem1() {
//        File f = null;
//        final String kSuSearchPaths[] = { "/system/bin/", "/system/xbin/",
//                "/system/sbin/", "/sbin/", "/vendor/bin/" };
//        try {
//            for (int i = 0; i < kSuSearchPaths.length; i++) {
//                f = new File(kSuSearchPaths[i] + "su");
//                if (f != null && f.exists()&&f.canExecute()) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }
//    private static boolean isRootSystem2() {
//        List<String> pros = getPath();
//        File f = null;
//        try {
//            for (int i = 0; i < pros.size(); i++) {
//                f = new File(pros.get(i),"su");
//                System.out.println("f.getAbsolutePath():"+f.getAbsolutePath());
//                if (f != null && f.exists()&&f.canExecute()) {
//                    return true;
//                }
//            }
//        } catch (Exception e) {
//        }
//        return false;
//    }

    private static List<String> getPath() {
        return Arrays.asList(System.getenv("PATH").split(":"));
    }

    /**
     * 检查设备是否root
     *
     * @return
     */
    public boolean isDeviceRooted() {
        if (checkRootMethod1()) {
            return true;
        }
        if (checkRootMethod2()) {
            return true;
        }
        if (checkRootMethod3()) {
            return true;
        }
        return false;
    }

    private boolean checkRootMethod1() {
        String buildTags = android.os.Build.TAGS;
        if (buildTags != null && buildTags.contains("test-keys")) {
            return true;
        }
        return false;
    }

    private boolean checkRootMethod2() {
        try {
            File file = new File("/system/app/Superuser.apk");
            if (file.exists()) {
                return true;
            }
        } catch (Exception e) {
        }

        return false;
    }

    private boolean checkRootMethod3() {
        if (new ExecShell().executeCommand(ExecShell.SHELL_CMD.check_su_binary) != null) {
            return true;
        } else {
            return false;
        }
    }
}
