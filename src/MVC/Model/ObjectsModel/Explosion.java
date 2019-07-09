package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.Clockable;

public class Explosion extends GameObject implements Clockable {
    private int clock;
    public Explosion(int[] location) {
        super(location[0],location[1], 0, 0, "exp", "exp");
    }

    @Override
    public void stepClock() {
        clock++;
    }

    @Override
    public boolean dispose() {
        return getFrame()==59;
    }

    public int getFrame(){
        return clock/20;
    }
}
