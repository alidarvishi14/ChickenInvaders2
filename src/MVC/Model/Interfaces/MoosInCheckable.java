package MVC.Model.Interfaces;

import MVC.Model.ObjectsModel.CursorObject;

import java.util.ArrayList;

public interface MoosInCheckable {
    public void setMouseIn(ArrayList<CursorObject> cursorObjects);
    public boolean isMouseIn();
}
