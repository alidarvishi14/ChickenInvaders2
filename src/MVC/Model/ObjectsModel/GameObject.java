package MVC.Model.ObjectsModel;

import java.io.Serializable;

public abstract class GameObject implements Serializable {
    private int x;
    private int y;
    private int width;
    private int height;
    private String text;
    private String paintMethod;
    //private Date date;
    //private boolean setMouseIn;
//    private int maximumTime;
    public GameObject(int x, int y, int width, int height, String text, String paintMethod){
        this.x=x;
        this.y=y;
        this.width=width;
        this.height=height;
        this.text=text;
        this.paintMethod = paintMethod;
        //date=new Date();
        //setMouseIn=false;
//        maximumTime=10000;
    }

    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x=x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y=y;
    }

    public int getWidth(){
        return width;
    }
    void setWidth(int width){
        this.width=width;
    }

    public int getHeight(){
        return height;
    }

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text=text;
    }

    public String getPaintMethod(){
        return paintMethod;
    }

//    public Date getDate(){
//        return date;
//    }

}
