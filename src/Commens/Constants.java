package Commens;

public class Constants {
    public static int frameWidth=1440;
    public static int frameHeight=900;
    public static String dataFileLocation="data.data";
    public static int bulletWidth=BufferedFiles.get("bullet").getWidth();
    public static int coinWidth=BufferedFiles.get("coin").getWidth();
    public static int eggWidth=BufferedFiles.get("egg").getWidth();
    public static int giftWidth=BufferedFiles.get("powerup").getWidth();
    public static int powerUpWidth=BufferedFiles.get("gift").getWidth();
    public static void setFrameDimensionss(int width,int height){
        frameWidth=width;
        frameHeight=height;
        System.out.println(width);
        System.out.println(height);
    }

}
