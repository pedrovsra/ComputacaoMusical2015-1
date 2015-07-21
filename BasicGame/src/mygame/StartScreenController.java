package mygame;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 *
 * @author Pedro Tiago
 */
public class StartScreenController extends AbstractAppState implements ScreenController {

    private StartScreenApp app;
    private Nifty nifty;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        this.app = (StartScreenApp) app;
        super.initialize(stateManager, app);
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStartScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onEndScreen() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void startGame(String nextScreen) {
        //this.app.stopAudio();
        this.nifty.gotoScreen(nextScreen);
    }

    public void quitGame() {
        System.exit(0);
        //this.app.stop();
    }

    public void calibrate(String nextScreen) {
        //this.app.stopAudio();
        nifty.gotoScreen(nextScreen);
    }
}
