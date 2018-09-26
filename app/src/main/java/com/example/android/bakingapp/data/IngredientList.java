package com.example.android.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;


public class IngredientList implements Parcelable {
    public static final Creator<IngredientList> CREATOR = new Creator<IngredientList>() {
        @Override
        public IngredientList createFromParcel(Parcel in) {
            return new IngredientList(in);
        }

        @Override
        public IngredientList[] newArray(int size) {
            return new IngredientList[size];
        }
    };
    private int quantity;
    private String measure;
    private String ingredient;

    public IngredientList() {
    }

    private IngredientList(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(quantity);
        parcel.writeString(measure);
        parcel.writeString(ingredient);
    }
}
