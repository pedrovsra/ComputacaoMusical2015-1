/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Basics;

/**
 *
 * @author Pedro Tiago
 */
public class Recording {

    private String name;
    private String path;

    public Recording(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}