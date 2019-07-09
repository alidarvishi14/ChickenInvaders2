package MVC.View;

import MVC.Controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameView {
    public GameView(){
        // Create views swing UI components
        //AudioPlayer a
//        // Create model
//        GameModel model = new GameModel();


        // Create controller
        GameController controller = new GameController ();


        //Sounds
        //new soundPlayer(controller);


        // Make frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        jPanel panel=new jPanel(controller,frame);

        //make Cursor invisible
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Cursor blank = toolkit.createCustomCursor(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "");
        frame.setCursor(blank);

        // Create views swing UI components
        frame.add(panel);
        frame.repaint();
        frame.setVisible(true);
        com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(frame,true);
        com.apple.eawt.Application.getApplication().requestToggleFullScreen(frame);
        com.apple.eawt.FullScreenUtilities.setWindowCanFullScreen(frame,true);
//        com.apple.eawt.Application.getApplication().requestToggleFullScreen(frame);
//        frame.setSize(100,10);

//        com.apple.eawt.Application.getApplication().requestToggleFullScreen(frame);

    }

}

//class myFrame extends JFrame {
//    myFrame(){
//        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
//        if (gd.isFullScreenSupported()) {
//            setResizable(false);
////            setUndecorated(true);
//            gd.setFullScreenWindow(this);
//            gd.setFullScreenWindow(null);
////            gd.setFullScreenWindow(null);
//            Constants.frameWidth=getWidth();
//            Constants.frameHeight=getHeight();
////            System.out.println(getHeight());
////            System.out.println("hi");
//        } else {
//            System.err.println("Full screen not supported");
//
//            setSize(100, 100); // just something to let you see the window
//            setVisible(true);
//        }
//    }
//}


//class soundPlayer{
//    private GameController controller;
//    private ArrayList<AudioInputStream> streams = new ArrayList<>();
//    private ArrayList<SourceDataLine> lines = new ArrayList<>();
//    soundPlayer(GameController controller){
//        this.controller=controller;
//        new Thread(this::getNewStreams).start();
//        new Thread(this::play).start();
//
//    }
//    private void getNewStreams(){
//        while (true){
//            for (String s:controller.newsounds()) {
//                try {
//                    System.out.println(s);
//                    AudioInputStream stream=getAudioInputStream(new File(s));
//                    streams.add(stream);
//                    lines.add(newLine(stream));
//                } catch (UnsupportedAudioFileException | IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            //System.out.println(streams.size());
//            //System.out.println(lines.size());
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private void play(){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        int n;
//        byte[] buffer=new byte[1024];
//        while (true){
//            //System.out.print(1);
//            for (int i = 0; i < lines.size() ; i++) {
//                try {
//                    n = streams.get(i).read(buffer,0,buffer.length);
//                    if(n==-1){
//                        System.out.println(i);
//                        lines.get(i).drain();
//                        lines.get(i).stop();
//                        lines.remove(i);
//                        streams.remove(i);
//                        continue;
//                    }
//                    lines.get(i).write(buffer,0, n);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    private SourceDataLine newLine (AudioInputStream stream) {
//        AudioFormat inFormat = stream.getFormat();
//        final int ch = inFormat.getChannels();
//        final float rate = inFormat.getSampleRate();
//        AudioFormat format = new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
//        final DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
//        SourceDataLine line = null;
//        try {
//            line = (SourceDataLine) AudioSystem.getLine(info);
//            line.open(format);
//            line.start();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//        return line;
//    }
//}