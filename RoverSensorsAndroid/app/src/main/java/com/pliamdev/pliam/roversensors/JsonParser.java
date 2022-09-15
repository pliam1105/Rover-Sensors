package com.pliamdev.pliam.roversensors;


import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;


public class JsonParser {

  final String TAG = "JsonParser.java";

  static InputStream is = null;
  static JSONArray jObj = null;
  static String json = "";

  public JSONArray getJSONFromUrl(String url) {
//    Log.i("info", url);
    // make HTTP request
    HttpURLConnection urlConnection = null;
    try {
      URL uri = new URL(url);
      urlConnection = (HttpURLConnection) uri.openConnection();
      is = new BufferedInputStream(urlConnection.getInputStream());
    } catch (IOException e) {
      /*StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      String exceptionAsString = sw.toString();
      Log.i("info", exceptionAsString);
      e.printStackTrace();*/
    }
//    Log.i("info", String.valueOf(is));
    try {

      BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        sb.append(line + "\n");
      }
      is.close();
      json = sb.toString();
//      Log.i("info", json);
    } catch (Exception e) {
      /*StringWriter sw = new StringWriter();
      e.printStackTrace(new PrintWriter(sw));
      String exceptionAsString = sw.toString();
      Log.i("info", exceptionAsString);
      e.printStackTrace();*/
    }
    Log.i("info", json);
    // try parse the string to a JSON object
    try {
      jObj = new JSONArray(json);
    } catch (JSONException e) {
//      e.printStackTrace();
    }
    urlConnection.disconnect();
    // return JSON String
    return jObj;
  }
}
