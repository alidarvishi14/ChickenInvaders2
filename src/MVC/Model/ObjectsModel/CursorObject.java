package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.Interfaces.HitCheckable;

public class CursorObject extends GameObject implements HitCheckable , Clockable {
    private boolean hidden;
    private boolean isFork;
    private int clock;
    private int steady=0;
    private int tilt=0;
    private final int maxSteady=10;
    private int frame =0;
    private int react=0;
    private final int maxReact=10;
    private boolean trigger;
    private int temp;
    public boolean overHeat;
    private boolean spectator;
    //private Date date;

    @Override
    public String toString() {
        return "x: "+getX();
    }

    public CursorObject(boolean isFork) {
        super(0, 0, 0, 0, "", "cursor");
        this.isFork=isFork;
        setWidth(BufferedFiles.get("cursor").getWidth());
        clock=0;
        spectator=false;
    }
    public int moved(){
        if(isFork)
            return 0;
        double time = (clock) / 1000.0;
        if (time > 10)
            return 0;
        return (int) (500 * Math.pow(10, -time));
    }

    public void react(){
        if (react==0)
            react=maxReact;
    }

    @Override
    public int getY() {
        return super.getY()+moved()+(maxReact-Math.abs(maxReact-2*react));
    }

    @Override
    public void setX(int x) {
        if(x<getX()) {
            tilt = -1;
            steady=0;
        }else if(x>getX()) {
            tilt = 1;
            steady=0;
        }
        super.setX(x);
    }

    public int[] getLocation(){
        return new int[]{getX(),getY()};
    }

    public void changType(){
        if(!spectator)
            setHidden(false);
        isFork=!isFork;
        setWidth(BufferedFiles.get(isFork?"fork":"spaceship").getWidth());
    }

    public void resetSpaceship(){
        clock=0;
        overHeat=true;
        temp=30;
        //exp=true;
    }

    public void explode(){
        clock=-2000;
    }

    public boolean isFork() {
        return isFork;
    }

    @Override
    public boolean checkHit(GameObject object) {
        if(object.equals(this) || isHidden()){
            return false;
        }
        int delX=getX()-object.getX();
        int delY=getY()-object.getY()+getWidth()/2;
        double r=Math.sqrt(delX*delX+delY*delY);
        return 2*r<object.getWidth()+getWidth();
    }

    @Override
    public void stepClock() {
        clock++;
        if(clock%20==0) {
            tilt();
            if(steady<maxSteady)
                steady++;
            if(steady==maxSteady)
                tilt=0;
        }
        if(clock%15==0)
            if(react>0)
                react--;
    }

    @Override
    public boolean dispose() {
        return false;
    }

    private void tilt(){
        if(tilt==-1&& frame >-BufferedFiles.spaceshipFrames/2){
            frame--;
        }
        if(tilt==1&& frame <BufferedFiles.spaceshipFrames/2){
            frame++;
        }
        if(frame !=0 && steady==maxSteady){
            frame = frame - frame / Math.abs(frame);
        }
    }
    public boolean isHidden(){
        if(isFork)
            return false;
//        System.out.println((hidden && !spectator )+"  : is hidden");
        return hidden || spectator;
    }


    public boolean canShoot(){
        return trigger && !overHeat && !hidden && !spectator ;
    }

    public void setTrigger(boolean trigger){
        this.trigger=trigger;
    }

    public void setTemp(int temp){
        this.temp =temp;
    }

    public int getTemp(){
        return temp;
    }

    public void setHidden(boolean hidden){
        this.hidden=hidden;
    }
    public int getFrame() {
        return frame+BufferedFiles.spaceshipFrames/2;
    }

    public void setSpectator(boolean spectator){
        this.spectator=spectator;
//        System.out.println("spectator is :"+ spectator);
    }
}
