package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.widget.RemoteViews;

import com.example.android.bakingapp.DetailActivity;
import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.IngredientList;
import com.example.android.bakingapp.data.RecipeList;


public class RecipeWidgetProvider extends AppWidgetProvider {

    public static RecipeList currentRecipe;

    private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                        int appWidgetId) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget_provider);

        if (currentRecipe != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (IngredientList i : currentRecipe.getIngredientList()) {
                stringBuilder.append(i.getIngredient()).append(" - ").append(i.getQuantity()).append(" ").append(i.getMeasure()).append(")");
                stringBuilder.append("\n");
            }

            remoteViews.setTextViewText(R.id.recipe_name, currentRecipe.getName());
            remoteViews.setTextViewText(R.id.recipe_ingredients, stringBuilder);

            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(DetailActivity.RECIPE, currentRecipe);
            PendingIntent pendingIntent = TaskStackBuilder.create(context)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);


            remoteViews.setOnClickPendingIntent(R.id.root_layout, pendingIntent);
        }


        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    public static void updateRecipeWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}
