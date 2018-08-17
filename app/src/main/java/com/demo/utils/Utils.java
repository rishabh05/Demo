package com.demo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import org.apache.http.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Admin on 8/17/2018.
 */

public class Utils {

    /**
     * convert HTTP response  to String .
     * @param response
     * @return String
     * @throws Exception
     */
    public static String convertResponseToString(HttpResponse response) {
        InputStream in = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = response.getEntity().getContent();
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String serverResponse = null;
            while ((serverResponse = reader.readLine()) != null) {
                sb.append(serverResponse);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        return str;
    }

    /**
     * convert InputStream to String .
     * @param response
     * @return String
     * @throws Exception
     */
    public static String convertResponseToString(InputStream response) {
        InputStream in = null;
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        try {
            in = response;
            reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String serverResponse = null;
            while ((serverResponse = reader.readLine()) != null) {
                sb.append(serverResponse);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String str = sb.toString();
        return str;
    }
    /**
     * Method For Show Message
     */
    public static void toast(Context context, String string) {

        Toast toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * For Check Internet Connection available or not
     */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
