package com.example.generaltemplate;

public class Locations {
    int x,y,chrctType;
    public Locations(int xx, int yy){
        x = xx;
        y = yy;
    }
    public Locations(int xx, int yy,int chrctType){
        x = xx;
        y = yy;
        this.chrctType=chrctType;
    }

    public int getChrctType() {
        return chrctType;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
