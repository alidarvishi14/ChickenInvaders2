package MVC.Model;

import MVC.Model.Interfaces.Clockable;

import java.io.Serializable;

public class User implements Clockable, Serializable {

    private String username;
    private int score;
    private int wave;
    private int rocket;
    private int life;
    private int bulletType;
    private int powerUp;
    private int coin;
    private boolean resume;
    private int waveToResum;

    private int clock;

    public int getBulletType() {
        return bulletType;
    }

    public void setBulletType(int bulletType) {
        this.bulletType = bulletType;
    }

    public int getPowerUp() {
        return powerUp;
    }

    public void setPowerUp(int powerUp) {
        this.powerUp = powerUp;
    }

    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public User(String username){
        this.username=username;
        resetUser();
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWaveToResum() {
        return waveToResum;
    }

    public void setWaveToResum(int wave) {
        this.waveToResum = wave;
    }

    public int getWave() {
        return wave;
    }

    public void setWave(int wave) {
        this.wave = wave;
    }

    public int getRocket() {
        return rocket;
    }

    public void setRocket(int rocket) {
        this.rocket = rocket;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isResume(){
        return resume;
    }

    public void setResume(boolean resume){
        this.resume=resume;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void resetUser(){
        score=0;
        wave=0;
        rocket=13;
        life=5;
        bulletType=0;
        powerUp=0;
        coin=0;
        clock=0;
        resume=false;
        waveToResum=0;
    }

    public User(String username,int score,int wave,int rocket,int life,int bulletType,int powerUp,int coin , boolean resume,int waveToResum,int clock){
        this.username=username;
//        resetUser();
    }

    @Override
    public void stepClock() {
        clock++;
    }


    public int getClock() {
        return clock;
    }

    public String getTimePlayed(){
        int hour=clock/3600000;
        int min=clock/60000-hour*60;
        int second=clock/1000-min*60-hour*3600;
        return hour+" h "+min+" min "+second+" s";
    }

    public void setClock(int clock) {
        this.clock = clock;
    }

    @Override
    public boolean dispose() {
        return false;
    }

    @Override
    public String toString() {
        return getUsername()+getClock();
    }
}
