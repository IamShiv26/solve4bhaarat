package com.example.eldercare;

public class FoodDets {
    private String Food_Type;
    private String Food_and_Serving;
    private int Sugars;

    public FoodDets() {}

    public FoodDets(String foodType, String foodName, int sugarLevel) {
        this.Food_Type = foodType;
        this.Food_and_Serving = foodName;
        this.Sugars = sugarLevel;
    }

    public int getSugars() {
        return Sugars;
    }

    public String getFood_and_Serving() {
        return Food_and_Serving;
    }

    public String getFood_Type() {
        return Food_Type;
    }

    public void setFood_and_Serving(String food_and_Serving) {
        this.Food_and_Serving = food_and_Serving;
    }

    public void setFood_Type(String food_Type) {
        this.Food_Type = food_Type;
    }

    public void setSugars(int sugars) {
        this.Sugars = sugars;
    }

}
