package MVC.Controller;

import MVC.Model.GameModel;
import MVC.Model.ObjectsModel.GameObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

class GameClient {
    private GameModel model;
    private Socket socket;
    private int mainPort;
    private int mouseX;
    private int mouseY;
    private final ArrayList<String> soundList=new ArrayList<>();
//    private ReentrantLock lock=new ReentrantLock();
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    GameClient(int port){
        this.mainPort =port;
        try {
            socket=new Socket("127.0.0.1",port);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this::sendModelRequest).start();
        new Thread(this::inputThread).start();
        new Thread(this::sendMouseLocation).start();
    }
    void mouseMoved(int x,int y){
        mouseX=x;
        mouseY=y;
    }
    private void sendMouseLocation(){
        while (!socket.isClosed()){
            synchronized (this) {
                try {
                    ArrayList message=new ArrayList();
                    message.add("mouseMoved");
                    message.add(new int[]{mouseX,mouseY});
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
//                    objectOutputStream.writeUTF("mouseMoved");
//                    objectOutputStream.writeObject(new int[]{mouseX,mouseY});
//                    objectOutputStream.writeInt(mouseX);
//                    objectOutputStream.writeInt(mouseY);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    void mousePressed(int mouseButton){
        synchronized (this) {
            try {
                ArrayList message=new ArrayList();
                message.add("mousePressed");
                message.add(mouseButton);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
//                objectOutputStream.writeUTF("mousePressed");
//                objectOutputStream.writeInt(mouseButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void mouseReleased(int mouseButton) {
        synchronized (this) {
            try {
                ArrayList message=new ArrayList();
                message.add("mouseReleased");
                message.add(mouseButton);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
//                objectOutputStream.writeUTF("mouseReleased");
//                objectOutputStream.writeInt(mouseButton);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void keyPressed(char keyChar) {
        synchronized (this) {
            try {
                ArrayList message=new ArrayList();
                message.add("keyPressed");
                message.add(keyChar);
                objectOutputStream.writeObject(message);
                objectOutputStream.flush();
//                objectOutputStream.writeUTF("keyPressed");
//                objectOutputStream.writeChar(keyChar);
//                soundList.add("bullet");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    ArrayList<GameObject> getObjectList(){
        if(model!=null)
            return model.getObjects();
        else
            return new ArrayList<>();
    }

    ArrayList<String> getNewSounds(){
        ArrayList<String> strings = new ArrayList<>(soundList);
        soundList.clear();
        return strings;
    }
    //todo reduce Threads
    private void sendModelRequest(){
        while (!socket.isClosed()) {
            synchronized (this) {
                try {
//                    objectOutputStream.writeUTF("getObjectList");
                    ArrayList message=new ArrayList();
                    message.add("getObjectList");
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("server not responding");
                    System.out.println("back to local server");
                    changeServer(mainPort,false);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void inputThread(){
        while (!socket.isClosed()) {
            try {
                ArrayList message = (ArrayList) objectInputStream.readObject();
                String newLine = (String) message.get(0);
                switch (newLine) {
                    case "getObjectList": {
                        int playerToShow= (int) message.get(1);
//                        System.out.println(playerToShow);
                        GameModel model;
                        model = (GameModel) message.get(2);
                        this.model = model;
                        this.model.setTempAndStatusBar(playerToShow);
                        break;
                    }case "changePort": {
                        int newPort= (int) message.get(1);
                        boolean asSpectator= (boolean) message.get(2);
                        changeServer(newPort,asSpectator);
                        break;
                    }case "Sound": {
                        soundList.add((String) message.get(1));
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("server not responding");
                System.out.println("back to local server");
                changeServer(mainPort, false);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void changeServer(int newPort,boolean asSpectator){
            try {
                synchronized (this) {
                    System.out.println("trying to connect to new server");
                    socket = new Socket("127.0.0.1", newPort);
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    ArrayList message = new ArrayList();
                    message.add("setUser");
                    message.add(model.getUser(0));
//                    message.add("asSpectator");
                    message.add(asSpectator);
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                    System.out.println("user sent to server");
                }
//                objectOutputStream.writeUTF("setUser");
//                objectOutputStream.writeObject(model.getUser(0));
//                objectOutputStream.writeUTF("asSpectator");
//                objectOutputStream.writeBoolean(asSpectator);
//                objectOutputStream.flush();
//                System.out.println("user sent");

//            System.out.println("applied");
            } catch (IOException e) {
                e.printStackTrace();
            }
//        }
    }
}
