package MVC.Model.ObjectsModel;

import Commens.Constants;

public class bulletGift extends Item {
    private int bulletType;
    public bulletGift(int[]Location, int bulletType) {
        super( Location ,"gift");
        this.bulletType=bulletType;
    }


    public int getBulletType(){
        return bulletType;
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
