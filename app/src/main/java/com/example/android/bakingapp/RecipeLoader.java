package com.example.android.bakingapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

class RecipeLoader extends AsyncTaskLoader<String> {
    private final String recipeListUrl;

    public RecipeLoader(Context context, String url) {
        super(context);
        recipeListUrl = url;
    }

    @Override
    public String loadInBackground() {
        return new Utils().establishConnection(recipeListUrl);
    }

    @Override
    public void deliverResult(String result) {
        super.deliverResult(result);
    }
}
