package MVC.Model.ObjectsModel;

import java.awt.*;

public class JustABoxObject extends GameObject{
    private Color color;

    public JustABoxObject(int x, int y, int width, int height, Color color,String text) {
        super(x, y, width, height, text, "justABox");
        this.color=color;
    }
    public Color getColor(){
        return color;
    }
}
