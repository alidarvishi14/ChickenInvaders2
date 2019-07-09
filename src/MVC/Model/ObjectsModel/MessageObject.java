package MVC.Model.ObjectsModel;

import Commens.Constants;
import MVC.Model.Interfaces.Clockable;

import java.awt.*;

public class MessageObject extends GameObject implements Clockable {
    private int clock;

    private Color color;
    public MessageObject(String txt,Color color){
        super(Constants.frameWidth/2,Constants.frameHeight,0,0,txt,"message");
        this.color=color;
    }
//    public int[] getNowLocation(){
////        int X = getX();
//        int Y = -3*clock / 10 + getY();
//        return new int[]{getX(),getY()};
//    }

    public void stepClock(){
        clock++;
        if(clock%8==0 && getY()> Constants.frameHeight/2)
            move();
    }

    @Override
    public boolean dispose() {
        return clock==5000;
    }

    private void move(){
        setX(getX());
        setY(getY()-1);
    }

    public Color getColor() {
        return color;
    }
}
