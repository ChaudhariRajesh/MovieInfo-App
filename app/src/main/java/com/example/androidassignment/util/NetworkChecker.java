package com.example.androidassignment.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/*
Simple custom class for checking the network connectivity.
Returns True if network is present, else returns False.
*/

public class NetworkChecker {

    public static boolean connectionStatus(Context c)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) c.getSystemService(c.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null)
        {
            return true;
        }
        return false;
    }

}
