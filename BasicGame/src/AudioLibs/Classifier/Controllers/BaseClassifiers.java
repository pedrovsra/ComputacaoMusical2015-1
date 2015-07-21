/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Controllers;

/**
 *
 * @author Pedro Tiago
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Bruno
 */
public class BaseClassifiers {

    private ArrayList<String> classifiers;
    private File classifierFile;

    public BaseClassifiers() {
        classifierFile = new File("classifiers/list");
        classifiers = new ArrayList<>();
        loadClassifiers();
    }

    public String getName(int i) {
        return classifiers.get(i);
    }

    public int getSize() {
        return classifiers.size();
    }

    public void addClassifier(String model) {
        classifiers.add(model);
        copyTaxonomy(model);
        writeFile();

    }

    public void copyTaxonomy(String modelname) {
        File src = new File("classifiers/Taxonomy.xml");
        File dest = new File("classifiers/Taxonomy-" + modelname + ".xml");

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

    public void writeFile() {
        try {
            FileWriter fw = new FileWriter(classifierFile.getAbsoluteFile());

            BufferedWriter bw = new BufferedWriter(fw);

            for (String classifier : classifiers) {
                bw.write(classifier);
                bw.newLine();
            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadClassifiers() {

        BufferedReader br;
        try {
            try {
                br = new BufferedReader(new FileReader(classifierFile));
            } catch (FileNotFoundException e) {
                classifierFile.createNewFile();
                br = new BufferedReader(new FileReader(classifierFile));
            }

            String line = null;
            classifiers.clear();

            while ((line = br.readLine()) != null) {

                classifiers.add(line);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int isRepeated(String model) {
        int repeated = -1;

        for (int i = 0; i < classifiers.size(); i++) {
            if (classifiers.get(i).equals(model)) {
                repeated = i;
                break;
            }
        }
        return repeated;
    }

    public void replaceClassifier(int index, String model) {
        classifiers.set(index, model);
        writeFile();
    }

    public void removeClassifier(int index) {
        String name = classifiers.get(index);

        File file = new File("classifiers/" + name + ".model");

        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("File to be deleted not found");
        }

        file = new File("classifiers/Taxonomy-" + name + ".xml");

        if (file.delete()) {
            System.out.println(file.getName() + " is deleted!");
        } else {
            System.out.println("File to be deleted not found");
        }

        classifiers.remove(index);
        writeFile();
    }
}