package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.data.RecipeList;

import java.util.List;


public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeHolder> {


    private final Context context;
    private final List<RecipeList> recipeList;

    private ItemClickListener clickListener;


    public RecipeAdapter(Context context, List<RecipeList> recipes) {
        this.context = context;
        recipeList = recipes;
    }

    public void setClickListener(ItemClickListener listener) {
        clickListener = listener;
    }

    @Override
    public RecipeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeHolder(v);
    }

    @Override
    public void onBindViewHolder(RecipeHolder recipeHolder, int position) {
        recipeHolder.name.setText(recipeList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }


    public interface ItemClickListener {
        void recipeSelected(View view, int pos);
    }

    public class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView name;


        RecipeHolder(View view) {
            super(view);
            name = view.findViewById(R.id.recipe_name);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.recipeSelected(view, getAdapterPosition());
        }
    }
}

