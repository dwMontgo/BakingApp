package com.example.android.bakingapp.fragments;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.StepAdapter;
import com.example.android.bakingapp.data.IngredientList;
import com.example.android.bakingapp.data.RecipeList;
import com.example.android.bakingapp.widget.RecipeWidgetProvider;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment extends Fragment implements StepAdapter.OnItemClickListener {

    private static final String ARGS = "recipe";

    @BindView(R.id.ingredients_label)
    TextView ingredientsTextView;

    @BindView(R.id.steps_recyclerview)
    RecyclerView stepsRecyclerView;

    RecipeList recipe;

    private StepClickListener stepClickListener;

    public RecipeFragment() {
    }

    public static RecipeFragment newInstance(RecipeList recipeList) {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS, recipeList);
        recipeFragment.setArguments(args);
        return recipeFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = getArguments().getParcelable(ARGS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_overview, container, false);
        ButterKnife.bind(this, view);


        StringBuilder ingredientsString = new StringBuilder();
        for (IngredientList i : recipe.getIngredientList()) {
            ingredientsString.append(i.getIngredient()).append(" - ").append(i.getQuantity()).append(" ").append(i.getMeasure());
            ingredientsString.append("\n");
        }
        ingredientsTextView.setText(ingredientsString);


        StepAdapter stepAdapter = new StepAdapter(getActivity(), recipe.getStepList());
        stepAdapter.setClickListener(this);
        stepsRecyclerView.setAdapter(stepAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setNestedScrollingEnabled(false);


        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getActivity());
        int[] appWidgetId = appWidgetManager.getAppWidgetIds(new ComponentName(getActivity(), RecipeWidgetProvider.class));
        RecipeWidgetProvider.currentRecipe = recipe;
        RecipeWidgetProvider.updateRecipeWidget(getActivity(), appWidgetManager, appWidgetId);


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        stepClickListener = (StepClickListener) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
        stepClickListener = null;
    }

    @Override
    public void onStepClicked(int position) {
        if (stepClickListener != null) {
            stepClickListener.stepSelected(position);
        }
    }

    public interface StepClickListener {
        void stepSelected(int pos);
    }
}
