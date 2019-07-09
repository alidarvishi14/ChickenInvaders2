package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.Clockable;

import java.util.Date;

public class BackGroundObject extends GameObject implements Clockable {

    private boolean isInGame;
    private int clock;
    public BackGroundObject(boolean isInGame) {
        super(0,0, 0, 0, "background", "background");
        this.isInGame=isInGame;
        clock=0;
    }

    public int[] getNowLocation(){
        int X = getX();
        int Y = (clock / 10) % 1200 + getY();
        int yp= (int) ((new Date().getTime() / 10) %1200);
        return new int[]{X,Y};
    }

    public void stepClock(){
        clock++;
    }

    @Override
    public boolean dispose() {
        return false;
    }

    public void setInGame(boolean inGame) {
        isInGame = inGame;
    }

    public boolean isInGame() {
        return isInGame;
    }

//    public void setRun(boolean run){
//        this.run=run;
//    }
//    public boolean getRun(){
//        return run;
//    }
//    public int spead(){
//        if(run){
//            return 1;
//        }else
//            return 0;
//    }
}
