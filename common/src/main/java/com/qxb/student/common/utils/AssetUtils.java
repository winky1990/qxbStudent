package com.qxb.student.common.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author winky
 * @date 2018/8/27
 */
public class AssetUtils {


    private static final Singleton<AssetUtils> SINGLETON = new Singleton<AssetUtils>() {
        @Override
        protected AssetUtils create() {
            return new AssetUtils();
        }
    };

    public static AssetUtils getInstance() {
        return SINGLETON.get();
    }

    private AssetUtils() {
    }


    public String readFile(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder buffer = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                buffer.append(line);
            }
            return buffer.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
