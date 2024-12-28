package com.example.generaltemplate;

public class FarmLand {
    private int xCord;
    private int yCord;
    private int cropLevel=0;
    private long spawnTime = 1000000000;
    private long startSpawnTime;
    public FarmLand(int x,int y,int[][] boardData,int boardNum){
        xCord=x;
        yCord=y;
        boardData[x][y]=boardNum;
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public long getStartSpawnTime() {
        return startSpawnTime;
    }
    public void resetCropLevel(){
        cropLevel=0;
    }
    public void changeLoc(int newx, int newy){
        xCord=newx;
        yCord=newy;
    }
    public void incCropLevel(){
        cropLevel++;
    }
    public int getCropLevel() {
        return cropLevel;
    }

    public int getyCord() {
        return yCord;
    }

    public int getxCord() {
        return xCord;
    }
}
