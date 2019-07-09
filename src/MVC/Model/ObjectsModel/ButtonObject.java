package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.MoosInCheckable;

import java.util.ArrayList;

public class ButtonObject extends GameObject implements MoosInCheckable {
    private int clock=0;
    private boolean isMouseIn;
    private String command;
    public ButtonObject(int x, int y, int width, int height , String command, String text) {
        super(x, y, width, height, text , "button");
        this.command=command;
        isMouseIn=false;
    }

    @Override
    public void setMouseIn(ArrayList<CursorObject> cursorObjects){
        isMouseIn=false;
        for (CursorObject cursor:cursorObjects) {
            isMouseIn=isMouseIn || (getX()<cursor.getX() && getX()+getWidth()>cursor.getX() && getY()< cursor.getY() && getY()+getHeight()>cursor.getY());
        }
    }

    @Override
    public boolean isMouseIn(){
        return isMouseIn;
    }

    public String getCommand(){
        return command;
    }
}
