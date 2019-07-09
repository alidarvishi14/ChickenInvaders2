package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.Interfaces.Clockable;

public class RocketObject extends GameObject implements Clockable {
    private int clock;
    private double r0;
    private double angel;
    private int playerId;
    public RocketObject(int[]Location,int playerId) {
        super( Location[0], Location[1], 0, 0, "rocket" , "rocket");
        this.playerId=playerId;
        r0=Math.sqrt((Constants.frameWidth/2-getX())*(Constants.frameWidth/2-getX())+(getY()-Constants.frameHeight /2)*(getY()-Constants.frameHeight /2));
        angel=Math.atan2(Constants.frameWidth/2-getX(),getY()-Constants.frameHeight /2);
        clock =0;
        setWidth(BufferedFiles.get("rocket").getWidth());
    }
    public int[] getNowLocation(){
        double r=r0*Math.pow(clock/1000.0,3);
        int X= (int) (r*Math.sin(angel)+getX());
        int Y = (int) (-r*Math.cos(angel)+getY());
        return new int[]{X,Y};
    }

    public int getPlayerId(){
        return playerId;
    }

    public int getR(){
        return (int) Math.sqrt((Constants.frameWidth/2-getNowLocation()[0])*(Constants.frameWidth/2-getX())+(getY()-Constants.frameHeight /2)*(getNowLocation()[1]-Constants.frameHeight /2));
    }

    public void stepClock(){
        clock++;
    }

    @Override
    public boolean dispose() {
        return getR()<getWidth();
    }

    public double getAngel() {
        return angel;
    }
}
