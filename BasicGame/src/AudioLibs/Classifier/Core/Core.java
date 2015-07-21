/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Core;

import AudioLibs.Classifier.Basics.Category;
import AudioLibs.Classifier.Basics.Recording;
import AudioLibs.Classifier.Controllers.BaseCategories;
import AudioLibs.Classifier.Controllers.BaseClassifiers;
import AudioLibs.Classifier.Controllers.BaseRecordings;
import ace.datatypes.Taxonomy;
import jAudioFeatureExtractor.Controller;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author Pedro Tiago
 */
public class Core {

    private BaseCategories baseCategories;
    private BaseRecordings baseRecordings;
    private BaseClassifiers baseClassifiers;
    private Taxonomy taxonomy;
    private static Core core;
    private boolean help;
    //private static ResultsScreen results;

    private Core() {
        baseCategories = new BaseCategories();
        baseRecordings = new BaseRecordings();
        baseClassifiers = new BaseClassifiers();
        taxonomy = new Taxonomy();
    }

    public static Core getInstance() {
        if (core == null) {
            core = new Core();
        }
        return core;
    }

    /*
    public void setResults(ResultsScreen results) {
        this.results = results;
    }

    public ResultsScreen getResults() {
        return results;
    }
    */
    
    public Category addCategory(String id) {
        Category c = new Category(id);
        baseCategories.addCategory(c);
        taxonomy.addClass(id);
        saveAsFile();

        return c;
    }

    public Controller createController() {
        return new Controller();

    }

    public void editCategory(String oldId, String newId) {
        baseCategories.editCategory(oldId, newId);
        taxonomy.deleteClass(oldId);
        taxonomy.addClass(newId);
        saveAsFile();
    }

    public void removeCategory(String id) {
        baseCategories.removeCategory(id);
        taxonomy.deleteClass(id);
        saveAsFile();
    }

    public void addRecording(String idCategory, String name, String path) {
        if (baseCategories.getCategories().get(baseCategories.existsCategory(idCategory)).existsRecording(path) == -1) {
            Recording r = new Recording(name, path);
            baseCategories.addRecording(idCategory, r);
        }
    }

    public void addRecording(String name, String path) {
        Recording r = new Recording(name, path);
        baseRecordings.addRecording(r);
    }

    public void removeRecording(String idCategory, String pathRecording) {
        baseCategories.removeRecording(idCategory, pathRecording);
    }

    public boolean extractFeatures() {
        return baseCategories.extractFeatures();
    }

    public BaseCategories getBaseCategories() {
        return baseCategories;
    }

    public BaseClassifiers getBaseClassifiers() {
        return baseClassifiers;
    }

    public int getRecordingsSize() {
        return baseRecordings.getRecordingsSize();

    }

    public BaseRecordings getBaseRecordings() {
        return baseRecordings;

    }

    public void saveAsFile() {

        // Get file to write to
        File save_file = new File("Taxonomy.xml");
        // Change the root name of the taxonomy
        Taxonomy.setRootName(taxonomy, save_file.getPath());

        // Save the file
        try {
            Taxonomy.saveTaxonomy(taxonomy, save_file, "");
        } catch (Exception e) {
            // e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveClassifier(String model) {
        //create a copy of the files used for this classifier in the classifiers folder
    }

    public void setHelp(Boolean enabled) {
        this.help = enabled;
    }

    public Boolean getHelp() {
        return this.help;
    }
}