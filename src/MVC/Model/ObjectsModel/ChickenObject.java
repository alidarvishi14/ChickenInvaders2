package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.Interfaces.HitCheckable;

import java.io.Serializable;

public class ChickenObject extends GameObject implements HitCheckable , Serializable {
    private int type;
    private int stepType;
    private double doubleX;
    private double doubleY;
    private double life;

    ChickenObject (int type,int n) {
        super(0,0,BufferedFiles.get(type!=5?"chicken":"boss").getWidth(),0,null,null);
        this.type=type;
        life=1.5*type*Math.sqrt(n);
        if(type==5){
            life=250*Math.sqrt(n);
        }
    }

    ChickenObject(double x,double y,int type,int stepType,int n) {
        this(type,n);
        this.stepType=stepType;
        setDoubleX(x);
        setDoubleY(y);
    }
    void stepToLocation(int[] newLocation){
        double ratio = 0.004;
        if(stepType!=3) {
            setDoubleX(getDoubleX() + ratio * (-getDoubleX() + newLocation[0]));
            setDoubleY(getDoubleY() + ratio * (-getDoubleY() + newLocation[1]));
        }else {
            double r1=Math.sqrt((getDoubleX()- Constants.frameWidth/2.0)*(getDoubleX()- Constants.frameWidth/2.0)+(getDoubleY()-Constants.frameHeight/2.0)*(getDoubleY()-Constants.frameHeight/2.0));
            if(r1>Constants.frameWidth*2.8/16){
                setDoubleX(getDoubleX() + ratio * (-getDoubleX() + newLocation[0]));
                setDoubleY(getDoubleY() + ratio * (-getDoubleY() + newLocation[1]));
            }else {

                double r2 = Math.sqrt((newLocation[0] - Constants.frameWidth / 2.0) * (newLocation[0] - Constants.frameWidth / 2.0) + (newLocation[1] - Constants.frameHeight / 2.0) * (newLocation[1] - Constants.frameHeight / 2.0));
                double theta1 = Math.atan2(Constants.frameHeight / 2.0 - getDoubleY(), getDoubleX() - Constants.frameWidth / 2.0);
                double theta2 = Math.atan2(Constants.frameHeight / 2.0 - newLocation[1], newLocation[0] - Constants.frameWidth / 2.0);
                double theta = theta1 + 2 * ratio * (theta2 - theta1);
                double r = r1 + (theta - theta1) * (r2 - r1) / (theta2 - theta1);
                setDoubleX(r * Math.cos(theta) + Constants.frameWidth / 2.0);
                setDoubleY(-r * Math.sin(theta) + Constants.frameHeight / 2.0);
            }
        }
    }

    public double getDoubleX() {
        return doubleX;
    }

    private void setDoubleX(double x) {
        doubleX = x;
    }

    public double getDoubleY() {
        return doubleY;
    }

    private void setDoubleY(double y) {
        doubleY = y;
    }

    public boolean lay(){
        return Math.random()< (type==5?0.0003:0.00003);
    }

    public boolean coin(){
        return Math.random()<0.08*type;
    }

    public boolean gift(){
        return powerUp();
    }

    public boolean powerUp(){
        return Math.random()<0.03*type;
    }

    int[] getLocation(){
        return new int[]{(int) getDoubleX(), (int) getDoubleY()};
    }

    @Override
    public boolean checkHit (GameObject object) {
        double delX=getDoubleX()-object.getX();
        double delY=getDoubleY()-object.getY();
        double r=Math.sqrt(delX*delX+delY*delY);
        return 2 * r < object.getWidth() + getWidth();
    }

    public boolean hitAndIsDead(double damage){
        life-=damage;
        return life<=0;
    }

    public int getX() {
        return (int) getDoubleX();
    }

    public int getY() {
        return (int) getDoubleY();
    }

    public int getType() {
        return type;
    }

    public int getFrameForBoss(){
        return (int) (BufferedFiles.bossFrames*life/251);
    }

}
