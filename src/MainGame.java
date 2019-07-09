import Commens.BufferedFiles;
import MVC.View.GameView;

import javax.swing.*;

public class MainGame {
    public static void main(String[] args) {
        BufferedFiles.load();
        SwingUtilities.invokeLater(() -> {
            try {
                createAndShowGUI();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static void createAndShowGUI() {
        new GameView();
    }
}
