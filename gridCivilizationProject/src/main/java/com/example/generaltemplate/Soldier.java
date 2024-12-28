package com.example.generaltemplate;

import javafx.scene.control.Label;

import java.util.ArrayList;

public class Soldier {
    private int xCord;
    private int yCord;
    private long startTime;
    private boolean moving = true;
    private boolean chase;
    private int level=1;
    private String name;
    private int hitPoints=25;
    private boolean battle = false;
//    private Farmer battleFarmer;
//    private Soldier battleSolider;
    private int playerOwned;
    private int boardnum;
    private int kills;
    private boolean followSolider;
    private ArrayList<Locations> locs = new ArrayList<>();
    private ArrayList<Locations> soliderLocs = new ArrayList<>();
    private ArrayList<Locations> farmerLocs = new ArrayList<>();

    public Soldier(int x,int y,int boardNum, int[][] boardData,int playerOwned,String name){
        xCord=x;
        yCord=y;
        this.name=name;
        startTime=System.nanoTime();
        this.boardnum=boardNum;
        boardData[xCord][yCord]=boardNum;
        this.playerOwned=playerOwned;
    }

    public String getName() {
        return name;
    }

    public int getKills() {
        return kills;
    }
    public void incHitPoints(int amt){
        hitPoints+=amt;
    }

