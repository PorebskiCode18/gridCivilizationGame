package com.example.generaltemplate;

import java.util.ArrayList;

public class Farmer {
    private int xCord;
    private int yCord;
    private int level=1;
    private long startTime;
    private String name;
    private boolean moving = true;
    private boolean chase;
    private int hitPoints = 10;
    private int boardnum;
    private int playerOwned;
    private int cropStorage=0;
    private Housing originalFarmHouse;

    public Farmer(int x, int y,int boardnum, int[][] boardData,int playerOwned,String name,Housing originalFarmHouse) {
        this.xCord = x;
        this.yCord = y;
        this.name=name;
        this.originalFarmHouse=originalFarmHouse;
        startTime = System.nanoTime();
        this.boardnum = boardnum;
        boardData[xCord][yCord] = boardnum;
        this.playerOwned = playerOwned;
    }

    public void decreaseHitPoints(int amt){
        hitPoints-=amt;
    }
    public int getPlayerOwned() {
        return playerOwned;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getBoardnum() {
        return boardnum;
    }

    public void resetStartTime() {
        this.startTime = System.nanoTime();
    }

    public long getStartTime() {
        return startTime;
    }

    public int getLevel() {
        return level;
    }

    public int getCropStorage() {
        return cropStorage;
    }

    public String getName() {
        return name;
    }

    public void changeLocation(int[][] boardData, ArrayList<FarmLand>farmLands, Players p1, Players p2) {
        if (moving){
            locs.clear();
            checkForFarmLand(farmLands,level,0);
            if (locs.size()>0){
                chase=true;
            }else{
                chase=false;
            }
            if (chase){
                int shortestDistance=Integer.MAX_VALUE;
                int shortestLoc = 0;
                for (int i = 0; i < locs.size(); i++) {
                    int tempDistance = Math.max(Math.abs(xCord - locs.get(i).getX()), Math.abs(yCord - locs.get(i).getY()));
                    if (tempDistance < shortestDistance) {
                        shortestDistance=tempDistance;
                        shortestLoc = i;
                    }
                }
                int xprev = xCord;
                int yprev = yCord;
                int xNew = xCord;
                int yNew = yCord;
                if(xCord> locs.get(shortestLoc).getX()){
                    xNew--;
                } else if (xCord<locs.get(shortestLoc).getX()) {
                    xNew++;
                }
                if (yCord>locs.get(shortestLoc).getY()){
                    yNew--;
                } else if (yCord<locs.get(shortestLoc).getY()) {
                    yNew++;
                }
                if (boardData[xNew][yNew] == 0|| boardData[xNew][yNew]==playerOwned*10){
                    xCord=xNew;
                    yCord=yNew;
                    boardData[xprev][yprev] = 0;
                    boardData[xCord][yCord] = boardnum;
                }else if (boardData[xNew][yCord]==0||boardData[xNew][yCord]==playerOwned*10){
                    xCord = xNew;
                    boardData[xprev][yprev] = 0;
                    boardData[xCord][yCord] = boardnum;
                }else if (boardData[xCord][yNew]==0||boardData[xCord][yNew]==playerOwned*10){
                    yCord = yNew;
                    boardData[xprev][yprev] = 0;
                    boardData[xCord][yCord] = boardnum;
                }
                for (FarmLand x:farmLands){
                    if (locs.get(shortestLoc).getX()==x.getxCord()&&locs.get(shortestLoc).getY()==x.getyCord()){
                        x.incCropLevel();
                    }
                }
            }else {
                if (cropStorage < 10 * level) {
                    checkForFarmLand(farmLands, level, 4);
                    boolean chase2;
                    if (locs.size() > 0) {
                        chase2 = true;
                    } else {
                        chase2 = false;
                    }
                    if (chase2) {
                        int shortestDistance = Integer.MAX_VALUE;
                        int shortestLoc = 0;
                        for (int i = 0; i < locs.size(); i++) {
                            int tempDistance = Math.max(Math.abs(xCord - locs.get(i).getX()), Math.abs(yCord - locs.get(i).getY()));
                            if (tempDistance < shortestDistance) {
                                shortestDistance = tempDistance;
                                shortestLoc = i;
                            }
                        }
                        int xprev = xCord;
                        int yprev = yCord;
                        int xNew = xCord;
                        int yNew = yCord;
                        if (xCord > locs.get(shortestLoc).getX()) {
                            xNew--;
                        } else if (xCord < locs.get(shortestLoc).getX()) {
                            xNew++;
                        }
                        if (yCord > locs.get(shortestLoc).getY()) {
                            yNew--;
                        } else if (yCord < locs.get(shortestLoc).getY()) {
                            yNew++;
                        }
                        if (boardData[xNew][yNew] == 0 || boardData[xNew][yNew] == playerOwned * 10) {
                            xCord = xNew;
                            yCord = yNew;
                            boardData[xprev][yprev] = 0;
                            boardData[xCord][yCord] = boardnum;
                        }
                        for (FarmLand x : farmLands) {
                            if (locs.get(shortestLoc).getX() == x.getxCord() && locs.get(shortestLoc).getY() == x.getyCord()) {
                                x.resetCropLevel();
                                cropStorage += 2;
                            }
                        }

                    } else {
                        locs.clear();
                        checkWithinDistanceForSomething(boardData, playerOwned * 10, 1);
                        if (locs.size() > 0) {
                            int xprev = xCord;
                            int yprev = yCord;
                            int rnum = (int) (Math.random() * locs.size());
                            xCord = locs.get(rnum).getX();
                            yCord = locs.get(rnum).getY();
                            boardData[xprev][yprev] = 0;
                            boardData[xCord][yCord] = boardnum;
                        }
                    }
                }else{
                    locs.clear();
                    checkWithinDistanceForSomething(boardData,playerOwned*10+2,3);
                    boolean chase3;
                    if (locs.size()>0){
                        chase3=true;
                    }else{
                        chase3=false;
                    }
                    if (chase3){
                        int shortestDistance = Integer.MAX_VALUE;
                        int shortestLoc = 0;
                        for (int i = 0; i < locs.size(); i++) {
                            int tempDistance = Math.max(Math.abs(xCord - locs.get(i).getX()), Math.abs(yCord - locs.get(i).getY()));
                            if (tempDistance < shortestDistance) {
                                shortestDistance = tempDistance;
                                shortestLoc = i;
                            }
                        }
                        int xprev = xCord;
                        int yprev = yCord;
                        int xNew = xCord;
                        int yNew = yCord;
                        if (xCord > locs.get(shortestLoc).getX()) {
                            xNew--;
                        } else if (xCord < locs.get(shortestLoc).getX()) {
                            xNew++;
                        }
                        if (yCord > locs.get(shortestLoc).getY()) {
                            yNew--;
                        } else if (yCord < locs.get(shortestLoc).getY()) {
                            yNew++;
                        }
                        if (boardData[xNew][yNew] == 0 || boardData[xNew][yNew] == playerOwned * 10) {
                            xCord = xNew;
                            yCord = yNew;
                            boardData[xprev][yprev] = 0;
                            boardData[xCord][yCord] = boardnum;
                        }
                        locs.clear();
                        checkWithinDistanceForSomething(boardData,playerOwned*10+2,1);
                        if (locs.size()>0){
                            if (playerOwned==1){
                                p1.incCashAmt(cropStorage/10*originalFarmHouse.getLevel());
                            }else if (playerOwned==2){
                                p2.incCashAmt(cropStorage/10*originalFarmHouse.getLevel());
                            }
                            cropStorage=0;
                        }
                    }else{
                        locs.clear();
                        checkWithinDistanceForSomething(boardData, playerOwned * 10, 1);
                        if (locs.size() > 0) {
                            int xprev = xCord;
                            int yprev = yCord;
                            int rnum = (int) (Math.random() * locs.size());
                            xCord = locs.get(rnum).getX();
                            yCord = locs.get(rnum).getY();
                            boardData[xprev][yprev] = 0;
                            boardData[xCord][yCord] = boardnum;
                        }
                    }
                }
            }
        }
    }

    public int getyCord() {
        return yCord;
    }

    public int getxCord() {
        return xCord;
    }

    private ArrayList<Locations> locs = new ArrayList<>();
    public void checkForFarmLand(ArrayList<FarmLand>allFarmLand,int distance,int cropLevel){
        for (int r = xCord - distance; r <= xCord + distance; r++) {
            for (int c = yCord - distance; c <= yCord + distance; c++) {
                for (FarmLand x:allFarmLand){
                    if (x.getxCord()==r && x.getyCord()==c && x.getCropLevel()==cropLevel){
                        locs.add(new Locations(r,c));
                    }
                }
            }
        }
    }

    public void checkWithinDistanceForSomething(int[][] boardData, int thingToCheck, int distance) {
        for (int r = xCord - distance; r <= xCord + distance; r++) {
            for (int c = yCord - distance; c <= yCord + distance; c++) {
                if (r < boardData.length && r >= 0 && c < boardData[r].length && c >= 0) {
                    if (boardData[r][c] == thingToCheck && !(r == xCord && c == yCord)) {
                        locs.add(new Locations(r, c));
                    }
                }

            }

        }
    }
    public void incLevel(){
        level++;
    }
    public void incHitPoints(int amt){
        hitPoints+=amt;
    }
    public void setMoving(boolean moving) {
        this.moving = moving;
    }
}
