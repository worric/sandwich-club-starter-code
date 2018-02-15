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

        Sandwich resultSandwich = null;

        try {
            JSONObject baseObject = new JSONObject(json);

            String mainName = baseObject.getString("mainName");

            JSONArray alsoKnownAsArray = baseObject.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = null;
            if (alsoKnownAsArray.length() > 0) {
                alsoKnownAsList = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsArray.length(); i++) {
                    String alsoKnownAsEntry = alsoKnownAsArray.getString(i);
                    alsoKnownAsList.add(alsoKnownAsEntry);
                }
            }

            String placeOfOrigin = baseObject.getString("placeOfOrigin");

            String description = baseObject.getString("description");

            String image = baseObject.getString("image");

            JSONArray ingredientsArray = baseObject.getJSONArray("ingredients");
            List<String> ingregientsList = null;
            if (ingredientsArray.length() > 0) {
                ingregientsList = new ArrayList<>();
                for (int i = 0; i < ingredientsArray.length(); i++) {
                    String ingredientsEntry = ingredientsArray.getString(i);
                    ingregientsList.add(ingredientsEntry);
                }
            }

            resultSandwich = new Sandwich(
                    mainName,
                    alsoKnownAsList,
                    placeOfOrigin,
                    description,
                    image,
                    ingregientsList
            );
        } catch (JSONException e) {
            Log.e(TAG, "parseSandwichJson: Error in JSON parsing", e);
        }

        return resultSandwich;
    }
}