    public void changeLocation(int[][] boardData, ArrayList<Farmer> farmers, ArrayList<Soldier> soldiers, ArrayList<Battle>battles, Label GameOver,Players p1,Players p2){
        if (moving){
            int otherPlayer;
            if (playerOwned==1){
                otherPlayer=2;
            }else{
                otherPlayer=1;
            }
            locs.clear();
            checkWithinDistanceForSomething(boardData,10*otherPlayer+1,1);
            if (locs.size()>0){
                GameOver.setText("Game Over: Player " + playerOwned + " wins");
            }
            locs.clear();
            checkWithinDistanceForSomething(boardData,4,1);
            for (int i=0;i<locs.size();i++){
                if (playerOwned==1){
                    p1.incWoodAmt((int)(Math.random()*3*level));
                }else{
                    p2.incWoodAmt((int)(Math.random()*3*level));
                }
                boardData[locs.get(i).getX()][locs.get(i).getY()]=0;
            }
            soliderLocs.clear();
            farmerLocs.clear();
            if (soldiers.size()>0){
                soliderLocs=getLocsOfOpposingSoldier(soldiers,1+level*2);
            }
            if (farmers.size()>0){
                farmerLocs=getLocsOfOpposingFarmer(farmers,1+level*2);
            }
            if (farmerLocs.size()>0|| soliderLocs.size()>0){
                chase=true;
            }else{
                chase=false;
            }
            boolean lure;
            locs.clear();
            checkWithinDistanceForSomething(boardData,playerOwned*10+8,5);
            ArrayList <Locations> lureLocs=new ArrayList<>();
            for (Locations x: locs){
                lureLocs.add(new Locations(x.getX(),x.getY()));
            }
            if (lureLocs.size()>0){
                lure=true;
            }else{
                lure=false;
            }
            boolean center;
            locs.clear();

            checkWithinDistanceForSomething(boardData,otherPlayer*10+1,1+level*2);
            ArrayList <Locations> centerLocs=new ArrayList<>();
            for (Locations x: locs){
                centerLocs.add(new Locations(x.getX(),x.getY()));
            }
            if (centerLocs.size()>0){
                center=true;
            }else{
                center=false;
            }

            boolean tree;
            locs.clear();
            checkWithinDistanceForSomething(boardData,4,1+level*2);
            ArrayList<Locations> treeLocs=new ArrayList<>();
            for (Locations x: locs){
                treeLocs.add(new Locations(x.getX(),x.getY()));
            }
            if (treeLocs.size()>0){
                tree=true;
            }else{
                tree=false;
            }
            if (center){
                follow(getClosest(centerLocs),boardData);
            }else if (lure){
                follow(getClosest(lureLocs),boardData);
            }else if (chase){
                follow(getClosest(farmerLocs,soliderLocs),boardData,farmers,soldiers,battles);
            }else if (tree){
                follow(getClosest(treeLocs),boardData);
            }else{
                locs.clear();
                checkWithinDistanceForSomething(boardData, 0, 1);
                checkWithinDistanceForSomething(boardData, playerOwned*10, 1);
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
    public Locations getClosest(ArrayList<Locations> farmerLocs,ArrayList<Locations> soliderLocs) {
        int SoldiershortestDistance = Integer.MAX_VALUE;
        int FarmershortestDistance = Integer.MAX_VALUE;
        int SoldiershortestLoc = 0;
        int FarmershortestLoc=0;
        for (int i = 0; i < soliderLocs.size(); i++) {
            int tempDistance = Math.max(Math.abs(xCord - soliderLocs.get(i).getX()), Math.abs(yCord - soliderLocs.get(i).getY()));
            if (tempDistance < SoldiershortestDistance) {
                SoldiershortestDistance=tempDistance;
                SoldiershortestLoc = i;
            }
        }
        for (int i = 0; i < farmerLocs.size(); i++) {
            int tempDistance = Math.max(Math.abs(xCord - farmerLocs.get(i).getX()), Math.abs(yCord - farmerLocs.get(i).getY()));
            if (tempDistance < FarmershortestDistance) {
                FarmershortestDistance=tempDistance;
                FarmershortestLoc = i;
            }
        }
        if (FarmershortestDistance<=SoldiershortestDistance){
            followSolider=false;
            return farmerLocs.get(FarmershortestLoc);
        }else{
            followSolider=true;
            return soliderLocs.get(SoldiershortestLoc);
        }
    }
    public Locations getClosest(ArrayList<Locations> locations) {
        int shortestDistance = Integer.MAX_VALUE;
        int shortestLoc=0;
        for (int i = 0; i < locations.size(); i++) {
            int tempDistance = Math.max(Math.abs(xCord - locations.get(i).getX()), Math.abs(yCord - locations.get(i).getY()));
            if (tempDistance < shortestDistance) {
                shortestDistance=tempDistance;
                shortestLoc = i;
            }
        }
        return locations.get(shortestLoc);
    }
    public ArrayList<Locations> getLocsOfOpposingSoldier(ArrayList<Soldier> soldiers, int distance){
        ArrayList<Locations> temp = new ArrayList<>();
        for (int i = 0; i < soldiers.size(); i++) {
            if(soldiers.get(i).getPlayerOwned() !=playerOwned){
                if(xCord-distance <= soldiers.get(i).getxCord() && xCord+distance>= soldiers.get(i).getxCord() && yCord-distance <= soldiers.get(i).getyCord() && yCord+distance >= soldiers.get(i).getyCord()){
                    temp.add(new Locations(soldiers.get(i).getxCord(),soldiers.get(i).getyCord()));
                }
            }
        }
        return temp;
    }
    public ArrayList<Locations> getLocsOfOpposingFarmer(ArrayList<Farmer> farmers, int distance){
        ArrayList<Locations> temp = new ArrayList<>();
        for (int i = 0; i < farmers.size(); i++) {
            if(farmers.get(i).getPlayerOwned() !=playerOwned){
                if(xCord-distance >= farmers.get(i).getxCord() && xCord+distance< farmers.get(i).getxCord() && yCord-distance >= farmers.get(i).getyCord() && yCord+distance <= farmers.get(i).getyCord()){
                    temp.add(new Locations(farmers.get(i).getxCord(),farmers.get(i).getyCord()));
                }

            }
        }
        return temp;
    }
    public int getyCord() {
        return yCord;
    }

    public int getxCord() {
        return xCord;
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

    public int getBoardnum() {
        return boardnum;
    }

    public void resetStartTime() {
        this.startTime = System.nanoTime();
    }

    public long getStartTime() {
        return startTime;
    }
    public void follow(Locations follow, int[][] boardData,ArrayList<Farmer> farmers,ArrayList<Soldier>soldiers,ArrayList<Battle>battles) {
        int xprev = xCord;
        int yprev = yCord;
        int xNew = xCord;
        int yNew = yCord;
        if (xCord > follow.getX()) {
            xNew--;
        } else if (xCord < follow.getX()) {
            xNew++;
        }
        if (yCord > follow.getY()) {
            yNew--;
        } else if (yCord < follow.getY()) {
            yNew++;
        }
        if (boardData[xNew][yNew] == 0 || boardData[xNew][yNew] == 10 || boardData[xNew][yNew] == 20) {
            xCord = xNew;
            yCord = yNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }else if (boardData[xNew][yCord]==0||boardData[xNew][yCord]==10||boardData[xNew][yCord]==20){
            xCord = xNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }else if (boardData[xCord][yNew]==0||boardData[xCord][yNew]==10||boardData[xCord][yNew]==20){
            yCord = yNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }
        if (followSolider) {
            locs.clear();
            checkWithinDistanceForSomething(boardData, soldiers.get(0).getBoardnum(), 1);
            if (locs.size() > 0) {
                int rnum = (int) (Math.random() * locs.size());
                int which;
                System.out.println("h");
                for (int i = 0; i < soldiers.size(); i++) {
                    if (soldiers.get(i).getxCord() == locs.get(rnum).getX() && soldiers.get(i).getyCord() == locs.get(rnum).getY() && !soldiers.get(i).isBattle() && !battle) {
                        which = i;
                        battle = true;
                        System.out.println("hello");
                        for (Soldier h:soldiers){
                            if (h.getxCord()==xCord&& h.getyCord()==yCord){
                                battles.add(new Battle(h,soldiers.get(which)));
                            }
                        }
//                            battleSolider = soldiers.get(which);
//                            battleFarmer = null;
                        soldiers.get(which).setMoving(false);
                        moving = false;
                    }
                }
            }
        } else {
            locs.clear();
            checkWithinDistanceForSomething(boardData, farmers.get(0).getBoardnum(), 1);
            if (locs.size() > 0) {
                int rnum = (int) (Math.random() * locs.size());
                int which;
                for (int i = 0; i < farmers.size(); i++) {
                    if (farmers.get(i).getxCord() == locs.get(rnum).getX() && farmers.get(i).getyCord() == locs.get(rnum).getY()) {
                        which = i;
                        battle = true;
                        for (Soldier h:soldiers){
                            if (h.getxCord()==xCord&& h.getyCord()==yCord){
                                battles.add(new Battle(h,farmers.get(which)));
                            }
                        }
//                            battleFarmer = farmers.get(which);
//                            battleSolider = null;
                        farmers.get(which).setMoving(false);
                        moving = false;
                    }
                }
            }
        }
    }
    public void follow(Locations follow, int[][] boardData) {
        int xprev = xCord;
        int yprev = yCord;
        int xNew = xCord;
        int yNew = yCord;
        if (xCord > follow.getX()) {
            xNew--;
        } else if (xCord < follow.getX()) {
            xNew++;
        }
        if (yCord > follow.getY()) {
            yNew--;
        } else if (yCord < follow.getY()) {
            yNew++;
        }
        if (boardData[xNew][yNew] == 0 || boardData[xNew][yNew] == 10 || boardData[xNew][yNew] == 20) {
            xCord = xNew;
            yCord = yNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }else if (boardData[xNew][yCord]==0||boardData[xNew][yCord]==10||boardData[xNew][yCord]==20){
            xCord = xNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }else if (boardData[xCord][yNew]==0||boardData[xCord][yNew]==10||boardData[xCord][yNew]==20){
            yCord = yNew;
            boardData[xprev][yprev] = 0;
            boardData[xCord][yCord] = boardnum;
        }
    }


    public void setMoving(boolean moving) {
        this.moving = moving;
    }
//    public void Battle(ArrayList<Farmer>farmers,ArrayList<Soldier>soldiers,int[][] boardData){
//        if (battle){
//            if (battleSolider!=null){
//                int damage = (int)(Math.random()*(level*5));
//                battleSolider.decreaseHitPoints(damage);
//                int damage2=(int)(Math.random()* (battleSolider.getLevel()*5));
//                hitPoints-=damage2;
//                if (battleSolider.getHitPoints()<0){
//                    boardData[battleSolider.getxCord()][battleSolider.getyCord()]=0;
//                    soldiers.remove(battleSolider);
//                    kills++;
//                    moving=true;
//                    battle=false;
//                    battleSolider=null;
//
//                }
//            }
//        }
//    }
//
//    public void setBattleSolider(Soldier battleSolider) {
//        this.battleSolider = battleSolider;
//    }
//
//    public Soldier getBattleSolider() {
//        return battleSolider;
//    }
    public void incKills(){
        kills++;
    }
    public void setBattle(boolean battle) {
        this.battle = battle;
    }

//    public Farmer getBattleFarmer() {
//        return battleFarmer;
//    }

    public boolean isBattle() {
        return battle;
    }

    public void decreaseHitPoints(int amt){
        hitPoints-=amt;
    }

    public int getHitPoints() {
        return hitPoints;
    }

}
