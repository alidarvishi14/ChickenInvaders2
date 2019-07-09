package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.Interfaces.HitCheckable;

public class BulletObject extends GameObject implements HitCheckable, Clockable {
    private int clock;
    private int type;
    private double angle;
    private double doubleX;
    private double doubleY;
    private double power;
    private int playerId;
    public BulletObject(int[]Location,int type,double angle,double power,int playerId) {
        super( Location[0], Location[1], BufferedFiles.get("bullet",type).getWidth(), BufferedFiles.get("bullet",type).getHeight(), "bullet" , "bullet");
        this.type=type;
        this.playerId=playerId;
        this.angle=angle*Math.PI/180;
        this.power=power;
        doubleX=Location[0];
        doubleY=Location[1];
        clock =0;
    }
//    public int[] getNowLocation(){
////        int X = getX();
//        int Y = -3*clock / 10 + getY();
//        return new int[]{getX(),getY()};
//    }

    public void stepClock(){
        clock++;
        if(clock%3==0)
            move();
    }

    public int getPlayerId(){
        return playerId;
    }

    private void move(){
        doubleX += Math.sin(angle);
        doubleY -= Math.cos(angle);
    }

    @Override
    public boolean dispose() {
        return getY()<-getWidth();
    }

    @Override
    public boolean checkHit(GameObject object) {
        return false;
    }

    @Override
    public int getX(){
        return (int) doubleX;
    }

    @Override
    public int getY() {
        return (int) doubleY;
    }

    public int getType(){
        return type;
    }

    public double getDamage(){
        return power+1;
    }
}
