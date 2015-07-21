/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Util;

import ace.datatypes.SegmentedClassification;
import static ace.xmlparsers.XMLDocumentParser.parseXMLDocument;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * @author Bruno
 */
public class Embedding {

    //TODO: explain what to do next
    public static void exportClassifier(String classifierName) {
        // -zipfile myproject.zip -dozip myproject 
        //  copy ace files into folder export/classifiername
        copyModel(classifierName);
        copyTaxonomy(classifierName);

        System.out.println("classifier sucessfully exported");
    }

    private static void copyModel(String modelname) {
        File src = new File("classifiers/" + modelname + ".model");
        File dest = new File("export/classifiers/" + modelname + "/classifier.model");

        InputStream is = null;
        OutputStream os = null;
        try {

            dest.getParentFile().mkdirs();
            dest.createNewFile();

            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //TODO throw error if src taxonomy does not exists
    private static void copyTaxonomy(String modelname) {
        File src = new File("classifiers/Taxonomy-" + modelname + ".xml");
        File dest = new File("export/classifiers/" + modelname + "/Taxonomy.xml");

        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(src);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}