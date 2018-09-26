package com.example.android.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.bakingapp.fragments.StepFragment;
import com.example.android.bakingapp.data.RecipeList;

public class StepActivity extends AppCompatActivity
        implements StepFragment.ClickListener {

    public static final String RECIPE = "recipe";
    public static final String ID = "id";
    private static final String FRAGMENT = "fragment";
    private StepFragment stepFragment;
    private RecipeList recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        Bundle bundle = getIntent().getExtras();
        int stepInt;


        recipeList = bundle.getParcelable(RECIPE);
        stepInt = bundle.getInt(ID);

        getSupportActionBar().setTitle(recipeList.getName());

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment frag = fragmentManager.findFragmentByTag(FRAGMENT);
        if(frag == null) {
            stepFragment = StepFragment.newInstance(recipeList.getStepList()[stepInt], recipeList.getStepList().length - 1);
            fragmentManager.beginTransaction()
                    .add(R.id.frame_layout_steps, stepFragment, FRAGMENT)
                    .commit();
        }
    }


    @Override
    public void selectedStep(int position) {
        stepFragment = StepFragment.newInstance(recipeList.getStepList()[position],
                recipeList.getStepList().length - 1);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_steps, stepFragment, FRAGMENT);
        fragmentTransaction.commit();
    }
}
