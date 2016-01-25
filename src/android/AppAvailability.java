package com.ohh2ahh.appavailability;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppAvailability extends CordovaPlugin {
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if(action.equals("checkAvailability")) {
            String uri = args.getString(0);
            this.checkAvailability(uri, callbackContext);
            return true;
        }
        return false;
    }
    private void checkAvailability(String uri, CallbackContext callbackContext) throws JSONException {
            Context ctx = this.cordova.getActivity().getApplicationContext();
            PackageManager packageManager = this.cordova.getActivity().getPackageManager();
            boolean app_installed = false;
            JSONObject appData = new JSONObject();
            JSONObject versionJson = new JSONObject();
            try {
                String[] version = packageManager.getPackageInfo(uri, 0).versionName.split(" ");
                String[] versionDetails = version[0].split("\\.");
                versionJson.put("major", Integer.parseInt(versionDetails[0]));
                versionJson.put("minor", Integer.parseInt(versionDetails[1]));
                versionJson.put("revision", Integer.parseInt(versionDetails[2]));
                appData.put("versionName", packageManager.getPackageInfo(uri, 0).versionName);
                appData.put("versionCode", packageManager.getPackageInfo(uri, 0).versionCode);
                appData.put("versionDetails", versionJson);
                callbackContext.success(appData);
            }
            catch(PackageManager.NameNotFoundException e) {
                callbackContext.error("");
            }

    }
}
