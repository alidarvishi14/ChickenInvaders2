package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.MoosInCheckable;
import MVC.Model.User;

import java.util.ArrayList;

public class TextfieldObject extends GameObject implements MoosInCheckable {

    private boolean isMouseIn;
    private boolean isSelected;
    private User user;

    public TextfieldObject(int x, int y, int width, int height , User user)  {
        super(x, y, width, height, user.getUsername() , "textfield");
        this.user=user;
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

    public boolean isSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String getText(){
        return user.getUsername();
    }

    public void updateText(char keycode){
        if(('a'<=keycode && keycode<='z')||('A'<=keycode && keycode<='Z')||('0'<=keycode && keycode<='9')||keycode==32){
            if(getText().equals("blank")){
                user.setUsername("");
            }
            if(getText().length()<15)
                user.setUsername(user.getUsername()+keycode);
        }else if(keycode==8 && user.getUsername().length()>0){
            user.setUsername(user.getUsername().substring(0,user.getUsername().length()-1));
        }
    }

    public User getUser() {
        return user;
    }
}
