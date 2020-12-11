package com.example.my_library.utill;

import com.example.my_library.BuildConfig;

public class LogUtill {

    private static final int MAX_TAG_SIZE = 23;
    // Objのクラス名をMAX_TAG_SIZEの文字数以内で出力してくれる。
    public static String TAG(Object obj) {
        String objName = obj.getClass().getSimpleName();
        return objName.length() > MAX_TAG_SIZE ? objName.substring(0, MAX_TAG_SIZE) : objName;
    }

    public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;
    // [クラス.メソッド名(行番号)]
    public static String CLASS_LINE() {
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            for (StackTraceElement ste: elements) {
                String cls = ste.getClassName();
                if (cls.startsWith(PACKAGE_NAME)) {
                    cls = cls.replace(PACKAGE_NAME, "");
                    return cls + "." + ste.getMethodName() + "(" + ste.getLineNumber() + ")";
                }
            }
        } catch (Exception e) {
            return "null";
        }
        return "null";
    }

    // [パッケージ名.クラス名.メソッド名(ファイル名:行数)] AndroidStudioのLogCat上からファイルの対象行に飛べる
    public static String METHOD_INFO() {
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();
            for (StackTraceElement ste: elements) {
                // 最後の改行がないとリンクされない
                if (ste.getClassName().startsWith(PACKAGE_NAME))  return ste.toString() + "\n";
            }
        } catch (Exception e) {
            return "null";
        }
        return "null";
    }

    // CALLED_BYが呼ばれているメソッドを呼び出した[クラス名.メソッド名(行数)]が出力される
    public static String CALLED_BY() {
        try {
            StackTraceElement[] elements = Thread.currentThread().getStackTrace();

            boolean breakFlg = false;
            for (StackTraceElement ste: elements) {
                String cls = ste.getClassName();
                if (breakFlg) {
                    cls = cls.replace(PACKAGE_NAME, "");
                    return cls + "." + ste.getMethodName() + "(" + ste.getLineNumber() + ")";
                }
                if (cls.startsWith(PACKAGE_NAME)) breakFlg = true;
            }
        } catch (Exception e) {
            return "null";
        }
        return "null";
    }
}
