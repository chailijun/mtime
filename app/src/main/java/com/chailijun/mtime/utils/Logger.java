package com.chailijun.mtime.utils;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;


public class Logger {
    private static final String DEBUG_TAG = "MTIME";
    public static boolean isDebug;
    public static boolean isWriteToLog;
    public static boolean memDebug;

    static {
        isWriteToLog = false;
        isDebug = true;
        memDebug = false;
    }

    public static void d(Object obj, String str) {
        if (isDebug) {
            Log.d(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void d(String str) {
        if (isDebug) {
            Log.d(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void d(String str, Object obj, String str2) {
        if (isDebug) {
            Log.d(str, obj.getClass().getSimpleName() + ": " + str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void d(String str, String str2) {
        if (isDebug) {
            Log.d(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void debugError(String str) {
        if (isDebug) {
            Log.e(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void debugError(String str, String str2) {
        if (isDebug) {
            Log.e(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str2);
        }
    }

    public static void debugInfo(String str) {
        if (isDebug) {
            Log.i(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void e(Object obj, String str) {
        if (isDebug) {
            Log.e(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void e(String str) {
        if (isDebug) {
            Log.e(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void e(String str, Object obj, String str2) {
        if (isDebug) {
            Log.e(str, obj.getClass().getSimpleName() + ": " + str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void e(String str, String str2) {
        if (isDebug) {
            Log.e(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void i(Object obj, String str) {
        if (isDebug) {
            Log.i(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void i(String str) {
        if (isDebug) {
            Log.i(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void i(String str, Object obj, String str2) {
        if (isDebug) {
            Log.i(str, obj.getClass().getSimpleName() + ": " + str2);
        }
        if (isWriteToLog) {
            LogToFile(str, obj.getClass().getSimpleName() + ": " + str2);
        }
    }

    public static void i(String str, String str2) {
        if (isDebug) {
            Log.i(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void LogToFile(String str, String str2) {
        if (isWriteToLog) {
            String str3 = str + ":" + str2;
            try {
                Writer fileWriter = new FileWriter(new File(Environment.getExternalStorageDirectory().getPath() + "/logFile.txt"), true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(str3);
                bufferedWriter.newLine();
                bufferedWriter.close();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void LogToFile(String str) {
        LogToFile(DEBUG_TAG, str);
    }

    public static void v(Object obj, String str) {
        if (isDebug) {
            Log.v(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
    }

    public static void v(String str) {
        if (isDebug) {
            Log.v(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void v(String str, Object obj, String str2) {
        if (isDebug) {
            Log.v(str, obj.getClass().getSimpleName() + ": " + str2);
        }
        if (isWriteToLog) {
            LogToFile(str, obj.getClass().getSimpleName() + ": " + str2);
        }
    }

    public static void v(String str, String str2) {
        if (isDebug) {
            Log.v(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void w(Object obj, String str) {
        if (isDebug) {
            Log.w(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, obj.getClass().getSimpleName() + ": " + str);
        }
    }

    public static void w(Object obj) {
        if (isDebug) {
            Log.w(DEBUG_TAG, obj.getClass().getSimpleName());
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, obj.getClass().getSimpleName());
        }
    }

    public static void w(String str) {
        if (isDebug) {
            Log.w(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }

    public static void w(String str, Object obj, String str2) {
        if (isDebug) {
            Log.w(str, obj.getClass().getSimpleName() + ": " + str2);
        }
        if (isWriteToLog) {
            LogToFile(str, obj.getClass().getSimpleName() + ": " + str2);
        }
    }

    public static void w(String str, String str2) {
        if (isDebug) {
            Log.w(str, str2);
        }
        if (isWriteToLog) {
            LogToFile(str, str2);
        }
    }

    public static void warnInfo(String str) {
        if (isDebug) {
            Log.w(DEBUG_TAG, str);
        }
        if (isWriteToLog) {
            LogToFile(DEBUG_TAG, str);
        }
    }
}
