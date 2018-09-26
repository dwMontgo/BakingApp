package com.example.android.bakingapp;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.example.android.bakingapp.data.IngredientList;
import com.example.android.bakingapp.data.RecipeList;
import com.example.android.bakingapp.data.StepList;

class Utils {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String SERVINGS = "servings";
    private static final String INGREDIENTS = "ingredients";
    private static final String QUANTITY = "quantity";
    private static final String MEASURE = "measure";
    private static final String INGREDIENT = "ingredient";
    private static final String STEPS = "steps";
    private static final String STEP_ID = "id";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";

    public static List<RecipeList> retrieveRecipeList(String json) {

        try {
            List<RecipeList> recipeList = new ArrayList<>();

            JSONArray recipeArray = new JSONArray(json);

            for(int i = 0; i < recipeArray.length(); ++i) {
                JSONObject recipeObject = recipeArray.getJSONObject(i);

                int id = recipeObject.optInt(ID);
                String name = recipeObject.optString(NAME);
                int servings = recipeObject.optInt(SERVINGS);

                IngredientList[] ingredientList = retrieveIngredientList(recipeObject.getJSONArray(INGREDIENTS));

                StepList[] stepList = retrieveStepList(recipeObject.getJSONArray(STEPS));

                recipeList.add(new RecipeList(id, name, ingredientList, stepList, servings));
            }

            return recipeList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static StepList[] retrieveStepList(JSONArray stepArray) throws JSONException {
        StepList[] stepList = new StepList[stepArray.length()];

        for(int i = 0; i < stepArray.length(); ++i) {
            JSONObject stepObject = stepArray.getJSONObject(i);

            stepList[i] = new StepList();
            stepList[i].setId(stepObject.optInt(STEP_ID));
            stepList[i].setDescription(stepObject.optString(DESCRIPTION));
            stepList[i].setShortDescription(stepObject.optString(SHORT_DESCRIPTION));
            stepList[i].setThumbnailURL(stepObject.optString(THUMBNAIL_URL));
            stepList[i].setVideoURL(stepObject.optString(VIDEO_URL));
        }

        return stepList;
    }

    private static IngredientList[] retrieveIngredientList(JSONArray ingredientArray) throws JSONException {
        IngredientList[] ingredientList = new IngredientList[ingredientArray.length()];

        for(int i = 0; i < ingredientArray.length(); ++i) {
            JSONObject ingredientObject = ingredientArray.getJSONObject(i);

            ingredientList[i] = new IngredientList();
            ingredientList[i].setIngredient(ingredientObject.optString(INGREDIENT));
            ingredientList[i].setMeasure(ingredientObject.optString(MEASURE));
            ingredientList[i].setQuantity(ingredientObject.optInt(QUANTITY));
        }

        return ingredientList;
    }

    public String establishConnection(String url) {

        HttpURLConnection httpUrlConnection = null;
        try {
            httpUrlConnection = (HttpURLConnection) new URL(url).openConnection();
            InputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());

            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            if(!scanner.hasNext())
                return "";

            return scanner.next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert httpUrlConnection != null;
            httpUrlConnection.disconnect();
        }

        return "";
    }
}