package com.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class RecipeList implements Parcelable {
    public static final Creator<RecipeList> CREATOR = new Creator<RecipeList>() {
        @Override
        public RecipeList createFromParcel(Parcel in) {
            return new RecipeList(in);
        }

        @Override
        public RecipeList[] newArray(int size) {
            return new RecipeList[size];
        }
    };
    private final int id;
    private final String name;
    private final IngredientList[] ingredientList;
    private final StepList[] stepList;
    private final int servings;

    public RecipeList(int id, String name, IngredientList[] ingredientList, StepList[] stepList, int servings) {
        this.id = id;
        this.name = name;
        this.ingredientList = ingredientList;
        this.stepList = stepList;
        this.servings = servings;
    }

    private RecipeList(Parcel in) {
        id = in.readInt();
        name = in.readString();
        ingredientList = in.createTypedArray(IngredientList.CREATOR);
        stepList = in.createTypedArray(StepList.CREATOR);
        servings = in.readInt();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public IngredientList[] getIngredientList() {
        return ingredientList;
    }

    public StepList[] getStepList() {
        return stepList;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeTypedArray(ingredientList, i);
        parcel.writeTypedArray(stepList, i);
        parcel.writeInt(servings);
    }
}
