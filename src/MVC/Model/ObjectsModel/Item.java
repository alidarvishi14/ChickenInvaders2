package MVC.Model.ObjectsModel;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.Interfaces.HitCheckable;

public abstract class Item extends GameObject implements HitCheckable , Clockable {
    private int clock;
    public Item(int[]Location, String method) {
        super( Location[0], Location[1], BufferedFiles.get("powerup").getWidth(), BufferedFiles.get("powerup").getHeight(), "powerUp" , method);
        clock =0;
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
        setY(getY()+1);
        setX(getX());
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
