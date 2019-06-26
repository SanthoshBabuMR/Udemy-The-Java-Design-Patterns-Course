package com.babusa.java.designprinciples.programmingtointerface;

import java.util.ConcurrentModificationException;

interface Fuel {
    void cook();
}

class LPG implements Fuel {
    @Override
    public void cook() {
        System.out.println("Cooking with LPG");
    }
}

class FireWood implements Fuel {
    @Override
    public void cook() {
        System.out.println("Cooking with FireWood");
    }
}
// Client "Cook" is insulated from changes of new implementation
class FuelFactory {
    public static Fuel get (String type) throws Exception {
        Fuel cf;
        switch (type) {
            case "lpg":
                cf = new LPG();
                break;
            case "firewood":
                cf = new FireWood();
                break;
            default:
                throw new Exception(String.format("Cooking fuel type '%s' unavailable", type));
        }
        return cf;
    }
}

class Cook {
    Fuel fuel;
    public void setFuel(Fuel fuel) {
        this.fuel = fuel; // client not concerned with instantiation of Fuel type
    }
    public void cook() {
        fuel.cook();
    }
}
public class Main {
    public static void main(String[]args)  throws Exception {
        boolean isLPGOutOfStock = true;
        Cook ck = new Cook();
        ck.setFuel(FuelFactory.get("lpg"));
        ck.cook();
        if(isLPGOutOfStock) {
            ck.setFuel(FuelFactory.get("firewood")); // concrete class implementation can change during runtime
        }
        ck.cook();
        try {
            ck.setFuel(FuelFactory.get("eletric"));
            ck.cook();
        } catch(Exception e) {
            System.out.println("Exception");
            System.out.println(e);
            System.out.println("Lets order online");
        }
    }
}
