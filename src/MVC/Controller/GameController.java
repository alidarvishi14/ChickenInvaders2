package MVC.Controller;

import MVC.Model.ObjectsModel.GameObject;

import java.util.ArrayList;

public class GameController {
//    private GameModel model;
    private GameServer gameServer;
    private GameClient gameClient;
    //private boolean isServer;

    public GameController(){
        int port= (int) (Math.random()*1000);
        gameServer=new GameServer(port);
        gameClient=new GameClient(port);
    }

    public ArrayList<GameObject> getObjectList(){
        return gameClient.getObjectList();
    }

    public void mouseMoved(int x, int y){
        gameClient.mouseMoved(x,y);
    }

    public void mousePressed(int mouseButton ){
        gameClient.mousePressed(mouseButton);
    }

    public void mouseReleased(int mouseButton) {
        gameClient.mouseReleased(mouseButton);
    }

    public void keyPressed(char keychar) {
        gameClient.keyPressed(keychar);
    }

    public ArrayList<String> getNewSounds(){
        return gameClient.getNewSounds();
    }
}
