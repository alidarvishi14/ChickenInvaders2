package MVC.Model.ObjectsModel;

import Commens.Constants;

import java.awt.*;

public class TempBar extends GameObject{
    //private Color color;

    private int value;
    public TempBar() {
        super(0, 0, Constants.frameWidth/4, Constants.frameHeight /15, "box", "tempBar");
        //this.color=Color.RED;
    }
    public Color getColor(){
        if(value<25)
        return Color.green;
        if(value<50)
            return Color.yellow;
        if(value<75)
            return Color.ORANGE;
        else return Color.RED;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getBarWidth(){
        return (int) (getWidth()*(value<100?value:100)/100.0*0.825);
    }

}
