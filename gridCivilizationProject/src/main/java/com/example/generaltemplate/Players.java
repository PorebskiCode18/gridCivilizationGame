package com.example.generaltemplate;

import java.util.ArrayList;

public class Players {
    private int woodAmt=10;
    private int cashAmt=10;
    private int numSoldiersSpawned;
    private int numFarmersSpawned;
    private Soldier selectedSoldier;
    private Farmer selectedFarmer;
    private int lureResearch=0;
    private ResearchCenter selectedResearchCenter;
    private FarmLand selectedFarmLand;
    private Housing selectedHousing;
    private Wall selectedWall;
    private ArrayList<Locations> exploredLocs= new ArrayList<>();

    public void incSoldiersSpawned(){
        numSoldiersSpawned++;
    }
    public void incFarmersSpawned(){
        numFarmersSpawned++;
    }

    public int getNumSoldiersSpawned() {
        return numSoldiersSpawned;
    }

    public int getNumFarmersSpawned() {
        return numFarmersSpawned;
    }

    public int getCashAmt() {
        return cashAmt;
    }
    public void dcrCashAmt(int amt){
        cashAmt-=amt;
    }
    public void dcrWoodAmt(int amt){woodAmt-=amt;}

    public int getLureResearch() {
        return lureResearch;
    }
    public void incWoodAmt(int amt){
        woodAmt+=amt;
    }
    public void incLureResearch(){
        lureResearch++;
    }
    public int getWoodAmt() {
        return woodAmt;
    }

    public ArrayList<Locations> getExploredLocs() {
        return exploredLocs;
    }
    public void incCashAmt(int amt){
        cashAmt+=amt;
    }

    public Farmer getSelectedFarmer() {
        return selectedFarmer;
    }

    public FarmLand getSelectedFarmLand() {
        return selectedFarmLand;
    }

    public Housing getSelectedHousing() {
        return selectedHousing;
    }

    public ResearchCenter getSelectedResearchCenter() {
        return selectedResearchCenter;
    }

    public Soldier getSelectedSoldier() {
        return selectedSoldier;
    }

    public Wall getSelectedWall() {
        return selectedWall;
    }

    public void setSelectedFarmer(Farmer selectedFarmer) {
        this.selectedFarmer = selectedFarmer;
    }

    public void setSelectedHousing(Housing selectedHousing) {
        this.selectedHousing = selectedHousing;
    }

    public void setSelectedResearchCenter(ResearchCenter selectedResearchCenter) {
        this.selectedResearchCenter = selectedResearchCenter;
    }

    public void setSelectedSoldier(Soldier selectedSoldier) {
        this.selectedSoldier = selectedSoldier;
    }

    public void setSelectedWall(Wall selectedWall) {
        this.selectedWall = selectedWall;
    }

    public void setSelectedFarmLand(FarmLand selectedFarmLand) {
        this.selectedFarmLand = selectedFarmLand;
    }
}
