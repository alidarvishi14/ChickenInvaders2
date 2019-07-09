package MVC.View;

import Commens.BufferedFiles;

import javax.sound.sampled.*;
import java.io.IOException;

class SoundPlayer {
    private Clip mainClip;
    SoundPlayer(){
        try {
            AudioInputStream mainAudio = AudioSystem.getAudioInputStream(BufferedFiles.mainTrack);
            mainClip = AudioSystem.getClip();
            mainClip.open(mainAudio);
            mainClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    void play(String s){
        switch (s) {
            case "bullet":
                playBulletSound();
                break;
            case "rocket":
                playRocketSound();
                break;
            case "exp":
                playExplosionSound();
                break;
            case "playMain":
                playMain();
                break;
            case "stopMain":
                stopMain();
                break;
        }
    }

    private void playBulletSound(){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(BufferedFiles.bulletSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playRocketSound(){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(BufferedFiles.rocketSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playExplosionSound(){
        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(BufferedFiles.expSoundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playMain(){
        if(!mainClip.isRunning())
            mainClip.start();
    }

    private void stopMain(){
        mainClip.stop();
        mainClip.setFramePosition(0);
    }

}
