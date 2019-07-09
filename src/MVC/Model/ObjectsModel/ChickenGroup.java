package MVC.Model.ObjectsModel;

import Commens.Constants;
import MVC.Model.Interfaces.Clockable;

import java.io.Serializable;
import java.util.ArrayList;

public class ChickenGroup extends GameObject implements Clockable , Serializable {
    private int clock;
    private int groupType;
    private int chickenType;
    private int groupSpeedX =1;
    private int groupSpeedY =1;
    private int number;
    private int n;
    private int m;
//    private int x;
//    private int y;

    private final ArrayList<ChickenObject> chickenObjects = new ArrayList<>();
    private final ArrayList<int[]> locs=new ArrayList<>();
    public ChickenGroup(int number, int groupType,int wave,int chickenType,int m) {
        super(100,100,0,0,"","chickenGroup");
        clock =0;
        this.m=m;
        setText(wave!=-1?"Wave "+wave:"final Wave");
        this.groupType =groupType;
        this.chickenType=chickenType;
        this.number=number;
        for (int i = 0; i <number ; i++) {
            locs.add(new int[]{(int) (Constants.frameWidth*(0.1+0.8*Math.random())), (int) (Constants.frameHeight*(0.05+0.6*Math.random()))});
        }
        n=7;
    }
    private int[] getNowLocation(){
        return new int[]{getX(), getY()};
    }

    private void updateLocations (int i){
        int X=0;
        int Y=0;
        if(groupType ==1) {
            X = 60*(i%n)+getNowLocation()[0];
            Y = 60*(i/n)+getNowLocation()[1];
            if(X<20){
                groupSpeedX =1;
            }else if(X > Constants.frameWidth-20)
                groupSpeedX =-1;
        }
        if(groupType ==2){
            int r=Constants.frameWidth/16*(1+i/n);
            double angle=2*Math.PI*((2*((i/n)%2)-1)*clock/8000.0+(i%n)/(n+0.0));
            X = (int) (r*Math.cos(angle)+getNowLocation()[0]);
            Y = (int) (r*Math.sin(angle)+getNowLocation()[1]);
            if(X<20){
                groupSpeedX = (int) (3*Math.random())+1;
            }else if(X > Constants.frameWidth-20)
                groupSpeedX = -(int) (3*Math.random())-1;
            if(Y<10){
                groupSpeedY = (int) (2*Math.random())+1;
            }else if(Y > 3*Constants.frameHeight/4)
                groupSpeedY = -(int) (2*Math.random())-1;
        }
        if(groupType ==3){
            int r=Constants.frameWidth/16*(3+i/n);
            double angle=2*Math.PI*((2*((i/n)%2)-1)*clock/8000.0+(i%n)/(n+0.0));
            X = (int) (r*Math.cos(angle)+getNowLocation()[0]);
            Y = (int) (r*Math.sin(angle)+getNowLocation()[1]);

        }
        if(groupType ==4){
            X=locs.get(i)[0];
            Y=locs.get(i)[1];
        }
        if(groupType==5){
            X=locs.get(0)[0];
            Y=locs.get(0)[1];
        }
        chickenObjects.get(i).stepToLocation(new int[]{X, Y});
    }

    public int[] getChickenLocation(int i){
        return chickenObjects.get(i).getLocation();
    }

    public void stepClock(){
        clock++;
        move();
        makeChickens();
        if(clock%4000==0) {
            if(chickenType==2)
                n = (int) (3 + getChickenObjectsize() / 7 + Math.random() * 3);
            if(chickenType==1)
                n = (int) (1 + getChickenObjectsize() / 5 + Math.random() * 3);
            if(chickenType==3)
                n = (int) (2 + getChickenObjectsize() / 4 + Math.random() * 3);
        }
        if(clock%2000==0){
            if(groupType==4)
                for (int i = 0; i <getChickenObjectsize() ; i++) {
                    locs.set(i,new int[]{(int) (Constants.frameWidth*(0.1+0.8*Math.random())), (int) (Constants.frameHeight*(0.05+0.6*Math.random()))});
                }
            if(groupType==5)
                locs.set(0,new int[]{(int) (Constants.frameWidth*(0.48+0.04*Math.random())),(int) (Constants.frameHeight*(0.48+0.04*Math.random()))});
        }
    }

    @Override
    public boolean dispose() {
        return getChickenObjectsize()==0&&number==0;
    }

    public ArrayList<ChickenObject> getChickenObjects(){
        return new ArrayList<>(chickenObjects);
    }

    public int getChickenObjectsize(){
        return chickenObjects.size();
    }

    public void remove(ChickenObject chicken){
        chickenObjects.remove(chicken);
    }

    public int getGroupType() {
        return groupType;
    }

    public int getChickenType() {
        return chickenType;
    }

    public boolean Alarm(){
        return clock<2000;
    }

    private void makeChickens(){
        if (!Alarm() && clock%50==0 && getChickenObjectsize()<number){
            if(groupType!=3&&groupType!=5)
                    chickenObjects.add(new ChickenObject(chickenType,m));
            else if(groupType==3) {
                double angle=2*Math.PI/n*getChickenObjectsize();
                double x=Constants.frameWidth/2.0+Constants.frameWidth*Math.cos(angle);
                double y=Constants.frameHeight /2.0+Constants.frameWidth*Math.sin(angle);
                chickenObjects.add(new ChickenObject(x, y, chickenType,3,m));
            }else {
                chickenObjects.add(new ChickenObject(Constants.frameWidth/2,-Constants.frameHeight/2,5,1,m));
            }
        }
        if(getChickenObjectsize()==number){
            number=0;
        }
    }

    private void move(){
        if(clock%10==0) {
            if(groupType ==1) {
                setX(getX() + groupSpeedX);
            }else if(groupType ==2) {
                setX(getX() + groupSpeedX);
                setY(getY() + groupSpeedY);
            }else{
                setX(Constants.frameWidth/2);
                setY(Constants.frameHeight /2);
            }
        }
        for (int i = 0; i < getChickenObjectsize(); i++) {
            updateLocations(i);
        }
    }

    public int getFrame(){
        if(groupType!=5)
            return clock/40;
        else
            return chickenObjects.get(0).getFrameForBoss();
    }
}
