
package com.demo.utils;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This is Base class which is responsible for all network related operation .
 */
public class BaseService {

    protected RequestCallback requestCallback = null;

    public BaseService() {
    }

    public void callNetworkAPI(String url, String httpMethodType, String methodName, String jsonString, RequestCallback requestCallback) {
        new NetworkAsyncTask(url, httpMethodType, methodName, jsonString).
                executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, requestCallback);
    }

    /*Common thread for background operation for GET, POST, PUT, DELETE, NONE and NONE WITH EMP ID*/
    protected class NetworkAsyncTask extends AsyncTask<RequestCallback, Void, Void> {

        private String url = "";
        private String httpMethodType = "";
        private String methodName = "";
        private String jsonString = "";

        public NetworkAsyncTask(String url, String httpMethodType, String methodName, String jsonString) {
            this.url = url;
            this.httpMethodType = httpMethodType;
            this.methodName = methodName;
            this.jsonString = jsonString;
        }

        @Override
        protected Void doInBackground(RequestCallback... params) {
            switch (httpMethodType) {
                //Get entities from server e.g. for requiedSetPassword method
                case Commons.GET:
                    callHttpMethodInBackground(Commons.ZERO, params);
                    break;

                //Add entities e.g. for add employees
                case Commons.POST:
                    callHttpMethodInBackground(Commons.ONE, params);
                    break;

                //Update entities  e.g. for update employees
                case Commons.PUT:
                    callHttpMethodInBackground(Commons.TWO, params);
                    break;

                //Delete entities from server e.g. delete employee from group
                case Commons.DELETE:
                    callHttpMethodInBackground(Commons.THREE, params);
                    break;
            }
            return null;
        }

        /*
        * Common call for Http GET, POST, PUT, DELETE and return callback in calling method
        * */
        private Void callHttpMethodInBackground(int i, RequestCallback... params) {
            URL url = null;
            HttpURLConnection connection = null;
            OutputStream os = null;
            try {
                url = new URL(this.url);
                connection = (HttpURLConnection) url.openConnection();


                // Set HTTP method to POST.

                switch (i) {
                    //For GET
                    case 0:
                        connection.setRequestMethod(Commons.GET);
                        connection.setRequestProperty(Commons.CONTENT_TYPE, Commons.APPLICATION_JSON);
                        break;

                    //For POST
                    case 1:
                        // Allow Inputs &amp; Outputs.
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.setConnectTimeout(120000);
                        connection.setRequestMethod(Commons.POST);

                        connection.setRequestProperty(Commons.CONTENT_TYPE, Commons.APPLICATION_JSON);
                        connection.setRequestProperty(Commons.ACCEPT, Commons.APPLICATION_JSON);
                        os = connection.getOutputStream();
                        os.write(jsonString.getBytes());
                        os.flush();
                        break;

                    //For PUT
                    case 2:
                        connection.setRequestMethod(Commons.PUT);
                        os.write(jsonString.getBytes());
                        os.flush();
                        connection.setRequestProperty(Commons.CONTENT_TYPE, Commons.APPLICATION_JSON);
                        connection.setRequestProperty(Commons.ACCEPT, Commons.APPLICATION_JSON);
                        break;

                    //For DELETE
                    case 3:
                        connection.setRequestMethod(Commons.DELETE);
                        connection.setRequestProperty(Commons.CONTENT_TYPE, Commons.APPLICATION_JSON);
                        connection.setRequestProperty(Commons.ACCEPT, Commons.APPLICATION_JSON);
                        break;
                }

            } catch (Exception e) {
                if (!TextUtils.isEmpty(methodName))
                    params[Commons.ZERO].onFailure(methodName, e);
                e.printStackTrace();
                return null;
            }
            try {
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    String out = Utils.convertResponseToString(connection.getInputStream());
                    System.out.println("GETTING SERVER RESPONSE 200 HERE");
                    Log.d(Commons.RESPONSE, out);
                    if (!TextUtils.isEmpty(methodName))
                        params[Commons.ZERO].onSuccess(methodName, out);

                } else {
                    if (!TextUtils.isEmpty(methodName))
                        params[Commons.ZERO].onFailure(methodName, connection.getErrorStream());
                }
            } catch (Exception e) {
                if (!TextUtils.isEmpty(methodName))
                    params[Commons.ZERO].onFailure(methodName, e);
            }
            return null;
        }
    }

}
