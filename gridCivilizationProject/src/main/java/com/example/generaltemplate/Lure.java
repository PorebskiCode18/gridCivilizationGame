package com.example.generaltemplate;

public class Lure {
    private int xCord;
    private int yCord;
    private int numLifeLeft;
    private long startTime;
    public Lure(int x, int y, int[][] boardData,int boardNum,int numLifeLeft){
        xCord=x;
        yCord=y;
        this.numLifeLeft=numLifeLeft;
        boardData[x][y]=boardNum;
        startTime = System.nanoTime();
    }

    public int getyCord() {
        return yCord;
    }

    public int getxCord() {
        return xCord;
    }

    public void resetStartTime() {
        this.startTime = System.nanoTime();
    }
    public void dcrLife(){
        numLifeLeft--;
    }
    public long getStartTime() {
        return startTime;
    }

    public int getNumLifeLeft() {
        return numLifeLeft;
    }
}
