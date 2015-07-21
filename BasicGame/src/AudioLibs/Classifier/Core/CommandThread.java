/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AudioLibs.Classifier.Core;

import ace.CommandLine;
import java.util.logging.Level;
import java.util.logging.Logger;
import AudioLibs.Classifier.Util.ACECommunicator;

/**
 *
 * @author Pedro Tiago
 */
public class CommandThread extends Thread {

    private String[] args;

    public CommandThread(String[] args) {
        this.args = args;
    }

    public void run() {
        try {
            new CommandLine(args).processRequests();
        } catch (Exception ex) {
            Logger.getLogger(ACECommunicator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}