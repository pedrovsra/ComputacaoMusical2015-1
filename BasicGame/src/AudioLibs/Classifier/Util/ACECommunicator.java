/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Util;

import ace.CommandLine;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import AudioLibs.Classifier.Core.CommandThread;

/**
 *
 * @author Pedro Tiago
 */
public class ACECommunicator {

    private File aceCommunicator;
    private CommandLine aceCmd;
    private static ACECommunicator comunicator = null;

    private ACECommunicator() {
    }

    public static ACECommunicator getInstance() {
        if (comunicator != null) {
            return comunicator;
        } else {
            return comunicator = new ACECommunicator();
        }
    }

    public void train(String output, String learner, String dr) {
        //command
        // -lfkey feature_valuesFK.xml -lfvec feature_valuesFV.xml -lmclas Instances.xml -train -sres saida.model -learner learner
        ArrayList<String> args = new ArrayList<>();

        args.add("-lfkey");
        args.add("feature_valuesFK.xml");
        args.add("-lfvec");
        args.add("feature_valuesFV.xml");
        args.add("-lmclas");
        args.add("Instances.xml");
        args.add("-train");
        args.add("-sres");
        args.add("classifiers/" + output + ".model");
        args.add("-learner");
        args.add(learner);

        if (dr != "none") {
            args.add("-dr");
            args.add(dr);
        }

        String[] argsArray = args.toArray(new String[args.size()]);

        //threaded way

        new CommandThread(argsArray).start();
        //copy taxonomy file
    }

    public void classify(String model) {

        ArrayList<String> args = new ArrayList<>();

        args.add("-lfkey");
        args.add("feature_valuesFK.xml");
        args.add("-lfvec");
        args.add("feature_valuesFV.xml");
        //TODO: add option to test classifier hit rate, adding instance
        //args.add("-lmclas");
        //args.add("Instances.xml");
        args.add("-ltax");
        args.add("classifiers/Taxonomy-" + model + ".xml");
        args.add("-classify");
        args.add("classifiers/" + model + ".model");
        args.add("-sres");
        args.add("classifications.xml");


        String[] argsArray = args.toArray(new String[args.size()]);

        CommandThread cmd = new CommandThread(argsArray);

        cmd.start();
        try {
            cmd.join();
            System.out.println("Classification completed!");
            System.out.println("Open file \"..EasyClassifier4MIR/classifications.xml\" to check output");
        } catch (Exception e) {
            System.out.println("Error during classification");
        }
    }

    public String classify2(String fdef, String fvec, String classes, String model) {
        String response = "";
        try {
            File ffdef = new File(fdef);
            File ffvec = new File(fvec);
            File fclasses = new File(classes);
            File fmodel = new File(model);
            ProcessBuilder processBuilder = new ProcessBuilder("java", "-jar", aceCommunicator.getName(), "-lfkey",
                    ffdef.getAbsolutePath(), "-lfvec", ffvec.getAbsolutePath(),
                    "-lmclas", fclasses.getAbsolutePath(), "-classify", fmodel.getAbsolutePath());

            processBuilder.directory(aceCommunicator.getParentFile());

            Process process = processBuilder.start();
            process.waitFor();
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufr = new BufferedReader(isr);
            String line;

            while ((line = bufr.readLine()) != null) {
                response = response + line + "\n";
            }

        } catch (IOException ex) {
            Logger.getLogger(ACECommunicator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ACECommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return response;
    }

    public void crossValidate(String folds, String algorithm, String dr) {
        ArrayList<String> args = new ArrayList<>();


        args.add("-lfkey");
        args.add("feature_valuesFK.xml");
        args.add("-lfvec");
        args.add("feature_valuesFV.xml");
        args.add("-lmclas");
        args.add("Instances.xml");
        args.add("-cv");
        args.add(folds);
        args.add("-learner");
        args.add(algorithm);

        if (dr != "none") {
            args.add("-dr");
            args.add(dr);
        }

        String[] argsArray = args.toArray(new String[args.size()]);

        //threaded way

        new CommandThread(argsArray).start();

    }

    public void experiment(String folds) {
        try {
            ArrayList<String> args = new ArrayList<>();

            //Command
            //-lfkey feature_valuesFk.xml -lfvec feature_valuesFV.xml -lmclas Instances.xml -exp 5

            args.add("-lfkey");
            args.add("feature_valuesFK.xml");
            args.add("-lfvec");
            args.add("feature_valuesFV.xml");
            args.add("-lmclas");
            args.add("Instances.xml");
            args.add("-exp");
            args.add(folds);

            String[] argsArray = args.toArray(new String[args.size()]);

            new CommandThread(argsArray).start();

            /*
             aceCmd = new CommandLine(argsArray);  
            
             aceCmd.processRequests();*/

        } catch (Exception ex) {
            Logger.getLogger(ACECommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
