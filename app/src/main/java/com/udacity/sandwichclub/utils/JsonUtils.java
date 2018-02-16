package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json) {
        JSONObject baseJsonObject;

        try {
            baseJsonObject = new JSONObject(json);
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: There was a problem parsing the JSON string.", e);
            return null;
        }

        String mainName = getStringFromJson(baseJsonObject, "mainName");
        List<String> alsoKnownAsList = getListOfStringsFromJsonArray(baseJsonObject, "alsoKnownAs");
        String placeOfOrigin = getStringFromJson(baseJsonObject, "placeOfOrigin");
        String description = getStringFromJson(baseJsonObject, "description");
        String image = getStringFromJson(baseJsonObject, "image");
        List<String> ingregientsList = getListOfStringsFromJsonArray(baseJsonObject, "ingredients");

        return new Sandwich(
                mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                image,
                ingregientsList
        );
    }

    private static List<String> getListOfStringsFromJsonArray(JSONObject jsonObject, String key) {
        List<String> resultList = null;

        try {
            JSONArray jsonArray = jsonObject.getJSONArray(key);

            if (jsonArray != null && jsonArray.length() > 0) {
                resultList = new ArrayList<>();

                for (int i = 0; i < jsonObject.length(); i++) {
                    String alsoKnownAsEntry = jsonArray.getString(i);
                    resultList.add(alsoKnownAsEntry);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "getListOfStringsFromJsonArray: JSONArray with key: " + key + " cannot be processed.", e);
        }

        return resultList;
    }

    private static String getStringFromJson(JSONObject jsonObject, String key) {
        String resultString = null;

        try {
            resultString = jsonObject.getString(key);
        } catch (JSONException e) {
            Log.e(TAG, "getStringFromJson: String with key: " + key + " cannot be extracted.", e);
        }

        return resultString;
    }
}
