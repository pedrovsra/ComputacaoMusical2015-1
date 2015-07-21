/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Core;

import AudioLibs.Classifier.Basics.Category;
import AudioLibs.Classifier.Basics.Recording;
import AudioLibs.Classifier.Controllers.BaseRecordings;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFrame;
import jAudio.JAudioCommandLine;

/**
 *
 * @author Pedro Tiago
 */
public class JAudioThread extends Thread {

    private JFrame mainScreen;
    private Core core;
    private boolean isRecordings;

    public JAudioThread(JFrame mainScreen, boolean recordings) {
        this.mainScreen = mainScreen;
        core = Core.getInstance();
        this.isRecordings = recordings;
    }

    public void run() {
        if (isRecordings) {
            extractFeatures();
        } else {
            extractFeaturesCategories();
        }
    }

    public void extractFeatures() {
        BaseRecordings recordings = core.getBaseRecordings();

        File jsettings = new File("jaudio-settings.xml");
        String jspath = jsettings.getAbsolutePath();

        ArrayList<String> argsArray = new ArrayList();
        argsArray.add("-s");
        argsArray.add(jspath);
        argsArray.add("feature_values");
        for (int i = 0; i < recordings.getRecordingsSize(); i++) {
            argsArray.add(recordings.getRecording(i).getPath());
        }

        String[] args = argsArray.toArray(new String[(argsArray.size())]);

        try {
            JAudioCommandLine.execute(args);
        } catch (Exception e) {
            //JOptionPane.showMessageDialog(mainScreen, "Error during extraction","Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void extractFeaturesCategories() {
        ArrayList<String> argsArray = new ArrayList();

        File jsettings = new File("jaudio-settings.xml");
        String jspath = jsettings.getAbsolutePath();

        argsArray.add("-s");
        argsArray.add(jspath);
        argsArray.add("feature_values");

        ArrayList<Category> categories = (ArrayList<Category>) core.getBaseCategories().getCategories();
        ArrayList<Recording> recordings;
        for (int i = 0; i < categories.size(); i++) {
            recordings = (ArrayList<Recording>) categories.get(i).getRecordings();
            for (int k = 0; k < recordings.size(); k++) {
                boolean repeated = false;
                //only inserts if recording does not exist
                for (int y = 0; y < argsArray.size(); y++) {
                    if (recordings.get(i).getPath().compareTo(recordings.get(k).getPath()) == 0) {
                        repeated = true;
                        break;
                    }
                }
                if (!repeated) {
                    argsArray.add(recordings.get(k).getPath());
                }
            }
        }

        String[] args = argsArray.toArray(new String[(argsArray.size())]);
        try {
            JAudioCommandLine.execute(args);
        } catch (Exception e) {
            System.out.println("error");
        }
    }
}