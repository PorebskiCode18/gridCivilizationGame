package com.example.generaltemplate;

public class TownCenter {
    private int playerOwned;
    private int xCord;
    private int yCord;

    public TownCenter(int x, int y, int[][] boardData,int playerOwned){
        xCord=x;
        yCord=y;
        this.playerOwned=playerOwned;
        if (playerOwned==1){
            boardData[x][y]=11;
        }else if (playerOwned==2){
            boardData[x][y]=21;
        }
    }

    public int getxCord() {
        return xCord;
    }

    public int getyCord() {
        return yCord;
    }

    public int getPlayerOwned() {
        return playerOwned;
    }
}
