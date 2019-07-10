package MVC.Controller;

import Commens.BufferedFiles;
import Commens.Constants;
import MVC.Model.GameModel;
import MVC.Model.Interfaces.Clockable;
import MVC.Model.ObjectsModel.*;
import MVC.Model.User;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


class GameServer {
    private ServerSocket serverSocket;
    private final GameModel model;
    private ArrayList<ClientHandler> clientHandlers=new ArrayList<>();
    private int port;
    private int maximumClients=1;
    private int numberOfWaves;
    private int playingWave = 0;

    GameServer(int port){
        this.port=port;
        model=new GameModel();
        new Thread(this::runServer).start();
        choosePlayer();
        new Thread(this::Control).start();
        new Thread(this::triggerController).start();
        new Thread(this::tempController).start();
    }
    private void runServer(){
        try {
            serverSocket=new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true){
            if(( (model.page==6||model.page==2 || model.page==3) && maximumClients > clientHandlers.size() )||clientHandlers.size()==0) {
                try {
                    new ClientHandler(serverSocket.accept()).start();
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(model.page==3){
                        pause();
                        pause();
                    }
                    if(model.page==6)
                        waitForClients();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    class ClientHandler extends Thread{
        private Socket socket;
        private ObjectOutputStream objectOutputStream;
        private ObjectInputStream objectInputStream;
        ClientHandler(Socket socket) throws IOException {
            this.socket=socket;
            clientHandlers.add(this);
            CursorObject newCursor=new CursorObject(model.page != 2);
            model.add(newCursor);
            if(model.page==2)
                newCursor.setHidden(true);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        void changePort(int newPort,boolean asSpectator){
            try {
                ArrayList<Object> message=new ArrayList<>();
                message.add("changePort");
                message.add(newPort);
                message.add(asSpectator);
                synchronized (model) {
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                }
                closeAll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        void closeAll() {
            model.remove(model.getPlaeyrCuror(clientHandlers.indexOf(this)));
            model.removeClient(model.getUser(clientHandlers.indexOf(this)));
            try {
                socket.close();
                objectOutputStream.close();
                objectInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            clientHandlers.remove(this);
            if(model.page==6){
                waitForClients();
            }
        }

        void playSound(String s){
            try {
                ArrayList list=new ArrayList();
                list.add("Sound");
                list.add(s);
                synchronized (model) {
                    objectOutputStream.writeObject(list);
                    objectOutputStream.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run(){
            while (!socket.isClosed()){
                try {
                    ArrayList messaage= (ArrayList) objectInputStream.readObject();
                    String newLine= (String) messaage.get(0);
                    switch (newLine) {
                        case "mouseMoved":
                            int[] location= (int[]) messaage.get(1);
                            mouseMoved(location[0], location[1],clientHandlers.indexOf(this));
                            break;
                        case "mousePressed": {
                            int mouseButton = (int) messaage.get(1);
                            mousePressed(mouseButton,clientHandlers.indexOf(this));
                            break;
                        }
                        case "setUser": {
                            System.out.println("adding new Client user");
                            User newUser= (User) messaage.get(1);
                            model.addClientUser(newUser);
                            model.getPlaeyrCuror(clientHandlers.indexOf(this)).setText(newUser.getUsername());
                            boolean asSpectator= (boolean) messaage.get(2);
                            if(asSpectator){
                                CursorObject cursorObject=model.getCursorObjects().get(clientHandlers.indexOf(this));
                                cursorObject.setSpectator(true);
                            }
                            break;
                        }
//                        case "asSpectator": {
//
//                            boolean asSpectator= (boolean) messaage.get(1);
//                            if(asSpectator){
//                                model.getCursorObjects().get(clientHandlers.indexOf(this)).setHidden(true);
//                            }
//                            break;
//                        }
                        case "mouseReleased": {
                            int mouseButton = (int) messaage.get(1);
                            mouseReleased(mouseButton,clientHandlers.indexOf(this));
                            break;
                        }
                        case "keyPressed": {
                            char keychar = (char) messaage.get(1);
                            keyPressed(keychar, clientHandlers.indexOf(this));
                            break;
                        }
//                        case "connectionStatus": {
//                            objectOutputStream.writeUTF("connectionStatus");
//                            objectOutputStream.writeBoolean(true);
//                            break;
//                        }
                        case "getObjectList": {
                            ArrayList list=new ArrayList();
                            list.add("getObjectList");
                            list.add(clientHandlers.indexOf(this));
                            synchronized (model) {
                                objectOutputStream.reset();
                                list.add(model);
                                objectOutputStream.writeObject(list);
                                objectOutputStream.flush();
                            }
                            break;
                        }
                    }
                } catch (IOException e) {
                    closeAll();
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /////////////////

    private void mouseMoved(int x, int y,int i){
        model.getPlaeyrCuror(i).setX(x);
        model.getPlaeyrCuror(i).setY(y);
    }

    private void mousePressed(int mouseButton,int i){
        if(mouseButton==1) {
            model.getPlaeyrCuror(i).setTrigger(true);
            for(ButtonObject button:model.getButtonObjects()){
                if(button.isMouseIn()){
                    try {
                        GameServer.class.getMethod(button.getCommand()).invoke(this);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        System.out.println(button.getCommand());
                        e.printStackTrace();
                    }
                }
            }
            for (TextfieldObject textfieldObject:model.getTextfieldObjects()) {
                if(textfieldObject.isMouseIn()) {
                    for (TextfieldObject textfieldObject1:model.getTextfieldObjects()) {
                        if(textfieldObject1.isSelected()){
                            textfieldObject1.setSelected(false);
                        }
                    }
                    textfieldObject.setSelected(true);
                }
            }
            for (NumberFieldObject numberFieldObject:model.getNumberFieldObjects()) {
                if(numberFieldObject.isMouseIn()) {
                    for (NumberFieldObject numberFieldObject1:model.getNumberFieldObjects()) {
                        if(numberFieldObject1.isSelected()){
                            numberFieldObject1.setSelected(false);
                        }
                    }
                    numberFieldObject.setSelected(true);
                }
            }
        }else if(mouseButton==3){
            if(model.page==2) {
                if(model.getUser(i).getRocket()>0 && !model.getPlaeyrCuror(i).isHidden()) {
                    for (ClientHandler clientHandler:clientHandlers) {
                        clientHandler.playSound("rocket");
                    }
                    model.add(new RocketObject(model.getPlayerLocation(i),i));
                    model.getUser(i).setRocket(model.getUser(i).getRocket() - 1);
                }
            }
        }
    }

    private void mouseReleased(int mouseButton,int i) {
        if(mouseButton==1){
            model.getPlaeyrCuror(i).setTrigger(false);
        }
    }

    private void keyPressed(char keychar,int i) {
        if(keychar==27 && (model.page==2||model.page==3)){
            pause();
        }
        for (TextfieldObject textfieldObject:model.getTextfieldObjects()) {
            if(textfieldObject.isSelected()){
                textfieldObject.updateText(keychar);
            }
        }
        for (NumberFieldObject numberFieldObject:model.getNumberFieldObjects()) {
            if(numberFieldObject.isSelected()){
                numberFieldObject.updateText(keychar);
            }
        }
    }

    private void Control () {
        while (true){
            if(clientHandlers.size()!=0) {
                ClockControl();
                checkButtons();
                checkAnyHit();
                layEggControl();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void ClockControl(){
        if(model.page!=3) {
            for (Clockable clockable : model.getClockableObjects()) {
                clockable.stepClock();
                if (clockable.dispose()) {
                    if(clockable.getClass()==MessageObject.class){
                        mainMenu();
                        if(clientHandlers.size()!=1)
                            waitForClients();
                        playingWave=0;
                    }
                    if (clockable.getClass() == ChickenGroup.class) {
                        playingWave++;
                        for (CursorObject cursor:model.getCursorObjects()) {
                            if(cursor.isHidden()) {
                                if( model.getClientUsers().get(model.getCursorObjects().indexOf(cursor)).getLife()!=0 ) {
                                    cursor.resetSpaceship();
                                    cursor.setHidden(false);
                                }
                            }
                        }
                        if(playingWave>=numberOfWaves) {
                            viewCongrats();
                        }else {
                            model.add(newChickenGroup());
                        }
                    }
                    if (clockable.getClass() == RocketObject.class) {
                        model.add(new Explosion(new int[]{Constants.frameWidth / 2, Constants.frameHeight / 2}));
                        for (ClientHandler clientHandler:clientHandlers) {
                            clientHandler.playSound("exp");
                        }
//                        for (ChickenGroup chickenGroup : model.getActiveGroup()) {
                            for (ChickenObject chicken : model.getChickens()) {
                                if (chicken.hitAndIsDead(50)) {
                                    removeChicken(chicken,((RocketObject)clockable).getPlayerId());
                                }
                            }
//                        }
                        for (EggObject egg : model.getEggObjects()) {
                            model.remove(egg);
                        }
                    }
                    model.remove(clockable);
                }
            }
        }
    }

    private void checkAnyHit(){
        if(model.page==2) {
            //Bullet to chickens
            for (BulletObject bullet : model.getBulletObjects()) {
//                for (ChickenGroup group : model.getActiveGroup()) {
                    for (ChickenObject chicken : model.getChickens()) {
                        if (chicken.checkHit(bullet)) {
                            model.remove(bullet);
                            if(chicken.hitAndIsDead(bullet.getDamage())) {
                                removeChicken(chicken,bullet.getPlayerId());
                            }
                        }
                    }
//                }
            }
            //for each cursor
            ArrayList<CursorObject> cursorObjects = model.getCursorObjects();
            for (int i = 0; i < cursorObjects.size(); i++) {
                CursorObject cursorObject = cursorObjects.get(i);
                if (cursorObject.moved() == 0) {
                    //cursor to chickens
                        for (ChickenObject chicken : model.getChickens()) {
                            if (cursorObject.checkHit(chicken)) {
                                if (chicken.hitAndIsDead(50)) {
                                    removeChicken(chicken,model.getCursorObjects().indexOf(cursorObject));
                                }
                                //TODO check this line for die
                                die(cursorObject);
                            }
                        }
                    //cursor to eggs
                    for (EggObject egg : model.getEggObjects()) {
                        if (cursorObject.checkHit(egg)) {
                            model.remove(egg);
                            die(cursorObject);
                        }
                    }
                    for (BulletObject bulletObject : model.getBulletObjects()) {
                        if (cursorObject.checkHit(bulletObject)&&bulletObject.getPlayerId()!=cursorObjects.indexOf(cursorObject)) {
                            model.remove(bulletObject);
                            die(cursorObject);
                        }
                    }
//                    //cursor to cursor
//                    for (CursorObject cursor : model.getCursorObjects()) {
//                        if (cursorObject.checkHit(cursor)) {
////                            model.remove(cursor);
//                            die(cursorObject);
//                        }
//                    }
                } else {
                    for (EggObject egg : model.getEggObjects()) {
                        if (cursorObject.checkHit(egg)) {
                            model.remove(egg);
                        }
                    }
                }
                //cursor to coin
                for (CoinObject coin : model.getCoinObjects()) {
                    if (cursorObject.checkHit(coin)) {
                        model.remove(coin);
                        model.getUser(i).setCoin(model.getUser(i).getCoin() + 1);
                    }
                }
                //cursor to power up
                for (Item power : model.getItems()) {
                    if (cursorObject.checkHit(power)) {
                        model.remove(power);
                        if (power.getClass() == bulletGift.class) {
                            if (model.getUser(i).getBulletType() == ((bulletGift) power).getBulletType())
                                model.getUser(i).setPowerUp(model.getUser(i).getPowerUp() + 1);
                            else
                                model.getUser(i).setBulletType(((bulletGift) power).getBulletType());
                        } else
                            model.getUser(i).setPowerUp(model.getUser(i).getPowerUp() + 1);
                        cursorObject.setTemp(1);
                        //model.getUser().setScore(model.getUser().getScore()+1);
                    }
                }
            }
        }
    }

    private void removeChicken(ChickenObject chicken,int playerId){
        model.remove(chicken);
        if (chicken.getType() == 5) {
            for (int i = 0; i < 30; i++) {
                model.add(new CoinObject(new int[]{chicken.getX(), chicken.getY()}));
            }
        }
        model.getUser(playerId).setScore(model.getUser(playerId).getScore()+chicken.getType());
//        model.getUser().setScore(model.getUser().getScore() + chicken.getType());
        if (chicken.coin()) {
            model.add(new CoinObject(new int[]{chicken.getX(), chicken.getY()}));
        } else if (chicken.powerUp()) {
            model.add(new Powerup(new int[]{chicken.getX(),chicken.getY()}));
        } else if(chicken.gift()){
            model.add(new bulletGift(new int[]{chicken.getX(), chicken.getY()}, (int) (Math.random()* BufferedFiles.bulletTypeNumber)));
        }
    }

    private void layEggControl(){
        if(model.page==2)
//            for (ChickenGroup group:model.getActiveGroup()) {
                for (ChickenObject chicken:model.getChickens()) {
                    if(chicken.getType()==5 && chicken.lay()){
                        double randomAngle=Math.random()*Math.PI/2;
                        for (int i = 0; i <8 ; i++) {
                            model.add(new EggObject(new int[]{chicken.getX(),chicken.getY()},2 ,Math.PI/4*i+randomAngle));
                        }
                    }
                    if(chicken.lay()&& chicken.getType()!=5){
                        model.add(new EggObject(new int[]{(int) chicken.getDoubleX(), (int) chicken.getDoubleY()},chicken.getType()*0.7));
                    }
                }
//            }
    }

    private void checkButtons(){
        if(model.page!=2)
            for (ButtonObject button:model.getButtonObjects()) {
                button.setMouseIn(model.getCursorObjects());
            }
        if(model.page!=2)
            for (TextfieldObject textfield:model.getTextfieldObjects()) {
                textfield.setMouseIn(model.getCursorObjects());
            }
        if(model.page!=2)
            for (NumberFieldObject numberFieldObject:model.getNumberFieldObjects()) {
                numberFieldObject.setMouseIn(model.getCursorObjects());
            }
    }

//    private void checkBackground(){
//        if(model.page!=3)
//            model.getBackGroundObject().stepClock();
//    }

    private void triggerController(){
        while (true) {
            for (CursorObject cursorObject:model.getCursorObjects()) {
                if(cursorObject.canShoot()&&model.page==2){
                    shoot(cursorObject);
                }
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void shoot(CursorObject cursorObject){
        for (ClientHandler clientHandler:clientHandlers) {
            clientHandler.playSound("bullet");
        }
        int i=model.getCursorObjects().indexOf(cursorObject);
        cursorObject.setTemp(cursorObject.getTemp() + 3);
        cursorObject.react();
        int type=model.getUser(i).getBulletType();
        int powerUp=model.getUser(i).getPowerUp();
        if(type<BufferedFiles.bulletTypeNumber) {
            int[] loc=cursorObject.getLocation();
            int newType=model.getUser(i).getBulletType();
            int newPowerUp=model.getUser(i).getPowerUp();
            //todo deffrent type of power ups
            if(powerUp==0) {
                model.add(new BulletObject(loc,newType,0,newPowerUp,i));
            }else if(powerUp==1){
                model.add(new BulletObject(new int[]{loc[0]- BufferedFiles.get("bullet").getWidth()/3,loc[1]},newType,0,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]+ BufferedFiles.get("bullet").getWidth()/3,loc[1]},newType,0,newPowerUp,i));
            }else if(powerUp==2){
                model.add(new BulletObject(loc,newType,0,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]- BufferedFiles.get("bullet").getWidth()/2,loc[1]},newType,-5,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]+ BufferedFiles.get("bullet").getWidth()/2,loc[1]},newType,+5,newPowerUp,i));
            }else{
                model.add(new BulletObject(new int[]{loc[0]- BufferedFiles.get("bullet").getWidth()/3,loc[1]},newType,-2.5,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]+ BufferedFiles.get("bullet").getWidth()/3,loc[1]},newType,+2.5,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]- BufferedFiles.get("bullet").getWidth()/3*2,loc[1]},newType,-5,newPowerUp,i));
                model.add(new BulletObject(new int[]{loc[0]+ BufferedFiles.get("bullet").getWidth()/3*2,loc[1]},newType,+5,newPowerUp,i));
            }
        }
    }

    private void tempController(){
        while (true){
            for (CursorObject cursorObject:model.getCursorObjects()) {
                if(!cursorObject.canShoot() && cursorObject.getTemp()>0 && model.page==2){
                    cursorObject.setTemp(cursorObject.getTemp()-1);
                }
                if(cursorObject.getTemp()>=100 && !cursorObject.overHeat && model.page==2){
                    cursorObject.setTemp(cursorObject.getTemp()+60);
                    cursorObject.overHeat=true;
                }
                if(cursorObject.getTemp()==0 && model.page==2){
                    cursorObject.overHeat=false;
                }
            }
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void die(CursorObject cursorObject){
        int j=model.getCursorObjects().indexOf(cursorObject);
        model.getUser(j).setLife(model.getUser(j).getLife()-1);
        model.getUser(j).setPowerUp(model.getUser(j).getPowerUp()==0?0:model.getUser(j).getPowerUp()-1);
        cursorObject.overHeat=true;
        cursorObject.setTemp(100);
        for (int i = 0; i <2*model.getUser(j).getCoin()/3 ; i++) {
            model.add(new CoinObject(cursorObject.getLocation()));
        }
        model.getUser(j).setCoin(0);
        model.add(new Explosion(cursorObject.getLocation()));
        for (ClientHandler clientHandler:clientHandlers) {
            clientHandler.playSound("exp");
        }
        if(model.getUser(j).getLife()==0){
            cursorObject.setHidden(true);
            viewGameOver();
        }
        cursorObject.explode();
//        mainMenu();
//        model.getCursorObjects().get(0).changType();
    }

    public void mainMenu() {
        for (ClientHandler clientHandler:clientHandlers) {
            clientHandler.playSound("playMain");
        }
        int size=clientHandlers.size();
        for (int i = 1; i <size ; i++) {
            clientHandlers.get(i).closeAll();
        }
        maximumClients=1;
        boolean go=false;
        if(model.getSelectedUser()!=null){
            go=true;
            model.setUser(model.getSelectedUser());
        }
        if(model.page!=0){
            go=true;
        }
        if(model.page==3&&clientHandlers.size()==1){
            model.getUser(0).setWaveToResum(playingWave);
        }

        if(go){
            saveDataFromModel();
            model.clearObjectsExceptMouseAndBackGround();
            for (CursorObject cursorObject:model.getCursorObjects()) {
                if(!cursorObject.isFork())
                    cursorObject.changType();
            }
            if(model.getUser(0).isResume()) {
                model.add(new ButtonObject(Constants.frameWidth * 5 / 12, Constants.frameHeight / 12 * 3, Constants.frameWidth / 6, Constants.frameHeight / 8, "newGame", "new game"));
                model.add(new ButtonObject(Constants.frameWidth * 5 / 12, Constants.frameHeight / 12 * 5, Constants.frameWidth / 6, Constants.frameHeight / 8, "resumeGame", "resume game"));
            }else
                model.add(new ButtonObject(Constants.frameWidth * 5 / 12, Constants.frameHeight / 12 * 5, Constants.frameWidth / 6, Constants.frameHeight / 8, "newGame", "new game"));
            model.add(new ButtonObject(Constants.frameWidth*7/24,Constants.frameHeight /12*7,Constants.frameWidth/6,Constants.frameHeight /8,"choosePlayer", "choose player"));
            model.add(new ButtonObject(Constants.frameWidth*13/24,Constants.frameHeight /12*7,Constants.frameWidth/6,Constants.frameHeight /8,"multiPlayer", "multi player"));
            model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
            model.add(new ButtonObject(Constants.frameWidth*8/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"options", "Options"));
            model.add(new ButtonObject(Constants.frameWidth*2/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"exit", "exit game"));
            model.page=1;
            model.getBackGroundObject().setInGame(false);
        }
    }

    public void exit() {
        System.exit(0);
    }

    public void addPlayer(){
        if (model.getTextfieldObjects().size()<5) {
            User newUser = new User("blank");
            model.addUser(newUser);
            model.add(new TextfieldObject(Constants.frameWidth/20, (int) (Constants.frameHeight/8 * (model.getUsers().size() - 0.5)), Constants.frameWidth/8,Constants.frameHeight/12, newUser));
            for (TextfieldObject textfield : model.getTextfieldObjects()) {
                textfield.setSelected(false);
            }
            model.getTextfieldObjects().get(model.getTextfieldObjects().size() - 1).setSelected(true);
        }
    }

    public void removePlayer(){
        for (TextfieldObject textfieldObject: model.getTextfieldObjects()) {
            if(textfieldObject.isSelected()){
                int i=model.getTextfieldObjects().indexOf(textfieldObject);
                model.remove(textfieldObject);
                model.remove(textfieldObject.getUser());
                if(i!=0){
                    model.getTextfieldObjects().get(i-1).setSelected(true);
                }else if(model.getTextfieldObjects().size()!=0){
                    model.getTextfieldObjects().get(0).setSelected(true);
                }
                break;
            }
        }
        ArrayList<TextfieldObject> textfieldObjects = model.getTextfieldObjects();
        for (int i = 0; i < textfieldObjects.size(); i++) {
            TextfieldObject textfieldObject = textfieldObjects.get(i);
            textfieldObject.setY((int) (Constants.frameHeight*(i+0.5)/8));
        }
    }

    public void newGame() {
        model.getUser(0).resetUser();
        model.getUser(0).setResume(true);
        numberOfWaves=12;
//        model.setJustWave(0);
        resumeGame();
    }

    public void resumeGame() {
        for (ClientHandler clientHandler:clientHandlers) {
            clientHandler.playSound("stopMain");
        }
        model.clearObjectsExceptMouseAndBackGround();
        for (CursorObject cursor:model.getCursorObjects()) {
            cursor.changType();
            cursor.resetSpaceship();
        }
        model.getBackGroundObject().setInGame(true);
        if(model.page==6){
            playingWave=0;
        }else {
            playingWave=model.getUser(0).getWaveToResum();
//            model.setJustWave(model.getUser(0).getWaveToResum());
        }
        model.page=2;
        model.add(newChickenGroup());
    }

    public void choosePlayer(){
        for (ClientHandler clientHandler:clientHandlers) {
            clientHandler.playSound("playMain");
        }
        model.page=0;
        model.clearObjectsExceptMouseAndBackGround();
        loadDataToModel();
        model.add(new ButtonObject(Constants.frameWidth*2/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"addPlayer", "add player"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","login"));
        model.add(new ButtonObject(Constants.frameWidth*8/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"removePlayer","remove player"));
        ArrayList<User> users = model.getUsers();
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            model.add(new TextfieldObject(Constants.frameWidth/20, (int) (Constants.frameHeight*(i+0.5)/8),Constants.frameWidth/8,Constants.frameHeight/12 ,user));
        }
        if(model.getTextfieldObjects().size()!=0)
            model.getTextfieldObjects().get(model.getTextfieldObjects().size()-1).setSelected(true);
    }

    private void viewCongrats(){
        model.add(new MessageObject("congrats!!!",Color.white));
        model.getUser(0).setResume(false);
    }

    public void viewGameOver(){
        boolean allDead=true;
        for (User user:model.getClientUsers()) {
            allDead=allDead&&user.getLife()==0;
        }
        if(allDead) {
            model.add(new MessageObject("game over!!!", Color.red));
            model.getUser(0).setResume(false);
        }
    }

    public void hallOfFame(){
        model.clearObjectsExceptMouseAndBackGround();
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8 ,"mainMenu","main menu"));
//        model.add(new ButtonObject(Constants.frameWidth/7*5,Constants.frameHeight /8*5,Constants.frameWidth/7,Constants.frameHeight /8,"addPlayer", "add player"));
//        model.add(new ButtonObject(Constants.frameWidth/7,Constants.frameHeight /8*5,Constants.frameWidth/7, Constants.frameHeight /8 ,"removePlayer","remove player"));
        ArrayList<User> users = model.getUsers();
        users.sort((o1, o2) -> {
            if(o1.getWave()-o2.getWave()!=0){
                return o2.getWave()-o1.getWave();
            }
            if(o1.getScore()-o2.getScore()!=0)
                return o2.getScore()-o1.getScore();
            else
                return o1.getClock()-o2.getClock();
        });
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            model.add(new UserPreviewer(0,Constants.frameHeight/8*(i+1),Constants.frameWidth,Constants.frameHeight/10,user));
        }
        if(model.getTextfieldObjects().size()!=0)
            model.getTextfieldObjects().get(model.getTextfieldObjects().size()-1).setSelected(true);
    }

    public void options(){
        model.clearObjectsExceptMouseAndBackGround();
        //model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","main menu"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*7,Constants.frameWidth/6, Constants.frameHeight /8 ,"sounds","sounds"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"graphics","graphics"));
    }

    public void sounds(){
        //todo make sounds configurations
        model.clearObjectsExceptMouseAndBackGround();
        //model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"options","back to options"));

    }

    public void graphics(){
        //todo make graphics configurations
        model.clearObjectsExceptMouseAndBackGround();
        //model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"options","back to options"));

    }

    public void multiPlayer(){
        model.clearObjectsExceptMouseAndBackGround();
        model.add(new ButtonObject(Constants.frameWidth*7/24,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"joinGame","join game"));
        model.add(new ButtonObject(Constants.frameWidth*13/24,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"hostGame","host game"));
        //model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","main menu"));
    }

    public void joinGame(){
        model.clearObjectsExceptMouseAndBackGround();
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","main menu"));
        model.add(new ButtonObject(Constants.frameWidth*7/24,Constants.frameHeight /12*7,Constants.frameWidth/6, Constants.frameHeight /8 ,"changeClientPort","join game as player"));
        model.add(new ButtonObject(Constants.frameWidth*13/24,Constants.frameHeight /12*7,Constants.frameWidth/6, Constants.frameHeight /8 ,"changeClientPort","join game as spectator"));
        model.add(new NumberFieldObject(Constants.frameWidth*3/24, Constants.frameHeight/12*5,Constants.frameWidth/8,Constants.frameHeight/12,"port",true,5));
//        model.add(new NumberFieldObject(Constants.frameWidth*3/24, Constants.frameHeight/12*7,Constants.frameWidth/8,Constants.frameHeight/12,"wave"));
//        model.add(new ButtonObject(Constants.frameWidth*7/24,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","join game"));
//        model.add(new ButtonObject(Constants.frameWidth*13/24,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","host game"));
//        //model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6,Constants.frameHeight /8,"hallOfFame", "hall of fame"));
//        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","main menu"));
    }

    public void changeClientPort(){
        int port=0;
        boolean asSpectator=false;
        for (ButtonObject button:model.getButtonObjects()) {
            if(button.getText().equals("join game as spectator"))
                asSpectator=button.isMouseIn();
        }
        ArrayList<ClientHandler> list=new ArrayList<>(clientHandlers);
        for (ClientHandler clientHandler:list) {
            for (NumberFieldObject numberFieldObject:model.getNumberFieldObjects()) {
                if(numberFieldObject.getDefualtText().equals("port")){
                    if(!numberFieldObject.getText().equals("port"))
                        port= Integer.parseInt(numberFieldObject.getText());
                    else
                        return;
                }
            }
            clientHandler.changePort(port,asSpectator);
        }
    }

    public void hostGame(){
        model.clearObjectsExceptMouseAndBackGround();
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*9,Constants.frameWidth/6, Constants.frameHeight /8 ,"mainMenu","main menu"));
        model.add(new ButtonObject(Constants.frameWidth*5/12,Constants.frameHeight /12*7,Constants.frameWidth/6, Constants.frameHeight /8 ,"waitForClients","run server"));
        model.add(new NumberFieldObject(Constants.frameWidth*3/24, Constants.frameHeight/12*3,Constants.frameWidth/3,Constants.frameHeight/12, "maximum player number: ",false,0));
        model.add(new NumberFieldObject(Constants.frameWidth*12/24, Constants.frameHeight/12*3,Constants.frameWidth/12,Constants.frameHeight/12, "",true,1));
        model.add(new NumberFieldObject(Constants.frameWidth*3/24, Constants.frameHeight/12*5,Constants.frameWidth/3,Constants.frameHeight/12, "number of waves",false,0));
        model.add(new NumberFieldObject(Constants.frameWidth*12/24, Constants.frameHeight/12*5,Constants.frameWidth/12,Constants.frameHeight/12, String.valueOf(4),true,2));
    }

    public void waitForClients(){
        for (NumberFieldObject numberFieldObject : model.getNumberFieldObjects()) {
            if(numberFieldObject.getDefualtText().equals("")){
                maximumClients=Integer.parseInt(numberFieldObject.getText());
            }
            if(numberFieldObject.getDefualtText().equals("4")){
                numberOfWaves=Integer.parseInt(numberFieldObject.getText());
            }
        }
        if (numberOfWaves <= 0|| maximumClients<=0) {
            return;
        }
        model.page=6;
        model.clearObjectsExceptMouseAndBackGround();
        model.add(new NumberFieldObject(Constants.frameWidth*8/24, Constants.frameHeight/12*9,Constants.frameWidth/3,Constants.frameHeight/12, "ask your friend to connect to port: "+ port,false,0));
        model.add(new ButtonObject(Constants.frameWidth*9/12,Constants.frameHeight /12*5,Constants.frameWidth/6, Constants.frameHeight /8 ,"resumeGame","start game"));
        ArrayList<User> clientUsers = model.getClientUsers();
        int i=0;
        for (; i < clientUsers.size(); i++) {
            User user = clientUsers.get(i);
            model.add(new NumberFieldObject((int) (Constants.frameWidth /24*(i/5*6.5+1)), Constants.frameHeight/24*(2+3*(i%5)),Constants.frameWidth/4,Constants.frameHeight/12, user.getUsername() +" joined",false,0));
        }
        for (; i < maximumClients; i++) {
            model.add(new NumberFieldObject((int) (Constants.frameWidth /24*(i/5*6.5+1)), Constants.frameHeight/24*(2+3*(i%5)),Constants.frameWidth/4,Constants.frameHeight/12, "waiting for players to join",false,0));
        }
    }

    private void pause() {
        if(model.page==2){
            model.page=3;
            model.add(new ButtonObject(maximumClients==1 ? Constants.frameWidth*5/12:Constants.frameWidth*6/24,Constants.frameHeight /12*6,Constants.frameWidth/6, Constants.frameHeight /8, "mainMenu",maximumClients==1 ? "back to main menu":"close server"));
            int i=0;
            for (; i <model.getClientUsers().size()-1 ; i++) {
                model.add(new ButtonObject((int) (Constants.frameWidth*21/48*(1+i/5*0.4)),Constants.frameHeight *(25+7*(i%5))/80,Constants.frameWidth/6, Constants.frameHeight /15, "removeClient","disconnect "+model.getUser(i+1).getUsername()));
            }
            for (;i<maximumClients-1;i++){
                model.add(new ButtonObject((int) (Constants.frameWidth*21/48*(1+i/5*0.4)),Constants.frameHeight *(25+7*(i%5))/80,Constants.frameWidth/6, Constants.frameHeight /15, "removeClient","waiting for player.."));
            }
        }else if(model.page==3){
            model.page=2;
            for (ButtonObject button:model.getButtonObjects()) {
                model.remove(button);
            }
        }
        for (CursorObject cursor:model.getCursorObjects()) {
            cursor.changType();
        }
    }

    public void removeClient(){
        for (ButtonObject button:model.getButtonObjects()) {
            if(button.isMouseIn()){
                int i=model.getButtonObjects().indexOf(button);
                if(!button.getText().equals("waiting for player..")) {
                    clientHandlers.get(i).closeAll();
                    pause();
                    pause();
                }
            }
        }
    }

    private void loadDataToModel(){
        for (User user:model.getUsers()) {
            model.remove(user);
        }
//        model.addUser(ReadAndWrite.ReadUsersFromFile());
        model.addUser(ReadAndWrite.ReadUsersFromDB());
    }

//    public void setPlayerToShow(int playerToShow){
//        this.playerToShow=playerToShow;
//    }

    private void saveDataFromModel(){
//        ReadAndWrite.WriteUsersToFile(model.getUsers());
        ReadAndWrite.WriteUsersToDB(model.getUsers());
    }

    private ChickenGroup newChickenGroup(){
        if(playingWave==numberOfWaves-1){
            return new ChickenGroup(1,5,-1,5,clientHandlers.size());
        }
        return new ChickenGroup(playingWave%5==4 ? 1 : (int) (4*Math.sqrt(playingWave)+10),playingWave%5+1 , playingWave+1 ,1,clientHandlers.size());
    }
}
