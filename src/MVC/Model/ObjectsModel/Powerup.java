package MVC.Model.ObjectsModel;

import Commens.Constants;

public class Powerup extends Item {
    public Powerup(int[]Location) {
        super( Location ,"powerup");
    }
//    public int[] getNowLocation(){
////        int X = getX();
//        int Y = -3*clock / 10 + getY();
//        return new int[]{getX(),getY()};
//    }

    @Override
    public boolean dispose() {
        return getY()> Constants.frameHeight;
    }

    @Override
    public boolean checkHit(GameObject object) {
        return false;
    }
}
