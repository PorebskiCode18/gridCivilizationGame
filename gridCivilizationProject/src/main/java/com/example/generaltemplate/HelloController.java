package com.example.generaltemplate;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    public GridPane gpane;
    public Button start;
    public ListView lstBuildings1,lstBuildings2;
    public ListView lstResourcesB;
    public ListView lstBattles;
    public ListView lstResourcesR;
    public ListView lstCharacterB;
    public ListView lstCharacterR;
    public ListView lstBuildingB;
    public ListView lstBuildingR;
    public Button btnRespawnB;
    public Button btnChangeLocB;
    public Button btnUpgradeChrctB;
    public TextField txtChrctNumB;
    public Button btnChangeLocR;
    public TextField txtChrctNumR;
    public Button btnRespawnR;
    public Button btnUpgradeChrctR;
    public Button btnUpgradeBuildingB;
    public Button btnUpgradeBuildingR;
    public Button btnResearchB;
    public Button btnResearchR;
    public Label lblGameOver;
    private FileInputStream backk,TWNcenterR,TWNcenterB,backkR,backkB,fogg,armyyB,armyyR,farmmB,farmmR,farmerrR,farmerrB,knighttR,knighttB,frm1,frm2,frm3,frm4,frm5,walllR,walllRT,walllB,walllBT,rschC,redLl,blueLl,treee;
    private Image back,centerR,centerB,backR,backB,fog,armyB,armyR,farmB,farmR,farmerR,farmerB,knightR,knightB,fr1,fr2,fr3,fr4,fr5,wallR,wallRT,wallB,wallBT,rsc,redL,blueL,tree;
    private Button[][] board;
    private int[][] boardData;
    private ArrayList<Soldier> allSoldiers = new ArrayList<>();
    private ArrayList<Farmer> allFarmers = new ArrayList<>();
    private ArrayList<ResearchCenter> allResearchCenters = new ArrayList<>();
    private ArrayList<TownCenter> allTownCenters = new ArrayList<>();
    private ArrayList<Wall> allWalls = new ArrayList<>();
    private ArrayList<FarmLand> allFarmLand = new ArrayList<>();
    private ArrayList<Housing> allHousing = new ArrayList<>();
    private ArrayList<Lure> allLure = new ArrayList<>();
    private int selectedBuildingTeam;
    private Players playerB= new Players();
    private Players playerR = new Players();
    private ArrayList<Battle> battles = new ArrayList<>();
    private Boolean changeBuildLocB=false;
    private Boolean changeBuildLocR=false;
    private int trees=0;

    @FXML
    private int row,col;
    public void handleStart(ActionEvent actionEvent) {
        start.setVisible(false);
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Button();
                board[i][j].setMinHeight(15);
                board[i][j].setMinWidth(15);
                board[i][j].setMaxHeight(15);
                board[i][j].setMaxWidth(15);

                ImageView dummy = new ImageView(back);
                dummy.setFitHeight(15);
                dummy.setPreserveRatio(true);
                board[i][j].setGraphic(dummy);
                boardData[i][j] = 0;
                gpane.add(board[i][j], j, i);
            }
        }
        gpane.setGridLinesVisible(true);
        EventHandler z =new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                row = GridPane.getRowIndex((Button) event.getSource());
                col = GridPane.getColumnIndex((Button) event.getSource());

                if (selectedBuildingTeam==1){
                    for (Soldier x:allSoldiers){
                        if (row==x.getxCord() && col==x.getyCord() && x.getPlayerOwned()==1){
                            playerB.setSelectedSoldier(x);
                            playerB.setSelectedFarmer(null);
                        }
                    }
                    for (Farmer x: allFarmers){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==1){
                            playerB.setSelectedSoldier(null);
                            playerB.setSelectedFarmer(x);
                        }
                    }
                    for (ResearchCenter x: allResearchCenters){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==1){
                            playerB.setSelectedResearchCenter(x);
                            playerB.setSelectedFarmLand(null);
                            playerB.setSelectedHousing(null);
                            playerB.setSelectedWall(null);
                        }
                    }
                    for (FarmLand x: allFarmLand){
                        if (row==x.getxCord() && col==x.getyCord()){
                            playerB.setSelectedResearchCenter(null);
                            playerB.setSelectedFarmLand(x);
                            playerB.setSelectedHousing(null);
                            playerB.setSelectedWall(null);
                        }
                    }
                    for (Housing x: allHousing){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==1){
                            playerB.setSelectedResearchCenter(null);
                            playerB.setSelectedFarmLand(null);
                            playerB.setSelectedHousing(x);
                            playerB.setSelectedWall(null);
                        }
                    }
                    for (Wall x: allWalls){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==1){
                            playerB.setSelectedResearchCenter(null);
                            playerB.setSelectedFarmLand(null);
                            playerB.setSelectedHousing(null);
                            playerB.setSelectedWall(x);
                        }
                    }
                    if (!changeBuildLocB) {
                        String Sbuilding = lstBuildings1.getSelectionModel().getSelectedItem().toString();
                        if (Sbuilding.equals("Town Center (50 coins 50 wood)") && playerB.getCashAmt()>=50 && playerB.getWoodAmt()>=50) {
                            for (Locations x : playerB.getExploredLocs()) {
                                if (x.getX() == row && x.getY() == col && boardData[row][col] == 0) {
                                    playerB.dcrCashAmt(50);
                                    playerB.dcrWoodAmt(50);
                                    allTownCenters.add(new TownCenter(row, col, boardData, selectedBuildingTeam));
                                }
                            }
                        } else if (Sbuilding.equals("Farm House (5 coins)") && playerB.getCashAmt()>=5 && boardData[row][col] == selectedBuildingTeam * 10) {
                            playerB.dcrCashAmt(5);
                            allHousing.add(new Housing(row, col, selectedBuildingTeam, "Farm House", selectedBuildingTeam * 10 + 2, boardData));
                        } else if (Sbuilding.equals("Army Base (5 coins)") && playerB.getCashAmt()>=5 && boardData[row][col] == selectedBuildingTeam * 10) {
                            playerB.dcrCashAmt(5);
                            allHousing.add(new Housing(row, col, selectedBuildingTeam, "Army House", selectedBuildingTeam * 10 + 3, boardData));
                        } else if (Sbuilding.equals("Wall (5 coins 10 wood)") && playerB.getCashAmt()>=5 && playerB.getWoodAmt()>=10 && boardData[row][col] == selectedBuildingTeam * 10) {
                            allWalls.add(new Wall(row,col,selectedBuildingTeam,boardData,16));
                            playerB.dcrWoodAmt(10);
                            playerB.dcrCashAmt(5);
                        } else if (Sbuilding.equals("Research Center (10 coins 20 wood)") && playerB.getCashAmt()>=10 && playerB.getWoodAmt()>=20 && boardData[row][col] == selectedBuildingTeam * 10) {
                            allResearchCenters.add(new ResearchCenter(row,col,boardData,3,selectedBuildingTeam));
                            playerB.dcrWoodAmt(20);
                            playerB.dcrCashAmt(10);
                        } else if (Sbuilding.equals("Farm Land") && boardData[row][col] == selectedBuildingTeam * 10) {
                            allFarmLand.add(new FarmLand(row, col, boardData, selectedBuildingTeam * 10));
                        }else if (Sbuilding.equals("Lure (5 Coins)") && playerB.getCashAmt()>=5 && boardData[row][col]==0){
                            playerB.dcrCashAmt(5);
                            allLure.add(new Lure(row,col,boardData,18,playerB.getLureResearch()*5));
                        }
                    }else {
                        if (boardData[row][col]==10){
                            if (playerB.getSelectedHousing()!=null){
                                boardData[playerB.getSelectedHousing().getxCord()][playerB.getSelectedHousing().getyCord()]=0;
                                playerB.getSelectedHousing().changeLoc(row,col);
                                boardData[row][col]=playerB.getSelectedHousing().getBoardNum();
                            }else if (playerB.getSelectedResearchCenter()!=null){
                                boardData[playerB.getSelectedResearchCenter().getxCord()][playerB.getSelectedResearchCenter().getyCord()]=0;
                                playerB.getSelectedResearchCenter().changeLoc(row,col);
                                boardData[row][col]=playerB.getSelectedResearchCenter().getBoardNum();
                            }else if (playerB.getSelectedFarmLand()!=null){
                                playerB.getSelectedFarmLand().changeLoc(row,col);
                            }else if (playerB.getSelectedWall()!=null){
                                boardData[playerB.getSelectedWall().getxCord()][playerB.getSelectedWall().getyCord()]=0;
                                playerB.getSelectedWall().changeLoc(row,col);
                                boardData[row][col]=playerB.getSelectedWall().getBoardNum();
                            }
                        }

                    }
                }else if (selectedBuildingTeam==2){
                    for (Soldier x:allSoldiers){
                        if (row==x.getxCord() && col==x.getyCord() && x.getPlayerOwned()==2){
                            playerR.setSelectedSoldier(x);
                            playerR.setSelectedFarmer(null);
                        }
                    }
                    for (Farmer x: allFarmers){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==2){
                            playerR.setSelectedSoldier(null);
                            playerR.setSelectedFarmer(x);
                        }
                    }
                    for (ResearchCenter x: allResearchCenters){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==2){
                            playerR.setSelectedResearchCenter(x);
                            playerR.setSelectedFarmLand(null);
                            playerR.setSelectedHousing(null);
                            playerR.setSelectedWall(null);
                        }
                    }
                    for (FarmLand x: allFarmLand){
                        if (row==x.getxCord() && col==x.getyCord()){
                            playerR.setSelectedResearchCenter(null);
                            playerR.setSelectedFarmLand(x);
                            playerR.setSelectedHousing(null);
                            playerR.setSelectedWall(null);
                        }
                    }
                    for (Housing x: allHousing){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==2){
                            playerR.setSelectedResearchCenter(null);
                            playerR.setSelectedFarmLand(null);
                            playerR.setSelectedHousing(x);
                            playerR.setSelectedWall(null);
                        }
                    }
                    for (Wall x: allWalls){
                        if (row==x.getxCord() && col==x.getyCord()&& x.getPlayerOwned()==2){
                            playerR.setSelectedResearchCenter(null);
                            playerR.setSelectedFarmLand(null);
                            playerR.setSelectedHousing(null);
                            playerR.setSelectedWall(x);
                        }
                    }
                    if (!changeBuildLocB) {
                        String Sbuilding = lstBuildings2.getSelectionModel().getSelectedItem().toString();
                        if (Sbuilding.equals("Town Center (50 coins 50 wood)")&& playerR.getCashAmt()>=50 && playerR.getWoodAmt()>=50) {
                            for (Locations x : playerR.getExploredLocs()) {
                                if (x.getX() == row && x.getY() == col && boardData[row][col] == 0) {
                                    playerR.dcrCashAmt(50);
                                    playerR.dcrWoodAmt(50);
                                    allTownCenters.add(new TownCenter(row, col, boardData, selectedBuildingTeam));
                                }
                            }
                        } else if (Sbuilding.equals("Farm House (5 coins)") && playerR.getCashAmt()>=5 && boardData[row][col] == selectedBuildingTeam * 10) {
                            playerR.dcrCashAmt(5);
                            allHousing.add(new Housing(row, col, selectedBuildingTeam, "Farm House", selectedBuildingTeam * 10 + 2, boardData));
                        } else if (Sbuilding.equals("Army Base (5 coins)") && playerR.getCashAmt()>=5 && boardData[row][col] == selectedBuildingTeam * 10) {//
                            playerR.dcrCashAmt(5);
                            allHousing.add(new Housing(row, col, selectedBuildingTeam, "Army House", selectedBuildingTeam * 10 + 3, boardData));
                        } else if (Sbuilding.equals("Wall (5 coins 10 wood)") && playerR.getWoodAmt()>=10 && playerR.getCashAmt()>=5 && boardData[row][col] == selectedBuildingTeam * 10) {
                            playerB.dcrWoodAmt(10);
                            playerB.dcrCashAmt(5);
                            allWalls.add(new Wall(row,col,selectedBuildingTeam,boardData,26));
                        } else if (Sbuilding.equals("Research Center (10 coins 20 wood)") && playerR.getCashAmt()>=10 && playerR.getWoodAmt()>=20 && boardData[row][col] == selectedBuildingTeam * 10) {
                            allResearchCenters.add(new ResearchCenter(row,col,boardData,3,selectedBuildingTeam));
                            playerR.dcrCashAmt(10);
                            playerR.dcrCashAmt(20);
                        } else if (Sbuilding.equals("Farm Land") && boardData[row][col] == selectedBuildingTeam * 10) {
                            allFarmLand.add(new FarmLand(row, col, boardData, selectedBuildingTeam * 10));

                        }else if (Sbuilding.equals("Lure (5 Coins)") && playerR.getCashAmt()>=5 && boardData[row][col]==0){
                            allLure.add(new Lure(row,col,boardData,28,playerR.getLureResearch()*5));
                            playerR.dcrCashAmt(5);
                        }
                    }else {
                        if (boardData[row][col]==20){
                            if (playerR.getSelectedHousing()!=null){
                                boardData[playerR.getSelectedHousing().getxCord()][playerR.getSelectedHousing().getyCord()]=0;
                                playerR.getSelectedHousing().changeLoc(row,col);
                                boardData[row][col]=playerR.getSelectedHousing().getBoardNum();
                            }else if (playerR.getSelectedResearchCenter()!=null){
                                boardData[playerR.getSelectedResearchCenter().getxCord()][playerR.getSelectedResearchCenter().getyCord()]=0;
                                playerR.getSelectedResearchCenter().changeLoc(row,col);
                                boardData[row][col]=playerR.getSelectedResearchCenter().getBoardNum();
                            }else if (playerR.getSelectedFarmLand()!=null){
                                playerR.getSelectedFarmLand().changeLoc(row,col);
                            }else if (playerR.getSelectedWall()!=null){
                                boardData[playerR.getSelectedWall().getxCord()][playerR.getSelectedWall().getyCord()]=0;
                                playerR.getSelectedWall().changeLoc(row,col);
                                boardData[row][col]=playerR.getSelectedWall().getBoardNum();
                            }
                        }

                    }
                }
                updateGame();
            }
        };
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j].setOnAction(z);
            }
        }
        start();
    }
    public void updateGame(){
        lstCharacterB.getItems().clear();
        lstCharacterR.getItems().clear();
        lstResourcesB.getItems().clear();
        lstResourcesR.getItems().clear();
        lstResourcesB.getItems().add("Wood: " + playerB.getWoodAmt());
        lstResourcesB.getItems().add("Cash: " + playerB.getCashAmt());
        lstResourcesR.getItems().add("Wood: " + playerR.getWoodAmt());
        lstResourcesR.getItems().add("Cash: " + playerR.getCashAmt());
        lstBuildingB.getItems().clear();
        lstBuildingR.getItems().clear();
        if (playerB.getSelectedResearchCenter()!=null){
            btnResearchB.setVisible(true);
            if (playerB.getSelectedResearchCenter().getLevel()<2){
                btnUpgradeBuildingB.setVisible(true);
                btnUpgradeBuildingB.setText("Upgrade Building (3)");
            }else {
                btnUpgradeBuildingB.setVisible(false);
            }
            lstBuildingB.getItems().add("Level: " + playerB.getSelectedResearchCenter().getLevel());
            lstBuildingB.getItems().add("Available Research:");
            if (playerB.getSelectedResearchCenter().getLevel()==1 && playerB.getLureResearch()<1){
                lstBuildingB.getItems().add("Research Lure(5)");
            }else if (playerB.getSelectedResearchCenter().getLevel()==2&& playerB.getLureResearch()<2){
                lstBuildingB.getItems().add("Research Lure 2(5)");
            }
        }else{
            btnResearchB.setVisible(false);
        }
        if (playerB.getSelectedHousing()!=null){
            if (playerB.getSelectedHousing().getLevel()<3){
                btnUpgradeBuildingB.setVisible(true);
                if (playerB.getSelectedHousing().getName().equals("Army House")){
                    btnUpgradeBuildingB.setText("Upgrade Building (" + 5*playerB.getSelectedHousing().getLevel()+ ")");
                }else if (playerB.getSelectedHousing().getName().equals("Farm House")){
                    btnUpgradeBuildingB.setText("Upgrade Building (" + 3*playerB.getSelectedHousing().getLevel()+ ")");
                }
            }else {
                btnUpgradeBuildingB.setVisible(false);
            }
            btnRespawnB.setVisible(true);
            lstBuildingB.getItems().add("Type: " + playerB.getSelectedHousing().getName());
            lstBuildingB.getItems().add("Level: " + playerB.getSelectedHousing().getLevel());
            lstBuildingB.getItems().add("Housed Characters:");
            if (playerB.getSelectedHousing().getName().equals("Army House")){
                if (playerB.getSelectedHousing().getNumSpawned()>playerB.getSelectedHousing().getHousedSoldiers().size()){
                    btnRespawnB.setVisible(true);
                }else{
                    btnRespawnB.setVisible(false);
                }
                if (!playerB.getSelectedHousing().getHousedSoldiers().isEmpty()){
                    btnUpgradeChrctB.setVisible(true);
                    txtChrctNumB.setVisible(true);

                    if (!txtChrctNumB.getText().isEmpty()&& Integer.parseInt(txtChrctNumB.getText())<playerB.getSelectedHousing().getHousedSoldiers().size()) {
                        btnUpgradeChrctB.setText("Upgrade Soldier(" + (3 * playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel()) + ")");
                    }else{
                        btnUpgradeChrctB.setText("Input #");
                    }
                }else{
                    btnUpgradeChrctB.setVisible(false);
                    txtChrctNumB.setVisible(false);
                }
                btnRespawnB.setText("Respawn Soldier (5 Coins)");
                if (!playerB.getSelectedHousing().getHousedSoldiers().isEmpty()) {
                    for (Soldier x : playerB.getSelectedHousing().getHousedSoldiers()) {
                        lstBuildingB.getItems().add(playerB.getSelectedHousing().getHousedSoldiers().indexOf(x) + ")Lvl: " + x.getLevel() + ", HitPoints: " + x.getHitPoints()+"/"+ (x.getLevel()*25));
                    }
                }
                lstBuildingB.getItems().add(playerB.getSelectedHousing().getHousedSoldiers().size()+"/"+playerB.getSelectedHousing().getLevel());
            }else if (playerB.getSelectedHousing().getName().equals("Farm House")){
                if (playerB.getSelectedHousing().getNumSpawned()>playerB.getSelectedHousing().getHousedFarmers().size()){
                    btnRespawnB.setVisible(true);
                }else{
                    btnRespawnB.setVisible(false);
                }
                if (!playerB.getSelectedHousing().getHousedFarmers().isEmpty()){
                    btnUpgradeChrctB.setVisible(true);
                    txtChrctNumB.setVisible(true);

                    if (!txtChrctNumB.getText().isEmpty()&& Integer.parseInt(txtChrctNumB.getText())<playerB.getSelectedHousing().getHousedFarmers().size()) {
                        btnUpgradeChrctB.setText("Upgrade Farmer(" + (3 * playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel()) + ")");
                    }else{
                        btnUpgradeChrctB.setText("Input #");
                    }
                }else{
                    btnUpgradeChrctB.setVisible(false);
                    txtChrctNumB.setVisible(false);
                }
                btnRespawnB.setText("Respawn Farmer (3 Coins)");
                if (!playerB.getSelectedHousing().getHousedFarmers().isEmpty()) {
                    for (Farmer x : playerB.getSelectedHousing().getHousedFarmers()) {
                        lstBuildingB.getItems().add(playerB.getSelectedHousing().getHousedFarmers().indexOf(x) + ")Lvl: "  + x.getLevel() + ", HitPoints: " + x.getHitPoints()+"/"+(x.getLevel()*10));
                    }
                }
                lstBuildingB.getItems().add(playerB.getSelectedHousing().getHousedFarmers().size()+"/"+playerB.getSelectedHousing().getLevel());
            }
        }else{
            btnRespawnB.setVisible(false);
            btnUpgradeChrctB.setVisible(false);
            txtChrctNumB.setVisible(false);
        }
        if (playerB.getSelectedFarmLand()!=null){
            btnUpgradeBuildingB.setVisible(false);
            lstBuildingB.getItems().add("Crop Level: " + playerB.getSelectedFarmLand().getCropLevel());
        }
        if (playerB.getSelectedWall()!=null){
            if (playerB.getSelectedWall().getLevel()==1) {
                btnUpgradeBuildingB.setVisible(true);
                btnUpgradeBuildingB.setText("Build Turret (10)");
            }else {
                btnUpgradeBuildingB.setVisible(false);
            }
            if (playerB.getSelectedWall().getLevel()==2){
                lstBuildingB.getItems().add("Wall: turret installed");
            }else{
                lstBuildingB.getItems().add("Wall: no turret");
            }
        }
        if (playerB.getSelectedSoldier()!=null){
            lstCharacterB.getItems().clear();
            lstCharacterB.getItems().add("Name: " + playerB.getSelectedSoldier().getName());
            lstCharacterB.getItems().add("Level: " + playerB.getSelectedSoldier().getLevel());
            lstCharacterB.getItems().add("Hitpoints: " + playerB.getSelectedSoldier().getHitPoints() + "/"+ playerB.getSelectedSoldier().getLevel()*25);
            lstCharacterB.getItems().add("Kills: " + playerB.getSelectedSoldier().getKills());
        }
        if (playerB.getSelectedFarmer()!=null){
            lstCharacterB.getItems().clear();
            lstCharacterB.getItems().add("Name: " + playerB.getSelectedFarmer().getName());
            lstCharacterB.getItems().add("Level: " + playerB.getSelectedFarmer().getLevel());
            lstCharacterB.getItems().add("Hitpoints: " + playerB.getSelectedFarmer().getHitPoints()+ "/"+ playerB.getSelectedFarmer().getLevel()*10);
            lstCharacterB.getItems().add("Crop Storage: " + playerB.getSelectedFarmer().getCropStorage() + "/"+ playerB.getSelectedFarmer().getLevel()*10);
        }




        if (playerR.getSelectedResearchCenter()!=null){
            btnResearchR.setVisible(true);
            if (playerR.getSelectedResearchCenter().getLevel()<2){
                btnUpgradeBuildingR.setVisible(true);
                btnUpgradeBuildingR.setText("Upgrade Building (3)");
            }else {
                btnUpgradeBuildingR.setVisible(false);
            }
            lstBuildingR.getItems().add("Level: " + playerR.getSelectedResearchCenter().getLevel());
            lstBuildingR.getItems().add("Available Research:");
            if (playerR.getSelectedResearchCenter().getLevel()==1&& playerR.getLureResearch()<1){
                lstBuildingR.getItems().add("Research Lure(5)");
            }else if (playerR.getSelectedResearchCenter().getLevel()==2&& playerR.getLureResearch()<2){
                lstBuildingR.getItems().add("Research Lure 2(5)");
            }
        }else{
            btnResearchR.setVisible(false);
        }
        if (playerR.getSelectedHousing()!=null){
            if (playerR.getSelectedHousing().getLevel()<3){
                btnUpgradeBuildingR.setVisible(true);
                if (playerR.getSelectedHousing().getName().equals("Army House")){
                    btnUpgradeBuildingR.setText("Upgrade Building (" + 5*playerR.getSelectedHousing().getLevel()+ ")");
                }else if (playerR.getSelectedHousing().getName().equals("Farm House")){
                    btnUpgradeBuildingR.setText("Upgrade Building (" + 3*playerR.getSelectedHousing().getLevel()+ ")");
                }
            }else {
                btnUpgradeBuildingR.setVisible(false);
            }
            btnRespawnR.setVisible(true);
            lstBuildingR.getItems().add("Type: " + playerR.getSelectedHousing().getName());
            lstBuildingR.getItems().add("Level: " + playerR.getSelectedHousing().getLevel());
            lstBuildingR.getItems().add("Housed Characters:");
            if (playerR.getSelectedHousing().getName().equals("Army House")){
                if (playerR.getSelectedHousing().getNumSpawned()>playerR.getSelectedHousing().getHousedSoldiers().size()){
                    btnRespawnR.setVisible(true);
                }else{
                    btnRespawnR.setVisible(false);
                }
                if (!playerR.getSelectedHousing().getHousedSoldiers().isEmpty()){
                    btnUpgradeChrctR.setVisible(true);
                    txtChrctNumR.setVisible(true);

                    if (!txtChrctNumR.getText().isEmpty()&& Integer.parseInt(txtChrctNumR.getText())<playerR.getSelectedHousing().getHousedSoldiers().size()) {
                        btnUpgradeChrctR.setText("Upgrade Soldier(" + (3 * playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel()) + ")");
                    }else{
                        btnUpgradeChrctR.setText("Input #");
                    }
                }else{
                    btnUpgradeChrctR.setVisible(false);
                    txtChrctNumR.setVisible(false);
                }
                btnRespawnR.setText("Respawn Soldier (5 Coins)");
                if (!playerR.getSelectedHousing().getHousedSoldiers().isEmpty()) {
                    for (Soldier x : playerR.getSelectedHousing().getHousedSoldiers()) {
                        lstBuildingR.getItems().add(playerR.getSelectedHousing().getHousedSoldiers().indexOf(x) + ")Lvl: " + x.getLevel() + ", HitPoints: " + x.getHitPoints()+"/"+ (x.getLevel()*25));
                    }
                }
                lstBuildingR.getItems().add(playerR.getSelectedHousing().getHousedSoldiers().size()+"/"+playerR.getSelectedHousing().getLevel());
            }else if (playerR.getSelectedHousing().getName().equals("Farm House")){
                if (playerR.getSelectedHousing().getNumSpawned()>playerR.getSelectedHousing().getHousedFarmers().size()){
                    btnRespawnR.setVisible(true);
                }else{
                    btnRespawnR.setVisible(false);
                }
                if (!playerR.getSelectedHousing().getHousedFarmers().isEmpty()){
                    btnUpgradeChrctR.setVisible(true);
                    txtChrctNumR.setVisible(true);
                    if (!txtChrctNumR.getText().isEmpty()&& Integer.parseInt(txtChrctNumR.getText())<playerR.getSelectedHousing().getHousedFarmers().size()) {
                        btnUpgradeChrctR.setText("Upgrade Farmer(" + (2 * playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel()) + ")");
                    }else{
                        btnUpgradeChrctR.setText("Input #");
                    }
                }else{
                    btnUpgradeChrctR.setVisible(false);
                    txtChrctNumR.setVisible(false);
                }
                btnRespawnR.setText("Respawn Farmer (3 Coins)");
                if (!playerR.getSelectedHousing().getHousedFarmers().isEmpty()) {
                    for (Farmer x : playerR.getSelectedHousing().getHousedFarmers()) {
                        lstBuildingR.getItems().add(playerR.getSelectedHousing().getHousedFarmers().indexOf(x) + ")Lvl: "  + x.getLevel() + ", HitPoints: " + x.getHitPoints()+"/"+(x.getLevel()*10));
                    }
                }
                lstBuildingR.getItems().add(playerR.getSelectedHousing().getHousedFarmers().size()+"/"+playerR.getSelectedHousing().getLevel());
            }
        }else{
            btnRespawnR.setVisible(false);
            btnUpgradeChrctR.setVisible(false);
            txtChrctNumR.setVisible(false);
        }
        if (playerR.getSelectedFarmLand()!=null){
            lstBuildingR.getItems().add("Crop Level: " + playerR.getSelectedFarmLand().getCropLevel());
        }else if (playerR.getSelectedWall()!=null){
            if (playerR.getSelectedWall().getLevel()==1) {
                btnUpgradeBuildingR.setVisible(true);
                btnUpgradeBuildingR.setText("Build Turret (10)");
            }else {
                btnUpgradeBuildingR.setVisible(false);
            }




        }
        if (playerR.getSelectedSoldier()!=null){
            lstCharacterR.getItems().clear();
            lstCharacterR.getItems().add("Name: " + playerR.getSelectedSoldier().getName());
            lstCharacterR.getItems().add("Level: " + playerR.getSelectedSoldier().getLevel());
            lstCharacterR.getItems().add("Hitpoints: " + playerR.getSelectedSoldier().getHitPoints()+ "/"+ playerR.getSelectedSoldier().getLevel()*25);
            lstCharacterR.getItems().add("Kills: " + playerR.getSelectedSoldier().getKills());
        }
        if (playerR.getSelectedFarmer()!=null){
            lstCharacterR.getItems().clear();
            lstCharacterR.getItems().add("Name: " + playerR.getSelectedFarmer().getName());
            lstCharacterR.getItems().add("Level: " + playerR.getSelectedFarmer().getLevel());
            lstCharacterR.getItems().add("Hitpoints: " + playerR.getSelectedFarmer().getHitPoints()+ "/"+ playerR.getSelectedFarmer().getLevel()*10);
            lstCharacterR.getItems().add("Crop Storage: " + playerR.getSelectedFarmer().getCropStorage() + "/"+ playerB.getSelectedFarmer().getLevel()*10);
        }

        // 11/21-town center
        // 10/20-area owned
        // 0-grass
        // 12/22-farm house
        // 13/23-army base
        // 14/24-farmers
        // 15/25-knights
        // 16/26-walls
        // 17/27-wall with turrets
        // 18/28-lures
        // 3 - research center
        // 4- tree
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (trees<25){
                    double ranChance=Math.random();
                    if (ranChance<.001){
                        boardData[i][j]=4;
                        trees++;
                    }
                }
                if (boardData[i][j]==16){
                    ImageView dummy = new ImageView(wallB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==26){
                    ImageView dummy = new ImageView(wallR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==17){
                    ImageView dummy = new ImageView(wallBT);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==27){
                    ImageView dummy = new ImageView(wallRT);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==3){
                    ImageView dummy = new ImageView(rsc);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==18){
                    ImageView dummy = new ImageView(blueL);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==28){
                    ImageView dummy = new ImageView(redL);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==4){
                    ImageView dummy = new ImageView(tree);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }
                if (boardData[i][j]==11){
                    ImageView dummy = new ImageView(centerB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                    for (TownCenter n: allTownCenters){
                        if (n.getPlayerOwned()==1&& selectedBuildingTeam==1) {
                            for (int x = n.getxCord()-5;x<=n.getxCord()+5;x++){
                                for (int y=n.getyCord()-5;y<=n.getyCord()+5;y++){
                                    if (x>-1 && y>-1 && x<40 && y<40 && (boardData[x][y]==0) ){
                                        boardData[x][y]=10;
                                        boolean foundLoc=false;
                                        for (Locations g: playerB.getExploredLocs()){
                                            if (x==g.getX() && y==g.getY()){
                                                foundLoc=true;
                                            }
                                        }
                                        if (!foundLoc){
                                            playerB.getExploredLocs().add(new Locations(x,y));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (boardData[i][j]==21){
                    ImageView dummy = new ImageView(centerR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                    for (TownCenter n: allTownCenters){
                        if (n.getPlayerOwned()==2&& selectedBuildingTeam==2) {
                            for (int x = n.getxCord()-5;x<=n.getxCord()+5;x++){
                                for (int y=n.getyCord()-5;y<=n.getyCord()+5;y++){
                                    if (x>-1 && y>-1 && x<40 && y<40 && (boardData[x][y]==0)){
                                        boardData[x][y]=20;
                                        boolean foundLoc=false;
                                        for (Locations g: playerR.getExploredLocs()){
                                            if (x==g.getX() && y==g.getY()){
                                                foundLoc=true;
                                            }
                                        }
                                        if (!foundLoc){
                                            playerR.getExploredLocs().add(new Locations(x,y));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else if (boardData[i][j]==10){
                    ImageView dummy = new ImageView(backB);
                    for (FarmLand x:allFarmLand){
                        if (x.getxCord()==i && x.getyCord()==j){
                            if (x.getCropLevel()==0){
                                dummy = new ImageView(fr1);
                            }else if (x.getCropLevel()==1){
                                dummy = new ImageView(fr2);
                            }else if (x.getCropLevel()==2){
                                dummy = new ImageView(fr3);
                            }else if (x.getCropLevel()==3){
                                dummy = new ImageView(fr4);
                            }else if (x.getCropLevel()==4){
                                dummy = new ImageView(fr5);
                            }
                        }
                    }
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                    if (selectedBuildingTeam==2){
                        boardData[i][j]=0;
                    }
                }else if (boardData[i][j]==20){
                    ImageView dummy = new ImageView(backR);

                    for (FarmLand x:allFarmLand){
                        if (x.getxCord()==i && x.getyCord()==j){
                            if (x.getCropLevel()==0){
                                dummy = new ImageView(fr1);
                            }else if (x.getCropLevel()==1){
                                dummy = new ImageView(fr2);
                            }else if (x.getCropLevel()==2){
                                dummy = new ImageView(fr3);
                            }else if (x.getCropLevel()==3){
                                dummy = new ImageView(fr4);
                            }else if (x.getCropLevel()==4){
                                dummy = new ImageView(fr5);
                            }
                        }
                    }
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                    if (selectedBuildingTeam==1){
                        boardData[i][j]=0;
                    }
                }else if (boardData[i][j]==0){
                    ImageView dummy = new ImageView(back);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                } else if (boardData[i][j]==12){
                    ImageView dummy = new ImageView(farmB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==22){
                    ImageView dummy = new ImageView(farmR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==13){
                    ImageView dummy = new ImageView(armyB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==23){
                    ImageView dummy = new ImageView(armyR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==14){
                    ImageView dummy = new ImageView(farmerB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==24){
                    ImageView dummy = new ImageView(farmerR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==15){
                    ImageView dummy = new ImageView(knightB);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }else if (boardData[i][j]==25){
                    ImageView dummy = new ImageView(knightR);
                    dummy.setFitHeight(15);
                    dummy.setPreserveRatio(true);
                    board[i][j].setGraphic(dummy);
                }
            }
        }
        for (int i=0;i<allSoldiers.size();i++){
            for (int r = allSoldiers.get(i).getxCord() - allSoldiers.get(i).getLevel(); r <= allSoldiers.get(i).getxCord() + allSoldiers.get(i).getLevel(); r++) {
                for (int c = allSoldiers.get(i).getyCord() - allSoldiers.get(i).getLevel(); c <= allSoldiers.get(i).getyCord() + allSoldiers.get(i).getLevel(); c++) {
                    if (r < boardData.length && r >= 0 && c < boardData[r].length && c >= 0) {
                        if (allSoldiers.get(i).getPlayerOwned()==1){
                            boolean foundLoc=false;
                            for (Locations g: playerB.getExploredLocs()){
                                if (r==g.getX() && c==g.getY()){
                                    foundLoc=true;
                                }
                            }
                            if (!foundLoc){
                                playerB.getExploredLocs().add(new Locations(r,c));
                            }
                        }
                        if (allSoldiers.get(i).getPlayerOwned()==2){
                            boolean foundLoc=false;
                            for (Locations g: playerR.getExploredLocs()){
                                if (r==g.getX() && c==g.getY()){
                                    foundLoc=true;
                                }
                            }
                            if (!foundLoc){
                                playerR.getExploredLocs().add(new Locations(r,c));
                            }
                        }
                    }
                }
            }

        }
        for (Wall x: allWalls){
            if (x.getLevel()==2){
                boardData[x.getxCord()][x.getyCord()]=x.getPlayerOwned()*10+7;
            }
        }
        if (selectedBuildingTeam==1){
            for (int i=0;i<boardData.length;i++) {
                for (int j = 0; j < boardData[0].length; j++) {
                    boolean locFound=false;
                    for (int n = 0; n < playerB.getExploredLocs().size(); n++) {
                        if (playerB.getExploredLocs().get(n).getX()==i && playerB.getExploredLocs().get(n).getY()==j){
                            locFound=true;
                        }
                    }
                    if (!locFound){
                        ImageView dummy = new ImageView(fog);
                        dummy.setFitHeight(15);
                        dummy.setPreserveRatio(true);
                        board[i][j].setGraphic(dummy);
                    }
                }
            }

        }
        if (selectedBuildingTeam==2){
            for (int i=0;i<boardData.length;i++) {
                for (int j = 0; j < boardData[0].length; j++) {
                    boolean locFound=false;
                    for (int n = 0; n < playerR.getExploredLocs().size(); n++) {
                        if (playerR.getExploredLocs().get(n).getX()==i && playerR.getExploredLocs().get(n).getY()==j){
                            locFound=true;
                        }
                    }
                    if (!locFound){
                        ImageView dummy = new ImageView(fog);
                        dummy.setFitHeight(15);
                        dummy.setPreserveRatio(true);
                        board[i][j].setGraphic(dummy);
                    }
                }
            }
        }

    }
    public void start(){
        lstBuildings1.getItems().add("Mouse Clicker");
        lstBuildings2.getItems().add("Mouse Clicker");
        lstBuildings1.getItems().add("Town Center (50 coins 50 wood)");
        lstBuildings2.getItems().add("Town Center (50 coins 50 wood)");
        lstBuildings1.getItems().add("Wall (5 coins 10 wood)");
        lstBuildings2.getItems().add("Wall (5 coins 10 wood)");
        lstBuildings1.getItems().add("Farm Land");
        lstBuildings2.getItems().add("Farm Land");
        lstBuildings1.getItems().add("Farm House (5 coins)");
        lstBuildings2.getItems().add("Farm House (5 coins)");
        lstBuildings1.getItems().add("Army Base (5 coins)");
        lstBuildings2.getItems().add("Army Base (5 coins)");
        lstBuildings1.getItems().add("Research Center (10 coins 20 wood)");
        lstBuildings2.getItems().add("Research Center (10 coins 20 wood)");

        allTownCenters.add(new TownCenter(0,0,boardData,1));
        allTownCenters.add(new TownCenter(39,39,boardData,2));
        playerR.getExploredLocs().add(new Locations(39,39));
        playerB.getExploredLocs().add(new Locations(0,0));
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                for (int i=0;i<allLure.size();i++){
                    if (now-allLure.get(i).getStartTime()>1000000000){
                        allLure.get(i).dcrLife();
                        allLure.get(i).resetStartTime();
                        if (allLure.get(i).getNumLifeLeft()<0){
                            boardData[allLure.get(i).getxCord()][allLure.get(i).getyCord()]=0;
                            allLure.remove(i);
                        }
                    }
                }
                for (int i=0;i<allWalls.size();i++) {
                    if (now - allWalls.get(i).getStartShootTime()>2000000000){
                        if (allWalls.get(i).getLevel() == 2) {
                            for (Soldier x: allSoldiers){
                                if (x.getPlayerOwned()!=allWalls.get(i).getPlayerOwned() && x.getxCord()<allWalls.get(i).getxCord()+4 && x.getxCord()>allWalls.get(i).getxCord()-4 && x.getyCord()<allWalls.get(i).getyCord()+4 && x.getyCord()>allWalls.get(i).getyCord()-4){
                                    double ranChance = Math.random();
                                    if (ranChance<.1){
                                        x.decreaseHitPoints((int)(Math.random()*2));
                                    }
                                    allWalls.get(i).incShots();
                                    if (allWalls.get(i).getShots()>100){
                                        allWalls.get(i).dcrLevel();
                                        allWalls.get(i).resetShots();
                                    }

                                }
                            }
                        }
                    }
                }
                for (Housing housing : allHousing) {
                    if (housing.isSpawn()) {
                        if (housing.getName().equals("Farm House")) {
                            if (now -housing.getStartSpawnTime() > housing.getSpawnTime()) {
                                int ranX=housing.getxCord();
                                int ranY=housing.getyCord();
                                while (ranX==housing.getxCord()&&ranY==housing.getyCord()){
                                    ranY=housing.getyCord();
                                    ranX=housing.getxCord();
                                    ranX+=(int)(Math.random()*4-2);
                                    ranY+=(int)(Math.random()*4-2);
                                    if (ranX<0 || ranX>39||ranY<0 || ranY>39){
                                        ranY=housing.getyCord();
                                        ranX=housing.getxCord();
                                    }
                                }
                                if (boardData[ranX][ranY]==0||boardData[ranX][ranY]==housing.getPlayerOwned()*10){
                                    int frmNum=0;
                                    if (housing.getPlayerOwned()==1){
                                        playerB.incFarmersSpawned();
                                        frmNum=playerB.getNumFarmersSpawned();
                                    }else if (housing.getPlayerOwned()==2){
                                        playerR.incFarmersSpawned();
                                        frmNum=playerR.getNumFarmersSpawned();
                                    }
                                    allFarmers.add(new Farmer(ranX, ranY,housing.getPlayerOwned()*10+4, boardData,selectedBuildingTeam,"Farmer " + frmNum,housing));
                                    housing.addHousedFarmer(allFarmers.get(allFarmers.size()-1));
                                    housing.incNumSpawned();
                                }
                                if (housing.getNumSpawned() > housing.getLevel()-1) {
                                    housing.setSpawn(false);
                                } else {
                                    housing.setSpawn(true);
                                }
                            }
                        }else if(housing.getName().equals("Army House")){
                            if (now -housing.getStartSpawnTime() > housing.getSpawnTime()) {
                                int ranX=housing.getxCord();
                                int ranY=housing.getyCord();
                                while (ranX==housing.getxCord()&&ranY==housing.getyCord()){
                                    ranY=housing.getyCord();
                                    ranX=housing.getxCord();
                                    ranX+=(int)(Math.random()*4-2);
                                    ranY+=(int)(Math.random()*4-2);
                                    if (ranX<0 || ranX>39||ranY<0 || ranY>39){
                                        ranY=housing.getyCord();
                                        ranX=housing.getxCord();
                                    }
                                }
                                if (housing.getPlayerOwned()==1){
                                    for (int i=0;i<playerB.getExploredLocs().size();i++){
                                        if (playerB.getExploredLocs().get(i).getX()==ranX && playerB.getExploredLocs().get(i).getY()==ranY && (boardData[ranX][ranY]==0||boardData[ranX][ranY]==10)){
                                            playerB.incSoldiersSpawned();
                                            allSoldiers.add(new Soldier(ranX, ranY, housing.getPlayerOwned() * 10 + 5, boardData, selectedBuildingTeam,"Knight " + playerB.getNumSoldiersSpawned()));
                                            housing.addHousedSoldier(allSoldiers.get(allSoldiers.size()-1));
                                            housing.incNumSpawned();
                                        }
                                    }
                                }else if (housing.getPlayerOwned()==2){
                                    for (int i=0;i<playerR.getExploredLocs().size();i++){
                                        if (playerR.getExploredLocs().get(i).getX()==ranX && playerR.getExploredLocs().get(i).getY()==ranY && (boardData[ranX][ranY]==0||boardData[ranX][ranY]==20)){
                                            playerR.incSoldiersSpawned();
                                            allSoldiers.add(new Soldier(ranX, ranY, housing.getPlayerOwned() * 10 + 5, boardData, selectedBuildingTeam,"Knight " + playerR.getNumSoldiersSpawned()));
                                            housing.addHousedSoldier(allSoldiers.get(allSoldiers.size()-1));
                                            housing.incNumSpawned();
                                        }
                                    }
                                }
                                if (housing.getNumSpawned() > housing.getLevel()-1) {
                                    housing.setSpawn(false);
                                } else {
                                    housing.setSpawn(true);
                                }
                            }
                        }
                    }
                }
                for (int i = 0; i < allFarmers.size(); i++) {
                    if (now - allFarmers.get(i).getStartTime()>500000000){
                        allFarmers.get(i).changeLocation(boardData,allFarmLand,playerB,playerR);
                        allFarmers.get(i).resetStartTime();
                    }

                }
                for (int i = 0; i < allSoldiers.size(); i++) {
                    if (now - allSoldiers.get(i).getStartTime()>500000000){
                        allSoldiers.get(i).changeLocation(boardData,allFarmers,allSoldiers,battles,lblGameOver,playerB,playerR);
                        allSoldiers.get(i).resetStartTime();
                    }
                }
                for (int i=0;i<battles.size();i++){
                    if (now-battles.get(i).getStartTime()>1000000000){
                        System.out.println(battles);
                        battles.get(i).doBattle();
                        battles.get(i).resetStartTime();
                        if (battles.get(i).getBattleSoldier1()==null){
                            battles.remove(battles.get(i));
                        }else{
                            if (battles.get(i).getBattleSoldier2()==null && battles.get(i).getBattleFarmer()==null){
                                battles.remove(battles.get(i));
                            }
                        }
                    }
                }
                lstBattles.getItems().clear();
                for (int i=0;i<battles.size();i++){
                    if (battles.get(i).getBattleSoldier1()!=null){
                        if (battles.get(i).getBattleSoldier2()!=null){
                            lstBattles.getItems().add(battles.get(i).getBattleSoldier1().getName() + " (" + battles.get(i).getBattleSoldier1().getHitPoints() + ") vs " + battles.get(i).getBattleSoldier2().getName() + " (" + battles.get(i).getBattleSoldier2().getHitPoints() + ")");
                        }else if (battles.get(i).getBattleFarmer()!=null){
                            lstBattles.getItems().add(battles.get(i).getBattleSoldier1().getName() + " (" + battles.get(i).getBattleSoldier1().getHitPoints() + ") vs " + battles.get(i).getBattleFarmer().getName() + " (" + battles.get(i).getBattleFarmer().getHitPoints() + ")");
                        }
                    }
                }
                for (int i=0;i<allSoldiers.size();i++){
                    if (allSoldiers.get(i).getHitPoints()<0){
                        boardData[allSoldiers.get(i).getxCord()][allSoldiers.get(i).getyCord()]=0;
                        for (Housing x:allHousing){
                            for (int j=0;j<x.getHousedSoldiers().size();j++){
                                if (x.getHousedSoldiers().get(j)==allSoldiers.get(i)){
                                    x.rmvHousedSoldier(x.getHousedSoldiers().get(j));
                                }
                            }
                        }
                        allSoldiers.remove(i);
                    }
                }
                for (int i=0;i<allFarmers.size();i++){
                    if (allFarmers.get(i).getHitPoints()<0){
                        boardData[allFarmers.get(i).getxCord()][allFarmers.get(i).getyCord()]=0;
                        for (Housing x:allHousing){
                            for (int j=0;j<x.getHousedFarmers().size();j++){
                                if (x.getHousedFarmers().get(j)==allFarmers.get(i)){
                                    x.rmvHousedFarmer(x.getHousedFarmers().get(j));
                                }
                            }
                        }
                        allFarmers.remove(i);
                    }
                }
                for (int i=0;i<allFarmLand.size();i++){
                    if (allFarmLand.get(i).getCropLevel()>0){
                        if (now - allFarmLand.get(i).getStartSpawnTime()>allFarmLand.get(i).getSpawnTime()){
                            double ranChance=Math.random();
                            if (ranChance<.005 && allFarmLand.get(i).getCropLevel()<4){
                                allFarmLand.get(i).incCropLevel();
                            }
                        }
                    }
                }
                updateGame();
            }
        }.start();
    }
    public HelloController(){
        int i=40;
        int j=40;
        board=new Button[i][j];
        boardData= new int[i][j];
        try{
            backk = new FileInputStream("src/main/resources/Pictures/grass.jpg");
            back=new Image(backk);
            TWNcenterR = new FileInputStream("src/main/resources/Pictures/redTownCenter.jpg");
            centerR=new Image(TWNcenterR);
            TWNcenterB = new FileInputStream("src/main/resources/Pictures/blueTownCenter.png");
            centerB=new Image(TWNcenterB);
            backkB = new FileInputStream("src/main/resources/Pictures/blueGrass.png");
            backB=new Image(backkB);
            backkR = new FileInputStream("src/main/resources/Pictures/redGrass.png");
            backR=new Image(backkR);
            fogg = new FileInputStream("src/main/resources/Pictures/fog.jpg");
            fog=new Image(fogg);
            armyyB = new FileInputStream("src/main/resources/Pictures/blueArmyBase.png");
            armyB=new Image(armyyB);
            armyyR = new FileInputStream("src/main/resources/Pictures/redArmyBase.png");
            armyR=new Image(armyyR);
            farmmB = new FileInputStream("src/main/resources/Pictures/blueFarmHouse.png");
            farmB=new Image(farmmB);
            farmmR = new FileInputStream("src/main/resources/Pictures/redFarmHouse.png");
            farmR=new Image(farmmR);
            farmerrR = new FileInputStream("src/main/resources/Pictures/redFarmer.png");
            farmerR=new Image(farmerrR);
            farmerrB = new FileInputStream("src/main/resources/Pictures/blueFarmer.png");
            farmerB=new Image(farmerrB);
            knighttR = new FileInputStream("src/main/resources/Pictures/redKnight.png");
            knightR=new Image(knighttR);
            knighttB = new FileInputStream("src/main/resources/Pictures/blueKnight.png");
            knightB=new Image(knighttB);

            frm1 = new FileInputStream("src/main/resources/Pictures/farmLand1.png");
            fr1=new Image(frm1);
            frm2 = new FileInputStream("src/main/resources/Pictures/farmLand2.png");
            fr2=new Image(frm2);
            frm3 = new FileInputStream("src/main/resources/Pictures/farmLand3.png");
            fr3=new Image(frm3);
            frm4 = new FileInputStream("src/main/resources/Pictures/farmLand4.png");
            fr4=new Image(frm4);
            frm5 = new FileInputStream("src/main/resources/Pictures/farmLand5.png");
            fr5=new Image(frm5);

            walllR = new FileInputStream("src/main/resources/Pictures/redWall.jpg");
            wallR=new Image(walllR);
            walllRT = new FileInputStream("src/main/resources/Pictures/redWallT.png");
            wallRT=new Image(walllRT);
            walllB = new FileInputStream("src/main/resources/Pictures/blueWall.jpg");
            wallB=new Image(walllB);
            walllBT = new FileInputStream("src/main/resources/Pictures/blueWallT.png");
            wallBT=new Image(walllBT);
            rschC = new FileInputStream("src/main/resources/Pictures/researchCenter.png");
            rsc=new Image(rschC);
            redLl = new FileInputStream("src/main/resources/Pictures/redLure.png");
            redL=new Image(redLl);
            blueLl = new FileInputStream("src/main/resources/Pictures/blueLure.png");
            blueL=new Image(blueLl);
            treee = new FileInputStream("src/main/resources/Pictures/tree.png");
            tree=new Image(treee);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void handleBuildingsB(MouseEvent mouseEvent) {
        selectedBuildingTeam=1;
    }

    public void handleBuildingR(MouseEvent mouseEvent) {
        selectedBuildingTeam=2;
    }

    public void handleChangeBuildLocB(ActionEvent actionEvent) {
        if (changeBuildLocB){
            changeBuildLocB=false;
            btnChangeLocB.setTextFill(Color.BLACK);
        }else{
            changeBuildLocB=true;
            btnChangeLocB.setTextFill(Color.RED);
//            allSoldiers.get(0).decreaseHitPoints(26);
        }
    }

    public void handleRespawnB(ActionEvent actionEvent) {
        if (playerB.getSelectedHousing().getName().equals("Army House")) {
            if (playerB.getSelectedHousing().getNumSpawned() > playerB.getSelectedHousing().getHousedSoldiers().size() && playerB.getCashAmt() >= 5) {
                playerB.getSelectedHousing().decreaseNumSpawned();
                playerB.getSelectedHousing().setSpawn(true);
                playerB.dcrCashAmt(5);
            }
        }else if (playerB.getSelectedHousing().getName().equals("Farm House")){
            if (playerB.getSelectedHousing().getNumSpawned() > playerB.getSelectedHousing().getHousedFarmers().size() && playerB.getCashAmt() >= 3) {
                playerB.getSelectedHousing().decreaseNumSpawned();
                playerB.getSelectedHousing().setSpawn(true);
                playerB.dcrCashAmt(3);
            }
        }
    }
    public void handleUpgradeChrctB(ActionEvent actionEvent) {
        if (!txtChrctNumB.getText().isEmpty()&& playerB.getSelectedHousing().getName().equals("Army House") && Integer.parseInt(txtChrctNumB.getText())<playerB.getSelectedHousing().getHousedSoldiers().size()&&playerB.getCashAmt()>=3 * playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel() && playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel()<3) {
            playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).incLevel();
            playerB.dcrCashAmt(3*playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel());
            playerB.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumB.getText())).incHitPoints(25);
        }else if (!txtChrctNumB.getText().isEmpty()&& playerB.getSelectedHousing().getName().equals("Farm House") && Integer.parseInt(txtChrctNumB.getText())<playerB.getSelectedHousing().getHousedFarmers().size()&&playerB.getCashAmt()>=2 * playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel() && playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel()<3) {
            playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).incLevel();
            playerB.dcrCashAmt(2*playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).getLevel());
            playerB.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumB.getText())).incHitPoints(10);
        }
    }

    public void handleUpgradeChrctR(ActionEvent actionEvent) {
        if (!txtChrctNumR.getText().isEmpty()&& playerR.getSelectedHousing().getName().equals("Army House") && Integer.parseInt(txtChrctNumR.getText())<playerR.getSelectedHousing().getHousedSoldiers().size()&&playerR.getCashAmt()>=3 * playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel() && playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel()<3) {
            playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).incLevel();
            playerR.dcrCashAmt(3*playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel());
            playerR.getSelectedHousing().getHousedSoldiers().get(Integer.parseInt(txtChrctNumR.getText())).incHitPoints(25);
        }else if (!txtChrctNumR.getText().isEmpty()&& playerR.getSelectedHousing().getName().equals("Farm House") && Integer.parseInt(txtChrctNumR.getText())<playerR.getSelectedHousing().getHousedFarmers().size()&&playerR.getCashAmt()>=2 * playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel() && playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel()<3) {
            playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).incLevel();
            playerR.dcrCashAmt(2*playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).getLevel());
            playerR.getSelectedHousing().getHousedFarmers().get(Integer.parseInt(txtChrctNumR.getText())).incHitPoints(10);
        }
    }

    public void handleChangeBuildLocR(ActionEvent actionEvent) {
        if (changeBuildLocR){
            changeBuildLocR=false;
            btnChangeLocR.setTextFill(Color.BLACK);
        }else{
            changeBuildLocR=true;
            btnChangeLocR.setTextFill(Color.RED);
        }
    }

    public void handleRespawnR(ActionEvent actionEvent) {
        if (playerR.getSelectedHousing().getName().equals("Army House")) {
            if (playerR.getSelectedHousing().getNumSpawned() > playerR.getSelectedHousing().getHousedSoldiers().size() && playerR.getCashAmt() >= 5) {
                playerR.getSelectedHousing().decreaseNumSpawned();
                playerR.getSelectedHousing().setSpawn(true);
                playerR.dcrCashAmt(5);
            }
        }else if (playerR.getSelectedHousing().getName().equals("Farm House")){
            if (playerR.getSelectedHousing().getNumSpawned() > playerR.getSelectedHousing().getHousedFarmers().size() && playerR.getCashAmt() >= 3) {
                playerR.getSelectedHousing().decreaseNumSpawned();
                playerR.getSelectedHousing().setSpawn(true);
                playerR.dcrCashAmt(3);
            }
        }
    }

    public void handleUpgradeBuildingB(ActionEvent actionEvent) {
        if (playerB.getSelectedHousing()!=null){
            if (playerB.getSelectedHousing().getName().equals("Army House") && playerB.getCashAmt()>=5*playerB.getSelectedHousing().getLevel()){
                playerB.dcrCashAmt(5*playerB.getSelectedHousing().getLevel());
                playerB.getSelectedHousing().incLevel();
                playerB.getSelectedHousing().setSpawn(true);
            }else if (playerB.getSelectedHousing().getName().equals("Farm House") && playerB.getCashAmt()>=3*playerB.getSelectedHousing().getLevel()){
                playerB.dcrCashAmt(3*playerB.getSelectedHousing().getLevel());
                playerB.getSelectedHousing().incLevel();
                playerB.getSelectedHousing().setSpawn(true);
            }
        }else if (playerB.getSelectedWall()!=null){
            if (playerB.getCashAmt()>10){
                playerB.dcrCashAmt(10);
                playerB.getSelectedWall().incLevel();
            }
        }else if (playerB.getSelectedResearchCenter()!=null){
            if (playerB.getCashAmt()>3){
                playerB.dcrCashAmt(3);
                playerB.getSelectedResearchCenter().incLevel();
            }
        }
    }

    public void handleUpgradeBuildingR(ActionEvent actionEvent) {
        if (playerR.getSelectedHousing()!=null){
            if (playerR.getSelectedHousing().getName().equals("Army House") && playerR.getCashAmt()>=5*playerR.getSelectedHousing().getLevel()){
                playerR.dcrCashAmt(5*playerR.getSelectedHousing().getLevel());
                playerR.getSelectedHousing().incLevel();
                playerR.getSelectedHousing().setSpawn(true);
            }else if (playerR.getSelectedHousing().getName().equals("Farm House") && playerR.getCashAmt()>=3*playerR.getSelectedHousing().getLevel()){
                playerR.dcrCashAmt(3*playerR.getSelectedHousing().getLevel());
                playerR.getSelectedHousing().incLevel();
                playerR.getSelectedHousing().setSpawn(true);
            }
        }else if (playerR.getSelectedWall()!=null){
            if (playerR.getCashAmt()>10){
                playerR.dcrCashAmt(10);
                playerR.getSelectedWall().incLevel();
            }
        }else if (playerR.getSelectedResearchCenter()!=null){
            if (playerR.getCashAmt()>3){
                playerR.dcrCashAmt(3);
                playerR.getSelectedResearchCenter().incLevel();
            }
        }
    }

    public void handleResearchB(ActionEvent actionEvent) {
        if (playerB.getCashAmt()>=5 && playerB.getLureResearch()<2){
            playerB.dcrCashAmt(5);
            playerB.incLureResearch();
            if (playerB.getLureResearch()==1){
                lstBuildings1.getItems().add("Lure (5 Coins)");
            }
        }
    }

    public void handleResearchR(ActionEvent actionEvent) {
        if (playerR.getCashAmt()>=5 && playerR.getLureResearch()<2 && playerR.getLureResearch()==playerR.getSelectedResearchCenter().getLevel()-1){
            playerR.dcrCashAmt(5);
            playerR.incLureResearch();
            if (playerR.getLureResearch()==1){
                lstBuildings2.getItems().add("Lure (5 Coins)");
            }
        }
    }
}