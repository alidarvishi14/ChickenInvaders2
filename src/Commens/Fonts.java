package Commens;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {
    static Font load_font(File fontFile, String size) {
        Font fontRaw = null;
        try {
            fontRaw = Font.createFont(Font.TRUETYPE_FONT, fontFile);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        assert fontRaw != null;
        return fontRaw.deriveFont(Float.parseFloat(size));
    }

    public static Font text_font(int size) {
        return BufferedFiles.textFont.get(size);
//        return load_font("Resources/Fonts/Chalkboard.ttc", size);
    }

    public static Font Rain(int size) {
        return BufferedFiles.RainFont.get(size);
    }

    public static Font button_font(int size) {
        return BufferedFiles.buttonFont.get(size);
    }

}