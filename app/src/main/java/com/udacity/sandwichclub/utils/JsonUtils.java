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
        JSONObject nameJsonObject;

        try {
            baseJsonObject = new JSONObject(json);
            nameJsonObject = baseJsonObject.getJSONObject("name");
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: There was a problem parsing the JSON string.", e);
            return null;
        }

        String mainName = getStringFromJson(nameJsonObject, "mainName");
        List<String> alsoKnownAsList =
                getListOfStringsFromJsonArray(nameJsonObject, "alsoKnownAs");
        String placeOfOrigin = getStringFromJson(baseJsonObject, "placeOfOrigin");
        String description = getStringFromJson(baseJsonObject, "description");
        String image = getStringFromJson(baseJsonObject, "image");
        List<String> ingredientsList =
                getListOfStringsFromJsonArray(baseJsonObject, "ingredients");

        return new Sandwich(
                mainName,
                alsoKnownAsList,
                placeOfOrigin,
                description,
                image,
                ingredientsList
        );
    }

    private static List<String> getListOfStringsFromJsonArray(JSONObject jsonObject, String key) {
        List<String> resultList = null;

        try {
            JSONArray jsonStringArray = jsonObject.getJSONArray(key);

            if (jsonStringArray.length() > 0) {
                resultList = new ArrayList<>();

                for (int i = 0; i < jsonStringArray.length(); i++) {
                    String entry = jsonStringArray.getString(i);
                    resultList.add(entry);
                }
            }
        } catch (JSONException e) {
            Log.e(TAG, "getListOfStringsFromJsonArray: JSONArray with key: " + key
                    + " cannot be processed.", e);
        }

        return resultList;
    }

    private static String getStringFromJson(JSONObject jsonObject, String key) {
        String resultString = null;

        try {
            resultString = jsonObject.getString(key);
        } catch (JSONException e) {
            Log.e(TAG, "getStringFromJson: String with key: " + key
                    + " cannot be extracted.", e);
        }

        return resultString;
    }
}
