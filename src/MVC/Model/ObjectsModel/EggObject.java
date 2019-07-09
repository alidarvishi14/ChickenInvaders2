package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.Interfaces.HitCheckable;

public class EggObject extends GameObject implements HitCheckable , Clockable {
    private int clock;
    private double vX;
    private double vY;
    private double doubleX;
    private double doubleY;
    public EggObject(int[]Location,double v) {
        this(Location , v ,0);
    }
    public EggObject(int[]Location,double v,double angle) {
        super( Location[0], Location[1], BufferedFiles.get("egg").getWidth(), BufferedFiles.get("egg").getHeight(), "egg" , "egg");
        clock =0;
        doubleX=Location[0];
        doubleY=Location[1];
        vX=v*Math.sin(angle);
        vY=v*Math.cos(angle);
    }
//    public int[] getNowLocation(){
////        int X = getX();
//        int Y = -3*clock / 10 + getY();
//        return new int[]{getX(),getY()};
//    }

    public void stepClock(){
        clock++;
        if(clock%8==0)
            move();
    }

    private void move(){
        doubleY+=vY;
        doubleX+=vX;
    }

    @Override
    public int getX() {
        return (int) doubleX;
    }

    @Override
    public int getY() {
        return (int) doubleY;
    }

    @Override
    public boolean dispose() {
        return getY()> Constants.frameHeight;
    }

    @Override
    public boolean checkHit(GameObject object) {
        return false;
    }
}
