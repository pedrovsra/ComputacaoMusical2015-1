/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Fast;

import AudioLibs.Classifier.Fast.Classification.InstanceClassifier;
import AudioLibs.Classifier.Fast.dataTypes.DataBoard;
import AudioLibs.Classifier.Fast.dataTypes.DataSet;
import AudioLibs.Classifier.Fast.dataTypes.FeatureDefinition;
import AudioLibs.Classifier.Fast.dataTypes.SegmentedClassification;
import AudioLibs.Classifier.Fast.dataTypes.Taxonomy;
import ace.datatypes.TrainedModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.DefaultMutableTreeNode;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author Bruno
 */
public class FastClassifier {

    private static DataBoard db;
    private static Instances inst;
    private static TrainedModel model;
    private static SegmentedClassification[] resulting_classifications;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller control = Controller.getInstance();

        double[] samples = control.readTxtFile("wolfcrab.txt");
        System.out.println("samples read. " + samples.length);

        final long startTime = System.currentTimeMillis();
        double[][][] features = null;
        try {
            features = control.ExtractFeatures(samples, null);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //first indice is the aggregator, the second the feature
            control.calcOverallFeatures(features);
        } catch (Exception ex) {
            System.out.println("error while calculating overall Features");
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[][] overallFeatures = control.getOverallFeatureValues();

        //printing feature 0 of window 0 num of dimensions
        System.out.println(features[0][0].length);
        System.out.println(overallFeatures.length);
        //printing mfcc dimension 2
        System.out.println(overallFeatures[0].length);

        //GOT THE FEATURES, CLASSIFY NOW BITCH.
        createDataBoard(overallFeatures);
        createInstances();
        try {
            createTrainedModel("sopro-lean-stdev.model");
        } catch (IOException ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        classify();
        final long endTime = System.currentTimeMillis();
        System.out.println(resulting_classifications[0].classifications[0]);
        System.out.println("Total execution time: " + (endTime - startTime) + "milliseconds");

    }

    public static void clean() {
        db = null;
        inst = null;
        model = null;
        resulting_classifications = null;
    }

    public static String classifyAssobio(double[] samples) {
        Controller control = Controller.getInstance();

        double[][][] features = null;
        try {
            features = control.ExtractFeatures(samples, null);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //first indice is the aggregator, the second the feature
            control.calcOverallFeatures(features);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[][] overallFeatures = control.getOverallFeatureValues();

        //GOT THE FEATURES, CLASSIFY NOW
        createDataBoard(overallFeatures);
        createInstances();
        try {
            createTrainedModel("assobio-teste-mfcc.model");
        } catch (IOException ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        classify();
        return resulting_classifications[0].classifications[0];
    }

    public static String classifyAssobio(double[] samples, TrainedModel trained) {
        Controller control = Controller.getInstance();
        model = trained;

        double[][][] features = null;
        try {
            features = control.ExtractFeatures(samples, null);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            //first indice is the aggregator, the second the feature
            control.calcOverallFeatures(features);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }
        double[][] overallFeatures = control.getOverallFeatureValues();

        //GOT THE FEATURES, CLASSIFY NOW
        createDataBoard(overallFeatures);
        createInstances();
        classify();
        return resulting_classifications[0].classifications[0];
    }

    public static void createInstances() {
        try {
            //must be run after createdataboard
            inst = db.getInstanceAttributes("Testing Relation", 100);
            boolean use_top_level_features = true;
            //******this may lead to bugs (sub-sections)
            boolean use_sub_section_features = true;
            db.storeInstances(inst, use_top_level_features, use_sub_section_features);


        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void classify() {
        try {
            resulting_classifications = InstanceClassifier.classify(model, db, inst, null, false);
        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public static void createTrainedModel(String testing_to_load_classifiers_file) throws IOException {
        try {
            FileInputStream load_stream = new FileInputStream(new File(testing_to_load_classifiers_file));
            ObjectInputStream object_stream = new ObjectInputStream(load_stream);
            model = (TrainedModel) object_stream.readObject();
            load_stream.close();
        } catch (IOException e) {
            throw new IOException("Invalid classifier file: " + testing_to_load_classifiers_file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createDataBoard(double[][] overallFeatures) {
        Controller control = Controller.getInstance();
        FeatureDefinition[] defs = control.getOverallFeatureDefinitions();
        // defs[0] = new FeatureDefinition("MFCC Overall Average","MFCC calculations based upon Orange Cow code\n" +
//"This is the overall average over all windows.",true,13);

        DataSet[] values = new DataSet[1];

        values[0] = new DataSet();
        values[0].identifier = "assobioclassifier";
        values[0].feature_values = overallFeatures;

        //String[] names = {"MFCC Overall Average"};
        String[] names = new String[defs.length];
        for (int i = 0; i < defs.length; i++) {
            names[i] = defs[i].name;
            System.out.println(names[i]);
        }
        values[0].feature_names = names;

        Taxonomy tax = new Taxonomy();
        //create tree
        /* how to add the nodes
         DefaultMutableTreeNode yesnode = new DefaultMutableTreeNode("yes");
         DefaultMutableTreeNode nonode = new DefaultMutableTreeNode("no");*/
        //create taxonomy
        tax.addClass("yes");
        tax.addClass("no");

        try {
            //create databoard
            db = new DataBoard(tax, defs, values, null);


        } catch (Exception ex) {
            Logger.getLogger(FastClassifier.class.getName()).log(Level.SEVERE, null, ex);
        }





    }
}
