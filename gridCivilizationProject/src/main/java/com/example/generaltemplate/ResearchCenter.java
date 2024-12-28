package com.example.generaltemplate;

public class ResearchCenter {
    private int xCord;
    private int yCord;
    private int playerOwned;
    private long startSpawnTime;
    private int boardNum;
    private int level=1;
    public ResearchCenter(int x,int y,int[][] boardData,int boardNum,int playerOwned){
        xCord=x;
        yCord=y;
        this.playerOwned=playerOwned;
        this.boardNum=boardNum;
        boardData[x][y]=boardNum;
    }
    public int getBoardNum() {
        return boardNum;
    }
    public void changeLoc(int newx, int newy){
        xCord=newx;
        yCord=newy;
    }
    public int getLevel() {
        return level;
    }
    public void incLevel(){
        level++;
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
