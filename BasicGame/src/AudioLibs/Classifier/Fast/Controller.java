/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Fast;

import AudioLibs.Classifier.Fast.GeneralTools.DSPMethods;
import AudioLibs.Classifier.Fast.dataTypes.FeatureDefinition;
import AudioLibs.Classifier.Fast.featureExtraction.Aggregators.Aggregator;
import AudioLibs.Classifier.Fast.featureExtraction.Aggregators.StandardDeviation;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.MFCC;
import AudioLibs.Classifier.Fast.featureExtraction.FeatureProcessor;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.AreaMoments;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.BeatHistogram;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.BeatHistogramLabels;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.BeatSum;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.Compactness;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.FFTBinFrequencies;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.FeatureExtractor;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.FractionOfLowEnergyWindows;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.LPC;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.MagnitudeSpectrum;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.Moments;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.PowerSpectrum;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.RMS;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.RelativeDifferenceFunction;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.SpectralCentroid;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.SpectralFlux;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.SpectralRolloffPoint;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.SpectralVariability;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.StrengthOfStrongestBeat;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.StrongestBeat;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.StrongestFrequencyViaFFTMax;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.StrongestFrequencyViaSpectralCentroid;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.StrongestFrequencyViaZeroCrossings;
import AudioLibs.Classifier.Fast.featureExtraction.audioFeatures.ZeroCrossings;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class Controller {

    private FeatureExtractor[] features;
    private boolean[] definitions;
    private FeatureProcessor processor;
    private static Controller control;

    //return must be an array
    //make a documentation explaining which features represent each indice
    private Controller() {
        //TODO: user sets parameters
        //TODO: make processor settings be set separately. Example: set at launch to gain speed.
        //you will need to check if the parameters have been set before

        populateFeatures();
        int windowSize = 512;
        double windowOverlap = 0;
        double samplingRate = 16000;
        //normalise as different function?
        boolean normalise = true;
        boolean perWindowStats = false;
        boolean overallStats = true;

        try {
            //TODO: change definition input by the function one. To control which features to extract from the outside
            processor = new FeatureProcessor(windowSize,
                    windowOverlap, samplingRate, normalise, this.features,
                    this.definitions, perWindowStats,
                    overallStats);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Controller getInstance() {
        if (control == null) {
            control = new Controller();
            return control;
        } else {
            return control;
        }
    }

    /**
     * Extract Features from audio File
     *
     * @param audioSamples The samples of the audio(in mono) to be extracted
     * features from.
     * @param featuresToExtract Array of booleans indicating which features to
     * extract.
     * @throws Exception Throws an informative exception if the input parameters
     * are invalid.
     */
    public double[][][] ExtractFeatures(double[] audioSamples, boolean[] featuresToExtract) {



        //returns features for each window.

        double[][][] feat = null;
        try {
            feat = processor.extractFeatures(audioSamples, null);
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        //aggregates features.
        //indice 1 means means mean will be taken
        //double[][] overallFeat = processor.aggregateFeatures(feat, 1);

        return feat;

    }

    //exepction vem do getoverallrecordingfeatures
    public void calcOverallFeatures(double[][][] windowFeatureValues) throws Exception {
        Aggregator[] aggList = new Aggregator[1];
        aggList[0] = new StandardDeviation();
//            aggList[1] = new Mean();
//			aggList[2] = new AreaMoments();
//			aggList[2].setParameters(new String[]{"MFCC"},new String[]{});
//			aggList[3] = new AreaMoments();
//			aggList[3].setParameters(new String[]{"LPC"},new String[]{});
//			aggList[4] = new AreaMoments();
//			aggList[4].setParameters(new String[]{"Derivative of MFCC"},new String[]{});
//			aggList[5] = new AreaMoments();
//			aggList[5].setParameters(new String[]{"Derivative of LPC"},new String[]{});
//			aggList[6] = new AreaMoments();
//			aggList[6].setParameters(new String[]{"Derivative of Method of Moments"},new String[]{});
//			aggList[7] = new AreaMoments();
//			aggList[7].setParameters(new String[]{"Method of Moments"},new String[]{});
//			aggList[8] = new AreaMoments();
//			aggList[8].setParameters(new String[]{"Area Method of Moments"},new String[]{});
//			aggList[9] = new AreaMoments();
//			aggList[9].setParameters(new String[]{"Derivative of Area Method of Moments"},new String[]{});
//			aggList[2] = new MFCC();
//			aggList[2] = new MultipleFeatureHistogram(new FeatureExtractor[]{new RMS(),new ZeroCrossings()},8);
//			aggList[3] = new MultipleFeatureHistogram(new FeatureExtractor[]{new MFCC()},4);
        processor.calcOverallRecordingFeatures(windowFeatureValues, aggList);
    }

    public double[][] getOverallFeatureValues() {
        return processor.getOverallRecordingFeatureValues();
    }

    public FeatureDefinition[] getOverallFeatureDefinitions() {
        return processor.getOverallRecordingFeatureDefinitions();
    }

    //the first indice will be the feature. If you choose to get mean and stdeviation, even indecis will hold means and odd deviations.
    public double[][] getOverallFeaturesBackup(double[][][] windowFeatureValues) {
        return processor.getOverallRecordingFeatures(
                windowFeatureValues, true, false);

    }

    private void populateFeatures() {
        FeatureExtractor[] extractors = features;
        boolean[] def = definitions;
        features = new FeatureExtractor[24];
        definitions = new boolean[24];
        ArrayIndex = 0;

        addFeature(new AreaMoments(), true);
        addFeature(new BeatHistogram(), false);
        addFeature(new BeatHistogramLabels(), false);
        addFeature(new BeatSum(), false);
        addFeature(new Compactness(), false);
        addFeature(new FFTBinFrequencies(), false);
        addFeature(new FractionOfLowEnergyWindows(), false);
        // extractors.add(new HarmonicSpectralCentroid());
        // def.add(false);
        // extractors.add(new HarmonicSpectralFlux());
        // def.add(false);
        // extractors.add(new HarmonicSpectralSmoothness());
        // def.add(false);
        addFeature(new MagnitudeSpectrum(), false);
        addFeature(new MFCC(), true);
        addFeature(new LPC(), true);
        addFeature(new Moments(), false);
        // extractors.add(new PeakFinder());
        // def.add(false);
        addFeature(new PowerSpectrum(), false);
        addFeature(new RelativeDifferenceFunction(), false);
        addFeature(new RMS(), false);
        addFeature(new SpectralCentroid(), false);
        addFeature(new SpectralFlux(), false);
        addFeature(new SpectralRolloffPoint(), false);
        addFeature(new SpectralVariability(), false);
        addFeature(new StrengthOfStrongestBeat(), false);
        addFeature(new StrongestBeat(), false);
        // extractors.add(new StrongestFrequencyVariability());
        // def.add(false);
        addFeature(new StrongestFrequencyViaFFTMax(), false);
        addFeature(new StrongestFrequencyViaSpectralCentroid(), false);
        addFeature(new StrongestFrequencyViaZeroCrossings(), false);
        addFeature(new ZeroCrossings(), false);

    }
    private int ArrayIndex = 0;

    private void addFeature(FeatureExtractor feat, boolean extract) {

        features[ArrayIndex] = feat;
        definitions[ArrayIndex] = extract;
        ArrayIndex++;

    }

    //get audio samples and return the normalised samples 
    public double[] normalizeSamples(double[] audioSamples) {
        return DSPMethods.normalizeSamples(audioSamples);
    }

    public void splitWindows() {
        //split windows according to size
    }

    //get a wav file and return its samples
    public double[] decodeWav(String filepath) {
        return new double[10];
    }

    public static double[] readTxtFile(String path) {
        try {
            Scanner in = new Scanner(new FileReader(path));
            ArrayList<Double> samples = new ArrayList<Double>();
            String next;
            while (in.hasNext()) {
                next = in.next();
                samples.add(Double.parseDouble(next));
            }

            double[] primSamples = new double[samples.size()];

            for (int i = 0; i < samples.size(); i++) {
                primSamples[i] = samples.get(i);
            }

            return primSamples;

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
