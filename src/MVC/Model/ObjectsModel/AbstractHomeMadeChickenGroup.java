package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.Clockable;

import java.util.ArrayList;

public abstract class AbstractHomeMadeChickenGroup extends GameObject implements Clockable {
    public AbstractHomeMadeChickenGroup (int x, int y, int width, int height, String text, String paintMethod) {
        super(x, y, width, height, text, paintMethod);
    }

    public abstract int[] getChickenLocation(int i);
    public abstract ArrayList<ChickenObject> getChickenObjects();
    public abstract int getChickenObjectsize();
    public abstract void remove(ChickenObject chicken);
    public abstract int getGroupType();
    public abstract int getChickenType();
    public abstract boolean Alarm();
    public abstract int getFrame();
}
