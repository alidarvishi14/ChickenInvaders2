package MVC.Model;

import Commens.Constants;
import MVC.Model.ObjectsModel.AbstractHomeMadeChickenGroup;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.ObjectsModel.*;

import java.awt.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class GameModel implements Serializable {
    //    private final int maxSteady=100;
//    private int steady;
//    private int cursorFrame=0;
//    private int tilt=0;
    //TODO readwrite lock has better performance than synchronized on this
    private final ArrayList<User>                 users = new ArrayList<>();
//    private User user;
    private final ArrayList<User> clientUsers = new ArrayList<>();
    private BackGroundObject backGroundObject=new BackGroundObject(false);
    //    private final ArrayList<BulletObject> bulletObjects = new ArrayList<>();
    private final ArrayList<BulletObject> bulletObjects = new ArrayList<>();
    private final ArrayList<RocketObject> rocketObjects = new ArrayList<>();
    private final ArrayList<ButtonObject> buttonObjects = new ArrayList<>();
    private final ArrayList<EggObject>    eggObjects    = new ArrayList<>();
    private final ArrayList<Item>         items         = new ArrayList<>();
    private final ArrayList<CursorObject> cursorObjects = new ArrayList<>();
    private final ArrayList<Explosion> explosions       = new ArrayList<>();
    private final ArrayList<CoinObject> coinObjects     = new ArrayList<>();
    private final ArrayList<MessageObject> messageObjects = new ArrayList<>();
    private final ArrayList<UserPreviewer> userPreviewers = new ArrayList<>();
    private final TempBar tempBar=new TempBar();
    private final StatusBox statusBox=new StatusBox();
    private final JustABoxObject justABoxObject= new JustABoxObject(Constants.frameWidth/5,Constants.frameHeight/4,Constants.frameWidth*3/5,Constants.frameHeight/2, new Color(106, 38, 255, 139),"pause");
//    private final ArrayList<GameObject> anyOtherObject  = new ArrayList<>();
    private final ArrayList<TextfieldObject> textfieldObjects = new ArrayList<>();
    private final ArrayList<NumberFieldObject> numberFieldObjects = new ArrayList<>();
    //TODO when chicken groups size is big there is a performance problem in program!
    private final ArrayList<ChickenGroup> chickenGroups = new ArrayList<>();
    private final ArrayList<AbstractHomeMadeChickenGroup> homeMadeGroups = new ArrayList<>();
//    private int playerToShow=0;
    public int page=0;
    // 0:choose player // 1:menus // 2: game // 3: pause // 4:   // 5:     // 6: waiting for clients to join
//    private int justWave=0;
//    private int allWaves=0;

    public GameModel(){
    }

    //getters
    public ArrayList<GameObject> getObjects(){
        synchronized (this) {
            ArrayList<GameObject> arrayList = new ArrayList<>();
            arrayList.addAll(cursorObjects);
            arrayList.addAll(buttonObjects);
            arrayList.addAll(messageObjects);
            if(page==2||page==3){
                arrayList.add(tempBar);
                arrayList.add(statusBox);
            }
            if(page==3){
                arrayList.add(justABoxObject);
            }
            arrayList.addAll(userPreviewers);
            arrayList.addAll(rocketObjects);
            arrayList.addAll(explosions);
            arrayList.addAll(chickenGroups);
            arrayList.addAll(homeMadeGroups);
//            if(chickenGroups.size()>justWave)
//                arrayList.add(chickenGroups.get(justWave));
            arrayList.addAll(coinObjects);
            arrayList.addAll(items);
            arrayList.addAll(eggObjects);
            arrayList.addAll(textfieldObjects);
            arrayList.addAll(numberFieldObjects);
            arrayList.addAll(bulletObjects);
            arrayList.add(backGroundObject);
            return arrayList;
        }
    }

    public ArrayList<Clockable> getClockableObjects(){
        synchronized (this) {
            ArrayList<Clockable> arrayList = new ArrayList<>();
            arrayList.addAll(bulletObjects);
            arrayList.addAll(eggObjects);
            arrayList.addAll(rocketObjects);
            arrayList.addAll(explosions);
            arrayList.addAll(coinObjects);
            arrayList.addAll(chickenGroups);
            arrayList.addAll(homeMadeGroups);
//            if(chickenGroups.size()>justWave)
//                arrayList.add(chickenGroups.get(justWave));
            arrayList.addAll(messageObjects);
            arrayList.addAll(items);
            arrayList.add(backGroundObject);
            arrayList.addAll(cursorObjects);
            arrayList.addAll(clientUsers);
            return arrayList;
        }
    }

    public ArrayList<BulletObject> getBulletObjects(){
        synchronized (this) {
            return new ArrayList<>(bulletObjects);
        }
    }

    //active chickenGroup
//    public ArrayList<ChickenGroup> getActiveGroup(){
//        synchronized (this) {
//            ArrayList<ChickenGroup> list = new ArrayList<>();
//            if(chickenGroups.size()>justWave)
//                list.add(chickenGroups.get(justWave));
//            return list;
//        }
//    }

    public ArrayList<ChickenObject> getChickens(){
        synchronized (this) {
            ArrayList<ChickenObject> list=new ArrayList<>();
            for (ChickenGroup group:chickenGroups) {
                list.addAll(group.getChickenObjects());
            }
            for (AbstractHomeMadeChickenGroup group:homeMadeGroups) {
                list.addAll(group.getChickenObjects());
            }
            return list;
        }
    }

    public ArrayList<RocketObject> getRocketObjects(){
        synchronized (this) {
            return new ArrayList<>(rocketObjects);
        }
    }

    public ArrayList<CoinObject> getCoinObjects(){
        synchronized (this) {
            return new ArrayList<>(coinObjects);
        }
    }

    public ArrayList<ButtonObject> getButtonObjects(){
        synchronized (this) {
            return new ArrayList<>(buttonObjects);
        }
    }

    public ArrayList<EggObject> getEggObjects(){
        synchronized (this) {
            return new ArrayList<>(eggObjects);
        }
    }

    public ArrayList<Item> getItems(){
        synchronized (this) {
            return new ArrayList<>(items);
        }
    }

    public ArrayList<CursorObject> getCursorObjects(){
        synchronized (this) {
            return new ArrayList<>(cursorObjects);
        }
    }

//    public ArrayList<GameObject> getAnyOtherObject(){
//        synchronized (this) {
//            return new ArrayList<>(anyOtherObject);
//        }
//    }

    public BackGroundObject getBackGroundObject() {
        synchronized (this) {
            return backGroundObject;
        }
    }

    public ArrayList<TextfieldObject> getTextfieldObjects(){
        synchronized (this) {
            return new ArrayList<>(textfieldObjects);
        }
    }

    public ArrayList<NumberFieldObject> getNumberFieldObjects(){
        synchronized (this) {
            return new ArrayList<>(numberFieldObjects);
        }
    }

    public ArrayList<User> getUsers(){
        synchronized (this) {
            return new ArrayList<>(users);
        }
    }

    public ArrayList<User> getClientUsers(){
        synchronized (this){
            return new ArrayList<>(clientUsers);
        }
    }

    public User getUser(int i){
        synchronized (this) {
            return clientUsers.get(i);
        }
    }

    public User getSelectedUser(){
        synchronized (this) {
            for (TextfieldObject text : textfieldObjects) {
                if (text.isSelected()) {
                    return text.getUser();
                }
            }
            return null;
        }
    }

    public int[] getPlayerLocation(int i){
        synchronized (this) {
            return cursorObjects.get(i).getLocation();
        }
    }

    public CursorObject getPlaeyrCuror(int i){
        synchronized (this){
            return cursorObjects.get(i);
        }
    }

//    public int getJustWave() {
//        return justWave;
//    }

    //removes
    public void remove(BulletObject bulletObject){
        synchronized (this) {
            bulletObjects.remove(bulletObject);
        }
    }

    public void remove(CoinObject coinObject){
        synchronized (this) {
            coinObjects.remove(coinObject);
        }
    }

//    public void remove(UserPreviewer userPreviewer){
//        synchronized (this) {
//            userPreviewers.remove(userPreviewer);
//        }
//    }


    public void remove(Item power){
        synchronized (this) {
            items.remove(power);
        }
    }

    public void remove(bulletGift power){
        synchronized (this) {
            items.remove(power);
        }
    }

    public void remove(EggObject eggObject){
        synchronized (this) {
            eggObjects.remove(eggObject);
        }
    }

    public void remove(ChickenObject chickenObject){
        synchronized (this) {
            for (ChickenGroup group:chickenGroups) {
                group.remove(chickenObject);
            }
            for (AbstractHomeMadeChickenGroup group:homeMadeGroups) {
                group.remove(chickenObject);
            }
        }
    }

    public void remove(RocketObject rocketObject){
        synchronized (this) {
            rocketObjects.remove(rocketObject);
        }
    }

    public void remove(ButtonObject buttonObject){
        synchronized (this) {
            buttonObjects.remove(buttonObject);
        }
    }

    public void remove(ChickenGroup chickenGroup){
        synchronized (this) {
            chickenGroups.remove(chickenGroup);
        }
    }

    public void remove(AbstractHomeMadeChickenGroup chickenGroup){
        synchronized (this) {
            homeMadeGroups.remove(chickenGroup);
        }
    }

    public void remove(TextfieldObject textfieldObject){
        synchronized (this) {
            textfieldObjects.remove(textfieldObject);
        }
    }

    public void remove(NumberFieldObject numberFieldObject){
        synchronized (this) {
            numberFieldObjects.remove(numberFieldObject);
        }
    }

    public void remove(CursorObject cursorObject){
        synchronized (this) {
            cursorObjects.remove(cursorObject);
        }
    }

//    public void remove(GameObject object){
//        synchronized (this) {
//            anyOtherObject.remove(object);
//        }
//    }

    public void remove(Explosion object){
        synchronized (this) {
            explosions.remove(object);
        }
    }

    public void remove(MessageObject object){
        synchronized (this) {
            messageObjects.remove(object);
        }
    }

    public void remove (User user){
        synchronized (this) {
            users.remove(user);
        }
    }

    public void removeClient (User user){
        synchronized (this) {
            clientUsers.remove(user);
        }
    }

    public void remove (Powerup powerup){
        synchronized (this) {
            items.remove(powerup);
        }
    }

    public void remove(Clockable clockable){
        synchronized (this) {
            try {
                GameModel.class.getMethod("remove", clockable.getClass()).invoke(this, clockable);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

//    public void removeAllChickens(){
//        synchronized (chickenGroups){
//            chickenGroups.clear();
//        }
//    }


    public void clearObjectsExceptMouseAndBackGround(){
        synchronized (this) {
            buttonObjects.clear();
            bulletObjects.clear();
            messageObjects.clear();
            textfieldObjects.clear();
            userPreviewers.clear();
            numberFieldObjects.clear();
            rocketObjects.clear();
            chickenGroups.clear();
            homeMadeGroups.clear();
            eggObjects.clear();
            coinObjects.clear();
            items.clear();
            explosions.clear();
        }
    }

    //adds
    public void add(ButtonObject buttonObject){
        synchronized (this) {
            buttonObjects.add(buttonObject);
        }
    }

    public void add(BulletObject bulletObject){
        synchronized (this) {
            bulletObjects.add(bulletObject);
        }
    }

    public void add(Explosion explosion){
        synchronized (this) {
            explosions.add(explosion);
        }
    }

    public void add(CoinObject coinObject){
        synchronized (this) {
            coinObjects.add(coinObject);
        }
    }

    public void add(Item power){
        synchronized (this) {
            items.add(power);
        }
    }

    public void add(ChickenGroup group){
        synchronized (this) {
            chickenGroups.add(group);
        }
    }

    public void add(AbstractHomeMadeChickenGroup group){
        synchronized (this) {
            homeMadeGroups.add(group);
        }
    }

    public void add(CursorObject cursorObject){
        synchronized (this) {
            cursorObjects.add(cursorObject);
        }
    }

    public void add(EggObject eggObject){
        synchronized (this) {
            eggObjects.add(eggObject);
        }
    }

    public void add(TextfieldObject textfieldObject){
        synchronized (this) {
            textfieldObjects.add(textfieldObject);
        }
    }

    public void add(NumberFieldObject numberFieldObject){
        synchronized (this) {
            numberFieldObjects.add(numberFieldObject);
        }
    }

    public void add(MessageObject object){
        synchronized (this) {
            messageObjects.add(object);
        }
    }

    public void add(UserPreviewer object){
        synchronized (this) {
            userPreviewers.add(object);
        }
    }

    public void add(RocketObject object){
        synchronized (this) {
            rocketObjects.add(object);
        }
    }

    public void addUser(ArrayList<User> list){
        synchronized (this) {
            users.addAll(list);
        }
    }

    public void addUser(User user){
        synchronized (this) {
            users.add(user);
        }
    }

    //set
    public void setUser (User user){
        synchronized (this) {
            if(clientUsers.size()==0)
                clientUsers.add(user);
            else
                clientUsers.set(0,user);
            System.out.println(clientUsers.size());
        }
    }

    public void addClientUser(User user){
        synchronized (this){
            clientUsers.add(user);
            System.out.println(clientUsers.size());
        }
    }

//    public void setJustWave(int wave){
//            justWave = wave;
//    }

    public void setTempAndStatusBar(int i){
        if (page == 2 || page==3) {
            tempBar.setValue(getPlaeyrCuror(i).getTemp());
            User user = clientUsers.get(i);
            statusBox.setText("Life: " + user.getLife() + "         Rockets: " + user.getRocket() + "         Score: " + user.getScore());
        }
    }


    //mouse
//    public void setMouseLocation(int x, int y){
//        mouse_x=x;
//        mouse_y=y;
//    }

//    public int[] getMouseLocation(){
//        return new int[]{mouse_x,mouse_y};
//    }

    //trigger
//    public boolean canShoot(){
//        return trigger && page==2 && !overHeat;
//    }
//
//    public void setTrigger(boolean trigger){
//        this.trigger=trigger;
//    }
//
//    public void setTemp(int temp){
//        this.temp =temp;
//    }
//
//    public int getTemp(){
//        return temp;
//    }

}


