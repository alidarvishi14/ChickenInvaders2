package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.Interfaces.HitCheckable;

public class CoinObject extends GameObject implements HitCheckable, Clockable {
    private int clock;
    private double doubleX;
    private double doubleY;
    private double vX;
    private double vY;
    private int ttl;
    public CoinObject(int[]Location) {
        super( Location[0], Location[1], BufferedFiles.get("coin").getWidth(), BufferedFiles.get("coin").getHeight(), "coin" , "coin");
        doubleX=Location[0];
        doubleY=Location[1];
        vX=4*(Math.random()-0.5);
        vY=2*(0.2-Math.random());
        ttl=5;
        clock =0;
    }
//    public int[] getNowLocation(){
////        int X = getX();
//        int Y = -3*clock / 10 + getY();
//        return new int[]{getX(),getY()};
//    }

    public void stepClock(){
        clock++;
        if(clock%10==0) {
            hitBorder();
            move();
        }
    }

    private void hitBorder(){
        if(ttl>0){
            if(getX()<0){
                vX=0.9*Math.abs(vX);
                ttl--;
            }
            if(getX()> Constants.frameWidth){
                vX=-0.9*Math.abs(vX);
                ttl--;
            }
            if(getY()> Constants.frameHeight){
                vY=-0.85*Math.abs(vY);
                ttl--;
            }
        }
    }

    private void move(){
        doubleX+=vX;
        doubleY+=vY/3;
        vY+=0.5;
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
}
