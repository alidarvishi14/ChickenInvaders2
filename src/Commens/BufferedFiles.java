package Commens;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BufferedFiles {

    private static ArrayList<BufferedImage> spaceship_exp_frame=new ArrayList<>();
    private static ArrayList<BufferedImage> chickenframe=new ArrayList<>();
    public static int spaceshipFrames;
    private static int chickenFrames;
    public static int bossFrames;
    private static ArrayList<BufferedImage> space_time_line=new ArrayList<>();
    private static ArrayList<BufferedImage> bullet_image=new ArrayList<>();
    private static ArrayList<BufferedImage> boss=new ArrayList<>();
    private static BufferedImage fork_image;
    private static BufferedImage rocket_image;
//    private static BufferedImage boss_image;
    public static int bulletTypeNumber;
    private static BufferedImage egg_image;
    private static BufferedImage background_image;
    private static BufferedImage game_back_ground;
    private static BufferedImage coin_image;
    private static BufferedImage power_up;
    private static ArrayList<BufferedImage> gift=new ArrayList<>();
    static ArrayList<Font> textFont=new ArrayList<>();
    static ArrayList<Font> RainFont=new ArrayList<>();
    static ArrayList<Font> buttonFont=new ArrayList<>();
    public static File bulletSoundFile;
    public static File rocketSoundFile;
    public static File expSoundFile;
    public static File mainTrack;


//    public static File bullet_sound_file;
//    public static File rocket_launch_sound_file;
//    public static File rocket_collision_sound_file;

    public static void load(){
        try {
            File textFontFile = new File("Resources/Fonts/Chalkboard.ttc");
            File rainFontFile = new File("Resources/Fonts/SignPainter.ttc");
            File buttonFontFile = new File("Resources/Fonts/Blueberry_Regular.otf");
            for (int i = 0; i <60 ; i++) {
                textFont.add(Fonts.load_font(textFontFile, String.valueOf(i)));
                RainFont.add(Fonts.load_font(rainFontFile, String.valueOf(i)));
                buttonFont.add(Fonts.load_font(buttonFontFile, String.valueOf(i)));
            }

            bulletSoundFile=new File("Resources/sounds/Tink.wav");
            rocketSoundFile=new File("Resources/sounds/launch.wav");
            expSoundFile=new File("Resources/sounds/launch2.wav");
            mainTrack=new File("Resources/sounds/star-wars-theme-song.wav");

            for (String s:new String[]{"Resources/Pics/spaceship/-6.PNG", "Resources/Pics/spaceship/-5.PNG", "Resources/Pics/spaceship/-4.PNG", "Resources/Pics/spaceship/-3.PNG", "Resources/Pics/spaceship/-2.PNG", "Resources/Pics/spaceship/-1.PNG", "Resources/Pics/spaceship/+0.PNG", "Resources/Pics/spaceship/+1.PNG", "Resources/Pics/spaceship/+2.PNG", "Resources/Pics/spaceship/+3.PNG", "Resources/Pics/spaceship/+4.PNG", "Resources/Pics/spaceship/+5.PNG", "Resources/Pics/spaceship/+6.PNG"}) {
                space_time_line.add(ImageIO.read(new File(s)));
                spaceshipFrames++;
            }
            for (String s:new String[]{"Resources/Pics/chickens/1/01.PNG", "Resources/Pics/chickens/1/02.PNG", "Resources/Pics/chickens/1/03.PNG", "Resources/Pics/chickens/1/04.PNG", "Resources/Pics/chickens/1/05.PNG", "Resources/Pics/chickens/1/06.PNG","Resources/Pics/chickens/1/07.PNG", "Resources/Pics/chickens/1/08.PNG", "Resources/Pics/chickens/1/09.PNG", "Resources/Pics/chickens/1/10.PNG", "Resources/Pics/chickens/1/11.PNG", "Resources/Pics/chickens/1/12.PNG"}) {
                chickenframe.add(ImageIO.read(new File(s)));
                chickenFrames++;
            }
            for (String s:new String[]{"Resources/Pics/boss/1.PNG", "Resources/Pics/boss/2.PNG", "Resources/Pics/boss/3.PNG", "Resources/Pics/boss/4.PNG", "Resources/Pics/boss/5.PNG", "Resources/Pics/boss/6.PNG","Resources/Pics/boss/7.PNG", "Resources/Pics/boss/8.PNG", "Resources/Pics/boss/9.PNG"}) {
                boss.add(ImageIO.read(new File(s)));
                bossFrames++;
            }
            for (String s:new String[]{"Resources/Pics/bullets/red.png","Resources/Pics/bullets/green.png","Resources/Pics/bullets/blue.png","Resources/Pics/bullets/gray.png"}) {
                bullet_image.add(ImageIO.read(new File(s)));
                bulletTypeNumber++;
            }
            for (String s:new String[]{"Resources/Pics/powerup/red.png","Resources/Pics/powerup/green.png","Resources/Pics/powerup/blue.png","Resources/Pics/powerup/gray.png"}) {
                gift.add(ImageIO.read(new File(s)));
            }
            BufferedImage spaceship_exp = ImageIO.read(new File("Resources/Pics/exp/exp4.png"));
            int width= spaceship_exp.getWidth()/8;
            for (int i = 0; i <60 ; i++) {
                spaceship_exp_frame.add(spaceship_exp.getSubimage(width*(i%8),width*(i/8),width,width));
            }
            power_up=ImageIO.read(new File("Resources/Pics/powerup/powerup.png"));
//            boss_image=ImageIO.read(new File("Resources/Pics/boss/bosss.png"));
            fork_image=ImageIO.read(new File("Resources/Pics/Fork/fork5.png"));
            rocket_image=ImageIO.read(new File("Resources/Pics/rocket/rocket3.png"));
            //spaceship_image=ImageIO.read(new File("Resources/Pics/spaceship/+0.PNG"));
            egg_image=ImageIO.read(new File("Resources/Pics/egg/egg.png"));
//            tick_image=ImageIO.read(new File(OptionsData.getOptions().tick_address));
            coin_image=ImageIO.read(new File("Resources/Pics/coin/coin.png"));
            background_image=ImageIO.read(new File("Resources/Pics/backgrounds/final_back2.jpg"));
//            choose_player_image=ImageIO.read(new File(OptionsData.getOptions().choose_player_background_address));
            game_back_ground=ImageIO.read(new File("Resources/Pics/backgrounds/final_back.jpg"));
//            bullet_sound_file=new File(OptionsData.getOptions().Bullet_track_address);
//            rocket_launch_sound_file=new File(OptionsData.getOptions().Rocket_track_address);
//            rocket_collision_sound_file=new File(OptionsData.getOptions().Rocket_explode_track_address);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage get(String name){
        if(name.equals("fork"))
            return fork_image;
//        if(name.equals("bullet"))
//            return bullet_image;
//        if(name.equals("spaceship"))
//            return spaceship_image;
        if(name.equals("background1"))
            return background_image;
        if(name.equals("background2"))
            return game_back_ground;
        if(name.equals("rocket"))
            return rocket_image;
        if(name.equals("egg"))
            return egg_image;
        if(name.equals("coin"))
            return coin_image;
        if(name.equals("powerup"))
            return power_up;
        if(name.equals("boss"))
            return boss.get(0);
        return gift.get(0);
    }

    public static BufferedImage get(String name,int frame){
        if(name.equals("bullet")){
            return bullet_image.get(frame);
        }
        if(name.equals("exp")) {
            return spaceship_exp_frame.get(frame);
        }
        if(name.equals("spaceship"))
            return space_time_line.get(frame);
        if(name.equals("chick"))
            return chickenframe.get(chickenFrames-1 - Math.abs(chickenFrames-1 - frame % (2 * (chickenFrames-1))));
        if(name.equals("gift")) {
            return gift.get(frame);
        }
        if(name.equals("boss")) {
            return boss.get(frame);
        }
        return fork_image;
    }

    public static BufferedImage rotate_Image_By_rad (BufferedImage img, double angle) {

        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);

        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) >> 1, (newHeight - h) >> 1);

        int x = w / 2;
        int y = h / 2;

        at.rotate(angle, x, y);
        g2d.setTransform(at);
        g2d.drawImage(img, 0, 0,null);
        g2d.setColor(Color.RED);
        g2d.dispose();

        return rotated;
    }
}
