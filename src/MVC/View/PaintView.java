package MVC.View;

import Commens.BufferedFiles;
import Commens.Constants;
import Commens.Fonts;
import MVC.Model.ObjectsModel.*;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintView {
    public static void bullet (Graphics g,BulletObject object){
        g.drawImage( BufferedFiles.get("bullet",object.getType()), object.getX()-Constants.bulletWidth/2, object.getY()-Constants.bulletWidth/2 ,null);
    }
    public static void coin (Graphics g,CoinObject object){
        g.drawImage( BufferedFiles.get("coin"), object.getX()-Constants.coinWidth/2, object.getY()-Constants.coinWidth/2 ,null);
    }
    public static void egg (Graphics g,EggObject object){
        g.drawImage( BufferedFiles.get("egg"), object.getX()-Constants.eggWidth/2, object.getY()-Constants.eggWidth/2 ,null);
    }
    public static void gift (Graphics g, bulletGift object){
        g.drawImage( BufferedFiles.get("gift",object.getBulletType()), object.getX()-Constants.giftWidth/2, object.getY()-Constants.giftWidth/2 ,null);
    }
    public static void powerup (Graphics g, Powerup object){
        g.drawImage( BufferedFiles.get("powerup"), object.getX()-Constants.powerUpWidth/2, object.getY()-Constants.powerUpWidth/2 ,null);
    }
    public static void message (Graphics g,MessageObject object){
        g.setFont(Fonts.Rain(50));
        g.setColor(object.getColor());
        g.drawString(object.getText() ,object.getX()-object.getText().length()*g.getFont().getSize()/5,object.getY());
    }
    public static void chickenGroup (Graphics g,ChickenGroup object){
        BufferedImage image;
        Color alarmZoneColor=new Color(15,100, 22, 95);
        Color alarmZoneBorder=Color.green;
        if(object.Alarm()){
            if(object.getGroupType()==3){
                g.setColor(alarmZoneColor);
                g.fillOval(Constants.frameWidth/3,Constants.frameHeight/2-Constants.frameWidth/6,Constants.frameWidth/3,Constants.frameWidth/3);
                g.setColor(alarmZoneBorder);
                g.drawOval(Constants.frameWidth/3,Constants.frameHeight/2-Constants.frameWidth/6,Constants.frameWidth/3,Constants.frameWidth/3);
                g.setColor(Color.black);
            }else if(object.getGroupType()==2){
                g.setColor(alarmZoneColor);
                g.fillRoundRect(Constants.frameWidth/10,Constants.frameHeight*3/4,
                        Constants.frameWidth*8/10,Constants.frameHeight/5,Constants.frameWidth/10,Constants.frameWidth/10);
                g.setColor(alarmZoneBorder);
                g.drawRoundRect(Constants.frameWidth/10,Constants.frameHeight*3/4,
                        Constants.frameWidth*8/10,Constants.frameHeight/5,Constants.frameWidth/10,Constants.frameWidth/10);
                g.setColor(Color.black);
            }
            g.setColor(Color.white);
            g.setFont(Fonts.Rain(50));
            g.drawString(object.getText(),Constants.frameWidth/2-object.getText().length()*g.getFont().getSize()/5,Constants.frameHeight/2);
        }
        if(object.getGroupType()!=5)
            for (int i = 0; i < object.getChickenObjectsize(); i++) {
                image=BufferedFiles.get("chick",3*i+object.getFrame());
                g.drawImage(image , object.getChickenLocation(i)[0] - image.getWidth() / 2, object.getChickenLocation(i)[1] - image.getHeight() / 2, null);
            }
        else{
            for (int i = 0; i < object.getChickenObjectsize(); i++) {
                image=BufferedFiles.get("boss",object.getFrame());
                g.drawImage(image , object.getChickenLocation(i)[0] - image.getWidth() / 2, object.getChickenLocation(i)[1] - image.getHeight() / 2, null);
            }
        }

    }
    public static void rocket (Graphics g,RocketObject object){
        BufferedImage image= BufferedFiles.rotate_Image_By_rad(BufferedFiles.get("rocket"),object.getAngel());

        int[] newLocation= object.getNowLocation();
        g.drawImage( image, newLocation[0]-image.getWidth()/2, newLocation[1]-image.getHeight()/2 ,null);
    }
    public static void background (Graphics g, BackGroundObject object){
        BufferedImage image=BufferedFiles.get(object.isInGame()?"background2":"background1");
        int[] newLocation= object.getNowLocation();
        g.drawImage(image, newLocation[0], newLocation[1],null);
        g.drawImage(image, newLocation[0], newLocation[1]-image.getHeight(),null);
    }
    public static void justABox (Graphics g, JustABoxObject object){
        g.setColor(object.getColor());
        g.fillRect(object.getX(),object.getY(),object.getWidth(),object.getHeight());
        g.setColor(Color.red);
        g.setFont(Fonts.Rain(40));
        g.drawString(object.getText(),object.getWidth()/2+object.getX()-object.getText().length()*g.getFont().getSize()/5,object.getY()+g.getFont().getSize());
        g.setColor(Color.black);
    }
    public static void userPreviewer (Graphics g, UserPreviewer object){
        g.setColor(new Color(3, 39,100));
        int y=object.getY()+object.getHeight()/15*8;
        int delX=object.getWidth()/10;
        g.fillRect(object.getX(),object.getY(),object.getWidth(),object.getHeight());
        g.setColor(Color.cyan);
        g.setFont(Fonts.button_font(20));
        g.drawString(object.getUser().getUsername(),object.getX()+2*delX,y);
        g.drawString(String.valueOf(object.getUser().getWave()),object.getX()+delX*4,y);
        g.drawString(String.valueOf(object.getUser().getScore()),object.getX()+delX*5,y);
        g.drawString(String.valueOf(object.getUser().getTimePlayed()),object.getX()+delX*6,y);
        //g.drawString(object.getText(),object.getX(),object.getY()+object.getHeight()/5*2);
        g.setColor(Color.black);
        g.setFont(Fonts.text_font(20));
    }
    public static void statusBox (Graphics g, StatusBox object){
        g.setColor(Color.BLUE);
        g.fillRect(object.getX(),object.getY(),object.getWidth(),object.getHeight());
        g.setFont(Fonts.Rain(20));
        g.setColor(Color.cyan);
        g.drawString(object.getText(),object.getX()+object.getWidth()/10,object.getY()+object.getHeight()*3/5);
        g.setColor(Color.black);
    }
    public static void tempBar (Graphics g, TempBar object){
        g.setColor(object.getColor());
        g.fillRect((int) (object.getX()+object.getWidth()*0.175),object.getY(),object.getBarWidth(),object.getHeight());
        g.setColor(new Color(0,70, 210));
        g.fillRect(object.getX(),object.getY(), (int) (object.getWidth()*0.175),object.getHeight());
        for (int i = 0; i <17 ; i++) {
            g.fillRect((int) (object.getX()+object.getWidth()*(0.2+i*0.05)),object.getY(), (int) (object.getWidth()*0.025), object.getHeight() );
        }
        g.fillRect(object.getX(),   object.getY()                       ,object.getWidth(),object.getHeight()/10);
        g.fillRect(object.getX(), (int) (object.getHeight()*0.9)+object.getY(),object.getWidth(), (int) (object.getHeight()*0.1));
        g.drawLine(0, object.getHeight() +object.getY(),object.getWidth(), object.getHeight() +object.getY());
        g.drawLine(0, object.getHeight() +object.getY()-1,object.getWidth(), object.getHeight() +object.getY()-1);
//        g.setColor(Color.blue);
//        g.drawRect(object.getX(),object.getY(),object.getWidth(),object.getHeight());
        g.setColor(Color.black);
    }
    public static void exp(Graphics g, Explosion exp){
        BufferedImage image;
        image = BufferedFiles.get("exp", exp.getFrame());
        g.drawImage(image,exp.getX()-image.getWidth()/2, exp.getY()-image.getWidth()/2 ,null);
    }
    public static void cursor (Graphics g, CursorObject object){
        BufferedImage image;
        int moved=object.moved();
        if(!object.isHidden()) {
            if (!object.isFork()) {
                //image = BufferedFiles.get("spaceship");
                image = BufferedFiles.get("spaceship", object.getFrame());
                g.drawImage(image, object.getX() - image.getWidth() / 2, object.getY(), null);
                if (moved != 0) {
                    g.setColor(Color.RED);
                    g.drawOval(object.getX() - image.getWidth(), object.getY() - image.getHeight() / 2, 2 * image.getWidth(), 2 * image.getWidth());
                    g.setColor(Color.black);
                }
                g.setColor(Color.orange);
                g.setFont(Fonts.Rain(10));
                g.drawString(object.getText(),object.getX(),object.getY());
            }
            if (object.isFork()) {
                image = BufferedFiles.get("fork");
                g.drawImage(image, object.getX() - image.getWidth() * 3 / 10, object.getY() - image.getHeight() / 5, null);
            }
        }
//        BufferedImage image;
//        int moved= object.moved();
//        if(!object.exploding()) {
//            image = BufferedFiles.get("spaceship");
//            g.drawImage(image,object.getX()-image.getWidth()/2, object.getY() ,null);
//        }else {
//            image = BufferedFiles.get("exp", object.getFrame());
//            //System.out.println(object.getFrame());
//            g.drawImage(image,object.getX()-image.getWidth()/2, object.getY()-image.getWidth()/2 ,null);
//        }
//
//        if(moved!=0){
//            g.setColor(Color.RED);
//            g.drawOval(object.getX()-image.getWidth(), object.getY()-image.getHeight()/2,2*image.getWidth(),2*image.getHeight());
//            g.setColor(Color.black);
//        }
    }
    public static void button (Graphics g, ButtonObject object){
        g.setFont(Fonts.button_font(16));
        int border=4;
        Color in=new Color(27, 16,250);
        Color out=new Color(30, 153,250);
        if(object.isMouseIn()){
            g.setColor(in);
        }else {
            g.setColor(out);
        }
        g.fillRoundRect(object.getX()-border, object.getY()-border, object.getWidth()+2*border, object.getHeight()+2*border,object.getWidth()/5,object.getWidth()/5);
        if(!object.isMouseIn()){
            g.setColor(in);
        }else {
            g.setColor(out);
        }
        g.fillRoundRect(object.getX(), object.getY(), object.getWidth(), object.getHeight(),object.getWidth()/5,object.getWidth()/5);
        if(object.isMouseIn()){
            g.setColor(in);
        }else {
            g.setColor(out);
        }
        g.drawString(object.getText(),object.getX()+object.getWidth()/2-object.getText().length()*g.getFont().getSize()/4,object.getY()+object.getHeight()/2);
        g.setColor(Color.black);
    }
    public static void textfield (Graphics g, TextfieldObject object){
        Color a=new Color(50,100,255);
        Color b=new Color(0,200,255);
        Color c=new Color(70,50,200);
        if(object.isSelected()){
            g.setColor(a);
        }else if(!object.isMouseIn()){
            g.setColor(b);
        }else {
            g.setColor(c);
        }
        g.fillRoundRect(object.getX(), object.getY(), object.getWidth(), object.getHeight(),object.getWidth()/5,object.getWidth()/5);
        if(object.isSelected()){
            g.setColor(b);
        }else if(!object.isMouseIn()){
            g.setColor(c);
        }else {
            g.setColor(a);
        }
        g.setFont(Fonts.text_font(12));
        g.drawString(object.getText(), (int) (object.getX()+object.getWidth()/2-object.getText().length()*g.getFont().getSize()/3.8),object.getY()+object.getHeight()/2);
    }
    public static void numberField (Graphics g, NumberFieldObject object){
        Color a=new Color(50,100,255);
        Color b=new Color(0,200,255);
        Color c=new Color(21, 30,200);
        if(object.isSelected()){
            g.setColor(a);
        }else if(!object.isMouseIn()){
            g.setColor(b);
        }else {
            g.setColor(c);
        }
        g.fillRoundRect(object.getX(), object.getY(), object.getWidth(), object.getHeight(),object.getWidth()/5,object.getWidth()/5);
        if(object.isSelected()){
            g.setColor(b);
        }else if(!object.isMouseIn()){
            g.setColor(c);
        }else {
            g.setColor(a);
        }
        g.setFont(Fonts.text_font(13));
        g.drawString(object.getText(),object.getX()+object.getWidth()/2-object.getText().length()*g.getFont().getSize()/4,object.getY()+object.getHeight()/2);
    }
}
