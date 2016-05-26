package com.chicago.peng.vendingmachine_v1_8;

/**
 * Created by Peng on 5/26/16.
 */
public class Beverage {
    public int id;
    public String name;
    public double cost;
    public int calories;
    public int quantity;

    Beverage(int id, String name, double cost, int calories, int quantity) {
        this.id = id;
        this.name = name;
        this.cost = cost;
        this.calories = calories;
        this.quantity = quantity;
    }
}
