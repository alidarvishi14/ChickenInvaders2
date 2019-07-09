//import javax.sound.sampled.*;
//import javax.sound.sampled.DataLine.Info;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//
//import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
//import static javax.sound.sampled.AudioSystem.getAudioInputStream;
//
//public class AudioFilePlayer {
//
//    public static void main(String[] args) throws LineUnavailableException, IOException {
//        final AudioFilePlayer player = new AudioFilePlayer ();
//        player.play("Resources/sounds/exp.wav");
//        //player.play("something.ogg");
//    }
//
//    public void play(String filePath) throws LineUnavailableException, IOException {
//        final File file = new File(filePath);
//        //AudioInputStream in2 = null;
//        AudioInputStream in3 = null;
//        try {
//            in3=getAudioInputStream(new File("Resources/sounds/beep.wav"));
//            //in2 = getAudioInputStream(new File("Resources/sounds/beep.wav"));
//        } catch (UnsupportedAudioFileException | IOException e) {
//            e.printStackTrace();
//        }
//        AudioInputStream in = null;
//        try {
//            in = getAudioInputStream(new File("Resources/sounds/exp.wav"));
//        } catch (UnsupportedAudioFileException | IOException ex) {
//            ex.printStackTrace();
//        }
//
//        //inal AudioFormat outFormat = getOutFormat(in.getFormat());
//            //final Info info = new Info(SourceDataLine.class, outFormat);
//            ArrayList<AudioInputStream> list=new ArrayList<>();
////            ArrayList<SourceDataLine> lines=new ArrayList<>();
//            list.add(in);
//            //list.add(in2);
//            list.add(in3);
//            //play(list);
//            //final  SourceDataLine line=(SourceDataLine) AudioSystem.getLine(info);
//            //final  SourceDataLine line1=(SourceDataLine) AudioSystem.getLine(info);
////            line.open(outFormat);
////            line.start();
////            line1.open(outFormat);
////            line1.start();
////            lines.add(line);
////            lines.add(line1);
//            //play(list,lines);
//            play(list);
//            //doublestream(in,in2,line,line1);
//            //stream(in,line);
//            //tream(in2,line1);
////            try (final SourceDataLine line =
////                         (SourceDataLine) AudioSystem.getLine(info)) {
////                final SourceDataLine line1 = (SourceDataLine) AudioSystem.getLine(info);
////                if (line != null) {
////                    line.open(outFormat);
////                    //line.start();
////                    line1.start();
//////                    byte[] a=getbyte(getAudioInputStream(outFormat,in));
//////                    byteout(line,a);
////                    AudioInputStream in2 = getAudioInputStream(new File("Resources/sounds/beep.wav"));
////                    stream(getAudioInputStream(outFormat, in2), line);
////                    stream(getAudioInputStream(outFormat, in), line1);
//////                    line.drain();
//////                    line.stop();
////                }
////            }
//
//    }
//
//    private AudioFormat getOutFormat(AudioFormat inFormat) {
//        final int ch = inFormat.getChannels();
//        final float rate = inFormat.getSampleRate();
//        return new AudioFormat(PCM_SIGNED, rate, 16, ch, ch * 2, rate, false);
//    }
//
//    private void stream(AudioInputStream in, SourceDataLine line)
//            throws IOException {
//        final byte[] buffer = new byte[2];
////        for (int i = 0; i <buffer.length ; i++) {
////            System.out.print(buffer[i]);
////        }
//        for (int n = 0; n != -1 ; n = in.read(buffer, 0, buffer.length) ) {
//             line.write(buffer, 0, n);
//        }
//        //System.out.println();
////        for (int i = 0; i <buffer.length ; i++) {
////            System.out.print(buffer[i]);
////        }
//    }
//
//    private void doublestream(AudioInputStream in1,AudioInputStream in2, SourceDataLine line1,SourceDataLine line2) throws IOException {
//        final byte[] buffer1 = new byte[32];
//        //final byte[] buffer2 = new byte[2];
//        int n1;
//        while (true) {
//            //line.write(buffer, 0, n);
//            n1 = in1.read(buffer1, 0, buffer1.length);
//            if(n1==-1)
//                break;
//            line1.write(buffer1,0,n1);
//            n1 = in2.read(buffer1, 0, buffer1.length);
//            if(n1==-1)
//                break;
//            line2.write(buffer1,0,n1);
//        }
//    }
//    private void play(ArrayList<AudioInputStream> ins,ArrayList<SourceDataLine> lines) throws IOException {
//        int n;
//        byte[] buffer=new byte[32];
//        while (true){
//            for (int i = 0; i <ins.size() ; i++) {
//                n=ins.get(i).read(buffer,0,buffer.length);
//                lines.get(i).write(buffer,0,n);
//            }
//        }
//    }
//
//    private void play(ArrayList<AudioInputStream> list) throws LineUnavailableException, IOException {
//        //ArrayList<SourceDataLine> lines = new ArrayList<>();
//        int n;
//        //ArrayList<Integer> n = new ArrayList<>();
////        AudioFormat format = new AudioFormat();
////        Info infs=new Info(SourceDataLine.class,);
//        final AudioFormat outFormat = getOutFormat(list.get(0).getFormat());
//        final Info info = new Info(SourceDataLine.class, outFormat);
//        SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
//        line.open(outFormat);
//        line.start();
////        for (int i = 0; i <list.size() ; i++) {
////            final  SourceDataLine line=(SourceDataLine) AudioSystem.getLine(info);
////            line.open(outFormat);
////            line.start();
////            lines.add(line);
////            //n.add(0);
////        }
//        byte[] buffer=new byte[4];
//        while (true){
//            for (int i = 0; i <list.size() ; i++) {
//                n=list.get(i).read(buffer,0,buffer.length);
//                System.out.println(n);
//                if(n==-1){
//                    //lines.remove(i);
//                    list.remove(i);
//                    continue;
//                }
//                line.write(buffer,0,n);
//                //System.out.println(i+" "+n);
//            }
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//}