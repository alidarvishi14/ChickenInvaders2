package MVC.Model.ObjectsModel;

import MVC.Model.Interfaces.MoosInCheckable;

import java.util.ArrayList;

public class NumberFieldObject extends GameObject implements MoosInCheckable {

    private boolean isMouseIn;
    private boolean isSelected;
    private String defualtText;
    private boolean editable;
    private int maxChar;

    public NumberFieldObject(int x, int y, int width, int height,String text,boolean editable,int maxChar)  {
        super(x, y, width, height, text , "numberField");
        this.editable=editable;
        this.maxChar=maxChar;
        defualtText=text;
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
        if(editable) {
            if (!selected && isSelected && getText().length() == 0) {
                setText(defualtText);
            }
            isSelected = selected;
        }
    }

//    @Override
//    public String getText(){
//        return user.getUsername();
//    }

    public void updateText(char keycode){
        if(editable) {
            if (('0' <= keycode && keycode <= '9') || keycode == 32) {
                if (getText().length() > 0)
                    if (getText().charAt(0) < '0' || getText().charAt(0) > '9')
                        setText("");
                if(getText().length()<maxChar)
                    setText(getText() + keycode);
            } else if (keycode == 8 && getText().length() > 0) {
                if (getText().charAt(0) < '0' || getText().charAt(0) > '9')
                    setText("a");
                setText(getText().substring(0, getText().length() - 1));
            }
        }
    }

    public String getDefualtText() {
        return defualtText;
    }
}
