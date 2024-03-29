package MVC.View;

import MVC.Controller.GameController;
import MVC.Model.ObjectsModel.GameObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

class jPanel extends JPanel {
    private GameController controller;
    private SoundPlayer soundPlayer;
    private JFrame frame;
//    private Cursor blank;
    jPanel(GameController controller,JFrame frame){
        super();

//        //make Cursor invisible
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        blank = toolkit.createCustomCursor(new BufferedImage(64, 64, BufferedImage.TYPE_4BYTE_ABGR), new Point(0, 0), "");

        soundPlayer=new SoundPlayer();
        setFocusable(true);
        requestFocus();
        this.controller=controller;
        this.frame=frame;
        setSize(getPreferredSize());
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                controller.mousePressed(e.getButton());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                controller.mouseReleased(e.getButton());
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                controller.mouseMoved(e.getX(),e.getY());
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                super.mouseMoved(e);
                controller.mouseMoved(e.getX(),e.getY());
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                controller.keyPressed(e.getKeyChar());
//                if(e.getKeyChar()=='a'){
//
//                    try {
//                        getTheClass();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                }
//                com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(frame,true);
//                com.apple.eawt.Application.getApplication().requestToggleFullScreen(frame);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
            }
        });
        new Thread(this::play).start();
    }

    public void paint(Graphics g) {
        super.paint(g);
//        setCursor(blank);
        ArrayList<GameObject> objectList = controller.getObjectList();
        for (int i = 1 ; i <= objectList.size(); i++) {
            GameObject object = objectList.get(objectList.size()-i);
            try {
                PaintView.class.getMethod(object.getPaintMethod(),Graphics.class , object.getClass()).invoke(null , g , object);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        repaint();
    }

    private void play(){
        while (true){
            ArrayList<String>  newSoundList=controller.getNewSounds();
            for (String s:newSoundList) {
                soundPlayer.play(s);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
//    private Class getTheClass() throws Exception {
//
//        JFileChooser fileChooser = new JFileChooser();
//        fileChooser.showOpenDialog(new JFrame());
//        if (fileChooser.getSelectedFile() != null) {
//            String path = fileChooser.getSelectedFile().getPath();
//            System.out.println(path);
//            String name = fileChooser.getSelectedFile().getName();
//            System.out.println(name);
//            for(int i=0;i<path.length();i++){
//                if(path.charAt(i) == '\\'){
//                    path = path.substring(0,i)+"/"+path.substring(i+1);
//                }
//            }
//            path = "file:///"+path;
//            System.out.println(path);
//            ClassLoader cl = new URLClassLoader(new URL[]{new URL(path)});
//
////
////            // Getting the jar URL which contains target class
////            URL[] classLoaderUrls = new URL[]{new URL()};
////
////            // Create a new URLClassLoader
////            URLClassLoader urlClassLoader = new URLClassLoader(classLoaderUrls);
//
//
//            return cl.loadClass(name.substring(0, name.length() - 6));
////                gameServer.getNewMonstersClasses().add(monsterClass);
//        }
//        return null;
//    }
}
