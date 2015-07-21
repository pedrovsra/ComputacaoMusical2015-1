/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier;

import AudioLibs.Classifier.Basics.Category;
import AudioLibs.Classifier.Controllers.BaseRecordings;
import AudioLibs.Classifier.Core.Core;
import AudioLibs.Classifier.Util.ACECommunicator;
import AudioLibs.Classifier.Util.Embedding;
import jAudio.JAudioCommandLine;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Pedro Tiago
 */
public class Classifier {

    private final String filePath = "assets/Models/";
    private Core core;
    private ACECommunicator ace;

    public Classifier() {
        this.core = Core.getInstance();
        this.ace = ACECommunicator.getInstance();
    }

    public void addCategory(String name) {
        Category c = core.addCategory(name);
    }

    public void addRecording(String catName, String recPath) {
        File f = new File(recPath);
        core.addRecording(catName, f.getName(), recPath);
    }

    public void train(String name, String inAlg, String inDr) {
        String method = "", dr = "";
        switch (inAlg) {
            case ("Unweighted k-nn (k = 1)"):
                method = "ibk";
                break;
            case ("Naive Bayesian (Gaussian)"):
                method = "NaiveBayes";
                break;
            case ("Support Vector Machine"):
                method = "smo";
                break;
            case ("C4.5 Decision Tree"):
                method = "j48";
                break;
            case ("Backprop Neural Network"):
                method = "MultilayerPerceptron";
                break;
            case ("AdaBoost seeded with C4.5 Decision Trees"):
                method = "AdaBoostM1";
                break;
            case ("Bagging"):
                method = "Bagging";
                break;
        }

        switch (inDr) {
            case ("Principal Components Analysis (PCA)"):
                dr = "PCA";
                break;
            case ("Exhaustive search using naive Bayesian classifier (EXB)"):
                dr = "EXB";
                break;
            case ("Genetic search using naive Bayesian classifier (GNB)"):
                dr = "GNB";
                break;
            case ("none"):
                dr = "none";
                break;
        }

        this.ace.train(name, method, dr);

    }

    public void extract() throws Exception {
        BaseRecordings base = core.getBaseRecordings();
        ArrayList<String> args = new ArrayList<String>();

        args.add("-s");
        args.add("classifiers/jaudio-settings.xml");
        args.add("feature_values");

        for (int i = 0; i < base.getRecordingsSize(); i++) {
            args.add(base.getRecording(i).getPath());
        }

        String[] arg = args.toArray(new String[core.getRecordingsSize() + 3]);

        JAudioCommandLine.executeException(arg);
        System.out.println("terminei");
    }

    public void export(String name) {
        Embedding.exportClassifier(name);
    }
}