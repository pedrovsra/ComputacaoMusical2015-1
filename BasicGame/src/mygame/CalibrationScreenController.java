package mygame;

import AudioLibs.AudioListener;
import AudioLibs.Classifier.Classifier;
import AudioLibs.LineListener;
import AudioLibs.Model.Note;
import AudioLibs.Model.WAVEncoder;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioNode;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.swing.JFrame;

/**
 *
 * @author Pedro Tiago
 */
public class CalibrationScreenController extends AbstractAppState implements ScreenController {

    private static final int SAMPLES = 88200;
    private Nifty nifty;
    private AudioListener aL;
    private WAVEncoder wav;
    //private AudioNode tick;
    private StartScreenApp app;
    private Classifier classifier;
    LineListener line;
    String filePath = "";
    int n = 1;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (StartScreenApp) app;
        this.classifier = new Classifier();
        System.out.println("iniciei1");
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.classifier = new Classifier();
        System.out.println("iniciei");
    }

    @Override
    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void startScreen(String screen) {
        this.nifty.gotoScreen(screen);
    }

    public void calibrate(String type) throws Exception {
        //this.aL = new AudioListener(16000, (short) 16, (short) 2);
        //if (!this.aL.isOpen()) {
        //    throw new LineUnavailableException();
        //}

        String[] arr = {"chimbal", "prato", "bumbo", "tom", "caixa"};

        System.out.println(type);
        switch (type) {
            case "chimbal":
                captureAndTrain("chimbal");
                /*
                 // calibrando 2 a 2
                 for (int i = 1; i < arr.length; i++) {
                 captureAndTrain(type + "_" + arr[i]);
                 }

                 // calibrando 3 a 3
                 for (int i = 1; i < arr.length; i++) {
                 for (int j = i + 1; j < arr.length; j++) {
                 captureAndTrain(type + "_" + arr[i] + "_" + arr[j]);
                 }
                 }

                 // calibrando 4 a 4
                 for (int i = 1; i < arr.length; i++) {
                 for (int j = i + 1; j < arr.length; j++) {
                 for (int k = j + 1; k < arr.length; k++) {
                 captureAndTrain(type + "_" + arr[i] + "_" + arr[j] + "_" + arr[k]);
                 }
                 }
                 }

                 captureAndTrain(type + "_" + arr[1] + "_" + arr[2] + "_" + arr[3] + "_" + arr[4]);
                 */
                captureAndTrain(type + "_tom_bumbo");
                captureAndTrain(type + "_caixa_tom_bumbo");
                break;
            case "prato":
                captureAndTrain("prato");
                captureAndTrain(type + "_bumbo");
                captureAndTrain(type + "_caixa");
                break;
            case "bumbo":
                captureAndTrain("bumbo");
                break;
            case "tom":
                captureAndTrain("tom");
                break;
            case "caixa":
                captureAndTrain("caixa");
                break;
            case "treinar":
                train();
                break;
        }
        //this.aL.close();
    }

    private void captureAndTrain(String classe) throws Exception {
        JFrame frame;
        System.out.println("entrei");
        line = new LineListener();
        //this.tick = new AudioNode(app.getAssetManager(), "Sounds/tick.wav");
        //this.tick.setLooping(false);
        //this.tick.setPositional(false);
        //this.tick.setVolume(3);
        //app.getRootNode().attachChild(tick);

        ArrayList<Note> notes = new ArrayList<>();
        //ArrayList<Float> arr = new ArrayList<Float>();
        byte[] buffer = new byte[SAMPLES];
        int numSamples = 0;
        byte[] samples = new byte[1024];
        //float[] samplesF;
        //int n = 1;
        filePath = "assets/Models/" + classe + "-";
        //this.wav = new WAVEncoder("", aL.getChannels(), aL.getSampleSize(), aL.getSamplingRate());
        //System.out.println(aL.getChannels() + "  " + aL.getSampleSize() + "  " + aL.getSamplingRate());

        //AudioFormat outputFormat = new AudioFormat(44100.0f, 16, 2, true, true);
        //SourceDataLine output = AudioSystem.getSourceDataLine(outputFormat);
        //DataLine.Info outInfo = new DataLine.Info(SourceDataLine.class, outputFormat);

        //output = (SourceDataLine) AudioSystem.getLine(outInfo);
        //output.open(this.aL.getAudioFormat());

        do {
            //this.wav.setFilePath(filePath + n);
            //this.wav.prepare();
            frame = new JFrame();
            frame.setSize(400, 100);
            frame.setBounds(600, 600, 600, 100);
            frame.setVisible(true);
            numSamples = 0;
            int second = 3;

            while (second > 0) {
                frame.setTitle("Em " + second-- + " segundos, toque o " + classe.toUpperCase() + "!");
                //tick.play();
                Thread.sleep(1000);
            }
            //output.flush();
            //output.start();

            //this.aL.flush();


            frame.setTitle("AGORA!");
            //this.aL.start();
            //Thread.sleep(1000);
            //frame.dispose();
            line.open();
            System.out.println("open");

            /*
             new Thread(new Runnable() {
             @Override
             public void run() {
             try {
             System.out.println("sleep");
             Thread.sleep(1000);
             } catch (InterruptedException ex) {
             ex.printStackTrace();
             }
             line.stop();
             System.out.println("stop");
             line.close();
             System.out.println("close");
             }
             }).start();
             */

            Thread recordThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("Start recording...");
                    try {
                        line.record(filePath + n);
                    } catch (IOException ex) {
                        Logger.getLogger(CalibrationScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(CalibrationScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            recordThread.start();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            line.stop();
            line.close();

            /*
             line.record(filePath + n);
             Thread.sleep(1000);
             line.stop();
             System.out.println("stop");
             line.close();
             System.out.println("close");
             */

            /*
             int c = 1;
             while (numSamples < SAMPLES) {
             samples = this.aL.read();
             numSamples += this.aL.getNumBytesRead();
             //output.write(samples, 0, 1024);
             this.wav.write(samples);
             //arr.addAll(AudioProcess.convertByte2ArrayFloat(samples));
             //byteArray.addAll(AudioProcess.convertArray2List(samples));
             /*
             for (int j = 0; j < samples.length; j++) {
             buffer[j * c] = samples[j];
             }
             c++;
                 
             }
             * */


            //this.aL.stop();
            //output.close();

            //this.wav.setPayload(numSamples);

            //this.wav.save();
            //this.wav.save(AudioProcess.convertList2Array(byteArray));

            //frame = new JFrame();
            //frame.setSize(400, 100);
            //frame.setBounds(600, 600, 600, 100);
            //frame.setVisible(true);
            frame.setTitle("OKAY!");
            Thread.sleep(2000);
            frame.dispose();
            //samplesF = new float[arr.size()];
            //for (int i = 0; i < arr.size(); i++) {
            //    samplesF[i] = arr.get(i);
            //}

            notes.add(new Note(filePath));

            //arr.clear();
            System.out.println("clear");
        } while (--n > 0);
        // System.out.println(this.aL.isActive());

        System.out.println("quantidade de notas: " + notes.size());

        //TODO ENVIAR PARA TREINAR
        //mandar pro treino (nome da classe, arquivos)
        this.classifier.addCategory(classe);
        for (int i = 1; i <= 10; i++) {
            this.classifier.addRecording(classe, notes.get(i - 1).getFilePath());
        }

        /*
         int soma = 0;
         for (Note no : notes) {
         soma += no.getSamples().length;
         }
         System.out.println(soma);
         */
        notes.clear();
    }

    private void train() {
        //this.aL.close();
        System.out.println("1");
        try {
            this.classifier.extract();
            System.out.println("2");
            this.classifier.train("My Trained Model", "C4.5 Decision Tree", "none");
            System.out.println("3");
            this.classifier.export("My Trained Model");
            System.out.println("4");
        } catch (Exception ex) {
            //Logger.getLogger(CalibrationScreenController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("deu merda");
        }
    }
}