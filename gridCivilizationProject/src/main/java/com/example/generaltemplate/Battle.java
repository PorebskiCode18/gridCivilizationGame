package com.example.generaltemplate;

public class Battle {
    private Soldier battleSoldier1;
    private Soldier battleSoldier2;
    private Farmer battleFarmer;
    private long startTime;
    public Battle (Soldier battleSoldier1,Soldier battleSoldier2){
        this.battleSoldier1=battleSoldier1;
        this.battleSoldier2=battleSoldier2;
    }
    public Battle(Soldier battleSoldier1,Farmer battleFarmer){
        this.battleSoldier1=battleSoldier1;
        this.battleFarmer=battleFarmer;
    }
    public void doBattle() {
        if (battleSoldier1 != null){
            if (battleSoldier2 != null) {
                battleSoldier1.decreaseHitPoints((int) (Math.random() * (battleSoldier2.getLevel() * 5)));
                battleSoldier2.decreaseHitPoints((int) (Math.random() * (battleSoldier1.getLevel() * 5)));
                if (battleSoldier1.getHitPoints() <= 0) {
                    battleSoldier2.incKills();
                    battleSoldier2.setMoving(true);
                    battleSoldier2.setBattle(false);
                    battleSoldier1 = null;
                }
                if (battleSoldier2.getHitPoints() <= 0 && getBattleSoldier1()!=null) {
                    battleSoldier1.incKills();
                    battleSoldier1.setMoving(true);
                    battleSoldier1.setBattle(false);
                    battleSoldier2 = null;
                }
            } else if (battleFarmer!=null){
                battleSoldier1.decreaseHitPoints((int) (Math.random() * (battleFarmer.getLevel() * 3)));
                battleFarmer.decreaseHitPoints((int) (Math.random() * (battleSoldier1.getLevel() * 5)));
                if (battleFarmer.getHitPoints() <= 0) {
                    battleSoldier1.incKills();
                    battleFarmer=null;
                }
                if (battleSoldier1.getHitPoints() <= 0) {
                    battleSoldier1=null;
                }
            }
        }
    }

    public Farmer getBattleFarmer() {
        return battleFarmer;
    }

    public Soldier getBattleSoldier1() {
        return battleSoldier1;
    }

    public Soldier getBattleSoldier2() {
        return battleSoldier2;
    }

    public long getStartTime() {
        return startTime;
    }
    public void resetStartTime() {
        this.startTime = System.nanoTime();
    }
}
