package com.couchbase.touchdb;

import android.os.Build;

import net.sqlcipher.database.SQLiteDatabase;

import java.text.Collator;

public class TDCollateJSON {

    public static void registerCustomCollators(SQLiteDatabase database, String libPath) {
        nativeRegisterCustomCollators(database, Build.VERSION.SDK_INT, libPath);
    }

    public static int compareStringsUnicode(String a, String b) {
        Collator c = Collator.getInstance();
        int res = c.compare(a, b);
        return res;
    }

    private static native void nativeRegisterCustomCollators(SQLiteDatabase database, int sdkVersion, String libPath);

    //FIXME only public for now until tests are moved int same package
    public static native int testCollateJSON(int mode, int len1, String string1, int len2, String string2);
    public static native char testEscape(String source);
    public static native int testDigitToInt(int digit);

    /**
     * Convenience wrapper around testCollateJSON which calculates lengths based on string lengths
     * of params.
     */
    public static int testCollateJSONWrapper(int mode, String string1, String string2) {
        return testCollateJSON(mode, string1.length(), string1, string2.length(), string2);
    }

    static {
        System.loadLibrary("com_couchbase_touchdb_TDCollateJSON");
    }

}
