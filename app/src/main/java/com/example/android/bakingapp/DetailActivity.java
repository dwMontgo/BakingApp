package com.example.android.bakingapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.bakingapp.fragments.RecipeFragment;
import com.example.android.bakingapp.fragments.StepFragment;
import com.example.android.bakingapp.data.RecipeList;


public class DetailActivity extends AppCompatActivity
        implements RecipeFragment.StepClickListener,
        StepFragment.ClickListener {

    public static final String RECIPE = "recipe";

    private RecipeList recipeList;
    private boolean tablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null && bundle.containsKey(RECIPE)) {
            recipeList = bundle.getParcelable(RECIPE);
        }

        getSupportActionBar().setTitle(recipeList.getName());


        if(savedInstanceState == null) {
            RecipeFragment recipeFragment = RecipeFragment.newInstance(recipeList);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame_layout_detail, recipeFragment)
                    .commit();
        }

        tablet = getResources().getBoolean(R.bool.tabletBoolean);
    }



    @Override
    public void stepSelected(int pos) {

        if(!tablet) {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(StepActivity.RECIPE, recipeList);
            intent.putExtra(StepActivity.ID, pos);
            startActivity(intent);
        } else {
            StepFragment fragment = StepFragment.newInstance(recipeList.getStepList()[pos],
                    recipeList.getStepList().length - 1);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_steps, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void selectedStep(int position) {
        stepSelected(position);
    }
}
