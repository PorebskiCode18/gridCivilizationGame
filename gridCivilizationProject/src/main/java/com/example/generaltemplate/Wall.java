package com.example.generaltemplate;

public class Wall {
    private int xCord;
    private int yCord;
    private int playerOwned;
    private long startShootTime;
    private int boardNum;
    private int shots;
    private int level=1;
    public Wall(int x, int y, int playerOwned, int[][] boardData, int boardNum){
        xCord=x;
        yCord=y;
        this.playerOwned=playerOwned;
        boardData[x][y]=boardNum;
        startShootTime=System.nanoTime();
    }

    public int getShots() {
        return shots;
    }
    public void incShots(){
        shots++;
    }
    public void resetShots(){
        shots=0;
    }
    public void dcrLevel(){
        level--;
    }
    public long getStartShootTime() {
        return startShootTime;
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

    public int getBoardNum() {
        return boardNum;
    }

    public int getPlayerOwned() {
        return playerOwned;
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }
}
