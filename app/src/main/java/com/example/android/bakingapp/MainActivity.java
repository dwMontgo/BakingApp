package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.android.bakingapp.adapters.RecipeAdapter;
import com.example.android.bakingapp.data.RecipeList;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>,
        RecipeAdapter.ItemClickListener {

    @BindView(R.id.recycler_view_main)
    RecyclerView recyclerView;

    private static final int LOADER_INT = 1;
    private List<RecipeList> recipeList;
    private static final String RECIPE_LIST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadRecipeList();
    }


    private void loadRecipeList() {
        getSupportLoaderManager().restartLoader(LOADER_INT, null, this).forceLoad();
    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this, RECIPE_LIST_URL);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        recipeList = Utils.retrieveRecipeList(data);
        RecipeAdapter adapter = new RecipeAdapter(this, recipeList);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void recipeSelected(View view, int position) {

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.RECIPE, recipeList.get(position));
        startActivity(intent);
    }
}
