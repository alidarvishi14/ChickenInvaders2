package MVC.Model.ObjectsModel;

import MVC.Model.User;

public class UserPreviewer extends GameObject{
    //private Color color;
    private User user;
    public UserPreviewer(int x, int y, int width, int height, User user) {
        super(x, y, width, height, "", "userPreviewer");
        String firstSpace="";
        for (int i = 0; i <15-user.getUsername().length() ; i++) {
            firstSpace+=" ";
        }
        this.user=user;
        //System.out.println(firstSpace.length()+" "+user.getUsername());
        //setText(user.getUsername()+" :"+ firstSpace + user.getWave() + "     "+ user.getScore() + "     "+ user.getTimePlayed());
        //System.out.println(getText());
        //this.color=color;
    }
//    public Color getColor(){
//        return color;
//    }

    public User getUser() {
        return user;
    }
}
