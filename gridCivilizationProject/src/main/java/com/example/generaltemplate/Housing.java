package com.example.generaltemplate;

import java.util.ArrayList;

public class Housing {
    private int xCord;
    private int yCord;
    private boolean spawn=true;
    private String name;
    private int playerOwned;
    private long spawnTime = 1000000000;
    private long startSpawnTime;
    private int numSpawned=0;
    private int level=1;
    private ArrayList<Soldier> housedSoldiers=new ArrayList<>();
    private ArrayList<Farmer> housedFarmers = new ArrayList<>();
    private int boardNum;

    public Housing(int x, int y,int playerOwned,String name,int boardNum, int[][] boardData){
        this.xCord=x;
        this.yCord=y;
        this.playerOwned=playerOwned;
        this.name=name;
        this.boardNum=boardNum;
        boardData[xCord][yCord] = boardNum;
    }

    public int getBoardNum() {
        return boardNum;
    }

    public ArrayList<Farmer> getHousedFarmers() {
        return housedFarmers;
    }

    public ArrayList<Soldier> getHousedSoldiers() {
        return housedSoldiers;
    }

    public void addHousedSoldier(Soldier newSld){
        housedSoldiers.add(newSld);
    }
    public void decreaseNumSpawned(){
        numSpawned--;
    }
    public void addHousedFarmer(Farmer newFrm){
        housedFarmers.add(newFrm);
    }
    public void rmvHousedSoldier(Soldier soldier){
        housedSoldiers.remove(soldier);
    }
    public void rmvHousedFarmer(Farmer farmer){
        housedFarmers.remove(farmer);
    }

    public int getLevel() {
        return level;
    }
    public void incLevel(){
        level++;
    }
    public void changeLoc(int newx, int newy){
        xCord=newx;
        yCord=newy;
    }
    public int getyCord() {
        return yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public String getName() {
        return name;
    }

    public int getNumSpawned() {
        return numSpawned;
    }
    public void incNumSpawned(){
        numSpawned++;
    }
    public int getPlayerOwned() {
        return playerOwned;
    }

    public void setSpawn(boolean spawn) {
        this.spawn = spawn;
        if (spawn){
            startSpawnTime = System.nanoTime();
        }
    }

    public long getStartSpawnTime() {
        return startSpawnTime;
    }

    public boolean isSpawn() {
        return spawn;
    }
    public long getSpawnTime() {
        return spawnTime;
    }
}
