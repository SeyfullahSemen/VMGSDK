package com.vmg.LoggerPack;


import android.util.Log;

/**
 * Created by Seyfullah on 15-9-2017.
 */

public class VMGLogs {
    private static final String STANDARDLOGTAG = "VMGLOG: ";
    private static VMGLogs.VMGLogLevels level;

    public VMGLogs() {
        level.logLevel = 0;
    }

    public static void StandardLog(String message) {
        Log.i(STANDARDLOGTAG, message);
    }

    public static void debug(String message) {
        int debug = 4;
        if (debug == VMGLogLevels.VMGDebug.getLogLevel()) {
            Log.d(STANDARDLOGTAG + " " + debug, message);
        }
    }

    public static void fatal(String message) {
        int fatal = 1;
        if (fatal == VMGLogLevels.VMGFatal.getLogLevel()) {
            Log.e(STANDARDLOGTAG + " " + fatal, " " + message);
        }
    }

    public static void Information(String message) {
        int information = 3;
        if (information == VMGLogLevels.VMGInformation.getLogLevel()) {
            Log.i(STANDARDLOGTAG + " " + information, " " + message);
        }
    }

    public static void warning(String message) {
        int warning = 5;
        if (warning == VMGLogLevels.VMGWarning.getLogLevel()) {
            Log.w(STANDARDLOGTAG + " " + warning, " " + message);
        }
    }

    public static void verbose(String message) {
        int verbose = 2;
        if (verbose == VMGLogLevels.VMGVerbose.getLogLevel()) {
            Log.v(STANDARDLOGTAG + " " + verbose, " " + message);
        }
    }

    public static enum VMGLogLevels {
        VMGFatal(1),
        VMGVerbose(2),
        VMGInformation(3),
        VMGDebug(4),
        VMGWarning(5);

        private int logLevel;

        VMGLogLevels(int logLevel) {
            this.logLevel = logLevel;
        }

        public int getLogLevel() {
            return logLevel;
        }

        public void setLogLevel(int level) {
            this.logLevel = level;
        }

        public static int getLevel(int id) {
            VMGLogLevels level = null;
            switch (id) {
                case 1:
                    level = VMGFatal;
                    break;
                case 2:
                    level = VMGVerbose;
                    break;
                case 3:
                    level = VMGInformation;
                    break;
                case 4:
                    level = VMGDebug;
                    break;
                case 5:
                    level = VMGWarning;
                    break;
                default:
                    break;
            }
            return id;
        }
    }
}
