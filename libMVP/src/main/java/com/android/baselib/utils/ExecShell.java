package com.android.baselib.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 执行shell命令<br/>
 * Created by sven on 2015/7/15.
 */
public class ExecShell {
    private static String LOG_TAG = ExecShell.class.getName();

    public static enum SHELL_CMD {
        check_su_binary(new String[]{"/system/xbin/which", "su"}),;
        String[] command;
        SHELL_CMD(String[] command) {
            this.command = command;
        }
    }

    /**
     * 执行shell命令
     *
     * @param shellCmd shell命令
     * @return
     */
    public ArrayList<String> executeCommand(SHELL_CMD shellCmd) {
        String line;
        ArrayList<String> fullResponse = new ArrayList<String>();
        Process localProcess;

        try {
            localProcess = Runtime.getRuntime().exec(shellCmd.command);
        } catch (Exception e) {
            return null;
        }
//        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(localProcess.getOutputStream()));
        BufferedReader in = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
        try {
            while ((line = in.readLine()) != null) {
//                Logger.t(LOG_TAG).d("--> Line received: " + line);
                fullResponse.add(line);
            }
        } catch (Exception ignored) {
        }
//        Logger.t(LOG_TAG).d("--> Full response was: " + fullResponse);
        return fullResponse;
    }
}